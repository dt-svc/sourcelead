package com.bis.sourcelead.service.impl;

import java.util.List;

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

	public List<UserRole> getUsersListByLocationId(Long locationId,String userState,Long userId) throws Exception {
		logger.debug("In UserRoleService class...");
		return userRoleDao.getUsersListByLocationId(locationId,userState,userId);
	}

	@Override
	public List<UserRole> getUsersListByRoleAndLocationId(Long roleId,Long locationId,String userState,Long userId) throws Exception {
		logger.debug("In UserRoleService class...");
		return userRoleDao.getUsersListByRoleAndLocationId(roleId, locationId,userState,userId);
	}

	@Override
	public List<UserRole> getUsersListByUserNameAndLocationId(String userName, Long locationId,String userState,Long userId) throws Exception {
		logger.debug("In UserRoleService class...");
		return userRoleDao.getUsersListByUserNameAndLocationId(userName, locationId,userState,userId);
	}

	@Override
	public List<UserRole> getUsersListByRoleAndNameAndLocationId(Long roleId, String userName,
			Long locationId,String userState,Long userId) throws Exception {
		logger.debug("In UserRoleService class...");
		return userRoleDao.getUsersListByRoleAndNameAndLocationId(roleId, userName, locationId,userState,userId);
	}

	@Override
	public List<UserRole> getUsersListByUserStateAndLocationId(String userState, Long locationId,Long userId)
			throws Exception {
		logger.debug("In UserRoleService class...");
		return userRoleDao.getUsersListByUserStateAndLocationId(userState, locationId,userId);
	}
	
	public List<UserRole> getUsersBasedOnUserRoles() throws Exception {
		return userRoleDao.getUsersBasedOnUserRoles();
	}

	@Override
	public List<UserRole> getApproverAssignedUsers(List<Long> projectIds, String userState, String searchByName)
			throws Exception {
		return userRoleDao.getApproverAssignedUsers(projectIds,userState,searchByName);
	}

	@Override
	public UserRole getUserDetailsByEmailAndLocationId(String email,Long locationId) throws Exception {
		return userRoleDao.getUserDetailsByEmailAndLocationId(email,locationId);
	}
	
	

}
