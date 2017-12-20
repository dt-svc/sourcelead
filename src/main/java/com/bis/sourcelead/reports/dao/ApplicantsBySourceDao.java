package com.bis.sourcelead.reports.dao;

import java.util.Date;
import java.util.List;

public interface ApplicantsBySourceDao {

	Integer getCrowdsourcingApplicantsCount(List<Long> jobIds,Date startDate,Date endDate,Long orgLocationId);
	
	Integer getRequirementsCount(Long orgLocationId) throws Exception;
}
