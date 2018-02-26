package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.MasterCandidateDao;
import com.bis.sourcelead.entity.MasterCandidate;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class MasterCandidateDaoImpl implements MasterCandidateDao{
	
	
private static final Logger logger = LoggerFactory.getLogger(MasterCandidateDaoImpl.class);
	
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
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Override
	@Transactional
	public MasterCandidate saveMasterCandidate(MasterCandidate masterCandidate) throws Exception
	{
		logger.debug("Storing Master Candidate");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (masterCandidate != null) {
				session.saveOrUpdate(masterCandidate);
			}
			logger.debug("Master Candidate Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"MasterCandidate"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return masterCandidate;
	}

	@Override
	@Transactional
	public MasterCandidate getMasterCandidateByEmailOrPhoneNumber(String email, String phoneNumber) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "";
		List masterCandidateList = null;
		
/*		if(!isNullOrEmpty(phoneNumber)){
			query = "from MasterCandidate masterCandidate  where (masterCandidate.email =:email  OR  masterCandidate.phoneNumber =:phoneNumber )";
			masterCandidateList = session.createQuery(query).setString("email", email)
																 .setString("phoneNumber", phoneNumber).list();
		}else{
			query = "from MasterCandidate masterCandidate  where (masterCandidate.email =:email)";
			masterCandidateList = session.createQuery(query).setString("email", email).list();
		}*/
		
		query = "from MasterCandidate masterCandidate  where (masterCandidate.email =:email)";
		masterCandidateList = session.createQuery(query).setString("email", email).list();
		
		MasterCandidate masterCandidate = null;
		if(masterCandidateList != null && !masterCandidateList.isEmpty())
		{
			masterCandidate = (MasterCandidate) masterCandidateList.iterator().next();
		}
		return masterCandidate;
	}
	
}
