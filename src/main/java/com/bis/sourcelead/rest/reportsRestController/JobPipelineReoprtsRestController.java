package com.bis.sourcelead.rest.reportsRestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.reports.service.JobsPipelineReportService;
import com.bis.sourcelead.reportsDTO.JobFilterDTO;
import com.bis.sourcelead.reportsDTO.JobPipelineDTO;
import com.bis.sourcelead.service.RequirementReportService;
import com.bis.sourcelead.service.RequirementSubmissionService;
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
			
			if(StringUtils.isNotBlank(jobFilterDTO.getDateSelection())) {
				searchDates = reportsUtil.getJobFilterDate(jobFilterDTO.getDateSelection());
			}
	 
	    	int applicantsSum = 0;
//			int requestSentSum = 0;
			int shortlistedSum = 0;
			int submitToClientSum = 0;
			int interviewsSum = 0;
			int offeredSum = 0;
			int rejectedSum = 0;
			int backoutSum = 0;
			Map<Integer,Long> shortlistedMap = new HashMap<>();
			Map<Integer,Long> interviewMap = new HashMap<>();
			Map<Integer,Long> offerMadeMap = new HashMap<>();
			Map<Integer,Long> offerAcceptedMap = new HashMap<>();
			Map<Integer,Long> submitToClientMap = new HashMap<>();
			Map<Integer,Long> backOutMap = new HashMap<>();
			
			
			Long shortlistedDuration    = 0l;
			Long interviewDuration     = 0l;
			Long offerMadeDuration      = 0l;
			Long offerAcceptedDuration = 0l;
			Long submitToClientDuration = 0l;
			Long backOutDuration        = 0l;
			JobPipelineDTO report = new JobPipelineDTO();
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
					int rejectedCount = 0;
					int backoutCount = 0;
					List<RequirementSubmissionHistory> requirementSubmissionHistoryList = jobsPipelineReportService.getAllJobsReportsCountsBySubmmisonId(reqSub.getId());
					if(null != requirementSubmissionHistoryList && requirementSubmissionHistoryList.size() >0){
						Date applicantCreatedDate = null;
						Date shortlistCreatedDate = null;
						Date interviewCreatedDate = null;
						Date offerMadeCreatedDate = null;
						Date submitToClientCreatedDate = null;
						Date backOutCreatedDate = null;
						
						for(RequirementSubmissionHistory rsh : requirementSubmissionHistoryList){
							
							if("submission".equalsIgnoreCase(rsh.getToStage())){
								applicantsCount++;
								applicantCreatedDate= rsh.getCreatedDate();
							}else if("shortList".equalsIgnoreCase(rsh.getToStage())){
								shortlistedCount++;
								shortlistCreatedDate = rsh.getCreatedDate();
							}else if("interview".equalsIgnoreCase(rsh.getToStage())){
								interviewsCount++;
								interviewCreatedDate = rsh.getCreatedDate();
							}else if("offermade".equalsIgnoreCase(rsh.getToStage())){
								offeredCount++;
								offerMadeCreatedDate = rsh.getCreatedDate();
							}else if("request_sent".equalsIgnoreCase(rsh.getToStage())){
								requestSentCount++;
							}else if("submit_to_client".equalsIgnoreCase(rsh.getToStage())){
								submitToClientCount++;
								submitToClientCreatedDate = rsh.getCreatedDate();
							}else if("rejected".equalsIgnoreCase(rsh.getToStage())){
								rejectedCount++;
							}else if("backout".equalsIgnoreCase(rsh.getToStage())){
								backoutCount++;
								backOutCreatedDate = rsh.getCreatedDate();
							}
						}
						if(shortlistCreatedDate != null && applicantCreatedDate != null) {
							shortlistedMap.put(count, CalendarUtil.getNumberOfDays(applicantCreatedDate,shortlistCreatedDate));
						}
						if(interviewCreatedDate != null && applicantCreatedDate != null) {
							interviewMap.put(count, CalendarUtil.getNumberOfDays(applicantCreatedDate,interviewCreatedDate));
						}
						if(submitToClientCreatedDate != null && applicantCreatedDate != null) {
							submitToClientMap.put(count, CalendarUtil.getNumberOfDays(applicantCreatedDate,submitToClientCreatedDate));
						}
						if(offerMadeCreatedDate != null && applicantCreatedDate != null) {
							offerMadeMap.put(count, CalendarUtil.getNumberOfDays(applicantCreatedDate, offerMadeCreatedDate));
						}
						if(backOutCreatedDate != null && applicantCreatedDate != null) {
							backOutMap.put(count, CalendarUtil.getNumberOfDays(applicantCreatedDate,backOutCreatedDate));
						}
						
						
						 applicantsSum = applicantsSum+applicantsCount;
//						 requestSentSum = requestSentSum+requestSentCount;
						 shortlistedSum = shortlistedSum+shortlistedCount;
						 submitToClientSum = submitToClientSum+submitToClientCount;
						 interviewsSum = interviewsSum+interviewsCount;
						 offeredSum = offeredSum+offeredCount;
						 rejectedSum = rejectedSum+rejectedCount;
						 backoutSum = backoutSum + backoutCount;
					}
					count++;
				}
		    	if(shortlistedMap != null && !shortlistedMap.isEmpty()) {
		    		Long totalDays = 0l;
		    		for(Integer flag :shortlistedMap.keySet()) {
		    			totalDays = totalDays+shortlistedMap.get(flag);
		    		}
		    		shortlistedDuration = totalDays/(shortlistedMap.size());
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
		    		offerMadeDuration = totalDays/(offerMadeMap.size());
		    	}
		    	if(backOutMap != null && !backOutMap.isEmpty()) {
		    		Long totalDays = 0l;
		    		for(Integer flag :backOutMap.keySet()) {
		    			totalDays = totalDays+backOutMap.get(flag);
		    		}
		    		backOutDuration = totalDays/(backOutMap.size());
		    	}
		    	
		    	//days
		    	report.setShortlistedDays(shortlistedDuration);
		    	report.setInterviewsDays(interviewDuration);
		    	report.setOfferedDays(offerMadeDuration);
		    	report.setBackoutDays(backOutDuration);
		    	
		    	//count 
		    	report.setApplicantsCount(applicantsSum);
		    	report.setShortlistedCount(shortlistedSum);
		    	report.setSubmitToClientCount(submitToClientSum);
		    	report.setInterviewsCount(interviewsSum);
		    	report.setOfferedAcceptedCount(offeredSum);
		    	report.setOfferedCount(offeredSum);
		    	report.setRejectedCount(rejectedSum);
		    	report.setBackoutCount(backoutSum);
		    	
		    	
	    	}
			return report;
		 
	}

}
