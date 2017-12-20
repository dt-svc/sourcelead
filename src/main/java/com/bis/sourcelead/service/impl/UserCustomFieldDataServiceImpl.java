package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.UserCustomFieldDataDao;
import com.bis.sourcelead.entity.UserCustomFieldData;
import com.bis.sourcelead.service.UserCustomFieldDataService;

@Service
public class UserCustomFieldDataServiceImpl implements UserCustomFieldDataService {

	@Autowired
	private UserCustomFieldDataDao userCustomFieldDataDao;
	
	@Override
	public UserCustomFieldData saveUserCustomFieldData(UserCustomFieldData userCustomFields) {
		return userCustomFieldDataDao.saveUserCustomFieldData(userCustomFields);
	}

}
