package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.RequirementAssignToDao;
import com.bis.sourcelead.entity.RequirementAssignTo;

@Repository
public class RequirementAssignToDaoImpl implements RequirementAssignToDao{
	
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
	public List<RequirementAssignTo> getRequirementAssignToListByRequirementId(Long requirementId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementAssignTo rat   left join fetch rat.teamId  tm  left join fetch tm.ownerUserId    left join fetch rat.assignUserId   where  rat.requirementId.id =:requirementId";
		return session.createQuery(query).setLong("requirementId", requirementId).setCacheable(true).list();
		
		
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public RequirementAssignTo getRequirementAssignToByRequirementIdandAssignToId(Long requirementId,Long teamId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementAssignTo  rato left join fetch rato.requirementId where rato.teamId.id = :teamId and rato.requirementId.id = :requirementId ";
		List<RequirementAssignTo> requirementAssignToList =  session.createQuery(query.toString()).setLong("teamId", teamId).setLong("requirementId", requirementId)
				.setCacheable(true).list();
		

		RequirementAssignTo requirementAssignTo = null;
		if (requirementAssignToList != null && requirementAssignToList.size() > 0) {
			requirementAssignTo = (RequirementAssignTo) requirementAssignToList.iterator().next();
		}
		return requirementAssignTo;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public boolean deleteRequirementAssignToByRequirementId(Long requirementId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String hql ="delete from RequirementAssignTo rato where rato.requirementId = :requirementId";
		
		Query query = session.createQuery(hql).setLong("requirementId", requirementId);
		query.executeUpdate();
		
		return true;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getAssignedTeamMembersCountByTeamMemberUserId(Long assignUserId, String JobType) {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		if("open".equalsIgnoreCase(JobType)){
			String query ="select count(*)from RequirementAssignTo rato where rato.assignUserId = :assignUserId and rato.requirementId IN(select req.id from Requirement req where req.requirementSubmissionStatus = 'open') ";
			List<Long> requirementAssignToCount = session.createQuery(query.toString()).setLong("assignUserId", assignUserId).list();
				Integer count = 0;
				if(requirementAssignToCount != null && !requirementAssignToCount.isEmpty()){
						count = requirementAssignToCount.get(0).intValue();
				}
				return count;
		}
		else{
			String query ="select count(*)from RequirementAssignTo rato where rato.assignUserId = :assignUserId";
			List<Long> requirementAssignToCount = session.createQuery(query.toString()).setLong("assignUserId", assignUserId).list();
				Integer count = 0;
				if(requirementAssignToCount != null && !requirementAssignToCount.isEmpty()){
						count = requirementAssignToCount.get(0).intValue();
				}
				return count;
		}
	
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public RequirementAssignTo getRequirementAssignToByRequirementId(Long requirementId) throws Exception {
		
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementAssignTo  rato left join fetch rato.requirementId  left join fetch rato.teamId  tm   left join fetch tm.ownerUserId   where rato.requirementId.id = :requirementId ";
			List<RequirementAssignTo> requirementAssignToList =  session.createQuery(query.toString()).setLong("requirementId", requirementId)
					.setCacheable(true).list();
			

			RequirementAssignTo requirementAssignTo = null;
			if (requirementAssignToList != null && requirementAssignToList.size() > 0) {
				requirementAssignTo = (RequirementAssignTo) requirementAssignToList.iterator().next();
			}
			return requirementAssignTo;
		}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean checkLoginUserIsRequirementAssignedToByRequirementIdAndUserId(Long requirementId, Long userId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		boolean isAvilable= false;
		String query = "from RequirementAssignTo  rato   where rato.requirementId.id = :requirementId and rato.assignUserId.id = :assignUserId";
		List<RequirementAssignTo> requirementAssignToList =  session.createQuery(query.toString()).setLong("requirementId", requirementId).setLong("assignUserId", userId)
				.setCacheable(true).list();
		

		RequirementAssignTo requirementAssignTo = null;
		if (requirementAssignToList != null && requirementAssignToList.size() > 0) {
			requirementAssignTo = (RequirementAssignTo) requirementAssignToList.iterator().next();
			isAvilable = true;
		}
		return isAvilable;
	}

    
  }

