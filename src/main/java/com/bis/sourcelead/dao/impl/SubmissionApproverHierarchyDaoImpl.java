package com.bis.sourcelead.dao.impl;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Date;
import java.util.LinkedHashMap;
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
import com.bis.sourcelead.dao.SubmissionApproverHierarchyDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;
import com.bis.sourcelead.util.TimesheetFrequencyGeneratorUtil;

@Repository
public class SubmissionApproverHierarchyDaoImpl implements SubmissionApproverHierarchyDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
	UserDao userDao;
	
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
	public SubmissionApproverHierarchy getSubmissionApproverHierarchy(String id) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		return (SubmissionApproverHierarchy) session.get(SubmissionApproverHierarchy.class, id);
	}

	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean deleteSubmissionApproverHierarchy(String id) throws Exception {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
		
		// TODO Auto-generated method stub
		return submissionApproverHierarchy;
	}
	
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean getCronRemainderForEmployeeByProjectMemberId(Date frequencyStartDate,Date frequencyEndDate,Long projectMemberId) {
		
		Boolean flag = true; 
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = new java.sql.Date(frequencyStartDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(frequencyEndDate.getTime());
		
		Query submissionApproverHierarchyListQuery= session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId left join fetch sah.submissionId submission  left join fetch submission.projectMembers pm where pm.id = :projectMemberId and (sah.submissionStatus = 'SUBMITTED' OR sah.submissionStatus = 'APPROVED')  and  ((submission.startDate = :sqlStartDate) OR (submission.startDate BETWEEN :sqlStartDate and :sqlEndDate))");
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
		// TODO Auto-generated method stub
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
					// TODO Auto-generated catch block
					e.printStackTrace();
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
		
		Query submissionApproverHierarchyListQuery= session.createQuery("from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee  emp  left join fetch emp.user where pm.id = :projectMemberId and  (ts.startDate between :startDate and :endDate OR ts.startDate = :startDate)");
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
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		
		User loginUser = userDao.getUserById(approverUserId);
		/*
		 *  get submissionapproverhierarchy list by using empId and logged in user id as approver(either user may exist in project hierarchy  or user may be admin)
		 */
		String query = " from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId submission left join fetch submission.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee emp left join fetch emp.user "
				+ " where pm.id =:projectMemberId and sah.frequencyStartDate =:startDate and  sah.frequencyEndDate =:endDate "
				        + "and ("
				           /*
						   *  logged in user as approver in project_approver_hierarchy (Either Admins/project_managers/team leads ...etc)
						   */
					        + "    hierarchy.approverUserId.id = :approverUserId  "
					        /*
							 *  logged in user as admin but not in project_approver_hierarchy(hint:logged in user is admin but not assigned to project as approver)
							 */
					          + " OR ( :approverUserId NOT IN( select subsah.hierarchyId.approverUserId.id from SubmissionApproverHierarchy subsah where subsah.submissionId.projectMembers.id = :projectMemberId and subsah.submissionApprovalStatus != 'APPROVED' and subsah.frequencyStartDate = :startDate and subsah.frequencyEndDate = :endDate ) "
					        + " and "+ Constants.loginUserAsAdminWithMaxHeirarchyLevel + ")"
				            + ")";
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy> sahMainList = session.createQuery(query)
																	  .setLong("projectMemberId", projectMemberId)	
																	  .setLong("approverUserId",approverUserId)
																	  .setLong("userId",approverUserId)
				                                                      .setParameter("startDate", sqlStartDate)
				                                                      .setParameter("endDate", sqlEndDate)
				                                                      .list();
		
	
		if(sahMainList != null && !sahMainList.isEmpty()){
	    	for(SubmissionApproverHierarchy sah :sahMainList){
	    		
	    		/*
	    		 * get Max Level
	    	    */
	    		Query queryForMaxLevel = session.createQuery("select Max(sah.hierarchyId.level) from SubmissionApproverHierarchy sah  where sah.submissionId.projectMembers.id =:projectMemberId and sah.submissionId.id = :submissionId ");
	    		List<Integer> maxList = queryForMaxLevel.setLong("projectMemberId", projectMemberId).setLong("submissionId", sah.getSubmissionId().getId()).list();
	    		Integer maxLevel = 0;
	    		if(maxList !=null && !maxList.isEmpty()){
	    			maxLevel = maxList.iterator().next();
	    		}
	    		
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
	    		
	    		String queryForAllApprovers = " from SubmissionApproverHierarchy sah left join fetch "
	    				+ " sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId submission left join fetch submission.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user where sah.id <> :id and pm.id =:projectMemberId  and sah.submissionId = :submissionId";
	    		@SuppressWarnings("unchecked")
        		List<SubmissionApproverHierarchy> approversList = session.createQuery(queryForAllApprovers)
        																	  .setLong("projectMemberId", projectMemberId)	
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
                			 else if(sah.getHeirarchyId().getLevel()==(maxLevel) && (status!=null && status.equalsIgnoreCase("APPROVED"))){
		            	            	 approver.setSubmissionApprovalStatus(Submission_ApprovalStatus.APPROVED);
		            	            	 approver.setSubmissionStatus(Submission_Status.APPROVED);
		            	            	 approver.setActionPerformedBy(" "+loginUser.getFullName());
		            	            	 approver.setReopenReason("");
		                 				 approver.setReopenedUser(null);
		                 				 approver.setReopenRejectedReason("");
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
		// TODO Auto-generated method stub
		
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
	public Float getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee(Long userId, Long projectId,
			String approvalStatus) {
		Session session = this.sessionFactory.getCurrentSession();
		
		//Getting List for which employee  is Assigned
		String subQueryForUserIdChecking = " (emp.id IN(select emp.id from Employees emp where emp.user.id = :userId)) ";
		String subQueryForList = " from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp where "+subQueryForUserIdChecking+" and pm.projects.id = :projectId ";
		
		//Getting Max Hierarchy Level for which employee is Assigned
		String subQueryForUserIdCheckingInMaxLevel = " (subsah.submissionId.projectMembers.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId)) ";
		String subQueryForMaxLevel = " select MAX(subsah.hierarchyId.level) from  SubmissionApproverHierarchy subsah  where "+subQueryForUserIdCheckingInMaxLevel+" and pm.projects.id = :projectId ";
		
		String query = subQueryForList +" and  ( hierarchy.level IN("+subQueryForMaxLevel+"))  and  sah.submissionApprovalStatus = :approvalStatus" ;
		List<SubmissionApproverHierarchy> tslist =session.createQuery(query.toString()).setLong("userId", userId).setLong("projectId",projectId).setString("approvalStatus", approvalStatus).setCacheable(true).list(); 
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
	public Float getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(Long userId, Long projectId,
			String submissionStatus) {
        Session session = this.sessionFactory.getCurrentSession();
		
		//Getting List for which employee  is Assigned
		String subQueryForUserIdChecking = " (emp.id IN(select emp.id from Employees emp where emp.user.id = :userId)) ";
		String subQueryForList = " from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp where "+subQueryForUserIdChecking+" and pm.projects.id = :projectId ";
		
		//Getting Max Hierarchy Level for which employee is Assigned
		String subQueryForUserIdCheckingInMaxLevel = " (subsah.submissionId.projectMembers.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId)) ";
		String subQueryForMaxLevel = " select Min(subsah.hierarchyId.level) from  SubmissionApproverHierarchy subsah  where "+subQueryForUserIdCheckingInMaxLevel+" and pm.projects.id = :projectId ";
		
		String query = subQueryForList +" and  ( hierarchy.level IN("+subQueryForMaxLevel+"))  and  sah.submissionStatus = :submissionStatus" ;
		List<SubmissionApproverHierarchy> tslist =session.createQuery(query.toString()).setLong("userId", userId).setLong("projectId",projectId).setString("submissionStatus", submissionStatus).setCacheable(true).list(); 
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
	public List<SubmissionApproverHierarchy> getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForEmployeeLogin(
			Long userId, Long orgLocationId, String submissionStatus) {
		
          Session session = this.sessionFactory.getCurrentSession();
		
		//Getting List for which employee  is Assigned
		String subQueryForList = " from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp left join fetch emp.user";
		
		//Getting Max Hierarchy Level for which employee is Assigned
		String subQueryForUserIdCheckingInMaxLevel = "(subsah.submissionId.projectMembers.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId)) ";
		String subQueryForMaxLevel = " select MAX(subsah.hierarchyId.level) from  SubmissionApproverHierarchy subsah  where "+subQueryForUserIdCheckingInMaxLevel+" and subsah.submissionId.projectMembers.employee.orgLocations.id = :orgLocationId ";
		
		String query = subQueryForList +" where emp.orgLocations.id = :orgLocationId  and emp.user.id = :userId  and ( hierarchy.level IN("+subQueryForMaxLevel+")) and  sah.submissionApprovalStatus = :submissionStatus GROUP BY sah.submissionId,hierarchy.level" ;
		List<SubmissionApproverHierarchy> tslist =session.createQuery(query.toString()).setLong("userId", userId).setLong("orgLocationId",orgLocationId).setString("submissionStatus", submissionStatus).setCacheable(true).list(); 
		
		return tslist;
		
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(
			Long userId, Long orgLocationId, String submissionStatus) {
		

		Session session = this.sessionFactory.getCurrentSession();
		String subQueryForUserIdChecking = "(emp.id IN(select emp.id from Employees emp where emp.user.id = :userId))  ";
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp left join fetch emp.user where "+subQueryForUserIdChecking+" and emp.orgLocations.id = :orgLocationId and  sah.submissionStatus = :submissionStatus  GROUP BY sah.submissionId.id)" ;
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy> tslist =session.createQuery(query.toString()).setLong("userId", userId).setLong("orgLocationId",orgLocationId).setString("submissionStatus", submissionStatus).setCacheable(true).list(); 
		
		return tslist;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(
			Long userId, Long orgLocationId, String submissionStatus) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee  emp  left join fetch emp.user "
				+ " where  emp.orgLocations.id = :orgLocationId and "
				+ " ( "
				    /*
				    *  logged in user as approver in project_approver_hierarchy (Either Admins/project_managers/team leads ...etc)
				    */
				+ "  hierarchy.approverUserId.id = :userId "
				    /*
					 *  logged in user as admin but not in project_approver_hierarchy(hint:logged in user is admin but not assigned to project as approver)
					 */
				+ "  OR ( :userId NOT IN( select subsah.hierarchyId.approverUserId.id from SubmissionApproverHierarchy subsah where subsah.submissionId.id = sah.submissionId.id ) and "+Constants.loginUserAsAdminWithMaxHeirarchyLevel+") "
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
		// TODO Auto-generated method stub
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
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLogin(Long approverUserId,Long projectMemberId, Date startDate, Date endDate) throws Exception {
			
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
	    		 
	    		 System.out.println( sah.getSubmissionApprovalStatus());
			    
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
	    	 DateFormat format = Constants.DF_DMY;
	    	 Map<String,String> tabFrequencyDates = timesheetFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(format.format(startDate), format.format(endDate));
	    	
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
	

}
