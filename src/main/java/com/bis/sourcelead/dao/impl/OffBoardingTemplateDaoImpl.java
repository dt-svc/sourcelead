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

import com.bis.sourcelead.dao.OffBoardingTemplateDao;
import com.bis.sourcelead.entity.OffBoardingTemplate;
import com.bis.sourcelead.entity.OnBoardingTemplate;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class OffBoardingTemplateDaoImpl implements OffBoardingTemplateDao {

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

	private static final Logger logger = LoggerFactory.getLogger(OffBoardingTemplateDaoImpl.class);

	@Override
	@Transactional
	public List<OffBoardingTemplate> getOffBoardingTemplatesList(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from OffBoardingTemplate template left join fetch template.orgLocationId where template.orgLocationId.id =:orgLocationId ";
	    List<OffBoardingTemplate> offBoardingTemplateList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
        return offBoardingTemplateList;
	}

	@Override
	@Transactional
	public OffBoardingTemplate saveOffBoardingTemplate(OffBoardingTemplate offboardingTemplate) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (offboardingTemplate != null) {
				session.saveOrUpdate(offboardingTemplate);
			}
			logger.debug("offboardingTemplate Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"OFF_BOARDING_TEMPLATE"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return offboardingTemplate;
		
	}

	@Override
	@Transactional
	public OffBoardingTemplate getOffBoardingTemplateById(Long templateId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<OffBoardingTemplate> offBoardingTemplate = session.createQuery("from OffBoardingTemplate template where template.id =:templateId" ).setLong( "templateId", templateId ).list();
		if (offBoardingTemplate.size() > 0) {
			return offBoardingTemplate.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public OffBoardingTemplate getOffBoardingTemplateByTemplateNameAndOrgLocationId(String templateName,
			Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<OffBoardingTemplate> offBoardingTemplate = session.createQuery("from OffBoardingTemplate template left join fetch template.orgLocationId "
				+ " where template.templateName =:templateName and template.orgLocationId.id =:orgLocationId" ).setString("templateName", templateName).setLong( "orgLocationId", orgLocationId ).list();
		if (offBoardingTemplate.size() > 0) {
			return offBoardingTemplate.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public OffBoardingTemplate getOffBoardingTemplateByTemplateUUID(String templateUUID) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<OffBoardingTemplate> offBoardingTemplate = session.createQuery("from OffBoardingTemplate template where template.templateUUID =:templateUUID" ).setString( "templateUUID", templateUUID ).list();
		if (offBoardingTemplate.size() > 0) {
			return offBoardingTemplate.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public List<OffBoardingTemplate> getOffBoardingFinishedTemplatesList(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from OffBoardingTemplate template left join fetch template.orgLocationId where template.orgLocationId.id =:orgLocationId "
	    		+ " and template.templateStatus = 'Finished' and template.status = '1' ";
	    List<OffBoardingTemplate> offBoardingTemplateList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
        return offBoardingTemplateList;
	}
}
