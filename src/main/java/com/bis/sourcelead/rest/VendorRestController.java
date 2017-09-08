package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ClientDTO;
import com.bis.sourcelead.elk.index.ClientVendorContactsIndex;
import com.bis.sourcelead.elk.index.ClientVendorIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.ClientVendorExport;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementReportService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 
 * @author Rajashekar
 * Handers all Vendor actions
 *
 */

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class VendorRestController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
	RequirementService requirementService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	UserService userService;
	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	private ClientVendorExport clientVendorExport;
	
	@Autowired
	private UserRoleDao userRoleDao;

	@Autowired
	RequirementReportService requirementReportService;

	@Autowired
	private SourceleadELKBaseIndexer<ClientVendorIndex> sourceleadELKBaseIndexer;
	
	@Autowired
	ClientService clientService;
	
	
	/**
	 * Handles Vendor Contacts 
	 * @param json
	 * @param request
	 * @return Json
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_CREATE_CLIENT_CONTACT','ROLE_CREATE_VENDOR_CONTACT')")
	@RequestMapping(value = "/saveClinentAndVendorContact", method = RequestMethod.POST)
	@ResponseBody
	public String saveVenodrContact(ClientDTO clientDTO,AddUserDTO addUserDTO,CandidateDTO candidateDTO, @RequestParam(value="orgRelationId", required=false) String orgRelationId,
			                        @RequestParam(value="clientLocationId", required=false) String clientLocationId ,
			                        @RequestParam(value="responderType", required=false) String responderType,
			                        HttpServletRequest request) throws Exception {

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		
		if(clientDTO.getOwner() != null && !clientDTO.getOwner().isEmpty()){
			for(String owners :clientDTO.getOwner()){
				User ownerUser = userService.getUserById(Long.parseLong(owners));
				OrgSpoc orgSpoc = orgSpocService.validateSpacEmailIdByOwnerUserIdAndOrgRelationId(clientDTO.getEmail(), Long.parseLong(orgRelationId), ownerUser.getId(),1);	
		     if(orgSpoc == null){
			    	orgSpocService.addVendorContacts(clientDTO,addUserDTO,candidateDTO, user, orgLocations, orgRelationId, clientLocationId,responderType,org);
			    	if(responderType.equalsIgnoreCase("Vendor")){
			    		SourceLeadSessionManager.setSessionAttribute(request, "vendorContactCreateMsg","Success !! New contact added.");
				 	}else if(responderType.equalsIgnoreCase("Client")){
				 		SourceLeadSessionManager.setSessionAttribute(request, "clientContactCreateMsg","Success !! New contact added.");
				 	}
					
			    }
			} 
		}
		
			return "vendorsList";
	}

	
	
	
	
	/**
	 * @author Laxman
	 * this is method is using Vendor List
	 * @param request
	 * @return json
	 * @throws Exception
	 */
	 
	@RequestMapping(value = "/getReleasedJobsCountByOrgRelationId", method = RequestMethod.POST)
	public @ResponseBody Long getReleasedJobsCountByOrgRelationId(@RequestParam(value="orgRelationId", required=false) Long orgRelationId,HttpServletRequest request) throws Exception {

	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	    String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
	    
	    OrgRelations orgRelation =orgRelationsService.getOrRelationsById(orgRelationId);
	    Long releasedJobsSize =0l;
	    
	    List<Requirement> vendorReleasedJobsList = null;
	    if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
	    	if(orgRelation !=null) {
	    		vendorReleasedJobsList = requirementService.getAllReleasedJobsForVendor(orgRelation.getLocationId().getId(),orgRelation.getId(),user,orgLocations);
	    	}
	    }else{
	    	List<Requirement> reqlist = requirementService.getAllReleasedJobsBasedOnUserIdAndLocationId(user,orgLocations);
		    List<Long> parentreqIds = new ArrayList<>();
		    for(Requirement req : reqlist){
		    	parentreqIds.add(req.getId());
		    }
		    if(orgRelation !=null) {
		    vendorReleasedJobsList = requirementService.getAllReleasedJobsBasedOnParetRequirementIdAndVendorLocationId(parentreqIds,orgRelation.getId());
		    }
		    }
		
		if(null != vendorReleasedJobsList ){
			
			releasedJobsSize = (long) vendorReleasedJobsList.size();
		}
			 return releasedJobsSize;
		}
	
	
	/**
	 * @author Laxman
	 * this is method is using Vendor List
	 * @param request
	 * @return json
	 * @throws Exception
	 */
	 
	@RequestMapping(value = "/numberOfContactsByOrgRelationId", method = RequestMethod.POST)
	public @ResponseBody Long numberOfContactsByOrgRelationId(@RequestParam(value="orgRelationId", required=false) Long orgRelationId,HttpServletRequest request) throws Exception {

	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

	    OrgRelations orgRelation =orgRelationsService.getOrRelationsById(orgRelationId);
	    Long numberOfContacts =0l;
		List<OrgSpoc> contactsList = orgSpocService.getAllContactsbyOrgId(orgRelation.getId());
		if(null != contactsList ){
			numberOfContacts = (long) contactsList.size();
		}
			 return numberOfContacts;
		}
	
	
	
	
	/**
	 * @author Laxman
	 * this is method is using Vendor List
	 * @param request
	 * @return json
	 * @throws Exception
	 */
	 
	@RequestMapping(value = "/getVendorFilterLists", method = RequestMethod.POST)
	public @ResponseBody String getVendorFilterLists(@RequestParam(value="values", required=false) String status,HttpServletRequest request) throws Exception {

	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    String resultJson;
	    
	    List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllVendorsFilterByOrgLocationId(orgLocations.getId(),status,"");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    //List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocations.getId());
 
		    for (OrgRelations orgRelation : vandorsListByOrgLocationId) {
				
				if(orgRelation.getLocationId()!= null && orgRelation.getLocationId().getId()!=null &&orgRelation.getLocationId().getId().longValue() == orgLocations.getId().longValue()){
					if(orgRelation.getResponderLocationId() !=null){
						orgRelation.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
						orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrgLocationName());
						orgRelation.setCountry(orgRelation.getResponderLocationId().getCountry());
						orgRelation.setState(orgRelation.getResponderLocationId().getState());
						orgRelation.setCity(orgRelation.getResponderLocationId().getCity());
						orgRelation.setVendorlocationId(orgRelation.getResponderLocationId().getId());
						List<Requirement> vendorReleasedJobsList = requirementService.getAllReleasedJobsForVendor(orgRelation.getResponderLocationId().getId(),orgRelation.getId(),user,orgLocations);
						orgRelation.setNoOfReleasedJobs(vendorReleasedJobsList.size());
					}
					//List<OrgSpoc>  contactsList = orgSpocService.getAllContactsbyOrgLocationIdAndUserIdForRequirement(orgLocations.getId(),user.getId(),"Client");
					
					List<OrgSpoc> contactsList = orgSpocService.getAllContactsbyOrgId(orgRelation.getId());
					
					
					orgRelation.setNoOfContatcs(contactsList.size()); 
					if(orgRelation.getInitiatorLevel()!=null){
						orgRelation.setLevel(orgRelation.getInitiatorLevel());
					}
					else{
						orgRelation.setLevel("");
					}
					
				}
				
				if(orgRelation.getResponderLocationId()!= null && orgRelation.getResponderLocationId().getId()!=null &&orgRelation.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
					if(orgRelation.getLocationId() !=null){
						orgRelation.setWebSite(orgRelation.getLocationId().getOrg().getWebSite());
						orgRelation.setOrgName(orgRelation.getLocationId().getOrgLocationName());
						orgRelation.setCountry(orgRelation.getLocationId().getCountry());
						orgRelation.setState(orgRelation.getLocationId().getState());
						orgRelation.setCity(orgRelation.getLocationId().getCity());
						orgRelation.setVendorlocationId(orgRelation.getLocationId().getId());
						List<OrgSpoc>  contactsList = orgSpocService.getAllContactsbyOrgLocationIdAndUserIdForRequirement(orgLocations.getId(),user.getId(),"Vendor");
						orgRelation.setNoOfContatcs(contactsList.size()); 
					}
					List<Requirement> vendorReleasedJobsList = requirementService.getAllReleasedJobsForVendor(orgRelation.getLocationId().getId(),orgRelation.getId(),user,orgLocations);
					orgRelation.setNoOfReleasedJobs(vendorReleasedJobsList.size());
					if(orgRelation.getResponderLevel()!=null){
						orgRelation.setLevel(orgRelation.getResponderLevel());
					}
					else{
						orgRelation.setLevel("");
					}
					
				}
				
				User createdUser = userService.getUserDetailsByUserCode(orgRelation.getCreatedBy());
				 StringBuilder vendorCreatedName = new StringBuilder();
				if(createdUser != null){
					orgRelation.setCreatedBy(vendorCreatedName.append(createdUser.getFirstName()).append(" ").append(createdUser.getLastName()).toString());
				}
			}
		    
		    
			 resultJson = commonUtil.toJSON(vandorsListByOrgLocationId);
			 return resultJson;
		}
	
	
	
	
	
	/**
	 * @author Laxman
	 * this is method is using Vendor List
	 * @param request
	 * @return json
	 * @throws Exception
	 */
	 
	@RequestMapping(value = "/getVendorLists")
	public @ResponseBody String getVendorLists(@RequestParam(value="values", required=false) String status,HttpServletRequest request) throws Exception {

	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    String resultJson;
	    
	    List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllVendorsFilterByOrgLocationId(orgLocations.getId(),status,"");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    //List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocations.getId());
 
			
		    for (OrgRelations orgRelation : vandorsListByOrgLocationId) {
				
				if(orgRelation.getLocationId()!= null && orgRelation.getLocationId().getId()!=null &&orgRelation.getLocationId().getId().longValue() == orgLocations.getId().longValue()){
					if(orgRelation.getResponderLocationId()!=null){
						orgRelation.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
						orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrgLocationName());
						orgRelation.setCountry(orgRelation.getResponderLocationId().getCountry());
						orgRelation.setState(orgRelation.getResponderLocationId().getState());
						orgRelation.setCity(orgRelation.getResponderLocationId().getCity());
						orgRelation.setVendorlocationId(orgRelation.getResponderLocationId().getId());
					}
					List<OrgSpoc>  contactsList = orgSpocService.getAllContactsbyOrgLocationIdAndUserIdForRequirement(orgLocations.getId(),user.getId(),"Client");
					orgRelation.setNoOfContatcs(contactsList.size()); 
					if(orgRelation.getInitiatorLevel()!=null){
						orgRelation.setLevel(orgRelation.getInitiatorLevel());
					}
					else{
						orgRelation.setLevel("");
					}
					
				}
				
				if(orgRelation.getResponderLocationId()!= null && orgRelation.getResponderLocationId().getId()!=null &&orgRelation.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
					if(orgRelation.getResponderLocationId()!=null){
						orgRelation.setWebSite(orgRelation.getLocationId().getOrg().getWebSite());
						orgRelation.setOrgName(orgRelation.getLocationId().getOrgLocationName());
						orgRelation.setCountry(orgRelation.getLocationId().getCountry());
						orgRelation.setState(orgRelation.getLocationId().getState());
						orgRelation.setCity(orgRelation.getLocationId().getCity());
						orgRelation.setVendorlocationId(orgRelation.getLocationId().getId());
					}
					List<OrgSpoc>  contactsList = orgSpocService.getAllContactsbyOrgLocationIdAndUserIdForRequirement(orgLocations.getId(),user.getId(),"Vendor");
					orgRelation.setNoOfContatcs(contactsList.size()); 
					if(orgRelation.getResponderLevel()!=null){
						orgRelation.setLevel(orgRelation.getResponderLevel());
					}
					else{
						orgRelation.setLevel("");
					}
					
				}
				
				
			}
		    
		    
			 resultJson = commonUtil.toJSON(vandorsListByOrgLocationId);
			 return resultJson;
		}
	
	
	   /**
	    * @author Rajashekar
	    * @param request
	    * @return JSON
	    * @throws Exception
	    */
	   
	@RequestMapping(value = "/getVendorContactLists")
	   @ResponseBody
		public  String getVendorContactLists(@RequestParam(value="vendorStatus", required=false) String status,@RequestParam(value="vendorId", required=false) String vendorIds,@RequestParam(value="invitationStatus", required=false) String invitationStatus,HttpServletRequest request) throws Exception {

		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");		
		    String resultJson;
		   
		    List<OrgSpoc> vandorsContactListByOrgLocationId = orgRelationsService.getAllVendorsContactFilterListByOrgLocationId(loginUser.getId(),orgLocations.getId(), "Client",status,vendorIds,invitationStatus);
		    resultJson = commonUtil.toJSON(vandorsContactListByOrgLocationId);
		    return resultJson;
		}
	
	   
	   // this method is used to re-send email to Spoc
	   @RequestMapping(value = "/resendSpocInvitationEmail" , method = RequestMethod.POST)
		  public @ResponseBody  String resendInvitationEmail(@RequestParam(value="spocUserId", required=false) String spocUserId, @RequestParam(value="orgSpocId", required=false) String orgSpocId,HttpServletRequest request,String responderType, Long orgRelationID) throws Exception {
			    
			    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			    Org loginOrg = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			    OrgRelations orgRelations = orgRelationsService.getRespondingPartyDetailsById(orgRelationID);
			    //get user by userId
			    User user = userService.getUserById(Long.parseLong(spocUserId));
			    
			    String password = null;
		 		if(null != user  && null != orgRelations){
		 		    //to update spoc invitation status
		 			
		 			// this is commented for not updating status while spoc user duplicate in orgspoc table
		 			//OrgSpoc spoc = orgSpocService.getSpocUserByUserId(Long.parseLong(spocUserId));
		 		
		 			OrgSpoc spoc = orgSpocService.getOrgSpocById(Long.parseLong(orgSpocId));
		 			spoc.setInvitationStatus(Constants.MAIL_SENT);
		 			orgSpocService.addOrgSpoc(spoc);
		 			
		 			userService.sendEmailToClientOrVendor(spoc.getUUID(),user, null,loginUser, password, responderType, loginOrg,orgRelations.getResponderLocationId().getOrg(),orgRelations.getResponderLocationId(),loginOrgLocation);
		 		}
			
		 	  return "success";
		    }
	   
	   /**
	    * @author Rajashekar
	    * writes vendor to to excel and pdf
	    * @param status
	    * @param request
	    * @throws Exception
	    */
	   
	   @PreAuthorize("hasRole('ROLE_EXPORT_VENDORS')")
	   @RequestMapping(value = "/exportVendorDetails")
		public void  exportVendorDetails(@RequestParam(value="values", required=false) String status,@RequestParam(value="exportType", required=false) String exportType,HttpServletRequest request,HttpServletResponse response) throws Exception {

		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		    List<OrgRelations> vandorsList = orgRelationsService.getAllVendorsFilterByOrgLocationId(orgLocations.getId(),status,"");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				
			    for (OrgRelations orgRelation : vandorsList) {
					
					if(orgRelation.getLocationId() != null && orgRelation.getLocationId().getId().longValue() == orgLocations.getId().longValue()){
						orgRelation.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
						orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrgLocationName());
						orgRelation.setCountry(orgRelation.getResponderLocationId().getCountry());
						orgRelation.setState(orgRelation.getResponderLocationId().getState());
						orgRelation.setCity(orgRelation.getResponderLocationId().getCity());
						orgRelation.setVendorlocationId(orgRelation.getResponderLocationId().getId());
						//List<OrgSpoc>  contactsList = orgSpocService.getAllContactsbyOrgLocationIdAndUserIdForRequirement(orgLocations.getId(),user.getId(),"Client");
						List<Requirement> vendorReleasedJobsList = requirementService.getAllReleasedJobsForVendor(orgRelation.getResponderLocationId().getId(),orgRelation.getId(),user,orgLocations);
						List<OrgSpoc> contactsList = orgSpocService.getAllContactsbyOrgId(orgRelation.getId());
						
						orgRelation.setNoOfReleasedJobs(vendorReleasedJobsList.size());
						orgRelation.setNoOfContatcs(contactsList.size()); 
						if(orgRelation.getInitiatorLevel()!=null){
							orgRelation.setLevel(orgRelation.getInitiatorLevel());
						}
						else{
							orgRelation.setLevel("");
						}
						
					}
					
					if(orgRelation.getResponderLocationId() != null && orgRelation.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
						orgRelation.setWebSite(orgRelation.getLocationId().getOrg().getWebSite());
						orgRelation.setOrgName(orgRelation.getLocationId().getOrgLocationName());
						orgRelation.setCountry(orgRelation.getLocationId().getCountry());
						orgRelation.setState(orgRelation.getLocationId().getState());
						orgRelation.setCity(orgRelation.getLocationId().getCity());
						orgRelation.setVendorlocationId(orgRelation.getLocationId().getId());
//						List<OrgSpoc>  contactsList = orgSpocService.getAllContactsbyOrgLocationIdAndUserIdForRequirement(orgLocations.getId(),user.getId(),"Vendor");
						List<OrgSpoc> contactsList = orgSpocService.getAllContactsbyOrgId(orgRelation.getId());
						orgRelation.setNoOfContatcs(contactsList.size()); 
						if(orgRelation.getResponderLevel()!=null){
							orgRelation.setLevel(orgRelation.getResponderLevel());
						}
						else{
							orgRelation.setLevel("");
						}
					}
				}
			    File file = clientVendorExport.getVendorDetailsFile(vandorsList,orgLocations.getOrgLocationName(),exportType);
			    String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);
				StringBuilder fileName = new StringBuilder();
				response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				inputStream.close();
				response.getOutputStream().close();
				response.getOutputStream().flush();
			    
			}
	   
	  /**
	   * @author Rajashekar
	   * writes vendor contacts details to excel and pdf based on exportType 
	   * @param status
	   * @param vendorIds
	   * @param invitationStatus
	   * @param exportType
	   * @param request
	   * @throws Exception
	   */
	   @PreAuthorize("hasRole('ROLE_EXPORT_VENDOR_CONTACTS')") 
	   @RequestMapping(value = "/exportVendorContactLists")
		public  void exportVendorContactLists(@RequestParam(value="vendorStatus", required=false) String status,@RequestParam(value="vendorId", required=false) String vendorIds,@RequestParam(value="invitationStatus", required=false) String invitationStatus,@RequestParam(value="exportType", required=false) String exportType,HttpServletRequest request,HttpServletResponse response) throws Exception {

		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");		
		    Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		    List<OrgSpoc> vandorsContactList = orgRelationsService.getAllVendorsContactFilterListByOrgLocationId(loginUser.getId(),orgLocations.getId(), "Client",status,vendorIds,invitationStatus);
			
			 File file = clientVendorExport.getVendorContactDetailsFile(vandorsContactList,orgLocations.getOrgLocationName(),exportType);
			    String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);
				StringBuilder fileName = new StringBuilder();
				response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				inputStream.close();
				response.getOutputStream().close();
				response.getOutputStream().flush();
			if(file!= null){
				file.delete();
			}

	   }
	   
	   
	   /**
		   * @author 
		   * @since  Jan 18 2017
		   * @version 0.0.1
		   * 
		   *  
		   * @return string 
		   * 
		   * @throws NullPointer
		   */
		 @RequestMapping(value = "/searchVendorThroughElasticSearch", method = RequestMethod.POST)
		 public String searchVendorThroughElasticSearch(@RequestParam(value = "responderType", required = false) String responderType, @RequestParam(value = "json", required = false) String json, MultipartHttpServletRequest request) throws Exception {

			    MultipartFile multiFile = null;
				Iterator<String> itrator = request.getFileNames();
				if(itrator.hasNext()){
			        multiFile = request.getFile(itrator.next());
				}
				String jsonc = "{" + json + "}";
				JSONObject jsonObj = new JSONObject(jsonc);
				
				 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
				
				 
				 
                 BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
				 
				 if("Vendor".equalsIgnoreCase(responderType)){
					 responderType = "Client";
				 }else if("Client".equalsIgnoreCase(responderType)){
					 responderType = "Vendor"; 
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
				 
			   	 
				 // to   get the number of contacts 
					Long maxResults = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CLIENTVENDOR_INDEX, Constants.ELK_CLIENTVENDOR_TYPE,0,0,boolQueryBuilder).getTotalHits();
					  
				 
				 //Exclude Keyword
				 ArrayList<String> excludeKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "excludeKeyWord");
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
					ArrayList<String> anyKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "anyOfKeyword");
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
					ArrayList<String> allKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "allKeywords");
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
					ArrayList<String> vendorNameArray = getSearchKeywordsFromChosenSelectField(jsonObj, "orgNameAdvanced");
					if(vendorNameArray != null && !vendorNameArray.isEmpty()){
						BoolQueryBuilder vendorNameBoolQueryBuilder = QueryBuilders.boolQuery();
						for(String keywd : vendorNameArray){
							vendorNameBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("initiatorOrgName", keywd));
							vendorNameBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("responderOrgName", keywd));
						}
						boolQueryBuilder.must(vendorNameBoolQueryBuilder);
					}
					
					 //Country
					if(jsonObj.has("country") && StringUtils.isNotEmpty(jsonObj.getString("country"))){
						boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("responderCountry", jsonObj.getString("country")));
						boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("initiatorCountry", jsonObj.getString("country")));
				    }
					
					
					 //State
					if(jsonObj.has("state") && StringUtils.isNotEmpty(jsonObj.getString("state"))){
						boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("responderState", jsonObj.getString("state")));
				    }
					if(jsonObj.has("state") && StringUtils.isNotEmpty(jsonObj.getString("state"))){
						boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("initiatorState", jsonObj.getString("state")));
				    }
					
					//City
					if(jsonObj.has("city")  && StringUtils.isNotEmpty(jsonObj.getString("city"))){
						boolQueryBuilder.should(QueryBuilders.matchQuery("initiatorCity", jsonObj.getString("city")));
				    }
					if(jsonObj.has("city")  && StringUtils.isNotEmpty(jsonObj.getString("city"))){
						boolQueryBuilder.should(QueryBuilders.matchQuery("responderCity", jsonObj.getString("city")));
				    }
					
						
					//CreatedBy
					ArrayList<String> createdByArray = getSearchKeywordsFromChosenSelectField(jsonObj, "createdByAdvanced");
					if(createdByArray != null && !createdByArray.isEmpty()){
						BoolQueryBuilder createdByBoolQueryBuilder = QueryBuilders.boolQuery();
						for(String keywd : createdByArray){
							createdByBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("createdBy", keywd));
						}
						boolQueryBuilder.must(createdByBoolQueryBuilder);
					}
					
					//tierAdvanced
					ArrayList<String> tierAdvancedByArray = getSearchKeywordsFromChosenSelectField(jsonObj, "tierAdvanced");
					if(tierAdvancedByArray != null && !tierAdvancedByArray.isEmpty()){
						BoolQueryBuilder tierAdvancedBoolQueryBuilder = QueryBuilders.boolQuery();
						for(String keywd : tierAdvancedByArray){
							tierAdvancedBoolQueryBuilder.should(QueryBuilders.matchPhrasePrefixQuery("initiatorLevel", keywd));
							//tierAdvancedBoolQueryBuilder.should(QueryBuilders.matchPhrasePrefixQuery("responderLevel", keywd));
						}
						boolQueryBuilder.must(tierAdvancedBoolQueryBuilder);
					}
					
					List<ClientVendorIndex> clientVendorList = new ArrayList<ClientVendorIndex>();
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					df.setTimeZone(TimeZone.getTimeZone("GMT"));
					SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CLIENTVENDOR_INDEX, Constants.ELK_CLIENTVENDOR_TYPE,0,maxResults.intValue(),boolQueryBuilder).getHits();
					if(searchHits != null && searchHits.length >0){
						ObjectMapper mapper = new ObjectMapper();
						for (SearchHit hit : searchHits) {
									json = hit.getSourceAsString();
									mapper.setDateFormat(df);
									ClientVendorIndex clientVendor = mapper.readValue(json, ClientVendorIndex.class);
									
									
									 
								//  This is to get the number of Contacts based  on Org Relation Id
									 BoolQueryBuilder forNumberOfContacts= QueryBuilders.boolQuery();
									 forNumberOfContacts.must(QueryBuilders.matchQuery("orgRelationId", clientVendor.getOrgRelationId()));
									 
									 Long numberOfContacts = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CLIENTVENDOR_CONTACT_INDEX, Constants.ELK_CLIENTVENDOR_CONTACT_TYPE,0,0,forNumberOfContacts).getTotalHits();
									 clientVendor.setNoContacts(numberOfContacts);
									 
									if(null != clientVendor.getOrgLocationId() && clientVendor.getOrgLocationId().longValue() == loginOrgLocation.getId().longValue()){
										
									    clientVendor.setWebsite(clientVendor.getResponderWebSite());
										clientVendor.setOrgName(clientVendor.getResponderOrgName());
										clientVendor.setCountry(clientVendor.getResponderCountry());
										clientVendor.setState(clientVendor.getResponderState());
										clientVendor.setCity(clientVendor.getResponderCity());
										
										if(clientVendor.getInitiatorLevel()!=null){
											clientVendor.setInitiatorLevel(clientVendor.getInitiatorLevel());
										}
									}
								  if(null != clientVendor.getResponderLocationId()  && clientVendor.getResponderLocationId().longValue() == loginOrgLocation.getId().longValue()){
									 
									  clientVendor.setWebsite(clientVendor.getInitiatorWebSite());
									  clientVendor.setOrgName(clientVendor.getInitiatorOrgName());
									  clientVendor.setCountry(clientVendor.getInitiatorCountry());
									  clientVendor.setState(clientVendor.getInitiatorState());
									  clientVendor.setCity(clientVendor.getInitiatorCity());
									  
									  if(clientVendor.getResponderLevel()!=null){
										  clientVendor.setInitiatorLevel(clientVendor.getResponderLevel());
									  }
									}
								  if(!responderType.isEmpty() &&  Constants.VENDOR.equalsIgnoreCase(responderType)){
									  if(null != clientVendor.getCreatedBy() && !clientVendor.getCreatedBy().isEmpty()){
										  clientVendor.setCreatedBy(clientVendor.getCreatedBy());
									  }
									 
								  }
								  
								  clientVendorList.add(clientVendor);

						}	
					}
					
				 String resultJson;
				 resultJson = commonUtil.toJSON(clientVendorList);
				 return resultJson;
		 }
		 
		 private ArrayList<String> getSearchKeywordsFromChosenSelectField(JSONObject jsonObj,String param) throws Exception {
			 ArrayList<String> keyWordList = null;
			 if(jsonObj.has(param)){
				 String searchKeywordString = null;
				 try{
		   		   JSONArray jsonArray = jsonObj.getJSONArray(param);
		   		   searchKeywordString = jsonArray.toString();
			   	  }
			   	  catch(JSONException e){
			   		  searchKeywordString= jsonObj.getString(param);
			   	  }
				 
			     searchKeywordString = searchKeywordString.replaceAll("\\[", "").replaceAll("\\]","");
			     keyWordList = new ArrayList<String>(Arrays.asList(searchKeywordString.split(",")));
			 }
			 else{
				 keyWordList = new ArrayList<String>();
			 }
	        return keyWordList;
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
		 
		 /**
		   * @author 
		   * @since  Jan 18 2017
		   * @version 0.0.1
		   * 
		   *  
		   * @return string 
		   * 
		   * @throws NullPointer
		   */
		 @RequestMapping(value = "/searchVendorContactsThroughElasticSearch", method = RequestMethod.POST)
		 public String searchVendorContactsThroughElasticSearch(@RequestParam(value = "responderType", required = false) String responderType,@RequestParam(value = "json", required = false) String json,MultipartHttpServletRequest request) throws Exception {

			    MultipartFile multiFile = null;
				Iterator<String> itrator = request.getFileNames();
				if(itrator.hasNext()){
			        multiFile = request.getFile(itrator.next());
				}
				String jsonc = "{" + json + "}";
				JSONObject jsonObj = new JSONObject(jsonc);
				
				 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
				 User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");		

			     Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(loginUser.getId(),loginOrgLocation.getId());
					
				 BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
				 
				
				 // Newly Added queries based  on the changes in Client Vendor Savings
				 BoolQueryBuilder responderQueryBuilder = QueryBuilders.boolQuery();
				 if("Vendor".equalsIgnoreCase(responderType)){
					 responderQueryBuilder.must(QueryBuilders.matchQuery("clientLocationId", loginOrgLocation.getId()));
				 }else if("Client".equalsIgnoreCase(responderType)){
					 responderQueryBuilder.must(QueryBuilders.matchQuery("vendorLocationId", loginOrgLocation.getId()));
				 }
				 
				 BoolQueryBuilder statusQueryBuilder = QueryBuilders.boolQuery();
				 statusQueryBuilder.must(QueryBuilders.matchQuery("status", "1"));
				 
				 boolQueryBuilder.must(responderQueryBuilder);
				 boolQueryBuilder.must(statusQueryBuilder);
				 
				
				 //  These  are Newly added Queries Based on the Client Vendor Contacts Changes
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
				 
				 // to   get the number of contacts 
			      Long maxResults = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CLIENTVENDOR_CONTACT_INDEX, Constants.ELK_CLIENTVENDOR_CONTACT_TYPE,0,0,boolQueryBuilder).getTotalHits();
					 
			   	
				 //Exclude Keyword
				 ArrayList<String> excludeKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "excludeKeyWord");
				 if(excludeKeyWordArray != null && !excludeKeyWordArray.isEmpty()){
				 	 BoolQueryBuilder excludeBoolQueryBuilder = QueryBuilders.boolQuery();
					 for(String keywd : excludeKeyWordArray){
						 BoolQueryBuilder excludeBoolSubQueryBuilder = QueryBuilders.boolQuery();
						ArrayList<MatchQueryBuilder> matchQueryBuilderList =getMatchQueryBuilderListForContacts(keywd,responderType);
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
					ArrayList<String> anyKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "anyOfKeyword");
					if(anyKeyWordArray != null && !anyKeyWordArray.isEmpty()){
						 BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
						 for(String keywd : anyKeyWordArray){
							 BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
							ArrayList<MatchQueryBuilder> matchQueryBuilderList =getMatchQueryBuilderListForContacts(keywd,responderType);
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
					ArrayList<String> allKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "allKeywords");
					if(allKeyWordArray != null && !allKeyWordArray.isEmpty()){
						 BoolQueryBuilder allBoolQueryBuilder = QueryBuilders.boolQuery();
						 for(String keywd : allKeyWordArray){
							 BoolQueryBuilder allBoolSubQueryBuilder = QueryBuilders.boolQuery();
							ArrayList<MatchQueryBuilder> matchQueryBuilderList =getMatchQueryBuilderListForContacts(keywd,responderType);
							if(matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()){
								for(MatchQueryBuilder matchQueryBuilder :matchQueryBuilderList){
									allBoolSubQueryBuilder.should(matchQueryBuilder);
								}
								allBoolQueryBuilder.must(allBoolSubQueryBuilder);
							}
						}
						 boolQueryBuilder.must(allBoolQueryBuilder);
					}
					
					//contactName
					ArrayList<String> contactNameArray = getSearchKeywordsFromChosenSelectField(jsonObj, "contactNameAdvanced");
					if(contactNameArray != null && !contactNameArray.isEmpty()){
						BoolQueryBuilder contactNameBoolQueryBuilder = QueryBuilders.boolQuery();
						for(String keywd : contactNameArray){
							 if("Vendor".equalsIgnoreCase(responderType)){
								 contactNameBoolQueryBuilder.should(QueryBuilders.matchQuery("vendorUserFirstName" , keywd));
								 contactNameBoolQueryBuilder.should(QueryBuilders.matchQuery("vendorUserLastName", keywd));
								  }
								 if("Client".equalsIgnoreCase(responderType)){
									 contactNameBoolQueryBuilder.should(QueryBuilders.matchQuery("clientUserFirstName" , keywd));
									 contactNameBoolQueryBuilder.should(QueryBuilders.matchQuery("clientUserLastName", keywd));  
								  }
						}
						boolQueryBuilder.must(contactNameBoolQueryBuilder);
					}
					
					
					//Spoc Email
					ArrayList<String> contactEmailNameArray = getSearchKeywordsFromChosenSelectField(jsonObj, "emailAdvanced");
					if(contactEmailNameArray != null && !contactEmailNameArray.isEmpty()){
						BoolQueryBuilder contactEmailBoolQueryBuilder = QueryBuilders.boolQuery();
						for(String keywd : contactEmailNameArray){
							if("Vendor".equalsIgnoreCase(responderType)){
								contactEmailBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("vendorUserEmail", keywd));
							  }
							 if("Client".equalsIgnoreCase(responderType)){
								 contactEmailBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("clientUserEmail", keywd));
							  }
						}
						boolQueryBuilder.must(contactEmailBoolQueryBuilder);
					}
					
					//Spoc PhoneNumber
					ArrayList<String> phoneNumberNameArray = getSearchKeywordsFromChosenSelectField(jsonObj, "phoneNumberAdvanced");
					if(phoneNumberNameArray != null && !phoneNumberNameArray.isEmpty()){
						BoolQueryBuilder phoneNumberBoolQueryBuilder = QueryBuilders.boolQuery();
						for(String keywd : phoneNumberNameArray){
							 if("Vendor".equalsIgnoreCase(responderType)){
								 phoneNumberBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("vendorUserPhNo", keywd));
							  }
							 if("Client".equalsIgnoreCase(responderType)){
								 phoneNumberBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("clientUserPhNo", keywd));
							  }
						}
						boolQueryBuilder.must(phoneNumberBoolQueryBuilder);
					}
					
					//vendor
					ArrayList<String> orgAdvancedArray = getSearchKeywordsFromChosenSelectField(jsonObj, "orgAdvanced");
					if(orgAdvancedArray != null && !orgAdvancedArray.isEmpty()){
						BoolQueryBuilder orgAdvancedBoolQueryBuilder = QueryBuilders.boolQuery();
						for(String keywd : orgAdvancedArray){
							if("Vendor".equalsIgnoreCase(responderType)){
								orgAdvancedBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("vendorOrgName", keywd));
							  }
							 if("Client".equalsIgnoreCase(responderType)){
								 orgAdvancedBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("clientOrgName", keywd));
							  }
						}
						boolQueryBuilder.must(orgAdvancedBoolQueryBuilder);
					}
					
					//Spoc ownerAdvanced
					ArrayList<String> ownerAdvancedNameArray = getSearchKeywordsFromChosenSelectField(jsonObj, "ownerAdvanced");
					if(ownerAdvancedNameArray != null && !ownerAdvancedNameArray.isEmpty()){
						BoolQueryBuilder ownerAdvancedBoolQueryBuilder = QueryBuilders.boolQuery();
						for(String keywd : ownerAdvancedNameArray){
							 if("Vendor".equalsIgnoreCase(responderType)){
								 ownerAdvancedBoolQueryBuilder.should(QueryBuilders.matchQuery("clientUserFirstName" , keywd));
								 ownerAdvancedBoolQueryBuilder.should(QueryBuilders.matchQuery("clientUserLastName", keywd));
								  }
								 if("Client".equalsIgnoreCase(responderType)){
									 ownerAdvancedBoolQueryBuilder.should(QueryBuilders.matchQuery("vendorUserFirstName" , keywd));
									 ownerAdvancedBoolQueryBuilder.should(QueryBuilders.matchQuery("vendorUserLastName", keywd));
								  }
						}
						boolQueryBuilder.must(ownerAdvancedBoolQueryBuilder);
					}
					
					//Spoc Status
					ArrayList<String> statusAdvancedArray = getSearchKeywordsFromChosenSelectField(jsonObj, "statusAdvanced");
					if(statusAdvancedArray != null && !statusAdvancedArray.isEmpty()){
						BoolQueryBuilder statusAdvancedBoolQueryBuilder = QueryBuilders.boolQuery();
						for(String keywd : statusAdvancedArray){
								if(keywd.equalsIgnoreCase("Active")){
									statusAdvancedBoolQueryBuilder.should(QueryBuilders.matchPhrasePrefixQuery("spocStatus", "Active"));
									statusAdvancedBoolQueryBuilder.mustNot(QueryBuilders.matchPhrasePrefixQuery("spocStatus", "In-Active"));
								}else{
									  statusAdvancedBoolQueryBuilder.should(QueryBuilders.matchPhrasePrefixQuery("spocStatus", keywd));
								}
						}
						boolQueryBuilder.must(statusAdvancedBoolQueryBuilder);
					}
					
					//Invitation  Status
					
					ArrayList<String> invitationAdvancedArray = getSearchKeywordsFromChosenSelectField(jsonObj, "invitationAdvanced");
					if(invitationAdvancedArray != null && !invitationAdvancedArray.isEmpty()){
						BoolQueryBuilder invitationAdvancedBoolQueryBuilder = QueryBuilders.boolQuery();
						for(String keywd : invitationAdvancedArray){
							invitationAdvancedBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("invitationStatus", keywd));
						}
						boolQueryBuilder.must(invitationAdvancedBoolQueryBuilder);
					}
					
					SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CLIENTVENDOR_CONTACT_INDEX, Constants.ELK_CLIENTVENDOR_CONTACT_TYPE,0,maxResults.intValue(),boolQueryBuilder).getHits();
					
					List<ClientVendorContactsIndex> clientVendorContactsList = new ArrayList<ClientVendorContactsIndex>();
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					df.setTimeZone(TimeZone.getTimeZone("GMT"));
					if(searchHits != null && searchHits.length >0){
						ObjectMapper mapper = new ObjectMapper();
						for (SearchHit hit : searchHits) {
							json = hit.getSourceAsString();
							mapper.setDateFormat(df);
							ClientVendorContactsIndex clientVendorContactsIndex = mapper.readValue(json, ClientVendorContactsIndex.class);
							clientVendorContactsList.add(clientVendorContactsIndex);
						}
						
						for(ClientVendorContactsIndex clientVendorContactsIndex : clientVendorContactsList){
							if("Vendor".equalsIgnoreCase(responderType)){
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
								
							 }else if("Client".equalsIgnoreCase(responderType)){
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
				 String resultJson;
				 resultJson = commonUtil.toJSON(clientVendorContactsList);
				 return resultJson;
		 }

/**
 * @author 
 * @since  Jan 25 2017
 * @version 0.0.1
 * 
 *  
 * @return string 
 * 
 * @throws NullPointer
 */
		 @RequestMapping(value = "/getVendorFilterListsElK", method = RequestMethod.POST)
		 public String getVendorFilterListsElK(@RequestParam(value = "responderType", required = false) String responderType,@RequestParam(value = "values", required = false) String values, HttpServletRequest request) throws Exception {

				 User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");		  
			 
				 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
				
				 BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
				 
//				 String type= responderType;
				 if("Vendor".equalsIgnoreCase(responderType)){
					 responderType = "Client";
				 }else if("Client".equalsIgnoreCase(responderType)){
					 responderType = "Vendor"; 
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
				 
     			 // to   get the number of Vendor/Clients 
				Long maxResults = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CLIENTVENDOR_INDEX, Constants.ELK_CLIENTVENDOR_TYPE,0,0,boolQueryBuilder).getTotalHits();
				 
				List<ClientVendorIndex> clientVendorList = new ArrayList<ClientVendorIndex>();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				df.setTimeZone(TimeZone.getTimeZone("GMT"));
				SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CLIENTVENDOR_INDEX, Constants.ELK_CLIENTVENDOR_TYPE,0,maxResults.intValue(),boolQueryBuilder).getHits();
				if(searchHits != null && searchHits.length >0){
					ObjectMapper mapper = new ObjectMapper();
					for (SearchHit hit : searchHits) {
					String	json = hit.getSourceAsString();
						mapper.setDateFormat(df);
						ClientVendorIndex clientVendor = mapper.readValue(json, ClientVendorIndex.class);
						
						//  This is to get the number of Contacts based  on Org Relation Id
						 BoolQueryBuilder forNumberOfContacts= QueryBuilders.boolQuery();
						 forNumberOfContacts.must(QueryBuilders.matchQuery("orgRelationId", clientVendor.getOrgRelationId()));
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
				}
				 String resultJson;
				 resultJson = commonUtil.toJSON(clientVendorList);
				 return resultJson;
		 }
		 
		 
		 
		 
		 /**
		   * @author 
		   * @since  Jan 25 2017
		   * @version 0.0.1
		   * 
		   *   This Method is used for vendor contacts from ELK on Load
		   * @return string 
		   * 
		   * @throws NullPointer
		   */
		 @RequestMapping(value = "/getVendorContactListsELK", method = RequestMethod.POST)
		 public String getVendorContactListsELK(@RequestParam(value = "responderType", required = false) String responderType,@RequestParam(value="vendorStatus", required=false) String status,@RequestParam(value="vendorId", required=false) String vendorIds,@RequestParam(value="invitationStatus", required=false) String invitationStatus,HttpServletRequest request) throws Exception {

				
				 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
				 User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");		

			     Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(loginUser.getId(),loginOrgLocation.getId());
					
				 BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
				 
				// boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", loginOrgLocation.getId()));
				
				 BoolQueryBuilder responderQueryBuilder = QueryBuilders.boolQuery();
				 if("Vendor".equalsIgnoreCase(responderType)){
					 responderQueryBuilder.must(QueryBuilders.matchQuery("clientLocationId", loginOrgLocation.getId()));
				 }else if("Client".equalsIgnoreCase(responderType)){
					 responderQueryBuilder.must(QueryBuilders.matchQuery("vendorLocationId", loginOrgLocation.getId()));
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
				 
				   // to   get the number of contacts 
					Long maxResults = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CLIENTVENDOR_CONTACT_INDEX, Constants.ELK_CLIENTVENDOR_CONTACT_TYPE,0,0,boolQueryBuilder).getTotalHits();
						
					SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CLIENTVENDOR_CONTACT_INDEX, Constants.ELK_CLIENTVENDOR_CONTACT_TYPE,0,maxResults.intValue(),boolQueryBuilder).getHits();

					List<ClientVendorContactsIndex> clientVendorContactsList = new ArrayList<ClientVendorContactsIndex>();
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					df.setTimeZone(TimeZone.getTimeZone("GMT"));
					if(searchHits != null && searchHits.length >0){
						ObjectMapper mapper = new ObjectMapper();
						for (SearchHit hit : searchHits) {
							String json = hit.getSourceAsString();
							mapper.setDateFormat(df);
							ClientVendorContactsIndex clientVendorContactsIndex = mapper.readValue(json, ClientVendorContactsIndex.class);
							clientVendorContactsList.add(clientVendorContactsIndex);
						}
						
						for(ClientVendorContactsIndex clientVendorContactsIndex : clientVendorContactsList){
							if("Vendor".equalsIgnoreCase(responderType)){
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
								
							 }else if("Client".equalsIgnoreCase(responderType)){
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
				 String resultJson;
				 resultJson = commonUtil.toJSON(clientVendorContactsList);
				 return resultJson;
		 }
		 
		
		 @RequestMapping(value = "/getUniversalSearchVendorList")
		 public String getUniversalSearchVendorList(@RequestParam(value="searchValue", required=false) String  searchValue,@RequestParam(value="searchType", required=false) String  searchType,
				 HttpServletRequest request) throws Exception {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			String resultJson;
			List<Object> uniVendorList =	clientService.getAllUniversalSearchVendorList(orgLocations, searchValue,searchType);
			resultJson = commonUtil.toJSON(uniVendorList);
			return resultJson;
		 }
		 
		 /**
		  * for vendor jobs
		  * 
		  * @param searchValue
		  * @param searchType
		  * @param request
		  * @return
		  * @throws Exception
		  */
	@RequestMapping(value = "/getVendorJobs")
	public String getVendorJobs(@RequestParam(value = "orgRelationID", required = false) Long orgRelationID, @RequestParam(value = "status", required = false) String status, HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		OrgRelations orgrel = orgRelationsService.getOrgRelationById(orgRelationID);
		long vendorLocationId = 0L;
		if (orgrel.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()) {
			vendorLocationId = orgrel.getLocationId().getId();
		}
		else if (orgrel.getLocationId().getId().longValue() == orgLocations.getId().longValue()) {
			vendorLocationId = orgrel.getResponderLocationId().getId();
		}
		// List<Requirement> reqList =
		// requirementService.getAllReleasedJobsForVendorBasedOnStartAndEndDates(orgLocations.getId(),
		// orgRelationID,null,null);
		List<Requirement> mainReqList = new ArrayList<>();
		Requirement mainRequirement = null;
		Long tempReqId =0l;
		int interviewsSum = 0;
		int offeredSum = 0;
		int backoutSum = 0;
		int submitToclient = 0;
		List<Requirement> reqList = requirementService.getAllVendorJobsBasedonRelationId(orgLocations.getId(), orgRelationID, userRoleName, loginUser, status);
		for (Requirement requirement : reqList) {
			if(tempReqId.longValue() != requirement.getParentRequirement().getId().longValue()){
				mainRequirement = requirementService.getRequirementDetailsById(requirement.getParentRequirement().getId());
				interviewsSum = 0;
				offeredSum = 0;
				backoutSum = 0;
				submitToclient = 0;	
//			}
			List<RequirementSubmission> requirementSubmissionList1 = requirementSubmissionService.getApplicantRequirmentCountSubmittedByVendor(orgLocations.getId(), requirement.getParentRequirement().getId(), vendorLocationId);
			if (null != requirementSubmissionList1 && requirementSubmissionList1.size() > 0) {
				for (RequirementSubmission reqSub : requirementSubmissionList1) {
					int vendorInterviewsCount = 0;
					int vendorOfferedCount = 0;
					int vendorBackoutCount = 0;
					int vendorSubmittoClientCount = 0;
					List<RequirementSubmissionHistory> requirementSubmissionHistoryList = requirementReportService.getApplicantsCountsByDateRange(reqSub.getId(), null, null);
					if (null != requirementSubmissionHistoryList
							&& requirementSubmissionHistoryList.size() > 0) {
						for (RequirementSubmissionHistory rsh : requirementSubmissionHistoryList) {
							if ("interview".equalsIgnoreCase(rsh.getToStage())) {
								vendorInterviewsCount++;
							}
							else if ("offermade".equalsIgnoreCase(rsh.getToStage())) {
								vendorOfferedCount++;
							}
							
							else if ("backout".equalsIgnoreCase(rsh.getToStage())) {
								vendorBackoutCount++;
							}
							else if(ApplicantSubmissionStatus.SUBMISSION.toString().equalsIgnoreCase(rsh.getToStage())){
								vendorSubmittoClientCount++;
							}
						}
						interviewsSum = interviewsSum + vendorInterviewsCount;
						offeredSum = offeredSum + vendorOfferedCount;
						backoutSum = backoutSum + vendorBackoutCount;
						submitToclient = submitToclient + vendorSubmittoClientCount;
					}
				}
			}
			   //SimpleDateFormat formatter = orgLocations.getLocFullFormDateFormatter();
			mainRequirement.setVendorInterviewsCount(interviewsSum);
			mainRequirement.setVendorOfferedCount(offeredSum);
			mainRequirement.setVendorBackoutCount(backoutSum);
			mainRequirement.setNoOfSubmittedApplicants(String.valueOf(submitToclient));
			mainRequirement.setPostedDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),requirement.getCreatedDate()));
			if (mainRequirement.getManagerUserId() != null) {
				String managerFirstName = "";
				if (StringUtils.isNotEmpty(mainRequirement.getManagerUserId().getFirstName())) {
					managerFirstName = mainRequirement.getManagerUserId().getFirstName();
				}
				String managerLastName = "";
				if (StringUtils.isNotEmpty(mainRequirement.getManagerUserId().getLastName())) {
					managerLastName = mainRequirement.getManagerUserId().getLastName();
				}
				StringBuilder name = new StringBuilder();
				mainRequirement.setManager(name.append(managerFirstName).append(" ").append(managerLastName).toString());
			}
//			if(tempReqId.longValue() != requirement.getParentRequirement().getId().longValue()){
				tempReqId = requirement.getParentRequirement().getId();
				mainReqList.add(mainRequirement);
			}
			
		}
		return commonUtil.toJSON(mainReqList);
	}
	
	
	@RequestMapping(value = "/getVendorAssociatesListForVendors")
	public String getVendorAssociatesListForVendors(@RequestParam(value = "orgRelationId", required = false) Long orgRelationId, HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String resultJson;
		List<Candidate> candidateList = candidateService.getCandidatesListByOrgLocationIdAndOrgRelationId(orgLocations.getId(), orgRelationId);
		resultJson = commonUtil.toJSON(candidateList);
		return resultJson;
	}
}