package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.dto.RequirementDefaultWorkFlowStagesDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementWorkFlow;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowDTO;
import com.bis.sourcelead.workflow.service.RequirementWorkFlowService;

@Controller
@RequestMapping("/app")
public class RequirementWorkflowController {
	
	@Autowired
	private RequirementWorkFlowService requirementWorkFlowService;

	@RequestMapping(value = "/workflowDashboard")
	public ModelAndView workflowDashboard(HttpServletRequest request) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("workflow/workflowDashboard");
		return modelandview;
	}
	
	@RequestMapping(value = "/defaultWorkflow")
	public ModelAndView defaultWorkflow(HttpServletRequest request) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("workflow/defaultWorkflow");
		return modelandview;
	}
	
	@RequestMapping(value = "/workflowList")
	public ModelAndView workflowList(HttpServletRequest request) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("workflow/workflowList");
		return modelandview;
	}
	
	@RequestMapping(value = "/viewWorkflow")
	public ModelAndView viewWorkflow(HttpServletRequest request) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("workflow/viewWorkflow");
		return modelandview;
	}
	
	
	@RequestMapping(value = "/addStage")
	public ModelAndView addStage(HttpServletRequest request) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("workflow/addStage");
		return modelandview;
	}
	
	@RequestMapping(value = "/questionnaireList")
	public ModelAndView questionnaireList(HttpServletRequest request) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("workflow/questionnaireList");
		return modelandview;
	}
	
	@RequestMapping(value = "/addQuestionnaire")
	public ModelAndView addQuestionnaire(HttpServletRequest request) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("workflow/addQuestionnaire");
		return modelandview;
	}
	
	
	@RequestMapping(value = "/saveWorkFlowDetailsPage", method = RequestMethod.POST)
	public ResponseList<String> saveWorkFlowDetailsPage(
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
	public @ResponseBody List<RequirementDefaultWorkFlowStagesDTO> createRequirementDefaultWorkFlow(
			RequirementWorkFlowDTO requirementWorkFlowDTO,
			HttpServletRequest request) {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<RequirementDefaultWorkFlowStagesDTO> requirementDefaultWorkFlowStagesDTO= null;
		try {
			List<RequirementWorkFlow> requirementWorkFlow = requirementWorkFlowService.getRequirementWorkFlowByOrgLocationId(orgLocations.getId());
			if (requirementWorkFlow == null || requirementWorkFlow.isEmpty()) {
				requirementWorkFlowService.saveRequirementWorkFlowStagesByOrgLocationId(orgLocations, user, requirementWorkFlowDTO);
				requirementDefaultWorkFlowStagesDTO = requirementWorkFlowService.getRequirementDefaultWorkFlowStages();
			}
			if(requirementWorkFlow.size() >= 1 && !(requirementWorkFlow.isEmpty())) {
				requirementDefaultWorkFlowStagesDTO = requirementWorkFlowService.getRequirementDefaultWorkFlowStages();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return requirementDefaultWorkFlowStagesDTO;

	}
	
	@RequestMapping(value = "/getWorkflowList", method = RequestMethod.GET)
	public ResponseList<RequirementWorkFlowDTO> getWorkflowList(
			RequirementWorkFlowDTO requirementWorkFlowDTO,
			HttpServletRequest request){
		ResponseList<RequirementWorkFlowDTO> responseList = new ResponseList<>();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		try {
			
			String[] tableColumns = { "workFlowName", "noOfStages", "status", "Action"};
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
	
}
