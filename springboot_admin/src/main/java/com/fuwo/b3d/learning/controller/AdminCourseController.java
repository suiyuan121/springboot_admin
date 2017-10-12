package com.fuwo.b3d.learning.controller;

import com.fuwo.b3d.enums.StateEnum;
import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.enums.TagEnum;
import com.fuwo.b3d.learning.model.Course;
import com.fuwo.b3d.learning.model.Video;
import com.fuwo.b3d.learning.service.CourseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;

@Controller
@RequestMapping(BASE_MAPPING + "/learning/course")
public class AdminCourseController {


    @Autowired
    private CourseService courseService;


    @GetMapping("/save")
    public String add(Model model, Course course) {

        if (course.getId() != null) {
            //来源于编辑
            course = courseService.get(course.getId());
        }

        String contactNum = "";
        String contactWay = "";
        if (StringUtils.isNotBlank(course.getWeixin())) {
            contactNum = course.getWeixin();
            contactWay = "weixin";
        } else if (StringUtils.isNotBlank(course.getQq())) {
            contactNum = course.getQq();
            contactWay = "qq";
        } else {
            contactNum = course.getTel();
            contactWay = "tel";
        }
        model.addAttribute("contactNum", contactNum);
        model.addAttribute("contactWay", contactWay);
        model.addAttribute("course", course);
        model.addAttribute("states", StateEnum.values());

        return "learning/course";
    }

    @PostMapping(value = "/save")
    public String addPost(Course course, Model model, String contactWay, String contactNum) {



        if (course.getId() == null) {
            //创建
            course.setCreated(new Date());
            course.setCreator("testUser");
        }
        if ("weixin".equals(contactWay)) {
            course.setWeixin(contactNum);
        } else if ("qq".equals(contactWay)) {
            course.setQq(contactNum);
        } else {
            course.setTel(contactNum);
        }

        course.setModified(new Date());
        course.setModifier("testUser");
        course.setStatus(StatusEnum.ENABLE);
        courseService.save(course);

        model.addAttribute("course", course);
        model.addAttribute("contactNum", contactNum);
        model.addAttribute("contactWay", contactWay);

        return "learning/course";
    }


}
