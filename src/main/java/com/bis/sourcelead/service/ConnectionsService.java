package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.ConnectionsDTO;
import com.bis.sourcelead.dto.UserRoleDTO;
import com.bis.sourcelead.entity.Connections;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ConnectionStatus;

public interface ConnectionsService {

	
	Connections saveConnection(Connections connections) throws Exception;
	
	Connections updateConnection(Connections connections) throws Exception;

	List<ConnectionsDTO> getConnectionsListByStatus(String searchValue, Integer offset, Long userId, String status) throws Exception;

	int acceptConnection(User user, User toUser, String status);

	Long getConnectionReceivedCount(Long id);
	
	Connections getConnectionByUUID(String connectionUUID) throws Exception;

	Long checkUserInContacts(Long fromUid, Long toUid);

	int deleteConnection(String connectionUUID);

}
