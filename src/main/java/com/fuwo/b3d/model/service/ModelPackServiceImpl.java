package com.fuwo.b3d.model.service;

import com.fuwo.b3d.enums.PriceTyleEnum;
import com.fuwo.b3d.model.model.Model;
import com.fuwo.b3d.model.model.ModelPack;
import com.fuwo.b3d.model.service.repository.ModelPackRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@CacheConfig(cacheNames = "com.fuwo.b3d.modelpacks")
@Transactional
@Service
public class ModelPackServiceImpl implements ModelPackService {

    @Autowired
    ModelPackRepository modelPackRepository;

    @Autowired
    EntityManager entityManager;

    @Override
    public Page<ModelPack> pageQuery(ModelPack modelPack, String searchText, Pageable pageable) {
        Assert.notNull(pageable);
        Assert.notNull(modelPack);

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int firstResult = pageNumber * pageSize;

        StringBuffer hql = new StringBuffer(" from ModelPack t left join t.models t2 where  t.status='1' ");

        if (modelPack.getType() != null) {
            hql.append(" and t.type=").append(modelPack.getType().getCode());
        }
        if (modelPack.getState() != null) {
            hql.append(" and t.state=").append(modelPack.getState().getCode());
        }
        if (StringUtils.isNotBlank(modelPack.getName()) && StringUtils.isNotBlank(searchText)) {
            hql.append(" and t.name  like '%").append(modelPack.getName()).append("%'");
            hql.append(" or t2.no='").append(searchText).append("'");
        }

        hql.append(" order by t.priority desc, t.modified desc");

        List<ModelPack> modelPacks = entityManager.createQuery(" select distinct t " + hql.toString()).setMaxResults(pageSize).setFirstResult(firstResult).getResultList();

        //get total records count
        String countHql = " select count(distinct t)" + hql;
        Query queryTotal = entityManager.createQuery(countHql);
        long countResult = (long) queryTotal.getSingleResult();


        return new PageImpl<ModelPack>(modelPacks, pageable, countResult);
    }


    @Override
    public void save(ModelPack modelPack) {
        Assert.notNull(modelPack);
        modelPackRepository.save(modelPack);
    }

    @Cacheable(key="#id")
    @Override
    public ModelPack get(Long id) {
        Assert.notNull(id);
        return modelPackRepository.findOne(id);
    }

    @Override
    public Page<ModelPack> findAll(Example<ModelPack> example, Pageable pageable) {
        Assert.notNull(example);
        Assert.notNull(pageable);
        return modelPackRepository.findAll(example, pageable);
    }

    @Override
    public Page<ModelPack> findByCond(ModelPack modelPack, PriceTyleEnum priceTyle, Pageable pageable) {
        Assert.notNull(modelPack);
        Assert.notNull(pageable);

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int firstResult = pageNumber * pageSize;

        StringBuffer hql = new StringBuffer(" from ModelPack t where status=1 ");

        if (modelPack.getType() != null) {
            hql.append(" and t.type=").append(modelPack.getType().getCode());
        }
        if (priceTyle != null && priceTyle == PriceTyleEnum.FREE) {
            hql.append(" and t.price=0");
        } else if (priceTyle != null && priceTyle == PriceTyleEnum.FCOIN) {
            hql.append(" and t.price>0");
        }
        if (StringUtils.isNotBlank(modelPack.getName())) {
            hql.append(" and t.name like '%").append(modelPack.getName()).append("%'");
        }


        hql.append(" order by t.priority desc, t.created desc");

        List<ModelPack> modelPacks = entityManager.createQuery(" select distinct t " + hql.toString()).setMaxResults(pageSize).setFirstResult(firstResult).getResultList();
        //get total records count
        String countHql = " select count(*)" + hql;
        Query queryTotal = entityManager.createQuery(countHql);
        long countResult = (long) queryTotal.getSingleResult();


        return new PageImpl<ModelPack>(modelPacks, pageable, countResult);

    }

    @Override
    public boolean deleteModels(ModelPack modelPack, List<Model> model) {
        Assert.notNull(modelPack);
        Assert.notNull(model);
        return modelPack.getModels().removeAll(model);
    }

    @Override
    public boolean deleteModel(ModelPack modelPack, Model model) {
        Assert.notNull(modelPack);
        Assert.notNull(model);
        return modelPack.getModels().remove(model);
    }


}
