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

import com.bis.sourcelead.dao.OnBoardingTemplateDocumentsDao;
import com.bis.sourcelead.entity.OnBoardingTemplateDocuments;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class OnBoardingTemplateDocumentsDaoImpl implements OnBoardingTemplateDocumentsDao {

	
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
	

	private static final Logger logger = LoggerFactory.getLogger(OnBoardingTemplateDocumentsDaoImpl.class);
	
	@Override
	@Transactional
	public OnBoardingTemplateDocuments saveOnBoardingTemplateDocuments(
			OnBoardingTemplateDocuments onBoardingTemplateDocuments) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (onBoardingTemplateDocuments != null) {
				session.saveOrUpdate(onBoardingTemplateDocuments);
			}
			logger.debug("onBoardingTemplateDocuments Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ON_BOARDING_TEMPLATE_DOCUMENTS"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return onBoardingTemplateDocuments;
	}

	@Override
	@Transactional
	public List<OnBoardingTemplateDocuments> getOnBoardingTemplateDocumentsByTemplateId(Long templateId,String documentType)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from OnBoardingTemplateDocuments documents left join fetch documents.onboardingTemplateId where documents.onboardingTemplateId.id = :templateId "
	    		+ " and documents.documentType = :documentType and documents.status = '1' ";
	    List<OnBoardingTemplateDocuments> documentsList = session.createQuery(query.toString()).setLong("templateId", templateId).setString("documentType", documentType).list();
        return documentsList;
	}

	@Override
	@Transactional
	public OnBoardingTemplateDocuments getOnBoardingTemplateDocumentsBydocumentUUID(String documentUUID)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<OnBoardingTemplateDocuments> documents = session.createQuery("from OnBoardingTemplateDocuments documents where documents.documentUUID =:documentUUID" ).setString( "documentUUID", documentUUID ).list();
		if (documents.size() > 0) {
			return documents.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public List<OnBoardingTemplateDocuments> getCandidateOnBoardingTemplateDocumentsByTemplateId(List<Long> templateIds,
			String documentType) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from OnBoardingTemplateDocuments documents left join fetch documents.onboardingTemplateId where documents.onboardingTemplateId.id IN (:templateIds) "
	    		+ " and documents.documentType = :documentType and documents.status = '1' ";
	    List<OnBoardingTemplateDocuments> documentsList = session.createQuery(query.toString()).setParameterList("templateIds", templateIds).setString("documentType", documentType).list();
        return documentsList;
	}

	@Override
	@Transactional
	public OnBoardingTemplateDocuments getOnBoardingTemplateDocumentsById(Long Id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<OnBoardingTemplateDocuments> documents = session.createQuery("from OnBoardingTemplateDocuments documents where documents.id =:id" ).setLong( "id", Id ).list();
		if (documents.size() > 0) {
			return documents.get(0);
		} else {
			return null;
		}
	
	}

	@Override
	@Transactional
	public Integer getCandidateOnboardingDocumentsByCandidateOnboardingId(Long candidateOnBoardingId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query ="select count(*) from OnBoardingTemplateDocuments documents where documents.candidateOnBoardingId.id =:candidateOnBoardingId ";
		List<Long> onboardCount = session.createQuery(query.toString()).setLong("candidateOnBoardingId", candidateOnBoardingId).list();
			Integer count = 0;
			if(onboardCount != null && !onboardCount.isEmpty()){
					count = onboardCount.get(0).intValue();
			}
			return count;
	}

	@Override
	@Transactional
	public List<OnBoardingTemplateDocuments> getOnBoardingTemplateDocumentsByCandidateOnBoardingId(
			Long candidateOnBoardingId, String documentType) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from OnBoardingTemplateDocuments documents left join fetch documents.candidateOnBoardingId where documents.candidateOnBoardingId.id = :candidateOnBoardingId "
	    		+ " and documents.documentType = :documentType and documents.status = '1' ";
	    List<OnBoardingTemplateDocuments> documentsList = session.createQuery(query.toString()).setLong("candidateOnBoardingId", candidateOnBoardingId).setString("documentType", documentType).list();
        return documentsList;
	}

	@Override
	@Transactional
	public Integer getCandidateOnboardingDocumentsCountByCandidateOnboardingIdExpectSupportDocs(
			Long candidateOnBoardingId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query ="select count(*) from OnBoardingTemplateDocuments documents where documents.candidateOnBoardingId.id =:candidateOnBoardingId and "
				+ " documents.documentType != 'Supported' ";
		List<Long> onboardCount = session.createQuery(query.toString()).setLong("candidateOnBoardingId", candidateOnBoardingId).list();
			Integer count = 0;
			if(onboardCount != null && !onboardCount.isEmpty()){
					count = onboardCount.get(0).intValue();
			}
			return count;
	}

	@Override
	@Transactional
	public Integer getCandidateAcceptedOnboardingDocumentsCountByCandidateOnboardingId(Long candidateOnBoardingId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query ="select count(*) from OnBoardingTemplateDocuments documents where documents.candidateOnBoardingId.id =:candidateOnBoardingId and "
				+ " documents.documentStatus = 'ACCEPTED' ";
		List<Long> onboardCount = session.createQuery(query.toString()).setLong("candidateOnBoardingId", candidateOnBoardingId).list();
			Integer count = 0;
			if(onboardCount != null && !onboardCount.isEmpty()){
					count = onboardCount.get(0).intValue();
			}
			return count;
	}

	@Override
	@Transactional
	public List<Long> getOnBoardingTemplateDocumentIdsByCandidateOnBoardingId(Long candidateOnBoardingId,
			String documentType) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "select documents.id from OnBoardingTemplateDocuments documents  where documents.candidateOnBoardingId.id = :candidateOnBoardingId "
	    		+ " and documents.documentType = :documentType and documents.status = '1' ";
	    List<Long> onboardCount  = session.createQuery(query.toString()).setLong("candidateOnBoardingId", candidateOnBoardingId).setString("documentType", documentType).list();
        return onboardCount;
	}

}
