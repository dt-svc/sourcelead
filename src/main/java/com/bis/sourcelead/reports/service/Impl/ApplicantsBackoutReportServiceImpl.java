package com.bis.sourcelead.reports.service.Impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.reports.dao.ApplicantsBackoutReportDao;
import com.bis.sourcelead.reports.service.ApplicantsBackoutReportService;
@Service
public class ApplicantsBackoutReportServiceImpl implements ApplicantsBackoutReportService {

	
	@Autowired
	private ApplicantsBackoutReportDao applicantsBackoutReportDao;
	
	@Override
	public Map<String, Integer> getApplicantBackoutReport(List<Long> jobIds, Date[] searchDates) {
		return applicantsBackoutReportDao.getApplicantBackoutReport(jobIds, searchDates);
	}

	@Override
	public void getApplicantBackoutReportForinduvidalJobs(List<Long> jobIdList, Date[] searchDates) {
		 applicantsBackoutReportDao.getApplicantBackoutReportForinduvidalJobs(jobIdList, searchDates);		
	}

}
