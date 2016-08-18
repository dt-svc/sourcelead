package com.bis.sourcelead.web;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
		
		if(auth != null && auth.getPrincipal() != null){
			logger.error(ExceptionUtils.getFullStackTrace(exception));
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
			logger.error(ExceptionUtils.getFullStackTrace(exception)); 
			return "redirect:/errorPage";
		 }
		 else{
			 return "redirect:/login";
		 }
	}
	
	/**
	 * Returns Error Page when ArrayIndexOutOfBoundsException occurred
	 * @param exception
	 * @return error jsp page
	 */
	@ExceptionHandler(ArrayIndexOutOfBoundsException.class)
	public String customErrorPageArrayIndexOutOfBound(ArrayIndexOutOfBoundsException exception){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		exception.printStackTrace();
		if(auth != null && auth.getPrincipal() != null){
			logger.error(ExceptionUtils.getFullStackTrace(exception)); 
			return "redirect:/errorPage";
		 }
		 else{
			 return "redirect:/login";
		 }
	}
	
	/**
	 * Returns Error Page when ClassCastException occurred
	 * @param exception
	 * @return error jsp page
	 */
	@ExceptionHandler(ClassCastException.class)
	public String customErrorPageClassCast(ClassCastException exception){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		exception.printStackTrace();
		if(auth != null && auth.getPrincipal() != null){
			logger.error(ExceptionUtils.getFullStackTrace(exception)); 
			return "redirect:/errorPage";
		 }
		 else{
			 return "redirect:/login";
		 }
	}

}
