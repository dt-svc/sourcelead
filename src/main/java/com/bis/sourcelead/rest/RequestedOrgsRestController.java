package com.bis.sourcelead.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.dto.EmployerRegistrationDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRequest;
import com.bis.sourcelead.service.OrgRequestService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class RequestedOrgsRestController {
	
	@Autowired
	OrgRequestService orgRequestService;

	
	 @RequestMapping(value = {"/getDemoOrgsList", "/rest/getDemoOrgsList"}, method = RequestMethod.GET)
	 @ResponseBody  ResponseList<EmployerRegistrationDTO> getDemoOrgsList(EmployerRegistrationDTO employerRegistrationDTO,HttpServletRequest request) throws Exception {
		 
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				    String[] tableColumns = {"companyName", "requestedBy", "country", "email", "phoneno", "modules", "demodate"};
				    int sortColumnIndex = 1; //by default sorting using updated date
					String sortColumnOrder = "desc"; //by default descending order
					if(employerRegistrationDTO.getOrder() != null && !employerRegistrationDTO.getOrder().isEmpty()) {
						sortColumnIndex = Integer.parseInt(employerRegistrationDTO.getOrder().get(0).get("column"));
						sortColumnOrder = employerRegistrationDTO.getOrder().get(0).get("dir");
					}
					employerRegistrationDTO.setSortColumn(tableColumns[sortColumnIndex]);
					employerRegistrationDTO.setSortOrder(sortColumnOrder);
			ResponseList<EmployerRegistrationDTO> demoOrgsList = orgRequestService.getDemoRequestsList(employerRegistrationDTO,orgLocations,"Demo");
			return demoOrgsList;
		   
	   }
	 
	 @RequestMapping(value = {"/getRequestOrgsList", "/rest/getRequestOrgsList"}, method = RequestMethod.GET)
	 @ResponseBody  ResponseList<EmployerRegistrationDTO> getRequestOrgsList(EmployerRegistrationDTO employerRegistrationDTO,HttpServletRequest request) throws Exception {
		 
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				    String[] tableColumns = {"companyName", "requestedBy", "country", "email", "phoneno", "modules"};
				    int sortColumnIndex = 1; //by default sorting using updated date
					String sortColumnOrder = "desc"; //by default descending order
					if(employerRegistrationDTO.getOrder() != null && !employerRegistrationDTO.getOrder().isEmpty()) {
						sortColumnIndex = Integer.parseInt(employerRegistrationDTO.getOrder().get(0).get("column"));
						sortColumnOrder = employerRegistrationDTO.getOrder().get(0).get("dir");
					}
					employerRegistrationDTO.setSortColumn(tableColumns[sortColumnIndex]);
					employerRegistrationDTO.setSortOrder(sortColumnOrder);
			ResponseList<EmployerRegistrationDTO> demoOrgsList = orgRequestService.getDemoRequestsList(employerRegistrationDTO,orgLocations,"Request");
			return demoOrgsList;
		   
	   }
	 
	 
	 
	 
	 @RequestMapping(value = {"/changeOrgDemoRequestStatus", "/rest/changeOrgDemoRequestStatus"}, method = RequestMethod.GET)
	 @ResponseBody  String changeOrgDemoRequestStatus(EmployerRegistrationDTO employerRegistrationDTO,HttpServletRequest request) throws Exception {
		 
		 OrgRequest orgRequest = orgRequestService.getOrgRequestById(employerRegistrationDTO.getId());
		 if(orgRequest != null) {
			 orgRequest.setRequestStatus(employerRegistrationDTO.getOrgRequestStatus());
			 orgRequestService.saveOrgRequest(orgRequest);
		 }
		
		 return "success";
		 
	   }
}
