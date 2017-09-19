package com.bis.sourcelead.rest;
import java.text.SimpleDateFormat;
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
import com.bis.sourcelead.web.LoginSessionController;
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
	public @ResponseBody String getAllContactsbyOrgLocationIdAndUserIdForRequirement(String requirementId, String vendors, String status, String vendorSelectedIds, HttpServletRequest request) throws Exception {

	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    String resultJson;
	    String[] vendorsRelationId = null;
	    if(vendors != ""){
	    	vendorsRelationId = vendors.split(",");
	    }
	    Requirement req = requirementService.getRequirementByRequirementId(Long.parseLong(requirementId));
	    
	    List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllVendorsFilterByOrgLocationId(orgLocations.getId(),status, vendorSelectedIds);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<OrgSpoc>  contactsList = null;	
		//SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		
		Long clientLocationId = null;
	    if(null != req.getClientId() && "Client".equals(req.getClientId().getInitiatorType())){
	    	if(req.getClientId().getResponderLocationId().getId().equals(orgLocations.getId())){
	    		clientLocationId = req.getClientId().getLocationId().getId();
	    	}
	    }else if(null != req.getClientId() && "Vendor".equals(req.getClientId().getInitiatorType())){
	    	if(req.getClientId().getLocationId().getId().equals(orgLocations.getId())){
	    		clientLocationId = req.getClientId().getResponderLocationId().getId();
	    	}
	    }
	    
		List<Long> vendorOrgLocationsIds = new ArrayList<Long>();
		   for (OrgRelations orgRelation : vandorsListByOrgLocationId) {
			   
			    Boolean isCyclicRelease = false;
			    if(orgRelation.getResponderLocationId().getId().equals(orgLocations.getId())){
				    /*this check is for the vendor org is owner for parent requirement or not (@Venkat Kesav)*/
				    if((null != req.getParentRequirement() && null != req.getParentRequirement().getOrgLocationId() && req.getParentRequirement().getOrgLocationId().getId() == orgRelation.getLocationId().getId()) || ("Y".equalsIgnoreCase(req.getIsClientJob()) && clientLocationId.equals(orgRelation.getLocationId().getId()))){
			    		isCyclicRelease = true;
			    	}else{
				    	vendorOrgLocationsIds.add(orgRelation.getLocationId().getId());
				    }
			    }else if(orgRelation.getLocationId().getId().equals(orgLocations.getId())){
				    /*this check is for the vendor org is owner for parent requirement or not (@Venkat Kesav)*/
				    if((null != req.getParentRequirement() && null != req.getParentRequirement().getOrgLocationId() && req.getParentRequirement().getOrgLocationId().getId() == orgRelation.getResponderLocationId().getId()) || ("Y".equalsIgnoreCase(req.getIsClientJob()) && clientLocationId.equals(orgRelation.getResponderLocationId().getId()))){
			    		isCyclicRelease = true;
			    	}else{
				    	vendorOrgLocationsIds.add(orgRelation.getResponderLocationId().getId());
				    }
			    }
			   
			   /*if(orgRelation.getResponderLocationId().getId().equals(orgLocations.getId())){
				   if(isCyclicRelease){
				    	
				    }else{
				    	vendorOrgLocationsIds.add(orgRelation.getLocationId().getId());
				    }
			   }else if(orgRelation.getLocationId().getId().equals(orgLocations.getId())){
				   if(isCyclicRelease){
				    	
				    }else{
				    	vendorOrgLocationsIds.add(orgRelation.getResponderLocationId().getId());
				    }
			   }*/
			    
			    
			  /* if(orgRelation.getResponderLocationId().getId().equals(orgLocations.getId())){
				   this check is for the vendor org is owner for parent requirement or not -- is Cyclic Release or not (@Venkat Kesav)
				    if(null != req.getParentRequirement() && null != req.getParentRequirement().getOrgLocationId() && req.getParentRequirement().getOrgLocationId().getId() != orgRelation.getLocationId().getId()){
				    	vendorOrgLocationsIds.add(orgRelation.getLocationId().getId());
			    	}else if("Y".equalsIgnoreCase(req.getIsClientJob()) && !clientLocationId.equals(orgRelation.getLocationId().getId())){
			    		vendorOrgLocationsIds.add(orgRelation.getLocationId().getId());
			    	}else if(null == req.getParentRequirement()){
			    		vendorOrgLocationsIds.add(orgRelation.getLocationId().getId());
			    	}
			   }else if(orgRelation.getLocationId().getId().equals(orgLocations.getId())){
				   this check is for the vendor org is owner for parent requirement or not -- is Cyclic Release or not (@Venkat Kesav)
				    if(null != req.getParentRequirement() && null != req.getParentRequirement().getOrgLocationId() && req.getParentRequirement().getOrgLocationId().getId() != orgRelation.getResponderLocationId().getId()){
				    	vendorOrgLocationsIds.add(orgRelation.getResponderLocationId().getId());
			    	}else if("Y".equalsIgnoreCase(req.getIsClientJob()) && !clientLocationId.equals(orgRelation.getResponderLocationId().getId())){
			    		vendorOrgLocationsIds.add(orgRelation.getResponderLocationId().getId());
			    	}else if(null == req.getParentRequirement()){
			    		vendorOrgLocationsIds.add(orgRelation.getResponderLocationId().getId());
			    	}
			   }*/
		   }
		    	
		
		    	//contactsList = orgRelationsService.getAllVendorsContactFilterListByOrgLocationId(user.getId(),orgLocations.getId(), "Client","Active,New,Contracted",null,"");
		    	if(vendorOrgLocationsIds.size() > 0){
		    		contactsList = orgRelationsService.getAllVendorsContactFilterListByOrgLocationIdAndTabAccess(user.getId(),orgLocations.getId(), "Client","Active,New,Contracted",null,"", vendorOrgLocationsIds);
		    	}
		    	
		    	/*for (OrgSpoc orgSpoc : contactsList) {
		    		

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
						orgSpoc.setOrgName(orgSpoc.getInitiatorLocationId().getOrgLocationName());
					}
					if(orgSpoc.getInitiatorLocationId().getId().longValue() == orgLocations.getId().longValue() ){
						orgSpoc.setSpocLocationId(orgSpoc.getResponderLocationId().getId());
						orgSpoc.setOrgName(orgSpoc.getResponderLocationId().getOrgLocationName());
					}
					
					orgSpoc.setOwnerUserId(orgSpoc.getOwnerUser().getId().toString());
					orgSpoc.setSpocUserId(orgSpoc.getSpocUser().getId());
					orgSpoc.setOrgRelationId(orgSpoc.getOrgRelations().getId());
		    	}*/
					
		    if(contactsList != null){
		    	for(OrgSpoc contact : contactsList){
/*			    	Boolean isJobReleased = requirementService.getRequirementByParentRequirementIdAndSpocId(Long.parseLong(requirementId), contact.getId());
*/			    	List<Requirement>  reqList = requirementService.getRequirementListByParentRequirementIdAndSpocId(Long.parseLong(requirementId), contact.getId());
					
					if(reqList.size()>0){
			    	contact.setReleasedRequirement("true");
			    	for(Requirement req11 : reqList){
						contact.setStringDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),req11.getCreatedDate()));
					}
					}else{
						contact.setReleasedRequirement("false");	
					}
			    	if(contact.getVendorUser().getFirstName() != null && contact.getVendorUser().getFirstName() != "" && contact.getVendorUser().getLastName() != null && contact.getVendorUser().getLastName() != "" ){
						StringBuilder spocNameSB3 = new StringBuilder();
						contact.setSpocName(spocNameSB3.append(contact.getVendorUser().getFirstName()).append(" ").append(contact.getVendorUser().getLastName()).toString());
					}else{
						contact.setSpocName(contact.getVendorUser().getFirstName());
					}
			    	
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
	    
	    Requirement req = requirementService.getRequirementByRequirementId(Long.parseLong(requirementId));
	    
	    List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllVendorsFilterByOrgLocationId(orgLocations.getId(),vendorStatus,"");
	    //SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		CopyOnWriteArrayList<OrgRelations> vandorsListByOrgLocationIdDuplicate = new CopyOnWriteArrayList<>(vandorsListByOrgLocationId);
	    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    //List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocations.getId());
	    Long clientLocationId = null;
	    if(null != req.getClientId() && req.getClientId().getInitiatorType().equals("Client")){
	    	if(req.getClientId().getResponderLocationId().getId().equals(orgLocations.getId())){
	    		clientLocationId = req.getClientId().getLocationId().getId();
	    	}
	    }else if(null != req.getClientId() && req.getClientId().getInitiatorType().equals("Vendor")){
	    	if(req.getClientId().getLocationId().getId().equals(orgLocations.getId())){
	    		clientLocationId = req.getClientId().getResponderLocationId().getId();
	    	}
	    }
	    
		    for (OrgRelations orgRelation : vandorsListByOrgLocationIdDuplicate) {
		    	
		    	/*isCyclicRelease variable is for to check the vendor org is owner for parent requirement or not (@Venkat Kesav)*/
		    	Boolean isCyclicRelease = false;
		    	
		    	List<Long> vendorOrgLocationsIds = new ArrayList<Long>();
			    if(orgRelation.getResponderLocationId().getId().equals(orgLocations.getId())){
				    vendorOrgLocationsIds.add(orgRelation.getLocationId().getId());
				    /*this check is for the vendor org is owner for parent requirement or not (@Venkat Kesav)*/
				    if((null != req.getParentRequirement() && null != req.getParentRequirement().getOrgLocationId() && req.getParentRequirement().getOrgLocationId().getId() == orgRelation.getLocationId().getId()) || ("Y".equalsIgnoreCase(req.getIsClientJob()) && clientLocationId.equals(orgRelation.getLocationId().getId()))){
			    		isCyclicRelease = true;
			    	}
			    }else if(orgRelation.getLocationId().getId().equals(orgLocations.getId())){
				    vendorOrgLocationsIds.add(orgRelation.getResponderLocationId().getId());
				    /*this check is for the vendor org is owner for parent requirement or not (@Venkat Kesav)*/
				    if((null != req.getParentRequirement() && null != req.getParentRequirement().getOrgLocationId() && req.getParentRequirement().getOrgLocationId().getId() == orgRelation.getResponderLocationId().getId()) || ("Y".equalsIgnoreCase(req.getIsClientJob()) && clientLocationId.equals(orgRelation.getResponderLocationId().getId()))){
			    		isCyclicRelease = true;
			    	}
			    }
			    
			    if(isCyclicRelease){
			    	vandorsListByOrgLocationIdDuplicate.remove(orgRelation);
			    }else{
					if(orgRelation.getLocationId().getId().equals(orgLocations.getId())){
						orgRelation.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
						orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrgLocationName());
						orgRelation.setCountry(orgRelation.getResponderLocationId().getCountry());
						orgRelation.setState(orgRelation.getResponderLocationId().getState());
						orgRelation.setCity(orgRelation.getResponderLocationId().getCity());
						orgRelation.setVendorlocationId(orgRelation.getResponderLocationId().getId());
						//List<OrgSpoc>  contactsList = orgSpocService.getAllContactsbyOrgLocationIdAndUserIdForRequirement(orgLocations.getId(),user.getId(),"Client");
						//List<OrgSpoc> contactsList = orgSpocService.getAllContactsbyOrgId(orgRelation.getId());
						//List<OrgSpoc> contactsList = orgSpocService.getAllContactsbyOrgIdAndInvitationStatusAndSpocStatus(orgRelation.getId(),"Accepted","Active");
						//List<OrgSpoc> contactsList = orgRelationsService.getAllVendorsContactFilterListByOrgLocationId(user.getId(),orgLocations.getId(), "Client","Active,New,Contracted",orgRelation.getId().toString(),"");
						List<OrgSpoc> contactsList = orgRelationsService.getAllVendorsContactFilterListByOrgLocationIdAndTabAccess(user.getId(),orgLocations.getId(), "Client","Active,New,Contracted",orgRelation.getId().toString(),"", vendorOrgLocationsIds);
						
						int nonAssignedCount = 0;
						orgRelation.setNoOfContatcs(0); 
						if(!contactsList.isEmpty()){
							orgRelation.setNoOfContatcs(contactsList.size());
							for(OrgSpoc orgSpoc : contactsList){
								/*Boolean isJobReleased = requirementService.getRequirementByParentRequirementIdAndSpocId(Long.parseLong(requirementId), orgSpoc.getId());
								if(isJobReleased.toString().equalsIgnoreCase("false")){
									nonAssignedCount = nonAssignedCount + 1;
								}*/
								
								List<Requirement>  reqList = requirementService.getRequirementListByParentRequirementIdAndSpocId(Long.parseLong(requirementId), orgSpoc.getId());
								
								
								if(reqList.size()>0){
									orgSpoc.setReleasedRequirement("true");
										Requirement requi  = reqList.get(0);
										orgRelation.setStringDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),requi.getCreatedDate()));	
									
						    	/*for(Requirement req11 : reqList){
						    		orgSpoc.setStringDate(LoginSessionController.DATEFORMAT.format(req11.getCreatedDate()));
								}*/
								}else{
									orgSpoc.setReleasedRequirement("false");	
									nonAssignedCount = nonAssignedCount + 1;
								}
								
								/*if(null != req.getParentRequirement().getCreatedDate()){
								orgRelation.setStringCreatedDate(LoginSessionController.DATEFORMAT.format(req.getParentRequirement().getCreatedDate()));
								}*/
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
						orgRelation.setOrgName(orgRelation.getLocationId().getOrgLocationName());
						orgRelation.setCountry(orgRelation.getLocationId().getCountry());
						orgRelation.setState(orgRelation.getLocationId().getState());
						orgRelation.setCity(orgRelation.getLocationId().getCity());
						orgRelation.setVendorlocationId(orgRelation.getLocationId().getId());
						//List<OrgSpoc>  contactsList = orgSpocService.getAllContactsbyOrgLocationIdAndUserIdForRequirement(orgLocations.getId(),user.getId(),"Vendor");
						//List<OrgSpoc> contactsList = orgSpocService.getAllContactsbyOrgId(orgRelation.getId());
						//List<OrgSpoc> contactsList = orgSpocService.getAllContactsbyOrgIdAndInvitationStatusAndSpocStatus(orgRelation.getId(),"Accepted","Active");
						//List<OrgSpoc> contactsList = orgRelationsService.getAllVendorsContactFilterListByOrgLocationId(user.getId(),orgLocations.getId(), "Client","Active,New,Contracted",orgRelation.getId().toString(),"");
						List<OrgSpoc> contactsList = orgRelationsService.getAllVendorsContactFilterListByOrgLocationIdAndTabAccess(user.getId(),orgLocations.getId(), "Client","Active,New,Contracted",orgRelation.getId().toString(),"", vendorOrgLocationsIds);
						int nonAssignedCount = 0;
						orgRelation.setNoOfContatcs(0); 
						if(!contactsList.isEmpty()){
							orgRelation.setNoOfContatcs(contactsList.size()); 
							for(OrgSpoc orgSpoc : contactsList){
								/*Boolean isJobReleased = requirementService.getRequirementByParentRequirementIdAndSpocId(Long.parseLong(requirementId), orgSpoc.getId());
								if(isJobReleased.toString().equalsIgnoreCase("false")){
									nonAssignedCount = nonAssignedCount + 1;
								}*/
								List<Requirement>  reqList = requirementService.getRequirementListByParentRequirementIdAndSpocId(Long.parseLong(requirementId), orgSpoc.getId());
								
								
								if(reqList.size()>0){
									orgSpoc.setReleasedRequirement("true");
										Requirement requi  = reqList.get(0);
										orgRelation.setStringDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),requi.getCreatedDate()));	
									
						    	/*for(Requirement req11 : reqList){
						    		orgSpoc.setStringDate(LoginSessionController.DATEFORMAT.format(req11.getCreatedDate()));
								}*/
								}else{
									orgSpoc.setReleasedRequirement("false");	
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
					List<Long> vendorOrgLocationsIds = new ArrayList<Long>();
						   if(orgRelations.getResponderLocationId().getId().equals(loginOrgLocation.getId())){
							   vendorOrgLocationsIds.add(orgRelations.getLocationId().getId());
						   }else if(orgRelations.getLocationId().getId().equals(loginOrgLocation.getId())){
							   vendorOrgLocationsIds.add(orgRelations.getResponderLocationId().getId());
						   }
					
					 List<OrgSpoc> vandorsContactListByOrgLocationId = orgRelationsService.getAllVendorsContactFilterListByOrgLocationIdAndTabAccess(loginUser.getId(),loginOrgLocation.getId(), "Client","Active",vendor,"", vendorOrgLocationsIds);
					
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
			
			StringBuilder reqReleaseMsg = new StringBuilder();
			SourceLeadSessionManager.setSessionAttribute(request, "reqReleaseMessage", reqReleaseMsg.append("Success!! ").append(" job  released").toString());
			
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
		requirement.setCreatedBy(orgSpoc.getVendorUser());
		
		requirement.setJobTitle(parentRequirement.getJobTitle());
		requirement.setJobType(parentRequirement.getJobType());
		if(null != parentRequirement.getJobType() &&  parentRequirement.getJobType() != "" &&  parentRequirement.getJobType() != "undefined" && parentRequirement.getJobType().equalsIgnoreCase("Other")){
			requirement.setOtherJobType(parentRequirement.getOtherJobType());
		}
		
		requirement.setReqCoolingOffPeriod(parentRequirement.getReqCoolingOffPeriod());
		
//		requirement.setJobId(parentRequirement.getJobId());
		requirement.setPositions(parentRequirement.getPositions());
		requirement.setEducation(parentRequirement.getEducation());
		requirement.setMinExp(parentRequirement.getMinExp());
		requirement.setMaxExp(parentRequirement.getMaxExp());
		requirement.setCurrencyCode(parentRequirement.getCurrencyCode());
		requirement.setRate(parentRequirement.getRate());
		requirement.setPriority(parentRequirement.getPriority());
		requirement.setIndustryName(parentRequirement.getIndustryName());
		requirement.setMaxRate(parentRequirement.getMaxRate());
		requirement.setTravelAllowance(parentRequirement.getTravelAllowance());
		requirement.setProjectDurationMonths(parentRequirement.getProjectDurationMonths());
		requirement.setProjectDurationYears(parentRequirement.getProjectDurationYears());
		requirement.setRateType(parentRequirement.getRateType());
		requirement.setCountry(parentRequirement.getCountry());
		requirement.setState(parentRequirement.getState());
		requirement.setCity(parentRequirement.getCity());
		requirement.setLocation(parentRequirement.getLocation());
		requirement.setSkills(parentRequirement.getSkills());
		requirement.setDescription(parentRequirement.getDescription());
		requirement.setNoticePeriod(parentRequirement.getNoticePeriod());
		requirement.setDocFile(parentRequirement.getDocFile());
		requirement.setFileContentType(parentRequirement.getFileContentType());
		requirement.setFileName(parentRequirement.getFileName());
		if(parentRequirement.getVisaType() != null){
			requirement.setVisaType(parentRequirement.getVisaType());	
		}
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
		requirement.setTotalApplicants(0);
		requirement.setRequirementUUID(SourceLeadRandomCodeHelper.generateRandomUserCode());
		requirement.setGeneratedId(requirementService.getRequirementId(orgSpoc.getOrgRelations().getResponderLocationId()));
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
	
	
	 @RequestMapping(value = "/releaseToCrowdSourcingNetwork" , method = RequestMethod.POST)
	 public  String releaseToCrowdSourcingNetwork(@RequestParam(value="json", required=false) String json,
			 @RequestParam(value = "requirementId", required = false) Long requirementId,
			 HttpServletRequest request) throws Exception {
		 	
		 json = "{"+json+"}";
		 
		 JSONObject jsonObj = new JSONObject(json);
		 	
	 	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	 Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
	 	 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	 
	 	Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
	 	
	 	if(jsonObj.has("isPublic") && !isNullOrEmpty(jsonObj.getString("isPublic")) && jsonObj.getString("isPublic").equalsIgnoreCase("Y")){
	 		requirement.setIsPublic(jsonObj.getString("isPublic"));
	 		requirement.setPostedOrgName(orgLocations.getOrgLocationName());
	 	}else{
	 		requirement.setIsPublic(jsonObj.getString("isPublic"));
	 	}
	 	
	 	if(jsonObj.has("isCrowdScourcing") && !isNullOrEmpty("isCrowdScourcing") && jsonObj.getString("isCrowdScourcing").equalsIgnoreCase("Y")){
	 		requirement.setIsCrowdSourcing(jsonObj.getString("isCrowdScourcing"));
	 		requirement.setPostedOrgName(orgLocations.getOrgLocationName());
	 		
	 		if(jsonObj.has("rewardType") && !isNullOrEmpty("rewardType") && jsonObj.getString("rewardType").equalsIgnoreCase("Percentage")){
	 			if(jsonObj.has("percentageReward")){
	 				requirement.setRewardPercentage(jsonObj.getString("percentageReward"));
	 			}
	 			requirement.setRewardType(jsonObj.getString("rewardType"));
	 		}
	 		
	 		if(jsonObj.has("rewardType") && !isNullOrEmpty("rewardType") && jsonObj.getString("rewardType").equalsIgnoreCase("Flat")){
	 			
	 			if(jsonObj.has("flatReward")){
	 				requirement.setRewardFlat(jsonObj.getString("flatReward"));
	 			}
	 			
	 			if(jsonObj.has("flatRateType")){
	 				requirement.setRewardFlatRateType(jsonObj.getString("flatRateType"));
	 			}
	 			requirement.setRewardType(jsonObj.getString("rewardType"));
	 		}
	 		
	 		if(jsonObj.has("guaranteePeriod") && !isNullOrEmpty(jsonObj.getString("guaranteePeriod"))){
	 			requirement.setRewardGuaranteePeriod(jsonObj.getString("guaranteePeriod"));
	 		}
	 	}else{
	 		requirement.setIsCrowdSourcing(jsonObj.getString("isCrowdScourcing"));
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