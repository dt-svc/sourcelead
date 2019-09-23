package com.bis.sourcelead.rest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.UserService;

/**
 * This class is used for...
 * 
 * @author: Srinivas Vemula
 * @date: 12-May-2016
 */

@RestController
@RequestMapping("/app")
public class DisplayImageRestController {

	private static final Logger logger = LoggerFactory.getLogger(DisplayImageRestController.class);
	
	@Autowired
	public UserService userService;
	
	@RequestMapping(value = "/userImage", method = RequestMethod.GET)
	public @ResponseBody  void addTimesheetEntries(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
    	User user = userService.findByUserName("SourceLeadAdmin");
		/*if(user.getDocFile() !=null)
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
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
		}*/
	}
   
    
	
}
