package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
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
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.ibm.icu.text.SimpleDateFormat;

@Repository
public class TaskDaoImpl implements TaskDao{
	
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

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Tasks> assignedTasksListByIdANDOrgLocationId(Long OrgLocationId,Long userId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Tasks tasks where tasks.ownerOrglocationId = :OrgLocationId and tasks.taskOwnerId =:userId ";
	    List<Tasks> tasksList = session.createQuery(query).setLong( "OrgLocationId", OrgLocationId).setLong( "userId", userId).setCacheable(true).list();
        return tasksList;
	}
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Tasks> myTasksListByIdAndOrgLocationIdAssignedUserId(Long OrgLocationId,Long userId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Tasks tasks where tasks.ownerOrglocationId = :OrgLocationId and tasks.taskOwnerId =:userId and tasks.assignedUserId =:userId ";
	    List<Tasks> tasksList = session.createQuery(query).setLong( "OrgLocationId", OrgLocationId).setLong( "userId", userId).setCacheable(true).list();
        return tasksList;
	}



	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Tasks> tasksListByIdAndOrgLocationIdAssignedUserId(Long OrgLocationId, Long userId, String Status,String date) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		String subQuery = "";
		List<Tasks> tasksList  = null;
		String strDate = null;
		String query = null;
		if(null != date && "" != date){
			Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(date);
			 strDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
			subQuery = "and tasks.taskDate =:strDate";	
		}else{
			subQuery = "";
		}
		 
		if(Constants.TASK_STATUS_MYTASK.equalsIgnoreCase(Status)){
			query = "from Tasks tasks where tasks.ownerOrglocationId = :OrgLocationId and tasks.assignedUserId =:userId "+subQuery+" ORDER BY tasks.createdDate DESC";
		}else if(Constants.TASK_STATUS_ASSIGNTASK.equalsIgnoreCase(Status)){
			 query = "from Tasks tasks where tasks.ownerOrglocationId = :OrgLocationId and tasks.taskOwnerId =:userId "+subQuery+" ORDER BY tasks.createdDate DESC";
		}else if(Constants.TASK_STATUS_CLOSEDTASK.equalsIgnoreCase(Status)){
			query = "from Tasks tasks where tasks.ownerOrglocationId = :OrgLocationId and (tasks.taskOwnerId =:userId OR tasks.assignedUserId =:userId) and  tasks.taskStatus = 'Closed' "+subQuery+" ORDER BY tasks.createdDate DESC";
		}
		if(null != date && "" != date){
			 tasksList = session.createQuery(query).setLong( "OrgLocationId", OrgLocationId).setLong( "userId", userId).setString("strDate", strDate).setCacheable(true).list();	
		}else{
			tasksList = session.createQuery(query).setLong( "OrgLocationId", OrgLocationId).setLong( "userId", userId).setCacheable(true).list();
		}
	    
        return tasksList;
	}




}

