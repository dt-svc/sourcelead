package com.bis.sourcelead.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.UserCustomFieldDataDao;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
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

	@Override
	public Map<Long, String[]> getUserCustomFieldDataByUserId(Long orgId, List<CustomFormFieldDTO> formFieldList,	Long userId) {
		return userCustomFieldDataDao.getUserCustomFieldDataByUserId(orgId,formFieldList,userId);
	}

	@Override
	public UserCustomFieldData getUserCustomFieldDataById(Long entityId) {
		return userCustomFieldDataDao.getUserCustomFieldDataById(entityId);
	}}
