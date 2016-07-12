package com.bis.sourcelead.service;

import java.util.Collection;

import com.bis.sourcelead.entity.UserRole;

public interface UserRoleService {
	
	public UserRole addUserRole(UserRole userRole) throws Exception;
	
	public UserRole getLoginUserPrimaryRole(Long userId) throws Exception;

	public Collection<UserRole> getUsersBasedOnUserRoles() throws Exception;
	
}
