package com.bis.sourcelead.reports.service;

import java.util.Date;
import java.util.List;

public interface ApplicantsBySourceService {

	Integer getCrowdsourcingApplicantsCount(List<Long> jobIds,Date[] searchDates);
	
	Integer getRequirementsCount(Long orgLocationId) throws Exception;
}
