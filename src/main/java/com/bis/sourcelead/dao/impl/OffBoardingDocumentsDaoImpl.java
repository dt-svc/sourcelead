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

import com.bis.sourcelead.dao.OffBoardingDocumentsDao;
import com.bis.sourcelead.entity.OffBoardingDocuments;
import com.bis.sourcelead.entity.OnBoardingTemplateDocuments;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class OffBoardingDocumentsDaoImpl implements OffBoardingDocumentsDao {
	
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
	

	private static final Logger logger = LoggerFactory.getLogger(OffBoardingDocumentsDaoImpl.class);


	@Override
	@Transactional
	public OffBoardingDocuments saveOffBoardingDocuments(OffBoardingDocuments offBoardingDocuments)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (offBoardingDocuments != null) {
				session.saveOrUpdate(offBoardingDocuments);
			}
			logger.debug("offBoardingDocuments Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"OFF_BOARDING_DOCUMENTS"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return offBoardingDocuments;
	}


	@Override
	@Transactional
	public List<OffBoardingDocuments> getOffBoardingDocumentsByTemplateId(Long templateId, String documentType)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from OffBoardingDocuments documents left join fetch documents.offboardingTemplateId where documents.offboardingTemplateId.id = :templateId "
	    		+ " and documents.documentType = :documentType and documents.status = '1' ";
	    List<OffBoardingDocuments> documentsList = session.createQuery(query.toString()).setLong("templateId", templateId).setString("documentType", documentType).list();
        return documentsList;
	}


	@Override
	@Transactional
	public OffBoardingDocuments getOffBoardingDocumentsBydocumentUUID(String documentUUID) throws Exception {
	
		Session session = this.sessionFactory.getCurrentSession();
		List<OffBoardingDocuments> documents = session.createQuery("from OffBoardingDocuments documents where documents.documentUUID =:documentUUID" ).setString( "documentUUID", documentUUID ).list();
		if (documents.size() > 0) {
			return documents.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public Integer getOffboardingDocumentsByOffboardingId(Long offBoardingId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query ="select count(*) from OffBoardingDocuments documents where documents.candidateOffBoardingId.id =:offBoardingId ";
		List<Long> offboardCount = session.createQuery(query.toString()).setLong("offBoardingId", offBoardingId).list();
			Integer count = 0;
			if(offboardCount != null && !offboardCount.isEmpty()){
					count = offboardCount.get(0).intValue();
			}
			return count;
	}


	@Override
	@Transactional
	public List<OffBoardingDocuments> getOffBoardingDocumentsByCandidateOffBoardingId(Long candidateOffBoardingId,
			String documentType) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from OffBoardingDocuments documents left join fetch documents.candidateOffBoardingId where documents.candidateOffBoardingId.id = :candidateOffBoardingId "
	    		+ " and documents.documentType = :documentType and documents.status = '1' ";
	    List<OffBoardingDocuments> documentsList = session.createQuery(query.toString()).setLong("candidateOffBoardingId", candidateOffBoardingId).setString("documentType", documentType).list();
        return documentsList;
		
	}


	@Override
	@Transactional
	public List<OffBoardingDocuments> getOffBoardingDocumentsByTemplateIdsList(List<Long> templateIds,
			String documentType) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from OffBoardingDocuments documents left join fetch documents.offboardingTemplateId where documents.offboardingTemplateId.id IN (:templateIds) "
	    		+ " and documents.documentType = :documentType and documents.status = '1' ";
	    List<OffBoardingDocuments> documentsList = session.createQuery(query.toString()).setParameterList("templateIds", templateIds).setString("documentType", documentType).list();
        return documentsList;
	}


	@Override
	@Transactional
	public OffBoardingDocuments getOffBoardingDocumentsById(Long id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<OffBoardingDocuments> documents = session.createQuery("from OffBoardingDocuments documents where documents.id = :id" ).setLong( "id", id ).list();
		if (documents.size() > 0) {
			return documents.get(0);
		} else {
			return null;
		}
	}

}
