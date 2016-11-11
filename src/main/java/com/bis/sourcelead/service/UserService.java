package com.bis.sourcelead.service;


import java.util.Collection;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.TeamMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.form.UserProfile;
public interface UserService  {

	User findByUserName(String username);
	
	public User addUser(User user) throws Exception;
	
	public void createUser(JSONObject jsonObj, User user, Long orgLocationsId) throws Exception;
	

	public void sendEmail(User user, User loginUser, String password, Long orgLocationsId) throws Exception;
	
	String validateEmailandCandidate(String website, String email,String sourceleadBLockedwebsites) throws Exception;
	
	User getUserById(Long Id) throws Exception;
	
	User getUserByUserCode(String userCode) throws Exception;
	
	User getUserByIdAndOrgLocationsId(Long Id, Long orgLocationsId) throws Exception;

	public Collection<User> getUsers() throws Exception;
	
	public User findByEmail(String email)  throws Exception;
	
	public User findByEmailAndOrgLocationsId(String email, Long orgLocationsId)  throws Exception;
	
	public User saveUserProfile(UserProfile userProfile,MultipartFile multiFile,User user) throws Exception;
	
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
	
	User storeUser(JSONObject jsonObj,User users,OrgLocations orgLocations,String sourceType,String responderType,Org loginOrg)  throws Exception;
	
	/**
	 * This method to fetch Owner used in Client and vendor
	 * @param orgLocationsId
	 * @return
	 * @throws Exception
	 */
	public List<User> getOwnersByOrgLocationId(Long orgLocationsId) throws Exception;

	//Prasad Salina to get managers list baded on role type
	List<User> getManagersByUserRolesAndOrgLocationId(String roleType , Long orgLocationsId) throws Exception;
			
	//Prasad Salina to get managers list baded on role type and except current user
	List<User> getManagersByUserIdAndUserRolesAndOrgLocationId(Long userId,String roleType , Long orgLocationsId) throws Exception;
	
	public List<User>  getUserListExceptExistedUsersList(List<Long> userIds,Long orgLocationId)throws Exception;

	
	/**
	 * @author Prasanna vadla
	 * to fetch user details by usercode
	 * @param createdBy
	 * @return
	 */
	User getUserDetailsByUserCode(String userCode);


	List<User> getOwnersByOrgLocationIdAndNotExistedOwnerId(Long id, Long ownerId, List<Long> ownerListOfSpoc);
	
	void sendEmailToClientOrVendor(User user, User loginUser, String password, String responderType, Org loginOrg,Org respondingPartyOrg,OrgLocations respondingPartyOrgLocation) throws Exception;

	/**
	 * @author Sammeta David
	 * to fetch get All Users By OrgLocation Code
	 * @param orgLocationsId
	 * @return List<User> 
	 */
	List<User> getAllUsersByOrgLocationCode(Long orgLocationsId) throws Exception;
	
	
	
	/**
	 * @author Sammeta David
	 * to fetch get All Users By OrgLocation Code Except TeamOwner
	 * @param orgLocationsId,teamOwnerUserId
	 * @return List<User> 
	 */
	List<User> getAllUsersByOrgLocationCodeExceptTeamOwner(Long teamOwnerUserId ,Long orgLocationsId) throws Exception;

	/**
	 * Rajashekar
	 * @param id
	 * @param teamType
	 * @return List<User>
	 */
	List<User> getUsersByTeamType(Long id, String teamType)throws Exception;
	
	public List<User> getUsersByTeamTypeAndNoCurrentUser(Long currentApproverId,Long orgLocationsId)throws Exception;

	
	public List<User>  getUserListExceptExistedTeamMembersList(List<Long> teamMembersId,Long orgLocationId)throws Exception;
	
}	
		
	
