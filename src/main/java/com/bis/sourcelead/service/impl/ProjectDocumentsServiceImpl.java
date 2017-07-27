package com.bis.sourcelead.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.GenericDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateDocuments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectDocuments;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.model.ProjectDocumentBO;
import com.bis.sourcelead.service.ProjectDocumentsService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

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

	@Override
	public List<ProjectDocuments> getProjectDocumentsListByProjectIdId(Long projectId) throws Exception {
		return genericDao.getProjectDocumentsListByProjectIdId(projectId);
	}

	 
	@Override
	public ProjectDocuments getDocumentById(Long porjectDocumentId) throws Exception {
		return genericDao.getDocumentById(porjectDocumentId);
	}

	@Override
	public ProjectDocuments updateProjectDocuments(ProjectDocuments projectDocuments) throws Exception {
		return genericDao.updateProjectDocuments(projectDocuments);
	}

	@Override
	public ProjectDocuments storeProjectDocuments(JSONObject jsonObj, Long projectId, MultipartFile multiFile,Long documentId) throws JSONException, Exception {
		ProjectDocuments projectDocuments  = null;
		if(documentId != null){
			projectDocuments =  genericDao.findById(documentId);
		}else{
			 projectDocuments = new ProjectDocuments();	
		}
		
		if (jsonObj.has("name")) {
			projectDocuments.setFileName(jsonObj.getString("name"));
		}
		if (jsonObj.has("description")) {
			projectDocuments.setDescription(jsonObj.getString("description"));
		}
		Projects project = new Projects();
		project.setId(projectId);
		projectDocuments.setProjects(project);
		projectDocuments.setStatus(Constants.CANDIDATE_ACTIVE);
		if (multiFile != null) {
			projectDocuments.setFileContents(multiFile.getBytes());
			projectDocuments.setFileName(multiFile.getOriginalFilename());
			projectDocuments.setFileType(multiFile.getContentType());
		}
		 genericDao.updateProjectDocuments(projectDocuments);
		return projectDocuments;
	}
	
	
}
