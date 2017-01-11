package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.service.impl.TasksDao;

@Repository
public class TasksDaoImpl implements TasksDao {
	
private static final Logger logger = LoggerFactory.getLogger(TasksDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${SourceLead.106}")
	private String duplicateValue;

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Tasks saveTasks(Tasks tasks) throws Exception {
		logger.debug("Storing Tasks");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (tasks != null) {
				session.saveOrUpdate(tasks);
			}
			logger.debug("Tasks Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"Tasks"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return tasks;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Tasks getTasksById(int id) {
		return null;
	}

}
