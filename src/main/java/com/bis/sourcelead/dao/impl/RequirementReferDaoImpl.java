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

import com.bis.sourcelead.dao.RequirementReferDao;
import com.bis.sourcelead.entity.RequirementRefer;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class RequirementReferDaoImpl implements RequirementReferDao {

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
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementReferDaoImpl.class);

	@Override
	@Transactional
	public RequirementRefer addRequirementRefer(RequirementRefer requirementRefer) throws Exception {
		logger.debug("Adding addRequirementRefer");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (requirementRefer != null) {
				session.saveOrUpdate(requirementRefer);
			}
			logger.debug("RequirementRefer Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"REQUIREMENT_REFER"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return requirementRefer;
	}

	@Override
	@Transactional
	public Boolean isProfessionalReferToRequirement(String email, Long requirementId, Long userId) throws Exception {
		
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from RequirementRefer refer left join fetch refer.referFromUser user left join fetch refer.requirementId requirement "
				+ "where user.id = :userId and requirement.id = :requirementId and refer.referToEmail = :email"; 
		List<RequirementRefer> requirementReferList = session.createQuery(query.toString()).setString("email", email).setLong("requirementId", requirementId).setLong("userId", userId).setCacheable(true).list();
         boolean isReferFound = false;
		if (requirementReferList != null && !requirementReferList.isEmpty()) {
			isReferFound = true;
         }
         return isReferFound;
	}

	@Override
	@Transactional
	public Boolean isReferToRequirement(String fromEmail, Long requirementId) throws Exception {
		
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from RequirementRefer refer left join fetch refer.referFromUser user left join fetch refer.requirementId requirement "
				+ "where requirement.id = :requirementId and refer.referFromEmail = :email"; 
		List<RequirementRefer> requirementReferList = session.createQuery(query.toString()).setString("email", fromEmail).setLong("requirementId", requirementId).setCacheable(true).list();
         boolean isReferFound = false;
		if (requirementReferList != null && !requirementReferList.isEmpty()) {
			isReferFound = true;
         }
         return isReferFound;
	}

	@Override
	@Transactional
	public RequirementRefer getProfessionalReferToRequirement(String email, Long requirementId, Long userId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<RequirementRefer> requirementRefer = new ArrayList<RequirementRefer>();
		logger.debug("getProfessionalReferToRequirement start");
		requirementRefer = session.createQuery("from RequirementRefer refer left join fetch refer.referFromUser user left join fetch refer.requirementId requirement "
				+ "where user.id = :userId and requirement.id = :requirementId and refer.referToEmail = :email" ).setString("email", email).setLong("requirementId", requirementId).setLong("userId", userId).setCacheable(true).list();
		logger.debug("getProfessionalReferToRequirement end");
		if (requirementRefer.size() > 0) {
			return requirementRefer.get(0);
		} else {
			return null;
		}
   	}
}
