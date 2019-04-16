package com.bis.sourcelead.workflow.service;



import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.workflow.dto.RequirementQuestionnaireTemplateDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowStagesDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowTasksDTO;
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

	ResponseList<RequirementQuestionnaireTemplateDTO> getQuestionnaireTemplateListByOrgLocationId(RequirementQuestionnaireTemplateDTO questionnaireDTO , String searchValue,  Long orgLocationId);

	RequirementQuestionnaireTemplate getQuestionnaireTemplateByLabelName(Long orgLocationId,  String questinnaireName);

	RequirementWorkFlowDTO getRequirementDefaultWorkflow()throws Exception;

	String deleteQuestionnaireTemplateById(String templateUuid);

	RequirementQuestionnaireTemplate getQuestionnaireTemplateById(String templateUuid);
	
	void saveRequirementWorkflowStageByWorkflowId(RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO, Long id, User user)throws Exception;
	
	RequirementWorkFlowStagesDTO getRequirementWorkFlowStagesBySeqOrderAndWorkflowUuid(String uuid, String workflowUuid)throws Exception;
	
    RequirementWorkFlowStages getRequirementWorkFlowStagesByUuid(String uuid)throws Exception;
	
	RequirementWorkFlowStages saveRequirementWorkFlowStages(RequirementWorkFlowStages requirementWorkFlowStages)throws Exception;

	String deleteRequirementWorkFlowStagesByOrgLocationIdAndUuid(String uuid);
	
	List<RequirementWorkFlowStages> getRequirementWorkFlowStagesListByOrgLocationId(Long orgLocationId);
	
	void deleteRequirementWorkFlowStagesByuuid(String uuid);
	
	List<RequirementQuestionnaireTemplateDTO> getRequirementQuestionnaireTemplates(Long id)throws Exception;
	
	void saveRequirementWorkflowStageByWorkflowIdAndOrgLocationId(RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO, RequirementWorkFlowStages requirementWorkFlowStages, Long id, User user)throws Exception;

	void saveRequirementWorkFlowTask(RequirementWorkFlowTasksDTO requirementWorkFlowTasksDTO, User user, OrgLocations orgLocations)throws Exception;
	
	ResponseList<RequirementWorkFlowTasksDTO> getRequirementWorkFlowTaskList(RequirementWorkFlowTasksDTO requirementWorkFlowTasksDTO, OrgLocations orgLocations, User user)throws Exception;

	void saveRequirementWorkflowStagesByWorkflowId(RequirementWorkFlow requirementWorkFlow, OrgLocations orgLocations, User user)throws Exception;
	
	Boolean saveRequirementWorkFlowStagesBySeqOrder(RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO, OrgLocations orgLocations, User user)throws Exception;
	
	void saveRequirementWorkFlowDetailsByWorkflowUuid(RequirementWorkFlowDTO requirementWorkFlowDTO, OrgLocations orgLocations, User user)throws Exception;

	void cloneRequirementWorkFlowDetailsByWorkflowUuid(RequirementWorkFlowDTO requirementWorkFlowDTO,
			OrgLocations orgLocations, User user) throws Exception;
}
