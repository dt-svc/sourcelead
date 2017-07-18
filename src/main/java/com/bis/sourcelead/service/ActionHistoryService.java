package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.ActionHistory;
import com.bis.sourcelead.entity.User;

public interface ActionHistoryService {

	public  List<ActionHistory> getAllActionPerformedByUser(Long userId) ;

	public void saveActionHistory(String methodName, User user) throws Exception;
		

}
