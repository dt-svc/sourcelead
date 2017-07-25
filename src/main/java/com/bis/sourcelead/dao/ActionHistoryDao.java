package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ActionHistory;
import com.bis.sourcelead.entity.ActionNames;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserLoginLogoutSession;

public interface ActionHistoryDao {
	
	public List<ActionHistory> getAllActionHistoryList(Long userSessionId);
	
	public void saveActionHistory(String methodName, UserLoginLogoutSession userLoginLogoutSession) throws Exception;

	ActionNames getActionNameUsingMethodName(String methodName) throws Exception;

}
