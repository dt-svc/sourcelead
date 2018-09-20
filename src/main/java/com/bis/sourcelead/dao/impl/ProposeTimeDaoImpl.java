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
import com.bis.sourcelead.dao.ProposeTimeDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.ProposeTime;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class ProposeTimeDaoImpl implements ProposeTimeDao{
	
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(ProposeTimeDaoImpl.class);

	@Override
	@Transactional
	public ProposeTime saveProposeTime(ProposeTime proposeTime) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (proposeTime != null) {
				session.saveOrUpdate(proposeTime);
			}
			logger.debug("Saving ProposeTime");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ProposeTime"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return proposeTime;
	}

	@Override
	@Transactional
	public ProposeTime getProposeTimeById(Long proposeTimetId) throws Exception {
		logger.debug("In ProposeTimeDaoImpl getProposeTimeById method");
		Session session = this.sessionFactory.getCurrentSession();
		return (ProposeTime) session.get(ProposeTime.class, proposeTimetId);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProposeTime> getProposeTimeByInterViewId(Long interviewId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProposeTime proposeTime  where proposeTime.interviewId.id =:interviewId and proposeTime.status = '1' "; 
		List<ProposeTime> proposeTimeList = session.createQuery(query).setLong("interviewId", interviewId).list();
		return proposeTimeList;
	}
}
