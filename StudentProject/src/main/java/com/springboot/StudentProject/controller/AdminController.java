package com.springboot.StudentProject.controller;

import java.util.List;
import java.util.Optional;

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
	   public String register(Model model) {
		   Admin admin=new Admin();
		   admin.setRole("Admin");
		   model.addAttribute("admin", admin);
		   return "register";
	   }
	   
	   @PostMapping("/register")
	   public String insert_register(@ModelAttribute("admin") Admin admin,Model model,RedirectAttributes res) {
		   
		   try {
			 
			   boolean iscountAdminEmail=adminrepo.existsByEmail(admin.getEmail());
			   if(iscountAdminEmail) {
				   model.addAttribute("err","This Email Id ["+admin.getEmail()+"] Already Exit..");
				   return "register";
			   }
			   admin.setEmail(admin.getEmail());
			   String regexp="^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[~!@#$%^&*_=+-]).{8,}$";
			   
			   if(admin.getPass().matches(regexp)) {
				   String epass=PasswordSecure.encrypt(admin.getPass());
				   admin.setPass(epass);
			   }else {
				   model.addAttribute("err","Password Must 8 Charactor And Include With One Uppercase,One Lowercase , One Number, One Special Charactor");
				   return "register";
			   }
			   adminrepo.save(admin);
			   
			   res.addFlashAttribute("msg","Admin Register Successfully...");
			   return "redirect:/register";
		   }catch(Exception e) {
			   model.addAttribute("err","Registration Failed..."+e.getMessage());
			   return "register";
		   }
		   
	   }
	   
//	  @GetMapping("/login")
//	  public String loginweb() {
//		  return "login";
//	  }
	  
	  @PostMapping("/login")
	  public String login_pass(@RequestParam("email") String email,
			  @RequestParam("pass") String password,
			  Model model,RedirectAttributes redirectattribute)
	  {
		  Optional<Admin> Optadmin=adminrepo.findByEmail(email);
		  if(Optadmin.isPresent()) {
			  try {
				  String decryptPassword=PasswordSecure.decrypt(Optadmin.get().getPass());
				  if(decryptPassword.equals(password)) {
					  model.addAttribute("msg","Login SuccessFully.....");
					  return "features";
				  }else {
					  model.addAttribute("err","Invalid Password");
					  return "index";
				  }
				  
			  }catch(Exception e) {
				  model.addAttribute("err","Login Failed..."+e.getMessage());
				  return "index";
			  }
			  
		  }else {
			  model.addAttribute("err","Invalid Email Id...");
			  return "index";
		  }
		  
	  }
	  @GetMapping("/features")
	  public String feature() {
		  return "features";
	  }
	  @GetMapping("/reset")
	  public String reset() {
		  return "reset";
	  }
	  
	  @PostMapping("/search")
	  public String search(@RequestParam Long id,Model model) throws Exception {
		  try {
			  Optional<Admin> Optadmin=adminrepo.findById(id);
			  if(Optadmin.isPresent()) {
				  Admin admin=Optadmin.get();
				  System.out.println("Admin Email :"+Optadmin.get().getEmail());
				  model.addAttribute("admin",admin);
				  model.addAttribute("msg", "Admin Found");
			  }else {
				  model.addAttribute("err","Admin Not Found");
			  }
			  return "reset";

		  }catch(Exception e) {
			  model.addAttribute("err", e.getMessage());
			  return "reset";
		  }
    }
	  
	  @PostMapping("/reset")
	  public String reset_data(@RequestParam(name="password",required=false)String newpassword,
			  @ModelAttribute Admin admin,Model model) {
		  try {
			  Admin admindetails=adminrepo.findById(admin.getId()).orElseThrow(()->new RuntimeException("This Admin Id Is Not Found..."));
			  String regexp="^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[~!@#$%^&*_=+-]).{8,}$";
			  if(newpassword.isEmpty()) {
				  admin.setPass(admindetails.getPass());
				  adminrepo.save(admin);
			  }else {
				  if(newpassword.matches(regexp)) {
					  String encryptPassword=PasswordSecure.encrypt(admin.getPass());
					  adminrepo.changePassword(admindetails.getId(),encryptPassword);
					  model.addAttribute("msg","Admin Password Changed Succuessfully...");
					  
				  }else {
					  model.addAttribute("err","Password Must 8 Charactor And Include With One Uppercase,One Lowercase , One Number, One Special Charactor");
					  return "reset";
				  }
			  }
			  
			  return "reset";
		  }catch(Exception e) { 
			  model.addAttribute("err","Reset Process Failed..."+e.getMessage());
			  return "reset";
		  }
		  
	  }
	  
	  @GetMapping("/logout")
	  public String logout() {
		  return "redirect:/";
	  }
	  
	  @GetMapping("/getalladmin")
	  public String getalladmin(Model model) throws Exception {
		   
		  List<Admin> admin=adminrepo.getall();
		  model.addAttribute("admindetails",admin);
		  return "getalladmin";
	  }
	  
	  @GetMapping("/edit/{id}")
	  public String editbyid(@PathVariable("id") Long id,Model model) throws Exception {
		  
		   Optional<Admin> Optadmin=adminrepo.findById(id);
		  
		   model.addAttribute("admin",Optadmin.get());
		  return "edit_admin";
	  }
	  
	  @PostMapping("/editadmin")
	  public String editadmin(@RequestParam(value="password",required=false)String password,
			                  @ModelAttribute Admin admin,Model model,RedirectAttributes re) {
		  try {
			  if(password.isEmpty()) {
				  admin.setPass(admin.getPass());
			  }else {
				  String regexp="^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[~!@#$%^&*_=+-]).{8,}$";
				  if(password.matches(regexp)) {
					  String EncryptPassword=PasswordSecure.encrypt(admin.getPass());
					  admin.setPass(EncryptPassword);
				  }else {
					  model.addAttribute("err","Password Must Be 8 Charctor Include With One UpperCase,"
					  		+ "One LowerCase, One Number,One Special Charactor");
					  return "edit_admin";
				  }
			  }
			  admin.setName(admin.getName());
			  admin.setEmail(admin.getEmail());
			  adminrepo.save(admin);
			  re.addFlashAttribute("msg","Admin Edit SuccessFully.....");
			  return "redirect:/getalladmin";
		  }catch(Exception e) {
			  model.addAttribute("err","Edit Process Failed..."+e.getMessage());
			  return "edit_admin";
		  }
		  
	  }
	  	  
	  @GetMapping("/delete/{id}")
	  public String deletebyid(@PathVariable Long id,RedirectAttributes re) {
		  adminrepo.deleteById(id);
		  re.addFlashAttribute("msg","Admin Delete Successfully....");
		  return "redirect :/getalladmin";
	  }
}
