package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.bouncycastle.crypto.tls.SessionParameters;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ConnectionsDao;
import com.bis.sourcelead.entity.Connections;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ConnectionStatus;
import com.bis.sourcelead.model.ConnectionBean;

@Repository
public class ConnectionsDaoImpl implements ConnectionsDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public List<ConnectionBean> getFindConnections(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select u.id, u.first_name, u.last_name,( select count(*) from connections c "
				+ "where (u.id = c.from_uid or u.id = c.to_uid) and c.status like :status) as mutCount, u.file_url"
				+ " from user u where u.id != :id "
				+ "and u.id not in (select a.to_uid from connections a where a.from_UID = :id1) and u.id not in (select a.from_uid from connections a where a.to_UID = :id2) ";
		List<Object[]> findConnections = session.createSQLQuery(query)
				.setString("status", ConnectionStatus.CONNECTED.getValue().toString()).setLong("id", id).setLong("id1", id).setLong("id2", id).list();
		List<ConnectionBean> connectionsBeanList = new ArrayList<ConnectionBean>();
 		for(Object[] object: findConnections) {
			ConnectionBean connectionBean = new ConnectionBean();
			connectionBean.setId(Long.parseLong(object[0].toString()));
			connectionBean.setFirstName(object[1].toString());
			connectionBean.setLastName(object[2].toString());
			connectionBean.setMutCount(object[3].toString());
			if(object[4] != null) {
				connectionBean.setFileUrl(object[4].toString());
			}
			String query2 = "select user_type from user_role where user_id = :id";
			String userType = (String) session.createSQLQuery(query2).setLong("id", Long.parseLong(object[0].toString())).uniqueResult();
			connectionBean.setUserType(userType);
			String query1 = "select org_name, city from org_locations where PRIMARY_CONTACT_USER_ID = :id";
			Object[] objects =  (Object[]) session.createSQLQuery(query1).setLong("id", Long.parseLong(object[0].toString())).uniqueResult();
			if(objects != null) {
				if(objects[0] != null) {
					connectionBean.setOrgName(objects[0].toString());
				}
				if(objects[1] != null) {
					connectionBean.setCity(objects[1].toString());
				}
			}
			connectionsBeanList.add(connectionBean);
			}
		return connectionsBeanList;
	}

	@Override
	@Transactional
	public List<ConnectionBean> getSentList(Long userId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select distinct u.id, u.first_name, u.last_name,"
				+ " (select count(*) from connections c where (u.id = c.from_uid or u.id = c.to_uid) and c.status like :status) as mutCount, u.file_url from"
				+ " user u where u.id in (select a.to_uid from connections a where a.status like :statusSent and a.from_uid = :id)";
		List<Object[]> sentConnections = session.createSQLQuery(query)
				.setString("status", ConnectionStatus.CONNECTED.getValue().toString()).setString("statusSent", ConnectionStatus.REQUEST_SENT.getValue().toString()).setLong("id", userId).list();
		List<ConnectionBean> connectionsBeanList = new ArrayList<ConnectionBean>();
 		for(Object[] object: sentConnections) {
			ConnectionBean connectionBean = new ConnectionBean();
			connectionBean.setId(Long.parseLong(object[0].toString()));
			connectionBean.setFirstName(object[1].toString());
			connectionBean.setLastName(object[2].toString());
			connectionBean.setMutCount(object[3].toString());
			if(object[4] != null) {
				connectionBean.setFileUrl(object[4].toString());
			}
			String query2 = "select user_type from user_role where user_id = :id";
			String userType = (String) session.createSQLQuery(query2).setLong("id", Long.parseLong(object[0].toString())).uniqueResult();
			connectionBean.setUserType(userType);
			String query1 = "select org_name, city from org_locations where PRIMARY_CONTACT_USER_ID = :id";
			Object[] objects =  (Object[]) session.createSQLQuery(query1).setLong("id", Long.parseLong(object[0].toString())).uniqueResult();
			if(objects != null) {
				if(objects[0] != null) {
					connectionBean.setOrgName(objects[0].toString());
				}
				if(objects[1] != null) {
					connectionBean.setCity(objects[1].toString());
				}
			}
			connectionsBeanList.add(connectionBean);
			}
		return connectionsBeanList;
	}

	@Override
	@Transactional
	public void changeClickConnect(String userId, Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		Connections connections = new Connections();
		connections.setFromUid(id);
		connections.setToUid(Long.parseLong(userId));
		connections.setStatus(ConnectionStatus.REQUEST_SENT.getValue());
		session.save(connections);
	}

	/* (non-Javadoc)
	 * @see com.bis.sourcelead.dao.ConnectionsDao#winthdrawUser(java.lang.String, java.lang.Long)
	 */
	@Override
	@Transactional
	public void winthdrawUser(String userId, Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		int query = session.createQuery("delete Connections where fromUid = :fromUid and toUid = :toUid and status = :status").setLong("fromUid", id).setLong("toUid", Long.parseLong(userId)).setString("status", ConnectionStatus.REQUEST_SENT.getValue().toString()).executeUpdate();
	}

	@Override
	@Transactional
	public List<ConnectionBean> getReceivedList(Long userId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select distinct u.id, u.first_name, u.last_name, u.user_type, " + 
				"(select count(*) from connections c where (u.id = c.from_uid or u.id = c.to_uid) and c.status like :statusConnect) as mutCount, u.file_url " + 
				"from user u where u.id in (select a.from_uid from connections a where a.status like :statusSent and a.to_uid = :id)";
		List<Object[]> receivedConnections = session.createSQLQuery(query)
				.setString("statusConnect", ConnectionStatus.CONNECTED.getValue().toString()).setString("statusSent", ConnectionStatus.REQUEST_SENT.getValue().toString()).setLong("id", userId).list();
		List<ConnectionBean> connectionsBeanList = new ArrayList<ConnectionBean>();
 		for(Object[] object: receivedConnections) {
			ConnectionBean connectionBean = new ConnectionBean();
			connectionBean.setId(Long.parseLong(object[0].toString()));
			connectionBean.setFirstName(object[1].toString());
			connectionBean.setLastName(object[2].toString());
			connectionBean.setMutCount(object[3].toString());
			if(object[4] != null) {
				connectionBean.setFileUrl(object[4].toString());
			}
			String query2 = "select user_type from user_role where user_id = :id";
			String userType = (String) session.createSQLQuery(query2).setLong("id", Long.parseLong(object[0].toString())).uniqueResult();
			connectionBean.setUserType(userType);
			String query1 = "select org_name, city from org_locations where PRIMARY_CONTACT_USER_ID = :id";
			Object[] objects =  (Object[]) session.createSQLQuery(query1).setLong("id", Long.parseLong(object[0].toString())).uniqueResult();
			if(objects != null) {
				if(objects[0] != null) {
					connectionBean.setOrgName(objects[0].toString());
				}
				if(objects[1] != null) {
					connectionBean.setCity(objects[1].toString());
				}
			}
			connectionsBeanList.add(connectionBean);
			}
		return connectionsBeanList;
	}

	@Override
	@Transactional
	public void acceptRequest(String userId, Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Connections c where c.fromUid = :fromUid and c.toUid = :toUid and c.status like :status";
		Connections connection = (Connections) session.createQuery(query).setLong("fromUid",  Long.parseLong(userId)).setLong("toUid", id).setString("status", ConnectionStatus.REQUEST_SENT.getValue().toString()).uniqueResult();
		connection.setStatus(ConnectionStatus.CONNECTED.getValue().toString());
		session.update(connection);
	}
	
	@Override
	@Transactional
	public void ignoreUser(String userId, Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		int query = session.createQuery("delete Connections where fromUid = :fromUid and toUid = :toUid and status = :status").setLong("fromUid", Long.parseLong(userId)).setLong("toUid", id).setString("status", ConnectionStatus.REQUEST_SENT.getValue().toString()).executeUpdate();
	}

	@Override
	@Transactional
	public List<ConnectionBean> getConnectedList(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select distinct u.id, u.first_name, u.last_name, u.user_type, " + 
				"(select count(*) from connections c where (u.id = c.from_uid or u.id = c.to_uid) and c.status like :statusConnect) as mutCount, u.file_url " + 
				"from user u where u.id in (select a.from_uid from connections a where a.status like :status and (a.to_uid = :id1 or a.from_uid = :id2))";
		List<Object[]> receivedConnections = session.createSQLQuery(query)
				.setString("statusConnect", ConnectionStatus.CONNECTED.getValue().toString()).setString("status", ConnectionStatus.CONNECTED.getValue().toString()).setLong("id1", id).setLong("id2", id).list();
		List<ConnectionBean> connectionsBeanList = new ArrayList<ConnectionBean>();
 		for(Object[] object: receivedConnections) {
			ConnectionBean connectionBean = new ConnectionBean();
			connectionBean.setId(Long.parseLong(object[0].toString()));
			connectionBean.setFirstName(object[1].toString());
			connectionBean.setLastName(object[2].toString());
			connectionBean.setMutCount(object[3].toString());
			if(object[4] != null) {
				connectionBean.setFileUrl(object[4].toString());
			}
			String query2 = "select user_type from user_role where user_id = :id";
			String userType = (String) session.createSQLQuery(query2).setLong("id", Long.parseLong(object[0].toString())).uniqueResult();
			connectionBean.setUserType(userType);
			String query1 = "select org_name, city from org_locations where PRIMARY_CONTACT_USER_ID = :id";
			Object[] objects =  (Object[]) session.createSQLQuery(query1).setLong("id", Long.parseLong(object[0].toString())).uniqueResult();
			if(objects != null) {
				if(objects[0] != null) {
					connectionBean.setOrgName(objects[0].toString());
				}
				if(objects[1] != null) {
					connectionBean.setCity(objects[1].toString());
				}
			}
			connectionsBeanList.add(connectionBean);
			}
		return connectionsBeanList;
	}

	@Override
	@Transactional
	public void disconnectConnect(String userId, Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		int query = session.createQuery("delete Connections where fromUid = :fromUid and toUid = :toUid and status = :status").setLong("fromUid", Long.parseLong(userId)).setLong("toUid", id).setString("status", ConnectionStatus.CONNECTED.getValue().toString()).executeUpdate();
	}

}
