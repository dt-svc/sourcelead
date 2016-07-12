package com.bis.sourcelead.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.form.UserProfile;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RestController
@PropertySource(value = { "classpath:config/application.properties" })
public class UserProfileController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	UserService userService;
	
	@Autowired
    private SourceleadPasswordEncoder sourceleadPasswordEncoder;
	    

	   @RequestMapping(value = "/userProfile")
	 	public ModelAndView userProfile() {
	 			ModelAndView model = new ModelAndView();
	 			model.setViewName("userProfile");
	 			model.addObject("sourceleadUrl", sourceleadUrl);
	 			return model;
	 	}
	   
	   
	   
	   @RequestMapping(value = "/saveUserProfile" , method = RequestMethod.POST)
	   public String saveUserProfile(UserProfile userProfile,MultipartHttpServletRequest request) {
			
	  User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		   try {
			user = userService.saveUserProfile(userProfile, request, user);
			SourceLeadSessionManager.setSessionAttribute(request, "user", user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		   return "home";
	   }
	   
	   
	   
	   @RequestMapping(value = "/passwordSettings" , method = RequestMethod.POST)
	   public String passwordSettings(UserProfile userProfile,HttpServletRequest request) {
		   User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		   
		   if(sourceleadPasswordEncoder.matches(userProfile.getCurrentPassword(), user.getPassword())){
			   user.setPassword(sourceleadPasswordEncoder.encode(userProfile.getPassword()));
			   try {
				userService.addUser(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			   
			   return "home";
		   }else{
			   return "Invalid Password";
		   }
		   
	   }
	   
	   
	   
     @RequestMapping(value = "/displayUserImage/{userId}" , method = RequestMethod.GET)
     public void displayUserImage(@PathVariable Long userId,HttpServletRequest request,HttpServletResponse response) {
    	 
    	 User user = null;
			try {
				user = userService.getUserById(userId);
				
				if(user != null && user.getDocFile() !=null)
				{
					user.setFileContentType("image/jpeg");
					InputStream in = new ByteArrayInputStream(user.getDocFile());
					BufferedImage bi = ImageIO.read(in);
					OutputStream out = response.getOutputStream();
					ImageIO.write(bi, "jpg", out);
					out.close();
					
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
			catch (Exception e1) {
				e1.printStackTrace();
			}
			
     }

}
