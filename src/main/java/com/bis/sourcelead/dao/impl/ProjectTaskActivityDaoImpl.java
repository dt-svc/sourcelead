package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
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
import com.bis.sourcelead.dao.AssetDao;
import com.bis.sourcelead.dao.DepartmentDao;
import com.bis.sourcelead.dao.ProjectTaskActivityDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.ProjectTaskActivity;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class ProjectTaskActivityDaoImpl implements ProjectTaskActivityDao{
	
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectTaskActivityDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectTaskActivity saveProjectTaskActivity(ProjectTaskActivity projectTaskActivity) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (projectTaskActivity != null) {
				session.saveOrUpdate(projectTaskActivity);
			}
			logger.debug("Saving ProjectTaskActivity");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECT_TASK_ACTIVITY"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return projectTaskActivity;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<ProjectTaskActivity> getProjectTaskActivityByProjectTaskId(Long projectTaskId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectTaskActivity activity  where projectTaskId.id =:projectTaskId ORDER BY activity.createdDate DESC"; 
		List<ProjectTaskActivity> activityList = session.createQuery(query).setLong("projectTaskId", projectTaskId).list();
		return activityList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<ProjectTaskActivity> getTasksActitivtyListByLookupIdAndCategoryType(Long lookupId, String taskType)throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "";
		List<ProjectTaskActivity> activityList = null;
		if(taskType.equalsIgnoreCase(Constants.TASKPRIORITY)) {
			query = "from ProjectTaskActivity activity  where activity.priority.id =:lookupId "; 
			activityList = session.createQuery(query).setLong("lookupId", lookupId).list();
		}else if(taskType.equalsIgnoreCase(Constants.TASKSTATUS)) {
			query = "from ProjectTaskActivity activity  where activity.taskStatus.id =:lookupId";
			activityList = session.createQuery(query).setLong("lookupId", lookupId).list();
		}
		return activityList;
	}
	
}
