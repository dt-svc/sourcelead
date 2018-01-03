package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.model.ConnectionBean;

public interface ConnectionsService {

	List<ConnectionBean> getFindConnections(Long id);

	List<ConnectionBean> getConnectedList(Long long1);

	List<ConnectionBean> getReceivedList(Long userId);

	List<ConnectionBean> getSentList(Long userId);

	void changeClickConnect(String userId, Long id);

	void withdrawUser(String userId, Long id);

	void acceptRequest(String userId, Long id);

	void ignoreUser(String userId, Long id);

	void disconnectConnect(String userId, Long id);

}
