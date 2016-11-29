package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.Date;
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

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.SubmissionApproverHierarchyDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;

@Repository
public class SubmissionApproverHierarchyDaoImpl implements SubmissionApproverHierarchyDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
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
			session.save(submissionApproverHierarchy);
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
		// TODO Auto-generated method stub
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
	public void updateSubmissionStatus(Long submissionId,String status) {
		Session session = this.sessionFactory.getCurrentSession();
		Query submissionApproverHierarchyListQuery= session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId left join fetch sah.submissionId where sah.submissionId.id = :submissionId");
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy>  submissionApproverHierarchyList=submissionApproverHierarchyListQuery.setLong("submissionId", submissionId).setCacheable(true).list();
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
					updateSubmissionApproverHierarchy(sah);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	
	    }
	
	
	}
	 
	//can you write the description
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void updateApprovalStatusForApprover(Long empId,Long approverUserId, Date startDate, String status,String rejectedReason) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		
		/*
		 *  get submissionapproverhierarchy list by using empId and logged in user id as approver(either user may exist in project hierarchy  or user may be admin)
		 */
		String query = " from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId submission left join fetch submission.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee emp left join fetch emp.user where sah.submissionApprovalStatus != 'APPROVED' and emp.id =:empId and sah.submissionId.startDate = :startDate "
				        + "and ("
				           /*
						   *  logged in user as approver in project_approver_hierarchy (Either Admins/project_managers/team leads ...etc)
						   */
					        + "    (hierarchy.approverUserId.id = :approverUserId ) "
					        /*
							 *  logged in user as admin but not in project_approver_hierarchy(hint:logged in user is admin but not assigned to project as approver)
							 */
					        + " OR ( :approverUserId NOT IN( select subsah.hierarchyId.approverUserId.id from SubmissionApproverHierarchy subsah where subsah.submissionId.projectMembers.employee.id = :empId and subsah.submissionApprovalStatus != 'APPROVED' and subsah.submissionId.startDate = :startDate) and "+Constants.loginUserAsAdminWithMaxHeirarchyLevel+")"
				        	+ ")";
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy> sahMainList = session.createQuery(query)
																	  .setLong("empId", empId)	
																	  .setLong("approverUserId",approverUserId)
																	  .setLong("userId",approverUserId)
				                                                      .setParameter("startDate", sqlStartDate).list();
		
	
		if(sahMainList != null && !sahMainList.isEmpty()){
	    	for(SubmissionApproverHierarchy sah :sahMainList){
	    		
	    		/*
	    		 * get Max Level
	    	    */
	    		Query queryForMaxLevel = session.createQuery("select Max(sah.hierarchyId.level) from SubmissionApproverHierarchy sah  where sah.submissionId.projectMembers.employee.id =:empId and sah.submissionId.id = :submissionId and sah.submissionId.startDate = :startDate ");
	    		List<Integer> maxList = queryForMaxLevel.setLong("empId", empId).setLong("submissionId", sah.getSubmissionId().getId()).setParameter("startDate", sqlStartDate).list();
	    		Integer maxLevel = 0;
	    		if(maxList !=null && !maxList.isEmpty()){
	    			maxLevel = maxList.iterator().next();
	    		}
	    		
	    		
	    	   /* query = " from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId submission left join fetch submission.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user where emp.id =:empId and sah.submissionId.id = :submissionId and sah.submissionId.startDate = :startDate and hierarchy.approverUserId.id = :approverUserId ";
	    		@SuppressWarnings("unchecked")
	    		List<SubmissionApproverHierarchy> sahList = session.createQuery(query)
	    																	  .setLong("empId", empId)	
	    																	  .setLong("approverUserId",approverUserId)
	    																	  .setLong("submissionId", mainSah.getSubmissionId().getId())
	    				                                                      .setParameter("startDate", sqlStartDate).list();*/
	         /* for(SubmissionApproverHierarchy sah :sahList){*/
	    		
	    		if(status!=null && status.equalsIgnoreCase("REJECTED")){
            	 	sah.setSubmissionApprovalStatus(Submission_ApprovalStatus.REJECTED);
            	 	sah.setSubmissionStatus(Submission_Status.REJECTED);
            	 	sah.setActionPerformedBy("Rejected By "+sah.getHeirarchyId().getApproverUserId().getFirstName() + " "+sah.getHeirarchyId().getApproverUserId().getLastName());
            	 	sah.setRejectedReason(rejectedReason);
            	 	
	             }else if(status!=null && status.equalsIgnoreCase("APPROVED")){
	            	 sah.setSubmissionApprovalStatus(Submission_ApprovalStatus.APPROVED);
	            	 sah.setSubmissionStatus(Submission_Status.APPROVED);
	            	 sah.setActionPerformedBy("Approved By "+sah.getHeirarchyId().getApproverUserId().getFirstName() + " "+sah.getHeirarchyId().getApproverUserId().getLastName());
	             }
	    		 try {
					updateSubmissionApproverHierarchy(sah);
				} catch (Exception e) {
				}
	    		
	    		String queryForAllApprovers = " from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId submission left join fetch submission.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user where sah.id <> :id and emp.id =:empId and sah.submissionId.id = :submissionId  and sah.submissionId.startDate = :startDate  ";
	    		@SuppressWarnings("unchecked")
        		List<SubmissionApproverHierarchy> approversList = session.createQuery(queryForAllApprovers)
        																	  .setLong("empId", empId)	
        																	  .setLong("submissionId", sah.getSubmissionId().getId())
        																	  .setLong("id", sah.getId())
        				                                                      .setParameter("startDate", sqlStartDate).list();
	    		
                	if(approversList != null && !approversList.isEmpty()){
                		for(SubmissionApproverHierarchy approver  :  approversList){
                			  //If logged in user is high level approver then all appropriate submission will be reflected
	                	      if(sah.getHeirarchyId().getLevel()==(maxLevel)){
		                			if(status!=null && status.equalsIgnoreCase("REJECTED")){
		                				approver.setSubmissionApprovalStatus(Submission_ApprovalStatus.REJECTED);
		                				approver.setSubmissionStatus(Submission_Status.REJECTED);
		                				approver.setActionPerformedBy("Rejected By "+sah.getHeirarchyId().getApproverUserId().getFirstName() + " "+sah.getHeirarchyId().getApproverUserId().getLastName());
		                				approver.setRejectedReason(rejectedReason);
		                        	 	
		            	             }else if(status!=null && status.equalsIgnoreCase("APPROVED")){
		            	            	 approver.setSubmissionApprovalStatus(Submission_ApprovalStatus.APPROVED);
		            	            	 approver.setSubmissionStatus(Submission_Status.APPROVED);
		            	            	 approver.setActionPerformedBy("Approved By "+sah.getHeirarchyId().getApproverUserId().getFirstName() + " "+sah.getHeirarchyId().getApproverUserId().getLastName());
		            	             }
	                		 }
	                	      //if logged in user is low level approver then record will be submitted to high level approver
	                	     else{
	                	    	 approver.setSubmissionApprovalStatus(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL);
            	            	 approver.setSubmissionStatus(Submission_Status.SUBMITTED);
	                	     }
                	        try {
             			 		updateSubmissionApproverHierarchy(approver);
             				} catch (Exception e) {
             				}
                	   }
                	}
	    	   //}	 
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
	public Float getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(Long userId, Long projectId,
			String submissionStatus) {
        Session session = this.sessionFactory.getCurrentSession();
		
		//Getting List for which employee  is Assigned
		String subQueryForUserIdChecking = " (emp.id IN(select emp.id from Employees emp where emp.user.id = :userId)) ";
		String subQueryForList = " from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp where "+subQueryForUserIdChecking+" and pm.projects.id = :projectId ";
		
		//Getting Max Hierarchy Level for which employee is Assigned
		String subQueryForUserIdCheckingInMaxLevel = " (subsah.submissionId.projectMembers.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId)) ";
		String subQueryForMaxLevel = " select MAX(subsah.hierarchyId.level) from  SubmissionApproverHierarchy subsah  where "+subQueryForUserIdCheckingInMaxLevel+" and pm.projects.id = :projectId ";
		
		String query = subQueryForList +" and  ( hierarchy.level IN("+subQueryForMaxLevel+"))  and  sah.submissionStatus = :submissionStatus" ;
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
	public List<SubmissionApproverHierarchy> getTotalDraftAndSubmittedListByuserIdAndProjectIdForEmployee(Long userId,
			Long projectId, String submissionStatus) {
		
		
		 Session session = this.sessionFactory.getCurrentSession();
		   //Getting List for which employee  is Assigned
				String subQueryForUserIdChecking = " (emp.id IN(select emp.id from Employees emp where emp.user.id = :userId)) ";
				String subQueryForList = " from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp where "+subQueryForUserIdChecking+" and pm.projects.id = :projectId ";
				
				//Getting Max Hierarchy Level for which employee is Assigned
				String subQueryForUserIdCheckingInMaxLevel = " (subsah.submissionId.projectMembers.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId)) ";
				String subQueryForMaxLevel = " select MAX(subsah.hierarchyId.level) from  SubmissionApproverHierarchy subsah  where "+subQueryForUserIdCheckingInMaxLevel+" and pm.projects.id = :projectId ";
				
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
				+ " and  sah.submissionStatus = :submissionStatus    GROUP BY sah.submissionId order by pm.projects.id ASC)" ;
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


	

}
