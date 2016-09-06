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
	public String saveVenodrContact(@RequestParam(value = "json", required = false) String json, @RequestParam(value="orgRelationId", required=false) String orgRelationId, HttpServletRequest request) throws Exception {

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		orgSpocService.addVendorContacts(jsonObj, user, orgLocations, orgRelationId);
		SourceLeadSessionManager.setSessionAttribute(request, "clientCreateMsg","Successfully you have created a client");
		return "vendorsList";
	}

	
	
	
	
	/**
	 * @author Laxman
	 * this is method is using Vendor List
	 * @param request
	 * @return json
	 * @throws Exception
	 */
	 
	   @RequestMapping(value = "/getVendorLists")
		public @ResponseBody String getVendorLists(HttpServletRequest request) throws Exception {

		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    String resultJson;
		    
		    List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocations.getId());
			
		    for (OrgRelations orgRelation : vandorsListByOrgLocationId) {
				
				if(orgRelation.getLocationId().getId() == orgLocations.getId()){
					orgRelation.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
					orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrg().getOrgName());
					orgRelation.setCountry(orgRelation.getResponderLocationId().getCountry());
					orgRelation.setState(orgRelation.getResponderLocationId().getState());
					orgRelation.setCity(orgRelation.getResponderLocationId().getCity());
					orgRelation.setVendorlocationId(orgRelation.getResponderLocationId().getId());
				}
				
				if(orgRelation.getResponderLocationId().getId() == orgLocations.getId()){
					orgRelation.setWebSite(orgRelation.getLocationId().getOrg().getWebSite());
					orgRelation.setOrgName(orgRelation.getLocationId().getOrg().getOrgName());
					orgRelation.setCountry(orgRelation.getLocationId().getCountry());
					orgRelation.setState(orgRelation.getLocationId().getState());
					orgRelation.setCity(orgRelation.getLocationId().getCity());
					orgRelation.setVendorlocationId(orgRelation.getLocationId().getId());
				}
				
			}
		    
		    
			 resultJson = commonUtil.toJSON(vandorsListByOrgLocationId);
			 return resultJson;
		}
	   
	   
	   
	   
	   
	   @RequestMapping(value = "/getVendorContactLists")
		public @ResponseBody String getVendorContactLists(HttpServletRequest request) throws Exception {

		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");		
		    String resultJson;
		    
		    List<OrgSpoc> vandorsContactListByOrgLocationId = orgRelationsService.getAllVendorsContactListByOrgLocationId(loginUser.getId(),orgLocations.getId(), "Client");
		
			
			for (OrgSpoc orgSpoc : vandorsContactListByOrgLocationId) {
				if(orgSpoc.getSpocUser().getId() == loginUser.getId() || orgSpoc.getOwnerUser().getId() == loginUser.getId()){
					//if login user is spoc
					if(orgSpoc.getSpocUser().getId() == loginUser.getId()){
	    				 orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
	    				 orgSpoc.setSpocName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
	    				 orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
	    				 orgSpoc.setOwnerName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
					}
					//if login user is Owner
					else if(orgSpoc.getOwnerUser().getId() == loginUser.getId()){
							 orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
							 orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
							 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
							 orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
						}
						
					 
				}else{
					if(orgSpoc.getInitiatorLocationId().getId() == orgLocations.getId() ){
						 orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
						 orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
						 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
						 orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
					 }
					if(orgSpoc.getResponderLocationId().getId() == orgLocations.getId()){
					 orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
					 orgSpoc.setSpocName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
					 orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
					 orgSpoc.setOwnerName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
					}
				}
				if(orgSpoc.getResponderLocationId().getId() == orgLocations.getId()){
					 orgSpoc.setOrgName(orgSpoc.getInitiatorLocationId().getOrg().getOrgName());
				}
				if(orgSpoc.getInitiatorLocationId().getId() == orgLocations.getId() ){
					orgSpoc.setOrgName(orgSpoc.getResponderLocationId().getOrg().getOrgName());
				}
				
			}
			
			 resultJson = commonUtil.toJSON(vandorsContactListByOrgLocationId);
			 return resultJson;
		}
	   
	   
	   // this method is used to re-send email to Spoc
	   @RequestMapping(value = "/resendSpocInvitationEmail" , method = RequestMethod.POST)
		  public @ResponseBody  String resendInvitationEmail(@RequestParam(value="spocUserId", required=false) String spocUserId, HttpServletRequest request,String responderType, Long orgRelationID) throws Exception {
			    
			    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			    Org loginOrg = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			    //get user by userId
			    User user = userService.getUserById(Long.parseLong(spocUserId));
			    
			    //generate random password
			    String password = SourceLeadRandomCodeHelper.generateRandomPassword();
		 		if(user != null){
		 			user.setPassword(sourceleadPasswordEncoder.encode(password));
		 			user.setIsMailSent(Status.Y);
		 			userService.addUser(user);
		 			
		 			//to update spoc invitation status
		 			/*OrgSpoc spoc = orgSpocService.getOrgSpocById(Long.parseLong(spocUserId));
		 			spoc.setInvitationStatus(Constants.MAIL_SENT);
		 			orgSpocService.addOrgSpoc(spoc);*/
		 			
		 			userService.sendEmailToClientOrVendor(user, loginUser, password, responderType, loginOrg);
		 		}
			
		 	  return "success";
		    }
	   
}
