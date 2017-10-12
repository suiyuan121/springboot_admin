package com.fuwo.b3d.learning.controller;

import com.fuwo.b3d.learning.model.Course;
import com.fuwo.b3d.learning.service.CourseService;
import com.fuwo.b3d.model.model.ModelComb;
import com.fuwo.b3d.model.service.ModelCombService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;

@Controller
@RequestMapping(BASE_MAPPING + "/learning/course")
public class AdminCourseListController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/list")
    public String list(Model model, Pageable pageable, String searchText,Course course) {
        ExampleMatcher matcher
                = ExampleMatcher.matching();
        if (StringUtils.isNotBlank(searchText)) {
            matcher.withMatcher("teacher", match -> match.contains().ignoreCase())
                    .withMatcher("title", match -> match.contains().ignoreCase());
            course.setTeacher(searchText);
            course.setTitle(searchText);
        }
        Example<Course> example = Example.of(course, matcher);
        Page<Course> courses=courseService.pageQuery(example,pageable);
        model.addAttribute("page",courses);

        return "learning/course_list";
    }
}
