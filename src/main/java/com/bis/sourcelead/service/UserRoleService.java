package com.bis.sourcelead.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.bis.sourcelead.elk.index.UserIndex;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;

public interface UserRoleService {
	
	public UserRole addUserRole(UserRole userRole) throws Exception;
	
	public UserRole getLoginUserPrimaryRole(Long userId) throws Exception;
	
	public UserRole getNotPrimaryUser(Long userId) throws Exception;
	
	public List<UserRole> getUsersListByLocationId(Long locationId,String userState,Long userId,String loginUserRole,String userCode) throws Exception;
	
	public List<UserRole> getUsersListByRoleAndLocationId(Long roleId,Long locationId,String userState,Long userId,String loginUserRole,String userCode) throws Exception;
	
	public List<UserRole> getUsersListByUserNameAndLocationId(String userName,Long locationId,String userState,Long userId,String loginUserRole,String userCode) throws Exception;
	
	public List<UserRole> getUsersListByRoleAndNameAndLocationId(Long roleId,String userName,Long locationId,String userState,Long userId,String loginUserRole,String userCode,Integer offset, String viewType) throws Exception;
	
	public List<UserRole> getUsersListByUserStateAndLocationId(String userState,Long locationId,Long userId,String loginUserRole,String userCode) throws Exception;

	public List<UserRole> getUsersBasedOnUserRoles() throws Exception;
	
	public List<UserRole> getApproverAssignedUsers(List<Long> projectIds,String userState,String searchByName) throws Exception;
	
	public UserRole getUserDetailsByEmailAndLocationId(String email,Long locationId) throws Exception;
	
	public UserRole getUserDetailsByUserIdAndLocationId(Long userId,Long locationId) throws Exception;
	
	public UserRole getUserByUserId(Long userId) throws Exception;
	
	public Long getUsersCountByOrgLocationID(Long orgLocationId) throws Exception;

	/**To get user role by userid and locationId
	 * @author Prasad salina
	 * @param userId
	 * @param locationId
	 * @return
	 * @throws Exception
	 */
	public UserRole getUserRoleByUserIdAndLocationId(Long userId,Long locationId) throws Exception;
	
	/**
	 * @author Prasad salina
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean isUserActive(Long userId) throws Exception;
	
	
	public Boolean isUserAdmin(Long userId);
	
	public Boolean isUserAdminByOrgLocationId(Long userId, Long orgLocationId);
	
	public Boolean isUserisPrimaryForOrg(Long userId, Long orgLocationId);


	
	//Prasad Salina to get managers list baded on role type
	/*List<UserRole> getManagersByUserRolesAndOrgLocationId(String roleType , Long orgLocationsId) throws Exception;
			
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

	public Sheet getExcelFile(Sheet createExcelsheet, Workbook createExcelworkbook, List<UserRole> activeList,
			List<UserRole> inactiveList, List<UserRole> notLoggedInList, ArrayList<UserRole> userRolesList, String userState) throws Exception;

	public File getPdfFile(List<UserRole> activeList,
			List<UserRole> inactiveList, List<UserRole> notLoggedInList, ArrayList<UserRole> userRolesList,
			String userState, OrgLocations orgLocations) throws Exception;

	 UserRole getUserRoleByUserId(Long userId)throws Exception;

	/**
	 * @author : Naresh Bolisetty
	 * @purpose : Email JobScheduler(For to intimate unfilled timesheets)
	 * 
	 */
	 public HashSet<ProjectMembers> getAllUserPrevoiusWeekTimeSheetSubmissionStatus(String invoiceFrequency) throws Exception;
	 
	 public HashSet<ProjectMembers> getAllUserPrevoiusWeekTimeSheetSubmissionStatusByApproverUserId(String invoiceFrequency,Long approverUserId,Long locationId) throws Exception;
	 
	 public HashSet<UserRole> getAllUserTimeSheetSubmissionStatusCurrentWeek();
	 
	 public List<UserRole> getUsersListByUserId(Long userId) throws Exception;
	 
	 List<UserRole> getAllActiveUsersListByOrgLocationId(Long orgLocationId) throws Exception;

	 public HashSet<ProjectMembers> getAllUserPrevoiusFridayTimeSheetSubmissionStatus(String wEEKLY,String dateFormat) throws Exception;

	 List<Long> getAllAssignedUsersIdsByReportingManagerId(Long reportingManagerId,Long orgLocationId);
	 
	 List<UserRole> getAllAssignedUsersByReportingManagerId(Long reportingManagerId,Long orgLocationId);
	 
	 UserRole getChangeRolePermissions(List<Long> userRoletabIds,Long userId,Long orgLocationId) throws Exception;
	
	 List<UserRole> getAllUsersListByOrgLocationId(Long orgLocationId,Long loginUserId ) throws Exception;
	 
	 public UserRole getUserDetailsByUserIdAndLocationIdAndUserType(Long userId,Long locationId,String userType) throws Exception;
	 
	 UserRole getIsProfessionalOrRecruiterExistsWithEmailAndUserType(String email,String userType) throws Exception;

}
