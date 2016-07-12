package com.bis.sourcelead.dao;

import java.util.Collection;

import com.bis.sourcelead.entity.UserRole;

public interface UserRoleDao {

	public UserRole addUserRole(UserRole userRole) throws Exception;
	
	public UserRole getLoginUserPrimaryRole(Long userId) throws Exception;
	
	public Collection<UserRole> getUsersBasedOnUserRoles() throws Exception;
}
