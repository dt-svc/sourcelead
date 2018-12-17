package com.bis.sourcelead.service.impl;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectTaskDocumentDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ProjectTaskDocumentDTO;
import com.bis.sourcelead.dto.ProjectTasksDTO;
import com.bis.sourcelead.entity.ProjectTaskDocuments;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ProjectTaskActivityService;
import com.bis.sourcelead.service.ProjectTaskDocumentsService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.PdfConversionUtil;

@Service("projectTaskDocService")
public class ProjectTaskDocumentsServiceImpl implements ProjectTaskDocumentsService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectTaskDocumentsServiceImpl.class);
	
	@Autowired
	ProjectTaskDocumentDao projectTaskDocumentDao;
	
	@Autowired
	ProjectTasksService projectTasksService ;
	
	@Autowired
	private PdfConversionUtil pdfConversionUtil;
	
	@Autowired
	ProjectTaskActivityService projectTaskActivityService;
	
	@Autowired
	UserService userService;
	
	@Override
	public ProjectTaskDocuments updateProjectTaskDocuments(ProjectTaskDocuments projectTaskDoc) throws Exception {
		return projectTaskDocumentDao.updateProjectTaskDocuments(projectTaskDoc);
	}

	@Override
	public List<ProjectTaskDocuments> getProjectTaskDocumentsListByProjectIdId(Long projectId) throws Exception {
		return projectTaskDocumentDao.getProjectTaskDocumentsListByProjectIdId(projectId);
	}
	
	@Override
	public ProjectTaskDocuments getProjectTaskDocumentsById(Long projectTaskDocumentId) throws Exception {
		return projectTaskDocumentDao.getProjectTaskDocumentsById(projectTaskDocumentId);
	}
	 
	@Override
	public ProjectTaskDocuments deleteProjectTaskDocuments(ProjectTaskDocuments projectDocuments) throws Exception {
		return projectTaskDocumentDao.deleteProjectTaskDocuments(projectDocuments);
	}

	
	@Override
	public void storeProjectTaskDocuments(ProjectTaskDocumentDTO projectTaskDocumentDTO)throws Exception {
		User user = userService.getUserById(Long.valueOf(projectTaskDocumentDTO.getLoginUserId()));
		ProjectTaskDocuments projectTaskDoc = null;
		ProjectTaskDocuments oldProjectTaskDoc = null;
		StringBuffer activity = null;
		StringBuffer activitySummary = null;
		if(projectTaskDocumentDTO.getDocumentId() != null) {
			projectTaskDoc =  projectTaskDocumentDao.getProjectTaskDocumentsById(Long.valueOf(projectTaskDocumentDTO.getDocumentId()));
			
			//this code is get the old document data
			 oldProjectTaskDoc =  projectTaskDocumentDao.getProjectTaskDocumentsById(Long.valueOf(projectTaskDocumentDTO.getDocumentId()));
			 activity = new StringBuffer();
		     activitySummary = new StringBuffer();
		     activity.append("Edit Document");	
		}else {
			 projectTaskDoc = new ProjectTaskDocuments();
		}
		if (projectTaskDocumentDTO.getTaskDescription() != null && !projectTaskDocumentDTO.getTaskDescription().isEmpty()) {
				projectTaskDoc.setDescription(projectTaskDocumentDTO.getTaskDescription());
		}else {
			projectTaskDoc.setDescription(null);
		}
		if (projectTaskDocumentDTO.getDocumentName() != null && !projectTaskDocumentDTO.getDocumentName().isEmpty() ) {
			projectTaskDoc.setDocumentName(projectTaskDocumentDTO.getDocumentName());
		}else {
			projectTaskDoc.setDocumentName(null);
		}
		
		ProjectTasks  projectTask = projectTasksService.getTasksId(projectTaskDocumentDTO.getProjectTaskId());
		projectTaskDoc.setProjectTaskId(projectTask);
		projectTaskDoc.setStatus(Constants.CANDIDATE_ACTIVE);
		
		if(projectTaskDocumentDTO.getFileInfo() != null) {
			FileInfo fileInfo = projectTaskDocumentDTO.getFileInfo().get(0);
			if (projectTaskDocumentDTO.getFileInfo() != null) {
				projectTaskDoc.setFileURL(fileInfo.getFileURL());
				projectTaskDoc.setFileName(fileInfo.getFileName());
				projectTaskDoc.setFileType(fileInfo.getContentType());
			}
			FileUploadUtil.moveFile(projectTaskDocumentDTO.getStaticPath(), fileInfo, "tasks");
		}
		
		
		ProjectTaskDocuments projectDoc  = projectTaskDocumentDao.updateProjectTaskDocuments(projectTaskDoc);
		
		
		if(projectTaskDocumentDTO.getDocumentId() != null) {
		     activitySummary.append(oldProjectTaskDoc.getFileName()).append(" to ").append(projectDoc.getFileName());
			 projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),projectTaskDoc.getProjectTaskId(),user);
		}else {
			 activity = new StringBuffer();
		     activitySummary = new StringBuffer();
		     activity.append("New Document");	
		     activitySummary.append(projectDoc.getFileName());
			 projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),projectTaskDoc.getProjectTaskId(),user);
		}
		
	}
	 
	 
	@Override
	public void viewProjectTaskDocument(Long documentId, HttpServletResponse response) throws Exception {
		ProjectTaskDocuments projectTaskDoc = projectTaskDocumentDao.getProjectTaskDocumentsById(documentId);
		 byte[] pdfBytes = null;
			if("application/pdf".equalsIgnoreCase(projectTaskDoc.getFileType())){
				pdfBytes = projectTaskDoc.getFileContents();
			}
			else{
				 pdfBytes = pdfConversionUtil.convertDocToPdf(projectTaskDoc.getFileContents());
			}
			 response.setHeader("Content-disposition","inline; filename=contractPdf.pdf");
		  	    response.setContentType("application/pdf");
		  	    ServletOutputStream sos = response.getOutputStream();
		  	    if(null != pdfBytes){
		  	    	response.setContentLength(pdfBytes.length);
		  	    	sos.write(pdfBytes);
		  	    }
		          sos.flush();
		          sos.close();
		
	}

	@Override
	public void deleteProjectTaskDocumentByPorjectTaskDocumentId(Long porjectTaskDocumentId) throws Exception {
		ProjectTaskDocuments projectTaskDoc = projectTaskDocumentDao.getProjectTaskDocumentsById(porjectTaskDocumentId);
		if(projectTaskDoc != null) {
			projectTaskDocumentDao.deleteProjectTaskDocuments(projectTaskDoc);
		}
		
	}

	

	
}
