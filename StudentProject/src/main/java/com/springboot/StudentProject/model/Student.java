package com.springboot.StudentProject.model;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="Student_details")
public class Student {
       
	    @Id
	    @NotBlank(message="Id is Required")
	    private String id;
	    @NotBlank(message="Name is Required")
	    private String name;
	    
	    @NotBlank(message="Age is Required")
	    private String age;
	    
	    @NotBlank(message="Gender is Required")
	    private String gender;
	    @NotNull(message="DOB Is Required")
	    @DateTimeFormat(pattern="yyyy-MM-dd")
	    private String dob;
	    private String time;
	    private String image;
	    
		@ManyToOne
	    @JoinColumn(name="course_id", referencedColumnName="id")
	    private Course course;
		
		public Student() {
			super();
			// TODO Auto-generated constructor stub
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

		public String getAge() {
			return age;
		}

		public void setAge(String age) {
			this.age = age;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public String getDob() {
			return dob;
		}

		public void setDob(String dob) {
			this.dob = dob;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public Course getCourse() {
			return course;
		}

		public void setCourse(Course course) {
			this.course = course;
		}
		
		
	 
}
