package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.ReferContactsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.ReferContacts;

public interface ReferContactsDao {
	
	ReferContacts addReferContacts(ReferContacts referContacts) throws Exception;
	
	ReferContacts getReferContactsByUUID(String contactUUID) throws Exception;
	
    void saveAllReferContacts(List<ReferContacts> referContactsList) throws Exception;
	
	ReferContacts getReferContactsByEmails(Long userId,String toEmail) throws Exception;

	ResponseList<ReferContactsDTO> getReferContactsListByUserIdAndOrglocationId(ReferContactsDTO contactsDTO,Long userId,Long orgLocationId) throws Exception;
	
	List<ReferContacts> getUserContactsListByUserIdAndRequirementId(Long userId,Long requirementId) throws Exception;
	
	ReferContacts getReferContactsById(Long id) throws Exception;
	
	Boolean validateGoogleEamil(String email, Long loginUserId) throws Exception;

	ReferContacts findByEmail(String recipientMail)throws Exception;
	
	ReferContacts getReferContactsByFromUserIdAndToUserId(Long fromUserId, Long toUserId) throws Exception;
	
	ReferContacts deleteContactById(ReferContacts referContacts) throws Exception;

}
