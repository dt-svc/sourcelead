package com.bis.sourcelead.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.UserLoginLogoutSessionDao;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.dto.UsersUsageReportDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.service.ActionHistoryService;
import com.bis.sourcelead.service.UserLoginLogoutSessionService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ReportsUtil;

/**
 * This Service Class is UserLoginLogoutSession to get UserLoginLogoutSession Object
 * 
 * @author: Naresh Bolisetty
 * @date: 27-May-2016
 */


@Service
public class UserLoginLogoutSessionServiceImpl implements UserLoginLogoutSessionService {


    @Autowired
    private UserLoginLogoutSessionDao userLoginLogoutSessionDao;
    
    @Autowired
    ActionHistoryService actionHistoryService;
    
    @Autowired
    CommonUtil commonUtil;
    
    @Autowired
    ReportsUtil reportsUtil;

    @Override
    public UserLoginLogoutSession storeUserLoginLogoutSession(UserLoginLogoutSession userLoginLogoutSession) {
        return userLoginLogoutSessionDao.storeUserLoginLogoutSession(userLoginLogoutSession);
    }

    @Override
    public UserLoginLogoutSession getLastLoginRecord(Long userId) {
        return userLoginLogoutSessionDao.getLastLoginRecord(userId);
    }
    
    @Override
    public UserLoginLogoutSession getUserLoginLogoutSessionById(Long userSessionId) {
        return userLoginLogoutSessionDao.getUserLoginLogoutSessionById(userSessionId);
    }
    
    @Override
    public UserLoginLogoutSession getUserLoginLogoutSessionByActionUUID(String actionUUID) {
        return userLoginLogoutSessionDao.getUserLoginLogoutSessionByActionUUID(actionUUID);
    }

    @Override
    public boolean validatePreviouslyAuthenticatedByUserIdAndIpAddress(String ipAddress, Long userId)
    {
        return userLoginLogoutSessionDao.validatePreviouslyAuthenticatedByUserIdAndIpAddress(ipAddress, userId);
    }

    @Override
    public ResponseList<UsersUsageReportDTO> getMultipleIps(UsersUsageReportDTO usersUsageReportDTO, OrgLocations orgLocation)throws Exception
    {
        
        Date[] freqDates = new Date[2];
        if("Custom".equalsIgnoreCase(usersUsageReportDTO.getCustomDates())){
            freqDates[0] = commonUtil.getDateFormat(usersUsageReportDTO.getFromDate());
            freqDates[1] = commonUtil.getDateFormat(usersUsageReportDTO.getToDate());
        }else{
            freqDates = reportsUtil.getJobFilterDate(usersUsageReportDTO.getCustomDates(),orgLocation);
        }
        ResponseList<UsersUsageReportDTO> responseList = new ResponseList<>();
        usersUsageReportDTO.setFreqDates(freqDates);
        List<String> iplist =  userLoginLogoutSessionDao.getMultipleIps(usersUsageReportDTO);
        List<UsersUsageReportDTO> list = new ArrayList<>();
        for(String str : iplist){
            UsersUsageReportDTO usagedto = new UsersUsageReportDTO();
            usagedto.setIpAddress(str);
//            List<Long> ipAddressIdsList = getUsersListByIpAddress(str,usersUsageReportDTO.getOrgId());
//            usagedto.setNoOfUsers(ipAddressIdsList.size());
//            int totalActivities = 0;
//            for (Long id : ipAddressIdsList) {
                try {
//                    UserDTO actionHistory = actionHistoryService.getAllActionHistoryByUserId(id);
//                    totalActivities += Integer.parseInt(actionHistory.getNoOfActivities());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
//            }
//            usagedto.setNoOfActivities(String.valueOf(totalActivities));
            
            
//            UserDTO actionHistory = actionHistoryService.getAllActionHistoryByUserId(user.getId());
//            usagedto.setNoOfActivities(actionHistory.getNoOfActivities());
            
            list.add(usagedto);
        }
        responseList.setRecordsFiltered(iplist.size());
        responseList.setRecordsTotal(iplist.size());
        responseList.setData(list);
        return responseList;
    }

    public UserDTO getUsersListByIpAddress(String str, List<Long> list,Long orgLocationId)
    {
        return userLoginLogoutSessionDao.getUsersListByIpAddress(str,list,orgLocationId);
    }

    @Override
    public ResponseList<UsersUsageReportDTO> getUsersByipAddress(UsersUsageReportDTO usersUsageReportDTO)
    {
        ResponseList<UsersUsageReportDTO> responseList = new ResponseList<>();
        List<UserLoginLogoutSession> ipUserslist =  userLoginLogoutSessionDao.getUsersByipAddress(usersUsageReportDTO);
        List<UsersUsageReportDTO> list = new ArrayList<>();
        for(UserLoginLogoutSession userLoginLogout : ipUserslist){
            UsersUsageReportDTO userusagedto = new UsersUsageReportDTO();
            
            StringBuilder sb = new StringBuilder();
            if(StringUtils.isNotBlank(userLoginLogout.getUserId().getLastName())){
                userusagedto.setUserName(sb.append(userLoginLogout.getUserId().getFirstName()).append(" ").append(userLoginLogout.getUserId().getLastName()).toString());
            }else{
                userusagedto.setUserName(sb.append(userLoginLogout.getUserId().getFirstName()).toString());
            }
            
            userusagedto.setUserEmail(userLoginLogout.getUserId().getEmail());
            
            if(StringUtils.isNotBlank(userLoginLogout.getUserId().getMobilePhone())){
              userusagedto.setMobilePhone(userLoginLogout.getUserId().getMobilePhone());
            }
            
            list.add(userusagedto);
        }
        responseList.setRecordsFiltered(ipUserslist.size());
        responseList.setRecordsTotal(ipUserslist.size());
        responseList.setData(list);
        return responseList;
    }

	@Override
	public List<UserLoginLogoutSession> getAll()
	{
		return userLoginLogoutSessionDao.getAll();
	}

	@Override
	public List<String> getUsersByUserId(UsersUsageReportDTO usersUsageReportDTO)
	{
		return userLoginLogoutSessionDao.getUsersByUserId(usersUsageReportDTO);
	}
    
}