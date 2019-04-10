package com.bis.sourcelead.workflow.service.Impl;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.workflow.dao.RequirementWorkFlowDao;
import com.bis.sourcelead.workflow.dto.RequirementQuestionnaireTemplateDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowStagesDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowTasksDTO;
import com.bis.sourcelead.workflow.entity.RequirementDefaultWorkFlowStages;
import com.bis.sourcelead.workflow.entity.RequirementQuestionnaireTemplate;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlow;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlowStages;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlowTasks;
import com.bis.sourcelead.workflow.service.RequirementWorkFlowService;

@Service
public class RequirementWorkFlowServiceImpl implements RequirementWorkFlowService {
	private static final Logger logger = LoggerFactory.getLogger(RequirementWorkFlowServiceImpl.class);
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	RequirementWorkFlowDao requirementWorkFlowDao;

	@Override
	public RequirementWorkFlow saveRequirementWorkFlowDetails(RequirementWorkFlowDTO requirementWorkFlowDTO, User user,
			OrgLocations orgLocations) throws Exception {
			RequirementWorkFlow requirementWorkFlow = null;
			RequirementWorkFlow workFlow = null;
			try {
				if(StringUtils.isNotBlank(requirementWorkFlowDTO.getReqWorkFlowId())) {
					requirementWorkFlow =requirementWorkFlowDao.getRequirementWorkFlowById(Long.parseLong(requirementWorkFlowDTO.getReqWorkFlowId()));
				}else {
					requirementWorkFlow = new RequirementWorkFlow();
				}
				requirementWorkFlow = saveRequirementWorkFlowDetailsInfo(requirementWorkFlowDTO,user,orgLocations);
				workFlow = requirementWorkFlowDao.saveRequirementWorkFlowDetails(requirementWorkFlow, orgLocations);
				saveRequirementWorkflowStagesByWorkflowId(workFlow,orgLocations, user);
			} catch (Exception e) {
				logger.error("Exception caugth in savePetitionDetails service" + e.getMessage());
				e.printStackTrace();
			}
	
			  return requirementWorkFlow;
		}
	
	
   private RequirementWorkFlow saveRequirementWorkFlowDetailsInfo(RequirementWorkFlowDTO requirementWorkFlowDTO, User user, OrgLocations orgLocations) throws Exception {
		
	   RequirementWorkFlow reqWorkFlow = null;
		Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
		if (requirementWorkFlowDTO.getReqWorkFlowId() != null && StringUtils.isNotEmpty(requirementWorkFlowDTO.getReqWorkFlowId())) {
			reqWorkFlow = requirementWorkFlowDao.getRequirementWorkFlowById(Long.parseLong(requirementWorkFlowDTO.getReqWorkFlowId()));
		} else {
			reqWorkFlow = new RequirementWorkFlow();
			reqWorkFlow.setCreatedBy(user);
			reqWorkFlow.setCreatedDate(currentDate);
			reqWorkFlow.setNoOfStages(10);
		}
		if(orgLocations != null){
			reqWorkFlow.setOrgLocationId(orgLocations);
			}
		reqWorkFlow.setWorkFlowName(requirementWorkFlowDTO.getWorkFlowName());
		reqWorkFlow.setDescription(requirementWorkFlowDTO.getDescription());
		
		
		if(requirementWorkFlowDTO.getNoOfStages() != null) {
		reqWorkFlow.setNoOfStages(Integer.parseInt(requirementWorkFlowDTO.getNoOfStages()));
		}
		reqWorkFlow.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		reqWorkFlow.setStatus(Constants.RECORD_ACTIVE);
		
		reqWorkFlow.setUpdatedBy(user);
		reqWorkFlow.setUpdatedDate(currentDate);

		return reqWorkFlow;
		
	}

