package com.springboot.StudentProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.springboot.StudentProject.model.Student;

import jakarta.transaction.Transactional;
@Repository
public interface StudentRepository extends JpaRepository<Student,String>{

	   @Query(nativeQuery=true, value="select count(*) from student_details where id=:id")
	   int countbyid(@Param("id") String id);
	   
	   @Transactional
	   @Modifying
	   @Query(nativeQuery=true, value="insert into student_details (id,name,age,gender,dob,time,image,course_id)"
	   		+ "values(:id,:name,:age,:gender,:dob,:time,:image,:courseid)")
	   public void studentinsert(@Param("id") String id,@Param("name") String name,
			             @Param("age") String age,@Param("gender") String gender,
			             @Param("dob") String dob,@Param("time") String time,
			             @Param("image") String image,@Param("courseid") String courseId);
	   
	  @Transactional
	  @Modifying
      @Query(nativeQuery=true, value="update student_details set name=:name,age=:age,gender=:gender,dob=:dob,time=:time,image=:image,course_id=:courseid where id=:id")
	  public void studentupdate(@Param("id") String id,@Param("name") String name,
			             @Param("age") String age,@Param("gender") String gender,
			             @Param("dob") String dob,@Param("time") String time,
			             @Param("image") String image,@Param("courseid") String courseid);
	  
	  @Query(nativeQuery=true, value="select * from student_details where id=:id")
	  public Student getbyid(@Param("id") String id);
	  
	  @Query(nativeQuery=true, value="select * from student_details")
	  public List<Student> getallstudent();
	  
	  @Transactional
	  @Modifying
	  @Query(nativeQuery=true, value="delete from student_details where id=:id")
	  public void deletebystudent(@Param("id") String id);
	  
	  
}
