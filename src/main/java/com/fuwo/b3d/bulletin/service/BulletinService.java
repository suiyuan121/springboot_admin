package com.fuwo.b3d.bulletin.service;

import com.fuwo.b3d.bulletin.model.Bulletin;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BulletinService {

    Page<Bulletin> pageQuery(Example<Bulletin> example, Pageable pageable);

    void save(Bulletin bulletin);

    void updateBulletin(Bulletin bulletin);

    Bulletin get(Long id);


}
