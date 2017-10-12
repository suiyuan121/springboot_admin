package com.fuwo.b3d.designcase.controller;


import com.fuwo.b3d.designcase.model.Case;
import com.fuwo.b3d.designcase.model.CaseImg;
import com.fuwo.b3d.designcase.service.CaseService;
import com.fuwo.b3d.designcase.util.CaseUtil;
import com.fuwo.b3d.enums.StatusEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fuwo.b3d.common.RestResult;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;

@Controller
@RequestMapping(BASE_MAPPING + "/case")
public class AdminCaseController {


    @Autowired
    private CaseService caseService;


    /**
     * 列表页面点击添加案例，进入添加页面
     * @param model
     * @param designCase
     * @return
     */
    @GetMapping("/add")
    public String add(Model model, Case designCase) {
        //枚举
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        model.addAttribute("designStyleEnum", Case.DesignStyleEnum.values());
        model.addAttribute("houseTypeEnum", Case.HouseTypeEnum.values());
        model.addAttribute("spaceTypeEnum", Case.SpaceTypeEnum.values());
        model.addAttribute("areaTypeEnum", Case.AreaTypeEnum.values());
        model.addAttribute("uuid", uuid);

        return "case/case_add";
    }


    /**
     * 添加案例页面点击保存，保存添加案例，发送ajax请求
     * @param param
     * @param principal
     * @return
     */

    @PostMapping(value = "/ajaxAddSave")
    @ResponseBody
    public RestResult ajaxAddSave(@RequestBody String param, @NotNull Principal principal) {
        String currentUser=principal.getName();
        JSONObject  jsonObject= JSONObject.fromObject(param);
        JSONObject  caseJson =(JSONObject)jsonObject.get("designCase");
        Case  designCase= CaseUtil.change(caseJson);
        designCase.setCreator(currentUser);
        designCase.setCreated(new Date());
        designCase.setModifier(currentUser);
        designCase.setModified(new Date());
        designCase.setStatus(StatusEnum.ENABLE);
        caseService.save(designCase);

        JSONObject  houseImgJson =(JSONObject)jsonObject.get("houseImg");
        CaseImg houseImg=(CaseImg)JSONObject.toBean(houseImgJson,CaseImg.class);
        houseImg.setCaseId(designCase.getId());
        houseImg.setCreator(currentUser);
        houseImg.setCreated(new Date());
        houseImg.setModifier(currentUser);
        houseImg.setModified(new Date());
        houseImg.setStatus(StatusEnum.ENABLE);
        caseService.save(houseImg);

        JSONArray  effectArray =(JSONArray)jsonObject.get("effectImages");
        for(int i=0;i<effectArray.size();i++){
            JSONObject effectJson=(JSONObject)effectArray.get(i);
            CaseImg effectImg=(CaseImg)JSONObject.toBean(effectJson,CaseImg.class);
            effectImg.setCaseId(designCase.getId());
            effectImg.setCreator(currentUser);
            effectImg.setCreated(new Date());
            effectImg.setModifier(currentUser);
            effectImg.setModified(new Date());
            effectImg.setStatus(StatusEnum.ENABLE);
            caseService.save(effectImg);
        }

        RestResult restResult=new RestResult();
        restResult.setCode("1");
        restResult.setMsg("成功");
        return restResult;
    }


    /**
     * 列表页面点击编辑进入编辑页面
     * @param model
     * @param designCase
     * @return
     */

    @GetMapping("/edit")
    public String edit(Model model, Case designCase) {
        if(designCase.getId()!=null){
            Case  caseDb=caseService.get(designCase.getId());
            CaseImg  houseImg=caseService.myGetImg(designCase.getId(),"house");//户型图=平面图=设计图
            List<CaseImg> effectImgs=caseService.myGetImgs(designCase.getId(),"effect");

            model.addAttribute("designCase", caseDb);
            model.addAttribute("houseImg", houseImg);
            model.addAttribute("effectImgs", effectImgs);
            //枚举
            model.addAttribute("designStyleEnum", Case.DesignStyleEnum.values());
            model.addAttribute("houseTypeEnum", Case.HouseTypeEnum.values());
            model.addAttribute("spaceTypeEnum", Case.SpaceTypeEnum.values());
            model.addAttribute("areaTypeEnum", Case.AreaTypeEnum.values());
        }

        return "case/case_edit";
    }

