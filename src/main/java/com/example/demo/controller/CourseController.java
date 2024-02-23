package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @RequestMapping("/courses")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String listCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "courses";
    }

    @GetMapping("/courses/new")
    public String createCourseForm(Model model) {
        Course course = new Course();
        model.addAttribute("course", course);
        return "create_course";
    }

    @PostMapping("/courses")
    public String saveCourse(@ModelAttribute("course") Course course){
        courseService.saveCourse(course);
        return "redirect:/courses";
    }

    @GetMapping("/courses/edit/{id}")
    public String editCourseForm(@PathVariable Long id, Model model){
        model.addAttribute("course", courseService.getCourseById(id));
        return "edit_course";
    }

    @PostMapping("/courses/{id}")
    public String updateCourse(@PathVariable Long id, @ModelAttribute("course") Course course, Model model){

        Course existingCourse = courseService.getCourseById(id);
        existingCourse.setCourseNum(course.getCourseNum());
        existingCourse.setDepName(course.getDepName());
        existingCourse.setCourseCredit(course.getCourseCredit());

        courseService.updateCourse(existingCourse);
        return "redirect:/courses";
    }

    @GetMapping("/courses/{id}")
    public String deleteString(@PathVariable Long id){
        courseService.deleteCourseById(id);
        return "redirect:/courses";
    }



}
