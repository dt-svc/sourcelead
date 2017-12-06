package com.bis.sourcelead.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.GenericDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ProjectsDocumentsDTO;
import com.bis.sourcelead.entity.ProjectDocumentFolder;
import com.bis.sourcelead.entity.ProjectDocuments;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserMailBox;
import com.bis.sourcelead.model.ProjectDocumentBO;
import com.bis.sourcelead.service.ProjectDocumentsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service("projectDocumentsService")
public class ProjectDocumentsServiceImpl implements ProjectDocumentsService {
	
	private  GenericDao<ProjectDocuments> genericDao;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	public void setGenericDao(GenericDao<ProjectDocuments> dao){
		genericDao = dao;
		genericDao.setEntityClass(ProjectDocuments.class);
	}

	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
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
		
		
		if (jsonObj.has("description")) {
			projectDocuments.setDescription(jsonObj.getString("description"));
		}
		Projects project = new Projects();
		project.setId(projectId);
		projectDocuments.setProjects(project);
		projectDocuments.setStatus(Constants.CANDIDATE_ACTIVE);
		if (multiFile != null) {
			projectDocuments.setFileContents(multiFile.getBytes());
			if(documentId != null){
				projectDocuments.setFileName(projectDocuments.getFileName());
			}else{
				projectDocuments.setFileName(multiFile.getOriginalFilename());
			}
			
			projectDocuments.setFileType(multiFile.getContentType());
		}
		 genericDao.updateProjectDocuments(projectDocuments);
		return projectDocuments;
	}

	@Override
	public ProjectDocuments storeProjectDocuments(ProjectsDocumentsDTO projectsDocumentsDTO) throws Exception {
		  User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		ProjectDocuments projectDocuments  = null;
		if(projectsDocumentsDTO.getDocumentId() != null){
			projectDocuments =  genericDao.findById(Long.parseLong(projectsDocumentsDTO.getDocumentId()));
			projectDocuments.setUpdatedBy(user);
			projectDocuments.setUpdatedByName(user.getFullName());
			projectDocuments.setUpdatedDate(commonUtil.currentDate());
		}else{
			 projectDocuments = new ProjectDocuments();
			 projectDocuments.setCreatedBy(user);
			 projectDocuments.setCreatedByName(user.getFullName());
			 projectDocuments.setCreatedDate(commonUtil.currentDate());
		}
		Projects project = new Projects();
		project.setId(Long.parseLong(projectsDocumentsDTO.getProjectId()));
		projectDocuments.setProjects(project);
		ProjectDocumentFolder pdf = new ProjectDocumentFolder();
		pdf.setId(Long.valueOf(projectsDocumentsDTO.getProjectFolderId()));
		projectDocuments.setProjectFolderId(pdf);
		projectDocuments.setStatus(Constants.CANDIDATE_ACTIVE);
		
		if(projectsDocumentsDTO.getFileInfo() != null) {
			FileInfo fileInfo = projectsDocumentsDTO.getFileInfo().get(0);
			projectDocuments.setFileUrl(fileInfo.getFileURL());
			projectDocuments.setFileName(fileInfo.getFileName());
			projectDocuments.setFileType(fileInfo.getContentType());
			String[] fileFormat = fileInfo.getFileName().split(Pattern.quote("."));
			projectDocuments.setFileFormat(fileFormat[1].toString());
			FileUploadUtil.moveFile(projectsDocumentsDTO.getStaticPath(), fileInfo, "ProjectDoument");
			genericDao.updateProjectDocuments(projectDocuments);
		}
		return projectDocuments;
	}

	@Override
	public List<ProjectDocuments> getEachProjectDocumentsListByProjectIdIdAndFolderId(Long projectId, Long folderId) throws Exception {
			return genericDao.getEachProjectDocumentsListByProjectIdIdAndFolderId(projectId, folderId);
	}

	@Override
	public List<ProjectDocuments> getDeleteProjectDocumentsListByDocumentId(String documentid) throws Exception {
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(!isNullOrEmpty(documentid)){
			String[] documentIds = documentid.split(",");
			for(int i=0; i< documentIds.length; i++) {
				ProjectDocuments projectDocuments =  genericDao.getDocumentById(Long.valueOf(documentIds[i]));
				projectDocuments.setStatus(Constants.CANDIDATE_IN_ACTIVE);
				projectDocuments.setUpdatedBy(user);
				projectDocuments.setUpdatedDate(commonUtil.currentDate());
				projectDocuments.setUpdatedByName(user.getFullName());
				genericDao.updateProjectDocuments(projectDocuments);
			}
		}
		return null;
	}

}
