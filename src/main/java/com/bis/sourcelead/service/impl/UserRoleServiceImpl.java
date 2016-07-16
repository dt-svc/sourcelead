package com.bis.sourcelead.service.impl;

import java.util.Collection;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);
	
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

	public Collection<UserRole> getUsersListByLocationId(Long locationId,String userState,Long userId) throws Exception {
		logger.debug("In UserRoleService class...");
		return userRoleDao.getUsersListByLocationId(locationId,userState,userId);
	}

	@Override
	public Collection<UserRole> getUsersListByRoleAndLocationId(String roleName,Long locationId,String userState,Long userId) throws Exception {
		logger.debug("In UserRoleService class...");
		return userRoleDao.getUsersListByRoleAndLocationId(roleName, locationId,userState,userId);
	}

	@Override
	public Collection<UserRole> getUsersListByUserNameAndLocationId(String userName, Long locationId,String userState,Long userId) throws Exception {
		logger.debug("In UserRoleService class...");
		return userRoleDao.getUsersListByUserNameAndLocationId(userName, locationId,userState,userId);
	}

	@Override
	public Collection<UserRole> getUsersListByRoleAndNameAndLocationId(String roleName, String userName,
			Long locationId,String userState,Long userId) throws Exception {
		logger.debug("In UserRoleService class...");
		return userRoleDao.getUsersListByRoleAndNameAndLocationId(roleName, userName, locationId,userState,userId);
	}

	@Override
	public Collection<UserRole> getUsersListByUserStateAndLocationId(String userState, Long locationId,Long userId)
			throws Exception {
		logger.debug("In UserRoleService class...");
		return userRoleDao.getUsersListByUserStateAndLocationId(userState, locationId,userId);
	}
	
	public Collection<UserRole> getUsersBasedOnUserRoles() throws Exception {
		// TODO Auto-generated method stub
		return userRoleDao.getUsersBasedOnUserRoles();
	}

}
