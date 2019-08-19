package com.bis.sourcelead.workflow.dao;


import java.util.LinkedHashMap;
import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.workflow.dto.RequirementQuestionnaireFeedbackTemplateDTO;
import com.bis.sourcelead.workflow.dto.RequirementQuestionnaireTemplateDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowStagesDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowTasksDTO;
import com.bis.sourcelead.workflow.entity.RequirementDefaultWorkFlowStages;
import com.bis.sourcelead.workflow.entity.RequirementQuestionnaireFeedbackTemplate;
import com.bis.sourcelead.workflow.entity.RequirementQuestionnaireTemplate;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlow;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlowStages;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlowTasks;


public interface RequirementWorkFlowDao {
	
	RequirementWorkFlow saveRequirementWorkFlowDetails(RequirementWorkFlow requirementWorkFlow,OrgLocations orgLocations);
	
	RequirementWorkFlow  getRequirementWorkFlowById(Long id);
	
	List<RequirementWorkFlow> getRequirementWorkFlowByOrgLocationId(Long orgLocationId)throws Exception;
	
	List<RequirementDefaultWorkFlowStages> getRequirementDefaultWorkFlowStages()throws Exception;
	
	RequirementWorkFlowStages saveRequirementWorkFlowStagesByOrgLocationId(RequirementWorkFlowStages requirementWorkFlowStages)throws Exception;
	
	ResponseList<RequirementWorkFlowDTO> getRequirementWorkFlowsByOrgLocationId(RequirementWorkFlowDTO workFlowDTO, OrgLocations orgLocations)throws Exception;

	RequirementQuestionnaireTemplate saveQuestionnaireTemplate(RequirementQuestionnaireTemplate template) throws Exception;
	
	RequirementQuestionnaireTemplate getQuestionnaireByTemplateId(long templateId) throws Exception;

	ResponseList<RequirementQuestionnaireTemplateDTO> getQuestionnaireTemplateListByOrgLocationId(RequirementQuestionnaireTemplateDTO questionnaireDto, String searchValue, Long orgLocationId);
	
	RequirementWorkFlow getRequirementWorkFlowByUuid(String Uuid, Long orgLocationId)throws Exception;
	
	List<RequirementWorkFlowStages> getRequirementWorkflowStagesListByUuid(String uuid, Long orgLocationId)throws Exception;

	RequirementQuestionnaireTemplate getQuestionnaireTemplateByLabelName(Long orgLocationId, String questionnaireName);
	
	RequirementWorkFlow getRequirementDefaultWorkflow(Long orgLocationId)throws Exception;

	String deleteQuestionnaireTemplateById(Long id);

	RequirementQuestionnaireTemplate getQuestionnaireTemplateById(String templateUuid);
	
	RequirementWorkFlowStages getRequirementWorkFlowStageByUuidAndWorkflowUuid(String uuid, String workflowUuid, Long orgLocationId)throws Exception;
	
	List<RequirementWorkFlowStages> getRequirementWorkflowStagesBySeqOrderAndWorkflowUuid(String workflowUuid, Integer id, Long orgLocationId)throws Exception;
	
	// delete workflow stage
    RequirementWorkFlowStages getRequirementWorkFlowStagesByUuid(String uuid, Long orgLocationId)throws Exception;
	
	String deleteRequirementWorkFlowStagesByOrgLocationIdAndUuid(String uuid);
	
	RequirementWorkFlowStages saveRequirementWorkFlowStages(RequirementWorkFlowStages requirementWorkFlowStages)throws Exception;
	
	List<RequirementWorkFlowStages> getRequirementWorkFlowStagesListByOrgLocationId(Long orgLocationId);

	void deleteRequirementWorkFlowStagesByuuid(Long id);
	
	List<RequirementQuestionnaireTemplate> getRequirementQuestionnaireTemplates(Long id)throws Exception;
	
	void saveRequirementWorkFlowTask(RequirementWorkFlowTasks requirementWorkFlowTasks)throws Exception;
	
	ResponseList<RequirementWorkFlowTasksDTO> getRequirementWorkFlowTaskList(RequirementWorkFlowTasksDTO requirementWorkFlowTasksDTO, OrgLocations orgLocations, User user)throws Exception;

	Boolean deleteApplicationWorkFlowByuuid(Long workflowuuid);

	List<RequirementWorkFlowTasks> getworkflowTasksBystagesId(Long workstageId);
	
	List<RequirementWorkFlowStages> getNextRequirementWorkflowStagesListByUuid(RequirementWorkFlowDTO requirementWorkFlowDTO, Long id)throws Exception;
	
	List<RequirementWorkFlowStages> getInactiveRequirementWorkflowStagesListByUuidAndOrgLocationId(String uuid, Long id)throws Exception;
	
	void deleteInactiveRequirementWorkflowStagesByStageId(List<RequirementWorkFlowStages> requirementWorkFlowStage)throws Exception;

	List<RequirementWorkFlowStages> getRequirementworkflowStageByQuestionnaireId(Long id, Long orgLocationId)throws Exception;
	
	List<RequirementWorkFlow> validateWorkflowName(Long id, String name, String uuid)throws Exception;
	
	List<RequirementWorkFlowStages> validateWorkflowStageName(Long id, String name, Long workflowId, String stageUuid)throws Exception;
	
	 RequirementWorkFlow getRequirementWorkFlowByOrgLocId(Long orgLocationId) throws Exception;

	List<RequirementWorkFlowStages> getRequirementWorkflowStagesByWorkflowIdAndOrglocations(Long workflowId, OrgLocations orgLocations);
	
	List<RequirementWorkFlowStages> getRequirementWorkflowStagesBySeqOrderWorkflowIdAndStageName(Long workflowId, String stageName, OrgLocations orgLocations);
	
	List<Requirement> getRequirementListByWorkflowIdAndOrgLocationId(String uuid, Long id)throws Exception;
	
	List<RequirementWorkFlowStages> getRequirementWorkflowStagesListByUuidAndOrgLocationId(String uuid, Long id, String stageUuid)throws Exception;

	LinkedHashMap<String, String> getColumnsMapbyWorkflowStage(String shortCode, Long workflowId, Long orgLocationId);

	RequirementWorkFlowStages getRequirementWorkFlowStagesDetails(String shortCode, Long workflowId, Long orgLocationId);

	RequirementSubmission validateWorkflowEditable(Long requirementId, Long orgLocationId);
	
	RequirementQuestionnaireFeedbackTemplate saveQuestionnaireFeedbackTemplate(RequirementQuestionnaireFeedbackTemplate qemplate)throws Exception;
	
	RequirementQuestionnaireFeedbackTemplateDTO getQuestionnaireTemplateFeedbackList(String uuid)throws Exception;

	List<RequirementSubmission> validateWorkflowUpdate(Long workflowId, Long orgLocationId);
	
	RequirementQuestionnaireFeedbackTemplate getQuestionnaireTemplateFeedbackByUuid(String uuid) throws Exception;
}