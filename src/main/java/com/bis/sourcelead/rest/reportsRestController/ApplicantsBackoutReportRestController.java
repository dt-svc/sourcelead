package com.bis.sourcelead.rest.reportsRestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.reports.service.ApplicantsBackoutReportService;
import com.bis.sourcelead.reportsDTO.ApplicantDeclineDTO;
import com.bis.sourcelead.reportsDTO.ApplicantsBackoutDTO;
import com.bis.sourcelead.reportsDTO.JobFilterDTO;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")

public class ApplicantsBackoutReportRestController {

	@Autowired
	ReportsUtil reportsUtil;

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	ApplicantsBackoutReportService applicantsBackoutReportService;

	@Autowired
	RequirementService requirementService;
	
	@Autowired
	TimeZoneService timeZoneService;
	private static final Logger logger = LoggerFactory.getLogger(ApplicantsBackoutReportRestController.class);

	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/getApplicantBackoutReport", method = RequestMethod.GET)
	public Response<ApplicantsBackoutDTO> getApplicantBackoutReport(JobFilterDTO jobFilterDTO, HttpServletRequest request) throws Exception {
		
		Response<ApplicantsBackoutDTO> response = new Response<>();
		ApplicantsBackoutDTO applicantsBackoutDTOEmpty = new ApplicantsBackoutDTO();
		try{
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(loginUser != null){
				
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				List<Long> jobIdList = new ArrayList<>();
				if(StringUtils.isNotBlank(jobFilterDTO.getJobIds())) {
					String[] jobIds = jobFilterDTO.getJobIds().split(",");
					for(int i=0; i<jobIds.length; i++) {
						jobIdList.add(Long.valueOf(jobIds[i]));
					}
				}else{
					jobIdList = (List<Long>) SourceLeadSessionManager.getSessionAttribute(request, "requirementIdsList");
				}
				Date[] searchDates = null;

				if(StringUtils.isNotBlank(jobFilterDTO.getDateSelection())) {
					if(!jobFilterDTO.getDateSelection().equalsIgnoreCase("custom")) {
						searchDates = reportsUtil.getJobFilterDate(jobFilterDTO.getDateSelection(),orgLocation);
					}else {
						Date fromDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), jobFilterDTO.getFromDate());
			    	    Date toDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), jobFilterDTO.getToDate());
			    	   
			    	    Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
			    	    DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    	    DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
			    	    DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
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
				//Map<String, Integer> ApplicantsBackoutCount = null;
				ApplicantsBackoutDTO applicantsBackoutDTO = null;
				if ( jobIdList != null && !jobIdList.isEmpty()) {
					 applicantsBackoutDTO = applicantsBackoutReportService.getBackoutApplicantCount(jobIdList, searchDates,orgLocation);
					/*for (Entry<String, Integer> entry : ApplicantsBackoutCount.entrySet()){
						if(entry.getKey().equalsIgnoreCase("Better Offer")) {
							applicantsBackoutDTO.setBetterOffer(entry.getValue());
						}
						if(entry.getKey().equalsIgnoreCase("Current Company")) {
							applicantsBackoutDTO.setCurrentCompany(entry.getValue());
						}
						if(entry.getKey().equalsIgnoreCase("Compensation")) {
							applicantsBackoutDTO.setCompensation(entry.getValue());
						}
						if(entry.getKey().equalsIgnoreCase("Cold Feet")) {
							applicantsBackoutDTO.setColdFeet(entry.getValue());
						}
						if(entry.getKey().equalsIgnoreCase("Personal")) {
							applicantsBackoutDTO.setPersonal(entry.getValue());
						}
						if(entry.getKey().equalsIgnoreCase("other")) {
							applicantsBackoutDTO.setOther(entry.getValue());
						}
					}*/
				}

				if (applicantsBackoutDTO != null) {
					response.setObject(applicantsBackoutDTO);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}else if (applicantsBackoutDTO == null && jobIdList.isEmpty()) {
					if (response.getObject() == null) { // if jobIdList is empty then sending empty list as a response to service.
						response.setObject(applicantsBackoutDTOEmpty);
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					}
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

	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/getApplicantBackoutReportForInduvidualJob", method = RequestMethod.GET)
	public ResponseList<ApplicantsBackoutDTO> getApplicantBackoutReportForInduvidualJob(JobFilterDTO jobFilterDTO, HttpServletRequest request) throws Exception {

		ResponseList<ApplicantsBackoutDTO> response = new ResponseList<>();
		try{
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(loginUser != null){
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

				List<ApplicantsBackoutDTO> applicantsBackoutList = new ArrayList<>();
				ApplicantsBackoutDTO applicantsBackoutDTO=new ApplicantsBackoutDTO();
				List<Long> jobIdList = new ArrayList<>();

				Date[] searchDates = null;

				if(StringUtils.isNotBlank(jobFilterDTO.getDateSelection())) {
					if(!jobFilterDTO.getDateSelection().equalsIgnoreCase("custom")) {
						searchDates = reportsUtil.getJobFilterDate(jobFilterDTO.getDateSelection(),orgLocation);
					}else {
						Date fromDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), jobFilterDTO.getFromDate());
			    	    Date toDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), jobFilterDTO.getToDate());
			    	   
			    	    Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
			    	    DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    	    DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
			    	    DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
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

				if(StringUtils.isNotBlank(jobFilterDTO.getJobIds())) {
					String[] jobIds = jobFilterDTO.getJobIds().split(",");
					for(int i=0; i<jobIds.length; i++) {
						jobIdList.add(Long.valueOf(jobIds[i]));
					}
				}else {
					jobIdList = (List<Long>) SourceLeadSessionManager.getSessionAttribute(request, "requirementIdsList");
				}


				for (Long jobId : jobIdList) {
					if(jobId!=null){
						Requirement requirement = requirementService.getRequirementByRequirementIdAndSearchValue(jobId,jobFilterDTO.getSearchValue());
						List<Long> jobids = new ArrayList<>();
						jobids.add(jobId);
						applicantsBackoutDTO =  applicantsBackoutReportService.getBackoutApplicantCount(jobids, searchDates,orgLocation);
						if(requirement != null){
						applicantsBackoutDTO.setJobName(requirement.getJobTitle());
						applicantsBackoutDTO.setGeneratedId(requirement.getGeneratedId());
						applicantsBackoutList.add(applicantsBackoutDTO);
						}
					}

				}
				if(applicantsBackoutList != null){
					response.setData(applicantsBackoutList);
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
}