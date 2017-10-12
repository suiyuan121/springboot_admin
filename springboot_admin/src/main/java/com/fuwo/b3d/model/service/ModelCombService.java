package com.fuwo.b3d.model.service;

import com.fuwo.b3d.enums.PriceTyleEnum;
import com.fuwo.b3d.model.model.Model;
import com.fuwo.b3d.model.model.ModelComb;
import com.fuwo.b3d.model.model.ModelPack;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ModelCombService {

    void save(ModelComb modelComb);

    ModelComb get(Long id);

    Page<ModelComb> pageQuery
            (ModelComb modelComb, Pageable pageable);

    Page<ModelComb> findAll(Example<ModelComb> example, Pageable pageable);

    Page<ModelComb> findByCond
            (ModelComb modelComb, PriceTyleEnum priceTyle, Pageable pageable);

    boolean deleteModels(ModelComb modelComb, List<Model> model);

    boolean deleteModel(ModelComb modelComb, Model model);

}
