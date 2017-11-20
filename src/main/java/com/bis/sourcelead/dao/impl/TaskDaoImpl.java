package com.bis.sourcelead.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.TaskDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.util.CommonUtil;
import com.ibm.icu.text.SimpleDateFormat;

@Repository
public class TaskDaoImpl implements TaskDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	  
	@Autowired
	private CommonUtil commonUtil;
	
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
    
	
	private static final Logger logger = LoggerFactory.getLogger(TaskDaoImpl.class);



	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Tasks saveTasks(Tasks tasks) throws Exception {
		logger.debug("In Tasks Save method");
		Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(tasks);
		return tasks;
	}



	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Tasks getTasksById(Long id) {
		logger.debug("In Tasks getTasksById method");
		Session session = this.sessionFactory.getCurrentSession();
		return (Tasks) session.get(Tasks.class, id);
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Tasks> tasksListByIdAndOrgLocationIdAssignedUserId(Long OrgLocationId, Long userId, String taskStatus,String date, Date startDateUTC, Date endDateUTC) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		String subQuery = "";
		List<Tasks> tasksList  = null;
		String query = null;
		if(null != date && "" != date){
			subQuery = "and tasks.taskDate >= :startDateUTC and tasks.taskDate <= :endDateUTC";
		}else{
			subQuery = "";
		}
		
		
		
		if(Constants.TASK_STATUS_MYTASK.equalsIgnoreCase(taskStatus)){
			query = "from Tasks tasks where tasks.assignedUserId =:userId "+subQuery+" and ((tasks.ownerOrglocationId =:OrgLocationId) OR (tasks.assignedOrglocationId =:OrgLocationId)) and  tasks.taskStatus = 'Open' ORDER BY tasks.createdDate DESC";
			//query = "from Tasks tasks where tasks.ownerOrglocationId = :OrgLocationId and tasks.assignedUserId =:userId "+subQuery+" ORDER BY tasks.createdDate DESC";
		}else if(Constants.TASK_STATUS_ASSIGNTASK.equalsIgnoreCase(taskStatus)){
			query = "from Tasks tasks where tasks.ownerOrglocationId = :OrgLocationId and tasks.taskOwnerId =:userId "+subQuery+" and  tasks.taskStatus = 'Open' ORDER BY tasks.createdDate DESC";
		}else if(Constants.TASK_STATUS_CLOSEDTASK.equalsIgnoreCase(taskStatus)){
			query = "from Tasks tasks where tasks.ownerOrglocationId = :OrgLocationId and (tasks.taskOwnerId =:userId OR tasks.assignedUserId =:userId) and  tasks.taskStatus = 'Closed' "+subQuery+" ORDER BY tasks.createdDate DESC";
		}
		if(null != date && "" != date){
			if(Constants.TASK_STATUS_MYTASK.equalsIgnoreCase(taskStatus)){
				tasksList = session.createQuery(query).setLong( "userId", userId).setParameter("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).setLong( "OrgLocationId", OrgLocationId).setCacheable(true).list();
			}else{
				tasksList = session.createQuery(query).setLong( "OrgLocationId", OrgLocationId).setLong( "userId", userId).setParameter("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).setCacheable(true).list();	
			}
			 	
		}else{
			if(Constants.TASK_STATUS_MYTASK.equalsIgnoreCase(taskStatus)){
				tasksList = session.createQuery(query).setLong( "userId", userId).setLong( "OrgLocationId", OrgLocationId).setCacheable(true).list();	
			}else{
				tasksList = session.createQuery(query).setLong( "OrgLocationId", OrgLocationId).setLong( "userId", userId).setCacheable(true).list();
			}
			
		}
	    
        return tasksList;
	}



	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Tasks> getTaskByScheduledTime(String currentTime) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		 String query ="from Tasks tasks left join fetch tasks.ownerOrglocationId orglocation left join fetch tasks.taskOwnerId user left join fetch tasks.assignedUserId assignUser where tasks.taskDate = :currentTime";
		List<Tasks> tasksScheduledList = session.createQuery(query).setString("currentTime",currentTime).setCacheable(true).list();
		return tasksScheduledList;
	}



	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getTasksCompletedTodayByLocationCodeandUserId(OrgLocations orglocationCode, User user)
			throws Exception {
		

		Session session = this.sessionFactory.getCurrentSession();
		
		// This is to check with current date which is converted to normal sql date
		java.sql.Date sqlCurrentDate = null;
 	    Date currentDate =  commonUtil.currentDate();
 	    java.sql.Timestamp sq =  null;
 		// for current Date Converting to Sql Date Format
 		 if(currentDate != null){
 				sqlCurrentDate = new java.sql.Date(currentDate.getTime());
 				sq = new java.sql.Timestamp(currentDate.getTime());
 		 }
		
	    String query ="select count(*) from Tasks tasks where  DATE(tasks.updatedDate) =:sqlCurrentDate and tasks.ownerOrglocationId =:orgLocationId  and tasks.taskStatus = 'Closed' and tasks.assignedUserId.id =:assignUserId";
		List<Long> results = session.createQuery(query).setDate("sqlCurrentDate",sq).setLong("orgLocationId", orglocationCode.getId()).setLong("assignUserId", user.getId()).setCacheable(true).list();
		logger.debug("..getCandidatesCount() Method Ended..");
		return results.get(0);
		
	}



	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Tasks> getTasskByAssignedUserIdAndOrgLocationId(Long orgLocationId, Long userId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		 String query ="from Tasks tasks left join fetch tasks.ownerOrglocationId orglocation  left join fetch tasks.assignedUserId assignUser "
		 		+ " where assignUser.id = :userId and orglocation.id = :orgLocationId";
		List<Tasks> tasksScheduledList = session.createQuery(query).setLong("userId",userId).setLong("orgLocationId",orgLocationId).setCacheable(true).list();
		return tasksScheduledList;
	}



	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Tasks> getTasskByAssignedUserIdAndOrgLocationIdAndOnboardingId(Long orgLocationId, Long userId,
			Long onboardingId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		 String query ="from Tasks tasks left join fetch tasks.ownerOrglocationId orglocation  left join fetch tasks.assignedUserId assignUser "
		 		+ " left join fetch tasks.onboardingId where assignUser.id = :userId and orglocation.id = :orgLocationId "
		 		+ " and tasks.onboardingId.id = :onboardingId";
		List<Tasks> tasksScheduledList = session.createQuery(query).setLong("userId",userId).setLong("orgLocationId",orgLocationId).setLong("onboardingId", onboardingId).setCacheable(true).list();
		return tasksScheduledList;
	}



	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Tasks> getTasskByAssignedUserIdAndOrgLocationIdAndOffboardingId(Long orgLocationId, Long userId,
			Long offboardingId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		 String query ="from Tasks tasks left join fetch tasks.ownerOrglocationId orglocation  left join fetch tasks.assignedUserId assignUser "
		 		+ " left join fetch tasks.offboardingId where assignUser.id = :userId and orglocation.id = :orgLocationId "
		 		+ " and tasks.offboardingId.id = :offboardingId";
		List<Tasks> tasksScheduledList = session.createQuery(query).setLong("userId",userId).setLong("orgLocationId",orgLocationId).setLong("offboardingId", offboardingId).setCacheable(true).list();
		return tasksScheduledList;
	}



	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Tasks> getTasksListOffboardingId(Long offboardingId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		 String query ="from Tasks tasks left join fetch tasks.offboardingId left join fetch tasks.assignedUserId where tasks.offboardingId.id = :offboardingId";
		List<Tasks> tasksList = session.createQuery(query).setLong("offboardingId", offboardingId).setCacheable(true).list();
		return tasksList;
	}



	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Tasks> getTasksListOnboardingId(Long onboardingId) throws Exception {
	
		Session session = this.sessionFactory.getCurrentSession();
		 String query ="from Tasks tasks left join fetch tasks.onboardingId left join fetch tasks.assignedUserId where tasks.onboardingId.id = :onboardingId";
		List<Tasks> tasksList = session.createQuery(query).setLong("onboardingId", onboardingId).setCacheable(true).list();
		return tasksList;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Tasks> getTaskListByRepeatStatus(String taskRepeatStatus) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		 String query ="from Tasks tasks  where tasks.taskRepeat = :taskRepeatStatus and tasks.taskStatus ='Open'";
		List<Tasks> tasksScheduledList = session.createQuery(query).setString("taskRepeatStatus",taskRepeatStatus).setCacheable(true).list();
		return tasksScheduledList;
	}



	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Tasks> getTaskByAssignedUserIdAndOrgLocationIdAndVendorOnboardingId(Long orgLocationId, Long userId,
			Long vendorOnboardingId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		 String query ="from Tasks tasks left join fetch tasks.ownerOrglocationId orglocation  left join fetch tasks.assignedUserId assignUser "
		 		+ " left join fetch tasks.onboardingId where assignUser.id = :userId and orglocation.id = :orgLocationId "
		 		+ " and tasks.vendorOnboardingId.id = :vendorOnboardingId";
		List<Tasks> tasksScheduledList = session.createQuery(query).setLong("userId",userId).setLong("orgLocationId",orgLocationId).setLong("vendorOnboardingId", vendorOnboardingId).setCacheable(true).list();
		return tasksScheduledList;
	}



}

