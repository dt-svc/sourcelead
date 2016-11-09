package com.bis.sourcelead.dao;

import java.util.Collection;
import java.util.List;

import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;

public interface UserRoleDao {

	public UserRole addUserRole(UserRole userRole) throws Exception;
	
	public UserRole getLoginUserPrimaryRole(Long userId) throws Exception;
	
	public UserRole getNotPrimaryUser(Long userId) throws Exception;

	public List<UserRole> getUsersListByLocationId(Long locationId,String userState,Long userId,String loginUserRole,String userCode) throws Exception;
	
	public List<UserRole> getUsersListByRoleAndLocationId(Long roleId,Long locationId,String userState,Long userId,String loginUserRole,String userCode) throws Exception;
	
	public List<UserRole> getUsersListByUserNameAndLocationId(String userName,Long locationId,String userState,Long userId,String loginUserRole,String userCode) throws Exception;
	
	public List<UserRole> getUsersListByRoleAndNameAndLocationId(Long roleId,String userName,Long locationId,String userState,Long userId,String loginUserRole,String userCode) throws Exception;
	
	public List<UserRole> getUsersListByUserStateAndLocationId(String userState,Long locationId,Long userId,String loginUserRole,String userCode) throws Exception;

	public List<UserRole> getUsersBasedOnUserRoles() throws Exception;

	public List<UserRole> getApproverAssignedUsers(List<Long> projectIds, String userState, String searchByName) throws Exception;
	
	public Boolean isUserAdminByOrgLocationId(Long userId, Long orgLocationId);
	

	public Boolean isUserAdmin(Long userId);


	public UserRole getUserDetailsByEmailAndLocationId(String email,Long locationId) throws Exception;
	
	/**To get user role by userid and locationId
	 * @author Prasad salina
	 * @param userId
	 * @param locationId
	 * @return
	 * @throws Exception
	 */
	public UserRole getUserRoleByUserIdAndLocationId(Long userId,Long locationId) throws Exception;
	
	public boolean isUserActive(Long userId) throws Exception;
	
	/*//Prasad Salina to get managers list baded on role type
	List<UserRole> getManagersByUserRolesAndOrgLocationId(String roleType , Long orgLocationsId) throws Exception;
			
	//Prasad Salina to get managers list baded on role type and except current user
	List<UserRole> getManagersByUserIdAndUserRolesAndOrgLocationId(Long userId,String roleType , Long orgLocationsId) throws Exception;*/
	
	//Prasad Salina to get all the user ids baded on reportingManager userid
	List<Long> getAllUserIdsByReportingManagerId(Long reportingManagerId) throws Exception;
	
	/**
	 * This method is used for whether spoc is in multile orgs 
	 * @param Venkat I
	 * @return
	 * @throws Exception
	 */
	public List<UserRole> getUserSpocsListByUserId(Long userId) throws Exception;


}
