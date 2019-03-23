package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementWorkFlow;

public interface RequirementWorkFlowDao {
	
	RequirementWorkFlow saveRequirementWorkFlowDetails(RequirementWorkFlow requirementWorkFlow,OrgLocations orgLocations);
	
	RequirementWorkFlow  getRequirementWorkFlowById(Long id);

}