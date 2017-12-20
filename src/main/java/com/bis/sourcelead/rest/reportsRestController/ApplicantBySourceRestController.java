package com.bis.sourcelead.rest.reportsRestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.reports.service.ApplicantsBySourceService;
import com.bis.sourcelead.reportsDTO.ApplicantsBySourceDTO;
import com.bis.sourcelead.reportsDTO.JobFilterDTO;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class ApplicantBySourceRestController {
	
	@Autowired
	ReportsUtil reportsUtil;
	
	@Autowired
	ApplicantsBySourceService applicantsBySourceService;
	
	@RequestMapping(value = "/getApplicantBySourceDetails", method = RequestMethod.GET)
	public List<ApplicantsBySourceDTO> getApplicantBySourceDetails(JobFilterDTO jobFilterDTO, HttpServletRequest request) throws Exception {
		
		List<ApplicantsBySourceDTO> applicantBySourceList = new ArrayList<>();
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
		
		Integer crowdsourcingApplicantsCount =  applicantsBySourceService.getCrowdsourcingApplicantsCount(jobIdList, searchDates[0], searchDates[1]); 
		System.out.println(crowdsourcingApplicantsCount);
		
		return applicantBySourceList;
	}

}
