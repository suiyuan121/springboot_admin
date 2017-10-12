package com.fuwo.b3d.learning.service;

import com.fuwo.b3d.learning.model.BeginnerDocument;
import com.fuwo.b3d.learning.model.BeginnerDocument;
import com.fuwo.b3d.learning.model.GeneralDocument;
import com.fuwo.b3d.learning.service.repository.BeginnerDocumentRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@CacheConfig(cacheNames = "com.fuwo.b3d.documents")
@Transactional
@Service
public class BeginnerDocumentServiceImpl implements BeginnerDocumentService {

    @Autowired
    BeginnerDocumentRepository documentRepository;


    @Override
    public void save(BeginnerDocument document) {
        Assert.notNull(document);
        documentRepository.save(document);
    }

    @Override
    public BeginnerDocument get(Long id) {
        Assert.notNull(id);
        return documentRepository.getOne(id);
    }


    @Override
    public Page<BeginnerDocument> findAll(BeginnerDocument document, Pageable pageable) {
        Assert.notNull(document);
        Assert.notNull(pageable);

        Example<BeginnerDocument> example = Example.of(document);
        return documentRepository.findAll(example, pageable);
    }
}
