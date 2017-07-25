package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ActionHistory;
import com.bis.sourcelead.entity.ActionNames;
import com.bis.sourcelead.entity.User;

public interface ActionHistoryDao {
	
	public List<ActionHistory> getAllActionHistoryList(Long userId);
	
	public void saveActionHistory(String methodName, User user) throws Exception;

	ActionNames getActionNameUsingMethodName(String methodName) throws Exception;

}
