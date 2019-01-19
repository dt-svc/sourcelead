package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.ContactsGroupDTO;
import com.bis.sourcelead.entity.ContactsGroup;
import com.bis.sourcelead.entity.User;

public interface ContactsGroupService {
	
	
	public void addContactsGroup(ContactsGroupDTO contactsGroupDTO,User user) throws Exception;
	
	ContactsGroup saveContactsGroup(ContactsGroup contactsGroup) throws Exception;
	
	List<ContactsGroup> getContactsGroupListByUserId(Long userId) throws Exception;
	
	void deleteContactsGroupById(Long groupId) throws Exception;
	
	public ContactsGroup getContactsGroupByGroupId(Long groupId) throws Exception;
	
	void saveContactsReferContactGroup(Long groupId,String contactsList) throws Exception;
	
	public ContactsGroup findByContactsGroupAndUserId(String groupName, Long userId) throws Exception;
	
	public ContactsGroup validateGrouppNameByGroupNameAndGroupId(String groupName, Long groupId) throws Exception;
	
	Boolean validateGroupNameByGroupNameAndUserId(String groupName,Long userId)throws Exception;
}
