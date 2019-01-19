package com.bis.sourcelead.service;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.dto.UsersUsageReportDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
public interface UserLoginLogoutSessionService  {
   
	UserLoginLogoutSession storeUserLoginLogoutSession(UserLoginLogoutSession userLoginLogoutSession);
	
	UserLoginLogoutSession getLastLoginRecord(Long userId);
	
	UserLoginLogoutSession getUserLoginLogoutSessionById(Long userSessionId);
	
	UserLoginLogoutSession getUserLoginLogoutSessionByActionUUID(String actionUUID);

	boolean validatePreviouslyAuthenticatedByUserIdAndIpAddress(String ipAddress, Long userId);
	
	ResponseList<UsersUsageReportDTO> getMultipleIps(UsersUsageReportDTO usersUsageReportDTO, OrgLocations orgLocation) throws Exception;

	ResponseList<UsersUsageReportDTO> getUsersByipAddress(UsersUsageReportDTO usersUsageReportDTO);
	
	List<UserLoginLogoutSession>  getAll();
	
	UserDTO getUsersListByIpAddress(String str, List<Long> list,Long orgLocationId);
	
	List<String> getUsersByUserId(UsersUsageReportDTO usersUsageReportDTO);

}	
		
	
