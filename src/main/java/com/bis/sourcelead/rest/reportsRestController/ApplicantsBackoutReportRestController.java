package com.bis.sourcelead.rest.reportsRestController;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.reports.service.ApplicantsBackoutReportService;
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
	public Map<String, Integer> getApplicantBackoutReport(JobFilterDTO jobFilterDTO, HttpServletRequest request) throws Exception {
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
		Map<String, Integer> ApplicantsBackoutCount =  applicantsBackoutReportService.getApplicantBackoutReport(jobIdList, searchDates);
		return ApplicantsBackoutCount;
	
	
}
}
	
