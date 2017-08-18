package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ProjectTasksDao;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;

@Repository
public class ProjectTasksDaoImpl implements ProjectTasksDao {

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

	private static final Logger logger = LoggerFactory.getLogger(ProjectTasksDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectTasks addTasks(ProjectTasks tasks) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.save(tasks);
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECTS_TASKS"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return tasks;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectTasks updateTasks(ProjectTasks tasks) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		try{
			if(tasks != null){
				session.saveOrUpdate(tasks);
			}
		}catch(Exception e){
			StringBuilder message= new StringBuilder(MessageFormat.format(statusUpdate,"PROJECTS_TASKS"));
			throw new SourceLeadObjectNotFoundException(message.toString());
		}
		return tasks;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectTasks getTasksId(String id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		return (ProjectTasks) session.get(ProjectTasks.class, Long.valueOf(id));
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean deleteTask(String id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		boolean isDeleted = false;
		try{
			ProjectTasks tasks = (ProjectTasks) session.get(ProjectTasks.class, Long.valueOf(id));
			tasks.setStatus("1");
			session.saveOrUpdate(tasks);
			isDeleted = true;
			logger.info("Successfully deleted !!!");
		}catch(Exception e){
			StringBuilder message= new StringBuilder(MessageFormat.format(statusDelete,"TASK"));
			throw new SourceLeadObjectNotFoundException(message.toString());	
		}
		
		return isDeleted;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectTasks> getAllTasks() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<ProjectTasks> adTaskList = null;
		Query taskListQuery = session.createQuery("from ProjectTasks");
		adTaskList = taskListQuery.list();
		;
		return adTaskList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectTasks> getAllTasksByProjectId(Long projectId) throws Exception {
		// TODO Auto-generated method stub

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectTasks pt where pt.projectId.id = :projectId " ;
		List<ProjectTasks> projectTasksList =session.createQuery(query.toString()).setInteger("projectId", projectId.intValue()).setCacheable(true) 
			    .list(); 
		return projectTasksList;  
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectTasks> getAllTasksByProjectIdAndExistedProjectTaskIds(Long projectId,
			List<Long> avilableProjectTaskIds) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		if(avilableProjectTaskIds != null  && !avilableProjectTaskIds.isEmpty()){
			String query = "from ProjectTasks pt where pt.projectId.id = :projectId and pt.id NOT IN (:avilableProjectTaskIds) ";
			List<ProjectTasks> projectTasksList = session.createQuery(query.toString()).setInteger("projectId", projectId.intValue()).setParameterList("avilableProjectTaskIds", avilableProjectTaskIds).list();
			return projectTasksList;
			
		}else{
			String query = "from ProjectTasks pt where pt.projectId.id = :projectId";
			List<ProjectTasks> projectTasksList = session.createQuery(query.toString()).setInteger("projectId", projectId.intValue()).list();
			return projectTasksList;
			
		}
		
	}
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectTasks> getAllSourceleadTasks() throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectTasks projectsTasks where projectsTasks.projectId IS NULL and  projectsTasks.createdBy = 'SLADMIN'";
		List<ProjectTasks> projectTasksList =session.createQuery(query.toString()).list();
		return projectTasksList; 
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void saveAll(Collection<ProjectTasks> taskList) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if(taskList != null && !taskList.isEmpty()){
				for(ProjectTasks tasks :taskList){
					session.save(tasks);
				}
			}
		
		} catch (HibernateException exception) {
		}
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectTasks validateTaskNameByTaskId(Long taskId, String taskName, Long projectId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectTasks projectsTasks where projectsTasks.projectId =:projectId and projectsTasks.taskName =:taskName and projectsTasks.id =:taskId";
		List<ProjectTasks> projectTasksList =session.createQuery(query) .setString("taskName", taskName).setLong("taskId", taskId).setLong("projectId", projectId).setCacheable(true).list();
		ProjectTasks projectTasks = null;
		if(projectTasksList != null && !projectTasksList.isEmpty())
		{
			projectTasks = (ProjectTasks) projectTasksList.iterator().next();
		}
		return projectTasks;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectTasks> getAllTasksByProjectIdAndTaskType(Long projectId, String TaskType) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectTasks pt where pt.projectId.id = :projectId and pt.taskType = :TaskType" ;
		List<ProjectTasks> projectTasksList =session.createQuery(query.toString()).setInteger("projectId", projectId.intValue()).setString("TaskType", TaskType).setCacheable(true).list(); 
		return projectTasksList; 
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectTasks> getAllTasksByProjectIdAndTaskTypeAndTaskStatus(Long projectId, String taskType, String taskStatus) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		
		String[] type1 = null;
		if(null != taskType && !"".equals(taskType)){	
			 type1 = taskType.split(",");
		}
		List<Long> taskTypeList = new ArrayList<>();
		if(type1 != null){
			for(int i=0;i<type1.length;i++){
				taskTypeList.add(Long.parseLong(type1[i]));
			}
		}
		
		String[] status1 = null;
		if(null != taskStatus && !"".equals(taskStatus)){	
			status1 = taskStatus.split(",");
		}
		List<Long> taskStatusList = new ArrayList<>();
		if(status1 != null){
			for(int i=0;i<status1.length;i++){
				taskStatusList.add(Long.parseLong(status1[i]));
			}
		}
		
		String query = null;
		List<ProjectTasks> projectTasksList  = null;
		if((taskType != null && taskType != "")  || (taskStatus != null && taskStatus != "")) {
			if(taskType != null && taskType != "") {
				 query = "from ProjectTasks pt where pt.projectId.id = :projectId  and pt.taskType.id IN (:taskTypeList)" ;
				 projectTasksList =session.createQuery(query.toString()).setInteger("projectId", projectId.intValue()).setParameterList("taskTypeList", taskTypeList).setCacheable(true).list(); 
			}
			if(taskStatus != null && taskStatus != "") {
				query = "from ProjectTasks pt where pt.projectId.id = :projectId  and pt.taskStatus.id IN (:taskStatusList)" ;
				 projectTasksList =session.createQuery(query.toString()).setInteger("projectId", projectId.intValue()).setParameterList("taskStatusList", taskStatusList).setCacheable(true).list(); 
			}
		}else {
			 query = "from ProjectTasks pt where pt.projectId.id = :projectId" ;
			 projectTasksList =session.createQuery(query.toString()).setInteger("projectId", projectId.intValue()).setCacheable(true).list(); 
			
		}
	   return projectTasksList;  
	} 
 
}
