package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@PropertySource("${propertyLocation:app.properties}")
/**
 * 
 * @author root
 * Handles client requests
 */
public class ClientRestController {

	private static final Logger logger = LoggerFactory.getLogger(ClientRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	private OrgService orgService;

	@Autowired
	private OrgLocationService orgLocationService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private OrgRelationsService orgRelationsService;

	@Autowired
	private ProjectsService projectsService;

	@Autowired
	HttpServletRequest request;
	ProjectApproverHierarchyService projectApproverHierarchyService;

	@Autowired
	ProjectMembersService projectMembersService;

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private OrgSpocService orgSpocService;

	@Autowired
	ClientService clientService;
	
	/**
	 * Handles client as well as project save
	 * 
	 * @param json
	 * @param request
	 * @return ResponseBody
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveClient", method = RequestMethod.POST)
	public @ResponseBody String saveClient(@RequestParam(value = "json", required = false) String json,
			@RequestParam(value = "responderType", required = false) String responderType,
			HttpServletRequest request) throws Exception {

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		clientService.storeClient(jsonObj, user, orgLocation,responderType);
	 	
		SourceLeadSessionManager.setSessionAttribute(request, "clientCreateMsg",
				"Successfully you have created a client");

		return "clientDashboard";
	}

	/**
	 * validate duplicate projects by project sequence number
	 * 
	 * @param ProjectSequeneNumber
	 * @param request
	 * @return boolean
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateProjectSequeneNumberId", method = RequestMethod.POST)
	public @ResponseBody Boolean validateProjectSequeneNumberById(
			@RequestParam(value = "ProjectSequeneNumber", required = false) String ProjectSequeneNumber,
			HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		Boolean flag = projectsService.validateProjectSequeneNumber(ProjectSequeneNumber, orgLocations.getId());

		return flag;
	}

	/**
	 * Handles client Details by OrgRelationId
	 * 
	 * @param orgLocationId
	 * @param request
	 * @return JSON
	 * @throws Exception
	 */

	@RequestMapping(value = "/getClientDeatilsById", method = RequestMethod.POST)
	@ResponseBody String getProjectById(@RequestParam(value = "clientId", required = false) String orgLocationId) throws Exception {
		OrgRelations orgRelation = orgRelationsService.getClientByOrgLocationId(Long.parseLong(orgLocationId));
		Long projectCount = orgRelationsService.getNumberOfProjectsPerClient(orgRelation.getId());
		orgRelation.setProjectsCont(projectCount);
		return commonUtil.toJSON(orgRelation);
	}
	
	
	/**@author Prasad Salina
	 * To get all the spocs list
	 * @param orgLocationId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getOrgSpocsList", method = RequestMethod.POST)
	public @ResponseBody String getOrgSpocsList(@RequestParam(value = "orgRelationId", required = false) Long orgRelationId) throws Exception {
		List<OrgSpoc> orgSpocsList  = orgSpocService.getAllSpocsByOrgRelationId(orgRelationId);
		for (OrgSpoc orgSpoc : orgSpocsList) {
			orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
			orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
			orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
			orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
		}
		return commonUtil.toJSON(orgSpocsList);
	}


	/**
	 * Handles project inline editing
	 * @param orgRelationId
	 * @param fieldName
	 * @param fieldValue
	 * @param request
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = "/clientDetailsUpdateUsingId", method = RequestMethod.POST)
	public String clientDetailsUpdateUsingId(@RequestParam(value = "json", required = false) String json,
			@RequestParam(value = "orgRelationId", required = false) Long orgRelationId) throws Exception {
		
	       json = "{" + json + "}";
	       JSONObject jsonObj = new JSONObject(json);
	
	       clientService.updateClientDetails(jsonObj, orgRelationId);

		return "success";
	}

	/**
	 * Handles ClientName duplicate or not
	 * 
	 * @param clientName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ws/isClientNameValid", method = RequestMethod.GET)
	public @ResponseBody boolean isClientNameValidated(
			@RequestParam(value = "clientName", required = false) String clientName,HttpServletRequest request) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		return orgService.isClientNameValid(clientName,orgLocation.getId());
	}
/**
 * Verifies Client Email
 * @param EmailId
 * @return
 * @throws Exception
 */
	@RequestMapping(value = "/getClientEmailVerification", method = RequestMethod.POST)
	public @ResponseBody String validateClientEmail(@RequestParam(value = "EmailId", required = false) String EmailId)
			throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String result;
		OrgLocations orgLocations = orgLocationService.findByEmail(EmailId,orgLocation.getId());

		if (orgLocations != null) {
			result = "true";
		} else {
			result = "false";
		}
		return result;
	}

