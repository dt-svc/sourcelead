package com.bis.sourcelead.reports.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ApplicantsBackoutReportService {

	Map<String, Integer> getApplicantBackoutReport(List<Long> jobIdList, Date[] searchDates);
	

}
