package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.RequirementAssignTo;

public interface RequirementAssignToDao {

	public List<RequirementAssignTo> getRequirementAssignToListByRequirementId(Long requirementId)throws Exception;
	
}
