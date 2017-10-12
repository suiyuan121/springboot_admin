package com.fuwo.b3d.learning.service;

import com.fuwo.b3d.learning.model.BeginnerDocument;
import com.fuwo.b3d.learning.model.GeneralDocument;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BeginnerDocumentService {

    void save(BeginnerDocument document);

    BeginnerDocument get(Long id);

    Page<BeginnerDocument> findAll
            (BeginnerDocument document, Pageable pageable);

}
