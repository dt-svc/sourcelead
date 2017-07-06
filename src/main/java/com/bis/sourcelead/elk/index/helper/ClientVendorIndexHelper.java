package com.bis.sourcelead.elk.index.helper;

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
import com.bis.sourcelead.service.UserService;

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
	
	
	public ClientVendorIndex getClientVendorIndexFromCandidate(Org org,OrgLocations clientOrgLocations, String responderType,
			OrgSpoc orgSpoc, OrgRelations orgRelationsList, SubmissionTemplate submissionTemplate) throws Exception{
		
		ClientVendorIndex clientVendorIndex = new ClientVendorIndex();
		
		if(null != clientOrgLocations){
			
			// this is to setting values for Initiator Details
			OrgLocations initiatorLocation = orgLocationService.getOrgLocations(orgRelationsList.getLocationId().getId());
			
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
				       
				OrgLocations  responderorgLocation = orgLocationService.getOrgLocations(orgRelationsList.getResponderLocationId().getId());
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
		
		if(null != orgRelationsList){
			clientVendorIndex.setOrgRelationId(orgRelationsList.getId());
			clientVendorIndex.setResponderType(orgRelationsList.getResponderType());
			clientVendorIndex.setInitiatorType(orgRelationsList.getInitiatorType());
			clientVendorIndex.setResponderLocationId(orgRelationsList.getResponderLocationId().getId());
			clientVendorIndex.setOrgLocationId(orgRelationsList.getLocationId().getId());
			clientVendorIndex.setInitiatorLevel(orgRelationsList.getInitiatorLevel());
			clientVendorIndex.setResponderLevel(orgRelationsList.getResponderLevel());
			
			clientVendorIndex.setStatus(orgRelationsList.getStatus());
			clientVendorIndex.setCreatedByUserCode(orgRelationsList.getCreatedBy());
			
			User user = userService.getUserByUserCode(orgRelationsList.getCreatedBy());
			if(null != user){
				StringBuilder sb = new StringBuilder();
				clientVendorIndex.setCreatedBy(sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
			}
			
			clientVendorIndex.setCreatedDate(Constants.DF_YMD.format(orgRelationsList.getCreatedDate()));
			clientVendorIndex.setUpdatedDate(Constants.DF_YMD.format(orgRelationsList.getUpdatedDate()));
		}
		return clientVendorIndex;
	}
	
}
