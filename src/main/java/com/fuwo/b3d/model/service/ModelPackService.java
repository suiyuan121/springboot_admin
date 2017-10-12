package com.fuwo.b3d.model.service;

import com.fuwo.b3d.enums.PriceTyleEnum;
import com.fuwo.b3d.model.model.Model;
import com.fuwo.b3d.model.model.ModelComb;
import com.fuwo.b3d.model.model.ModelPack;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ModelPackService {

    void save(ModelPack modelPack);

    ModelPack get(Long id);

    Page<ModelPack> pageQuery
            (ModelPack modelPack, String searchText, Pageable pageable);

    Page<ModelPack> findAll(Example<ModelPack> example, Pageable pageable);

    Page<ModelPack> findByCond
            (ModelPack modelPack, PriceTyleEnum priceTyle, Pageable pageable);

    boolean deleteModels(ModelPack modelPack, List<Model> model);

    boolean deleteModel(ModelPack modelPack, Model model);
}
