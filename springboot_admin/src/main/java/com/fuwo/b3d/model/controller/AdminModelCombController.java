package com.fuwo.b3d.model.controller;

import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.model.model.ModelComb;
import com.fuwo.b3d.model.service.ModelCombService;
import com.fuwo.b3d.model.service.ModelService;
import org.apache.commons.lang.StringUtils;
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
@RequestMapping(BASE_MAPPING + "/modelcomb")
public class AdminModelCombController {

    @Autowired
    private ModelCombService modelCombService;

    @Autowired
    private ModelService modelService;

    @GetMapping("/save")
    public String add(Model model, ModelComb modelComb) {

        if (modelComb.getId() != null) {
            //来源于编辑
            modelComb = modelCombService.get(modelComb.getId());
        }
        model.addAttribute("styles", ModelComb.StyleEnum.values());
        model.addAttribute("spaces", ModelComb.SpaceEnum.values());
        model.addAttribute("modelComb", modelComb);
        model.addAttribute("categories", com.fuwo.b3d.model.model.Model.CategoryEnum.values());


        return "model/modelcomb";
    }

    @PostMapping(value = "/save")
    public String addPost(ModelComb modelCombDto, Model modelUi, @RequestParam(value = "modelJson") String modelJson, @NotNull Principal principal) {

        StringBuffer errorMessages = new StringBuffer();
        if (StringUtils.isBlank(modelCombDto.getPicUrl())) {
            errorMessages.append("效果图不能为空！");
        }
        if (modelCombDto.getStyle() == null) {
            errorMessages.append("风格不能为空！");
        }
        if (modelCombDto.getSpace() == null) {
            errorMessages.append("空间分类不能为空！");
        }
        if (modelCombDto.getCollectsInitial() == null) {
            errorMessages.append("收藏次数不能为空！");
        }
        if (modelCombDto.getPrice() == null) {
            errorMessages.append("总价格不能为空！");
        }
        if (StringUtils.isBlank(modelCombDto.getName())) {
            errorMessages.append("模型组名称不能为空！");
        }
        if (StringUtils.isBlank(modelCombDto.getDesignNo())) {
            errorMessages.append("设计编号不能为空！");
        }
        if (StringUtils.isNotBlank(errorMessages.toString())) {
            modelUi.addAttribute("errorMessages", errorMessages.toString());
            modelUi.addAttribute("styles", ModelComb.StyleEnum.values());
            modelUi.addAttribute("spaces", ModelComb.SpaceEnum.values());
            modelUi.addAttribute("modelComb", modelCombDto);
            modelUi.addAttribute("categories", com.fuwo.b3d.model.model.Model.CategoryEnum.values());
            return "model/modelcomb";
        }

        boolean flag = true;
        ModelComb modelComb;
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
        if (modelCombDto.getId() == null) {
            //创建
            modelCombDto.setCreated(new Date());
            modelCombDto.setCreator(principal.getName());
            modelCombDto.setCollectsIncrease(modelCombDto.getCollectsInitial());


            modelCombDto.setModels(models);
            modelComb = modelCombDto;
        } else {
            modelComb = modelCombService.get(modelCombDto.getId());
            //更新的时候，

            BeanUtils.copyProperties(modelCombDto, modelComb, "id", "collectsIncrease", "created", "creator", "priority", "models");
            modelComb.setCollectsIncrease(modelComb.getCollectsIncrease() - modelComb.getCollectsInitial() + modelCombDto.getCollectsInitial());
        }

        if (!flag) {
            modelUi.addAttribute("styles", ModelComb.StyleEnum.values());
            modelUi.addAttribute("spaces", ModelComb.SpaceEnum.values());
            modelUi.addAttribute("modelComb", modelCombDto);
            modelUi.addAttribute("categories", com.fuwo.b3d.model.model.Model.CategoryEnum.values());
            return "model/modelcomb";
        }

        modelComb.setModified(new Date());
        modelComb.setModifier(principal.getName());
        modelComb.setStatus(StatusEnum.ENABLE);
        modelCombService.save(modelComb);


        return "redirect:./list";
    }


}
