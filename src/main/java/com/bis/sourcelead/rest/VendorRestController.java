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
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.elk.index.ClientVendorContactsIndex;
import com.bis.sourcelead.elk.index.ClientVendorIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.ClientVendorExport;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementService;
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
	UserService userService;
	
	@Autowired
	private ClientVendorExport clientVendorExport;
	

	@Autowired
	private UserRoleDao userRoleDao;
	
	

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
	public String saveVenodrContact(@RequestParam(value = "json", required = false) String json, @RequestParam(value="orgRelationId", required=false) String orgRelationId,
			                        @RequestParam(value="clientLocationId", required=false) String clientLocationId ,
			                        @RequestParam(value="responderType", required=false) String responderType,
			                        HttpServletRequest request) throws Exception {

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		
		if(jsonObj.has("owner")){
			String ownersId = jsonObj.getString("owner");
			ownersId = ownersId.replaceAll("\\[", "").replaceAll("\\]","");
			String[] owners = ownersId.split(",");
			/*for(int i=0 ;i<owners.length;i++ ){
				User ownerUser = userService.getUserById(Long.parseLong(owners[i].replace("\"", "")));
				OrgSpoc orgSpoc = orgSpocService.validateSpacEmailIdByOwnerUserIdAndOrgRelationId(jsonObj.getString("email"), Long.parseLong(orgRelationId), ownerUser.getId());
			     if(orgSpoc == null){*/
			    	orgSpocService.addVendorContacts(jsonObj, user, orgLocations, orgRelationId, clientLocationId,responderType,org);
			    	if(responderType.equalsIgnoreCase("Vendor")){
			    		SourceLeadSessionManager.setSessionAttribute(request, "vendorContactCreateMsg","Success !! New contact added.");
				 	}else if(responderType.equalsIgnoreCase("Client")){
				 		SourceLeadSessionManager.setSessionAttribute(request, "clientContactCreateMsg","Success !! New contact added.");
				 	}
					
			    /*}
			} */
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

	    OrgRelations orgRelation =orgRelationsService.getOrRelationsById(orgRelationId);
	    Long releasedJobsSize =0l;
		List<Requirement> vendorReleasedJobsList = requirementService.getAllReleasedJobsForVendor(orgRelation.getLocationId().getId(),orgRelation.getId(),user,orgLocations);
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
	    
	    List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllVendorsFilterByOrgLocationId(orgLocations.getId(),status);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    //List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocations.getId());
 
		    for (OrgRelations orgRelation : vandorsListByOrgLocationId) {
				
				if(orgRelation.getLocationId()!= null && orgRelation.getLocationId().getId()!=null &&orgRelation.getLocationId().getId().longValue() == orgLocations.getId().longValue()){
					if(orgRelation.getResponderLocationId() !=null){
						orgRelation.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
						orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrg().getOrgName());
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
						orgRelation.setOrgName(orgRelation.getLocationId().getOrg().getOrgName());
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
	    
	    List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllVendorsFilterByOrgLocationId(orgLocations.getId(),status);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    //List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocations.getId());
 
			
		    for (OrgRelations orgRelation : vandorsListByOrgLocationId) {
				
				if(orgRelation.getLocationId()!= null && orgRelation.getLocationId().getId()!=null &&orgRelation.getLocationId().getId().longValue() == orgLocations.getId().longValue()){
					if(orgRelation.getResponderLocationId()!=null){
						orgRelation.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
						orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrg().getOrgName());
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
						orgRelation.setOrgName(orgRelation.getLocationId().getOrg().getOrgName());
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
		    
		    //List<OrgSpoc> vandorsContactListByOrgLocationId = orgRelationsService.getAllVendorsContactListByOrgLocationId(loginUser.getId(),orgLocations.getId(), "Client");
		
			
			for (OrgSpoc orgSpoc : vandorsContactListByOrgLocationId) {
				if((orgSpoc.getSpocUser() != null && orgSpoc.getSpocUser().getId().longValue() == loginUser.getId().longValue() )|| (orgSpoc.getOwnerUser() != null &&orgSpoc.getOwnerUser().getId().longValue() == loginUser.getId().longValue())){
					//if login user is spoc
					if(orgSpoc.getSpocUser() != null && orgSpoc.getSpocUser().getId().longValue() == loginUser.getId().longValue()){
						if(orgSpoc.getOwnerUser() != null && orgSpoc.getOwnerUser().getFirstName() != "" && orgSpoc.getOwnerUser().getLastName() != null && orgSpoc.getOwnerUser().getLastName() != "" ){
							StringBuilder spocNameSB = new StringBuilder();
							orgSpoc.setSpocName(spocNameSB.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
						}else{
							orgSpoc.setSpocName(orgSpoc.getOwnerUser().getFirstName());
						}
						if(orgSpoc.getSpocUser() != null && orgSpoc.getSpocUser().getFirstName() != null && orgSpoc.getSpocUser().getFirstName() != "" && orgSpoc.getSpocUser().getLastName() != null && orgSpoc.getSpocUser().getLastName() != "" ){
							StringBuilder ownerNameSB = new StringBuilder();
							orgSpoc.setOwnerName(ownerNameSB.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
						}else{
							orgSpoc.setOwnerName(orgSpoc.getSpocUser().getFirstName());
						}
	    				 orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
	    				 orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
	    				// orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
	    				 orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
	    				 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
					}
					//if login user is Owner
					else if(orgSpoc.getOwnerUser() != null  && orgSpoc.getOwnerUser().getId().longValue() == loginUser.getId().longValue()){
						if(orgSpoc.getSpocUser() != null && orgSpoc.getSpocUser().getFirstName() != "" && orgSpoc.getSpocUser().getLastName() != null && orgSpoc.getSpocUser().getLastName() != "" ){
							StringBuilder spocNameSB1 = new StringBuilder();
							orgSpoc.setSpocName(spocNameSB1.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
						}else{
							orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName());
						}
						if(orgSpoc.getOwnerUser() != null && orgSpoc.getOwnerUser().getFirstName() != "" && orgSpoc.getOwnerUser().getLastName() != null && orgSpoc.getOwnerUser().getLastName() != "" ){
							StringBuilder ownerNameSB1 = new StringBuilder();
							 orgSpoc.setOwnerName(ownerNameSB1.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
						}else{
							orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName());
						}
							 orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
							 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
							 //orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
							 orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
							 orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
						}
						
					 
				}else{
					if(orgSpoc.getInitiatorLocationId() != null  && orgSpoc.getInitiatorLocationId().getId().longValue() == orgLocations.getId().longValue() ){
						if(orgSpoc.getSpocUser() != null && orgSpoc.getSpocUser().getFirstName() != "" && orgSpoc.getSpocUser().getLastName() != null && orgSpoc.getSpocUser().getLastName() != "" ){
							StringBuilder spocNameSB2 = new StringBuilder();
							orgSpoc.setSpocName(spocNameSB2.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
						}else{
							orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName());
						}
						
						if(orgSpoc.getOwnerUser() != null && orgSpoc.getOwnerUser().getFirstName() != "" && orgSpoc.getOwnerUser().getLastName() != null && orgSpoc.getOwnerUser().getLastName() != "" ){
							StringBuilder ownerNameSB2 = new StringBuilder();
							orgSpoc.setOwnerName(ownerNameSB2.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
						}else{
							orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName());
						}
						 orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
						 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
						 orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
						 orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
					 }
					if(orgSpoc.getResponderLocationId() != null  &&  orgSpoc.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
						if(orgSpoc.getOwnerUser() != null && orgSpoc.getOwnerUser().getFirstName() != "" && orgSpoc.getOwnerUser().getLastName() != null && orgSpoc.getOwnerUser().getLastName() != "" ){
							StringBuilder spocNameSB3 = new StringBuilder();
							orgSpoc.setSpocName(spocNameSB3.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
						}else{
							orgSpoc.setSpocName(orgSpoc.getOwnerUser().getFirstName());
						}
						
						if(orgSpoc.getSpocUser() != null && orgSpoc.getSpocUser().getFirstName() != "" && orgSpoc.getSpocUser().getLastName() != null && orgSpoc.getSpocUser().getLastName() != "" ){
							StringBuilder ownerNameSB3 = new StringBuilder();
							orgSpoc.setOwnerName(ownerNameSB3.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
						}else{
							orgSpoc.setOwnerName(orgSpoc.getSpocUser().getFirstName());
						}
					 orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
					 orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
					 orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
					 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
					}
				}
				if(orgSpoc.getResponderLocationId() != null && orgSpoc.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
					if(orgSpoc.getInitiatorLocationId()!=null){
					   orgSpoc.setSpocLocationId(orgSpoc.getInitiatorLocationId().getId());
					   orgSpoc.setOrgName(orgSpoc.getInitiatorLocationId().getOrg().getOrgName());
					}
					
				}
				if(orgSpoc.getInitiatorLocationId() != null && orgSpoc.getInitiatorLocationId().getId().longValue() == orgLocations.getId().longValue() ){
					if(orgSpoc.getResponderLocationId()!=null){
					orgSpoc.setSpocLocationId(orgSpoc.getResponderLocationId().getId());
					orgSpoc.setOrgName(orgSpoc.getResponderLocationId().getOrg().getOrgName());
					}
				}
				
				orgSpoc.setOwnerUserId(orgSpoc.getOwnerUser().getId().toString());
				orgSpoc.setSpocUserId(orgSpoc.getSpocUser().getId());
				orgSpoc.setOrgRelationId(orgSpoc.getOrgRelations().getId());
			}
			
			 resultJson = commonUtil.toJSON(vandorsContactListByOrgLocationId);
			 return resultJson;
		}
	
	   
	   // this method is used to re-send email to Spoc
	   @RequestMapping(value = "/resendSpocInvitationEmail" , method = RequestMethod.POST)
		  public @ResponseBody  String resendInvitationEmail(@RequestParam(value="spocUserId", required=false) String spocUserId, @RequestParam(value="orgSpocId", required=false) String orgSpocId,HttpServletRequest request,String responderType, Long orgRelationID) throws Exception {
			    
			    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			    Org loginOrg = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
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
		 			
		 			userService.sendEmailToClientOrVendor(user, loginUser, password, responderType, loginOrg,orgRelations.getResponderLocationId().getOrg(),orgRelations.getResponderLocationId());
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
		    List<OrgRelations> vandorsList = orgRelationsService.getAllVendorsFilterByOrgLocationId(orgLocations.getId(),status);
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				
			    for (OrgRelations orgRelation : vandorsList) {
					
					if(orgRelation.getLocationId() != null && orgRelation.getLocationId().getId().longValue() == orgLocations.getId().longValue()){
						orgRelation.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
						orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrg().getOrgName());
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
						orgRelation.setOrgName(orgRelation.getLocationId().getOrg().getOrgName());
						orgRelation.setCountry(orgRelation.getLocationId().getCountry());
						orgRelation.setState(orgRelation.getLocationId().getState());
						orgRelation.setCity(orgRelation.getLocationId().getCity());
						orgRelation.setVendorlocationId(orgRelation.getLocationId().getId());
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
			    File file = clientVendorExport.getVendorDetailsFile(vandorsList,org.getOrgName(),exportType);
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
		    
		    //List<OrgSpoc> vandorsContactListByOrgLocationId = orgRelationsService.getAllVendorsContactListByOrgLocationId(loginUser.getId(),orgLocations.getId(), "Client");
		
			
			for (OrgSpoc orgSpoc : vandorsContactList) {
				if(orgSpoc.getSpocUser().getId().longValue() == loginUser.getId().longValue() || orgSpoc.getOwnerUser().getId().longValue() == loginUser.getId().longValue()){
					//if login user is spoc
					if(orgSpoc.getSpocUser() !=null && orgSpoc.getSpocUser().getId().longValue() == loginUser.getId().longValue()){
						
						if(orgSpoc.getOwnerUser() != null && orgSpoc.getOwnerUser().getFirstName() != "" && orgSpoc.getOwnerUser().getLastName() != null && orgSpoc.getOwnerUser().getLastName() != "" ){
							StringBuilder spocNameSB = new StringBuilder();
							orgSpoc.setSpocName(spocNameSB.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
						}else{
							orgSpoc.setSpocName(orgSpoc.getOwnerUser().getFirstName());
						}
						if(orgSpoc.getSpocUser().getFirstName() != null && orgSpoc.getSpocUser().getFirstName() != "" && orgSpoc.getSpocUser().getLastName() != null && orgSpoc.getSpocUser().getLastName() != "" ){
							StringBuilder ownerNameSB = new StringBuilder();
							orgSpoc.setOwnerName(ownerNameSB.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
						}else{
							orgSpoc.setOwnerName(orgSpoc.getSpocUser().getFirstName());
						}
	    				 orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
	    				 orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
	    				// orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
	    				 orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
	    				 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
					}
					//if login user is Owner
					else if(orgSpoc.getOwnerUser() != null && orgSpoc.getOwnerUser().getId().longValue() == loginUser.getId().longValue()){
						if(orgSpoc.getSpocUser().getFirstName() != null && orgSpoc.getSpocUser().getFirstName() != "" && orgSpoc.getSpocUser().getLastName() != null && orgSpoc.getSpocUser().getLastName() != "" ){
							StringBuilder spocNameSB1 = new StringBuilder();
							orgSpoc.setSpocName(spocNameSB1.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
						}else{
							orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName());
						}
						if(orgSpoc.getOwnerUser() != null && orgSpoc.getOwnerUser().getFirstName() != "" && orgSpoc.getOwnerUser().getLastName() != null && orgSpoc.getOwnerUser().getLastName() != "" ){
							StringBuilder ownerNameSB1 = new StringBuilder();
							 orgSpoc.setOwnerName(ownerNameSB1.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
						}else{
							orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName());
						}
							 orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
							 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
							 //orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
							 orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
							 orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
						}
						
					 
				}else{
					if(orgSpoc.getInitiatorLocationId() != null && orgSpoc.getInitiatorLocationId().getId().longValue() == orgLocations.getId().longValue() ){
						if(orgSpoc.getSpocUser().getFirstName() != null && orgSpoc.getSpocUser().getFirstName() != "" && orgSpoc.getSpocUser().getLastName() != null && orgSpoc.getSpocUser().getLastName() != "" ){
							StringBuilder spocNameSB2 = new StringBuilder();
							orgSpoc.setSpocName(spocNameSB2.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
						}else{
							orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName());
						}
						
						if(orgSpoc.getOwnerUser().getFirstName() != null && orgSpoc.getOwnerUser().getFirstName() != "" && orgSpoc.getOwnerUser().getLastName() != null && orgSpoc.getOwnerUser().getLastName() != "" ){
							StringBuilder ownerNameSB2 = new StringBuilder();
							orgSpoc.setOwnerName(ownerNameSB2.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
						}else{
							orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName());
						}
						 orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
						 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
						 orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
						 orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
					 }
					if(orgSpoc.getResponderLocationId() !=null && orgSpoc.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
						if(orgSpoc.getOwnerUser().getFirstName() != null && orgSpoc.getOwnerUser().getFirstName() != "" && orgSpoc.getOwnerUser().getLastName() != null && orgSpoc.getOwnerUser().getLastName() != "" ){
							StringBuilder spocNameSB3 = new StringBuilder();
							orgSpoc.setSpocName(spocNameSB3.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
						}else{
							orgSpoc.setSpocName(orgSpoc.getOwnerUser().getFirstName());
						}
						
						if(orgSpoc.getSpocUser().getFirstName() != null && orgSpoc.getSpocUser().getFirstName() != "" && orgSpoc.getSpocUser().getLastName() != null && orgSpoc.getSpocUser().getLastName() != "" ){
							StringBuilder ownerNameSB3 = new StringBuilder();
							orgSpoc.setOwnerName(ownerNameSB3.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
						}else{
							orgSpoc.setOwnerName(orgSpoc.getSpocUser().getFirstName());
						}
					 orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
					 orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
					 orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
					 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
					}
				}
				if(orgSpoc.getResponderLocationId() !=null && orgSpoc.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
					orgSpoc.setSpocLocationId(orgSpoc.getInitiatorLocationId().getId());
					orgSpoc.setOrgName(orgSpoc.getInitiatorLocationId().getOrg().getOrgName());
				}
				if(orgSpoc.getInitiatorLocationId() !=null && orgSpoc.getInitiatorLocationId().getId().longValue() == orgLocations.getId().longValue() ){
					orgSpoc.setSpocLocationId(orgSpoc.getResponderLocationId().getId());
					orgSpoc.setOrgName(orgSpoc.getResponderLocationId().getOrg().getOrgName());
				}
				
				orgSpoc.setOwnerUserId(orgSpoc.getOwnerUser().getId().toString());
				orgSpoc.setSpocUserId(orgSpoc.getSpocUser().getId());
				orgSpoc.setOrgRelationId(orgSpoc.getOrgRelations().getId());
			}
			
			 File file = clientVendorExport.getVendorContactDetailsFile(vandorsContactList,org.getOrgName(),exportType);
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
				/* 
				 boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", loginOrgLocation.getId()));
				 boolQueryBuilder.must(QueryBuilders.matchQuery("responderType", responderType));*/
				 
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
							vendorNameBoolQueryBuilder.should(QueryBuilders.matchQuery("initiatorOrgName", keywd));
							vendorNameBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("responderOrgName", keywd));
						}
						boolQueryBuilder.must(vendorNameBoolQueryBuilder);
					}
					
					/* matchQueryBuilderList.add(QueryBuilders.matchQuery("initiatorOrgName", keywd));
					    matchQueryBuilderList.add(QueryBuilders.matchQuery("responderOrgName", keywd));
					*/
					 //Country
					if(jsonObj.has("country") && StringUtils.isNotEmpty(jsonObj.getString("country"))){
						boolQueryBuilder.should(QueryBuilders.matchQuery("responderCountry", jsonObj.getString("country")));
				    }
					if(jsonObj.has("country") && StringUtils.isNotEmpty(jsonObj.getString("country"))){
						boolQueryBuilder.should(QueryBuilders.matchQuery("responderCountry", jsonObj.getString("country")));
				    }
					
					
					 //State
					if(jsonObj.has("state") && StringUtils.isNotEmpty(jsonObj.getString("state"))){
						boolQueryBuilder.should(QueryBuilders.matchQuery("responderState", jsonObj.getString("state")));
				    }
					if(jsonObj.has("state") && StringUtils.isNotEmpty(jsonObj.getString("state"))){
						boolQueryBuilder.should(QueryBuilders.matchQuery("initiatorState", jsonObj.getString("state")));
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
							tierAdvancedBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("initiatorLevel", keywd));
							tierAdvancedBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("responderLevel", keywd));
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
									
									if(null != clientVendor.getOrgLocationId() && clientVendor.getOrgLocationId().longValue() == loginOrgLocation.getId().longValue()){
										
									    StringBuilder website=new StringBuilder();
									    clientVendor.setWebsite(website.append("www.").append(clientVendor.getResponderWebSite()).toString());
										clientVendor.setOrgName(clientVendor.getResponderOrgName());
										clientVendor.setCountry(clientVendor.getResponderCountry());
										clientVendor.setState(clientVendor.getResponderState());
										clientVendor.setCity(clientVendor.getResponderCity());
										
										if(clientVendor.getInitiatorLevel()!=null){
											clientVendor.setInitiatorLevel(clientVendor.getInitiatorLevel());
										}
									}
								  if(null != clientVendor.getResponderLocationId()  && clientVendor.getResponderLocationId().longValue() == loginOrgLocation.getId().longValue()){
									 
									  StringBuilder website=new StringBuilder();
									  clientVendor.setWebsite(website.append("www.").append(clientVendor.getInitiatorWebSite()).toString());
									  clientVendor.setOrgName(clientVendor.getInitiatorOrgName());
									  clientVendor.setCountry(clientVendor.getInitiatorCountry());
									  clientVendor.setState(clientVendor.getInitiatorState());
									  clientVendor.setCity(clientVendor.getInitiatorCity());
									  
									  if(clientVendor.getResponderLevel()!=null){
										  clientVendor.setInitiatorLevel(clientVendor.getResponderLevel());
									  }
									}
								  if(!responderType.isEmpty() &&  Constants.VENDOR.equalsIgnoreCase(responderType)){
									  if(!clientVendor.getCreatedBy().isEmpty()){
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
			    matchQueryBuilderList.add(QueryBuilders.matchQuery("initiatorLevel", keywd));
			    matchQueryBuilderList.add(QueryBuilders.matchQuery("responderLevel", keywd));
				matchQueryBuilderList.add(QueryBuilders.matchQuery("createdBy", keywd));
				
				matchQueryBuilderList.add(QueryBuilders.matchQuery("responderCountry", keywd));
				matchQueryBuilderList.add(QueryBuilders.matchQuery("responderState", keywd));
				matchQueryBuilderList.add(QueryBuilders.matchQuery("responderCity", keywd));
				matchQueryBuilderList.add(QueryBuilders.matchQuery("initiatorCountry", keywd));
				matchQueryBuilderList.add(QueryBuilders.matchQuery("initiatorState", keywd));
				matchQueryBuilderList.add(QueryBuilders.matchQuery("initiatorCity", keywd));
				
				matchQueryBuilderList.add(QueryBuilders.matchQuery("responderWebSite", keywd));
				matchQueryBuilderList.add(QueryBuilders.matchQuery("initiatorWebSite", keywd));
				
				return matchQueryBuilderList; 
		 }
		 
		 private ArrayList<MatchQueryBuilder> getMatchQueryBuilderListForContacts(String keywd){
			    ArrayList<MatchQueryBuilder> matchQueryBuilderListForContacts = new ArrayList<MatchQueryBuilder>();
			   
			    //Contact  Search 
			    matchQueryBuilderListForContacts.add(QueryBuilders.matchQuery("ownerUserFirstName", keywd));
			    matchQueryBuilderListForContacts.add(QueryBuilders.matchQuery("ownerUserLastName", keywd));
			    matchQueryBuilderListForContacts.add(QueryBuilders.matchQuery("spocUserFirstName", keywd));
			    matchQueryBuilderListForContacts.add(QueryBuilders.matchQuery("spocUserLastName", keywd));
			    matchQueryBuilderListForContacts.add(QueryBuilders.matchQuery("spocUserPhNo", keywd));
			    matchQueryBuilderListForContacts.add(QueryBuilders.matchQuery("ownerUserPhNo", keywd));
			    matchQueryBuilderListForContacts.add(QueryBuilders.matchQuery("spocUserEmail", keywd));
			    matchQueryBuilderListForContacts.add(QueryBuilders.matchQuery("ownerUserEmail", keywd));
			    matchQueryBuilderListForContacts.add(QueryBuilders.matchQuery("responderOrgName", keywd));
			    matchQueryBuilderListForContacts.add(QueryBuilders.matchQuery("spocStatus", keywd));
			    matchQueryBuilderListForContacts.add(QueryBuilders.matchQuery("invitationStatus", keywd));
			    
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
				 
				// boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", loginOrgLocation.getId()));
				

				 if("Vendor".equalsIgnoreCase(responderType)){
					 responderType = "Client";
				 }else if("Client".equalsIgnoreCase(responderType)){
					 responderType = "Vendor"; 
				 }
				 
				 BoolQueryBuilder combinedQueryBuilder = QueryBuilders.boolQuery();
				 
				 BoolQueryBuilder initiatorQueryBuilder = QueryBuilders.boolQuery();
				 initiatorQueryBuilder.must(QueryBuilders.matchQuery("initiatorLocationId", loginOrgLocation.getId()));
				 initiatorQueryBuilder.must(QueryBuilders.matchQuery("initiatorType", responderType));
				 
				 
				 BoolQueryBuilder responderQueryBuilder = QueryBuilders.boolQuery();
				 responderQueryBuilder.must(QueryBuilders.matchQuery("responderLocationId", loginOrgLocation.getId()));
				 responderQueryBuilder.must(QueryBuilders.matchQuery("responderType", responderType));
				 
				 combinedQueryBuilder.should(initiatorQueryBuilder);
				 combinedQueryBuilder.should(responderQueryBuilder);
				 
				 boolQueryBuilder.must(combinedQueryBuilder);
				 
				 //  this condition is if Login user is Owner or SpocUserId
				 if(!isadmin){
									 
							 BoolQueryBuilder ownerLoginQueryBuilder = QueryBuilders.boolQuery();
							 BoolQueryBuilder spockQueryBuilder = QueryBuilders.boolQuery();
							 BoolQueryBuilder combinationQueryBuilder = QueryBuilders.boolQuery();
							 
							 ownerLoginQueryBuilder.must(QueryBuilders.matchQuery("ownerUserId", loginUser.getId()));
							 spockQueryBuilder.must(QueryBuilders.matchQuery("spocUserId", loginUser.getId()));
							 
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
						ArrayList<MatchQueryBuilder> matchQueryBuilderList =getMatchQueryBuilderListForContacts(keywd);
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
							ArrayList<MatchQueryBuilder> matchQueryBuilderList =getMatchQueryBuilderListForContacts(keywd);
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
							ArrayList<MatchQueryBuilder> matchQueryBuilderList =getMatchQueryBuilderListForContacts(keywd);
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
							contactNameBoolQueryBuilder.should(QueryBuilders.matchQuery("spocUserFirstName", keywd));
							contactNameBoolQueryBuilder.should(QueryBuilders.matchQuery("spocUserLastName", keywd));
						}
						boolQueryBuilder.must(contactNameBoolQueryBuilder);
					}
					
					
					//Spoc Email
					ArrayList<String> contactEmailNameArray = getSearchKeywordsFromChosenSelectField(jsonObj, "emailAdvanced");
					if(contactEmailNameArray != null && !contactEmailNameArray.isEmpty()){
						BoolQueryBuilder contactEmailBoolQueryBuilder = QueryBuilders.boolQuery();
						for(String keywd : contactEmailNameArray){
							contactEmailBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("spocUserEmail", keywd));
							contactEmailBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("ownerUserEmail", keywd));
						}
						boolQueryBuilder.must(contactEmailBoolQueryBuilder);
					}
					
					//Spoc PhoneNumber
					ArrayList<String> phoneNumberNameArray = getSearchKeywordsFromChosenSelectField(jsonObj, "phoneNumberAdvanced");
					if(phoneNumberNameArray != null && !phoneNumberNameArray.isEmpty()){
						BoolQueryBuilder phoneNumberBoolQueryBuilder = QueryBuilders.boolQuery();
						for(String keywd : phoneNumberNameArray){
							phoneNumberBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("spocUserPhNo", keywd));
							phoneNumberBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("ownerUserPhNo", keywd));
						}
						boolQueryBuilder.must(phoneNumberBoolQueryBuilder);
					}
					
					//vendor
					ArrayList<String> orgAdvancedArray = getSearchKeywordsFromChosenSelectField(jsonObj, "orgAdvanced");
					if(orgAdvancedArray != null && !orgAdvancedArray.isEmpty()){
						BoolQueryBuilder orgAdvancedBoolQueryBuilder = QueryBuilders.boolQuery();
						for(String keywd : orgAdvancedArray){
							orgAdvancedBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("responderOrgName", keywd));
						}
						boolQueryBuilder.must(orgAdvancedBoolQueryBuilder);
					}
					
					//Spoc ownerAdvanced
					ArrayList<String> ownerAdvancedNameArray = getSearchKeywordsFromChosenSelectField(jsonObj, "ownerAdvanced");
					if(ownerAdvancedNameArray != null && !ownerAdvancedNameArray.isEmpty()){
						BoolQueryBuilder ownerAdvancedBoolQueryBuilder = QueryBuilders.boolQuery();
						for(String keywd : ownerAdvancedNameArray){
							ownerAdvancedBoolQueryBuilder.should(QueryBuilders.matchQuery("ownerUserFirstName", keywd));
							ownerAdvancedBoolQueryBuilder.should(QueryBuilders.matchQuery("ownerUserLastName", keywd));
						}
						boolQueryBuilder.must(ownerAdvancedBoolQueryBuilder);
					}
					
					//Spoc Status
					ArrayList<String> statusAdvancedArray = getSearchKeywordsFromChosenSelectField(jsonObj, "statusAdvanced");
					if(statusAdvancedArray != null && !statusAdvancedArray.isEmpty()){
						BoolQueryBuilder statusAdvancedBoolQueryBuilder = QueryBuilders.boolQuery();
						for(String keywd : statusAdvancedArray){
							statusAdvancedBoolQueryBuilder.should(QueryBuilders.matchQuery("spocStatus", "*"+keywd+"*"));
						
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
							
						//loginOrgLocation
						
						if(clientVendorContactsIndex.getSpocUserId() == loginUser.getId().longValue() || clientVendorContactsIndex.getOwnerUserId() == loginUser.getId().longValue()){
							//if login user is spoc
							if(clientVendorContactsIndex.getSpocUserId() !=null && clientVendorContactsIndex.getSpocUserId() == loginUser.getId().longValue()){
								
								if(clientVendorContactsIndex.getOwnerUserId() != null && clientVendorContactsIndex.getOwnerUserFirstName() != "" && clientVendorContactsIndex.getOwnerUserLastName() != null && clientVendorContactsIndex.getOwnerUserLastName() != "" ){
									StringBuilder spocNameSB = new StringBuilder();
									clientVendorContactsIndex.setSpocName(spocNameSB.append(clientVendorContactsIndex.getOwnerUserFirstName()).append(" ").append(clientVendorContactsIndex.getOwnerUserLastName()).toString());
								}else{
									clientVendorContactsIndex.setSpocName(clientVendorContactsIndex.getOwnerUserFirstName());
								}
								if(clientVendorContactsIndex.getSpocUserFirstName() != null && clientVendorContactsIndex.getSpocUserFirstName() != "" && clientVendorContactsIndex.getSpocUserLastName() != null && clientVendorContactsIndex.getSpocUserLastName() != "" ){
									StringBuilder ownerNameSB = new StringBuilder();
									clientVendorContactsIndex.setOwnerNames(ownerNameSB.append(clientVendorContactsIndex.getSpocUserFirstName()).append(" ").append(clientVendorContactsIndex.getSpocUserLastName()).toString());
								}else{
									clientVendorContactsIndex.setOwnerNames(clientVendorContactsIndex.getSpocUserFirstName());
								}
								clientVendorContactsIndex.setSpocEmail(clientVendorContactsIndex.getOwnerUserEmail());
								clientVendorContactsIndex.setSpocMobilePhone(clientVendorContactsIndex.getOwnerUserPhNo());
			    				 //orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
			    				 //orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
								
								 clientVendorContactsIndex.setTempOwnerId(clientVendorContactsIndex.getSpocUserId());
			    				 clientVendorContactsIndex.setUserId(clientVendorContactsIndex.getOwnerUserId());
								
							}
							//if login user is Owner
							else if(clientVendorContactsIndex.getOwnerUserId() != null && clientVendorContactsIndex.getOwnerUserId() == loginUser.getId().longValue()){
								if(clientVendorContactsIndex.getSpocUserFirstName() != null && clientVendorContactsIndex.getSpocUserFirstName() != "" && clientVendorContactsIndex.getSpocUserLastName() != null && clientVendorContactsIndex.getSpocUserLastName() != "" ){
									StringBuilder spocNameSB1 = new StringBuilder();
									clientVendorContactsIndex.setSpocName(spocNameSB1.append(clientVendorContactsIndex.getSpocUserFirstName()).append(" ").append(clientVendorContactsIndex.getSpocUserLastName()).toString());
								}else{
									clientVendorContactsIndex.setSpocName(clientVendorContactsIndex.getSpocUserFirstName());
								}
								if(clientVendorContactsIndex.getOwnerUserId() != null && clientVendorContactsIndex.getOwnerUserFirstName() != "" && clientVendorContactsIndex.getOwnerUserLastName() != null && clientVendorContactsIndex.getOwnerUserLastName() != "" ){
									StringBuilder ownerNameSB1 = new StringBuilder();
									clientVendorContactsIndex.setOwnerNames(ownerNameSB1.append(clientVendorContactsIndex.getOwnerUserFirstName()).append(" ").append(clientVendorContactsIndex.getOwnerUserLastName()).toString());
								}else{
									clientVendorContactsIndex.setOwnerNames(clientVendorContactsIndex.getOwnerUserFirstName());
								}
								clientVendorContactsIndex.setSpocEmail(clientVendorContactsIndex.getSpocUserEmail());
								clientVendorContactsIndex.setSpocMobilePhone(clientVendorContactsIndex.getSpocUserPhNo());
									 //orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
									 //orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
								
								 clientVendorContactsIndex.setTempOwnerId(clientVendorContactsIndex.getOwnerUserId());
			    				 clientVendorContactsIndex.setUserId(clientVendorContactsIndex.getSpocUserId());
								
								}
								
							 
						}else{
							if(clientVendorContactsIndex.getInitiatorLocationId() != null && clientVendorContactsIndex.getInitiatorLocationId() == loginOrgLocation.getId().longValue() ){
								if(clientVendorContactsIndex.getSpocUserFirstName() != null && clientVendorContactsIndex.getSpocUserFirstName() != "" && clientVendorContactsIndex.getSpocUserLastName() != null && clientVendorContactsIndex.getSpocUserLastName() != "" ){
									StringBuilder spocNameSB2 = new StringBuilder();
									clientVendorContactsIndex.setSpocName(spocNameSB2.append(clientVendorContactsIndex.getSpocUserFirstName()).append(" ").append(clientVendorContactsIndex.getSpocUserLastName()).toString());
								}else{
									clientVendorContactsIndex.setSpocName(clientVendorContactsIndex.getSpocUserFirstName());
								}
								
								if(clientVendorContactsIndex.getOwnerUserFirstName() != null && clientVendorContactsIndex.getOwnerUserFirstName() != "" && clientVendorContactsIndex.getOwnerUserLastName() != null && clientVendorContactsIndex.getOwnerUserLastName() != "" ){
									StringBuilder ownerNameSB2 = new StringBuilder();
									clientVendorContactsIndex.setOwnerNames(ownerNameSB2.append(clientVendorContactsIndex.getOwnerUserFirstName()).append(" ").append(clientVendorContactsIndex.getOwnerUserLastName()).toString());
								}else{
									clientVendorContactsIndex.setOwnerNames(clientVendorContactsIndex.getOwnerUserFirstName());
								}
								clientVendorContactsIndex.setSpocEmail(clientVendorContactsIndex.getSpocUserEmail());
								clientVendorContactsIndex.setSpocMobilePhone(clientVendorContactsIndex.getSpocUserPhNo());
								 //orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
								 //orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
								

								 clientVendorContactsIndex.setTempOwnerId(clientVendorContactsIndex.getOwnerUserId());
			    				 clientVendorContactsIndex.setUserId(clientVendorContactsIndex.getSpocUserId());
								
							 }
							if(clientVendorContactsIndex.getResponderLocationId() !=null && clientVendorContactsIndex.getResponderLocationId() == loginOrgLocation.getId().longValue()){
								if(clientVendorContactsIndex.getOwnerUserFirstName() != null && clientVendorContactsIndex.getOwnerUserFirstName() != "" && clientVendorContactsIndex.getOwnerUserLastName() != null && clientVendorContactsIndex.getOwnerUserLastName() != "" ){
									StringBuilder spocNameSB3 = new StringBuilder();
									clientVendorContactsIndex.setSpocName(spocNameSB3.append(clientVendorContactsIndex.getOwnerUserFirstName()).append(" ").append(clientVendorContactsIndex.getOwnerUserLastName()).toString());
								}else{
									clientVendorContactsIndex.setSpocName(clientVendorContactsIndex.getOwnerUserFirstName());
								}
								
								if(clientVendorContactsIndex.getSpocUserFirstName() != null && clientVendorContactsIndex.getSpocUserFirstName() != "" && clientVendorContactsIndex.getSpocUserLastName() != null && clientVendorContactsIndex.getSpocUserLastName() != "" ){
									StringBuilder ownerNameSB3 = new StringBuilder();
									clientVendorContactsIndex.setOwnerNames(ownerNameSB3.append(clientVendorContactsIndex.getSpocUserFirstName()).append(" ").append(clientVendorContactsIndex.getSpocUserLastName()).toString());
								}else{
									clientVendorContactsIndex.setOwnerNames(clientVendorContactsIndex.getSpocUserFirstName());
								}
								clientVendorContactsIndex.setSpocEmail(clientVendorContactsIndex.getOwnerUserEmail());
								clientVendorContactsIndex.setSpocMobilePhone(clientVendorContactsIndex.getOwnerUserPhNo());
							 //orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
							 //orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
								

								 clientVendorContactsIndex.setTempOwnerId(clientVendorContactsIndex.getSpocUserId());
			    				 clientVendorContactsIndex.setUserId(clientVendorContactsIndex.getOwnerUserId());
								
							}
						}
						if(clientVendorContactsIndex.getResponderLocationId() !=null && clientVendorContactsIndex.getResponderLocationId() == loginOrgLocation.getId().longValue()){
							clientVendorContactsIndex.setSpocLocationId(clientVendorContactsIndex.getInitiatorLocationId());
							clientVendorContactsIndex.setOrgName(clientVendorContactsIndex.getInitiatorOrgName());
						}
						if(clientVendorContactsIndex.getInitiatorLocationId() !=null && clientVendorContactsIndex.getInitiatorLocationId() == loginOrgLocation.getId().longValue() ){
							clientVendorContactsIndex.setSpocLocationId(clientVendorContactsIndex.getResponderLocationId());
							clientVendorContactsIndex.setOrgName(clientVendorContactsIndex.getResponderOrgName());
						}
						
						//clientVendorContactsIndex.setOwnerUserId(orgSpoc.getOwnerUser().getId().toString());
						//clientVendorContactsIndex.setSpocUserId(orgSpoc.getSpocUser().getId());
						//clientVendorContactsIndex.setOrgRelationId(orgSpoc.getOrgRelations().getId());
						
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
						
						if(null != clientVendor.getOrgLocationId() && clientVendor.getOrgLocationId().longValue() == loginOrgLocation.getId().longValue()){
							
							StringBuilder website=new StringBuilder();
							clientVendor.setWebsite(website.append("www.").append(clientVendor.getResponderWebSite()).toString());
							clientVendor.setOrgName(clientVendor.getResponderOrgName());
							clientVendor.setCountry(clientVendor.getResponderCountry());
							clientVendor.setState(clientVendor.getResponderState());
							clientVendor.setCity(clientVendor.getResponderCity());
							if(clientVendor.getInitiatorLevel()!=null){
								clientVendor.setInitiatorLevel(clientVendor.getInitiatorLevel());
							}
						}
					  if(null != clientVendor.getResponderLocationId()  && clientVendor.getResponderLocationId().longValue() == loginOrgLocation.getId().longValue()){
						 
						  StringBuilder website=new StringBuilder();
						  clientVendor.setWebsite(website.append("www.").append(clientVendor.getInitiatorWebSite()).toString());
						  clientVendor.setOrgName(clientVendor.getInitiatorOrgName());
						  clientVendor.setCountry(clientVendor.getInitiatorCountry());
						  clientVendor.setState(clientVendor.getInitiatorState());
						  clientVendor.setCity(clientVendor.getInitiatorCity());
						  
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
				

				 if("Vendor".equalsIgnoreCase(responderType)){
					 responderType = "Client";
				 }else if("Client".equalsIgnoreCase(responderType)){
					 responderType = "Vendor"; 
				 }
				 
				 BoolQueryBuilder combinedQueryBuilder = QueryBuilders.boolQuery();
				 
				 BoolQueryBuilder initiatorQueryBuilder = QueryBuilders.boolQuery();
				 initiatorQueryBuilder.must(QueryBuilders.matchQuery("initiatorLocationId", loginOrgLocation.getId()));
				 initiatorQueryBuilder.must(QueryBuilders.matchQuery("initiatorType", responderType));
				 
				 
				 BoolQueryBuilder responderQueryBuilder = QueryBuilders.boolQuery();
				 responderQueryBuilder.must(QueryBuilders.matchQuery("responderLocationId", loginOrgLocation.getId()));
				 responderQueryBuilder.must(QueryBuilders.matchQuery("responderType", responderType));
				 
				 combinedQueryBuilder.should(initiatorQueryBuilder);
				 combinedQueryBuilder.should(responderQueryBuilder);
				 
				 boolQueryBuilder.must(combinedQueryBuilder);
				 
				 //  this condition is if Login user is Owner or SpocUserId
				 if(!isadmin){
									 
							 BoolQueryBuilder ownerLoginQueryBuilder = QueryBuilders.boolQuery();
							 BoolQueryBuilder spockQueryBuilder = QueryBuilders.boolQuery();
							 BoolQueryBuilder combinationQueryBuilder = QueryBuilders.boolQuery();
							 
							 ownerLoginQueryBuilder.must(QueryBuilders.matchQuery("ownerUserId", loginUser.getId()));
							 spockQueryBuilder.must(QueryBuilders.matchQuery("spocUserId", loginUser.getId()));
							 
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
							
							//loginOrgLocation
							
							if(clientVendorContactsIndex.getSpocUserId() == loginUser.getId().longValue() || clientVendorContactsIndex.getOwnerUserId() == loginUser.getId().longValue()){
								//if login user is spoc
								if(clientVendorContactsIndex.getSpocUserId() !=null && clientVendorContactsIndex.getSpocUserId() == loginUser.getId().longValue()){
									
									if(clientVendorContactsIndex.getOwnerUserId() != null && clientVendorContactsIndex.getOwnerUserFirstName() != "" && clientVendorContactsIndex.getOwnerUserLastName() != null && clientVendorContactsIndex.getOwnerUserLastName() != "" ){
										StringBuilder spocNameSB = new StringBuilder();
										clientVendorContactsIndex.setSpocName(spocNameSB.append(clientVendorContactsIndex.getOwnerUserFirstName()).append(" ").append(clientVendorContactsIndex.getOwnerUserLastName()).toString());
									}else{
										clientVendorContactsIndex.setSpocName(clientVendorContactsIndex.getOwnerUserFirstName());
									}
									if(clientVendorContactsIndex.getSpocUserFirstName() != null && clientVendorContactsIndex.getSpocUserFirstName() != "" && clientVendorContactsIndex.getSpocUserLastName() != null && clientVendorContactsIndex.getSpocUserLastName() != "" ){
										StringBuilder ownerNameSB = new StringBuilder();
										clientVendorContactsIndex.setOwnerNames(ownerNameSB.append(clientVendorContactsIndex.getSpocUserFirstName()).append(" ").append(clientVendorContactsIndex.getSpocUserLastName()).toString());
									}else{
										clientVendorContactsIndex.setOwnerNames(clientVendorContactsIndex.getSpocUserFirstName());
									}
									clientVendorContactsIndex.setSpocEmail(clientVendorContactsIndex.getOwnerUserEmail());
									clientVendorContactsIndex.setSpocMobilePhone(clientVendorContactsIndex.getOwnerUserPhNo());
				    				 //orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
				    				 //orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
									
									 clientVendorContactsIndex.setTempOwnerId(clientVendorContactsIndex.getSpocUserId());
				    				 clientVendorContactsIndex.setUserId(clientVendorContactsIndex.getOwnerUserId());
									
								}
								//if login user is Owner
								else if(clientVendorContactsIndex.getOwnerUserId() != null && clientVendorContactsIndex.getOwnerUserId() == loginUser.getId().longValue()){
									if(clientVendorContactsIndex.getSpocUserFirstName() != null && clientVendorContactsIndex.getSpocUserFirstName() != "" && clientVendorContactsIndex.getSpocUserLastName() != null && clientVendorContactsIndex.getSpocUserLastName() != "" ){
										StringBuilder spocNameSB1 = new StringBuilder();
										clientVendorContactsIndex.setSpocName(spocNameSB1.append(clientVendorContactsIndex.getSpocUserFirstName()).append(" ").append(clientVendorContactsIndex.getSpocUserLastName()).toString());
									}else{
										clientVendorContactsIndex.setSpocName(clientVendorContactsIndex.getSpocUserFirstName());
									}
									if(clientVendorContactsIndex.getOwnerUserId() != null && clientVendorContactsIndex.getOwnerUserFirstName() != "" && clientVendorContactsIndex.getOwnerUserLastName() != null && clientVendorContactsIndex.getOwnerUserLastName() != "" ){
										StringBuilder ownerNameSB1 = new StringBuilder();
										clientVendorContactsIndex.setOwnerNames(ownerNameSB1.append(clientVendorContactsIndex.getOwnerUserFirstName()).append(" ").append(clientVendorContactsIndex.getOwnerUserLastName()).toString());
									}else{
										clientVendorContactsIndex.setOwnerNames(clientVendorContactsIndex.getOwnerUserFirstName());
									}
									clientVendorContactsIndex.setSpocEmail(clientVendorContactsIndex.getSpocUserEmail());
									clientVendorContactsIndex.setSpocMobilePhone(clientVendorContactsIndex.getSpocUserPhNo());
										 //orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
										 //orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
									
									 clientVendorContactsIndex.setTempOwnerId(clientVendorContactsIndex.getOwnerUserId());
				    				 clientVendorContactsIndex.setUserId(clientVendorContactsIndex.getSpocUserId());
									
									}
									
								 
							}else{
								if(clientVendorContactsIndex.getInitiatorLocationId() != null && clientVendorContactsIndex.getInitiatorLocationId() == loginOrgLocation.getId().longValue() ){
									if(clientVendorContactsIndex.getSpocUserFirstName() != null && clientVendorContactsIndex.getSpocUserFirstName() != "" && clientVendorContactsIndex.getSpocUserLastName() != null && clientVendorContactsIndex.getSpocUserLastName() != "" ){
										StringBuilder spocNameSB2 = new StringBuilder();
										clientVendorContactsIndex.setSpocName(spocNameSB2.append(clientVendorContactsIndex.getSpocUserFirstName()).append(" ").append(clientVendorContactsIndex.getSpocUserLastName()).toString());
									}else{
										clientVendorContactsIndex.setSpocName(clientVendorContactsIndex.getSpocUserFirstName());
									}
									
									if(clientVendorContactsIndex.getOwnerUserFirstName() != null && clientVendorContactsIndex.getOwnerUserFirstName() != "" && clientVendorContactsIndex.getOwnerUserLastName() != null && clientVendorContactsIndex.getOwnerUserLastName() != "" ){
										StringBuilder ownerNameSB2 = new StringBuilder();
										clientVendorContactsIndex.setOwnerNames(ownerNameSB2.append(clientVendorContactsIndex.getOwnerUserFirstName()).append(" ").append(clientVendorContactsIndex.getOwnerUserLastName()).toString());
									}else{
										clientVendorContactsIndex.setOwnerNames(clientVendorContactsIndex.getOwnerUserFirstName());
									}
									clientVendorContactsIndex.setSpocEmail(clientVendorContactsIndex.getSpocUserEmail());
									clientVendorContactsIndex.setSpocMobilePhone(clientVendorContactsIndex.getSpocUserPhNo());
									 //orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
									 //orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
									

									 clientVendorContactsIndex.setTempOwnerId(clientVendorContactsIndex.getOwnerUserId());
				    				 clientVendorContactsIndex.setUserId(clientVendorContactsIndex.getSpocUserId());
									
								 }
								if(clientVendorContactsIndex.getResponderLocationId() !=null && clientVendorContactsIndex.getResponderLocationId() == loginOrgLocation.getId().longValue()){
									if(clientVendorContactsIndex.getOwnerUserFirstName() != null && clientVendorContactsIndex.getOwnerUserFirstName() != "" && clientVendorContactsIndex.getOwnerUserLastName() != null && clientVendorContactsIndex.getOwnerUserLastName() != "" ){
										StringBuilder spocNameSB3 = new StringBuilder();
										clientVendorContactsIndex.setSpocName(spocNameSB3.append(clientVendorContactsIndex.getOwnerUserFirstName()).append(" ").append(clientVendorContactsIndex.getOwnerUserLastName()).toString());
									}else{
										clientVendorContactsIndex.setSpocName(clientVendorContactsIndex.getOwnerUserFirstName());
									}
									
									if(clientVendorContactsIndex.getSpocUserFirstName() != null && clientVendorContactsIndex.getSpocUserFirstName() != "" && clientVendorContactsIndex.getSpocUserLastName() != null && clientVendorContactsIndex.getSpocUserLastName() != "" ){
										StringBuilder ownerNameSB3 = new StringBuilder();
										clientVendorContactsIndex.setOwnerNames(ownerNameSB3.append(clientVendorContactsIndex.getSpocUserFirstName()).append(" ").append(clientVendorContactsIndex.getSpocUserLastName()).toString());
									}else{
										clientVendorContactsIndex.setOwnerNames(clientVendorContactsIndex.getSpocUserFirstName());
									}
									clientVendorContactsIndex.setSpocEmail(clientVendorContactsIndex.getOwnerUserEmail());
									clientVendorContactsIndex.setSpocMobilePhone(clientVendorContactsIndex.getOwnerUserPhNo());
								 //orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
								 //orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
									

									 clientVendorContactsIndex.setTempOwnerId(clientVendorContactsIndex.getSpocUserId());
				    				 clientVendorContactsIndex.setUserId(clientVendorContactsIndex.getOwnerUserId());
									
								}
							}
							if(clientVendorContactsIndex.getResponderLocationId() !=null && clientVendorContactsIndex.getResponderLocationId() == loginOrgLocation.getId().longValue()){
								clientVendorContactsIndex.setSpocLocationId(clientVendorContactsIndex.getInitiatorLocationId());
								clientVendorContactsIndex.setOrgName(clientVendorContactsIndex.getInitiatorOrgName());
							}
							if(clientVendorContactsIndex.getInitiatorLocationId() !=null && clientVendorContactsIndex.getInitiatorLocationId() == loginOrgLocation.getId().longValue() ){
								clientVendorContactsIndex.setSpocLocationId(clientVendorContactsIndex.getResponderLocationId());
								clientVendorContactsIndex.setOrgName(clientVendorContactsIndex.getResponderOrgName());
							}
							
							//clientVendorContactsIndex.setOwnerUserId(orgSpoc.getOwnerUser().getId().toString());
							//clientVendorContactsIndex.setSpocUserId(orgSpoc.getSpocUser().getId());
							//clientVendorContactsIndex.setOrgRelationId(orgSpoc.getOrgRelations().getId());
							
						
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
}