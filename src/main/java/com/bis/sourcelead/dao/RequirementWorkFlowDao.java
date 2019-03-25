package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementDefaultWorkFlowStages;
import com.bis.sourcelead.entity.RequirementWorkFlow;
import com.bis.sourcelead.entity.RequirementWorkFlowStages;


public interface RequirementWorkFlowDao {
	
	RequirementWorkFlow saveRequirementWorkFlowDetails(RequirementWorkFlow requirementWorkFlow,OrgLocations orgLocations);
	
	RequirementWorkFlow  getRequirementWorkFlowById(Long id);
	
	List<RequirementWorkFlow> getRequirementWorkFlowByOrgLocationId(Long orgLocationId)throws Exception;
	
	List<RequirementDefaultWorkFlowStages> getRequirementDefaultWorkFlowStages()throws Exception;
	
	void saveRequirementWorkFlowStagesByOrgLocationId(RequirementWorkFlowStages requirementWorkFlowStages)throws Exception;
	
	

}