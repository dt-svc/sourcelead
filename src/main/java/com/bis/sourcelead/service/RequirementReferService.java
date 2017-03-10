package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementRefer;
import com.bis.sourcelead.entity.User;

public interface RequirementReferService {
	
	RequirementRefer addRequirementRefer(RequirementRefer requirementRefer) throws Exception;
	
	public void saveReferCandidate(User user, OrgLocations orgLocations,JSONObject jsonObj) throws Exception;
	
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

}
