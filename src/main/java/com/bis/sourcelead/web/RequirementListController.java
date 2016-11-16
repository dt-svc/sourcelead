package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class RequirementListController {
	
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	
	@RequestMapping(value = "/requirementList")
	public ModelAndView requirementList(HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("requirement/requirementList");
		
		OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		
		  List<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(loginOrgLocations.getId()); 
	        for(OrgRelations orgRelations:orgClientList){
	        	orgRelations.setClientName(orgRelations.getResponderLocationId().getOrg().getOrgName());
	        }
	        model.addObject("add_client", orgClientList);
	        model.addObject(orgClientList);
	        
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Requirement);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		
		return model;

	}
	
	
	@RequestMapping(value = "/releaseRequirementToClientOrVendors")
	public ModelAndView releaseRequirementToClientOrVendors(String requirementId,HttpServletRequest request) {
		
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("requirement/releaseRequirement");
		
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Requirement);
		
		
		List<OrgRelations> vendorsList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLoc.getId());
		for(OrgRelations orgRelation :vendorsList){
			orgRelation.setClientName(orgRelation.getResponderLocationId().getOrg().getOrgName());
		}
		model.addObject("vendor_list", vendorsList);
        model.addObject(vendorsList);
		
		request.getSession().setAttribute("sessionData",sessionData);
		return model;

	}
	
	
	
	
	
	
	
	 
}
