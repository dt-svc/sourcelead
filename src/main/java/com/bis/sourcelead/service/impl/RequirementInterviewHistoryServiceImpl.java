package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RequirementInterviewDao;
import com.bis.sourcelead.dao.RequirementInterviewHistoryDao;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.service.RequirementInterviewHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.util.CalendarUtil;

@Service
public class RequirementInterviewHistoryServiceImpl implements RequirementInterviewHistoryService{
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementInterviewHistoryServiceImpl.class);

    @Autowired
    RequirementInterviewHistoryDao requirementInterviewHistoryDao;
    
    @Autowired
	private RequirementInterviewDao requirementInterviewDao;
    
	@Autowired
	private RequirementSubmissionService requirementSubmissionService;
	
	@Override
	public RequirementInterviewHistory storeRequirementInterviewHistory(
			RequirementInterviewHistory requirementInterviewHistory) throws Exception {
		return requirementInterviewHistoryDao.storeRequirementInterviewHistory(requirementInterviewHistory);
	}
	
	@Override
	public RequirementInterviewHistory getRequirementInterviewHistoryById(Long Id) {
		return requirementInterviewHistoryDao.getRequirementInterviewHistoryById(Id);
	}
	
	@Override
	public List<RequirementInterviewHistory> getRequirementInternalAndIsClientInterviewsBySubmissionIdAndOrgLocationsId(Long submissionId, Long orgLocationsId) throws Exception {
		logger.debug("In RequirementInterviewServiceImpl class getRequirementInternalAndIsClientInterviewsBySubmissionIdAndOrgLocationsId method...");
		
		List<RequirementInterviewHistory> interviewslist = null;
		interviewslist = requirementInterviewHistoryDao.getRequirementInternalAndIsClientInterviewsBySubmissionIdAndOrgLocationsId(submissionId,orgLocationsId);
		if (interviewslist != null && !interviewslist.isEmpty() ) {
			for (RequirementInterviewHistory interviewHistory : interviewslist) {
				
				RequirementInterview requirementInterview = requirementInterviewDao.getRequirementInterviewById(interviewHistory.getInterviewId().getId().toString());
				
				if(requirementInterview.getRemainedBefore() != null && !requirementInterview.getRemainedBefore().isEmpty()){
					Integer hours = (Integer.parseInt(requirementInterview.getRemainedBefore()) / 60);
					Integer minutes = (Integer.parseInt(requirementInterview.getRemainedBefore()) % 60);
					interviewHistory.setRemainderTime(hours + " : " + minutes + " minutes");
				}
				
			    if(requirementInterview.getDateOfInterview() != null){
			    	interviewHistory.setDateOfInterviewStr(Constants.DMY.format(requirementInterview.getDateOfInterview()));
			    }
			    
			    if(requirementInterview.getStartTime() != null){
			    	 String hours = requirementInterview.getStartTime().getHours()+"";
			    	 String minutes = requirementInterview.getStartTime().getMinutes()+"";
			    	 interviewHistory.setStartTimeStr(CalendarUtil.get12FormatTimeFrom24FormatTime(hours+":"+minutes));
			    }
			    
			    if(requirementInterview.getEndTime() != null){
			    	 String hours = requirementInterview.getEndTime().getHours()+"";
			    	 String minutes = requirementInterview.getEndTime().getMinutes()+"";
			    	 interviewHistory.setEndTimeStr(CalendarUtil.get12FormatTimeFrom24FormatTime(hours+":"+minutes));
			    	 if(requirementInterview.getEndTime().compareTo(new Date())<0){
			    		 interviewHistory.setFeedbackEndTime("feedbackEndTime");
			    	 }
			    }
			    if(requirementInterview.getInterviewTemplateId() != null){
			    	interviewHistory.setInterviewTemplateid(requirementInterview.getInterviewTemplateId().getId());
			    }
			    
			    if(interviewHistory.getSubmissionId().getVendorLocationId() != null){
			    	interviewHistory.setIsNotifyToVendorRequired(Constants.YES);
				}else{
					interviewHistory.setIsNotifyToVendorRequired(Constants.NO);
				}
			    
			    if(requirementInterview.getSubmissionId().getRequirement().getClientId() != null){
					if(requirementInterview.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Client")){
						interviewHistory.setClientName(requirementInterview.getSubmissionId().getRequirement().getClientId().getResponderLocationId().getOrg().getOrgName());
					}
					if(requirementInterview.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Vendor")){
						interviewHistory.setClientName(requirementInterview.getSubmissionId().getRequirement().getClientId().getLocationId().getOrg().getOrgName());
					}
				}else{
					RequirementSubmission requirementSubmission = requirementSubmissionService.getSubmissionsBySourceSubmissionId(requirementInterview.getSubmissionId().getId(),orgLocationsId);
				    if(requirementSubmission != null){
				    	interviewHistory.setClientName(requirementSubmission.getOrgLocations().getOrg().getOrgName());
				    }else if(requirementInterview.getSubmissionId().getSourceSubmission() != null && requirementInterview.getSubmissionId().getSourceSubmission().getId() != null){
				    	RequirementSubmission parentRequirementSubmission = requirementSubmissionService.getSubmissionsBySourceSubmissionId(requirementInterview.getSubmissionId().getSourceSubmission().getId(),orgLocationsId);
				    	 if(parentRequirementSubmission != null){
						     interviewHistory.setClientName(parentRequirementSubmission.getOrgLocations().getOrg().getOrgName());
				    	 }
				    }
				}
			    
			    
			    
			    String InterviewersList = "";
				Set<RequirementInterviewer> Interviewers = requirementInterview.getRequirementInterviewers();
				int i = 0;
				for(RequirementInterviewer interviewer: Interviewers){
					if(i <= 50){
						InterviewersList = InterviewersList+interviewer.getInterviewerUser().getFirstName()+" "+interviewer.getInterviewerUser().getLastName();
						if(i != Interviewers.size()-1){
							InterviewersList = InterviewersList+",";
						}
					}
					i++;
				}
				interviewHistory.setInterviewersList(InterviewersList);
			}
		}
		return interviewslist;
	}
	
	@Override
	public List<RequirementInterviewHistory> getAllInterviewListByCurrentDate(String currentTime, String triggerTime) {
		return requirementInterviewHistoryDao.getAllInterviewListByCurrentDate(currentTime,  triggerTime);
	}

}
