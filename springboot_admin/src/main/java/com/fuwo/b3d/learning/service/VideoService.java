package com.fuwo.b3d.learning.service;

import com.fuwo.b3d.learning.model.BeginnerDocument;
import com.fuwo.b3d.learning.model.GeneralDocument;
import com.fuwo.b3d.learning.model.Video;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VideoService {

    void save(Video video);

    Video get(Long id);

    Page<Video> pageQuery
            (Video video, Pageable pageable);

    Page<Video> findAll(Example<Video> example, Pageable pageable);

}
