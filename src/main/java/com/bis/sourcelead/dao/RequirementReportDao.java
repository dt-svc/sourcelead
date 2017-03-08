package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.User;

public interface RequirementReportDao {

	List<Requirement> getRequirementReports(String userRoleName, OrgLocations orgLocation, User user,String filterDate, Date startDateUTC, Date endDateUTC);

	List<RequirementSubmissionHistory> getApplicantsReports(String userRoleName, OrgLocations orgLocation, User user,Date startDateUTC, Date endDateUTC);

	List<RequirementSubmissionHistory> getRequirementReports(Long requirementId);

}
