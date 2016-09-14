package com.bis.sourcelead.dao.impl;


import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.OrgDocumentsDao;
import com.bis.sourcelead.entity.OrgDocuments;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.rest.CandidateResumeRestController;


/**
 *  to handle the documents of the client and vendors
 * @author Prasanna Vadla
 *
 */
@Repository
public class OrgDocumentsDaoImpl implements OrgDocumentsDao{
	

	private static final Logger logger = LoggerFactory.getLogger(CandidateResumeRestController.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${SourceLead.106}")
	private String duplicateValue;
	
	/**
	 *  to save the documents of the client and vendors
	 * @author Prasanna Vadla
	 * @throws SourceLeadConstraintViolationException 
	 *
	 */

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void saveDocuments(OrgDocuments orgDocuments) throws SourceLeadConstraintViolationException {
		logger.debug("Storing orgDocuments");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orgDocuments != null) {
				session.saveOrUpdate(orgDocuments);
			}
			logger.debug("orgDocuments Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"orgDocuments"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgDocuments> getClientDocumentsListByResponderLocationIdAndOrgLocationID(Long responderLocationId, Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrgDocuments od left join fetch od.spocUser left join fetch od.ownerUser where od.locationId =:orgLocationId and od.responderLocationId.id =:responderLocationId and od.status = 'ACTIVE' ";  
		List<OrgDocuments> orgDocuments = session.createQuery(query).setLong("responderLocationId", responderLocationId).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		return orgDocuments;	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgDocuments getOrgDocumentsById(Long Id) throws Exception {
		logger.debug("In getOrgDocumentsById");
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from OrgDocuments od where od.id = :Id" ;
		List<OrgDocuments> orgDocumentsList = session.createQuery(query).setLong("Id", Id).setCacheable(true).list(); 
		OrgDocuments orgDocuments = null;
		if(orgDocumentsList != null && !orgDocumentsList.isEmpty()){
			orgDocuments = orgDocumentsList.iterator().next();
		}
		return orgDocuments;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgDocuments updateDocumnet(OrgDocuments orgDocuments) throws Exception {
		logger.debug("In Terminate OrgDocument Update method");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			if(orgDocuments != null){
				session.saveOrUpdate(orgDocuments);
				logger.info("Record updated successfully..!");
			}
		}catch(ConstraintViolationException exception)
		{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ORG_DOCUMENTS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return orgDocuments;
	}


}
