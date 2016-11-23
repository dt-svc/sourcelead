package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.BoJmpcTransDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.BoJmpcTrans;

@Repository
public class BoJmpcTransDaoImpl implements BoJmpcTransDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
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
	

	private static final Logger logger = LoggerFactory.getLogger(BoJmpcTransDaoImpl.class);


	@Override
	@Transactional
	public List<BoJmpcTrans> getBoJmpcTrans() throws Exception {
		
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from BoJmpcTrans bjct where bjct.status = '1' "; 
		List<BoJmpcTrans> boJmpcTransList = session.createQuery(query.toString()).setCacheable(true).list();
		return boJmpcTransList;
	}


	@Override
	@Transactional
	public List<BoJmpcTrans> getBoJmpcTransByFilters(JSONObject jsonObj) throws Exception {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from BoJmpcTrans bjct where bjct.status = '1' "; 
		List<BoJmpcTrans> boJmpcTransList = session.createQuery(query.toString()).setCacheable(true).list();
		return boJmpcTransList;
	}

}
