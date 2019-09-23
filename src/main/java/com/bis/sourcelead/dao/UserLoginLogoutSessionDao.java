package com.bis.sourcelead.dao;

import java.math.BigInteger;
import java.util.List;

import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.dto.UsersUsageReportDTO;
import com.bis.sourcelead.entity.UserLoginLogoutSession;

public interface UserLoginLogoutSessionDao {
    
    UserLoginLogoutSession storeUserLoginLogoutSession(UserLoginLogoutSession userLoginLogoutSession);
    
    UserLoginLogoutSession getLastLoginRecord(Long userId);
    
    UserLoginLogoutSession getUserLoginLogoutSessionById(Long userSessionId);
    
    UserLoginLogoutSession getUserLoginLogoutSessionByActionUUID(String actionUUID);

    boolean validatePreviouslyAuthenticatedByUserIdAndIpAddress(String ipAddress, Long userId);

    UserDTO getUsersListByIpAddress(String ipAddress, List<Long> list,Long orgLocationId);

    List<String> getMultipleIps(UsersUsageReportDTO usersUsageReportDTO);
    
    List<UserLoginLogoutSession> getUsersByipAddress(UsersUsageReportDTO usersUsageReportDTO);
    
	List<UserLoginLogoutSession>  getAll();
	
	List<String> getUsersByUserId(UsersUsageReportDTO usersUsageReportDTO);

}