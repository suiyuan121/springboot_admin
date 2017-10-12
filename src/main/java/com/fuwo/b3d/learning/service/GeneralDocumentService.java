package com.fuwo.b3d.learning.service;

import com.fuwo.b3d.learning.model.GeneralDocument;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GeneralDocumentService {

    void save(GeneralDocument document);

    GeneralDocument get(Long id);

    Page<GeneralDocument> pageQuery
            (GeneralDocument document, Pageable pageable);

    List<GeneralDocument.SubjectEnum> getSubjectTypes();


    Page<GeneralDocument> findTopCountBySubjectOrderByViews(GeneralDocument.SubjectEnum subject, Pageable pageable);

    Page<GeneralDocument> findAll(Example<GeneralDocument> example, Pageable pageable);


}
