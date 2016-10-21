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

import com.bis.sourcelead.dao.RequirementSubmissionTemplateFieldsDao;
import com.bis.sourcelead.entity.CandidateCertifications;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class RequirementSubmissionTemplateFieldsDaoImpl implements RequirementSubmissionTemplateFieldsDao{
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
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementSubmissionTemplateFieldsDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmissionTemplateFields storeRequirementSubmissionTemplateFields(RequirementSubmissionTemplateFields requirementSubmissionTemplateFields) throws Exception {
		logger.debug("Adding RequirementSubmissionTemplateFields");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (requirementSubmissionTemplateFields != null) {
				session.saveOrUpdate(requirementSubmissionTemplateFields);
			}
			logger.debug("RequirementSubmissionTemplateFields Added Successfully");
		} catch (Exception exception) {
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"REQUIREMENT_SUBMISSION_TEMPLATE_FIELDS"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return requirementSubmissionTemplateFields;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmissionTemplateFields getSubmissionsTemplateFieldsByRequirementSubmissionsId(
			Long requirementSubmissionsId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmissionTemplateFields reqSubTemp where reqSubTemp.requirementSubmission.id =:requirementSubmissionsId";
		List<RequirementSubmissionTemplateFields> requirementSubmissionTemplateFieldsList =  session.createQuery(query.toString()).setLong("requirementSubmissionsId", requirementSubmissionsId)
				.setCacheable(true).list();
		
		RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = null;
		if (requirementSubmissionTemplateFieldsList != null && requirementSubmissionTemplateFieldsList.size() > 0) {
			requirementSubmissionTemplateFields = (RequirementSubmissionTemplateFields) requirementSubmissionTemplateFieldsList.iterator().next();
		}
		return requirementSubmissionTemplateFields;
	}

	
    
  }

