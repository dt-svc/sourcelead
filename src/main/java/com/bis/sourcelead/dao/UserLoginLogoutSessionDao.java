package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UsersUsageReportDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.UserLoginLogoutSession;

public interface UserLoginLogoutSessionDao {
    
    UserLoginLogoutSession storeUserLoginLogoutSession(UserLoginLogoutSession userLoginLogoutSession);
    
    UserLoginLogoutSession getLastLoginRecord(Long userId);
    
    UserLoginLogoutSession getUserLoginLogoutSessionById(Long userSessionId);
    
    UserLoginLogoutSession getUserLoginLogoutSessionByActionUUID(String actionUUID);

    boolean validatePreviouslyAuthenticatedByUserIdAndIpAddress(String ipAddress, Long userId);

    List<Long> getUsersListByIpAddress(String ipAddress, List<Long> list);

    List<String> getMultipleIps(UsersUsageReportDTO usersUsageReportDTO);
    
    List<UserLoginLogoutSession> getUsersByipAddress(UsersUsageReportDTO usersUsageReportDTO);
    
    
}