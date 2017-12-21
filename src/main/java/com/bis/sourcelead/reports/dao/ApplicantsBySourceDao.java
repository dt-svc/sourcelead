package com.bis.sourcelead.reports.dao;

import java.util.Date;
import java.util.List;

public interface ApplicantsBySourceDao {

	Integer getCrowdsourcingApplicantsCount(List<Long> jobIdList,Date[] searchDates);
	
	Integer getRequirementsCount(Long orgLocationId) throws Exception;
	
	Integer getCrowdsourcingApplicantsCountByStage(List<Long> jobIdList,Date[] searchDates,String stage);
	
	Integer getVendorSourceApplicantsCountByStage(List<Long> jobIdList,Date[] searchDates,String stage);
	
	Integer getFreelanceRecruiterSourceApplicantsCountByStage(List<Long> jobIdList,Date[] searchDates,String stage);
	
	Integer getInternalSourceApplicantsCountByStage(List<Long> jobIdList,Date[] searchDates,String stage,Long orgLocationId);
}
