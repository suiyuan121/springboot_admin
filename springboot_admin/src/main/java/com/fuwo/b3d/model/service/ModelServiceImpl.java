package com.fuwo.b3d.model.service;

import com.fuwo.b3d.model.model.Model;
import com.fuwo.b3d.model.model.ModelCategory;
import com.fuwo.b3d.model.service.repository.ModelCategoryRepository;
import com.fuwo.b3d.model.service.repository.ModelRepository;
import com.fuwo.b3d.user.model.UserInfo;
import com.fuwo.b3d.user.service.repository.UserInfoRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private ModelCategoryRepository modelCategoryRepository;

    @Autowired
    EntityManager entityManager;


    @Override
    public Model getByModelNoAndPerfect(String modelNo, Model.PerfectEnum perfect) {
        Assert.notNull(modelNo);
        return modelRepository.findByNoAndPerfect(modelNo, perfect);
    }


    @Override
    public void save(Model model) {
        Assert.notNull(model);
        modelRepository.save(model);
    }


    @Override
    public Model get(Integer id) {
        Assert.notNull(id);
        return modelRepository.findOne(id);
    }

    @Override
    public Page<Model> findAll(Example<Model> example, Pageable pageable) {
        Assert.notNull(example);
        Assert.notNull(pageable);
        return modelRepository.findAll(example, pageable);
    }


    @Override
    public Page<Model> pageQuery(Model model, Pageable pageable, Date startDate, Date endDate) {
        Assert.notNull(pageable);
        Assert.notNull(model);

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int firstResult = pageNumber * pageSize;

        StringBuffer hql = new StringBuffer(" from Model t  where  t.state !=9");


        if (StringUtils.isNotBlank(model.getProductName())) {
            hql.append(" and t.productName  like '%").append(model.getProductName()).append("%'");
        }
        if (startDate != null) {
            hql.append(" and t.created>=:startDate");
        }
        if (endDate != null) {
            hql.append(" and t.created<=:endDate");
        }
        if (model.getState() != null) {
            //pyhton之前 0是公开  1不公开，9删除
            hql.append(" and t.state =").append(model.getState());
        }


        Query query = entityManager.createQuery(" select distinct t " + hql.toString());
        if (startDate != null) {
            query.setParameter("startDate", startDate);
        }
        if (endDate != null) {
            query.setParameter("endDate", endDate);
        }


        List<Model> models = query.setMaxResults(pageSize).setFirstResult(firstResult).getResultList();
        List<Model> modelRet = new ArrayList<Model>();
        for (Model item : models) {
            UserInfo userInfo = userInfoRepository.findById(item.getUserId());
            ModelCategory modelCategory = modelCategoryRepository.getTopByItemNoOrderByCategoryDesc(item.getNo());
            if (modelCategory != null) {
                item.setItemCategory(modelCategory.getCategory());
            }
            if (userInfo != null) {
                item.setUserInfo(userInfo);
            }
            modelRet.add(item);
        }

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


        return new PageImpl(modelRet, pageable, countResult);
    }


    @Override
    public Model getByModelNo(String modelNo) {
        Assert.notNull(modelNo);
        return modelRepository.findByNo(modelNo);
    }

    @Transactional
    @Override
    public Integer updatePrice(List<String> nos, Integer price) {
        Assert.notNull(nos);
        Assert.notNull(price);
        return modelRepository.updatePrice(price, nos);
    }

    @Override
    public List<Model> findByNos(List<String> nos) {
        Assert.notNull(nos);
        return modelRepository.findByNoIn(nos);
    }
}
