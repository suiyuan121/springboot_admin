package com.fuwo.b3d.learning.service;

import com.fuwo.b3d.learning.model.Course;
import com.fuwo.b3d.model.model.ModelComb;
import com.fuwo.b3d.model.model.ModelPack;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {


    void save(Course course);

    Course get(Long id);

    Page<Course> pageQuery(Example<Course> example,Pageable pageable);
}
