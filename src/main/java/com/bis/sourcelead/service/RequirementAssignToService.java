package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.RequirementAssignTo;

public interface RequirementAssignToService {

	public List<RequirementAssignTo> getRequirementAssignToListByRequirementId(Long requirementId)throws Exception;  
	
}
