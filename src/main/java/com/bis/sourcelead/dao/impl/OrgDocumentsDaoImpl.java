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
import com.bis.sourcelead.dao.UserRoleDao;
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
	
	@Autowired
	private UserRoleDao userRoleDao;
	
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
	public List<OrgDocuments> getOrgDocumentsListByResponderLocationIdAndOrgLocationIdAndSourceType(Long responderLocationId, Long loginUserId, List<Long> managerUserIds,Long orgLocationId, String sourceType) {
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgDocuments> orgDocumentsList;
		Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(loginUserId,orgLocationId);
		if (isadmin) {
			String query = "from OrgDocuments od left join fetch od.spocUser left join fetch od.ownerUser where od.locationId =:orgLocationId and od.responderLocationId.id =:responderLocationId and od.sourceType = :sourceType and od.status = '1' ";
			orgDocumentsList = session.createQuery(query).setLong("responderLocationId", responderLocationId).setLong("orgLocationId", orgLocationId).setString("sourceType", sourceType).list();
		}else if(managerUserIds != null && !managerUserIds.isEmpty()){
			String query = "from OrgDocuments od left join fetch od.spocUser left join fetch od.ownerUser where od.locationId =:orgLocationId and od.responderLocationId.id =:responderLocationId and (od.ownerUser.id = :loginUserId OR od.ownerUser IN (:managerUserIds)) and od.sourceType = :sourceType and od.status = '1' ";
			orgDocumentsList = session.createQuery(query).setLong("responderLocationId", responderLocationId).setLong("orgLocationId", orgLocationId).setLong("loginUserId", loginUserId).setParameterList("managerUserIds", managerUserIds).setString("sourceType", sourceType).list();
		}else{
			String query = "from OrgDocuments od left join fetch od.spocUser left join fetch od.ownerUser where od.locationId =:orgLocationId and od.responderLocationId.id =:responderLocationId and od.ownerUser.id = :loginUserId and od.sourceType = :sourceType and od.status = '1' ";
			orgDocumentsList = session.createQuery(query).setLong("responderLocationId", responderLocationId).setLong("orgLocationId", orgLocationId).setLong("loginUserId", loginUserId).setString("sourceType", sourceType).list();
		}
		return orgDocumentsList;	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgDocuments getOrgDocumentsById(Long Id) throws Exception {
		logger.debug("In getOrgDocumentsById");
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from OrgDocuments od where od.id = :Id" ;
		List<OrgDocuments> orgDocumentsList = session.createQuery(query).setLong("Id", Id).list(); 
		OrgDocuments orgDocuments = null;
		if(orgDocumentsList != null && !orgDocumentsList.isEmpty()){
			orgDocuments = orgDocumentsList.iterator().next();
		}
		return orgDocuments;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgDocuments> getclientDocumentsListByResponderLocationIdAndSpocUserIdOrgLocationID(
			Long responderLocationId, Long orgLocationId, Long spocUserId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrgDocuments od left join fetch od.spocUser left join fetch od.ownerUser where od.locationId =:orgLocationId and od.responderLocationId.id =:responderLocationId and od.spocUser.id =:spocUserId and od.status = '1' ";  
		List<OrgDocuments> orgDocuments = session.createQuery(query).setLong("spocUserId", spocUserId).setLong("responderLocationId", responderLocationId).setLong("orgLocationId", orgLocationId).list();
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

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgDocuments getOrgDocumentsByIdAndSpocId(Long docId, Long userId) {
		logger.debug("In getOrgDocumentsByIdAndSpocId");
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from OrgDocuments od where od.id = :Id and od.spocUser.id =:userId" ;
		List<OrgDocuments> orgDocumentsList = session.createQuery(query).setLong("Id", docId).setLong("userId", userId).list(); 
		OrgDocuments orgDocuments = null;
		if(orgDocumentsList != null && !orgDocumentsList.isEmpty()){
			orgDocuments = orgDocumentsList.iterator().next();
		}
		return orgDocuments;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getDocumentOwnerCountByUserIdAndOrgLocationIdAndSourceType(Long userId, Long orgLocationId,
			String sourceType) throws Exception {
		
		logger.debug("getDocumentOwnerCountByUserIdAndOrgLocationIdAndSourceType start");
		Session session = this.sessionFactory.getCurrentSession();
			String query = "select count(orgDocuments.id) from OrgDocuments orgDocuments "
					+ " where orgDocuments.ownerUser.id = :userId and orgDocuments.locationId.id = :orgLocationId "
					+ " and orgDocuments.sourceType = :sourceType and orgDocuments.status = '1' ";
			
			List<Long> projectsCount = session.createQuery(query.toString()).setLong("userId", userId).setLong("orgLocationId", orgLocationId).setString("sourceType", sourceType).list();
			Integer count = 0;
			if(projectsCount != null && !projectsCount.isEmpty()){
					count = projectsCount.get(0).intValue();
			}
			return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgDocuments> getOrgDocumentsListByUserIdAndOrgLocationIdAndSourceType(Long userId, Long orgLocationId,
			String sourceType) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrgDocuments orgDocuments "
					+ " where orgDocuments.ownerUser.id = :userId and orgDocuments.locationId.id = :orgLocationId "
					+ " and orgDocuments.sourceType = :sourceType and orgDocuments.status = '1' ";  
		List<OrgDocuments> orgDocuments = session.createQuery(query).setLong("userId", userId).setLong("orgLocationId", orgLocationId).setString("sourceType", sourceType).list();
		return orgDocuments;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgDocuments getOrgDocumentsByDocIdAndLocationId(Long Id, Long locationId) throws Exception
	{
		logger.debug("In getOrgDocumentsById");
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from OrgDocuments od where od.id = :Id and od.locationId= :locationId" ;
		List<OrgDocuments> orgDocumentsList = session.createQuery(query).setLong("Id", Id).setLong("locationId", locationId).list(); 
		OrgDocuments orgDocuments = null;
		if(orgDocumentsList != null && !orgDocumentsList.isEmpty()){
			orgDocuments = orgDocumentsList.iterator().next();
		}
		return orgDocuments;
	}

	

}
