package com.bis.sourcelead.dao;

import java.util.Collection;
import java.util.List;

import com.bis.sourcelead.entity.UserRole;

public interface UserRoleDao {

	public UserRole addUserRole(UserRole userRole) throws Exception;
	
	public UserRole getLoginUserPrimaryRole(Long userId) throws Exception;

	public Collection<UserRole> getUsersListByLocationId(Long locationId,String userState,Long userId) throws Exception;
	
	public Collection<UserRole> getUsersListByRoleAndLocationId(Long roleId,Long locationId,String userState,Long userId) throws Exception;
	
	public Collection<UserRole> getUsersListByUserNameAndLocationId(String userName,Long locationId,String userState,Long userId) throws Exception;
	
	public Collection<UserRole> getUsersListByRoleAndNameAndLocationId(Long roleId,String userName,Long locationId,String userState,Long userId) throws Exception;
	
	public Collection<UserRole> getUsersListByUserStateAndLocationId(String userState,Long locationId,Long userId) throws Exception;

	public Collection<UserRole> getUsersBasedOnUserRoles() throws Exception;
	
	public Collection<UserRole> getApproverAssignedUsers(List<Long> projectIds,String userState,String searchByName) throws Exception;

}
