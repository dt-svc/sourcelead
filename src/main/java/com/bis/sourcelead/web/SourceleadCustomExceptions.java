package com.bis.sourcelead.web;

import org.hibernate.ObjectNotFoundException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles Exception and displays Error page
 * @author Rajashekar
 * @date 10-08-2015
 *
 */
@Controller
@PropertySource("${propertyLocation:app.properties}")
@ControllerAdvice
public class SourceleadCustomExceptions {
	private static final Logger logger = LoggerFactory.getLogger(SourceleadCustomExceptions.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
/**
 * Returns Error Page if NullPointerException occurred
 * @param exception
 * @return error jsp page
 */
	@ExceptionHandler(NullPointerException.class)
	public String customErrorPage(NullPointerException exception){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		exception.printStackTrace();
		if(auth != null && auth.getPrincipal() != null){
			logger.error("Root cause for Exception is: "+exception.getMessage()); 
			return "redirect:/errorPage";
		 }
		 else{
			 return "redirect:/login";
		 }
	}
	
	/**
	 * Returns Error Page when ObjectNotFoundException occurred
	 * @param exception
	 * @return error jsp page
	 */
	@ExceptionHandler(ObjectNotFoundException.class)
	public String customErrorPageObjectNotFound(ObjectNotFoundException exception){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		exception.printStackTrace();
		if(auth != null && auth.getPrincipal() != null){
			logger.error("Root cause for Exception is: "+exception.getMessage()); 
			return "redirect:/errorPage";
		 }
		 else{
			 return "redirect:/login";
		 }
		
		
	}
	
/**
 * Returns Error Page if JSONException is  occurred	
 * @param exception
 * @return error jsp page
 */
	// As of now commented need handle rest exceptions because its returning json result not View Object
    /*@ExceptionHandler(JSONException.class)
	public String customErrorPageJsonException(JSONException exception){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null && auth.getPrincipal() != null){
			exception.printStackTrace();
			return "redirect:/errorPage";
		 }
		 else{
			 return "redirect:/login";
		 }
	}*/
}
