package com.bis.sourcelead.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ActionHistoryDao;
import com.bis.sourcelead.dao.UserLoginLogoutSessionDao;
import com.bis.sourcelead.entity.ActionHistory;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.service.ActionHistoryService;

@Service
public class ActionHistoryServiceImpl implements ActionHistoryService{
	
	@Autowired
	ActionHistoryDao actionHistoryDao;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserLoginLogoutSessionDao userLoginLogoutSessionDao;
	
	@Override
	public List<ActionHistory> getAllActionPerformedByUser(Long userId) {
		/*UserLoginLogoutSession userLoginLogoutSession = userLoginLogoutSessionDao.getLastLoginRecord(userId);
		System.out.println(userLoginLogoutSession.getLoginTime());*/
		return actionHistoryDao.getAllExpensesCategoryByLocationId(userId);
	}

	@Override
	public void saveActionHistory(String methodName, User user) {
		try {
			actionHistoryDao.saveActionHistory(methodName,user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
