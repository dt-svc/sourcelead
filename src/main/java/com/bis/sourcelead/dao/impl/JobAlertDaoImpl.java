package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.Arrays;
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

import com.bis.sourcelead.dao.AssetDao;
import com.bis.sourcelead.dao.DepartmentDao;
import com.bis.sourcelead.dao.JobAlertDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.JobAlert;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class JobAlertDaoImpl implements JobAlertDao{
	
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(JobAlertDaoImpl.class);

	@Override
	@Transactional
	public JobAlert saveJobAlert(JobAlert jobalert) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (jobalert != null) {
				session.saveOrUpdate(jobalert);
			}
			logger.debug("Saving JobAlert");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"JobAlert"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return jobalert;
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })		
	public JobAlert getJobAlertById(Long jobalertId) throws Exception {
		logger.debug("In JobAlertDaoImpl getJobAlertById method");
		Session session = this.sessionFactory.getCurrentSession();
		return (JobAlert) session.get(JobAlert.class, jobalertId);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<JobAlert> getJobAlertByuserId(Long userId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from JobAlert jobAlert  where jobAlert.userId.id =:userId and jobAlert.status ='1' "; 
		List<JobAlert> jobAlertList = session.createQuery(query).setLong("userId", userId).setCacheable(true).list();
		return jobAlertList;
	}
}
