package com.bis.sourcelead.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.web.AutoCompleteController;

/**
 * Prasanna Vadla
 * @author bis113
 *
 */

@RestController
public class RequirementInterviewRestController {

	private static final Logger logger = LoggerFactory.getLogger(AutoCompleteController.class);
	
	@Autowired
	private ApplicationContext applicationContext;
	
    
}
