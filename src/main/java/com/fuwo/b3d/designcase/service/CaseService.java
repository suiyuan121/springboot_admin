package com.fuwo.b3d.designcase.service;

import com.fuwo.b3d.designcase.model.Case;
import com.fuwo.b3d.designcase.model.CaseImg;
import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.model.model.Model;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CaseService {
    void save(Object object);


    /**
     * 根据id得到没被逻辑删除的case
     * @param id
     * @return
     */
    public abstract  Case getEnable(Long id);

    /**
     * 根据id得到case,包含逻辑删除和没被逻辑删除的
     * @param id
     * @return
     */
    Case get(Long id);
    CaseImg getCaseImg(Long caseId);
    CaseImg getCaseImg(Long caseId,String type);

    List<CaseImg> getCaseImgs(Long caseId);
    List<CaseImg> getCaseImgs(Long caseId,String type);
    Page<Case> pageQuery
            (Case designCase, Pageable pageable, String serachText);

    Page<Case> findAll(Example<Case> example, Pageable pageable);

    /**
     * 自定义找单个图片
     * @param case_id  案例id
     * @param type  图片类型
     * @return   单个图片
     */
    public abstract CaseImg myGetImg(Long case_id,String type);

    /**
     * 自定义找多个图片
     * @param case_id  案例id
     * @param type  图片类型
     * @return 多个图片
     */
    public abstract List<CaseImg> myGetImgs(Long case_id,String type);

    public abstract  List<Case> findByIds(List<Long> ids);


    public void deleteAllCaseImg(Long caseId,String type);

    public void deleteCase(Long id);

    public  List<Case> getCorrelation4(Long id,Case.DesignStyleEnum designStyleEnum);

}
