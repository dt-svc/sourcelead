package com.bis.sourcelead.workflow.dao;


import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowDTO;
import com.bis.sourcelead.workflow.entity.RequirementDefaultWorkFlowStages;
import com.bis.sourcelead.workflow.entity.RequirementQuestionnaireTemplate;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlow;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlowStages;


public interface RequirementWorkFlowDao {
	
	RequirementWorkFlow saveRequirementWorkFlowDetails(RequirementWorkFlow requirementWorkFlow,OrgLocations orgLocations);
	
	RequirementWorkFlow  getRequirementWorkFlowById(Long id);
	
	List<RequirementWorkFlow> getRequirementWorkFlowByOrgLocationId(Long orgLocationId)throws Exception;
	
	List<RequirementDefaultWorkFlowStages> getRequirementDefaultWorkFlowStages()throws Exception;
	
	void saveRequirementWorkFlowStagesByOrgLocationId(RequirementWorkFlowStages requirementWorkFlowStages)throws Exception;
	
	ResponseList<RequirementWorkFlowDTO> getRequirementWorkFlowsByOrgLocationId(RequirementWorkFlowDTO workFlowDTO, OrgLocations orgLocations)throws Exception;

	RequirementQuestionnaireTemplate saveQuestionnaireTemplate(RequirementQuestionnaireTemplate template) throws Exception;
	
	RequirementWorkFlow getRequirementWorkFlowByUuid(String Uuid)throws Exception;
	
	

}