package com.bis.sourcelead.rest.reportsRestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.reports.service.JobsPipelineReportService;
import com.bis.sourcelead.reportsDTO.JobFilterDTO;
import com.bis.sourcelead.reportsDTO.JobPipelineDTO;
import com.bis.sourcelead.service.RequirementReportService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class JobPipelineReoprtsRestController {
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	RequirementReportService requirementReportService;
	
	@Autowired
	JobsPipelineReportService jobsPipelineReportService;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Autowired
	ReportsUtil reportsUtil;
	
	
	@SuppressWarnings("unchecked")
	@Async
	@RequestMapping(value = "/getAllJobsPipelineReoportsList", method = RequestMethod.POST)
	@ResponseBody
	JobPipelineDTO getAllJobsPipelineReoportsList(JobFilterDTO jobFilterDTO, HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

	       List<Long> jobIdList = new ArrayList<>();
			if(StringUtils.isNotBlank(jobFilterDTO.getJobIds())) {
				String[] jobIds = jobFilterDTO.getJobIds().split(",");
				for(int i=0; i<jobIds.length; i++) {
					jobIdList.add(Long.valueOf(jobIds[i]));
				}
			}else {
				jobIdList = (List<Long>) SourceLeadSessionManager.getSessionAttribute(request, "requirementIdsList");
			}
			
			Date[] searchDates = null;
			
			if(StringUtils.isNotBlank(jobFilterDTO.getDateSelection()) && !jobFilterDTO.getDateSelection().equalsIgnoreCase("custom")) {
				searchDates = reportsUtil.getJobFilterDate(jobFilterDTO.getDateSelection());
			}else {
				
				Date fromDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), jobFilterDTO.getFromDate());
	    	    Date toDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), jobFilterDTO.getToDate());
	    	    searchDates = new Date[2];
	    	    searchDates[0] = fromDate;
	    	    searchDates[1] = toDate;
			}
	 
	    	int applicantsSum = 0;
			int requestSentSum = 0;
			int shortlistedSum = 0;
			int submitToClientSum = 0;
			int interviewsSum = 0;
			int offeredSum = 0;
			int offeracceptedSum = 0;
			int rejectedSum = 0;
			int backoutSum = 0;
			Map<Integer,Long> applicantMap = new HashMap<>();
			Map<Integer,Long> shortlistedMap = new HashMap<>();
			Map<Integer,Long> interviewMap = new HashMap<>();
			Map<Integer,Long> offerMadeMap = new HashMap<>();
			Map<Integer,Long> offerAcceptedMap = new HashMap<>();
			Map<Integer,Long> submitToClientMap = new HashMap<>();
			Map<Integer,Long> backOutMap = new HashMap<>();
			
			double applicantDuration    = 0l;
			double shortlistedDuration    = 0l;
			double interviewDuration     = 0l;
			double offerMadeDuration      = 0l;
			double offerAcceptedDuration = 0l;
			double submitToClientDuration = 0l;
			double backOutDuration        = 0l;
			JobPipelineDTO report = new JobPipelineDTO();
			
			Integer openJobcount    = jobsPipelineReportService.requirementListStatusCountByJobIdsAndDates(jobIdList, searchDates, "OPEN");
			Integer onholdJobcount  = jobsPipelineReportService.requirementListStatusCountByJobIdsAndDates(jobIdList, searchDates, "ONHOLD");
			Integer closedJobcount  = jobsPipelineReportService.requirementListStatusCountByJobIdsAndDates(jobIdList, searchDates, "CLOSED");
			Integer cancelJobcount  = jobsPipelineReportService.requirementListStatusCountByJobIdsAndDates(jobIdList, searchDates, "CANCEL");
			
			int count = 1;
	    	List<RequirementSubmission> reqSubmissionList = jobsPipelineReportService.getAllJobsReportCountByOrglocationIdAndLoginUserCodeAndStartAndEndDate(jobIdList, searchDates);
	    	if(null != reqSubmissionList && reqSubmissionList.size() > 0){
		    	for(RequirementSubmission reqSub : reqSubmissionList){
					int applicantsCount = 0;
					int requestSentCount = 0;
					int shortlistedCount = 0;
			 		int submitToClientCount = 0;
					int interviewsCount = 0;
					int offeredCount = 0;
					int offeracceptedCount = 0;
					int rejectedCount = 0;
					int backoutCount = 0;
					List<RequirementSubmissionHistory> requirementSubmissionHistoryList = jobsPipelineReportService.getAllJobsReportsCountsBySubmmisonId(reqSub.getId());
					if(null != requirementSubmissionHistoryList && requirementSubmissionHistoryList.size() >0){
						Date requirementCreatedDate = reqSub.getRequirement().getCreatedDate();
						Date applicantCreatedDate = null;
						Date shortlistCreatedDate = null;
						Date interviewCreatedDate = null;
						Date offerMadeCreatedDate = null;
						Date offerAcceptCreatedDate = null;
						Date submitToClientCreatedDate = null;
						Date backOutCreatedDate = null;
						Map<String,Date> createdDatesMap = new LinkedHashMap<>();
						for(RequirementSubmissionHistory rsh : requirementSubmissionHistoryList){
							createdDatesMap.put(rsh.getToStage(), rsh.getCreatedDate());
						}
						for(RequirementSubmissionHistory rsh : requirementSubmissionHistoryList){
							
							if("submission".equalsIgnoreCase(rsh.getToStage())){
								applicantsCount++;
								applicantCreatedDate= rsh.getCreatedDate();
							} if("shortList".equalsIgnoreCase(rsh.getToStage()) || (rsh.getFromStage().equalsIgnoreCase("submission") && rsh.getToStage().equalsIgnoreCase("offermade"))|| (rsh.getFromStage().equalsIgnoreCase("submission") && rsh.getToStage().equalsIgnoreCase("interview"))){
								shortlistedCount++;
								if((rsh.getFromStage().equalsIgnoreCase("submission") && rsh.getToStage().equalsIgnoreCase("offermade"))|| (rsh.getFromStage().equalsIgnoreCase("submission") && rsh.getToStage().equalsIgnoreCase("interview"))) {
									shortlistCreatedDate = createdDatesMap.get("SUBMISSION");
								}else {
									shortlistCreatedDate = rsh.getCreatedDate();
								}
								
							} if("interview".equalsIgnoreCase(rsh.getToStage()) || (rsh.getFromStage().equalsIgnoreCase("submission") && rsh.getToStage().equalsIgnoreCase("offermade")) || (rsh.getFromStage().equalsIgnoreCase("shortList") && rsh.getToStage().equalsIgnoreCase("offermade"))){
								interviewsCount++;
								if(rsh.getFromStage().equalsIgnoreCase("submission") && rsh.getToStage().equalsIgnoreCase("offermade")) {
									interviewCreatedDate = createdDatesMap.get("SUBMISSION");
								}else if(rsh.getFromStage().equalsIgnoreCase("shortList") && rsh.getToStage().equalsIgnoreCase("offermade")) {
									interviewCreatedDate = createdDatesMap.get("SHORTLIST");
								}else {
									interviewCreatedDate = rsh.getCreatedDate();
								}
								
							} if("offermade".equalsIgnoreCase(rsh.getToStage())){
								offeredCount++;
								offerMadeCreatedDate = rsh.getCreatedDate();
							} if("request_sent".equalsIgnoreCase(rsh.getToStage())){
								requestSentCount++;
							} if("submit_to_client".equalsIgnoreCase(rsh.getToStage())){
								submitToClientCount++;
								submitToClientCreatedDate = rsh.getCreatedDate();
							} if("rejected".equalsIgnoreCase(rsh.getToStage())){
								rejectedCount++;
							} if("backout".equalsIgnoreCase(rsh.getToStage())){
								backoutCount++;
								backOutCreatedDate = rsh.getCreatedDate();
							} if("offeraccepted".equalsIgnoreCase(rsh.getToStage())){
								offeracceptedCount++;
								offerAcceptCreatedDate = rsh.getCreatedDate();
							}
						}
						
						if(applicantCreatedDate != null && requirementCreatedDate != null) {
							applicantMap.put(count, CalendarUtil.getNumberOfDays(requirementCreatedDate,applicantCreatedDate));
						}
						
						if(shortlistCreatedDate != null && applicantCreatedDate != null) {
							shortlistedMap.put(count, CalendarUtil.getNumberOfDays(applicantCreatedDate,shortlistCreatedDate));
						}
						if(interviewCreatedDate != null && shortlistCreatedDate != null) {
							interviewMap.put(count, CalendarUtil.getNumberOfDays(shortlistCreatedDate,interviewCreatedDate));
						}
						if(submitToClientCreatedDate != null && applicantCreatedDate != null) {
							submitToClientMap.put(count, CalendarUtil.getNumberOfDays(applicantCreatedDate,submitToClientCreatedDate));
						}
						if(offerMadeCreatedDate != null && interviewCreatedDate != null) {
							offerMadeMap.put(count, CalendarUtil.getNumberOfDays(interviewCreatedDate, offerMadeCreatedDate));
						}
						if(backOutCreatedDate != null && applicantCreatedDate != null) {
							backOutMap.put(count, CalendarUtil.getNumberOfDays(applicantCreatedDate,backOutCreatedDate));
						}
						if(offerAcceptCreatedDate != null && offerMadeCreatedDate != null) {
							offerAcceptedMap.put(count, CalendarUtil.getNumberOfDays(offerMadeCreatedDate,offerAcceptCreatedDate));
						}
						
						
						 applicantsSum = applicantsSum+applicantsCount;
						 requestSentSum = requestSentSum+requestSentCount;
						 shortlistedSum = shortlistedSum+shortlistedCount;
						 submitToClientSum = submitToClientSum+submitToClientCount;
						 interviewsSum = interviewsSum+interviewsCount;
						 offeredSum = offeredSum+offeredCount;
						 offeracceptedSum = offeracceptedSum+offeracceptedCount;
						 rejectedSum = rejectedSum+rejectedCount;
						 backoutSum = backoutSum + backoutCount;
					}
					count++;
				}
		    	if(applicantMap != null && !applicantMap.isEmpty()) {
		    		Long totalDays = 0l;
		    		for(Integer flag :applicantMap.keySet()) {
		    			totalDays = totalDays+applicantMap.get(flag);
		    		}
		    		applicantDuration = Math.round((double)totalDays/(double)(applicantMap.size()));
		    	}
		    	
		    	if(shortlistedMap != null && !shortlistedMap.isEmpty()) {
		    		Long totalDays = 0l;
		    		for(Integer flag :shortlistedMap.keySet()) {
		    			totalDays = totalDays+shortlistedMap.get(flag);
		    		}
		    		shortlistedDuration = Math.round((double)totalDays/(double)(shortlistedMap.size()));
		    	}
		    	if(interviewMap != null && !interviewMap.isEmpty()) {
		    		Long totalDays = 0l;
		    		for(Integer flag :interviewMap.keySet()) {
		    			totalDays = totalDays+interviewMap.get(flag);
		    		}
		    		interviewDuration = totalDays/(interviewMap.size());
		    	}
		    	if(offerMadeMap != null && !offerMadeMap.isEmpty()) {
		    		Long totalDays = 0l;
		    		for(Integer flag :offerMadeMap.keySet()) {
		    			totalDays = totalDays+offerMadeMap.get(flag);
		    		}
		    		offerMadeDuration = Math.round((double)totalDays/(double)(offerMadeMap.size()));
		    	}
		    	if(offerAcceptedMap != null && !offerAcceptedMap.isEmpty()) {
		    		Long totalDays = 0l;
		    		for(Integer flag :offerAcceptedMap.keySet()) {
		    			totalDays = totalDays+offerAcceptedMap.get(flag);
		    		}
		    		offerAcceptedDuration = (double)totalDays/(double)(offerAcceptedMap.size());
		    	}

		    	if(submitToClientMap != null && !submitToClientMap.isEmpty()) {
		    		Long totalDays = 0l;
		    		for(Integer flag :submitToClientMap.keySet()) {
		    			totalDays = totalDays+submitToClientMap.get(flag);
		    		}
		    		submitToClientDuration = Math.round((double)totalDays/(double)(submitToClientMap.size()));
		    	}
		    	
		    	if(backOutMap != null && !backOutMap.isEmpty()) {
		    		Long totalDays = 0l;
		    		for(Integer flag :backOutMap.keySet()) {
		    			totalDays = totalDays+backOutMap.get(flag);
		    		}
		    		backOutDuration = Math.round((double)totalDays/(double)(backOutMap.size()));
		    	}
		    	
		    	//days
		    	report.setApplicantsDays(applicantDuration);
		    	report.setShortlistedDays(shortlistedDuration);
		    	report.setInterviewsDays(interviewDuration);
		    	report.setOfferedDays(offerMadeDuration);
		    	report.setOfferedAcceptedDays(offerAcceptedDuration);
		    	report.setSubmitToClientDays(submitToClientDuration);
		    	report.setBackoutDays(backOutDuration);
		    	
		    	
		    	//count 
		    	report.setApplicantsCount(applicantsSum);
		    	report.setShortlistedCount(shortlistedSum);
		    	report.setSubmitToClientCount(submitToClientSum);
		    	report.setInterviewsCount(interviewsSum);
		    	report.setOfferedAcceptedCount(offeracceptedSum);
		    	report.setOfferedCount(offeredSum);
		    	report.setRejectedCount(rejectedSum);
		    	report.setBackoutCount(backoutSum);
		    	
		    	
		    	//Moveforward percentage
		    	report.setShortlistedMove(Math.round((double)shortlistedSum/(double)applicantsSum));
		    	report.setInterviewsMove(Math.round((double)interviewsSum/(double)shortlistedSum));
		    	report.setOfferedMove(Math.round((double)offeredSum/(double)interviewsSum));
		    	report.setOfferedAcceptedMove(Math.round((double)offeredSum/(double)offeracceptedSum));
		    	report.setBackoutMove(Math.round((double)offeracceptedSum/(double)backoutSum));
		    	
		    	 
		    	//jobs count
		    	report.setAllJobsCount(openJobcount + onholdJobcount + closedJobcount + cancelJobcount);
		    	report.setOpenJobsCount(openJobcount);
		    	report.setOnHoldJobsCount(onholdJobcount);
		    	report.setClosedJonbsCount(closedJobcount);
		    	report.setCancelledjobsCount(cancelJobcount);
		    	
	    	
	    	}
			return report;
		 
	}

}
