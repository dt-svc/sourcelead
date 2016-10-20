package com.bis.sourcelead.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
/**
 * Handles requests for the Requirement Interview.
 * 
 * @author Prasanna Vadla
 *
 */

@Controller
@PropertySource("${propertyLocation:app.properties}")
public class InterviewReminderController {

	private static final Logger logger = LoggerFactory.getLogger(InterviewReminderController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	


}