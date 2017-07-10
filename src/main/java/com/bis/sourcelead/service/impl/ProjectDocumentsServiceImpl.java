package com.bis.sourcelead.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.GenericDao;
import com.bis.sourcelead.entity.ProjectDocuments;
import com.bis.sourcelead.model.ProjectDocumentBO;
import com.bis.sourcelead.service.ProjectDocumentsService;

@Service("projectDocumentsService")
public class ProjectDocumentsServiceImpl implements ProjectDocumentsService {
	
	private  GenericDao<ProjectDocuments> genericDao;
	
	@Autowired
	public void setGenericDao(GenericDao<ProjectDocuments> dao){
		genericDao = dao;
		genericDao.setEntityClass(ProjectDocuments.class);
	}

	@Override
	public List<ProjectDocumentBO> findDocumentsByProjectId(long projectId) {
		List<ProjectDocuments> projectDocs = genericDao.findByCriteria("projects.id", projectId);
		return projectDocs.stream().map(p -> new ProjectDocumentBO(p.getFileName(), p.getFileType(), p.getFileContents())).collect(Collectors.toList());
	}

	@Override
	public ProjectDocumentBO findDocumentById(long porjectDocumentId) {
		ProjectDocumentBO projectDocumentBO = null;
		ProjectDocuments projectDocument =  genericDao.findById(porjectDocumentId);
		if(projectDocument != null){
			projectDocumentBO = new ProjectDocumentBO(projectDocument.getId(), projectDocument.getFileName(), projectDocument.getFileType(), projectDocument.getFileContents());
		}
		
		return projectDocumentBO;
	}

}
