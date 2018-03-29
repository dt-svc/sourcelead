package com.bis.sourcelead.reports.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.reportsDTO.ApplicantDeclineDTO;
import com.bis.sourcelead.reportsDTO.ApplicantsBackoutDTO;

public interface ApplicantsBackoutReportService {

	Map<String, Integer> getApplicantBackoutReport(List<Long> jobIdList, Date[] searchDates);

	void getApplicantBackoutReportForinduvidalJobs(List<Long> jobIdList, Date[] searchDates);
	
	ApplicantsBackoutDTO getBackoutApplicantCount(List<Long> jobIds,Date[] searchDates,OrgLocations orgLocation)throws Exception;	

}
