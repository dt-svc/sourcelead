package com.bis.sourcelead.dao.impl;

import java.text.SimpleDateFormat;
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
import com.bis.sourcelead.entity.BoComericaTrans;
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
	public List<BoComericaTrans> getBoComericaTrans() throws Exception {
		
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from BoComericaTrans bct where bct.status = '1' "; 
		List<BoComericaTrans> boComericaTransList = session.createQuery(query.toString()).setCacheable(true).list();
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
		if(jsonObj.has("fromTransDate") && jsonObj.getString("fromTransDate") != null && !"".equals(jsonObj.getString("fromTransDate")) 
				&& jsonObj.has("toTransDate") && "".equals(jsonObj.getString("toTransDate"))){
			    fromTransDate = sdf.parse(jsonObj.getString("fromTransDate"));
			return session.createQuery("from BoComericaTrans bt where bt.status = '1' "+whereString+" ").setDate("fromTransDate", fromTransDate).setCacheable(true).list();
		}else if(jsonObj.has("toTransDate") && jsonObj.getString("toTransDate") != null && !"".equals(jsonObj.getString("toTransDate")) 
					&& jsonObj.has("fromTransDate") && "".equals(jsonObj.getString("fromTransDate"))){
			toTransDate = sdf.parse(jsonObj.getString("toTransDate"));
			return session.createQuery("from BoComericaTrans bt where bt.status = '1' "+whereString+" ").setDate("toTransDate", toTransDate).setCacheable(true).list();
		}else if(jsonObj.has("toTransDate") && jsonObj.getString("toTransDate") != null && !"".equals(jsonObj.getString("toTransDate")) && 
				jsonObj.has("fromTransDate") && jsonObj.getString("fromTransDate") != null && !"".equals(jsonObj.getString("fromTransDate"))){
			fromTransDate = sdf.parse(jsonObj.getString("fromTransDate"));
			toTransDate = sdf.parse(jsonObj.getString("toTransDate"));
			return session.createQuery("from BoComericaTrans bt where bt.status = '1' "+whereString+" ").setDate("fromTransDate", fromTransDate).setDate("toTransDate", toTransDate).setCacheable(true).list();
		}
		return session.createQuery("from BoComericaTrans bt where bt.status = '1' "+whereString+" ").setCacheable(true).list();
	}

}
