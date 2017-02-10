package com.bis.sourcelead.elk.index.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bis.sourcelead.elk.index.ClientVendorContactsIndex;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.UserService;

@Component
public class ClientVendorContactsIndexHelper {
	
	
	@Autowired
	private UserService userService;
	
	
	public ClientVendorContactsIndex getClientVendorContactIndexFromSpoc(OrgSpoc orgSpoc) throws Exception{
		
		ClientVendorContactsIndex clientVendorContactsIndex = new ClientVendorContactsIndex();
		
		if(null != orgSpoc){
			
			clientVendorContactsIndex.setId(orgSpoc.getId());
			clientVendorContactsIndex.setInitiatorLocationId(orgSpoc.getInitiatorLocationId().getId());
			clientVendorContactsIndex.setOrgRelationId(orgSpoc.getOrgRelations().getId());
			clientVendorContactsIndex.setOrgName(orgSpoc.getResponderLocationId().getOrg().getOrgName());
			//clientVendorContactsIndex.setSpocEmail(orgSpoc.getSpocUser().getEmail());
			//clientVendorContactsIndex.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
			clientVendorContactsIndex.setInvitationStatus(orgSpoc.getInvitationStatus());
			clientVendorContactsIndex.setSpocStatus(orgSpoc.getSpocStatus());
			clientVendorContactsIndex.setSpocUserId(orgSpoc.getSpocUser().getId());
			clientVendorContactsIndex.setStatus(orgSpoc.getStatus());
			User ownerUser = userService.getUserById(orgSpoc.getOwnerUser().getId());
			
			
			if(null != ownerUser){
				
				clientVendorContactsIndex.setOwnerUserFirstName(ownerUser.getFirstName());
				clientVendorContactsIndex.setOwnerUserLastName(ownerUser.getLastName());
				clientVendorContactsIndex.setOwnerUserId(orgSpoc.getOwnerUser().getId());
				clientVendorContactsIndex.setOwnerUserEmail(ownerUser.getEmail());
				clientVendorContactsIndex.setOwnerUserPhNo(ownerUser.getMobilePhone());
			}
			clientVendorContactsIndex.setResponderLocationId(orgSpoc.getResponderLocationId().getId());
			
			//clientVendorContactsIndex.setSpocName(orgSpoc.getSpocUser().getFirstName());
			//clientVendorContactsIndex.setTempOwnerId(orgSpoc.getSpocUser().getId());
			//clientVendorContactsIndex.setUserId(orgSpoc.getOwnerUser().getId());
			
			
			//  Storing the value of OrgRelations InitiatorType and ResponderType
			clientVendorContactsIndex.setInitiatorType(orgSpoc.getOrgRelations().getInitiatorType());
			clientVendorContactsIndex.setResponderType(orgSpoc.getOrgRelations().getResponderType());
			User spockUser = userService.getUserById(orgSpoc.getSpocUser().getId());
			
            if(null != spockUser){
            	
            	clientVendorContactsIndex.setSpocUserFirstName(spockUser.getFirstName());
    			clientVendorContactsIndex.setSpocUserLastName(spockUser.getLastName());
    			clientVendorContactsIndex.setSpocUserEmail(spockUser.getEmail());
    			clientVendorContactsIndex.setSpocUserPhNo(spockUser.getMobilePhone());
            	
            }
			
			clientVendorContactsIndex.setInitiatorOrgName(orgSpoc.getInitiatorLocationId().getOrg().getOrgName());
			clientVendorContactsIndex.setResponderOrgName(orgSpoc.getResponderLocationId().getOrg().getOrgName());
		
		}
		return clientVendorContactsIndex;
	}
	
}
