package com.bis.sourcelead.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.SubmissionApproverHierarchyDao;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;

@Repository
public class SubmissionApproverHierarchyDaoImpl implements SubmissionApproverHierarchyDao {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(SubmissionApproverHierarchyDaoImpl.class);
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy addSubmissionApproverHierarchy(
			SubmissionApproverHierarchy submissionApproverHierarchy) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.save(submissionApproverHierarchy);
		}
		catch(HibernateException exception)	{
		}
		return submissionApproverHierarchy;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy updateSubmissionApproverHierarchy(
			SubmissionApproverHierarchy submissionApproverHierarchy) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
	    session.saveOrUpdate(submissionApproverHierarchy);
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
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForApprover(Long submissionId,Long approverUserId) {
			
		Session session = this.sessionFactory.getCurrentSession();
		Query submissionApproverHierarchyListQuery= session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId where sah.submissionId.id = :submissionId and hierarchy.approverUserId.id = :approverUserId ");
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy>  submissionApproverHierarchyList=submissionApproverHierarchyListQuery.setLong("submissionId", submissionId).setLong("approverUserId",approverUserId).list();
		SubmissionApproverHierarchy sah = null;
		if(submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()){
			sah = submissionApproverHierarchyList.iterator().next();
		}
	    return sah;
	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyForEmployeeIdAndStartDate(Long empId,
			Long approverUserId, Date startDate) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		
		String query = " from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId submission left join fetch submission.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user where emp.id =:empId and sah.submissionId.startDate = :startDate and  hierarchy.approverUserId.id = :approverUserId ";
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy> sahList = session.createQuery(query)
																	  .setLong("empId", empId)	
				                                                      .setLong("approverUserId",approverUserId)
				                                                      .setParameter("startDate", sqlStartDate).setCacheable(true).list();
		
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

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void updateApprovalStatusForApprover(Long empId,Long approverUserId, Date startDate, String status,String rejectedReason) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		
		String query = " from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId submission left join fetch submission.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user where emp.id =:empId and sah.submissionId.startDate = :startDate and  hierarchy.approverUserId.id = :approverUserId ";
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy> sahList = session.createQuery(query)
																	  .setLong("empId", empId)	
																	  .setLong("approverUserId",approverUserId)
				                                                      .setParameter("startDate", sqlStartDate).list();
		
		Query queryForMaxLevel = session.createQuery("select Max(sah.hierarchyId.level) from SubmissionApproverHierarchy sah  where sah.submissionId.projectMembers.employee.id =:empId and sah.submissionId.startDate = :startDate ");
		List<Integer> maxList = queryForMaxLevel.setLong("empId", empId).setParameter("startDate", sqlStartDate).list();
		Integer maxLevel = 0;
		if(maxList !=null && !maxList.isEmpty()){
			maxLevel = maxList.iterator().next();
		}
		if(sahList != null && !sahList.isEmpty()){
	    	for(SubmissionApproverHierarchy sah :sahList){
	    		
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    		String queryForAllApprovers = " from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId submission left join fetch submission.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user where sah.id <> :id and emp.id =:empId and sah.submissionId.startDate = :startDate  ";
	    		@SuppressWarnings("unchecked")
        		List<SubmissionApproverHierarchy> approversList = session.createQuery(queryForAllApprovers)
        																	  .setLong("empId", empId)	
        																	  .setLong("id", sah.getId())
        				                                                      .setParameter("startDate", sqlStartDate).list();
        		
                if(sah.getHeirarchyId().getLevel()==(maxLevel)){
                	if(approversList != null && !approversList.isEmpty()){
                		for(SubmissionApproverHierarchy approver  :  approversList){
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
                			 try {
             					updateSubmissionApproverHierarchy(approver);
             				} catch (Exception e) {
             					// TODO Auto-generated catch block
             					e.printStackTrace();
             				}
                		}
                	}
	    		}
	    		 
	    	}
	    }
		
		
	}

	/*@Override
	public void updateSubmissionStatusForApprover(Long submissionId, Long approverUserId,String status) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		Query submissionApproverHierarchyListQuery= session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId where sah.submissionId.id = :submissionId and hierarchy.approverUserId.id = :approverUserId ");
		List<SubmissionApproverHierarchy>  submissionApproverHierarchyList=submissionApproverHierarchyListQuery.setLong("submissionId", submissionId).list();
		
		Query queryForMaxLevel = session.createQuery("select sah.hierarchyId.level from SubmissionApproverHierarchy sah where sah.submissionId.id = :submissionId");
		List<Integer> maxList = queryForMaxLevel.setLong("submissionId", submissionId).list();
		Integer maxLevel = 0;
		if(maxList !=null && !maxList.isEmpty()){
			maxLevel = maxList.iterator().next();
		}
		if(submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()){
		    	for(SubmissionApproverHierarchy sah :submissionApproverHierarchyList){
		    		
		    	}
		 }
	
	}*/

}
