package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.RequirementApproverHierarchyDao;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
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
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = { Exception.class })	
	@Override
	public List<RequirementApproverHierarchy> getRequirementApproverHierarchyList(Long requirementId,
			Long approverUserId) throws Exception {
			
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementApproverHierarchy rah where rah.requirementId.id =:requirementId and rah.approverUserId.id =:approverUserId";
		return session.createQuery(query)
				      .setLong("requirementId", requirementId)
				      .setLong("approverUserId", approverUserId)
				      
				      .list();
	
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = { Exception.class })	
	@Override
	public RequirementApproverHierarchy getRequirementApproverHierarchyByRequirementId(Long requirementId,Long approverUserId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementApproverHierarchy rah left join fetch rah.requirementId left join fetch rah.requirementId where rah.requirementId.id =:requirementId and rah.approverUserId.id =:approverUserId";
		List<RequirementApproverHierarchy> requirementApproverHierarchyList =  session.createQuery(query)
														.setLong("requirementId", requirementId)
														.setLong("approverUserId", approverUserId)
														
														.list();
		
		RequirementApproverHierarchy requirementApproverHierarchy = null;
		if(requirementApproverHierarchyList != null && !requirementApproverHierarchyList.isEmpty())
		{
			requirementApproverHierarchy = requirementApproverHierarchyList.iterator().next();
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

	
	@Transactional(rollbackFor = { Exception.class })	
	@Override
	public Integer getRequirementApproverHierarachyWithHighestLevel(Long requirementId)
			throws Exception {

		Integer maxLevel = 1;
		Session session = this.sessionFactory.getCurrentSession();
		
	    String query = "select   max(rah.level) from  RequirementApproverHierarchy rah where rah.requirementId.id =:requirementId";

		List<RequirementApproverHierarchy> results =  session.createQuery(query).setLong("requirementId", requirementId).list();

		if(results != null && !results.isEmpty()){
			  for(Object result :results){
					  maxLevel = (Integer) result;
			  }
		}
		return maxLevel;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = { Exception.class })
	@Override
	public List<RequirementApproverHierarchy> getRequirementApproverHierarchyListByRequirementId(Long requirementId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementApproverHierarchy rah left join fetch rah.approverUserId where rah.requirementId.id =:requirementId  ORDER BY rah.level ASC";
		List<RequirementApproverHierarchy> list =  session.createQuery(query)
													      .setLong("requirementId", requirementId)
													      
													      .list();
		if(list!=null && list.size()>0){
			return list;
		}else{
			return null;
		}
		
	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean deleteTheRequirementApproverByRequirementIdAndApproverId(RequirementApproverHierarchy requirementApproverHierarchy) throws Exception{
		
		Session session = this.sessionFactory.getCurrentSession();

		RequirementApproverHierarchy cat = new RequirementApproverHierarchy();
		cat.setId(requirementApproverHierarchy.getId());
		session.delete(cat); 
		 
		return true;

}

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public RequirementApproverHierarchy getRequirementApproverHierarchyByRequirementIdAndMaximumLevel(
			Long requirementId) throws Exception {




		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementApproverHierarchy rah where rah.requirementId.id =:requirementId and  rah.level = 2";

		List<RequirementApproverHierarchy> requirementApproverHierarchyList =  session.createQuery(query).setLong("requirementId", requirementId).list();
		
		RequirementApproverHierarchy requirementApproverHierarchy = null;
		if(requirementApproverHierarchyList != null && !requirementApproverHierarchyList.isEmpty())
		{
			requirementApproverHierarchy = requirementApproverHierarchyList.iterator().next();
		}
		return requirementApproverHierarchy;
		
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementApproverHierarchy> getRequirementsAsApprover(Long userId, Long orgLocationId)
			throws Exception {
		
	Session session = this.sessionFactory.getCurrentSession();
	String query = "from RequirementApproverHierarchy hierarchy left join fetch hierarchy.requirementId where hierarchy.approverUserId.id = :userId and hierarchy.requirementId.orgLocationId.id = :orgLocationId";
	return session.createQuery(query)
				  .setLong("userId", userId)
				  .setLong("orgLocationId", orgLocationId)
				  
				  .list();

}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementApproverHierarchy getApproverHierarchyByRequirementId(Long requirementId) {
	Session session = this.sessionFactory.getCurrentSession();
	String query = "from RequirementApproverHierarchy hierarchy where hierarchy.requirementId.id = :requirementId";
	List<RequirementApproverHierarchy> requirementApproverHierarchyList =  session.createQuery(query).setLong("requirementId", requirementId).list();
	RequirementApproverHierarchy requirementApproverHierarchy = null;
	if(requirementApproverHierarchyList != null && !requirementApproverHierarchyList.isEmpty())
	{
		requirementApproverHierarchy = requirementApproverHierarchyList.iterator().next();
	}
	return requirementApproverHierarchy;
	 
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementApproverHierarchy getRejectedApproverHierarchyByRequirementId(Long requirementId) {
	Session session = this.sessionFactory.getCurrentSession();
	String query = "from RequirementApproverHierarchy hierarchy where hierarchy.requirementId.id = :requirementId and hierarchy.rejectedReason <> '' ";
	List<RequirementApproverHierarchy> requirementApproverHierarchyList =  session.createQuery(query).setLong("requirementId", requirementId).list();
	RequirementApproverHierarchy requirementApproverHierarchy = null;
	if(requirementApproverHierarchyList != null && !requirementApproverHierarchyList.isEmpty())
	{
		requirementApproverHierarchy = requirementApproverHierarchyList.iterator().next();
	}
	return requirementApproverHierarchy;
	 
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementApproverHierarchy getRequirementApproverHierarchyByRequirementIdExcludingApproverHierarchyId(
			Long requirementApproverHierarchyId, Long requirementId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementApproverHierarchy hierarchy where hierarchy.id != :requirementApproverHierarchyId and  hierarchy.requirementId.id = :requirementId";
		List<RequirementApproverHierarchy> requirementApproverHierarchyList =  session.createQuery(query).setLong("requirementId", requirementId).setLong("requirementApproverHierarchyId", requirementApproverHierarchyId).list();
		RequirementApproverHierarchy requirementApproverHierarchy = null;
		if(requirementApproverHierarchyList != null && !requirementApproverHierarchyList.isEmpty())
		{
			requirementApproverHierarchy = requirementApproverHierarchyList.iterator().next();
		}
		return requirementApproverHierarchy;
		 
		}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean getisRequirementApprover(Long userId,Long requirementId) {
		// TODO Auto-generated method stub

		Session session = this.sessionFactory.getCurrentSession();
		List<RequirementApproverHierarchy> rahList = new ArrayList<>();
		rahList = session.createQuery("from RequirementApproverHierarchy rah where rah.requirementId.id = :requirementId and rah.approverUserId.id = :userId")
						   .setLong("userId", userId)
						   .setLong("requirementId", requirementId)
						   .list();
		
		Boolean flag = false;
		if (rahList.size() > 0) {
			flag = true;
		} 
		return flag;
		
	}
	
    
  }

