package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
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
	OrgRelationsService orgRelationsService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	UserService userService;
	
	
	@Autowired
	private SourceleadPasswordEncoder sourceleadPasswordEncoder;
	
	/**
	 * Handles Vendor Contacts 
	 * @param json
	 * @param request
	 * @return Json
	 * @throws Exception
	 */
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
		
		if(jsonObj.has("owner")){
			String ownersId = jsonObj.getString("owner");
			ownersId = ownersId.replaceAll("\\[", "").replaceAll("\\]","");
			String[] owners = ownersId.split(",");
			for(int i=0 ;i<owners.length;i++ ){
				User ownerUser = userService.getUserById(Long.parseLong(owners[i].replace("\"", "")));
				OrgSpoc orgSpoc = orgSpocService.validateSpacEmailIdByOwnerUserIdAndOrgRelationId(jsonObj.getString("email"), Long.parseLong(orgRelationId), ownerUser.getId());
			     if(orgSpoc == null){
			    	orgSpocService.addVendorContacts(jsonObj, user, orgLocations, orgRelationId, clientLocationId,responderType);
					SourceLeadSessionManager.setSessionAttribute(request, "clientCreateMsg","Successfully you have created a client");
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
	 
	@RequestMapping(value = "/getVendorFilterLists", method = RequestMethod.POST)
	public @ResponseBody String getVendorFilterLists(@RequestParam(value="values", required=false) String status,HttpServletRequest request) throws Exception {

	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    String resultJson;
	    
	    List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllVendorsFilterByOrgLocationId(orgLocations.getId(),status);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    //List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocations.getId());
 
			
		    for (OrgRelations orgRelation : vandorsListByOrgLocationId) {
				
				if(orgRelation.getLocationId().getId() == orgLocations.getId()){
					orgRelation.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
					orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrg().getOrgName());
					orgRelation.setCountry(orgRelation.getResponderLocationId().getCountry());
					orgRelation.setState(orgRelation.getResponderLocationId().getState());
					orgRelation.setCity(orgRelation.getResponderLocationId().getCity());
					orgRelation.setVendorlocationId(orgRelation.getResponderLocationId().getId());
					//orgRelation.setNoOfContatcs(noOfContatcs); 
					List<OrgSpoc>  contactsList = orgSpocService.getAllContactsbyOrgLocationIdAndUserIdForRequirement(orgLocations.getId(),user.getId(),"Client");
					orgRelation.setNoOfContatcs(contactsList.size()); 
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
				
				if(orgRelation.getLocationId().getId() == orgLocations.getId()){
					orgRelation.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
					orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrg().getOrgName());
					orgRelation.setCountry(orgRelation.getResponderLocationId().getCountry());
					orgRelation.setState(orgRelation.getResponderLocationId().getState());
					orgRelation.setCity(orgRelation.getResponderLocationId().getCity());
					orgRelation.setVendorlocationId(orgRelation.getResponderLocationId().getId());
					List<OrgSpoc>  contactsList = orgSpocService.getAllContactsbyOrgLocationIdAndUserIdForRequirement(orgLocations.getId(),user.getId(),"Client");
					orgRelation.setNoOfContatcs(contactsList.size()); 
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
				if(orgSpoc.getSpocUser().getId() == loginUser.getId() || orgSpoc.getOwnerUser().getId() == loginUser.getId()){
					//if login user is spoc
					if(orgSpoc.getSpocUser().getId() == loginUser.getId()){
	    				 orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
	    				 orgSpoc.setSpocName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
	    				 orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
	    				 orgSpoc.setOwnerName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
	    				// orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
	    				 orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
	    				 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
					}
					//if login user is Owner
					else if(orgSpoc.getOwnerUser().getId() == loginUser.getId()){
							 orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
							 orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
							 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
							 orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
							 //orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
							 orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
							 orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
						}
						
					 
				}else{
					if(orgSpoc.getInitiatorLocationId().getId() == orgLocations.getId() ){
						 orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
						 orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
						 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
						 orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
						 orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
						 orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
					 }
					if(orgSpoc.getResponderLocationId().getId() == orgLocations.getId()){
					 orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
					 orgSpoc.setSpocName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
					 orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
					 orgSpoc.setOwnerName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
					 orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
					 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
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
	
	   
	   // this method is used to re-send email to Spoc
	   @RequestMapping(value = "/resendSpocInvitationEmail" , method = RequestMethod.POST)
		  public @ResponseBody  String resendInvitationEmail(@RequestParam(value="spocUserId", required=false) String spocUserId, HttpServletRequest request,String responderType, Long orgRelationID) throws Exception {
			    
			    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			    Org loginOrg = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			    OrgRelations orgRelations = orgRelationsService.getRespondingPartyDetailsById(orgRelationID);
			    //get user by userId
			    User user = userService.getUserById(Long.parseLong(spocUserId));
			    
			    String password = null;
		 		if(null != user  && null != orgRelations){
		 		    //to update spoc invitation status
		 			OrgSpoc spoc = orgSpocService.getSpocUserByUserId(Long.parseLong(spocUserId));
		 			spoc.setInvitationStatus(Constants.MAIL_SENT);
		 			orgSpocService.addOrgSpoc(spoc);
		 			
		 			userService.sendEmailToClientOrVendor(user, loginUser, password, responderType, loginOrg,orgRelations.getResponderLocationId().getOrg(),orgRelations.getResponderLocationId());
		 		}
			
		 	  return "success";
		    }
	   
}