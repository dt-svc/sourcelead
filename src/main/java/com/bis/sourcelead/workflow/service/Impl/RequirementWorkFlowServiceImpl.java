package com.bis.sourcelead.workflow.service.Impl;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.workflow.dao.RequirementWorkFlowDao;
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
import com.bis.sourcelead.workflow.service.RequirementWorkFlowService;

@Service
public class RequirementWorkFlowServiceImpl implements RequirementWorkFlowService {
	private static final Logger logger = LoggerFactory.getLogger(RequirementWorkFlowServiceImpl.class);
	

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	RequirementWorkFlowDao requirementWorkFlowDao;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;

	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Override
	public RequirementWorkFlow saveRequirementWorkFlowDetails(RequirementWorkFlowDTO requirementWorkFlowDTO, User user,
			OrgLocations orgLocations) {
		logger.debug("Started saveRequirementWorkFlowDetails Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
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
				logger.error(e+" in saveRequirementWorkFlowDetails service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
				e.printStackTrace();
			}
			logger.debug("Finished saveRequirementWorkFlowDetails Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
			  return requirementWorkFlow;
		}
	
	
   public RequirementWorkFlow saveRequirementWorkFlowDetailsInfo(RequirementWorkFlowDTO requirementWorkFlowDTO, User user, OrgLocations orgLocations){
	   logger.debug("Started saveRequirementWorkFlowDetailsInfo Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
	   try {
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
					requirementWorkFlowStages.setConfigureType(Constants.CUSTOMIZE);
				}else {
					requirementWorkFlowStages.setConfigureType(Constants.DEFAULT);
				}
				
				requirementWorkFlowStages.setEditable(requirementDefaultWorkFlowStages.get(i).getEditable());
				requirementWorkFlowStages.setStatus(requirementDefaultWorkFlowStages.get(i).getStatus());
				requirementWorkFlowStages.setCommentRequired(Constants.YES);
				requirementWorkFlowStages.setRequiredStatsList(Constants.NO);
				requirementWorkFlowStages.setStageShortCode(requirementDefaultWorkFlowStages.get(i).getStageShortCode());
				requirementWorkFlowStages.setCreatedDate(new Date());
				requirementWorkFlowStages.setCreatedBy(user);
				requirementWorkFlowStages.setUpdatedDate(new Date());
				requirementWorkFlowStages.setUpdatedBy(user);
				requirementWorkFlow.add(requirementWorkFlowStages);
			}
		}
		reqWorkFlow.setWorkflowStages(requirementWorkFlow);
		logger.debug("Finished saveRequirementWorkFlowDetailsInfo Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
		return reqWorkFlow;
	   }catch(Exception e) {
		   logger.error(e+" in saveRequirementWorkFlowDetailsInfo Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
		   e.printStackTrace();
		   return null;
	   }
	}

	@Override
	public List<RequirementWorkFlow> getRequirementWorkFlowByOrgLocationId(OrgLocations orgLocations){
		logger.debug("Started getRequirementWorkFlowByOrgLocationId Service for "+orgLocations.getId()+" orgLocation");
		try {
		List<RequirementWorkFlow> requirementWorkFlow = requirementWorkFlowDao
				.getRequirementWorkFlowByOrgLocationId(orgLocations.getId());
		logger.debug("Finished getRequirementWorkFlowByOrgLocationId Service for "+orgLocations.getId()+" orgLocation");
		return requirementWorkFlow;
		}catch(Exception e) {
			logger.error(e+" in getRequirementWorkFlowByOrgLocationId Service for "+orgLocations.getId()+" orgLocation");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public RequirementWorkFlow saveRequirementDefaultWorkFlowAndStagesByOrgLocationId(OrgLocations orgLocations, User user) {
		logger.debug("Started saveRequirementDefaultWorkFlowAndStagesByOrgLocationId Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
		RequirementWorkFlow workFlow = null;
		try {
		List<RequirementWorkFlow> requirementWorkFlow= requirementWorkFlowDao.getRequirementWorkFlowByOrgLocationId(orgLocations.getId());
		if (requirementWorkFlow.size() == 0) {
			RequirementWorkFlowDTO requirementWorkFlowDTO = new RequirementWorkFlowDTO(); 
			
			StringBuilder jobWorkflow = new StringBuilder(Constants.DEFAULT_JOB_WORKFLOW);
			requirementWorkFlowDTO.setWorkFlowName(jobWorkflow.toString());
			requirementWorkFlowDTO.setDescription(jobWorkflow.toString());
			requirementWorkFlowDTO.setConfigureType(Constants.DEFAULT);
			workFlow = saveRequirementWorkFlowDetailsInfo(requirementWorkFlowDTO,user,orgLocations);
			workFlow.setConfigureType(Constants.DEFAULT);
			workFlow = requirementWorkFlowDao.saveRequirementWorkFlowDetails(workFlow, orgLocations);
			
		}
		}catch(Exception e) {
			logger.error(e+" in saveRequirementDefaultWorkFlowAndStagesByOrgLocationId Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
			e.printStackTrace();
			return null;
		}
		logger.debug("Finished saveRequirementDefaultWorkFlowAndStagesByOrgLocationId Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
		return workFlow;
	}


	@Override
	public List<RequirementWorkFlowStagesDTO> getRequirementDefaultWorkFlowStages(OrgLocations orgLocations){
		logger.debug("Started getRequirementDefaultWorkFlowStages Service for "+orgLocations.getId()+" orgLocation");
		try {
		RequirementWorkFlowDTO requirementWorkFlowDTO = getRequirementDefaultWorkflow(orgLocations);
		List<RequirementWorkFlowStagesDTO> newDefaultStages = getRequirementWorkflowStagesByWorkflowUuid(requirementWorkFlowDTO, orgLocations);
		logger.debug("Finished getRequirementDefaultWorkFlowStages Service for "+orgLocations.getId()+" orgLocation");
		return newDefaultStages;
		}catch(Exception e) {
			logger.error(e+" in getRequirementDefaultWorkFlowStages Service for "+orgLocations.getId()+" orgLocation");
			e.printStackTrace();
			return null;
		}
		}


	@Override
	public ResponseList<RequirementWorkFlowDTO> getRequirementWorkFlowList(RequirementWorkFlowDTO workFlowDTO, OrgLocations orgLocations, User user ) {
		logger.debug("Started getRequirementWorkFlowList Service for "+orgLocations.getId()+" orgLocation");
		try {
		RequirementWorkFlow requirementWorkFlow = requirementWorkFlowDao.getRequirementWorkFlowByUuid(workFlowDTO.getUuid(), orgLocations.getId());
		
		if (workFlowDTO.getUuid() != null && workFlowDTO.getStatus() == 1) {
			requirementWorkFlow.setStatus(Constants.RECORD_INACTIVE);
			requirementWorkFlow.setUpdatedBy(user);
			requirementWorkFlowDao.saveRequirementWorkFlowDetails(requirementWorkFlow, orgLocations);
		}
		if (workFlowDTO.getUuid() != null && workFlowDTO.getStatus() == 0) {
			requirementWorkFlow.setStatus(Constants.RECORD_ACTIVE);
			requirementWorkFlow.setUpdatedBy(user);
			requirementWorkFlowDao.saveRequirementWorkFlowDetails(requirementWorkFlow, orgLocations);
		}
		logger.debug("Finished getRequirementWorkFlowList Service for "+orgLocations.getId()+" orgLocation");
		return requirementWorkFlowDao.getRequirementWorkFlowsByOrgLocationId(workFlowDTO, orgLocations);
		}catch(Exception e) {
			logger.error(e+" in getRequirementWorkFlowList Service for "+orgLocations.getId()+" orgLocation");
			e.printStackTrace();
			return null;
		}
		}
	
	@Override
	public RequirementQuestionnaireTemplate saveQuestionnaireTemplate(RequirementQuestionnaireTemplate template) {
		logger.debug("Running saveQuestionnaireTemplate Service for "+template.getId()+" template");
		try {
       return requirementWorkFlowDao.saveQuestionnaireTemplate(template);
		}catch(Exception e) {
			logger.error(e+" in saveQuestionnaireTemplate Service for "+template.getId()+" template");
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public RequirementQuestionnaireTemplate getQuestionnaireByTemplateId(long templateId) throws Exception {
		
       return requirementWorkFlowDao.getQuestionnaireByTemplateId(templateId);
		
	}


	@Override
	public ResponseList<RequirementQuestionnaireTemplateDTO> getQuestionnaireTemplateListByOrgLocationId(
			RequirementQuestionnaireTemplateDTO questionnaireDto,  String searchValue, Long orgLocationId) {
		logger.debug("Running getQuestionnaireTemplateListByOrgLocationId Service for "+orgLocationId+" orgLocation");
		try {
		return requirementWorkFlowDao.getQuestionnaireTemplateListByOrgLocationId(questionnaireDto, searchValue, orgLocationId);
    }catch(Exception e ) {
    	logger.error(e+" in getQuestionnaireTemplateListByOrgLocationId Service for "+orgLocationId+" orgLocation");
    	e.printStackTrace();
    	return null;
    }
	}
	
	
	public List<RequirementWorkFlowStagesDTO> getRequirementWorkflowStagesByWorkflowUuid(RequirementWorkFlowDTO requirementWorkFlowDTO, OrgLocations orgLocations) {
		logger.debug("Started getRequirementWorkflowStagesByWorkflowUuid Service for "+orgLocations.getId()+" orgLocation");
		try {
		List<RequirementWorkFlowStages> requirementWorkFlowStages = requirementWorkFlowDao.getRequirementWorkflowStagesListByUuid(requirementWorkFlowDTO.getUuid().trim(), orgLocations.getId());
		RequirementWorkFlow requirementWorkFlow = requirementWorkFlowDao.getRequirementWorkFlowByUuid(requirementWorkFlowDTO.getUuid().trim(), orgLocations.getId());

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
			requirementWorkFlowStagesDTO.setConfigureType(workFlowStages.getConfigureType());
			requirementWorkFlowStagesDTO.setStageCount(requirementWorkFlow.getNoOfStages());
			requirementWorkFlowStagesDTO.setRequirementWorkFlowId(requirementWorkFlow.getId());
			workFlowStagesDTO.add(requirementWorkFlowStagesDTO);
		}	
		}
		logger.debug("Finished getRequirementWorkflowStagesByWorkflowUuid Service for "+orgLocations.getId()+" orgLocation");
		return workFlowStagesDTO;
		}catch(Exception e ) {
			logger.error(e+" in getRequirementWorkflowStagesByWorkflowUuid Service for "+orgLocations.getId()+" orgLocation");
			e.printStackTrace();
			return null;
		}
		}


	@Override
	public RequirementQuestionnaireTemplate getQuestionnaireTemplateByLabelName(Long orgLocationId,
			String questionnaireName) {
		logger.debug("Running getQuestionnaireTemplateByLabelName Service for "+orgLocationId+" orgLocation and "+questionnaireName+" questionnaireName");
		try {
		return requirementWorkFlowDao.getQuestionnaireTemplateByLabelName(orgLocationId, questionnaireName);
	}catch(Exception e) {
		logger.error(e+" in getQuestionnaireTemplateByLabelName Service for "+orgLocationId+" orgLocation and "+questionnaireName+" questionnaireName");
		e.printStackTrace();
		return null;
	}
	}
	
	public RequirementWorkFlowDTO getRequirementDefaultWorkflow(OrgLocations orgLocations){
		logger.debug("Started getRequirementDefaultWorkflow Service for "+orgLocations.getId()+" orgLocation");
		try {
		RequirementWorkFlowDTO requirementWorkFlowDTO = new RequirementWorkFlowDTO();
		RequirementWorkFlow defaultWorkflow = requirementWorkFlowDao.getRequirementDefaultWorkflow(orgLocations.getId());
		requirementWorkFlowDTO.setId(defaultWorkflow.getId());
		requirementWorkFlowDTO.setWorkFlowName(defaultWorkflow.getWorkFlowName());
		requirementWorkFlowDTO.setDescription(defaultWorkflow.getDescription());
		requirementWorkFlowDTO.setUuid(defaultWorkflow.getUuid());
		requirementWorkFlowDTO.setConfigureType(defaultWorkflow.getConfigureType());
		logger.debug("Finished getRequirementDefaultWorkflow Service for "+orgLocations.getId()+" orgLocation");
		return requirementWorkFlowDTO;
	}catch(Exception e) {
		logger.error(e+" in getRequirementDefaultWorkflow Service for "+orgLocations.getId()+" orgLocation");
		e.printStackTrace();
		return null;
	}
	}


	@Override
	public String deleteQuestionnaireTemplateById(String templateUuid, Long id) {
		logger.debug("Started deleteQuestionnaireTemplateById Service for "+id+" orgLocation");
		try {
		RequirementQuestionnaireTemplate requirementQuestionnaireTemplate = requirementWorkFlowDao.getQuestionnaireTemplateById(templateUuid);
		List<RequirementWorkFlowStages> requirementWorkFlowStages = requirementWorkFlowDao.getRequirementworkflowStageByQuestionnaireId(requirementQuestionnaireTemplate.getId(), id);
		for(RequirementWorkFlowStages stages : requirementWorkFlowStages) {
			stages.setRequirementQuestionnaireTemplateId(null);
			requirementWorkFlowDao.saveRequirementWorkFlowStages(stages);
		}
		logger.debug("Finished deleteQuestionnaireTemplateById Service for "+id+" orgLocation");
		return requirementWorkFlowDao.deleteQuestionnaireTemplateById(requirementQuestionnaireTemplate.getId());
	}catch(Exception e) {
		logger.debug(e+" in deleteQuestionnaireTemplateById Service for "+id+" orgLocation");
		e.printStackTrace();
		return null;
	}
	}


	@Override
	public RequirementQuestionnaireTemplate getQuestionnaireTemplateById(String templateUuid) {
		logger.debug("Running getQuestionnaireTemplateById Service");
		try {
		return requirementWorkFlowDao.getQuestionnaireTemplateById(templateUuid);
	}catch(Exception e) {
		logger.error(e+" in getQuestionnaireTemplateById");
		e.printStackTrace();
		return null;
	}
	}
	
	@Override
	public void saveRequirementWorkflowStageByWorkflowId(RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO, OrgLocations orgLocations, User user) {
		logger.debug("Started saveRequirementWorkflowStageByWorkflowId Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
		try {
		List<RequirementWorkFlowStages> workFlowStages = null;
		/*OrgLocations orgId = new OrgLocations();
		orgId.setId(id);*/
		RequirementWorkFlow workflow = requirementWorkFlowDao.getRequirementWorkFlowByUuid(requirementWorkFlowStagesDTO.getRequirementWorkFlowUuid().trim(), orgLocations.getId());
		RequirementWorkFlowStages requirementWorkFlowStages = requirementWorkFlowDao.getRequirementWorkFlowStageByUuidAndWorkflowUuid(requirementWorkFlowStagesDTO.getUuid(), requirementWorkFlowStagesDTO.getRequirementWorkFlowUuid().trim(), orgLocations.getId());
		if(requirementWorkFlowStages != null && !requirementWorkFlowStagesDTO.getUuid().equalsIgnoreCase("Select")) {
			
			requirementWorkFlowStagesDTO.setSeqOrder(requirementWorkFlowStages.getSeqOrder());
			
			List<RequirementWorkFlowStages> workFlowStagesbySeqOrder = requirementWorkFlowDao.getRequirementWorkflowStagesBySeqOrderAndWorkflowUuid(requirementWorkFlowStagesDTO.getRequirementWorkFlowUuid().trim(), requirementWorkFlowStagesDTO.getSeqOrder(), orgLocations.getId());
			
			if(workFlowStagesbySeqOrder.size() >= 1 && requirementWorkFlowStagesDTO.getLastStage().equals("")) {
			for(RequirementWorkFlowStages stages : workFlowStagesbySeqOrder) {
				int stageSeqorder = stages.getSeqOrder();
				stageSeqorder++;
				stages.setSeqOrder(stageSeqorder);
				requirementWorkFlowDao.saveRequirementWorkFlowStages(stages);
			}
				saveRequirementWorkflowStageByWorkflowIdAndOrgLocationId(requirementWorkFlowStagesDTO, requirementWorkFlowStages, orgLocations, user);
			}else {
				int stageSeqorder = requirementWorkFlowStages.getSeqOrder();
				stageSeqorder++;
				requirementWorkFlowStages.setSeqOrder(stageSeqorder);
				saveRequirementWorkflowStageByWorkflowIdAndOrgLocationId(requirementWorkFlowStagesDTO, requirementWorkFlowStages, orgLocations, user);
			}
			
			 workFlowStages = requirementWorkFlowDao.getRequirementWorkflowStagesListByUuid(requirementWorkFlowStagesDTO.getRequirementWorkFlowUuid().trim(), orgLocations.getId());
			 workflow.setNoOfStages(workFlowStages.size());
			 requirementWorkFlowDao.saveRequirementWorkFlowDetails(workflow, orgLocations);
		}
		}catch(Exception e) {
			logger.error(e+" in saveRequirementWorkflowStageByWorkflowId Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
			e.printStackTrace();
		}
		logger.debug("Finished saveRequirementWorkflowStageByWorkflowId Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
	}


	@Override
	public List<RequirementWorkFlowStagesDTO> getRequirementWorkFlowStagesBySeqOrderAndWorkflowUuid(String uuid, String workflowUuid, OrgLocations orgLocations, String currentStageuuid) {
		logger.debug("Started getRequirementWorkFlowStagesBySeqOrderAndWorkflowUuid Service for "+orgLocations.getId()+" orgLocation");
		try {
		RequirementWorkFlowStages requirementWorkFlowStages = requirementWorkFlowDao.getRequirementWorkFlowStageByUuidAndWorkflowUuid(uuid, workflowUuid, orgLocations.getId());
		RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO = new RequirementWorkFlowStagesDTO();
		if(requirementWorkFlowStages != null) {
		requirementWorkFlowStagesDTO.setSeqOrder(requirementWorkFlowStages.getSeqOrder());
		}
		List<RequirementWorkFlowStages> workFlowStagesbySeqOrder = requirementWorkFlowDao.getRequirementWorkflowStagesBySeqOrderAndWorkflowUuid(workflowUuid, requirementWorkFlowStagesDTO.getSeqOrder(), orgLocations.getId());
		List<RequirementWorkFlowStagesDTO> workFlowStagesDTO = new ArrayList<>();
		
		for(RequirementWorkFlowStages workFlowStages : workFlowStagesbySeqOrder) {
			requirementWorkFlowStagesDTO = new RequirementWorkFlowStagesDTO();
			requirementWorkFlowStagesDTO.setStageName(workFlowStages.getStageName());
			requirementWorkFlowStagesDTO.setEditable(workFlowStages.getEditable());
			requirementWorkFlowStagesDTO.setUuid(workFlowStages.getUuid());
			workFlowStagesDTO.add(requirementWorkFlowStagesDTO);
		}	
		logger.debug("Finished getRequirementWorkFlowStagesBySeqOrderAndWorkflowUuid Service for "+orgLocations.getId()+" orgLocation");
		return workFlowStagesDTO;
	}catch(Exception e) {
		logger.error(e+" in getRequirementWorkFlowStagesBySeqOrderAndWorkflowUuid Service for "+orgLocations.getId()+" orgLocation");
		e.printStackTrace();
		return null;
	}
	}
	
	//delete workflow stage 
	
	@Override
	public RequirementWorkFlowStages getRequirementWorkFlowStagesByUuid(String uuid, OrgLocations orgLocations){
		logger.debug("Running getRequirementWorkFlowStagesByUuid Service for "+orgLocations.getId()+" orgLocation");
		try {
		return requirementWorkFlowDao.getRequirementWorkFlowStagesByUuid(uuid, orgLocations.getId());
	}catch(Exception e) {
		logger.error(e+" in getRequirementWorkFlowStagesByUuid Service for "+orgLocations.getId()+" orgLocation");
		e.printStackTrace();
		return null;
	}
	}

	@Override
	public String deleteRequirementWorkFlowStagesByOrgLocationIdAndUuid(String uuid) {
		logger.debug("Running deleteRequirementWorkFlowStagesByOrgLocationIdAndUuid Service");
		try {
		return requirementWorkFlowDao.deleteRequirementWorkFlowStagesByOrgLocationIdAndUuid(uuid);
	}catch(Exception e) {
		logger.error(e+" in deleteRequirementWorkFlowStagesByOrgLocationIdAndUuid Service");
		e.printStackTrace();
		return null;
	}
	}


	@Override
	public RequirementWorkFlowStages saveRequirementWorkFlowStages(RequirementWorkFlowStages requirementWorkFlowStages) {
		logger.debug("Running saveRequirementWorkFlowStages Service");
		try {
		return requirementWorkFlowDao.saveRequirementWorkFlowStages(requirementWorkFlowStages);
	}catch(Exception e) {
		logger.error(e+" in saveRequirementWorkFlowStages Service");
		e.printStackTrace();
		return null;
	}
	}


	@Override
	public List<RequirementWorkFlowStages> getRequirementWorkFlowStagesListByOrgLocationId(OrgLocations orgLocations) {
		logger.debug("Running getRequirementWorkFlowStagesListByOrgLocationId Service for "+orgLocations.getId()+" orgLocation");
		try {
		return requirementWorkFlowDao.getRequirementWorkFlowStagesListByOrgLocationId(orgLocations.getId());
	}catch(Exception e) {
		logger.error(e+" in getRequirementWorkFlowStagesListByOrgLocationId Service for "+orgLocations.getId()+" orgLocation");
		e.printStackTrace();
		return null;
	}
	}


	@Override
	public void deleteRequirementWorkFlowStagesByuuid(String uuid, OrgLocations orgLocations) {
		logger.debug("Running deleteRequirementWorkFlowStagesByuuid Service for "+orgLocations.getId()+" orgLocation");
		try {
		RequirementWorkFlowStages requirementWorkFlowStages = requirementWorkFlowDao.getRequirementWorkFlowStagesByUuid(uuid, orgLocations.getId());
		requirementWorkFlowDao.deleteRequirementWorkFlowStagesByuuid(requirementWorkFlowStages.getId());
		}catch(Exception e) {
			logger.error(e+" in deleteRequirementWorkFlowStagesByuuid Service for "+orgLocations.getId()+" orgLocation");
			e.printStackTrace();
		}
	}


	@Override
	public List<RequirementQuestionnaireTemplateDTO> getRequirementQuestionnaireTemplates(Long id) {
		logger.debug("Started getRequirementQuestionnaireTemplates Service for "+id+" questionnaireTemplate");
		try {
		List<RequirementQuestionnaireTemplateDTO> requirementQuestionnaireTemplateDTO = new ArrayList<>();
		List<RequirementQuestionnaireTemplate> requirementQuestionnaireTemplate = requirementWorkFlowDao.getRequirementQuestionnaireTemplates(id);
		RequirementQuestionnaireTemplateDTO questionnaireTemplateDTO = null;
	
		for(RequirementQuestionnaireTemplate questionnaireTemplate : requirementQuestionnaireTemplate) {
			questionnaireTemplateDTO = new RequirementQuestionnaireTemplateDTO();
			questionnaireTemplateDTO.setName(questionnaireTemplate.getName());
			questionnaireTemplateDTO.setId(questionnaireTemplate.getId());
			requirementQuestionnaireTemplateDTO.add(questionnaireTemplateDTO);
			
			
		}
		logger.debug("Finished getRequirementQuestionnaireTemplates Service for "+id+" questionnaireTemplate");
		return requirementQuestionnaireTemplateDTO;
	}catch(Exception e) {
		logger.error(e+" in getRequirementQuestionnaireTemplates Service for "+id+" questionnaireTemplate");
		e.printStackTrace();
		return null;
	}
	}

	
	@Override
	public String saveRequirementWorkflowStageByWorkflowIdAndOrgLocationId(RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO, RequirementWorkFlowStages requirementWorkFlowStages, OrgLocations orgLocations, User user) {
		logger.debug("Started saveRequirementWorkflowStageByWorkflowIdAndOrgLocationId Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
		try {
		RequirementWorkFlowStages workFlowStage =  new RequirementWorkFlowStages();
		RequirementQuestionnaireTemplate requirementQuestionnaireTemplate = new RequirementQuestionnaireTemplate(); 
		
		if(requirementWorkFlowStagesDTO.getCurrentStageUuid().equals(Constants.DEFAULT)) {
			workFlowStage.setStageName(requirementWorkFlowStagesDTO.getStageName());
			workFlowStage.setDescription(requirementWorkFlowStagesDTO.getStageName());
			workFlowStage.setRequirementWorkFlowId(requirementWorkFlowStages.getRequirementWorkFlowId());
			workFlowStage.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			workFlowStage.setStatus(Constants.RECORD_INACTIVE);
			workFlowStage.setSeqOrder(Constants.RECORD_INACTIVE);
			workFlowStage.setOrgLocationId(orgLocations);
			workFlowStage.setConfigureType(Constants.CLIENT);
			workFlowStage.setEditable(Constants.YES);
			workFlowStage.setCreatedDate(new Date());
			workFlowStage.setCreatedBy(user);
			workFlowStage.setUpdatedDate(new Date());
			workFlowStage.setUpdatedBy(user);
		}if(!requirementWorkFlowStagesDTO.getCurrentStageUuid().equals("") && !requirementWorkFlowStagesDTO.getCurrentStageUuid().equals(Constants.DEFAULT)) {
				workFlowStage = requirementWorkFlowDao.getRequirementWorkFlowStageByUuidAndWorkflowUuid(requirementWorkFlowStagesDTO.getCurrentStageUuid(), requirementWorkFlowStagesDTO.getRequirementWorkFlowUuid().trim(), orgLocations.getId());	
				workFlowStage.setStageName(requirementWorkFlowStagesDTO.getStageName());
				workFlowStage.setDescription(requirementWorkFlowStagesDTO.getDescription());
				workFlowStage.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
				workFlowStage.setStatus(Constants.RECORD_ACTIVE);
				workFlowStage.setUpdatedDate(new Date());
				workFlowStage.setUpdatedBy(user);
			}
		if(requirementWorkFlowStagesDTO.getStageName() != null && StringUtils.isNotBlank(requirementWorkFlowStagesDTO.getStageName()) && !requirementWorkFlowStagesDTO.getCurrentStageUuid().equals(Constants.DEFAULT)) {
			workFlowStage.setStageName(requirementWorkFlowStagesDTO.getStageName());
			workFlowStage.setDescription(requirementWorkFlowStagesDTO.getDescription());
			if(!requirementWorkFlowStagesDTO.getCurrentStageUuid().equals(Constants.DEFAULT)) {
				workFlowStage.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
				workFlowStage.setStatus(Constants.RECORD_ACTIVE);
			}
			workFlowStage.setOrgLocationId(orgLocations);
			workFlowStage.setConfigureType(Constants.CLIENT);
			workFlowStage.setEditable(Constants.YES);
			workFlowStage.setCreatedDate(new Date());
			workFlowStage.setCreatedBy(user);
			workFlowStage.setUpdatedDate(new Date());
			workFlowStage.setUpdatedBy(user);
			}
		if(requirementWorkFlowStages != null && !requirementWorkFlowStagesDTO.getCurrentStageUuid().equals(Constants.DEFAULT)) {
			workFlowStage.setRequirementWorkFlowId(requirementWorkFlowStages.getRequirementWorkFlowId());
			workFlowStage.setSeqOrder(requirementWorkFlowStages.getSeqOrder());
		}
		
		if(requirementWorkFlowStagesDTO.getStageShortCode() != null && StringUtils.isNotBlank(requirementWorkFlowStagesDTO.getStageShortCode())) {
			workFlowStage.setStageShortCode(requirementWorkFlowStagesDTO.getStageShortCode().toUpperCase(Locale.ROOT));
		}
		
		if(requirementWorkFlowStagesDTO.getRequirementQuestionnaireTemplateId() != null) {
		requirementQuestionnaireTemplate.setId(requirementWorkFlowStagesDTO.getRequirementQuestionnaireTemplateId());
		workFlowStage.setRequirementQuestionnaireTemplateId(requirementQuestionnaireTemplate);
		}
		
		if(requirementWorkFlowStagesDTO.getCommentRequired() != null && !requirementWorkFlowStagesDTO.getCommentRequired().equals("")) {
					workFlowStage.setCommentRequired(requirementWorkFlowStagesDTO.getCommentRequired());
			}
		
		if(requirementWorkFlowStagesDTO.getRequiredStatsList() != null && !requirementWorkFlowStagesDTO.getRequiredStatsList().equals("")) {
			if(StringUtils.equals(requirementWorkFlowStagesDTO.getRequiredStatsList(), Constants.ON)){
				requirementWorkFlowStagesDTO.setRequiredStatsList(Constants.YES);
			}
			else{
				requirementWorkFlowStagesDTO.setRequiredStatsList(Constants.NO);
			}
			workFlowStage.setRequiredStatsList(requirementWorkFlowStagesDTO.getRequiredStatsList());
		}
		
		if(requirementWorkFlowStagesDTO.getRequiredStatsList() != null && !requirementWorkFlowStagesDTO.getRequiredStatsList().equalsIgnoreCase(Constants.NO)) {
		if(requirementWorkFlowStagesDTO.getChosenColumns() != null && !requirementWorkFlowStagesDTO.getChosenColumns().equals("")) {
			workFlowStage.setChosenColumns(requirementWorkFlowStagesDTO.getChosenColumns());
		}
		}
		
		//task saving..
		User assignedUser = new User();
		Set<RequirementWorkFlowTasks> requirementWorkFlowTasks = new HashSet<RequirementWorkFlowTasks>();
		
		if(requirementWorkFlowStagesDTO.getTaskList() != null && requirementWorkFlowStagesDTO.getTaskList().size() > 0) {
			List<RequirementWorkFlowTasksDTO> requirementWorkFlowTasksDTO = requirementWorkFlowStagesDTO.getTaskList();
			workFlowStage.setConfigureType(Constants.INACTIVE);
			for(RequirementWorkFlowTasksDTO workFlowTasksDTO : requirementWorkFlowTasksDTO) {
				RequirementWorkFlowTasks tasks = new RequirementWorkFlowTasks();
				tasks.setRequirementWorkFlowStagesId(workFlowStage);
				tasks.setOrgLocationId(orgLocations);
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
		logger.debug("Finished saveRequirementWorkflowStageByWorkflowIdAndOrgLocationId Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
		return workFlowStage.getUuid();
	}catch(Exception e) {
		logger.error(e+" in saveRequirementWorkflowStageByWorkflowIdAndOrgLocationId Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
		e.printStackTrace();
		return null;
	}
	}


	@Override
	public ResponseList<RequirementWorkFlowTasksDTO> getRequirementWorkFlowTaskList(
			RequirementWorkFlowTasksDTO requirementWorkFlowTasksDTO, OrgLocations orgLocations, User user) {
		logger.debug("Started getRequirementWorkFlowTaskList Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
		try {
		RequirementWorkFlowStages requirementWorkFlowStages = requirementWorkFlowDao.getRequirementWorkFlowStagesByUuid(requirementWorkFlowTasksDTO.getCurrentStageUuid(), orgLocations.getId());
		if(requirementWorkFlowStages != null) {
		requirementWorkFlowTasksDTO.setStageId(requirementWorkFlowStages.getId());
		}
		logger.debug("Finished getRequirementWorkFlowTaskList Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
		return requirementWorkFlowDao.getRequirementWorkFlowTaskList(requirementWorkFlowTasksDTO, orgLocations, user);
	}catch(Exception e) {
		logger.error(e+" in getRequirementWorkFlowTaskList Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
		e.printStackTrace();
		return null;
	}
	}
	
   /** comments added by SRINIVAS SABBU.
    *  Below method is used reset the sequence order which are greater than the deleted stage.
    */
	@Override
	public Boolean saveRequirementWorkFlowStagesBySeqOrder(RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO,
			OrgLocations orgLocations, User user) {
		logger.debug("Started saveRequirementWorkFlowStagesBySeqOrder Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
		try {
		List<RequirementWorkFlowStages> workFlowStages = null;
		// load the deleted stage here.
		RequirementWorkFlowStages requirementWorkFlowStages = requirementWorkFlowDao.getRequirementWorkFlowStageByUuidAndWorkflowUuid(requirementWorkFlowStagesDTO.getUuid(), requirementWorkFlowStagesDTO.getRequirementWorkFlowUuid().trim(), orgLocations.getId());
		// load the associated work flow to be deleted.
		RequirementWorkFlow workflow = requirementWorkFlowDao.getRequirementWorkFlowByUuid(requirementWorkFlowStagesDTO.getRequirementWorkFlowUuid().trim(), orgLocations.getId());
		
		if(requirementWorkFlowStages != null) {
			
			requirementWorkFlowStagesDTO.setSeqOrder(requirementWorkFlowStages.getSeqOrder());
			// load the all the stage which are greater than current deleted work flow id.
			List<RequirementWorkFlowStages> workFlowStagesbySeqOrder = requirementWorkFlowDao.getRequirementWorkflowStagesBySeqOrderAndWorkflowUuid(requirementWorkFlowStagesDTO.getRequirementWorkFlowUuid().trim(), requirementWorkFlowStagesDTO.getSeqOrder(), orgLocations.getId());
			// if  there are stages greater than sequence order of the deleted stage then decrement the sequence order of the all the stages.
			if(workFlowStagesbySeqOrder.size() > 1) {
			for(RequirementWorkFlowStages stages : workFlowStagesbySeqOrder) {
				int stageSeqorder = stages.getSeqOrder();
				stageSeqorder--;
				stages.setSeqOrder(stageSeqorder);
				requirementWorkFlowDao.saveRequirementWorkFlowStages(stages); // update and save the stage with updated sequence order.
			}
			}// finally deleted the current stage once all stage are saved with updated sequence order.
			deleteRequirementWorkFlowStagesByuuid(requirementWorkFlowStagesDTO.getUuid(), orgLocations);
	}
		 workFlowStages = requirementWorkFlowDao.getRequirementWorkflowStagesListByUuid(requirementWorkFlowStagesDTO.getRequirementWorkFlowUuid().trim(), orgLocations.getId());
		 workflow.setNoOfStages(workFlowStages.size()); // updated the no of stages count for the work flow and saves the work flow.
		 requirementWorkFlowDao.saveRequirementWorkFlowDetails(workflow, orgLocations);
		 logger.debug("Finished saveRequirementWorkFlowStagesBySeqOrder Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
		 return true;
}catch(Exception e) {
	logger.error(e+" in saveRequirementWorkFlowStagesBySeqOrder Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
	e.printStackTrace();
	return  null;
}
	}


	@Override
	public void saveRequirementWorkFlowDetailsByWorkflowUuid(RequirementWorkFlowDTO requirementWorkFlowDTO,
			OrgLocations orgLocations, User user) {
		logger.debug("Started  saveRequirementWorkFlowDetailsByWorkflowUuid Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
		try {
		RequirementWorkFlow requirementWorkFlow = requirementWorkFlowDao.getRequirementWorkFlowByUuid(requirementWorkFlowDTO.getUuid().trim(), orgLocations.getId());
		
		requirementWorkFlow.setWorkFlowName(requirementWorkFlowDTO.getWorkFlowName());
		requirementWorkFlow.setDescription(requirementWorkFlowDTO.getDescription());
		requirementWorkFlow.setUpdatedBy(user);
		requirementWorkFlow.setUpdatedDate(new Date());
		
		requirementWorkFlowDao.saveRequirementWorkFlowDetails(requirementWorkFlow, orgLocations);
	}catch(Exception e) {
		logger.error(e+" in saveRequirementWorkFlowDetailsByWorkflowUuid Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
		e.printStackTrace();
	}
		logger.debug("Finished  saveRequirementWorkFlowDetailsByWorkflowUuid Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
	}
	
	@Override
	public Boolean deleteApplicationWorkFlowByuuid(String workflowuuid, OrgLocations orgLocations) {
		logger.debug("Running deleteApplicationWorkFlowByuuid Service for "+orgLocations.getId()+" orgLocation");
		try {
		RequirementWorkFlow requirementWorkFlow = requirementWorkFlowDao.getRequirementWorkFlowByUuid(workflowuuid, orgLocations.getId());
		return requirementWorkFlowDao.deleteApplicationWorkFlowByuuid(requirementWorkFlow.getId());
	}catch(Exception e) {
		logger.error(e+" in deleteApplicationWorkFlowByuuid Service for "+orgLocations.getId()+" orgLocation");
		e.printStackTrace();
		return null;
	}
	}


	@Override
	public void cloneRequirementWorkFlowDetailsByWorkflowUuid(RequirementWorkFlowDTO requirementWorkFlowDTO,
			OrgLocations orgLocations, User user)  {
		logger.debug("Started cloneRequirementWorkFlowDetailsByWorkflowUuid Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
		try {
		RequirementWorkFlow requirementWorkFlow = requirementWorkFlowDao.getRequirementWorkFlowByUuid(requirementWorkFlowDTO.getUuid(), orgLocations.getId());
		List<RequirementWorkFlowStages> workFlowStages = requirementWorkFlowDao.getRequirementWorkflowStagesListByUuid(requirementWorkFlow.getUuid(), orgLocations.getId());
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
		
		
	Set<RequirementWorkFlowStages> workFlowSet = new HashSet<RequirementWorkFlowStages>();
	Set<RequirementWorkFlowTasks> workFlowTaskSet = new HashSet<RequirementWorkFlowTasks>();
		for(int i=0; i < workFlowStages.size(); i++) {
			RequirementWorkFlowStages requirementWorkFlowStages = new RequirementWorkFlowStages();
			requirementWorkFlowStages.setRequirementWorkFlowId(workFlow);
			requirementWorkFlowStages.setOrgLocationId(orgLocations);
			requirementWorkFlowStages.setStageName(workFlowStages.get(i).getStageName());
			requirementWorkFlowStages.setDescription(workFlowStages.get(i).getDescription());
			requirementWorkFlowStages.setSeqOrder(workFlowStages.get(i).getSeqOrder());
			requirementWorkFlowStages.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			requirementWorkFlowStages.setConfigureType(workFlowStages.get(i).getConfigureType());
			
			requirementWorkFlowStages.setEditable(workFlowStages.get(i).getEditable());
			requirementWorkFlowStages.setStatus(workFlowStages.get(i).getStatus());
			requirementWorkFlowStages.setCommentRequired(workFlowStages.get(i).getCommentRequired());
			requirementWorkFlowStages.setRequiredStatsList(workFlowStages.get(i).getRequiredStatsList());
			requirementWorkFlowStages.setChosenColumns(workFlowStages.get(i).getChosenColumns());
			requirementWorkFlowStages.setStageShortCode(workFlowStages.get(i).getStageShortCode());
			requirementWorkFlowStages.setCreatedDate(new Date());
			requirementWorkFlowStages.setCreatedBy(user);
			requirementWorkFlowStages.setUpdatedDate(new Date());
			requirementWorkFlowStages.setUpdatedBy(user);
			  
			
			List<RequirementWorkFlowTasks> workFlowtasks =requirementWorkFlowDao.getworkflowTasksBystagesId(workFlowStages.get(i).getId());
			  for(RequirementWorkFlowTasks workFlowtaskss : workFlowtasks) {
			  RequirementWorkFlowTasks reqtasks = new RequirementWorkFlowTasks();
			  reqtasks.setRequirementWorkFlowStagesId(requirementWorkFlowStages);
			  reqtasks.setAssignTo(workFlowtaskss.getAssignTo());
			  reqtasks.setCreatedBy(workFlowtaskss.getCreatedBy());
			  reqtasks.setCreatedDate(new Date());
			  reqtasks.setDescription(workFlowtaskss.getDescription());
			  reqtasks.setOrgLocationId(workFlowtaskss.getOrgLocationId());
			  reqtasks.setPriority(workFlowtaskss.getPriority());
			  reqtasks.setStatus(workFlowtaskss.getStatus()); reqtasks.setUpdatedDate(new
			  Date()); reqtasks.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			  reqtasks.setWorkFlowTaskName(workFlowtaskss.getWorkFlowTaskName());
			  workFlowTaskSet.add(reqtasks);
			 
			  requirementWorkFlowStages.setWorkflowTasks(workFlowTaskSet);
		}
			  workFlowSet.add(requirementWorkFlowStages);
	}
		workFlow.setWorkflowStages(workFlowSet);
		requirementWorkFlowDao.saveRequirementWorkFlowDetails(workFlow, orgLocations);
	}catch(Exception e) {
		logger.error(e+" in cloneRequirementWorkFlowDetailsByWorkflowUuid Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
		e.printStackTrace();
	}
		logger.debug("Finished cloneRequirementWorkFlowDetailsByWorkflowUuid Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
	}

	@Override
	public String saveRequirementWorkFlowTask(RequirementWorkFlowTasksDTO requirementWorkFlowTasksDTO,
			OrgLocations orgLocations, User user) {
		logger.debug("Started saveRequirementWorkFlowTask Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
		try {
			String Uuid = null;
		RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO = new RequirementWorkFlowStagesDTO();
		List<RequirementWorkFlowStages> workflowStages = new ArrayList<>();
		List<RequirementWorkFlowTasksDTO> workflowtasks = new ArrayList<>();
		workflowtasks.add(requirementWorkFlowTasksDTO);
		workflowtasks.get(0).setWorkFlowTaskName(requirementWorkFlowTasksDTO.getWorkFlowTaskName());
		workflowtasks.get(0).setAssignToUserId(requirementWorkFlowTasksDTO.getAssignToUserId());
		workflowtasks.get(0).setPriority(requirementWorkFlowTasksDTO.getPriority());
		workflowtasks.get(0).setWorkFlowTaskDescription(requirementWorkFlowTasksDTO.getWorkFlowTaskDescription());
		
		requirementWorkFlowStagesDTO.setRequirementWorkFlowUuid(requirementWorkFlowTasksDTO.getRequirementWorkFlowUuid().trim());
		requirementWorkFlowStagesDTO.setCurrentStageUuid(requirementWorkFlowTasksDTO.getCurrentStageUuid());
		requirementWorkFlowStagesDTO.setStageName(requirementWorkFlowTasksDTO.getStageName());
		requirementWorkFlowStagesDTO.setDescription(requirementWorkFlowTasksDTO.getDescription());
		
		requirementWorkFlowStagesDTO.setTaskList(workflowtasks);
		if(requirementWorkFlowTasksDTO.getCurrentStageUuid().equals(Constants.DEFAULT)){
			workflowStages = requirementWorkFlowDao.getRequirementWorkflowStagesListByUuid(requirementWorkFlowTasksDTO.getRequirementWorkFlowUuid().trim(), orgLocations.getId());
			Uuid = saveRequirementWorkflowStageByWorkflowIdAndOrgLocationId(requirementWorkFlowStagesDTO, workflowStages.get(0), orgLocations, user);
		}else {
			Uuid = saveRequirementWorkflowStageByWorkflowIdAndOrgLocationId(requirementWorkFlowStagesDTO, null, orgLocations, user);
		}
		logger.debug("Finished saveRequirementWorkFlowTask Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
		return Uuid;
	}catch(Exception e) {
		logger.error(e+" in saveRequirementWorkFlowTask Service for "+orgLocations.getId()+" orgLocation and "+user.getId()+" user");
		e.printStackTrace();
		return null;
	}
	}


	@Override
	public RequirementWorkFlowStages getRequirementWorkFlowStageForEditStage(RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO,
			OrgLocations orgLocations) {
		logger.debug("Running getRequirementWorkFlowStageForEditStage Service for "+orgLocations.getId()+" orgLocation");
		try {
		RequirementWorkFlowStages stages = requirementWorkFlowDao.getRequirementWorkFlowStagesByUuid(requirementWorkFlowStagesDTO.getUuid().trim(), orgLocations.getId());
		return stages;
	}catch(Exception e) {
		logger.error(e+" in getRequirementWorkFlowStageForEditStage Service for "+orgLocations.getId()+" orgLocation");
		e.printStackTrace();
		return null;
	}
	}


	@Override
	public List<RequirementWorkFlowStagesDTO> getNextRequirementWorkflowStagesByWorkflowUuid(
			RequirementWorkFlowDTO requirementWorkFlowDTO, OrgLocations orgLocations) {
		logger.debug("Started getNextRequirementWorkflowStagesByWorkflowUuid Service for "+orgLocations.getId()+" orgLocation");
		try {
		List<RequirementWorkFlowStages> requirementWorkFlowStages = requirementWorkFlowDao.getNextRequirementWorkflowStagesListByUuid(requirementWorkFlowDTO, orgLocations.getId());
		RequirementWorkFlow requirementWorkFlow = requirementWorkFlowDao.getRequirementWorkFlowByUuid(requirementWorkFlowDTO.getUuid(), orgLocations.getId());

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
		logger.debug("Finished getNextRequirementWorkflowStagesByWorkflowUuid Service for "+orgLocations.getId()+" orgLocation");
		return workFlowStagesDTO;
	}catch(Exception e) {
		logger.error(e+" in getNextRequirementWorkflowStagesByWorkflowUuid Service for "+orgLocations.getId()+" orgLocation");
		e.printStackTrace();
		return null;
	}
	}


	@Override
	public void deleteInactiveRequirementWorkflowStagesByWorkflowIdAndOrgLocationId(String uuid, Long id) {
		logger.debug("Started deleteInactiveRequirementWorkflowStagesByWorkflowIdAndOrgLocationId Service for "+id+" orgLocation");
		try {
		List<RequirementWorkFlowStages> requirementWorkFlowStages = requirementWorkFlowDao.getInactiveRequirementWorkflowStagesListByUuidAndOrgLocationId(uuid, id);
		if(requirementWorkFlowStages.size() > 0) {
		requirementWorkFlowDao.deleteInactiveRequirementWorkflowStagesByStageId(requirementWorkFlowStages);
	}
		}catch(Exception e) {
			logger.error(e+" in deleteInactiveRequirementWorkflowStagesByWorkflowIdAndOrgLocationId Service for "+id+" orgLocation");
			e.printStackTrace();
		}
		logger.debug("Finished deleteInactiveRequirementWorkflowStagesByWorkflowIdAndOrgLocationId Service for "+id+" orgLocation");
	}

	@Override
	public Boolean validateWorkflowName(Long id, String name, String uuid)  {
		logger.debug("Started validateWorkflowName Service for "+id+" orgLocation and "+name+" workflow");
		Boolean flag = null;
		List<RequirementWorkFlow> workflow= new ArrayList<RequirementWorkFlow>();
		try {
			workflow = requirementWorkFlowDao.validateWorkflowName(id, name, uuid);
			if(workflow != null && workflow.size() > 0) {
				flag = true;
			}else {
				flag = false;
			}
			logger.debug("Finished validateWorkflowName Service for "+id+" orgLocation and "+name+" workflow");
			return flag;
		}catch(Exception e) {
			logger.error(e+" in validateWorkflowName Service for "+id+" orgLocation and "+name+" workflow");
			e.printStackTrace();
			return flag;
		}
	}


	@Override
	public String validateWorkflowStageName(Long id, String name, String uuid, String stageUuid) throws Exception {
		logger.debug("Started validateWorkflowStageName Service for "+id+" orgLocation and "+name+" stage");
		String nonSpecialChars = "[a-zA-Z]{2,}|^[a-zA-Z]{2,}[_]|^[a-zA-Z]{2,}[_]{1}[0-9]{1,}$";
		String flag = null;
		Boolean nonSpecialFlag = false;
		Long workflowId = null;
		Iterator<RequirementWorkFlowStages> i = null;
		List<RequirementWorkFlowStages> workflowStages= new ArrayList<RequirementWorkFlowStages>();
		try {
			if(name != null && !StringUtils.isBlank(name)) {
				nonSpecialFlag = Pattern.matches(nonSpecialChars, name);
			}
			if(nonSpecialFlag == true) {
			workflowStages = requirementWorkFlowDao.getRequirementWorkflowStagesListByUuid(uuid, id);
			if(stageUuid == null && StringUtils.isBlank(stageUuid)) {
			if(workflowStages == null || workflowStages.size() == 0) {
				flag = "false";
			}
			if(workflowStages != null && workflowStages.size() > 0) {
				if(name != null && !StringUtils.isBlank(name)) {
					Stream<RequirementWorkFlowStages> stageStream = workflowStages.stream().filter(stage -> stage.getStageName().equalsIgnoreCase(name));
					if(stageStream.count() > 0) {
						return "true";
					}
					else {
						flag = "false";
					}
				}else {
					flag = "false";
				}
			}
			}else {
				workflowId = workflowStages.get(0).getRequirementWorkFlowId().getId();
				workflowStages = requirementWorkFlowDao.validateWorkflowStageName(id, name, workflowId, stageUuid);
				if(workflowStages != null && workflowStages.size() > 0) {
					flag = "true";
				}else {
					flag = "false";
				}
			}
			}else {
				flag = "special";
			}
			logger.debug("Finished validateWorkflowStageName Service for "+id+" orgLocation and "+name+" stage");
			return flag;
		}catch(Exception e) {
			logger.error(e+" in validateWorkflowStageName Service for "+id+" orgLocation and "+name+" stage");
			e.printStackTrace();
			return flag;
		}
	}


	@Override
	public RequirementWorkFlow getRequirementWorkFlowByOrgLocId(Long orgLocationId) throws Exception {
		return requirementWorkFlowDao.getRequirementWorkFlowByOrgLocId(orgLocationId);
	}


	@Override
	public void saveWorkflowIdInAllOrgsForRequirement(RequirementWorkFlow requirementWorkFlow, OrgLocations orgLocations) throws Exception {
		requirementWorkFlowDao.saveRequirementWorkFlowDetails(requirementWorkFlow, orgLocations);
	}


	@Override
	public List<RequirementWorkFlowStages> getRequirementWorkflowStagesByWorkflowIdAndOrglocations(Long workflowId,
			OrgLocations orgLocations) {
		return requirementWorkFlowDao.getRequirementWorkflowStagesByWorkflowIdAndOrglocations(workflowId, orgLocations);
	}


	@Override
	public String validateStageShortCode(Long id, String shortCode, String workflowUuid, String stageUuid) {
		logger.debug("Started validateStageShortCode Service for "+id+" orgLocation and "+shortCode+" shortCode");
		List<RequirementWorkFlowStages> workflowStages = new ArrayList<RequirementWorkFlowStages>();
		String flag = null;
		String nonSpecialChars = "[a-zA-Z]{2,}|^[a-zA-Z]{2,}[_]|^[a-zA-Z]{2,}[_]{1}[0-9]{1,}$";
		Boolean nonSpecialFlag = false;
		try {
			if(shortCode != null && !StringUtils.isBlank(shortCode)) {
				nonSpecialFlag = Pattern.matches(nonSpecialChars, shortCode);
			}
			if(nonSpecialFlag == true) {
			workflowStages = requirementWorkFlowDao.getRequirementWorkflowStagesListByUuidAndOrgLocationId(workflowUuid, id, stageUuid);
			if(workflowStages != null && workflowStages.size() > 0) {
				if(shortCode != null && !StringUtils.isBlank(shortCode)) {
					Stream<RequirementWorkFlowStages> stageStream = workflowStages.stream().filter(stage -> stage.getStageShortCode().equalsIgnoreCase(shortCode));
					if(stageStream.count() > 0) {
						return "true";
					}
				}
			}
			}else {
				flag = "special";
			}
			logger.debug("Finished validateStageShortCode Service for "+id+" orgLocation and "+shortCode+" shortCode");
		return flag;
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e+" in validateStageShortCode Service for "+id+" orgLocation and "+shortCode+" shortCode");
			return null;
		}
	}


	@Override
	public TreeMap<Long, String> checkWorkflowInRequirements(String workflowUuid, Long id)  {
		logger.debug("Started checkWorkflowInRequirements Service for "+id+" orgLocation");
		try {
		TreeMap<Long, String> requirementMap = new TreeMap<Long, String>();
		List<Requirement> requirementList = requirementWorkFlowDao.getRequirementListByWorkflowIdAndOrgLocationId(workflowUuid, id);
		if(requirementList != null && requirementList.size() > 0) {
			for(Requirement requirement : requirementList) {
				requirementMap.put(requirement.getId(), requirement.getJobTitle());
			}
			return requirementMap;
		}
		logger.debug("Finished checkWorkflowInRequirements Service for "+id+" orgLocation");
		return null;
	}catch(Exception e) {
		e.printStackTrace();
		logger.error(e+" in checkWorkflowInRequirements Service for "+id+" orgLocation");
		return null;
	}
	}


	@Override
	public LinkedHashMap<String, String> getColumnsMapbyWorkflowStage(String shortCode, Long workflowId, Long orgLocationId) {
		return requirementWorkFlowDao.getColumnsMapbyWorkflowStage(shortCode, workflowId, orgLocationId);
	}


	@Override
	public RequirementWorkFlowStages getRequirementWorkFlowStagesDetails(String shortCode, Long workflowId,
			Long orgLocationId) {
		return requirementWorkFlowDao.getRequirementWorkFlowStagesDetails(shortCode, workflowId, orgLocationId);
	}


	@Override
	public LinkedHashMap<String , String> getRequirementWorkflowStagesBySeqOrderWorkflowIdAndStageName(Long workflowId,
			OrgLocations orgLocation, String submissionType) throws Exception {
		logger.debug("Started getRequirementWorkflowStagesByWorkflowIdAndOrgLocationId Service for "
				+ orgLocation.getId() + " orgLocation");
		try {
			List<RequirementWorkFlowStages> requirementWorkFlowStages = requirementWorkFlowDao
					.getRequirementWorkflowStagesBySeqOrderWorkflowIdAndStageName(workflowId, submissionType, orgLocation);
			
			LinkedHashMap<String , String> stagesMap = new LinkedHashMap<String , String>();
			
			if (requirementWorkFlowStages != null && requirementWorkFlowStages.size() > 0) {
				for (RequirementWorkFlowStages stage : requirementWorkFlowStages) {
					stagesMap.put(stage.getStageShortCode(), stage.getStageName());
				}
			}
			logger.debug("Finished getRequirementWorkflowStagesByWorkflowIdAndOrgLocationId Service for "
					+ orgLocation.getId() + " orgLocation");
			return stagesMap;
		} catch (Exception e) {
			logger.error(e + " in getRequirementWorkflowStagesByWorkflowIdAndOrgLocationId Service for "
					+ orgLocation.getId() + " orgLocation");
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public String sendEmailToCandidateWithQuestionnaireTemplate(HashMap<String , Object> paramList) throws Exception {
		User candidate = (User) paramList.get("candidate");
		User loginUser = (User) paramList.get("loginUser");
		OrgLocations orgLocations = (OrgLocations) paramList.get("orgLocations");
		String templateUuid = (String) paramList.get("templateUuid");
		String templateId = (String) paramList.get("templateId");
		String stageName = (String) paramList.get("stageName");
		String jobTitle = (String) paramList.get("jobTitle");
		
		EmailMessage emailMessage = new EmailMessage();
		if(candidate.getFirstName()!=null){
			 emailMessage.setFirstName(candidate.getFirstName());
		}
		else{
			 emailMessage.setFirstName("");
		}
		if(candidate.getLastName()!=null){
			 emailMessage.setLastName(candidate.getLastName());
		}
		else{
			 emailMessage.setLastName("");
		}
		 StringBuilder fullNameStringBuilder=new StringBuilder();
		 emailMessage.setToEmail(candidate.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 if(StringUtils.isNotBlank(loginUser.getLastName())){
			 emailMessage.setFromName(fullNameStringBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 }else{
			 emailMessage.setFromName(fullNameStringBuilder.append(loginUser.getFirstName()).toString());
		 }
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 
	
		 StringBuilder  questionnaireLink  = buildCandidateQuestionnaireTable(templateId, templateUuid);
		 StringBuilder linkBuild = new StringBuilder();
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("assignedUser", emailMessage.getFirstName());
			 globalVarsMap.put("jobTitle",jobTitle);
			 globalVarsMap.put("LoginUser", emailMessage.getFromName());
			 globalVarsMap.put("stageName", stageName);
			 globalVarsMap.put("questionnaireLink", questionnaireLink.toString());
			 globalVarsMap.put("loginLink",linkBuild.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
			 globalVarsMap.put("loginOrg",orgLocations.getOrgLocationName());

		    System.out.println("===loginLink===");
		    
		    System.out.println(globalVarsMap.get("loginLink"));
			
		 //calling the sendEmail method of emailScenerioUtil
		 
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.CANDIDATE_QUESTIONNARIE_TEMPLATE,new ArrayList<EmailAttachment>());

		 EmailScenario noificationEmailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.CANDIDATE_QUESTIONNARIE_TEMPLATE,Constants.ENTITY_TYPE_NOTIFICATION); 
		 StringBuilder actionLinkBuilder = new StringBuilder();
		 String actionLink =actionLinkBuilder.append("").append(sourceleadUrl).append("/").append("viewQuestionnaire?templateUuid="+templateUuid).toString();	 
		 if(candidate.getFirstName()!=null){
			 String notificationbody = emailScenerioUtil.buildTemplateBody(noificationEmailScenario.getBody(),globalVarsMap,noificationEmailScenario);
			 noificationEmailScenario.setBody(notificationbody);
			 emailScenerioUtil.sendNotification(actionLink, loginUser, candidate, orgLocations, noificationEmailScenario,new HashMap<String,String>());
		 }
		return "success";
	}

	public StringBuilder buildCandidateQuestionnaireTable(String templateId, String templateUuid) throws Exception {
		StringBuilder tempHTML = new StringBuilder();
		tempHTML.append(
				"<table width='100%' bgcolor='#F9F9F9' cellpadding='0' border='2' cellspacing='0' style='background-color:#F9F9F9;'>");

		tempHTML.append("<tr>");

		int i = 0;
		if (null != templateUuid && !"".equalsIgnoreCase(templateUuid)) {
			tempHTML.append("<td>").append("<a href='").append(sourceleadUrl)
					.append("/ws/publicQuestionnaireTemplate?templateUuid=").append(templateUuid)
					.append("'style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#D33B39; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Click Here</div></a>")
					.append("</td>");
		}
		tempHTML.append("</tr>");
		tempHTML.append("</table> ");
		return tempHTML;
	}


	@Override
	public RequirementSubmission validateWorkflowEditable(Long requirementId, Long orgLocationId) {
		// TODO Auto-generated method stub
		return requirementWorkFlowDao.validateWorkflowEditable(requirementId, orgLocationId);
	}
	
	@Override
	public RequirementQuestionnaireFeedbackTemplate saveQuestionnaireFeedbackTemplate(
			RequirementQuestionnaireFeedbackTemplateDTO qemplateDTO, OrgLocations orgLocations, User user)  {
		RequirementQuestionnaireTemplate requirementQuestionnaireTemplate = null;
		Candidate candi = new Candidate();
		candi.setId(1l);
		RequirementWorkFlow work = new RequirementWorkFlow();
		work.setId(2l);
		RequirementQuestionnaireFeedbackTemplate qtemplate = null;
		try {
			if(qemplateDTO.getUuid() != null && StringUtils.isNotBlank(qemplateDTO.getUuid())) {
				qtemplate = requirementWorkFlowDao.getQuestionnaireTemplateFeedbackByUuid(qemplateDTO.getUuid());
				qtemplate.setJsonStringValues(qemplateDTO.getJsonStringData());
				
				if(qemplateDTO.getUploadedFileInfo() != null && !qemplateDTO.getUploadedFileInfo().isEmpty()) {
					qemplateDTO.setStaticPath(qemplateDTO.getStaticPath());
					 for(FileInfo fileInfo : qemplateDTO.getUploadedFileInfo()){
						 if(fileInfo.getFileURL() != null && fileInfo.getStatus() == 0) {
							 FileUploadUtil.moveFile(qemplateDTO.getStaticPath(), fileInfo, "QuestionnaireFeedbackAttachments");
						 }
							
						}
				}
				
				if(qemplateDTO.getTotalPoints() != null) {
					qtemplate.setTotalPoints(qemplateDTO.getTotalPoints());
				}
				if(qemplateDTO.getSubmissionsCount() != null) {
					qtemplate.setSubmissionsCount(qemplateDTO.getSubmissionsCount());
				}
				qtemplate.setUpdatedBy(user);
				qtemplate.setUpdatedDate(new Date());
			}else {
			
		qtemplate =  new RequirementQuestionnaireFeedbackTemplate();
		qtemplate.setOrgLocationId(orgLocations);
		qtemplate.setCandidateId(candi);
		qtemplate.setWorkflowId(work);
		qtemplate.setJsonStringValues(qemplateDTO.getJsonStringData());
		qtemplate.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		qtemplate.setCreatedBy(user);
		qtemplate.setCreatedDate(new Date());
		qtemplate.setUpdatedBy(user);
		qtemplate.setUpdatedDate(new Date());

			
		if(qemplateDTO.getUploadedFileInfo() != null && !qemplateDTO.getUploadedFileInfo().isEmpty()) {
			qemplateDTO.setStaticPath(qemplateDTO.getStaticPath());
			 for(FileInfo fileInfo : qemplateDTO.getUploadedFileInfo()){
				 if(fileInfo.getFileURL() != null && fileInfo.getStatus() == 0) {
					 FileUploadUtil.moveFile(qemplateDTO.getStaticPath(), fileInfo, "QuestionnaireFeedbackAttachments");
				 }
					
				}
		}
		
		if(qemplateDTO.getQuestionnaireTemplateUuid() != null && StringUtils.isNotBlank(qemplateDTO.getQuestionnaireTemplateUuid())){
			requirementQuestionnaireTemplate = requirementWorkFlowDao.getQuestionnaireTemplateById(qemplateDTO.getQuestionnaireTemplateUuid());
		}
		
		if(requirementQuestionnaireTemplate!= null) {
			if(requirementQuestionnaireTemplate.getJsonString() != null) {
				qtemplate.setJsonString(requirementQuestionnaireTemplate.getJsonString());
			}
			if(requirementQuestionnaireTemplate.getTimeLimitMinutes() != null) {
				qtemplate.setTimeLimitMinutes(requirementQuestionnaireTemplate.getTimeLimitMinutes());
			}
			if(requirementQuestionnaireTemplate.getTimeLimitSeconds() != null) {
				qtemplate.setTimeLimitSeconds(requirementQuestionnaireTemplate.getTimeLimitSeconds());
			}
		}
		
		if(qemplateDTO.getTotalPoints() != null) {
			qtemplate.setTotalPoints(qemplateDTO.getTotalPoints());
		}
		if(qemplateDTO.getSubmissionsCount() != null) {
			qtemplate.setSubmissionsCount(qemplateDTO.getSubmissionsCount());
		}
		
			}
		return requirementWorkFlowDao.saveQuestionnaireFeedbackTemplate(qtemplate);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public RequirementQuestionnaireFeedbackTemplateDTO getQuestionnaireTemplateFeedbackList(String uuid)
			throws Exception {
		return requirementWorkFlowDao.getQuestionnaireTemplateFeedbackList(uuid);
	}


	@Override
	public List<RequirementSubmission> validateWorkflowUpdate(Long workflowId, Long orgLocationId) {
		return  requirementWorkFlowDao.validateWorkflowUpdate(workflowId, orgLocationId);
	}
}