package com.bis.sourcelead.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.service.RequirementInterviewService;
/**
 * Handles requests for the Requirement Interview.
 * 
 * @author Prasanna Vadla
 *
 */

@Controller
@PropertySource("${propertyLocation:app.properties}")
public class RequirementInterviewController {

	private static final Logger logger = LoggerFactory.getLogger(RequirementInterviewController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	RequirementInterviewService requirementInterviewService;
	

	@RequestMapping(value = "/acceptOrRejectInterview")
	 @ResponseBody  ModelAndView acceptOrRejectInterview(@RequestParam(value="interviewStatus", required=false) String interviewStatus,
				@RequestParam(value = "requirementInterviewId", required = false) String requirementInterviewId,
			 HttpServletRequest request) throws Exception {
		     ModelAndView model = new ModelAndView();
		     RequirementInterview requirementInterview = requirementInterviewService.getRequirementInterviewById(requirementInterviewId);
			 requirementInterview.setInterviewStatus(interviewStatus);
			 requirementInterviewService.saveRequirementInterview(requirementInterview);
			     
			 //bellow dates are convert to sql dates to current date
			     Date date = new Date();
		    	 java.sql.Timestamp currentDate = new java.sql.Timestamp(date.getTime());
			     if(requirementInterview.getStartTime() != null){
			    	 if(currentDate.equals(requirementInterview.getStartTime()) || requirementInterview.getStartTime().after(currentDate)){
			    		 requirementInterview.setStartTimeStr("interviewStartTime");
			      }
			     }
			     if(requirementInterview.getEndTime() != null){
			    	 if(currentDate.equals(requirementInterview.getEndTime()) || currentDate.before(requirementInterview.getEndTime())){
			    		 requirementInterview.setEndTimeStr("interviewEndTime");
			    	 }
	    	     }
			     
				model.setViewName("requirement/interviewAcceptOrRejectOrTentative");
				model.addObject("interviewStatus",interviewStatus);
				model.addObject("requirementInterviewId",requirementInterviewId);
				model.addObject("requirementInterview",requirementInterview);
				model.addObject("interviewEndTime",requirementInterview.getEndTimeStr());
				model.addObject("interviewStartTime",requirementInterview.getStartTimeStr());
				model.addObject("sourceleadUrl", sourceleadUrl);
				
			 return model;
	 }	
}