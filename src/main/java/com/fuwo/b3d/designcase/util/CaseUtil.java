package com.fuwo.b3d.designcase.util;

import com.fuwo.b3d.designcase.model.Case;
import net.sf.json.JSONObject;
import org.apache.poi.ss.formula.functions.T;

import java.util.Date;

public class CaseUtil {
    /**
     * 将caseJson对象转换成Case
     * @param jsonObject
     * @return
     */
    public static Case change(JSONObject jsonObject){

        String enumName1=(String)jsonObject.get("designStyle");
        Case.DesignStyleEnum  styleEnum=Case.DesignStyleEnum.valueOf(enumName1);
        jsonObject.remove("designStyle");

        String enumName2=(String)jsonObject.get("houseType");
        Case.HouseTypeEnum  houseEnum=Case.HouseTypeEnum.valueOf(enumName2);
        jsonObject.remove("houseType");

        String enumName3=(String)jsonObject.get("spaceType");
        Case.SpaceTypeEnum spaceTypeEnum=Case.SpaceTypeEnum.valueOf(enumName3);
        jsonObject.remove("spaceValue");

        String enumName4=(String)jsonObject.get("areaType");
        Case.AreaTypeEnum areaTypeEnum=Case.AreaTypeEnum.valueOf(enumName4);
        jsonObject.remove("areaValue");

        String openStr=(String)jsonObject.get("open");
        boolean open=false;
        if(openStr.equals("公开")){
            open=true;
        }

        Case designCase=(Case)JSONObject.toBean(jsonObject,Case.class);
        designCase.setDesignStyle(styleEnum);
        designCase.setHouseType(houseEnum);
        designCase.setSpaceType(spaceTypeEnum);
        designCase.setAreaType(areaTypeEnum);
        designCase.setOpen(open);
        return  designCase;
    }

    /**
     * 将caseJson对象转换成Case
     * @param jsonObject
     * @return
     */
    public static Case change2(JSONObject jsonObject){

        String enumName1=(String)jsonObject.get("designStyle");
        Case.DesignStyleEnum  styleEnum=Case.DesignStyleEnum.valueOf(enumName1);
        jsonObject.remove("designStyle");

        String enumName2=(String)jsonObject.get("houseType");
        Case.HouseTypeEnum  houseEnum=Case.HouseTypeEnum.valueOf(enumName2);
        jsonObject.remove("houseType");

        String enumName3=(String)jsonObject.get("spaceType");
        Case.SpaceTypeEnum spaceTypeEnum=Case.SpaceTypeEnum.valueOf(enumName3);
        jsonObject.remove("spaceValue");

        String enumName4=(String)jsonObject.get("areaType");
        Case.AreaTypeEnum areaTypeEnum=Case.AreaTypeEnum.valueOf(enumName4);
        jsonObject.remove("areaValue");


        String openStr=(String)jsonObject.get("open");
        boolean open=false;
        if(openStr.equals("公开")){
            open=true;
        }
        Case designCase=(Case)JSONObject.toBean(jsonObject,Case.class);
        designCase.setDesignStyle(styleEnum);
        designCase.setHouseType(houseEnum);
        designCase.setSpaceType(spaceTypeEnum);
        designCase.setAreaType(areaTypeEnum);
        designCase.setOpen(open);
        return  designCase;
    }

    /**
     * 赋值给数据库案例对象页面修改的值
     * @return
     */
    public static Case assignmentValue(Case dbCase,Case  pageCase){
        dbCase.setName(pageCase.getName());
        dbCase.setDesignNo(pageCase.getDesignNo());
        //设计风格
        dbCase.setDesignStyle(pageCase.getDesignStyle());
        //户型
        dbCase.setHouseType(pageCase.getHouseType());
        //空间
        dbCase.setSpaceType(pageCase.getSpaceType());
        //面积
        dbCase.setAreaType(pageCase.getAreaType());
        //全景图链接
        dbCase.setUrl(pageCase.getUrl());
        //是否公开
        dbCase.setOpen(pageCase.isOpen());
        //是否可售
        dbCase.setSale(pageCase.isSale());
        //价格
        dbCase.setPrice(pageCase.getPrice());
        //描述
        dbCase.setDescription(pageCase.getDescription());
        //封面图url
        dbCase.setCoverUrl(pageCase.getCoverUrl());
        //修改者
        dbCase.setModifier(pageCase.getModifier());
        //修改时间
        dbCase.setModified(new Date());
         return dbCase;
    }



}
