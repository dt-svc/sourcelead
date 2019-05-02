package com.bis.sourcelead.web;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.UploadedFile;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.UploadFileService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.validation.FileValidator;
/**
 * Handles uploads
 */
@Controller
@RequestMapping("/app")
public class UploadController {

	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	@Autowired
	FileValidator fileValidator;

	@Autowired
	UploadFileService uploadFileService;
/**
 * Handles upload csv,xls and xlsx
 * @param uploadedFile
 * @param result
 * @param model
 * @param request
 * @return
 */
	@RequestMapping("/fileUpload")
	public String fileUploaded(@ModelAttribute("uploadedFile") UploadedFile uploadedFile, BindingResult result,
			Model model, HttpServletRequest request) {
		if(uploadedFile.getFile() != null){
			fileValidator.validate(uploadedFile, result);
		}else{
			return "upload/uploadForm";
		}
		if (result.hasErrors()) {
			return "upload/uploadForm";
		}
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");	
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		try {
			String isFileCreated = uploadFileService.uploadFile(uploadedFile.getFile(), orgLocations,user,uploadedFile.getEmail(),org,roleHierarchyId);
			if ("FailureAssociatesForCheck.xlsx".equalsIgnoreCase(isFileCreated)) {
				model.addAttribute("errorMessage", "Please download Error file here");
                 model.addAttribute("fileName", "xlsx");
				return "upload/uploadForm";
			}else if("FailureAssociatesForCheck.xls".equalsIgnoreCase(isFileCreated)){
				model.addAttribute("errorMessage", "Please download Error file here");
                model.addAttribute("fileName", "xls");
				return "upload/uploadForm";
			}
		} catch (Exception e) {
			logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));	
		}
		model.addAttribute("msg", "successfully uploaded");
		 model.addAttribute("fileName", "");
		 SourceLeadSessionManager.setSessionAttribute(request, "userCreateMsg", "Success !!New Associate Created");
		return "redirect:/app/usersList";
	}
/**
 * Handles Errored file Download
 * @param fileName
 * @param response
 * @throws IOException 
 * @throws Exception 
 */
	@RequestMapping(value = "/download/{fileName}", method = RequestMethod.GET)
	public void downloadFile(@PathVariable String fileName,HttpServletResponse response) throws IOException {
		
			File file;
			if("xlsx".equalsIgnoreCase(fileName)){
				file = new File("FailureAssociatesForCheck.xlsx");
			}else{
				 file = new File("FailureAssociatesForCheck.xls");
			}
			
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			StringBuilder fileNameSB = new StringBuilder();
			response.setHeader("Content-Disposition", fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			inputStream.close();
			response.getOutputStream().close();
			response.getOutputStream().flush();	
	}
	
	/**
	 * Handles Temaplate download
	 * 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadCreateUserTemplate", method = RequestMethod.GET)
	public void downloadCreateUserTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
		File file = uploadFileService.getUserTemplate(org, orgLocations.getId(), roleHierarchyId);
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		StringBuilder fileNameSB = new StringBuilder();
		response.setHeader("Content-Disposition", fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();
	}
	
}