	@Override
	public List<RequirementWorkFlow> getRequirementWorkFlowByOrgLocationId(Long orgLocationId) throws Exception {
		List<RequirementWorkFlow> requirementWorkFlow = requirementWorkFlowDao
				.getRequirementWorkFlowByOrgLocationId(orgLocationId);
		return requirementWorkFlow;
	}

	@Override
	public void saveRequirementDefaultWorkFlowAndStagesByOrgLocationId(OrgLocations orgLocations, User user) throws Exception {
		List<RequirementDefaultWorkFlowStages> requirementDefaultWorkFlowStages = requirementWorkFlowDao.getRequirementDefaultWorkFlowStages();
		RequirementWorkFlowDTO requirementWorkFlowDTO = new RequirementWorkFlowDTO(); 
		//setting default value to display in jsp.
		Integer workflowSize = requirementDefaultWorkFlowStages.size();
		StringBuilder jobWorkflow = new StringBuilder(" Job Workflow");
		requirementWorkFlowDTO.setWorkFlowName(Constants.DEFAULT + jobWorkflow);
		requirementWorkFlowDTO.setDescription(Constants.DEFAULT + jobWorkflow);
		requirementWorkFlowDTO.setNoOfStages(workflowSize.toString());

		// saving and fetching a new default workflow for an org..
		List<RequirementWorkFlow> requirementWorkFlow= requirementWorkFlowDao.getRequirementWorkFlowByOrgLocationId(orgLocations.getId());
		if (requirementWorkFlow.size() == 0) {
			RequirementWorkFlow newRequirementWorkFlow = saveRequirementWorkFlowDetails(requirementWorkFlowDTO, user, orgLocations);
			
			/*// saving default stages..
			if (newRequirementWorkFlow != null) {
				saveRequirementWorkflowStagesByWorkflowId(newRequirementWorkFlow , orgLocations, user);
				
				for (int i = 0; i < requirementDefaultWorkFlowStages.size(); i++) {
					RequirementWorkFlowStages requirementWorkFlowStages = new RequirementWorkFlowStages();
					requirementWorkFlowStages.setRequirementWorkFlowId(newRequirementWorkFlow);
					requirementWorkFlowStages.setOrgLocationId(orgLocations);
					requirementWorkFlowStages.setStageName(requirementDefaultWorkFlowStages.get(i).getStageName());
					requirementWorkFlowStages.setDescription(requirementDefaultWorkFlowStages.get(i).getDescription());
					requirementWorkFlowStages.setSeqOrder(requirementDefaultWorkFlowStages.get(i).getSeqOrder());
					requirementWorkFlowStages.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
					requirementWorkFlowStages.setConfigureType(Constants.DEFAULT);
					requirementWorkFlowStages.setEditable(requirementDefaultWorkFlowStages.get(i).getEditable());
					requirementWorkFlowStages.setStatus(requirementDefaultWorkFlowStages.get(i).getStatus());
					requirementWorkFlowStages.setCreatedDate(new Date());
					requirementWorkFlowStages.setCreatedBy(user);
					requirementWorkFlowStages.setUpdatedDate(new Date());
					requirementWorkFlowStages.setUpdatedBy(user);
					requirementWorkFlowDao.saveRequirementWorkFlowStagesByOrgLocationId(requirementWorkFlowStages);
				}
				
			}*/
		}
	}


	@Override
	public List<RequirementWorkFlowStagesDTO> getRequirementDefaultWorkFlowStages() throws Exception {
		RequirementWorkFlowDTO requirementWorkFlowDTO = getRequirementDefaultWorkflow();
		List<RequirementWorkFlowStagesDTO> newDefaultStages = getRequirementWorkflowStagesByWorkflowUuid(requirementWorkFlowDTO);
		return newDefaultStages;
	}


