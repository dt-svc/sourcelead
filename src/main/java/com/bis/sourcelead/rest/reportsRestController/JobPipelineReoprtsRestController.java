package com.bis.sourcelead.rest.reportsRestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.bis.sourcelead.service.RequirementReportService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
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
	
	
	@Async
	@RequestMapping(value = "/getAllJobsPipelineReoportsList", method = RequestMethod.POST)
	@ResponseBody
	String getAllJobsPipelineReoportsList(@RequestParam(value = "requirementId", required = false) String requirementId, @RequestParam(value = "submissionType", required = false) String submissionType, HttpServletRequest request) throws Exception
	{
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		 Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd 00:00:00");
		 DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd 23:59:59");
		 DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		 Date endDateUTC =null ;
		 Date startDateUTC = null;
		 

	    	int applicantsSum = 0;
			int requestSentSum = 0;
			int shortlistedSum = 0;
			int submitToClientSum = 0;
			int interviewsSum = 0;
			int offeredSum = 0;
			int rejectedSum = 0;
			int backoutSum = 0;
			long vendorLocationId = 0L;
	    	List<Requirement> reqList = jobsPipelineReportService.getAllJobsForReportBasedOnStartAndEndDates(orgLocations.getId(), user.getId(),startDateUTC,endDateUTC);
	     
	    	List<RequirementSubmission> reqSubmissionList = jobsPipelineReportService.getAllJobsReportCountByOrglocationIdAndLoginUserCodeAndStartAndEndDate(orgLocations.getId(), user.getUserCode(),startDateUTC,endDateUTC);
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
					List<RequirementSubmissionHistory> requirementSubmissionHistoryList = requirementReportService.getApplicantsCountsByDateRange(reqSub.getId(),startDateUTC,endDateUTC);
					if(null != requirementSubmissionHistoryList && requirementSubmissionHistoryList.size() >0){
						for(RequirementSubmissionHistory rsh : requirementSubmissionHistoryList){
							if("submission".equalsIgnoreCase(rsh.getToStage())){
								applicantsCount++;
							}else if("shortList".equalsIgnoreCase(rsh.getToStage())){
								shortlistedCount++;
							}else if("interview".equalsIgnoreCase(rsh.getToStage())){
								interviewsCount++;
							}else if("offermade".equalsIgnoreCase(rsh.getToStage())){
								offeredCount++;
							}else if("request_sent".equalsIgnoreCase(rsh.getToStage())){
								requestSentCount++;
							}else if("submit_to_client".equalsIgnoreCase(rsh.getToStage())){
								submitToClientCount++;
							}else if("rejected".equalsIgnoreCase(rsh.getToStage())){
								rejectedCount++;
							}else if("backout".equalsIgnoreCase(rsh.getToStage())){
								backoutCount++;
							}
						}
						 applicantsSum = applicantsSum+applicantsCount;
						 requestSentSum = requestSentSum+requestSentCount;
						 shortlistedSum = shortlistedSum+shortlistedCount;
						 submitToClientSum = submitToClientSum+submitToClientCount;
						 interviewsSum = interviewsSum+interviewsCount;
						 offeredSum = offeredSum+offeredCount;
						 rejectedSum = rejectedSum+rejectedCount;
						 backoutSum = backoutSum + backoutCount;
					}
				}
	    	}
			return submissionType;
		 
	}

}
