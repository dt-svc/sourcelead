package com.bis.sourcelead.service.impl;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectDocumentFloderDao;
import com.bis.sourcelead.dto.ProjectsDocumentFolderDTO;
import com.bis.sourcelead.entity.ProjectDocumentFolder;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ProjectDocumentFolderService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class ProjectDocumentFolderServiceImpl implements ProjectDocumentFolderService{

	@Autowired
	HttpServletRequest request;
	
	@Autowired
	ProjectDocumentFloderDao projectDocumentFloderDao;

	 @Autowired
	 private CommonUtil commonUtil;

	@Override
	public ProjectDocumentFolder saveProjectDocumentFolder(ProjectDocumentFolder projectDocumentFolder) throws Exception {
		return projectDocumentFloderDao.saveProjectDocumentFolder(projectDocumentFolder);
	}

	@Override
	public List<ProjectDocumentFolder> getProjectDocumentFolderByProjectId(Long peojectId,Long roleId,String userRoleType,Long candidateId) throws Exception {
		return projectDocumentFloderDao.getProjectDocumentFolderByProjectId(peojectId,roleId,userRoleType,candidateId);
	}

	@Override
	public String saveProjectDocumentFolder(ProjectsDocumentFolderDTO projectsDocumentFolderDTO) throws Exception {
		  User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		   ProjectDocumentFolder pdf = new ProjectDocumentFolder();
		    Projects project = new Projects();
			project.setId(Long.parseLong(projectsDocumentFolderDTO.getProjectId()));
			pdf.setProjectId(project);
			pdf.setFoderName(projectsDocumentFolderDTO.getFolderName());
			pdf.setStatus(String.valueOf(Constants.RECORD_ACTIVE));
			pdf.setCreatedDate(commonUtil.currentDate());
			pdf.setCreatedBy(user);
			projectDocumentFloderDao.saveProjectDocumentFolder(pdf);
			return "success";
	}

	@Override
	public ProjectDocumentFolder getProjectDocumentFolderByDocumentId(Long folderId) throws Exception {
		return projectDocumentFloderDao.getProjectDocumentFolderByDocumentId(folderId);
	}

	@Override
	public ProjectDocumentFolder validateProjectDocumentFolderByFolderIdAndFolderName(Long folder, String folderName)throws Exception {
		return projectDocumentFloderDao.validateProjectDocumentFolderByFolderIdAndFolderName(folder, folderName);
	}

	@Override
	public void deleteProjectFolderByFolderId(Long folderId) throws Exception {
		projectDocumentFloderDao.deleteProjectFolderByFolderId(folderId);
	}

	@Override
	public ProjectDocumentFolder getProjectDocumentFolderByProjectId(Long projectId) throws Exception {
		return projectDocumentFloderDao.getProjectDocumentFolderByProjectId(projectId);
	}
	
	 
	
}
