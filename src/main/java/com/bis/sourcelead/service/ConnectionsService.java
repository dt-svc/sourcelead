package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.ConnectionsDTO;
import com.bis.sourcelead.entity.Connections;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface ConnectionsService {

	String saveConnection(User user, String userCode, OrgLocations loginOrgLocation, String sourceleadUrl, String sourceleadLoginUrl) throws Exception;

	Connections updateConnection(Connections connections) throws Exception;

	List<ConnectionsDTO> getConnectionsListByStatus(String searchValue, Integer offset, Long userId, String status)
			throws Exception;

	Long getConnectionReceivedCount(Long id);

	Connections getConnectionByUUID(String connectionUUID) throws Exception;

	Long checkUserInContacts(Long fromUid, Long toUid);

	List<ConnectionsDTO> getMututalConnections(Long userId, Long sessionUid);

	int deleteConnection(String connectionUUID);

	String inviteAllMutualAndSearch(User user, String[] connectionsUerIdList, OrgLocations loginOrgLocation, String sourceleadUrl, String sourceleadLoginUrl);

	String withdrawAllValues(String[] connectionsUerIdList);

}