	@Override
	public ResponseList<RequirementWorkFlowDTO> getRequirementWorkFlowList(RequirementWorkFlowDTO workFlowDTO, OrgLocations orgLocations) throws Exception {
		
		RequirementWorkFlow requirementWorkFlow = requirementWorkFlowDao.getRequirementWorkFlowByUuid(workFlowDTO.getUuid());
		
		if (workFlowDTO.getUuid() != null && workFlowDTO.getStatus() == 1) {
			requirementWorkFlow.setStatus(Constants.RECORD_INACTIVE);
			requirementWorkFlowDao.saveRequirementWorkFlowDetails(requirementWorkFlow, orgLocations);
		}
		if (workFlowDTO.getUuid() != null && workFlowDTO.getStatus() == 0) {
			requirementWorkFlow.setStatus(Constants.RECORD_ACTIVE);
			requirementWorkFlowDao.saveRequirementWorkFlowDetails(requirementWorkFlow, orgLocations);
		}
		return requirementWorkFlowDao.getRequirementWorkFlowsByOrgLocationId(workFlowDTO, orgLocations);
	}
	
	@Override
	public RequirementQuestionnaireTemplate saveQuestionnaireTemplate(RequirementQuestionnaireTemplate template) throws Exception {
       return requirementWorkFlowDao.saveQuestionnaireTemplate(template);
		
	}


	@Override
	public ResponseList<RequirementQuestionnaireTemplateDTO> getQuestionnaireTemplateListByOrgLocationId(
			RequirementQuestionnaireTemplateDTO questionnaireDto,  String searchValue, Long orgLocationId) {
		return requirementWorkFlowDao.getQuestionnaireTemplateListByOrgLocationId(questionnaireDto, searchValue, orgLocationId);
    }
	
	
	public List<RequirementWorkFlowStagesDTO> getRequirementWorkflowStagesByWorkflowUuid(RequirementWorkFlowDTO requirementWorkFlowDTO) throws Exception {
		List<RequirementWorkFlowStages> requirementWorkFlowStages = requirementWorkFlowDao.getRequirementWorkflowStagesByUuid(requirementWorkFlowDTO.getUuid());
		RequirementWorkFlow requirementWorkFlow = requirementWorkFlowDao.getRequirementWorkFlowByUuid(requirementWorkFlowDTO.getUuid());

		List<RequirementWorkFlowStagesDTO> workFlowStagesDTO = new ArrayList<>();
		RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO = null;
		
		if(requirementWorkFlow != null) {
		for(RequirementWorkFlowStages workFlowStages : requirementWorkFlowStages) {
			requirementWorkFlowStagesDTO = new RequirementWorkFlowStagesDTO();
			requirementWorkFlowStagesDTO.setWorkFlowName(requirementWorkFlow.getWorkFlowName());
			requirementWorkFlowStagesDTO.setWorkflowDescription(requirementWorkFlow.getDescription());
			requirementWorkFlowStagesDTO.setRequirementWorkFlowUuid(requirementWorkFlow.getUuid());
			requirementWorkFlowStagesDTO.setStageName(workFlowStages.getStageName());
			requirementWorkFlowStagesDTO.setEditable(workFlowStages.getEditable());
			requirementWorkFlowStagesDTO.setUuid(workFlowStages.getUuid());
			workFlowStagesDTO.add(requirementWorkFlowStagesDTO);
		}	
		}
		return workFlowStagesDTO;
	}


	@Override
	public RequirementQuestionnaireTemplate getQuestionnaireTemplateByLabelName(Long orgLocationId,
			String questionnaireName) {
		return requirementWorkFlowDao.getQuestionnaireTemplateByLabelName(orgLocationId, questionnaireName);
	}
	
	public RequirementWorkFlowDTO getRequirementDefaultWorkflow()throws Exception {
		RequirementWorkFlowDTO requirementWorkFlowDTO = new RequirementWorkFlowDTO();
		RequirementWorkFlow defaultWorkflow = requirementWorkFlowDao.getRequirementDefaultWorkflow();
		requirementWorkFlowDTO.setId(defaultWorkflow.getId());
		requirementWorkFlowDTO.setWorkFlowName(defaultWorkflow.getWorkFlowName());
		requirementWorkFlowDTO.setDescription(defaultWorkflow.getDescription());
		requirementWorkFlowDTO.setUuid(defaultWorkflow.getUuid());
		return requirementWorkFlowDTO;
	}


