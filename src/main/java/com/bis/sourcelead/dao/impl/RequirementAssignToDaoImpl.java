package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
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


import com.bis.sourcelead.dto.RequirementAssignToDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.ExpensesEntryDocuments;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

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
		String query = "from RequirementAssignTo rat    where  rat.requirementId.id =:requirementId and rat.status <> '0' ";
		return session.createQuery(query)
				      .setLong("requirementId", requirementId)
				      
				      .list();
		
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = { Exception.class })	
	@Override
	public ResponseList<RequirementAssignTo> loadRequirementAssignToListByRequirementId(RequirementAssignToDTO requirementAssignToDTO) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<RequirementAssignTo> assignedToList = null;
		ResponseList<RequirementAssignTo> response = new ResponseList<>();
		String query = "from RequirementAssignTo rat    where  rat.requirementId.id =:requirementId and rat.status <> '0'";
		Query queryObj = session.createQuery(query);
		if(requirementAssignToDTO.getStart() > 0) {
			queryObj.setFirstResult(requirementAssignToDTO.getStart());
		}
		if (requirementAssignToDTO != null && requirementAssignToDTO.getLength() > 0) {
			queryObj.setLong("requirementId", requirementAssignToDTO.getRequirementId()).setMaxResults(requirementAssignToDTO.getLength()).list();
		}
		assignedToList = queryObj.list();
		int count = 0;
		int filteredCount = 0;
		@SuppressWarnings("unchecked")
		List<Long> countlist = session.createQuery("select count(*) "
				+ query).setLong("requirementId", requirementAssignToDTO.getRequirementId()).list();
		if (countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		if (requirementAssignToDTO != null) {
			@SuppressWarnings("unchecked")
			List<Long> filteredCountlist = session.createQuery("select count(*) "
					+ query).setLong("requirementId", requirementAssignToDTO.getRequirementId()).list();
			if (filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.iterator().next().intValue();
			}
			response.setRecordsFiltered(filteredCount);
		}
		response.setData(assignedToList);
		return response;
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
		String query = "from RequirementAssignTo rato left join fetch rato.teamId  where rato.requirementId.id = :requirementId and rato.status <> '0' and rato.requirementId.id IN ( select req.id from Requirement req where req.orgLocationId.id =:orgLocationId)";
		return session.createQuery(query).setLong("requirementId", requirementId).setLong("orgLocationId", orgLocationId).list();
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
		String query = "from RequirementAssignTo  rato left join fetch rato.teamId team left join fetch rato.requirementId requirement where requirement.orgLocationId.id = :orgLocationId and rato.status <> '0'";
		return  session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
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
		String query = "from RequirementAssignTo  rato left join fetch rato.teamId team left join fetch rato.assignUserId left join fetch rato.requirementId requirement where requirement.orgLocationId.id = :orgLocationId and rato.status <> '0' "+subQuery;
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
		 return qry.list();
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<RequirementAssignTo> getAllAssignedRequirementByOrglocationIdAndTeamId(Long orgLocationId,
			Long teamId) {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementAssignTo rat    where  rat.teamId.orgLocationsId.id =:orgLocationId and rat.status <> '0' and rat.teamId.id =:teamId  GROUP BY rat.id";
		return session.createQuery(query)
				      .setLong("orgLocationId", orgLocationId)
				      .setLong("teamId", teamId)
				      
				      .list();
	}


    
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<RequirementAssignTo> getAllAssignRequirementByUserIdAndOrglocationId(String userId, Long orgLocationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query ="from RequirementAssignTo rat left join fetch rat.requirementId requirement left join fetch rat.assignUserId "
				      + "where rat.assignUserId =:userId and requirement.orgLocationId.id = :orgLocationId";
		return session.createQuery(query)
			      .setString("userId", userId)
			      .setLong("orgLocationId", orgLocationId)
			      
			      .list();

	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Integer getRequirementAssignToListCountByRequirementId(Long requirementId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select count(*)from RequirementAssignTo rat    where  rat.requirementId.id =:requirementId and rat.status <> '0'";
		List<Long>  requirementAssignToCount = session.createQuery(query.toString()).setLong("requirementId", requirementId)  .list();
		Integer count = 0;
		if(null != requirementAssignToCount && !requirementAssignToCount.isEmpty()){
		count = requirementAssignToCount.get(0).intValue();
		}
		return count;
			 

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementAssignTo> updateRequirementAssignToListByRequirementId(List<RequirementAssignTo> requirementAssignToList) throws Exception
	{
		List<RequirementAssignTo> newlyUpdatedRequirementAssignToList = new ArrayList<RequirementAssignTo>();
		Session session = this.sessionFactory.getCurrentSession();
		if (requirementAssignToList.size() > 0) {
			for (RequirementAssignTo rat : requirementAssignToList) {
				session.saveOrUpdate(rat);
				newlyUpdatedRequirementAssignToList.add(rat);
			}
		}
		return newlyUpdatedRequirementAssignToList;
	}
}

