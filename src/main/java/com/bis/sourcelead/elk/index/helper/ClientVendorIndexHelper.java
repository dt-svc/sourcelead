package com.bis.sourcelead.elk.index.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.elk.index.ClientVendorIndex;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.UserService;

@Component
public class ClientVendorIndexHelper {
	
	

	@Autowired
	OrgRelationsService orgRelationsService;
	

	@Autowired
	UserService userService;
	
	
	public ClientVendorIndex getClientVendorIndexFromCandidate(Org org,OrgLocations clientOrgLocations, String responderType,
			OrgSpoc orgSpoc, OrgRelations orgRelationsList, SubmissionTemplate submissionTemplate) throws Exception{
		
		ClientVendorIndex clientVendorIndex = new ClientVendorIndex();
		
		if(null != clientOrgLocations){
			
				if(!"".equalsIgnoreCase(clientOrgLocations.getCountry())){
					clientVendorIndex.setCountry(clientOrgLocations.getCountry());
				}
				if(!"".equalsIgnoreCase(clientOrgLocations.getState())){
					clientVendorIndex.setState(clientOrgLocations.getState());
				}
				if(!"".equalsIgnoreCase(clientOrgLocations.getCity())){
					clientVendorIndex.setCity(clientOrgLocations.getCity());
				}
			
		}
		if(null != org){
			
			clientVendorIndex.setOrgName(org.getOrgName());
			clientVendorIndex.setWebsite(org.getWebSite());
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
				clientVendorIndex.setCreatedBy(sb.append(user.getFirstName()).append("").append(user.getLastName()).toString());
			}
		}
		return clientVendorIndex;
	}
	
}
