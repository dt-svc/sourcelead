package com.bis.sourcelead.service;

import java.util.Collection;
import java.util.List;

import com.bis.sourcelead.entity.UserRole;

public interface UserRoleService {
	
	public UserRole addUserRole(UserRole userRole) throws Exception;
	
	public UserRole getLoginUserPrimaryRole(Long userId) throws Exception;
	
	public List<UserRole> getUsersListByLocationId(Long locationId,String userState,Long userId) throws Exception;
	
	public List<UserRole> getUsersListByRoleAndLocationId(Long roleId,Long locationId,String userState,Long userId) throws Exception;
	
	public List<UserRole> getUsersListByUserNameAndLocationId(String userName,Long locationId,String userState,Long userId) throws Exception;
	
	public List<UserRole> getUsersListByRoleAndNameAndLocationId(Long roleId,String userName,Long locationId,String userState,Long userId) throws Exception;
	
	public List<UserRole> getUsersListByUserStateAndLocationId(String userState,Long locationId,Long userId) throws Exception;

	public List<UserRole> getUsersBasedOnUserRoles() throws Exception;
	
	public List<UserRole> getApproverAssignedUsers(List<Long> projectIds,String userState,String searchByName) throws Exception;
	
	public UserRole getUserDetailsByEmailAndLocationId(String email,Long locationId) throws Exception;
	
}
