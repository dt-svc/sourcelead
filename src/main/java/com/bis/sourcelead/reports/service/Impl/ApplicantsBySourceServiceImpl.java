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
	public Integer getCrowdsourcingApplicantsCount(List<Long> jobIdList, Date[] searchDates) {
		return applicantsBySourceDao.getCrowdsourcingApplicantsCount(jobIdList, searchDates);
	}

	@Override
	public Integer getRequirementsCount(Long orgLocationId) throws Exception {
		return applicantsBySourceDao.getRequirementsCount(orgLocationId);
	}

	@Override
	public Integer getCrowdsourcingApplicantsCountByStage(List<Long> jobIdList, Date[] searchDates, String stage) {
		return applicantsBySourceDao.getCrowdsourcingApplicantsCountByStage(jobIdList, searchDates, stage);
	}

	@Override
	public Integer getVendorSourceApplicantsCountByStage(List<Long> jobIds, Date[] searchDates, String stage) {
		return applicantsBySourceDao.getVendorSourceApplicantsCountByStage(jobIds, searchDates, stage);
	}

}
