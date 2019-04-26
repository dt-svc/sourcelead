package com.bis.sourcelead.rest.reportsRestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.reports.service.ClientReportService;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.reports.service.JobsPipelineReportService;
import com.bis.sourcelead.reportsDTO.ApplicantDeclineDTO;
import com.bis.sourcelead.reportsDTO.JobFilterDTO;
import com.bis.sourcelead.reportsDTO.JobPipelineDTO;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementReportService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

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
	
	@Autowired
	ClientReportService clientReportService;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	private static final Logger logger = LoggerFactory.getLogger(JobPipelineReoprtsRestController.class);
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Async
	@RequestMapping(value = "/getAllJobsPipelineReoportsList", method = RequestMethod.POST)
	@ResponseBody
	Response<JobPipelineDTO> getAllJobsPipelineReoportsList(JobFilterDTO jobFilterDTO, HttpServletRequest request, @RequestParam(defaultValue = "0") long orgRelationId, @RequestParam(defaultValue = "0") long spocId) throws Exception
	{
		Response<JobPipelineDTO> response = new Response<>();
		try{
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(loginUser != null){
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				List<Long> jobIdList = new ArrayList<>();
				Date[] searchDates = null;
				if(StringUtils.isNotBlank(jobFilterDTO.getDateSelection())) {
					if(!jobFilterDTO.getDateSelection().equalsIgnoreCase("custom")) {
						searchDates = reportsUtil.getJobFilterDate(jobFilterDTO.getDateSelection(),orgLocation);
					}else {
						Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
						DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
						DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
						Date fromDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), jobFilterDTO.getFromDate());
			    	    Date toDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), jobFilterDTO.getToDate());
						String customStartTimeStr = TZ_DF_CSD.format(fromDate);
						String customEndTimeStr = TZ_DF_CED.format(toDate);
						String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
						String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
						fromDate = TZ_DF_TS.parse(startDateString);
						toDate = TZ_DF_TS.parse(endDateString);
						
			    	    searchDates = new Date[2];
			    	    searchDates[0] = fromDate;
			    	    searchDates[1] = toDate;
					}
				}
				
				if(jobFilterDTO.getFromtype() != null && jobFilterDTO.getFromtype().equalsIgnoreCase(Constants.CLIENT_REPORT)) {
					
					jobIdList = getClientRequirementList(orgLocation,user,searchDates);
				}else{
					if(StringUtils.isNotBlank(jobFilterDTO.getJobIds())) {
						String[] jobIds = jobFilterDTO.getJobIds().split(",");
						for(int i=0; i<jobIds.length; i++) {
							jobIdList.add(Long.valueOf(jobIds[i]));
						}
					}else {
						jobIdList = (List<Long>) SourceLeadSessionManager.getSessionAttribute(request, "requirementIdsList");
						if(orgRelationId > 0) {							
							//above jobIdList is released to vendor job id's
							//now get the parentJobId based on vendorReleasedJob and get the jobStages for that vendor resubmitted job('submit to client')
							jobIdList = requirementReportService.getParentRequirementIdListByRequirementIds(jobIdList);
						}
					}
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
					Map<Integer,Long> rejectedMap = new HashMap<>();
					
					double applicantDuration    = 0l;
					double shortlistedDuration    = 0l;
					double interviewDuration     = 0l;
					double offerMadeDuration      = 0l;
					double offerAcceptedDuration = 0l;
					double submitToClientDuration = 0l;
					double backOutDuration        = 0l;
					double rejectDuration        = 0l;
					
					int rejectedFromSubmissionCount = 0;
		            int rejectedFromShortlistedCount = 0;
		            int rejectedFromInterviewCount = 0;
		            int rejectedFromOffermadeCount = 0;
		            int rejectedFromRequestSentCount = 0;
		            int rejectedFromOfferAccepetCount = 0;
		            int rejectedFromSubmitTOClientCount = 0;
		            int rejectedFromNewCount = 0;
		            
					JobPipelineDTO report = new JobPipelineDTO();
					
					Integer openJobcount = new Integer(0);
					Integer onholdJobcount = new Integer(0);
					Integer closedJobcount = new Integer(0);
					Integer unfilledclosedJobcount = new Integer(0);
					Integer filledclosedJobcount = new Integer(0);
					Integer cancelJobcount = new Integer(0);
					
					List<Requirement> requirementList = null;
					
					if(orgRelationId > 0) {
						requirementList = jobsPipelineReportService.vendorRequirementListStatusCountByJobIdsAndDates(jobIdList, searchDates);
					} else {
						requirementList = jobsPipelineReportService.requirementListStatusCountByJobIdsAndDates(jobIdList, searchDates);
					}
					
					if(requirementList != null && !requirementList.isEmpty()) {
						openJobcount = requirementList.stream().filter(r->r.getRequirementSubmissionStatus().toString().equalsIgnoreCase("OPEN")).collect(Collectors.toList()).size();
						onholdJobcount = requirementList.stream().filter(r->r.getRequirementSubmissionStatus().toString().equalsIgnoreCase("ONHOLD")).collect(Collectors.toList()).size();
						closedJobcount = requirementList.stream().filter(r->r.getRequirementSubmissionStatus().toString().equalsIgnoreCase("CLOSED")).collect(Collectors.toList()).size();
						unfilledclosedJobcount = requirementList.stream().filter(r->r.getRequirementSubmissionStatus().toString().equalsIgnoreCase("UNFILLEDCLOSED")).collect(Collectors.toList()).size();
						filledclosedJobcount = requirementList.stream().filter(r->r.getRequirementSubmissionStatus().toString().equalsIgnoreCase("FILLEDCLOSED")).collect(Collectors.toList()).size();
						cancelJobcount = requirementList.stream().filter(r->r.getRequirementSubmissionStatus().toString().equalsIgnoreCase("CANCEL")).collect(Collectors.toList()).size();
					}

					int count = 1;
					int dupCount = 0;
					
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
							
							List<RequirementSubmissionHistory> requirementSubmissionHistoryList = null;
							
							if(orgRelationId > 0) {
					    		String masterUserCode = "";
					    		if (spocId > 0) {
					    			OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(spocId);
					    			if(orgSpoc != null) {
					    				masterUserCode = "'" + orgSpoc.getVendorUser().getUserCode() + "'";
					    			}
					    		} else {
					    			List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocByOrgRelationId(orgRelationId);
					    			if (orgSpocList != null && !orgSpocList.isEmpty()) {
					    				for (OrgSpoc orgSpoc : orgSpocList) {
					    					masterUserCode += (masterUserCode.isEmpty() ? "" : ",") + "'" + orgSpoc.getVendorUser().getUserCode() + "'";
					    				}
					    			}
					    		}
					    		requirementSubmissionHistoryList = jobsPipelineReportService.getAllJobsReportsCountsBySubmmisonIdAndMasterUserCode(reqSub.getId(), masterUserCode);
					    	} else {
					    		requirementSubmissionHistoryList = jobsPipelineReportService.getAllJobsReportsCountsBySubmmisonId(reqSub.getId());
					    	}
							
							if(null != requirementSubmissionHistoryList && requirementSubmissionHistoryList.size() >0){
								Date requirementCreatedDate = reqSub.getRequirement().getCreatedDate();
								Date applicantCreatedDate = null;
								Date shortlistCreatedDate = null;
								Date interviewCreatedDate = null;
								Date offerMadeCreatedDate = null;
								Date offerAcceptCreatedDate = null;
								Date submitToClientCreatedDate = null;
								Date backOutCreatedDate = null;
								Date rejectedCreatedDate = null;
								Date rejectedFromCreatedDate = null;
								Map<String,Date> createdDatesMap = new LinkedHashMap<>();
								for(RequirementSubmissionHistory rsh : requirementSubmissionHistoryList){
									createdDatesMap.put(rsh.getToStage(), rsh.getCreatedDate());
								}
								for(RequirementSubmissionHistory rsh : requirementSubmissionHistoryList){
									
									if("submission".equalsIgnoreCase(rsh.getToStage())){
										applicantsCount++;
										dupCount++;
										applicantCreatedDate= rsh.getCreatedDate();
									} if("shortList".equalsIgnoreCase(rsh.getToStage()) || (rsh.getFromStage().equalsIgnoreCase("submission") && rsh.getToStage().equalsIgnoreCase("offermade"))|| 
											(rsh.getFromStage().equalsIgnoreCase("submission") && rsh.getToStage().equalsIgnoreCase("interview"))){
										shortlistedCount++;
										dupCount++;
										if((rsh.getFromStage().equalsIgnoreCase("submission") && rsh.getToStage().equalsIgnoreCase("offermade"))|| (rsh.getFromStage().equalsIgnoreCase("submission") && rsh.getToStage().equalsIgnoreCase("interview")) 
												|| (rsh.getFromStage().equalsIgnoreCase("shortList") && rsh.getToStage().equalsIgnoreCase("interview"))) {
											shortlistCreatedDate = createdDatesMap.get("SUBMISSION");
										}else {
											shortlistCreatedDate = rsh.getCreatedDate();
										}
										
									} if("interview".equalsIgnoreCase(rsh.getToStage()) || (rsh.getFromStage().equalsIgnoreCase("submission") && rsh.getToStage().equalsIgnoreCase("offermade")) || (rsh.getFromStage().equalsIgnoreCase("shortList") && ((rsh.getToStage().equalsIgnoreCase("offermade") || rsh.getToStage().equalsIgnoreCase("client_offermade")) || rsh.getToStage().equalsIgnoreCase("client_interview")))
											|| ((rsh.getFromStage().equalsIgnoreCase("submit_to_client") || rsh.getFromStage().equalsIgnoreCase("client_interview")) && rsh.getToStage().equalsIgnoreCase("client_interview"))){
										interviewsCount++;
										dupCount++;
										if(rsh.getFromStage().equalsIgnoreCase("submission") && rsh.getToStage().equalsIgnoreCase("offermade")) {
											interviewCreatedDate = createdDatesMap.get("SUBMISSION");
										}else if(rsh.getFromStage().equalsIgnoreCase("shortList") && rsh.getToStage().equalsIgnoreCase("offermade")) {
											interviewCreatedDate = createdDatesMap.get("SHORTLIST");
										}else {
											interviewCreatedDate = rsh.getCreatedDate();
										}
										
									} if("offermade".equalsIgnoreCase(rsh.getToStage())  ||  "client_offermade".equalsIgnoreCase(rsh.getToStage())){
										offeredCount++;
										dupCount++;
										offerMadeCreatedDate = rsh.getCreatedDate();
									} if("request_sent".equalsIgnoreCase(rsh.getToStage())){
										requestSentCount++;
										dupCount++;
									} if("submit_to_client".equalsIgnoreCase(rsh.getToStage())){
										submitToClientCount++;
										dupCount++;
										submitToClientCreatedDate = rsh.getCreatedDate();
									} if("rejected".equalsIgnoreCase(rsh.getToStage())){
										rejectedCount++;
										dupCount++;
										rejectedCreatedDate = rsh.getCreatedDate();
										if(rsh.getFromStage().equalsIgnoreCase("submission")) {
											rejectedFromSubmissionCount++;
											rejectedFromCreatedDate =  createdDatesMap.get("SUBMISSION");
										}else if(rsh.getFromStage().equalsIgnoreCase("shortlist")) {
											rejectedFromShortlistedCount++;
											rejectedFromCreatedDate =  createdDatesMap.get("SHORTLIST");
										}else if(rsh.getFromStage().equalsIgnoreCase("interview")){
											rejectedFromInterviewCount++;
											rejectedFromCreatedDate =  createdDatesMap.get("INTERVIEW");
										}else if(rsh.getFromStage().equalsIgnoreCase("offermade")){
											rejectedFromOffermadeCount++;
											rejectedFromCreatedDate =  createdDatesMap.get("OFFERMADE");
										}else if(rsh.getFromStage().equalsIgnoreCase("request_sent")){
											rejectedFromRequestSentCount++;
											rejectedFromCreatedDate =  createdDatesMap.get("REQUEST_SENT");
										}else if(rsh.getFromStage().equalsIgnoreCase("offeraccepted")){
											rejectedFromOfferAccepetCount++;
											rejectedFromCreatedDate =  createdDatesMap.get("OFFERACCEPTED");
										}else if(rsh.getFromStage().equalsIgnoreCase("submit_to_client")){
											rejectedFromSubmitTOClientCount++;
											rejectedFromCreatedDate =  createdDatesMap.get("SUBMIT_TO_CLIENT");
										}else if(rsh.getFromStage().equalsIgnoreCase("NEW")){
											rejectedFromNewCount++;
											rejectedFromCreatedDate =  createdDatesMap.get("NEW");
										}
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
								if(rejectedCreatedDate != null && rejectedFromCreatedDate != null) {
									rejectedMap.put(count, CalendarUtil.getNumberOfDays(rejectedFromCreatedDate,rejectedCreatedDate));
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
				    	
				    	if(rejectedMap != null && !rejectedMap.isEmpty()) {
				    		Long totalDays = 0l;
				    		for(Integer flag :rejectedMap.keySet()) {
				    			totalDays = totalDays+rejectedMap.get(flag);
				    		}
				    		rejectDuration = Math.round((double)totalDays/(double)(rejectedMap.size()));
				    	}
				    	//days
				    	report.setApplicantsDays(applicantDuration);
				    	report.setShortlistedDays(shortlistedDuration);
				    	report.setInterviewsDays(interviewDuration);
				    	report.setOfferedDays(offerMadeDuration);
				    	report.setOfferedAcceptedDays(offerAcceptedDuration);
				    	report.setSubmitToClientDays(submitToClientDuration);
				    	report.setBackoutDays(backOutDuration);
				    	report.setRejectedDays(rejectDuration);
				    	
				    	//count 
				    	report.setApplicantsCount(applicantsSum);
				    	report.setRequestSentCount(requestSentSum);
				    	report.setShortlistedCount(shortlistedSum);
				    	report.setSubmitToClientCount(submitToClientSum);
				    	report.setInterviewsCount(interviewsSum);
				    	report.setOfferedAcceptedCount(offeracceptedSum);
				    	report.setOfferedCount(offeredSum);
				    	report.setRejectedCount(rejectedSum);
				    	report.setBackoutCount(backoutSum);
				    	
				    	
				    	//Moveforward percentage
				    	report.setShortlistedMove(Math.round(((double)shortlistedSum/(double)applicantsSum)*100));
				    	report.setSubmitToClientMove(Math.round(((double)submitToClientSum/(double)shortlistedSum)*100));
				    	report.setInterviewsMove(Math.round(((double)interviewsSum/(double)shortlistedSum)*100));
				    	if(interviewsSum<offeredSum) {
					    	report.setOfferedMove(Math.round((((double)offeredSum/(double)interviewsSum)*100)/offeredSum));
				    	}else {
					    	report.setOfferedMove(Math.round(((double)offeredSum/(double)interviewsSum)*100));
				    	}
				    	report.setOfferedAcceptedMove(Math.round(((double)offeracceptedSum/(double)offeredSum)*100));
				    	report.setBackoutMove(Math.round(((double)backoutSum /(double)offeracceptedSum)*100));
				    	
				    	Double rejectedMove = 0d;
				    	int rejctMoveCount = 0;
				    	if(rejectedFromNewCount > 0 & applicantsSum >0) {
				    		rejctMoveCount++;
				    		rejectedMove = rejectedMove+(((double)rejectedFromNewCount/(double)applicantsSum)*100);
				    	}
				    	if(rejectedFromSubmissionCount > 0 && applicantsSum >0) {
				    		rejctMoveCount++;
				    		rejectedMove = rejectedMove+(((double)rejectedFromSubmissionCount/(double)applicantsSum)*100);
				    	}
				    	if(rejectedFromShortlistedCount > 0 && shortlistedSum >0) {
				    		rejctMoveCount++;
				    		rejectedMove = rejectedMove+(((double)rejectedFromShortlistedCount/(double)shortlistedSum)*100);
				    	}
				    	if(rejectedFromInterviewCount > 0 && interviewsSum > 0) {
				    		rejctMoveCount++;
				    		rejectedMove = rejectedMove+(((double)rejectedFromInterviewCount/(double)interviewsSum)*100);
				    	}
				    	if(rejectedFromOffermadeCount > 0 && offeredSum > 0) {
				    		rejctMoveCount++;
				    		rejectedMove = rejectedMove+(((double)rejectedFromOffermadeCount/(double)offeredSum)*100);
				    	}
				    	if(rejectedFromRequestSentCount > 0 && applicantsSum > 0) {
				    		rejctMoveCount++;
				    		rejectedMove = rejectedMove+(((double)rejectedFromRequestSentCount/(double)applicantsSum)*100);
				    	}
				    	if(rejectedFromOfferAccepetCount > 0 && offeracceptedSum > 0) {
				    		rejctMoveCount++;
				    		rejectedMove = rejectedMove+(((double)rejectedFromOfferAccepetCount/(double)offeracceptedSum)*100);
				    	}
				    	if(rejectedFromSubmitTOClientCount > 0 && submitToClientSum > 0) {
				    		rejctMoveCount++;
				    		rejectedMove = rejectedMove+(((double)rejectedFromSubmitTOClientCount/(double)submitToClientSum)*100);
				    	}
				    	report.setRejectedMove(Math.round(rejectedMove/rejctMoveCount));
				    	 
				    	//jobs count
				    	report.setAllJobsCount(openJobcount + onholdJobcount + closedJobcount + cancelJobcount + unfilledclosedJobcount + filledclosedJobcount);
				    	report.setOpenJobsCount(openJobcount);
				    	report.setOnHoldJobsCount(onholdJobcount);
				    	report.setClosedJonbsCount(closedJobcount + unfilledclosedJobcount + filledclosedJobcount);
				    	report.setCancelledjobsCount(cancelJobcount);
			    	
			    	}
			    	
			    	if(dupCount > 0) {
			    		report.setApplicantsMove(100);
			    	}else {
			    		report.setApplicantsMove(0);
			    	}
			    	
			    	if(report != null){
			    		response.setObject(report);
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					} else {
						response.setResponse(Result.FAILED.name());
						response.setResponseMessage(Result.FAILED.getValue());
					}	
			    	
			}else if (loginUser == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
			
		}catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
		 
	}


	private List<Long> getClientRequirementList(OrgLocations orgLocation, User user, Date[] searchDates) {
		List<Long> jobIds = new ArrayList<>();
		List<OrgRelations> clientList = clientReportService.getAllClientsByOrgLocationIdAndUserId(orgLocation.getId(), user.getId());
		if(clientList != null && clientList.size() > 0) {
			for(OrgRelations orgRelation : clientList) {
				List<Requirement> jobsList = clientReportService.getClientJobsByOrgRelationId(orgRelation.getId(), orgLocation.getId(), user.getId(), searchDates, null);
				if(jobsList != null && jobsList.size() > 0) {
					for(Requirement req :jobsList) {
						jobIds.add(req.getId());
					}
				}
			}
		}
		return jobIds;
	}

}
