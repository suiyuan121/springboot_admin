package com.fuwo.b3d.model.service;

import com.fuwo.b3d.common.RestResult;
import com.fuwo.b3d.enums.PriceTyleEnum;
import com.fuwo.b3d.model.model.Model;
import com.fuwo.b3d.model.model.ModelComb;
import com.fuwo.b3d.model.model.ModelPack;
import com.fuwo.b3d.model.service.repository.ModelCombRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@CacheConfig(cacheNames = "com.fuwo.b3d.modelcombs")
@Transactional
@Service
public class ModelCombServiceImpl implements ModelCombService {

    @Autowired
    ModelCombRepository modelCombRepository;

    @Autowired
    EntityManager entityManager;

    @Override
    public Page<ModelComb> pageQuery(ModelComb modelComb, Pageable pageable) {
        Assert.notNull(pageable);
        Assert.notNull(modelComb);
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int firstResult = pageNumber * pageSize;

        StringBuffer hql = new StringBuffer(" from ModelComb t where t.status='1'");
        if (modelComb.getSpace() != null) {
            hql.append(" and t.space=").append(modelComb.getSpace().getCode());
        }
        if (modelComb.getStyle() != null) {
            hql.append(" and t.style=").append(modelComb.getStyle().getCode());
        }
        if (StringUtils.isNotBlank(modelComb.getName()) && StringUtils.isNotBlank(modelComb.getDesignNo())) {
            hql.append(" and t.name like '%").append(modelComb.getName()).append("%'");
            hql.append(" or t.designNo like '%").append(modelComb.getDesignNo()).append("%'");
        }

        String countHql = "select count(*)" + hql;
        hql.append("order by priority desc , modified desc");


        List<ModelComb> modelCombs = entityManager.createQuery(hql.toString()).setMaxResults(pageSize).setFirstResult(firstResult).getResultList();

        //get total records count
        Query queryTotal = entityManager.createQuery(countHql);
        long countResult = (long) queryTotal.getSingleResult();


        return new PageImpl(modelCombs, pageable, countResult);
    }


    @Override
    public void save(ModelComb modelComb) {
        Assert.notNull(modelComb);
        modelCombRepository.save(modelComb);
    }

    @Cacheable
    @Override
    public ModelComb get(Long id) {
        Assert.notNull(id);
        return modelCombRepository.findOne(id);
    }

    @Override
    public Page<ModelComb> findAll(Example<ModelComb> example, Pageable pageable) {
        Assert.notNull(example);
        Assert.notNull(pageable);
        return modelCombRepository.findAll(example, pageable);
    }


    @Override
    public Page<ModelComb> findByCond(ModelComb modelComb, PriceTyleEnum priceTyle, Pageable pageable) {
        Assert.notNull(modelComb);
        Assert.notNull(pageable);

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int firstResult = pageNumber * pageSize;

        StringBuffer hql = new StringBuffer(" from ModelComb t where status=1 ");

        if (modelComb.getStyle() != null) {
            hql.append(" and t.type=").append(modelComb.getStyle().getCode());
        }
        if (priceTyle != null && priceTyle == PriceTyleEnum.FREE) {
            hql.append(" and t.price=0");
        } else if (priceTyle != null && priceTyle == PriceTyleEnum.FCOIN) {
            hql.append(" and t.price>0");
        }
        if (StringUtils.isNotBlank(modelComb.getName())) {
            hql.append(" and t.name like '%").append(modelComb.getName()).append("%'");
        }


        hql.append(" order by t.priority desc, t.created desc");

        List<ModelComb> modelCombs = entityManager.createQuery(" select distinct t " + hql.toString()).setMaxResults(pageSize).setFirstResult(firstResult).getResultList();
        //get total records count
        String countHql = " select count(*)" + hql;
        Query queryTotal = entityManager.createQuery(countHql);
        long countResult = (long) queryTotal.getSingleResult();


        return new PageImpl(modelCombs, pageable, countResult);

    }

    @Override
    public boolean deleteModels(ModelComb modelComb, List<Model> model) {
        Assert.notNull(modelComb);
        Assert.notNull(model);
        return modelComb.getModels().removeAll(model);
    }

    @Override
    public boolean deleteModel(ModelComb modelComb, Model model) {
        Assert.notNull(modelComb);
        Assert.notNull(model);
        return modelComb.getModels().remove(model);
    }


}
