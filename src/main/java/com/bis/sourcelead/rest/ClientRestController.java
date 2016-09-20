package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;

import com.bis.sourcelead.entity.Org;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.form.OrgSettings;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * 
 * @author root
 * Handles all client related operations
 */
@RestController
@PropertySource("${propertyLocation:app.properties}")
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
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	SubmissionTemplateService submissionTemplateService;
	
	
	/**
	 * Handles to get Orgs
	 * @param json
	 * @param request
	 * @return ResponseBody
	 * @throws Exception
	 */
	
	 @RequestMapping(value = "/getOrgs",  method = RequestMethod.POST)
	 @ResponseBody
	    public  String getOrgs(@RequestParam(value="orgCode", required=false) String orgCode,
	    		@RequestParam(value="json", required=false) String json) {
		 
		    HashMap<String, Long> hm = new HashMap<String, Long>();	
	    	List<OrgLocations> orgLocationsList = null;
			try {
				orgLocationsList = orgLocationService.getOrgsListForClientAndVendorOrgSearch(orgCode);
			} catch (Exception e) {
				logger.error(ExceptionUtils.getFullStackTrace(e)); 
			}
			if(orgLocationsList != null)
			{
				for(OrgLocations orgLocations: orgLocationsList){
					hm.put(orgLocations.getOrg().getOrgName(), orgLocations.getId());
				}
			}
			return commonUtil.toJSON(hm);
	    }
	 
	 
	 /**
		 * Handles to get Org Deatils
		 * 
		 * @param json
		 * @param request
		 * @return ResponseBody
		 * @throws Exception
		 */	 
	 
	 @RequestMapping(value = "/getOrgDetails",  method = RequestMethod.POST)
	    public @ResponseBody String getOrgDetails(@RequestParam(value="orgLocationId", required=false) Long orgLocationId,
	    		@RequestParam(value="json", required=false) String json) {
			
			ArrayList<String> orgsList = new ArrayList<String>();
			
			OrgLocations orgLocations = null;
			try {
				orgLocations = orgLocationService.getOrgLocations(orgLocationId);
			} catch (Exception e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			if(orgLocations != null)
			{
				orgsList.add(orgLocations.getCountry());
				orgsList.add(orgLocations.getState());
				orgsList.add(orgLocations.getCity());
				orgsList.add(orgLocations.getOrg().getWebSite());
			}
			return commonUtil.toJSON(orgsList);
			
		}
	
		
		@RequestMapping(value = "/saveClient", method = RequestMethod.POST)
			public @ResponseBody String saveClient(@RequestParam(value = "json", required = false) String json,
					@RequestParam(value = "responderType", required = false) String responderType,
					@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
					HttpServletRequest request) throws Exception {

				String json1 = "{" + json + "}";
				JSONObject jsonObj = new JSONObject(json1);
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				
				boolean flag = orgService.isClientNameValid(jsonObj.getString("orgName"),orgLocation.getId(),responderType);
				
				if(flag == false){
					if(null != orgLocationId){
						jsonObj.put("orgLocationId", orgLocationId);
					}
					
					clientService.storeClient(jsonObj, user, orgLocation,responderType,org);
				 	
					SourceLeadSessionManager.setSessionAttribute(request, "clientCreateMsg",
							"Successfully you have created a client");

					return "clientDashboard";
				}else{
					return "invalid";
				}
				
				
			} 
		
		 @RequestMapping(value = "/getSpocUserDetails",  method = RequestMethod.POST)
		    public @ResponseBody String getSpocUserDetails(@RequestParam(value="email", required=false) String email,
		    		@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
		    		@RequestParam(value="json", required=false) String json) {
				
			   ArrayList<String> userList = new ArrayList<String>();
			   UserRole userRole = null;
			   User user = null;
				try {
					user = userService.findByEmail(email);
				} catch (Exception e) {
					logger.error(ExceptionUtils.getFullStackTrace(e));
				}
				
				if(null != user)
				{
					userList.add(user.getFirstName());
					userList.add(user.getLastName());
					userList.add(user.getMobilePhone());
				}
				
				if(null != orgLocationId && null != user ){
					try {
						userRole = userRoleService.getUserDetailsByEmailAndLocationId(email,orgLocationId);
					} catch (Exception e) {
						logger.error(ExceptionUtils.getFullStackTrace(e));
					}
					if(userRole != null){
						userList.add(userRole.getRoles().getId().toString());
						userList.add(userRole.getRoles().getDisplayName());
					}
					
				}
				
				return commonUtil.toJSON(userList);
				
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
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		return projectsService.validateProjectSequeneNumber(ProjectSequeneNumber, orgLocations.getId());
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
		User user = userService.getUserDetailsByUserCode(orgRelation.getCreatedBy());
		orgRelation.setVendorCreateduserName(user.getFirstName()+" "+user.getLastName());
		return commonUtil.toJSON(orgRelation);
	}
	
	
	/**@author Prasad Salina
	 * To get all the spocs list
	 * @param orgLocationId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getOrgSpocsList", method = RequestMethod.POST)
	@ResponseBody String getOrgSpocsList(@RequestParam(value = "orgRelationId", required = false) Long orgRelationId) throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		List<OrgSpoc> orgSpocsList  = orgSpocService.getAllSpocsByOrgRelationId(orgRelationId, user.getId(),orgLocations.getId());
		for (OrgSpoc orgSpoc : orgSpocsList) {
			orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
			orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
			orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
			orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
			orgSpoc.setOwnerUserId(orgSpoc.getOwnerUser().getId().toString());
			orgSpoc.setSpocUserId(orgSpoc.getSpocUser().getId());
		}
		return commonUtil.toJSON(orgSpocsList);
	}
	
	
	
	@RequestMapping(value = "/getSubmissionTemplatesList", method = RequestMethod.POST)
	@ResponseBody String getSubmissionTemplatesList(@RequestParam(value = "clientLocationId", required = false) Long clientLocationId) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<SubmissionTemplate> submissiontTemplateList  = submissionTemplateService.getSubmissionTemplateByClientLocationIdAndOrgLocationId(clientLocationId, orgLocations.getId());
		
		return commonUtil.toJSON(submissiontTemplateList);
	}
	
	
	
	@RequestMapping(value = "/validateTemplateName", method = RequestMethod.POST)
	@ResponseBody String validateTemplateName(@RequestParam(value = "clientLocationId", required = false) Long clientLocationId,
			@RequestParam(value = "templateName", required = false) String templateName) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		
		SubmissionTemplate submissiontTemplate  = submissionTemplateService.getSubmissionTemplateByTemplateName(clientLocationId, orgLocations.getId(), templateName);
		if(null != submissiontTemplate){
			return "invalid";
		}else{
			return "valid";
		}
		
	}
	
	
	
	
	

	/** To Terminate Spoc soft deletion only)
	 * @author Prasad Salina
	 * @param spocId
	 * @param reason
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/terminateSpoc", method = RequestMethod.POST)
	 public @ResponseBody String terminateSpoc(@RequestParam(value = "spocId", required = false) Long spocId,@RequestParam(value="reason", required=false) String reason) throws Exception{
		       OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(spocId);
				if(orgSpoc != null){
					orgSpoc.setStatus(Constants.MEMBER_IN_ACTIVE);
					orgSpoc.setTerminateReason(reason);
					orgSpocService.addOrgSpoc(orgSpoc);
					logger.debug("Spoc was terminated");
				}
			
				return "success";
		}
	 
	 /** To change the spoc status
	  * @author Prasad Salina
	  * @param spocId
	  * @param reason
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/changeSpocStatus", method = RequestMethod.POST)
	 @ResponseBody String changeSpocStatus(@RequestParam(value = "spocId", required = false) Long spocId,@RequestParam(value="spocStatus", required=false) String spocStatus) throws Exception{
		       OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(spocId);
				if(orgSpoc != null){
					orgSpoc.setSpocStatus(spocStatus);
					orgSpocService.addOrgSpoc(orgSpoc);
				}
			
				return "success";
		}
	 

	 /** To validate spoc email id while create new spoc
	  * @author Prasad salina
	  * @param EmailId
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/validateSpocEmailId", method = RequestMethod.POST)
	 @ResponseBody String validateSpocEmailId(@RequestParam(value = "email", required = false) String email, @RequestParam(value = "orgRelationId", required = false) Long orgRelationId) throws Exception {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			String result;
			OrgSpoc orgSpoc = orgSpocService.validateSpacEmailIdByOwnerUserIdAndOrgRelationId(email, orgRelationId, user.getId()); 
			if (orgSpoc != null) {
				result = "true";
			} else {
				result = "false";
			}
			return result;
		}

	 /**
	  * Handles Invitation status request
	  * @param spocId
	  * @param invitationStaus
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/saveInvitationStatus", method = RequestMethod.POST)
	 public  String saveInvitationStatus(@RequestParam(value="spocId", required=false) Long spocId,
													@RequestParam(value="invitationStaus", required=false) String invitationStaus) throws Exception{
										             
	        OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(spocId);
	        if(orgSpoc !=  null){
	        	if(invitationStaus != null && "Accepted".equals(invitationStaus)){
	        		orgSpoc.setInvitationStatus(Constants.MAIL_ACCEPTED);
	        	}else if(invitationStaus != null && "Rejected".equals(invitationStaus)){
	        		orgSpoc.setInvitationStatus(Constants.MAIL_REJECTED);
	        	}
	        	orgSpocService.addOrgSpoc(orgSpoc);
	        }
	        return "success";
	    }

	 
	 /** To change the spoc status
	  * @author Prasanna Vadla
	  * @param spocId
	  * @param reason
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/getSpocAndOwnerDetailsById", method = RequestMethod.POST)
	 @ResponseBody String getSpocAndOwnerDetailsById(@RequestParam(value = "spocId", required = false) Long spocId,@RequestParam(value = "ownerId", required = false) Long ownerId,@RequestParam(value = "spocUserId", required = false) Long spocUserId) throws Exception{
		     OrgSpoc orgSpoc =  orgSpocService.getSpocAndOwnerDetailsById(spocId,ownerId);
		     User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		     
		     if(user.getId()==ownerId){
		    	 orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
		    	 orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());  
			     orgSpoc.setSpocStatus(orgSpoc.getSpocStatus());
			     orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
			     orgSpoc.setSpocFirstName(orgSpoc.getSpocUser().getFirstName());
			     orgSpoc.setSpocLastName(orgSpoc.getSpocUser().getLastName());
			     orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
			     orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
			     orgSpoc.setUserState(orgSpoc.getSpocUser().getUserState().toString());
		     }
		     else{
		    	 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
		    	 orgSpoc.setSpocName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());  
			     orgSpoc.setSpocStatus(orgSpoc.getSpocStatus());
			     orgSpoc.setOwnerName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
			     orgSpoc.setSpocFirstName(orgSpoc.getOwnerUser().getFirstName());
			     orgSpoc.setSpocLastName(orgSpoc.getOwnerUser().getLastName());
			     orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
			     orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
			     orgSpoc.setUserState(orgSpoc.getOwnerUser().getUserState().toString());
		     }
		     
		     OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		     List<Long> ownerListOfSpoc =  orgSpocService.getOwnerIdbySpocId(spocUserId,ownerId);
		     List<User> userList = userService.getOwnersByOrgLocationIdAndNotExistedOwnerId(orgLoc.getId(),ownerId,ownerListOfSpoc);
		     
		     orgSpoc.setUserList(userList);
		 return commonUtil.toJSON(orgSpoc);
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
				@RequestParam(value = "vendorLocationId", required = false) Long clientLocationId,
				@RequestParam(value = "orgRelationId", required = false) Long orgRelationId) throws Exception {
		
		    OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
	       String json1 = "{" + json + "}";
	       JSONObject jsonObj = new JSONObject(json1);
	       clientService.updateClientDetails(jsonObj, orgRelationId,clientLocationId, orgLocation.getId());

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
	@ResponseBody boolean isClientNameValidated(
			@RequestParam(value = "clientName", required = false) String clientName,HttpServletRequest request,
			@RequestParam(value = "orgRelationId", required = false) Long orgRelationId,
			@RequestParam(value = "responderType", required = false) String responderType) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		return orgService.isClientNameValid(clientName,orgLocation.getId(),responderType);
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

	@RequestMapping(value = "/getVendorClientEmailVerification", method = RequestMethod.POST)
	@ResponseBody String getVendorClientEmailVerification(@RequestParam(value = "EmailId", required = false) String emailId,@RequestParam(value = "spocId", required = false) Long spocId)
			throws Exception {
		String result = null;
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
		String resultJson;
		//if login user org id is equals to vendor location id then we get vendor list of ResponderLocationId Org details 
		 if(orgRelation.getLocationId().getId()== orgLocation.getId()){ 
				orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrg().getOrgName());
				orgRelation.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
				orgRelation.setCountry(orgRelation.getResponderLocationId().getCountry());
				orgRelation.setCity(orgRelation.getResponderLocationId().getCity());
				orgRelation.setState(orgRelation.getResponderLocationId().getState());
				orgRelation.setPhone(orgRelation.getResponderLocationId().getPhone());
				orgRelation.setEmail(orgRelation.getResponderLocationId().getEmail());
				orgRelation.setLevel(orgRelation.getInitiatorLevel());
				orgRelation.setOrgType(orgRelation.getResponderLocationId().getOrg().getOrgType());
				
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
				orgRelation.setLevel(orgRelation.getResponderLevel());
				orgRelation.setOrgType(orgRelation.getLocationId().getOrg().getOrgType());
		 } 
		 
		 resultJson = commonUtil.toJSON(orgRelation);
		 return resultJson;
	}
	
	/**
	 * @author Laxman
	 * this is method is using client details
	 * @param request
	 * @return json
	 * @throws Exception
	 */
	 
	@RequestMapping(value = "/getClientDetails")
	   @ResponseBody String getClientDetails(HttpServletRequest request) throws Exception {

		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    String resultJson;
		    List<OrgRelations> clientsList = orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId());
			
			for (OrgRelations orgRelation : clientsList) {
				 //to fetch user details by usercode 
				User user = userService.getUserDetailsByUserCode(orgRelation.getCreatedBy());
				if(user != null){
					orgRelation.setVendorCreateduserName(user.getFirstName()+" "+user.getLastName());
				}
				List<OrgSpoc> orgSpocs = orgSpocService.getAllSpocsByOrgRelationId(orgRelation.getId(), loginUser.getId(),orgLocations.getId());
				orgRelation.setNoOfContatcs(orgSpocs.size());
				if(orgRelation.getLocationId().getId() == orgLocations.getId()){
					
					orgRelation.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
					orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrg().getOrgName());
					orgRelation.setCountry(orgRelation.getResponderLocationId().getCountry());
					orgRelation.setState(orgRelation.getResponderLocationId().getState());
					orgRelation.setCity(orgRelation.getResponderLocationId().getCity());
					orgRelation.setVendorlocationId(orgRelation.getResponderLocationId().getId());
					if(orgRelation.getInitiatorLevel()!=null){
						orgRelation.setLevel(orgRelation.getInitiatorLevel());
					}
					else{
						orgRelation.setLevel("");
					}
				}
				
				if(orgRelation.getResponderLocationId().getId() == orgLocations.getId()){
					orgRelation.setWebSite(orgRelation.getLocationId().getOrg().getWebSite());
					orgRelation.setOrgName(orgRelation.getLocationId().getOrg().getOrgName());
					orgRelation.setCountry(orgRelation.getLocationId().getCountry());
					orgRelation.setState(orgRelation.getLocationId().getState());
					orgRelation.setCity(orgRelation.getLocationId().getCity());
					orgRelation.setVendorlocationId(orgRelation.getLocationId().getId());
					if(orgRelation.getInitiatorLevel()!=null){
						orgRelation.setLevel(orgRelation.getInitiatorLevel());
					}
					else{
						orgRelation.setLevel("");
					}
				}
				
			}
		    
			 resultJson = commonUtil.toJSON(clientsList);
			 return resultJson;
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
		
	      String  json1 = "{" + json + "}";
	       JSONObject jsonObj = new JSONObject(json1);
	
	       OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	       clientService.updateVendorDetails(jsonObj, orgRelationId,vendorLocationId, orgLocation.getId());

		return "success";
	}
	/**
	 * @author Prasanna vadla
	 * @param json,spocId
	 * @param vendorLocationId
	 * @param orgRelationId
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/vendorContactDetailsUpdateUsingId", method = RequestMethod.POST)
	 String vendorContactDetailsUpdateUsingId(@RequestParam(value = "json", required = false) String json,
			@RequestParam(value = "spocId", required = false) Long spocId) throws Exception {
		
	       String jsonVal = "{" + json + "}";
	       JSONObject jsonObj = new JSONObject(jsonVal);
	       User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	       OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	       clientService.updateVendorContactDetails(jsonObj, spocId, orgLocation.getId(),loginUser);

		return jsonObj.getString("owner");
	}
	
	
	
	@RequestMapping(value = "/getClientContactLists")
	public @ResponseBody String getClientContactLists(HttpServletRequest request) throws Exception {

	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");		
	    String resultJson;
	    
	    List<OrgSpoc> vandorsContactListByOrgLocationId = orgRelationsService.getAllVendorsContactListByOrgLocationId(loginUser.getId(),orgLocations.getId(),"Vendor");
	
		
		for (OrgSpoc orgSpoc : vandorsContactListByOrgLocationId) {
			
			if(orgSpoc.getSpocUser().getId() == loginUser.getId() || orgSpoc.getOwnerUser().getId() == loginUser.getId()){
				
				//if login user is spoc
				if(orgSpoc.getSpocUser().getId() == loginUser.getId()){
    				 orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
    				 orgSpoc.setSpocName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
    				 orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
    				 orgSpoc.setOwnerName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
    				 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
    				 orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
				}
				//if login user is Owner
				else if(orgSpoc.getOwnerUser().getId() == loginUser.getId()){
						 orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
						 orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
						 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
						 orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
						 orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
						 orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
					}
					
				 
			}else{
				if(orgSpoc.getInitiatorLocationId().getId() == orgLocations.getId() ){
					 orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
					 orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
					 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
					 orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
					 orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
					 orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
					 
				 }
				if(orgSpoc.getResponderLocationId().getId() == orgLocations.getId()){
				 orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
				 orgSpoc.setSpocName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
				 orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
				 orgSpoc.setOwnerName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
				 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
				 orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
				}
			}
			if(orgSpoc.getResponderLocationId().getId() == orgLocations.getId()){
				orgSpoc.setSpocLocationId(orgSpoc.getInitiatorLocationId().getId());
				 orgSpoc.setOrgName(orgSpoc.getInitiatorLocationId().getOrg().getOrgName());
			}
			if(orgSpoc.getInitiatorLocationId().getId() == orgLocations.getId() ){
				 orgSpoc.setSpocLocationId(orgSpoc.getResponderLocationId().getId());
				orgSpoc.setOrgName(orgSpoc.getResponderLocationId().getOrg().getOrgName());
			}
			orgSpoc.setOwnerUserId(orgSpoc.getOwnerUser().getId().toString());
			orgSpoc.setSpocUserId(orgSpoc.getSpocUser().getId());
			orgSpoc.setOrgRelationId(orgSpoc.getOrgRelations().getId());
		}
		
		 resultJson = commonUtil.toJSON(vandorsContactListByOrgLocationId);
		 return resultJson;
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "/saveClientSubmissionTemplate" , method = RequestMethod.POST)
	   public String saveClientSubmissionTemplate(@RequestParam(value="jsonData", required=false) String json,
			   @RequestParam(value="clientLocationId", required=false) Long clientLocationId,
			   @RequestParam(value="jsonStringData", required=false) String jsonStringData) throws Exception {
			
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");		
		    
		    String json1 = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(json1);
			
		   try {
			   SubmissionTemplate submissionTemplate = new SubmissionTemplate();
			   submissionTemplate.setTemplateName(jsonObj.getString("templateName"));
			   submissionTemplate.setDescription(jsonObj.getString("description"));
			   OrgLocations clientOrgLocation = orgLocationService.getOrgLocations(clientLocationId);
			   submissionTemplate.setClientLocationId(clientOrgLocation);
			   submissionTemplate.setOrgLocationId(orgLocations);
			   submissionTemplate.setJsonString(jsonStringData);
			   submissionTemplate.setCreatedBy(user.getUserCode());
			   submissionTemplate.setCreatedDate(new Date());
			   submissionTemplateService.addSubmissionTemplate(submissionTemplate);
				
			} catch (Exception e) {
				logger.error(ExceptionUtils.getFullStackTrace(e)); 
			}
			   return "home";
	    
	   }
	
	
	
	
	
	@RequestMapping(value = "/loadSubmissionTemplate" , method = RequestMethod.GET)
	   public String loadSubmissionTemplate(@RequestParam(value="clientLocationId", required=false) Long clientLocationId) throws Exception {
			
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    SubmissionTemplate submissionTemplate = submissionTemplateService.getSubmissionTemplateById(1L);
		    
			return submissionTemplate.getJsonString();
	    
	   }
	
	
	
	@RequestMapping(value = "/loadSavedSubmissionTemplate" , method = RequestMethod.GET)
	   public String loadSavedSubmissionTemplate(@RequestParam(value="templateId", required=false) Long templateId) throws Exception {
			
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    SubmissionTemplate submissionTemplate = submissionTemplateService.getSubmissionTemplateById(templateId);
		    
			return submissionTemplate.getJsonString();
	    
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