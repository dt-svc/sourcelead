package com.bis.sourcelead.elk.index.helper;

import org.springframework.stereotype.Component;

import com.bis.sourcelead.elk.index.ClientVendorContactsIndex;
import com.bis.sourcelead.entity.OrgSpoc;

@Component
public class ClientVendorContactsIndexHelper {
	
	public ClientVendorContactsIndex getClientVendorContactIndexFromSpoc(OrgSpoc orgSpoc) throws Exception{
		
		ClientVendorContactsIndex clientVendorContactsIndex = new ClientVendorContactsIndex();
		
		if(null != orgSpoc){
			
			clientVendorContactsIndex.setId(orgSpoc.getId());
			clientVendorContactsIndex.setOrgLocationId(orgSpoc.getInitiatorLocationId().getId());
			clientVendorContactsIndex.setOrgRelationId(orgSpoc.getOrgRelations().getId());
			clientVendorContactsIndex.setOrgName(orgSpoc.getResponderLocationId().getOrg().getOrgName());
			//clientVendorContactsIndex.setOwnerName(orgSpoc.geto);
			clientVendorContactsIndex.setContactEmail(orgSpoc.getSpocUser().getEmail());
			clientVendorContactsIndex.setPhoneNumber(orgSpoc.getSpocUser().getMobilePhone());
			clientVendorContactsIndex.setInvitationStatus(orgSpoc.getInvitationStatus());
			clientVendorContactsIndex.setContactStatus(orgSpoc.getSpocStatus());
			
			clientVendorContactsIndex.setSpocName(orgSpoc.getSpocUser().getFirstName());
			clientVendorContactsIndex.setTempOwnerId(orgSpoc.getSpocUser().getId());
			
		
		}
		return clientVendorContactsIndex;
	}
	
}
