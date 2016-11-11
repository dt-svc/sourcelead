package com.bis.sourcelead.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.User;
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
	public UserRole getNotPrimaryUser(Long userId) throws Exception{
		return userRoleDao.getNotPrimaryUser(userId);
	}

	@Override

	public List<UserRole> getUsersListByLocationId(Long locationId,String userState,Long userId,String loginUserRole,String userCode) throws Exception {
		logger.debug("In UserRoleService class...");
		return userRoleDao.getUsersListByLocationId(locationId,userState,userId,loginUserRole,userCode);
	}

	@Override
	public List<UserRole> getUsersListByRoleAndLocationId(Long roleId,Long locationId,String userState,Long userId,String loginUserRole,String userCode) throws Exception {
		logger.debug("In UserRoleService class...");
		return userRoleDao.getUsersListByRoleAndLocationId(roleId, locationId,userState,userId,loginUserRole,userCode);
	}

	@Override
	public List<UserRole> getUsersListByUserNameAndLocationId(String userName, Long locationId,String userState,Long userId,String loginUserRole,String userCode) throws Exception {
		logger.debug("In UserRoleService class...");
		return userRoleDao.getUsersListByUserNameAndLocationId(userName, locationId,userState,userId,loginUserRole,userCode);
	}

	@Override
	public List<UserRole> getUsersListByRoleAndNameAndLocationId(Long roleId, String userName,
			Long locationId,String userState,Long userId,String loginUserRole,String userCode) throws Exception {
		logger.debug("In UserRoleService class...");
		return userRoleDao.getUsersListByRoleAndNameAndLocationId(roleId, userName, locationId,userState,userId,loginUserRole,userCode);
	}

	@Override
	public List<UserRole> getUsersListByUserStateAndLocationId(String userState, Long locationId,Long userId,String loginUserRole,String userCode)
			throws Exception {
		logger.debug("In UserRoleService class...");
		return userRoleDao.getUsersListByUserStateAndLocationId(userState, locationId,userId,loginUserRole,userCode);
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
	
	@Override
	public boolean isUserActive(Long userId) throws Exception {
		return userRoleDao.isUserActive(userId);
	}
	
	/*@Override
	public List<UserRole> getManagersByUserRolesAndOrgLocationId(String roleType , Long orgLocationsId) throws Exception {
		return userRoleDao.getManagersByUserRolesAndOrgLocationId(roleType, orgLocationsId);
	}
	
	@Override
	public List<UserRole> getManagersByUserIdAndUserRolesAndOrgLocationId(Long userId,String roleType , Long orgLocationsId) throws Exception {
		return userRoleDao.getManagersByUserIdAndUserRolesAndOrgLocationId(userId, roleType, orgLocationsId);
	}*/
	
	@Override
	public List<Long> getAllUserIdsByReportingManagerId(Long reportingManagerId) throws Exception {
		return userRoleDao.getAllUserIdsByReportingManagerId(reportingManagerId);
	}
	
	@Override
	public UserRole getUserRoleByUserIdAndLocationId(Long userId,Long locationId) throws Exception {
		return userRoleDao.getUserRoleByUserIdAndLocationId(userId,locationId);
	}

	@Override
	public List<UserRole> getUserSpocsListByUserId(Long userId) throws Exception {
		return userRoleDao.getUserSpocsListByUserId(userId);
	}

	@Override
	public UserRole getUserByUserId(Long userId) throws Exception {
		return userRoleDao.getUserByUserId(userId);
	}
	
	

}
