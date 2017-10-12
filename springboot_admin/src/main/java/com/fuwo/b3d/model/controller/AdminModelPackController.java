package com.fuwo.b3d.model.controller;

import com.fuwo.b3d.enums.StateEnum;
import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.model.model.ModelComb;
import com.fuwo.b3d.model.model.ModelPack;
import com.fuwo.b3d.model.service.ModelPackService;
import com.fuwo.b3d.model.service.ModelService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;

@Controller
@RequestMapping(BASE_MAPPING + "/modelpack")
public class AdminModelPackController {

    private final Logger logger = LoggerFactory.getLogger(AdminModelPackController.class);

    @Autowired
    private ModelPackService modelPackService;

    @Autowired
    private ModelService modelService;


    @GetMapping("/save")
    public String add(Model model, ModelPack modelPack) {

        if (modelPack.getId() != null) {
            //来源于编辑
            modelPack = modelPackService.get(modelPack.getId());
        }
        model.addAttribute("modelPack", modelPack);
        model.addAttribute("types", ModelPack.TypeEnum.values());
        model.addAttribute("states", StateEnum.values());

        return "model/modelpack";
    }

    @PostMapping("/save")
    public String addPost(ModelPack modelPackDto, Model modelUi,
                          @RequestParam(value = "modelJson") String modelJson, @NotNull Principal principal) {

        StringBuffer errorMessages = new StringBuffer();
        if (StringUtils.isBlank(modelPackDto.getPicUrl())) {
            errorMessages.append("效果图不能为空！");
        }
        if (modelPackDto.getPrice() == null) {
            errorMessages.append("价格不能为空！");
        }
        if (modelPackDto.getState() == null) {
            errorMessages.append("风格不能为空！");
        }
        if (modelPackDto.getType() == null) {
            errorMessages.append("模型包类型不能为空！");
        }
        if (modelPackDto.getBuysInitial() == null) {
            errorMessages.append("购买次数不能为空！");
        }
        if (StringUtils.isBlank(modelPackDto.getName())) {
            errorMessages.append("模型包名称不能为空！");
        }

        if (StringUtils.isNotBlank(errorMessages.toString())) {
            modelUi.addAttribute("errorMessages", errorMessages.toString());
            modelUi.addAttribute("modelPack", modelPackDto);
            modelUi.addAttribute("types", ModelPack.TypeEnum.values());
            modelUi.addAttribute("states", StateEnum.values());
            return "model/modelpack";
        }

        boolean flag = true;
        //添加修改model

        ModelPack modelPack;
        String[] modelsArray = null;
        List<com.fuwo.b3d.model.model.Model> models = new ArrayList<>();
        if (StringUtils.isNotBlank(modelJson)) {
            modelsArray = modelJson.split(",");
            if (modelsArray.length > 20) {
                modelUi.addAttribute("errorMessages", "超过20个模型");
                flag = false;
            }
        }
        if (modelsArray != null) {
            for (String item : modelsArray) {
                com.fuwo.b3d.model.model.Model model = modelService.getByModelNoAndPerfect(item, com.fuwo.b3d.model.model.Model.PerfectEnum.NORMAL);
                if (model != null) {
                    if (models.contains(model)) {
                        modelUi.addAttribute("errorMessages", "重复的模型：" + item);
                        flag = false;
                    }
                    models.add(model);
                } else {
                    modelUi.addAttribute("errorMessages", "模型不存在：" + item);
                    flag = false;
                }
            }
        }
        if (modelPackDto.getId() == null) {
            //创建
            modelPack = modelPackDto;
            modelPack.setBuysIncrease(modelPack.getBuysInitial());
            modelPack.setCreated(new Date());
            modelPack.setCreator(principal.getName());
            modelPack.setModels(models);
        } else {
            modelPack = modelPackService.get(modelPackDto.getId());
            BeanUtils.copyProperties(modelPackDto, modelPack, "id", "buysIncrease", "created", "creator", "priority", "models");
            modelPack.setBuysIncrease(modelPack.getBuysIncrease() - modelPack.getBuysInitial() + modelPackDto.getBuysInitial());
            modelPack.setModels(models);
        }


        if (!flag) {
            modelUi.addAttribute("styles", ModelComb.StyleEnum.values());
            modelUi.addAttribute("spaces", ModelComb.SpaceEnum.values());
            modelUi.addAttribute("modelPack", modelPackDto);
            modelUi.addAttribute("categories", com.fuwo.b3d.model.model.Model.CategoryEnum.values());
            return "model/modelpack";
        }

        modelPack.setModified(new Date());
        modelPack.setModifier(principal.getName());
        modelPack.setStatus(StatusEnum.ENABLE);
        modelPackService.save(modelPack);


        return "redirect:./list";
    }


}
