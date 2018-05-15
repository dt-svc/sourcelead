package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ProjectTaskDocumentDao;
import com.bis.sourcelead.entity.ProjectAssignTask;
import com.bis.sourcelead.entity.ProjectTaskDocuments;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class ProjectTaskDocumentDaoImpl implements ProjectTaskDocumentDao{
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectTaskDocumentDaoImpl.class);
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectTaskDocuments updateProjectTaskDocuments(ProjectTaskDocuments projectTaskDocuments) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try{
			if(projectTaskDocuments != null){
				session.saveOrUpdate(projectTaskDocuments);
			}
		}catch(ConstraintViolationException exception)
		{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECT_TASK_DOCUMENTS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return projectTaskDocuments;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<ProjectTaskDocuments> getProjectTaskDocumentsListByProjectIdId(Long projectTaskId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectTaskDocuments  projectTaskDoc  where projectTaskDoc.projectTaskId.id =:projectTaskId";
		List<ProjectTaskDocuments> projectTaskDocumentList = session.createQuery(query).setLong("projectTaskId", projectTaskId).list();
		return projectTaskDocumentList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectTaskDocuments getProjectTaskDocumentsById(Long projectTaskDocumentId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectTaskDocuments  projectTaskDoc  where projectTaskDoc.Id =:projectTaskDocumentId";
		List<ProjectTaskDocuments> projectTaskDocList = session.createQuery(query).setLong("projectTaskDocumentId", projectTaskDocumentId).list();
		ProjectTaskDocuments projectTaskDoc = null;
		if (projectTaskDocList != null && projectTaskDocList.size() > 0) {
			projectTaskDoc = (ProjectTaskDocuments) projectTaskDocList.iterator().next();
		}
		return projectTaskDoc;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectTaskDocuments deleteProjectTaskDocuments(ProjectTaskDocuments projectDocuments) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (projectDocuments != null) {
				session.delete(projectDocuments);
			}
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECT_TASK_DOCUMENTS"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return null;
	}
}
