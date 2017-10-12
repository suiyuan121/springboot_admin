package com.fuwo.b3d.learning.service;

import com.fuwo.b3d.learning.model.GeneralDocument;
import com.fuwo.b3d.learning.service.repository.GeneralDocumentRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@CacheConfig(cacheNames = "com.fuwo.b3d.documents")
@Transactional
@Service
public class GeneralDocumentServiceImpl implements GeneralDocumentService {

    @Autowired
    GeneralDocumentRepository documentRepository;


    @Autowired
    EntityManager entityManager;


    @Override
    public Page<GeneralDocument> pageQuery(GeneralDocument document, Pageable pageable) {
        Assert.notNull(pageable);
        Assert.notNull(document);
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int firstResult = pageNumber * pageSize;

        StringBuffer hql = new StringBuffer(" from GeneralDocument t where t.status=1");
        if (document.getState() != null) {
            hql.append(" and t.state='").append(document.getState().getCode()).append("'");
        }
        if (document.getSubject() != null) {
            hql.append(" and t.subject='").append(document.getSubject().getCode()).append("'");
        }
        if (StringUtils.isNotBlank(document.getTitle())) {
            hql.append(" and t.title like '%").append(document.getTitle()).append("%'");
        }

        String countHql = "select count(*)" + hql;
        hql.append("order by priority desc , modified desc");

        List<GeneralDocument> documents = entityManager.createQuery(hql.toString()).setMaxResults(pageSize).setFirstResult(firstResult).getResultList();

        //get total records count
        Query queryTotal = entityManager.createQuery(countHql);
        long countResult = (long) queryTotal.getSingleResult();


        return new PageImpl<GeneralDocument>(documents, pageable, countResult);
    }


    @Override
    public void save(GeneralDocument document) {
        Assert.notNull(document);
        documentRepository.save(document);
    }

    @Override
    public GeneralDocument get(Long id) {
        Assert.notNull(id);
        return documentRepository.getOne(id);
    }


    @Override
    public List<GeneralDocument.SubjectEnum> getSubjectTypes() {
        StringBuffer hql = new StringBuffer(" select distinct  subject from GeneralDocument d ");
        return entityManager.createQuery(hql.toString()).getResultList();
    }

    @Override
    public Page<GeneralDocument> findTopCountBySubjectOrderByViews(GeneralDocument.SubjectEnum subject, Pageable pageable) {
        Assert.notNull(pageable);
        Assert.notNull(subject);
        return documentRepository.findBySubjectOrderByViews(subject, pageable);
    }

    @Override
    public Page<GeneralDocument> findAll(Example<GeneralDocument> example, Pageable pageable) {
        Assert.notNull(example);
        Assert.notNull(pageable);
        return documentRepository.findAll(example, pageable);
    }
}
