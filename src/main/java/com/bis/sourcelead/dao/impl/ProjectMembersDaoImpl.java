package com.bis.sourcelead.dao.impl;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsDTO;
import com.bis.sourcelead.dto.MarginSheetReportDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.enums.Primary;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.timesheet.dao.TimesheetEntriesDao;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class ProjectMembersDaoImpl implements ProjectMembersDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
    CommonUtil commonUtil;
	
	@Autowired
	TimesheetEntriesDao timesheetEntriesDao;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectMembersDaoImpl.class);

	/**
	 * This method for save project member
	 * 
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectMembers saveProjectMember(ProjectMembers projectMembers) throws Exception {
		logger.debug("In ProjectMember Save method");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			if(projectMembers != null){
				session.saveOrUpdate(projectMembers);
				logger.debug("Project Member saved successfully..");
			}
		}catch(ConstraintViolationException exception)
		{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ORG"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
		return projectMembers;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndProjectId(Date startDate,Date endDate,Long locationsId, Long projectId) throws Exception{
		logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if (startDate != null) {
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		java.sql.Date sqlEndDate = null;
		if (endDate != null) {
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		String dateQuery = "";
		if (startDate != null && endDate != null) {
			dateQuery = " and pm.id IN ( "
					+ " select pmh.projectMemberId.id from ProjectMembersHistory pmh where "
					+ " ( pmh.projectTerminationDate IS NOT NULL and :startDate between pmh.projectAssignedDate and pmh.projectTerminationDate) OR "
					+ " ( pmh.projectTerminationDate IS NOT NULL and :endDate between pmh.projectAssignedDate and pmh.projectTerminationDate) OR "
					+ " ( pmh.projectTerminationDate IS NULL and :endDate >= pmh.projectAssignedDate) "
					+ " ) ";
		}
		String queryStr = "from ProjectMembers pm left join fetch pm.projects projects left join fetch projects.orgRelationId left join fetch projects.orgLocationsId left join fetch pm.employee emp left join fetch emp.user user "
			+ " where projects.orgLocationsId.id = :locationsId and projects.id = :projectId " + dateQuery;
		Query query = session.createQuery(queryStr.toString());
		if (startDate != null && endDate != null) {
			query.setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate);
		}
		@SuppressWarnings("unchecked")
		List<ProjectMembers> projectMemberList = query.setLong("locationsId", locationsId).setLong("projectId", projectId).list();
		return projectMemberList;
	}

	/**
	 * This method for update project member
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectMembers updateProjectMember(ProjectMembers projectMembers) throws Exception {
		logger.debug("In ProjectMember Update method");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			if(projectMembers != null){
				session.saveOrUpdate(projectMembers);
				logger.info("Record updated successfully..!");
			}
		}catch(ConstraintViolationException exception)
		{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECT_MEMBERS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
		return projectMembers;
	}

	/**
	 * This method for get project member by id
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectMembers getProjectMemberById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date  currentDate = new java.sql.Date(new Date().getTime());
		String query = " from ProjectMembers pm left join fetch pm.projects project left join fetch pm.projectMemberCandidteId can left join fetch can.orgLocationId left join fetch pm.masterCandidateId mc "
				+ " left join fetch pm.projectMemberCandidteId.parentCandidateId left join fetch pm.projectClientsId pc left join fetch pc.orgLocationsId loc "
				+ "where pm.id = :id" ;
		List<ProjectMembers> pmList =session.createQuery(query).setLong("id", id).list(); 
		ProjectMembers pm = null;
		if(pmList != null && !pmList.isEmpty())  {
			pm  = pmList.iterator().next();
		}
		return pm;  
	}

	/**
	 * This method for delete project member by id
	 * Here we are doing soft deletion only, record not deleted from DB just we have to change the status
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Boolean deleteProjectMemberById(Long id) throws Exception {
		 logger.debug("In ProjectMember deleteProjectMember method");
		 Session session = this.sessionFactory.getCurrentSession();
		  boolean isDeleted = false;
		  if(id != null){
			  try{
			  ProjectMembers projectMembers= (ProjectMembers)session.load(ProjectMembers.class,id);
			  projectMembers.setStatus("0");
			  session.saveOrUpdate(projectMembers);
			  logger.info("Successfully deleted !!!"); 
			  isDeleted = true;
			  }catch(Exception exception){
				  StringBuilder message= new StringBuilder(MessageFormat.format(statusDelete,"PROJECT_MEMBERS"));
				 throw new SourceLeadObjectNotFoundException(message.toString());  
			  }
		  }
		return isDeleted;
	}

	/**
	 * This method for get all the project members
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Collection<ProjectMembers> getAllProjectMembers() throws Exception {
		logger.debug("In ProjectMember getAllProjectMembers method");
        Session session = this.sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(ProjectMembers.class);
        List<ProjectMembers> projectsMembersList = crit.list();
        return projectsMembersList;
	}
		

	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getEmployeeAssignedProjectsCount(Long orgRelationId, Long orgLocationId ,Long projectId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = "from ProjectMembers pm where pm.projects.id IN (select p.id from Projects p  "
				+ "where p.orgRelationId.id = :orgRelationId and p.orgLocationsId.id = :orgLocationId) and pm.projects.id = :projectId" ;
		List<ProjectMembers> projectMembersList =session.createQuery(query.toString())
														.setLong("orgRelationId", orgRelationId)
				                                        .setLong("orgLocationId", orgLocationId)
				                                        .setLong("projectId", projectId)
				                                    	.list(); 
		      
	    return projectMembersList.size();
}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllTerminatedProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(Long userId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date  currentDate = new java.sql.Date(new Date().getTime());
		
		
		String subQueryForUserIdChecking = "(pm.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId) OR pm.projects.id IN(select pah.projectId.id from ProjectApproverHierarchy pah where pah.approverUserId.id = :userId))";
		String query = " from ProjectMembers pm left join fetch pm.projects left join fetch pm.employee where "+subQueryForUserIdChecking+" and (pm.id IN(select pmh.projectMemberId.id from ProjectMembersHistory pmh where pmh.status ='OPEN' and  pmh.projectTerminationDate < :currentDate))" ;
		List<ProjectMembers> idsList =session.createQuery(query)
											.setInteger("userId", userId.intValue())
											.setParameter("currentDate", currentDate)
											
										    .list(); 
		return idsList;  
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllTerminatedProjectMembersIdsByTimesheet() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date  currentDate = new java.sql.Date(new Date().getTime());
		String query = " from ProjectMembers pm left join fetch pm.projects left join fetch pm.employee where pm.id IN(select pmh.projectMemberId.id from ProjectMembersHistory pmh where pmh.status ='OPEN' and  pmh.projectTerminationDate < :currentDate)" ;
		List<ProjectMembers> idsList =session.createQuery(query)
											.setParameter("currentDate", currentDate)
											
										    .list(); 
		return idsList;  
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectMembers getProjectMemberByUserIdAndProjectId(Long userId, Long projectId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date  currentDate = new java.sql.Date(new Date().getTime());
		String subQueryForUserIdChecking = "(pm.projectMemberCandidteId.id IN(select cand.id from Candidate cand where cand.consultantUser.id = :userId))";
		String query = " from ProjectMembers pm left join fetch pm.projects left join fetch pm.employee where "+subQueryForUserIdChecking+" and pm.projects.id = :projectId" ;
		List<ProjectMembers> pmList =session.createQuery(query).setLong("userId", userId).setLong("projectId", projectId).list(); 
		ProjectMembers pm = null;
		if(pmList != null && !pmList.isEmpty())  {
			pm  = pmList.iterator().next();
		}
		return pm;  
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectMembers getProjectMemberByUserIdAndLoginOrglocationId(Long userId, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String subQueryForUserIdChecking = "(pm.projectMemberCandidteId.id IN(select cand.id from Candidate cand where cand.consultantUser.id = :userId))";
		String query = " from ProjectMembers pm left join fetch pm.projects project left join fetch pm.employee left join fetch project.orgLocationsId orgloc where "+subQueryForUserIdChecking+" and orgloc.id = :orgLocationId" ;
		List<ProjectMembers> pmList =session.createQuery(query).setLong("userId", userId).setLong("orgLocationId", orgLocationId).list(); 
		ProjectMembers pm = null;
		if(pmList != null && !pmList.isEmpty())  {
			pm  = pmList.iterator().next();
		}
		return pm;  
	}
	
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectMembers getProjectMemberByEmpIdAndProjectId(Long empId, Long projectId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date  currentDate = new java.sql.Date(new Date().getTime());
		String query = " from ProjectMembers pm left join fetch pm.projects left join fetch pm.employee where pm.employee.id = :empId and pm.projects.id = :projectId" ;
		List<ProjectMembers> pmList =session.createQuery(query).setLong("empId", empId).setLong("projectId", projectId).list(); 
		ProjectMembers pm = null;
		if(pmList != null && !pmList.isEmpty())  {
			pm  = pmList.iterator().next();
		}
		return pm;  
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllProjectMembersAssignedOrTerminateDateisGreaterThanProjectEndDate(Long projectId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from ProjectMembers pm left join fetch pm.projects left join fetch pm.employee emp left join fetch emp.user where pm.projects.id = :projectId and pm.id IN( select pmh.projectMemberId.id from ProjectMembersHistory pmh where (pmh.projectTerminationDate IS NULL) OR (pmh.projectTerminationDate IS NOT NULL and pmh.projectTerminationDate > pm.projects.endDate) OR (pmh.projectAssignedDate > pm.projects.endDate)) " ;
		List<ProjectMembers> pmList =session.createQuery(query).setLong("projectId", projectId).list(); 
		return pmList;  
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndEmpUserId(Long locationsId, Long empUserId) {
		logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		 
		String query = "from ProjectMembers pm left join fetch pm.projects pj left join fetch pj.orgRelationId rl left join fetch rl.locationId left join fetch rl.responderLocationId left join fetch pm.employee where pj.orgLocationsId = :orgLocationsId and  pm.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId ) ";
		List<ProjectMembers> projectMemberList =  session.createQuery(query.toString()).setLong("orgLocationsId", locationsId).setLong("userId", empUserId).list();
		return projectMemberList;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndCandidateId(Long locationsId,Long orgId, Long candidateId) {
		logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectMembers pm left join fetch pm.projectClientsId pc   "
				         + "  left join fetch pc.orgRelationId rl left join fetch rl.locationId "
				         + " left join fetch rl.responderLocationId "
				         + " left join fetch pm.employee "
				         + " left join fetch pm.masterCandidateId "
				         + " left join fetch pm.projectMemberCandidteId  "
				         + " where pm.status != 0  and  "
				         + " pm.id IN(select pmh.projectMemberId.id  from ProjectMembersHistory pmh  where  pmh.projectTerminationDate IS NULL OR (pmh.projectTerminationDate IS NOT NULL and pmh.projectTerminationDate > pmh.projectAssignedDate)) "
				         + " and  (pm.projectMemberCandidteId.parentCandidateId.id = :candidateId OR pm.projectMemberCandidteId.id = :candidateId) and "
				         + " ((pm.projectMemberCandidteId.designationType IS NOT NULL and pm.projectMemberCandidteId.designationType ='"+Constants.OFFSHORE_C2C+"' and pc.orgLocationsId.org.id = :orgId) OR ((pm.projectMemberCandidteId.designationType IS  NULL OR (pm.projectMemberCandidteId.designationType IS  NOT NULL and pm.projectMemberCandidteId.designationType !='"+Constants.OFFSHORE_C2C+"')) and pc.orgLocationsId.id = :locationsId)) GROUP By pm.projects.id";
		List<ProjectMembers> projectMemberList =  session.createQuery(query.toString()).setLong("locationsId", locationsId).setLong("orgId", orgId).setLong("candidateId", candidateId).list();
		return projectMemberList;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getProjectMembersListByOrgIdAndMasterCandidateId(Long orgId, Long masterCandidateId) {
		logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectMembers pm left join fetch pm.projectClientsId pc   left join fetch pc.orgRelationId rl left join fetch rl.locationId left join fetch rl.responderLocationId left join fetch pm.employee left join fetch pm.masterCandidateId left join fetch pm.projectMemberCandidteId  where pm.id IN(select pmh.projectMemberId.id  from ProjectMembersHistory pmh  where pmh.projectTerminationDate IS NULL OR (pmh.projectTerminationDate IS NOT NULL and pmh.projectTerminationDate > pmh.projectAssignedDate)) and  pm.masterCandidateId.id = :masterCandidateId and  pc.orgLocationsId.org.id = :orgId GROUP BY pm.projects.id";
		List<ProjectMembers> projectMemberList =  session.createQuery(query.toString()).setLong("orgId", orgId).setLong("masterCandidateId", masterCandidateId).list();
		return projectMemberList;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndApproverUserId(Long locationsId,Long empUserId) {
		logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = "from ProjectMembers pm left join fetch pm.projects projects  left join fetch projects.orgRelationId left join fetch projects.orgLocationsId left join fetch pm.masterCandidateId mc left join fetch pm.projectMemberCandidteId candidate left join fetch pm.projectClientsId pclient left join fetch pm.employee emp left join fetch emp.user  user "
				+ " where pclient.orgLocationsId.id = :locationsId and "
				+ " user.id IN(select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE')  "
				+ " and ( "
				+ "  (projects.id IN ( select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId =:userId)) "
				+ "   OR "
				+     Constants.loginUserAsAdminForProjects+""
			    + ") "; 
		
		@SuppressWarnings("unchecked")
		List<ProjectMembers> projectMemberList =  session.createQuery(query.toString()).setLong("orgLocationsId", locationsId).setLong("userId", empUserId).list();
		return projectMemberList;
	}
	
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<ProjectMembers> getProjectMembersListByTimesheetFrequencyOrgLocationsIdForPayroll(Long locationsId,Date startDate,Date endDate,int start,int length){
		logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
		
		ResponseList<ProjectMembers> response = new ResponseList<>();
		Session session = this.sessionFactory.getCurrentSession();
		
		
		java.sql.Date sqlStartDate = null;
		if(startDate != null){
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		
		java.sql.Date sqlEndDate = null;
		if(endDate != null){
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		
		String dateQuery = "";
		String statusQuery = "";
		
		String dateCountQuery = "";
		
		if(startDate != null && endDate != null){
			   dateQuery =  " and pm.id IN ( "
                + "    select pmh.projectMemberId.id  from ProjectMembersHistory pmh  where  "
                + "    ( pmh.projectTerminationDate IS NOT NULL and :startDate between pmh.projectAssignedDate and pmh.projectTerminationDate) OR "
                + "    ( pmh.projectTerminationDate IS NOT NULL and  :endDate between pmh.projectAssignedDate and pmh.projectTerminationDate) OR "
                + "    ( pmh.projectTerminationDate IS NOT NULL and  pmh.projectTerminationDate between :startDate and :endDate) OR "
                + "    ( pmh.projectTerminationDate IS NULL and :endDate >= pmh.projectAssignedDate) "
                + "  )  ";
			   dateCountQuery = dateQuery;
	     }
		
		
		//(sah.submissionId.startDate = :startDate OR sah.submissionId.endDate = :endDate OR sah.submissionId.startDate BETWEEN :startDate and :endDate OR sah.submissionId.endDate BETWEEN :startDate and :endDate)
	    statusQuery = " and pm.id IN(select sah.submissionId.projectMembers.id from SubmissionApproverHierarchy sah where (sah.submissionId.startDate = :startDate OR sah.submissionId.endDate = :endDate OR sah.submissionId.startDate BETWEEN :startDate and :endDate OR sah.submissionId.endDate BETWEEN :startDate and :endDate) and sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' )";
	     
		String queryStr = " from ProjectMembers pm left join fetch pm.projects projects  left join fetch projects.orgRelationId left join fetch projects.orgLocationsId left join fetch pm.masterCandidateId mc left join fetch pm.projectMemberCandidteId candidate left join fetch pm.projectClientsId pclient left join fetch pclient.orgRelationId pcOrgRelation"
				+ " where  candidate.orgLocationId.id = :locationsId"
				    + statusQuery
				    + dateQuery;
		Query query = session.createQuery(queryStr.toString());
		
		
//		String countQuery = " Select count(*) from ProjectMembers pm where pm.projectMemberCandidteId.orgLocationId.id = :locationsId "
//				+ statusQuery
//				+ dateCountQuery;
		
		
		if(startDate != null && endDate != null){
			query.setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate);
		}
		
		@SuppressWarnings("unchecked")
//		List<ProjectMembers> projectMemberList =  query.setLong("locationsId", locationsId).setFirstResult(start).setMaxResults(length).list();
		List<ProjectMembers> projectMemberList =  query.setLong("locationsId", locationsId).list();
		response.setData(projectMemberList);
		
//		Query countQ = session.createQuery(countQuery);
//		if(startDate != null && endDate != null){
//			countQ.setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate);
//		}
//		countQ.setLong("locationsId", locationsId);
//		
//		List<Long> countList  = null;
//		if(countQ != null) {
//			countList  = countQ.list();
//		}
//		int recordsTotal=0;
//		if(countList != null && !countList.isEmpty()) {
//			recordsTotal  =  countList.iterator().next().intValue();
//		}
//		response.setRecordsTotal(recordsTotal);
		
		/*if(searchQuery.toString() != null && !searchQuery.toString().isEmpty()){
			response.setRecordsFiltered(recordsFiltered);
		}
		else {
			response.setRecordsFiltered(recordsTotal);
		}*/
		
		return response;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<ProjectMembers> getProjectMembersListByTimesheetFrequencyOrgLocationsIdAndApproverUserId(String timesheetFrequency,Date startDate,Date endDate,Long clientId,Long projectId,String status,Long locationsId, Long approverUserId,String searchValue,String projectName,int start, int length,String timesheetFor,String isGlobalUser){


		logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
		
		ResponseList<ProjectMembers> response = new ResponseList<>();
 		Session session = this.sessionFactory.getCurrentSession();
		
		java.sql.Date sqlStartDate = null;
		if(startDate != null){
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		
		java.sql.Date sqlEndDate = null;
		if(endDate != null){
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		
		
		StringBuilder searchCountQuery = new StringBuilder();
		if(StringUtils.isNotEmpty(searchValue) && searchValue != null && !searchValue.equalsIgnoreCase("undefined")) {
		searchCountQuery.append("and( ")
			             .append("(pm.projectMemberCandidteId.firstName like '" + searchValue + "%' OR pm.projectMemberCandidteId.lastName like '%" + searchValue + "' )")
						.append("OR (concat(REPLACE(pm.projectMemberCandidteId.firstName,' ',''),REPLACE(pm.projectMemberCandidteId.lastName,' ','')) like '%" + searchValue.replaceAll("[\\s]+", "").trim() + "%')" )
						.append("OR pm.projectMemberCandidteId.email like '%" + searchValue + "%' ")
						.append("OR pm.projectMemberCandidteId.mobilePhone like '%" + searchValue + "%' ")
						.append("OR pm.projectMemberCandidteId.designationType like '%" + searchValue + "%' ")
						.append("OR pm.projects.projectName like '%" + searchValue + "%' ")
					    .append( " )");
		}
		if(StringUtils.isNotEmpty(projectName) && projectName != null) {
			searchCountQuery.append("and pm.projects.projectName like '%" + projectName + "%'");
		}
		
		String dateQuery = "";
		String userQuery = "";
		String clientQuery = "";
		String projectQuery = "";
		String statusQuery = "";
		String fillingOptionQuery = "";
		String sortingOrder = "";
		String groupBy = "";
		String biweeklyDateFilter = "";
		String frequencyQuery = "";
		
		String fillingOptionCountQuery = "";
		String dateCountQuery = "";
		String userCountQuery = "";
		String clientCountQuery = "";
		String projectCountQuery = "";
		String statusCountQuery = "";
		
		StringBuilder searchQuery = new StringBuilder();
		if(StringUtils.isNotEmpty(searchValue) && searchValue != null && !searchValue.equalsIgnoreCase("undefined")) {
		 searchQuery.append("and ( ")
				    .append("(pm.projectMemberCandidteId.firstName like '" + searchValue + "%' OR pm.projectMemberCandidteId.lastName like '%" + searchValue + "' )")
					.append("OR (concat(REPLACE(pm.projectMemberCandidteId.firstName,' ',''),REPLACE(pm.projectMemberCandidteId.lastName,' ','')) like '%" + searchValue.replaceAll("[\\s]+", "").trim() + "%')" )
					.append("OR pm.projectMemberCandidteId.email like '%" + searchValue + "%' ")
					.append("OR pm.projectMemberCandidteId.mobilePhone like '%" + searchValue + "%' ")
					.append("OR pm.projectMemberCandidteId.designationType like '%" + searchValue + "%' ")
					.append("OR pm.projects.projectName like '%" + searchValue + "%' ")
					.append( " )");
		}
		if(StringUtils.isNotEmpty(projectName) && projectName != null) {
			searchQuery.append("and projects.projectName  like '%" + projectName + "%'");
		}
		groupBy = " group by concat(pm.masterCandidateId.id,pm.projects.id) ";
		sortingOrder = " order by pm.id desc ";
		
		if(StringUtils.isNotEmpty(timesheetFrequency) && timesheetFrequency.equalsIgnoreCase("Bi-Weekly")){
			biweeklyDateFilter = " and DATEDIFF(pm.projects.weekStartDate,:startDate)%14=0";
		}
		
		if(startDate != null && endDate != null){
			   dateQuery =  " and pm.id IN ( "
                + "    select pmh.projectMemberId.id  from ProjectMembersHistory pmh  where  "
                + "    ( pmh.projectTerminationDate IS NOT NULL and :startDate between pmh.projectAssignedDate and pmh.projectTerminationDate) OR "
                + "    ( pmh.projectTerminationDate IS NOT NULL and  :endDate between pmh.projectAssignedDate and pmh.projectTerminationDate) OR "
                + "    ( pmh.projectTerminationDate IS NOT NULL and  pmh.projectTerminationDate between :startDate and :endDate) OR "
                + "    ( pmh.projectTerminationDate IS NULL and :endDate >= pmh.projectAssignedDate) "
                + "  )  ";
			   dateCountQuery = dateQuery;
	     }
		if(clientId != null){
			clientQuery = " and pclient.orgRelationId.id = :clientId ";
			clientCountQuery = " and pm.projectClientsId.orgRelationId.id = :clientId ";
		}
		
		if(projectId != null){
			projectQuery = " and pm.projects.id = :projectId ";
			projectCountQuery = " and pm.projects.id = :projectId ";
		}
	  
	     if(StringUtils.isNotEmpty(status) && !(status.equalsIgnoreCase("NEW") || status.equalsIgnoreCase("All"))){
			 statusQuery = " and pm.projectMemberCandidteId.orgLocationId.id IN(select sah.hierarchyId.projectClientsId.orgLocationsId.id from SubmissionApproverHierarchy sah where sah.submissionId.masterCandidateId.id = pm.masterCandidateId.id and sah.hierarchyId.projectId.id = pm.projects.id and sah.frequencyStartDate = :startDate and  sah.frequencyEndDate = :endDate and sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.getNameByValue(status)+"' )";
			 statusCountQuery = statusQuery;
         }
	     else if(StringUtils.isNotEmpty(status) && !(status.equalsIgnoreCase("All"))){
	    	 statusQuery = " and pm.projectMemberCandidteId.orgLocationId.id NOT IN(select sah.hierarchyId.projectClientsId.orgLocationsId.id from SubmissionApproverHierarchy sah where sah.submissionId.masterCandidateId.id = pm.masterCandidateId.id and sah.hierarchyId.projectId.id = pm.projects.id and sah.frequencyStartDate = :startDate and  sah.frequencyEndDate = :endDate and (sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL+"' OR sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' OR sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.REJECTED+"' OR sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.REOPENED+"') )";
	    	 statusCountQuery = statusQuery;
	     }
	     
	     if(StringUtils.isNotEmpty(timesheetFrequency)){
	    	 frequencyQuery = " and pm.projects.invoiceFrequency = :timesheetFrequency ";
	     }
	    		
	     //for Approval list
	     if(StringUtils.isEmpty(timesheetFor) || (StringUtils.isNotEmpty(timesheetFor) && !timesheetFor.equalsIgnoreCase("reports"))) {
	    	 fillingOptionQuery = " and ((pclient.id IN(select pc.id from ProjectClients pc where pc.level IN(select Max(subPm.projectClientsId.level) from ProjectMembers subPm where subPm.projects.id = projects.id and subPm.masterCandidateId.id = mc.id and subPm.projectClientsId.orgLocationsId.org.orgType <> 'GUEST' and subPm.projectClientsId.status != 0) and pc.projectId.id = projects.id )) "
	 	     		+ "                   OR ( pm.masterCandidateId.id IN(select sah.submissionId.masterCandidateId.id from SubmissionApproverHierarchy sah where sah.submissionId.projects.id = projects.id and sah.frequencyStartDate = :startDate and  sah.frequencyEndDate = :endDate "
	 	     		+ "                         and (sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL+"' OR sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' OR sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.REJECTED+"' OR sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.REOPENED+"'))))";
	 	     
	    	 
	 	     fillingOptionCountQuery =        "	and ("
	 								+	 "           (pm.projectClientsId.id IN"
	 								+	 "               (select pc.id from ProjectClients pc where pc.level IN(select Max(subPm.projectClientsId.level) from ProjectMembers subPm where subPm.projects.id = pm.projects.id and subPm.masterCandidateId.id = pm.masterCandidateId.id and subPm.projectClientsId.orgLocationsId.org.orgType <> 'GUEST' and subPm.projectClientsId.status != 0) and pc.projectId.id = pm.projects.id)"
	 								+	 "           ) "
	 								+    "        OR ( pm.masterCandidateId.id IN(select sah.submissionId.masterCandidateId.id from SubmissionApproverHierarchy sah where sah.submissionId.projects.id = pm.projects.id and sah.frequencyStartDate = :startDate and  sah.frequencyEndDate = :endDate "
	 								+    "                and (sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL+"' OR sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' OR sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.REJECTED+"' OR sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.REOPENED+"'))"
	 								+    "           )"
	 							    +    "      )";
	 	     
	 	     
	 	      userQuery =  " and ( "
					+  "        projects.id IN "
					+ "               ( select pah.projectId.id from ProjectApproverHierarchy pah  where pah.approverUserId.id =:userId  and "
					+ "                  (pah.projectId.projectApprovalType ='"+Constants.PARALLEL+"' OR "
					+ "                     (pah.projectId.projectApprovalType = '"+Constants.SEQUENTIAL+"' and   "
					+ "                                (pah.level = '1' and pah.projectClientsId.level IN(select Max(subPm.projectClientsId.level) from ProjectMembers subPm where subPm.projects.id = pm.projects.id and subPm.masterCandidateId.id = pm.masterCandidateId.id and subPm.projectClientsId.orgLocationsId.org.orgType <> 'GUEST'))"
					+ "                             OR  (pah.level = '1' and (pah.projectClientsId.orgLocationsId.id IN(select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = pm.projects.id and subpm.masterCandidateId.id = pm.masterCandidateId.id and subpm.projectMemberCandidteId.designationType = '"+Constants.OFFSHORE_C2C+"' ) ))"
					+ "                             OR  (pah.level= '1' and (pah.projectClientsId.level+1) IN ( select sah.hierarchyId.projectClientsId.level from SubmissionApproverHierarchy sah where sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' and sah.hierarchyId.projectId.id = pah.projectId.id and sah.frequencyStartDate = :startDate and  sah.frequencyEndDate = :endDate))"
					+ "                             OR  (pah.level= '2' and '1' IN (select sah.hierarchyId.level from SubmissionApproverHierarchy sah where sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' and sah.hierarchyId.projectClientsId.id = pah.projectClientsId.id and sah.frequencyStartDate = :startDate and  sah.frequencyEndDate = :endDate)) "
					+ "                     )          "
					+ "                  ) "
					+ "       )"
					 + "       OR  (pm.projectClientsId.orgLocationsId.id IN (select userRole.orgLocations.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.user.id = :userId  and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = :locationsId and userRole.userState = 'ACTIVE') and (pm.projects.projectApprovalType = '"+Constants.PARALLEL+"' OR (pm.projects.projectApprovalType = '"+Constants.SEQUENTIAL+"' and '1' IN (select sah.hierarchyId.level from SubmissionApproverHierarchy sah where sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' and sah.hierarchyId.projectClientsId.id = pm.projectClientsId.id and sah.frequencyStartDate = :startDate and  sah.frequencyEndDate = :endDate))))"
				     + "       OR  (pm.projects.isReportingManagerAsApprover = 'Y'  and pm.projectMemberCandidteId.consultantUser IS NOT NULL and :userId IN (Select userRole.reportingManager.id from UserRole userRole where userRole.user.id = pm.projectMemberCandidteId.consultantUser.id) and (pm.projects.projectApprovalType = '"+Constants.PARALLEL+"' OR (pm.projects.projectApprovalType = '"+Constants.SEQUENTIAL+"' and pm.projectMemberCandidteId.designationType = '"+Constants.OFFSHORE_C2C+"') OR (pm.projects.projectApprovalType = '"+Constants.SEQUENTIAL+"' and ( pm.projectClientsId.level IN(select Max(subPm.projectClientsId.level) from ProjectMembers subPm where subPm.projects.id = pm.projects.id and subPm.masterCandidateId.id = pm.masterCandidateId.id and subPm.projectClientsId.orgLocationsId.org.orgType <> 'GUEST') OR ((pm.projectClientsId.level+1) IN (select sah.hierarchyId.level from SubmissionApproverHierarchy sah where sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' and sah.hierarchyId.projectClientsId.id = pm.projectClientsId.id and sah.frequencyStartDate = :startDate and  sah.frequencyEndDate = :endDate))))) )"
					 + "     ) ";
	     
	     
	         userCountQuery =  " and ( "
					+  "        pm.projects.id IN "
					+ "               ( select pah.projectId.id from ProjectApproverHierarchy pah  where pah.approverUserId.id =:userId  and "
					+ "                  (pah.projectId.projectApprovalType ='"+Constants.PARALLEL+"' OR "
					+ "                     (pah.projectId.projectApprovalType ='"+Constants.SEQUENTIAL+"' and  "
					+ "                               (pah.level = '1' and pah.projectClientsId.level IN(select Max(subPm.projectClientsId.level) from ProjectMembers subPm where subPm.projects.id = pm.projects.id and subPm.masterCandidateId.id = pm.masterCandidateId.id and subPm.projectClientsId.orgLocationsId.org.orgType <> 'GUEST'))"
					+ "                             OR  (pah.level = '1' and (pah.projectClientsId.orgLocationsId.id IN(select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = pm.projects.id and subpm.masterCandidateId.id = pm.masterCandidateId.id and subpm.projectMemberCandidteId.designationType = '"+Constants.OFFSHORE_C2C+"' ) ))"
					+ "                             OR  (pah.level= '1' and (pah.projectClientsId.level+1) IN ( select sah.hierarchyId.projectClientsId.level from SubmissionApproverHierarchy sah where sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' and sah.hierarchyId.projectId.id = pah.projectId.id and sah.frequencyStartDate = :startDate and  sah.frequencyEndDate = :endDate))"
					+ "                             OR  (pah.level= '2' and '1' IN (select sah.hierarchyId.level from SubmissionApproverHierarchy sah where sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' and sah.hierarchyId.projectClientsId.id = pah.projectClientsId.id and sah.frequencyStartDate = :startDate and  sah.frequencyEndDate = :endDate)) "
					+ "                     )          "
					+ "                  ) "
					+ "       )"
					 + "       OR  (pm.projectClientsId.orgLocationsId.id IN (select userRole.orgLocations.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.user.id = :userId  and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = :locationsId and userRole.userState = 'ACTIVE') and (pm.projects.projectApprovalType = '"+Constants.PARALLEL+"' OR (pm.projects.projectApprovalType = '"+Constants.SEQUENTIAL+"' and '1' IN (select sah.hierarchyId.level from SubmissionApproverHierarchy sah where sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' and sah.hierarchyId.projectClientsId.id = pm.projectClientsId.id and sah.frequencyStartDate = :startDate and  sah.frequencyEndDate = :endDate))))"
				     + "       OR  (pm.projects.isReportingManagerAsApprover = 'Y'  and pm.projectMemberCandidteId.consultantUser IS NOT NULL and :userId IN (Select userRole.reportingManager.id from UserRole userRole where userRole.user.id = pm.projectMemberCandidteId.consultantUser.id) and (pm.projects.projectApprovalType = '"+Constants.PARALLEL+"' OR (pm.projects.projectApprovalType = '"+Constants.SEQUENTIAL+"' and pm.projectMemberCandidteId.designationType = '"+Constants.OFFSHORE_C2C+"') OR (pm.projects.projectApprovalType = '"+Constants.SEQUENTIAL+"' and ( pm.projectClientsId.level IN(select Max(subPm.projectClientsId.level) from ProjectMembers subPm where subPm.projects.id = pm.projects.id and subPm.masterCandidateId.id = pm.masterCandidateId.id and subPm.projectClientsId.orgLocationsId.org.orgType <> 'GUEST') OR ((pm.projectClientsId.level+1) IN (select sah.hierarchyId.level from SubmissionApproverHierarchy sah where sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' and sah.hierarchyId.projectClientsId.id = pm.projectClientsId.id and sah.frequencyStartDate = :startDate and  sah.frequencyEndDate = :endDate))))) )"
					 + "     ) ";
	      }
	     //for monthly reports
	      else {
	    	  userQuery =  " and  ( "
						+  "          projects.id IN ( select pah.projectId.id from ProjectApproverHierarchy pah  where pah.approverUserId.id =:userId  and pah.projectClientsId.id = pm.projectClientsId.id ) "
						+ "       OR  (pm.projectClientsId.orgLocationsId.id IN (select userRole.orgLocations.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.user.id = :userId  and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = :locationsId and userRole.userState = 'ACTIVE')) "
					    + "       OR  (pm.projects.isReportingManagerAsApprover = 'Y'  and pm.projectMemberCandidteId.consultantUser IS NOT NULL and :userId IN (Select userRole.reportingManager.id from UserRole userRole where userRole.user.id = pm.projectMemberCandidteId.consultantUser.id))"
				    		+ "     ) ";
		     
		     
		         userCountQuery =  " and ( "
						+  "           pm.projects.id IN  ( select pah.projectId.id from ProjectApproverHierarchy pah  where pah.approverUserId.id =:userId  and pah.projectClientsId.id = pm.projectClientsId.id ) "
				        + "       OR  (pm.projectClientsId.orgLocationsId.id IN (select userRole.orgLocations.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.user.id = :userId  and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = :locationsId and userRole.userState = 'ACTIVE'))"
					    + "       OR  (pm.projects.isReportingManagerAsApprover = 'Y'  and pm.projectMemberCandidteId.consultantUser IS NOT NULL and :userId IN (Select userRole.reportingManager.id from UserRole userRole where userRole.user.id = pm.projectMemberCandidteId.consultantUser.id))"
						 + "     ) ";
	      }
	     
	     
	     
	    String globalUserQuery = "";
	    if(StringUtils.isNotEmpty(isGlobalUser) && isGlobalUser.equalsIgnoreCase("Y")) {
	    	globalUserQuery = " and  (pm.projectMemberCandidteId.orgLocationId.id = :locationsId   OR ( pm.projectMemberCandidteId.orgLocationId.id <> :locationsId AND pm.projectMemberCandidteId.orgLocationId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :locationsId))) ";
	     }else {
	    	 globalUserQuery = " and (pm.projectMemberCandidteId.orgLocationId.id = :locationsId)";
	     }
	    String projectForQuery = " (pm.projects.isProjectForTimeSheets = 'Y' OR pm.id IN(select sah.submissionId.projectMembers.id from SubmissionApproverHierarchy sah where sah.status != '0' and sah.frequencyStartDate = :startDate and  sah.frequencyEndDate = :endDate))";
		String queryStr = " from ProjectMembers pm left join fetch pm.projects projects  left join fetch projects.orgRelationId left join fetch projects.orgLocationsId left join fetch pm.masterCandidateId mc left join fetch pm.projectMemberCandidteId candidate left join fetch candidate.consultantUser left join fetch pm.projectClientsId pclient left join fetch pclient.orgRelationId pcOrgRelation "
				+ " where "
				    +projectForQuery
				    +globalUserQuery
				    +frequencyQuery
				    +fillingOptionQuery
				    + statusQuery
				    +clientQuery
				    +projectQuery
				    + dateQuery
					+ userQuery
					+biweeklyDateFilter
					+searchQuery
					+groupBy
					+sortingOrder;
		Query query = session.createQuery(queryStr.toString());
		
		
		String countQuery = " Select count(*) from ProjectMembers pm where pm.status !=0 and  "
				+projectForQuery
				+globalUserQuery
				+frequencyQuery
				+fillingOptionCountQuery
				+ statusCountQuery
				+ clientCountQuery
				+projectCountQuery
				+ dateCountQuery
				+ userCountQuery
				+biweeklyDateFilter
				+searchCountQuery
				+groupBy;
		
		
		if(StringUtils.isNotEmpty(timesheetFrequency)){
			query.setString("timesheetFrequency", timesheetFrequency);
		}
		if(startDate != null && endDate != null){
			query.setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate);
		}
		if(clientId != null){
			query.setParameter("clientId", clientId);
		}
		
		if(projectId != null){
			query.setParameter("projectId", projectId);
		}
		
		
		Query queryObject =  query.setLong("locationsId", locationsId).setLong("userId", approverUserId);
		 if(length > 0) {
			 queryObject = queryObject.setFirstResult(start).setMaxResults(length);
		 }
		 @SuppressWarnings("unchecked")
		List<ProjectMembers> projectMemberList = queryObject.list();
		response.setData(projectMemberList);
		
		if(length >0 ) {
			Query countQ = session.createQuery(countQuery);
			if(startDate != null && endDate != null){
				countQ.setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate);
			}
			if(clientId != null){
				countQ.setParameter("clientId", clientId);
			}
			if(projectId != null){
				countQ.setParameter("projectId", projectId);
			}
			if(StringUtils.isNotEmpty(timesheetFrequency)){
				countQ.setString("timesheetFrequency", timesheetFrequency);
			}
			countQ.setLong("locationsId", locationsId).setLong("userId", approverUserId);
			
			List<Long> countList  = null;
			if(countQ != null) {
				countList  = countQ.list();
			}
			int recordsTotal=0;
			if(countList != null && !countList.isEmpty()) {
				recordsTotal  =  countList.size();
			}
			response.setRecordsTotal(recordsTotal);
		}else if(projectMemberList != null && !projectMemberList.isEmpty()){
			response.setRecordsTotal(projectMemberList.size());
		}else {
			response.setRecordsTotal(0);
		}
		return response;
	
	}
	
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndApproverUserId(Date startDate,Date endDate,Long locationsId, Long approverUserId){
		logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		
		
		java.sql.Date sqlStartDate = null;
		if(startDate != null){
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		
		java.sql.Date sqlEndDate = null;
		if(endDate != null){
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		
		String dateQuery = "";
		if(startDate != null && endDate != null){
			   dateQuery =  " and pm.id IN ( "
                + "    select pmh.projectMemberId.id  from ProjectMembersHistory pmh  where  "
                + "    ( pmh.projectTerminationDate IS NOT NULL and  :startDate between pmh.projectAssignedDate and pmh.projectTerminationDate) OR "
                + "    ( pmh.projectTerminationDate IS NOT NULL and  :endDate between pmh.projectAssignedDate and pmh.projectTerminationDate) OR "
                + "    ( pmh.projectTerminationDate IS NULL and :endDate >= pmh.projectAssignedDate) "
                + "  )  ";
	     }
		String queryStr = "from ProjectMembers pm left join fetch pm.projects projects  left join fetch projects.orgRelationId left join fetch projects.orgLocationsId left join fetch pm.masterCandidateId mc left join fetch pm.projectMemberCandidteId candidate left join fetch pm.projectClientsId pclient left join fetch pm.employee emp left join fetch emp.user  user "
				+ " where pclient.orgLocationsId.id = :locationsId  "
				    + dateQuery
					+ " and user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE') "
					+ " and ( "
					+  "      (  projects.id IN ( select pah.projectId.id from ProjectApproverHierarchy pah  where pah.approverUserId.id =:userId and ((pah.level = '2' and candidate.consultantUser.id NOT IN(select subpah.approverUserId.id from ProjectApproverHierarchy subpah where subpah.projectId.id = pah.projectId.id and subpah.level = '2')) OR (pah.level = '1' and candidate.consultantUser.id NOT IN(select subpah.approverUserId.id from ProjectApproverHierarchy subpah where subpah.projectId.id = pah.projectId.id )))))"
					 + "       OR "+Constants.loginUserAsAdminForProjects+""
					+ "     ) ";
		
		
		Query query = session.createQuery(queryStr.toString());
		
		if(startDate != null && endDate != null){
			query.setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate);
		}
		
		@SuppressWarnings("unchecked")
		List<ProjectMembers> projectMemberList =  query.setLong("locationsId", locationsId).setLong("userId", approverUserId).list();
		return projectMemberList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndApproverUserIdForExpenseReports(Date startDate,Date endDate,Long locationsId, Long approverUserId){
		logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		
		
		java.sql.Date sqlStartDate = null;
		if(startDate != null){
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		
		java.sql.Date sqlEndDate = null;
		if(endDate != null){
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		
		String dateQuery = "";
		if(startDate != null && endDate != null){
			   dateQuery =  " and pm.id IN ( "
                + "    select pmh.projectMemberId.id  from ProjectMembersHistory pmh  where  "
                + "    ( pmh.projectTerminationDate IS NOT NULL and  :startDate between pmh.projectAssignedDate and pmh.projectTerminationDate) OR "
                + "    ( pmh.projectTerminationDate IS NOT NULL and  :endDate between pmh.projectAssignedDate and pmh.projectTerminationDate) OR "
                + "    ( :endDate >= pmh.projectAssignedDate) "
                + "  )  ";
	     }
		String queryStr = "from ProjectMembers pm left join fetch pm.projects projects  left join fetch projects.orgRelationId left join fetch projects.orgLocationsId left join fetch pm.masterCandidateId mc left join fetch pm.projectMemberCandidteId candidate left join fetch pm.projectClientsId pclient left join fetch pm.employee emp left join fetch emp.user  user "
				+ " where pclient.orgLocationsId.id = :locationsId "
				    + dateQuery
					/*+ " and user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE') "*/
					+ " and ( "
					+  "      (  projects.id IN "
					+ "               ( select pah.projectId.id from ProjectApproverHierarchy pah  where pah.approverUserId.id =:userId and "
					+ "                             ("
					+ "                                  (pah.level = '2' and (candidate.consultantUser IS NULL OR candidate.consultantUser IS NOT NULL and candidate.consultantUser.id NOT IN(select subpah.approverUserId.id from ProjectApproverHierarchy subpah where subpah.projectId.id = pah.projectId.id and subpah.level = '2')))"
					+ "                               OR (pah.level = '1' and (candidate.consultantUser IS NULL OR candidate.consultantUser IS NOT NULL and candidate.consultantUser.id NOT IN(select subpah.approverUserId.id from ProjectApproverHierarchy subpah where subpah.projectId.id = pah.projectId.id )))) "
					+ "               )"
					+ "       )"
					 + "       OR "+Constants.loginUserAsAdminForProjects+""
					+ "     ) ";
		
		
		
		
		Query query = session.createQuery(queryStr.toString());
		
		if(startDate != null && endDate != null){
			query.setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate);
		}
		
		@SuppressWarnings("unchecked")
		List<ProjectMembers> projectMemberList =  query.setLong("locationsId", locationsId).setLong("userId", approverUserId).list();
		return projectMemberList;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndProjectIdAndStratDateAndEndDateAndStatus(
			 String status, String startDate, String endDate, Long projectId) {
		
		logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = "from ProjectMembers pm left join fetch pm.projects pj where  pm.projects.id = :projectId";
		List<ProjectMembers> projectMemberList =  session.createQuery(query.toString()).setLong("projectId",projectId).list();
		return projectMemberList;
		
	}


	/**
	 * This method for get all the project members by project id
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<ProjectMembers> getAllProjectMembersByProjectId(Long id) throws Exception {
        Session session = this.sessionFactory.getCurrentSession();
        List<ProjectMembers> projectsMembersList = null;
		Query projectsMembersListQuery= session.createQuery("from ProjectMembers pm left join fetch pm.projects left join fetch pm.projectMemberCandidteId left join fetch pm.employee emp left join fetch emp.user where pm.projects.id = :id ");
		projectsMembersList=projectsMembersListQuery.setLong("id", id).list();
		return projectsMembersList;
        
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<ProjectMembers> getAllProjectMembersWithMasterCandidateIdByProjectIdAndLocationId(Long id,Long locationsId,String isGlobalUser) throws Exception {
		logger.debug("In ProjectMember getAllProjectMembersByProjectId method");
        Session session = this.sessionFactory.getCurrentSession();
        
        
        String globalUserQuery = "";
		 if(StringUtils.isNotEmpty(isGlobalUser) && isGlobalUser.equalsIgnoreCase("Y")) {
		    	globalUserQuery = " pc.orgLocationsId.org.id IN (select ol.org.id from OrgLocations ol where ol.id = :locationsId)";
		    }else {
		    	globalUserQuery = " pc.orgLocationsId.id = :locationsId";
		    }
		 
        List<ProjectMembers> projectsMembersList = null;
		Query projectsMembersListQuery= session.createQuery("from ProjectMembers pm left join fetch pm.projects left join fetch pm.projectMemberCandidteId left join fetch pm.masterCandidateId mc left join fetch pm.projectClientsId pc where "+globalUserQuery+" and pm.projects.id = :id and pm.status = '1' group by pm.masterCandidateId.id ");
		projectsMembersList=projectsMembersListQuery.setLong("id", id).setLong("locationsId", locationsId).list();
		return projectsMembersList;
        
	}
	
	
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllTerminatedProjectMembersByProjectIdAndterminatedStatus(Long projectId,Primary isManuallyTerminated) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from ProjectMembers pm left join fetch pm.projects left join fetch pm.employee emp left join fetch emp.user where pm.projects.id = :projectId and pm.isManuallyTerminated = :isManuallyTerminated " ;
		List<ProjectMembers> pmList =session.createQuery(query).setLong("projectId", projectId).setParameter("isManuallyTerminated", isManuallyTerminated).list(); 
		return pmList;  
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Long> getAllProjectMembersIdsByemployeeId(Long userId) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select pm.id from ProjectMembers pm where pm.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId )" ;
		List<Long> idsList =session.createQuery(query).setInteger("userId", userId.intValue()) 
			    .list(); 
		return idsList;  
		
        
	}
	
	/**
	 * This Method gives List Of all Projectmembers based on login user who is approver/employee to that particular project
	 *  who did have 4 weeks prior/post submission records in DB
	 * 
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(Long userId,OrgLocations orgLocations) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date  currentDate = new java.sql.Date(new Date().getTime());
		Calendar currentCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		currentCal.setLenient(false);
		currentCal.setTime(new Date());
		Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(currentCal, "MON",orgLocations.getDateFormat());
		
		Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(startMonday,-84);
		Date previousMondayDate = previousMonday.getTime();
		java.sql.Date  previousMondaySqlDate = new java.sql.Date(previousMondayDate.getTime());
		
		Calendar nextMonday = CalendarUtil.getDateIncrementByincrement(startMonday,84);
		Date nextMondayDate = nextMonday.getTime();
		java.sql.Date  nextMondaySqlDate = new java.sql.Date(nextMondayDate.getTime());
		
		String subQueryForUserIdChecking = "(pm.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId) OR pm.projects.id IN(select pah.projectId.id from ProjectApproverHierarchy pah where pah.approverUserId.id = :userId))";
		String query = " from ProjectMembers pm left join fetch pm.projects left join fetch pm.employee emp left join fetch emp.user where "+subQueryForUserIdChecking+" and (pm.id IN(select pmh.projectMemberId.id from ProjectMembersHistory pmh where pmh.status ='OPEN' and  pmh.projectAssignedDate <= :nextMondayDate and (pmh.projectTerminationDate IS NULL OR pmh.projectTerminationDate >= :previousMonday))) and (pm.id NOT IN(select ts.projectMembers.id from TimesheetSubmissions ts where ts.status ='1' and ts.startDate = :nextMondayDate))" ;
		List<ProjectMembers> idsList =session.createQuery(query).setInteger("userId", userId.intValue()).setParameter("previousMonday", previousMondaySqlDate).setParameter("nextMondayDate", nextMondaySqlDate) 
			    .list(); 
		return idsList;  
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllProjectsByUserId(Long userId,Long orgLocationId,String fromType) {
		String subQuery = "";
		if(! "Associate".equalsIgnoreCase(fromType)){
			subQuery = "and pm.status <> '0'";
		}
		
		Session session = this.sessionFactory.getCurrentSession();

		String query = "from ProjectMembers pm left join fetch pm.projects left join fetch pm.projectClientsId left join fetch pm.employee emp left join fetch pm.projectMemberCandidteId "
				+ " where (pm.projectMemberCandidteId.id IN(select cand.id from Candidate cand where cand.consultantUser.id = :userId and cand.orgLocationId.id = :orgLocationId )"+subQuery+" and pm.projects.projectMode <> 'Draft')" ;
		
		List<ProjectMembers> allProjectsList =session.createQuery(query).setLong("userId", userId).setLong("orgLocationId",orgLocationId) 
			    .list();
		return allProjectsList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllProjectMembersByProjectIdsList(List<Long> projectIds) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectMembers pm left join fetch pm.employee emp left join fetch emp.user user  where pm.projects.id IN (:projectIds) GROUP BY pm.employee" ;
		List<ProjectMembers> allProjectMembersList =session.createQuery(query).setParameterList("projectIds", projectIds) 
			    .list();
		return allProjectMembersList;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getAllProjectMembersCountByProjectId(Long id) {
		
		    Session session = this.sessionFactory.getCurrentSession();
		    String status = Constants.MEMBER_ACTIVE;
		    String query = "select count(*) from ProjectMembers pm  where pm.projects.id = :id and pm.status = :status";
		    List<Long> ProjectMembersCount = (List<Long>) session.createQuery(query).setLong("id", id).setString("status", status).list();
		    
		    Integer count = null;
			if(ProjectMembersCount != null && !ProjectMembersCount.isEmpty()){
				count = ProjectMembersCount.get(0).intValue();
			}
			
		    return count;
		    
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer updateProjectMemberDeletedReason(Long projectMemberId,String reason) {
		Session session =  this.sessionFactory.getCurrentSession();
		String status = Constants.MEMBER_IN_ACTIVE;
		Query query = session.createQuery("update ProjectMembers pm set pm.status =:status ,terminateReason =:terminateReason  where pm.id = :projectMemberId");
		query.setLong("projectMemberId", projectMemberId);
		query.setString("status", status);
		query.setString("terminateReason",reason);
		int result = query.executeUpdate();
		return result;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllProjectsByUserIdAndStatus(Long userId, String status, Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectMembers pm left join fetch pm.projects left join fetch pm.employee emp  where pm.status =:status and (pm.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId and emp.orgLocations.id = :orgLocationId ))" ;
		List<ProjectMembers> allProjectsList =session.createQuery(query).setLong("userId", userId).setLong("orgLocationId", orgLocationId).setString("status",status).list(); 
		return allProjectsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer updateEndDateOfProjectMemberId(Date endDate, Long projectMemberId) {
		Session session =  this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("update ProjectMembers pm set pm.projectEndDate =:endDate where pm.id = :projectMemberId");
		query.setLong("projectMemberId", projectMemberId);
		query.setDate("endDate", endDate);
		int result = query.executeUpdate();
		return result;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllProjectsByUserIdForCandidatesHotList(Long userId) throws ParseException {


		

		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = null;
		String updatedDate  ="";
		Date currentDate =  commonUtil.currentDate();
		List<Candidate> candidateList ;
		java.sql.Date sqlCurrentDate = null;
		
		// for current Date Converting to Sql Date Format
		 if(currentDate != null){
				sqlCurrentDate = new java.sql.Date(currentDate.getTime());
		 }
		
		// This Code is for  getting the sql enddate based on range
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     Calendar c = Calendar.getInstance();
	      c.setTime(new Date()); // Now use today date.
	    c.add(Calendar.DATE, +30); // Adding  The DateRange days
	    updatedDate     = dateFormat.format(c.getTime());
	    Date parsed = dateFormat.parse(updatedDate);
	    java.sql.Date endDate = new java.sql.Date(parsed.getTime());
	   
		
		
		String query = "from ProjectMembers pm left join fetch pm.projects left join fetch pm.employee emp  where (pm.employee.id IN (select emp.id from Employees emp where emp.user.id = :userId  and  pm.id IN ( select pmh.projectMemberId.id from ProjectMembersHistory pmh  where  pmh.projectTerminationDate BETWEEN :sqlCurrentDate AND :endDate)) )" ;
		List<ProjectMembers> allProjectsList =session.createQuery(query)
				                                     .setLong("userId", userId)
			  	                                     .setDate("sqlCurrentDate", sqlCurrentDate)
	    		                                      .setDate("endDate", endDate)
				                                       
			                                          .list();
		return allProjectsList;
		
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Collection<ProjectMembers> getAllProjectMembersByProjectIdAndStatus(Long projectId, String status)
			throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
	        List<ProjectMembers> projectsMembersList = null;
	        String[] tokens = status.split(",");
	        
	        String subQuery2 = "";
	        if (Arrays.asList(tokens).contains("All")) {
				subQuery2 = subQuery2 + "pm.status ='1' or pm.status ='0' or ";
			}
	        if (Arrays.asList(tokens).contains("Active")) {
				subQuery2 = subQuery2 + "pm.status ='1' or ";
			}
	        if (Arrays.asList(tokens).contains("In-Active")) {
				subQuery2 = subQuery2 + "pm.status ='0' or ";
			}
	        
	        if ((tokens[0] != null && tokens[0].length() > 0) ) {
				subQuery2 = "and (" + subQuery2.substring(0, subQuery2.length() - 4) + ")";
			} else if (subQuery2 != null && !"".equals(subQuery2)) {
				subQuery2 = " (" + subQuery2.substring(0, subQuery2.length() - 4) + ")";
			}
	        
		Query projectsMembersListQuery= session.createQuery("from ProjectMembers pm left join fetch pm.projectMemberCandidteId left join fetch pm.projects left join fetch pm.employee emp left join fetch emp.user where pm.projects.id = :projectId  "+ subQuery2 + "");

		projectsMembersList=projectsMembersListQuery.setLong("projectId", projectId).list();
		return projectsMembersList;
	
	}


	@Override
    @Transactional(rollbackFor = { Exception.class })
	public Map<String, Float> getTotalHours(Long projectMemberId, Date startDate, Date endDate) throws Exception
	{
		
//	    public Map<String,Float> getTotalHoursByProjectMemberIdStartDateAndEndDateForInvoice(Long projectMemberId, Date startDate, Date endDate) throws Exception {

	        Session session = this.sessionFactory.getCurrentSession();
	        java.sql.Date sqlStartDate = null;
	        java.sql.Timestamp tsStartDate = null;
	        if (startDate != null) {
	            sqlStartDate = new java.sql.Date(startDate.getTime());
	            tsStartDate = new java.sql.Timestamp(startDate.getTime());
	        }

	        java.sql.Date sqlEndDate = null;
	        java.sql.Timestamp tsEndDate = null;
	        if (endDate != null) {
	            sqlEndDate = new java.sql.Date(endDate.getTime());
	            tsEndDate = new java.sql.Timestamp(endDate.getTime());
	        }

	        // Getting Max Hierarchy Level for which employee is Assigned
	        String subQueryForMaxLevel = " select MAX(subsah.hierarchyId.level) from SubmissionApproverHierarchy subsah where  subsah.submissionId.projectMembers.id = :projectMemberId ";
	        String query = "from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts left join fetch ts.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee emp left join fetch emp.user "
	                + " where pm.id = :projectMemberId and (sah.submissionId.startDate = :startDate OR sah.submissionId.endDate = :endDate OR sah.submissionId.startDate BETWEEN :startDate and :endDate OR sah.submissionId.endDate BETWEEN :startDate and :endDate) and sah.submissionId.status = '1'"
	                /*
	                 * For getting approved timesheet hours list
	                 */

	                + " and ( hierarchy.level IN(" + subQueryForMaxLevel
	                + ")) and sah.submissionApprovalStatus = :approvalStatus ";

	        @SuppressWarnings("unchecked")
	        List<SubmissionApproverHierarchy> submissionApproverHierarchyList = session.createQuery(query.toString())
	                .setLong("projectMemberId", projectMemberId).setParameter("approvalStatus", Submission_Status.APPROVED)
	                .setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).list();
	        LinkedHashMap<String, Float> monthTotals = new LinkedHashMap<>();
	        float totalHours = 0f;
	        float billableTotalHours = 0f;
	        float nonBillableTotalHours = 0f;
	        if (submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()) {
	            for (SubmissionApproverHierarchy sah : submissionApproverHierarchyList) {
	                if ((sah.getSubmissionId().getStartDate().equals(tsStartDate)
	                        && sah.getSubmissionId().getEndDate().before(tsEndDate))
	                        || (sah.getSubmissionId().getStartDate().after(tsStartDate)
	                                && sah.getSubmissionId().getEndDate().before(tsEndDate))
	                        || sah.getSubmissionId().getEndDate().equals(tsEndDate)) {

	                    float weekTotalHours = 0f;
	                    float weekBillableTotalHours = 0f;
	                    float weekNonBillableTotalHours = 0f;

	                    if (StringUtils.isNotEmpty(sah.getSubmissionId().getTotalHours())) {
	                        weekTotalHours = Float.parseFloat(sah.getSubmissionId().getTotalHours());
	                    }
	                    if (StringUtils.isNotEmpty(sah.getSubmissionId().getBillableHours())) {
	                        weekBillableTotalHours = Float.parseFloat(sah.getSubmissionId().getBillableHours());
	                    }
	                    if (StringUtils.isNotEmpty(sah.getSubmissionId().getNonBillableHours())) {
	                        weekNonBillableTotalHours = Float.parseFloat(sah.getSubmissionId().getNonBillableHours());
	                    }

	                    
	                    totalHours = CommonUtil.getCombinedHours(totalHours, weekTotalHours);
	                    billableTotalHours = CommonUtil.getCombinedHours(billableTotalHours, weekBillableTotalHours);
	                    nonBillableTotalHours = CommonUtil.getCombinedHours(nonBillableTotalHours,
	                            weekNonBillableTotalHours);
	                } else {
	                    if (sah.getSubmissionId().getEndDate().equals(tsStartDate)
	                            || (sah.getSubmissionId().getStartDate().before(tsStartDate)
	                                    && sah.getSubmissionId().getEndDate().after(tsStartDate))) {
	                        float weekTotalHours = 0f;
	                        float weekBillableTotalHours = 0f;
	                        float weekNonBillableTotalHours = 0f;

	                        int dayOfWeek = CalendarUtil.getDayOfTheDate(tsStartDate);
	                        Collection<TimesheetEntries> timesheetEntries = timesheetEntriesDao
	                                .getAllTimesheetEntriesBySubmissionId(sah.getSubmissionId().getId());
	                        for (TimesheetEntries te : timesheetEntries) {
	                            if (te.getProjectTask() != null && te.getProjectTask().getBillableType() != null && te
	                                    .getProjectTask().getBillableType().equalsIgnoreCase(Constants.TASK_NON_BILLABLE)) {
	                                weekNonBillableTotalHours = CommonUtil.getCombinedHours(weekNonBillableTotalHours,
	                                        getTotalHoursBasedOnDayOfTheWeekBasedOnStartDate(te, dayOfWeek));
	                            } else {
	                                weekBillableTotalHours = CommonUtil.getCombinedHours(weekBillableTotalHours,
	                                        getTotalHoursBasedOnDayOfTheWeekBasedOnStartDate(te, dayOfWeek));
	                            }
	                        }
	                        weekTotalHours = CommonUtil.getCombinedHours(weekBillableTotalHours, weekNonBillableTotalHours);

	                        totalHours = CommonUtil.getCombinedHours(totalHours, weekTotalHours);
	                        billableTotalHours = CommonUtil.getCombinedHours(billableTotalHours, weekBillableTotalHours);
	                        nonBillableTotalHours = CommonUtil.getCombinedHours(nonBillableTotalHours,
	                                weekNonBillableTotalHours);
	                    }

	                    else {
	                        float weekTotalHours = 0f;
	                        float weekBillableTotalHours = 0f;
	                        float weekNonBillableTotalHours = 0f;

	                        int dayOfWeek = CalendarUtil.getDayOfTheDate(endDate);
	                        Collection<TimesheetEntries> timesheetEntries = timesheetEntriesDao
	                                .getAllTimesheetEntriesBySubmissionId(sah.getSubmissionId().getId());
	                        for (TimesheetEntries te : timesheetEntries) {
	                            if (te.getProjectTask() != null && te.getProjectTask().getBillableType() != null && te
	                                    .getProjectTask().getBillableType().equalsIgnoreCase(Constants.TASK_NON_BILLABLE)) {
	                                weekNonBillableTotalHours = CommonUtil.getCombinedHours(weekNonBillableTotalHours,
	                                        getTotalHoursBasedOnDayOfTheWeekBasedOnEndDate(te, dayOfWeek));
	                            } else {
	                                weekBillableTotalHours = CommonUtil.getCombinedHours(weekBillableTotalHours,
	                                        getTotalHoursBasedOnDayOfTheWeekBasedOnEndDate(te, dayOfWeek));
	                            }
	                        }
	                        weekTotalHours = CommonUtil.getCombinedHours(weekBillableTotalHours, weekNonBillableTotalHours);

	                        totalHours = CommonUtil.getCombinedHours(totalHours, weekTotalHours);
	                        billableTotalHours = CommonUtil.getCombinedHours(billableTotalHours, weekBillableTotalHours);
	                        nonBillableTotalHours = CommonUtil.getCombinedHours(nonBillableTotalHours,
	                                weekNonBillableTotalHours);

	                    }

	                }
	            }

	        }
	        monthTotals.put("TOTAL_HOURS", totalHours);
	        monthTotals.put("BILLABLE_HOURS", billableTotalHours);
	        monthTotals.put("NONBILLABLE_HOURS", nonBillableTotalHours);
	        return monthTotals;
	}
	
	private Float getTotalHoursBasedOnDayOfTheWeekBasedOnStartDate(TimesheetEntries te,int dayofweek){
        Float weekTotalHours = 0f;
        for(int i = 8;i>= dayofweek;i--){
            weekTotalHours = CommonUtil.getCombinedHours(weekTotalHours, getDayHours(te,i));
        }
       
       return weekTotalHours;
   }
   
   private Float getTotalHoursBasedOnDayOfTheWeekBasedOnEndDate(TimesheetEntries te,int dayofweek){
        Float weekTotalHours = 0f;
        for(int i = 2;i<= dayofweek;i++){
            weekTotalHours = CommonUtil.getCombinedHours(weekTotalHours, getDayHours(te,i));
        }
       
       return weekTotalHours;
   }
   private Float getDayHours(TimesheetEntries te,int flag){
       
       switch(flag){
        case 1:  if(StringUtils.isNotEmpty(te.getSunHours())){
                     String hours = te.getSunHours().replaceAll(":", ".");
                      return Float.parseFloat(hours);
                  }
          case 2:  if(StringUtils.isNotEmpty(te.getMonHours())){
                      String hours = te.getMonHours().replaceAll(":", ".");
                     return Float.parseFloat(hours);
                  }
              
          case 3:  if(StringUtils.isNotEmpty(te.getTueHours())){
                      String hours = te.getTueHours().replaceAll(":", ".");
                     return Float.parseFloat(hours);
                  }
              
         case 4:  if(StringUtils.isNotEmpty(te.getWedHours())){
                      String hours = te.getWedHours().replaceAll(":", ".");
                      return Float.parseFloat(hours);
                   }
             
         case 5:  if(StringUtils.isNotEmpty(te.getThuHours())){
                      String hours = te.getThuHours().replaceAll(":", ".");
                      return Float.parseFloat(hours);
                   }
         case 6:  if(StringUtils.isNotEmpty(te.getFriHours())){
                       String hours = te.getFriHours().replaceAll(":", ".");
                       return Float.parseFloat(hours);
                   }
     
         case 7:  if(StringUtils.isNotEmpty(te.getSatHours())){
                        String hours = te.getSatHours().replaceAll(":", ".");
                        return Float.parseFloat(hours);
                   }
         case 8:  if(StringUtils.isNotEmpty(te.getSunHours())){
             String hours = te.getSunHours().replaceAll(":", ".");
              return Float.parseFloat(hours);
          }
           
           default: return 0f;
       }
   }


	@Override
	 @Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllProjectsByOrgLocationId(Long orglocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from ProjectMembers pm left join fetch pm.projects left join fetch pm.projectMemberCandidteId left join fetch pm.masterCandidateId "
				+ " left join fetch pm.projectClientsId pc left join fetch pc.orgLocationsId loc where loc.id = :orglocationId group by pm.masterCandidateId.id";
		List<ProjectMembers> allProjectMembersList = session.createQuery(query).setLong("orglocationId", orglocationId).list();
		return allProjectMembersList;
	}



	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllProjectsbyEmployeeId(Long employeeId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectMembers pm left join fetch pm.employee emp where emp.id = :employeeId";
		List<ProjectMembers> allProjectMembersList = session.createQuery(query).setLong("employeeId", employeeId).list();
		return allProjectMembersList;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Long> getAllProjectMembersIdsByProjectId(Long projectId) throws Exception {
		
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select pm.id from ProjectMembers pm where pm.projects.id = :projectId" ;
		List<Long> idsList =session.createQuery(query).setLong("projectId", projectId) 
			    .list(); 
		return idsList;  
		
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deleteProjectMember(ProjectMembers projectMembers) throws Exception {
		logger.debug("In ProjectMember Save method");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			if(projectMembers != null){
				session.delete(projectMembers);
				logger.debug("Project Member deleted successfully..");
			}
		}catch(ConstraintViolationException exception)
		{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ORG"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
		
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllProjectMembersByProjectClientIdAndStatus(Long projectClientsId, String status,String roleIds,String teamIds)
			throws Exception {

		 Session session = this.sessionFactory.getCurrentSession();
		 List<ProjectMembers> pmList = null;
		 ArrayList<ProjectMembers> projectsMembersList = new ArrayList<ProjectMembers>();
		 if(StringUtils.isBlank(status)) {
			 status = "All";
		 }
	        String[] tokens = status.split(",");
	        String [] roleId = null;
	        String [] teamId = null;
	        List<Long> roleIdsList = new ArrayList<>();
	        List<Long> teamIdsList = new ArrayList<>();
	        String subQuery2 = "";
	     
	        if (Arrays.asList(tokens).contains("Active")) {
				subQuery2 = subQuery2 + "pm.status ='1' or ";
			}
	        
	        if (Arrays.asList(tokens).contains("In-Active")) {
				subQuery2 = subQuery2 + "pm.status ='0' or ";
			}
	        
	        if (Arrays.asList(tokens).contains("All")) {
				subQuery2 = subQuery2 + "pm.status ='0' or pm.status ='1' or ";
			}
	        
	        if ((tokens[0] != null && tokens[0].length() > 0) ) {
				subQuery2 = "and (" + subQuery2.substring(0, subQuery2.length() - 4) + ")";
			} else if (subQuery2 != null && !"".equals(subQuery2)) {
				subQuery2 = " (" + subQuery2.substring(0, subQuery2.length() - 4) + ")";
			}
	 
	        if(StringUtils.isNotBlank(roleIds)) {
				  roleId = roleIds.split(",");
				  for(int i=0; i < roleId.length; i++){
						 roleIdsList.add(Long.valueOf(roleId[i]));
					 }
		    }
	        
	        if(StringUtils.isNotBlank(teamIds)) {
	        	teamId = teamIds.split(",");
				  for(int i=0; i < teamId.length; i++){
						 teamIdsList.add(Long.valueOf(teamId[i]));
					 }
		    }
	        
	        String subquery3 = "and user.id IN(select userRole.user.id from UserRole userRole where userRole.roles.id IN (:roleIdsList))";
	        String subquery4 = "and user.id IN(select teamMembers.memberUserId.id from TeamMembers teamMembers where teamMembers.status = '1' and teamMembers.teamId.id IN (:teamIdsList))";
	        
	        
	      
	        if (roleIdsList != null && !roleIdsList.isEmpty() && StringUtils.isBlank(teamIds)) {
	        		
	        		 Query projectsMembersListQuery= session.createQuery("from ProjectMembers pm left join fetch pm.projectMemberCandidteId cand "
	        					+ "left join fetch pm.projects left join fetch pm.employee emp left join fetch cand.consultantUser user  "
	        					+ "where pm.projectClientsId = :projectClientsId  "+ subQuery2 + " "+subquery3+" ");
	        			pmList = projectsMembersListQuery.setLong("projectClientsId", projectClientsId).setParameterList("roleIdsList", roleIdsList).list();
	        			projectsMembersList.addAll(pmList);
	        } 
	        
	        else if (teamIdsList != null && !teamIdsList.isEmpty() && StringUtils.isBlank(roleIds)) {
        		
       		 Query projectsMembersListQuery= session.createQuery("from ProjectMembers pm left join fetch pm.projectMemberCandidteId cand "
       					+ "left join fetch pm.projects left join fetch pm.employee emp left join fetch cand.consultantUser user  "
       					+ "where pm.projectClientsId = :projectClientsId  "+ subQuery2 + " "+subquery4+" ");
       			pmList = projectsMembersListQuery.setLong("projectClientsId", projectClientsId).setParameterList("teamIdsList", teamIdsList).list();
       			projectsMembersList.addAll(pmList);
	        }
	        
	        else if (StringUtils.isNotBlank(teamIds) && StringUtils.isNotBlank(roleIds)) {
        		
	       		 Query projectsMembersListQuery= session.createQuery("from ProjectMembers pm left join fetch pm.projectMemberCandidteId cand "
	       					+ "left join fetch pm.projects left join fetch pm.employee emp left join fetch cand.consultantUser user  "
	       					+ "where pm.projectClientsId = :projectClientsId  "+subQuery2 +"  "+subquery3+" "+subquery4+" ");
	       			pmList = projectsMembersListQuery.setLong("projectClientsId", projectClientsId).setParameterList("roleIdsList", roleIdsList).setParameterList("teamIdsList", teamIdsList).list();
	       			projectsMembersList.addAll(pmList);
	        }
	        
	        else {
	        	 Query projectsMembersListQuery= session.createQuery("from ProjectMembers pm left join fetch pm.projectMemberCandidteId cand "
     					+ "left join fetch pm.projects left join fetch pm.employee emp left join fetch cand.consultantUser user  "
     					+ "where pm.projectClientsId = :projectClientsId  "+ subQuery2 + "");

	        	 pmList = projectsMembersListQuery.setLong("projectClientsId", projectClientsId).list();
	        	 projectsMembersList.addAll(pmList);
	        }
	        return projectsMembersList;
	
	}


	@Override
	@Transactional
	public List<Long> getAllProjectMembersIdsByProjectIdAndOrgLocationId(Long projectId, Long orglocationId)
			throws Exception {
				Session session = this.sessionFactory.getCurrentSession();
				String query = "select pm.id from ProjectMembers pm where pm.projects.id = :projectId and "
						+ "pm.projectClientsId.orgLocationsId.id = :orglocationId and pm.status = '1' " ;
				List<Long> idsList =session.createQuery(query).setLong("projectId", projectId).setLong("orglocationId", orglocationId)  
					    .list(); 
				return idsList;  
        
	}


	@Override
	@Transactional
	public ProjectMembers getProjectMemberByCandidateIdAndProjectIdAndOrgLocationId(Long candidateId, Long projectId,
			Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date  currentDate = new java.sql.Date(new Date().getTime());
		String query = " from ProjectMembers pm left join fetch pm.projects left join fetch pm.projectMemberCandidteId left join fetch pm.masterCandidateId "
				+ " left join fetch pm.projectClientsId left join fetch pm.projectClientsId.orgLocationsId "
				+ "where pm.projects.id = :projectId and pm.projectMemberCandidteId = :candidateId and pm.projectClientsId.orgLocationsId.id = :orgLocationId and pm.status = '1' " ;
		List<ProjectMembers> pmList =session.createQuery(query).setLong("candidateId", candidateId).setLong("projectId", projectId).setLong("orgLocationId", orgLocationId).list(); 
		ProjectMembers pm = null;
		if(pmList != null && !pmList.isEmpty())  {
			pm  = pmList.iterator().next();
		}
		return pm;  
	}


	@Override
	@Transactional
	public List<ProjectMembers> getAllProjectMembersByMasterCandidateId(Long masterCandidateId, Long projectId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectMembers pm left join fetch pm.projects left join fetch pm.masterCandidateId  where pm.projects.id = :projectId and "
				+ " pm.masterCandidateId.id = :masterCandidateId and pm.status = '1'" ;
		List<ProjectMembers> allProjectsList =session.createQuery(query).setLong("masterCandidateId", masterCandidateId).setLong("projectId",projectId).list(); 
		return allProjectsList;
	}


	@Override
	@Transactional
	public Integer getProjectMembersCountByProjectClientId(Long projectClientId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query ="select count(*) from ProjectMembers pm where pm.projectClientsId.id = :projectClientId and pm.status = '1' ";
		List<Long> favouriteCount = session.createQuery(query.toString()).setLong("projectClientId", projectClientId).list();
			Integer count = 0;
			if(favouriteCount != null && !favouriteCount.isEmpty()){
					count = favouriteCount.get(0).intValue();
			}
			return count;
	}


	@Override
	@Transactional
	public ProjectMembers getProjectMembersByCandidateIdAndProjectId(Long candidateId, Long projectId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from ProjectMembers pm left join fetch pm.projectMemberCandidteId left join fetch pm.projects where  "
				+ "pm.projects.id = :projectId and pm.projectMemberCandidteId.id = :candidateId and pm.status = '1' " ;
		List<ProjectMembers> pmList =session.createQuery(query).setLong("projectId", projectId).setLong("candidateId", candidateId).list(); 
		ProjectMembers pm = null;
		if(pmList != null && !pmList.isEmpty())  {
			pm  = pmList.iterator().next();
		}
		return pm;  
	}
	
	
	// This method is for fetching all project members irrespective of their status
	@Override
	@Transactional
	public ProjectMembers getAllProjectMembersByMasterCandidateIdAndProjectIdLocationId(Long masterCandidateId, Long projectId,Long locationId,String isGlobalUser)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		String globalUserQuery = "";
		 if(StringUtils.isNotEmpty(isGlobalUser) && isGlobalUser.equalsIgnoreCase("Y")) {
		    	globalUserQuery = " pc.orgLocationsId.org.id IN (select ol.org.id from OrgLocations ol where ol.id = :locationId)";
		    }else {
		    	globalUserQuery = " pc.orgLocationsId.id = :locationId";
		    }
		 
		String query = " from ProjectMembers pm left join fetch pm.masterCandidateId mc left join fetch pm.projectMemberCandidteId can left join fetch pm.projectClientsId pc left join fetch pc.orgLocationsId loc left join fetch pm.projects where  "
				+ "pm.projects.id = :projectId and pm.masterCandidateId.id = :masterCandidateId and "+globalUserQuery+" " ;
		List<ProjectMembers> pmList =session.createQuery(query).setLong("projectId", projectId).setLong("locationId", locationId).setLong("masterCandidateId", masterCandidateId).list(); 
		ProjectMembers pm = null;
		if(pmList != null && !pmList.isEmpty())  {
			pm  = pmList.iterator().next();
		}
		return pm;  
	}
		
	
	@Override
	@Transactional
	public ProjectMembers getProjectMembersByMasterCandidateIdAndProjectIdLocationId(Long masterCandidateId, Long projectId,Long locationId,String isGlobalUser)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		String globalUserQuery = "";
		 if(StringUtils.isNotEmpty(isGlobalUser) && isGlobalUser.equalsIgnoreCase("Y")) {
		    	globalUserQuery = " pc.orgLocationsId.org.id IN (select ol.org.id from OrgLocations ol where ol.id = :locationId)";
		    }else {
		    	globalUserQuery = " (pm.projectMemberCandidteId.designationType <> 'Offshore C2C' and pc.orgLocationsId.id = :locationId) OR (pm.projectMemberCandidteId.designationType = 'Offshore C2C' and pc.orgLocationsId.org.id IN (select ol.org.id from OrgLocations ol where ol.id = :locationId))";
		    }
		 
		String query = " from ProjectMembers pm left join fetch pm.masterCandidateId mc left join fetch pm.projectMemberCandidteId can left join fetch pm.projectClientsId pc left join fetch pc.orgLocationsId loc left join fetch pm.projects where  "
				+ "pm.projects.id = :projectId and pm.masterCandidateId.id = :masterCandidateId and "+globalUserQuery+" and pm.status = '1' " ;
		List<ProjectMembers> pmList =session.createQuery(query).setLong("projectId", projectId).setLong("locationId", locationId).setLong("masterCandidateId", masterCandidateId).list(); 
		ProjectMembers pm = null;
		if(pmList != null && !pmList.isEmpty())  {
			pm  = pmList.iterator().next();
		}
		return pm;  
	}
	
	@Override
	@Transactional
	public ProjectMembers getProjectMembersByMasterCandidateIdAndProjectIdLocationIdForFinance(Long masterCandidateId, Long projectId,Long locationId,String isGlobalUser)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		String globalUserQuery = "";
		 if(StringUtils.isNotEmpty(isGlobalUser) && isGlobalUser.equalsIgnoreCase("Y")) {
		    	globalUserQuery = " pc.orgLocationsId.org.id IN (select ol.org.id from OrgLocations ol where ol.id = :locationId)";
		    }else {
		    	globalUserQuery = " pc.orgLocationsId.id = :locationId";
		    	}
		String query = " from ProjectMembers pm left join fetch pm.masterCandidateId mc left join fetch pm.projectMemberCandidteId can left join fetch pm.projectClientsId pc left join fetch pc.orgLocationsId loc left join fetch pm.projects where  "
				+ "pm.projects.id = :projectId and pm.masterCandidateId.id = :masterCandidateId and "+globalUserQuery+" and pm.status = '1' " ;
		List<ProjectMembers> pmList =session.createQuery(query).setLong("projectId", projectId).setLong("locationId", locationId).setLong("masterCandidateId", masterCandidateId).list(); 
		ProjectMembers pm = null;
		if(pmList != null && !pmList.isEmpty())  {
			pm  = pmList.iterator().next();
		}
		return pm;  
	}
	
	@Override
	@Transactional
	public ProjectMembers getActiveOrNonProjectMemberByMasterCandidateIdAndProjectIdLocationId(Long masterCandidateId, Long projectId,Long locationId,Long orgId,String isGlobalAccess) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		 String  globalUserQuery = " and  (pm.projectMemberCandidteId.orgLocationId.id = :locationId  "
	    			+ "                      OR  ("
	    			+ "                                  (pm.projectMemberCandidteId.orgLocationId.id  IN(select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = pm.projects.id and subpm.masterCandidateId.id = pm.masterCandidateId.id and subpm.projectMemberCandidteId.designationType = '"+Constants.OFFSHORE_C2C+"') OR :locationId NOT IN(select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = pm.projects.id and subpm.masterCandidateId.id = pm.masterCandidateId.id)) "
	    					+ "                             and pm.projectMemberCandidteId.orgLocationId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :locationId)"
	    					+ "                  )"
	    					+ ") ";
		
		String query = " from ProjectMembers pm left join fetch pm.masterCandidateId mc left join fetch pm.projectMemberCandidteId can left join fetch pm.projectClientsId pc left join fetch pc.orgLocationsId loc left join fetch pm.projects where  "
				+ " pm.projects.id = :projectId and pm.masterCandidateId.id = :masterCandidateId  " +globalUserQuery;
	
		List<ProjectMembers> pmList =session.createQuery(query).setLong("projectId", projectId).setLong("locationId", locationId).setLong("masterCandidateId", masterCandidateId).list(); 
		ProjectMembers pm = null;
		if(pmList != null && !pmList.isEmpty())  {
			pm  = pmList.iterator().next();
		}
		return pm;  
	}
	
	/*removed status to resolve reopen issue in time sheet*/
	@Override
	@Transactional
	public ProjectMembers getClientProjectMemberByProjectMemberId(Long projectMemberId) throws Exception {
 		Session session = this.sessionFactory.getCurrentSession();
		String query = " from ProjectMembers pm  left join fetch pm.projectClientsId pc left join fetch pc.orgLocationsId loc left join fetch pm.masterCandidateId mc  left join fetch pm.projectMemberCandidteId pmc left join fetch pm.projects projects where  "
				+ " (projects.id IN( select subPm.projects.id from ProjectMembers subPm where subPm.id = :projectMemberId ) and mc.id IN( select subPm.masterCandidateId.id from ProjectMembers subPm where subPm.id = :projectMemberId )) and pc.level IN ( select MIN(subPm.projectClientsId.level) from ProjectMembers subPm where subPm.projectClientsId.orgLocationsId.org.orgType <> '"+Constants.GUEST+"' and subPm.masterCandidateId.id = pm.masterCandidateId.id and subPm.projects.id = pm.projects.id) " ;
		List<ProjectMembers> pmList =session.createQuery(query).setLong("projectMemberId", projectMemberId).list(); 
		ProjectMembers pm = null;
		if(pmList != null && !pmList.isEmpty())  {
			pm  = pmList.iterator().next();
		}
		return pm;  
	}
	
	@Override
	@Transactional
	public ProjectMembers getConsultantUserProjectMemberByProjectMemberId(Long projectMemberId) throws Exception {
 		Session session = this.sessionFactory.getCurrentSession();
		String query = " from ProjectMembers pm  left join fetch pm.projectClientsId pc left join fetch pc.orgLocationsId loc left join fetch pm.masterCandidateId mc  left join fetch pm.projectMemberCandidteId pmc left join fetch pmc.workLocation left join fetch pmc.consultantUser consultantUser  left join fetch pm.projects projects left join fetch pm.employee emp left join fetch emp.user user "
				+ " where  "
				+ " (projects.id IN( select subPm.projects.id from ProjectMembers subPm where subPm.id = :projectMemberId ) and mc.id IN( select subPm.masterCandidateId.id from ProjectMembers subPm where subPm.id = :projectMemberId )) and consultantUser IS NOT NULL  " ;
		List<ProjectMembers> pmList =session.createQuery(query).setLong("projectMemberId", projectMemberId).list(); 
		ProjectMembers pm = null;
		if(pmList != null && !pmList.isEmpty())  {
			pm  = pmList.iterator().next();
		}
		return pm;  
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllProjectMembersByProjectClientIdAndActiveStatusRecords(Long projectClientsId) throws Exception {

		 Session session = this.sessionFactory.getCurrentSession();
		 List<ProjectMembers> pmList = null;
		 ArrayList<ProjectMembers> projectsMembersList = new ArrayList<ProjectMembers>();
        	 Query projectsMembersListQuery= session.createQuery("from ProjectMembers pm left join fetch pm.projectMemberCandidteId cand "
 					+ "left join fetch pm.projects left join fetch pm.employee emp left join fetch cand.consultantUser user  "
 					+ "where pm.projectClientsId = :projectClientsId  and pm.status ='1'");
        	 pmList = projectsMembersListQuery.setLong("projectClientsId", projectClientsId).list();
	        	 projectsMembersList.addAll(pmList);
	      
	        return projectsMembersList;
	
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectMembers getProjectMembersByCandidateIdAndProjectClientId(Long candidateId, Long projectClientsId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from ProjectMembers pm left join fetch pm.projectMemberCandidteId left join fetch pm.projects where  "
				+ "pm.projectClientsId.id = :projectClientsId and pm.projectMemberCandidteId.id = :candidateId and pm.status = '1' " ;
		List<ProjectMembers> pmList =session.createQuery(query).setLong("projectClientsId", projectClientsId).setLong("candidateId", candidateId).list(); 
		ProjectMembers pm = null;
		if(pmList != null && !pmList.isEmpty())  {
			pm  = pmList.iterator().next();
		}
		return pm;  
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllProjectMembersByProjectClientId(Long projectClientsId) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 List<ProjectMembers> pmList = null;
		 ArrayList<ProjectMembers> projectsMembersList = new ArrayList<ProjectMembers>();
        	 Query projectsMembersListQuery= session.createQuery("from ProjectMembers pm left join fetch pm.masterCandidateId left join fetch pm.projectMemberCandidteId cand "
 					+ "left join fetch pm.projects left join fetch pm.employee emp left join fetch cand.consultantUser user  "
 					+ "where pm.projectClientsId = :projectClientsId");
        	 pmList = projectsMembersListQuery.setLong("projectClientsId", projectClientsId).list();
	        	 projectsMembersList.addAll(pmList);
	      
	        return projectsMembersList;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectMembers getProjectMembersByMasterCandidateIdAndProjectIdLocationIdForExpences(Long masterCandidateId,
			Long projectId, Long locationId,String isGlobalUser) throws Exception {
		
		String globalUserQuery = "";
		 if(StringUtils.isNotEmpty(isGlobalUser) && isGlobalUser.equalsIgnoreCase("Y")) {
		    	globalUserQuery = " pc.orgLocationsId.org.id IN (select ol.org.id from OrgLocations ol where ol.id = :locationId)";
		    }else {
		    	globalUserQuery = " pc.orgLocationsId.id = :locationId";
		    }

		Session session = this.sessionFactory.getCurrentSession();
		String query = " from ProjectMembers pm left join fetch pm.masterCandidateId mc left join fetch pm.projectMemberCandidteId can left join fetch pm.projectClientsId pc left join fetch pc.orgLocationsId loc left join fetch pm.projects where  "
				+ "pm.projects.id = :projectId and pm.masterCandidateId.id = :masterCandidateId and "+globalUserQuery+" " ;
		List<ProjectMembers> pmList =session.createQuery(query).setLong("projectId", projectId).setLong("locationId", locationId).setLong("masterCandidateId", masterCandidateId).list(); 
		ProjectMembers pm = null;
		if(pmList != null && !pmList.isEmpty())  {
			pm  = pmList.iterator().next();
		}
		return pm;  
	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllProjectMembersBasedOnProjectIdStartDateEndDateAndEmploymentType(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto) throws Exception {
		logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
		List<ProjectMembers> pmList = null;
		OrgLocations orgLocation = orgLocationService.getOrgLocations(dayWiseTimesheetReoprtsDto.getLocationsId());
		Date startDate = null;
		Date endDate = null;
		if(StringUtils.isNotEmpty(dayWiseTimesheetReoprtsDto.getStartDate())&&StringUtils.isNotEmpty(dayWiseTimesheetReoprtsDto.getEndDate())){
			  startDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dayWiseTimesheetReoprtsDto.getStartDate());
			  endDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dayWiseTimesheetReoprtsDto.getEndDate());
		}
		java.sql.Date sqlStartDate = null;
		if(startDate != null){
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		
		java.sql.Date sqlEndDate = null;
		if(endDate!= null){
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		
		String dateQuery = "";
		if(startDate != null && endDate!= null){
			   dateQuery =  " and pm.id IN ( "
                + "    select pmh.projectMemberId.id  from ProjectMembersHistory pmh  where "
                + "    ( pmh.projectTerminationDate IS NOT NULL and  :startDate between pmh.projectAssignedDate and pmh.projectTerminationDate) OR "
                + "    ( pmh.projectTerminationDate IS NOT NULL and  :endDate between pmh.projectAssignedDate and pmh.projectTerminationDate) OR "
                + "    ( :endDate >= pmh.projectAssignedDate) "
                + "  )  ";
	     }
		
		String memberStatusQuery = "";
		if(dayWiseTimesheetReoprtsDto.getAssociateStatus() != null && !dayWiseTimesheetReoprtsDto.getAssociateStatus().equalsIgnoreCase("ALL")) {
			if(dayWiseTimesheetReoprtsDto.getAssociateStatus().equalsIgnoreCase("ACTIVE")) {
				memberStatusQuery += " and urole.userState = 'ACTIVE' ";
			} else {
				memberStatusQuery += " and urole.userState = 'INACTIVE' ";
			}
		}
		
		String loginUserRoleQuery = "";
		if(dayWiseTimesheetReoprtsDto.getLoginUserRoleType() != null && !dayWiseTimesheetReoprtsDto.getLoginUserRoleType().equalsIgnoreCase("ROLE_ADMIN")) {
			if(StringUtils.isNoneBlank(dayWiseTimesheetReoprtsDto.getIsGlobalUser()) && 
					dayWiseTimesheetReoprtsDto.getIsGlobalUser().equalsIgnoreCase(Constants.YES)) {
				loginUserRoleQuery += " and ((pm.projectMemberCandidteId.consultantUser.id IN (select urole.user.id from UserRole urole where urole.orgLocations.org.id = '"+dayWiseTimesheetReoprtsDto.getOrgId()+"' "
						+ "and  urole.reportingManager.id = '"+dayWiseTimesheetReoprtsDto.getLoginUserId()+"' "+memberStatusQuery+")) OR "
								+ " (pm.projects.id IN (select pah.projectId.id from ProjectApproverHierarchy pah where pah.approverUserId = '"+dayWiseTimesheetReoprtsDto.getLoginUserId()+"' and pah.projectId.id IN (:projectIds))) ) ";
			}else {
				
				loginUserRoleQuery += " and ((pm.projectMemberCandidteId.consultantUser.id IN (select urole.user.id from UserRole urole where urole.orgLocations.id = '"+dayWiseTimesheetReoprtsDto.getLocationsId()+"' "
						+ "and  urole.reportingManager.id = '"+dayWiseTimesheetReoprtsDto.getLoginUserId()+"' "+memberStatusQuery+")) OR "
								+ " (pm.projects.id IN (select pah.projectId.id from ProjectApproverHierarchy pah where pah.approverUserId = '"+dayWiseTimesheetReoprtsDto.getLoginUserId()+"' and pah.projectId.id IN (:projectIds))) ) ";
				
			}
			
		}else{
			if(dayWiseTimesheetReoprtsDto.getLoginUserRoleType().equalsIgnoreCase("ROLE_ADMIN") && StringUtils.isNoneBlank(dayWiseTimesheetReoprtsDto.getIsGlobalUser()) && 
					dayWiseTimesheetReoprtsDto.getIsGlobalUser().equalsIgnoreCase(Constants.YES)) {
				loginUserRoleQuery += " and pm.projectMemberCandidteId.consultantUser.id IN (select urole.user.id from UserRole urole where urole.orgLocations.org.id = '"+dayWiseTimesheetReoprtsDto.getOrgId()+"'  "+memberStatusQuery+") ";
			}else {
				loginUserRoleQuery += " and pm.projectMemberCandidteId.consultantUser.id IN (select urole.user.id from UserRole urole where urole.orgLocations.id = '"+dayWiseTimesheetReoprtsDto.getLocationsId()+"'  "+memberStatusQuery+") ";
			}
		}
		
		String employmentTypeQuery = "";
		String query= "";
		if(dayWiseTimesheetReoprtsDto.getEmploymentType()!= null && !dayWiseTimesheetReoprtsDto.getEmploymentType().isEmpty()){
			employmentTypeQuery = " and can.designationType IN (:empType) and can.orgLocationId.id = '"+dayWiseTimesheetReoprtsDto.getLocationsId()+"'";
		}
	
		String projectQuery = "";
		if(dayWiseTimesheetReoprtsDto.getProjectIds()!= null && !dayWiseTimesheetReoprtsDto.getProjectIds().isEmpty()){
			projectQuery = "  projects.id  IN (:projectIds)";
		}
		
		String groupByCandidateId = " group by pm.masterCandidateId.id";
		
		
		Session session = this.sessionFactory.getCurrentSession();
		
		if(dayWiseTimesheetReoprtsDto.getTypeOfProject().equalsIgnoreCase("internal") ) {
			query= " from ProjectMembers pm left join fetch pm.masterCandidateId mc left join fetch pm.projectMemberCandidteId can left join fetch pm.projectClientsId pc left join fetch pc.orgLocationsId loc left join fetch pm.projects projects "
					+" where " 
					+projectQuery
					  +dateQuery
					  +employmentTypeQuery
					  +loginUserRoleQuery
					  +groupByCandidateId;
		}else if(dayWiseTimesheetReoprtsDto.getTypeOfProject().equalsIgnoreCase("client")){
			 query= " from ProjectMembers pm left join fetch pm.masterCandidateId mc left join fetch pm.projectMemberCandidteId can left join fetch pm.projectClientsId pc left join fetch pc.orgLocationsId loc left join fetch pm.projects projects "
					+" where "
					 +projectQuery
					  +dateQuery
					  +employmentTypeQuery
					  +loginUserRoleQuery
					  +groupByCandidateId;
		}else {
			query= " from ProjectMembers pm left join fetch pm.masterCandidateId mc left join fetch pm.projectMemberCandidteId can left join fetch pm.projectClientsId pc left join fetch pc.orgLocationsId loc left join fetch pm.projects projects "
					+" where " 
					+projectQuery
					  +dateQuery
					  +employmentTypeQuery
					  +loginUserRoleQuery
					  +groupByCandidateId;
		}
		try {
			Query queryObj = session.createQuery(query);
			if(StringUtils.isNotEmpty(dayWiseTimesheetReoprtsDto.getStartDate())&&StringUtils.isNotEmpty(dayWiseTimesheetReoprtsDto.getEndDate())) {
				queryObj.setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate);
			}
			
			if(dayWiseTimesheetReoprtsDto.getProjectIds()!= null && !dayWiseTimesheetReoprtsDto.getProjectIds().isEmpty()) {
				queryObj.setParameterList("projectIds", dayWiseTimesheetReoprtsDto.getProjectIds());
			}
			
			if(dayWiseTimesheetReoprtsDto.getEmploymentType()!= null && !dayWiseTimesheetReoprtsDto.getEmploymentType().isEmpty()) {
				queryObj.setParameterList("empType", dayWiseTimesheetReoprtsDto.getEmploymentType());
			}
			pmList =queryObj.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pmList;
	}
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<String> getProjectMembersTimesheetFrequenciesByOrgLocationsIdAndCandidateId(Long locationsId, Long candidateId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = " SELECT DISTINCT PROJ.INVOICE_FREQUENCY FROM PROJECTS PROJ"
			       + " LEFT JOIN PROJECT_CLIENTS PC ON PC.PROJECT_ID = PROJ.ID "
			       + " LEFT JOIN PROJECT_MEMBERS PM ON PM.PROJECT_ID = PROJ.ID  "
			       + "  WHERE "
			       + "  PC.ORG_LOCATIONS_ID = :locationsId"
			       + "  AND PM.ID IN(SELECT PMH.PROJECT_MEMBERS_ID FROM PROJECT_MEMBERS_HISTORY PMH WHERE PMH.PROJECT_TERMINATION_DATE IS NULL OR ( PMH.PROJECT_TERMINATION_DATE IS NOT NULL AND PMH.PROJECT_TERMINATION_DATE > PMH.PROJECT_ASSIGNED_DATE )) "
			       + "  AND PM.CANDIDATE_ID = :candidateId AND PM.STATUS !=0 ";
		List<String> projectMemberList =  session.createSQLQuery(query.toString()).setLong("locationsId", locationsId).setLong("candidateId", candidateId).list();
		return projectMemberList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<String> getProjectMembersTimesheetFrequenciesByOrgLocationsIdAndMasterCandidateId(Long locationsId,Long orgId, Long masterCandidateId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = " SELECT DISTINCT PROJ.INVOICE_FREQUENCY FROM PROJECTS PROJ"
			       + " LEFT JOIN PROJECT_CLIENTS PC ON PC.PROJECT_ID = PROJ.ID"
			       + " LEFT JOIN PROJECT_MEMBERS PM ON PM.PROJECT_ID = PROJ.ID"
			       + " LEFT JOIN CANDIDATE CAN ON CAN.ID = PM.CANDIDATE_ID"
			       + " LEFT JOIN ORG_LOCATIONS OL ON OL.ID = CAN.ORG_LOCATION_ID"
			       + " LEFT JOIN ORG org ON org.ID = OL.ORG_ID"
			       + "  WHERE "
			       + "  ((CAN.DESIGNATION_TYPE IS NOT NULL AND CAN.DESIGNATION_TYPE ='"+Constants.OFFSHORE_C2C+"' AND org.ID = :orgId ) OR ((CAN.DESIGNATION_TYPE IS  NULL OR (CAN.DESIGNATION_TYPE IS NOT NULL AND CAN.DESIGNATION_TYPE != '"+Constants.OFFSHORE_C2C+"')) AND  CAN.ORG_LOCATION_ID = :locationsId))"
			       + "  AND PM.ID IN(SELECT PMH.PROJECT_MEMBERS_ID FROM PROJECT_MEMBERS_HISTORY PMH WHERE PMH.PROJECT_TERMINATION_DATE IS NULL OR ( PMH.PROJECT_TERMINATION_DATE IS NOT NULL AND PMH.PROJECT_TERMINATION_DATE > PMH.PROJECT_ASSIGNED_DATE )) "
			       + "  AND PM.MASTER_CANDIDATE_ID = :masterCandidateId AND PM.STATUS !=0 ";
		List<String> projectMemberList =  session.createSQLQuery(query.toString()).setLong("locationsId", locationsId).setLong("orgId", orgId).setLong("masterCandidateId", masterCandidateId).list();
		return projectMemberList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getProjectMembersByOrgLocationsIdAndCandidateIdTimesheetFrequency(Long locationsId,Long orgId, Long candidateId,String timesheetFrequency) {
			 
		logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectMembers pm left join fetch pm.projectClientsId pc   "
				         + "  left join fetch pc.orgRelationId rl left join fetch rl.locationId "
				         + " left join fetch rl.responderLocationId "
				         + " left join fetch pm.employee "
				         + " left join fetch pm.masterCandidateId "
				         + " left join fetch pm.projectMemberCandidteId  "
				         + " where pm.status != 0 and pm.projects.invoiceFrequency = :timesheetFrequency and  "
				         + " pm.id IN(select pmh.projectMemberId.id  from ProjectMembersHistory pmh  where  pmh.projectTerminationDate IS NULL OR (pmh.projectTerminationDate IS NOT NULL and pmh.projectTerminationDate > pmh.projectAssignedDate)) "
				         + " and  (pm.projectMemberCandidteId.parentCandidateId.id = :candidateId OR pm.projectMemberCandidteId.id = :candidateId) and "
				         + " ((pm.projectMemberCandidteId.designationType IS NOT NULL and pm.projectMemberCandidteId.designationType ='"+Constants.OFFSHORE_C2C+"' and pc.orgLocationsId.org.id = :orgId) OR ((pm.projectMemberCandidteId.designationType IS  NULL OR (pm.projectMemberCandidteId.designationType IS  NOT NULL and pm.projectMemberCandidteId.designationType !='"+Constants.OFFSHORE_C2C+"')) and pc.orgLocationsId.id = :locationsId)) GROUP BY pm.projects.id";
		List<ProjectMembers> projectMemberList =  session.createQuery(query.toString()).setString("timesheetFrequency", timesheetFrequency).setLong("locationsId", locationsId).setLong("orgId", orgId).setLong("candidateId", candidateId).list();
		return projectMemberList;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllProjectMembersListByMasterCandidateId(Long masterCandidateId, Long projectId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectMembers pm left join fetch pm.projects left join fetch pm.masterCandidateId  "
				+ " left join fetch pm.projectMemberCandidteId cand left join fetch cand.orgLocationId where pm.projects.id = :projectId and "
				+ " pm.masterCandidateId.id = :masterCandidateId " ;
		List<ProjectMembers> allProjectsList =session.createQuery(query).setLong("masterCandidateId", masterCandidateId).setLong("projectId",projectId).list(); 
		return allProjectsList;
	}


	@Override
	@Transactional
	public List<ProjectMembers> getAssignedProjectMembersGroupListByProjectClientId(List<Long> projectClientsId)
			throws Exception {
		
		 Session session = this.sessionFactory.getCurrentSession();
		 List<ProjectMembers> pmList = null;
		 ArrayList<ProjectMembers> projectsMembersList = new ArrayList<ProjectMembers>();
       	 Query projectsMembersListQuery= session.createQuery("from ProjectMembers pm left join fetch pm.masterCandidateId left join fetch pm.projectMemberCandidteId cand "
					+ "left join fetch pm.projects left join fetch pm.employee emp left join fetch cand.consultantUser user  "
					+ "where pm.projectClientsId.id IN (:projectClientsId) group by concat(pm.projects.id,pm.masterCandidateId.id)");
       	 pmList = projectsMembersListQuery.setParameterList("projectClientsId", projectClientsId).list();
	        	 projectsMembersList.addAll(pmList);
	      
	        return projectsMembersList;
	
	}


	@Override
	@Transactional
	public boolean isProjectMembersExisted(Long projectId, Long masterCandidateId, Long projectClientId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from ProjectMembers pm left join fetch pm.masterCandidateId mc left join fetch pm.projectClientsId pc left join fetch pm.projects where  "
				+ "pm.projects.id = :projectId and pm.masterCandidateId.id = :masterCandidateId and pc.id = :projectClientId " ;
		List<ProjectMembers> pmList =session.createQuery(query).setLong("projectId", projectId).setLong("projectClientId", projectClientId).setLong("masterCandidateId", masterCandidateId).list(); 
		boolean flag = false;
		if(pmList != null && !pmList.isEmpty())  {
			flag = true; 
		}
		return flag;
	}


	@Override
	@Transactional
	public List<ProjectMembers> getAllActiveProjectsByUserIdAndOrgLocationId(Long userId, Long orgLocationId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();

		String query = "from ProjectMembers pm left join fetch pm.projects left join fetch pm.projectClientsId left join fetch pm.employee emp left join fetch pm.projectMemberCandidteId "
				+ " where pm.projectMemberCandidteId.id IN(select cand.id from Candidate cand where cand.consultantUser.id = :userId and cand.orgLocationId.id = :orgLocationId ) and pm.status <> '0' " ;
		
		List<ProjectMembers> allProjectsList =session.createQuery(query).setLong("userId", userId).setLong("orgLocationId",orgLocationId) 
			    .list();
		return allProjectsList;
	}


	@Override
	@Transactional
	public List<ProjectMembers> getProjectMembersListByCandidateIdAndProjectClientId(Long candidateId,
			List<Long> projectClientsId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String query = " from ProjectMembers pm left join fetch pm.projectMemberCandidteId left join fetch pm.projects where  "
				+ "pm.projectClientsId.id IN (:projectClientsId) and pm.projectMemberCandidteId.id = :candidateId and pm.status = '1' " ;
		
		List<ProjectMembers> allProjectsList =session.createQuery(query).setParameterList("projectClientsId", projectClientsId).setLong("candidateId",candidateId) 
			    .list();
		return allProjectsList;
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseList<MarginSheetReportDTO> getProjectMemeberByClientProjectAndOrglocation(Long orglocationId,Date[] searchDates,MarginSheetReportDTO marginSheetReport)
	{
		Session session = this.sessionFactory.getCurrentSession();
//		Query queryString = null;
		List<Object[]> marginSheetReportDTOList = null;
		ResponseList<MarginSheetReportDTO> marginSheetReportDTO = new ResponseList<>();
		java.sql.Timestamp fromDate1 = new java.sql.Timestamp(searchDates[0].getTime());
		java.sql.Timestamp toDate1 = new java.sql.Timestamp(searchDates[1].getTime());
		java.sql.Date fromDate = new java.sql.Date(fromDate1.getTime());
		java.sql.Date toDate = new java.sql.Date(toDate1.getTime());
		String dateQury = null;
		StringBuilder query1 = new StringBuilder("");
		String sortingQry = "";
		dateQury = " and DATE_FORMAT(pm.CREATED_DATE,'%Y-%m-%d') BETWEEN  '" + fromDate
				+ "' and '" + toDate + "'";
		
		
	     if(StringUtils.isNotEmpty(marginSheetReport.getSearchValue()) && marginSheetReport.getSearchValue() != null) {
			    query1.append(" and ((SELECT  CONCAT(user.FIRST_NAME,user.LAST_NAME)) like '%" + marginSheetReport.getSearchValue() + "%' ")
				.append("OR user.FIRST_NAME like '%" + marginSheetReport.getSearchValue() + "%' ")
				.append("OR user.LAST_NAME like '%" + marginSheetReport.getSearchValue() + "%' ")
				.append("OR ol.ORG_NAME like '%" + marginSheetReport.getSearchValue() + "%' ")
				.append("OR emp.EMPLOYEE_ID like '%" + marginSheetReport.getSearchValue() + "%' )");
/*			    if(sqlDate != null) {
			    	query1.append("OR userRole.updatedDate like '%" + sqlDate + "%' ");
			    	query1.append("OR userRole.user.id IN(select employee.user.id from Employees employee where employee.hireDate like '%" + sqlDate + "%')");
			    }*/
	    	 
	     }
		
         if (StringUtils.isNotBlank(marginSheetReport.getSortColumn())) {
             sortingQry = " order by " + marginSheetReport.getSortColumn() + " "
                     + marginSheetReport.getSortOrder();
         }
         
         //Clinet reated filter 
	        List<Long> clientIdsList = new ArrayList<>();
	        String clientSubQuery = "";
			if(marginSheetReport.getClientId() != null && !marginSheetReport.getClientId().isEmpty()){
				String[] client = marginSheetReport.getClientId().split(",");
				for(int i=0; i<client.length; i++){
					clientIdsList.add(Long.valueOf(client[i]));
				}
			}else {
				clientIdsList = null;
			}
			Map<String, Object> params = new HashMap<String,Object>();
    		if(clientIdsList != null) {
    			clientSubQuery = " and (orl.ID IN (:clientIdsList))";
				params.put("clientIdsList",clientIdsList);
    		}
	     
//		String 	query = "from  Projects ps where ps.id IN(select pc.projectId.id from  ProjectClients pc where pc.orgLocationsId = :orglocationId) and ps.isClientProject = 'Y' ";
		String 	query ="SELECT projects.ID as projectId, projects.PROJECT_NAME as projectName, emp.EMPLOYEE_ID as employeeId,(SELECT  CONCAT(user.FIRST_NAME,user.LAST_NAME)) as employeeName, pm.PAY_RATE as payRate,"
				+" pm.PAY_RATE_TYPE as payRateType, pm.PAY_RATE_CURRENCY as payRateCurrency, pm.CURRENT_BILL_RATE as billRate, pm.RATE_TYPE as billRateType,"
				+" pm.BILL_RATE_CURRENCY as billRateTypeCurrency, "
				+" ol.ORG_NAME as clientName, ol.ID as orglocationId, orl.ID as orgRelationId,projects.START_DATE as startDate, projects.END_DATE as endDate,emp.HIRE_DATE as associateJoinedDate"
				 +" from PROJECTS projects,PROJECT_CLIENTS pc,"
				+" PROJECT_MEMBERS pm,EMPLOYEE emp,USER user, ORG_RELATIONS orl,ORG_LOCATIONS ol"
				 +" where" 
				+" pc.PROJECT_ID =projects.ID and pc.ORG_LOCATIONS_ID= '" + orglocationId + " ' and projects.IS_CLIENT_PROJECT = 'Y'"
				+" and pc.PROJECT_ID = pm.PROJECT_ID and pm.EMPLOYEE_ID = emp.ID and emp.USER_ID = user.ID "
				+" and pc.ORG_RELATION_ID=orl.ID and orl.RESPONDER_LOCATION_ID = ol.ID" + query1 + clientSubQuery + dateQury + sortingQry;
//		List<ProjectMembers> allProjectsList =session.createSQLQuery(query).list();
//		  queryString =  session.createSQLQuery(query.toString());
		  Query queryObj = session.createSQLQuery(query);
  		for (String str : queryObj.getNamedParameters())
        {
  			if(str.equalsIgnoreCase("clientIdsList")){
  				queryObj.setParameterList(str, clientIdsList);
			}else{
				queryObj.setParameter( str, params.get( str ) );
			}
        }
//		  queryObj.setResultTransformer(Transformers.aliasToBean(MarginSheetReportDTO.class));
		  marginSheetReportDTOList = queryObj.list();
		  List<MarginSheetReportDTO> mt = new LinkedList<MarginSheetReportDTO>();
		  double totalPayRate=0;
		  double totalbilRate=0;
		  MarginSheetReportDTO mts=null;
		  for(Object[] mm:marginSheetReportDTOList){
			  mts= new MarginSheetReportDTO();
			  mts.setProjectId((BigInteger)mm[0]);		
			  mts.setEmployeeName(mm[3].toString());
			  mts.setProjectName(mm[1].toString());
			  
			  if (mm[4] !=null) {
				  mts.setPayRate((double)mm[4]);
			  } else {
				  mts.setPayRate(0);
			  }
			  if (mm[2] != null) {
				  mts.setEmployeeId(mm[2].toString());
			  } else {
				  mts.setEmployeeId("--");
			  }
			  mts.setPayRateType(mm[5].toString());
			  mts.setPayRateCurrency(mm[6].toString());
			  
			  if (mm[7] !=null) {
				  mts.setBillRate((double)mm[7]);  
			  }else {
				  mts.setBillRate(0);
			  }
			  mts.setBillRateType(mm[8].toString());
			  mts.setBillRateTypeCurrency(mm[9].toString());
			  mts.setClinetName(mm[10].toString());
			  mts.setOrglocationId((BigInteger)mm[11]);
			  mts.setOrgRelationId((BigInteger)mm[12]);
			  if(mm[14] !=null) {
			  mts.setProjectDuration(mm[13].toString() +"-"+mm[14].toString());
			  }else {
				  mts.setProjectDuration(mm[13].toString());  
			  }
			  if(mm[15] != null) {
			  mts.setAssociateJoinedDate(mm[15].toString());
			  }else {
				  mts.setAssociateJoinedDate("--");  
			  }
			  mts.setMarginRate((double)mm[7] - (double)mm[4] );
			  totalPayRate+=(double)mm[4];
			  totalbilRate+=(double)mm[7];
			  mt.add(mts);
		  }
		  if(totalPayRate != 0 && totalbilRate != 0){
//			  mts.setTotalPayRate(totalPayRate);
//			  mts.setTotalbilRate(totalbilRate);
			  mt.get(0).setTotalPayRate(totalPayRate);
			  mt.get(0).setTotalbilRate(totalbilRate);
			  mt.get(0).setTotalMarginRate(totalbilRate - totalPayRate);
		  }
	         int count = 0;
	            int filteredCount = 0;
	            if (mt != null && !mt.isEmpty()) {
	                count = queryObj.list().size();
	                filteredCount = queryObj.list().size();
	            }
	            marginSheetReportDTO.setRecordsTotal(count);
	            marginSheetReportDTO.setRecordsFiltered(filteredCount);
		  
		marginSheetReportDTO.setData(mt);
		return marginSheetReportDTO;
	}
	

	@Override
	@Transactional
	public ProjectMembers getProjectMembersByMasterCandiateIdAndProjectClientId(Long masterCandidateId,
			Long projectClientsId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from ProjectMembers pm left join fetch pm.projectMemberCandidteId left join fetch pm.projects where  "
				+ "pm.projectClientsId.id = :projectClientsId and pm.masterCandidateId.id = :masterCandidateId and pm.status = '1' " ;
		List<ProjectMembers> pmList =session.createQuery(query).setLong("projectClientsId", projectClientsId).setLong("masterCandidateId", masterCandidateId).list(); 
		ProjectMembers pm = null;
		if(pmList != null && !pmList.isEmpty())  {
			pm  = pmList.iterator().next();
		}
		return pm;  
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> getAllAvailableAssociates( Long orgLocationId) {
	Session session = this.sessionFactory.getCurrentSession();
	/*String query = "select * from Candidate candidate where candidate.orgLocationId = :orgLocationId";
	query += " and (case when candidate.parentCandidateId is null then (candidate.isAssociate) else (candidate.parentCandidateId.isAssociate) end) = 'Y'";*/

	String query = "SELECT c.ID, c.FIRST_NAME, c.MIDDLE_NAME, c.LAST_NAME FROM CANDIDATE c where c.ORG_LOCATION_ID = :orgLocationId and case when c.PARENT_CANDIDATE_ID is null then c.IS_ASSOCIATE else (select c1.IS_ASSOCIATE from CANDIDATE c1 where c1.ID=c.PARENT_CANDIDATE_ID) end = 'Y';";

	List<Object[]> list = session.createSQLQuery(query).setLong("orgLocationId", orgLocationId).list();
	return list;
	}
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectMembers getProjectMembersByMasterCandidateIdAndProjectClientId(Long masterCandidateId, Long projectClientsId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from ProjectMembers pm left join fetch pm.projectMemberCandidteId left join fetch pm.projects where  "
				+ "pm.projectClientsId.id = :projectClientsId and pm.masterCandidateId.id = :masterCandidateId and pm.status = '1' " ;
		List<ProjectMembers> pmList =session.createQuery(query).setLong("projectClientsId", projectClientsId).setLong("masterCandidateId", masterCandidateId).list(); 
		ProjectMembers pm = null;
		if(pmList != null && !pmList.isEmpty())  {
			pm  = pmList.iterator().next();
		}
		return pm;  
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllProjectsAssigendToCandidateIdAndStatus(Long candidateId,String status) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectMembers pm left join fetch pm.projects left join fetch pm.employee emp  where pm.status =:status and pm.projectMemberCandidteId.id = :candidateId)" ;
		List<ProjectMembers> allProjectsList =session.createQuery(query).setLong("candidateId", candidateId).setString("status",status).list(); 
		return allProjectsList;
	}



	
}

