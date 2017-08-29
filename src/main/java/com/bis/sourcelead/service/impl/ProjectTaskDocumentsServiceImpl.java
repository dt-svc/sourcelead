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
import com.bis.sourcelead.entity.ProjectTaskDocuments;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ProjectTaskActivityService;
import com.bis.sourcelead.service.ProjectTaskDocumentsService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.UserService;
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
	public ProjectTaskDocuments storeProjectTaskDocuments(JSONObject jsonObj, String projectTaskId, MultipartFile multiFile, Long documentId,Long userId) throws Exception {
		User user = userService.getUserById(userId);
		ProjectTaskDocuments projectTaskDoc = null;
		ProjectTaskDocuments oldProjectTaskDoc = null;
		StringBuffer activity = null;
		StringBuffer activitySummary = null;
		if(documentId != null) {
			projectTaskDoc =  projectTaskDocumentDao.getProjectTaskDocumentsById(Long.valueOf(documentId));
			
			//this code is get the old document data
			 oldProjectTaskDoc =  projectTaskDocumentDao.getProjectTaskDocumentsById(Long.valueOf(documentId));
			 activity = new StringBuffer();
		     activitySummary = new StringBuffer();
		     activity.append("Edit Document");	
		}else {
			 projectTaskDoc = new ProjectTaskDocuments();
		}
		
		if (jsonObj.has("description")) {
				projectTaskDoc.setDescription(jsonObj.getString("description"));
		}
		if (jsonObj.has("documentName")) {
			projectTaskDoc.setDocumentName(jsonObj.getString("documentName"));
		}
		
		
		ProjectTasks  projectTask = projectTasksService.getTasksId(projectTaskId);
		projectTaskDoc.setProjectTaskId(projectTask);
		projectTaskDoc.setStatus(Constants.CANDIDATE_ACTIVE);
		
		if (multiFile != null) {
			projectTaskDoc.setFileContents(multiFile.getBytes());
			projectTaskDoc.setFileName(multiFile.getOriginalFilename());
			projectTaskDoc.setFileType(multiFile.getContentType());
		}
		ProjectTaskDocuments projectDoc  = projectTaskDocumentDao.updateProjectTaskDocuments(projectTaskDoc);
		if(documentId != null) {
		     activitySummary.append(oldProjectTaskDoc.getFileName()).append(" to ").append(projectDoc.getFileName());
			 projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),projectTaskDoc.getProjectTaskId(),user);
		}else {
			 activity = new StringBuffer();
		     activitySummary = new StringBuffer();
		     activity.append("New Document");	
		     activitySummary.append(projectDoc.getFileName());
			 projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),projectTaskDoc.getProjectTaskId(),user);
		}
		return projectTaskDoc;
		
	}

	@Override
	public void downloadProjectTaskDocuments(Long porjectTaskDocumentId, HttpServletResponse response) throws Exception {
		ProjectTaskDocuments projectTaskDoc = projectTaskDocumentDao.getProjectTaskDocumentsById(porjectTaskDocumentId);
		if (null != projectTaskDoc) {
			response.setContentType("application/octet-stream");
			StringBuilder fileName = new StringBuilder();
			response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(projectTaskDoc.getFileName()).append("\"").toString());
			InputStream inputStream = new ByteArrayInputStream(projectTaskDoc.getFileContents());
			try {
				OutputStream outStream = response.getOutputStream();
				byte[] buffer = new byte[4096];
				int bytesRead = -1;
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}
				inputStream.close();
				outStream.close();
			} catch (FileNotFoundException e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
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
