package com.bis.sourcelead.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.LoginService;
import com.bis.sourcelead.service.UserService;

/**
 * This class is used for...
 * 
 * @author: Srinivas Vemula
 * @date: 12-May-2016
 */

@Controller
@RestController
public class DisplayImageController {

	private static final Logger logger = LoggerFactory.getLogger(DisplayImageController.class);

	@Autowired
	public LoginService loginService;
	
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	public UserService userService;
	
	
	
	@PostConstruct
	public void init() {
		System.out.println(" *** HomeController init with: " + applicationContext);
	}

    @RequestMapping(value = "/userAutoComplete")
  	public ModelAndView userAutoComplete() {
  		ModelAndView model = new ModelAndView();
  		model.setViewName("userAutoComplete");
  		return model;

  	}
    
    @RequestMapping(value = "/userImage", method = RequestMethod.GET)
	public @ResponseBody  void addTimesheetEntries(HttpServletRequest request, HttpServletResponse response) throws Exception
			 
	{
    	User user = userService.findByUserName("SourceLeadAdmin");
		if(user.getDocFile() !=null)
		{
			response.setContentType("image/jpeg");
			
			try {
				
				InputStream in = new ByteArrayInputStream(user.getDocFile());
				BufferedImage bi = ImageIO.read(in);
				OutputStream out = response.getOutputStream();
				if(bi != null){
					ImageIO.write(bi, "jpg", out);
				}
				
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
   
    
	
}
