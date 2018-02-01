package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CandidateHistoryDao;
import com.bis.sourcelead.entity.CandidateCertificationsHistory;
import com.bis.sourcelead.entity.CandidateDocumentsHistory;
import com.bis.sourcelead.entity.CandidateEducationHistory;
import com.bis.sourcelead.entity.CandidateEmployersHistory;
import com.bis.sourcelead.entity.CandidateHistory;
import com.bis.sourcelead.entity.CandidateReferencesHistory;
import com.bis.sourcelead.entity.CandidateResumesHistory;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

/**
 * 
 * @author Venkat Kesav
 * 
 *
 */
@Repository
public class CandidateHistoryDaoImpl implements CandidateHistoryDao {
	private static final Logger logger = LoggerFactory.getLogger(CandidateHistoryDaoImpl.class);
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
	
	protected boolean isNullOrEmpty(String str)
	{
		return (str == null || "".equals(str.trim()));
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateHistory storeCandidateHistory(CandidateHistory candidateHistory) throws Exception
	{
		logger.debug("Storing candidateHistory");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateHistory != null) {
				session.saveOrUpdate(candidateHistory);
			}
			logger.debug("candidateHistory Stored Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "candidateHistory"));
			throw new SourceLeadConstraintViolationException(message.toString()
					+ exception.getCause().toString());
		}
		return candidateHistory;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateEducationHistory storeCandidateEducationHistory(CandidateEducationHistory candidateEducationHistory) throws Exception
	{
		logger.debug("Storing candidateEducationHistory");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateEducationHistory != null) {
				session.saveOrUpdate(candidateEducationHistory);
			}
			logger.debug("candidateEducationHistory Stored Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "candidateEducationHistory"));
			throw new SourceLeadConstraintViolationException(message.toString()
					+ exception.getCause().toString());
		}
		return candidateEducationHistory;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateEducationHistory> storeCandidateEducationHistoryList(Set<CandidateEducationHistory> candidateEducationHistoryList) throws Exception
	{
		logger.debug("Storing candidateEducationHistoryList");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateEducationHistoryList != null) {
				for(CandidateEducationHistory candidateEducationHistory : candidateEducationHistoryList){
					session.saveOrUpdate(candidateEducationHistory);
				}
			}
			logger.debug("candidateEducationHistoryList Stored Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "candidateEducationHistoryList"));
			throw new SourceLeadConstraintViolationException(message.toString()
					+ exception.getCause().toString());
		}
		return null;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateEmployersHistory storeCandidateEmployersHistory(CandidateEmployersHistory candidateEmployersHistory) throws Exception
	{
		logger.debug("Storing candidateEmployersHistory");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateEmployersHistory != null) {
				session.saveOrUpdate(candidateEmployersHistory);
			}
			logger.debug("candidateEmployersHistory Stored Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "candidateEmployersHistory"));
			throw new SourceLeadConstraintViolationException(message.toString()
					+ exception.getCause().toString());
		}
		return candidateEmployersHistory;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateEmployersHistory> storeCandidateEmployersHistoryList(Set<CandidateEmployersHistory> candidateEmployersHistoryList) throws Exception
	{
		logger.debug("Storing candidateEmployersHistoryList");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateEmployersHistoryList != null) {
				for(CandidateEmployersHistory candidateEmployersHistory : candidateEmployersHistoryList){
					session.saveOrUpdate(candidateEmployersHistory);
				}
			}
			logger.debug("candidateEmployersHistoryList Stored Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "candidateEmployersHistoryList"));
			throw new SourceLeadConstraintViolationException(message.toString()
					+ exception.getCause().toString());
		}
		return null;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateResumesHistory storeCandidateResumesHistory(CandidateResumesHistory candidateResumesHistory) throws Exception
	{
		logger.debug("Storing candidateResumesHistory");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateResumesHistory != null) {
				session.saveOrUpdate(candidateResumesHistory);
			}
			logger.debug("candidateResumesHistory Stored Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "candidateResumesHistory"));
			throw new SourceLeadConstraintViolationException(message.toString()
					+ exception.getCause().toString());
		}
		return candidateResumesHistory;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateResumesHistory> storeCandidateResumesHistoryList(Set<CandidateResumesHistory> candidateResumesHistoryList) throws Exception
	{
		logger.debug("Storing candidateResumesHistoryList");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateResumesHistoryList != null) {
				for(CandidateResumesHistory candidateResumesHistory : candidateResumesHistoryList){
					session.saveOrUpdate(candidateResumesHistory);
				}
			}
			logger.debug("candidateResumesHistoryList Stored Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "candidateResumesHistoryList"));
			throw new SourceLeadConstraintViolationException(message.toString()
					+ exception.getCause().toString());
		}
		return null;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateHistory> getCandidateHistoryListByCandidateId(Long candidateId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateHistory> candidateHistoryList = new ArrayList<CandidateHistory>();
		logger.debug("findByCandidateEmail start");
		try {
			candidateHistoryList = session.createQuery("from CandidateHistory ch where ch.candidateId.id=:candidateId ORDER BY ch.id DESC").setLong("candidateId", candidateId).list();
			logger.debug("findByCandidateEmail end");
			if (candidateHistoryList.size() > 0) {
				return candidateHistoryList;
			}
			else {
				return null;
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return candidateHistoryList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateCertificationsHistory storeCandidateCertificationsHistory(CandidateCertificationsHistory candidateCertificationsHistory) throws Exception
	{
		logger.debug("Storing candidateCertificationsHistory");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateCertificationsHistory != null) {
				session.saveOrUpdate(candidateCertificationsHistory);
			}
			logger.debug("candidateCertificationsHistory Stored Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "candidateCertificationsHistory"));
			throw new SourceLeadConstraintViolationException(message.toString()
					+ exception.getCause().toString());
		}
		return candidateCertificationsHistory;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateDocumentsHistory storeCandidateDocumentsHistory(CandidateDocumentsHistory candidateDocumentsHistory) throws Exception
	{
		logger.debug("Storing candidateDocumentsHistory");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateDocumentsHistory != null) {
				session.saveOrUpdate(candidateDocumentsHistory);
			}
			logger.debug("candidateDocumentsHistory Stored Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "candidateDocumentsHistory"));
			throw new SourceLeadConstraintViolationException(message.toString()
					+ exception.getCause().toString());
		}
		return candidateDocumentsHistory;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateReferencesHistory storeCandidateReferencesHistory(CandidateReferencesHistory candidateReferencesHistory) throws Exception
	{
		logger.debug("Storing candidateReferencesHistory");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateReferencesHistory != null) {
				session.saveOrUpdate(candidateReferencesHistory);
			}
			logger.debug("candidateReferencesHistory Stored Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "candidateReferencesHistory"));
			throw new SourceLeadConstraintViolationException(message.toString()
					+ exception.getCause().toString());
		}
		return candidateReferencesHistory;
	}
}
