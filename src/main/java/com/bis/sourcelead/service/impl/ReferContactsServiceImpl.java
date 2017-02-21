package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ReferContactsDao;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.service.ReferContactsService;

@Service
public class ReferContactsServiceImpl implements ReferContactsService{
	
	@Autowired
	ReferContactsDao referContactsDao;

	@Override
	public ReferContacts addReferContacts(ReferContacts referContacts) throws Exception {
		return referContactsDao.addReferContacts(referContacts);
	}

	@Override
	public ReferContacts getReferContactsByUUID(String contactUUID) throws Exception {
		return referContactsDao.getReferContactsByUUID(contactUUID);
	}

}
