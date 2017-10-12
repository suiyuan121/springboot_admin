package com.fuwo.b3d.model.service;

import com.fuwo.b3d.model.model.ModelRecom;
import com.fuwo.b3d.model.service.repository.ModelRecomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

@CacheConfig(cacheNames = "com.fuwo.b3d.modelrecoms")
@Transactional
@Service
public class ModelRecomServiceImpl implements ModelRecomService {

    @Autowired
    private ModelRecomRepository recmModelRepository;

    @Override
    public void save(ModelRecom recmModel) {
        Assert.notNull(recmModel);
        recmModelRepository.save(recmModel);
    }

    @Override
    public ModelRecom get(Integer id) {
        Assert.notNull(id);
        return recmModelRepository.findOne(id);
    }

    @Override
    public Page<ModelRecom> findAll(Example<ModelRecom> example, Pageable pageable) {
        Assert.notNull(example);
        Assert.notNull(pageable);

        return recmModelRepository.findAll(example, pageable);
    }

    @Override
    public long count(Example<ModelRecom> example) {
        Assert.notNull(example);
        return recmModelRepository.count(example);
    }
}