    /**
     * 编辑页面点击保存，保存编辑案例，通过表单提交
     * @param param
     * @param principal
     * @return
     */
    @PostMapping(value = "/editSubmit")
    public String editSubmit(Case caseDesign ,String param, @NotNull Principal principal) {
        String currentUser=principal.getName();
        JSONObject  jsonObject= JSONObject.fromObject(param);
        JSONObject  caseJson =(JSONObject)jsonObject.get("designCase");
        Case  pageCase= CaseUtil.change2(caseJson);

        Case  caseDb=caseService.getEnable(caseDesign.getId());
        if(caseDb!=null){
            Case finalCase=CaseUtil.assignmentValue(caseDb,pageCase);
            caseService.save(finalCase);

            JSONObject  houseImgJson =(JSONObject)jsonObject.get("houseImg");
            CaseImg houseImg=(CaseImg)JSONObject.toBean(houseImgJson,CaseImg.class);
            //如果为空，则上传了新户型照片
            if(houseImg.getId()==null){
                caseService.deleteAllCaseImg(finalCase.getId(),CaseImg.HOUSE);
                houseImg.setCaseId(finalCase.getId());
                houseImg.setModifier(currentUser);
                houseImg.setModified(new Date());
                houseImg.setStatus(StatusEnum.ENABLE);
                caseService.save(houseImg);
            }

            //清空之前效果图，上传最新效果图
            caseService.deleteAllCaseImg(finalCase.getId(),CaseImg.EFFECT);
            JSONArray  effectArray =(JSONArray)jsonObject.get("effectImages");
            for(int i=0;i<effectArray.size();i++){
                JSONObject effectJson=(JSONObject)effectArray.get(i);
                CaseImg effectImg=(CaseImg)JSONObject.toBean(effectJson,CaseImg.class);
                effectImg.setCaseId(finalCase.getId());
                effectImg.setCreator(currentUser);
                effectImg.setCreated(new Date());
                effectImg.setModifier(currentUser);
                effectImg.setModified(new Date());
                effectImg.setStatus(StatusEnum.ENABLE);
                caseService.save(effectImg);
            }

        }

        return "redirect:./list";
    }

    /**
     * 删除案例
     * @param caseDesign
     * @return
     */
    @PostMapping(value = "/delete")
    public String delete(Case caseDesign , @NotNull Principal principal) {
        Long  id=caseDesign.getId();
        if(id!=null){
            caseService.deleteCase(id);
        }
        return "redirect:./list";
    }


    /**
     * 设置公开与否
     * @param param
     * @return
     */
    @PostMapping(value = "/set")
    @ResponseBody
    public  RestResult  set(@RequestBody String param , @NotNull Principal principal) {
        JSONObject  jsonObject= JSONObject.fromObject(param);
        Object obj=jsonObject.get("id");
        Case caseDesign=null;
        if( obj instanceof Integer){
            Integer ids=(Integer)jsonObject.get("id");
            caseDesign=caseService.get(Long.valueOf(ids));
        }else{
            String ids=(String)jsonObject.get("id");
            caseDesign=caseService.get(Long.valueOf(ids));
        }

        RestResult restResult=new RestResult();
        if(caseDesign!=null){
            String openStr=(String)jsonObject.get("open");
            if(Case.OPEN.equals(openStr)){
                caseDesign.setOpen(true);
            }else{
                caseDesign.setOpen(false);
            }
            caseService.save(caseDesign);
            restResult.setCode("1");
            restResult.setMsg("成功");
        }else{
            restResult.setCode("-1");
            restResult.setMsg("失败");
        }
        return restResult;
    }



}
