package com.fuwo.b3d.order.service;

import com.fuwo.b3d.GlobalConstant;
import com.fuwo.b3d.common.RestResult;
import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.model.model.Model;
import com.fuwo.b3d.model.model.ModelPack;
import com.fuwo.b3d.model.service.repository.ModelPackRepository;
import com.fuwo.b3d.model.service.repository.ModelRepository;
import com.fuwo.b3d.order.model.Order;
import com.fuwo.b3d.order.service.repository.OrderRepository;
import com.fuwo.b3d.user.model.UserInfo;
import com.fuwo.b3d.user.model.UserModel;
import com.fuwo.b3d.user.service.repository.UserInfoRepository;
import com.fuwo.b3d.user.service.repository.UserModelRepository;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CacheConfig(cacheNames = "com.fuwo.b3d.orders")
@Transactional
@Service
public class


OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserModelRepository userModelRepository;

    @Autowired
    private ClientHttpRequestFactory clientHttpRequestFactory;

    @Autowired
    private ModelPackRepository modelPackRepository;


    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private EntityManager entityManager;


    @Override
    @Transactional(readOnly = true)
    public Page<Order> pageQuery(Order order, Pageable pageable, String searchText, Date startDate, Date endDate) {
        Assert.notNull(order);
        Assert.notNull(pageable);
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int firstResult = pageNumber * pageSize;

        StringBuffer hql = new StringBuffer(" from Order t where t.status=1");
        if (StringUtils.isNotBlank(searchText)) {
            hql.append(" and ( no='").append(searchText).append("'");
            hql.append(" or t.userInfo.username like '%").append(searchText).append("%')");
        }

        if (startDate != null) {
            hql.append(" and t.created>=:startDate");
        }
        if (endDate != null) {
            hql.append(" and t.created<=:endDate");
        }

        Query query = entityManager.createQuery(hql.toString());
        if (startDate != null) {
            query.setParameter("startDate", startDate);
        }
        if (endDate != null) {
            query.setParameter("endDate", endDate);
        }
        List<Order> orders = query.setMaxResults(pageSize).setFirstResult(firstResult).getResultList();

        //get total records count
        String countHql = " select count(*)" + hql;
        Query queryTotal = entityManager.createQuery(countHql);
        if (startDate != null) {
            queryTotal.setParameter("startDate", startDate);
        }
        if (endDate != null) {
            queryTotal.setParameter("endDate", endDate);
        }

        long countResult = (long) queryTotal.getSingleResult();


        return new PageImpl(orders, pageable, countResult);
    }

    @Override
    public Order save(Order order) {
        Assert.notNull(order);
        return orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Order findByNo(String no) {
        Assert.notNull(no);
        return orderRepository.findByNo(no);
    }

    @Override
    public boolean pay(Order order) {
        Assert.notNull(order);
        if (order.getState() == Order.OrderStatusEnum.SUCCESS) {
            throw new RuntimeException("付款失败:订单已支付成功");
        }
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();

        params.set("uid", order.getUserInfo().getId());
        params.set("amount", order.getAmount() * (-1));//扣减
        params.set("deal_type", "9");
        params.set("remark", order.getDealType().getDesc());

        //请求支付
        JSONObject json = restTemplate.postForEntity(GlobalConstant.FCOIN_TRADE_API, params, JSONObject.class).getBody();
        if (json != null) {
            try {
                String code = json.getString("code");
                String msg = json.getString("msg");
                if (StringUtils.equals(RestResult.ResultCodeEnum.SUCC.getCode(), code)) {
                    //扣款成功，处理业务数据
                    dealOrder(order);
                } else {
                    throw new RuntimeException("付款失败:code:" + code + "-" + msg);
                }
            } catch (Exception e) {
                throw new RuntimeException("付款失败:" + e.getMessage());
            }
        } else {
            throw new RuntimeException("付款失败:无支付结果信息");
        }
        return true;
    }

    private void dealOrder(Order order) {
        String id = StringUtils.substring(order.getNo(), 26, 36);
        UserInfo userInfo = null;
        switch (order.getDealType()) {
            case MODELPACK:
                ModelPack modelPack = modelPackRepository.findOne(Long.valueOf(id));
                if (modelPack == null) {
                    throw new RuntimeException("付款成功，购买商品失败:模型包不存在");
                }
                List<ModelPack> modelPacks = order.getUserInfo().getModelPacks();
                if (modelPacks == null) {
                    modelPacks = new ArrayList<>();
                }
                modelPacks.add(modelPack);
                userInfo = order.getUserInfo();
                userInfo.setModelPacks(modelPacks);

                //获取模型下的模型包
                List<Model> packModels = modelPack.getModels();

                //获取当前用户下有效的模型
                List<UserModel> userModelMapper = userModelRepository.findByUidAndAndStatus(userInfo.getId(), StatusEnum.ENABLE);
                List<Model> userModels = new ArrayList<>();
                for (UserModel item : userModelMapper) {
                    userModels.add(item.getModel());
                }

                //加上新购买的模型包
                userModels.addAll(packModels);

                userInfo.setModels(userModels);
                userInfoRepository.save(userInfo);

                //更新模型包购买次数
                modelPack.setBuysIncrease(modelPack.getBuysIncrease() + 1);
                modelPackRepository.save(modelPack);

                break;
            case MODEL:
                Model model = modelRepository.findOne(Integer.valueOf(id));
                if (model == null) {
                    throw new RuntimeException("付款成功，购买商品失败:模型不存在");
                }

                userInfo = order.getUserInfo();

                //获取当前用户下有效的模型
                List<UserModel> _userModelMapper = userModelRepository.findByUidAndAndStatus(userInfo.getId(), StatusEnum.ENABLE);
                List<Model> current = new ArrayList<>();
                for (UserModel item : _userModelMapper) {
                    current.add(item.getModel());
                }

                current.add(model);//增加新购买的模型
                userInfo.setModels(current);
                userInfoRepository.save(userInfo);
                break;
        }
        //修改订单为支付成功
        order.setState(Order.OrderStatusEnum.SUCCESS);
        orderRepository.save(order);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<Order> findAll(Example<Order> example, Pageable pageable) {
        Assert.notNull(example);
        Assert.notNull(pageable);
        return orderRepository.findAll(example, pageable);
    }

    @Override
    public List<Order> findByCreatedBetween(Date sDate, Date endDate) {
        Assert.notNull(sDate);
        Assert.notNull(endDate);
        return orderRepository.findByCreatedBetween(sDate, endDate);
    }
}
