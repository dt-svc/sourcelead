package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ConnectionsDao;
import com.bis.sourcelead.model.ConnectionBean;
import com.bis.sourcelead.service.ConnectionsService;

@Service
public class ConnectionsServiceImpl implements ConnectionsService {

	@Autowired
	private ConnectionsDao connectionsDao;
	
	
	@Override
	public List<ConnectionBean> getFindConnections(Long id) {
		return connectionsDao.getFindConnections(id);
	}

	@Override
	public void changeClickConnect(String userId, Long id) {

		connectionsDao.changeClickConnect(userId, id);
	}

	@Override
	public List<ConnectionBean> getSentList(Long userId) {
		return connectionsDao.getSentList(userId);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void withdrawUser(String userId, Long id) {
		connectionsDao.winthdrawUser(userId, id);
	}

	@Override
	public List<ConnectionBean> getReceivedList(Long userId) {
		return connectionsDao.getReceivedList(userId);
	}

	@Override
	public void acceptRequest(String userId, Long id) {
		connectionsDao.acceptRequest(userId, id);
	}

	@Override
	public void ignoreUser(String userId, Long id) {
		connectionsDao.ignoreUser(userId, id);
	}

	@Override
	public List<ConnectionBean> getConnectedList(Long id) {
		return connectionsDao.getConnectedList(id);
	}

	@Override
	public void disconnectConnect(String userId, Long id) {
		connectionsDao.disconnectConnect(userId, id);
	}

}
