package com.bis.sourcelead.reports.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ApplicantsBackoutReportDao {

	Map<String, Integer> getApplicantBackoutReport(List<Long> jobIdList, Date[] searchDates);

}
