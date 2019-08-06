package com.bis.sourcelead.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserRoleDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.workflow.dto.RequirementQuestionnaireTemplateDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowStagesDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowTasksDTO;
import com.bis.sourcelead.workflow.entity.RequirementQuestionnaireTemplate;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlow;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlowStages;
import com.bis.sourcelead.workflow.service.RequirementWorkFlowService;

@Controller
@RequestMapping("/app")
public class RequirementWorkflowController {
	
	@Autowired
	private RequirementWorkFlowService requirementWorkFlowService;
	
	@Autowired
	private UserRoleService userRoleService;

	private static final Logger logger = LoggerFactory.getLogger(RequirementWorkflowController.class);
	
	@RequestMapping(value = "/workflowDashboard")
	public ModelAndView workflowDashboard(HttpServletRequest request) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("workflow/workflowDashboard");
		return modelandview;
	}
	
	@RequestMapping(value = "/defaultWorkflow")
	public ModelAndView defaultWorkflow(HttpServletRequest request) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
		Long userId =  (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		User user = new User();
		user.setId(userId);
		logger.debug("creating defalut workflow for the  orglocation id "+orgLocations.getId());
		requirementWorkFlowService.saveRequirementDefaultWorkFlowAndStagesByOrgLocationId(orgLocations, user);
		modelandview.setViewName("workflow/defaultWorkflow");
		return modelandview;
	}
	
	@RequestMapping(value = "/workflowList")
	public ModelAndView workflowList(HttpServletRequest request, 
			@RequestParam(value = "workflowName", required = false)String workflowName)throws Exception{
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("workflow/workflowList");
		if(StringUtils.isNotBlank(workflowName)) {
			modelandview.addObject("workflowName", workflowName);
		}
		OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
		Long userId =  (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		User user = new User();
		user.setId(userId);
		logger.debug("before saving default workflow for the orglocation id: >>> "+orgLocations.getId());
		requirementWorkFlowService.saveRequirementDefaultWorkFlowAndStagesByOrgLocationId(orgLocations, user);
		return modelandview;
	}
	
	@RequestMapping(value = "/viewWorkflow")
	public ModelAndView viewWorkflow(HttpServletRequest request,
			RequirementWorkFlowDTO requirementWorkFlowDTO) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
		List<RequirementWorkFlowStagesDTO> requirementWorkFlowStagesDTO = requirementWorkFlowService.getRequirementWorkflowStagesByWorkflowUuid(requirementWorkFlowDTO, orgLocations);
		logger.debug("before calling deleteInactiveRequirementWorkflowStagesByWorkflowIdAndOrgLocationId >>> "+orgLocations.getId());
		requirementWorkFlowService.deleteInactiveRequirementWorkflowStagesByWorkflowIdAndOrgLocationId(requirementWorkFlowDTO.getUuid(), orgLocations.getId());
		modelandview.setViewName("workflow/viewWorkflow");
		modelandview.addObject("requirementWorkFlowStagesDTO", requirementWorkFlowStagesDTO);
		modelandview.addObject("workflowDescription", requirementWorkFlowStagesDTO.get(0).getWorkflowDescription());
		return modelandview;
	}
	
	
	@RequestMapping(value = "/addStage")
	public ModelAndView addStage(HttpServletRequest request,
			RequirementWorkFlowDTO requirementWorkFlowDTO) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
		Long userId =  (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		User user = new User();
		user.setId(userId);
		logger.debug("inside add stage for the workflow >>> "+orgLocations.getId());
		List<RequirementWorkFlowStagesDTO> requirementWorkFlowStagesDTO = requirementWorkFlowService.getRequirementWorkflowStagesByWorkflowUuid(requirementWorkFlowDTO, orgLocations);
		
		modelandview.setViewName("workflow/addStage");
		modelandview.addObject("requirementWorkFlowStagesDTO", requirementWorkFlowStagesDTO);
		modelandview.addObject("workFlowName", requirementWorkFlowDTO.getWorkFlowName());
		modelandview.addObject("workflowUuid", requirementWorkFlowStagesDTO.get(0).getRequirementWorkFlowUuid());
		return modelandview;
	}
	
	@RequestMapping(value = "/questionnaireList")
	public ModelAndView goToquestionnaireList(HttpServletRequest request,
			@RequestParam(value = "templateName", required = false)String templateName) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		logger.debug("inside goToquestionnaireList>>> ");
		if(StringUtils.isNotBlank(templateName)) {
		modelandview.addObject("templateName", templateName);
		}
		modelandview.setViewName("workflow/questionnaireList");
		return modelandview;
	}
	@RequestMapping(value = "/getQuestionnaireList" , method = RequestMethod.GET)
	public @ResponseBody ResponseList<RequirementQuestionnaireTemplateDTO> getQuestionnaireList(
			RequirementQuestionnaireTemplateDTO requirementQuestionnaireTemplateDTO,
			@RequestParam(value="searchVal", required=false) String searchVal,
																	HttpServletRequest request) throws Exception{
		 ResponseList<RequirementQuestionnaireTemplateDTO>responseList = null;
		Long orgLocationId  = (Long) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocationId");
		logger.debug("inside getQuestionnaireList>>> "+orgLocationId);
	try {
			String[] tableColumns = { "name", "description", "createdBy"};
		  	int sortColumnIndex = -1; 
	  		String sortColumnOrder = "desc"; 
	  		if (requirementQuestionnaireTemplateDTO.getOrder() != null && !requirementQuestionnaireTemplateDTO.getOrder().isEmpty()) {
	  		sortColumnIndex = Integer.parseInt(requirementQuestionnaireTemplateDTO.getOrder().get(0).get("column"));
	  		sortColumnOrder = requirementQuestionnaireTemplateDTO.getOrder().get(0).get("dir"); 
	  		requirementQuestionnaireTemplateDTO.setSortOrder(sortColumnOrder);
	  		}
	  		if (sortColumnIndex != -1){
	  			requirementQuestionnaireTemplateDTO.setSortColumn(tableColumns[sortColumnIndex]);
	  		}
			responseList = requirementWorkFlowService.getQuestionnaireTemplateListByOrgLocationId(requirementQuestionnaireTemplateDTO, 
					searchVal, orgLocationId);
		}catch(Exception e) {
			e.printStackTrace();
		}
	   logger.debug("after getQuestionnaireList>>> "+orgLocationId);
		return responseList;
	}
	@RequestMapping(value = "/addQuestionnaire")
	public ModelAndView addQuestionnaire(HttpServletRequest request) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		logger.debug(" inside addQuestionnaire >>>>>>>>>>>>>");
		modelandview.setViewName("workflow/addQuestionnaire");
		return modelandview;
	}
	@RequestMapping(value = "/editQuestionnaire")
	public ModelAndView editQuestionnaire(@RequestParam(value = "templateUuid", required = true) String templateUuid, HttpServletRequest request) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		modelandview.addObject("templateUuid",templateUuid);
		modelandview.setViewName("workflow/addQuestionnaire");
		logger.debug("after editQuestionnaireList>>> ");
		return modelandview;
	}
	 @RequestMapping(value = "/loadSavedQuestionnaireTemplate" , method = RequestMethod.GET)
	   public @ResponseBody RequirementQuestionnaireTemplate loadSavedquestionnaireTemplate(@RequestParam(value="templateUuid", required=true) String templateUuid,HttpServletRequest request) throws Exception {
		 logger.debug("inside loadSavedquestionnaireTemplate>>> templateUuid "+templateUuid);
		 RequirementQuestionnaireTemplate questionnaireTemplate = requirementWorkFlowService.getQuestionnaireTemplateById(templateUuid);
			return questionnaireTemplate;
	    
	   }
	
	
	@RequestMapping(value = "/saveWorkFlowDetailsPage", method = RequestMethod.POST)
	public @ResponseBody ResponseList<String> saveWorkFlowDetailsPage(
			RequirementWorkFlowDTO requirementWorkFlowDTO,HttpServletRequest request) {
		
		ResponseList<String> response = new ResponseList<String>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 RequirementWorkFlow requirementWorkFlow = null;
		 logger.debug("inside saveWorkFlowDetailsPage>>> orgLocationid "+orgLocations.getId());
		try {
			if (user != null && Validator.validate(requirementWorkFlowDTO)) {
				
				requirementWorkFlow = requirementWorkFlowService.saveRequirementWorkFlowDetails(requirementWorkFlowDTO, user, orgLocations);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else if (user == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			e.printStackTrace();
		}
		 logger.debug("after saveWorkFlowDetailsPage>>> orgLocationid "+orgLocations.getId());
		return response;
	}
	
	@RequestMapping(value = "/createRequirementDefaultWorkFlow", method = RequestMethod.GET)
	public @ResponseBody RequirementWorkFlowDTO createRequirementDefaultWorkFlow(
			RequirementWorkFlowDTO requirementWorkFlowDTO, HttpServletRequest request) {
		RequirementWorkFlowDTO workFlowDTO = null;
		OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
		try {
			 logger.debug("inside createRequirementDefaultWorkFlow>>> templateUuid "+orgLocations.getId());
			workFlowDTO = requirementWorkFlowService.getRequirementDefaultWorkflow(orgLocations);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workFlowDTO;
	}

	@RequestMapping(value = "/createRequirementDefaultWorkFlowStages", method = RequestMethod.GET)
	public @ResponseBody List<RequirementWorkFlowStagesDTO> createRequirementDefaultWorkFlowStages(
			RequirementWorkFlowDTO requirementWorkFlowDTO, HttpServletRequest request) {
		List<RequirementWorkFlowStagesDTO> requirementDefaultWorkFlowStagesDTO = null;
		OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
		try {
			 logger.debug("inside createRequirementDefaultWorkFlowStages>>> orgLocationid "+orgLocations.getId());
			requirementDefaultWorkFlowStagesDTO = requirementWorkFlowService.getRequirementDefaultWorkFlowStages(orgLocations);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return requirementDefaultWorkFlowStagesDTO;

	}
	
	@RequestMapping(value = "/getWorkflowList", method = RequestMethod.GET)
	public @ResponseBody ResponseList<RequirementWorkFlowDTO> getWorkflowList(
			RequirementWorkFlowDTO requirementWorkFlowDTO,
			HttpServletRequest request){
		ResponseList<RequirementWorkFlowDTO> responseList = new ResponseList<>();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 logger.debug("before getWorkflowList>>> orgLocationid "+orgLocations.getId());
		try {
			

			String[] tableColumns = { "workFlowName","description", "noOfStages", "status","updatedBy"};
		  	int sortColumnIndex = -1; 
	  		String sortColumnOrder = "desc"; 
 	  		if (requirementWorkFlowDTO.getOrder() != null && !requirementWorkFlowDTO.getOrder().isEmpty()) {
	  		sortColumnIndex = Integer.parseInt(requirementWorkFlowDTO.getOrder().get(0).get("column"));
	  		sortColumnOrder = requirementWorkFlowDTO.getOrder().get(0).get("dir"); 
	  		}
 	  		    if (sortColumnIndex != -1) {
 	  			 requirementWorkFlowDTO.setSortOrder(sortColumnOrder);
 	  			  requirementWorkFlowDTO.setSortColumn(tableColumns[sortColumnIndex]);
 	  		    }
	  		
	  			
	  			
			responseList = requirementWorkFlowService.getRequirementWorkFlowList(requirementWorkFlowDTO, orgLocations, user);
		}catch(Exception e) {
			e.printStackTrace();
		}
		 logger.debug("after getWorkflowList>>> orgLocationid "+orgLocations.getId());
		return responseList;
		
	}
// Below methods are related to work flow questionnaire added by SRINIVAS SABBU.
	@RequestMapping(value = "/saveQuestionnaireTemplate", method = RequestMethod.POST)
	public @ResponseBody String saveQuestionnaireTemplate(@RequestParam(value = "jsonData", required = false) String json,
			@RequestParam(value = "templateId", required = false) String templateId,
			@RequestParam(value = "timeLimitMinutes", required = false) Integer timeLimitMinutes,
			@RequestParam(value = "timeLimitSeconds", required = false) Integer timeLimitSeconds,
			@RequestParam(value = "jsonStringData", required = false) String jsonStringData, 
	        @RequestParam(value = "templateName", required = false) String templateName, HttpServletRequest request)
			throws Exception {

		Long orgLocationId  = (Long) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocationId");
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(orgLocationId);
		Long userId =  (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		User user = new User();
		user.setId(userId);
		String result = null;
		logger.debug("before saveQuestionnaireTemplate>>> orgLocationid "+orgLocations.getId());
		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);

		try {
			RequirementQuestionnaireTemplate template = null;
			if (StringUtils.isNotBlank(templateId)) {
			//   template = performanceFeedbackTemplateService.getPerformanceFeedbackTemplateById(Long.parseLong(templateId));
				template=requirementWorkFlowService.getQuestionnaireByTemplateId(Long.parseLong(templateId));
			} else {
				template = new RequirementQuestionnaireTemplate();
				template.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
				template.setCreatedBy(user);
				template.setCreatedDate(new Date());
			}

			template.setName(jsonObj.getString("templateName").trim());
			template.setDescription(jsonObj.getString("description"));
			template.setOrgLocationId(orgLocations);
			template.setJsonString(jsonStringData);
			template.setStatus(Constants.RECORD_ACTIVE);
			template.setTimeLimitMinutes(timeLimitMinutes);
			template.setTimeLimitSeconds(timeLimitSeconds);
			template.setUpdatedBy(user);
			template.setUpdatedDate(new Date());
		 	requirementWorkFlowService.saveQuestionnaireTemplate(template);
			result = Result.SUCCESS.name();
			logger.debug("after saveQuestionnaireTemplate>>> orgLocationid "+orgLocations.getId());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result = Result.AWKWARD.name();
			return result;
		}

	}
	
	@RequestMapping(value = "/validateQuestionnaireName" , method = RequestMethod.GET)
	   public @ResponseBody String validateQuestionnaireTemplateLabelName(@RequestParam(value="questionnaireName", required=true) String questinnaireName,HttpServletRequest request) throws Exception {
		Long orgLocationId  = (Long) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocationId");	
		RequirementQuestionnaireTemplate questionnaireTemplate = requirementWorkFlowService.getQuestionnaireTemplateByLabelName(orgLocationId, questinnaireName);
		   
		    if(questionnaireTemplate == null){
		    	return "Success";
		    }else{
		    	return "Existed";
		    }
	    
	   }
	
	@RequestMapping(value = "/deleteQuestionnaireTemplateById" , method = RequestMethod.POST)
	   public @ResponseBody ResponseList<String> deleteQuestionnaireTemplateById(@RequestParam(value="templateUuid", required=true) String templateUuid
			      ,HttpServletRequest request) throws Exception {
		Long userId =  (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		Long orgLocationId  = (Long) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocationId");	
		ResponseList<String> response = new ResponseList<String>();
		logger.debug("before deleteQuestionnaireTemplateById>>> orgLocationid "+orgLocationId);
		try {
			if (templateUuid != null ) {
				String resp = requirementWorkFlowService.deleteQuestionnaireTemplateById(templateUuid, orgLocationId);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else if (userId == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			e.printStackTrace();
		}
		logger.debug("after deleteQuestionnaireTemplateById>>> orgLocationid "+orgLocationId);
		return response;

	}
		
	@RequestMapping(value = "/saveRequirementWorkflowStage")
	public @ResponseBody Response<String> saveRequirementWorkflowStage(HttpServletRequest request,
			@RequestParam(value = "chosenColumnStr", required = false) String chosenColumnStr,
			RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO) {
		OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
		Long userId =  (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		User user = new User();
		user.setId(userId);
		Response<String> response = new Response<>();
		logger.debug("before saveRequirementWorkflowStage>>> orgLocationid "+orgLocations.getId());
			try {
				if (user != null && null != requirementWorkFlowStagesDTO) {
					if(StringUtils.isNotBlank(chosenColumnStr)) // chosenColumnStr is a comma separated string passed form the UI.
					{
						requirementWorkFlowStagesDTO.setChosenColumns(chosenColumnStr);
					}
					requirementWorkFlowService.saveRequirementWorkflowStageByWorkflowId(requirementWorkFlowStagesDTO, orgLocations, user);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else if(user == null) {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				}
				else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			
				}catch (Exception e) {
				e.printStackTrace();
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
			logger.debug("after saveRequirementWorkflowStage>>> orgLocationid "+orgLocations.getId());
		return response;
	}
		
		@RequestMapping(value = "/getRequirementWorkflowStage")
		public @ResponseBody List<RequirementWorkFlowStagesDTO> getRequirementWorkflowStage(RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO,
				HttpServletRequest request){
			List<RequirementWorkFlowStagesDTO> requirementDefaultWorkFlowStagesDTO = null;
			OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
			try {
				requirementDefaultWorkFlowStagesDTO = requirementWorkFlowService.getRequirementWorkFlowStagesBySeqOrderAndWorkflowUuid(requirementWorkFlowStagesDTO.getUuid(), requirementWorkFlowStagesDTO.getRequirementWorkFlowUuid().trim(), orgLocations, requirementWorkFlowStagesDTO.getCurrentStageUuid());
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.debug("after getRequirementWorkflowStage>>> orgLocationid "+orgLocations.getId());
			return requirementDefaultWorkFlowStagesDTO;
		}
		
		@RequestMapping(value = { "/deleteWorkflowStage" })
		public String deleteWorkflowStage(
				@RequestParam(value = "uuid", required = false) String uuid,
				HttpServletRequest request) throws Exception {
		
			OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
			logger.debug("before deleteWorkflowStage>>> orgLocationid "+orgLocations.getId());
			requirementWorkFlowService.deleteRequirementWorkFlowStagesByuuid(uuid, orgLocations);
			
			
			List<RequirementWorkFlowStages> stagesList = requirementWorkFlowService.getRequirementWorkFlowStagesListByOrgLocationId(orgLocations);
			int stageCount = 1;
			for(RequirementWorkFlowStages stage:stagesList) {
				stage.setSeqOrder(stageCount);
				stageCount++;
				requirementWorkFlowService.saveRequirementWorkFlowStages(stage);
				
			}
			logger.debug("after  deleteWorkflowStage>>> orgLocationid "+orgLocations.getId());
			return "success";
		}
		
		@RequestMapping(value = "/getRequirementQuestionnaireTemplates")
		public @ResponseBody List<RequirementQuestionnaireTemplateDTO> getRequirementQuestionnaireTemplates(HttpServletRequest request){
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<RequirementQuestionnaireTemplateDTO> requirementQuestionnaireTemplateDTO = null;
			logger.debug("before getRequirementQuestionnaireTemplates>>> orgLocationid "+orgLocations.getId());
			try {
				requirementQuestionnaireTemplateDTO = requirementWorkFlowService.getRequirementQuestionnaireTemplates(orgLocations.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.debug("after getRequirementQuestionnaireTemplates>>> orgLocationid "+orgLocations.getId());
			return requirementQuestionnaireTemplateDTO;
		}
		
		
		@RequestMapping(value = "/getWorkflowTaskList", method = RequestMethod.GET)
		public @ResponseBody ResponseList<RequirementWorkFlowTasksDTO> getWorkflowTaskList(
				RequirementWorkFlowTasksDTO requirementWorkFlowTasksDTO,
				HttpServletRequest request){
			ResponseList<RequirementWorkFlowTasksDTO> responseList = new ResponseList<>();
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Long userId =  (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
			User user = new User();
			user.setId(userId);
			logger.debug("before getWorkflowTaskList>>> orgLocationid "+orgLocations.getId());
			try {
				
				String[] tableColumns = { "workFlowTaskName", "assignToUserId", "priority", "description"};
			  	int sortColumnIndex = -1; 
		  		String sortColumnOrder = "desc"; 
		  		if (requirementWorkFlowTasksDTO.getOrder() != null && !requirementWorkFlowTasksDTO.getOrder().isEmpty()) {
		  		sortColumnIndex = Integer.parseInt(requirementWorkFlowTasksDTO.getOrder().get(0).get("column"));
		  		sortColumnOrder = requirementWorkFlowTasksDTO.getOrder().get(0).get("dir"); 
		  		requirementWorkFlowTasksDTO.setSortOrder(sortColumnOrder);
		  		}
		  		if (sortColumnIndex != -1){
		  			requirementWorkFlowTasksDTO.setSortColumn(tableColumns[sortColumnIndex]);
		  		}
				responseList = requirementWorkFlowService.getRequirementWorkFlowTaskList(requirementWorkFlowTasksDTO, orgLocations, user);
			}catch(Exception e) {
				e.printStackTrace();
			}
			logger.debug("after getWorkflowTaskList>>> orgLocationid "+orgLocations.getId());
			return responseList;
		
}
		
		@RequestMapping(value = { "/deleteRequirementWorkflowStage" })
		public @ResponseBody String deleteRequirementWorkflowStage(RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO,
				HttpServletRequest request) {
			try {
			OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
			Long userId =  (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
			User user = new User();
			user.setId(userId);
			Boolean flag = false;
			logger.debug("before deleteRequirementWorkflowStage>>> orgLocationid "+orgLocations.getId());
			String[] deletedStageUuids = requirementWorkFlowStagesDTO.getDeletedStageUuids();
			for(int i=0; i< deletedStageUuids.length; i++) {
				requirementWorkFlowStagesDTO.setUuid(deletedStageUuids[i]);
				flag = requirementWorkFlowService.saveRequirementWorkFlowStagesBySeqOrder(requirementWorkFlowStagesDTO, orgLocations, user);
			}
			if(flag == true) {
				return Constants.SUCCESS;
			}
			logger.debug("afer deleteRequirementWorkflowStage>>> orgLocationid "+orgLocations.getId());
			return Constants.FAILED;
		}catch(Exception e) {
			e.printStackTrace();
			return Constants.FAILED;
		}
		}
		
		@RequestMapping(value = "/saveWorkFlowDetails", method = RequestMethod.POST)
		public @ResponseBody ResponseList<String> saveWorkFlowDetails(
				RequirementWorkFlowDTO requirementWorkFlowDTO, 
				HttpServletRequest request) {
			
			ResponseList<String> response = new ResponseList<String>();
			OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
			Long userId =  (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
			User user = new User();
			user.setId(userId);		
			logger.debug("before  saveWorkFlowDetails>>> orgLocationid "+orgLocations.getId());
			try {
				if (user != null && Validator.validate(requirementWorkFlowDTO)) {
					
					requirementWorkFlowService.saveRequirementWorkFlowDetailsByWorkflowUuid(requirementWorkFlowDTO, orgLocations, user);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else if (user == null) {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			} catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				e.printStackTrace();
			}
			logger.debug("after  saveWorkFlowDetails>>> orgLocationid "+orgLocations.getId());
			return response;
		}
		
		@RequestMapping(value = "/cloneWorkFlow", method = RequestMethod.POST)
		public @ResponseBody ResponseList<String> cloneWorkFlowDetails(
				RequirementWorkFlowDTO requirementWorkFlowDTO, 
				HttpServletRequest request) {
			
			ResponseList<String> response = new ResponseList<String>();
			OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
			Long userId =  (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
			User user = new User();
			user.setId(userId);	
			logger.debug("before  cloneWorkFlowDetails>>> orgLocationid "+orgLocations.getId());
			try {
				if (user != null && Validator.validate(requirementWorkFlowDTO)) {
					
					requirementWorkFlowService.cloneRequirementWorkFlowDetailsByWorkflowUuid(requirementWorkFlowDTO, orgLocations, user);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else if (user == null) {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			} catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				e.printStackTrace();
			}
			logger.debug("after  cloneWorkFlowDetails>>> orgLocationid "+orgLocations.getId());
			return response;
		}
		
		@RequestMapping(value="/removeWorkFlowEntry" , method = RequestMethod.POST)
		public @ResponseBody String removeWorkFlowEntry(@RequestParam(value = "workflowuuid", required = false) String workflowuuid, HttpServletRequest request) throws Exception {
			
			OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
			logger.debug("inside  removeWorkFlowEntry>>> orgLocationid "+orgLocations.getId());
			Boolean flag = requirementWorkFlowService.deleteApplicationWorkFlowByuuid(workflowuuid, orgLocations);
			if(flag == true) {
				return Constants.SUCCESS;
			}
			return Constants.FAILED;
			
			
		}
		
		@RequestMapping(value = "/getAllActiveUsersList")
		public @ResponseBody List<UserRoleDTO> getAllActiveUsersList(HttpServletRequest request){
			OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
			List<UserRoleDTO> userRoleListDTO = new ArrayList<>();
			logger.debug("inside  getAllActiveUsersList>>> orgLocationid "+orgLocations.getId());
			try {
				List<UserRole> userRoleList = userRoleService.getAllActiveUsersListByOrgLocationId(orgLocations.getId());
				
				for (UserRole ur : userRoleList) {
					UserRoleDTO userRoleDTO = new UserRoleDTO();
					StringBuilder userName = new StringBuilder();
					userRoleDTO.setUserId(ur.getUser().getId());
					if (null != ur.getUser().getLastName()) {
						userRoleDTO.setUserName(
								userName.append(ur.getUser().getFirstName()).append("").append(ur.getUser().getLastName()).toString());
					} else {
						userRoleDTO.setUserName(userName.append(ur.getUser().getFirstName()).toString());
					}
					userRoleListDTO.add(userRoleDTO);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.debug("after  getAllActiveUsersList>>> orgLocationid "+orgLocations.getId());
			return userRoleListDTO;
		}
		
		@RequestMapping(value = "/saveWorkflowTask")
		public @ResponseBody  Response<String> saveWorkflowTask(RequirementWorkFlowTasksDTO requirementWorkFlowTasksDTO,
				HttpServletRequest request)throws Exception{
			OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
			Long userId =  (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
			User user = new User();
			user.setId(userId);
			Response<String> response = new Response<>();
			String Uuid;
			logger.debug("inside  saveWorkflowTask>>> orgLocationid "+orgLocations.getId());
			try {
				if (user != null && Validator.validate(requirementWorkFlowTasksDTO)) {
					Uuid = requirementWorkFlowService.saveRequirementWorkFlowTask(requirementWorkFlowTasksDTO, orgLocations, user);
					response.setResponse(Uuid);
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else if (user == null) {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			} catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				e.printStackTrace();
			}
			logger.debug("after  saveWorkflowTask>>> orgLocationid "+orgLocations.getId());
			return response;
			
		}
		
		@RequestMapping(value = "/editStage")
		public ModelAndView editStage(HttpServletRequest request,
				RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO,
				@RequestParam(value = "workflowName", required = false)String workflowName) throws Exception{
			ModelAndView modelandview = new ModelAndView();
			OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
			RequirementWorkFlowDTO requirementWorkFlowDTO = new RequirementWorkFlowDTO();
			requirementWorkFlowDTO.setUuid(requirementWorkFlowStagesDTO.getRequirementWorkFlowUuid());
			requirementWorkFlowDTO.setCurrentStageUuid(requirementWorkFlowStagesDTO.getUuid().trim());
			logger.debug("before  editStage>>> orgLocationid "+orgLocations.getId());
			RequirementWorkFlowStages stages = requirementWorkFlowService.getRequirementWorkFlowStageForEditStage(requirementWorkFlowStagesDTO, orgLocations);
			
			List<RequirementWorkFlowStagesDTO> stagesDTO = requirementWorkFlowService.getNextRequirementWorkflowStagesByWorkflowUuid(requirementWorkFlowDTO, orgLocations);
			if(StringUtils.isNotBlank(workflowName)) {
				modelandview.addObject("workFlowName", workflowName);
			}
			modelandview.setViewName("workflow/addStage");
			modelandview.addObject("stageName", stages.getStageName());
			modelandview.addObject("description", stages.getDescription());
			if(stages.getRequirementQuestionnaireTemplateId() != null) {
			modelandview.addObject("questionnaireId",stages.getRequirementQuestionnaireTemplateId().getId());
			modelandview.addObject("questionnaireName",stages.getRequirementQuestionnaireTemplateId().getName());
			}
			if(stages.getStageShortCode() != null) {
				modelandview.addObject("stageShortCode",stages.getStageShortCode());
			}
			modelandview.addObject("commentRequired", stages.getCommentRequired());
			modelandview.addObject("requiredList", stages.getRequiredStatsList());
			modelandview.addObject("chosenColumns", stages.getChosenColumns());
			modelandview.addObject("currentStageUuid", requirementWorkFlowStagesDTO.getUuid().trim());
			modelandview.addObject("workflowUuid", requirementWorkFlowStagesDTO.getRequirementWorkFlowUuid());
			modelandview.addObject("requirementWorkFlowStagesDTO", stagesDTO);
			modelandview.addObject("configureType", stages.getConfigureType());
			logger.debug("after  editStage>>> orgLocationid "+orgLocations.getId());
			return modelandview;
		}
		
		@RequestMapping(value = "/validateWorkflowName")
		@ResponseBody
		public Response<Boolean> validateWorkflowName(
				@RequestParam(value = "workFlowName", required = true)String workFlowName,
				@RequestParam(value = "workflowUuid", required = false)String workflowUuid,
				HttpServletRequest request) throws Exception{
			Long orgLocationId  = (Long) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocationId");
			Boolean flag ;
			Response<Boolean> response = new Response<Boolean>();
			logger.debug("before validateWorkflowName for "+orgLocationId+" orgLocation");
			flag = requirementWorkFlowService.validateWorkflowName(orgLocationId, workFlowName, workflowUuid);
			response.setResponse(flag.toString());
			logger.debug("after validateWorkflowName for "+orgLocationId+" orgLocation");
			return response;
			
		}
		
		@RequestMapping(value = "/validateWorkflowStageName")
		@ResponseBody
		public Response<String> validateWorkflowStageName(
				@RequestParam(value = "stageName", required = true)String stageName,
				@RequestParam(value = "workflowUuid", required = true)String workflowUuid,
				@RequestParam(value = "currentStageUuid", required = false)String stageUuid,
				HttpServletRequest request) throws Exception{
			Long orgLocationId  = (Long) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocationId");
			String flag ;
			Response<String> response = new Response<String>();
			logger.debug("before validateWorkflowStageName for "+orgLocationId+" orgLocation");
			flag = requirementWorkFlowService.validateWorkflowStageName(orgLocationId, stageName, workflowUuid, stageUuid);
			response.setResponse(flag);
			logger.debug("after validateWorkflowStageName for "+orgLocationId+" orgLocation");
			return response;
			
		}
		
		@RequestMapping(value = "/validateStageShortCode")
		@ResponseBody
		public Response<String> validateStageShortCode(
				@RequestParam(value = "stageShortCode", required = true)String stageShortCode,
				@RequestParam(value = "workflowUuid", required = true)String workflowUuid,
				@RequestParam(value = "currentStageUuid", required = false)String stageUuid,
				HttpServletRequest request) throws Exception{
			Long orgLocationId  = (Long) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocationId");
			String flag ;
			Response<String> response = new Response<String>();
			logger.debug("before validateStageShortCode for "+orgLocationId+" orgLocation");
			flag = requirementWorkFlowService.validateStageShortCode(orgLocationId, stageShortCode, workflowUuid, stageUuid);
			response.setResponse(flag);
			logger.debug("after validateStageShortCode for "+orgLocationId+" orgLocation");
			return response;
			
		}
		
		@RequestMapping(value = "/checkWorkflowInRequirements")
		@ResponseBody
		public TreeMap<Long, String> checkWorkflowInRequirements(@RequestParam(value = "workflowUuid", required = true)String workflowUuid,
				HttpServletRequest request)throws Exception {
			Long orgLocationId  = (Long) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocationId");
			return requirementWorkFlowService.checkWorkflowInRequirements(workflowUuid, orgLocationId);
			
		}
		
		@RequestMapping(value = "/getColumnsMapbyWorkflowStage")
		@ResponseBody
		public LinkedHashMap<String, String> getColumnsMapbyWorkflowStage(@RequestParam(value = "shortCode", required = true)String shortCode, @RequestParam(value = "workflowId", required = true)Long workflowId,
				HttpServletRequest request)throws Exception {
			Long orgLocationId  = (Long) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocationId");
			return requirementWorkFlowService.getColumnsMapbyWorkflowStage(shortCode, workflowId, orgLocationId);
			
		}
		
		/**
		 *  below request mapping used to test send email to candidate for work flow questionnaire
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/sendEmailToCandidateQuestionnaire")
		@ResponseBody
		public String sendEmailToCandidateWithQuestionnaireTemplate(HttpServletRequest request)throws Exception {
			OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
			User loginUser  =    (User) SourceLeadSessionManager.getSessionAttribute(request,	"user");
			System.out.println("inside sending questionnaire link to candidate Started >>>>>>>>>>>>>>>>>");
			HashMap<String , Object> paramsMap = new HashMap<String , Object>();
		   // here creating a dummy candidate for testing email functionality.
		    User candidate = new User();
		    candidate.setId(Long.valueOf("4193"));
		    candidate.setFirstName("Srinivas");
		    candidate.setLastName("Sabbu");
		    candidate.setEmail("srinivas.sabbu@businessintelli.com");
		    
			paramsMap.put("candidate" , candidate); 
			paramsMap.put("orgLocations" , orgLocations);
			paramsMap.put("loginUser" , loginUser);
			paramsMap.put("templateId" , "2");
			paramsMap.put("templateUuid" , "LbDT9TBn");
			paramsMap.put("stageName" , "Screening");
			paramsMap.put("jobTitle" , "SAP ABAP Developer");
			
			String status= requirementWorkFlowService.sendEmailToCandidateWithQuestionnaireTemplate(paramsMap);
			
			System.out.println("Email sent successfully  "+status);
			return status;
		}
}
