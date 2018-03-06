package com.bis.sourcelead.reports.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.entity.OrgLocations;

public interface ApplicantDeclineDao {

	Map<String,Integer> getDeclineApplicantSkillCount(List<Long> jobIds,Date[] searchDates,OrgLocations orgLocation)throws Exception;
}
