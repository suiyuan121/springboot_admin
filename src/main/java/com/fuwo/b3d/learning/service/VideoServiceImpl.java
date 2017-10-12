package com.fuwo.b3d.learning.service;

import com.fuwo.b3d.learning.model.Video;
import com.fuwo.b3d.learning.service.repository.VideoRepository;
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

@CacheConfig(cacheNames = "com.fuwo.b3d.videos")
@Transactional
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    VideoRepository videoRepository;


    @Autowired
    EntityManager entityManager;

    @Override
    public Page<Video> pageQuery(Video video, Pageable pageable) {
        Assert.notNull(pageable);
        Assert.notNull(video);
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int firstResult = pageNumber * pageSize;

        StringBuffer hql = new StringBuffer(" from Video t where t.status='1'");

        if (StringUtils.isNotBlank(video.getTitle()) && StringUtils.isNotBlank(video.getSpeaker())) {
            hql.append(" and t.title like '%").append(video.getTitle()).append("%'");
            hql.append(" or t.speaker like '%").append(video.getSpeaker()).append("%'");
        }

        if (video.getState() != null) {
            hql.append(" and t.state  ='").append(video.getState().getCode()).append("'");
        }
        if (video.getCategory() != null) {
            hql.append(" and t.category  ='").append(video.getCategory().getCode()).append("'");
        }


        String countHql = "select count(*)" + hql;
        hql.append("order by priority desc , modified desc");

        List<Video> videos = entityManager.createQuery(hql.toString()).setMaxResults(pageSize).setFirstResult(firstResult).getResultList();

        //get total records count
        Query queryTotal = entityManager.createQuery(countHql);
        long countResult = (long) queryTotal.getSingleResult();


        return new PageImpl<Video>(videos, pageable, countResult);
    }

    @Override
    public void save(Video video) {
        Assert.notNull(video);
        videoRepository.save(video);
    }

    @Override
    public Video get(Long id) {
        Assert.notNull(id);
        return videoRepository.findOne(id);
    }

    @Override
    public Page<Video> findAll(Example<Video> example, Pageable pageable) {
        Assert.notNull(example);
        Assert.notNull(pageable);
        return videoRepository.findAll(example, pageable);
    }
}
