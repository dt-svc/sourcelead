package com.bis.sourcelead.rest;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.UserService;
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
public class RequirementReleaseRestController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	RequirementService requirementService;
	
	@Autowired
	RequirementAssignToService requirementAssignToService;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	UserService userService;
	
	
	/**
	 * @author Venkat Kesav
	 * this is method is using contacts List
	 * @param request
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllContactsbyOrgLocationIdAndUserIdForRequirement")
	public @ResponseBody String getAllContactsbyOrgLocationIdAndUserIdForRequirement(String requirementId, String vendors, String status, HttpServletRequest request) throws Exception {

	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    String resultJson;
	    String[] vendorsRelationId = null;
	    if(vendors != ""){
	    	vendorsRelationId = vendors.split(",");
	    }
	    List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllVendorsFilterByOrgLocationId(orgLocations.getId(),status);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<OrgSpoc>  contactsList = null;	
		    for (OrgRelations orgRelation : vandorsListByOrgLocationId) {
		    	
		    	contactsList = orgRelationsService.getAllVendorsContactFilterListByOrgLocationId(user.getId(),orgLocations.getId(), "Client","Active",null,"Accepted");
		    	
		    	for (OrgSpoc orgSpoc : contactsList) {
		    		

					if(orgSpoc.getSpocUser().getId() == user.getId() || orgSpoc.getOwnerUser().getId() == user.getId()){
						//if login user is spoc
						if(orgSpoc.getSpocUser().getId() == user.getId()){
		    				 orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
		    				 orgSpoc.setSpocName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
		    				 orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
		    				 orgSpoc.setOwnerName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
		    				// orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
		    				 orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
		    				 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
						}
						//if login user is Owner
						else if(orgSpoc.getOwnerUser().getId() == user.getId()){
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
					/*if(orgRelation.getLocationId().getId() == orgLocations.getId()){
						contactsList = orgSpocService.getAllContactsbyOrgLocationIdAndUserIdForRequirement(orgLocations.getId(),user.getId(),"Client");
					}
					if(orgRelation.getResponderLocationId().getId() == orgLocations.getId()){
						contactsList = orgSpocService.getAllContactsbyOrgLocationIdAndUserIdForRequirement(orgLocations.getId(),user.getId(),"Vendor");
					}*/
					
					/*for (Iterator<OrgSpoc> it = contactsList.iterator(); it.hasNext(); ) {

						OrgSpoc orgSpoc = it.next();
					    if ((vendorsRelationId != null && !Arrays.asList(vendorsRelationId).contains(orgSpoc.getOrgRelations().getId().toString())) || (orgSpoc.getSpocStatus().equalsIgnoreCase("In-Active"))) {
					       it.remove();
					    }
					}*/
			}
		    if(contactsList != null){
		    	for(OrgSpoc contact : contactsList){
			    	Boolean isJobReleased = requirementService.getRequirementByParentRequirementIdAndSpocId(Long.parseLong(requirementId), contact.getId());
			    	contact.setReleasedRequirement(isJobReleased.toString());
				}
		    }
		    
			 resultJson = commonUtil.toJSON(contactsList);
			 return resultJson;
		}
	   
	
	/**
	 * @author Venkat Kesav
	 * this is method is using Vendor List
	 * @param request
	 * @return json
	 * @throws Exception
	 */
	 
	@RequestMapping(value = "/getVendorListsForReleaseRequirement")
	public @ResponseBody String getVendorListsForReleaseRequirement(String requirementId, String vendorStatus, HttpServletRequest request) throws Exception {

	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    String resultJson;
	    
	    List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllVendorsFilterByOrgLocationId(orgLocations.getId(),vendorStatus);
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
					//List<OrgSpoc>  contactsList = orgSpocService.getAllContactsbyOrgLocationIdAndUserIdForRequirement(orgLocations.getId(),user.getId(),"Client");
					List<OrgSpoc> contactsList = orgSpocService.getAllContactsbyOrgId(orgRelation.getId());
					int nonAssignedCount = 0;
					for(OrgSpoc orgSpoc : contactsList){
						Boolean isJobReleased = requirementService.getRequirementByParentRequirementIdAndSpocId(Long.parseLong(requirementId), orgSpoc.getId());
						if(isJobReleased.toString().equalsIgnoreCase("false")){
							nonAssignedCount = nonAssignedCount + 1;
						}
					}
					orgRelation.setNoOfNonReleasedContacts(nonAssignedCount);
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
					//List<OrgSpoc>  contactsList = orgSpocService.getAllContactsbyOrgLocationIdAndUserIdForRequirement(orgLocations.getId(),user.getId(),"Vendor");
					List<OrgSpoc> contactsList = orgSpocService.getAllContactsbyOrgId(orgRelation.getId());
					int nonAssignedCount = 0;
					for(OrgSpoc orgSpoc : contactsList){
						Boolean isJobReleased = requirementService.getRequirementByParentRequirementIdAndSpocId(Long.parseLong(requirementId), orgSpoc.getId());
						if(isJobReleased.toString().equalsIgnoreCase("false")){
							nonAssignedCount = nonAssignedCount + 1;
						}
					}
					orgRelation.setNoOfNonReleasedContacts(nonAssignedCount);
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
	 * Handles Vendor Contacts 
	 * @param json
	 * @param request
	 * @return Json
	 * @throws Exception
	 */
	@RequestMapping(value = "/releaseJobsToClientsOrVendors", method = RequestMethod.POST)
	@ResponseBody
	public String releaseJobsToClientsOrVendors(@RequestParam(value = "requirementId", required = false) Long requirementId,
												@RequestParam(value = "contactsIds", required = false) String contactsIds,
			                                    @RequestParam(value="vendorIds", required=false) String vendorIds,
			                                    HttpServletRequest request) throws Exception {
			
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	String releasetype = null;
			Requirement parentRequirement = requirementService.getRequirementByRequirementId(requirementId);
			String[] contacts = contactsIds.split(",");
			if(contacts != null &&  contacts.length>0 && contacts[0] != null && contacts[0] != "" ){
				for(String contact   : contacts){
						releasetype = Constants.CONTACTS;
						OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(Long.parseLong(contact));
						Requirement requirement  = new Requirement();
						releaseJobToVendorOrContacts(requirement, parentRequirement, orgSpoc,null);
						requirementService.releaseMailScenario(loginUser, requirement, orgSpoc, loginOrgLocation,releasetype,null);

				}
			}	
			
			String[] vendors = vendorIds.split(",");
			if(vendors != null &&  vendors.length>0 && vendors[0] != null && vendors[0] != ""){
				for(String vendor   : vendors){
					
					//List<OrgSpoc> orgSpocList = orgSpocService.getAllContactsbyOrgId(Long.parseLong(vendor));
					
					OrgRelations orgRelations = orgRelationsService.getOrRelationsById(Long.parseLong(vendor));
					Long locationId = null;
					if(StringUtils.equals(orgRelations.getInitiatorType(), "Vendor")){
						locationId = orgRelations.getLocationId().getId();
					}else if(StringUtils.equals(orgRelations.getInitiatorType(), "Client")){
						locationId = orgRelations.getResponderLocationId().getId();
					}
					
					List<OrgSpoc> orgSpocList = orgSpocService.getAllContactsbyOrgRelationIdAndLocationId(Long.parseLong(vendor), locationId, orgRelations.getInitiatorType());
					for(OrgSpoc orgSpoc : orgSpocList){
						
					    Boolean isJobReleased = requirementService.getRequirementByParentRequirementIdAndSpocId(requirementId, orgSpoc.getId());
					    if(isJobReleased.toString().equalsIgnoreCase("false")){
					    	Requirement requirement  = new Requirement();
					    	releasetype = Constants.VENDORSTYPE;
							releaseJobToVendorOrContacts(requirement, parentRequirement, orgSpoc, orgRelations.getInitiatorType());
							requirementService.releaseMailScenario(loginUser, requirement, orgSpoc, loginOrgLocation,releasetype,null);

					    }
					}
				}
			}
			
			return "vendorsList";
	}

	private void releaseJobToVendorOrContacts(Requirement requirement, Requirement parentRequirement, OrgSpoc orgSpoc, String initiator) throws Exception {
		
		//requirement = parentRequirement;
		
		requirement.setParentRequirement(parentRequirement);
		if(StringUtils.equalsIgnoreCase(initiator, "Vendor")){
			requirement.setOrgLocationId(orgSpoc.getInitiatorLocationId());
		}else{
			requirement.setOrgLocationId(orgSpoc.getResponderLocationId());
		}
		
		requirement.setJobTitle(parentRequirement.getJobTitle());
		requirement.setJobType(parentRequirement.getJobType());
		requirement.setJobId(parentRequirement.getJobId());
		requirement.setPositions(parentRequirement.getPositions());
		requirement.setEducation(parentRequirement.getEducation());
		requirement.setMinExp(parentRequirement.getMinExp());
		requirement.setMaxExp(parentRequirement.getMaxExp());
		requirement.setCurrencyCode(parentRequirement.getCurrencyCode());
		requirement.setRate(parentRequirement.getRate());
		requirement.setRateType(parentRequirement.getRateType());
		requirement.setCountry(parentRequirement.getCountry());
		requirement.setState(parentRequirement.getState());
		requirement.setCity(parentRequirement.getCity());
		requirement.setSkills(parentRequirement.getSkills());
		requirement.setDescription(parentRequirement.getDescription());
		requirement.setDocFile(parentRequirement.getDocFile());
		requirement.setFileContentType(parentRequirement.getFileContentType());
		requirement.setFileName(parentRequirement.getFileName());
		requirement.setIsClientJob("Y");
		
		requirement.setSubmissionTemplateJsonString(parentRequirement.getSubmissionTemplateJsonString());
		requirement.setClientSubmissionTemplateJsonString(parentRequirement.getSubmissionTemplateJsonString());
		
		if(parentRequirement.getClientSubmissionTemplateId()!=null){
			requirement.setClientSubmissionTemplateId(parentRequirement.getClientSubmissionTemplateId());
		}
		if(parentRequirement.getSubmissionTemplateId()!=null){
			requirement.setSubmissionTemplateId(parentRequirement.getSubmissionTemplateId());
		}
		
		
		requirement.setStatus(Constants.TEAM_ACTIVE);
		requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.NEW);
		
		if(orgSpoc != null){
			requirement.setSpocId(orgSpoc);
			requirement.setClientId(orgSpoc.getOrgRelations());
			if(StringUtils.equalsIgnoreCase(initiator, "Vendor")){
				requirement.setUpdatedBy(orgSpoc.getOwnerUser().getUserCode());
				requirement.setCreatedBy(orgSpoc.getOwnerUser().getUserCode());
				requirement.setManagerUserId(orgSpoc.getOwnerUser());
			}else{
				requirement.setUpdatedBy(orgSpoc.getSpocUser().getUserCode());
				requirement.setCreatedBy(orgSpoc.getSpocUser().getUserCode());
				requirement.setManagerUserId(orgSpoc.getSpocUser());
			}
		}
		
		requirement.setCreatedDate(commonUtil.currentDate());
		requirement.setUpdatedDate(commonUtil.currentDate());
		
		requirement.setId(null);
		requirementService.addRequirement(requirement);
	}
}