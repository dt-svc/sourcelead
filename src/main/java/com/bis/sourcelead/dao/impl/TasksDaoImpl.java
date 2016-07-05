package com.bis.sourcelead.dao.impl;

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

import com.bis.sourcelead.dao.TasksDao;
import com.bis.sourcelead.entity.Tasks;

@Repository
public class TasksDaoImpl implements TasksDao {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(TasksDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Tasks addTasks(Tasks tasks) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.save(tasks);
		} catch (HibernateException exception) {
		}
		return tasks;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Tasks updateTasks(Tasks tasks) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(tasks);
		return tasks;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Tasks getTasksId(String id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		return (Tasks) session.get(Tasks.class, Long.valueOf(id));
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean deleteTask(String id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Tasks tasks = (Tasks) session.get(Tasks.class, Long.valueOf(id));
		tasks.setStatus("1");
		System.out.println("in timesheetEntriesdddddd " + tasks.getId());
		session.saveOrUpdate(tasks);
		logger.info("Successfully deleted !!!");
		return null;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Tasks> getAllTasks() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Tasks> adTaskList = null;
		Query taskListQuery = session.createQuery("from Tasks");
		adTaskList = taskListQuery.list();
		;
		return adTaskList;
	}

}
