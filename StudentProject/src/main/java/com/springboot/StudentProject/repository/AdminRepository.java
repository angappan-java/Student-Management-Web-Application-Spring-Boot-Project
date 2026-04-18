package com.springboot.StudentProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.StudentProject.model.Admin;

import jakarta.transaction.Transactional;
@Repository
public interface AdminRepository extends JpaRepository<Admin,String>{
  
	
	  @Query(nativeQuery=true, value="sp_admin_getbyid:id")
	  public Admin getone(@Param("id") String id);
	  
	  @Transactional
	  @Modifying
	  @Query(nativeQuery=true,value="sp_admin_insert :id,:name,:pass")
	  public void insert(@Param("id") String id,@Param("name") String name,@Param("pass") String pass);
	  
	  @Query(nativeQuery=true, value="sp_admin_getall")
	  public List<Admin> getall();
	  
	  
	  @Transactional
	  @Modifying
	  @Query(nativeQuery=true,value="sp_admin_update :id,:name,:pass")
	  public void upadte(@Param("id") String id,@Param("name") String name,@Param("pass") String pass);
	 
	  
	  @Transactional
	  @Modifying
	  @Query(nativeQuery=true, value="sp_admin_delete :id")
	  public void delete(@Param("id") String id);
	  
	  @Query(nativeQuery=true, value="sp_login :name")
	  public Admin login(@Param("name") String name);
	  
	
	
}