	@Override
	public String deleteQuestionnaireTemplateById(String templateUuid) {
		return requirementWorkFlowDao.deleteQuestionnaireTemplateById(templateUuid);
	}


	@Override
	public RequirementQuestionnaireTemplate getQuestionnaireTemplateById(String templateUuid) {
		return requirementWorkFlowDao.getQuestionnaireTemplateById(templateUuid);
	}
	
	@Override
	public void saveRequirementWorkflowStageByWorkflowId(RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO, Long id, User user) throws Exception {
		List<RequirementWorkFlowStages> workFlowStages = null;
		OrgLocations orgId = new OrgLocations();
		orgId.setId(id);
		RequirementWorkFlow workflow = requirementWorkFlowDao.getRequirementWorkFlowByUuid(requirementWorkFlowStagesDTO.getRequirementWorkFlowUuid().trim());
		RequirementWorkFlowStages requirementWorkFlowStages = requirementWorkFlowDao.getRequirementWorkFlowStageByUuidAndWorkflowUuid(requirementWorkFlowStagesDTO.getUuid(), requirementWorkFlowStagesDTO.getRequirementWorkFlowUuid().trim());
		if(requirementWorkFlowStages != null && !requirementWorkFlowStagesDTO.getUuid().equalsIgnoreCase("Select")) {
			
			requirementWorkFlowStagesDTO.setSeqOrder(requirementWorkFlowStages.getSeqOrder());
			
			List<RequirementWorkFlowStages> workFlowStagesbySeqOrder = requirementWorkFlowDao.getRequirementWorkflowStagesBySeqOrderAndWorkflowUuid(requirementWorkFlowStagesDTO.getRequirementWorkFlowUuid().trim(), requirementWorkFlowStagesDTO.getSeqOrder());
			
			if(workFlowStagesbySeqOrder.size() > 1) {
			for(RequirementWorkFlowStages stages : workFlowStagesbySeqOrder) {
				int stageSeqorder = stages.getSeqOrder();
				stageSeqorder++;
				stages.setSeqOrder(stageSeqorder);
				requirementWorkFlowDao.saveRequirementWorkFlowStages(stages);
			}
				saveRequirementWorkflowStageByWorkflowIdAndOrgLocationId(requirementWorkFlowStagesDTO, requirementWorkFlowStages, id, user);
			}else {
				int stageSeqorder = requirementWorkFlowStages.getSeqOrder();
				stageSeqorder++;
				requirementWorkFlowStages.setSeqOrder(stageSeqorder);
				saveRequirementWorkflowStageByWorkflowIdAndOrgLocationId(requirementWorkFlowStagesDTO, requirementWorkFlowStages, id, user);
			}
			
			 workFlowStages = requirementWorkFlowDao.getRequirementWorkflowStagesByUuid(requirementWorkFlowStagesDTO.getRequirementWorkFlowUuid().trim());
			 workflow.setNoOfStages(workFlowStages.size());
			 requirementWorkFlowDao.saveRequirementWorkFlowDetails(workflow, orgId);
		}
	}


