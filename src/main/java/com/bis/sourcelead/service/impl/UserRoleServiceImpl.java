package com.bis.sourcelead.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService{
	
	@Autowired
	UserRoleDao userRoleDao;

	@Override
	public UserRole addUserRole(UserRole userRole) throws Exception {
		return userRoleDao.addUserRole(userRole);
	}

	@Override
	public UserRole getLoginUserPrimaryRole(Long userId) throws Exception{
		return userRoleDao.getLoginUserPrimaryRole(userId);
	}

	@Override
	public Collection<UserRole> getUsersBasedOnUserRoles() throws Exception {
		// TODO Auto-generated method stub
		return userRoleDao.getUsersBasedOnUserRoles();
	}

}
