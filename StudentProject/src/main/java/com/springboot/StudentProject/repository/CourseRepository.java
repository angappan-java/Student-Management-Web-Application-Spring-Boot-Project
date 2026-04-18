package com.springboot.StudentProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.StudentProject.model.Course;

import jakarta.transaction.Transactional;
@Repository
public interface CourseRepository extends JpaRepository<Course,String> {
    
	   @Query(nativeQuery=true, value="select count(*) from course_detail where id=:id")  
	   int countbyid(@Param("id") String id);
	
	   @Query(nativeQuery=true, value="select * from course_detail")
	   public List<Course> getallacourse();
	   
	   @Transactional
	   @Modifying
	   @Query(nativeQuery=true, value="insert into course_detail (id,name) values(:id,:name)")
	   public void courseinsert(@Param("id") String id,@Param("name") String name);
	   
	   
	   @Transactional
	   @Modifying
	   @Query(nativeQuery=true, value="update course_detail set name=:name where id=:id")
	   public void courseupdate(@Param("id") String id,@Param("name") String name);
	   
	   
	   @Query(nativeQuery=true, value="select * from course_detail where id=:id")
	   public Course getbyid(@Param("id") String id);
	   
	   @Transactional
	   @Modifying
	   @Query(nativeQuery=true, value="delete from course_detail where id=:id")
	   public void deletebyid(@Param("id") String id);
	  
	
}