	@Override
	public RequirementWorkFlowStagesDTO getRequirementWorkFlowStagesBySeqOrderAndWorkflowUuid(String uuid, String workflowUuid) throws Exception {
		
		RequirementWorkFlowStages requirementWorkFlowStages = requirementWorkFlowDao.getRequirementWorkFlowStageByUuidAndWorkflowUuid(uuid, workflowUuid);
		RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO = new RequirementWorkFlowStagesDTO();
		if(requirementWorkFlowStages != null) {
		requirementWorkFlowStagesDTO.setSeqOrder(requirementWorkFlowStages.getSeqOrder());
		}
		List<RequirementWorkFlowStages> workFlowStagesbySeqOrder = requirementWorkFlowDao.getRequirementWorkflowStagesBySeqOrderAndWorkflowUuid(workflowUuid, requirementWorkFlowStagesDTO.getSeqOrder());
		List<RequirementWorkFlowStagesDTO> workFlowStagesDTO = new ArrayList<>();
		
		for(RequirementWorkFlowStages workFlowStages : workFlowStagesbySeqOrder) {
			requirementWorkFlowStagesDTO = new RequirementWorkFlowStagesDTO();
			requirementWorkFlowStagesDTO.setStageName(workFlowStages.getStageName());
			requirementWorkFlowStagesDTO.setEditable(workFlowStages.getEditable());
			requirementWorkFlowStagesDTO.setUuid(workFlowStages.getUuid());
			workFlowStagesDTO.add(requirementWorkFlowStagesDTO);
		}	
		if(workFlowStagesDTO.size() == 1) {
			return null;
		}
		return workFlowStagesDTO.get(1);
	}
	
	//delete workflow stage 
	
	@Override
	public RequirementWorkFlowStages getRequirementWorkFlowStagesByUuid(String uuid) throws Exception {
		return requirementWorkFlowDao.getRequirementWorkFlowStagesByUuid(uuid);
	}

	@Override
	public String deleteRequirementWorkFlowStagesByOrgLocationIdAndUuid(String uuid) {
		return requirementWorkFlowDao.deleteRequirementWorkFlowStagesByOrgLocationIdAndUuid(uuid);
	}


	@Override
	public RequirementWorkFlowStages saveRequirementWorkFlowStages(RequirementWorkFlowStages requirementWorkFlowStages)
			throws Exception {
		return requirementWorkFlowDao.saveRequirementWorkFlowStages(requirementWorkFlowStages);
	}


	@Override
	public List<RequirementWorkFlowStages> getRequirementWorkFlowStagesListByOrgLocationId(Long orgLocationId) {
		return requirementWorkFlowDao.getRequirementWorkFlowStagesListByOrgLocationId(orgLocationId);
	}


	@Override
	public void deleteRequirementWorkFlowStagesByuuid(String uuid) {
		requirementWorkFlowDao.deleteRequirementWorkFlowStagesByuuid(uuid);
	}


	@Override
	public List<RequirementQuestionnaireTemplateDTO> getRequirementQuestionnaireTemplates(Long id) throws Exception {
		
		List<RequirementQuestionnaireTemplateDTO> requirementQuestionnaireTemplateDTO = new ArrayList<>();
		List<RequirementQuestionnaireTemplate> requirementQuestionnaireTemplate = requirementWorkFlowDao.getRequirementQuestionnaireTemplates(id);
		RequirementQuestionnaireTemplateDTO questionnaireTemplateDTO = null;
	
		for(RequirementQuestionnaireTemplate questionnaireTemplate : requirementQuestionnaireTemplate) {
			questionnaireTemplateDTO = new RequirementQuestionnaireTemplateDTO();
			questionnaireTemplateDTO.setName(questionnaireTemplate.getName());
			questionnaireTemplateDTO.setId(questionnaireTemplate.getId());
			requirementQuestionnaireTemplateDTO.add(questionnaireTemplateDTO);
			
			
		}
		
		return requirementQuestionnaireTemplateDTO;
	}

	
	@Override
	public void saveRequirementWorkflowStageByWorkflowIdAndOrgLocationId(RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO, RequirementWorkFlowStages requirementWorkFlowStages, Long id, User user) throws Exception{
		
		RequirementQuestionnaireTemplate requirementQuestionnaireTemplate = new RequirementQuestionnaireTemplate(); 
		RequirementWorkFlowStages newStages = new RequirementWorkFlowStages();
		
		
		OrgLocations orglocations = new OrgLocations();
		orglocations.setId(id);
		
		
		RequirementWorkFlowStages workFlowStage = new RequirementWorkFlowStages();
		workFlowStage.setId(newStages.getId());
		workFlowStage.setRequirementWorkFlowId(requirementWorkFlowStages.getRequirementWorkFlowId());
		
		if(requirementWorkFlowStagesDTO.getRequirementQuestionnaireTemplateId() != null) {
		requirementQuestionnaireTemplate.setId(requirementWorkFlowStagesDTO.getRequirementQuestionnaireTemplateId());
		workFlowStage.setRequirementQuestionnaireTemplateId(requirementQuestionnaireTemplate);
		}
		
		workFlowStage.setOrgLocationId(orglocations);
		workFlowStage.setStageName(requirementWorkFlowStagesDTO.getStageName());
		workFlowStage.setDescription(requirementWorkFlowStagesDTO.getDescription());
		workFlowStage.setSeqOrder(requirementWorkFlowStages.getSeqOrder());
		workFlowStage.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		workFlowStage.setConfigureType(Constants.DEFAULT);
		workFlowStage.setEditable(Constants.YES);
		workFlowStage.setStatus(Constants.RECORD_ACTIVE);
		workFlowStage.setCreatedDate(new Date());
		workFlowStage.setCreatedBy(user);
		workFlowStage.setUpdatedDate(new Date());
		workFlowStage.setUpdatedBy(user);
		
		requirementWorkFlowDao.saveRequirementWorkFlowStagesByOrgLocationId(workFlowStage);		
	}


