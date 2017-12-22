package com.bis.sourcelead.reports.service;

import java.util.Date;
import java.util.List;

public interface ApplicantsBySourceService {

	Integer getCrowdsourcingApplicantsCount(List<Long> jobIdList,Date[] searchDates);
	
	Integer getRequirementsCount(Long orgLocationId) throws Exception;
	
	Integer getCrowdsourcingApplicantsCountByStage(List<Long> jobIdList,Date[] searchDates,String stage);
	
	Integer getVendorSourceApplicantsCountByStage(List<Long> jobIdList,Date[] searchDates,String stage);
	
	Integer getFreelanceRecruiterSourceApplicantsCountByStage(List<Long> jobIdList,Date[] searchDates,String stage);
	
	Integer getInternalSourceApplicantsCountByStage(List<Long> jobIdList,Date[] searchDates,String stage,Long orgLocationId);
	
}
