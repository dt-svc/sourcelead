package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.model.ConnectionBean;

public interface ConnectionsDao {

	List<ConnectionBean> getFindConnections(Long id);

	List<ConnectionBean> getConnectedList(Long id);

	List<ConnectionBean> getReceivedList(Long userId);

	List<ConnectionBean> getSentList(Long userId);

	void changeClickConnect(String userId, Long id);

	void winthdrawUser(String userId, Long id);

	void acceptRequest(String userId, Long id);

	void ignoreUser(String userId, Long id);

	void disconnectConnect(String userId, Long id);
}
