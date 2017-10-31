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
	public List<ActionHistory> getAllActionPerformedByUser(Long userSessionId, Long orgLocation) {
		return actionHistoryDao.getAllActionHistoryList(userSessionId, orgLocation);
	}
	
	@Override
	public List<ActionHistory> getAllOrglocationsActionHistoryList(Long userSessionId) {
		return actionHistoryDao.getAllOrglocationsActionHistoryList(userSessionId);
	}

	@Override
	public void saveActionHistory(String methodName, UserLoginLogoutSession userLoginLogoutSession,OrgLocations orgLocations,String type) {
		try {
			actionHistoryDao.saveActionHistory(methodName,userLoginLogoutSession,orgLocations,type);
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
