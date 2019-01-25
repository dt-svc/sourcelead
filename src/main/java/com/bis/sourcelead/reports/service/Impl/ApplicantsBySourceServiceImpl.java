package com.bis.sourcelead.reports.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.ApplicantBySourceCountDTO;
import com.bis.sourcelead.reports.dao.ApplicantsBySourceDao;
import com.bis.sourcelead.reports.service.ApplicantsBySourceService;
import com.googlecode.mp4parser.boxes.apple.AppleLyricsBox;

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
	public ApplicantBySourceCountDTO getCrowdsourcingApplicantsCountByStage(List<Long> jobIdList, Date[] searchDates) {
		return applicantsBySourceDao.getCrowdsourcingApplicantsCountByStage(jobIdList, searchDates);
	}

	@Override
	public ApplicantBySourceCountDTO getVendorSourceApplicantsCountByStage(List<Long> jobIds, Date[] searchDates) {
		return applicantsBySourceDao.getVendorSourceApplicantsCountByStage(jobIds, searchDates);
	}

	@Override
	public ApplicantBySourceCountDTO getFreelanceRecruiterSourceApplicantsCountByStage(List<Long> jobIdList, Date[] searchDates) {
		return applicantsBySourceDao.getFreelanceRecruiterSourceApplicantsCountByStage(jobIdList, searchDates);
	}

	@Override
	public ApplicantBySourceCountDTO getInternalSourceApplicantsCountByStage(List<Long> jobIdList, Date[] searchDates) {
		return applicantsBySourceDao.getInternalSourceApplicantsCountByStage(jobIdList, searchDates);
	}

}
