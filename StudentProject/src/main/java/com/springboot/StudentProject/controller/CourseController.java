package com.springboot.StudentProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.springboot.StudentProject.model.Course;
import com.springboot.StudentProject.repository.CourseRepository;

import jakarta.validation.Valid;

@Controller
public class CourseController {
        @Autowired
	   private CourseRepository courserepo;
	
      @GetMapping("/course")
      public String getCourse(Model model) {
    	  List<Course> course=courserepo.getallacourse();
    	  model.addAttribute("coursedetails", course);
    	  return "course";
      }
      @GetMapping("/addcourse")
      public String courseadd(Model model) {
    	  model.addAttribute("course",new Course());
    	  return "course_add";
      }
      
      @PostMapping("/addcourse")
      public String addcourse(@Valid @ModelAttribute Course course,
    		  BindingResult result,Model model) {
           if(result.hasErrors()) {
        	   return "course_add";
           }
           if(courserepo.countbyid(course.getId())>0) {
        	   model.addAttribute("err","This Course Id Already Exits");
        	   return "course_add";
           }
    	 courserepo.courseinsert(course.getId(),course.getName());
    	 model.addAttribute("msg","Course Add SuccessFully....");
    	 return "course_add";
      }
      
      
      @GetMapping("/editcourse/{id}")
      public String getcourseid(@PathVariable("id") String id,Model model) {
    	  
    	  Course course=courserepo.getbyid(id);
    	  if(course==null) {
    		  model.addAttribute("err","This Course Id Not Found...");
    		  return "course_edit";
    	  }
    	  if(course!=null) {
    		  model.addAttribute("course", course);
    		  model.addAttribute("found", "Course Found It");
    	  }
    	  return "course_edit";
      }
      
      @PostMapping("/updatecourse")
      public String updatecourse(@ModelAttribute Course course,
    		  Model model) {
    	  courserepo.courseupdate(course.getId(), course.getName());
    	  model.addAttribute("msg","Course Updated....");
    	  return "course_edit";
      }
      
      @PostMapping("/delete/{id}")
      public String deletecourse(@PathVariable("id") String id,Model model) {
    	  courserepo.deletebyid(id);
    	  model.addAttribute("msg", "This Course["+id+"] Is Deleted...");
    	  return "course_edit";
      }
      
        
}
