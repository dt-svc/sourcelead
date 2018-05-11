package com.bis.sourcelead.elk.index.helper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.elk.index.ClientVendorContactsIndex;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;

@Component
public class ClientVendorContactsIndexHelper {
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	
	public ClientVendorContactsIndex getClientVendorContactIndexFromSpoc(OrgSpoc orgSpoc) throws Exception{
		
		ClientVendorContactsIndex clientVendorContactsIndex = new ClientVendorContactsIndex();
		
		if(null != orgSpoc){
			
			clientVendorContactsIndex.setId(orgSpoc.getId());
			clientVendorContactsIndex.setVendorLocationId(orgSpoc.getVendorLocationId().getId());
			clientVendorContactsIndex.setOrgRelationId(orgSpoc.getOrgRelations().getId());
			clientVendorContactsIndex.setOrgName(orgSpoc.getClientLocationId().getOrgLocationName());
			//clientVendorContactsIndex.setSpocEmail(orgSpoc.getSpocUser().getEmail());
			//clientVendorContactsIndex.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
			clientVendorContactsIndex.setInvitationStatus(orgSpoc.getInvitationStatus());
			clientVendorContactsIndex.setSpocStatus(orgSpoc.getSpocStatus());
			clientVendorContactsIndex.setSpocUserId(orgSpoc.getClientUser().getId());
			clientVendorContactsIndex.setStatus(orgSpoc.getStatus());
			User ownerUser = userService.getUserById(orgSpoc.getVendorUser().getId());
			
			clientVendorContactsIndex.setCreatedDate(commonUtil.elkDateFormatter(orgSpoc.getCreatedDate()));
			clientVendorContactsIndex.setUpdatedDate(commonUtil.elkDateFormatter(orgSpoc.getUpdatedDate()));
			
			
			if(null != ownerUser){
				
				clientVendorContactsIndex.setVendorUserFirstName(ownerUser.getFirstName());
				clientVendorContactsIndex.setVendorUserLastName(ownerUser.getLastName());
				
				if(StringUtils.isNoneBlank(ownerUser.getLastName())) {
					StringBuilder fullName = new StringBuilder();
					fullName = fullName.append(ownerUser.getFirstName()).append(" ").append(ownerUser.getLastName());
					clientVendorContactsIndex.setVendorUserName(fullName.toString());
				}else {
					clientVendorContactsIndex.setVendorUserName(ownerUser.getFirstName());
				}
				
				
				clientVendorContactsIndex.setVendorUserId(orgSpoc.getVendorUser().getId());
				clientVendorContactsIndex.setVendorUserEmail(ownerUser.getEmail());
				clientVendorContactsIndex.setVendorUserPhNo(ownerUser.getMobilePhone());
			}
			clientVendorContactsIndex.setClientLocationId(orgSpoc.getClientLocationId().getId());
			
			//clientVendorContactsIndex.setSpocName(orgSpoc.getSpocUser().getFirstName());
			//clientVendorContactsIndex.setTempOwnerId(orgSpoc.getSpocUser().getId());
			//clientVendorContactsIndex.setUserId(orgSpoc.getOwnerUser().getId());
			
			
			//  Storing the value of OrgRelations InitiatorType and ResponderType
			clientVendorContactsIndex.setInitiatorType(orgSpoc.getOrgRelations().getInitiatorType());
			clientVendorContactsIndex.setResponderType(orgSpoc.getOrgRelations().getResponderType());
			User spockUser = userService.getUserById(orgSpoc.getClientUser().getId());
			
            if(null != spockUser){
            	
            	
            	if(StringUtils.isNoneBlank(spockUser.getLastName())) {
					StringBuilder fullName = new StringBuilder();
					fullName = fullName.append(spockUser.getFirstName()).append(" ").append(spockUser.getLastName());
					clientVendorContactsIndex.setClientUserName(fullName.toString());
				}else {
					clientVendorContactsIndex.setClientUserName(spockUser.getFirstName());
				}
            	
            	clientVendorContactsIndex.setClientUserFirstName(spockUser.getFirstName());
    			clientVendorContactsIndex.setClientUserLastName(spockUser.getLastName());
    			clientVendorContactsIndex.setClientUserEmail(spockUser.getEmail());
    			clientVendorContactsIndex.setClientUserPhNo(spockUser.getMobilePhone());
    			clientVendorContactsIndex.setClientUserId(orgSpoc.getClientUser().getId());
            	
            }
            
            if(orgSpoc.getOwnerUser() != null) {
            	clientVendorContactsIndex.setOwnerUserId(orgSpoc.getOwnerUser().getId());
            }
            
			clientVendorContactsIndex.setVendorOrgName(orgSpoc.getVendorLocationId().getOrgLocationName());
			clientVendorContactsIndex.setClientOrgName(orgSpoc.getClientLocationId().getOrgLocationName());
			clientVendorContactsIndex.setOrgSpocUUUID(orgSpoc.getUUID());
		
		}
		return clientVendorContactsIndex;
	}
	
}
