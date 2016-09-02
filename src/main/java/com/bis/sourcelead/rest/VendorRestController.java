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

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.util.CommonUtil;
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
				
				orgRelation.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
				orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrg().getOrgName());
				orgRelation.setCountry(orgRelation.getResponderLocationId().getCountry());
				orgRelation.setState(orgRelation.getResponderLocationId().getState());
				orgRelation.setCity(orgRelation.getResponderLocationId().getCity());
				
				orgRelation.setLocationOrgname(orgRelation.getLocationId().getOrg().getOrgName());
				orgRelation.setLocationWebsite(orgRelation.getLocationId().getOrg().getWebSite());
				orgRelation.setLocationCountry(orgRelation.getLocationId().getCountry());
				orgRelation.setLocationState(orgRelation.getLocationId().getState());
				orgRelation.setLocationCity(orgRelation.getLocationId().getCity());
				orgRelation.setResponderId(orgRelation.getResponderLocationId().getId().toString());
				orgRelation.setLocationID(orgRelation.getLocationId().getId().toString());
				
			}
		    
			 resultJson = commonUtil.toJSON(vandorsListByOrgLocationId);
			 return resultJson;
		}
	   
	   
	   
	   
	   
	   @RequestMapping(value = "/getVendorContactLists")
		public @ResponseBody String getVendorContactLists(HttpServletRequest request) throws Exception {

		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");		
		    String resultJson;
		    
		    List<OrgSpoc> vandorsContactListByOrgLocationId = orgRelationsService.getAllVendorsContactListByOrgLocationId(loginUser.getId(),orgLocations.getId());
		
			
			for (OrgSpoc orgSpoc : vandorsContactListByOrgLocationId) {
				if(orgSpoc.getSpocUser() != null){
					orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
					orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
					orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
					orgSpoc.setOwnerUserId(orgSpoc.getOwnerUser().getId().toString());
				}
				orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
				if(orgSpoc.getOrgRelations().getLocationId().getId() == orgLocations.getId()){
					orgSpoc.setOrgName(orgSpoc.getOrgRelations().getResponderLocationId().getOrg().getOrgName());
				}
				if(orgSpoc.getOrgRelations().getResponderLocationId().getId() == orgLocations.getId()){
					orgSpoc.setOrgName(orgSpoc.getOrgRelations().getLocationId().getOrg().getOrgName());
				}
			}
			
			 resultJson = commonUtil.toJSON(vandorsContactListByOrgLocationId);
			 return resultJson;
		}
	   
	   
}
