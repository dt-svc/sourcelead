package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ConnectionsDao;
import com.bis.sourcelead.dto.UserRoleDTO;
import com.bis.sourcelead.entity.Connections;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ConnectionStatus;
import com.bis.sourcelead.service.ConnectionsService;

@Service
public class ConnectionsServiceImpl implements ConnectionsService {

	@Autowired
	private ConnectionsDao connectionsDao;
	
	@Override
	public Connections saveConnection(Connections connections) throws Exception {
		return connectionsDao.saveConnection(connections);
	}

	@Override
	public List<UserRoleDTO> getConnectionsListByStatus(String searchValue, Integer offset, Long userId,
			String connectionStatus, String status) throws Exception {
		return connectionsDao.getConnectionsListByStatus(searchValue, offset, userId, connectionStatus, status);
	}


	@Override
	public int deleteConnection(String connectionUUID) {
		return connectionsDao.deleteConnection(connectionUUID);
		
	}

	@Override
	public int acceptConnection(User user, User toUser, String status) {
		return connectionsDao.acceptConnection(user, toUser, status);
	}

	@Override
	public List<UserRoleDTO> getConnectedList(String searchValue, Integer offset, Long id, String status) {
		return connectionsDao.getConnectedList(searchValue, offset, id, status);
	}

	@Override
	public Long getConnectionReceivedCount(Long id) {
		return connectionsDao.getConnectionReceivedCount(id);
	}

	@Override
	public Connections getConnectionByUUID(String connectionUUID) throws Exception {
		return connectionsDao.getConnectionByUUID(connectionUUID);
	}

	@Override
	public Connections updateConnection(Connections connections) throws Exception {
		return connectionsDao.updateConnection(connections);
	}

	@Override
	public Long checkUserInContacts(Long fromUid, Long toUid) {
		return connectionsDao.checkUserInContacts(fromUid, toUid);
	}


}
