package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ActionHistory;
import com.bis.sourcelead.entity.ActionNames;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserLoginLogoutSession;

public interface ActionHistoryDao {
	
	public List<ActionHistory> getAllActionHistoryList(Long userSessionId, Long orgLocation);
	
	public void saveActionHistory(String methodName, UserLoginLogoutSession userLoginLogoutSession,OrgLocations loginOrgLocation,String type) throws Exception;

	public List<ActionHistory> getAllOrglocationsActionHistoryList(Long userSessionId);
	
	List<ActionNames> getActionNameUsingMethodName(String methodName) throws Exception;

}
