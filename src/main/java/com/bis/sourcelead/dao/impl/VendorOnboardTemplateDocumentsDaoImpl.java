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

import com.bis.sourcelead.dao.VendorOnboardTemplateDocumentsDao;
import com.bis.sourcelead.entity.OnBoardingTemplateDocuments;
import com.bis.sourcelead.entity.VendorOnboardingTemplateDocuments;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class VendorOnboardTemplateDocumentsDaoImpl implements VendorOnboardTemplateDocumentsDao{
	
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
	

	private static final Logger logger = LoggerFactory.getLogger(VendorOnboardTemplateDocumentsDaoImpl.class);


	@Override
	@Transactional
	public List<VendorOnboardingTemplateDocuments> getVendorOnBoardingTemplateDocumentsByTemplateId(Long templateId,
			String documentType) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from VendorOnboardingTemplateDocuments documents left join fetch documents.vendorOnboardingTemplateId where documents.vendorOnboardingTemplateId.id = :templateId "
	    		+ " and documents.documentType = :documentType and documents.status = '1' ";
	    List<VendorOnboardingTemplateDocuments> documentsList = session.createQuery(query.toString()).setLong("templateId", templateId).setString("documentType", documentType).setCacheable(true).list();
        return documentsList;
	}


	@Override
	@Transactional
	public VendorOnboardingTemplateDocuments saveVendorOnBoardingTemplateDocuments(
			VendorOnboardingTemplateDocuments vendorOnboardingTemplateDocuments) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (vendorOnboardingTemplateDocuments != null) {
				session.saveOrUpdate(vendorOnboardingTemplateDocuments);
			}
			logger.debug("vendorOnBoardingTemplateDocuments Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"VENDOR_ON_BOARDING_TEMPLATE_DOCUMENTS"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return vendorOnboardingTemplateDocuments;
	}


	@Override
	@Transactional
	public VendorOnboardingTemplateDocuments getVendorOnBoardingTemplateDocumentsBydocumentUUID(String documentUUID)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<VendorOnboardingTemplateDocuments> documents = session.createQuery("from VendorOnboardingTemplateDocuments documents where documents.documentUUID =:documentUUID" ).setString( "documentUUID", documentUUID ).setCacheable(true).list();
		if (documents.size() > 0) {
			return documents.get(0);
		} else {
			return null;
		}
	}

}
