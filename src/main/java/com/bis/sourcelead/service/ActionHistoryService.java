package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.ActionHistory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserLoginLogoutSession;

public interface ActionHistoryService {

	public  List<ActionHistory> getAllActionPerformedByUser(Long userSessionId, Long orgLocation) ;
	
	public  List<ActionHistory> getAllOrglocationsActionHistoryList(Long userSessionId) ;

	public void saveActionHistory(String methodName,UserLoginLogoutSession userLoginLogoutSession,OrgLocations loginOrgLocation,String type) throws Exception;
	
}
