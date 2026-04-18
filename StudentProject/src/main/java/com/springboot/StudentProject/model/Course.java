package com.springboot.StudentProject.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="course_detail")
public class Course {
     
	  @Id
	  @Column(name="id")
	  private String id;
	  
	  @NotBlank(message="Course Name Field Required")
	  @Column(name="name",nullable=false)
	  private String name;
	  
	  

	  @OneToMany(mappedBy="course" ,cascade=CascadeType.ALL, orphanRemoval=true)
	  private List<Student> student;
	
	  public Course() {
			super();
			
		}

	  public String getId() {
		  return id;
	  }

	  public void setId(String id) {
		  this.id = id;
	  }

	  public String getName() {
		  return name;
	  }

	  public void setName(String name) {
		  this.name = name;
	  }

	  public List<Student> getStudent() {
		  return student;
	  }

	  public void setStudent(List<Student> student) {
		  this.student = student;
	  }
	  
	  
}
