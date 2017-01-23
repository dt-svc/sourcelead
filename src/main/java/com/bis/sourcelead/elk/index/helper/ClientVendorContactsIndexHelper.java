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
			//clientVendorContactsIndex.setOrgName(orgSpoc.getResponderLocationId().getOrg().getOrgName());
			//clientVendorContactsIndex.setSpocEmail(orgSpoc.getSpocUser().getEmail());
			//clientVendorContactsIndex.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
			clientVendorContactsIndex.setInvitationStatus(orgSpoc.getInvitationStatus());
			clientVendorContactsIndex.setSpocStatus(orgSpoc.getSpocStatus());
			clientVendorContactsIndex.setSpocUserId(orgSpoc.getSpocUser().getId());
			clientVendorContactsIndex.setOwnerUserId(orgSpoc.getOwnerUser().getId());
			clientVendorContactsIndex.setSpocLocationId(orgSpoc.getResponderLocationId().getId());
			//clientVendorContactsIndex.setSpocName(orgSpoc.getSpocUser().getFirstName());
			//clientVendorContactsIndex.setTempOwnerId(orgSpoc.getSpocUser().getId());
			//clientVendorContactsIndex.setUserId(orgSpoc.getOwnerUser().getId());
			
			
			clientVendorContactsIndex.setSpocUserFirstName(orgSpoc.getSpocUser().getFirstName());
			clientVendorContactsIndex.setSpocUserLastName(orgSpoc.getSpocUser().getLastName());
			clientVendorContactsIndex.setOwnerUserFirstName(orgSpoc.getOwnerUser().getFirstName());
			clientVendorContactsIndex.setOwnerUserLastName(orgSpoc.getOwnerUser().getLastName());
			clientVendorContactsIndex.setSpocUserEmail(orgSpoc.getSpocUser().getEmail());
			clientVendorContactsIndex.setSpocUserPhNo(orgSpoc.getSpocUser().getMobilePhone());
			clientVendorContactsIndex.setOwnerUserEmail(orgSpoc.getOwnerUser().getEmail());
			clientVendorContactsIndex.setOwnerUserPhNo(orgSpoc.getOwnerUser().getMobilePhone());
			clientVendorContactsIndex.setInitiatorOrgName(orgSpoc.getInitiatorLocationId().getOrg().getOrgName());
			clientVendorContactsIndex.setResponderOrgName(orgSpoc.getResponderLocationId().getOrg().getOrgName());
		
		}
		return clientVendorContactsIndex;
	}
	
}
