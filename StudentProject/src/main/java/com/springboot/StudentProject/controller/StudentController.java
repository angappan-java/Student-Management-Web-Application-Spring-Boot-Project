package com.springboot.StudentProject.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.springboot.StudentProject.model.Student;
import com.springboot.StudentProject.repository.CourseRepository;
import com.springboot.StudentProject.repository.StudentRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class StudentController {
          
	      @Autowired
	      private CourseRepository courserepo;
	      @Autowired
	      private StudentRepository studentrepo;
	      
	    
	      
	      
	      
	      
	    @GetMapping("/addstudent")
	    public String studentinsert(Model model) {
	    	 
	    	Student student=new Student();
	    	
	    	DateTimeFormatter  formatter=DateTimeFormatter.ofPattern("HH:mm:ss a");
	    	student.setTime(LocalTime.now().format(formatter).toString());
	    	
	    	model.addAttribute("student",student);
	    	model.addAttribute("courses",courserepo.getallacourse());
	    	
	    	return "student_add";
	    	
	    }
	    
	    @PostMapping("/addstudent")
	    public String studentadd(@Valid @ModelAttribute("student") Student student,
	    		      BindingResult result,Model model,
	    		      @RequestParam("imagefile") MultipartFile imageFile) {
	    	  
	    	if(result.hasErrors()) {
	    		return "student_add";
	    	}
	    	
	    	if(studentrepo.countbyid(student.getId())>0) {
	    		model.addAttribute("err","This Student Id Already Extits");
	    		return "student_add";
	    	}
	    	try {
	    		if(!imageFile.isEmpty()) {
	    			String imagepath="D:/Angappan/3.Spring Boot Project/StudentProject/StudentProject/src/main/resources/static/images/";
	    			File  imageupload=new File(imagepath);
	    			if(!imageupload.exists()) {
	    				imageupload.mkdirs();
	    			}
	    			String imageName=imageFile.getOriginalFilename();
	    			imageFile.transferTo(new File(imageupload+imageName));
	    			student.setImage(imageName);
	    			
	    	   }
	    			studentrepo.studentinsert(student.getId(),student.getName(),student.getAge(),
	    					student.getGender(),student.getDob(),student.getTime(),
	    					student.getImage(),student.getCourse().getId());
	    			model.addAttribute("msg","Student Add SuccesFully......");
	    			return "student_add";
	    		
	    		
	    	}catch(Exception e) {
	    		model.addAttribute("err", "Student Not Added...");
	    		return "student_add";
	    	}
	    	
	    	
	    }
	    @GetMapping("/editstudent")
	    public String editstudent() {
	        return "student_edit";
	    }
	    @GetMapping("/searchstudent")
	    public String searchstudent(@RequestParam("id") String id,Model model) {
	    	
	    	Student student=studentrepo.getbyid(id);
	    	
	    	if(student==null) {
	    		model.addAttribute("err","This Student Id["+id+"] Is Not Found");
	    		return "student_edit";
	    	}
//	    	if(student.getCourse()==null) {
//	    		Course course=courserepo.getbyid(student.getCourse().getId());
//	    		student.setCourse(course);
//	    		
//	    	}
	    	if(student!=null) {
	    		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("HH:mm:ss a");
	    		student.setTime(LocalTime.now().format(formatter).toString());
	    		
	    		model.addAttribute("student",student);
	    		model.addAttribute("courses",courserepo.getallacourse());
	    	}
	    	return "student_edit";
	    	
	    }
	    @PostMapping("/updatestudent")
	    public String updatestudent( @Valid @ModelAttribute("student") Student student,
	    		                   BindingResult resilt,Model model,
	    		                   @RequestParam(value = "imagefile", required = false) MultipartFile imageFile ) throws IllegalStateException, IOException {
	    	Student st=studentrepo.getbyid(student.getId());
	    	
	    	if(!imageFile.isEmpty() && imageFile!=null) {
    			String imagepath="D:/Angappan/3.Spring Boot Project/StudentProject/StudentProject/src/main/resources/static/images/";
    			File  imageupload=new File(imagepath);
    			if(!imageupload.exists()) {
    				imageupload.mkdirs();
    			}
    			String imageName=imageFile.getOriginalFilename();
    			imageFile.transferTo(new File(imageupload+imageName));
    			student.setImage(imageName);
    			
    	   }else {
	    		student.setImage(st.getImage());	    		
	    	}
    			studentrepo.studentupdate(student.getId(),student.getName(),student.getAge(),
    					student.getGender(),student.getDob(),student.getTime(),
    					student.getImage(),student.getCourse().getId());
    			model.addAttribute("msg","Student Edit SuccesFully......");
    			return "student_edit";
	    }
	    
	    @PostMapping("/studentdelete")
	    public String deletestudent(@RequestParam("id") String id,
	    		      Model model) {
	    	try {
	    		     studentrepo.deletebystudent(id);
	    		     model.addAttribute("msg","This Student Id["+id+"] Is Deleted.....");
	    		     return "student_get";
	    		}catch(Exception e) {
	    		     model.addAttribute("err","This Student Not Deleted.....");
	    		     return "student_get";
	    	}
	    }
	    
	    @GetMapping("/pdf")
	    public String studentpdf() {
	    	return "studentpdf";
	    }
	    @GetMapping("/studentpdf")
	    public String pdf(@RequestParam("id") String id,
	    		HttpServletResponse response,Model model) throws DocumentException, IOException {
	    	
	    	Student stu=studentrepo.getbyid(id);
	    	
	    	if(stu==null) {
	    		model.addAttribute("err","This Student Id["+id+"] Is Not Found");
	    		return "studentpdf";
	    	}
	    	
	    	response.setContentType("application/pdf");
	    	response.setHeader("Content-Disposition","attachment;filename="+stu.getId()+".pdf");
	    	
	    	Document document=new Document();
	    	PdfWriter.getInstance(document, response.getOutputStream());
	    	
	    	document.open();
	    	document.addTitle("Student Details");
	    	
	    	document.add(new Paragraph("Student Details",
	    			new Font(FontFamily.TIMES_ROMAN,20,java.awt.Font.BOLD)));
	    	document.add(new Paragraph("\n"));
	    	
	    	String imagePath="D:/Angappan/3.Spring Boot Project/StudentProject/StudentProject/src/main/resources/static/images/"+stu.getImage();
	    	
	        try {
	        	Image image=Image.getInstance(imagePath);
		    	image.scaleToFit(100,100);
		    	image.setAlignment(Image.ALIGN_CENTER);
		    	document.add(image);
	        }catch(Exception e) {
	        	document.add(new Paragraph("Image Not Found"));
	        }
	        
	        
	        document.add(new Paragraph("\n"));
	        
	        document.add(new Paragraph("Id : "+stu.getId()));
	        document.add(new Paragraph("\n"));
	        document.add(new Paragraph("Name : "+stu.getName()));
	        document.add(new Paragraph("\n"));
	        document.add(new Paragraph("Age : "+stu.getAge()));
	        document.add(new Paragraph("\n"));
	        document.add(new Paragraph("Gender : "+stu.getGender()));
	        document.add(new Paragraph("\n"));
	        document.add(new Paragraph("Date Of Birth : "+stu.getDob()));
	        document.add(new Paragraph("\n"));
	        document.add(new Paragraph("Time : "+stu.getTime()));
	        document.add(new Paragraph("\n"));
	        document.add(new Paragraph("Course : "+stu.getCourse().getName()));
	        document.add(new Paragraph("\n"));
	        document.add(new Paragraph("\n"));
	        document.add(new Paragraph("\n"));
	        document.add(new Paragraph("-----------------Thank You---------------"));
	        document.close();
	        model.addAttribute("msg", " Student Pdf Generated SuccesFully");
	        
	        
	    	
	        
	        return "studentpdf";
	        
	    }
	   
	
}
