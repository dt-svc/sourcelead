package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.RequirementRefer;

public interface RequirementReferDao {
	
	RequirementRefer addRequirementRefer(RequirementRefer requirementRefer) throws Exception;
	
	public Boolean isProfessionalReferToRequirement(String email, Long requirementId,Long userId) throws Exception;
	
	public Boolean isReferToRequirement(String fromEmail, Long requirementId) throws Exception;
	
	public RequirementRefer getProfessionalReferToRequirement(String email, Long requirementId,Long userId) throws Exception;
	
	List<Long> getReferByMeRequirementsByUserId(Long userId,Integer offset,Integer maxResults) throws Exception;
	
	List<RequirementRefer> getRequirementReferListByEmail(String email) throws Exception;
	
	List<RequirementRefer> getReferedToMeRequirementsByUserId(Long userId,Integer offset,Integer maxResults,String jobTitle,String location,String postedOn) throws Exception;
	
	RequirementRefer getRequirementReferById(Long referId) throws Exception;
	
	RequirementRefer getAppliedRequirementReferByRequirementIdAndUserId(Long requirementId,Long userId) throws Exception;
	
	Integer getReferJobsByReferType(Long userId,String referType,Date startDate,Date endDate) throws Exception;
	
	Integer getReferJobsByReferTypeByReferedDate(Long userId,String referType,Date referedDate) throws Exception;
	
	List<RequirementRefer> getReferJobsListByReferTypeByReferedDate(Long userId,String referType,Date referedDate) throws Exception;
	
	List<RequirementRefer> getReferJobsListByReferType(Long userId,String referType,Date startDate,Date endDate) throws Exception;

	Integer getReferredByMeCountByStartDateAndEndDate(Long userId,Date startDate,Date endDate) throws Exception;
	
	List<RequirementRefer> getReferredByMeCountListByStartDateAndEndDate(Long userId,Date startDate,Date endDate) throws Exception;
	
	List<RequirementRefer> getLoginUserReferredListByUserId(Long userId,Long requirementId) throws Exception;

}
