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

import com.bis.sourcelead.dao.AssetDao;
import com.bis.sourcelead.dao.DepartmentDao;
import com.bis.sourcelead.dao.ProjectTaskActivityDao;
import com.bis.sourcelead.dao.ProjectTaskCommentsDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.ProjectTaskActivity;
import com.bis.sourcelead.entity.ProjectTaskComments;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class ProjectTaskCommentsDaoImpl implements ProjectTaskCommentsDao{
	
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectTaskCommentsDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectTaskComments saveProjectTaskComments(ProjectTaskComments projectTaskComments) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (projectTaskComments != null) {
				session.saveOrUpdate(projectTaskComments);
			}
			logger.debug("Saving ProjectTaskComments");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECT_TASK_COMMENTS"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return projectTaskComments;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<ProjectTaskComments> getProjectTaskCommentsByProjectTaskId(Long projectTaskId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectTaskComments comments  where comments.projectTaskId.id =:projectTaskId ORDER BY comments.createdDate DESC"; 
		List<ProjectTaskComments> commentsList = session.createQuery(query).setLong("projectTaskId", projectTaskId).list();
		return commentsList;
	}
	 
}
