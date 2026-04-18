package com.springboot.StudentProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.StudentProject.encryptpass.PasswordSecure;
import com.springboot.StudentProject.model.Admin;
import com.springboot.StudentProject.repository.AdminRepository;

@Controller
public class AdminController {
       
	   @Autowired
	   private AdminRepository adminrepo;
	   
	   @GetMapping("/")
	   public String home() {
		   return "index";
	   }
	   
	   @GetMapping("/register")
	   public String register() {
		   return "register";
	   }
	   
	   @PostMapping("/register")
	   public String insert_register(@RequestParam("id") String id,@RequestParam("name") String name,
			   @RequestParam("pass") String pass,Model model) {
		   
		   try {
			    Admin sameid=adminrepo.getone(id);
			    if(sameid!=null) {
			    	model.addAttribute("err","This Id Is Exit");
			    	return "register";
			    }
			   String epass=PasswordSecure.encrypt(pass);
			   
			   adminrepo.insert(id, name, epass);
			   
			   model.addAttribute("msg","Admin Register Successfully...");
			 
		   }catch(Exception e) {
			   model.addAttribute("msg","Registration Failed..."+e.getMessage());
		   }
		   return "register";
		 // return "redirect:/"; 
		   
	   }
	   
//	  @GetMapping("/login")
//	  public String loginweb() {
//		  return "login";
//	  }
	  
	  @PostMapping("/login")
	  public String login_pass(@RequestParam("name") String name,
			  @RequestParam("pass") String password,
			  Model model,RedirectAttributes redirectattribute)
	  {
		  Admin admin=adminrepo.login(name);
		  if(admin!=null) {
			  try {
				  String decryptPassword=PasswordSecure.decrypt(admin.getPass());
				  if(decryptPassword.equals(password)) {
					  model.addAttribute("msg","Login SuccessFully.....");
					  return "student";
				  }else {
					  model.addAttribute("err","Invalid Password");
				  }
				  
			  }catch(Exception e) {
				  model.addAttribute("err","Login Failed..."+e.getMessage());
			  }
			  
		  }else {
			  model.addAttribute("err","Please Enter Your UserName...");
		  }
		  return  "index";
	  }
	  
	  @GetMapping("/student")
	  public String student() {
		  return "student";
	  }
	  
	  @GetMapping("/reset")
	  public String reset() {
		  return "reset";
	  }
	  
	  @PostMapping("/search")
	  public String search(@RequestParam String id,Model model) throws Exception {
		  
		  Admin admin=adminrepo.getone(id);
		  if(admin!=null) {
			  
			  String decryptPassword=PasswordSecure.decrypt(admin.getPass());
			  admin.setPass(decryptPassword);
			  
			  model.addAttribute("admin",admin);
			  model.addAttribute("found", "User Found");
		  }else {
			  model.addAttribute("notfound","User Not Found");
		  }
		  return "reset";
	  }
	  
	  @PostMapping("/reset")
	  public String reset_data(@ModelAttribute Admin admin,Model model) {
		  
		  try {
			  String encryptPassword=PasswordSecure.encrypt(admin.getPass());
			  admin.setPass(encryptPassword);
			  adminrepo.upadte(admin.getId(), admin.getName(),admin.getPass());
			  model.addAttribute("msg","Admin Reset Succuessfully...");
			  
		  }catch(Exception e) { 
			  model.addAttribute("err","Reset Process Failed..."+e.getMessage());
		  }
		  return "reset";
	  }
	  
	  @GetMapping("/logout")
	  public String logout() {
		  return "redirect:/";
	  }
	  
	  @GetMapping("/getalladmin")
	  public String getalladmin(Model model) throws Exception {
		   
		  List<Admin> admin=adminrepo.getall();
		  
		  for(Admin admindetail:admin) {
			  String decryptPasswpord=PasswordSecure.decrypt(admindetail.getPass());
			  admindetail.setPass(decryptPasswpord);
		  }
		  model.addAttribute("admin",admin);
		  return "getalladmin";
	  }
	  
	  @GetMapping("/edit/{id}")
	  public String editbyid(@PathVariable("id") String id,Model model) throws Exception {
		  
		   Admin admin=adminrepo.getone(id);
		   if(admin!=null) {
			   String DecryptPassword=PasswordSecure.decrypt(admin.getPass());
			   admin.setPass(DecryptPassword);
		   }
		   
		   model.addAttribute("admin", admin);
		  return "edit_admin";
	  }
	  
	  @PostMapping("/edit")
	  public String editadmin(@ModelAttribute Admin admin,RedirectAttributes re) {
		  try {
			  String EncryptPassword=PasswordSecure.encrypt(admin.getPass());
			  admin.setPass(EncryptPassword);
			  adminrepo.upadte(admin.getId(),admin.getName(),admin.getPass());
			  re.addFlashAttribute("msg","Admin Edit SuccessFully.....");
			  
		  }catch(Exception e) {
			  re.addFlashAttribute("err","Edit Process Failed..."+e.getMessage());
		  }
		  return "redirect:/getalladmin";
	  }
	  
	  
	  
	  @GetMapping("/delete/{id}")
	  public String deletebyid(@PathVariable String id,RedirectAttributes re) {
		  adminrepo.delete(id);
		  re.addFlashAttribute("msg","Admin Delete Successfully....");
		  return "redirect :/getalladmin";
	  }
}
