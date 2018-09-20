package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ContactsGroup;

public interface ContactsGroupDao {
	
	ContactsGroup saveContactsGroup(ContactsGroup contactsGroup) throws Exception;
	
	List<ContactsGroup> getContactsGroupListByUserId(Long userId) throws Exception;
	
	public ContactsGroup getContactsGroupByGroupId(Long groupId) throws Exception;
	
	public ContactsGroup deleteContactsGroupId(Long groupId) throws Exception;
	
	void saveContactsReferContactGroup(Long groupId,String contactsList) throws Exception;
	
	void deleteContactsGroup(ContactsGroup contactsGroup) throws Exception;
	
	public ContactsGroup findByContactsGroupAndUserId(String groupName, Long userId) throws Exception;
	
    public ContactsGroup validateGrouppNameByGroupNameAndGroupId(String groupName, Long groupId) throws Exception;
	
	Boolean validateGroupNameByGroupNameAndUserId(String groupName,Long userId)throws Exception;
}
