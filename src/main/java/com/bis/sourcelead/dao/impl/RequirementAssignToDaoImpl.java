package com.bis.sourcelead.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.RequirementAssignToDao;
import com.bis.sourcelead.entity.OrgLocations;
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
	
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = { Exception.class })	
	@Override
	public List<RequirementAssignTo> getRequirementAssignToListByRequirementId(Long requirementId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementAssignTo rat    where  rat.requirementId.id =:requirementId";
		return session.createQuery(query)
				      .setLong("requirementId", requirementId)
				      .setCacheable(true)
				      .list();
		
		
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public RequirementAssignTo getRequirementAssignToByRequirementIdandAssignToId(Long requirementId,Long teamId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementAssignTo  rato left join fetch rato.requirementId where rato.teamId.id = :teamId and rato.requirementId.id = :requirementId ";
		List<RequirementAssignTo> requirementAssignToList =  session.createQuery(query.toString())
																	.setLong("teamId", teamId)
																	.setLong("requirementId", requirementId)
																	.setCacheable(true)
																	.list();
		

		RequirementAssignTo requirementAssignTo = null;
		if (null != requirementAssignToList && requirementAssignToList.size() > 0) {
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


	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getAssignedTeamMembersCountByTeamMemberUserId(Long assignUserId, String JobType) {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		if("open".equalsIgnoreCase(JobType)){
			String query ="select count(*)from RequirementAssignTo rato where rato.assignUserId = :assignUserId and rato.requirementId IN(select req.id from Requirement req where req.requirementSubmissionStatus = 'open') ";
			List<Long> requirementAssignToCount = session.createQuery(query.toString())
					                                     .setLong("assignUserId", assignUserId)
					                                     .setCacheable(true)
					                                     .list();
				Integer count = 0;
				if(null != requirementAssignToCount && !requirementAssignToCount.isEmpty()){
						count = requirementAssignToCount.get(0).intValue();
				}
				return count;
		}
		else{
			String query ="select count(*)from RequirementAssignTo rato where rato.assignUserId = :assignUserId";
			List<Long> requirementAssignToCount = session.createQuery(query.toString())
														.setLong("assignUserId", assignUserId)
														.setCacheable(true)
														.list();
				Integer count = 0;
				if(null != requirementAssignToCount && !requirementAssignToCount.isEmpty()){
						count = requirementAssignToCount.get(0).intValue();
				}
				return count;
		}
	
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public RequirementAssignTo getRequirementAssignToByRequirementId(Long requirementId) throws Exception {
		
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementAssignTo  rato left join fetch rato.requirementId  left join fetch rato.teamId  tm   left join fetch tm.ownerUserId   where rato.requirementId.id = :requirementId ";
			List<RequirementAssignTo> requirementAssignToList =  session.createQuery(query.toString())
																		.setLong("requirementId", requirementId)
																		.setCacheable(true)
																		.list();
			

			RequirementAssignTo requirementAssignTo = null;
			if (null != requirementAssignToList && requirementAssignToList.size() > 0) {
				requirementAssignTo = (RequirementAssignTo) requirementAssignToList.iterator().next();
			}
			return requirementAssignTo;
		}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean checkLoginUserIsRequirementAssignedToByRequirementIdAndUserId(Long requirementId, Long userId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		boolean isAvilable= false;
		String query = "from RequirementAssignTo  rato   where rato.requirementId.id = :requirementId and rato.assignUserId.id = :assignUserId";
		List<RequirementAssignTo> requirementAssignToList =  session.createQuery(query.toString())
																	.setLong("requirementId", requirementId)
																	.setLong("assignUserId", userId)
				                                                    .setCacheable(true)
				                                                    .list();
		

		RequirementAssignTo requirementAssignTo = null;
		if (null != requirementAssignToList && requirementAssignToList.size() > 0) {
			requirementAssignTo = (RequirementAssignTo) requirementAssignToList.iterator().next();
			isAvilable = true;
		}
		return isAvilable;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementAssignTo> getAssignedToMembersByRequirementIdAndOrgLocationId(Long requirementId,Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementAssignTo rato   where rato.requirementId.id = :requirementId and rato.requirementId.id IN ( select req.id from Requirement req where req.orgLocationId.id =:orgLocationId)";
		return session.createQuery(query).setLong("requirementId", requirementId).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
	}


	@Override
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = { Exception.class })	
	public RequirementAssignTo getRequirementAssignToByRequirementIdandAssignedUserId(Long requirementId,Long assignedUserId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementAssignTo  rato   where rato.requirementId.id = :requirementId and rato.assignUserId.id = :assignedUserId ";
		List<RequirementAssignTo> requirementAssignToList =  session.createQuery(query.toString())
																	.setLong("assignedUserId", assignedUserId)
																	.setLong("requirementId", requirementId)
																	.setCacheable(true)
																	.list();
		RequirementAssignTo requirementAssignTo = null;
		if (null != requirementAssignToList && requirementAssignToList.size() > 0) {
			requirementAssignTo = (RequirementAssignTo) requirementAssignToList.iterator().next();
		}
		return requirementAssignTo;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<RequirementAssignTo> getAllAssignedRequirementByOrglocationId(Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementAssignTo  rato left join fetch rato.teamId team left join fetch rato.requirementId requirement where requirement.orgLocationId.id = :orgLocationId";
		return  session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<RequirementAssignTo> getAllAssignedRequirementByDateRange(Long orgLocationId, String userId,Date startDateUTC, Date endDateUTC) {
		
		Session session = this.sessionFactory.getCurrentSession();
		Map<String, Object> params = new HashMap<String,Object>();
		String subQuery = "";
		if(null != orgLocationId){
			params.put("orgLocationId", orgLocationId);
		}
		if(null != startDateUTC && null != endDateUTC){
			params.put("startDateUTC", startDateUTC);
			params.put("endDateUTC", endDateUTC);
			subQuery = "and rato.createdDate >= :startDateUTC and rato.createdDate <= :endDateUTC";
		}
		if(null != userId){
			Long usrId = Long.parseLong(userId);
			params.put("usrId", usrId);
			subQuery = subQuery + " and rato.assignUserId.id = :usrId";
		}
		String query = "from RequirementAssignTo  rato left join fetch rato.teamId team left join fetch rato.assignUserId left join fetch rato.requirementId requirement where requirement.orgLocationId.id = :orgLocationId "+subQuery;
		Query qry = session.createQuery(query);
		for (String str : qry.getNamedParameters())
        {
			if(str.equalsIgnoreCase("orgLocationId") ){
				qry.setLong(str, (long) params.get("orgLocationId"));
			}
			if(str.equalsIgnoreCase("startDateUTC") ){
				qry.setDate(str, (Date) params.get("startDateUTC"));
			}
			if(str.equalsIgnoreCase("endDateUTC") ){
				qry.setDate(str, (Date) params.get("endDateUTC"));
			}
			if(str.equalsIgnoreCase("usrId") ){
				qry.setLong(str, (long) params.get("usrId"));
			}
        }
		 return qry.setCacheable(true).list();
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<RequirementAssignTo> getAllAssignedRequirementByOrglocationIdAndTeamId(Long orgLocationId,
			Long teamId) {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementAssignTo rat    where  rat.teamId.orgLocationsId.id =:orgLocationId and rat.teamId.id =:teamId  GROUP BY rat.id";
		return session.createQuery(query)
				      .setLong("orgLocationId", orgLocationId)
				      .setLong("teamId", teamId)
				      .setCacheable(true)
				      .list();
	}
}

