package com.fuwo.b3d.model.controller;

import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.model.model.ModelRecom;
import com.fuwo.b3d.model.service.ModelService;
import com.fuwo.b3d.model.service.ModelRecomService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Date;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;

@Controller
@RequestMapping(BASE_MAPPING + "/modelRecom")
public class AdminModelRecomController {

    @Autowired
    private ModelRecomService modelRecomService;

    @Autowired
    private ModelService modelService;


    private static final long MODELPACK_RECM_MAX_AMOUNT = 5;


    @GetMapping("/save")
    public String add(Model model, ModelRecom recmModel) {

        if (recmModel.getId() != null) {
            //来源于编辑
            recmModel = modelRecomService.get(recmModel.getId());
        }
        model.addAttribute("recmModel", recmModel);

        return "model/model_recom";
    }

    @PostMapping("/save")
    public String addPost(ModelRecom recmModelDto, Model model,
                          @NotNull Principal principal, @RequestParam(name = "no") String no) {

        StringBuffer errorMessages = new StringBuffer();
        if (StringUtils.isBlank(recmModelDto.getPicUrl())) {
            errorMessages.append("效果图不能为空！");
        }
        if (recmModelDto.getPrice() == null) {
            errorMessages.append("价格不能为空！");
        }
        if (recmModelDto.getCollectsInitial() == null) {
            errorMessages.append("收藏次数不能为空！");
        }
        if (StringUtils.isBlank(recmModelDto.getName())) {
            errorMessages.append("模型包名称不能为空！");
        }
        if (StringUtils.isBlank(no)) {
            errorMessages.append("模型编号不能为空！");
        }

        if (StringUtils.isNotBlank(errorMessages.toString())) {
            model.addAttribute("errorMessages", errorMessages.toString());
            model.addAttribute("recmModel", recmModelDto);
            return "model/model_recom";
        }

        ModelRecom recmModel;

        if (recmModelDto.getId() == null) {
            //校验是否超过5个推荐模型
            ModelRecom cond = new ModelRecom();
            cond.setStatus(StatusEnum.ENABLE);
            Example<ModelRecom> example = Example.of(cond);
            long amount = modelRecomService.count(example);
            if (amount >= MODELPACK_RECM_MAX_AMOUNT) {
                model.addAttribute("errorMessages", "已超过五个推荐模型，请删除后再操作");
                model.addAttribute("recmModel", recmModelDto);
                return "model/model_recom";
            }

            //创建
            recmModel = recmModelDto;
            recmModel.setCollectsIncrease(recmModel.getCollectsInitial());
            recmModel.setCreated(new Date());
            recmModel.setCreator(principal.getName());
        } else {
            recmModel = modelRecomService.get(recmModelDto.getId());
            BeanUtils.copyProperties(recmModelDto, recmModel, "id", "collectsIncrease", "created", "creator", "model");
            recmModel.setCollectsIncrease(recmModel.getCollectsIncrease() - recmModel.getCollectsInitial() + recmModelDto.getCollectsInitial());
        }

        com.fuwo.b3d.model.model.Model temp = modelService.getByModelNoAndPerfect(no, com.fuwo.b3d.model.model.Model.PerfectEnum.PERFECT);
        recmModel.setModelId(temp == null ? null : temp.getId());
        recmModel.setModified(new Date());
        recmModel.setModifier(principal.getName());
        recmModel.setStatus(StatusEnum.ENABLE);
        modelRecomService.save(recmModel);

        return "redirect:./list";
    }


    @GetMapping(value = "/pop")
    public String pop(Integer id, @NotNull Principal principal, Model model) {
        if (id == null) {
            model.addAttribute("errorMessages", "置顶失败，参数错误");
        } else {
            ModelRecom recmModel = modelRecomService.get(id);
            recmModel.setModified(new Date());
            recmModel.setModifier(principal.getName());
            modelRecomService.save(recmModel);
        }
        return "redirect:./list";
    }

}
