package com.bis.sourcelead.workflow.service.Impl;



import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		if(requirementWorkFlowDTO.getConfigureType()!=null && requirementWorkFlowDTO.getConfigureType().equals(Constants.DEFAULT)) {
			reqWorkFlow.setConfigureType(Constants.DEFAULT);
		}else {
			reqWorkFlow.setConfigureType(Constants.CLIENT);
		}
		reqWorkFlow.setUpdatedBy(user);
		reqWorkFlow.setUpdatedDate(currentDate);
		
		//stage saving..
		Set<RequirementWorkFlowStages> requirementWorkFlow = new HashSet<RequirementWorkFlowStages>();
		List<RequirementDefaultWorkFlowStages> requirementDefaultWorkFlowStages = requirementWorkFlowDao.getRequirementDefaultWorkFlowStages();
		reqWorkFlow.setNoOfStages(requirementDefaultWorkFlowStages.size());
		if(requirementDefaultWorkFlowStages != null && requirementDefaultWorkFlowStages.size() > 0) {
			for (int i = 0; i < requirementDefaultWorkFlowStages.size(); i++) {
				RequirementWorkFlowStages requirementWorkFlowStages = new RequirementWorkFlowStages();
				requirementWorkFlowStages.setRequirementWorkFlowId(reqWorkFlow);
				requirementWorkFlowStages.setOrgLocationId(orgLocations);
				requirementWorkFlowStages.setStageName(requirementDefaultWorkFlowStages.get(i).getStageName());
				requirementWorkFlowStages.setDescription(requirementDefaultWorkFlowStages.get(i).getDescription());
				requirementWorkFlowStages.setSeqOrder(requirementDefaultWorkFlowStages.get(i).getSeqOrder());
				requirementWorkFlowStages.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
				
				if(requirementDefaultWorkFlowStages.get(i).getEditable().equalsIgnoreCase(Constants.YES)) {
					requirementWorkFlowStages.setConfigureType(Constants.CLIENT);
				}else {
					requirementWorkFlowStages.setConfigureType(Constants.DEFAULT);
				}
				
				requirementWorkFlowStages.setEditable(requirementDefaultWorkFlowStages.get(i).getEditable());
				requirementWorkFlowStages.setStatus(requirementDefaultWorkFlowStages.get(i).getStatus());
				requirementWorkFlowStages.setCreatedDate(new Date());
				requirementWorkFlowStages.setCreatedBy(user);
				requirementWorkFlowStages.setUpdatedDate(new Date());
				requirementWorkFlowStages.setUpdatedBy(user);
				requirementWorkFlow.add(requirementWorkFlowStages);
			}
		}
		reqWorkFlow.setWorkflowStages(requirementWorkFlow);
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
		RequirementWorkFlow workFlow = null;
		//setting default value to display in jsp.
		Integer workflowSize = requirementDefaultWorkFlowStages.size();
		StringBuilder jobWorkflow = new StringBuilder(Constants.DEFAULT_JOB_WORKFLOW);
		requirementWorkFlowDTO.setWorkFlowName(jobWorkflow.toString());
		requirementWorkFlowDTO.setDescription(jobWorkflow.toString());
		requirementWorkFlowDTO.setNoOfStages(workflowSize.toString());
		requirementWorkFlowDTO.setConfigureType(Constants.DEFAULT);
		
		// saving and fetching a new default workflow for an org..
		List<RequirementWorkFlow> requirementWorkFlow= requirementWorkFlowDao.getRequirementWorkFlowByOrgLocationId(orgLocations.getId());
		if (requirementWorkFlow.size() == 0) {
			
			workFlow = saveRequirementWorkFlowDetailsInfo(requirementWorkFlowDTO,user,orgLocations);
			workFlow.setConfigureType(Constants.DEFAULT);
			requirementWorkFlowDao.saveRequirementWorkFlowDetails(workFlow, orgLocations);
			
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
		try {
		RequirementWorkFlowStages requirementWorkFlowStages = requirementWorkFlowDao.getRequirementWorkFlowStagesByUuid(uuid);
		requirementWorkFlowDao.deleteRequirementWorkFlowStagesByuuid(requirementWorkFlowStages.getId());
		}catch(Exception e) {
			e.printStackTrace();
		}
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
		
		OrgLocations orglocations = new OrgLocations();
		orglocations.setId(id);
	
		
		RequirementQuestionnaireTemplate requirementQuestionnaireTemplate = new RequirementQuestionnaireTemplate(); 
		
		
		RequirementWorkFlowStages workFlowStage = new RequirementWorkFlowStages();
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
		workFlowStage.setConfigureType(Constants.CLIENT);
		workFlowStage.setEditable(Constants.YES);
		workFlowStage.setStatus(Constants.RECORD_ACTIVE);
		workFlowStage.setCreatedDate(new Date());
		workFlowStage.setCreatedBy(user);
		workFlowStage.setUpdatedDate(new Date());
		workFlowStage.setUpdatedBy(user);
		
		//task saving..
		User assignedUser = new User();
		Set<RequirementWorkFlowTasks> requirementWorkFlowTasks = new HashSet<RequirementWorkFlowTasks>();
		
		if(requirementWorkFlowStagesDTO.getTaskList() != null && requirementWorkFlowStagesDTO.getTaskList().size() > 0) {
			List<RequirementWorkFlowTasksDTO> requirementWorkFlowTasksDTO = requirementWorkFlowStagesDTO.getTaskList();
			for(RequirementWorkFlowTasksDTO workFlowTasksDTO : requirementWorkFlowTasksDTO) {
				RequirementWorkFlowTasks tasks = new RequirementWorkFlowTasks();
				tasks.setRequirementWorkFlowStagesId(workFlowStage);
				tasks.setOrgLocationId(orglocations);
				tasks.setWorkFlowTaskName(workFlowTasksDTO.getWorkFlowTaskName());
				
				assignedUser.setId(Long.parseLong(workFlowTasksDTO.getAssignToUserId()));
				tasks.setAssignTo(assignedUser);
				
				tasks.setPriority(workFlowTasksDTO.getPriority());
				tasks.setDescription(workFlowTasksDTO.getWorkFlowTaskDescription());
				tasks.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
				tasks.setStatus(Constants.RECORD_ACTIVE);
				tasks.setCreatedDate(new Date());
				tasks.setCreatedBy(user);
				tasks.setUpdatedDate(new Date());
				tasks.setUpdatedBy(user);
				requirementWorkFlowTasks.add(tasks);
			}
		}
		workFlowStage.setWorkflowTasks(requirementWorkFlowTasks);
		
		requirementWorkFlowDao.saveRequirementWorkFlowStagesByOrgLocationId(workFlowStage);		
	}


	@Override
	public ResponseList<RequirementWorkFlowTasksDTO> getRequirementWorkFlowTaskList(
			RequirementWorkFlowTasksDTO requirementWorkFlowTasksDTO, OrgLocations orgLocations, User user) throws Exception {
		return requirementWorkFlowDao.getRequirementWorkFlowTaskList(requirementWorkFlowTasksDTO, orgLocations, user);
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


	@Override
	public void saveRequirementWorkFlowDetailsByWorkflowUuid(RequirementWorkFlowDTO requirementWorkFlowDTO,
			OrgLocations orgLocations, User user) throws Exception {

		RequirementWorkFlow requirementWorkFlow = requirementWorkFlowDao.getRequirementWorkFlowByUuid(requirementWorkFlowDTO.getUuid().trim());
		
		requirementWorkFlow.setWorkFlowName(requirementWorkFlowDTO.getWorkFlowName());
		requirementWorkFlow.setDescription(requirementWorkFlowDTO.getDescription());
		requirementWorkFlow.setUpdatedBy(user);
		requirementWorkFlow.setUpdatedDate(new Date());
		
		requirementWorkFlowDao.saveRequirementWorkFlowDetails(requirementWorkFlow, orgLocations);
	}
	
	@Override
	public Boolean deleteApplicationWorkFlowByuuid(String workflowuuid) throws Exception{
		RequirementWorkFlow requirementWorkFlow = requirementWorkFlowDao.getRequirementWorkFlowByUuid(workflowuuid);
		return requirementWorkFlowDao.deleteApplicationWorkFlowByuuid(requirementWorkFlow.getId());
	}


	@Override
	public void cloneRequirementWorkFlowDetailsByWorkflowUuid(RequirementWorkFlowDTO requirementWorkFlowDTO,
			OrgLocations orgLocations, User user) throws Exception {
		RequirementWorkFlow requirementWorkFlow = requirementWorkFlowDao.getRequirementWorkFlowByUuid(requirementWorkFlowDTO.getUuid());
		List<RequirementWorkFlowStages> workFlowStages = requirementWorkFlowDao.getRequirementWorkflowStagesByUuid(requirementWorkFlow.getUuid());
		RequirementWorkFlow workFlow = new RequirementWorkFlow ();
		Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
		workFlow.setNoOfStages(workFlowStages.size());
		workFlow.setCreatedBy(user);
		workFlow.setCreatedDate(currentDate);
	if(orgLocations != null){
		workFlow.setOrgLocationId(orgLocations);
		}
	workFlow.setWorkFlowName(requirementWorkFlowDTO.getWorkFlowName());
	workFlow.setDescription(requirementWorkFlowDTO.getDescription());
	
	
	if(requirementWorkFlowDTO.getNoOfStages() != null) {
		workFlow.setNoOfStages(Integer.parseInt(requirementWorkFlowDTO.getNoOfStages()));
	}
	workFlow.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
	workFlow.setStatus(Constants.RECORD_ACTIVE);
	workFlow.setConfigureType(Constants.CLIENT);
	
	workFlow.setUpdatedBy(user);
	workFlow.setUpdatedDate(currentDate);
		
		requirementWorkFlowDao.saveRequirementWorkFlowDetails(workFlow, orgLocations);
		
		for(int i=0; i < workFlowStages.size(); i++) {
			RequirementWorkFlowStages requirementWorkFlowStages = new RequirementWorkFlowStages();
			requirementWorkFlowStages.setRequirementWorkFlowId(workFlow);
			requirementWorkFlowStages.setOrgLocationId(orgLocations);
			requirementWorkFlowStages.setStageName(workFlowStages.get(i).getStageName());
			requirementWorkFlowStages.setDescription(workFlowStages.get(i).getDescription());
			requirementWorkFlowStages.setSeqOrder(workFlowStages.get(i).getSeqOrder());
			requirementWorkFlowStages.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			
			if(workFlowStages.get(i).getEditable().equalsIgnoreCase(Constants.YES)) {
				requirementWorkFlowStages.setConfigureType(Constants.CLIENT);
			}else {
				requirementWorkFlowStages.setConfigureType(Constants.DEFAULT);
			}
			
			requirementWorkFlowStages.setEditable(workFlowStages.get(i).getEditable());
			requirementWorkFlowStages.setStatus(workFlowStages.get(i).getStatus());
			requirementWorkFlowStages.setCreatedDate(new Date());
			requirementWorkFlowStages.setCreatedBy(user);
			requirementWorkFlowStages.setUpdatedDate(new Date());
			requirementWorkFlowStages.setUpdatedBy(user);
			requirementWorkFlowDao.saveRequirementWorkFlowStagesByOrgLocationId(requirementWorkFlowStages);
		}

		
	}

}
















