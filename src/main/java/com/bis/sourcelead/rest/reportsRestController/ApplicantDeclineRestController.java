package com.bis.sourcelead.rest.reportsRestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.reports.service.ApplicantDeclineService;
import com.bis.sourcelead.reports.service.ApplicantsBySourceService;
import com.bis.sourcelead.reportsDTO.ApplicantDeclineDTO;
import com.bis.sourcelead.reportsDTO.ApplicantsBySourceDTO;
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


	@RequestMapping(value = "/app/getDeclinedApplicantsCount", method = RequestMethod.GET)
	public ApplicantDeclineDTO getDeclinedApplicantsCount(JobFilterDTO jobFilterDTO, HttpServletRequest request) throws Exception {

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

		applicantDeclineDTO =  applicantDeclineService.getDeclineApplicantSkillCount(jobIdList, searchDates,orgLocation); 

		return applicantDeclineDTO;
	}

	@RequestMapping(value = "/app/getDeclinedApplicantsList", method = RequestMethod.GET)
	public List<ApplicantDeclineDTO> getDeclinedApplicantsList(JobFilterDTO jobFilterDTO, HttpServletRequest request) throws Exception {

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
			Requirement requirement = requirementService.getRequirementByRequirementId(jobId);
			List<Long> jobids = new ArrayList<>();
			jobids.add(jobId);
			applicantDeclineDTO =  applicantDeclineService.getDeclineApplicantSkillCount(jobids, searchDates,orgLocation);
			applicantDeclineDTO.setJobName(requirement.getJobTitle());
			applicantDeclineList.add(applicantDeclineDTO);
			}
			
		}


		return applicantDeclineList;
	}


}
