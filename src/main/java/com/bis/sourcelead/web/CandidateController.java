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
public class CandidateController {
	private static final Logger logger = LoggerFactory.getLogger(CandidateController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@RequestMapping(value = "/candidatesList")
	public ModelAndView usersList(HttpServletRequest request) {
		logger.debug("--------candidatesList------");
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("candidates/candidatesList");
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Candidates);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		return model;

	}

}
