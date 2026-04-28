package com.springboot.StudentProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.StudentProject.model.Admin;

import jakarta.transaction.Transactional;
@Repository
public interface AdminRepository extends JpaRepository<Admin,Long>{
  
	
	  public Optional<Admin> findByEmail(String email);
	  
	  public boolean existsByEmail(String email);
	    
	  @Query(nativeQuery=true, value="sp_admin_getall")
	  public List<Admin> getall();
	 
	  @Transactional
	  @Modifying
	  @Query(nativeQuery=true,value="update admin_details set password=:password where admin_id=:id")
	  public void changePassword(@Param("id") Long id,@Param("password")String password);
	  
	  @Query(nativeQuery=true, value="select * from admin_details where email=:email and password=:password")
	  public Admin login(@Param("email") String email,@Param("password")String password);
	  
	
	
}
