package com.bis.sourcelead.service;


import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
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
	
	
}	
		
	
