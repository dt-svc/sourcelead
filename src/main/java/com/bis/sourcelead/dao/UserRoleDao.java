package com.bis.sourcelead.dao;

import java.util.Collection;

import com.bis.sourcelead.entity.UserRole;

public interface UserRoleDao {

	public UserRole addUserRole(UserRole userRole) throws Exception;
	
	public UserRole getLoginUserPrimaryRole(Long userId) throws Exception;
	
	public Collection<UserRole> getUsersListByLocationId(Long locationId) throws Exception;
	
	public Collection<UserRole> getUsersListByRoleAndLocationId(String roleName,Long locationId) throws Exception;
	
	public Collection<UserRole> getUsersListByUserNameAndLocationId(String userName,Long locationId) throws Exception;
}
