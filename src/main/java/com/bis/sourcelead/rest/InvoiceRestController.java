package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.InvoiceService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class InvoiceRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(InvoiceRestController.class);
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private OrgSpocService orgSpocService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private ProjectsService projectsService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@RequestMapping(value = "/getClientContactsByOrgRelationId", method = RequestMethod.GET)
	public @ResponseBody String getVendorContactsByOrgRelationId(@RequestParam(value = "orgRelationId", required = false) String orgRelationId,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocByOrgRelationId(Long.parseLong(orgRelationId));
		
		for(OrgSpoc orgSpoc : orgSpocList){
			StringBuilder sb = new StringBuilder();
			StringBuilder clientSb = new StringBuilder();
			if(null != orgSpoc.getVendorUser().getFirstName() && null != orgSpoc.getVendorUser().getLastName()){
			sb.append(orgSpoc.getVendorUser().getFirstName()).append(" ").append(orgSpoc.getVendorUser().getLastName());
			}else{
			sb.append(orgSpoc.getVendorUser().getFirstName());
			}
			if(null != orgSpoc.getClientUser().getFirstName() && null != orgSpoc.getClientUser().getLastName()){
			clientSb.append(orgSpoc.getClientUser().getFirstName()).append(" ").append(orgSpoc.getClientUser().getLastName());
			}else{
			clientSb.append(orgSpoc.getClientUser().getFirstName());
			}
			orgSpoc.setSpocName(sb.toString());
			orgSpoc.setSpocUserId(orgSpoc.getClientUser().getId());
			orgSpoc.setTempOwnerId(orgSpoc.getVendorUser().getId());
			orgSpoc.setOwnerName(clientSb.toString());
			}
		return commonUtil.toJSON(orgSpocList);
	} 
	
	
	@RequestMapping(value = "/saveInvoice", method = RequestMethod.POST)
	public @ResponseBody String saveClient(@RequestParam(value = "json", required = false) String json,
			HttpServletRequest request) throws Exception {

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		return "success";
	} 
	
    @RequestMapping(value = "/getAssociatesList", method = RequestMethod.GET)
 	public @ResponseBody  String getUserList(HttpServletRequest request, HttpServletResponse response) throws Exception
 	{
     	String resultJson;
     	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
     	List<Candidate> associateList = candidateService.getAllActiveAssociatesByLocation(loginOrgLocation.getId());
 	  resultJson = commonUtil.toJSON(associateList);
 	  return resultJson;
 	}
    
//    /getClientProjectsByOrgRelationId 
    
    @RequestMapping(value = "/getClientProjectsByOrgRelationId", method = RequestMethod.GET)
 	public @ResponseBody  String getUserList(@RequestParam(value = "orgRelationId", required = false) Long orgRelationId,HttpServletRequest request, HttpServletResponse response) throws Exception
 	{
     	String resultJson;
     	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
     	List<Projects> projectsList = projectsService.getClientProjectsByOrgRelationId(orgRelationId,loginOrgLocation.getId());
 	  resultJson = commonUtil.toJSON(projectsList);
 	  return resultJson;
 	}
    
    @RequestMapping(value = "/getSowcontractListByAssociateId", method = RequestMethod.GET)
 	public @ResponseBody  String getSowcontractListByAssociateId(@RequestParam(value = "candidateId", required = false) Long candidateId,
 			@RequestParam(value = "orgRelationId", required = false) Long orgRelationId,HttpServletRequest request, HttpServletResponse response) throws Exception
 	{
     	String resultJson;
     	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
     	
     	
     	List<Contracts> projectsList = invoiceService.getSowcontractListByAssociateId(candidateId,loginOrgLocation.getId(),orgRelationId);
 	  resultJson = commonUtil.toJSON(projectsList);
 	  return resultJson;
 	}
    
    
    
}
