package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class BoJmpcTransDaoImpl implements BoJmpcTransDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private CommonUtil commonUtil;
	
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
	public BoJmpcTrans getBoJmpcTransById(Long Id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<BoJmpcTrans> boJmpcTrans = new ArrayList<BoJmpcTrans>();
		boJmpcTrans = session.createQuery("from BoJmpcTrans where id=:id" ).setCacheable(true).setLong( "id", Id ).setCacheable(true).list();
		if (boJmpcTrans.size() > 0) {
			return boJmpcTrans.get(0);
		} else {
			return null;
		}
   	}


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
		String whereString = commonUtil.getBackOfficeSummaryFilters(jsonObj);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date fromTransDate = null;
		Date toTransDate = null;
		if(jsonObj.has("fromTransDate") && jsonObj.getString("fromTransDate") != null && !"".equals(jsonObj.getString("fromTransDate")) 
				&& jsonObj.has("toTransDate") && "".equals(jsonObj.getString("toTransDate"))){
			    fromTransDate = sdf.parse(jsonObj.getString("fromTransDate"));
			return session.createQuery("from BoJmpcTrans bt where bt.status = '1' "+whereString+" ").setDate("fromTransDate", fromTransDate).setCacheable(true).list();
		}else if(jsonObj.has("toTransDate") && jsonObj.getString("toTransDate") != null && !"".equals(jsonObj.getString("toTransDate")) 
					&& jsonObj.has("fromTransDate") && "".equals(jsonObj.getString("fromTransDate"))){
			toTransDate = sdf.parse(jsonObj.getString("toTransDate"));
			return session.createQuery("from BoJmpcTrans bt where bt.status = '1' "+whereString+" ").setDate("toTransDate", toTransDate).setCacheable(true).list();
		}else if(jsonObj.has("toTransDate") && jsonObj.getString("toTransDate") != null && !"".equals(jsonObj.getString("toTransDate")) && 
				jsonObj.has("fromTransDate") && jsonObj.getString("fromTransDate") != null && !"".equals(jsonObj.getString("fromTransDate"))){
			fromTransDate = sdf.parse(jsonObj.getString("fromTransDate"));
			toTransDate = sdf.parse(jsonObj.getString("toTransDate"));
			return session.createQuery("from BoJmpcTrans bt where bt.status = '1' "+whereString+" ").setDate("fromTransDate", fromTransDate).setDate("toTransDate", toTransDate).setCacheable(true).list();
		}
		return session.createQuery("from BoJmpcTrans bt where bt.status = '1' "+whereString+" ").setCacheable(true).list();
	}


	@Override
	@Transactional
	public BoJmpcTrans saveBoJmpcTrans(BoJmpcTrans boJmpcTrans) throws Exception {
		logger.debug("Saving Chase Transactions");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (boJmpcTrans != null) {
				session.saveOrUpdate(boJmpcTrans);
			}
			logger.debug("Saving Chase Transactions End");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"Transactions"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return boJmpcTrans;
	}

}
