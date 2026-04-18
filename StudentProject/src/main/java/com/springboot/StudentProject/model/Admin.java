package com.springboot.StudentProject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="admin_details")
public class Admin {
          
	      @Id
	      @Column(name="id" ,length=50)
	      private String id;
	      @Column(name="name",length=150,unique=false,nullable=false)
	      private String name;
	      @Column(name="password",unique=false,nullable=false)
	      private String pass;
		  public Admin() {
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
		  public String getPass() {
			  return pass;
		  }
		  public void setPass(String pass) {
			  this.pass = pass;
		  }
		  
		  
	
	
}
