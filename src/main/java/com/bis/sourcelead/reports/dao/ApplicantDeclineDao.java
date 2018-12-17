package com.bis.sourcelead.reports.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.reportsDTO.ApplicantDeclineDTO;
import com.bis.sourcelead.reportsDTO.ApplicantsBackoutDTO;

public interface ApplicantDeclineDao {

	List<ApplicantDeclineDTO> getDeclineApplicantSkillCount(List<Long> jobIds,Date[] searchDates,OrgLocations orgLocation,int offset)throws Exception;
}
