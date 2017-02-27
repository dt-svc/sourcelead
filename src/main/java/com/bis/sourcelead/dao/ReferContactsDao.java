package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ReferContacts;

public interface ReferContactsDao {
	
	ReferContacts addReferContacts(ReferContacts referContacts) throws Exception;
	
	ReferContacts getReferContactsByUUID(String contactUUID) throws Exception;
	
	ReferContacts getReferContactsByEmails(Long userId,String toEmail) throws Exception;

	List<ReferContacts> getReferContactsListByUserIdAndOrglocationId(Long userId,Long orgLocationId) throws Exception;
}
