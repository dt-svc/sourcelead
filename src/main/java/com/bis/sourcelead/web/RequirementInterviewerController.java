package com.bis.sourcelead.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Handles requests for the Requirement Interview.
 * 
 * @author Prasanna Vadla
 *
 */

@Controller
@PropertySource("${propertyLocation:app.properties}")
@RequestMapping("/app")
public class RequirementInterviewerController {

	private static final Logger logger = LoggerFactory.getLogger(RequirementInterviewerController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	


}