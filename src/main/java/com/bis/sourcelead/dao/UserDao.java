package com.bis.sourcelead.dao;

import java.util.Collection;
import java.util.List;

import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.TeamMembers;
import com.bis.sourcelead.entity.User;

public interface UserDao {

	User findByUserName(String username);
	
	public User addUser(User user) throws Exception;
	
	User getUserById(Long Id) throws Exception;
	
	User getUserByUserCode(String userCode) throws Exception;
	
	User getUserByIdAndOrgLocationsId(Long Id, Long orgLocationsId) throws Exception;

	String validateEmailandCandidate(String website, String email, String sourceleadBLockedwebsites) throws Exception;
	
	public Collection<User> getUsers() throws Exception;
	
	User findByEmail(String email);
	
	public User findByEmailAndOrgLocationsId(String email, Long orgLocationsId)  throws Exception;
	
	public List<User> getReoprtingManagersByOrgLocationId(Long orgLocationsId) throws Exception;
	
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
	
	//Prasad Salina to get managers list baded on role type
	List<User> getManagersByUserRolesAndOrgLocationId(Long roleId , Long orgLocationsId,Long tabId) throws Exception;
			
	//Prasad Salina to get managers list baded on role type and except current user
	List<User> getManagersByUserIdAndUserRolesAndOrgLocationId(Long userId,Long roleId , Long orgLocationsId) throws Exception;
	
	public List<User>  getUserListExceptExistedUsersList(List<Long> userIds,Long orgLocationId)throws Exception;

	User getUserDetailsByUserCode(String userCode);

	List<User> getOwnersByOrgLocationIdAndNotExistedOwnerId(Long id, Long ownerId, List<Long> ownerListOfSpoc);
	
	
	/**
	 * @author Sammeta David Raju
	 * This method to fetch all users in the org by orgLocation code
	 * @param orgLocationsId
	 * @return
	 * @throws Exception
	 */
	List<User> getAllUsersByOrgLocationCode(Long orgLocationsId) throws Exception;;
	
	
	public List<User> getAllUsersByOrgLocationCodeExcludingEmployees(Long orgLocationsId) throws Exception;
	
	
	List<User> getAllUsersByOrgLocationCodeExceptTeamOwner(Long teamOwnerUserId ,Long orgLocationsId) throws Exception;

	List<User> getUsersByTeamType(Long id, String teamType)throws Exception;

	public List<User> getUsersByTeamTypeAndNoCurrentUser(Long currentApproverId,Long orgLocationsId);

	public List<User>  getUserListExceptExistedTeamMembersList(List<Long> teamMembersId,Long orgLocationId)throws Exception;
	
	public List<User> getTabAccessOwnersByOrgLocationIdAndTabId(Long orgLocationsId,Long tabId) throws Exception;
	
	public List<Roles> getTabAccessRolesListByOrgLocationIdAndTabId(Long orgLocationsId,Long tabId) throws Exception;
	
}