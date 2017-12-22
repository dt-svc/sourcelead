package com.bis.sourcelead.rest.reportsRestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.reports.service.ApplicantsBackoutReportService;
import com.bis.sourcelead.reportsDTO.ApplicantsBackoutDTO;
import com.bis.sourcelead.reportsDTO.JobFilterDTO;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")

public class ApplicantsBackoutReportRestController {

	@Autowired
	ReportsUtil reportsUtil;
	
	@Autowired
	ApplicantsBackoutReportService applicantsBackoutReportService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getApplicantBackoutReport", method = RequestMethod.GET)
	public ApplicantsBackoutDTO getApplicantBackoutReport(JobFilterDTO jobFilterDTO, HttpServletRequest request) throws Exception {
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
			searchDates = reportsUtil.getJobFilterDate(jobFilterDTO.getDateSelection());
		}
		Map<String, Integer> ApplicantsBackoutCount = null;
		 ApplicantsBackoutDTO applicantsBackoutDTO = new ApplicantsBackoutDTO();;
		if ( jobIdList != null && !jobIdList.isEmpty()) {
		 ApplicantsBackoutCount =  applicantsBackoutReportService.getApplicantBackoutReport(jobIdList, searchDates);
		 for (Entry<String, Integer> entry : ApplicantsBackoutCount.entrySet()){
				if(entry.getKey().equalsIgnoreCase("betterOffer")) {
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
			}
		}
		
	
		
		return applicantsBackoutDTO;
	
	
}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getApplicantBackoutReportForInduvidualJob", method = RequestMethod.GET)
	public ApplicantsBackoutDTO getApplicantBackoutReportForInduvidualJob(JobFilterDTO jobFilterDTO, HttpServletRequest request) throws Exception {
	
	
		
	
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
			searchDates = reportsUtil.getJobFilterDate(jobFilterDTO.getDateSelection());
		}
		
		if ( jobIdList != null && !jobIdList.isEmpty()) {
			applicantsBackoutReportService.getApplicantBackoutReportForinduvidalJobs(jobIdList, searchDates);
		
}
		return null;
	
}
}