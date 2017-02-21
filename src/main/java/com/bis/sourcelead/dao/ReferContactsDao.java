package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.ReferContacts;

public interface ReferContactsDao {
	
	ReferContacts addReferContacts(ReferContacts referContacts) throws Exception;
	
	ReferContacts getReferContactsByUUID(String contactUUID) throws Exception;

}
