package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.ActionHistory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserLoginLogoutSession;

public interface ActionHistoryService {

	public  List<ActionHistory> getAllActionPerformedByUser(Long userSessionId, Long orgLocation) ;
	
	public  List<ActionHistory> getAllActionPerformedByUserActionUUID(String actionUUID, Long orgLocation) ;
	
	public  List<ActionHistory> getAllOrglocationsActionHistoryList(Long userSessionId) ;
	
	public  List<ActionHistory> getAllOrglocationsActionHistoryListActionUUID(String actionUUID) ;

	public void saveActionHistory(String methodName,UserLoginLogoutSession userLoginLogoutSession,OrgLocations loginOrgLocation,String type) throws Exception;

	public UserDTO getAllActionHistoryByUserId(Long userId) throws Exception;

	public List<ActionHistory> getAllMultipleIpsByUserId(Long userId) throws Exception;

	public List<Long> getAllUserIdsByFrequencyBasedOnActivity(Date[] freqDates);
	
}
