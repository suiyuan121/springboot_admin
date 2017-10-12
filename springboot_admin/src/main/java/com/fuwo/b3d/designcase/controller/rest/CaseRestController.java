package com.fuwo.b3d.designcase.controller.rest;

import com.fuwo.b3d.common.RestResponse;
import com.fuwo.b3d.common.RestResult;
import com.fuwo.b3d.designcase.model.Case;
import com.fuwo.b3d.designcase.model.CaseImg;
import com.fuwo.b3d.designcase.service.CaseService;
import com.fuwo.b3d.designcase.util.CaseRestResult;
import com.fuwo.b3d.learning.model.Video;
import com.fuwo.b3d.model.model.Model;
import com.fuwo.b3d.model.model.ModelComb;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/cases")
public class CaseRestController {


    @Autowired
   private CaseService caseService;


    @GetMapping(value = "/search/findAll")
    public Page<Case> findAll(Case designCase, Pageable pageable) {

        ExampleMatcher matcher = ExampleMatcher.matching() ;
        Example<Case> example = Example.of(designCase, matcher);
        Page<Case> page = caseService.findAll(example, pageable);
        return page;
    }


    @GetMapping(value = "/search/findByIds")
    public RestResponse findByIds( String idsStr) {
        RestResponse ret=new RestResponse();
        if(StringUtils.isBlank(idsStr)){
            return  new RestResponse();
        }

        String[] array = idsStr.split(",");
        List<Long> ids=new ArrayList<Long>();
        for(int i=0;i<array.length;i++){
            ids.add(Long.valueOf(array[i]));
        }
        List<Case> cases =caseService.findByIds(ids);
        ret.setContent(cases);
        return ret;
    }


    @PostMapping(value = "/good")
    public RestResult good(Long id, @NotNull Principal principal) {
        RestResult restResult = new RestResult();

        if (id == null) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.PARA_ERR.getDesc());
            return restResult;
        }

        Case  designCase= caseService.get(id);
        if (designCase != null) {
           Long  likes=designCase.getLikes();
           if(likes==null){
               likes=0l;
           }
            designCase.setLikes(likes+1l);
        }
        caseService.save(designCase);
        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        return restResult;
    }

    @PostMapping(value = "/collect")
    public RestResult collect(Long id, @NotNull Principal principal) {
        RestResult restResult = new RestResult();

        if (id == null) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.PARA_ERR.getDesc());
            return restResult;
        }

        Case  designCase= caseService.get(id);
        if (designCase != null) {
            Long  collection=designCase.getCollection();
            if(collection==null){
                collection=0l;
            }
            designCase.setCollection(collection+1l);
        }
        caseService.save(designCase);
        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        return restResult;
    }

    @PostMapping(value = "/comment")
    public RestResult comment(Long id, @NotNull Principal principal) {
        RestResult restResult = new RestResult();

        if (id == null) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.PARA_ERR.getDesc());
            return restResult;
        }

        Case  designCase= caseService.get(id);
        if (designCase != null) {
            Long  comment=designCase.getComment();
            if(comment==null){
                comment=0l;
            }
            designCase.setComment(comment+1l);
        }
        caseService.save(designCase);
        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        return restResult;
    }



    @PostMapping(value = "/getCaseDetail")
    public CaseRestResult getCaseDetail(Long id,String type) {
        CaseRestResult caseRestResult=new  CaseRestResult();

        if (id == null ||type==null) {
            return  caseRestResult;
        }
        Case  designCase= caseService.get(id);
        caseRestResult.setCaseDesign(designCase);
        if(type.equals("fullView")){//全景图   url==全景图
              return caseRestResult;
        } else if(type.equals("design")){//户型图=设计图=平面图
            CaseImg houseImg=caseService.getCaseImg(id,"house");//数据库用house字段区分
            caseRestResult.setDesignImg(houseImg);
        }else if(type.equals("effect")){//效果图
           List<CaseImg>  effectImgs=caseService.getCaseImgs(id,type);
            caseRestResult.setEffectImgs(effectImgs);
        }

        return  caseRestResult;
    }


    @PostMapping(value = "/getCorrelation")
    public CaseRestResult getCorrelation(Long id,String designStyle) {
        CaseRestResult caseRestResult=new  CaseRestResult();
        if (id == null ||designStyle==null) {
            return  caseRestResult;
        }
        //得到相关
        if(StringUtils.isNotBlank(designStyle)){
            Case.DesignStyleEnum designStyleEnum=Case.DesignStyleEnum.valueOf(designStyle);
            List<Case> correlationCases=caseService.getCorrelation4(id,designStyleEnum);
            caseRestResult.setCorrelationCase(correlationCases);
        }

        return  caseRestResult;
    }

}

