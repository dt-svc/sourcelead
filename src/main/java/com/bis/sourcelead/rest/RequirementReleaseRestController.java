package com.bis.sourcelead.rest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.RecommendedCustomizeScore;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RecommendedCustomizeScoreService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementService;
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
	
	@Autowired
	RecommendedCustomizeScoreService recommendedCustomizeScoreService;
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	

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
		    	
		    	contactsList = orgRelationsService.getAllVendorsContactFilterListByOrgLocationId(user.getId(),orgLocations.getId(), "Client","Active,New,Contracted",null,"");
		    	
		    	for (OrgSpoc orgSpoc : contactsList) {/*
		    		

					if(orgSpoc.getSpocUser().getId().longValue() == user.getId().longValue() || orgSpoc.getOwnerUser().getId().longValue() == user.getId().longValue()){
						//if login user is spoc
						if(orgSpoc.getSpocUser().getId() == user.getId()){
							if(orgSpoc.getOwnerUser().getFirstName() != null && orgSpoc.getOwnerUser().getFirstName() != "" && orgSpoc.getOwnerUser().getLastName() != null && orgSpoc.getOwnerUser().getLastName() != "" ){
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
		    				 orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
		    				 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
						}
						//if login user is Owner
						else if(orgSpoc.getOwnerUser().getId().longValue() == user.getId().longValue()){
							if(orgSpoc.getSpocUser().getFirstName() != null && orgSpoc.getSpocUser().getFirstName() != "" && orgSpoc.getSpocUser().getLastName() != null && orgSpoc.getSpocUser().getLastName() != "" ){
								StringBuilder spocNameSB1 = new StringBuilder();
								orgSpoc.setSpocName(spocNameSB1.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
							}else{
								orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName());
							}
							if(orgSpoc.getOwnerUser().getFirstName() != null && orgSpoc.getOwnerUser().getFirstName() != "" && orgSpoc.getOwnerUser().getLastName() != null && orgSpoc.getOwnerUser().getLastName() != "" ){
								StringBuilder ownerNameSB1 = new StringBuilder();
								 orgSpoc.setOwnerName(ownerNameSB1.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
							}else{
								orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName());
							}
								 orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
								 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
								 orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
								 orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
							}
							
						 
					}else{
						if(orgSpoc.getInitiatorLocationId().getId().longValue() == orgLocations.getId().longValue() ){
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
						if(orgSpoc.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
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
					if(orgSpoc.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
						orgSpoc.setSpocLocationId(orgSpoc.getInitiatorLocationId().getId());
						orgSpoc.setOrgName(orgSpoc.getInitiatorLocationId().getOrg().getOrgName());
					}
					if(orgSpoc.getInitiatorLocationId().getId().longValue() == orgLocations.getId().longValue() ){
						orgSpoc.setSpocLocationId(orgSpoc.getResponderLocationId().getId());
						orgSpoc.setOrgName(orgSpoc.getResponderLocationId().getOrg().getOrgName());
					}
					
					orgSpoc.setOwnerUserId(orgSpoc.getOwnerUser().getId().toString());
					orgSpoc.setSpocUserId(orgSpoc.getSpocUser().getId());
					orgSpoc.setOrgRelationId(orgSpoc.getOrgRelations().getId());*/
		    	}
					
			}
		    if(contactsList != null){
		    	for(OrgSpoc contact : contactsList){
			    	Boolean isJobReleased = requirementService.getRequirementByParentRequirementIdAndSpocId(Long.parseLong(requirementId), contact.getId());
			    	contact.setReleasedRequirement(isJobReleased.toString());
			    	StringBuilder ownerName = new StringBuilder();
			    	if(null != contact.getClientUser() && null != contact.getClientUser().getLastName() &&  "" != contact.getClientUser().getLastName()){
			    		contact.setOwnerName(ownerName.append(contact.getClientUser().getFirstName()).append(" ").append(contact.getClientUser().getLastName()).toString());
			    	}else{
			    		contact.setOwnerName(ownerName.append(contact.getClientUser().getFirstName()).toString());
			    	}
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

		CopyOnWriteArrayList<OrgRelations> vandorsListByOrgLocationIdDuplicate = new CopyOnWriteArrayList<>(vandorsListByOrgLocationId);
	    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

		    //List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocations.getId());
 
		    for (OrgRelations orgRelation : vandorsListByOrgLocationIdDuplicate) {
				
				if(orgRelation.getLocationId().getId().equals(orgLocations.getId())){
					orgRelation.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
					orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrg().getOrgName());
					orgRelation.setCountry(orgRelation.getResponderLocationId().getCountry());
					orgRelation.setState(orgRelation.getResponderLocationId().getState());
					orgRelation.setCity(orgRelation.getResponderLocationId().getCity());
					orgRelation.setVendorlocationId(orgRelation.getResponderLocationId().getId());
					//List<OrgSpoc>  contactsList = orgSpocService.getAllContactsbyOrgLocationIdAndUserIdForRequirement(orgLocations.getId(),user.getId(),"Client");
					//List<OrgSpoc> contactsList = orgSpocService.getAllContactsbyOrgId(orgRelation.getId());
					//List<OrgSpoc> contactsList = orgSpocService.getAllContactsbyOrgIdAndInvitationStatusAndSpocStatus(orgRelation.getId(),"Accepted","Active");
					List<OrgSpoc> contactsList = orgRelationsService.getAllVendorsContactFilterListByOrgLocationId(user.getId(),orgLocations.getId(), "Client","Active,New,Contracted",orgRelation.getId().toString(),"");
					int nonAssignedCount = 0;
					orgRelation.setNoOfContatcs(0); 
					if(!contactsList.isEmpty()){
						orgRelation.setNoOfContatcs(contactsList.size());
						for(OrgSpoc orgSpoc : contactsList){
							Boolean isJobReleased = requirementService.getRequirementByParentRequirementIdAndSpocId(Long.parseLong(requirementId), orgSpoc.getId());
							if(isJobReleased.toString().equalsIgnoreCase("false")){
								nonAssignedCount = nonAssignedCount + 1;
							}
						}
						orgRelation.setNoOfNonReleasedContacts(nonAssignedCount);
						if(orgRelation.getInitiatorLevel()!=null){
							orgRelation.setLevel(orgRelation.getInitiatorLevel());
						}
						else{
							orgRelation.setLevel("");
						}
					}else{
						vandorsListByOrgLocationIdDuplicate.remove(orgRelation);
					}
				}
				
				if(orgRelation.getResponderLocationId().getId().equals(orgLocations.getId())){
					orgRelation.setWebSite(orgRelation.getLocationId().getOrg().getWebSite());
					orgRelation.setOrgName(orgRelation.getLocationId().getOrg().getOrgName());
					orgRelation.setCountry(orgRelation.getLocationId().getCountry());
					orgRelation.setState(orgRelation.getLocationId().getState());
					orgRelation.setCity(orgRelation.getLocationId().getCity());
					orgRelation.setVendorlocationId(orgRelation.getLocationId().getId());
					//List<OrgSpoc>  contactsList = orgSpocService.getAllContactsbyOrgLocationIdAndUserIdForRequirement(orgLocations.getId(),user.getId(),"Vendor");
					//List<OrgSpoc> contactsList = orgSpocService.getAllContactsbyOrgId(orgRelation.getId());
					//List<OrgSpoc> contactsList = orgSpocService.getAllContactsbyOrgIdAndInvitationStatusAndSpocStatus(orgRelation.getId(),"Accepted","Active");
					List<OrgSpoc> contactsList = orgRelationsService.getAllVendorsContactFilterListByOrgLocationId(user.getId(),orgLocations.getId(), "Client","Active,New,Contracted",orgRelation.getId().toString(),"");
					int nonAssignedCount = 0;
					orgRelation.setNoOfContatcs(0); 
					if(!contactsList.isEmpty()){
						orgRelation.setNoOfContatcs(contactsList.size()); 
						for(OrgSpoc orgSpoc : contactsList){
							Boolean isJobReleased = requirementService.getRequirementByParentRequirementIdAndSpocId(Long.parseLong(requirementId), orgSpoc.getId());
							if(isJobReleased.toString().equalsIgnoreCase("false")){
								nonAssignedCount = nonAssignedCount + 1;
							}
						}
						orgRelation.setNoOfNonReleasedContacts(nonAssignedCount);
						if(orgRelation.getResponderLevel()!=null){
							orgRelation.setLevel(orgRelation.getResponderLevel());
						}
						else{
							orgRelation.setLevel("");
						}
					}else{
						vandorsListByOrgLocationIdDuplicate.remove(orgRelation);
					}
				}
			}
		    List<OrgRelations> vandorsList = new ArrayList<OrgRelations>(vandorsListByOrgLocationIdDuplicate);
			resultJson = commonUtil.toJSON(vandorsList);
			return resultJson;
		}
	
	
	/**
	 * Handles Vendor Contacts 
	 * @param json
	 * @param request
	 * @return Json
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_RELEASE_REQUIREMENT')")
	@RequestMapping(value = "/releaseJobsToClientsOrVendors", method = RequestMethod.POST)
	@ResponseBody
	public String releaseJobsToClientsOrVendors(@RequestParam(value = "requirementId", required = false) Long requirementId,
												@RequestParam(value = "contactsIds", required = false) String contactsIds,
			                                    @RequestParam(value="vendorIds", required=false) String vendorIds,
			                                    HttpServletRequest request) throws Exception {
			
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		 	String releasetype = null;
			Requirement parentRequirement = requirementService.getRequirementByRequirementId(requirementId);
			String[] contacts = contactsIds.split(",");
			if(contacts != null &&  contacts.length>0 && contacts[0] != null && contacts[0] != "" ){
				for(String contact   : contacts){
						releasetype = Constants.CONTACTS;
						OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(Long.parseLong(contact));
						Requirement requirement  = new Requirement();
						releaseJobToVendorOrContacts(requirement, parentRequirement, orgSpoc,null,loginOrgLocation);
						requirementService.releaseMailScenario(loginUser, requirement, orgSpoc, loginOrgLocation,releasetype);

				}
			}	
			
			String[] vendors = vendorIds.split(",");
			if(vendors != null &&  vendors.length>0 && vendors[0] != null && vendors[0] != ""){
				for(String vendor   : vendors){
					
					
					OrgRelations orgRelations = orgRelationsService.getOrRelationsById(Long.parseLong(vendor));
					
					 List<OrgSpoc> vandorsContactListByOrgLocationId = orgRelationsService.getAllVendorsContactFilterListByOrgLocationId(loginUser.getId(),loginOrgLocation.getId(), "Client","Active",vendor,"");
					
					for(OrgSpoc orgSpoc : vandorsContactListByOrgLocationId){
					    Boolean isJobReleased = requirementService.getRequirementByParentRequirementIdAndSpocId(requirementId, orgSpoc.getId());
					    if(isJobReleased.toString().equalsIgnoreCase("false")){
					    	Requirement requirement  = new Requirement();
					    	releasetype = Constants.VENDORSTYPE;
							releaseJobToVendorOrContacts(requirement, parentRequirement, orgSpoc, orgRelations.getInitiatorType(),loginOrgLocation);
							requirementService.releaseMailScenario(loginUser, requirement, orgSpoc, loginOrgLocation,releasetype);

					    }
					}
				}
			}
			
			/*if(null != isPublics && !"".equalsIgnoreCase(isPublics) && "undefined" == isPublics){
				parentRequirement.setIsPublic(isPublics);
				parentRequirement.setPostedOrgName(org.getOrgName());
			}
			if(null != isCrowdScourcing && !"".equalsIgnoreCase(isCrowdScourcing) && "undefined" == isCrowdScourcing){
				parentRequirement.setIsCrowdSourcing(isCrowdScourcing);
				parentRequirement.setPostedOrgName(org.getOrgName());
			}*/
			
			if(null == parentRequirement.getReleasedBy()){
				parentRequirement.setReleasedBy(loginUser.getId().toString());
			}else{
				List<Long> userId = new ArrayList<Long>();
				for (String s : parentRequirement.getReleasedBy().split(",")){
					userId.add(Long.parseLong(s));
				 }
				boolean flag = userId.contains(loginUser.getId());
         		if(flag == false){
         			StringBuilder releasedUser = new StringBuilder();
    				releasedUser = releasedUser.append(",").append(loginUser.getId().toString());
    				parentRequirement.setReleasedBy(parentRequirement.getReleasedBy()+releasedUser.toString());
         		}
			}
			parentRequirement.setUpdatedDate(new Date());
			requirementService.addRequirement(parentRequirement);
			
			return "vendorsList";
	}

	private void releaseJobToVendorOrContacts(Requirement requirement, Requirement parentRequirement, OrgSpoc orgSpoc, String initiator,OrgLocations loginOrgLocation) throws Exception {
		
		//requirement = parentRequirement;
		
		requirement.setParentRequirement(parentRequirement);
		/*if(StringUtils.equalsIgnoreCase(initiator, "Vendor") ){
			requirement.setOrgLocationId(orgSpoc.getInitiatorLocationId());
		}else{
			requirement.setOrgLocationId(orgSpoc.getResponderLocationId());
		}*/
		
		/*if(orgSpoc != null && orgSpoc.getResponderLocationId().getId().longValue() == loginOrgLocation.getId().longValue()){
			requirement.setOrgLocationId(orgSpoc.getInitiatorLocationId());
			requirement.setManagerUserId(orgSpoc.getOwnerUser());
			requirement.setUpdatedBy(orgSpoc.getOwnerUser().getUserCode());
			requirement.setCreatedBy(orgSpoc.getOwnerUser().getUserCode());
		}
		if(orgSpoc != null && orgSpoc.getInitiatorLocationId().getId().longValue() == loginOrgLocation.getId().longValue() ){
			requirement.setOrgLocationId(orgSpoc.getResponderLocationId());
			requirement.setManagerUserId(orgSpoc.getSpocUser());
			requirement.setUpdatedBy(orgSpoc.getSpocUser().getUserCode());
			requirement.setCreatedBy(orgSpoc.getSpocUser().getUserCode());
		}*/
		
		requirement.setOrgLocationId(orgSpoc.getVendorLocationId());
		requirement.setManagerUserId(orgSpoc.getVendorUser());
		requirement.setUpdatedBy(orgSpoc.getVendorUser().getUserCode());
		requirement.setCreatedBy(orgSpoc.getVendorUser().getUserCode());
		
		if(null != parentRequirement.getJobType()){
			requirement.setJobType(parentRequirement.getJobType());	
		}
		if(null != parentRequirement.getJobType() && parentRequirement.getJobType().equalsIgnoreCase("Other") &&  parentRequirement.getJobType() != "" &&  parentRequirement.getJobType() != "undefined"){
			requirement.setOtherJobType(parentRequirement.getOtherJobType());
		}
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
		requirement.setIsTemplate("N");
		requirement.setSubmissionTemplateJsonString(parentRequirement.getSubmissionTemplateJsonString());
		requirement.setClientSubmissionTemplateJsonString(parentRequirement.getSubmissionTemplateJsonString());
		//requirement.setClientCurrencyCode(parentRequirement.getClientCurrencyCode());
		//requirement.setClientRate(parentRequirement.getClientRate());
		//requirement.setClientRateType(parentRequirement.getClientRateType());
		requirement.setClientCurrencyCode(parentRequirement.getCurrencyCode());
		requirement.setClientRate(parentRequirement.getRate());
		requirement.setClientRateType(parentRequirement.getRateType());
		
		requirement.setRequirementUUID(SourceLeadRandomCodeHelper.generateRandomUserCode());
		
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
		}
		
		requirement.setCreatedDate(commonUtil.currentDate());
		requirement.setUpdatedDate(commonUtil.currentDate());
		
		requirement.setId(null);
        Requirement req= requirementService.addRequirement(requirement);
		
		//store recommended customizeScore
	   RecommendedCustomizeScore  recommendedCustomizeScore = recommendedCustomizeScoreService.saveCustomizeScore(req);

	}
	
	
	 @PreAuthorize("hasRole('ROLE_RELEASE_REQUIREMENT')")
	 @RequestMapping(value = "/releaseToCrowdSourcingNetwork" , method = RequestMethod.POST)
	 public  String releaseToCrowdSourcingNetwork(@RequestParam(value="json", required=false) String json,
			 @RequestParam(value = "requirementId", required = false) Long requirementId,
			 HttpServletRequest request) throws Exception {
		 	
		 json = "{"+json+"}";
		 
		 JSONObject jsonObj = new JSONObject(json);
		 	
	 	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	 Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
	 	 
	 	Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
	 	
	 	if(jsonObj.has("isPublic") && !isNullOrEmpty(jsonObj.getString("isPublic")) && jsonObj.getString("isPublic").equalsIgnoreCase("Y")){
	 		requirement.setIsPublic(jsonObj.getString("isPublic"));
	 		requirement.setPostedOrgName(org.getOrgName());
	 	}
	 	
	 	if(jsonObj.has("isCrowdScourcing") && !isNullOrEmpty("isCrowdScourcing") && jsonObj.getString("isCrowdScourcing").equalsIgnoreCase("Y")){
	 		requirement.setIsCrowdSourcing(jsonObj.getString("isCrowdScourcing"));
	 		requirement.setPostedOrgName(org.getOrgName());
	 		
	 		if(jsonObj.has("rewardType") && !isNullOrEmpty("rewardType") && jsonObj.getString("rewardType").equalsIgnoreCase("Percentage")){
	 			requirement.setRewardPercentage(jsonObj.getString("percentageReward"));
	 			requirement.setRewardType(jsonObj.getString("rewardType"));
	 		}
	 		
	 		if(jsonObj.has("rewardType") && !isNullOrEmpty("rewardType") && jsonObj.getString("rewardType").equalsIgnoreCase("Flat")){
	 			requirement.setRewardFlat(jsonObj.getString("flatReward"));
	 			requirement.setRewardFlatRateType(jsonObj.getString("flatRateType"));
	 			requirement.setRewardType(jsonObj.getString("rewardType"));
	 		}
	 		
	 		if(jsonObj.has("guaranteePeriod") && !isNullOrEmpty(jsonObj.getString("guaranteePeriod"))){
	 			requirement.setRewardGuaranteePeriod(jsonObj.getString("guaranteePeriod"));
	 		}
	 	}
	 	 
		if(null == requirement.getReleasedBy()){
			requirement.setReleasedBy(user.getId().toString());
		}else{
			List<Long> userId = new ArrayList<Long>();
			for (String s : requirement.getReleasedBy().split(",")){
				userId.add(Long.parseLong(s));
			 }
			boolean flag = userId.contains(user.getId());
  		if(flag == false){
  			StringBuilder releasedUser = new StringBuilder();
				releasedUser = releasedUser.append(",").append(user.getId().toString());
				requirement.setReleasedBy(requirement.getReleasedBy()+releasedUser.toString());
  		}
		}
		requirement.setUpdatedDate(new Date());
		requirementService.addRequirement(requirement);
	 	
		 return "success";
	   }
}