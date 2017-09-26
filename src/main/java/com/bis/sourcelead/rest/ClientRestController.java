package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ClientDTO;
import com.bis.sourcelead.dto.ClientVendorDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.ClientVendorContactsIndex;
import com.bis.sourcelead.elk.index.ClientVendorIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.ClientVendorExport;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementReportService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;


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
	private RequirementSubmissionHistoryService requirementSubmissionHistoryService;

	@Autowired
	HttpServletRequest request;
	ProjectApproverHierarchyService projectApproverHierarchyService;

	@Autowired
	ProjectMembersService projectMembersService;

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	private OrgSpocService orgSpocService;

	@Autowired
	ClientService clientService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	SubmissionTemplateService submissionTemplateService;
	
	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private ClientVendorExport clientVendorExport;
	
	@Autowired
	NotificationsService notificationsService;
	
	@Autowired
	ContractsTemplateService contractsTemplateService;

	@Autowired
	private RequirementService requirementService;
  
	@Autowired
	private SourceleadELKBaseIndexer<ClientVendorIndex> sourceleadELKBaseIndexer;
	
	@Autowired
	RequirementReportService requirementReportService;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
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
					hm.put(orgLocations.getOrgLocationName(), orgLocations.getId());
				}
			}
			return commonUtil.toJSON(hm);
	    }
	 
	 
	 @RequestMapping(value = "/getRolesList", method = RequestMethod.POST)
		public @ResponseBody String getRolesList(
				@RequestParam(value = "orgLocationId", required = false) String orgLocationId,
				@RequestParam(value = "moduleType", required = false) String moduleType,
				HttpServletRequest request) throws Exception {
			
			List<Roles> rolesList  = null;
			if(!isNullOrEmpty(orgLocationId)){
				rolesList = rolesService.getClientOrVendorRolesList(Long.parseLong(orgLocationId), moduleType);
			}else{
				rolesList = rolesService.getClientOrVendorRolesList(1L, moduleType);
			}
			
			return commonUtil.toJSON(rolesList);
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
	    		@RequestParam(value="orgName", required=false) String orgName,
	    		@RequestParam(value="json", required=false) String json) throws Exception {
			
			Org org = orgService.getOrgByOrgName(orgName);
			OrgLocations orgLocations = null;
			if(org != null && orgLocationId != null){
				orgLocations = orgLocationService.getOrgLocations(orgLocationId);
				orgLocations.setWebsite(orgLocations.getOrg().getWebSite());
			}
			return commonUtil.toJSON(orgLocations);
			
		}
	/**
	 * Register client along with contact(spoc)
	 * @param json
	 * @param responderType
	 * @param orgLocationId
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_CREATE_CLIENT','ROLE_CREATE_VENDOR')")
	@RequestMapping(value = "/saveClient", method = RequestMethod.POST)
	@ResponseBody
	public String saveClient(ClientDTO clientDTO,AddUserDTO addUserDTO,CandidateDTO candidateDTO,
			@RequestParam(value = "responderType", required = false) String responderType,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId, HttpServletRequest request)
			throws Exception {

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		if (null != orgLocationId) {
			clientDTO.setOrgLocationId(orgLocationId.toString());
		}

		clientService.storeClient(clientDTO,addUserDTO,candidateDTO, user, orgLocation, responderType, org);
		if (responderType.equalsIgnoreCase("Vendor")) {
			SourceLeadSessionManager.setSessionAttribute(request, "vendorCreateMsg", "Success!! New Vendor created");
		} else if (responderType.equalsIgnoreCase("Client")) {
			SourceLeadSessionManager.setSessionAttribute(request, "clientCreateMsg", "Success!! New client created");
		}
		return "clientDashboard";
	}
		
		
		 @RequestMapping(value = "/validateClientOrVendorEmail",  method = RequestMethod.POST)
		    public @ResponseBody String validateClientOrVendorEmail(@RequestParam(value="email", required=false) String email) throws Exception {
			 
			 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 
			 UserRole userRole = userRoleService.getUserDetailsByEmailAndLocationId(email, orgLocation.getId());
			 if(userRole == null){
				 return "Valid Email";
			 }else{
				 return "Invalid Email";
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
						if(userRole.getRoles() !=null){
							userList.add(userRole.getRoles().getId().toString());
							userList.add(userRole.getRoles().getDisplayName());
						}
						
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
		if(user != null){
			StringBuilder name = new StringBuilder();
			orgRelation.setVendorCreateduserName(name.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
		}
		return commonUtil.toJSON(orgRelation);
	}
	
	
	/**@author Prasad Salina
	 * To get all the spocs list
	 * @param orgLocationId
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_VIEW_VENDOR_CONTACT','ROLE_VIEW_EDIT_VENDOR_CONTACT','ROLE_CREATE_REQUIREMENT')")
	@RequestMapping(value = "/getOrgSpocsList", method = RequestMethod.POST)
	@ResponseBody String getOrgSpocsList(@RequestParam(value = "orgRelationId", required = false) Long orgRelationId,
			                             @RequestParam(value = "type", required = false) String type) throws Exception {
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<OrgSpoc> orgSpocsList  = orgSpocService.getAllSpocsByOrgRelationId(orgRelationId, loginUser.getId(),orgLocations.getId(),type);
		return commonUtil.toJSON(orgSpocsList);
	}
	
	
	
	@RequestMapping(value = "/getSubmissionTemplatesList", method = RequestMethod.POST)
	@ResponseBody String getSubmissionTemplatesList(@RequestParam(value = "clientLocationId", required = false) Long clientLocationId) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<SubmissionTemplate> submissiontTemplateList  = submissionTemplateService.getSubmissionTemplateByClientLocationIdAndOrgLocationIdAndTemplateType(clientLocationId, orgLocations.getId(),"Submission");
		
		return commonUtil.toJSON(submissiontTemplateList);
	}
	
	
	
	@RequestMapping(value = "/validateTemplateName", method = RequestMethod.POST)
	@ResponseBody String validateTemplateName(@RequestParam(value = "clientLocationId", required = false) Long clientLocationId,
			@RequestParam(value = "templateName", required = false) String templateName,
			@RequestParam(value = "templateId", required = false) String templateId) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		
		String result = null;
		SubmissionTemplate submissiontTemplate = null;
		
		if(!isNullOrEmpty(templateId)){
			submissiontTemplate  = submissionTemplateService.getSubmissionTemplateById(Long.parseLong(templateId));
			if(submissiontTemplate.getTemplateName().equalsIgnoreCase(templateName)){
				result = "valid";
			}else{
				submissiontTemplate  = submissionTemplateService.getSubmissionTemplateByTemplateName(clientLocationId, orgLocations.getId(), templateName);
				if(null != submissiontTemplate){
					result = "invalid";
				}else{
					result = "valid";
				}
			}
		}
		
		else{
			submissiontTemplate  = submissionTemplateService.getSubmissionTemplateByTemplateName(clientLocationId, orgLocations.getId(), templateName);
			if(null != submissiontTemplate){
				result = "invalid";
			}else{
				result = "valid";
			}
		}
		
		return result;
		
	}
	

	@RequestMapping(value = "/validateTemplateNameByOrg", method = RequestMethod.POST)
	@ResponseBody String validateTemplateNameByOrg(@RequestParam(value = "templateName", required = false) String templateName,
			@RequestParam(value = "templateId", required = false) String templateId) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		
		SubmissionTemplate submissiontTemplate = null;
		String result = null;
		
		if(!isNullOrEmpty(templateId)){
			submissiontTemplate  = submissionTemplateService.getSubmissionTemplateById(Long.parseLong(templateId));
			if(submissiontTemplate.getTemplateName().equalsIgnoreCase(templateName)){
				result = "valid";
			}else{
				submissiontTemplate  = submissionTemplateService.getSubmissionTemplateByTemplateNameByOrg(orgLocations.getId(), templateName);
				if(null != submissiontTemplate){
					result = "invalid";
				}else{
					result = "valid";
				}
			}
		}
		
		else{
			submissiontTemplate  = submissionTemplateService.getSubmissionTemplateByTemplateNameByOrg(orgLocations.getId(), templateName);
			if(null != submissiontTemplate){
				result = "invalid";
			}else{
				result = "valid";
			}
		}
		
		return result;
		
	}
	
	
	
	
	

	/** To Terminate Spoc soft deletion only)
	 * @author Prasad Salina
	 * @param spocId
	 * @param reason
	 * @return
	 * @throws Exception
	 */

	@PreAuthorize("hasAnyRole('ROLE_DELETE_CLIENT_CONTACT','ROLE_DELETE_VENDOR_CONTACT')")
	 @RequestMapping(value = "/terminateSpoc", method = RequestMethod.POST)
	 public @ResponseBody String terminateSpoc(@RequestParam(value = "spocId", required = false) Long spocId,
			 @RequestParam(value="reason", required=false) String reason,
			 @RequestParam(value="userId", required=false) String userId,
			 @RequestParam(value="orgLocationId", required=false) String orgLocationId,
			 @RequestParam(value="responderType", required=false) String responderType) throws Exception{
		
		OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(spocId);
				if(orgSpoc != null){
					orgSpoc.setStatus(Constants.MEMBER_IN_ACTIVE);
					orgSpoc.setTerminateReason(reason);
					orgSpocService.addOrgSpoc(orgSpoc);
					logger.debug("Spoc was terminated");
					List<OrgSpoc> orgSpocsList  = orgSpocService.getAllSpocsByOrgRelationId(orgSpoc.getOrgRelations().getId(), Long.parseLong(userId), Long.parseLong(orgLocationId), responderType);
					ClientVendorIndex clientVendorIndex = new ClientVendorIndex();
					clientVendorIndex.setNoContacts((long)orgSpocsList.size());
					sourceleadELKBaseIndexer.saveOrUpdateElkDocument(clientVendorIndex, Constants.ELK_CLIENTVENDOR_INDEX, Constants.ELK_CLIENTVENDOR_TYPE,orgSpoc.getOrgRelations().getId().toString());
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
			OrgSpoc orgSpoc = orgSpocService.validateSpacEmailIdByOwnerUserIdAndOrgRelationId(email, orgRelationId, user.getId(), Constants.RECORD_ACTIVE); 
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
													@RequestParam(value="invitationStaus", required=false) String invitationStaus,HttpServletRequest request) throws Exception{
		 User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");        
	        OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(spocId);
	        if(orgSpoc !=  null){
	        	
	        	Notifications notifications = notificationsService.getNotificationBySpocUUID(orgSpoc.getUUID());
	        	
	        	if(invitationStaus != null && "Accepted".equals(invitationStaus)){
	        		orgSpoc.setInvitationStatus(Constants.MAIL_ACCEPTED);
	        		clientService.sendAcceptEmailToClientOrVendor(orgSpoc,loginUser,orgLocations);
	        		
	        		if(null != notifications){
	        			 notifications.setUserNotificationStatus("Accepted");
						 notificationsService.saveNotification(notifications);
	        		}
	        		
	        		SourceLeadSessionManager.setSessionAttribute(request, "acceptContact","Success !! Accepted the invitation.");
	        	}else if(invitationStaus != null && "Rejected".equals(invitationStaus)){
	        		orgSpoc.setInvitationStatus(Constants.MAIL_REJECTED);
	        		orgSpoc.setSpocStatus(Constants.INACTIVE);
	        		clientService.sendRejectEmailToClientOrVendor(orgSpoc,loginUser,orgLocations);
	        		if(null != notifications){
	        			notifications.setUserNotificationStatus("Rejected");
						notificationsService.saveNotification(notifications);
	        		}
	        		SourceLeadSessionManager.setSessionAttribute(request, "acceptContact","Rejected the invitation.");
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
	 @PreAuthorize("hasAnyRole('ROLE_VIEW_CLIENT_CONTACT','ROLE_VIEW_EDIT_CLIENT_CONTACT','ROLE_VIEW_VENDOR_CONTACT','ROLE_VIEW_EDIT_VENDOR_CONTACT')")
	 @RequestMapping(value = "/getSpocAndOwnerDetailsById", method = RequestMethod.POST)
	 @ResponseBody String getSpocAndOwnerDetailsById(@RequestParam(value = "spocId", required = false) Long spocId,@RequestParam(value = "ownerId", required = false) Long ownerId,@RequestParam(value = "spocUserId", required = false) Long spocUserId,@RequestParam(value = "tempOwnerId", required = false) Long tempOwnerId 
			                                        ,@RequestParam(value = "type", required = false) String type) throws Exception{
		     User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	         OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 
	         OrgSpoc orgSpoc =  orgSpocService.getSpocAndOwnerDetailsByIdAndOwnerId(spocId,ownerId,user.getId(),spocUserId,orgLoc.getId(),type);
		     List<Long> ownerListOfSpoc =  orgSpocService.getOwnerIdbySpocId(spocUserId,ownerId);
		     List<User>  userList = null;
		     //List<User> userList = userService.getOwnersByOrgLocationIdAndNotExistedOwnerId(orgLoc.getId(),ownerId,ownerListOfSpoc);
		     if(null != type && !"".equalsIgnoreCase(type) && "Client".equalsIgnoreCase(type)){
		     	 userList = userService.getTabAccessOwnersByOrgLocationIdAndTabIdAndNotOwners(orgLoc.getId(),Long.valueOf(Constants.TAB_CLIENT),ownerListOfSpoc);
		     }
		     if(null != type && !"".equalsIgnoreCase(type) && "Vendor".equalsIgnoreCase(type)){
		    	 userList = userService.getTabAccessOwnersByOrgLocationIdAndTabIdAndNotOwners(orgLoc.getId(),Long.valueOf(Constants.TAB_VENDOR),ownerListOfSpoc);
		     }
		     	if(null != userList && !userList.isEmpty()){
		    	  orgSpoc.setUserList(userList);
		     }
		     String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "editSpocMsg");
		     if(msg != null && msg != ""){
		    	 orgSpoc.setMsg(msg);
		    	 SourceLeadSessionManager.removeSessionAttribute(request, "editSpocMsg");
		     }
		     
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
	public String clientDetailsUpdateUsingId(ClientDTO clientDTO,
				@RequestParam(value = "vendorLocationId", required = false) Long clientLocationId,
				@RequestParam(value = "orgRelationId", required = false) Long orgRelationId) throws Exception {
		
		    OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    
	       clientService.updateClientDetails(clientDTO, orgRelationId,clientLocationId, orgLocation.getId(),user);
	       
	       StringBuilder editClientMsg = new StringBuilder();
		   SourceLeadSessionManager.setSessionAttribute(request, "editClient", editClientMsg.append("Success !! ").append(clientDTO.getOrgName()).append(" details updated.").toString());

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
	@ResponseBody String isClientNameValidated(
			@RequestParam(value = "clientName", required = false) String clientName,HttpServletRequest request,
			@RequestParam(value = "orgRelationId", required = false) Long orgRelationId,
			@RequestParam(value = "responderType", required = false) String responderType) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<Org> org =orgService.getOrgs();
	    String flag = Constants.CLINT_VENDOR_DOES_NOT_EXIST;
			List<OrgRelations> relationsList = orgRelationsService.getClientOrVendorListByOrgLocationId(orgLocation.getId(), responderType);
			List<OrgLocations> orgLocationsList=orgLocationService.userList(orgLocation.getOrg().getId());
			for(OrgRelations orgRelations : relationsList){
				for(OrgLocations orgRelations123 : orgLocationsList){
				if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
					if(StringUtils.containsIgnoreCase(clientName.trim(), orgRelations.getResponderLocationId().getOrgLocationName())||orgRelations123.getOrgLocationName().equalsIgnoreCase(clientName)) {
//					if(clientName.trim().equalsIgnoreCase(orgRelations.getResponderLocationId().getOrgLocationName())){
						flag = Constants.CLINT_VENDOR_IF_EXIST;
						if(orgRelations123.getOrgLocationName().equalsIgnoreCase(clientName)){
							flag = Constants.CLINT_VENDOR_IF_EXIST_AS_SAMEORG;
						}
					}
				}	
				else if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
					if(StringUtils.containsIgnoreCase(clientName.trim(), orgRelations.getLocationId().getOrgLocationName())||orgRelations123.getOrgLocationName().equalsIgnoreCase(clientName)){
//					if(clientName.trim().equalsIgnoreCase(orgRelations.getLocationId().getOrgLocationName())){
						flag = Constants.CLINT_VENDOR_IF_EXIST;
						if(orgRelations123.getOrgLocationName().equalsIgnoreCase(clientName)){
							flag = Constants.CLINT_VENDOR_IF_EXIST_AS_SAMEORG;
						}
					}
					
				}
				
			}
			}
		return flag;
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
	@PreAuthorize("hasAnyRole('ROLE_VIEW_EDIT_CLIENT','ROLE_VIEW_CLIENT','ROLE_VIEW_VENDOR','ROLE_VIEW_EDIT_VENDOR')")
	@RequestMapping(value = "/getClientAndVendorOrgDeatilsById", method = RequestMethod.POST)
	@ResponseBody String getClientAndVendorOrgDeatilsById(@RequestParam(value = "vendorLocationId", required = false) Long orgLocationId,
			@RequestParam(value = "orgRelationId", required = false) Long orgRelationId) throws Exception {
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsByResponderLocationdIdAndOrgRelationId(orgLocationId,orgRelationId,orgLocation.getId());
		String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "editClient");
		if(msg != null && msg != ""){
			orgRelation.setMsg(msg);
			SourceLeadSessionManager.removeSessionAttribute(request, "editClient");
		}else{
			msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "editVendor");
			orgRelation.setMsg(msg);
			SourceLeadSessionManager.removeSessionAttribute(request, "editVendor");
		}
		String resultJson;
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
	   @ResponseBody String getClientDetails(@RequestParam(value="values", required=false) String status,HttpServletRequest request) throws Exception {

		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    String resultJson;
		    
		    List<OrgRelations> clientsList = orgRelationsService.getAllClientsFilterByOrgLocationId(orgLocations.getId(),status,loginUser.getId());
			resultJson = commonUtil.toJSON(clientsList);
			return resultJson;
		}
	 
	   
	   
	@RequestMapping(value = "/getClientReleasedRequirementList", method = RequestMethod.POST)
	@ResponseBody
	String getClientReleasedRequirementList(@RequestParam(value = "orgRelationId", required = false) String orgRelationId, @RequestParam(value = "status", required = false) String status, HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		String resultJson;
		List<Requirement> reqList = requirementService.getAllClientJobsBasedonRelationId(orgLocations.getId(), Long.parseLong(orgRelationId), userRoleName, loginUser, status);
		//SimpleDateFormat formatter = orgLocations.getLocFullFormDateFormatter();
		for (Requirement req : reqList) {
			req.setPostedDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),req.getCreatedDate()));
			int interviewsSum = 0;
			int offeredSum = 0;
			int submitToClient = 0;
			if (req.getManagerUserId() != null) {
				String managerFirstName = "";
				if (StringUtils.isNotEmpty(req.getManagerUserId().getFirstName())) {
					managerFirstName = req.getManagerUserId().getFirstName();
				}
				String managerLastName = "";
				if (StringUtils.isNotEmpty(req.getManagerUserId().getLastName())) {
					managerLastName = req.getManagerUserId().getLastName();
				}
				StringBuilder name = new StringBuilder();
				req.setManager(name.append(managerFirstName).append(" ").append(managerLastName).toString());
				
				List<RequirementSubmission> subList = requirementSubmissionService.getSubmissionsByRequirementId(req.getId(), loginUser, orgLocations,userRoleName);
				for(RequirementSubmission reqsub : subList){
					RequirementSubmission sourceSub = requirementSubmissionService.getClientRequierementSubmission(reqsub.getId());
					int vendorInterviewsCount = 0;
					int vendorOfferedCount = 0;
					int vendorSubmittoClientCount = 0;
					if(sourceSub != null && sourceSub.getRequirement() != null ){
						List<RequirementSubmissionHistory> requirementSubmissionHistoryList = requirementReportService.getApplicantsCountsByDateRange(sourceSub.getId(),null,null);
						for(RequirementSubmissionHistory subhistory : requirementSubmissionHistoryList){
							// iterate here for interview and offer stages and get the counts
							
							if (ApplicantSubmissionStatus.INTERVIEW.toString().equalsIgnoreCase(subhistory.getToStage())) {
								vendorInterviewsCount++;
							}
							else if (ApplicantSubmissionStatus.OFFERMADE.toString().equalsIgnoreCase(subhistory.getToStage())) {
								vendorOfferedCount++;
							}else if(ApplicantSubmissionStatus.SUBMISSION.toString().equalsIgnoreCase(subhistory.getToStage())){
								vendorSubmittoClientCount++;
							}
							
						}
						interviewsSum = interviewsSum + vendorInterviewsCount;
						offeredSum = offeredSum + vendorOfferedCount;
						submitToClient = submitToClient + vendorSubmittoClientCount;
						
					}else{
						// this is for isclientjob check box Y count 
						RequirementSubmission submission = requirementSubmissionService.getRequirementSubmissionById(reqsub.getId());
						List<RequirementSubmissionHistory> requirementSubmissionHistoryList = requirementReportService.getApplicantsCountsByDateRange(submission.getId(),null,null);
						for(RequirementSubmissionHistory subhistory : requirementSubmissionHistoryList){
							// iterate here for client interview and client offer and get the counts
							
							if (ApplicantSubmissionStatus.CLIENT_INTERVIEW.toString().equalsIgnoreCase(subhistory.getToStage())) {
								vendorInterviewsCount++;
							}
							else if (ApplicantSubmissionStatus.CLIENT_OFFERMADE.toString().equalsIgnoreCase(subhistory.getToStage())) {
								vendorOfferedCount++;
							}else if(ApplicantSubmissionStatus.SUBMIT_TO_CLIENT.toString().equalsIgnoreCase(subhistory.getToStage())){
								vendorSubmittoClientCount++;
							}
							
						}
						interviewsSum = interviewsSum + vendorInterviewsCount;
						offeredSum = offeredSum + vendorOfferedCount;
						submitToClient = submitToClient + vendorSubmittoClientCount;
					}
				}
				
				req.setNoOfInterviewedApplicants(String.valueOf(interviewsSum));
				req.setNoOfOfferedApplicants(String.valueOf(offeredSum));
				req.setNoOfSubmittedApplicants(String.valueOf(submitToClient));
			   /*RequirementSubmission rs=requirementSubmissionService.getRequirementSubmissionByRequirementIdAndUserId(req.getId(), loginUser.getId());
				requirementSubmissionHistoryService.getCandidatesCountBySubmissionIdandRequirementId(rs.getId(), rs.getSubmissionStatus().toString());*/
			}
		}
		resultJson = commonUtil.toJSON(reqList);
		return resultJson;
	}
	   
	/**
	 * @author Prasanna vadla
	 * @param json
	 * @param vendorLocationId
	 * @param orgRelationId
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/vendorDetailsUpdateUsingId", method = RequestMethod.POST)
	public String vendorDetailsUpdateUsingId(ClientDTO clientDTO,
			@RequestParam(value = "vendorLocationId", required = false) Long vendorLocationId,
			@RequestParam(value = "orgRelationId", required = false) Long orgRelationId) throws Exception {

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		clientService.updateVendorDetails(clientDTO, orgRelationId, vendorLocationId, orgLocation.getId(), user);

		StringBuilder editVendorMsg = new StringBuilder();
		SourceLeadSessionManager.setSessionAttribute(request, "editVendor", editVendorMsg.append("Success !! ")
				.append(clientDTO.getVendorName()).append(" details updated.").toString());

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
	 String vendorContactDetailsUpdateUsingId(ClientDTO clientDTO,
			@RequestParam(value = "spocId", required = false) Long spocId,
			@RequestParam(value = "ownerId", required = false) Long ownerId,@RequestParam(value = "spocUserId", required = false) Long spocUserId,
			@RequestParam(value = "type", required = false) String type) throws Exception {
		
	       User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	       OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	       OrgSpoc orgSpoc = clientService.updateVendorContactDetails(clientDTO, spocId, orgLocation.getId(),loginUser,ownerId,spocUserId,orgLocation,type);
	       
	       StringBuilder editSpocMsg = new StringBuilder();
		   //SourceLeadSessionManager.setSessionAttribute(request, "editSpocMsg", editSpocMsg.append("Success !! ").append(orgSpoc.getSpocUser().getFirstName()).append(" details updated.").toString());

		return orgSpoc.getOwnerUser().getId().toString();
	}
	
	
	
	@RequestMapping(value = "/getClientContactLists")
	public @ResponseBody String getClientContactLists(@RequestParam(value="status", required=false) String status,@RequestParam(value="clientIds", required=false) String clientIds,@RequestParam(value="invitationStatus", required=false) String invitationStatus,HttpServletRequest request) throws Exception {

	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");		
	    String resultJson , msg="" ;
	    
	    List<OrgSpoc> vandorsContactListByOrgLocationId = orgRelationsService.getAllVendorsContactFilterListByOrgLocationId(loginUser.getId(),orgLocations.getId(), "Vendor",status,clientIds,invitationStatus);
	    for(OrgSpoc orgSpoc :vandorsContactListByOrgLocationId){
			 msg = (String) SourceLeadSessionManager.getSessionAttribute(request,"acceptContact");
			 if(msg != null){
				 orgSpoc.setMsg(msg);
				 SourceLeadSessionManager.removeSessionAttribute(request, "acceptContact");
			 }
		 }
	    resultJson = commonUtil.toJSON(vandorsContactListByOrgLocationId);
		return resultJson;
	}
	
	
	
	@RequestMapping(value = "/getAllOwners")
	public @ResponseBody String getAllOwners(@RequestParam(value="email", required=false) String email,@RequestParam(value="orgRelationId", required=false) Long orgRelationId,
			                                 @RequestParam(value="type", required=false) String type,HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		String resultJson = null;
		Long tabType = null;
		if(null != type && "vendor".equalsIgnoreCase(type)){
			tabType = Long.valueOf(Constants.TAB_VENDOR);
		}else if(null != type && "client".equalsIgnoreCase(type)){
			tabType = Long.valueOf(Constants.TAB_CLIENT);
		}
		
		List<Long> existingOwnerUserIds = orgSpocService.getOwnerUserIdsbySpocEmailAndOrgRelationId(email,orgRelationId,type);
		List<User> userList = userService.getUserListExceptExistedUsersList(existingOwnerUserIds,orgLocations.getId(),tabType);
		resultJson = commonUtil.toJSON(userList);
		return resultJson;

	}	
	
	
	@PreAuthorize("hasAnyRole('ROLE_CREATE_SUBMISSION_TEMPLATE','ROLE_EDIT_SUBMISSION_TEMPLATE')")
	@RequestMapping(value = "/saveClientSubmissionTemplate" , method = RequestMethod.POST)
	   public String saveClientSubmissionTemplate(@RequestParam(value="jsonData", required=false) String json,
			   @RequestParam(value="clientLocationId", required=false) Long clientLocationId,
			   @RequestParam(value="templateId", required=false) String templateId,
			   @RequestParam(value="jsonStringData", required=false) String jsonStringData) throws Exception {
			
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");		
		    
		    String json1 = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(json1);
			
			
		   try {
			   SubmissionTemplate submissionTemplate = null;
			   if(!isNullOrEmpty(templateId)){
				   submissionTemplate = submissionTemplateService.getSubmissionTemplateById(Long.parseLong(templateId));
				}else{
					submissionTemplate = new SubmissionTemplate();
				}
			   
			   submissionTemplate.setTemplateName(jsonObj.getString("templateName").trim());
			   submissionTemplate.setDescription(jsonObj.getString("description"));
			   OrgLocations clientOrgLocation = orgLocationService.getOrgLocations(clientLocationId);
			   submissionTemplate.setClientLocationId(clientOrgLocation);
			   submissionTemplate.setOrgLocationId(orgLocations);
			   submissionTemplate.setJsonString(jsonStringData);
			   submissionTemplate.setStatus(Constants.CANDIDATE_ACTIVE);
			   submissionTemplate.setTemplateType("Submission");
			   submissionTemplate.setCreatedBy(user.getUserCode());
			   submissionTemplate.setCreatedDate(new Date());
			   submissionTemplate.setUpdatedBy(user.getUserCode());
			   submissionTemplate.setUpdatedDate(new Date());
			   submissionTemplateService.addSubmissionTemplate(submissionTemplate);
				
			} catch (Exception e) {
				logger.error(ExceptionUtils.getFullStackTrace(e)); 
			}
			   return "home";
	    
	   }
	
	
	
	
	
	@RequestMapping(value = "/loadSubmissionTemplate" , method = RequestMethod.GET)
	   public String loadSubmissionTemplate() throws Exception {
			
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
	
	@RequestMapping(value = "/validateSubmissionTemplateLabelName" , method = RequestMethod.GET)
	   public String validateSubmissionTemplateLabelName(@RequestParam(value="templateId", required=false) Long templateId,
			   @RequestParam(value="labelName", required=false) String labelName) throws Exception {
			
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    SubmissionTemplate submissionTemplate = submissionTemplateService.getSubmissionTemplateByLabelName(templateId, labelName);
		   
		    if(submissionTemplate == null){
		    	return "Success";
		    }else{
		    	return "Existed";
		    }
	    
	   }
	
	
	
	@RequestMapping(value = "/validateFeedbackTemplateLabelName" , method = RequestMethod.GET)
	   public String validateFeedbackTemplateLabelName(@RequestParam(value="templateId", required=false) Long templateId,
			   @RequestParam(value="labelName", required=false) String labelName) throws Exception {
			
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    SubmissionTemplate submissionTemplate = null;
		    if(templateId != null){
		    	submissionTemplate = submissionTemplateService.getSubmissionTemplateByLabelName(templateId, labelName);	
		    }
		    if(submissionTemplate == null){
		    	return "Success";
		    }else{
		    	return "Existed";
		    }
	    
	   }
	
	/**
	 * @author Rajashekar
	 * Export client data to excel and pdf
	 * @param status
	 * @param exportType
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	
	@PreAuthorize("hasRole('ROLE_EXPORT_CLIENTS')")
	 @RequestMapping(value = "/exportClientDetails")
	   @ResponseBody void exportClientDetails(@RequestParam(value="values", required=false) String status,@RequestParam(value="exportType", required=false) String exportType,HttpServletRequest request,HttpServletResponse response) throws Exception {

		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		    List<OrgRelations> clientsList = orgRelationsService.getAllClientsFilterByOrgLocationId(orgLocations.getId(),status,loginUser.getId());
		    File file = clientVendorExport.getClientExportFile(clientsList,exportType,orgLocations.getOrgLocationName());
		    String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			StringBuilder fileName = new StringBuilder();
			response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"" ).append(file.getName()).append("\"").toString());
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			inputStream.close();
			response.getOutputStream().close();
			response.getOutputStream().flush();	
		    
		}
	 
	 
	 /**
	  * @author Rajashekar
	  * export client contacts data to excel and pdf
	  * @param status
	  * @param clientIds
	  * @param invitationStatus
	  * @param request
	  * @return
	  * @throws Exception
	  */
	
	 @PreAuthorize("hasRole('ROLE_EXPORT_CLIENT_CONTACTS')")
	 @RequestMapping(value = "/exportClientContactDetails")
		public void exportClientContactDetails(@RequestParam(value="status", required=false) String status,@RequestParam(value="clientIds", required=false) String clientIds,@RequestParam(value="invitationStatus", required=false) String invitationStatus,@RequestParam(value="exportType", required=false) String exportType,HttpServletRequest request,HttpServletResponse response) throws Exception {

		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");		
		    Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		    List<OrgSpoc> vandorsContactList = orgRelationsService.getAllVendorsContactFilterListByOrgLocationId(loginUser.getId(),orgLocations.getId(), "Vendor",status,clientIds,invitationStatus);
		    File file = clientVendorExport.getClientContactsExportFile(vandorsContactList,exportType,orgLocations.getOrgLocationName());
		    String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			StringBuilder fileName = new StringBuilder();
			response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"" ).append(file.getName()).append( "\"").toString());
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			inputStream.close();
			response.getOutputStream().close();
			response.getOutputStream().flush();	
			if(file != null){
				file.delete();
			}
	 }
	
	 @RequestMapping(value = "/getUniversalClientList")
	 @ResponseBody
	 public String getUniversalClientList(@RequestParam(value="searchValue", required=false) String  searchValue,
			 @RequestParam(value="searchType", required=false) String  searchType,HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String resultJson;
		List<Object> uniClientList = clientService.getAllUniversalSearchClientList(orgLocations, searchValue,searchType);
		resultJson = commonUtil.toJSON(uniClientList);
		return resultJson;
	 }
	/**
	 * to get owners list in addcontact 
	 * @param email
	 * @param request
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = " /getOwnersList")
	 @ResponseBody
	 public String  getOwnersList(@RequestParam(value="email", required=false) String  email,@RequestParam(value="type", required=false) String  type,HttpServletRequest request) throws Exception {
		String resultJson;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<User> userList = null;
		if(null != email && !"".equalsIgnoreCase(email)){
			User user = userService.findByEmail(email);
			if(null != user){
				List<Long> ownersList= orgSpocService.getOwnerListByUserIdAndOrglocationId(user,orgLocations,type);
				userList = userService.getTabAccessOwnersByOrgLocationIdAndTabIdAndNotOwners(orgLocations.getId(),Long.valueOf(Constants.TAB_CLIENT),ownersList);
			}
			else{
				userList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocations.getId(),Long.valueOf(Constants.TAB_CLIENT));
			}
		}else{
			userList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocations.getId(),Long.valueOf(Constants.TAB_CLIENT));
		}
		resultJson = commonUtil.toJSON(userList);
		return resultJson;
	 }
	/**
	 * check given string empty or null
	 * @param str
	 * @return boolean
	 */
	protected static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}

	
	/**
	 * @author Pavan Jampana
	 * to get the client or vendor contracts List
	 */
	@RequestMapping(value = " /getClientOrVendorContracts")
	 @ResponseBody
	 public String  getClientOrVendorContracts(@RequestParam(value="orgRelationId", required=false)Long orgRelationId,@RequestParam(value="responderType", required=false)String responderType,@RequestParam(value="contractStatus", required=false)String contractStatus,HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		String  userRoleName = (String)SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		List<Contracts> clientcontractsList = contractsTemplateService.getAllClientOrVendorContractsBasedOnLocationId(orgLocations,responderType,user,userRoleName,contractStatus,orgRelationId);
		return commonUtil.toJSON(clientcontractsList);
	 }
	
	@RequestMapping(value = "/searchClientDetails")
	public ResponseList<ClientVendorIndex> searchClientDetails(ClientVendorDTO clientVendorDTO, HttpServletRequest request)
			throws Exception {
		/*
		 * This array is used to enable the sorting feature in data tables. And, these names should match the ELK Candidate index field names.
		 */
		String[] tableColumns1 = null;
		if(Constants.CLIENT.equalsIgnoreCase(clientVendorDTO.getResponderType())){
			String[] tableColumns = {"responderOrgName", "", "location", "responderLevel", "createdBy", "noContacts"};
			tableColumns1 = tableColumns;
		}else if(Constants.VENDOR.equalsIgnoreCase(clientVendorDTO.getResponderType())){
			String[] tableColumns = {"responderOrgName", "", "", "","createdBy","releasedJobsCount", "noContacts"};
			tableColumns1 = tableColumns;
		}
		
	     int sortColumnIndex = 4; //by default sorting using updated date
		boolean sortColumnOrder = false; //by default descending order
		if(clientVendorDTO.getOrder() != null && !clientVendorDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(clientVendorDTO.getOrder().get(0).get("column"));
			sortColumnOrder = !clientVendorDTO.getOrder().get(0).get("dir").equalsIgnoreCase("desc");
		}
		
		
		 User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");		  
		 
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		 
		 BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		 
		 String responderType= "";
		 if(Constants.VENDOR.equalsIgnoreCase(clientVendorDTO.getResponderType())){
			 responderType = Constants.CLIENT;
		 }else if(Constants.CLIENT.equalsIgnoreCase(clientVendorDTO.getResponderType())){
			 responderType = Constants.VENDOR; 
		 }
		 
		 BoolQueryBuilder combinedQueryBuilder = QueryBuilders.boolQuery();
		 
		 BoolQueryBuilder initiatorQueryBuilder = QueryBuilders.boolQuery();
		 initiatorQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", loginOrgLocation.getId()));
		 initiatorQueryBuilder.must(QueryBuilders.matchQuery("initiatorType", responderType));
		 
		 BoolQueryBuilder responderQueryBuilder = QueryBuilders.boolQuery();
		 responderQueryBuilder.must(QueryBuilders.matchQuery("responderLocationId", loginOrgLocation.getId()));
		 responderQueryBuilder.must(QueryBuilders.matchQuery("responderType", responderType));
		 
		 combinedQueryBuilder.should(initiatorQueryBuilder);
		 combinedQueryBuilder.should(responderQueryBuilder);
		 
		 boolQueryBuilder.must(combinedQueryBuilder);
		 
		 //------
		 BoolQueryBuilder boolQueryBuilderForCount = new BoolQueryBuilder();
		 
		 
        BoolQueryBuilder combinedQueryBuilderCount = QueryBuilders.boolQuery();
		 
		 BoolQueryBuilder initiatorQueryBuilderCount = QueryBuilders.boolQuery();
		 initiatorQueryBuilderCount.must(QueryBuilders.matchQuery("orgLocationId", loginOrgLocation.getId()));
		 initiatorQueryBuilderCount.must(QueryBuilders.matchQuery("initiatorType", responderType));
		 
		 BoolQueryBuilder responderQueryBuilderCount = QueryBuilders.boolQuery();
		 responderQueryBuilderCount.must(QueryBuilders.matchQuery("responderLocationId", loginOrgLocation.getId()));
		 responderQueryBuilderCount.must(QueryBuilders.matchQuery("responderType", responderType));
		 
		 combinedQueryBuilderCount.should(initiatorQueryBuilderCount);
		 combinedQueryBuilderCount.should(responderQueryBuilderCount);
		 
		 boolQueryBuilderForCount.must(combinedQueryBuilder);
		 //---end
		 
		 // filter based on client level
		 List<String> clientLevelArray = clientVendorDTO.getValues(); // 
			if(clientLevelArray != null && !clientLevelArray.isEmpty()){
				BoolQueryBuilder clientLevelBoolQueryBuilder = QueryBuilders.boolQuery();
				for(String keywd : clientLevelArray){
					clientLevelBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("initiatorLevel", keywd));
//					clientLevelBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("responderLevel", keywd));
				}
				boolQueryBuilder.must(clientLevelBoolQueryBuilder);
			}
		 
		
		 
		 // Rajashekar
		//Exclude Keyword
		 List<String> excludeKeyWordArray = clientVendorDTO.getExcludeKeyWord();// getSearchKeywordsFromChosenSelectField(jsonObj, "excludeKeyWord");
		 if(excludeKeyWordArray != null && !excludeKeyWordArray.isEmpty()){
		 	 BoolQueryBuilder excludeBoolQueryBuilder = QueryBuilders.boolQuery();
			 for(String keywd : excludeKeyWordArray){
				 BoolQueryBuilder excludeBoolSubQueryBuilder = QueryBuilders.boolQuery();
				ArrayList<MatchQueryBuilder> matchQueryBuilderList =getMatchQueryBuilderList(keywd);
				if(matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()){
					for(MatchQueryBuilder matchQueryBuilder :matchQueryBuilderList){
						excludeBoolSubQueryBuilder.should(matchQueryBuilder);
					}
					excludeBoolQueryBuilder.should(excludeBoolSubQueryBuilder);
				}
			}
			 boolQueryBuilder.mustNot(excludeBoolQueryBuilder);
		  }
			
			//Any Keyword
		 List<String> anyKeyWordArray = clientVendorDTO.getAnyOfKeyword();
			if(anyKeyWordArray == null)
				anyKeyWordArray = new ArrayList<>();
			if(StringUtils.isNotEmpty(clientVendorDTO.getSearchValue())) {
				anyKeyWordArray.add(clientVendorDTO.getSearchValue());
			}
			 // getSearchKeywordsFromChosenSelectField(jsonObj, "anyOfKeyword");
			if(anyKeyWordArray != null && !anyKeyWordArray.isEmpty()){
				 BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
				 for(String keywd : anyKeyWordArray){
					 BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
					ArrayList<MatchQueryBuilder> matchQueryBuilderList =getMatchQueryBuilderList(keywd);
					if(matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()){
						for(MatchQueryBuilder matchQueryBuilder :matchQueryBuilderList){
							anyBoolSubQueryBuilder.should(matchQueryBuilder);
						}
						anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
					}
				}
				 boolQueryBuilder.must(anyBoolQueryBuilder);
			}
			
			//All Keywords
			
			
			List<String> allKeyWordArray = clientVendorDTO.getAllKeywords();//getSearchKeywordsFromChosenSelectField(jsonObj, "allKeywords");
			if(allKeyWordArray != null && !allKeyWordArray.isEmpty()){
				 BoolQueryBuilder allBoolQueryBuilder = QueryBuilders.boolQuery();
				 for(String keywd : allKeyWordArray){
					 BoolQueryBuilder allBoolSubQueryBuilder = QueryBuilders.boolQuery();
					ArrayList<MatchQueryBuilder> matchQueryBuilderList =getMatchQueryBuilderList(keywd);
					if(matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()){
						for(MatchQueryBuilder matchQueryBuilder :matchQueryBuilderList){
							allBoolSubQueryBuilder.should(matchQueryBuilder);
						}
						allBoolQueryBuilder.must(allBoolSubQueryBuilder);
					}
				}
				 boolQueryBuilder.must(allBoolQueryBuilder);
			}
			
			//vendorName
			List<String> vendorNameArray = clientVendorDTO.getOrgNameAdvanced(); // getSearchKeywordsFromChosenSelectField(jsonObj, "orgNameAdvanced");
			if(vendorNameArray != null && !vendorNameArray.isEmpty()){
				BoolQueryBuilder vendorNameBoolQueryBuilder = QueryBuilders.boolQuery();
				for(String keywd : vendorNameArray){
					vendorNameBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("initiatorOrgName", keywd));
					vendorNameBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("responderOrgName", keywd));
				}
				boolQueryBuilder.must(vendorNameBoolQueryBuilder);
			}
			
			 //Country
			if(clientVendorDTO.getCountry() !=null){
				boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("responderCountry", clientVendorDTO.getCountry()));
				boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("initiatorCountry", clientVendorDTO.getCountry()));
		    }
			
			
			 //State
			if(clientVendorDTO.getState() !=null){
				boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("responderState", clientVendorDTO.getState()));
		    }
			if(clientVendorDTO.getState() !=null){
				boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("initiatorState", clientVendorDTO.getState()));
		    }
			
			//City
			if(clientVendorDTO.getCity() !=null){
				boolQueryBuilder.should(QueryBuilders.matchQuery("initiatorCity", clientVendorDTO.getCity()));
		    }
			if(clientVendorDTO.getCity() !=null){
				boolQueryBuilder.should(QueryBuilders.matchQuery("responderCity", clientVendorDTO.getCity()));
		    }
			
				
			//CreatedBy
			List<String> createdByArray = clientVendorDTO.getCreatedByAdvanced(); // getSearchKeywordsFromChosenSelectField(jsonObj, "createdByAdvanced");
			if(createdByArray != null && !createdByArray.isEmpty()){
				BoolQueryBuilder createdByBoolQueryBuilder = QueryBuilders.boolQuery();
				for(String keywd : createdByArray){
					createdByBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("createdBy", keywd));
				}
				boolQueryBuilder.must(createdByBoolQueryBuilder);
			}
			
			//tierAdvanced
			List<String> tierAdvancedByArray = clientVendorDTO.getTierAdvanced();// getSearchKeywordsFromChosenSelectField(jsonObj, "tierAdvanced");
			if(tierAdvancedByArray != null && !tierAdvancedByArray.isEmpty()){
				BoolQueryBuilder tierAdvancedBoolQueryBuilder = QueryBuilders.boolQuery();
				for(String keywd : tierAdvancedByArray){
					tierAdvancedBoolQueryBuilder.should(QueryBuilders.matchPhrasePrefixQuery("initiatorLevel", keywd));
					//tierAdvancedBoolQueryBuilder.should(QueryBuilders.matchPhrasePrefixQuery("responderLevel", keywd));
				}
				boolQueryBuilder.must(tierAdvancedBoolQueryBuilder);
			}
			
		 // Rajashekar end
			 // to   get the number of Vendor/Clients 
		/*Long maxResults = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CLIENTVENDOR_INDEX, Constants.ELK_CLIENTVENDOR_TYPE,
				clientVendorDTO.getStart(), clientVendorDTO.getLength(), tableColumns[sortColumnIndex], sortColumnOrder ? SortOrder.ASC : SortOrder.DESC,boolQueryBuilder).getTotalHits();*/
			SearchResponse searchResponse = sourceleadELKBaseIndexer
					.searchDocumentWithMaxResults(Constants.ELK_CLIENTVENDOR_INDEX, Constants.ELK_CLIENTVENDOR_TYPE,
							clientVendorDTO.getStart(), clientVendorDTO.getLength(), tableColumns1[sortColumnIndex], sortColumnOrder ? SortOrder.ASC : SortOrder.DESC, boolQueryBuilder);
			
			SearchHit searchHits[] = searchResponse.getHits().getHits();
		 
		List<ClientVendorIndex> clientVendorList = new ArrayList<ClientVendorIndex>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		ResponseList<ClientVendorIndex> response = new ResponseList<>();
