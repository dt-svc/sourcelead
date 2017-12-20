package com.bis.sourcelead.reports.service;

import java.util.Date;
import java.util.List;

public interface ApplicantsBySourceService {

	Integer getCrowdsourcingApplicantsCount(List<Long> jobIds,Date startDate,Date endDate,Long orgLocationId);
	
	Integer getRequirementsCount(Long orgLocationId) throws Exception;
}
