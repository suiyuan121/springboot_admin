package com.fuwo.b3d.model.service;

import com.fuwo.b3d.model.model.ModelRecom;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ModelRecomService {

    void save(ModelRecom recmModel);

    ModelRecom get(Integer id);

    Page<ModelRecom> findAll(Example<ModelRecom> example, Pageable pageable);

    long count(Example<ModelRecom> example);

}
