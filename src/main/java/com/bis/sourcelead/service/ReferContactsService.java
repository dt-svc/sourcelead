package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.ReferContactsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.entity.User;

public interface ReferContactsService {

	ReferContacts addReferContacts(ReferContacts referContacts) throws Exception;
	
	ReferContacts getReferContactsByUUID(String contactUUID) throws Exception;
	
	ResponseList<ReferContactsDTO> getReferContactsListByUserIdAndOrglocationId(ReferContactsDTO contactsDTO, Long userId,Long orgLocationId) throws Exception;

	ReferContacts getReferContactsByEmails(Long userId,String toEmail) throws Exception;

	public void saveuserContacts(User user,ReferContactsDTO referContactsDTO) throws Exception;
	
	public void saveuserContactsFromConnections(User fromUser, User toUser,OrgLocations loginOrgLocation, String sourceleadUrl, String sourceleadLoginUrl) throws Exception;
	
	 void saveAllReferContacts(List<ReferContacts> referContactsList) throws Exception;
	
	List<ReferContacts> getUserContactsListByUserIdAndRequirementId(Long userId,Long requirementId) throws Exception;
	
	ReferContacts getReferContactsById(Long id) throws Exception;
	
	Boolean validateGoogleEamil(String email, Long loginUserId) throws Exception;

	ReferContacts findByEmail(String recipientMail)throws Exception;
	
	ReferContacts getReferContactsByFromUserIdAndToUserId(Long fromUserId, Long toUserId) throws Exception;
	
	boolean deleteContactById(Long id) throws Exception;

	

}
