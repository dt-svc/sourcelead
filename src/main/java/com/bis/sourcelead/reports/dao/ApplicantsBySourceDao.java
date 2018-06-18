package com.bis.sourcelead.reports.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.ApplicantBySourceCountDTO;

public interface ApplicantsBySourceDao {

	Integer getCrowdsourcingApplicantsCount(List<Long> jobIdList,Date[] searchDates);
	
	Integer getRequirementsCount(Long orgLocationId) throws Exception;
	
	ApplicantBySourceCountDTO getCrowdsourcingApplicantsCountByStage(List<Long> jobIdList,Date[] searchDates);
	
	ApplicantBySourceCountDTO getVendorSourceApplicantsCountByStage(List<Long> jobIdList,Date[] searchDates);
	
	ApplicantBySourceCountDTO getFreelanceRecruiterSourceApplicantsCountByStage(List<Long> jobIdList,Date[] searchDates);
	
	ApplicantBySourceCountDTO getInternalSourceApplicantsCountByStage(List<Long> jobIdList,Date[] searchDates);
}
