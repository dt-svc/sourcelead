package com.bis.sourcelead.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
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
		requirementWorkFlowService.saveRequirementDefaultWorkFlowAndStagesByOrgLocationId(orgLocations, user);
		modelandview.setViewName("workflow/defaultWorkflow");
		return modelandview;
	}
	
	@RequestMapping(value = "/workflowList")
	public ModelAndView workflowList(HttpServletRequest request) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("workflow/workflowList");
		OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
		Long userId =  (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		User user = new User();
		user.setId(userId);
		requirementWorkFlowService.saveRequirementDefaultWorkFlowAndStagesByOrgLocationId(orgLocations, user);
		return modelandview;
	}
	
	@RequestMapping(value = "/viewWorkflow")
	public ModelAndView viewWorkflow(HttpServletRequest request,
			RequirementWorkFlowDTO requirementWorkFlowDTO) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
		List<RequirementWorkFlowStagesDTO> requirementWorkFlowStagesDTO = requirementWorkFlowService.getRequirementWorkflowStagesByWorkflowUuid(requirementWorkFlowDTO, orgLocations);
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
		
		List<RequirementWorkFlowStagesDTO> requirementWorkFlowStagesDTO = requirementWorkFlowService.getRequirementWorkflowStagesByWorkflowUuid(requirementWorkFlowDTO, orgLocations);
		
		modelandview.setViewName("workflow/addStage");
		modelandview.addObject("requirementWorkFlowStagesDTO", requirementWorkFlowStagesDTO);
		modelandview.addObject("workflowUuid", requirementWorkFlowStagesDTO.get(0).getRequirementWorkFlowUuid());
		return modelandview;
	}
	
	@RequestMapping(value = "/questionnaireList")
	public ModelAndView goToquestionnaireList(HttpServletRequest request) throws Exception{
		ModelAndView modelandview = new ModelAndView();
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
		return responseList;
	}
	@RequestMapping(value = "/addQuestionnaire")
	public ModelAndView addQuestionnaire(HttpServletRequest request) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("workflow/addQuestionnaire");
		return modelandview;
	}
	@RequestMapping(value = "/editQuestionnaire")
	public ModelAndView editQuestionnaire(@RequestParam(value = "templateUuid", required = true) String templateUuid, HttpServletRequest request) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		modelandview.addObject("templateUuid",templateUuid);
		modelandview.setViewName("workflow/addQuestionnaire");
		return modelandview;
	}
	 @RequestMapping(value = "/loadSavedQuestionnaireTemplate" , method = RequestMethod.GET)
	   public @ResponseBody RequirementQuestionnaireTemplate loadSavedquestionnaireTemplate(@RequestParam(value="templateUuid", required=true) String templateUuid,HttpServletRequest request) throws Exception {
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
		
		return response;
	}
	
	@RequestMapping(value = "/createRequirementDefaultWorkFlow", method = RequestMethod.GET)
	public @ResponseBody RequirementWorkFlowDTO createRequirementDefaultWorkFlow(
			RequirementWorkFlowDTO requirementWorkFlowDTO, HttpServletRequest request) {
		RequirementWorkFlowDTO workFlowDTO = null;
		OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
		try {
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
		try {
			
			String[] tableColumns = { "workFlowName", "noOfStages", "status"};
		  	int sortColumnIndex = -1; 
	  		String sortColumnOrder = "desc"; 
	  		if (requirementWorkFlowDTO.getOrder() != null && !requirementWorkFlowDTO.getOrder().isEmpty()) {
	  		sortColumnIndex = Integer.parseInt(requirementWorkFlowDTO.getOrder().get(0).get("column"));
	  		sortColumnOrder = requirementWorkFlowDTO.getOrder().get(0).get("dir"); 
	  		requirementWorkFlowDTO.setSortOrder(sortColumnOrder);
	  		}
	  		if (sortColumnIndex != -1){
	  			requirementWorkFlowDTO.setSortColumn(tableColumns[sortColumnIndex]);
	  		}
			responseList = requirementWorkFlowService.getRequirementWorkFlowList(requirementWorkFlowDTO, orgLocations);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return responseList;
		
	}
// Below methods are related to work flow questionnaire added by SRINIVAS SABBU.
	@RequestMapping(value = "/saveQuestionnaireTemplate", method = RequestMethod.POST)
	public @ResponseBody String saveQuestionnaireTemplate(@RequestParam(value = "jsonData", required = false) String json,
			@RequestParam(value = "templateId", required = false) String templateId,
			@RequestParam(value = "timeLimitMinutes", required = false) Integer timeLimitMinutes,
			@RequestParam(value = "timeLimitSeconds", required = false) Integer timeLimitSeconds,
			@RequestParam(value = "jsonStringData", required = false) String jsonStringData, HttpServletRequest request)
			throws Exception {

		Long orgLocationId  = (Long) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocationId");
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(orgLocationId);
		Long userId =  (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		User user = new User();
		user.setId(userId);
		String result = null;

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
		
		return response;

	}
		
		@RequestMapping(value = "/saveRequirementWorkflowStage")
		public @ResponseBody Response<String> saveRequirementWorkflowStage(HttpServletRequest request,
				RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO) {
			OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
			Long userId =  (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
			User user = new User();
			user.setId(userId);
			Response<String> response = new Response<>();
				try {
					if (user != null && null != requirementWorkFlowStagesDTO) {
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

			return requirementDefaultWorkFlowStagesDTO;
		}
		
		@RequestMapping(value = { "/deleteWorkflowStage" })
		public String deleteWorkflowStage(
				@RequestParam(value = "uuid", required = false) String uuid,
				HttpServletRequest request) throws Exception {
		
			OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
			
			requirementWorkFlowService.deleteRequirementWorkFlowStagesByuuid(uuid, orgLocations);
			
			
			List<RequirementWorkFlowStages> stagesList = requirementWorkFlowService.getRequirementWorkFlowStagesListByOrgLocationId(orgLocations);
			int stageCount = 1;
			for(RequirementWorkFlowStages stage:stagesList) {
				stage.setSeqOrder(stageCount);
				stageCount++;
				requirementWorkFlowService.saveRequirementWorkFlowStages(stage);
				
			}
			return "success";
		}
		
		@RequestMapping(value = "/getRequirementQuestionnaireTemplates")
		public @ResponseBody List<RequirementQuestionnaireTemplateDTO> getRequirementQuestionnaireTemplates(HttpServletRequest request){
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<RequirementQuestionnaireTemplateDTO> requirementQuestionnaireTemplateDTO = null;
			try {
				requirementQuestionnaireTemplateDTO = requirementWorkFlowService.getRequirementQuestionnaireTemplates(orgLocations.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}

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
			return responseList;
		
}
		
		@RequestMapping(value = { "/deleteRequirementWorkflowStage" })
		public @ResponseBody String deleteRequirementWorkflowStage(RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO,
				HttpServletRequest request) throws Exception {
			OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
			Long userId =  (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
			User user = new User();
			user.setId(userId);
			Boolean flag;
			flag = requirementWorkFlowService.saveRequirementWorkFlowStagesBySeqOrder(requirementWorkFlowStagesDTO, orgLocations, user);
			if(flag == true) {
				return Constants.SUCCESS;
			}
			return Constants.FAILED;
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
			
			return response;
		}
		
		@RequestMapping(value="/removeWorkFlowEntry" , method = RequestMethod.POST)
		public @ResponseBody String removeWorkFlowEntry(@RequestParam(value = "workflowuuid", required = false) String workflowuuid, HttpServletRequest request) throws Exception {
			
			OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
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
			return response;
			
		}
		
		@RequestMapping(value = "/editStage")
		public ModelAndView editStage(HttpServletRequest request,
				RequirementWorkFlowStagesDTO requirementWorkFlowStagesDTO) throws Exception{
			ModelAndView modelandview = new ModelAndView();
			OrgLocations orgLocations  = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,	"orgLocations");
			RequirementWorkFlowDTO requirementWorkFlowDTO = new RequirementWorkFlowDTO();
			requirementWorkFlowDTO.setUuid(requirementWorkFlowStagesDTO.getRequirementWorkFlowUuid());
			requirementWorkFlowDTO.setCurrentStageUuid(requirementWorkFlowStagesDTO.getUuid().trim());
			
			RequirementWorkFlowStages stages = requirementWorkFlowService.getRequirementWorkFlowStageForEditStage(requirementWorkFlowStagesDTO, orgLocations);
			
			List<RequirementWorkFlowStagesDTO> stagesDTO = requirementWorkFlowService.getNextRequirementWorkflowStagesByWorkflowUuid(requirementWorkFlowDTO, orgLocations);
			
			modelandview.setViewName("workflow/addStage");
			modelandview.addObject("stageName", stages.getStageName());
			modelandview.addObject("description", stages.getDescription());
			modelandview.addObject("currentStageUuid", requirementWorkFlowStagesDTO.getUuid().trim());
			modelandview.addObject("workflowUuid", requirementWorkFlowStagesDTO.getRequirementWorkFlowUuid());
			modelandview.addObject("requirementWorkFlowStagesDTO", stagesDTO);
			return modelandview;
		}
		
}
