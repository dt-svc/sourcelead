package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ActionHistoryDao;
import com.bis.sourcelead.entity.ActionHistory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.service.ActionHistoryService;

@Service
public class ActionHistoryServiceImpl implements ActionHistoryService{
	
	@Autowired
	ActionHistoryDao actionHistoryDao;
	
	@Override
	public List<ActionHistory> getAllActionPerformedByUser(Long userSessionId) {
		return actionHistoryDao.getAllActionHistoryList(userSessionId);
	}

	@Override
	public void saveActionHistory(String methodName, UserLoginLogoutSession userLoginLogoutSession,OrgLocations orgLocations) {
		try {
			actionHistoryDao.saveActionHistory(methodName,userLoginLogoutSession,orgLocations);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
