package com.bis.sourcelead.dao.impl;

import java.util.Collection;
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

import com.bis.sourcelead.dao.ProjectTasksDao;
import com.bis.sourcelead.entity.ProjectTasks;

@Repository
public class ProjectTasksDaoImpl implements ProjectTasksDao {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(ProjectTasksDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectTasks addTasks(ProjectTasks tasks) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.save(tasks);
		} catch (HibernateException exception) {
		}
		return tasks;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectTasks updateTasks(ProjectTasks tasks) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(tasks);
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
		ProjectTasks tasks = (ProjectTasks) session.get(ProjectTasks.class, Long.valueOf(id));
		tasks.setStatus("1");
		System.out.println("in timesheetEntriesdddddd " + tasks.getId());
		session.saveOrUpdate(tasks);
		logger.info("Successfully deleted !!!");
		return null;
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
	public Collection<ProjectTasks> getAllTasksByProjectId(Long projectId) throws Exception {
		// TODO Auto-generated method stub

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectTasks pt where pt.projectId.id = :projectId )" ;
		Collection<ProjectTasks> projectTasksList =session.createQuery(query.toString()).setInteger("projectId", projectId.intValue()) 
			    .list(); 
		return projectTasksList;  
		
	}
	
	

}
