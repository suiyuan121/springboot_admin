package com.fuwo.b3d.model.service;

import com.fuwo.b3d.model.model.Model;
import com.fuwo.b3d.model.model.ModelPack;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@CacheConfig(cacheNames = "com.fuwo.b3d.models")
@Transactional
@Service
public interface ModelService {

    Model getByModelNoAndPerfect(String modelNo, Model.PerfectEnum perfect);

    Model getByModelNo(String modelNo);

    void save(Model model);

    Model get(Integer id);

    Page<Model> findAll(Example<Model> example, Pageable pageable);

    Page<Model> pageQuery
            (Model model, Pageable pageable, Date startDate, Date endDate);

    Integer updatePrice(List<String> nos, Integer price);

    List<Model> findByNos(List<String> nos);

}
