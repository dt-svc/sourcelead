package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app")
public class CandidateReferencesController {
	private static final Logger logger = LoggerFactory.getLogger(CandidateReferencesController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	
	/**
	 * Handles to display candidate references jsp
	 * @return ModelAndView
	 * @throws Exception
	 */
	 @RequestMapping(value = "/candidateReferences")
		public ModelAndView candidateDocuments(HttpServletRequest request) throws Exception {
		 	logger.debug("Display the candidateReferences page");
			ModelAndView model = new ModelAndView();
			model.setViewName("candidates/candidate");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;
	    }
	
	
}
