package com.bis.sourcelead.web;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

import oauth.signpost.http.HttpRequest;

@Controller
public class ReportsController {

private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	OrgRelationsService orgRelationsService;

	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value = "/recruitersReports")
	public ModelAndView recruitersReports(HttpServletRequest request,HttpServletResponse response) throws Exception {
	 
	 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 ModelAndView model = new ModelAndView();
	 List<User> userList = userService.getAllUsersByOrgLocationCode(orgLocations.getId());
	 model.addObject("userList", userList);
	 model.addObject("sourceleadUrl", sourceleadUrl);
	 model.setViewName("reports/userReports");
	 return model;
		
    }	
	
	 @RequestMapping(value = "/applicantsListForReports")
		public ModelAndView applicantsListForReports() throws Exception {
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/applicantsReport");
		 return model;
			
	 }	
	 
	 @RequestMapping(value = "/applicantsReceivedListForReports")
		public ModelAndView applicantsReceivedListForReports() throws Exception {
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/applicantsReceivedReport");
		 return model;
	 }
	 
	 @RequestMapping(value = "/applicantsSubmittedListForReports")
		public ModelAndView applicantsSubmittedListForReports(HttpServletRequest request) throws Exception {
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 ModelAndView model = new ModelAndView();
		 Collection<OrgRelations> orgClientList = orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId()); 
			if(null != orgClientList && orgClientList.size() > 0){
				for(OrgRelations ocl :orgClientList){
					ocl.setOrgName(ocl.getResponderLocationId().getOrg().getOrgName());
					ocl.setOrgid(ocl.getResponderLocationId().getOrg().getId());
				 }
			    model.addObject("add_clientList", orgClientList);
			}
		 model.addObject("loginOrgLocId", orgLocations.getId());
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/applicantsSubmittedReport");
		 return model;
	 }	 
	 
	 @RequestMapping(value = "/requirementStagesListForReports")
		public ModelAndView requirementStagesListForReports() throws Exception {
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/requirementStageReports");
		  return model;		
	 }	

	 /**
	  * returns requirement assign to reports jsp page
	  * @return ModelAndView
	  * @throws Exception
	  */
	 @RequestMapping(value = "/requirementAssignToReports")
		public ModelAndView requirementAssignToReports(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 ModelAndView model = new ModelAndView();
		 List<User> userList = userService.getUsersByUserRolesAndOrgLocationId(orgLocation.getId());
		 model.addObject("userList", userList);
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/requirementAssignToReports");
		 return model;	
	 }	
	 
	 /**
	  * @author Pavan Jampana
	  * returns vendors report page 
	  */
	 @RequestMapping(value = "/vendorsReports")
		public ModelAndView vendorsReports() throws Exception {
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/vendorsReport");
		 return model;
	 }	
}
