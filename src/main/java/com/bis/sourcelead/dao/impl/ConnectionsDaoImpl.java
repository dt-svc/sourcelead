package com.bis.sourcelead.dao.impl;

import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ConnectionsDao;
import com.bis.sourcelead.dto.ConnectionsDTO;
import com.bis.sourcelead.entity.Connections;
import com.bis.sourcelead.enums.ConnectionStatus;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
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
				String query = "select count(connections) from Connections connections where (connections.fromUser.id = :fromUser and connections.toUser.id = :toUser and connections.connectionStatus = :connectionStatus) or (connections.fromUser.id = :toUser and connections.toUser.id = :fromUser and connections.connectionStatus = :connectionStatus)";
				Long count = (Long) session.createQuery(query).setLong("fromUser", connections.getFromUser().getId())
						.setLong("toUser", connections.getToUser().getId())
						.setString("connectionStatus", connections.getConnectionStatus().toString()).uniqueResult();
				if (count == 0) {
					session.saveOrUpdate(connections);
				}
			}
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "Connections"));
			throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
		}
		return connections;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ConnectionsDTO> getConnectionsListByStatus(String searchValue, Integer offset, Long userId,
			String status) throws Exception {
		List<ConnectionsDTO> usersList = new ArrayList<ConnectionsDTO>();
		Session session = this.sessionFactory.getCurrentSession();
		String searchByValue = searchValue.trim();
		StringBuilder subQuery = new StringBuilder("");
		String resultSqlQuery = null;
		String sqlQuery = "select distinct u.ID as id, u.USER_CODE as userCode, u.FILE_URL as fileUrl, concat(u.FIRST_NAME,' ',IFNULL(u.LAST_NAME, '')) as userName, u.EMAIL as email, ur.USER_TYPE as userType, ol.ORG_NAME as orgName,"
				+ "((select count(c.FROM_UID) from CONNECTIONS c where c.TO_UID = :loginUserId "
				+ "and (c.FROM_UID in (select c.FROM_UID from CONNECTIONS c where c.TO_UID = u.ID and c.CONNECTION_STATUS = 'CONNECTED') or "
				+ "c.FROM_UID in (select c.TO_UID from CONNECTIONS c where c.FROM_UID = u.ID and c.CONNECTION_STATUS = 'CONNECTED')))+ "
				+ "(select count(c.TO_UID) from CONNECTIONS c where c.FROM_UID = :loginUserId "
				+ "and (c.TO_UID in (select c.FROM_UID from CONNECTIONS c where c.TO_UID = u.ID and c.CONNECTION_STATUS = 'CONNECTED') or "
				+ "c.TO_UID in (select c.TO_UID from CONNECTIONS c where c.FROM_UID = u.ID and c.CONNECTION_STATUS = 'CONNECTED')))) as count, "
				+ "(select c.CONNECTION_UID from CONNECTIONS c where (c.FROM_UID = :loginUserId and c.TO_UID = u.ID) or (c.FROM_UID = u.ID and c.TO_UID = :loginUserId)) as connectionUUID "
				+ "from USER u, USER_ROLE ur, ORG_LOCATIONS ol, CONNECTIONS c "
				+ "where ur.USER_ID = u.ID and ur.ORG_LOCATIONS_ID = ol.ID and ur.IS_PRIMARY = 1 ";
		if (status.equalsIgnoreCase("sent")) {
			resultSqlQuery = sqlQuery.concat(
					"and c.FROM_UID = :loginUserId and c.TO_UID = u.ID and c.CONNECTION_STATUS = :connectionStatus");
		} else if (status.equalsIgnoreCase("received")) {
			resultSqlQuery = sqlQuery.concat(
					"and c.TO_UID = :loginUserId and c.FROM_UID = u.ID and c.CONNECTION_STATUS = :connectionStatus");
		} else if (status.equalsIgnoreCase("connected")) {
			resultSqlQuery = sqlQuery.concat(
					"and ((c.FROM_UID = u.ID and c.TO_UID = :loginUserId and c.CONNECTION_STATUS = :connectionStatus) or (c.TO_UID = u.ID and c.FROM_UID = :loginUserId and c.CONNECTION_STATUS = :connectionStatus))");
		}

		if (searchByValue != null && !"".equalsIgnoreCase(searchByValue)) {
			subQuery.append(
					" and (ur.USER_TYPE = :searchByUserType or u.FIRST_NAME like :searchByValue or u.LAST_NAME like :searchByValue or ol.ORG_NAME like :searchByValue or u.EMAIL like :searchByValue or concat(u.FIRST_NAME,' ',IFNULL(u.LAST_NAME, '')) like :searchByValue)");
			resultSqlQuery = resultSqlQuery.concat(subQuery.toString());
		}
		resultSqlQuery = resultSqlQuery.concat(" order by count DESC");
		Query queryString = session.createSQLQuery(resultSqlQuery);
		queryString.setParameter("loginUserId", userId);// setting same parameters multiple times in query
		if (status.equalsIgnoreCase("connected")) {
			queryString.setString("connectionStatus", ConnectionStatus.CONNECTED.toString());
		} else {
			queryString.setString("connectionStatus", ConnectionStatus.REQUEST_SENT.toString());
		}
		if (searchByValue != null && !"".equalsIgnoreCase(searchByValue)) {
			queryString.setParameter("searchByUserType", searchByValue).setParameter("searchByValue",
					"%" + searchByValue + "%");
		}
		// Pagination //Converting custom DTO class list from Sql result
		queryString.setFirstResult(offset).setMaxResults(18)
				.setResultTransformer(Transformers.aliasToBean(ConnectionsDTO.class));
		usersList = queryString.list();
		if (usersList.isEmpty()) {
			ConnectionsDTO connectionsDTO = new ConnectionsDTO();
			connectionsDTO.setCountFlag("true");
			usersList.add(connectionsDTO);
		}
		Map<String, BigInteger> countValues = getCountValuesForSentReceivedConnected(userId);
		if (!usersList.isEmpty()) {
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
	public Long getConnectionReceivedCount(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select count(connections) from Connections connections where connections.toUser.id = :userId and connections.connectionStatus = :connectionStatus";
		Long count = (Long) session.createQuery(query).setLong("userId", id)
				.setString("connectionStatus", ConnectionStatus.REQUEST_SENT.toString()).uniqueResult();
		return count;
	}

	@Override
	@Transactional
	public Connections getConnectionByUUID(String connectionUUID) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Connections> connections = new ArrayList<Connections>();
		logger.debug("getConnectionByUUID start");
		connections = session.createQuery(
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
		String checkConnectedStatus = "select count(c) from Connections c where (c.fromUser.id = :fromUid and c.toUser.id = :toUid and c.connectionStatus = :status) or (c.fromUser.id = :toUid and c.toUser.id = :fromUid and c.connectionStatus = :status)";
		Long count = (Long) session.createQuery(checkConnectedStatus)
				.setLong("fromUid", connections.getFromUser().getId()).setLong("toUid", connections.getToUser().getId())
				.setString("status", connections.getConnectionStatus().toString()).uniqueResult();
		if (count == 0) {
			try {
				if (connections != null) {
					session.update(connections);
				}
				logger.debug("connections updated Successfully");
			} catch (Exception exception) {
				StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "CONNECTIONS"));
				throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
			}
		}
		return connections;
	}

	@Override
	@Transactional
	public Long checkUserInContacts(Long fromUid, Long toUid) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select count(referContacts) from ReferContacts referContacts where referContacts.referFromUser.id = :fromUid and referContacts.contactUser.id = :toUid";
		Long count = (Long) session.createQuery(query).setLong("fromUid", fromUid).setLong("toUid", toUid)
				.uniqueResult();
		return count;
	}

	@Override
	@Transactional
	public Map<String, BigInteger> getCountValuesForSentReceivedConnected(Long userId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select (select count(*) from CONNECTIONS c where c.FROM_UID = :userId and c.CONNECTION_STATUS = 'REQUEST_SENT') as sentCount, "
				+ "(select count(*) from CONNECTIONS c where c.TO_UID = :userId and c.CONNECTION_STATUS = 'REQUEST_SENT') as receivedCount, "
				+ "(select count(*) from CONNECTIONS c where (c.TO_UID = :userId or c.FROM_UID = :userId) and c.CONNECTION_STATUS = 'CONNECTED') as connectedCount";
		Query queryString = session.createSQLQuery(query);
		queryString.setParameter("userId", userId);
		List data = queryString.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		Map<String, BigInteger> countValuesMap = new HashMap<>();
		for (Object object : data) {
			Map row = (Map) object;
			countValuesMap.put("sentCount", new BigInteger(row.get("sentCount").toString()));
			countValuesMap.put("receivedCount", new BigInteger(row.get("receivedCount").toString()));
			countValuesMap.put("connectedCount", new BigInteger(row.get("connectedCount").toString()));
		}
		return countValuesMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ConnectionsDTO> getMututalConnections(Long userId, Long sessionUid) {
		List<ConnectionsDTO> usersList = new ArrayList<ConnectionsDTO>();
		Session session = this.sessionFactory.getCurrentSession();
		String sqlQuery = "(SELECT u.FILE_URL as fileUrl, concat(u.FIRST_NAME,' ',IFNULL(u.LAST_NAME, '')) as userName, u.EMAIL as email, ur.USER_TYPE as userType, ol.ORG_NAME as orgName\r\n"
				+ "        FROM "
				+ "            CONNECTIONS c, USER u, USER_ROLE ur, ORG_LOCATIONS ol "
				+ "        WHERE " + "            c.TO_UID =  :sessionUid "
				+ "                AND (c.FROM_UID IN (SELECT " + "                    c.FROM_UID "
				+ "                FROM " + "                    CONNECTIONS c " + "                WHERE "
				+ "                    c.TO_UID = :userId "
				+ "                        AND c.CONNECTION_STATUS = 'CONNECTED') "
				+ "                OR c.FROM_UID IN (SELECT " + "                    c.TO_UID "
				+ "                FROM " + "                    CONNECTIONS c " + "                WHERE "
				+ "                    c.FROM_UID = :userId"
				+ "                        AND c.CONNECTION_STATUS = 'CONNECTED'))  AND c.FROM_UID = u.ID AND  ur.USER_ID = u.ID and ur.ORG_LOCATIONS_ID = ol.ID and ur.IS_PRIMARY = 1) "
				+ "						UNION " + "						(SELECT "
				+ "            u.FILE_URL as fileUrl, concat(u.FIRST_NAME,' ',IFNULL(u.LAST_NAME, '')) as userName, u.EMAIL as email, ur.USER_TYPE as userType, ol.ORG_NAME as orgName "
				+ "        FROM "
				+ "            CONNECTIONS c, USER u, USER_ROLE ur, ORG_LOCATIONS ol "
				+ "        WHERE " + "            c.FROM_UID =  :sessionUid "
				+ "                AND (c.TO_UID IN (SELECT " + "                    c.FROM_UID "
				+ "                FROM " + "                    CONNECTIONS c " + "                WHERE "
				+ "                    c.TO_UID = :userId "
				+ "                        AND c.CONNECTION_STATUS = 'CONNECTED') "
				+ "                OR c.TO_UID IN (SELECT " + "                    c.TO_UID " + "                FROM "
				+ "                    CONNECTIONS c " + "                WHERE "
				+ "                    c.FROM_UID = :userId "
				+ "                        AND c.CONNECTION_STATUS = 'CONNECTED')) AND c.TO_UID = u.ID AND  ur.USER_ID = u.ID and ur.ORG_LOCATIONS_ID = ol.ID and ur.IS_PRIMARY = 1)";
		Query queryString = session.createSQLQuery(sqlQuery);
		queryString.setParameter("userId", userId).setParameter("sessionUid", sessionUid);
		queryString.setResultTransformer(Transformers.aliasToBean(ConnectionsDTO.class));
		usersList = queryString.list();
		return usersList;
	}

	@Override
	@Transactional
	public int saveConnectionsList(List<Connections> connections) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.doWork(new Work() {
				@Override
				public void execute(java.sql.Connection connection) throws SQLException {
					boolean initialAutocommitSetting = connection.getAutoCommit();
					connection.setAutoCommit(false);
					CallableStatement stmt = connection.prepareCall("{call connectionRequestSent(?,?,?,?,?,?)}");
					for (Connections con : connections) {
						stmt.setLong(1, con.getFromUser().getId());
						stmt.setLong(2, con.getToUser().getId());
						stmt.setString(3, con.getConnectionStatus().toString());
						stmt.setLong(4, con.getStatus());
						stmt.setString(5, con.getConnectionUID());
						stmt.setDate(6, new java.sql.Date(con.getCreatedDate().getTime()));
						stmt.addBatch();
					}
					int[] updateCount = stmt.executeBatch();
					connection.commit();
					connection.setAutoCommit(initialAutocommitSetting);
				}
			});
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	@Transactional
	public String withdrawAllValues(String[] connectionsUerIdList) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.doWork(new Work() {
				@Override
				public void execute(java.sql.Connection connection) throws SQLException {
					boolean initialAutocommitSetting = connection.getAutoCommit();
					connection.setAutoCommit(false);
					CallableStatement stmt = connection.prepareCall("{call withdrawAllRequest(?)}");
					if (connectionsUerIdList != null) {
						for (String uuid : connectionsUerIdList) {
							stmt.setString(1, uuid);
							stmt.addBatch();
						}
					}
					int[] updateCount = stmt.executeBatch();
					connection.commit();
					connection.setAutoCommit(initialAutocommitSetting);
				}
			});
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
	}

}
