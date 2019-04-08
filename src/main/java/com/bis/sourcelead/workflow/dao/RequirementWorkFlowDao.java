package com.bis.sourcelead.workflow.dao;


import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.workflow.dto.RequirementQuestionnaireTemplateDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowStagesDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowTasksDTO;
import com.bis.sourcelead.workflow.entity.RequirementDefaultWorkFlowStages;
import com.bis.sourcelead.workflow.entity.RequirementQuestionnaireTemplate;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlow;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlowStages;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlowTasks;


public interface RequirementWorkFlowDao {
	
	RequirementWorkFlow saveRequirementWorkFlowDetails(RequirementWorkFlow requirementWorkFlow,OrgLocations orgLocations);
	
	RequirementWorkFlow  getRequirementWorkFlowById(Long id);
	
	List<RequirementWorkFlow> getRequirementWorkFlowByOrgLocationId(Long orgLocationId)throws Exception;
	
	List<RequirementDefaultWorkFlowStages> getRequirementDefaultWorkFlowStages()throws Exception;
	
	void saveRequirementWorkFlowStagesByOrgLocationId(RequirementWorkFlowStages requirementWorkFlowStages)throws Exception;
	
	ResponseList<RequirementWorkFlowDTO> getRequirementWorkFlowsByOrgLocationId(RequirementWorkFlowDTO workFlowDTO, OrgLocations orgLocations)throws Exception;

	RequirementQuestionnaireTemplate saveQuestionnaireTemplate(RequirementQuestionnaireTemplate template) throws Exception;

	ResponseList<RequirementQuestionnaireTemplateDTO> getQuestionnaireTemplateListByOrgLocationId(RequirementQuestionnaireTemplateDTO questionnaireDto, Long orgLocationId);
	
	RequirementWorkFlow getRequirementWorkFlowByUuid(String Uuid)throws Exception;
	
	List<RequirementWorkFlowStages> getRequirementWorkflowStagesByUuid(String uuid)throws Exception;

	RequirementQuestionnaireTemplate getQuestionnaireTemplateByLabelName(Long orgLocationId, String questionnaireName);
	
	RequirementWorkFlow getRequirementDefaultWorkflow()throws Exception;

	String deleteQuestionnaireTemplateById(String templateUuid);

	RequirementQuestionnaireTemplate getQuestionnaireTemplateById(String templateUuid);
	
	RequirementWorkFlowStages getRequirementWorkFlowStageByUuidAndWorkflowUuid(String uuid, String workflowUuid)throws Exception;
	
	List<RequirementWorkFlowStages> getRequirementWorkflowStagesBySeqOrderAndWorkflowUuid(String workflowUuid, Integer id)throws Exception;
	
	// delete workflow stage
    RequirementWorkFlowStages getRequirementWorkFlowStagesByUuid(String uuid)throws Exception;
	
	String deleteRequirementWorkFlowStagesByOrgLocationIdAndUuid(String uuid);
	
	RequirementWorkFlowStages saveRequirementWorkFlowStages(RequirementWorkFlowStages requirementWorkFlowStages)throws Exception;
	
	List<RequirementWorkFlowStages> getRequirementWorkFlowStagesListByOrgLocationId(Long orgLocationId);

	void deleteRequirementWorkFlowStagesByuuid(String uuid);
	
	List<RequirementQuestionnaireTemplate> getRequirementQuestionnaireTemplates(Long id)throws Exception;
	
	void saveRequirementWorkFlowTask(RequirementWorkFlowTasks requirementWorkFlowTasks)throws Exception;
	
	ResponseList<RequirementWorkFlowTasksDTO> getRequirementWorkFlowTaskList(RequirementWorkFlowTasksDTO requirementWorkFlowTasksDTO, OrgLocations orgLocations, User user)throws Exception;

	
}