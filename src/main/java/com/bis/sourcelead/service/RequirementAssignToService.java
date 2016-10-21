package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.RequirementAssignTo;

public interface RequirementAssignToService {

	public List<RequirementAssignTo> getRequirementAssignToListByRequirementId(Long requirementId)throws Exception; 
	
	public RequirementAssignTo getRequirementAssignToByRequirementIdandAssignToId(Long requirementId,Long assignToId)  throws Exception;
	
	public boolean deleteRequirementAssignToByRequirementId(Long requirementId)throws Exception;
}
