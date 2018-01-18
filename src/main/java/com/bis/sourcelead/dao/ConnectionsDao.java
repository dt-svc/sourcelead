package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.UserRoleDTO;
import com.bis.sourcelead.entity.Connections;
import com.bis.sourcelead.entity.User;

public interface ConnectionsDao {

	Connections saveConnection(Connections connections) throws Exception;
	
	Connections updateConnection(Connections connections) throws Exception;

	List<UserRoleDTO> getConnectionsListByStatus(String searchValue, Integer offset, Long userId, String connectionStatus, String status) throws Exception;

	int deleteConnection(User user, User toUser, String value);

	int acceptConnection(User user, User toUser, String status);

	List<UserRoleDTO> getConnectedList(String searchValue, Integer offset, Long id, String value);

	Long getConnectionReceivedCount(Long id);
	
	Connections getConnectionByUUID(String connectionUUID) throws Exception;

	Long checkUserInContacts(Long fromUid, Long toUid);

}
