package com.bis.sourcelead.rest.reportsRestController;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class ApplicantBySourceRestController {
	
	@RequestMapping(value = "/getApplicantBySourceDetails", method = RequestMethod.GET)
	public String getApplicantBySourceDetails(HttpServletRequest request) throws Exception {
		
		return null;
	}

}
