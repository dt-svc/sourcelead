package com.bis.sourcelead.dao.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bouncycastle.crypto.tls.SessionParameters;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ConnectionsDao;
import com.bis.sourcelead.dto.ConnectionsDTO;
import com.bis.sourcelead.dto.UserRoleDTO;
import com.bis.sourcelead.entity.Connections;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.ConnectionStatus;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.model.ConnectionBean;
import com.bis.sourcelead.util.CommonUtil;
import com.sun.jersey.api.representation.Form;

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


	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ConnectionsDTO> getConnectionsListByStatus(String searchValue, Integer offset, Long userId, String status) throws Exception {
		List<ConnectionsDTO> usersList = new ArrayList<ConnectionsDTO>();
		Session session = this.sessionFactory.getCurrentSession();
		String searchByValue = searchValue.trim();
		StringBuilder subQuery = new StringBuilder("");
		String resultSqlQuery = null;
		String sqlQuery = "select distinct u.ID as id, u.USER_CODE as userCode, u.FILE_URL as fileUrl, concat(u.FIRST_NAME,' ',IFNULL(u.LAST_NAME, '')) as userName, u.EMAIL as email, ur.USER_TYPE as userType, ol.ORG_NAME as orgName," + 
				"((select count(c.FROM_UID) from sourcelead.CONNECTIONS c where c.TO_UID = u.ID "+
				"and (c.FROM_UID in (select c.FROM_UID from sourcelead.CONNECTIONS c where c.TO_UID = :loginUserId and c.CONNECTION_STATUS = 'CONNECTED') or "+
				"c.FROM_UID in (select c.TO_UID from sourcelead.CONNECTIONS c where c.FROM_UID = :loginUserId and c.CONNECTION_STATUS = 'CONNECTED')))+ "+ 
				"(select count(c.TO_UID) from sourcelead.CONNECTIONS c where c.FROM_UID = u.ID "+
				"and (c.TO_UID in (select c.FROM_UID from sourcelead.CONNECTIONS c where c.TO_UID = :loginUserId and c.CONNECTION_STATUS = 'CONNECTED') or "+
				"c.TO_UID in (select c.TO_UID from sourcelead.CONNECTIONS c where c.FROM_UID = :loginUserId and c.CONNECTION_STATUS = 'CONNECTED')))) as count, "+
				"(select c.CONNECTION_UID from sourcelead.CONNECTIONS c where (c.FROM_UID = :loginUserId and c.TO_UID = u.ID) or (c.FROM_UID = u.ID and c.TO_UID = :loginUserId)) as connectionUUID " +
				"from sourcelead.USER u, sourcelead.USER_ROLE ur, sourcelead.ORG_LOCATIONS ol, sourcelead.CONNECTIONS c " + 
				"where ur.USER_ID = u.ID and ur.ORG_LOCATIONS_ID = ol.ID and ur.IS_PRIMARY = 1 "; 
		if(status.equalsIgnoreCase("sent")) {
			resultSqlQuery = sqlQuery.concat("and c.FROM_UID = :loginUserId and c.TO_UID = u.ID and c.CONNECTION_STATUS = :connectionStatus");
		}else if(status.equalsIgnoreCase("received")) {
			resultSqlQuery = sqlQuery.concat("and c.TO_UID = :loginUserId and c.FROM_UID = u.ID and c.CONNECTION_STATUS = :connectionStatus");
		}else if(status.equalsIgnoreCase("connected")) {
			resultSqlQuery = sqlQuery.concat("and ((c.FROM_UID = u.ID and c.TO_UID = :loginUserId and c.CONNECTION_STATUS = :connectionStatus) or (c.TO_UID = u.ID and c.FROM_UID = :loginUserId and c.CONNECTION_STATUS = :connectionStatus))");
		}
		
		if(searchByValue != null && !"".equalsIgnoreCase(searchByValue)) {
			subQuery.append(" and (ur.USER_TYPE = :searchByUserType or u.FIRST_NAME like :searchByValue or u.LAST_NAME like :searchByValue or ol.ORG_NAME like :searchByValue or u.EMAIL like :searchByValue or concat(u.FIRST_NAME,' ',IFNULL(u.LAST_NAME, '')) like :searchByValue)");
			resultSqlQuery = resultSqlQuery.concat(subQuery.toString());
		}
		resultSqlQuery = resultSqlQuery.concat(" order by count DESC, userName");
		Query queryString = session.createSQLQuery(resultSqlQuery);
		queryString.setParameter("loginUserId", userId);//setting same parameters multiple times in query
		if(status.equalsIgnoreCase("connected")) {
			queryString.setString("connectionStatus", ConnectionStatus.CONNECTED.toString());
		}else {
			queryString.setString("connectionStatus", ConnectionStatus.REQUEST_SENT.toString());
		}
		if(searchByValue != null && !"".equalsIgnoreCase(searchByValue)) {
			queryString.setParameter("searchByUserType",searchByValue)
			.setParameter("searchByValue", "%" + searchByValue + "%");
		}
		//Pagination                                                              //Converting custom DTO class list from Sql result 
		queryString.setFirstResult(offset).setMaxResults(18).setResultTransformer(Transformers.aliasToBean(ConnectionsDTO.class));
		usersList = queryString.list();
		if(usersList.isEmpty()) {
			ConnectionsDTO connectionsDTO = new ConnectionsDTO();
			connectionsDTO.setCountFlag("true");
			usersList.add(connectionsDTO);
		}
		Map<String, BigInteger> countValues = getCountValuesForSentReceivedConnected(userId);
		if(!usersList.isEmpty()) {
			usersList.get(0).setSentCount(countValues.get("sentCount"));
			usersList.get(0).setReceivedCount(countValues.get("receivedCount"));
			usersList.get(0).setConnectedCount(countValues.get("connectedCount"));
		}
		return usersList;
	}

	@Override
	@Transactional
	public int deleteConnection(String connectionUUID) {
		logger.debug("deleteConnection");
		Session session = this.sessionFactory.getCurrentSession();
		Query qry = null;
		String query = "delete from Connections connections where connections.connectionUID = :connectionUID";
    	qry = session.createQuery(query).setString("connectionUID", connectionUUID);
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
	public Long getConnectionReceivedCount(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select count(connections) from Connections connections where connections.toUser.id = :userId and connections.connectionStatus = :connectionStatus";
		Long count = (Long) session.createQuery(query).setLong("userId", id).setString("connectionStatus", ConnectionStatus.REQUEST_SENT.toString()).uniqueResult();
		return count;
	}


	@Override
	@Transactional
	public Connections getConnectionByUUID(String connectionUUID) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Connections> connections = new ArrayList<Connections>();
		logger.debug("getConnectionByUUID start");
		connections = session
				.createQuery(
						"from Connections connections left join fetch connections.fromUser left join fetch connections.toUser where connections.connectionUID = :connectionUUID")
				.setString("connectionUUID", connectionUUID).list();
		logger.debug("getConnectionByUUID end");
		if (connections.size() > 0) {
			return connections.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public Connections updateConnection(Connections connections) throws Exception {
		logger.debug("updateConnection");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (connections != null) {
				session.update(connections);
			}
			logger.debug("connections updated Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "CONNECTIONS"));
			throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
		}
		return connections;
	}


	@Override
	@Transactional
	public Long checkUserInContacts(Long fromUid, Long toUid) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select count(referContacts) from ReferContacts referContacts where referContacts.referFromUser.id = :fromUid and referContacts.contactUser.id = :toUid";
		Long count = (Long) session.createQuery(query).setLong("fromUid", fromUid).setLong("toUid", toUid).uniqueResult();
		return count;
	}

	@Override
	@Transactional
	public Map<String, BigInteger> getCountValuesForSentReceivedConnected(Long userId){
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select (select count(*) from sourcelead.CONNECTIONS c where c.FROM_UID = :userId and c.CONNECTION_STATUS = 'REQUEST_SENT') as sentCount, " + 
				"(select count(*) from sourcelead.CONNECTIONS c where c.TO_UID = :userId and c.CONNECTION_STATUS = 'REQUEST_SENT') as receivedCount, " + 
				"(select count(*) from sourcelead.CONNECTIONS c where (c.TO_UID = :userId or c.FROM_UID = :userId) and c.CONNECTION_STATUS = 'CONNECTED') as connectedCount";
		Query queryString = session.createSQLQuery(query);
		queryString.setParameter("userId", userId);
		List data = queryString.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		Map<String, BigInteger> countValuesMap = new HashMap<>();
		for(Object object: data) {
			Map row = (Map) object;
			countValuesMap.put("sentCount", new BigInteger(row.get("sentCount").toString()));
			countValuesMap.put("receivedCount", new BigInteger(row.get("receivedCount").toString()));
			countValuesMap.put("connectedCount", new BigInteger(row.get("connectedCount").toString()));
		}
		return countValuesMap;
	}
	
}
