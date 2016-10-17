package com.bis.sourcelead.rest;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.form.UserProfile;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class UserProfileRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserProfileRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	UserService userService;
	
	@Autowired
    private SourceleadPasswordEncoder sourceleadPasswordEncoder;
	    

	   @RequestMapping(value = "/saveUserProfile" , method = RequestMethod.POST)
	   public String saveUserProfile(@RequestParam(value="json", required=false) String json ,MultipartHttpServletRequest request) throws Exception {
			
		   
		    Iterator<String> itrator = request.getFileNames();
	        MultipartFile multiFile = request.getFile(itrator.next());
		
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    json = "{"+json+"}";
			JSONObject jsonObj = new JSONObject(json);
			Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			UserProfile userProfile =  gson.fromJson(json,UserProfile.class);
			   try {
				user = userService.saveUserProfile(userProfile, multiFile, user);
				
				SourceLeadSessionManager.setSessionAttribute(request, "user", user);
				SourceLeadSessionManager.setSessionAttribute(request, "userName", user.getFirstName()+" "+user.getLastName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			   return "home";
	  
	   
	   }
	   
	   
	   @RequestMapping(value = "/passwordSettings" , method = RequestMethod.POST)
	   public String passwordSettings(@RequestParam(value="json", required=false) String json,HttpServletRequest request) throws Exception {
		   User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		   json = "{"+json+"}";
		   JSONObject jsonObj = new JSONObject(json);
		   Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		   UserProfile userProfile =  gson.fromJson(json,UserProfile.class);
		   
		   if(sourceleadPasswordEncoder.matches(userProfile.getCurrentPassword(), user.getPassword())){
			   user.setPassword(sourceleadPasswordEncoder.encode(userProfile.getPassword()));
			   try {
				userService.addUser(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			   SourceLeadSessionManager.setSessionAttribute(request, "resetPwdMsg", "Your password has reset successfully");  
			   return "home";
		   }else{
			   return "Invalid Password";
		   }
		   
	   }
	   
	   
	   
	   @RequestMapping(value = "/validateCurrentPassword",  method = RequestMethod.POST)
			public  String validateCurrentPassword(@RequestParam(value="currentPassword", required=false) String currentPassword,HttpServletRequest request) throws Exception {
			
		         User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				 if(sourceleadPasswordEncoder.matches(currentPassword, user.getPassword())){
					 return "valid";
				 }else{
					 return "invalid";
				 }
		}
	   
	   
	   @RequestMapping(value = "/deleteProfileImage" , method = RequestMethod.GET)
	   public String deleteProfileImage(HttpServletRequest request) throws Exception {
		   
		   User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		   user.setDocFile(null);
		   user.setFileContentType(null);
		   userService.addUser(user);
		   SourceLeadSessionManager.setSessionAttribute(request, "user", user);
		   
		   return "success";
		   
	   }
	   
	   
     @RequestMapping(value = "/displayUserImage/{userId}" , method = RequestMethod.GET)
     public void displayUserImage(@PathVariable Long userId,HttpServletRequest request,HttpServletResponse response,HttpSession session) {
    	 
    	 User user = null;
			try {
				user = userService.getUserById(userId);
				
				if(user != null && user.getDocFile() != null)
				{
					user.setFileContentType("image/jpeg");
					InputStream in = new ByteArrayInputStream(user.getDocFile());
					BufferedImage bi = ImageIO.read(in);
					OutputStream out = response.getOutputStream();
					ImageIO.write(bi, "jpg", out);
					out.close();
					
				/*	try {
					    InputStream in = new ByteArrayInputStream(user.getDocFile());
			            BufferedImage img = colorImage(ImageIO.read(in));
			            OutputStream out = response.getOutputStream();
			            ImageIO.write(img, "png", out);
			        } catch (IOException ex) {
			            ex.printStackTrace();
			        }
					*/
					    
					
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
			catch (Exception e1) {
				e1.printStackTrace();
			}
			
     }
     
     private static BufferedImage colorImage(BufferedImage image) {
         int width = image.getWidth();
         int height = image.getHeight();
         WritableRaster raster = image.getRaster();

         for (int xx = 0; xx < width; xx++) {
             for (int yy = 0; yy < height; yy++) {
                 int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                 pixels[0] = 0;
                 pixels[1] = 0;
                 pixels[2] = 255;
                 raster.setPixel(xx, yy, pixels);
             }
         }
         return image;
     }
     
     protected boolean isNullOrEmpty(String str) {
 		return (str == null || "".equals(str.trim()));
 	}
    

}
