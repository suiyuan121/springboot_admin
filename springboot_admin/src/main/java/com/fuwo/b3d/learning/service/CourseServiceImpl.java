package com.fuwo.b3d.learning.service;

import com.fuwo.b3d.learning.model.Course;
import com.fuwo.b3d.learning.service.repository.CourseRepository;
import com.fuwo.b3d.model.model.ModelComb;
import com.fuwo.b3d.model.model.ModelPack;
import com.fuwo.b3d.model.service.repository.ModelCombRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

@CacheConfig(cacheNames = "com.fuwo.b3d.courses")
@Transactional
@Service
public class CourseServiceImpl implements  CourseService{

    @Autowired
    CourseRepository courseRepository;

    @Override
    public Page<Course> pageQuery(Example<Course> example,Pageable pageable) {
        Assert.notNull(pageable);
        Assert.notNull(example);
        return  courseRepository.findAll(example,pageable);
    }

    @Override
    public void save(Course course) {
        Assert.notNull(course);
        courseRepository.save(course);
    }

    @Override
    public Course get(Long id) {
        Assert.notNull(id);
        return  courseRepository.getOne(id);
    }
}
