package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.SavedJobsDao;
import com.bis.sourcelead.entity.RequirementRefer;
import com.bis.sourcelead.entity.SavedJobs;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class SavedJobsDaoImpl implements SavedJobsDao{
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(SavedJobsDaoImpl.class);
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Override
	@Transactional
	public SavedJobs addSavedJobs(SavedJobs savedJobs) throws Exception
	{
		logger.debug("Adding addSavedJobs");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (savedJobs != null) {
				session.saveOrUpdate(savedJobs);
			}
			logger.debug("SavedJobs Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"SAVED_JOBS"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return savedJobs;
	}

	@Override
	@Transactional
	public SavedJobs getSavedJobsByrequirementIdAndUserId(Long requirementId, Long userId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<SavedJobs> savedJobs = new ArrayList<SavedJobs>();
		logger.debug("getSavedJobsByrequirementIdAndUserId start");
		savedJobs = session.createQuery("from SavedJobs savedJobs left join fetch savedJobs.requirementId requirement left join fetch savedJobs.userId user "
				+ "where user.id = :userId and requirement.id = :requirementId " ).setLong("userId", userId).setLong("requirementId", requirementId).setCacheable(true).list();
		logger.debug("getSavedJobsByrequirementIdAndUserId end");
		if (savedJobs.size() > 0) {
			return savedJobs.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public List<SavedJobs> getSavedJobsListByUserId(Long userId) throws Exception
	{
		Session session =  this.sessionFactory.getCurrentSession();
		String query = " from SavedJobs savedJobs left join fetch savedJobs.requirementId left join fetch savedJobs.userId user "
				+ " left join fetch savedJobs.referId refer left join fetch refer.referFromUser where user.id = :userId ";
		List<SavedJobs> savedJobsList = session.createQuery(query.toString()).setLong("userId", userId).setCacheable(true).list();
        return savedJobsList;
	}

	@Override
	@Transactional
	public SavedJobs getSavedJobsByUUID(String savedJobUUID) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<SavedJobs> savedJobs = new ArrayList<SavedJobs>();
		logger.debug("getSavedJobsByUUID start");
		savedJobs = session.createQuery("from SavedJobs savedJobs left join fetch savedJobs.requirementId requirement left join fetch savedJobs.userId user "
				+ "where savedJobs.savedJobUUID = :savedJobUUID  " ).setString("savedJobUUID", savedJobUUID).setCacheable(true).list();
		logger.debug("getSavedJobsByUUID end");
		if (savedJobs.size() > 0) {
			return savedJobs.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public void deleteSavedJobs(SavedJobs savedJobs) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (null != savedJobs) {
				session.delete(savedJobs);
			}
			logger.debug("Deleting SavedJobs");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"SavedJobs"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
	}
	
}
