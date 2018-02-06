package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.InviteCompanyDao;
import com.bis.sourcelead.entity.InviteCompany;
import com.bis.sourcelead.entity.ScheduleMaintenance;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class InviteCompanyDaoImpl implements InviteCompanyDao{
	
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(InviteCompanyDaoImpl.class);

	@Override
	@Transactional
	public InviteCompany saveInviteCompany(InviteCompany inviteCompany) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (inviteCompany != null) {
				session.saveOrUpdate(inviteCompany);
			}
			logger.debug("Saving Department");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"InviteCompany"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return inviteCompany;
	}

	@Override
	@Transactional
	public void storeScheduleMaintenance(ScheduleMaintenance maintenance) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (maintenance != null) {
				session.saveOrUpdate(maintenance);
			}
			logger.debug("Saving Schedule Maintenance");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"Schedule Maintenance"));
				try {
					throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
				} catch (SourceLeadConstraintViolationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
