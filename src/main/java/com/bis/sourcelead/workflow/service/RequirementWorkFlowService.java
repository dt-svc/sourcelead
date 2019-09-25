package com.bis.sourcelead.workflow.service;



import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.workflow.dto.RequirementQuestionnaireFeedbackTemplateDTO;
import com.bis.sourcelead.workflow.dto.RequirementQuestionnaireTasksDTO;
import com.bis.sourcelead.workflow.dto.RequirementQuestionnaireTemplateDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowStagesDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowTasksDTO;
import com.bis.sourcelead.workflow.entity.RequirementQuestionnaireFeedbackTemplate;
import com.bis.sourcelead.workflow.entity.RequirementQuestionnaireTemplate;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlow;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlowStages;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlowTasks;

public interface RequirementWorkFlowService {
	RequirementWorkFlow saveRequirementWorkFlowDetails(RequirementWorkFlowDTO requirementWorkFlowDTO, User user, OrgLocations orgLocations) throws Exception;
	
	List<RequirementWorkFlow> getRequirementWorkFlowByOrgLocationId(OrgLocations orgLocations)throws Exception;
	
	RequirementWorkFlow saveRequirementDefaultWorkFlowAndStagesByOrgLocationId(OrgLocations orgLocations, User user)throws Exception;
	
	List<RequirementWorkFlowStagesDTO> getRequirementDefaultWorkFlowStages()throws Exception;
	
	ResponseList<RequirementWorkFlowDTO> getRequirementWorkFlowList(RequirementWorkFlowDTO requirementWorkFlowDTO, OrgLocations orgLocations , User user)throws Exception;

	RequirementQuestionnaireTemplate saveQuestionnaireTemplate(RequirementQuestionnaireTemplate template) throws Exception;
	
	RequirementQuestionnaireTemplate getQuestionnaireByTemplateId(long templateId) throws Exception;
	
	List<RequirementWorkFlowStagesDTO> getRequirementWorkflowStagesByWorkflowUuid(RequirementWorkFlowDTO requirementWorkFlowDTO, OrgLocations orgLocations)throws Exception;

	ResponseList<RequirementQuestionnaireTemplateDTO> getQuestionnaireTemplateListByOrgLocationId(RequirementQuestionnaireTemplateDTO questionnaireDTO , String searchValue,  Long orgLocationId);

	RequirementQuestionnaireTemplate getQuestionnaireTemplateByLabelName(Long orgLocationId,  String questinnaireName);

	RequirementWorkFlowDTO getRequirementDefaultWorkflow()throws Exception;

	String deleteQuestionnaireTemplateById(String templateUuid, Long id)throws Exception;

	RequirementQuestionnaireTemplate getQuestionnaireTemplateById(String templateUuid);
	
	void saveRequirementWorkflowStageByWorkflowId(RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO, OrgLocations orgLocations, User user)throws Exception;
	
	List<RequirementWorkFlowStagesDTO> getRequirementWorkFlowStagesBySeqOrderAndWorkflowUuid(String uuid, String workflowUuid, OrgLocations orgLocations, String currentStageUuid)throws Exception;
	
    RequirementWorkFlowStages getRequirementWorkFlowStagesByUuid(String uuid, OrgLocations orgLocations)throws Exception;
	
	RequirementWorkFlowStages saveRequirementWorkFlowStages(RequirementWorkFlowStages requirementWorkFlowStages)throws Exception;

	String deleteRequirementWorkFlowStagesByOrgLocationIdAndUuid(String uuid);
	
	List<RequirementWorkFlowStages> getRequirementWorkFlowStagesListByOrgLocationId(OrgLocations orgLocations);
	
	void deleteRequirementWorkFlowStagesByuuid(String uuid, OrgLocations orgLocations);
	
	List<RequirementQuestionnaireTemplateDTO> getRequirementQuestionnaireTemplates(Long id)throws Exception;
	
	String saveRequirementWorkflowStageByWorkflowIdAndOrgLocationId(RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO, RequirementWorkFlowStages requirementWorkFlowStages, OrgLocations orgLocations, User user)throws Exception;

	ResponseList<RequirementWorkFlowTasksDTO> getRequirementWorkFlowTaskList(RequirementWorkFlowTasksDTO requirementWorkFlowTasksDTO, OrgLocations orgLocations, User user)throws Exception;
	
	Boolean saveRequirementWorkFlowStagesBySeqOrder(RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO, OrgLocations orgLocations, User user)throws Exception;
	
