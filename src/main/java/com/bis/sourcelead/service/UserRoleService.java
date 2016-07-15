package com.bis.sourcelead.service;

import java.util.Collection;

import com.bis.sourcelead.entity.UserRole;

public interface UserRoleService {
	
	public UserRole addUserRole(UserRole userRole) throws Exception;
	
	public UserRole getLoginUserPrimaryRole(Long userId) throws Exception;
	
	public Collection<UserRole> getUsersListByLocationId(Long locationId,String userState) throws Exception;
	
	public Collection<UserRole> getUsersListByRoleAndLocationId(String roleName,Long locationId,String userState) throws Exception;
	
	public Collection<UserRole> getUsersListByUserNameAndLocationId(String userName,Long locationId,String userState) throws Exception;
	
	public Collection<UserRole> getUsersListByRoleAndNameAndLocationId(String roleName,String userName,Long locationId,String userState) throws Exception;
	
	public Collection<UserRole> getUsersListByUserStateAndLocationId(String userState,Long locationId) throws Exception;

}
