package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
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
import com.bis.sourcelead.dao.TimesheetEntriesDao;
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
		List<ProjectMembers> projectMemberList = query.setLong("locationsId", locationsId).setLong("projectId", projectId).setCacheable(true).list();
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
		String query = " from ProjectMembers pm left join fetch pm.projects project left join fetch pm.projectMemberCandidteId can left join fetch pm.masterCandidateId mc "
				+ " left join fetch pm.projectClientsId pc left join fetch pc.orgLocationsId loc "
				+ "where pm.id = :id" ;
		List<ProjectMembers> pmList =session.createQuery(query).setLong("id", id).setCacheable(true).list(); 
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
        List<ProjectMembers> projectsMembersList = crit.setCacheable(true).list();
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
				                                        .setCacheable(true)
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
											.setCacheable(true)
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
											.setCacheable(true)
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
		List<ProjectMembers> pmList =session.createQuery(query).setLong("userId", userId).setLong("projectId", projectId).setCacheable(true).list(); 
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
		List<ProjectMembers> pmList =session.createQuery(query).setLong("empId", empId).setLong("projectId", projectId).setCacheable(true).list(); 
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
		List<ProjectMembers> pmList =session.createQuery(query).setLong("projectId", projectId).setCacheable(true).list(); 
		return pmList;  
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndEmpUserId(Long locationsId, Long empUserId) {
		logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		 
		String query = "from ProjectMembers pm left join fetch pm.projects pj left join fetch pj.orgRelationId rl left join fetch rl.locationId left join fetch rl.responderLocationId left join fetch pm.employee where pj.orgLocationsId = :orgLocationsId and  pm.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId ) ";
		List<ProjectMembers> projectMemberList =  session.createQuery(query.toString()).setLong("orgLocationsId", locationsId).setLong("userId", empUserId).setCacheable(true).list();
		return projectMemberList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndCandidateId(Long locationsId, Long candidateId) {
		logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectMembers pm left join fetch pm.projectClientsId pc   left join fetch pc.orgRelationId rl left join fetch rl.locationId left join fetch rl.responderLocationId left join fetch pm.employee left join fetch pm.masterCandidateId left join fetch pm.projectMemberCandidteId  where pm.status = '1' and pm.projectMemberCandidteId.id = :candidateId and  pc.orgLocationsId.id = :locationsId ";
		List<ProjectMembers> projectMemberList =  session.createQuery(query.toString()).setLong("locationsId", locationsId).setLong("candidateId", candidateId).setCacheable(true).list();
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
		List<ProjectMembers> projectMemberList =  session.createQuery(query.toString()).setLong("orgLocationsId", locationsId).setLong("userId", empUserId).setCacheable(true).list();
		return projectMemberList;
	}
	
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getProjectMembersListByTimesheetFrequencyOrgLocationsIdAndApproverUserId(String timesheetFrequency,Date startDate,Date endDate,Long clientId,String status,Long locationsId, Long approverUserId){
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
		String clientQuery = "";
		String statusQuery = "";
		String fillingOptionQuery = "";
		if(startDate != null && endDate != null){
			   dateQuery =  " and pm.id IN ( "
                + "    select pmh.projectMemberId.id  from ProjectMembersHistory pmh  where  "
                + "    ( pmh.projectTerminationDate IS NOT NULL and :startDate between pmh.projectAssignedDate and pmh.projectTerminationDate) OR "
                + "    ( pmh.projectTerminationDate IS NOT NULL and  :endDate between pmh.projectAssignedDate and pmh.projectTerminationDate) OR "
                + "    ( pmh.projectTerminationDate IS NULL and :endDate >= pmh.projectAssignedDate) "
                + "  )  ";
	     }
		if(clientId != null){
			clientQuery = " and projects.orgRelationId.id = :clientId ";
		}
	  
	     if(StringUtils.isNotEmpty(status) && !(status.equalsIgnoreCase("NEW") || status.equalsIgnoreCase("All"))){
			 statusQuery = " and pm.masterCandidateId.id IN(select sah.submissionId.masterCandidateId.id from SubmissionApproverHierarchy sah where sah.frequencyStartDate = :startDate and  sah.frequencyEndDate = :endDate and sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.getNameByValue(status)+"' )";
         }
	     else if(!(status.equalsIgnoreCase("All"))){
	    	 statusQuery = " and pm.masterCandidateId.id NOT IN(select sah.submissionId.masterCandidateId.id from SubmissionApproverHierarchy sah where sah.frequencyStartDate = :startDate and  sah.frequencyEndDate = :endDate and (sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL+"' OR sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' OR sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.REJECTED+"' OR sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.REOPENED+"') )";
	     }
	     fillingOptionQuery = " and ((pclient.id IN(select pc.id from ProjectClients pc where pc.level IN(select Max(subPm.projectClientsId.level) from ProjectMembers subPm where subPm.projects.id = projects.id and subPm.masterCandidateId.id = mc.id) and pc.projectId.id = projects.id)) OR ( pm.masterCandidateId.id IN(select sah.submissionId.masterCandidateId.id from SubmissionApproverHierarchy sah where sah.frequencyStartDate = :startDate and  sah.frequencyEndDate = :endDate and (sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL+"' OR sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' OR sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.REJECTED+"' OR sah.submissionApprovalStatus = '"+Submission_ApprovalStatus.REOPENED+"'))))";
		String queryStr = " from ProjectMembers pm left join fetch pm.projects projects  left join fetch projects.orgRelationId left join fetch projects.orgLocationsId left join fetch pm.masterCandidateId mc left join fetch pm.projectMemberCandidteId candidate left join fetch pm.projectClientsId pclient "
				+ " where projects.invoiceFrequency = :timesheetFrequency and candidate.orgLocationId.id = :locationsId "
				    +fillingOptionQuery
				    + statusQuery
				    +clientQuery
				    + dateQuery
					+ " and ( "
					+  "      (  projects.id IN ( select pah.projectId.id from ProjectApproverHierarchy pah  where pah.approverUserId.id =:userId and ((pah.level = '2' and candidate.consultantUser.id NOT IN(select subpah.approverUserId.id from ProjectApproverHierarchy subpah where subpah.projectId.id = pah.projectId.id and subpah.level = '2')) OR (pah.level = '1' and candidate.consultantUser.id NOT IN(select subpah.approverUserId.id from ProjectApproverHierarchy subpah where subpah.projectId.id = pah.projectId.id )))))"
					 + "       OR "+Constants.loginUserAsAdminForProjects+""
					+ "     ) ";
		
		
		Query query = session.createQuery(queryStr.toString());
		
		if(startDate != null && endDate != null){
			query.setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate);
		}
		if(clientId != null){
			query.setParameter("clientId", clientId);
		}
		
		@SuppressWarnings("unchecked")
		List<ProjectMembers> projectMemberList =  query.setString("timesheetFrequency", timesheetFrequency).setLong("locationsId", locationsId).setLong("userId", approverUserId).setCacheable(true).list();
		return projectMemberList;
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
		List<ProjectMembers> projectMemberList =  query.setLong("locationsId", locationsId).setLong("userId", approverUserId).setCacheable(true).list();
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
		List<ProjectMembers> projectMemberList =  query.setLong("locationsId", locationsId).setLong("userId", approverUserId).setCacheable(true).list();
		return projectMemberList;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndProjectIdAndStratDateAndEndDateAndStatus(
			 String status, String startDate, String endDate, Long projectId) {
		
		logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = "from ProjectMembers pm left join fetch pm.projects pj where  pm.projects.id = :projectId";
		List<ProjectMembers> projectMemberList =  session.createQuery(query.toString()).setLong("projectId",projectId).setCacheable(true).list();
		return projectMemberList;
		
	}


	/**
	 * This method for get all the project members by project id
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<ProjectMembers> getAllProjectMembersByProjectId(Long id) throws Exception {
		logger.debug("In ProjectMember getAllProjectMembersByProjectId method");
        Session session = this.sessionFactory.getCurrentSession();
        List<ProjectMembers> projectsMembersList = null;
		Query projectsMembersListQuery= session.createQuery("from ProjectMembers pm left join fetch pm.projects left join fetch pm.projectMemberCandidteId left join fetch pm.employee emp left join fetch emp.user where pm.projects.id = :id ");
		projectsMembersList=projectsMembersListQuery.setLong("id", id).setCacheable(true).list();
		return projectsMembersList;
        
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<ProjectMembers> getAllProjectMembersWithMasterCandidateIdByProjectIdAndLocationId(Long id,Long locationsId) throws Exception {
		logger.debug("In ProjectMember getAllProjectMembersByProjectId method");
        Session session = this.sessionFactory.getCurrentSession();
        List<ProjectMembers> projectsMembersList = null;
		Query projectsMembersListQuery= session.createQuery("from ProjectMembers pm left join fetch pm.projects left join fetch pm.projectMemberCandidteId left join fetch pm.masterCandidateId mc left join fetch pm.projectClientsId pc where pc.orgLocationsId.id = :locationsId and pm.projects.id = :id and pm.status = '1' ");
		projectsMembersList=projectsMembersListQuery.setLong("id", id).setLong("locationsId", locationsId).setCacheable(true).list();
		return projectsMembersList;
        
	}
	
	
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllTerminatedProjectMembersByProjectIdAndterminatedStatus(Long projectId,Primary isManuallyTerminated) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from ProjectMembers pm left join fetch pm.projects left join fetch pm.employee emp left join fetch emp.user where pm.projects.id = :projectId and pm.isManuallyTerminated = :isManuallyTerminated " ;
		List<ProjectMembers> pmList =session.createQuery(query).setLong("projectId", projectId).setParameter("isManuallyTerminated", isManuallyTerminated).setCacheable(true).list(); 
		return pmList;  
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Long> getAllProjectMembersIdsByemployeeId(Long userId) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select pm.id from ProjectMembers pm where pm.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId )" ;
		List<Long> idsList =session.createQuery(query).setInteger("userId", userId.intValue()) 
			    .setCacheable(true).list(); 
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
			    .setCacheable(true).list(); 
		return idsList;  
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllProjectsByUserId(Long userId,Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();

		String query = "from ProjectMembers pm left join fetch pm.projects left join fetch pm.employee emp left join fetch pm.projectMemberCandidteId "
				+ " where (pm.projectMemberCandidteId.id IN(select cand.id from Candidate cand where cand.consultantUser.id = :userId and cand.orgLocationId.id = :orgLocationId ) and pm.projects.projectMode <> 'Draft')" ;
		
		List<ProjectMembers> allProjectsList =session.createQuery(query).setLong("userId", userId).setLong("orgLocationId",orgLocationId).setCacheable(true) 
			    .list();
		return allProjectsList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllProjectMembersByProjectIdsList(List<Long> projectIds) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectMembers pm left join fetch pm.employee emp left join fetch emp.user user  where pm.projects.id IN (:projectIds) GROUP BY pm.employee" ;
		List<ProjectMembers> allProjectMembersList =session.createQuery(query).setParameterList("projectIds", projectIds).setCacheable(true) 
			    .list();
		return allProjectMembersList;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getAllProjectMembersCountByProjectId(Long id) {
		
		    Session session = this.sessionFactory.getCurrentSession();
		    String status = Constants.MEMBER_ACTIVE;
		    String query = "select count(*) from ProjectMembers pm  where pm.projects.id = :id and pm.status = :status";
		    List<Long> ProjectMembersCount = (List<Long>) session.createQuery(query).setLong("id", id).setString("status", status).setCacheable(true).list();
		    
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
	public List<ProjectMembers> getAllProjectsByUserIdAndStatus(Long userId, String status) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectMembers pm left join fetch pm.projects left join fetch pm.employee emp  where pm.status =:status and (pm.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId))" ;
		List<ProjectMembers> allProjectsList =session.createQuery(query).setLong("userId", userId).setString("status",status).setCacheable(true).list(); 
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
				                                      .setCacheable(true) 
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

		projectsMembersList=projectsMembersListQuery.setLong("projectId", projectId).setCacheable(true).list();
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
	                .setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).setCacheable(true).list();
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
		String query = "from ProjectMembers pm left join fetch pm.employee emp left join fetch emp.user user   left join fetch pm.projects proj where proj.orgLocationsId = :orglocationId";
		List<ProjectMembers> allProjectMembersList = session.createQuery(query).setLong("orglocationId", orglocationId).setCacheable(true).list();
		return allProjectMembersList;
	}



	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllProjectsbyEmployeeId(Long employeeId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectMembers pm left join fetch pm.employee emp where emp.id = :employeeId";
		List<ProjectMembers> allProjectMembersList = session.createQuery(query).setLong("employeeId", employeeId).setCacheable(true).list();
		return allProjectMembersList;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Long> getAllProjectMembersIdsByProjectId(Long projectId) throws Exception {
		
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select pm.id from ProjectMembers pm where pm.projects.id = :projectId" ;
		List<Long> idsList =session.createQuery(query).setLong("projectId", projectId) 
			    .setCacheable(true).list(); 
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


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllProjectMembersByProjectClientIdAndStatus(Long projectClientsId, String status)
			throws Exception {

		 Session session = this.sessionFactory.getCurrentSession();
	        List<ProjectMembers> projectsMembersList = null;
	        String[] tokens = status.split(",");
	        
	        String subQuery2 = "";
	     
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
	        
		Query projectsMembersListQuery= session.createQuery("from ProjectMembers pm left join fetch pm.projectMemberCandidteId cand "
				+ "left join fetch pm.projects left join fetch pm.employee emp left join fetch cand.consultantUser "
				+ "where pm.projectClientsId = :projectClientsId and pm.status = '1' "+ subQuery2 + "");

		projectsMembersList = projectsMembersListQuery.setLong("projectClientsId", projectClientsId).setCacheable(true).list();
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
					    .setCacheable(true).list(); 
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
		List<ProjectMembers> pmList =session.createQuery(query).setLong("candidateId", candidateId).setLong("projectId", projectId).setLong("orgLocationId", orgLocationId).setCacheable(true).list(); 
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
		List<ProjectMembers> allProjectsList =session.createQuery(query).setLong("masterCandidateId", masterCandidateId).setLong("projectId",projectId).setCacheable(true).list(); 
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
		List<ProjectMembers> pmList =session.createQuery(query).setLong("projectId", projectId).setLong("candidateId", candidateId).setCacheable(true).list(); 
		ProjectMembers pm = null;
		if(pmList != null && !pmList.isEmpty())  {
			pm  = pmList.iterator().next();
		}
		return pm;  
	}
	
	@Override
	@Transactional
	public ProjectMembers getClientProjectMemberByProjectMemberId(Long projectMemberId) throws Exception {
 		Session session = this.sessionFactory.getCurrentSession();
		String query = " from ProjectMembers pm  left join fetch pm.projectClientsId pc left join fetch pc.orgLocationsId loc left join fetch pm.masterCandidateId mc  left join fetch pm.projectMemberCandidteId pmc left join fetch pm.projects projects where  "
				+ " (projects.id IN( select subPm.projects.id from ProjectMembers subPm where subPm.id = :projectMemberId ) and mc.id IN( select subPm.masterCandidateId.id from ProjectMembers subPm where subPm.id = :projectMemberId )) and pc.level IN ( select MIN(subPm.projectClientsId.level) from ProjectMembers subPm where subPm.projectClientsId.orgLocationsId.org.orgType <> '"+Constants.GUEST+"' and subPm.masterCandidateId.id = pm.masterCandidateId.id and subPm.projects.id = pm.projects.id) and pm.status = '1' " ;
		List<ProjectMembers> pmList =session.createQuery(query).setLong("projectMemberId", projectMemberId).setCacheable(true).list(); 
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
		String query = " from ProjectMembers pm  left join fetch pm.projectClientsId pc left join fetch pc.orgLocationsId loc left join fetch pm.masterCandidateId mc  left join fetch pm.projectMemberCandidteId pmc left join fetch pmc.consultantUser consultantUser left join fetch pm.projects projects left join fetch pm.employee emp left join fetch emp.user user "
				+ " where  "
				+ " (projects.id IN( select subPm.projects.id from ProjectMembers subPm where subPm.id = :projectMemberId ) and mc.id IN( select subPm.masterCandidateId.id from ProjectMembers subPm where subPm.id = :projectMemberId )) and consultantUser IS NOT NULL  " ;
		List<ProjectMembers> pmList =session.createQuery(query).setLong("projectMemberId", projectMemberId).setCacheable(true).list(); 
		ProjectMembers pm = null;
		if(pmList != null && !pmList.isEmpty())  {
			pm  = pmList.iterator().next();
		}
		return pm;  
	}
	
	
	
}

