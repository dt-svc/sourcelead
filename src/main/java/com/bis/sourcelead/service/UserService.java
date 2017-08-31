package com.bis.sourcelead.service;


import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.elk.index.UserIndex;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.form.UserProfile;
public interface UserService  {

	User findByUserName(String username);
	
	public User addUser(User user) throws Exception;
	
	public void createUser(JSONObject jsonObj, User user, Long orgLocationsId) throws Exception;
	

	public void sendEmail(User user, User loginUser, String password, Long orgLocationsId) throws Exception;
	
	public void sendEmailFromSourcelead(User user, User loginUser, String password, Long orgLocationsId,Long orgId) throws Exception;
	
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
	
	User storeUser(AddUserDTO addUserDTO,CandidateDTO candidateDTO,User users,OrgLocations orgLocations,String sourceType,String responderType,Org loginOrg)  throws Exception;
	
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
	
	List<User> getManagersByUserIdAndUserRolesAndOrgLocationIdAndUserTabId(Long userId, Long roleId, Long orgLocationsId, Long tabId) throws Exception;
	
	public List<User>  getUserListExceptExistedUsersList(List<Long> userIds,Long orgLocationId, Long tabId)throws Exception;

	
	/**
	 * @author Prasanna vadla
	 * to fetch user details by usercode
	 * @param createdBy
	 * @return
	 */
	User getUserDetailsByUserCode(String userCode);


	List<User> getOwnersByOrgLocationIdAndNotExistedOwnerId(Long id, Long ownerId, List<Long> ownerListOfSpoc);
	
	void sendEmailToClientOrVendor(String spocUUID, User spocUser,User ownerUser, User loginUser, String password, String responderType, Org loginOrg,Org respondingPartyOrg,OrgLocations respondingPartyOrgLocation,OrgLocations loginOrgLocation) throws Exception;

	/**
	 * @author Sammeta David
	 * to fetch get All Users By OrgLocation Code
	 * @param orgLocationsId
	 * @return List<User> 
	 */
	List<User> getAllUsersByOrgLocationCode(Long orgLocationsId) throws Exception;
	
	
	/**
	 * @author Sammeta David
	 * to fetch get All Users By OrgLocation Code who are not Employees
	 * @param orgLocationsId
	 * @return List<User> 
	 */
	List<User> getAllUsersByOrgLocationCodeExcludingEmployees(Long orgLocationsId) throws Exception;
	
	
	
	
	/**
	 * @author Sammeta David
	 * to fetch get All Users By OrgLocation Code Except TeamOwner
	 * @param orgLocationsId,teamOwnerUserId
	 * @return List<User> 
	 */
	List<User> getAllUsersByOrgLocationCodeExceptTeamOwner(Long teamOwnerUserId ,Long orgLocationsId, Long tabId) throws Exception;

	/**
	 * Rajashekar
	 * @param id
	 * @param teamType
	 * @return List<User>
	 */
	List<User> getUsersByTeamType(Long id, String teamType)throws Exception;
	
	List<User> getUsersByTeamTypeAndTabId(Long id, String teamType, Long tabId)throws Exception;
	
	public List<User> getUsersByTeamTypeAndNoCurrentUser(Long currentApproverId,Long orgLocationsId)throws Exception;

	
	public List<User>  getUserListExceptExistedTeamMembersList(List<Long> teamMembersId,Long orgLocationId)throws Exception;
	
	public List<User> getTabAccessOwnersByOrgLocationIdAndTabId(Long orgLocationsId,Long tabId) throws Exception;
	
	/**
	 * @author Prasad salina
	 * @param currentApproverId
	 * @param orgLocationsId
	 * @param tabId
	 * @return
	 * @throws Exception
	 * @Purpose to get level2 managers
	 */
	public List<User> getTabAccessOwnersByOrgLocationIdAndTabIdAndNoCurrentUser(Long currentApproverId, Long orgLocationsId,Long tabId)throws Exception;
	
	public List<User> getUsersForAddAsConsultant(Org org,Long orgLocationsId) throws Exception;
	
	public List<User> getUsersForConsultantsByUserRoleIdAndOrgLocationId(Long roleId,Long orgLocationsId) throws Exception;
	
	public List<User> getTabAccessUsersByOrgLocationIdAndTabIdAndUsersIds(Long orgLocationsId,List<Long> userIds,Long tabId) throws Exception;
	
	public List<User> getTabAccessUsersByOrgLocationIdAndTabIdAndNotInUsersId(Long orgLocationsId,Long userId,Long tabId) throws Exception;
	
	public void saveOrUpdateUserElkDocument(UserRole userRole, Employees employee, String loginUserCode) throws Exception;

	public List<User> getUsersListByOrglocationId(Long orgLocationId) throws Exception;

	List<UserIndex> getAllUniversalSearchUsersList(OrgLocations orgLocations, String searchValue, User user)throws Exception;
	
	User getUserByEmailAndUserType(String email,String userType) throws Exception;
	
	User getUserByUserName(String userName) throws Exception;

	List<User> getTabAccessOwnersByOrgLocationIdAndTabIdAndNotOwners(Long id, Long valueOf, List<Long> ownersList) throws Exception;

	public List<User> getAllUsersWithRequirementTabAccessAndMemeberInRequirementMemberOrgLocationIdAndTabId(Long orgLocationsId,Long tabId) throws Exception;

	LinkedHashSet<User> getUsersListAsReoprtingManagersByOrgLocationIdHavingTabAccess(Long orgLocationsId, User user,Long tabId) throws Exception;
	void passwordResetMailToLoginUser(User user, OrgLocations orgLocations) throws Exception;
	
	public List<User> getUsersByOrgLocationIdExceptLoggedInUser(Long orgLocationsId,Long userId) throws Exception;

}	
		
	
