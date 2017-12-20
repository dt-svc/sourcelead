package com.bis.sourcelead.reports.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.reports.dao.ApplicantsBySourceDao;
import com.bis.sourcelead.reports.service.ApplicantsBySourceService;

@Service
public class ApplicantsBySourceServiceImpl implements ApplicantsBySourceService{
	
	
	@Autowired
	ApplicantsBySourceDao applicantsBySourceDao;

	@Override
	public Integer getCrowdsourcingApplicantsCount(List<Long> jobIds, Date[] searchDates) {
		return applicantsBySourceDao.getCrowdsourcingApplicantsCount(jobIds, searchDates);
	}

	@Override
	public Integer getRequirementsCount(Long orgLocationId) throws Exception {
		return applicantsBySourceDao.getRequirementsCount(orgLocationId);
	}

}
