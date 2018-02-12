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

import com.bis.sourcelead.dao.VendorOnboardTemplateDao;
import com.bis.sourcelead.entity.OnBoardingTemplate;
import com.bis.sourcelead.entity.VendorOnboardingTemplate;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class VendorOnboardTemplateDaoImpl implements VendorOnboardTemplateDao{


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
	

	private static final Logger logger = LoggerFactory.getLogger(VendorOnboardTemplateDaoImpl.class);


	@Override
	@Transactional
	public List<VendorOnboardingTemplate> getVendorOnBoardingTemplatesList(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from VendorOnboardingTemplate template left join fetch template.orgLocationId where template.orgLocationId.id =:orgLocationId and template.status = '1' ";
	    List<VendorOnboardingTemplate> onBoardingTemplateList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
        return onBoardingTemplateList;
	}


	@Override
	@Transactional
	public VendorOnboardingTemplate getVendorOnBoardingTemplateById(Long templateId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<VendorOnboardingTemplate> onBoardingTemplate = session.createQuery("from VendorOnboardingTemplate template where template.id =:templateId" ).setLong( "templateId", templateId ).list();
		if (onBoardingTemplate.size() > 0) {
			return onBoardingTemplate.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public VendorOnboardingTemplate saveVendorOnBoardingTemplate(VendorOnboardingTemplate onboardingTemplate)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (onboardingTemplate != null) {
				session.saveOrUpdate(onboardingTemplate);
			}
			logger.debug("Vendor onboardingTemplate Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"VENDOR_ON_BOARDING_TEMPLATE"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return onboardingTemplate;
	}


	@Override
	@Transactional
	public VendorOnboardingTemplate getVendorOnBoardingTemplateByTemplateNameAndOrgLocationId(String templateName,
			Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<VendorOnboardingTemplate> onBoardingTemplate = session.createQuery("from VendorOnboardingTemplate template left join fetch template.orgLocationId "
				+ " where template.templateName =:templateName and template.orgLocationId.id =:orgLocationId" ).setString("templateName", templateName).setLong( "orgLocationId", orgLocationId ).list();
		if (onBoardingTemplate.size() > 0) {
			return onBoardingTemplate.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public VendorOnboardingTemplate getVendorOnBoardingTemplateByTemplateUUID(String templateUUID) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<VendorOnboardingTemplate> onBoardingTemplate = session.createQuery("from VendorOnboardingTemplate template where template.templateUUID =:templateUUID" ).setString( "templateUUID", templateUUID ).list();
		if (onBoardingTemplate.size() > 0) {
			return onBoardingTemplate.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public List<VendorOnboardingTemplate> getVendorOnBoardingFinishedTemplatesList(Long orgLocationId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from VendorOnboardingTemplate template left join fetch template.orgLocationId where template.orgLocationId.id =:orgLocationId and template.templateStatus = 'Finished' and template.status = '1' ";
	    List<VendorOnboardingTemplate> onBoardingTemplateList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
        return onBoardingTemplateList;
	}

}
