package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.UserMailBoxDao;
import com.bis.sourcelead.entity.UserMailBox;
import com.bis.sourcelead.service.UserMailBoxService;

@Service
public class UserMailBoxServiceImpl implements UserMailBoxService{
	
	@Autowired
	UserMailBoxDao userMailBoxDao;

	@Override
	public UserMailBox saveUserMailBox(UserMailBox userMailBox) throws Exception {
		return userMailBoxDao.saveUserMailBox(userMailBox);
	}

	@Override
	public List<UserMailBox> getUserMailBoxByUserIdAndOrgLocationIdAndMailStatus(Long userId, Long orgLocationId,
			String mailStatus) throws Exception {
		return userMailBoxDao.getUserMailBoxByUserIdAndOrgLocationIdAndMailStatus(userId, orgLocationId, mailStatus);
	}

	@Override
	public UserMailBox getUserMailBoxByUUID(String UUID) {
		return userMailBoxDao.getUserMailBoxByUUID(UUID);
	}

	
}