	void saveRequirementWorkFlowDetailsByWorkflowUuid(RequirementWorkFlowDTO requirementWorkFlowDTO, OrgLocations orgLocations, User user)throws Exception;
	void cloneRequirementWorkFlowDetailsByWorkflowUuid(RequirementWorkFlowDTO requirementWorkFlowDTO,
			OrgLocations orgLocations, User user) throws Exception;
	Boolean deleteApplicationWorkFlowByuuid(String workflowuuid, OrgLocations orgLocations) throws Exception;
	
	String saveRequirementWorkFlowTask(RequirementWorkFlowTasksDTO requirementWorkFlowTasksDTO, OrgLocations orgLocations, User user)throws Exception;
	
	RequirementWorkFlowStages getRequirementWorkFlowStageForEditStage(RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO, OrgLocations orgLocations)throws Exception;
	
	List<RequirementWorkFlowStagesDTO> getNextRequirementWorkflowStagesByWorkflowUuid(RequirementWorkFlowDTO requirementWorkFlowDTO, OrgLocations orgLocations)throws Exception;
	
	void deleteInactiveRequirementWorkflowStagesByWorkflowIdAndOrgLocationId(String uuid, Long id)throws Exception;
	
	Boolean validateWorkflowName(Long id, String name, String uuid)throws Exception;
	
	String validateWorkflowStageName(Long id, String name, String uuid, String stageUuid)throws Exception;
	
	RequirementWorkFlow getRequirementWorkFlowByOrgLocId(Long orgLocationId)throws Exception;
	
	RequirementWorkFlow saveRequirementWorkFlowDetailsInfo(RequirementWorkFlowDTO requirementWorkFlowDTO, User user, OrgLocations orgLocations);
	
	void saveWorkflowIdInAllOrgsForRequirement(RequirementWorkFlow requirementWorkFlow, OrgLocations orgLocations)throws Exception;

	List<RequirementWorkFlowStages> getRequirementWorkflowStagesByWorkflowIdAndOrglocations(Long workflowId, OrgLocations orgLocations);
	
	String validateStageShortCode(Long id, String shortCode, String workflowUuid, String stageUuid)throws Exception;
	
	TreeMap<Long, String> checkWorkflowInRequirements(String workflowUuid, Long id)throws Exception;
	
	LinkedHashMap<String, String> getColumnsMapbyWorkflowStage(String shortCode, Long workflowId, Long orgLocationId);
	
	RequirementWorkFlowStages getRequirementWorkFlowStagesDetails(String shortCode, Long workflowId, Long orgLocationId);
	
	LinkedHashMap<String , String> getRequirementWorkflowStagesBySeqOrderWorkflowIdAndStageName(Long workflowId, OrgLocations orgLocation, String submissionType)throws Exception;
	
	String  sendEmailToCandidateWithQuestionnaireTemplate(HashMap<String , Object> paramList) throws Exception;

	RequirementSubmission validateWorkflowEditable(Long requirementId, Long orgLocationId);	
	
	RequirementQuestionnaireFeedbackTemplate saveQuestionnaireFeedbackTemplate(RequirementQuestionnaireFeedbackTemplateDTO qemplateDTO, OrgLocations orgLocations, User user)throws Exception;

	RequirementQuestionnaireFeedbackTemplateDTO getQuestionnaireTemplateFeedbackList(String uuid)throws Exception;

	void copyWorkflowStagesAndTask(String uuid, User user, OrgLocations orgLocations,
			RequirementWorkFlowTasksDTO requirementWorkFlowTasksDTO);

	String getRequirementworkflowTaskListById(Long stageId, OrgLocations orgLocations, User user) throws Exception;

	List<RequirementSubmission> validateWorkflowUpdate(Long workflowId, String workflowUuid, Long orgLocationId);
	
	RequirementQuestionnaireTasksDTO getQuestionnaireIdAssignedToStage(Long workflowId, String stageName, Long orgLocationId);
	
	String saveDefaultRequirementWorkflowAndStages()throws Exception;

	Candidate getCandidateByRequirementSubmissionId(Long submissionId);
	
	String updateDefaultWorkflowInRequirementProcedure() throws Exception;
	
	RequirementQuestionnaireFeedbackTemplate getSubmittedQuestionnaireUuidByWorkflowStage(String stageUuid, Long submissionId, Long orgLocationId) throws Exception;
	
	RequirementQuestionnaireTemplateDTO getSavedQuestionnaire(String templateUuid, String stageId, String submissionId)throws Exception;

	String deleteAssignedTask(String currentStageUuid, Long id);

	String updateFeedbackQuestionnaireTimer(String questTemplUuid, Long stageId, Long submissionId, Integer minutes,
			Integer seconds);
	
	RequirementWorkFlow getRequirementWorkflowByNameAndOrgLocationId(String workflowId, long id)throws Exception;
}
