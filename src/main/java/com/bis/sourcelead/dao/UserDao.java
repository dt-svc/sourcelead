package com.bis.sourcelead.dao;

import java.util.Collection;
import java.util.List;

import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.User;

public interface UserDao {

	User findByUserName(String username);
	
	public User addUser(User user) throws Exception;
	
	User getUserById(Long Id) throws Exception;
	
	User getUserByIdAndOrgLocationsId(Long Id, Long orgLocationsId) throws Exception;

	String validateEmailandCandidate(String website, String email, String sourceleadBLockedwebsites) throws Exception;
	
	public Collection<User> getUsers() throws Exception;
	
	User findByEmail(String email);
	
	/**
	 * This method to fetch only Admin role users
	 * @param orgLocationsId
	 * @return
	 * @throws Exception
	 */
	public Collection<User> getUsersByUserRoles(Long orgLocationsId) throws Exception;
	
	public List<User> getUsersByUserRolesAndOrgLocationIdAndNoCurrentUser(Long userId, Long orgLocationsId) throws Exception;
	
	/**
	 * This method to fetch only Approver role users
	 * @param orgLocationsId
	 * @return
	 * @throws Exception
	 */
	public List<User> getUsersByUserRolesAndOrgLocationId(Long orgLocationsId) throws Exception;
	
	List<User> getUsersRoleCountByRoleIdAndLocationIdAndNotLoggedInUser(Long roleId,Long orgLocationsId,Long userId) throws Exception;
	
	public List<Employees> getAllUserOrgCodeAndLocationCodeTimeSheetNotification(Long userId) throws Exception;
	
	
	/**
	 * This method to fetch Owner used in Client and vendor
	 * @param orgLocationsId
	 * @return
	 * @throws Exception
	 */
	public List<User> getOwnersByOrgLocationId(Long orgLocationsId) throws Exception;
	
}