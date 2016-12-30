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
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
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
	
	@Autowired
	RequirementSubmissionHistoryService requirementSubmissionHistoryService; 

	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
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
			     
			     //stroe  requirementSubmissionHistoryService 
			     User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			     RequirementSubmission  requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(requirementInterview.getSubmissionId().getId());
			     RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
				 requirementSubmissionHistory.setFromStage(ApplicantSubmissionStatus.INTERVIEW.toString());
				 if(interviewStatus.equalsIgnoreCase(ApplicantSubmissionStatus.ATTENDING.toString())){
					 requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.INTERVIEW_ACCEPT);
				 }
				 if(interviewStatus.equalsIgnoreCase(ApplicantSubmissionStatus.NOT_ATTENDING.toString())){
					 requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.INTERVIEW_REJECT);
				 }
				 if(interviewStatus.equalsIgnoreCase(ApplicantSubmissionStatus.MAYBE.toString())){
					 requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.INTERVIEW_TENTATIVE);
				 }
				 requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user, requirementSubmissionHistory);
					
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