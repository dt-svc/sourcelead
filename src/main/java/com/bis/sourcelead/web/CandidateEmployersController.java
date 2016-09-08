package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.util.SessionMaintainanceData;

@Controller
public class CandidateEmployersController {
	private static final Logger logger = LoggerFactory.getLogger(CandidateEmployersController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	
	/**
	 * Handles to display candidate employers jsp
	 * @return ModelAndView
	 * @throws Exception
	 */
	 @RequestMapping(value = "/candidateEmployers")
		public ModelAndView candidateEmployers(HttpServletRequest request) throws Exception {
		 	logger.debug("Display the candiade page");
			ModelAndView model = new ModelAndView();
			model.setViewName("candidates/candidate");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;
	    }
	
	
}
