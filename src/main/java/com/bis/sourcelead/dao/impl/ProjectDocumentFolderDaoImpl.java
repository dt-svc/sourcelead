package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ProjectDocumentFloderDao;
import com.bis.sourcelead.entity.ProjectDocumentFolder;
import com.bis.sourcelead.entity.ProjectDocuments;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class ProjectDocumentFolderDaoImpl implements ProjectDocumentFloderDao{
	
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectDocumentFolderDaoImpl.class);

	
	@Override
	@Transactional
	public ProjectDocumentFolder saveProjectDocumentFolder(ProjectDocumentFolder projectDocumentFolder) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (projectDocumentFolder != null) {
				session.saveOrUpdate(projectDocumentFolder);
			}
			logger.debug("Saving ProjectDocumentFolder");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ProjectDocumentFolder"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return projectDocumentFolder;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<ProjectDocumentFolder> getProjectDocumentFolderByProjectId(Long projectId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectDocumentFolder pdf  where  pdf.projectId.id =:projectId and pdf.status = '1'"; 
		List<ProjectDocumentFolder> projectDocumentFolderList = session.createQuery(query).setLong("projectId", projectId).list();
		String query1 = "select docs.projectFolderId.id, count(id) from ProjectDocuments docs where docs.status = 1 and docs.projects.id = :projectId group by docs.projectFolderId.id";
		List<Object[]> data = session.createQuery(query1).setLong("projectId", projectId).list();
		if(data != null && !data.isEmpty()) {
			Map<Long, Integer> counts = new HashMap<>();
			for(Object[] o : data) {
				if(o != null && o[0] != null)
					counts.put(Long.parseLong(o[0].toString()), Integer.parseInt(o[1].toString()));
			}
			if(!counts.isEmpty()) {
				for(ProjectDocumentFolder pdf : projectDocumentFolderList) {
					pdf.setCount(counts.containsKey(pdf.getId()) ? counts.get(pdf.getId()) : 0);
				}
			}
		}
		
		return projectDocumentFolderList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectDocumentFolder getProjectDocumentFolderByDocumentId(Long folderId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectDocumentFolder pdf  where pdf.id =:folderId";
		@SuppressWarnings("unchecked")
		List<ProjectDocumentFolder> projectDocumentFolderList = session.createQuery(query).setLong("folderId", folderId).setCacheable(true).list();
		ProjectDocumentFolder projectDocumentFolder = null;
		if(projectDocumentFolderList != null){
			projectDocumentFolder = projectDocumentFolderList.iterator().next();
		}
		return projectDocumentFolder;
	}
	 
}
