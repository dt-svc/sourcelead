package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.RequirementApproverHierarchyDao;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class RequirementApproverHierarchyDaoImpl implements RequirementApproverHierarchyDao{
	
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
	
	@Transactional(rollbackFor = { Exception.class })	
	@Override
	public List<RequirementApproverHierarchy> getRequirementApproverHierarchyList(Long requirementId,
			Long approverUserId) throws Exception {
			
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementApproverHierarchy rah where rah.requirementId.id =:requirementId and rah.approverUserId.id =:approverUserId";
		return session.createQuery(query).setLong("requirementId", requirementId).setLong("approverUserId", approverUserId).list();
	
	}
	
	@Transactional(rollbackFor = { Exception.class })	
	@Override
	public RequirementApproverHierarchy getRequirementApproverHierarchyByRequirementId(Long requirementId,Long approverUserId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementApproverHierarchy rah where rah.requirementId.id =:requirementId and rah.approverUserId.id =:approverUserId";
		List requirementApproverHierarchyList =  session.createQuery(query).setLong("requirementId", requirementId).setLong("approverUserId", approverUserId).list();
		
		RequirementApproverHierarchy requirementApproverHierarchy = null;
		if(requirementApproverHierarchyList != null && !requirementApproverHierarchyList.isEmpty())
		{
			requirementApproverHierarchy = (RequirementApproverHierarchy) requirementApproverHierarchyList.iterator().next();
		}
		return requirementApproverHierarchy;
		
	}
	
	@Transactional(rollbackFor = { Exception.class })	
	@Override
	public RequirementApproverHierarchy storeRequirementApproverHierarchy(
			RequirementApproverHierarchy requirementApproverHierarchy) throws Exception {
	
			Session session = this.sessionFactory.getCurrentSession();
			try {
				if (requirementApproverHierarchy != null) {
					session.saveOrUpdate(requirementApproverHierarchy);
				}
			} catch (Exception exception) {
				    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"Requirement"));
					throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
			}
			return requirementApproverHierarchy;
	}
	
    
  }

