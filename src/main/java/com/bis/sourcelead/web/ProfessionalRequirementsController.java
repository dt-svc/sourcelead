package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class ProfessionalRequirementsController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	RequirementService requirementService;
	
	@Autowired
	CommonUtil commonUtil;
	
	 @PreAuthorize("hasRole('ROLE_CROWDSOURCING_REQUIREMENTS')")
	 @RequestMapping(value = "/professionalRequirements")
		public ModelAndView professionalProfile(HttpServletRequest request) throws Exception {
		    
		        Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
				
		        ModelAndView model = new ModelAndView();
				
				SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
				if(sessionData == null)
				{
					sessionData = new SessionMaintainanceData();
				}
				sessionData.setNav(Constants.Requirement);
				
				request.getSession().setAttribute("sessionData",sessionData);
				
				model.setViewName("professional/professionalRequirements");
				model.addObject("sourceleadUrl", sourceleadUrl);
				model.addObject("candidateId", candidateId);
				
				return model;
	}
	 
	 @RequestMapping(value = "/professionalRequirementProfile/{requirementId}", method = RequestMethod.GET)
		public ModelAndView professionalRequirementProfile(HttpServletRequest request) throws Exception {
		        Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
		        ModelAndView model = new ModelAndView();
				SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
				if(sessionData == null)
				{
					sessionData = new SessionMaintainanceData();
				}
				sessionData.setNav(Constants.Requirement);
				request.getSession().setAttribute("sessionData",sessionData);
				model.setViewName("professional/professionalRequirementProfile");
				model.addObject("sourceleadUrl", sourceleadUrl);
				model.addObject("candidateId", candidateId);
				
				return model;
	}
	 
	 @RequestMapping(value = "/requirementProfile/{requirementUUID}", method = RequestMethod.GET)
		public @ResponseBody void userSummary(@PathVariable String requirementUUID, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			StringBuilder redirectUrl = new StringBuilder();
			response.sendRedirect(redirectUrl.append("/sourcelead/requirementProfileDetails?requirementUUID=").append(requirementUUID).toString());

		}
	 
	 @RequestMapping(value = "/requirementProfileDetails")
		public ModelAndView userSummaryDetails(HttpServletRequest request, @RequestParam(value = "requirementUUID", required = false) String requirementUUID) throws Exception {
			  ModelAndView model = new ModelAndView();
			  SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			 if(sessionData == null)
			 {
			 	sessionData = new SessionMaintainanceData();
			 }
			sessionData.setNav(Constants.Requirement);
			request.getSession().setAttribute("sessionData",sessionData);
			model.setViewName("professional/professionalRequirementProfile");
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.addObject("requirementUUID", requirementUUID);
			return model;

		}
	 
	 @RequestMapping(value = "/getRequirementProfessionalProfileDetails" , method = RequestMethod.GET)
	 @ResponseBody
		public String getRequirementProfessionalProfileDetails(@RequestParam(value="requirementUUID", required=false) String  requirementUUID,HttpServletRequest request) throws Exception {
		        String resultJson;
		        Requirement requirement = requirementService.getRequirementByRequirementUUID(requirementUUID);
		        if(null != requirement.getOrgLocationId()){
		        	if(null != requirement.getOrgLocationId().getOrg()){
		        		if(null != requirement.getOrgLocationId().getOrg().getOrgCode()){
		        			requirement.setOrgcode(requirement.getOrgLocationId().getOrg().getOrgCode());	
		        		}
		        	}
		        }
		        resultJson = commonUtil.toJSON(requirement);
				return resultJson;
	}
}
