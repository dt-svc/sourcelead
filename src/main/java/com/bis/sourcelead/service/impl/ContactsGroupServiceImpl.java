package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ContactsGroupDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dto.ContactsGroupDTO;
import com.bis.sourcelead.entity.ContactsGroup;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ContactsGroupService;
import com.bis.sourcelead.service.UserService;

@Service
public class ContactsGroupServiceImpl implements ContactsGroupService{
	
	
	@Autowired
	UserDao userDao;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	ContactsGroupDao contactsGroupDao;
	
	@Autowired
	UserService userService;

	@Override
	public void addContactsGroup(ContactsGroupDTO contactsGroupDTO, User user) throws Exception
	{
		ContactsGroup contactsGroup = null;
		
		if(StringUtils.isNotBlank(contactsGroupDTO.getGroupId())){
			contactsGroup = contactsGroupDao.getContactsGroupByGroupId(Long.parseLong(contactsGroupDTO.getGroupId()));
		}else{
			contactsGroup = new ContactsGroup();
		}
			
		if(StringUtils.isNoneBlank(contactsGroupDTO.getGroupName())){
			contactsGroup.setGroupName(contactsGroupDTO.getGroupName());
		}
		
	    contactsGroup.setCreatedUser(user);
		contactsGroup.setStatus(Constants.RECORD_ACTIVE);
		contactsGroup.setCreatedDate(new Date());
		contactsGroup.setUpdatedDate(new Date());
		contactsGroupDao.saveContactsGroup(contactsGroup);
	}

	@Override
	public ContactsGroup saveContactsGroup(ContactsGroup contactsGroup) throws Exception
	{
		return contactsGroupDao.saveContactsGroup(contactsGroup);
	}

	@Override
	public List<ContactsGroup> getContactsGroupListByUserId(Long userId) throws Exception
	{
		return contactsGroupDao.getContactsGroupListByUserId(userId);
	}

	@Override
	public void deleteContactsGroupById(Long groupId) throws Exception
	{
		ContactsGroup contactsGroup = contactsGroupDao.getContactsGroupByGroupId(groupId);
		contactsGroupDao.deleteContactsGroup(contactsGroup);
	}

	@Override
	public ContactsGroup getContactsGroupByGroupId(Long groupId) throws Exception
	{
		return contactsGroupDao.getContactsGroupByGroupId(groupId);
	}

	@Override
	public void saveContactsReferContactGroup(Long groupId, String contactsList) throws Exception
	{
		contactsGroupDao.saveContactsReferContactGroup(groupId,contactsList);
		
	}

	@Override
	public ContactsGroup findByContactsGroupAndUserId(String groupName, Long userId) throws Exception
	{
		return contactsGroupDao.findByContactsGroupAndUserId(groupName, userId);
	}

	@Override
	public ContactsGroup validateGrouppNameByGroupNameAndGroupId(String groupName, Long groupId) throws Exception {
		return contactsGroupDao.validateGrouppNameByGroupNameAndGroupId(groupName, groupId);
	}

	@Override
	public Boolean validateGroupNameByGroupNameAndUserId(String groupName, Long userId) throws Exception {
		return contactsGroupDao.validateGroupNameByGroupNameAndUserId(groupName, userId);
	}
	

}
