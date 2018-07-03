package com.bis.sourcelead.rest.reportsRestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.bis.sourcelead.reports.service.ApplicantDeclineService;
import com.bis.sourcelead.reportsDTO.ApplicantDeclineDTO;
import com.bis.sourcelead.reportsDTO.JobFilterDTO;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
//@RequestMapping("/app")
public class ApplicantDeclineRestController {

	@Autowired
	ReportsUtil reportsUtil;

	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	ApplicantDeclineService applicantDeclineService;

	@Autowired
	RequirementService requirementService;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicantDeclineRestController.class);


	@SuppressWarnings("unused")
	@RequestMapping(value = "/app/getDeclinedApplicantsCount", method = RequestMethod.GET)
	public Response<ApplicantDeclineDTO> getDeclinedApplicantsCount(JobFilterDTO jobFilterDTO, HttpServletRequest request) throws Exception {
		Response<ApplicantDeclineDTO> response = new Response<>();
		try{
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(loginUser != null){

				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

				List<ApplicantDeclineDTO> applicantDeclineList = new ArrayList<>();
				ApplicantDeclineDTO applicantDeclineDTO=new ApplicantDeclineDTO();
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

				if(jobIdList.size() > 0){
					applicantDeclineDTO =  applicantDeclineService.getDeclineApplicantSkillCount(jobIdList, searchDates,orgLocation); 
				}else{
					response.setResponse(Result.DATA_NOT_FOUND.name());
					response.setResponseMessage(Result.DATA_NOT_FOUND.getValue());
				}
				
				if (applicantDeclineDTO != null) {
					response.setObject(applicantDeclineDTO);
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

	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/app/getDeclinedApplicantsList", method = RequestMethod.GET)
	public ResponseList<ApplicantDeclineDTO> getDeclinedApplicantsList(JobFilterDTO jobFilterDTO, HttpServletRequest request) throws Exception {
		
		ResponseList<ApplicantDeclineDTO> response = new ResponseList<>();
		
		try{
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(loginUser != null){

				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

				List<ApplicantDeclineDTO> applicantDeclineList = new ArrayList<>();
				ApplicantDeclineDTO applicantDeclineDTO=new ApplicantDeclineDTO();
				List<Long> jobIdList = new CopyOnWriteArrayList<>();

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
					applicantDeclineDTO =  applicantDeclineService.getDeclineApplicantSkillCount(jobids, searchDates,orgLocation);
					if(requirement != null){
					applicantDeclineDTO.setJobName(requirement.getJobTitle());
					applicantDeclineDTO.setGeneratedId(requirement.getGeneratedId());
					applicantDeclineList.add(applicantDeclineDTO);
					}
					if(applicantDeclineList != null){
						response.setData(applicantDeclineList);
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					} else {
						response.setResponse(Result.FAILED.name());
						response.setResponseMessage(Result.FAILED.getValue());
						}
					
					}
					
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
