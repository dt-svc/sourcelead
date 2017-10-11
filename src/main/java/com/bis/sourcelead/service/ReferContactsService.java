package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.ReferContactsDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.entity.User;

public interface ReferContactsService {

	ReferContacts addReferContacts(ReferContacts referContacts) throws Exception;
	
	ReferContacts getReferContactsByUUID(String contactUUID) throws Exception;
	
	List<ReferContacts> getReferContactsListByUserIdAndOrglocationId(Long userId,Long orgLocationId) throws Exception;

	ReferContacts getReferContactsByEmails(Long userId,String toEmail) throws Exception;

	public void saveuserContacts(User user, OrgLocations orgLocations,ReferContactsDTO referContactsDTO) throws Exception;
	
	 void saveAllReferContacts(List<ReferContacts> referContactsList) throws Exception;
	
	List<ReferContacts> getUserContactsListByUserIdAndRequirementId(Long userId,Long requirementId) throws Exception;
	
	ReferContacts getReferContactsById(Long id) throws Exception;

}