	@Override
	public void saveRequirementWorkFlowTask(RequirementWorkFlowTasksDTO requirementWorkFlowTasksDTO, User user,
			OrgLocations orgLocations) throws Exception {
		
		RequirementWorkFlowStages newStages = new RequirementWorkFlowStages();
		newStages.setId(Long.parseLong(Constants.RECORD_ACTIVE.toString()));
		
		User assignedUser = new User();
		assignedUser.setId(Long.parseLong(requirementWorkFlowTasksDTO.getAssignToUserId()));
		
		RequirementWorkFlowTasks requirementWorkFlowTasks = new RequirementWorkFlowTasks();
		requirementWorkFlowTasks.setId(requirementWorkFlowTasks.getId());
		requirementWorkFlowTasks.setRequirementWorkFlowStagesId(newStages);
		requirementWorkFlowTasks.setOrgLocationId(orgLocations);
		requirementWorkFlowTasks.setWorkFlowTaskName(requirementWorkFlowTasksDTO.getWorkFlowTaskName());
		requirementWorkFlowTasks.setAssignTo(assignedUser);
		requirementWorkFlowTasks.setPriority(requirementWorkFlowTasksDTO.getPriority());
		requirementWorkFlowTasks.setDescription(requirementWorkFlowTasksDTO.getDescription());
		requirementWorkFlowTasks.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		requirementWorkFlowTasks.setStatus(Constants.RECORD_ACTIVE);
		requirementWorkFlowTasks.setCreatedDate(new Date());
		requirementWorkFlowTasks.setCreatedBy(user);
		requirementWorkFlowTasks.setUpdatedDate(new Date());
		requirementWorkFlowTasks.setUpdatedBy(user);
		
		requirementWorkFlowDao.saveRequirementWorkFlowTask(requirementWorkFlowTasks);		
	}


	@Override
	public ResponseList<RequirementWorkFlowTasksDTO> getRequirementWorkFlowTaskList(
			RequirementWorkFlowTasksDTO requirementWorkFlowTasksDTO, OrgLocations orgLocations, User user) throws Exception {
		return requirementWorkFlowDao.getRequirementWorkFlowTaskList(requirementWorkFlowTasksDTO, orgLocations, user);
	}


