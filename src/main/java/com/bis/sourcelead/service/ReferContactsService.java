package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.ReferContacts;

public interface ReferContactsService {

	ReferContacts addReferContacts(ReferContacts referContacts) throws Exception;
	
	ReferContacts getReferContactsByUUID(String contactUUID) throws Exception;
}
