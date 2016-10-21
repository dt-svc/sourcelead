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

import com.bis.sourcelead.dao.SubmissionTemplateDao;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class SubmissionTemplateDaoImpl implements SubmissionTemplateDao{
	
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
	

	private static final Logger logger = LoggerFactory.getLogger(SubmissionTemplateDaoImpl.class);


	@Override
	@Transactional
	public SubmissionTemplate addSubmissionTemplate(SubmissionTemplate submissionTemplate) throws Exception {
		logger.debug("Adding SubmissionTemplate");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (submissionTemplate != null) {
				session.saveOrUpdate(submissionTemplate);
			}
			logger.debug("SubmissionTemplate Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"SUBMISSION_TEMPLATE"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return submissionTemplate;
	}


	@Override
	@Transactional
	public List<SubmissionTemplate> getSubmissionTemplateByClientLocationIdAndOrgLocationId(Long clientLocationId, Long orgLocationId)
			throws Exception {
		logger.debug("getSubmissionTemplateByClientLocationIdAndOrgLocationId start");
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from SubmissionTemplate st where st.clientLocationId.id = :clientLocationId and st.orgLocationId.id = :orgLocationId";
	    List<SubmissionTemplate> submissionTemplateList = session.createQuery(query).setLong( "clientLocationId", clientLocationId ).setLong( "orgLocationId", orgLocationId ).setCacheable(true).list();
        return submissionTemplateList;
        
	}


	@Override
	@Transactional
	public SubmissionTemplate getSubmissionTemplateById(Long Id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<SubmissionTemplate> submissionTemplate = new ArrayList<SubmissionTemplate>();
		logger.debug("getSubmissionTemplateById start");
		submissionTemplate = session.createQuery("from SubmissionTemplate where id=:id" ).setCacheable(true).setLong( "id", Id ).setCacheable(true).list();
		logger.debug("getSubmissionTemplateById end");
		if (submissionTemplate.size() > 0) {
			return submissionTemplate.get(0);
		} else {
			return null;
		}
   	}


	@Override
	@Transactional
	public SubmissionTemplate getDefaultSubmissionTemplateByClientLocationIdAndOrgLocationId(Long clientLocationId,
			Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<SubmissionTemplate> submissionTemplate = new ArrayList<SubmissionTemplate>();
		logger.debug("getDefaultSubmissionTemplateByClientLocationIdAndOrgLocationId start");
		submissionTemplate = session.createQuery("from SubmissionTemplate st where st.clientLocationId.id = :clientLocationId and st.orgLocationId.id = :orgLocationId and st.isDefault = 'Y' " ).setLong( "clientLocationId", clientLocationId)
				.setLong( "orgLocationId", orgLocationId ).setCacheable(true).list();
		logger.debug("getDefaultSubmissionTemplateByClientLocationIdAndOrgLocationId end");
		if (submissionTemplate.size() > 0) {
			return submissionTemplate.get(0);
		} else {
			return null;
		}
   	}


	@Override
	@Transactional
	public SubmissionTemplate getSubmissionTemplateByTemplateName(Long clientLocationId, Long orgLocationId,
			String templateName) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<SubmissionTemplate> submissionTemplate = new ArrayList<SubmissionTemplate>();
		logger.debug("getSubmissionTemplateByTemplateName start");
		submissionTemplate = session.createQuery("from SubmissionTemplate st where st.clientLocationId.id = :clientLocationId and st.orgLocationId.id = :orgLocationId and st.templateName = :templateName " ).setLong( "clientLocationId", clientLocationId)
				.setLong( "orgLocationId", orgLocationId ).setString("templateName", templateName).setCacheable(true).list();
		logger.debug("getSubmissionTemplateByTemplateName end");
		if (submissionTemplate.size() > 0) {
			return submissionTemplate.get(0);
		} else {
			return null;
		}
   	}


	@Override
	@Transactional
	public SubmissionTemplate getSubmissionTemplateByLabelName(Long templateId,String labelName) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<SubmissionTemplate> submissionTemplate = new ArrayList<SubmissionTemplate>();
		logger.debug("getSubmissionTemplateByLabelName start");
		submissionTemplate = session.createQuery("from SubmissionTemplate st where st.id = :templateId and st.jsonString like '%"+labelName+"%' " ).setLong( "templateId", templateId ).setCacheable(true).list();
		logger.debug("getSubmissionTemplateByLabelName end");
		if (submissionTemplate.size() > 0) {
			return submissionTemplate.get(0);
		} else {
			return null;
		}
   	}


	@Override
	@Transactional
	public List<SubmissionTemplate> getOrgSubmissionTemplatesByOrgLocationId(Long orgLocationId) throws Exception {
		logger.debug("getOrgSubmissionTemplatesByOrgLocationId start");
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from SubmissionTemplate st where st.orgLocationId.id = :orgLocationId and (st.clientLocationId IS NULL or st.clientLocationId = '') and st.status = '1' ";
	    List<SubmissionTemplate> submissionTemplateList = session.createQuery(query).setLong( "orgLocationId", orgLocationId ).setCacheable(true).list();
        return submissionTemplateList;
        
	}


	@Override
	@Transactional
	public SubmissionTemplate getSubmissionTemplateByTemplateNameByOrg(Long orgLocationId, String templateName)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<SubmissionTemplate> submissionTemplate = new ArrayList<SubmissionTemplate>();
		logger.debug("getSubmissionTemplateByTemplateNameByOrg start");
		submissionTemplate = session.createQuery("from SubmissionTemplate st where st.orgLocationId.id = :orgLocationId and st.templateName = :templateName " )
				.setLong( "orgLocationId", orgLocationId ).setString("templateName", templateName).setCacheable(true).list();
		logger.debug("getSubmissionTemplateByTemplateName end");
		if (submissionTemplate.size() > 0) {
			return submissionTemplate.get(0);
		} else {
			return null;
		}
   	}

}
