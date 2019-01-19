package com.bis.sourcelead.service.impl;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.GenericDao;
import com.bis.sourcelead.dao.ProjectDocumentPermissionDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ProjectsDocumentsDTO;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectDocumentFolder;
import com.bis.sourcelead.entity.ProjectDocuments;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.model.ProjectDocumentBO;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.ProjectDocumentsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service("projectDocumentsService")
public class ProjectDocumentsServiceImpl implements ProjectDocumentsService {
	
	private  GenericDao<ProjectDocuments> genericDao;
	
	@Value("${static.path}")
	private String staticPath;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	private ProjectDocumentPermissionDao projectDocumentPermissionDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
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
	public List<ProjectDocuments> getEachProjectDocumentsListByProjectIdIdAndFolderId(Long projectId,Long userRoleId,String userRoleType,Long folderId,Long candidateId) throws Exception {
			return genericDao.getEachProjectDocumentsListByProjectIdIdAndFolderId(projectId, userRoleId, userRoleType, folderId, candidateId);
	}

	@Override
	public List<ProjectDocuments> getDeleteProjectDocumentsListByDocumentId(String documentid) throws Exception {
		if(!isNullOrEmpty(documentid)){
			String[] documentIds = documentid.split(",");
			for(int i=0; i< documentIds.length; i++) {
				projectDocumentPermissionDao.deleteAllProjectDocumentByDocumentId(Long.valueOf(documentIds[i]));
				genericDao.delteEachDocumentById(Long.valueOf(documentIds[i]));
			}
		}
		return null;
	}

	
	
	@Override
	public ProjectDocuments sendProjectDocumentEmail(ProjectsDocumentsDTO projectsDocumentsDTO, User user, OrgLocations loginOrgLocation) throws Exception
	{
		EmailMessage emailMessage = new EmailMessage();
		String emails = null;
		StringBuilder toRecipantsEmailsList = new StringBuilder();
		String signature = "";
		InternetAddress[] iAdressArray = null;
		 
		if (StringUtils.isNotBlank(projectsDocumentsDTO.getSendTo())) {
			String jsonArray = projectsDocumentsDTO.getSendTo();
			emails = jsonArray.toString();
			if (null != emails) {
				emails = emails.replaceAll("\\[", "").replaceAll("\\]", "");
				emails = emails.replaceAll("\"", "");
			}
			String[] recipantsEmails = emails.split(",");
			for (int i = 0; i < recipantsEmails.length; i++) {
				if (validateEmail(recipantsEmails[i])) {
					toRecipantsEmailsList = toRecipantsEmailsList.append(recipantsEmails[i]);
					toRecipantsEmailsList.append(",");
				}
			}
			emails = toRecipantsEmailsList.toString();
			emails = emails.substring(0, emails.length() - 1);
		}
		StringBuilder tomaillBuilder = new StringBuilder();
		if (null != emails) {
			iAdressArray = InternetAddress.parse(tomaillBuilder.append(emails).toString());
		}
		
		emailMessage.setFromName(user.getFullName());
		emailMessage.setFirstName(user.getFullName());
		emailMessage.setToEmail(projectsDocumentsDTO.getSendTo());
		emailMessage.setFromEmail(user.getEmail());
		emailMessage.setCreatedBy(user.getUserCode());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL); 
		
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		globalVarsMap.put("body", projectsDocumentsDTO.getMessage());
		globalVarsMap.put("subject", projectsDocumentsDTO.getSubject());
		EmailScenario emailScenario = null;
		 if(loginOrgLocation != null)
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.MAIL_SEND_PROJECT_DOCUMENTS,Constants.ENTITY_TYPE_EMAIL, loginOrgLocation.getId());
			  if(emailScenario == null){
					emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_SEND_PROJECT_DOCUMENTS,Constants.ENTITY_TYPE_EMAIL);
			   }
			  
			  
			//covert subject,body and signature
				 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
				 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
				  
		// construct HTML template using required data
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body, signature, sourceleadUrl, loginOrgLocation.getOrg(), loginOrgLocation);
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);
		emailMessage.setOrgLocationId(loginOrgLocation.getId());
		emailMessage.setEmailType(Constants.MAIL_SEND_PROJECT_DOCUMENTS);
		ArrayList<EmailAttachment> attachmentList = new ArrayList<EmailAttachment>();
		EmailAttachment emailAttachment = new EmailAttachment();
		File file = null;
		if (!isNullOrEmpty(projectsDocumentsDTO.getDocumentId())) {
			String[] documentIds = projectsDocumentsDTO.getDocumentId().split(",");
			String path = StringUtils.removeEnd(staticPath, "/slead-static");
			if (documentIds.length > 1) {
				file = File.createTempFile("ProjectDocuments", ".zip");
				FileOutputStream fos = new FileOutputStream(file);
				ZipOutputStream zos = new ZipOutputStream(fos);
				for (int j = 0; j < documentIds.length; j++) {
					ProjectDocuments projectDocument = getDocumentById(Long.valueOf(documentIds[j]));
					try {
						byte[] buffer = new byte[128];
						File currentFile = new File(path + projectDocument.getFileUrl());
						if (!currentFile.isDirectory()) {
							ZipEntry entry = new ZipEntry(currentFile.getName());
							FileInputStream fis = new FileInputStream(currentFile);
							zos.putNextEntry(entry);
							int read = 0;
							while ((read = fis.read(buffer)) != -1) {
								zos.write(buffer, 0, read);
							}
							zos.closeEntry();
							fis.close();
						}
					} catch (FileNotFoundException e) {
						System.out.println("File not found : " + e);
					}
				}
				zos.close();
				fos.close();
				attachmentList = new ArrayList<EmailAttachment>();
				emailAttachment.setName("Project Documents");
				emailAttachment.setDocFile(commonUtil.readBytesFromFile(file.getPath()));
				attachmentList.add(emailAttachment);
			}
			else {
				ProjectDocuments projectDocument = getDocumentById(Long.valueOf(projectsDocumentsDTO.getDocumentId()));
				emailAttachment.setName(projectDocument.getFileName());
				emailAttachment.setFileUrl(projectDocument.getFileUrl());
				emailAttachment.setType(projectDocument.getFileType());
				emailAttachment.setDocFile(commonUtil.readBytesFromFile(path
						+ projectDocument.getFileUrl()));
				attachmentList.add(emailAttachment);
			}
		}
		// calling the sendEmail method of emailScenerioUtil
		emailScenerioUtil.sendEmail(emailMessage, loginOrgLocation.getId(), iAdressArray, attachmentList);
		return null;
	}
	
	private boolean validateEmail(String email)
	{
		Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	@Override
	public Boolean validateProjectDocumentFolderNameByProjectIdIdAndFolderName(Long projectId, String folderName) throws Exception {
		return genericDao.validateProjectDocumentFolderNameByProjectIdIdAndFolderName(projectId, folderName);
	}

	@Override
	public Boolean validateProjectDocumentNameByProjectIdIdAndDocumentName(Long projectId, String documentName) throws Exception {
		return genericDao.validateProjectDocumentNameByProjectIdIdAndDocumentName(projectId, documentName);
	}
}
