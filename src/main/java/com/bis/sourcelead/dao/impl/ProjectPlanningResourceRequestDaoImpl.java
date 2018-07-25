package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.ProjectClientDao;
import com.bis.sourcelead.dao.ProjectPlanningResourceRequestDao;
import com.bis.sourcelead.dto.ProjectPlanningResourceRequestDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectPlanningResourceRequest;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class ProjectPlanningResourceRequestDaoImpl implements ProjectPlanningResourceRequestDao{

private static final Logger logger = LoggerFactory.getLogger(ProjectPlanningResourceRequestDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
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
	
	@Autowired
	ProjectClientDao projectClientDao;

	@Autowired
	CandidateDao candidateDao;
	
	@Override
	@Transactional
	public List<Long> getProjectPlanningResourceRequestIdsByRequestId(Long projectPlanningRequestId) throws Exception {
		
		logger.debug("In ProjectPlanningResourceRequestDaoImpl class getProjectPlanningResourceRequestIdsByRequestId method");
		Session session = this.sessionFactory.getCurrentSession();
			 
		String query = "select resourceRequest.id from ProjectPlanningResourceRequest resourceRequest where resourceRequest.projectPlanningRequestId.id = :projectPlanningRequestId";
		List<Long> lineItemsIdsList =  session.createQuery(query.toString()).setLong("projectPlanningRequestId", projectPlanningRequestId)
									.list();
		
		return lineItemsIdsList;
	}

	@Override
	@Transactional
	public ProjectPlanningResourceRequest getProjectPlanningResourceRequestById(Long id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectPlanningResourceRequest resourceRequest left join fetch resourceRequest.projectPlanningRequestId "
				+ " where resourceRequest.id = :id and resourceRequest.status = '1' ";  
		List<ProjectPlanningResourceRequest> projectPlanningResourceRequestList = session.createQuery(query).setLong("id", id).list();
		ProjectPlanningResourceRequest projectPlanningResourceRequest = null;
		if(projectPlanningResourceRequestList != null && !projectPlanningResourceRequestList.isEmpty()){
			projectPlanningResourceRequest = projectPlanningResourceRequestList.iterator().next();
		}
		return projectPlanningResourceRequest;
	}

	@Override
	@Transactional
	public void deleteProjectPlanningResourceRequest(ProjectPlanningResourceRequest projectPlanningResourceRequest)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (projectPlanningResourceRequest != null) {
				session.delete(projectPlanningResourceRequest);
			}
			logger.debug("ProjectPlanningResourceRequest Deleted Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECT_PLANNING_RESOURCE_REQUEST"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
	}

	@Override
	@Transactional
	public ProjectPlanningResourceRequest getProjectPlanningResourceRequestByRequestUUID(String requestUUID)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectPlanningResourceRequest resourceRequest left join fetch resourceRequest.orgLocationId left join fetch resourceRequest.projectPlanningRequestId planningRequest "
				+ " left join fetch planningRequest.projectId project left join fetch project.orgLocationsId left join fetch resourceRequest.candidateId "
				+ " where resourceRequest.requestUUID = :requestUUID and resourceRequest.status = '1' ";  
		List<ProjectPlanningResourceRequest> resourceRequestList = session.createQuery(query).setString("requestUUID", requestUUID).list();
		ProjectPlanningResourceRequest projectPlanningResourceRequest = null;
		if(resourceRequestList != null && !resourceRequestList.isEmpty()){
			projectPlanningResourceRequest = resourceRequestList.iterator().next();
		}
		return projectPlanningResourceRequest;
	}

	@Override
	@Transactional
	public ProjectPlanningResourceRequest saveProjectPlanningResourceRequest(
			ProjectPlanningResourceRequest projectPlanningResourceRequest) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (projectPlanningResourceRequest != null) {
				session.saveOrUpdate(projectPlanningResourceRequest);
			}
			logger.debug("ProjectPlanningResourceRequest Saved Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECT_PLANNING_RESOURCE_REQUEST"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
		return projectPlanningResourceRequest;
	}

	@Override
	@Transactional
	public ResponseList<ProjectPlanningResourceRequestDTO> getResourceRequestListByApproverIdAndOrgLocationId(
			ProjectPlanningResourceRequestDTO resourceRequestDTO, Long approverUserId, Long orgLocationId,
			String userRole) throws Exception {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		Query queryObj = null;
		Integer filterCount = 0;
		StringBuilder query1 = new StringBuilder("");
		String query = null;
		List<Long> countlist = null;
		ResponseList<ProjectPlanningResourceRequestDTO> response = new ResponseList<>();
		List<ProjectPlanningResourceRequestDTO> requestDTOList = new ArrayList<>();
		List<ProjectPlanningResourceRequest> resourceRequestList = new ArrayList<>();
		
		
		if(resourceRequestDTO.getSearchValue() !=null && !"".equalsIgnoreCase(resourceRequestDTO.getSearchValue())) {
			 query1.append("resourceRequest.projectPlanningRequestId.requestId like '%" + resourceRequestDTO.getSearchValue() + "%' ")
					.append("OR resourceRequest.projectPlanningRequestId.projectName like '%" + resourceRequestDTO.getSearchValue() + "%' ")
					.append("OR resourceRequest.projectPlanningRequestId.approverName like '%" + resourceRequestDTO.getSearchValue() + "%' ")
					.append("OR resourceRequest.projectPlanningRequestId.requestedUser like '%" + resourceRequestDTO.getSearchValue() + "%' ")
					.append("OR resourceRequest.candidateName like '%" + resourceRequestDTO.getSearchValue() + "%' ");
						
			}
		
		if(!userRole.equalsIgnoreCase("ROLE_ADMIN") && !userRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")) {
			query = "from  ProjectPlanningResourceRequest resourceRequest  where  resourceRequest.approverUser.id = :approverUserId "
					+ "and resourceRequest.candidateId.orgLocationId.id = :orgLocationId  and resourceRequest.status = '1' "
					+ "and resourceRequest.resourceStatus = :resourceStatus and resourceRequest.projectPlanningRequestId.requestType = 'Associate Request' ";
			
			if(query1.toString() != null && !query1.toString().isEmpty()){
				 queryObj = session.createQuery(query + "and "+"("+query1.toString()+")");
			}else{
				 queryObj = session.createQuery(query + query1.toString());
			}
			
			queryObj.setFirstResult(resourceRequestDTO.getStart());
			queryObj.setMaxResults(resourceRequestDTO.getLength());
			queryObj.setLong("approverUserId", approverUserId).setLong("orgLocationId", orgLocationId).setString("resourceStatus", resourceRequestDTO.getResourceStatus()).list();
			resourceRequestList= queryObj.list();
			
			countlist = session.createQuery("select count(*) " + query).setLong("approverUserId", approverUserId).setLong("orgLocationId", orgLocationId).setString("resourceStatus", resourceRequestDTO.getResourceStatus()).list();
			if(query1.toString() != null && !query1.toString().isEmpty()){
				filterCount = resourceRequestList.size();
			}
			
		}else {
			
			query = "from  ProjectPlanningResourceRequest resourceRequest  where  resourceRequest.candidateId.orgLocationId.id = :orgLocationId "
					+ " and resourceRequest.status = '1' and resourceRequest.resourceStatus = :resourceStatus and resourceRequest.projectPlanningRequestId.requestType = 'Associate Request' ";
			
			if(query1.toString() != null && !query1.toString().isEmpty()){
				 queryObj = session.createQuery(query + "and "+"("+query1.toString()+")");
			}else{
				 queryObj = session.createQuery(query + query1.toString());
			}
			
			queryObj.setFirstResult(resourceRequestDTO.getStart());
			queryObj.setMaxResults(resourceRequestDTO.getLength());
			queryObj.setLong("orgLocationId", orgLocationId).setString("resourceStatus", resourceRequestDTO.getResourceStatus()).list();
			resourceRequestList = queryObj.list();
			
			countlist = session.createQuery("select count(*) " + query).setLong("orgLocationId", orgLocationId).setString("resourceStatus", resourceRequestDTO.getResourceStatus()).list();
			if(query1.toString() != null && !query1.toString().isEmpty()){
				filterCount = resourceRequestList.size();
			}
		}
			
		
        int totalCount = 0;

		if (resourceRequestDTO != null) {
			if(countlist != null && !countlist.isEmpty()) {
				totalCount = countlist.iterator().next().intValue();
			}
		} 
		response.setRecordsTotal(totalCount);
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			response.setRecordsFiltered(filterCount);
		}else {
			response.setRecordsFiltered(totalCount);
		}
		
		if(resourceRequestList != null && resourceRequestList.size() > 0) {
  			for(ProjectPlanningResourceRequest resourceRequest : resourceRequestList) {
  				ProjectPlanningResourceRequestDTO requestDTO = new ProjectPlanningResourceRequestDTO();
  				requestDTO.setProjectPlanningRequestId(resourceRequest.getProjectPlanningRequestId().getRequestId());
  				requestDTO.setProjectName(resourceRequest.getProjectPlanningRequestId().getProjectName());
  				requestDTO.setRequestedUser(resourceRequest.getProjectPlanningRequestId().getRequestedUser());
  				if(resourceRequest.getApproverUser() != null) {
  					requestDTO.setApproverName(resourceRequest.getApproverName());
  				}else {
  					requestDTO.setApproverName(resourceRequest.getProjectPlanningRequestId().getApproverName());
  				}
  				requestDTO.setCandidateName(resourceRequest.getCandidateName());
  				requestDTO.setConsultingType(resourceRequest.getConsultingType());
  				requestDTO.setResourceStatus(resourceRequest.getResourceStatus());
  				requestDTO.setRequestUUID(resourceRequest.getRequestUUID());
  				requestDTO.setRejectedReason(resourceRequest.getRejectedReason());
  				requestDTO.setProjectPlanningRequestUUID(resourceRequest.getProjectPlanningRequestId().getRequestUUID());
  				requestDTO.setApprovedUser(resourceRequest.getUpdatedBy().getFullName());
  				ProjectClients pc = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(resourceRequest.getProjectPlanningRequestId().getProjectId().getId(), orgLocationId);
  				if(pc != null) {
  					requestDTO.setProjectClientUUID(pc.getUuid());
  				}
  				requestDTO.setProjectUUID(resourceRequest.getProjectPlanningRequestId().getProjectId().getProjectUUID());
  				requestDTO.setRequestedOrgLocationId(resourceRequest.getOrgLocationId().getId());
  				
  				Candidate candidate = candidateDao.getCandidateByCandidateId(resourceRequest.getCandidateId().getId());
  				if(candidate != null) {
  					requestDTO.setCandidateUUID(resourceRequest.getCandidateId().getCandidateUUID());
  					requestDTO.setUserCode(candidate.getConsultantUser().getUserCode());
  				}
  				
  				requestDTOList.add(requestDTO);
  			}
  		}
		
		response.setData(requestDTOList);
		
		
		return response;
	}

	@Override
	@Transactional
	public List<ProjectPlanningResourceRequest> getResourceRequestListByApproverIdAndOrgLocationIdAndStatus(
			Long approverUserId, Long orgLocationId, String userRole) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<ProjectPlanningResourceRequest> projectPlanningResourceRequestList = null;
		if(!userRole.equalsIgnoreCase("ROLE_ADMIN") && !userRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")) {
			String query = "from ProjectPlanningResourceRequest resourceRequest  "
					+ " where resourceRequest.approverUser.id = :approverUserId and  resourceRequest.candidateId.orgLocationId.id = :orgLocationId "
					+ " and resourceRequest.status = '1' and resourceRequest.projectPlanningRequestId.requestType = 'Associate Request' ";
			projectPlanningResourceRequestList =  session.createQuery(query.toString()).setLong("approverUserId", approverUserId).setLong("orgLocationId", orgLocationId).list();
			
		}else {
			String query = "from ProjectPlanningResourceRequest resourceRequest  "
					+ " where  resourceRequest.candidateId.orgLocationId.id = :orgLocationId "
					+ " and resourceRequest.status = '1' and resourceRequest.projectPlanningRequestId.requestType = 'Associate Request' ";
			projectPlanningResourceRequestList =  session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
			
		}
		
		return projectPlanningResourceRequestList;
	}
}
