package com.bis.sourcelead.elk.index.helper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.elk.index.ClientVendorIndex;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;

@Component
public class ClientVendorIndexHelper {
	
	

	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	OrgService orgService;
	
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private OrgSpocService orgSpocService;
	
	public ClientVendorIndex getClientVendorIndexFromCandidate(Org org,OrgLocations clientOrgLocations, String responderType,
			OrgSpoc orgSpoc, OrgRelations orgRelations, SubmissionTemplate submissionTemplate, User loginUser, OrgLocations loginOrgLoctn) throws Exception{
		
		ClientVendorIndex clientVendorIndex = new ClientVendorIndex();
		
		if(null != clientOrgLocations){
			
			// this is to setting values for Initiator Details
			OrgLocations initiatorLocation = orgLocationService.getOrgLocations(orgRelations.getLocationId().getId());
			clientVendorIndex.setId(initiatorLocation.getId());
					if(!"".equalsIgnoreCase(initiatorLocation.getCountry())){
						clientVendorIndex.setInitiatorCountry(initiatorLocation.getCountry());
					}
					if(!"".equalsIgnoreCase(initiatorLocation.getState())){
						clientVendorIndex.setInitiatorState(initiatorLocation.getState());
					}
					if(!"".equalsIgnoreCase(initiatorLocation.getCity())){
						clientVendorIndex.setInitiatorCity(initiatorLocation.getCity());
					}
					
					if(!"".equalsIgnoreCase(initiatorLocation.getCity())){
						clientVendorIndex.setInitiatorCity(initiatorLocation.getCity());
					}
					
					if(!"".equalsIgnoreCase(initiatorLocation.getLocation())){
						clientVendorIndex.setLocation(initiatorLocation.getLocation());
					}
					
					if(!"".equalsIgnoreCase(initiatorLocation.getZipCode())){
						clientVendorIndex.setZipCode(initiatorLocation.getZipCode());
					}
					
					
					if(!"".equalsIgnoreCase(initiatorLocation.getLatitude())){
						clientVendorIndex.setLatitude(initiatorLocation.getLatitude());
					}
					
					if(!"".equalsIgnoreCase(initiatorLocation.getLongitude())){
						clientVendorIndex.setLongitude(initiatorLocation.getLongitude());
					}
					
					 clientVendorIndex.setInitiatorOrgName(initiatorLocation.getOrgLocationName());
					 
					 if(null != initiatorLocation.getWebsite()){
						 clientVendorIndex.setInitiatorWebSite(initiatorLocation.getWebsite());
					 }
					
					
				  /* Org initiatorOrg = orgService.findOrgByID(initiatorLocation.getOrg().getId());
				       if(null != initiatorOrg){
						 clientVendorIndex.setInitiatorOrgName(initiatorOrg.getOrgName());
						 clientVendorIndex.setInitiatorWebSite(initiatorOrg.getWebSite());
					 }*/
					
				// this is to setting values for ResponderLocationId
				       
				OrgLocations  responderorgLocation = orgLocationService.getOrgLocations(orgRelations.getResponderLocationId().getId());
				if(null !=responderorgLocation){
					
					if(!"".equalsIgnoreCase(responderorgLocation.getCountry())){
						clientVendorIndex.setResponderCountry(responderorgLocation.getCountry());
					}
					if(!"".equalsIgnoreCase(responderorgLocation.getState())){
						clientVendorIndex.setResponderState(responderorgLocation.getState());
					}
					if(!"".equalsIgnoreCase(responderorgLocation.getCity())){
						clientVendorIndex.setResponderCity(responderorgLocation.getCity());
					}
					
					if(!"".equalsIgnoreCase(responderorgLocation.getLocation())){
						clientVendorIndex.setLocation(responderorgLocation.getLocation());
					}
					
					if(!"".equalsIgnoreCase(responderorgLocation.getZipCode())){
						clientVendorIndex.setZipCode(responderorgLocation.getZipCode());
					}
					
					if(!"".equalsIgnoreCase(responderorgLocation.getLatitude())){
						clientVendorIndex.setLatitude(responderorgLocation.getLatitude());
					}
					
					if(!"".equalsIgnoreCase(responderorgLocation.getLongitude())){
						clientVendorIndex.setLongitude(responderorgLocation.getLongitude());
					}
					
					clientVendorIndex.setResponderOrgName(responderorgLocation.getOrgLocationName());
					 if(null != responderorgLocation.getWebsite()){
						 clientVendorIndex.setResponderWebSite(responderorgLocation.getWebsite());
					 }
					 
					
					 /*Org responderOrg = orgService.findOrgByID(responderorgLocation.getOrg().getId());
				       if(null != responderOrg){
						 clientVendorIndex.setResponderOrgName(responderOrg.getOrgName());
						 clientVendorIndex.setResponderWebSite(responderOrg.getWebSite());
					 }*/
					
				}
			
		}
		
		if(null != orgRelations){
			clientVendorIndex.setOrgRelationId(orgRelations.getId());
			clientVendorIndex.setResponderType(orgRelations.getResponderType());
			clientVendorIndex.setInitiatorType(orgRelations.getInitiatorType());
			clientVendorIndex.setResponderLocationId(orgRelations.getResponderLocationId().getId());
			clientVendorIndex.setOrgLocationId(orgRelations.getLocationId().getId());
			clientVendorIndex.setInitiatorLevel(orgRelations.getInitiatorLevel());
			clientVendorIndex.setResponderLevel(orgRelations.getResponderLevel());
			clientVendorIndex.setOrgRelationUUID(orgRelations.getUUID());
			clientVendorIndex.setIsMSPVendor(orgRelations.getIsMSPVendor());
			if(orgRelations.getVendorOnboardTemplateId() != null){
				clientVendorIndex.setVendorOnboardTemplateId(orgRelations.getVendorOnboardTemplateId());
			}
			
			if(orgRelations.getVendorRelationStatus() != null){
				clientVendorIndex.setVendorRelationStatus(orgRelations.getVendorRelationStatus());
			}
			
			clientVendorIndex.setStatus(orgRelations.getStatus());
			clientVendorIndex.setCreatedByUserCode(orgRelations.getCreatedBy());
			
			User user = userService.getUserByUserCode(orgRelations.getCreatedBy());
			if(null != user){
				StringBuilder sb = new StringBuilder();
				clientVendorIndex.setCreatedBy(sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
			}
			
			 List<OrgSpoc> orgSpocsList  = orgSpocService.getAllSpocsByOrgRelationId(orgRelations.getId(), loginUser.getId(), loginOrgLoctn.getId(), responderType);
			 clientVendorIndex.setNoContacts((long)orgSpocsList.size());
			 clientVendorIndex.setCreatedDate(commonUtil.elkDateFormatter(orgRelations.getCreatedDate()));
			clientVendorIndex.setUpdatedDate(commonUtil.elkDateFormatter(orgRelations.getUpdatedDate()));
		}
		return clientVendorIndex;
	}
	
}
