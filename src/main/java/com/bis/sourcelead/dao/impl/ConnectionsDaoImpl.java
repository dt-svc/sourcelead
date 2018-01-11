package com.bis.sourcelead.dao.impl;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bouncycastle.crypto.tls.SessionParameters;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ConnectionsDao;
import com.bis.sourcelead.dto.UserRoleDTO;
import com.bis.sourcelead.entity.Connections;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.ConnectionStatus;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.model.ConnectionBean;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class ConnectionsDaoImpl implements ConnectionsDao {
	
	private static final Logger logger = LoggerFactory.getLogger(ConnectionsDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Value("${SourceLead.100}")
	private String notFound;
	
	@Value("${SourceLead.101}")
	private String noRecords;
	
	@Value("${SourceLead.102}")
	private String statusUpdate;
	
	@Value("${SourceLead.103}")
	private String statusDelete;
	
	@Value("${SourceLead.106}")
	private String duplicateValue;
	

	@Override
	@Transactional
	public Connections saveConnection(Connections connections) throws Exception {
		
		logger.debug("saveConnection");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (connections != null) {
				String query = "select count(connections) from Connections connections where connections.fromUser.id = :fromUser and connections.toUser.id = :toUser and connections.connectionStatus = :connectionStatus";
				Long count = (Long) session.createQuery(query).setLong("fromUser", connections.getFromUser().getId()).setLong("toUser", connections.getToUser().getId()).setString("connectionStatus", connections.getConnectionStatus().toString()).uniqueResult();
				if(count == 0) {
					session.saveOrUpdate(connections);
				}
			}
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"Connections"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return connections;
		
	}


	@Override
	@Transactional
	public List<UserRoleDTO> getConnectionsListByStatus(String searchValue, Integer offset, Long userId,
			String connectionStatus, String status) throws Exception {
		List<UserRoleDTO> userRoleDTOList = new ArrayList<>();
		Session session = this.sessionFactory.getCurrentSession();
		String searchByValue = searchValue.trim();
		StringBuilder subQuery = new StringBuilder("");
		if(searchByValue != null && !"".equalsIgnoreCase(searchByValue)) {
			subQuery.append(" and( (userRole.user.firstName like '" + searchByValue + "%' OR userRole.user.lastName like '%" + searchByValue + "' )")
			   .append("OR (concat(userRole.user.firstName,userRole.user.lastName) like '%" + searchByValue.replaceAll("[\\s]+", "").trim() + "%')" )
			   .append("OR (concat(userRole.orgLocations.orgLocationName) like '%" + searchByValue + "%')" )
			   .append("OR (concat(userRole.user.email) like '%" + searchByValue +"%')")
			   .append("OR userRole.roles.displayName like '%" + searchByValue + "%' )");
		}
		String query = null;
		if(status.equals("sent")) {
			query = "from Connections connections left join fetch connections.fromUser left join fetch connections.toUser "
					+ " where connections.fromUser.id = :userId and connections.connectionStatus = :connectionStatus ";
		}else {
			query = "from Connections connections left join fetch connections.fromUser left join fetch connections.toUser "
					+ " where connections.toUser.id = :userId and connections.connectionStatus = :connectionStatus ";
		}
		List<Connections> connectionsList = session.createQuery(query.toString()).setLong("userId", userId).setString("connectionStatus", ConnectionStatus.getNameByValue(connectionStatus).toString()).setCacheable(true).list();
			if(connectionsList != null && connectionsList.size() > 0) {
				for(Connections connections : connectionsList) {
					UserRoleDTO userRoleDTO = new UserRoleDTO();
					StringBuilder userName = new StringBuilder();
					
					String query2 = "from UserRole userRole left join fetch userRole.user "
							+ "where userRole.user.id = :userId and userRole.isPrimary = '1' "+subQuery.toString()+"  ";
					Long userIdValue = null;
					if(status.equals("sent")) {
						if(connections.getToUser().getLastName() != null) {
							userName = userName.append(connections.getToUser().getFirstName()).append(" ").append(connections.getToUser().getLastName());
						}else {
							userName = userName.append(connections.getToUser().getFirstName());
						}
						userIdValue = connections.getToUser().getId();
					}else {
						if(connections.getFromUser().getLastName() != null) {
							userName = userName.append(connections.getFromUser().getFirstName()).append(" ").append(connections.getFromUser().getLastName());
						}else {
							userName = userName.append(connections.getFromUser().getFirstName());
						}
						userIdValue = connections.getFromUser().getId();
					}
					userRoleDTO.setUserName(userName.toString());
					UserRole userRole = (UserRole) session.createQuery(query2).setLong("userId", userIdValue).setCacheable(true).uniqueResult();
					if(userRole != null) {
						String queryMutual = "select count(connections) from Connections connections where (connections.fromUser.id = :userId or connections.toUser.id = :userId) and connections.fromUser.id <> :loginUserId and connections.toUser.id <> :loginUserId and connections.connectionStatus = :connectionStatus";
						Long count = (Long) session.createQuery(queryMutual).setLong("loginUserId", userId).setLong("userId", userIdValue).setString("connectionStatus", ConnectionStatus.CONNECTED.toString()).setCacheable(true).uniqueResult();
						userRoleDTO.setMutualCount(count);
						userRoleDTO.setUserRoleName(userRole.getRoles().getDisplayName());
						String userOrgName = null;
						if(userRole.getRoles().getId() == 10) {
							userOrgName = "";
						}else if(userRole.getRoles().getId() == 11) {
							userOrgName = "";
						}else {
							userOrgName = userRole.getOrgLocations().getOrgLocationName();
						}
						userRoleDTO.setOrgName(userOrgName);
						userRoleDTO.setUserId(userRole.getUser().getId());
						userRoleDTO.setUserCode(userRole.getUser().getUserCode());
						userRoleDTO.setFileUrl(userRole.getUser().getFileUrl());
						userRoleDTOList.add(userRoleDTO);
					}
				}
				if(userRoleDTOList.size() >= offset){
					if(userRoleDTOList.size() > offset + 18){
						userRoleDTOList = userRoleDTOList.subList(offset, (offset+18));
					}else{
						userRoleDTOList = userRoleDTOList.subList(offset, (  offset + (userRoleDTOList.size() - offset)));
					}
				return  userRoleDTOList;
			}else{
				return  null;
			}
			}else {
				return null;
			}
			
	}


	@Override
	@Transactional
	public int deleteConnection(User user, User toUser, String status) {
		logger.debug("deleteConnection");
		Session session = this.sessionFactory.getCurrentSession();
		Query qry = null;
		String queryGet = "from Connections connections where (connections.fromUser.id = :fromUser and connections.toUser.id = :toUser) or (connections.fromUser.id = :toUser and connections.toUser.id = :fromUser) and connections.connectionStatus = :status";
		Connections connections = (Connections) session.createQuery(queryGet).setLong("fromUser", user.getId()).setLong("toUser", toUser.getId()).setString("status", ConnectionStatus.getNameByValue(status).toString()).uniqueResult();
		String query = "delete from Connections connections where connections.id =:id";
    	qry = session.createQuery(query).setLong("id", connections.getId());
    	int i = qry.executeUpdate();
    	return i;
	}


	@Override
	@Transactional
	public int acceptConnection(User user, User toUser, String status) {
		logger.debug("deleteConnection");
		Session session = this.sessionFactory.getCurrentSession();
		Query qry = null;
		String queryString="UPDATE Connections connections SET connections.connectionStatus = :status, connections.createdDate = :createdDate WHERE connections.fromUser.id = :fromUid and connections.toUser.id = :toUid";
		qry = session.createQuery(queryString).setLong("fromUid", toUser.getId()).setLong("toUid", user.getId()).setTimestamp("createdDate", new Timestamp(new Date().getTime())).setString("status", ConnectionStatus.getNameByValue(status).toString());
    	int i = qry.executeUpdate();
    	return i;
	}


	@Override
	@Transactional
	public List<UserRoleDTO> getConnectedList(String searchValue, Integer offset, Long id, String status) {
		List<UserRoleDTO> userRoleDTOList = new ArrayList<>();
		Session session = this.sessionFactory.getCurrentSession();
		String searchByValue = searchValue.trim();
		StringBuilder subQuery = new StringBuilder("");
		if(searchByValue != null && !"".equalsIgnoreCase(searchByValue)) {
			subQuery.append(" and( (userRole.user.firstName like '" + searchByValue + "%' OR userRole.user.lastName like '%" + searchByValue + "' )")
			   .append("OR (concat(userRole.user.firstName,userRole.user.lastName) like '%" + searchByValue.replaceAll("[\\s]+", "").trim() + "%')" )
			   .append("OR (concat(userRole.orgLocations.orgLocationName) like '%" + searchByValue + "%')" )
			   .append("OR (concat(userRole.user.email) like '%" + searchByValue +"%')")
			   .append("OR userRole.roles.displayName like '%" + searchByValue + "%' )");
		}
		String query = "from Connections connections left join fetch connections.fromUser left join fetch connections.toUser "
					+ " where (connections.toUser.id =:userId or connections.fromUser.id =:userId1) and connections.connectionStatus = :connectionStatus ";
		List<Connections> connectionsList = session.createQuery(query.toString()).setLong("userId", id).setLong("userId1", id).setString("connectionStatus", ConnectionStatus.getNameByValue(status).toString()).setCacheable(true).list();
			if(connectionsList != null && connectionsList.size() > 0) {
				for(Connections connections : connectionsList) {
					UserRoleDTO userRoleDTO = new UserRoleDTO();
					StringBuilder userName = new StringBuilder();
					String queryRole = "from UserRole userRole left join fetch userRole.user "
							+ "where userRole.user.id = :userId and userRole.isPrimary = '1' "+subQuery.toString()+" ";
					Long userId = null;
					if(id == connections.getToUser().getId()) {
						if(connections.getFromUser().getLastName() != null) {
							userName = userName.append(connections.getFromUser().getFirstName()).append(" ").append(connections.getFromUser().getLastName());
						}else {
							userName = userName.append(connections.getFromUser().getFirstName());
						}
						userRoleDTO.setUserName(userName.toString());
						userId = connections.getFromUser().getId();
						
					}else if(id == connections.getFromUser().getId()) {
						if(connections.getToUser().getLastName() != null) {
							userName = userName.append(connections.getToUser().getFirstName()).append(" ").append(connections.getToUser().getLastName());
						}else {
							userName = userName.append(connections.getToUser().getFirstName());
						}
						userRoleDTO.setUserName(userName.toString());
						userId = connections.getToUser().getId();
					}
					UserRole userRole = null;
					userRole = (UserRole) session.createQuery(queryRole).setLong("userId", userId).setCacheable(true).uniqueResult();
					if(userRole != null) {
						String queryMutual = "select count(connections) from Connections connections where (connections.fromUser.id = :userId or connections.toUser.id = :userId) and connections.fromUser.id <> :loginUserId and connections.toUser.id <> :loginUserId and connections.connectionStatus = :connectionStatus";
						Long count = (Long) session.createQuery(queryMutual).setLong("loginUserId", id).setLong("userId", userId).setString("connectionStatus", ConnectionStatus.CONNECTED.toString()).setCacheable(true).uniqueResult();
						userRoleDTO.setMutualCount(count);
						userRoleDTO.setUserRoleName(userRole.getRoles().getDisplayName());
						String userOrgName = null;
						if(userRole.getRoles().getId() == 10) {
							userOrgName = "";
						}else if(userRole.getRoles().getId() == 11) {
							userOrgName = "";
						}else {
							userOrgName = userRole.getOrgLocations().getOrgLocationName();
						}
						userRoleDTO.setOrgName(userOrgName);
						userRoleDTO.setUserId(userRole.getUser().getId());
						userRoleDTO.setUserCode(userRole.getUser().getUserCode());
						userRoleDTO.setFileUrl(userRole.getUser().getFileUrl());
						userRoleDTOList.add(userRoleDTO);
					}
				}
				if(userRoleDTOList.size() >= offset){
					if(userRoleDTOList.size() > offset + 18){
						userRoleDTOList = userRoleDTOList.subList(offset, (offset+18));
					}else{
						userRoleDTOList = userRoleDTOList.subList(offset, (  offset + (userRoleDTOList.size() - offset)));
					}
				return  userRoleDTOList;
			}else{
				return  null;
			}
			}else {
				return null;
			}
	}

	@Override
	@Transactional
	public Long getConnectionReceivedCount(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select count(connections) from Connections connections where connections.toUser.id = :userId and connections.connectionStatus = :connectionStatus";
		Long count = (Long) session.createQuery(query).setLong("userId", id).setString("connectionStatus", ConnectionStatus.REQUEST_SENT.toString()).uniqueResult();
		return count;
	}

}
