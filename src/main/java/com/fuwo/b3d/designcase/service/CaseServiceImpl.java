package com.fuwo.b3d.designcase.service;

import com.fuwo.b3d.designcase.model.Case;
import com.fuwo.b3d.designcase.model.CaseImg;
import com.fuwo.b3d.designcase.service.repository.CaseImgResposity;
import com.fuwo.b3d.designcase.service.repository.CaseRepository;
import com.fuwo.b3d.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@CacheConfig(cacheNames = "com.fuwo.b3d.plan")
@Transactional
@Service
public class CaseServiceImpl implements CaseService {

    @Autowired
    private CaseRepository caseRepository;
    @Autowired
    private CaseImgResposity caseImgResposity;
    @Autowired
    EntityManager entityManager;

    @Override
    public void save(Object object) {
        if(object instanceof Case){
            Case  designCase=(Case)object;
            caseRepository.save(designCase);
        }else if(object instanceof CaseImg){
            CaseImg  caseImg=(CaseImg)object;
            caseImgResposity.save(caseImg);
        }

    }

    @Override
    public Case getEnable(Long id) {
        return caseRepository.findByIdAndStatus(id, StatusEnum.ENABLE);
    }


    @Override
    public Case get(Long id) {
        return caseRepository.findOne(id);
    }

    @Override
    public CaseImg getCaseImg(Long caseId) {
        return caseImgResposity.findByCaseId(caseId);
    }

    @Override
    public List<CaseImg> getCaseImgs(Long caseId) {
        return caseImgResposity.findAllByCaseId(caseId);
    }

    @Override
    public CaseImg getCaseImg(Long caseId, String type) {
        return caseImgResposity.findByCaseIdAndType(caseId,type);
    }


    @Override
    public List<CaseImg> getCaseImgs(Long caseId, String type) {
        return caseImgResposity.findAllByCaseIdAndType(caseId,type);
    }




    @Override
    public Page<Case> pageQuery(Case designCase, Pageable pageable,String serachText) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int firstResult = pageNumber * pageSize;

        StringBuffer hql = new StringBuffer(" from Case t where t.status='1'");

        if(serachText!=null){
            if (serachText.equals("GK")) {
                hql.append(" and t.open  ='1'");
            }else if(serachText.equals("BGK")){
                hql.append(" and t.open  ='0'");
            }
        }

        String countHql = "select count(*)" + hql;
        hql.append("order by  modified desc");

        List<Case> designCases = entityManager.createQuery(hql.toString()).setMaxResults(pageSize).setFirstResult(firstResult).getResultList();

        //get total records count
        Query queryTotal = entityManager.createQuery(countHql);
        long countResult = (long) queryTotal.getSingleResult();


        return new PageImpl<Case>(designCases, pageable, countResult);
    }

    @Override
    public Page<Case> findAll(Example<Case> example, Pageable pageable) {
        return caseRepository.findAll(example, pageable);
    }

    @Override
    public CaseImg myGetImg(Long case_id, String type) {
        StringBuffer hql = new StringBuffer(" from CaseImg i where i.status='1' and case_id=").append(case_id).append(" and type='").append(type).append("'");
        List<CaseImg> caseImgs = entityManager.createQuery(hql.toString()).getResultList();
        if(caseImgs!=null&&caseImgs.size()>0){
            return caseImgs.get(0);
        }
        return new CaseImg();
    }

    @Override
    public List<CaseImg> myGetImgs(Long case_id, String type) {
        StringBuffer hql = new StringBuffer(" from CaseImg i where i.status='1' and case_id=").append(case_id).append(" and type='").append(type).append("'");
        List<CaseImg> caseImgs = entityManager.createQuery(hql.toString()).getResultList();
        return caseImgs;
    }



    @Override
    public List<Case> findByIds(List<Long> ids) {
        return caseRepository.findByIdIn(ids);
    }

    @Override
    public void deleteAllCaseImg(Long caseId,String type) {
        caseImgResposity.deleteAllByCaseIdAndType(caseId,type);
    }

    @Override
    public void deleteCase(Long id) {
          caseRepository.delete(id);
    }

    @Override
    public List<Case> getCorrelation4(Long id, Case.DesignStyleEnum designStyleEnum) {
        return caseRepository.findTop4ByIdNotInAndDesignStyle(id,designStyleEnum);
    }

}
