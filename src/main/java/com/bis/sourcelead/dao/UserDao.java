package com.bis.sourcelead.dao;

import java.util.Collection;
import java.util.List;

import com.bis.sourcelead.entity.User;

public interface UserDao {

	User findByUserName(String username);
	
	public User addUser(User user) throws Exception;
	
	User getUserById(Long Id) throws Exception;

	String validateEmailandCandidate(String website, String email, String sourceleadBLockedwebsites) throws Exception;
	
	public Collection<User> getUsers() throws Exception;
	
	User findByEmail(String email);
	
	public Collection<User> getUsersByUserRoles() throws Exception;
	
	public List<User> getUsersByUserRolesAndOrgLocationIdAndNoCurrentUser(Long userId, Long orgLocationsId) throws Exception;
	
	public List<User> getUsersByUserRolesAndOrgLocationId(Long orgLocationsId) throws Exception;
	
	List<User> getUsersRoleCountByRoleIdAndLocationIdAndNotLoggedInUser(Long roleId,Long orgLocationsId,Long userId) throws Exception;
}