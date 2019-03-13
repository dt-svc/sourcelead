package com.bis.sourcelead.dao;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import com.bis.sourcelead.dto.ApplicantsJoinedReportDTO;
import com.bis.sourcelead.dto.ProbationDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.DashboardItems;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.reportsDTO.LeaveBalanceReportDTO;

public interface UserDao {

	User findByUserName(String username);
	
	public User addUser(User user) throws Exception;
	
	public void deleteUser(User user) throws Exception;
	
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
	
	List<User> getManagersByUserIdAndUserRolesAndOrgLocationIdAndUserTabId(Long userId, Long roleId, Long orgLocationsId, Long tabId) throws Exception;
	
	public List<User>  getUserListExceptExistedUsersList(List<Long> userIds,Long orgLocationId, Long tabId)throws Exception;

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
	
	
	List<User> getAllUsersByOrgLocationCodeExceptTeamOwner(Long teamOwnerUserId ,Long orgLocationsId, Long tabId) throws Exception;

	List<User> getUsersByTeamType(Long id, String teamType)throws Exception;
	
	List<User> getUsersByTeamTypeAndTabId(Long id, String teamType, Long tabId)throws Exception;

	public List<User> getUsersByTeamTypeAndNoCurrentUser(Long currentApproverId,Long orgLocationsId);

	public List<User>  getUserListExceptExistedTeamMembersList(List<Long> teamMembersId,Long orgLocationId)throws Exception;
	
	public List<User> getTabAccessOwnersByOrgLocationIdAndTabId(Long orgLocationsId,Long tabId) throws Exception;
	
	public User getTabAccessOwnersByOrgLocationIdEmailAndTabId(Long orgLocationsId, String email, Long tabId) throws Exception;
	
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
	
	public List<User> getUsersListByOrglocationId(Long orgLocationId) throws Exception;
	
	User getUserByEmailAndUserType(String email,String userType) throws Exception;
	
	User getUserByEmail(String email) throws Exception;
	
	User getUserByUserName(String userName) throws Exception;

	List<User> getTabAccessOwnersByOrgLocationIdAndTabIdAndNotOwners(Long orgLocationsId, Long tabId,
			List<Long> ownersList);
	
	
	public List<User> getAllUsersWithRequirementTabAccessAndMemeberInRequirementMemberOrgLocationIdAndTabId(Long orgLocationsId,Long tabId) throws Exception;

	public List<User> getUsersListAssignedToReoprtingManagersByOrgLocationId(Long orgLocationsId,User user,Long tabId) throws Exception;
	
	public LinkedHashSet<User> getUsersListAsReoprtingManagersByOrgLocationIdHavingTabAccess(Long orgLocationsId,User user,Long tabId) throws Exception;
	
	public List<User> getUsersByOrgLocationIdExceptLoggedInUser(Long orgLocationsId,Long userId) throws Exception;
	
	public List<User> getUsersHierarchyListByOrgLocationIdAndRoleIdAndNoCurrentUser(Long userId, Long roleId, Long orgLocationsId) throws Exception;
	
	List<User> getUserListByRoleActionIdAndOrgLocationId(Long roleActionId,Long orgLocationId) throws Exception;

	User getUserByVerficationUUID(String token);
	
	User getUserByUserCodeAndOrgLocationsId(String userCode, Long orgLocationsId) throws Exception;
	
	public ResponseList<UserDTO> getUsersDTOListByOrglocationId(Long orgLocationId,UserDTO userDTO) throws Exception;

	ResponseList<UserRole> getUsersResponseListByOrglocationId(UserDTO userDTO, OrgLocations orgLocation,Date[] searchDates) throws Exception;
	

	List<UserDTO> getLinkedUserListByUserId(Long id) throws Exception;
	
	public List<Long> getUserIdsListByLinkedUserId(Long userId) throws Exception;

	User getUserbyConsultantUserId(Long consultantUserId);

	List<User> getUsersListForSignatoryByOrglocationId(Long locationId);

	List<User> getGlobalUsersByOrgId(Long roleId,Long orgId,Long tabId) throws Exception;
	
	public List<User> getTabAccessGlobalOwnersByOrgIdAndTabId(Long orgId,Long tabId) throws Exception;
	
	public List<User> getTabAccessGlobalOwnersByOrgLocationIdAndTabIdAndNoCurrentUser(Long currentApproverId, Long orgId,Long tabId)throws Exception;
	
	List<User> getResourceManagersByOrgLocationIdAndTabId(Long orgLocationsId,Long tabId) throws Exception;
	
	List<User> getReportingManagersByUserRolesAndOrgLocationId(Long roleId , Long orgLocationsId) throws Exception;
	
	public List<User> getGlobalReportingManagersUsersByOrgId(Long roleId, Long orgId) throws Exception;
	
	List<User> getReportingManagersByUserIdAndUserRolesAndOrgLocationIdAndUserTabId(Long userId, Long roleId, Long orgLocationsId) throws Exception;
	
	public ResponseList<UserDTO> getRecruitersReports(Long userId,Long orgLocationId,Long orgId,String isGlobalUser,UserDTO userDTO,String loginUserRole,Date[] searchDates,List<Long> teamsList,String type,List<Long> rolesList) throws Exception;


	public DashboardItems getDashboardItemsByUserId(long loginUserId, long orgLocationId);
	
	public DashboardItems getDashboardItemsByDashboardId(long dashboardItemId);

	public long saveDashboardItems(DashboardItems dashboardItems);
	
	public List<DashboardItems> getAllDashboardItems();
	
	public List<User> getUsersListUsingMultipleStringIdsWithLocationId(String ids, Long orgLocationId) throws Exception;
	
	public List<LeaveBalanceReportDTO> getUserNameAndTypeByOrgLocationId(String searchValue, Long orgLocationId,List<Long> userRole,List<Long> orgDepartmentsList,String topleave,Date MonthStart,Date MonthEnd) throws Exception;

	public List<User> getUsersListUsingMultipleStringIds(String ids) throws Exception;
	
	public List<Long> getUserHierarchyIdList(Long userId) throws Exception;
	
	public List<String> getUserHierarchyUserCodeList(Long userId) throws Exception;
	
	public ResponseList<ApplicantsJoinedReportDTO> getApplicantsJoinedReport(ApplicantsJoinedReportDTO applicantsJoinedReportDTO,Long orgLocationId,Date[] searchDates,List<Long> teamsList,String type,List<Long> clientIdsList) throws Exception;

	List<Object[]> getApplicantsJoinedCountByDates(String createdBy, Date fromDate, Date toDate);

	List<ProbationDTO> getAllProbationUsersListForResourceManager(Long resourcemanagerId, Date futureDate, Long orgLocationId);

	User validateSSNDuplicate(String ssnNumber, Long locationId, String userId);

}