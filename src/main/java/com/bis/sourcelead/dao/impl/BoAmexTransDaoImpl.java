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

import com.bis.sourcelead.dao.BoAmexTransDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.BoAmexTrans;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class BoAmexTransDaoImpl implements BoAmexTransDao {
	
	
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
	

	private static final Logger logger = LoggerFactory.getLogger(BoAmexTransDaoImpl.class);
	
	@Override
	@Transactional
	public BoAmexTrans getBoAmexTransById(Long Id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<BoAmexTrans> boAmexTrans = new ArrayList<BoAmexTrans>();
		boAmexTrans = session.createQuery("from BoAmexTrans where id=:id" ).setCacheable(true).setLong( "id", Id ).setCacheable(true).list();
		if (boAmexTrans.size() > 0) {
			return boAmexTrans.get(0);
		} else {
			return null;
		}
   	}

	@Override
	@Transactional
	public List<BoAmexTrans> getBoAmexTrans() throws Exception {
		
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from BoAmexTrans bt where bt.status = '1' "; 
		List<BoAmexTrans> boAmexTransList = session.createQuery(query.toString()).setCacheable(true).list();
		return boAmexTransList;
	}

	@Override
	@Transactional
	public List<BoAmexTrans> getBoAmexTransByFilters(JSONObject jsonObj) throws Exception {
		Session session =  this.sessionFactory.getCurrentSession();
		String whereString = commonUtil.getBackOfficeSummaryFilters(jsonObj);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date fromTransDate = null;
		Date toTransDate = null;
		if(jsonObj.has("fromTransDate") && jsonObj.getString("fromTransDate") != null && !"".equals(jsonObj.getString("fromTransDate")) 
				&& jsonObj.has("toTransDate") && "".equals(jsonObj.getString("toTransDate"))){
			    fromTransDate = sdf.parse(jsonObj.getString("fromTransDate"));
			return session.createQuery("from BoAmexTrans bt where bt.status = '1' "+whereString+" ").setDate("fromTransDate", fromTransDate).setCacheable(true).list();
		}else if(jsonObj.has("toTransDate") && jsonObj.getString("toTransDate") != null && !"".equals(jsonObj.getString("toTransDate")) 
					&& jsonObj.has("fromTransDate") && "".equals(jsonObj.getString("fromTransDate"))){
			toTransDate = sdf.parse(jsonObj.getString("toTransDate"));
			return session.createQuery("from BoAmexTrans bt where bt.status = '1' "+whereString+" ").setDate("toTransDate", toTransDate).setCacheable(true).list();
		}else if(jsonObj.has("toTransDate") && jsonObj.getString("toTransDate") != null && !"".equals(jsonObj.getString("toTransDate")) && 
				jsonObj.has("fromTransDate") && jsonObj.getString("fromTransDate") != null && !"".equals(jsonObj.getString("fromTransDate"))){
			fromTransDate = sdf.parse(jsonObj.getString("fromTransDate"));
			toTransDate = sdf.parse(jsonObj.getString("toTransDate"));
			return session.createQuery("from BoAmexTrans bt where bt.status = '1' "+whereString+" ").setDate("fromTransDate", fromTransDate).setDate("toTransDate", toTransDate).setCacheable(true).list();
		}
		return session.createQuery("from BoAmexTrans bt where bt.status = '1' "+whereString+" ").setCacheable(true).list();
   }
	
	@Override
	@Transactional
	public BoAmexTrans saveBoAmexTrans(BoAmexTrans boAmexTrans) throws Exception {
		logger.debug("Saving Amex Transactions");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (boAmexTrans != null) {
				session.saveOrUpdate(boAmexTrans);
			}
			logger.debug("Saving Amex Transactions");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"Transactions"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return boAmexTrans;
	}
	
}
