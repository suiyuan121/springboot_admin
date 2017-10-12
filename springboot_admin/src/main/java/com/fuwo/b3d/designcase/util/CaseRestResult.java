package com.fuwo.b3d.designcase.util;

import com.fuwo.b3d.designcase.model.Case;
import com.fuwo.b3d.designcase.model.CaseImg;

import java.util.List;

public class CaseRestResult {

    private Case caseDesign;

    private CaseImg designImg;

    private List<CaseImg>  effectImgs;

    private List<Case> correlationCase;

    public Case getCaseDesign() {
        return caseDesign;
    }

    public void setCaseDesign(Case caseDesign) {
        this.caseDesign = caseDesign;
    }

    public CaseImg getDesignImg() {
        return designImg;
    }

    public void setDesignImg(CaseImg designImg) {
        this.designImg = designImg;
    }

    public List<CaseImg> getEffectImgs() {
        return effectImgs;
    }

    public void setEffectImgs(List<CaseImg> effectImgs) {
        this.effectImgs = effectImgs;
    }

    public List<Case> getCorrelationCase() {
        return correlationCase;
    }

    public void setCorrelationCase(List<Case> correlationCase) {
        this.correlationCase = correlationCase;
    }
}
