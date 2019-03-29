package com.bis.sourcelead.workflow.service;



import java.util.List;

import com.bis.sourcelead.dto.RequirementDefaultWorkFlowStagesDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.workflow.dto.RequirementQuestionnaireTemplateDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowDTO;
import com.bis.sourcelead.workflow.entity.RequirementQuestionnaireTemplate;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlow;

public interface RequirementWorkFlowService {
	RequirementWorkFlow saveRequirementWorkFlowDetails(RequirementWorkFlowDTO requirementWorkFlowDTO, User user, OrgLocations orgLocations) throws Exception;
	
	List<RequirementWorkFlow> getRequirementWorkFlowByOrgLocationId(Long orgLocationId)throws Exception;
	
	RequirementWorkFlowDTO saveRequirementWorkFlowAndStagesByOrgLocationId(OrgLocations orgLocations, User user, RequirementWorkFlowDTO requirementWorkFlowDTO)throws Exception;
	
	List<RequirementDefaultWorkFlowStagesDTO> getRequirementDefaultWorkFlowStages()throws Exception;
	
	ResponseList<RequirementWorkFlowDTO> getRequirementWorkFlowList(RequirementWorkFlowDTO requirementWorkFlowDTO, OrgLocations orgLocations)throws Exception;

	RequirementQuestionnaireTemplate saveQuestionnaireTemplate(RequirementQuestionnaireTemplate template) throws Exception;

	ResponseList<RequirementQuestionnaireTemplateDTO> getQuestionnaireTemplateListByOrgLocationId(RequirementQuestionnaireTemplateDTO questionnaireDTO , Long orgLocationId);

	
}
