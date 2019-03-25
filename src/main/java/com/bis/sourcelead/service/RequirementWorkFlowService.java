package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.RequirementWorkFlowDTO;
import com.bis.sourcelead.dto.RequirementWorkFlowStagesDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementWorkFlow;
import com.bis.sourcelead.entity.User;

public interface RequirementWorkFlowService {
	RequirementWorkFlow saveRequirementWorkFlowDetails(RequirementWorkFlowDTO requirementWorkFlowDTO, User user, OrgLocations orgLocations) throws Exception;
	
	List<RequirementWorkFlow> getRequirementWorkFlowByOrgLocationId(Long orgLocationId)throws Exception;
	
	void saveRequirementWorkFlowStagesByOrgLocationId(OrgLocations orgLocations, User user, RequirementWorkFlowDTO requirementWorkFlowDTO)throws Exception;
	
	List<RequirementWorkFlowStagesDTO> getRequirementDefaultWorkFlowStages()throws Exception;
}
