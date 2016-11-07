package com.bis.sourcelead.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.bis.sourcelead.entity.UserRole;

public interface UserRoleService {
	
	public UserRole addUserRole(UserRole userRole) throws Exception;
	
	public UserRole getLoginUserPrimaryRole(Long userId) throws Exception;
	
	public List<UserRole> getUsersListByLocationId(Long locationId,String userState,Long userId,String loginUserRole,String userCode) throws Exception;
	
	public List<UserRole> getUsersListByRoleAndLocationId(Long roleId,Long locationId,String userState,Long userId,String loginUserRole,String userCode) throws Exception;
	
	public List<UserRole> getUsersListByUserNameAndLocationId(String userName,Long locationId,String userState,Long userId,String loginUserRole,String userCode) throws Exception;
	
	public List<UserRole> getUsersListByRoleAndNameAndLocationId(Long roleId,String userName,Long locationId,String userState,Long userId,String loginUserRole,String userCode) throws Exception;
	
	public List<UserRole> getUsersListByUserStateAndLocationId(String userState,Long locationId,Long userId,String loginUserRole,String userCode) throws Exception;

	public List<UserRole> getUsersBasedOnUserRoles() throws Exception;
	
	public List<UserRole> getApproverAssignedUsers(List<Long> projectIds,String userState,String searchByName) throws Exception;
	
	public UserRole getUserDetailsByEmailAndLocationId(String email,Long locationId) throws Exception;
	
	/**
	 * This method is used for whether spoc is in multile orgs 
	 * @param Venkat I
	 * @return
	 * @throws Exception
	 */
	public List<UserRole> getUserSpocsListByUserId(Long userId) throws Exception;

	public Sheet getExcelFile(Sheet createExcelsheet, Workbook createExcelworkbook, List<UserRole> activeList,
			List<UserRole> inactiveList, List<UserRole> notLoggedInList, ArrayList<UserRole> userRolesList, String userState) throws Exception;
	
}
