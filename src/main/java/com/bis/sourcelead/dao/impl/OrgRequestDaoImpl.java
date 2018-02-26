package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.OrgRequestDao;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgRequest;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class OrgRequestDaoImpl implements OrgRequestDao{
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(OrgRequestDaoImpl.class);

	@Override
	@Transactional
	public OrgRequest saveOrgRequest(OrgRequest orgRequest) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orgRequest != null) {
				session.saveOrUpdate(orgRequest);
			}
			logger.debug("Saving OrgRequest");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"OrgRequest"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return orgRequest;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Boolean validateOrgRequestCompanyNameByName(String companyName) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
		 if(companyName != null){
				String query = "from OrgRequest orgRequeat where orgRequeat.companyName = :companyName ";
				List<OrgRequest> orgRequestList = session.createQuery(query.toString()).setString("companyName", companyName).list();
				if (orgRequestList != null && orgRequestList.size() > 0) {
					flag = true;
				}
				
		 }
		return flag;
	}
}