	@Override
	public void saveRequirementWorkflowStagesByWorkflowId(RequirementWorkFlow requirementWorkFlow, OrgLocations orgLocations, User user) throws Exception {
		List<RequirementDefaultWorkFlowStages> requirementDefaultWorkFlowStages = requirementWorkFlowDao.getRequirementDefaultWorkFlowStages();
		if (requirementWorkFlow != null) {
			for (int i = 0; i < requirementDefaultWorkFlowStages.size(); i++) {
				RequirementWorkFlowStages requirementWorkFlowStages = new RequirementWorkFlowStages();
				requirementWorkFlowStages.setRequirementWorkFlowId(requirementWorkFlow);
				requirementWorkFlowStages.setOrgLocationId(orgLocations);
				requirementWorkFlowStages.setStageName(requirementDefaultWorkFlowStages.get(i).getStageName());
				requirementWorkFlowStages.setDescription(requirementDefaultWorkFlowStages.get(i).getDescription());
				requirementWorkFlowStages.setSeqOrder(requirementDefaultWorkFlowStages.get(i).getSeqOrder());
				requirementWorkFlowStages.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
				requirementWorkFlowStages.setConfigureType(Constants.DEFAULT);
				requirementWorkFlowStages.setEditable(requirementDefaultWorkFlowStages.get(i).getEditable());
				requirementWorkFlowStages.setStatus(requirementDefaultWorkFlowStages.get(i).getStatus());
				requirementWorkFlowStages.setCreatedDate(new Date());
				requirementWorkFlowStages.setCreatedBy(user);
				requirementWorkFlowStages.setUpdatedDate(new Date());
				requirementWorkFlowStages.setUpdatedBy(user);
				requirementWorkFlowDao.saveRequirementWorkFlowStagesByOrgLocationId(requirementWorkFlowStages);
			}
		}
	}


	@Override
	public Boolean saveRequirementWorkFlowStagesBySeqOrder(RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO,
			OrgLocations orgLocations, User user) throws Exception {

		List<RequirementWorkFlowStages> workFlowStages = null;
		RequirementWorkFlowStages requirementWorkFlowStages = requirementWorkFlowDao.getRequirementWorkFlowStageByUuidAndWorkflowUuid(requirementWorkFlowStagesDTO.getUuid(), requirementWorkFlowStagesDTO.getRequirementWorkFlowUuid().trim());
		RequirementWorkFlow workflow = requirementWorkFlowDao.getRequirementWorkFlowByUuid(requirementWorkFlowStagesDTO.getRequirementWorkFlowUuid().trim());
		if(requirementWorkFlowStages != null && !requirementWorkFlowStagesDTO.getUuid().equalsIgnoreCase("Select")) {
			
			requirementWorkFlowStagesDTO.setSeqOrder(requirementWorkFlowStages.getSeqOrder());
			
			List<RequirementWorkFlowStages> workFlowStagesbySeqOrder = requirementWorkFlowDao.getRequirementWorkflowStagesBySeqOrderAndWorkflowUuid(requirementWorkFlowStagesDTO.getRequirementWorkFlowUuid().trim(), requirementWorkFlowStagesDTO.getSeqOrder());
			
			if(workFlowStagesbySeqOrder.size() > 1) {
			for(RequirementWorkFlowStages stages : workFlowStagesbySeqOrder) {
				int stageSeqorder = stages.getSeqOrder();
				stageSeqorder--;
				stages.setSeqOrder(stageSeqorder);
				requirementWorkFlowDao.saveRequirementWorkFlowStages(stages);
			}
			}
			deleteRequirementWorkFlowStagesByuuid(requirementWorkFlowStagesDTO.getUuid());
	}
		 workFlowStages = requirementWorkFlowDao.getRequirementWorkflowStagesByUuid(requirementWorkFlowStagesDTO.getRequirementWorkFlowUuid().trim());
		 workflow.setNoOfStages(workFlowStages.size());
		 requirementWorkFlowDao.saveRequirementWorkFlowDetails(workflow, orgLocations);
		 return true;
}

}
















