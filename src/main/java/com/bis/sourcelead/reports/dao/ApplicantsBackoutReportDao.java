package com.bis.sourcelead.reports.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.reportsDTO.ApplicantsBackoutDTO;

public interface ApplicantsBackoutReportDao {

	List<ApplicantsBackoutDTO> getApplicantBackoutReport(List<Long> jobIdList, Date[] searchDates,int offset);

}
