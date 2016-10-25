package com.bis.sourcelead.service.impl;

import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RequirementInterviewDao;
import com.bis.sourcelead.dao.RequirementInterviewerDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class RequirementInterviewServiceImpl implements RequirementInterviewService{
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementInterviewServiceImpl.class);
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private RequirementInterviewDao requirementInterviewDao;
	
	@Autowired
	private RequirementInterviewerDao requirementInterviewerDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RequirementSubmissionService requirementSubmissionService;

	@Override
	public void saveRequirementInterview(JSONObject jsonObj, User user, OrgLocations orgLocations) throws Exception  {
		
		String maxAdminTimeTypeRowNum = jsonObj.getString("maxTsRowNum");
		int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);
		RequirementInterview requirementInterview  = null;
		
		for (long c = 1; c < maxCount; c++) {
				if(jsonObj.has(c+"applicant") && !jsonObj.getString(c+"applicant").isEmpty()){
						String applicantIds = jsonObj.getString(c+"applicant");
						applicantIds = applicantIds.replaceAll("\\[", "").replaceAll("\\]","");
						String[] applicants = applicantIds.split(",");
					
					     for(int i=0 ;i<applicants.length;i++ ){
								requirementInterview  = new RequirementInterview();
								RequirementSubmission submission = new RequirementSubmission();
								submission.setId(Long.parseLong(applicants[i].replace("\"", "")));
								requirementInterview.setSubmissionId(submission);
								
								if(jsonObj.has(c+"dateOfInterview") && jsonObj.getString(c+"dateOfInterview") != null && !"".equalsIgnoreCase(jsonObj.getString(c+"dateOfInterview"))){
									requirementInterview.setDateOfInterview(commonUtil.getDateFormat(jsonObj.getString(c+"dateOfInterview")));
								}
								
								if(jsonObj.has(c+"interviewType") && !jsonObj.getString(c+"interviewType").isEmpty()){
									requirementInterview.setInterviewType(jsonObj.getString(c+"interviewType"));
								}
								
								if(jsonObj.has(c+"startTime") && !jsonObj.getString(c+"startTime").isEmpty()){
									requirementInterview.setStartTime(jsonObj.getString(c+"startTime"));
								}
								
								if(jsonObj.has(c+"endTime") && !jsonObj.getString(c+"endTime").isEmpty()){
									requirementInterview.setEndTime(jsonObj.getString(c+"endTime"));
								}
								
								if(jsonObj.has(c+"location") && !jsonObj.getString(c+"location").isEmpty()){
									requirementInterview.setLocation(jsonObj.getString(c+"location"));
								}
								
								if(jsonObj.has("notificationToApplicant") && !jsonObj.getString("notificationToApplicant").isEmpty()){
									requirementInterview.setNotificationToApplicant(jsonObj.getString("notificationToApplicant"));
								}
								
								if(jsonObj.has("notificationToSpoc") && !jsonObj.getString("notificationToSpoc").isEmpty()){
									requirementInterview.setNotificationToSpoc(jsonObj.getString("notificationToSpoc"));
								}
								
								if(jsonObj.has("notificationToOthers") && !jsonObj.getString("notificationToOthers").isEmpty()){
									requirementInterview.setNotificationToOthers(jsonObj.getString("notificationToOthers"));
								}
								
								if(jsonObj.has("notificationToOthersEmails") && !jsonObj.getString("notificationToOthersEmails").isEmpty()){
									requirementInterview.setNotificationToOthersEmails(jsonObj.getString("notificationToOthersEmails"));
								}
								
								if(jsonObj.has("reminderHours") && !jsonObj.getString("reminderHours").isEmpty() && jsonObj.has("reminderMinutes") && !jsonObj.getString("reminderMinutes").isEmpty()){
									Float reminderHours = Float.parseFloat(jsonObj.getString("reminderHours"));
									Float reminderMinutes = Float.parseFloat(jsonObj.getString("reminderMinutes"));
									Float remainedBefore = ((reminderHours*60) + reminderMinutes);
									
									requirementInterview.setRemainedBefore(remainedBefore.toString());
								}
								
							   requirementInterview.setStatus(Constants.RECORD_ACTIVE);
					           requirementInterview.setCreatedDate(new Date());
					           requirementInterview.setCreatedBy(user.getUserCode());
					           requirementInterview.setUpdatedDate(new Date());
					           requirementInterview.setUpdatedBy(user.getUserCode());
					           requirementInterviewDao.storeRequirementInterview(requirementInterview);
					           
					           RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(applicants[i].replace("\"", "")));
					           requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.INTERVIEW);
					           requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
					           
					           //to store RequirementInterviewers
					           if(jsonObj.has(c+"interviewer") && !jsonObj.getString(c+"interviewer").isEmpty()){
					        	    String interviewerIds = jsonObj.getString(c+"interviewer");
					        	    interviewerIds = interviewerIds.replaceAll("\\[", "").replaceAll("\\]","");
									String[] interviewers = interviewerIds.split(",");
									
									for(int j=0 ;j<interviewers.length;j++ ){
										 RequirementInterviewer interviewer = new RequirementInterviewer();
								         interviewer.setRequirementInterview(requirementInterview);
										 User interviewerUser = userService.getUserById(Long.parseLong(interviewers[j].replace("\"", "")));
										 interviewer.setInterviewerUser(interviewerUser);
										 interviewer.setStatus(Constants.RECORD_ACTIVE);
								         interviewer.setCreatedDate(new Date());
								         interviewer.setCreatedBy(user.getUserCode());
								         interviewer.setUpdatedDate(new Date());
							             interviewer.setUpdatedBy(user.getUserCode());
								         requirementInterviewerDao.storeRequirementInterviewer(interviewer);
									}
					           }
							}
				}
			continue;
		}
	}
}
