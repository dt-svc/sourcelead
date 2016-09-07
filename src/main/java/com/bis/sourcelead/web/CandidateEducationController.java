package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.service.LookUpDataService;

@Controller
public class CandidateEducationController {
	private static final Logger logger = LoggerFactory.getLogger(CandidateEmployersController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	/**
	 * Handles to display candidate employers jsp
	 * @return ModelAndView
	 * @throws Exception
	 */
	 @RequestMapping(value = "/candidateEducation")
		public ModelAndView candidateEducation(HttpServletRequest request) throws Exception {
		 	logger.debug("Display the candiadeEducation page");
			ModelAndView model = new ModelAndView();
			model.setViewName("candidates/candidate");
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			 List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
			 model.addObject("educationsList", educationsList);
			 
			return model;
	    }
	
	
}
