package com.bis.sourcelead.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.ConnectionsDTO;
import com.bis.sourcelead.dto.UserRoleDTO;
import com.bis.sourcelead.entity.Connections;
import com.bis.sourcelead.entity.User;

public interface ConnectionsDao {

	Connections saveConnection(Connections connections) throws Exception;
	
	Connections updateConnection(Connections connections) throws Exception;

	List<ConnectionsDTO> getConnectionsListByStatus(String searchValue, Integer offset, Long userId, String connectionStatus) throws Exception;

	int acceptConnection(User user, User toUser, String status);

	Long getConnectionReceivedCount(Long id);
	
	Connections getConnectionByUUID(String connectionUUID) throws Exception;

	Long checkUserInContacts(Long fromUid, Long toUid);

	int deleteConnection(String connectionUUID);

	Map<String, BigInteger> getCountValuesForSentReceivedConnected(Long userId);
	
	

}
