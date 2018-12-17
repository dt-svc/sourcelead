package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ProjectClientDao;
import com.bis.sourcelead.dao.ProjectPlanningNewResourceRequestDao;
import com.bis.sourcelead.dto.ProjectPlanningNewResourceRequestDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectPlanningNewResourceRequest;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class ProjectPlanningNewResourceRequestDaoImpl implements ProjectPlanningNewResourceRequestDao{

private static final Logger logger = LoggerFactory.getLogger(ProjectPlanningNewResourceRequestDaoImpl.class);
	
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

	@Override
	@Transactional
	public List<Long> getProjectPlanningNewResourceRequestIdsByRequestId(Long projectPlanningRequestId) throws Exception {
		
		logger.debug("In ProjectPlanningResourceRequestDaoImpl class getProjectPlanningResourceRequestIdsByRequestId method");
		Session session = this.sessionFactory.getCurrentSession();
			 
		String query = "select resourceRequest.id from ProjectPlanningNewResourceRequest resourceRequest where resourceRequest.projectPlanningRequestId.id = :projectPlanningRequestId ";
		@SuppressWarnings("unchecked")
		List<Long> lineItemsIdsList =  session.createQuery(query.toString()).setLong("projectPlanningRequestId", projectPlanningRequestId)
									.list();
		
		return lineItemsIdsList;
	}

	@Override
	@Transactional
	public void deleteProjectPlanningNewResourceRequest(ProjectPlanningNewResourceRequest projectPlanningNewResourceRequest) 	throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (projectPlanningNewResourceRequest != null) {
				session.delete(projectPlanningNewResourceRequest);
			}
			logger.debug("ProjectPlanningNewResourceRequest Deleted Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECT_PLANNING_NEW_RESOURCE_REQUEST"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ProjectPlanningNewResourceRequest getProjectPlanningNewResourceRequestById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectPlanningNewResourceRequest resourceRequest left join fetch resourceRequest.projectPlanningRequestId "
				+ " where resourceRequest.id = :id and resourceRequest.status = '1' ";  
		List<ProjectPlanningNewResourceRequest> projectPlanningNewResourceRequestList = session.createQuery(query).setLong("id", id).list();
		ProjectPlanningNewResourceRequest projectPlanningNewResourceRequest = null;
		if(projectPlanningNewResourceRequestList != null && !projectPlanningNewResourceRequestList.isEmpty()){
			projectPlanningNewResourceRequest = projectPlanningNewResourceRequestList.iterator().next();
		}
		return projectPlanningNewResourceRequest;
	}

	@Override
	@Transactional
	public ProjectPlanningNewResourceRequest saveProjectPlanningNewResourceRequest(ProjectPlanningNewResourceRequest projectPlanningNewResourceRequest) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
			try {
				if (projectPlanningNewResourceRequest != null) {
					session.saveOrUpdate(projectPlanningNewResourceRequest);
				}
				logger.debug("ProjectPlanningNewResourceRequest Saved Successfully");
			} catch (Exception exception) {
				 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECT_PLANNING_NEW_RESOURCE_REQUEST"));
					throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
			}
		return projectPlanningNewResourceRequest;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ProjectPlanningNewResourceRequest getProjectPlanningNewResourceRequestByRequestUUID(String requestUUID) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectPlanningNewResourceRequest resourceRequest left join fetch resourceRequest.projectPlanningRequestId  where resourceRequest.requestUUID = :requestUUID and resourceRequest.status = '1' ";  
		List<ProjectPlanningNewResourceRequest> newResourceRequestList = session.createQuery(query).setString("requestUUID", requestUUID).list();
		ProjectPlanningNewResourceRequest projectPlanningNewResourceRequest = null;
		if(newResourceRequestList != null && !newResourceRequestList.isEmpty()){
			projectPlanningNewResourceRequest = newResourceRequestList.iterator().next();
		}
		return projectPlanningNewResourceRequest;
	}

	@Override
	@Transactional
	public ResponseList<ProjectPlanningNewResourceRequestDTO> getNewResourceRequestListByApproverIdAndOrgLocationId(
			ProjectPlanningNewResourceRequestDTO resourceRequestDTO, Long approverUserId, Long orgLocationId,
			String userRole) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		Query queryObj = null;
		Integer filterCount = 0;
		StringBuilder query1 = new StringBuilder("");
		String query = null;
		List<Long> countlist = null;
		ResponseList<ProjectPlanningNewResourceRequestDTO> response = new ResponseList<>();
		List<ProjectPlanningNewResourceRequestDTO> requestDTOList = new ArrayList<>();
		List<ProjectPlanningNewResourceRequest> resourceRequestList = new ArrayList<>();
		
		
		if(resourceRequestDTO.getSearchValue() !=null && !"".equalsIgnoreCase(resourceRequestDTO.getSearchValue())) {
			 query1.append("resourceRequest.projectPlanningRequestId.requestId like '%" + resourceRequestDTO.getSearchValue() + "%' ")
					.append("OR resourceRequest.projectPlanningRequestId.projectName like '%" + resourceRequestDTO.getSearchValue() + "%' ")
					.append("OR resourceRequest.projectPlanningRequestId.approverName like '%" + resourceRequestDTO.getSearchValue() + "%' ")
					.append("OR resourceRequest.projectPlanningRequestId.requestedUser like '%" + resourceRequestDTO.getSearchValue() + "%' ")
					.append("OR resourceRequest.consultingType like '%" + resourceRequestDTO.getSearchValue() + "%' ")
					.append("OR resourceRequest.roleName like '%" + resourceRequestDTO.getSearchValue() + "%' ")
					.append("OR resourceRequest.otherRole like '%" + resourceRequestDTO.getSearchValue() + "%' ");
						
			}
		
		if(!userRole.equalsIgnoreCase("ROLE_ADMIN") && !userRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")) {
			query = "from  ProjectPlanningNewResourceRequest resourceRequest  where  resourceRequest.projectPlanningRequestId.approverUser.id = :approverUserId "
					+ "and resourceRequest.projectPlanningRequestId.orgLocationId.id = :orgLocationId  and resourceRequest.status = '1' "
					+ " and resourceRequest.resourceStatus = :resourceStatus and resourceRequest.projectPlanningRequestId.requestType = 'New Request' ";
			
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
			
			query = "from  ProjectPlanningNewResourceRequest resourceRequest  where  resourceRequest.projectPlanningRequestId.orgLocationId.id = :orgLocationId "
					+ " and resourceRequest.status = '1' and resourceRequest.resourceStatus = :resourceStatus and resourceRequest.projectPlanningRequestId.requestType = 'New Request' ";
			
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
  			for(ProjectPlanningNewResourceRequest resourceRequest : resourceRequestList) {
  				ProjectPlanningNewResourceRequestDTO requestDTO = new ProjectPlanningNewResourceRequestDTO();
  				requestDTO.setProjectPlanningRequestId(resourceRequest.getProjectPlanningRequestId().getRequestId());
  				requestDTO.setProjectName(resourceRequest.getProjectPlanningRequestId().getProjectName());
  				requestDTO.setRequestedUser(resourceRequest.getProjectPlanningRequestId().getRequestedUser());
  				requestDTO.setApproverName(resourceRequest.getProjectPlanningRequestId().getApproverName());
  				if(resourceRequest.getOtherRole() != null && !resourceRequest.getOtherRole().isEmpty()) {
  					requestDTO.setRoleName(resourceRequest.getOtherRole());
  				}else {
  					requestDTO.setRoleName(resourceRequest.getRoleName());
  				}
  				requestDTO.setConsultingType(resourceRequest.getConsultingType());
  				requestDTO.setResourceStatus(resourceRequest.getResourceStatus());
  				requestDTO.setRequestUUID(resourceRequest.getRequestUUID());
  				requestDTO.setRejectedReason(resourceRequest.getRejectedReason());
  				requestDTO.setProjectPlanningRequestUUID(resourceRequest.getProjectPlanningRequestId().getRequestUUID());
  				requestDTO.setApprovedUser(resourceRequest.getUpdatedBy().getFullName());
  				
  				ProjectClients pc = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(resourceRequest.getProjectPlanningRequestId().getProjectId().getId(), orgLocationId);
  				if(pc != null) {
  					requestDTO.setProjectClientUUID(pc.getUuid());
  					requestDTO.setProjectUUID(resourceRequest.getProjectPlanningRequestId().getProjectId().getProjectUUID());
  				}
  				
  				requestDTOList.add(requestDTO);
  			}
  		}
		
		response.setData(requestDTOList);
		return response;
		
	}

	@Override
	@Transactional
	public List<ProjectPlanningNewResourceRequest> getNewResourceRequestListByApproverIdAndOrgLocationIdAndStatus(
			Long approverUserId, Long orgLocationId, String userRole) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<ProjectPlanningNewResourceRequest> projectPlanningNewResourceRequestList = null;
		if(!userRole.equalsIgnoreCase("ROLE_ADMIN") && !userRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")) {
			String query = "from ProjectPlanningNewResourceRequest resourceRequest  "
					+ " where resourceRequest.projectPlanningRequestId.approverUser.id = :approverUserId and resourceRequest.projectPlanningRequestId.orgLocationId.id = :orgLocationId "
					+ " and resourceRequest.status = '1' and resourceRequest.projectPlanningRequestId.requestType = 'New Request' ";
			projectPlanningNewResourceRequestList =  session.createQuery(query.toString()).setLong("approverUserId", approverUserId).setLong("orgLocationId", orgLocationId).list();
			
		}else {
			String query = "from ProjectPlanningNewResourceRequest resourceRequest  "
					+ " where  resourceRequest.projectPlanningRequestId.orgLocationId.id = :orgLocationId "
					+ " and resourceRequest.status = '1' and resourceRequest.projectPlanningRequestId.requestType = 'New Request' ";
			projectPlanningNewResourceRequestList =  session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
			
		}
		
		return projectPlanningNewResourceRequestList;
	}

}
