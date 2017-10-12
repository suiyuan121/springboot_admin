package com.fuwo.b3d.checkin.service;

import com.fuwo.b3d.GlobalConstant;
import com.fuwo.b3d.checkin.model.Checkin;
import com.fuwo.b3d.checkin.service.repository.CheckinRepository;
import com.fuwo.b3d.common.RestResult;
import com.fuwo.b3d.order.model.Order;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@CacheConfig(cacheNames = "com.fuwo.b3d.checkins")
@Transactional
@Service
public class CheckinServiceImpl implements CheckinService {

    @Autowired
    private CheckinRepository checkinRepository;

    @Autowired
    private ClientHttpRequestFactory clientHttpRequestFactory;

    @Autowired
    private EntityManager entityManager;

    private static final int CHECKIN_COUNT = 1;

    private static final String CHECKIN_DEAL_TYPE = "10";


    @Override
    public void save(Checkin checkin) {
        Assert.notNull(checkin);
        checkinRepository.save(checkin);
    }

    @Override
    public Checkin get(Long id) {
        Assert.notNull(id);
        return checkinRepository.findOne(id);
    }

    @Override
    public boolean checkin(Integer uid) {
        Assert.notNull(uid);

        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();

        params.set("uid", uid);
        params.set("amount", CHECKIN_COUNT);//增加福币
        params.set("deal_type", CHECKIN_DEAL_TYPE);
        params.set("remark", "签到获取福币");

        //请求支付
        JSONObject json = restTemplate.postForEntity(GlobalConstant.FCOIN_TRADE_API, params, JSONObject.class).getBody();
        if (json != null) {
            try {
                String code = json.getString("code");
                String msg = json.getString("msg");
                if (StringUtils.equals(RestResult.ResultCodeEnum.SUCC.getCode(), code)) {
                    //成功

                    //新增一条签到记录
                    Checkin checkin = new Checkin();
                    checkin.setTime(new Date());
                    checkin.setUid(uid);

                    //获取昨天的签到记录
                    Checkin lastCheckin = checkinRepository.findLastByUid(uid);
                    if (lastCheckin != null) {
                        //如果昨天已经签到，则今天的连续签到数+1
                        checkin.setCount(lastCheckin.getCount() + 1);
                    } else {
                        //昨天未签到的，今天则默认为1
                        checkin.setCount(1);
                    }
                    checkinRepository.save(checkin);

                } else {
                    throw new RuntimeException("付款失败:" + code + "-" + msg);
                }
            } catch (Exception e) {
                throw new RuntimeException("付款失败:" + e.getMessage());
            }
        } else {
            throw new RuntimeException("付款失败:无支付结果信息");
        }
        return true;
    }

    @Override
    public List<Checkin> findByDate(Integer uid, Date startDate, Date endDate) {
        Assert.notNull(uid);
        Assert.notNull(startDate);
        Assert.notNull(endDate);

        StringBuffer hql = new StringBuffer(" from Checkin t where uid=").append(uid);
        hql.append(" and t.time>=:startDate");
        hql.append(" and t.time<=:endDate");

        Query query = entityManager.createQuery(hql.toString());
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);


        return query.getResultList();
    }


    @Override
    public Checkin findCurrentByUid(Integer uid) {
        Assert.notNull(uid);
        return checkinRepository.findCurrentByUid(uid);
    }
}