//		SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CLIENTVENDOR_INDEX, Constants.ELK_CLIENTVENDOR_TYPE,0,maxResults.intValue(),boolQueryBuilder).getHits();
		if(searchHits != null && searchHits.length >0){
			ObjectMapper mapper = new ObjectMapper();
			for (SearchHit hit : searchHits) {
			String	json = hit.getSourceAsString();
				mapper.setDateFormat(df);
				ClientVendorIndex clientVendor = mapper.readValue(json, ClientVendorIndex.class);
				
				//  This is to get the number of Contacts based  on Org Relation Id
				 BoolQueryBuilder forNumberOfContacts= QueryBuilders.boolQuery();
				 forNumberOfContacts.must(QueryBuilders.matchQuery("orgRelationId", clientVendor.getOrgRelationId()));
				 if(Constants.VENDOR.equalsIgnoreCase(clientVendorDTO.getResponderType())){
					 Integer releaseJobsCount = clientService.getReleasedJobsCountByOrgRelationId(clientVendor.getOrgRelationId(),loginOrgLocation,userRole,loginUser);
					 clientVendor.setReleasedJobsCount(String.valueOf(releaseJobsCount));
				 }
				 if(clientVendor.getNoContacts() !=null) {
					 clientVendor.setNoContacts(clientVendor.getNoContacts());
				 }else {
					 clientVendor.setNoContacts(0L);
				 }
				
				if(null != clientVendor.getOrgLocationId() && clientVendor.getOrgLocationId().longValue() == loginOrgLocation.getId().longValue()){
					
					clientVendor.setWebsite(clientVendor.getResponderWebSite());
					clientVendor.setOrgName(clientVendor.getResponderOrgName());
					clientVendor.setCountry(clientVendor.getResponderCountry());
					clientVendor.setState(clientVendor.getResponderState());
					clientVendor.setCity(clientVendor.getResponderCity());
					if(clientVendor.getInitiatorLevel()!=null){
						clientVendor.setInitiatorLevel(clientVendor.getInitiatorLevel());
					}
					clientVendor.setResponderLocationId(clientVendor.getResponderLocationId());
				}
			  if(null != clientVendor.getResponderLocationId()  && clientVendor.getResponderLocationId().longValue() == loginOrgLocation.getId().longValue()){
				 
				  clientVendor.setWebsite(clientVendor.getInitiatorWebSite());
				  clientVendor.setOrgName(clientVendor.getInitiatorOrgName());
				  clientVendor.setCountry(clientVendor.getInitiatorCountry());
				  clientVendor.setState(clientVendor.getInitiatorState());
				  clientVendor.setCity(clientVendor.getInitiatorCity());
				  clientVendor.setResponderLocationId(clientVendor.getOrgLocationId());
				  if(clientVendor.getResponderLevel()!=null){
					  clientVendor.setInitiatorLevel(clientVendor.getResponderLevel());
				  }
				}
			  if(!responderType.isEmpty() &&  Constants.VENDOR.equalsIgnoreCase(responderType)){
				  if(!"".equals(clientVendor.getCreatedBy())){
					  clientVendor.setCreatedBy(clientVendor.getCreatedBy());
				  }
			  }
		     clientVendorList.add(clientVendor);
				
			}
			response.setRecordsFiltered(searchHits.length);
			SearchResponse searchResponseForCount = sourceleadELKBaseIndexer
					.searchDocumentWithMaxResults(Constants.ELK_CLIENTVENDOR_INDEX, Constants.ELK_CLIENTVENDOR_TYPE,
							0, 10, "createdBy", SortOrder.DESC, boolQueryBuilderForCount);
			
			response.setRecordsTotal(searchResponseForCount == null ? response.getRecordsFiltered() : Long.valueOf(searchResponseForCount.getHits().getTotalHits()).intValue());
		}
		response.setData(clientVendorList);
		 return response;

	}
	
	

	private ArrayList<MatchQueryBuilder> getMatchQueryBuilderList(String keywd){
		    ArrayList<MatchQueryBuilder> matchQueryBuilderList = new ArrayList<MatchQueryBuilder>();
		   
		    //Vendor Search 
		    matchQueryBuilderList.add(QueryBuilders.matchQuery("initiatorOrgName", keywd));
		    matchQueryBuilderList.add(QueryBuilders.matchQuery("responderOrgName", keywd));
		    matchQueryBuilderList.add(QueryBuilders.matchPhrasePrefixQuery("initiatorLevel", keywd));
		    matchQueryBuilderList.add(QueryBuilders.matchPhrasePrefixQuery("responderLevel", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("createdBy", keywd));
			
			matchQueryBuilderList.add(QueryBuilders.matchQuery("responderCountry", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("responderState", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("responderCity", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("initiatorCountry", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("initiatorState", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("initiatorCity", keywd));
			
			matchQueryBuilderList.add(QueryBuilders.matchPhrasePrefixQuery("responderWebSite", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchPhrasePrefixQuery("initiatorWebSite", keywd));
			
			return matchQueryBuilderList; 
	 }
	 
	 /**
	  * client contacts
	  * @param clientVendorDTO
	  * @param request
	  * @return ResponseList<ClientVendorContactsIndex>
	  * @throws Exception
	  */
	 @RequestMapping(value = "/searchClientContactDetails")
		public ResponseList<ClientVendorContactsIndex> searchClientContactDetails(ClientVendorDTO clientVendorDTO, HttpServletRequest request)
				throws Exception {
			/*
			 * This array is used to enable the sorting feature in data tables. And, these names should match the ELK Candidate index field names.
			 */
			String[] tableColumns = {"clientLocationId", "clientUserFirstName", "clientUserEmail", "", "ownerUserId", "spocStatus","invitationStatus",""};
		     int sortColumnIndex = 0; //by default sorting using updated date
			boolean sortColumnOrder = false; //by default descending order
			if(clientVendorDTO.getOrder() != null && !clientVendorDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(clientVendorDTO.getOrder().get(0).get("column"));
				sortColumnOrder = !clientVendorDTO.getOrder().get(0).get("dir").equalsIgnoreCase("desc");
			}
			
			
			 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			 User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");		

		     Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(loginUser.getId(),loginOrgLocation.getId());
				
			 BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
			 
			// boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", loginOrgLocation.getId()));
			
			 BoolQueryBuilder responderQueryBuilder = QueryBuilders.boolQuery();
			 
			 if("Vendor".equalsIgnoreCase(clientVendorDTO.getResponderType())){
				 responderQueryBuilder.must(QueryBuilders.matchQuery("clientLocationId", loginOrgLocation.getId()));
			 }else if("Client".equalsIgnoreCase(clientVendorDTO.getResponderType())){
				 responderQueryBuilder.must(QueryBuilders.matchQuery("vendorLocationId", loginOrgLocation.getId()));
			 }
			 
				BoolQueryBuilder boolQueryBuilderForCount = new BoolQueryBuilder();
				 if("Vendor".equalsIgnoreCase(clientVendorDTO.getResponderType())){
					 boolQueryBuilderForCount.must(QueryBuilders.matchQuery("clientLocationId", loginOrgLocation.getId()));
				 }else if("Client".equalsIgnoreCase(clientVendorDTO.getResponderType())){
					 boolQueryBuilderForCount.must(QueryBuilders.matchQuery("vendorLocationId", loginOrgLocation.getId()));
				 }
				 
				
			 
			 BoolQueryBuilder statusQueryBuilder = QueryBuilders.boolQuery();
			 statusQueryBuilder.must(QueryBuilders.matchQuery("status", "1"));
			 
			 boolQueryBuilder.must(responderQueryBuilder);
			 boolQueryBuilder.must(statusQueryBuilder);
			 
			 //  this condition is if Login user is Owner or SpocUserId
			 if(!isadmin){
								 
						 BoolQueryBuilder ownerLoginQueryBuilder = QueryBuilders.boolQuery();
						 BoolQueryBuilder spockQueryBuilder = QueryBuilders.boolQuery();
						 BoolQueryBuilder combinationQueryBuilder = QueryBuilders.boolQuery();
						 
						 ownerLoginQueryBuilder.must(QueryBuilders.matchQuery("clientUserId", loginUser.getId()));
						 spockQueryBuilder.must(QueryBuilders.matchQuery("vendorUserId", loginUser.getId()));
						 
						 combinationQueryBuilder.should(ownerLoginQueryBuilder);
						 combinationQueryBuilder.should(spockQueryBuilder);
						 boolQueryBuilder.must(combinationQueryBuilder);
			}
			 
// Advance search start
				//Spoc Status
				 List<String> statusAdvancedArray = clientVendorDTO.getStatus();
				if(statusAdvancedArray != null && !statusAdvancedArray.isEmpty()){
					BoolQueryBuilder statusAdvancedBoolQueryBuilder = QueryBuilders.boolQuery();
					for(String keywd : statusAdvancedArray){
							if(Constants.ACTIVE.equalsIgnoreCase(keywd)){
								statusAdvancedBoolQueryBuilder.should(QueryBuilders.matchPhrasePrefixQuery("spocStatus", "Active"));
								statusAdvancedBoolQueryBuilder.mustNot(QueryBuilders.matchPhrasePrefixQuery("spocStatus", "In-Active"));
							}else{
								  statusAdvancedBoolQueryBuilder.should(QueryBuilders.matchPhrasePrefixQuery("spocStatus", keywd));
							}
					}
					boolQueryBuilder.must(statusAdvancedBoolQueryBuilder);
				}
				
				
				
				List<String> invitationAdvancedArray = clientVendorDTO.getInvitationStatus();
				if(invitationAdvancedArray != null && !invitationAdvancedArray.isEmpty()){
					BoolQueryBuilder invitationAdvancedBoolQueryBuilder = QueryBuilders.boolQuery();
					for(String keywd : invitationAdvancedArray){
						invitationAdvancedBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("invitationStatus", keywd));
					}
					boolQueryBuilder.must(invitationAdvancedBoolQueryBuilder);
				}
					
					//Any Keyword
				List<String> anyKeyWordArray = clientVendorDTO.getAnyOfKeyword();
				if(anyKeyWordArray == null)
					anyKeyWordArray = new ArrayList<>();
				if(StringUtils.isNotEmpty(clientVendorDTO.getSearchValue())) {
					anyKeyWordArray.add(clientVendorDTO.getSearchValue());
				}
					if(anyKeyWordArray != null && !anyKeyWordArray.isEmpty()){
						 BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
						 for(String keywd : anyKeyWordArray){
							 BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
							ArrayList<MatchQueryBuilder> matchQueryBuilderList = getMatchQueryBuilderListForContacts(keywd,clientVendorDTO.getResponderType());
							if(matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()){
								for(MatchQueryBuilder matchQueryBuilder :matchQueryBuilderList){
									anyBoolSubQueryBuilder.should(matchQueryBuilder);
								}
								anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
							}
						}
						 boolQueryBuilder.must(anyBoolQueryBuilder);
					}
				
				
				SearchResponse searchResponse = sourceleadELKBaseIndexer
						.searchDocumentWithMaxResults(Constants.ELK_CLIENTVENDOR_CONTACT_INDEX, Constants.ELK_CLIENTVENDOR_CONTACT_TYPE,
								clientVendorDTO.getStart(), clientVendorDTO.getLength(), tableColumns[sortColumnIndex], sortColumnOrder ? SortOrder.ASC : SortOrder.DESC, boolQueryBuilder);
				

				SearchHit searchHits[] = searchResponse.getHits().getHits();
				
				List<ClientVendorContactsIndex> clientVendorContactsList = new ArrayList<ClientVendorContactsIndex>();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				df.setTimeZone(TimeZone.getTimeZone("GMT"));
				ResponseList<ClientVendorContactsIndex> response = new ResponseList<>();
				if(searchHits != null && searchHits.length >0){
					ObjectMapper mapper = new ObjectMapper();
					for (SearchHit hit : searchHits) {
						String json = hit.getSourceAsString();
						mapper.setDateFormat(df);
						ClientVendorContactsIndex clientVendorContactsIndex = mapper.readValue(json, ClientVendorContactsIndex.class);
						clientVendorContactsList.add(clientVendorContactsIndex);
					}
					
					for(ClientVendorContactsIndex clientVendorContactsIndex : clientVendorContactsList){
						if("Vendor".equalsIgnoreCase(clientVendorDTO.getResponderType())){
							if(null != clientVendorContactsIndex.getVendorUserFirstName() && clientVendorContactsIndex.getVendorUserFirstName() != "" && clientVendorContactsIndex.getVendorUserLastName() != null && clientVendorContactsIndex.getVendorUserLastName() != "" ){
								StringBuilder spocNameSB = new StringBuilder();
								clientVendorContactsIndex.setSpocName(spocNameSB.append(clientVendorContactsIndex.getVendorUserFirstName()).append(" ").append(clientVendorContactsIndex.getVendorUserLastName()).toString());
							}else{
								clientVendorContactsIndex.setSpocName(clientVendorContactsIndex.getVendorUserFirstName());
							}
							if(clientVendorContactsIndex.getClientUserFirstName() != null && clientVendorContactsIndex.getClientUserFirstName() != "" && clientVendorContactsIndex.getClientUserLastName()!= null && clientVendorContactsIndex.getClientUserLastName() != "" ){
								StringBuilder ownerNameSB = new StringBuilder();
								clientVendorContactsIndex.setOwnerNames(ownerNameSB.append(clientVendorContactsIndex.getClientUserFirstName()).append(" ").append(clientVendorContactsIndex.getClientUserLastName()).toString());
							}else{
								clientVendorContactsIndex.setOwnerNames(clientVendorContactsIndex.getClientUserFirstName());
							}
							clientVendorContactsIndex.setSpocEmail(clientVendorContactsIndex.getVendorUserEmail());
							clientVendorContactsIndex.setSpocMobilePhone(clientVendorContactsIndex.getVendorUserPhNo());
							
						    clientVendorContactsIndex.setTempOwnerId(clientVendorContactsIndex.getClientUserId());
	    				    clientVendorContactsIndex.setUserId(clientVendorContactsIndex.getVendorUserId());
		    				 
							clientVendorContactsIndex.setSpocLocationId(clientVendorContactsIndex.getVendorLocationId());
							clientVendorContactsIndex.setOrgName(clientVendorContactsIndex.getVendorOrgName());
							
							if(null != clientVendorContactsIndex.getOwnerUserId() && null != clientVendorContactsIndex.getClientUserId() && clientVendorContactsIndex.getOwnerUserId().longValue() == clientVendorContactsIndex.getClientUserId().longValue()){
								 clientVendorContactsIndex.setSpocUserId(clientVendorContactsIndex.getVendorUserId());
								 clientVendorContactsIndex.setOwnerUserId(clientVendorContactsIndex.getClientUserId());
							}
							else{
								 clientVendorContactsIndex.setSpocUserId(clientVendorContactsIndex.getClientUserId());
								 clientVendorContactsIndex.setOwnerUserId(clientVendorContactsIndex.getVendorUserId());
							}
							
						 }else if("Client".equalsIgnoreCase(clientVendorDTO.getResponderType())){
							 if(null != clientVendorContactsIndex.getClientUserFirstName() && clientVendorContactsIndex.getClientUserFirstName() != "" && clientVendorContactsIndex.getClientUserLastName() != null && clientVendorContactsIndex.getClientUserLastName() != "" ){
									StringBuilder spocNameSB = new StringBuilder();
									clientVendorContactsIndex.setSpocName(spocNameSB.append(clientVendorContactsIndex.getClientUserFirstName()).append(" ").append(clientVendorContactsIndex.getClientUserLastName()).toString());
								}else{
									clientVendorContactsIndex.setSpocName(clientVendorContactsIndex.getClientUserFirstName());
								}
								if(clientVendorContactsIndex.getVendorUserFirstName() != null && clientVendorContactsIndex.getVendorUserFirstName() != "" && clientVendorContactsIndex.getVendorUserLastName()!= null && clientVendorContactsIndex.getVendorUserLastName() != "" ){
									StringBuilder ownerNameSB = new StringBuilder();
									clientVendorContactsIndex.setOwnerNames(ownerNameSB.append(clientVendorContactsIndex.getVendorUserFirstName()).append(" ").append(clientVendorContactsIndex.getVendorUserLastName()).toString());
								}else{
									clientVendorContactsIndex.setOwnerNames(clientVendorContactsIndex.getVendorUserFirstName());
								}
								clientVendorContactsIndex.setSpocEmail(clientVendorContactsIndex.getClientUserEmail());
								clientVendorContactsIndex.setSpocMobilePhone(clientVendorContactsIndex.getClientUserPhNo());
								
							    clientVendorContactsIndex.setTempOwnerId(clientVendorContactsIndex.getVendorUserId());
		    				    clientVendorContactsIndex.setUserId(clientVendorContactsIndex.getClientUserId());
			    				 
								clientVendorContactsIndex.setSpocLocationId(clientVendorContactsIndex.getClientLocationId());
								clientVendorContactsIndex.setOrgName(clientVendorContactsIndex.getClientOrgName());
								
								if(null != clientVendorContactsIndex.getClientUserId() && null != clientVendorContactsIndex.getOwnerUserId() && clientVendorContactsIndex.getOwnerUserId().longValue() == clientVendorContactsIndex.getClientUserId().longValue()){
									 clientVendorContactsIndex.setSpocUserId(clientVendorContactsIndex.getVendorUserId());
									 clientVendorContactsIndex.setOwnerUserId(clientVendorContactsIndex.getClientUserId());
								}
								else{
									 clientVendorContactsIndex.setSpocUserId(clientVendorContactsIndex.getClientUserId());
									 clientVendorContactsIndex.setOwnerUserId(clientVendorContactsIndex.getVendorUserId());
								}
						 }
					}	
					
				}
				response.setRecordsFiltered(searchHits.length);
				SearchResponse searchResponseForCount = sourceleadELKBaseIndexer
						.searchDocumentWithMaxResults(Constants.ELK_CLIENTVENDOR_CONTACT_INDEX, Constants.ELK_CLIENTVENDOR_CONTACT_TYPE,
								0, 10, "invitationStatus", SortOrder.DESC, boolQueryBuilderForCount);
				
			response.setRecordsTotal(searchResponseForCount == null ? response.getRecordsFiltered() : Long.valueOf(searchResponseForCount.getHits().getTotalHits()).intValue());
			response.setData(clientVendorContactsList);
			 return response;
		}
	 
	 
	 private ArrayList<MatchQueryBuilder> getMatchQueryBuilderListForContacts(String keywd,String responderType){
		    ArrayList<MatchQueryBuilder> matchQueryBuilderListForContacts = new ArrayList<MatchQueryBuilder>();
		   
		    //Contact  Search 
		    matchQueryBuilderListForContacts.add(QueryBuilders.matchPhraseQuery("spocStatus", keywd));
		    
		    if("Vendor".equalsIgnoreCase(responderType)){
		    	 // Client OrgName
			    matchQueryBuilderListForContacts.add(QueryBuilders.matchQuery("vendorOrgName", keywd));
		    }
		    if("Client".equalsIgnoreCase(responderType)){
		    	 // Vendor OrgName
			    matchQueryBuilderListForContacts.add(QueryBuilders.matchQuery("clientOrgName", keywd));
		    }
		   
		    
		    //owner Name
		    matchQueryBuilderListForContacts.add(QueryBuilders.matchQuery("clientUserFirstName", keywd));
		    matchQueryBuilderListForContacts.add(QueryBuilders.matchQuery("clientUserLastName", keywd));
		    
		    //spock Name
		    matchQueryBuilderListForContacts.add(QueryBuilders.matchQuery("vendorUserFirstName", keywd));
		    matchQueryBuilderListForContacts.add(QueryBuilders.matchQuery("vendorUserLastName", keywd));
		    
		    
		    if("Client".equalsIgnoreCase(responderType)){
		    // ClientUser Email
		    matchQueryBuilderListForContacts.add(QueryBuilders.matchPhraseQuery("clientUserEmail", keywd));
		    }
		    if("Vendor".equalsIgnoreCase(responderType)){
		    // VendorUser Email
		    matchQueryBuilderListForContacts.add(QueryBuilders.matchPhraseQuery("vendorUserEmail", keywd));
		    }
		    if("Client".equalsIgnoreCase(responderType)){
		   // ClientUser PhoneNumber
		    matchQueryBuilderListForContacts.add(QueryBuilders.matchPhraseQuery("clientUserPhNo", keywd));
		    }
		    if("Vendor".equalsIgnoreCase(responderType)){
		    // VendorUser PhoneNumber
		    matchQueryBuilderListForContacts.add(QueryBuilders.matchPhraseQuery("vendorUserPhNo", keywd));
		    }
			 
		    
		    if(keywd.equalsIgnoreCase("Resend") || keywd.equalsIgnoreCase("resend") ){
		    	matchQueryBuilderListForContacts.add(QueryBuilders.matchQuery("invitationStatus", "Sent"));
		    }else{
		    	matchQueryBuilderListForContacts.add(QueryBuilders.matchQuery("invitationStatus", keywd));
		    }
			return matchQueryBuilderListForContacts; 
	 }
	 

}