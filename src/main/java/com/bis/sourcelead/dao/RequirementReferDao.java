package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.RequirementRefer;

public interface RequirementReferDao {
	
	RequirementRefer addRequirementRefer(RequirementRefer requirementRefer) throws Exception;
	
	public Boolean isProfessionalReferToRequirement(String email, Long requirementId,Long userId) throws Exception;
	
	public Boolean isReferToRequirement(String fromEmail, Long requirementId) throws Exception;
	
	public RequirementRefer getProfessionalReferToRequirement(String email, Long requirementId,Long userId) throws Exception;

}
