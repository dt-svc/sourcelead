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

import com.bis.sourcelead.dao.BoComericaTransDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.BoAmexTrans;
import com.bis.sourcelead.entity.BoComericaTrans;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class BoComericaTransDaoImpl implements BoComericaTransDao{
	
	

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
	

	private static final Logger logger = LoggerFactory.getLogger(BoComericaTransDaoImpl.class);
	
	@Override
	@Transactional
	public BoComericaTrans getBoComericaTransById(Long Id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<BoComericaTrans> boComericaTrans = new ArrayList<BoComericaTrans>();
		boComericaTrans = session.createQuery("from BoComericaTrans where id=:id" ).setLong( "id", Id ).list();
		if (boComericaTrans.size() > 0) {
			return boComericaTrans.get(0);
		} else {
			return null;
		}
   	}

	@Override
	@Transactional
	public List<BoComericaTrans> getBoComericaTrans() throws Exception {
		
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from BoComericaTrans bct where bct.status = '1' "; 
		List<BoComericaTrans> boComericaTransList = session.createQuery(query.toString()).list();
		return boComericaTransList;
	}

	@Override
	@Transactional
	public List<BoComericaTrans> getBoComericaTransByFilters(JSONObject jsonObj) throws Exception {
		Session session =  this.sessionFactory.getCurrentSession();
		String whereString = commonUtil.getBackOfficeSummaryFilters(jsonObj);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date fromTransDate = null;
		Date toTransDate = null;
		StringBuilder querySB = new StringBuilder();
		if(jsonObj.has("fromTransDate") && jsonObj.getString("fromTransDate") != null && !"".equals(jsonObj.getString("fromTransDate")) 
				&& jsonObj.has("toTransDate") && "".equals(jsonObj.getString("toTransDate"))){
			    fromTransDate = sdf.parse(jsonObj.getString("fromTransDate"));
			return session.createQuery(querySB.append("from BoComericaTrans bt where bt.status = '1' ").append(whereString).append(" ").toString()).setDate("fromTransDate", fromTransDate).list();
		}else if(jsonObj.has("toTransDate") && jsonObj.getString("toTransDate") != null && !"".equals(jsonObj.getString("toTransDate")) 
					&& jsonObj.has("fromTransDate") && "".equals(jsonObj.getString("fromTransDate"))){
			toTransDate = sdf.parse(jsonObj.getString("toTransDate"));
			return session.createQuery(querySB.append("from BoComericaTrans bt where bt.status = '1' ").append(whereString).append(" ").toString()).setDate("toTransDate", toTransDate).list();
		}else if(jsonObj.has("toTransDate") && jsonObj.getString("toTransDate") != null && !"".equals(jsonObj.getString("toTransDate")) && 
				jsonObj.has("fromTransDate") && jsonObj.getString("fromTransDate") != null && !"".equals(jsonObj.getString("fromTransDate"))){
			fromTransDate = sdf.parse(jsonObj.getString("fromTransDate"));
			toTransDate = sdf.parse(jsonObj.getString("toTransDate"));
			return session.createQuery(querySB.append("from BoComericaTrans bt where bt.status = '1' ").append(whereString).append(" ").toString()).setDate("fromTransDate", fromTransDate).setDate("toTransDate", toTransDate).list();
		}
		return session.createQuery(querySB.append("from BoComericaTrans bt where bt.status = '1' ").append(whereString).append(" ").toString()).list();
	}
	
	
	
	
	@Override
	@Transactional
	public BoComericaTrans saveBoComericaTrans(BoComericaTrans boComericaTrans) throws Exception {
		logger.debug("Saving Comerica Transactions");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (boComericaTrans != null) {
				session.saveOrUpdate(boComericaTrans);
			}
			logger.debug("Saving Amex Transactions");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"Transactions"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return boComericaTrans;
	}

}