	/***
	 * @author  Prasanna vadla
	 * 26-8-2016
	 * To get the details of the org by the orgLocationId and orgRelationId
	 * to fetch user details by usercode 
	 * @param orgLocationId
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/getClientAndVendorOrgDeatilsById", method = RequestMethod.POST)
	@ResponseBody String getClientAndVendorOrgDeatilsById(@RequestParam(value = "vendorLocationId", required = false) Long orgLocationId,
			@RequestParam(value = "orgRelationId", required = false) Long orgRelationId) throws Exception {
		OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(orgLocationId,orgRelationId);
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		//if login user org id is equals to vendor location id then we get vendor list of ResponderLocationId Org details 
		 if(orgRelation.getLocationId().getId()== orgLocation.getId()){ 
				orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrg().getOrgName());
				orgRelation.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
				orgRelation.setCountry(orgRelation.getResponderLocationId().getCountry());
				orgRelation.setCity(orgRelation.getResponderLocationId().getCity());
				orgRelation.setState(orgRelation.getResponderLocationId().getState());
				orgRelation.setPhone(orgRelation.getResponderLocationId().getPhone());
				orgRelation.setEmail(orgRelation.getResponderLocationId().getEmail());
				
		 }
		 //to fetch user details by usercode 
		User user = userService.getUserDetailsByUserCode(orgRelation.getCreatedBy());
		orgRelation.setVendorCreateduserName(user.getFirstName()+" "+user.getLastName());
		
		//if login user org id is equals to vendor ResponderLocationId id then we get vendor list of location Org details 
		 if(orgRelation.getResponderLocationId().getId()== orgLocation.getId()){ 
				orgRelation.setOrgName(orgRelation.getLocationId().getOrg().getOrgName());
				orgRelation.setWebSite(orgRelation.getLocationId().getOrg().getWebSite());
				orgRelation.setCountry(orgRelation.getLocationId().getCountry());
				orgRelation.setCity(orgRelation.getLocationId().getCity());
				orgRelation.setState(orgRelation.getLocationId().getState());
				orgRelation.setPhone(orgRelation.getLocationId().getPhone());
				orgRelation.setEmail(orgRelation.getLocationId().getEmail());
		 } 
		 
		return commonUtil.toJSON(orgRelation);
		 
	}
	
	/**
	 * @author Prasanna vadla
	 * @param json
	 * @param vendorLocationId
	 * @param orgRelationId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/vendorDetailsUpdateUsingId", method = RequestMethod.POST)
	public String vendorDetailsUpdateUsingId(@RequestParam(value = "json", required = false) String json,
			@RequestParam(value = "vendorLocationId", required = false) Long vendorLocationId,
			@RequestParam(value = "orgRelationId", required = false) Long orgRelationId) throws Exception {
		
	       json = "{" + json + "}";
	       JSONObject jsonObj = new JSONObject(json);
	
	       OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	       clientService.updateVendorDetails(jsonObj, orgRelationId,vendorLocationId, orgLocation.getId());

		return "success";
	}
	
	/**
	 * check given string empty or null
	 * @param str
	 * @return boolean
	 */
	protected static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}

}