package com.bis.sourcelead.workflow.service;



import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.workflow.dto.RequirementQuestionnaireTemplateDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowStagesDTO;
import com.bis.sourcelead.workflow.entity.RequirementQuestionnaireTemplate;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlow;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlowStages;

public interface RequirementWorkFlowService {
	RequirementWorkFlow saveRequirementWorkFlowDetails(RequirementWorkFlowDTO requirementWorkFlowDTO, User user, OrgLocations orgLocations) throws Exception;
	
	List<RequirementWorkFlow> getRequirementWorkFlowByOrgLocationId(Long orgLocationId)throws Exception;
	
	void saveRequirementDefaultWorkFlowAndStagesByOrgLocationId(OrgLocations orgLocations, User user)throws Exception;
	
	List<RequirementWorkFlowStagesDTO> getRequirementDefaultWorkFlowStages()throws Exception;
	
	ResponseList<RequirementWorkFlowDTO> getRequirementWorkFlowList(RequirementWorkFlowDTO requirementWorkFlowDTO, OrgLocations orgLocations)throws Exception;

	RequirementQuestionnaireTemplate saveQuestionnaireTemplate(RequirementQuestionnaireTemplate template) throws Exception;
	
	List<RequirementWorkFlowStagesDTO> getRequirementWorkflowStagesByWorkflowUuid(RequirementWorkFlowDTO requirementWorkFlowDTO)throws Exception;

	ResponseList<RequirementQuestionnaireTemplateDTO> getQuestionnaireTemplateListByOrgLocationId(RequirementQuestionnaireTemplateDTO questionnaireDTO , Long orgLocationId);

	RequirementQuestionnaireTemplate getQuestionnaireTemplateByLabelName(Long orgLocationId,  String questinnaireName);

	RequirementWorkFlowDTO getRequirementDefaultWorkflow()throws Exception;

	String deleteQuestionnaireTemplateById(String templateUuid);

	RequirementQuestionnaireTemplate getQuestionnaireTemplateById(String templateUuid);
	
	void saveRequirementWorkflowStageByWorkflowId(RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO, Long id)throws Exception;
	
	List<RequirementWorkFlowStagesDTO> getRequirementWorkFlowStagesBySeqOrderAndWorkflowUuid(String uuid, String workflowUuid)throws Exception;
	
    RequirementWorkFlowStages getRequirementWorkFlowStagesByUuid(String uuid)throws Exception;
	
	RequirementWorkFlowStages saveRequirementWorkFlowStages(RequirementWorkFlowStages requirementWorkFlowStages)throws Exception;

	String deleteRequirementWorkFlowStagesByOrgLocationIdAndUuid(String uuid);
	
	List<RequirementWorkFlowStages> getRequirementWorkFlowStagesListByOrgLocationId(Long orgLocationId);
	
	void deleteRequirementWorkFlowStagesByuuid(String uuid);
}
