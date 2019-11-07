package com.bis.sourcelead.reports.service;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.reportsDTO.ApplicantDeclineDTO;

public interface ApplicantDeclineService {

	ApplicantDeclineDTO getDeclineApplicantSkillCount(List<Long> jobIds,Date[] searchDates,OrgLocations orgLocation)throws Exception;
	
}
