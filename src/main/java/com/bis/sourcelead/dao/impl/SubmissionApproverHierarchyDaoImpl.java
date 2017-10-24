package com.bis.sourcelead.dao.impl;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.dao.SubmissionApproverHierarchyDao;
import com.bis.sourcelead.dao.TimesheetEntriesDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.TimesheetFrequencyGeneratorUtil;
import com.bis.sourcelead.web.LoginSessionController;

@Repository
public class SubmissionApproverHierarchyDaoImpl implements SubmissionApproverHierarchyDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
	private ProjectMembersDao projectMembersDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	TimesheetEntriesDao timesheetEntriesDao;
	
	@Autowired
	private ProjectsDao projectsDao; 
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	TimesheetFrequencyGeneratorUtil timesheetFrequencyGeneratorUtil;
	
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
	
	

	private static final Logger logger = LoggerFactory.getLogger(SubmissionApproverHierarchyDaoImpl.class);
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy addSubmissionApproverHierarchy(
			SubmissionApproverHierarchy submissionApproverHierarchy) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(submissionApproverHierarchy);
		}
		catch(Exception exception)	{
			   StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"SUBMISSION_APPROVE_HIERARCHY"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return submissionApproverHierarchy;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy updateSubmissionApproverHierarchy(
			SubmissionApproverHierarchy submissionApproverHierarchy) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		if(submissionApproverHierarchy != null){
			 session.saveOrUpdate(submissionApproverHierarchy);
		}
		if(submissionApproverHierarchy == null){
			StringBuilder message= new StringBuilder(MessageFormat.format(statusUpdate,"SUBMISSION_APPROVE_HIERARCHY"));
			throw new SourceLeadObjectNotFoundException(message.toString());
		}
	   
	    return submissionApproverHierarchy;
	}

	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy getSubmissionApproverHierarchy(String id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Query submissionApproverHierarchyListQuery= session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId left join fetch sah.submissionId submission left join fetch submission.projectMembers  where sah.id = :id");
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy>  submissionApproverHierarchyList=submissionApproverHierarchyListQuery.setLong("id", Long.parseLong(id)).setCacheable(true).list();
		SubmissionApproverHierarchy sah = null;
		if(null != submissionApproverHierarchyList && !submissionApproverHierarchyList.isEmpty()){
			sah = submissionApproverHierarchyList.iterator().next();
		}
		return sah;
	}

	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean deleteSubmissionApproverHierarchy(String id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		boolean isDeleted = false;
		try{
			if (id != null) {
				SubmissionApproverHierarchy submissionApproverHierarchy = (SubmissionApproverHierarchy) session.get(SubmissionApproverHierarchy.class, id);
				submissionApproverHierarchy.setStatus("INACTIVE");;
				session.saveOrUpdate(submissionApproverHierarchy);
				isDeleted = true;
				logger.info("Successfully deleted..!");
			}
			else {
				isDeleted = false;
			}
		}catch(Exception e){
			StringBuilder message= new StringBuilder(MessageFormat.format(statusDelete,"SUBMISSION_APPROVE_HIERARCHY"));
			throw new SourceLeadObjectNotFoundException(message.toString());
		}
		
		return isDeleted;
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getAllSubmissionApproverHierarchies() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = null;
		Query submissionApproverHierarchyListQuery= session.createQuery("from SubmissionApproverHierarchy");
		submissionApproverHierarchyList=submissionApproverHierarchyListQuery.list();;
		return submissionApproverHierarchyList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployee(Long submissionId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		Query submissionApproverHierarchyListQuery= session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId left join fetch sah.submissionId where sah.submissionId.id = :submissionId");
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy>  submissionApproverHierarchyList=submissionApproverHierarchyListQuery.setLong("submissionId", submissionId).setCacheable(true).list();
		//return submissionApproverHierarchyList;
		
		Boolean isRejected = false; 
	    Boolean isAPPROVED = false; 
	    Boolean isSUBMITTED = false; 
	    Boolean isNEW = false;
	    Boolean isDRAFT = false;
	    SubmissionApproverHierarchy submissionApproverHierarchy = null;
		for(SubmissionApproverHierarchy sah : submissionApproverHierarchyList){
			
				if( sah.getSubmissionStatus() == Submission_Status.REJECTED){
					isRejected   = true;  
					submissionApproverHierarchy = sah;
				}
				else if(!isRejected  && sah.getSubmissionStatus() == Submission_Status.NEW){
					 isNEW   = true;  
					 submissionApproverHierarchy = sah;
				}
				else if(!isRejected && !isNEW &&  sah.getSubmissionStatus() == Submission_Status.DRAFT){
					 isDRAFT   = true;  
					 submissionApproverHierarchy = sah;
				}
				else if(!isRejected && !isNEW && !isDRAFT && sah.getSubmissionStatus() == Submission_Status.SUBMITTED){
					isSUBMITTED   = true;  
					submissionApproverHierarchy = sah;
				}
				else if(!isRejected && !isNEW && !isDRAFT && !isSUBMITTED && sah.getSubmissionStatus() == Submission_Status.APPROVED ){
					 isAPPROVED   = true;  
					 submissionApproverHierarchy = sah;
				}
		
	     }
		return submissionApproverHierarchy;
	}
	
	
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployeeByProjectMemberId(Date frequencyStartDate,Date frequencyEndDate,Long projectMemberId) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = new java.sql.Date(frequencyStartDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(frequencyEndDate.getTime());
		
		
		Query submissionApproverHierarchyListQuery= session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId left join fetch sah.submissionId submission  left join fetch submission.projectMembers pm where pm.id = :projectMemberId and sah.frequencyStartDate = :sqlStartDate and sah.frequencyEndDate = :sqlEndDate");
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy>  submissionApproverHierarchyList=submissionApproverHierarchyListQuery.setLong("projectMemberId", projectMemberId).setParameter("sqlStartDate", sqlStartDate).setParameter("sqlEndDate", sqlEndDate).setCacheable(true).list();
		//return submissionApproverHierarchyList;
		
		Boolean isRejected = false; 
	    Boolean isAPPROVED = false; 
	    Boolean isSUBMITTED = false; 
	    Boolean isNEW = false;
	    Boolean isDRAFT = false;
	    SubmissionApproverHierarchy submissionApproverHierarchy = null;
	    if(submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()){
	    	for(SubmissionApproverHierarchy sah : submissionApproverHierarchyList){
				
				if( sah.getSubmissionStatus() == Submission_Status.REJECTED){
					isRejected   = true;  
					submissionApproverHierarchy = sah;
				}
				else if(!isRejected  && sah.getSubmissionStatus() == Submission_Status.NEW){
					 isNEW   = true;  
					 submissionApproverHierarchy = sah;
				}
				else if(!isRejected && !isNEW &&  sah.getSubmissionStatus() == Submission_Status.DRAFT){
					 isDRAFT   = true;  
					 submissionApproverHierarchy = sah;
				}
				else if(!isRejected && !isNEW && !isDRAFT && sah.getSubmissionStatus() == Submission_Status.SUBMITTED){
					isSUBMITTED   = true;  
					submissionApproverHierarchy = sah;
				}
				else if(!isRejected && !isNEW && !isDRAFT && !isSUBMITTED && sah.getSubmissionStatus() == Submission_Status.APPROVED ){
					 isAPPROVED   = true;  
					 submissionApproverHierarchy = sah;
				}
	      }
	    }
	    else{
	    	 submissionApproverHierarchy = new SubmissionApproverHierarchy();
	    	 submissionApproverHierarchy.setSubmissionStatus(Submission_Status.getNameByValue("new"));
	    	 submissionApproverHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.getNameByValue("new"));
	    }
		
		return submissionApproverHierarchy;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployeeByProjectIdAndMasterCandidateId(Date frequencyStartDate,Date frequencyEndDate,Long projectId,Long masterCandidateId) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = new java.sql.Date(frequencyStartDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(frequencyEndDate.getTime());
		
		
		Query submissionApproverHierarchyListQuery= session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId left join fetch sah.submissionId submission  left join fetch submission.projectMembers pm left join fetch submission.projects pj left join fetch submission.masterCandidateId mc  where pj.id = :projectId and mc.id = :masterCandidateId and sah.frequencyStartDate = :sqlStartDate and sah.frequencyEndDate = :sqlEndDate");
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy>  submissionApproverHierarchyList=submissionApproverHierarchyListQuery.setLong("projectId", projectId).setLong("masterCandidateId", masterCandidateId).setParameter("sqlStartDate", sqlStartDate).setParameter("sqlEndDate", sqlEndDate).setCacheable(true).list();
		//return submissionApproverHierarchyList;
		
		Boolean isRejected = false; 
	    Boolean isAPPROVED = false; 
	    Boolean isSUBMITTED = false; 
	    Boolean isNEW = false;
	    Boolean isDRAFT = false;
	    SubmissionApproverHierarchy submissionApproverHierarchy = null;
	    if(submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()){
	    	for(SubmissionApproverHierarchy sah : submissionApproverHierarchyList){
				
				if( sah.getSubmissionStatus() == Submission_Status.REJECTED){
					isRejected   = true;  
					submissionApproverHierarchy = sah;
				}
				else if(!isRejected  && sah.getSubmissionStatus() == Submission_Status.NEW){
					 isNEW   = true;  
					 submissionApproverHierarchy = sah;
				}
				else if(!isRejected && !isNEW &&  sah.getSubmissionStatus() == Submission_Status.DRAFT){
					 isDRAFT   = true;  
					 submissionApproverHierarchy = sah;
				}
				else if(!isRejected && !isNEW && !isDRAFT && sah.getSubmissionStatus() == Submission_Status.SUBMITTED){
					isSUBMITTED   = true;  
					submissionApproverHierarchy = sah;
				}
				else if(!isRejected && !isNEW && !isDRAFT && !isSUBMITTED && sah.getSubmissionStatus() == Submission_Status.APPROVED ){
					 isAPPROVED   = true;  
					 submissionApproverHierarchy = sah;
				}
	      }
	    }
	    else{
	    	 submissionApproverHierarchy = new SubmissionApproverHierarchy();
	    	 submissionApproverHierarchy.setSubmissionStatus(Submission_Status.getNameByValue("new"));
	    	 submissionApproverHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.getNameByValue("new"));
	    }
		
		return submissionApproverHierarchy;
	}
	
	
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean getCronRemainderForEmployeeByProjectMemberId(Date frequencyStartDate,Date frequencyEndDate,Long projectMemberId) {
		
		Boolean flag = true; 
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = new java.sql.Date(frequencyStartDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(frequencyEndDate.getTime());
		
		Query submissionApproverHierarchyListQuery= session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId left join fetch sah.submissionId submission  left join fetch submission.projectMembers pm where pm.id = :projectMemberId and (sah.submissionStatus = 'SUBMITTED' OR sah.submissionStatus = 'APPROVED')  and  sah.frequencyStartDate = :sqlStartDate and sah.frequencyEndDate = :sqlEndDate");
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy>  submissionApproverHierarchyList=submissionApproverHierarchyListQuery.setLong("projectMemberId", projectMemberId).setParameter("sqlStartDate", sqlStartDate).setParameter("sqlEndDate", sqlEndDate).setCacheable(true).list();
		if(null != submissionApproverHierarchyList  && !submissionApproverHierarchyList.isEmpty()){
			flag = false;
		}
		return flag;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy getSubmissionLowLevelApproverHierarchyForEmployee(Long submissionId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		Query submissionApproverHierarchyListQuery= session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId left join fetch sah.submissionId where sah.submissionId.id = :submissionId and sah.hierarchyId.level IN(select min(subsah.hierarchyId.level) from SubmissionApproverHierarchy subsah where subsah.submissionId.id = :submissionId)");
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy>  submissionApproverHierarchyList=submissionApproverHierarchyListQuery.setLong("submissionId", submissionId).setCacheable(true).list();
		//return submissionApproverHierarchyList;
	    SubmissionApproverHierarchy submissionApproverHierarchy = null;
		if(submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()){
			submissionApproverHierarchy = submissionApproverHierarchyList.iterator().next();
	     }
		return submissionApproverHierarchy;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForApprover(Long submissionId,Long approverUserId) {
			
		Session session = this.sessionFactory.getCurrentSession();
		Query submissionApproverHierarchyListQuery= session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch sah.submissionId submission left join fetch submission.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch hierarchy.approverUserId left join fetch sah.submissionId where sah.submissionId.id = :submissionId and "
				+ " (hierarchy.approverUserId.id = :approverUserId "
				 + " OR ( :approverUserId NOT IN( select subsah.hierarchyId.approverUserId.id from SubmissionApproverHierarchy subsah where subsah.submissionId.id = :submissionId ) and "+Constants.loginUserAsAdminWithMaxHeirarchyLevel+"))"
				+ "");
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy>  submissionApproverHierarchyList=submissionApproverHierarchyListQuery.setLong("submissionId", submissionId).setLong("userId",approverUserId).setLong("approverUserId",approverUserId).list();
		SubmissionApproverHierarchy sah = null;
		if(submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()){
			sah = submissionApproverHierarchyList.iterator().next();
		}
	    return sah;
	
	}

	
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyForEmployeeIdAndStartDate(Long empId,
			Long approverUserId, Long submissionId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId submission left join fetch submission.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId  left join fetch pm.employee emp left join fetch emp.user where emp.id =:empId and sah.submissionId.id = :submissionId and "
				   /*
				   *  logged in user as approver in project_approver_hierarchy (Either Admins/project_managers/team leads ...etc)
				   */
			        + "    ((hierarchy.approverUserId.id = :approverUserId ) "
			        /*
					 *  logged in user as admin but not in project_approver_hierarchy(hint:logged in user is admin but not assigned to project as approver)
					 */
			        + " OR ( :approverUserId NOT IN( select subsah.hierarchyId.approverUserId.id from SubmissionApproverHierarchy subsah where subsah.submissionId.projectMembers.employee.id = :empId  and subsah.submissionId.id = :submissionId) and "+Constants.loginUserAsAdminWithMaxHeirarchyLevel+"))"
		      	+ ")";
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy> sahList = session.createQuery(query)
																	  .setLong("empId", empId)	
				                                                      .setLong("approverUserId",approverUserId)
				                                                      .setLong("userId",approverUserId)
				                                                      .setParameter("submissionId", submissionId).setCacheable(true).list();
		
		return sahList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void updateSubmissionStatusByProjectMemberIdStartDateAndEndDate(Long projectMemberId,Date startDate,Date endDate,String status) {
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if(startDate != null){
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		
		java.sql.Date sqlEndDate = null;
		if(endDate != null){
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		
		Query submissionApproverHierarchyListQuery= session.createQuery("from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee  emp  left join fetch emp.user where pm.id = :projectMemberId and  (ts.startDate between :startDate and :endDate OR ts.startDate = :startDate)");
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy>  submissionApproverHierarchyList=submissionApproverHierarchyListQuery.setLong("projectMemberId", projectMemberId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).setCacheable(true).list();
	    if(submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()){
	    	for(SubmissionApproverHierarchy sah :submissionApproverHierarchyList){
	    		if(status.equalsIgnoreCase("Draft")){
	    			sah.setSubmissionStatus(Submission_Status.DRAFT);
	    			sah.setSubmissionApprovalStatus(Submission_ApprovalStatus.NEW);
	    		}
	    		else if(status.equalsIgnoreCase("Submit")){
	    			sah.setSubmissionStatus(Submission_Status.SUBMITTED);
	    			sah.setSubmissionApprovalStatus(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL);
	    		}
	    		try {
	    			 session.saveOrUpdate(sah);
				} catch (Exception e) {
				}
	    	}
	    	
	    }
	
	
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void updateSubmissionStatusByProjectIdMasterCandidateIdStartDateAndEndDate(Long projectId,Long masterCandidateId,Date startDate,Date endDate,String status) {
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if(startDate != null){
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		
		java.sql.Date sqlEndDate = null;
		if(endDate != null){
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		
		Query submissionApproverHierarchyListQuery= session.createQuery("from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch ts.projects pj  left join fetch ts.masterCandidateId mc  left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee  emp  left join fetch emp.user where pj.id = :projectId and mc.id = :masterCandidateId and  (ts.startDate between :startDate and :endDate OR ts.startDate = :startDate)");
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy>  submissionApproverHierarchyList=submissionApproverHierarchyListQuery.setLong("projectId", projectId).setLong("masterCandidateId", masterCandidateId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).setCacheable(true).list();
	    if(submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()){
	    	for(SubmissionApproverHierarchy sah :submissionApproverHierarchyList){
	    		if(status.equalsIgnoreCase("Draft")){
	    			sah.setSubmissionStatus(Submission_Status.DRAFT);
	    			sah.setSubmissionApprovalStatus(Submission_ApprovalStatus.NEW);
	    		}
	    		else if(status.equalsIgnoreCase("Submit")){
	    			sah.setSubmissionStatus(Submission_Status.SUBMITTED);
	    			sah.setSubmissionApprovalStatus(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL);
	    		}
	    		try {
	    			 session.saveOrUpdate(sah);
				} catch (Exception e) {
				}
	    	}
	    	
	    }
	
	
	}
	
	
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public User updateSubmissionStatusByProjectMemberIdStartDateAndEndDateWithReOpenReason(Long projectMemberId,
			Date startDate, Date endDate,String reason,User loginUser, String type, String isSuperUser) {
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if(startDate != null){
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		
		java.sql.Date sqlEndDate = null;
		if(endDate != null){
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		
		Query submissionApproverHierarchyListQuery= session.createQuery("from  SubmissionApproverHierarchy sah left join fetch sah.submissionId submission left join fetch submission.masterCandidateId mc left join fetch submission.projects projects left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee  emp  left join fetch emp.user where project.id IN( select subPm.projects.id from ProjectMembers subPm where subPm.id = :projectMemberId ) and mc.id IN( select subPm.masterCandidateId.id from ProjectMembers subPm where subPm.id = :projectMemberId )  and  (ts.startDate between :startDate and :endDate OR ts.startDate = :startDate)");
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy>  submissionApproverHierarchyList=submissionApproverHierarchyListQuery.setLong("projectMemberId", projectMemberId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).setCacheable(true).list();
	    if(submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()){
	    	for(SubmissionApproverHierarchy sah :submissionApproverHierarchyList){
	    		if(null != type && "notify".equalsIgnoreCase(type) && null != isSuperUser && isSuperUser.equalsIgnoreCase(Constants.YES)){
	    			sah.setReopenReason(reason);
	    			sah.setReopenedUser(loginUser);
	    			sah.setSubmissionStatus(Submission_Status.DRAFT);
	    			sah.setSubmissionApprovalStatus(Submission_ApprovalStatus.NEW);
	    			sah.setReopenRejectedReason(null);
	    		}else{
	    			if(null != type && "notify".equalsIgnoreCase(type)){
		    			sah.setReopenReason(reason);
		    			sah.setReopenedUser(loginUser);
		    			sah.setReopenRejectedReason(null);
		    		}else if(null != type && "reject".equalsIgnoreCase(type)){
		    			sah.setReopenRejectedReason(reason);
		    			sah.setReopenReason(null);
		    		}else{
		    			sah.setSubmissionStatus(Submission_Status.DRAFT);
		    			sah.setSubmissionApprovalStatus(Submission_ApprovalStatus.NEW);
		    			sah.setReopenRejectedReason(null);
		    			sah.setReopenReason(null);
		    		}
	    		}
	    		try {
	    			 session.saveOrUpdate(sah);
				} catch (Exception e) {
					e.printStackTrace();
				}
	    	}
	    	return submissionApproverHierarchyList.get(0).getReopenedUser();
	    }else{
	    	return null;
	    }
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void updateApprovalStatusByApproverForProjectMemberId(Long projectMemberId,Long approverUserId, Date startDate, Date endDate, String status,String rejectedReason) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		
		User loginUser = userDao.getUserById(approverUserId);
		ProjectMembers projectMembers =projectMembersDao.getProjectMemberById(projectMemberId);
		/*
		 *  get submissionapproverhierarchy list by using empId and logged in user id as approver(either user may exist in project hierarchy  or user may be admin)
		 */
		String query = " from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId submission left join fetch submission.masterCandidateId mc left join fetch submission.projectMembers pm left join fetch submission.projects projects left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee emp left join fetch emp.user "
				+ " where projects.id =:projectId and mc.id = :masterCandidateId and  sah.frequencyStartDate =:startDate and  sah.frequencyEndDate =:endDate "
				        + "and ("
				           /*
						   *  logged in user as approver in project_approver_hierarchy (Either Admins/project_managers/team leads ...etc)
						   */
					        + "    hierarchy.approverUserId.id = :approverUserId  "
					        /*
							 *  logged in user as admin but not in project_approver_hierarchy(hint:logged in user is admin but not assigned to project as approver)
							 */
					          + " OR ( :approverUserId NOT IN( select subsah.hierarchyId.approverUserId.id from SubmissionApproverHierarchy subsah where subsah.submissionId.projects.id = :projectId and subsah.submissionId.masterCandidateId.id = :masterCandidateId  and subsah.submissionApprovalStatus != 'APPROVED' and subsah.frequencyStartDate = :startDate and subsah.frequencyEndDate = :endDate ) "
					        + " and "+ Constants.loginUserAsAdminWithMaxHeirarchyLevel + ")"
				            + ")";
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy> sahMainList = session.createQuery(query)
																	  .setLong("projectId", projectMembers.getProjects().getId())	
																	  .setLong("masterCandidateId", projectMembers.getMasterCandidateId().getId())	
																	  .setLong("approverUserId",approverUserId)
																	  .setLong("userId",approverUserId)
				                                                      .setParameter("startDate", sqlStartDate)
				                                                      .setParameter("endDate", sqlEndDate)
				                                                      .list();
		
	
		if(sahMainList != null && !sahMainList.isEmpty()){
	    	for(SubmissionApproverHierarchy sah :sahMainList){
	    		
	    		if(status!=null && status.equalsIgnoreCase("REJECTED")){
            	 	sah.setSubmissionApprovalStatus(Submission_ApprovalStatus.REJECTED);
            	 	sah.setSubmissionStatus(Submission_Status.REJECTED);
            	 	sah.setActionPerformedBy(""+loginUser.getFullName());
            	 	sah.setRejectedReason(rejectedReason);
            	 	sah.setReopenReason(null);
            	    sah.setReopenedUser(null);
            	    sah.setReopenRejectedReason(null);
            	 	
	             }else if(status!=null && status.equalsIgnoreCase("APPROVED")){
	            	 sah.setSubmissionApprovalStatus(Submission_ApprovalStatus.APPROVED);
	            	 sah.setSubmissionStatus(Submission_Status.APPROVED);
	            	 sah.setReopenReason(null);
	            	 sah.setReopenedUser(null);
	            	 sah.setReopenRejectedReason(null);
	            	 sah.setActionPerformedBy(""+loginUser.getFullName());
	             }
	    		 try {
	    			 updateSubmissionApproverHierarchy(sah);
				} catch (Exception e) {
				}
	    		 
	    		    			    		
	    		
	    		//Get Max Level in Current Location
	    		Query queryForMaxLevel = session.createQuery("select Max(sah.hierarchyId.level) from SubmissionApproverHierarchy sah  where  sah.submissionId.id = :submissionId and sah.hierarchyId.id = :hierarchyId");
	    		List<Integer> maxList = queryForMaxLevel.setLong("hierarchyId", sah.getHierarchyId().getId()).setLong("submissionId", sah.getSubmissionId().getId()).list();
	    		Integer maxLevel = 0;
	    		if(maxList !=null && !maxList.isEmpty()){
	    			maxLevel = maxList.iterator().next();
	    		}
		    		
	    		String queryForAllApprovers = " from SubmissionApproverHierarchy sah left join fetch "
	    				+ " sah.hierarchyId hierarchy left join fetch hierarchy.projectClientsId pc left join fetch pc.orgLocationsId loc left join fetch loc.org org  left join fetch hierarchy.approverUserId left join fetch sah.submissionId submission left join fetch submission.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user where sah.id <> :id and sah.submissionId = :submissionId";
	    		@SuppressWarnings("unchecked")
        		List<SubmissionApproverHierarchy> approversList = session.createQuery(queryForAllApprovers)
        																	  .setLong("submissionId", sah.getSubmissionId().getId())
        															 		  .setLong("id", sah.getId())
        				                                                      .list();
    		
            	 if(approversList != null && !approversList.isEmpty()){
            		for(SubmissionApproverHierarchy approver  :  approversList){
            			 if(status!=null && status.equalsIgnoreCase("REJECTED")){
	            				approver.setSubmissionApprovalStatus(Submission_ApprovalStatus.REJECTED);
	            				approver.setSubmissionStatus(Submission_Status.REJECTED);
	            				approver.setActionPerformedBy(" "+loginUser.getFullName());
	            				approver.setRejectedReason(rejectedReason);
	            				approver.setReopenReason("");
	            				approver.setReopenedUser(null);
	            				approver.setReopenRejectedReason("");
                    	  	
        	              }
            			 else if(status!=null && status.equalsIgnoreCase("APPROVED")){
            				 if(approver.getHeirarchyId().getProjectClientsId() != null && 
            					approver.getHeirarchyId().getProjectClientsId().getOrgLocationsId() != null && 
            				    approver.getHeirarchyId().getProjectClientsId().getOrgLocationsId().getOrg() != null && 
            				    approver.getHeirarchyId().getProjectClientsId().getOrgLocationsId().getOrg().getOrgType().equals(Constants.GUEST)) {
	            					 approver.setSubmissionApprovalStatus(Submission_ApprovalStatus.APPROVED);
	            	            	 approver.setSubmissionStatus(Submission_Status.APPROVED);
	            	            	 approver.setActionPerformedBy(" "+loginUser.getFullName());
	            	            	 approver.setReopenReason("");
	                 				 approver.setReopenedUser(null);
	                 				 approver.setReopenRejectedReason("");
            				 }
            				 else if((approver.getHeirarchyId().getProjectClientsId().getLevel() >=  sah.getHeirarchyId().getProjectClientsId().getLevel())
            					     || (approver.getHeirarchyId().getProjectClientsId().getLevel() ==  sah.getHeirarchyId().getProjectClientsId().getLevel() && sah.getHeirarchyId().getLevel()==(maxLevel))){
	            	            	 approver.setSubmissionApprovalStatus(Submission_ApprovalStatus.APPROVED);
	            	            	 approver.setSubmissionStatus(Submission_Status.APPROVED);
	            	            	 approver.setActionPerformedBy(" "+loginUser.getFullName());
	            	            	 approver.setReopenReason("");
	                 				 approver.setReopenedUser(null);
	                 				 approver.setReopenRejectedReason("");
                		     }
            			 }
            			 
            			 
            			
            			 
            	        try {
            	        	updateSubmissionApproverHierarchy(approver);
         				} catch (Exception e) {
         				}
            	   }
            	}
	      }
	    }
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy isThereAnyApproverChangesStatusForTheSubmission(Long submissionId,String approvalStatus) {
		Session session = this.sessionFactory.getCurrentSession();
		Query submissionApproverHierarchyListQuery= session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId left join fetch sah.submissionId where sah.submissionId.id = :submissionId and sah.submissionApprovalStatus = :approvalStatus");
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy>  submissionApproverHierarchyList=submissionApproverHierarchyListQuery.setLong("submissionId", submissionId).setString("approvalStatus", approvalStatus).setCacheable(true).list();
		SubmissionApproverHierarchy sah = null;
		if(submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()){
			sah = submissionApproverHierarchyList.iterator().next();
		}
		return sah;
	}
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Float getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(Long userId, Long projectId,
			String approvalStatus) {
		Session session = this.sessionFactory.getCurrentSession();
		String subQueryForUserIdChecking = " hierarchy.approverUserId.id = :userId ";
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp where "+subQueryForUserIdChecking+" and pm.projects.id = :projectId and  sah.submissionApprovalStatus = :submissionApprovalStatus)" ;
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy> tslist =session.createQuery(query.toString()).setLong("userId", userId).setLong("projectId",projectId).setString("submissionApprovalStatus", approvalStatus).setCacheable(true).list(); 
		Float totalHours = 0.0F;
		if(tslist !=null){
		   for (int i = 0; i < tslist.size(); i++) {
			   if(tslist.get(i).getSubmissionId().getTotalHours() != null && ! "".equalsIgnoreCase(tslist.get(i).getSubmissionId().getTotalHours())){
				   totalHours = totalHours + Float.parseFloat(tslist.get(i).getSubmissionId().getTotalHours());
			   }
		   }  
	   }
		return totalHours;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Float getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(Long userId, Long projectId,String submissionStatus) {
		Session session = this.sessionFactory.getCurrentSession();
		String subQueryForUserIdChecking = " hierarchy.approverUserId.id = :userId ";
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp where "+subQueryForUserIdChecking+" and pm.projects.id = :projectId and  sah.submissionStatus = :submissionStatus)" ;
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy> tslist =session.createQuery(query.toString()).setLong("userId", userId).setLong("projectId",projectId).setString("submissionStatus", submissionStatus).setCacheable(true).list(); 
		Float totalHours = 0.0F;
		if(tslist !=null){
		   for (int i = 0; i < tslist.size(); i++) {
			   if(tslist.get(i).getSubmissionId().getTotalHours() != null && ! "".equalsIgnoreCase(tslist.get(i).getSubmissionId().getTotalHours())){
				   totalHours = totalHours + Float.parseFloat(tslist.get(i).getSubmissionId().getTotalHours());
			   }
		   }  
	   }
		return totalHours;
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Float getTotalApprovedOrRejectedHoursByMasterCandidateIdAndProjectIdForEmployee(Long masterCandidateId, Long projectId,
			String approvalStatus) {
		Session session = this.sessionFactory.getCurrentSession();
		
		//Getting List for which employee  is Assigned
		String subQueryForList = " from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.projectClientsId pc left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts left join fetch ts.projects projects left join fetch ts.masterCandidateId mc left join fetch ts.projectMembers pm  where mc.id = :masterCandidateId and projects.id = :projectId ";
		
		//Getting Max Hierarchy Level for which employee is Assigned
		String subQueryForMaxLevel = " select MAX(subsah.hierarchyId.level) from  SubmissionApproverHierarchy subsah  where subsah.submissionId.masterCandidateId.id = mc.id and subsah.submissionId.projects.id = projects.id and subsah.hierarchyId.projectClientsId.level = '1' ";
		
		String query = subQueryForList +" and  ( hierarchy.level IN("+subQueryForMaxLevel+")) and pc.level = '1' and  sah.submissionApprovalStatus = :approvalStatus" ;
		List<SubmissionApproverHierarchy> tslist =session.createQuery(query.toString()).setLong("masterCandidateId", masterCandidateId).setLong("projectId",projectId).setString("approvalStatus", approvalStatus).setCacheable(true).list(); 
		Float totalHours = 0.0F;
		if(tslist !=null && !tslist.isEmpty()){
			for(SubmissionApproverHierarchy sah :tslist){
				if(sah.getSubmissionId() != null && sah.getSubmissionId().getTotalHours() != null){
					 totalHours = totalHours + Float.parseFloat(sah.getSubmissionId().getTotalHours());
				}
			}
	    }
        float tsHours = totalHours;
        float tsHoursValue = (int) tsHours;
        float tsMinutesValue = tsHours - tsHoursValue;
        float totalMinutes = (tsHoursValue* 60)+(tsMinutesValue*100);
     
        float actualHours = (int) (totalMinutes / 60); 
        float actualMinutes = (totalMinutes % 60)/100;
        String actualTimeString = String.format("%.2f", (actualHours+actualMinutes));
		return Float.parseFloat(actualTimeString);
	}
	
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Float getTotalDraftAndSubmittedHoursByMasterCandidateIdAndProjectIdForEmployee(Long masterCandidateId, Long projectId,
			String submissionStatus) {
        Session session = this.sessionFactory.getCurrentSession();
		
		//Getting List for which employee  is Assigned
		String subQueryForList = " from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.projectClientsId pc left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts left join fetch ts.projects projects left join fetch ts.projectMembers pm left join fetch ts.masterCandidateId mc  where mc.id = :masterCandidateId and projects.id = :projectId ";
		
		//Getting Max Hierarchy Level for which employee is Assigned
		String subQueryForMaxLevel = " select Max(subsah.hierarchyId.level) from  SubmissionApproverHierarchy subsah  where subsah.hierarchyId.projectClientsId.level = '1' and  subsah.submissionId.masterCandidateId.id = mc.id and subsah.submissionId.projects.id = projects.id ";
		
		String query = subQueryForList +" and  ( hierarchy.level IN("+subQueryForMaxLevel+"))   and pc.level = '1' and sah.submissionStatus = :submissionStatus" ;
		List<SubmissionApproverHierarchy> tslist =session.createQuery(query.toString()).setLong("masterCandidateId", masterCandidateId).setLong("projectId",projectId).setString("submissionStatus", submissionStatus).setCacheable(true).list(); 
		Float totalHours = 0.0F;
		if(tslist !=null && !tslist.isEmpty()){
			for(SubmissionApproverHierarchy sah :tslist){
				if(sah.getSubmissionId() != null && sah.getSubmissionId().getTotalHours() != null){
					 totalHours = totalHours + Float.parseFloat(sah.getSubmissionId().getTotalHours());
				}
			}
	    }
        float tsHours = totalHours;
        float tsHoursValue = (int) tsHours;
        float tsMinutesValue = tsHours - tsHoursValue;
        float totalMinutes = (tsHoursValue* 60)+(tsMinutesValue*100);
     
        float actualHours = (int) (totalMinutes / 60); 
        float actualMinutes = (totalMinutes % 60)/100;
        String actualTimeString = String.format("%.2f", (actualHours+actualMinutes));
		return Float.parseFloat(actualTimeString);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getTotalDraftAndSubmittedListByuserIdAndProjectIdForEmployee(Long userId,
			Long projectId, String submissionStatus) {
		 
	        Session session = this.sessionFactory.getCurrentSession();
	      //Getting List for which employee  is Assigned
			String subQueryForUserIdChecking = " (emp.id IN(select emp.id from Employees emp where emp.user.id = :userId)) ";
			String subQueryForList = " from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp where "+subQueryForUserIdChecking+" and pm.projects.id = :projectId ";
			
			//Getting Max Hierarchy Level for which employee is Assigned
			String subQueryForUserIdCheckingInMaxLevel = " (subsah.submissionId.projectMembers.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId)) ";
			String subQueryForMaxLevel = " select Min(subsah.hierarchyId.level) from  SubmissionApproverHierarchy subsah  where "+subQueryForUserIdCheckingInMaxLevel+" and pm.projects.id = :projectId ";
			
			String query = subQueryForList +" and  ( hierarchy.level IN("+subQueryForMaxLevel+"))  and  sah.submissionStatus = :submissionStatus " ;
			List<SubmissionApproverHierarchy> tslist =session.createQuery(query.toString()).setLong("userId", userId).setLong("projectId",projectId).setString("submissionStatus", submissionStatus).setCacheable(true).list(); 
			
			return tslist;
	}
	
	
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getTotalApprovedOrRejectedListByuserIdAndProjectIdForEmployee(Long userId, Long projectId,
			String approvalStatus) {
		Session session = this.sessionFactory.getCurrentSession();
		
		//Getting List for which employee  is Assigned
		String subQueryForUserIdChecking = " (emp.id IN(select emp.id from Employees emp where emp.user.id = :userId)) ";
		String subQueryForList = " from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp where "+subQueryForUserIdChecking+" and pm.projects.id = :projectId ";
		
		//Getting Max Hierarchy Level for which employee is Assigned
		String subQueryForUserIdCheckingInMaxLevel = " (subsah.submissionId.projectMembers.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId)) ";
		String subQueryForMaxLevel = " select MAX(subsah.hierarchyId.level) from  SubmissionApproverHierarchy subsah  where "+subQueryForUserIdCheckingInMaxLevel+" and pm.projects.id = :projectId ";
		
		String query = subQueryForList +" and  ( hierarchy.level IN("+subQueryForMaxLevel+"))  and  sah.submissionApprovalStatus = :approvalStatus " ;
		List<SubmissionApproverHierarchy> tslist =session.createQuery(query.toString()).setLong("userId", userId).setLong("projectId",projectId).setString("approvalStatus", approvalStatus).setCacheable(true).list(); 
		
		return tslist;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getTotalApprovedOrRejectedListByuserIdAndProjectIdForApprover(Long userId,
			Long projectId, String approvalStatus) {
		
				Session session = this.sessionFactory.getCurrentSession();
				String subQueryForUserIdChecking = " hierarchy.approverUserId.id = :userId ";
				String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp where "+subQueryForUserIdChecking+" and pm.projects.id = :projectId and  sah.submissionStatus = :approvalStatus)" ;
				@SuppressWarnings("unchecked")
				List<SubmissionApproverHierarchy> tslist =session.createQuery(query.toString()).setLong("userId", userId).setLong("projectId",projectId).setString("approvalStatus", approvalStatus).setCacheable(true).list(); 

		return 	tslist;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getTotalDraftAndSubmittedListByuserIdAndProjectIdForApprover(Long userId,
			Long projectId, String submissionStatus) {
		
		Session session = this.sessionFactory.getCurrentSession();
		String subQueryForUserIdChecking = " hierarchy.approverUserId.id = :userId ";
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp where "+subQueryForUserIdChecking+" and pm.projects.id = :projectId and  sah.submissionStatus = :submissionStatus)" ;
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy> tslist =session.createQuery(query.toString()).setLong("userId", userId).setLong("projectId",projectId).setString("submissionStatus", submissionStatus).setCacheable(true).list(); 
		return 	tslist;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getTotalApprovedOrRejectedListByMasterCandidateIdForEmployeeLogin(Long masterCandidateId, String submissionStatus) {
		
        Session session = this.sessionFactory.getCurrentSession();
      		
		//Getting List for which employee  is Assigned
		String subQueryForList = " from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy  left join fetch hierarchy.projectClientsId pc left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts left join fetch ts.projects projects left join fetch ts.masterCandidateId mc left join fetch ts.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user ";
		
		//Getting Max Hierarchy Level for which employee is Assigned
		String subQueryForMaxLevel = " select MAX(subsah.hierarchyId.level) from  SubmissionApproverHierarchy subsah  where subsah.submissionId.id = ts.id and subsah.hierarchyId.projectClientsId.level = '1' ";
		
		String query = subQueryForList +"  where mc.id = :masterCandidateId   and ( hierarchy.level IN("+subQueryForMaxLevel+")) and  sah.submissionApprovalStatus = :submissionStatus and pc.level = '1' GROUP BY ts.id" ;
		List<SubmissionApproverHierarchy> tslist =session.createQuery(query.toString()).setLong("masterCandidateId", masterCandidateId).setString("submissionStatus", submissionStatus).setCacheable(true).list(); 
		
		return tslist;
		
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getTotalDraftAndSubmittedListByMasterCandidateIdForEmployeeLogin(Long masterCandidateId, String submissionStatus) {
		Session session = this.sessionFactory.getCurrentSession();
		
		//Getting Max Hierarchy Level for which employee is Assigned
		String subQueryForMaxLevel = " select MAX(subsah.hierarchyId.level) from  SubmissionApproverHierarchy subsah  where subsah.submissionId.id = ts.id and subsah.hierarchyId.projectClientsId.level = '1' ";
				
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy  left join fetch hierarchy.projectClientsId pc "
				+ " left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts left join fetch ts.projects projects "
				+ " left join fetch ts.masterCandidateId mc left join fetch ts.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user"
				+ "  where mc.id = :masterCandidateId   and ( hierarchy.level IN("+subQueryForMaxLevel+")) and  sah.submissionStatus = :submissionStatus  and pc.level = '1' GROUP BY ts.id" ;
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy> tslist =session.createQuery(query.toString()).setLong("masterCandidateId", masterCandidateId).setString("submissionStatus", submissionStatus).setCacheable(true).list(); 
		
		return tslist;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(
			Long userId, Long orgLocationId, String submissionStatus) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.projectClientsId pc left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projectMemberCandidteId can left join fetch pm.projects left join fetch pm.employee emp left join fetch ts.projects project left join fetch ts.masterCandidateId mc "
				+ " where  pc.orgLocationsId.id = :orgLocationId and "
				+ " ( "
				    /*
				    *  logged in user as approver in project_approver_hierarchy (Either Admins/project_managers/team leads ...etc)
				    */
				+ "  ("
				+ "    hierarchy.approverUserId.id = :userId and "
				+ "      ("
				+ "        ( hierarchy.level = '2' and emp.user.id NOT IN(select subsah.hierarchyId.approverUserId.id from SubmissionApproverHierarchy subsah where subsah.hierarchyId.projectClientsId.orgLocationsId.id = pc.orgLocationsId.id and subsah.submissionId.id = sah.submissionId.id and subsah.hierarchyId.level = '2')) "
				+ "        OR (hierarchy.level = '1' and emp.user.id NOT IN(select subsah.hierarchyId.approverUserId.id from SubmissionApproverHierarchy subsah where subsah.hierarchyId.projectClientsId.orgLocationsId.id = pc.orgLocationsId.id and subsah.submissionId.id = sah.submissionId.id) ) "
				+ "     ) "
				+ "   ) "
				    /*
					 *  logged in user as admin but not in project_approver_hierarchy(hint:logged in user is admin but not assigned to project as approver)
					 */
				+ "  OR ( :userId NOT IN( select subsah.hierarchyId.approverUserId.id from SubmissionApproverHierarchy subsah where subsah.hierarchyId.projectClientsId.orgLocationsId.id = pc.orgLocationsId.id and subsah.submissionId.id = sah.submissionId.id ) and "+Constants.loginUserAsAdminWithMaxHeirarchyLevel+") "
				+ "   ) "
				
				+ " and  sah.submissionStatus = :submissionStatus and sah.submissionId.status = '1'  GROUP BY CONCAT(pm.id,sah.frequencyStartDate,sah.frequencyEndDate)order by pm.projects.id ASC)" ;
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy> tslist =session.createQuery(query.toString()).setLong("userId", userId).setLong("orgLocationId",orgLocationId).setString("submissionStatus", submissionStatus).setCacheable(true).list(); 
		return tslist;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyBySubmissionId(Long submissionId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		Query submissionApproverHierarchyListQuery= session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp  where sah.submissionId.id = :submissionId");
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy>  submissionApproverHierarchyList=submissionApproverHierarchyListQuery.setLong("submissionId", submissionId).setCacheable(true).list();
		return submissionApproverHierarchyList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyByApproverUserId(Long approverUserId) {
		Session session = this.sessionFactory.getCurrentSession();
		Query submissionApproverHierarchyListQuery= session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId approver left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp  where approver.id = :approverUserId");
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy>  submissionApproverHierarchyList=submissionApproverHierarchyListQuery.setLong("approverUserId", approverUserId).setCacheable(true).list();
		return submissionApproverHierarchyList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void saveAllSubmissionApproverHierarchy(
			List<SubmissionApproverHierarchy> submissionApproverHierarchyList) throws Exception {
		
		if(submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()){
			for(SubmissionApproverHierarchy sah :submissionApproverHierarchyList){
				addSubmissionApproverHierarchy(sah);
			}
		}
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy isThereAnyApproverChangesStatusForProjectMember(Long projectMemberId,Date frequencyStartDate,Date frequencyEndDate,String approvalStatus) {
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if(frequencyStartDate != null){
			sqlStartDate = new java.sql.Date(frequencyStartDate.getTime());
		}
		
		java.sql.Date sqlEndDate = null;
		if(frequencyEndDate != null){
			sqlEndDate = new java.sql.Date(frequencyEndDate.getTime());
		}
		
		Query submissionApproverHierarchyListQuery= session.createQuery("from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee  emp  left join fetch emp.user where pm.id = :projectMemberId and  sah.frequencyStartDate = :startDate and sah.frequencyEndDate = :endDate and sah.submissionApprovalStatus = :approvalStatus");
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy>  submissionApproverHierarchyList=submissionApproverHierarchyListQuery.setLong("projectMemberId", projectMemberId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).setString("approvalStatus", approvalStatus).setCacheable(true).list();
		SubmissionApproverHierarchy sah = null;
		if(submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()){
			sah = submissionApproverHierarchyList.iterator().next();
		}
		return sah;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy isThereAnyApproverChangesStatusForProjectAndMasterCandidateId(Long projectId,Long masterCandidateId,Date frequencyStartDate,Date frequencyEndDate,String approvalStatus) {
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if(frequencyStartDate != null){
			sqlStartDate = new java.sql.Date(frequencyStartDate.getTime());
		}
		
		java.sql.Date sqlEndDate = null;
		if(frequencyEndDate != null){
			sqlEndDate = new java.sql.Date(frequencyEndDate.getTime());
		}
		
		Query submissionApproverHierarchyListQuery= session.createQuery("from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm  left join fetch ts.projects pj left join fetch ts.masterCandidateId mc left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee  emp  left join fetch emp.user where pj.id = :projectId and mc.id = :masterCandidateId and sah.frequencyStartDate = :startDate and sah.frequencyEndDate = :endDate and sah.submissionApprovalStatus = :approvalStatus");
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy>  submissionApproverHierarchyList=submissionApproverHierarchyListQuery.setLong("projectId", projectId).setLong("masterCandidateId", masterCandidateId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).setString("approvalStatus", approvalStatus).setCacheable(true).list();
		SubmissionApproverHierarchy sah = null;
		if(submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()){
			sah = submissionApproverHierarchyList.iterator().next();
		}
		return sah;
	}
	
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(Long approverUserId,Long projectMemberId, Date startDate, Date endDate) throws Exception {
		ProjectMembers projectMembers =projectMembersDao.getProjectMemberById(projectMemberId);
		Projects projects = projectsDao.getProjectById(projectMembers.getProjects().getId());
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		java.sql.Timestamp tsStartDate = null;
		if(startDate != null){
			sqlStartDate = new java.sql.Date(startDate.getTime());
			tsStartDate = new java.sql.Timestamp(startDate.getTime());
		}
		
		java.sql.Date sqlEndDate = null;
		java.sql.Timestamp tsEndDate = null;
		if(endDate != null){
			sqlEndDate = new java.sql.Date(endDate.getTime());
			tsEndDate = new java.sql.Timestamp(endDate.getTime());
		}
		
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee  emp  left join fetch emp.user "
				+ " where  pm.id = :projectMemberId and (sah.frequencyStartDate = :startDate OR sah.frequencyEndDate = :endDate OR sah.frequencyStartDate BETWEEN :startDate and :endDate OR sah.frequencyEndDate BETWEEN :startDate and :endDate) and sah.submissionId.status = '1'"
				+ "  and ( "
				/*
				*  logged in user as approver in project_approver_hierarchy (Either Admins/project_managers/team leads ...etc)
				*/
				+ "  hierarchy.approverUserId.id = :userId "
				/*
				 *  logged in user as admin but not in project_approver_hierarchy(hint:logged in user is admin but not assigned to project as approver)
				 */
				+ "  OR ( :userId NOT IN( select subsah.hierarchyId.approverUserId.id from SubmissionApproverHierarchy subsah where subsah.submissionId.status = '1' and subsah.submissionId.projectMembers.id = pm.id  and (sah.frequencyStartDate = :startDate OR sah.frequencyEndDate = :endDate OR sah.frequencyStartDate BETWEEN :startDate and :endDate OR sah.frequencyEndDate BETWEEN :startDate and :endDate)) and "+Constants.loginUserAsAdminWithMaxHeirarchyLevel+") "
				+ "   ) ";
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList =session.createQuery(query.toString()).setLong("userId", approverUserId).setLong("projectMemberId", projectMemberId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).setCacheable(true).list(); 
	    SubmissionApproverHierarchy submissionApproverHierarchy = null;
	    
	    LinkedHashMap<Date, Submission_ApprovalStatus> weekApprovalStatusMap = new LinkedHashMap<>();
	
	    HashSet<SubmissionApproverHierarchy> semiMonthlyMidWeekList = new HashSet<>();
	    
	    LinkedHashMap<Date, Float> weekBillableHours = new LinkedHashMap<>();
	    LinkedHashMap<Date, Float> weekNonBillableHours = new LinkedHashMap<>();
	    LinkedHashMap<Date, Float> weekTotalHoursMap = new LinkedHashMap<>();
	   
	    if(submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()){
	    	int count = 0;
	    	for(SubmissionApproverHierarchy sah : submissionApproverHierarchyList){
	    		if(count == 0){
	    			submissionApproverHierarchy = sah;
	    		}
	    		if( (sah.getSubmissionId().getStartDate().equals(tsStartDate) &&  sah.getSubmissionId().getEndDate().before(tsEndDate))
	    		    || (sah.getSubmissionId().getStartDate().after(tsStartDate) &&  sah.getSubmissionId().getEndDate().before(tsEndDate))
	    			||	sah.getSubmissionId().getEndDate().equals(tsEndDate))   {
	    			
	    		    if(! sah.getSubmissionId().getEndDate().equals(tsEndDate)
	    					 && CalendarUtil.getNumberOfDays(sah.getSubmissionId().getStartDate(), sah.getSubmissionId().getEndDate())<6
	    					 ){
	    		    	semiMonthlyMidWeekList.add(sah);
	    		    	continue;
	    			 }
	    			 float weekTotalHours            = 0f;
	    			 float weekBillableTotalHours    = 0f;
	    			 float weekNonBillableTotalHours = 0f;
	    			
	    			 if(StringUtils.isNotEmpty(sah.getSubmissionId().getTotalHours())){
	    				 weekTotalHours=Float.parseFloat(sah.getSubmissionId().getTotalHours());
	    			 }
	    			 if(StringUtils.isNotEmpty(sah.getSubmissionId().getBillableHours())){
	    				 weekBillableTotalHours=Float.parseFloat(sah.getSubmissionId().getBillableHours());
	    			 }
	    			 if(StringUtils.isNotEmpty(sah.getSubmissionId().getNonBillableHours())){
	    				 weekNonBillableTotalHours=Float.parseFloat(sah.getSubmissionId().getNonBillableHours());
	    			 }
	    			 
	    			  
	    			 weekApprovalStatusMap.put(sah.getSubmissionId().getStartDate(),sah.getSubmissionApprovalStatus());
	    			
	    			 weekBillableHours.put(sah.getSubmissionId().getStartDate(),weekBillableTotalHours);
	    			 weekNonBillableHours.put(sah.getSubmissionId().getStartDate(),weekNonBillableTotalHours);
	    	         weekTotalHoursMap.put(sah.getSubmissionId().getStartDate(),weekTotalHours);
	    		}
	    		else{
	    			if(sah.getSubmissionId().getEndDate().equals(tsStartDate)
	    			   || (sah.getSubmissionId().getStartDate().before(tsStartDate) && sah.getSubmissionId().getEndDate().after(tsStartDate))){
	    				float weekTotalHours            = 0f;
		    			float weekBillableTotalHours    = 0f;
		    			float weekNonBillableTotalHours = 0f;
		    			 
	    				int dayOfWeek = CalendarUtil.getDayOfTheDate(startDate);
	    				Collection<TimesheetEntries> timesheetEntries = timesheetEntriesDao.getAllTimesheetEntriesBySubmissionId(sah.getSubmissionId().getId());
    					for(TimesheetEntries te : timesheetEntries){
    						if(te.getProjectTask()!= null && te.getProjectTask().getBillableType()!= null && te.getProjectTask().getBillableType().equalsIgnoreCase(Constants.TASK_NON_BILLABLE)){
    							weekNonBillableTotalHours = weekNonBillableTotalHours+getTotalHoursBasedOnDayOfTheWeekBasedOnStartDate(te, dayOfWeek);
    						}
    						else{
    							weekBillableTotalHours = weekBillableTotalHours+getTotalHoursBasedOnDayOfTheWeekBasedOnStartDate(te, dayOfWeek);
    						}
    					}
    					 weekTotalHours = weekBillableTotalHours+ weekNonBillableTotalHours;
    					
    					 weekApprovalStatusMap.put(startDate,sah.getSubmissionApprovalStatus());
    		    			
    					 weekBillableHours.put(startDate,weekBillableTotalHours);
    	    			 weekNonBillableHours.put(startDate,weekNonBillableTotalHours);
    	    	         weekTotalHoursMap.put(startDate,weekTotalHours);
    					
	    				
	    			}
	    			
	    			else{
	    				float weekTotalHours            = 0f;
		    			float weekBillableTotalHours    = 0f;
		    			float weekNonBillableTotalHours = 0f;
		    			 
	    				int dayOfWeek = CalendarUtil.getDayOfTheDate(endDate);
	    				Collection<TimesheetEntries> timesheetEntries = timesheetEntriesDao.getAllTimesheetEntriesBySubmissionId(sah.getSubmissionId().getId());
    					for(TimesheetEntries te : timesheetEntries){
    						if(te.getProjectTask()!= null && te.getProjectTask().getBillableType()!= null && te.getProjectTask().getBillableType().equalsIgnoreCase(Constants.TASK_NON_BILLABLE)){
    							weekNonBillableTotalHours = weekNonBillableTotalHours+getTotalHoursBasedOnDayOfTheWeekBasedOnEndDate(te, dayOfWeek);
    						}
    						else{
    							weekBillableTotalHours = weekBillableTotalHours+getTotalHoursBasedOnDayOfTheWeekBasedOnEndDate(te, dayOfWeek);
    						}
    					}
    					weekTotalHours = weekBillableTotalHours+ weekNonBillableTotalHours;
    					
    					 weekApprovalStatusMap.put(sah.getSubmissionId().getStartDate(),sah.getSubmissionApprovalStatus());
    		    			
    					 weekBillableHours.put(sah.getSubmissionId().getStartDate(),weekBillableTotalHours);
    	    			 weekNonBillableHours.put(sah.getSubmissionId().getStartDate(),weekNonBillableTotalHours);
    	    	         weekTotalHoursMap.put(sah.getSubmissionId().getStartDate(),weekTotalHours);
    					
	    			}
	    			
	    		}
	    		count++;
	        }
	    	
	    	if(semiMonthlyMidWeekList != null && !semiMonthlyMidWeekList.isEmpty()){
	    		 float weekTotalHours            = 0f;
    			 float weekBillableTotalHours    = 0f;
    			 float weekNonBillableTotalHours = 0f;
    			 Date weekStartDate = null;
    			 Boolean isRejected = false; 
			     Boolean isAPPROVED = false; 
			     Boolean isSUBMITTED = false; 
			     Boolean isNEW = false;
			     Submission_ApprovalStatus submissionStatus = null;
	    		 for(SubmissionApproverHierarchy sah :semiMonthlyMidWeekList){
	    			 /*if(CalendarUtil.getDayOfTheDate(sah.getSubmissionId().getStartDate()) == 2){*/
	    				 weekStartDate = sah.getSubmissionId().getStartDate();
	    			 /*}*/
	    			 if(sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.REJECTED){
	 					isRejected   = true;  
	 					submissionStatus = Submission_ApprovalStatus.REJECTED;
	 				}
	 				else if(!isRejected  && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.NEW){
	 					 isNEW   = true;  
	 					submissionStatus = Submission_ApprovalStatus.NEW;
	 				}
	 				else if(!isRejected && !isNEW  && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL){
	 					isSUBMITTED   = true;  
	 					submissionStatus = Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL;
	 				}
	 				else if(!isRejected && !isNEW  && !isSUBMITTED && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.APPROVED ){
	 					 isAPPROVED   = true;  
	 					submissionStatus = Submission_ApprovalStatus.APPROVED;
	 				 }
	    			 if(StringUtils.isNotEmpty(sah.getSubmissionId().getTotalHours())){
	    				 weekTotalHours=CommonUtil.getCombinedHours(weekTotalHours, Float.parseFloat(sah.getSubmissionId().getTotalHours()));
	    			 }
	    			 if(StringUtils.isNotEmpty(sah.getSubmissionId().getBillableHours())){
	    				 weekBillableTotalHours=CommonUtil.getCombinedHours(weekBillableTotalHours,Float.parseFloat(sah.getSubmissionId().getBillableHours()));
	    			 }
	    			 if(StringUtils.isNotEmpty(sah.getSubmissionId().getNonBillableHours())){
	    				 weekNonBillableTotalHours=CommonUtil.getCombinedHours(weekNonBillableTotalHours,Float.parseFloat(sah.getSubmissionId().getNonBillableHours()));
	    			 }
	    		 }
    			  
    			 weekApprovalStatusMap.put(weekStartDate,submissionStatus);
    			 weekBillableHours.put(weekStartDate,weekBillableTotalHours);
    			 weekNonBillableHours.put(weekStartDate,weekNonBillableTotalHours);
    	         weekTotalHoursMap.put(weekStartDate,weekTotalHours);
	    	}
	    	submissionApproverHierarchy.setWeekApprovalStatusMap(weekApprovalStatusMap);
	    	submissionApproverHierarchy.setWeekBillableHoursMap(weekBillableHours);
	    	submissionApproverHierarchy.setWeekNonBillableHoursMap(weekNonBillableHours);
	    	submissionApproverHierarchy.setWeekTotalHoursMap(weekTotalHoursMap);
	    	
			
	    }
	    else{
	    	submissionApproverHierarchy = new SubmissionApproverHierarchy();
	    	submissionApproverHierarchy.setTotalHours(0f);
	    	// DateFormat format = LoginSessionController.DATEFORMAT;
	    	 Map<String,String> tabFrequencyDates = timesheetFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(commonUtil.dateStrFromOrgDateFormatter(projects.getOrgLocationsId().getDateFormat(),startDate), commonUtil.dateStrFromOrgDateFormatter(projects.getOrgLocationsId().getDateFormat(),endDate),projects.getOrgLocationsId().getDateFormat());
	    	
             if(tabFrequencyDates != null && !tabFrequencyDates.isEmpty()){
            	tabFrequencyDates.forEach((k,v)->
            	{
            		try {
            			 weekApprovalStatusMap.put(commonUtil.orgLocationDateFormat(projects.getOrgLocationsId().getDateFormat(),k.replaceAll("/", "-")),Submission_ApprovalStatus.NEW);
	   	    			 weekBillableHours.put(commonUtil.orgLocationDateFormat(projects.getOrgLocationsId().getDateFormat(),k.replaceAll("/", "-")), 0f);
	   	    			 weekNonBillableHours.put(commonUtil.orgLocationDateFormat(projects.getOrgLocationsId().getDateFormat(),k.replaceAll("/", "-")), 0f);
	   	    	         weekTotalHoursMap.put(commonUtil.orgLocationDateFormat(projects.getOrgLocationsId().getDateFormat(),k.replaceAll("/", "-")), 0f);
					} catch (Exception e) {}
					
            	});
             }
             
            submissionApproverHierarchy.setWeekApprovalStatusMap(weekApprovalStatusMap);
 	    	submissionApproverHierarchy.setWeekBillableHoursMap(weekBillableHours);
 	    	submissionApproverHierarchy.setWeekNonBillableHoursMap(weekNonBillableHours);
 	    	submissionApproverHierarchy.setWeekTotalHoursMap(weekTotalHoursMap);
 	    	
	    }
	    LinkedHashMap<String,Date> previousMonthMap = CalendarUtil.getPreviousMonthStartDateAndEndDateFromGivenStartDateForTsReports(startDate);
	    Map<String,Float> previousMonthHours = getTotalHoursByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(approverUserId, projectMemberId, previousMonthMap.get("STARTDATE"), previousMonthMap.get("ENDDATE"));
	    submissionApproverHierarchy.setPreviousMonthBillableHours(previousMonthHours.get("MONTH_BILLABLE_HOURS"));
	    submissionApproverHierarchy.setPreviousMonthNonBillableHours(previousMonthHours.get("MONTH_NONBILLABLE_HOURS"));
	    submissionApproverHierarchy.setPreviousMonthTotalHours(previousMonthHours.get("MONTH_TOTAL_HOURS"));
	    
	    Map<String,Float> projectToDateHours =  getTotalHoursByProjectMemberIdStartDateAndEndDateForInvoice(projectMemberId, projectMembers.getProjects().getStartDate(), CommonUtil.currentDate());
	    submissionApproverHierarchy.setProjectToDateBillableHours(projectToDateHours.get("BILLABLE_HOURS"));
	    submissionApproverHierarchy.setProjectToDateNonBillableHours(projectToDateHours.get("NONBILLABLE_HOURS"));
	    submissionApproverHierarchy.setProjectToDateTotalHours(projectToDateHours.get("TOTAL_HOURS"));
	    
	    return submissionApproverHierarchy;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Map<String,Float> getTotalHoursByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(Long approverUserId,Long projectMemberId, Date startDate, Date endDate) throws Exception {
			
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		java.sql.Timestamp tsStartDate = null;
		if(startDate != null){
			sqlStartDate = new java.sql.Date(startDate.getTime());
			tsStartDate = new java.sql.Timestamp(startDate.getTime());
		}
		
		java.sql.Date sqlEndDate = null;
		java.sql.Timestamp tsEndDate = null;
		if(endDate != null){
			sqlEndDate = new java.sql.Date(endDate.getTime());
			tsEndDate = new java.sql.Timestamp(endDate.getTime());
		}
		
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee  emp  left join fetch emp.user "
				+ " where  pm.id = :projectMemberId and (sah.frequencyStartDate = :startDate OR sah.frequencyEndDate = :endDate OR sah.frequencyStartDate BETWEEN :startDate and :endDate OR sah.frequencyEndDate BETWEEN :startDate and :endDate) and sah.submissionId.status = '1'"
				+ "  and ( "
				/*
				*  logged in user as approver in project_approver_hierarchy (Either Admins/project_managers/team leads ...etc)
				*/
				+ "  hierarchy.approverUserId.id = :userId "
				/*
				 *  logged in user as admin but not in project_approver_hierarchy(hint:logged in user is admin but not assigned to project as approver)
				 */
				+ "  OR ( :userId NOT IN( select subsah.hierarchyId.approverUserId.id from SubmissionApproverHierarchy subsah where subsah.submissionId.status = '1' and subsah.submissionId.projectMembers.id = pm.id  and subsah.frequencyStartDate = :startDate and subsah.frequencyEndDate = :endDate) and "+Constants.loginUserAsAdminWithMaxHeirarchyLevel+") "
				+ "   ) ";
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList =session.createQuery(query.toString()).setLong("userId", approverUserId).setLong("projectMemberId", projectMemberId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).setCacheable(true).list(); 
	    LinkedHashMap<String, Float> monthTotals = new LinkedHashMap<>();
	    float monthTotalHours            = 0f;
		float monthBillableTotalHours    = 0f;
	    float monthNonBillableTotalHours = 0f;
	    if(submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()){
	    	for(SubmissionApproverHierarchy sah : submissionApproverHierarchyList){
	    		if( (sah.getSubmissionId().getStartDate().equals(tsStartDate) &&  sah.getSubmissionId().getEndDate().before(tsEndDate))
	    		    || (sah.getSubmissionId().getStartDate().after(tsStartDate) &&  sah.getSubmissionId().getEndDate().before(tsEndDate))
	    			||	sah.getSubmissionId().getEndDate().equals(tsEndDate))   {
	    			
	    			 float weekTotalHours            = 0f;
	    			 float weekBillableTotalHours    = 0f;
	    			 float weekNonBillableTotalHours = 0f;
	    			
	    			 if(StringUtils.isNotEmpty(sah.getSubmissionId().getTotalHours())){
	    				 weekTotalHours=Float.parseFloat(sah.getSubmissionId().getTotalHours());
	    			 }
	    			 if(StringUtils.isNotEmpty(sah.getSubmissionId().getBillableHours())){
	    				 weekBillableTotalHours=Float.parseFloat(sah.getSubmissionId().getBillableHours());
	    			 }
	    			 if(StringUtils.isNotEmpty(sah.getSubmissionId().getNonBillableHours())){
	    				 weekNonBillableTotalHours=Float.parseFloat(sah.getSubmissionId().getNonBillableHours());
	    			 }
	    			 
	    			 monthTotalHours = CommonUtil.getCombinedHours(monthTotalHours,weekTotalHours);
	    			 monthBillableTotalHours = CommonUtil.getCombinedHours(monthBillableTotalHours,weekBillableTotalHours);
	    			 monthNonBillableTotalHours = CommonUtil.getCombinedHours(monthNonBillableTotalHours,weekNonBillableTotalHours);
	    		}
	    		else{
	    			if(sah.getSubmissionId().getEndDate().equals(tsStartDate)
	    			   || (sah.getSubmissionId().getStartDate().before(tsStartDate) && sah.getSubmissionId().getEndDate().after(tsStartDate))){
	    				float weekTotalHours            = 0f;
		    			float weekBillableTotalHours    = 0f;
		    			float weekNonBillableTotalHours = 0f;
		    			 
	    				int dayOfWeek = CalendarUtil.getDayOfTheDate(startDate);
	    				Collection<TimesheetEntries> timesheetEntries = timesheetEntriesDao.getAllTimesheetEntriesBySubmissionId(sah.getSubmissionId().getId());
    					for(TimesheetEntries te : timesheetEntries){
    						if(te.getProjectTask()!= null && te.getProjectTask().getBillableType()!= null && te.getProjectTask().getBillableType().equalsIgnoreCase(Constants.TASK_NON_BILLABLE)){
    							weekNonBillableTotalHours = CommonUtil.getCombinedHours(weekNonBillableTotalHours,getTotalHoursBasedOnDayOfTheWeekBasedOnStartDate(te, dayOfWeek));
    						}
    						else{
    							weekBillableTotalHours = CommonUtil.getCombinedHours(weekBillableTotalHours,getTotalHoursBasedOnDayOfTheWeekBasedOnStartDate(te, dayOfWeek));
    						}
    					}
    					 weekTotalHours = CommonUtil.getCombinedHours(weekBillableTotalHours,weekNonBillableTotalHours);
    					 
    					 monthTotalHours = CommonUtil.getCombinedHours(monthTotalHours,weekTotalHours);
    	    			 monthBillableTotalHours = CommonUtil.getCombinedHours(monthBillableTotalHours,weekBillableTotalHours);
    	    			 monthNonBillableTotalHours = CommonUtil.getCombinedHours(monthNonBillableTotalHours,weekNonBillableTotalHours);
	    			}
	    			
	    			else{
	    				float weekTotalHours            = 0f;
		    			float weekBillableTotalHours    = 0f;
		    			float weekNonBillableTotalHours = 0f;
		    			 
		    			if(sah.getSubmissionId().getStartDate().before(endDate)){
		    				int dayOfWeek = CalendarUtil.getDayOfTheDate(endDate);
		    				Collection<TimesheetEntries> timesheetEntries = timesheetEntriesDao.getAllTimesheetEntriesBySubmissionId(sah.getSubmissionId().getId());
	    					for(TimesheetEntries te : timesheetEntries){
	    						if(te.getProjectTask()!= null && te.getProjectTask().getBillableType()!= null && te.getProjectTask().getBillableType().equalsIgnoreCase(Constants.TASK_NON_BILLABLE)){
	    							weekNonBillableTotalHours = CommonUtil.getCombinedHours(weekNonBillableTotalHours,getTotalHoursBasedOnDayOfTheWeekBasedOnEndDate(te, dayOfWeek));
	    						}
	    						else{
	    							weekBillableTotalHours = CommonUtil.getCombinedHours(weekBillableTotalHours,getTotalHoursBasedOnDayOfTheWeekBasedOnEndDate(te, dayOfWeek));
	    						}
	    					}
	    					 weekTotalHours = CommonUtil.getCombinedHours(weekBillableTotalHours,weekNonBillableTotalHours);
		    			}
    					monthTotalHours = CommonUtil.getCombinedHours(monthTotalHours,weekTotalHours);
   	    		  	    monthBillableTotalHours = CommonUtil.getCombinedHours(monthBillableTotalHours,weekBillableTotalHours);
   	    			    monthNonBillableTotalHours = CommonUtil.getCombinedHours(monthNonBillableTotalHours,weekNonBillableTotalHours);
    					
	    			}
	    			
	    		}
	        }
	    	
	    }
	    monthTotals.put("MONTH_TOTAL_HOURS", monthTotalHours);
	    monthTotals.put("MONTH_BILLABLE_HOURS", monthBillableTotalHours);
	    monthTotals.put("MONTH_NONBILLABLE_HOURS", monthNonBillableTotalHours);
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
		float weekhours = 0f;
		switch(flag){
		 case 1:  if(StringUtils.isNotEmpty(te.getSunHours())){
	                  String hours = te.getSunHours().replaceAll(":", ".");
	                  weekhours= Float.parseFloat(hours);
	               }
		             break;
	 	  case 2:  if(StringUtils.isNotEmpty(te.getMonHours())){
	 		          String hours = te.getMonHours().replaceAll(":", ".");
	 		         weekhours= Float.parseFloat(hours);
			       }
	 	             break;
	 		  
	 	  case 3:  if(StringUtils.isNotEmpty(te.getTueHours())){
	 		          String hours = te.getTueHours().replaceAll(":", ".");
	 		         weekhours= Float.parseFloat(hours);
			       }
	 	            break;
	 		  
		  case 4:  if(StringUtils.isNotEmpty(te.getWedHours())){
			           String hours = te.getWedHours().replaceAll(":", ".");
			           weekhours= Float.parseFloat(hours);
		            }
		            break;
		  case 5:  if(StringUtils.isNotEmpty(te.getThuHours())){
			           String hours = te.getThuHours().replaceAll(":", ".");
			           weekhours= Float.parseFloat(hours);
		            }
		             break;
		  case 6:  if(StringUtils.isNotEmpty(te.getFriHours())){
			            String hours = te.getFriHours().replaceAll(":", ".");
			            weekhours= Float.parseFloat(hours);
		            }
		            break;
	  
		  case 7:  if(StringUtils.isNotEmpty(te.getSatHours())){
			             String hours = te.getSatHours().replaceAll(":", ".");
			             weekhours= Float.parseFloat(hours);
				    }
		             break;
		  case 8:  if(StringUtils.isNotEmpty(te.getSunHours())){
              String hours = te.getSunHours().replaceAll(":", ".");
              weekhours= Float.parseFloat(hours);
           }
		            break;
			
			default: weekhours= 0f;
		}
		return weekhours;
	}
	
	/*@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLogin(Long approverUserId,Long projectMemberId, Date startDate, Date endDate) throws Exception {
		ProjectMembers projectMembers = projectMembersDao.getProjectMemberById(projectMemberId);
		Projects projects = projectsDao.getProjectById(projectMembers.getProjects().getId());
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if(startDate != null){
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		
		java.sql.Date sqlEndDate = null;
		if(endDate != null){
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee  emp  left join fetch emp.user "
				+ " where  pm.id = :projectMemberId and sah.frequencyStartDate = :startDate and sah.frequencyEndDate = :endDate and sah.submissionId.status = '1'"
				+ "  and ( "
				
				*  logged in user as approver in project_approver_hierarchy (Either Admins/project_managers/team leads ...etc)
				
				+ "  hierarchy.approverUserId.id = :userId "
				
				 *  logged in user as admin but not in project_approver_hierarchy(hint:logged in user is admin but not assigned to project as approver)
				 
				+ "  OR ( :userId NOT IN( select subsah.hierarchyId.approverUserId.id from SubmissionApproverHierarchy subsah where subsah.submissionId.status = '1' and subsah.submissionId.projectMembers.id = pm.id  and subsah.frequencyStartDate = :startDate and subsah.frequencyEndDate = :endDate) and "+Constants.loginUserAsAdminWithMaxHeirarchyLevel+") "
				+ "   ) ";
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList =session.createQuery(query.toString()).setLong("userId", approverUserId).setLong("projectMemberId", projectMemberId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).setCacheable(true).list(); 
		Boolean isRejected = false; 
	    Boolean isAPPROVED = false; 
	    Boolean isSUBMITTED = false; 
	    Boolean isNEW = false;
	    Boolean isREOPENED = false;
	    SubmissionApproverHierarchy submissionApproverHierarchy = null;
	    LinkedHashMap<Date, Float> weekTotalsMap = new LinkedHashMap<>();
	    if(submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()){
	    	for(SubmissionApproverHierarchy sah : submissionApproverHierarchyList){
	    		 if(StringUtils.isNotEmpty(sah.getSubmissionId().getTotalHours())){
	    			 float weekTotalHours =Float.parseFloat(sah.getSubmissionId().getTotalHours());
	    			 weekTotalsMap.put(sah.getSubmissionId().getStartDate(),weekTotalHours);
	    		 }
			     else{
			    	 weekTotalsMap.put(sah.getSubmissionId().getStartDate(),0f);
			     }
	    		 
				if(sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.REJECTED){
					isRejected   = true;  
					submissionApproverHierarchy = sah;
				}
				else if(!isRejected  && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.NEW){
					 isNEW   = true;  
					 submissionApproverHierarchy = sah;
				}
				else if(!isRejected && !isNEW  && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL){
					isSUBMITTED   = true;  
					submissionApproverHierarchy = sah;
				}
				else if(!isRejected && !isNEW  && !isSUBMITTED && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.APPROVED ){
					 isAPPROVED   = true;  
					 submissionApproverHierarchy = sah;
				}
				
				else if(!isRejected && !isNEW  && !isSUBMITTED && !isAPPROVED &&  sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.REOPENED){
					isREOPENED   = true;  
					 submissionApproverHierarchy = sah;
				}
		
	     }
			submissionApproverHierarchy.setWeekTotals(weekTotalsMap);
	    }
	    else{
	    	submissionApproverHierarchy = new SubmissionApproverHierarchy();
	    	submissionApproverHierarchy.setTotalHours(0f);
	    	 DateFormat format = projects.getOrgLocationsId().getLocFullFormDateFormatter();
	    	 Map<String,String> tabFrequencyDates = timesheetFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(format.format(startDate), format.format(endDate),projects.getOrgLocationsId().getDateFormat());
	    	
             if(tabFrequencyDates != null && !tabFrequencyDates.isEmpty()){
            	tabFrequencyDates.forEach((k,v)->
            	{
            		try {
            			weekTotalsMap.put(format.parse(k.replaceAll("/", "-")), 0f);
					} catch (Exception e) {}
					
            	});
             }
             submissionApproverHierarchy.setSubmissionStatus(Submission_Status.NEW);
             submissionApproverHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.NEW);
	     	 submissionApproverHierarchy.setWeekTotals(weekTotalsMap);
	    }
		
		return submissionApproverHierarchy;
	}
	*/
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyByMasterCandidateIdStartDateAndEndDateForApproverLogin(Long approverUserId,Long projectId,Long pmMasterCandidateId,Long locationsId,Date startDate, Date endDate) throws Exception {
		//ProjectMembers projectMembers = projectMembersDao.getProjectMemberById(projectMemberId);
		Projects projects = projectsDao.getProjectById(projectId);
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if(startDate != null){
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		
		java.sql.Date sqlEndDate = null;
		if(endDate != null){
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.projectClientsId pclient left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts left join fetch ts.masterCandidateId mc  left join fetch ts.projectMembers pm left join fetch ts.projects project left join fetch project.orgLocationsId left join fetch pm.employee  emp  left join fetch emp.user "
				+ " where pclient.orgLocationsId.id = :locationsId and  project.id = :projectId and mc.id = :masterCandidateId and sah.frequencyStartDate = :startDate and sah.frequencyEndDate = :endDate and sah.submissionId.status = '1'"
				+ "  and ( "
				/*
				*  logged in user as approver in project_approver_hierarchy (Either Admins/project_managers/team leads ...etc)
				*/
				+ "  hierarchy.approverUserId.id = :userId "
				/*
				 *  logged in user as admin but not in project_approver_hierarchy(hint:logged in user is admin but not assigned to project as approver)
				 */
				+ "  OR ( :userId NOT IN( select subsah.hierarchyId.approverUserId.id from SubmissionApproverHierarchy subsah where subsah.submissionId.status = '1' and subsah.hierarchyId.projectClientsId.orgLocationsId.id = :locationsId and subsah.submissionId.projects.id = project.id and  subsah.submissionId.masterCandidateId.id = mc.id and subsah.frequencyStartDate = :startDate and subsah.frequencyEndDate = :endDate) and "+Constants.loginUserAsAdminWithMaxHeirarchyLevel+") "
				+ "   ) ";
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList =session.createQuery(query.toString()).setLong("userId", approverUserId).setLong("locationsId", locationsId).setLong("projectId", projectId).setLong("masterCandidateId", pmMasterCandidateId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).setCacheable(true).list(); 
		Boolean isRejected = false; 
	    Boolean isAPPROVED = false; 
	    Boolean isSUBMITTED = false; 
	    Boolean isNEW = false;
	    Boolean isREOPENED = false;
	    SubmissionApproverHierarchy submissionApproverHierarchy = null;
	    LinkedHashMap<Date, Float> weekTotalsMap = new LinkedHashMap<>();
	    if(submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()){
	    	for(SubmissionApproverHierarchy sah : submissionApproverHierarchyList){
	    		 if(StringUtils.isNotEmpty(sah.getSubmissionId().getTotalHours())){
	    			 float weekTotalHours =Float.parseFloat(sah.getSubmissionId().getTotalHours());
	    			 weekTotalsMap.put(sah.getSubmissionId().getStartDate(),weekTotalHours);
	    		 }
			     else{
			    	 weekTotalsMap.put(sah.getSubmissionId().getStartDate(),0f);
			     }
	    		 
				if(sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.REJECTED){
					isRejected   = true;  
					submissionApproverHierarchy = sah;
				}
				else if(!isRejected  && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.NEW){
					 isNEW   = true;  
					 submissionApproverHierarchy = sah;
				}
				else if(!isRejected && !isNEW  && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL){
					isSUBMITTED   = true;  
					submissionApproverHierarchy = sah;
				}
				else if(!isRejected && !isNEW  && !isSUBMITTED && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.APPROVED ){
					 isAPPROVED   = true;  
					 submissionApproverHierarchy = sah;
				}
				
				else if(!isRejected && !isNEW  && !isSUBMITTED && !isAPPROVED &&  sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.REOPENED){
					isREOPENED   = true;  
					 submissionApproverHierarchy = sah;
				}
		
	     }
			submissionApproverHierarchy.setWeekTotals(weekTotalsMap);
	    }
	    else{
	    	submissionApproverHierarchy = new SubmissionApproverHierarchy();
	    	submissionApproverHierarchy.setTotalHours(0f);
	    	 DateFormat format = projects.getOrgLocationsId().getLocFullFormDateFormatter();
	    	 Map<String,String> tabFrequencyDates = timesheetFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(format.format(startDate), format.format(endDate),projects.getOrgLocationsId().getDateFormat());
	    	
             if(tabFrequencyDates != null && !tabFrequencyDates.isEmpty()){
            	tabFrequencyDates.forEach((k,v)->
            	{
            		try {
            			weekTotalsMap.put(format.parse(k.replaceAll("/", "-")), 0f);
					} catch (Exception e) {}
					
            	});
             }
             submissionApproverHierarchy.setSubmissionStatus(Submission_Status.NEW);
             submissionApproverHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.NEW);
	     	 submissionApproverHierarchy.setWeekTotals(weekTotalsMap);
	    }
		
		return submissionApproverHierarchy;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }) 
	public Map<String,Float> getTotalHoursByProjectMemberIdStartDateAndEndDateForInvoice(Long projectMemberId, Date startDate, Date endDate) throws Exception { 

		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if (startDate != null) {
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}

		java.sql.Date sqlEndDate = null;
		if (endDate != null) {
			sqlEndDate = new java.sql.Date(endDate.getTime());
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
				if ((sah.getSubmissionId().getStartDate().equals(startDate)
						&& sah.getSubmissionId().getEndDate().before(endDate))
						|| (sah.getSubmissionId().getStartDate().after(startDate)
								&& sah.getSubmissionId().getEndDate().before(endDate))
						|| sah.getSubmissionId().getEndDate().equals(endDate)) {

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
					if (sah.getSubmissionId().getEndDate().equals(startDate)
							|| (sah.getSubmissionId().getStartDate().before(startDate)
									&& sah.getSubmissionId().getEndDate().after(startDate))) {
						float weekTotalHours = 0f;
						float weekBillableTotalHours = 0f;
						float weekNonBillableTotalHours = 0f;

						int dayOfWeek = CalendarUtil.getDayOfTheDate(startDate);
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

	@Override
	@Transactional(rollbackFor = { Exception.class }) 
	public List<SubmissionApproverHierarchy> getApprovedSubmissionApproverHierarchiesByProjectIdFrequencyStartDateAndFrequencyEndDate(Long projectId, Date frequencyStartDate, Date frequencyEndDate,String invoiceType)
	{
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		String statusSubQuery ="";
		if(Constants.CLIENT_SOW.equalsIgnoreCase(invoiceType)){
			statusSubQuery = "sah.invoiceStatus IS NULL";
		}else{
			statusSubQuery = "sah.billingStatus IS NULL";
		}
				
		if (frequencyStartDate != null) {
			sqlStartDate = new java.sql.Date(frequencyStartDate.getTime());
		}

		java.sql.Date sqlEndDate = null;
		if (frequencyEndDate != null) {
			sqlEndDate = new java.sql.Date(frequencyEndDate.getTime());
		}

		// Getting Max Hierarchy Level for which employee is Assigned
		String subQueryForMaxLevel = " select MAX(subsah.hierarchyId.level) from SubmissionApproverHierarchy subsah where  subsah.submissionId.projectMembers.projects.id = :projectId ";
		String query = "from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts left join fetch ts.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee emp left join fetch emp.user "
				+ " where project.id = :projectId and sah.frequencyStartDate = :startDate and sah.frequencyEndDate = :endDate and "+statusSubQuery+" and sah.submissionId.status = '1'"
				/*
				 * For getting approved timesheet hours list
				 */

				+ " and ( hierarchy.level IN(" + subQueryForMaxLevel
				+ ")) order by pm.id";
		
		
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = session.createQuery(query.toString())
				.setLong("projectId", projectId)
				.setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).setCacheable(true).list();
		LinkedList<SubmissionApproverHierarchy> finalSahList = new LinkedList<>();
		if (submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()) {
			Long projectMemberId = 0l;
			int count = 0;
			SubmissionApproverHierarchy finalSah = null;
			for (SubmissionApproverHierarchy sah : submissionApproverHierarchyList) {
				if(projectMemberId == 0 || projectMemberId != sah.getSubmissionId().getProjectMembers().getId()){
					    if(projectMemberId != 0){
					    	finalSahList.add(finalSah);
					    	count = 0;
					    }
					    finalSah = sah;
					    projectMemberId = sah.getSubmissionId().getProjectMembers().getId();
					    float weekTotalHours = 0f;
						float weekBillableTotalHours = 0f;
						float weekNonBillableTotalHours = 0f;
						if(Submission_ApprovalStatus.APPROVED.getValue().equalsIgnoreCase(sah.getSubmissionApprovalStatus().toString())){
							if (StringUtils.isNotEmpty(sah.getSubmissionId().getTotalHours())) {
								weekTotalHours = Float.parseFloat(sah.getSubmissionId().getTotalHours());
							}
							if (StringUtils.isNotEmpty(sah.getSubmissionId().getBillableHours())) {
								weekBillableTotalHours = Float.parseFloat(sah.getSubmissionId().getBillableHours());
							}
							if (StringUtils.isNotEmpty(sah.getSubmissionId().getNonBillableHours())) {
								weekNonBillableTotalHours = Float.parseFloat(sah.getSubmissionId().getNonBillableHours());
							}
							float totalHours = 0f;
							float billableTotalHours = 0f;
							float nonBillableTotalHours = 0f;
	                        if(finalSah.getTotalHours() != null){
	                        	totalHours = finalSah.getTotalHours();
	                        }
	                        if(finalSah.getBillableHours() != null){
	                        	billableTotalHours = finalSah.getBillableHours();
	                        }
	                        if(finalSah.getNonBillableHours() != null){
	                        	nonBillableTotalHours = finalSah.getNonBillableHours();
	                        }
							finalSah.setTotalHours(CommonUtil.getCombinedHours(totalHours, weekTotalHours));
							finalSah.setBillableHours(CommonUtil.getCombinedHours(billableTotalHours, weekBillableTotalHours));
							finalSah.setNonBillableHours(CommonUtil.getCombinedHours(nonBillableTotalHours,weekNonBillableTotalHours));	
						}else{
							finalSah.setTotalHours(0f);
							finalSah.setBillableHours(0f);
							finalSah.setNonBillableHours(0f);	
						}
						count++;	
				 }
				 else{
					    if(finalSah == null)
					    continue;
					    
					    float weekTotalHours = 0f;
						float weekBillableTotalHours = 0f;
						float weekNonBillableTotalHours = 0f;
						if(Submission_ApprovalStatus.APPROVED.getValue().equalsIgnoreCase(sah.getSubmissionApprovalStatus().toString())){
							if (StringUtils.isNotEmpty(sah.getSubmissionId().getTotalHours())) {
								weekTotalHours = Float.parseFloat(sah.getSubmissionId().getTotalHours());
							}
							if (StringUtils.isNotEmpty(sah.getSubmissionId().getBillableHours())) {
								weekBillableTotalHours = Float.parseFloat(sah.getSubmissionId().getBillableHours());
							}
							if (StringUtils.isNotEmpty(sah.getSubmissionId().getNonBillableHours())) {
								weekNonBillableTotalHours = Float.parseFloat(sah.getSubmissionId().getNonBillableHours());
							}
							float totalHours = 0f;
							float billableTotalHours = 0f;
							float nonBillableTotalHours = 0f;
		                    if(finalSah.getTotalHours() != null){
		                     	totalHours = finalSah.getTotalHours();
		                    }
		                    if(finalSah.getBillableHours() != null){
		                     	billableTotalHours = finalSah.getBillableHours();
		                    }
		                    if(finalSah.getNonBillableHours() != null){
		                     	nonBillableTotalHours = finalSah.getNonBillableHours();
		                    }
							finalSah.setTotalHours(CommonUtil.getCombinedHours(totalHours, weekTotalHours));
							finalSah.setBillableHours(CommonUtil.getCombinedHours(billableTotalHours, weekBillableTotalHours));
							finalSah.setNonBillableHours(CommonUtil.getCombinedHours(nonBillableTotalHours,weekNonBillableTotalHours));
						}else{
							finalSah.setTotalHours(0f);
							finalSah.setBillableHours(0f);
							finalSah.setNonBillableHours(0f);
						}
						count++;
				 }
				
				
				
			}
			
			if(count>0){
				finalSahList.add(finalSah);
			}
			
		}
		
		return finalSahList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }) 
	public void updateAllSubmissionApproverHierarchyInvoiceStatusBySubmissionApproverHierarchyId(Long submissionHierarchyId,String invoiceType, String status) throws Exception {
		SubmissionApproverHierarchy sah = getSubmissionApproverHierarchy(submissionHierarchyId.toString());
		
		java.sql.Date sqlStartDate = null;
		if (sah.getFrequencyStartDate() != null) {
			sqlStartDate = new java.sql.Date(sah.getFrequencyStartDate().getTime());
		}

		java.sql.Date sqlEndDate = null;
		if (sah.getFrequencyEndDate() != null) {
			sqlEndDate = new java.sql.Date(sah.getFrequencyEndDate().getTime());
		}
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee  emp  left join fetch emp.user "
				+ " where  sah.frequencyStartDate = :sqlStartDate and sah.frequencyEndDate = :sqlEndDate and pm.id = :projectMemberId " ;
				
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy> sahList =session.createQuery(query.toString()).setParameter("sqlStartDate", sqlStartDate).setParameter("sqlEndDate",sqlEndDate).setLong("projectMemberId", sah.getSubmissionId().getProjectMembers().getId()).setCacheable(true).list(); 
		if(null != sahList && !sahList.isEmpty()){
			for(SubmissionApproverHierarchy subSah : sahList){
				if(Constants.PAYABLES.equalsIgnoreCase(invoiceType)){
					subSah.setBillingStatus(status);
				}else{
					subSah.setInvoiceStatus(status);
				}
			}
			saveAllSubmissionApproverHierarchy(sahList);
		}
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }) 
	public Boolean isReopenButtonVisible(Date frequencyStartDate,Date frequencyEndDate,Long projectId,Long masterCandidateId,Long locationId){
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = new java.sql.Date(frequencyStartDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(frequencyEndDate.getTime());
		
		Query submissionApproverHierarchyListQuery= session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy "
				+ " left join fetch hierarchy.projectClientsId pc left join fetch sah.submissionId submission  "
				+ "   left join fetch submission.projectMembers pm left join fetch submission.projects pj left join fetch submission.masterCandidateId mc  "
				+ "  where pj.id = :projectId and mc.id = :masterCandidateId and sah.frequencyStartDate = :sqlStartDate and sah.frequencyEndDate = :sqlEndDate"
				+ "  and pc.orgLocationsId.id = :locationId and (pc.level IN(select max(subsah.hierarchyId.projectClientsId.level) from SubmissionApproverHierarchy subsah where subsah.submissionId.id = submission.id) OR pc.level IN(select min(subsah.hierarchyId.projectClientsId.level) from SubmissionApproverHierarchy subsah where subsah.submissionId.id = submission.id))"
				+ " ");
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy>  sahList=submissionApproverHierarchyListQuery.setLong("projectId", projectId).setLong("masterCandidateId", masterCandidateId).setLong("locationId", locationId).setParameter("sqlStartDate", sqlStartDate).setParameter("sqlEndDate", sqlEndDate).setCacheable(true).list();
		boolean flag = false;
		if(sahList != null && !sahList.isEmpty()){
			flag = true;
		}
		return flag;
	}

	
}
