package com.springboot.StudentProject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="admin_details")
public class Admin {
          
	      @Id
	      @GeneratedValue(strategy=GenerationType.IDENTITY)
	      @Column(name="admin_id" ,length=50)
	      private Long id;
	      @Column(name="name",length=150,unique=false,nullable=false)
	      private String name;
	      @Column(name="email")
	      private String email;
	      @Column(name="password")
	      private String pass;
	      @Column(name="role")
	      private String role;
	      
	      
		  public Admin() {
			super();
			// TODO Auto-generated constructor stub
		  }
		  
		  
		  public Admin(Long id, String name, String email, String pass,String role) {
			super();
			this.id = id;
			this.name = name;
			this.email = email;
			this.pass = pass;
			this.role=role;
		 }


		  public Long getId() {
			  return id;
		  }
		  public void setId(Long id) {
			  this.id = id;
		  }
		  public String getName() {
			  return name;
		  }
		  public void setName(String name) {
			  this.name = name;
		  }
		  
		  public String getEmail() {
			return email;
		 }
		  public void setEmail(String email) {
			  this.email = email;
		  }
		  public String getPass() {
			  return pass;
		  }
		  public void setPass(String pass) {
			  this.pass = pass;
		  }
		  public String getRole() {
			  return role;
		  }
		  public void setRole(String role) {
			  this.role = role;
		  }
		  
		  
		  
	
	
}
