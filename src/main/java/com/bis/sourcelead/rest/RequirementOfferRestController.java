package com.bis.sourcelead.rest;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;

/**
 * sarath kumar punnana 
 * @author bis113
 *
 */

@RestController
public class RequirementOfferRestController {

	private static final Logger logger = LoggerFactory.getLogger(RequirementOfferRestController.class);
	
	@Autowired
	private ApplicationContext applicationContext;
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	CommonUtil commonUtil;
	
	
	@RequestMapping(value = "/saveOffer" , method = RequestMethod.POST)
	   public String saveOffer(@RequestParam(value="jsonData", required=false) String json, HttpServletRequest request) throws Exception {
		
			return "success";
	    
	}
}
