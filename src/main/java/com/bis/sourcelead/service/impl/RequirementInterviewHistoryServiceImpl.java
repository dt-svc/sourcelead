package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.bis.sourcelead.entity.InterviewFeedbackTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProposeTime;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.InterviewFeedbackTemplateService;
import com.bis.sourcelead.service.ProposeTimeService;
import com.bis.sourcelead.service.RequirementInterviewHistoryService;
import com.bis.sourcelead.service.RequirementInterviewerService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.web.LoginSessionController;

@Service
public class RequirementInterviewHistoryServiceImpl implements RequirementInterviewHistoryService{
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementInterviewHistoryServiceImpl.class);

    @Autowired
    RequirementInterviewHistoryDao requirementInterviewHistoryDao;
    
    @Autowired
	private RequirementInterviewDao requirementInterviewDao;
    
	@Autowired
	private RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private TimeZoneService timeZoneService;
	
	@Autowired
	private SubmissionTemplateService submissionTemplateService;
	
	@Autowired
	RequirementInterviewerService requirementInterviewerService;
	
	@Autowired
	InterviewFeedbackTemplateService interviewFeedbackTemplateService;
	
	
	@Autowired
	ProposeTimeService proposeTimeService;
	
	@Override
	public RequirementInterviewHistory storeRequirementInterviewHistory(
			RequirementInterviewHistory requirementInterviewHistory) throws Exception {
		requirementInterviewHistory.setUpdatedDate(commonUtil.currentDate());
		return requirementInterviewHistoryDao.storeRequirementInterviewHistory(requirementInterviewHistory);
	}
	
	@Override
	public RequirementInterviewHistory getRequirementInterviewHistoryById(Long Id) {
		return requirementInterviewHistoryDao.getRequirementInterviewHistoryById(Id);
	}
	
	@Override
	public RequirementInterviewHistory getRequirementInterviewHistoryByInterviewId(Long interviewId) throws Exception {
		return requirementInterviewHistoryDao.getRequirementInterviewHistoryByInterviewId(interviewId);
	}
	
	
	@Override
	public List<RequirementInterviewHistory> getRequirementInternalAndIsClientInterviewsBySubmissionIdAndOrgLocationsId(Long submissionId,OrgLocations orgLocation) throws Exception {
		logger.debug("In RequirementInterviewServiceImpl class getRequirementInternalAndIsClientInterviewsBySubmissionIdAndOrgLocationsId method...");
		Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		List<RequirementInterviewHistory> ineterviewList = new ArrayList<RequirementInterviewHistory>();
		List<RequirementInterviewHistory> interviewslist  = requirementInterviewHistoryDao.getRequirementInternalAndIsClientInterviewsBySubmissionIdAndOrgLocationsId(submissionId,orgLocation.getId());
		//SimpleDateFormat simpleDate=orgLocation.getLocFullFormDateFormatter();
		if (interviewslist != null && !interviewslist.isEmpty() ) {
			for (RequirementInterviewHistory interviewHistory : interviewslist) {
				
				RequirementInterview requirementInterview = requirementInterviewDao.getRequirementInterviewById(interviewHistory.getInterviewId().getId().toString());
				if(null != requirementInterview.getProposeTime()){
					interviewHistory.setProposeTimeStatus(requirementInterview.getProposeTime());
				}
				interviewHistory.setSubmissionStatus(interviewHistory.getSubmissionId().getSubmissionStatus().toString());
				if(requirementInterview.getRemainedBefore() != null && !requirementInterview.getRemainedBefore().isEmpty()){
					Integer hours = (Integer.parseInt(requirementInterview.getRemainedBefore()) / 60);
					Integer minutes = (Integer.parseInt(requirementInterview.getRemainedBefore()) % 60);
					StringBuilder interviewStringBuilder=new StringBuilder();
					interviewHistory.setRemainderTime(interviewStringBuilder.append(hours).append(" : ").append( minutes).append(" minutes").toString());
				}
				 
			    if(requirementInterview.getDateOfInterview() != null){

					String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getStartTime());
					String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE  ,timezone.getTimeZoneId(), currentTimeStr);
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
					currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),converteDate);
					
					String interviewDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE  ,requirementInterview.getTimezone().getTimeZoneId(), currentTimeStr);
					Date interviewConverteDate = TZ_DF_TS.parse(interviewDateInTimezone);
					interviewDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),interviewConverteDate);
					interviewHistory.setDateOfInterviewStr(currentDateInTimezone);
					interviewHistory.setInterviewTimezoneDateStr(interviewDateInTimezone);
					
			    	
			    	//interviewHistory.setDateOfInterviewStr(Constants.DMY.format(requirementInterview.getDateOfInterview()));
			    }
			    
			    if(requirementInterview.getStartTime() != null){
			    	DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
					String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getStartTime());  
					String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
					String convertedDateStr = DF_DMY.format(convertedDate);
					 if(null == requirementInterview.getEndTime()){
						 interviewHistory.setStartTimeStr(convertedDateStr+"("+timezone.getTimeZoneId()+")");
					 }
					 else{
						 interviewHistory.setStartTimeStr(convertedDateStr);
					 }
					 
					 String interviewStartTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,requirementInterview.getTimezone().getTimeZoneId(), currentTimeStr);
					 DateFormat TZ_DF_TSP = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					 Date interviewConverteDate = TZ_DF_TSP.parse(interviewStartTimeUTC);
						String interviewConvertedDateStr = DF_DMY.format(interviewConverteDate);
						interviewHistory.setInterviewTimeZoneStartTimeStr(interviewConvertedDateStr+"("+requirementInterview.getTimezone().getTimeZoneId()+")");
						if(requirementInterview.getStartTime().compareTo(new Date())<0){
								interviewHistory.setFeedbackStartTime("feedbackStartTime");
							}
			    }
			    
			    if(requirementInterview.getEndTime() != null){
			    	DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
					String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getEndTime());  
					String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
					String convertedDateStr = DF_DMY.format(convertedDate);  
					interviewHistory.setEndTimeStr(convertedDateStr+"("+timezone.getTimeZoneId()+")");
					
					 String interviewStartTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,requirementInterview.getTimezone().getTimeZoneId(), currentTimeStr);
					 DateFormat TZ_DF_TSP = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					 Date interviewConverteDate = TZ_DF_TSP.parse(interviewStartTimeUTC);
						String interviewConvertedDateStr = DF_DMY.format(interviewConverteDate);
						interviewHistory.setInterviewTimeZoneEndTimeStr(interviewConvertedDateStr);
						if(requirementInterview.getEndTime().compareTo(new Date())<0){
				    		 interviewHistory.setFeedbackEndTime("feedbackEndTime");
						}
			    }
			    if(requirementInterview.getInterviewTemplateId() != null){
			    	interviewHistory.setInterviewTemplateid(requirementInterview.getInterviewTemplateId().getId());
			    	interviewHistory.setInterviewTemplateUUId(requirementInterview.getInterviewTemplateId().getUuid());
			    }
			    if(requirementInterview.getTimezone() != null){
			    	interviewHistory.setTimezone(requirementInterview.getTimezone().getTimeZoneId());
			    }
			    
			    //This condition  is to enable the feedback  and reschedule icon for Own Interviews
			    if(requirementInterview.getSubmissionId() != null){
			    	interviewHistory.setInterviewSubmissionId(requirementInterview.getSubmissionId().getId().toString());
			    }
			    
			    if(requirementInterview.getRescheduleComment() != null){
			    	interviewHistory.setRescheduleComment(requirementInterview.getRescheduleComment());
			    }
			    
			    if(interviewHistory.getSubmissionId().getVendorLocationId() != null){
			    	interviewHistory.setIsNotifyToVendorRequired(Constants.YES);
				}else{
					interviewHistory.setIsNotifyToVendorRequired(Constants.NO);
				}
			    
			    if(requirementInterview.getSubmissionId().getRequirement().getClientId() != null){
					if(requirementInterview.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Client")){
						interviewHistory.setClientName(requirementInterview.getSubmissionId().getRequirement().getClientId().getResponderLocationId().getOrgLocationName());
					}
					if(requirementInterview.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Vendor")){
						interviewHistory.setClientName(requirementInterview.getSubmissionId().getRequirement().getClientId().getLocationId().getOrgLocationName());
					}
				}else{
					RequirementSubmission requirementSubmission = requirementSubmissionService.getSubmissionsBySourceSubmissionId(requirementInterview.getSubmissionId().getId(),orgLocation.getId());
				    if(requirementSubmission != null){
				    	interviewHistory.setClientName(requirementSubmission.getOrgLocations().getOrgLocationName());
				    }else if(requirementInterview.getSubmissionId().getSourceSubmission() != null && requirementInterview.getSubmissionId().getSourceSubmission().getId() != null){
				    	RequirementSubmission parentRequirementSubmission = requirementSubmissionService.getSubmissionsBySourceSubmissionId(requirementInterview.getSubmissionId().getSourceSubmission().getId(),orgLocation.getId());
				    	 if(parentRequirementSubmission != null){
						     interviewHistory.setClientName(parentRequirementSubmission.getOrgLocations().getOrgLocationName());
				    	 }
				    }
				}
			    
			    List<ProposeTime> proposeTime=proposeTimeService.getProposeTimeByInterViewId(interviewHistory.getInterviewId().getId());
				for(ProposeTime pst:proposeTime){
					if(null != pst.getProposeDate()){
						pst.setProposeDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),pst.getProposeDate()));	
						pst.setProposeTime(pst.getProposeTime());
					}
				}
				interviewHistory.setProposeTimeList(proposeTime);
			    
			    String InterviewersList = "";
				Set<RequirementInterviewer> Interviewers = requirementInterview.getRequirementInterviewers();
				int i = 0;
				for(RequirementInterviewer interviewer: Interviewers){
					if(i <= 50){
						 StringBuilder interviewerBuilder=new StringBuilder();
						 if(interviewer.getInterviewerUser() != null) {
							 InterviewersList = interviewerBuilder.append(InterviewersList).append(interviewer.getInterviewerUser().getFirstName()).append(" ").append(interviewer.getInterviewerUser().getLastName()).toString();
						 }else {
							 InterviewersList = interviewerBuilder.append(InterviewersList).append(interviewer.getExternalInterviewerName()).toString();
						 }
						
						if(i != Interviewers.size()-1){
							 StringBuilder strBuilder=new StringBuilder();
							InterviewersList = strBuilder.append(InterviewersList).append(",").toString();
						}
					}
					i++;
				}
				if(requirementInterview.getInterviewType() != null){
					if(requirementInterview.getInterviewType().equalsIgnoreCase("Other")){
						interviewHistory.setInterviewType(requirementInterview.getCustomInterviewType());
					}
					else{
						interviewHistory.setInterviewType(requirementInterview.getInterviewType());
					}
					
				}
				interviewHistory.setInterviewersList(InterviewersList);
				ineterviewList.add(interviewHistory);
			}
			
			
		}
		return ineterviewList;
	}
	
	@Override
	public List<RequirementInterviewHistory> getAllInterviewListByCurrentDate(String currentTime, String triggerTime) {
		return requirementInterviewHistoryDao.getAllInterviewListByCurrentDate(currentTime,  triggerTime);
	}
	

	@Override
	public List<RequirementInterviewHistory> getAllInterviewAssignedToLoginUser(OrgLocations loginOrgLocations, String dateRange, String currentTime, String userType, Long userId, String userCode, Date startUTCDate, Date endUTCDate,Timezone timezone,User user)
			throws Exception {
		
		List<RequirementInterviewHistory> requirementInterviewList = requirementInterviewHistoryDao.getAllInterviewAssignedToLoginUser(loginOrgLocations.getId(), dateRange, currentTime, userType, userId, userCode,startUTCDate,endUTCDate);
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		//SimpleDateFormat simpleDateFormat = loginOrgLocations.getLocFullFormDateFormatter();
		for(RequirementInterviewHistory interviewHistory : requirementInterviewList){
			String candidateFirstName = "";
			String candidateLastName = "";
			String requirementTitle = "";
			StringBuilder candidateName = new StringBuilder();
			if(interviewHistory.getSubmissionId() != null && interviewHistory.getSubmissionId().getCandidate() != null){
				if(interviewHistory.getSubmissionId().getCandidate().getFirstName() != null){
					
					candidateFirstName = interviewHistory.getSubmissionId().getCandidate().getFirstName();
				}
				if(interviewHistory.getSubmissionId().getCandidate().getLastName() != null){
					
					candidateLastName = interviewHistory.getSubmissionId().getCandidate().getLastName();
				}
			}
			
			if(interviewHistory.getSubmissionId().getRequirement() != null) {
				if(interviewHistory.getSubmissionId() != null && interviewHistory.getSubmissionId().getRequirement().getJobTitle() != null){
					requirementTitle = interviewHistory.getSubmissionId().getRequirement().getJobTitle();
				}
				if(interviewHistory.getSubmissionId() != null && interviewHistory.getSubmissionId().getRequirement().getRequirementUUID() != null){
					interviewHistory.setRequirementUUID(interviewHistory.getSubmissionId().getRequirement().getRequirementUUID());
				}
				
				if(null != interviewHistory.getInterviewId().getInterviewTemplateId()){
					interviewHistory.setInterviewTemplateUUId(interviewHistory.getInterviewId().getInterviewTemplateId().getUuid());
				}
			}
			
			
			
	/*		if(Constants.ADMIN.equalsIgnoreCase(userType)){
				List<RequirementInterviewer> reqiremList = requirementInterviewerService.getInterviwersListByInterviewId(interviewHistory.getInterviewId().getId());
				List<RequirementInterviewer> intrlist = new ArrayList<RequirementInterviewer>();
				for(RequirementInterviewer er:reqiremList){
					InterviewFeedbackTemplate ift = interviewFeedbackTemplateService.getInterviewFeedbackTemplateById(er.getId());
					if(ift != null){
						interviewHistory.setInterviewerId(ift.getRequirementInterviewerId().getId());
						if(ift.getRequirementInterviewerId() != null){
							intrlist.add(ift.getRequirementInterviewerId());	
						}
							
					}
				}
				interviewHistory.setRequirementInterviewerList(intrlist);
			}else{*/
				RequirementInterviewer requirementInterviewer =  requirementInterviewerService.getInterviwerByInterviewIdAndUserId(interviewHistory.getInterviewId().getId(), user.getId());
				if(null != requirementInterviewer){
					interviewHistory.setInterviewerUUID(requirementInterviewer.getUuid());
				} 
			/*}*/
			
			interviewHistory.setRequirementIntervieweId(interviewHistory.getInterviewId().getId());
			interviewHistory.setCandidateName(candidateName.append(candidateFirstName).append(" ").append(candidateLastName).toString());
			
			interviewHistory.setCandidateEmail(interviewHistory.getSubmissionId().getCandidate().getEmail());
			
			interviewHistory.setCandidatePhoneNumber(interviewHistory.getSubmissionId().getCandidate().getMobilePhone());
			
			interviewHistory.setRequirementTitle(requirementTitle);
			
			if(interviewHistory.getSubmissionId().getRequirement() != null) {
				interviewHistory.setRequirementId(interviewHistory.getSubmissionId().getRequirement().getId().toString());
			}
			
			if(userId != null) {
				List<RequirementInterviewer> interviewers = requirementInterviewerService.getInterviwersListByInterviewIdAndUserID(interviewHistory.getInterviewId().getId(),userId);
				for(RequirementInterviewer interviewer:interviewers){
					InterviewFeedbackTemplate feedbackTemplate = interviewFeedbackTemplateService.getInterviewFeedbackTemplateById(interviewer.getId()) ;
					if(null != feedbackTemplate){
						interviewHistory.setFeedbackDate(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),feedbackTemplate.getCreatedDate()));	
					}
				}
			}
			
			
			//changed as per the timezone
			DateFormat standardFormat = new SimpleDateFormat( "MMM dd, yyyy hh:mm aa");
			DateFormat eventstartTimrFroDashBoard = new SimpleDateFormat( "hh:mm aa");
			if(null != timezone.getTimeZoneId() && null != interviewHistory.getInterviewId() && null != interviewHistory.getInterviewId().getId() && null != interviewHistory.getInterviewId().getStartTime()){
				String currentTimeStr = Constants.TZ_DF.format(interviewHistory.getInterviewId().getStartTime());  
				String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				currentDateInTimezone = standardFormat.format(converteDate);
				String eventstartTimrFroDashBoardStr = eventstartTimrFroDashBoard.format(converteDate);
				interviewHistory.setInterviewStartDateTimeStr(currentDateInTimezone);
				interviewHistory.setStartTimeStr(eventstartTimrFroDashBoardStr);
			}
			
			
			if(null != timezone.getTimeZoneId() && null != interviewHistory.getInterviewId() && null != interviewHistory.getInterviewId().getId() && null != interviewHistory.getInterviewId().getEndTime()){
				String currentTimeStr = Constants.TZ_DF.format(interviewHistory.getInterviewId().getEndTime());  
				String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE  ,timezone.getTimeZoneId(), currentTimeStr);
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				String eventstartTimrFroDashBoardStr = eventstartTimrFroDashBoard.format(converteDate);
				interviewHistory.setEndTimeStr(eventstartTimrFroDashBoardStr);
			}
			else{
				interviewHistory.setEndTimeStr("--");
			}
			
			 if(interviewHistory.getSubmissionId().getRequirement().getClientId() != null){
					if(interviewHistory.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Client")){
						interviewHistory.setClientName(interviewHistory.getSubmissionId().getRequirement().getClientId().getResponderLocationId().getOrgLocationName());
					}
					if(interviewHistory.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Vendor")){
						interviewHistory.setClientName(interviewHistory.getSubmissionId().getRequirement().getClientId().getLocationId().getOrgLocationName());
					}
				}else{
					RequirementSubmission requirementSubmission = requirementSubmissionService.getSubmissionsBySourceSubmissionId(interviewHistory.getSubmissionId().getId(),loginOrgLocations.getId());
				    if(requirementSubmission != null){
				    	interviewHistory.setClientName(requirementSubmission.getOrgLocations().getOrgLocationName());
				    }else if(interviewHistory.getSubmissionId().getSourceSubmission() != null && interviewHistory.getSubmissionId().getSourceSubmission().getId() != null){
				    	RequirementSubmission parentRequirementSubmission = requirementSubmissionService.getSubmissionsBySourceSubmissionId(interviewHistory.getSubmissionId().getSourceSubmission().getId(),loginOrgLocations.getId());
				    	 if(parentRequirementSubmission != null){
						     interviewHistory.setClientName(parentRequirementSubmission.getOrgLocations().getOrgLocationName());
				    	 }
				    }
				}
			
		}
		
		
		return requirementInterviewList;

			}

	@Override
	public List<RequirementInterviewHistory> getClientInterviewsHistoryBySubmissionIdAndOrgLocationsId(Long submissionId, Long orgLocationsId) throws Exception {
		return requirementInterviewHistoryDao.getClientInterviewsHistoryBySubmissionIdAndOrgLocationsId(submissionId,  orgLocationsId);
	}

	@Override
	public Integer getRequirementInternalAndIsClientInterviewsCountBySubmissionIdAndOrgLocationsId(Long submissionId,
			Long orgLocationsId) throws Exception {
		return requirementInterviewHistoryDao.getRequirementInternalAndIsClientInterviewsCountBySubmissionIdAndOrgLocationsId(submissionId, orgLocationsId);
	}

	@Override
	public List<RequirementInterviewHistory> getAllProfessinalInterviews(String dateRange,Long candidateId,String currentTime, Date startUTCDate, Date endUTCDate,Timezone timezone)
			throws Exception {
		List<RequirementInterviewHistory> list = requirementInterviewHistoryDao.getAllProfessinalInterviews(dateRange,candidateId,currentTime,startUTCDate,endUTCDate,timezone);
		return list;

			}

	@Override
	public List<RequirementInterviewHistory> getAllRecruiterSubmittedInterviews(String dateRange,Long userId,String currentTime, Date startUTCDate, Date endUTCDate,Timezone timezone) throws Exception {
		List<RequirementInterviewHistory> list = requirementInterviewHistoryDao.getAllRecruiterSubmittedInterviews(dateRange,userId,currentTime,startUTCDate,endUTCDate,timezone);
		return list;
	}

	@Override
	public List<RequirementInterviewHistory> getCrowdSorurcingInterviews(Long submissionId,OrgLocations orgLocations) throws Exception {
		logger.debug("In RequirementInterviewServiceImpl class getRequirementInternalAndIsClientInterviewsBySubmissionIdAndOrgLocationsId method...");
		Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		List<RequirementInterviewHistory> ineterviewList = new ArrayList<RequirementInterviewHistory>();
		List<RequirementInterviewHistory> interviewslist  = requirementInterviewHistoryDao.getCrowdSorurcingInterviews(submissionId);
//		SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter(); 
		if (interviewslist != null && !interviewslist.isEmpty() ) {
			for (RequirementInterviewHistory interviewHistory : interviewslist) {
				
				RequirementInterview requirementInterview = requirementInterviewDao.getRequirementInterviewById(interviewHistory.getInterviewId().getId().toString());
				if(null !=requirementInterview.getProposeTime()){
			    	interviewHistory.setProposeTimeStatus(requirementInterview.getProposeTime());	
			    }
				interviewHistory.setSubmissionStatus(interviewHistory.getSubmissionId().getSubmissionStatus().toString());
				if(requirementInterview.getRemainedBefore() != null && !requirementInterview.getRemainedBefore().isEmpty()){
					Integer hours = (Integer.parseInt(requirementInterview.getRemainedBefore()) / 60);
					Integer minutes = (Integer.parseInt(requirementInterview.getRemainedBefore()) % 60);
					StringBuilder interviewStringBuilder=new StringBuilder();
					interviewHistory.setRemainderTime(interviewStringBuilder.append(hours).append(" : ").append( minutes).append(" minutes").toString());
				}
				
			    if(requirementInterview.getDateOfInterview() != null){

					String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getStartTime());
					String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE  ,timezone.getTimeZoneId(), currentTimeStr);
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
					currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),converteDate);
					
					String interviewDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE  ,requirementInterview.getTimezone().getTimeZoneId(), currentTimeStr);
					Date interviewConverteDate = TZ_DF_TS.parse(interviewDateInTimezone);
					interviewDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),interviewConverteDate);
					interviewHistory.setDateOfInterviewStr(currentDateInTimezone);
					interviewHistory.setInterviewTimezoneDateStr(interviewDateInTimezone);
					
			    	
			    }
			    
			    if(requirementInterview.getStartTime() != null){
			    	DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
					String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getStartTime());  
					String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
					String convertedDateStr = DF_DMY.format(convertedDate);
					 if(null == requirementInterview.getEndTime()){
						 interviewHistory.setStartTimeStr(convertedDateStr+"("+timezone.getTimeZoneId()+")");
					 }
					 else{
						 interviewHistory.setStartTimeStr(convertedDateStr);
					 }
					 
					 String interviewStartTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,requirementInterview.getTimezone().getTimeZoneId(), currentTimeStr);
					 DateFormat TZ_DF_TSP = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					 Date interviewConverteDate = TZ_DF_TSP.parse(interviewStartTimeUTC);
						String interviewConvertedDateStr = DF_DMY.format(interviewConverteDate);
						interviewHistory.setInterviewTimeZoneStartTimeStr(interviewConvertedDateStr+"("+requirementInterview.getTimezone().getTimeZoneId()+")");
						if(requirementInterview.getStartTime().compareTo(new Date())<0){
								interviewHistory.setFeedbackStartTime("feedbackStartTime");
							}
			    }
			    
			    if(requirementInterview.getEndTime() != null){
			    	DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
					String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getEndTime());  
					String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
					String convertedDateStr = DF_DMY.format(convertedDate);  
					interviewHistory.setEndTimeStr(convertedDateStr+"("+timezone.getTimeZoneId()+")");
					
					 String interviewStartTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,requirementInterview.getTimezone().getTimeZoneId(), currentTimeStr);
					 DateFormat TZ_DF_TSP = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					 Date interviewConverteDate = TZ_DF_TSP.parse(interviewStartTimeUTC);
						String interviewConvertedDateStr = DF_DMY.format(interviewConverteDate);
						interviewHistory.setInterviewTimeZoneEndTimeStr(interviewConvertedDateStr);
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
						interviewHistory.setClientName(requirementInterview.getSubmissionId().getRequirement().getClientId().getResponderLocationId().getOrgLocationName());
					}
					if(requirementInterview.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Vendor")){
						interviewHistory.setClientName(requirementInterview.getSubmissionId().getRequirement().getClientId().getLocationId().getOrgLocationName());
					}
				}else{
					RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(requirementInterview.getSubmissionId().getId());
				    if(requirementSubmission != null){
				    	interviewHistory.setClientName(requirementSubmission.getOrgLocations().getOrgLocationName());
				    }else if(requirementInterview.getSubmissionId().getSourceSubmission() != null && requirementInterview.getSubmissionId().getSourceSubmission().getId() != null){
				    	RequirementSubmission parentRequirementSubmission = requirementSubmissionService.getRequirementSubmissionById(requirementInterview.getSubmissionId().getSourceSubmission().getId());
				    	 if(parentRequirementSubmission != null){
						     interviewHistory.setClientName(parentRequirementSubmission.getOrgLocations().getOrgLocationName());
				    	 }
				    }
				}
			    
			    
			    String InterviewersList = "";
				Set<RequirementInterviewer> Interviewers = requirementInterview.getRequirementInterviewers();
				int i = 0;
				for(RequirementInterviewer interviewer: Interviewers){
					if(i <= 50){
						 StringBuilder interviewerBuilder=new StringBuilder();
						InterviewersList = interviewerBuilder.append(InterviewersList).append(interviewer.getInterviewerUser().getFirstName()).append(" ").append(interviewer.getInterviewerUser().getLastName()).toString();
						if(i != Interviewers.size()-1){
							 StringBuilder strBuilder=new StringBuilder();
							InterviewersList = strBuilder.append(InterviewersList).append(",").toString();
						}
					}
					i++;
				}
				interviewHistory.setInterviewersList(InterviewersList);
				ineterviewList.add(interviewHistory);
			}
		}
		return ineterviewList;
	}

	
	@Override
	public Long getCountOfAllInterviewAssignedToLoginUser(OrgLocations loginOrgLocations, String userType, Long userId, String userCode) throws Exception {
		return requirementInterviewHistoryDao.getCountOfAllInterviewAssignedToLoginUser(loginOrgLocations.getId(), userType, userId, userCode);
	}
}
