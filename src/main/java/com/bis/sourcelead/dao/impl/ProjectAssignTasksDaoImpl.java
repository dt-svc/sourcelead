package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ProjectAssignTasksDao;
import com.bis.sourcelead.entity.ProjectAssignTask;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class ProjectAssignTasksDaoImpl implements ProjectAssignTasksDao{
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectAssignTasksDaoImpl.class);

	@Override
	@Transactional
	public ProjectAssignTask saveProjectAssignTask(ProjectAssignTask projectAssignTask) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (projectAssignTask != null) {
				session.saveOrUpdate(projectAssignTask);
			}
			logger.debug("Saving Department");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ProjectAssignTask"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return projectAssignTask;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectAssignTask getProjectAssignTaskById(Long projectTaskId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectAssignTask  projectAssignTask  where projectAssignTask.projectTaskId.id =:projectTaskId and projectAssignTask.status ='1'";
		List<ProjectAssignTask> projectassignTaskList = session.createQuery(query).setLong("projectTaskId", projectTaskId).setCacheable(true).list();
		ProjectAssignTask projectAssignTask = null;
		if (projectassignTaskList != null && projectassignTaskList.size() > 0) {
			projectAssignTask = (ProjectAssignTask) projectassignTaskList.iterator().next();
		}
		return projectAssignTask;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectAssignTask> getProjectAssignTaskListByProjectTaskId(Long projectTaskId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectAssignTask  projectAssignTask  where projectAssignTask.projectTaskId.id =:projectTaskId and projectAssignTask.status ='1'";
		List<ProjectAssignTask> projectassignTaskList = session.createQuery(query).setLong("projectTaskId", projectTaskId).setCacheable(true).list();
		return projectassignTaskList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Long> getProjectAssignTaskListOfLongByProjectTaskId(Long projectTaskId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select projectAssignTask.projectMemberId.id from ProjectAssignTask  projectAssignTask  where projectAssignTask.projectTaskId.id =:projectTaskId and projectAssignTask.status ='1'";
		List<Long> projectassignTaskList = session.createQuery(query).setLong("projectTaskId", projectTaskId).setCacheable(true).list();
		return projectassignTaskList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectAssignTask deleteProjectAssignTask(ProjectAssignTask ProjectAssignTask) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (ProjectAssignTask != null) {
				session.delete(ProjectAssignTask);
			}
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECT_ASSIGN_TASK"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectAssignTask getProjectAssignTaskByIdAndMemberId(Long projectTaskId, Long memberId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectAssignTask  projectAssignTask  where projectAssignTask.projectTaskId.id =:projectTaskId and projectAssignTask.projectMemberId.id =:memberId and projectAssignTask.status ='1'";
		List<ProjectAssignTask> projectassignTaskList = session.createQuery(query).setLong("projectTaskId", projectTaskId).setLong("memberId", memberId).setCacheable(true).list();
		ProjectAssignTask projectAssignTask = null;
		if (projectassignTaskList != null && projectassignTaskList.size() > 0) {
			projectAssignTask = (ProjectAssignTask) projectassignTaskList.iterator().next();
		}
		return projectAssignTask;
	}


	 
}
