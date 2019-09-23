package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.EmailReminderDao;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.EmailReminder;

@Repository
public class EmailReminderDaoImpl implements EmailReminderDao {
	
	@Autowired
	SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(EmailReminderDaoImpl.class);

	@Override
	@Transactional
	public EmailReminder saveEmailReminderDetails(EmailReminder emailReminder) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (emailReminder != null) {
				session.saveOrUpdate(emailReminder);
			}
		} catch (Exception exception) { 
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return emailReminder;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmailReminder> getEmailReminderList() {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from EmailReminder e  where e.status = '1' "; 
		List<EmailReminder> emailReminderList = session.createQuery(query).list();
		return emailReminderList;
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmailReminder> getEmailReminderList(String scenarioName) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from EmailReminder e left join fetch e.orgLocationsId where e.scenarionName =:scenarioName and e.status = '1' "; 
		List<EmailReminder> emailReminderList = session.createQuery(query).setString("scenarioName", scenarioName).list();
		return emailReminderList;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmailReminder> getEmailReminderListByTabsIdAndOrgLocationId(Long tabsId, Long orgLocationsId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from EmailReminder e  where e.tabsId.id =:tabsId and e.orgLocationsId.id =:orgLocationsId and e.status = '1' "; 
		List<EmailReminder> emailReminderList = session.createQuery(query).setLong("tabsId", tabsId).setLong("orgLocationsId", orgLocationsId).list();
		return emailReminderList;
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public EmailReminder getEmailReminderById(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from EmailReminder e  where e.id =:id and e.status = '1' "; 
		List<EmailReminder> emailReminderList = session.createQuery(query).setLong("id", id).list();
		EmailReminder emailReminder = null;
		if(emailReminderList != null && !emailReminderList.isEmpty())
		{
			emailReminder = (EmailReminder) emailReminderList.iterator().next();
		}
		return emailReminder;
	}
}
