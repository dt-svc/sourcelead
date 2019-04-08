package com.bis.sourcelead.web;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.UploadedFile;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.AssociateBankErrorData;
import com.bis.sourcelead.payroll.service.AssociateBankDataService;
import com.bis.sourcelead.payroll.service.AssociateContributionService;
import com.bis.sourcelead.service.UploadFileService;
import com.bis.sourcelead.util.SessionMaintainanceData;
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
	
	@Autowired
	AssociateBankDataService associateBankDataService;
	
	@Autowired
	AssociateContributionService associateContributionService;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
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
	
	
	
	@RequestMapping(value = "/downloadAssociateBankDetailsTemplate", method = RequestMethod.GET)
	public void downloadAssociateBankDetailsTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		File file = uploadFileService.getAssociateBankDetailsTemplate();
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
	
	@RequestMapping(value = "/downloadAssociateDeductionContribitionDetailsTemplate", method = RequestMethod.GET)
	public void  downloadAssociateDeductionContribitionDetailsTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations"); 
		File file = uploadFileService.getAssociateDedAndContriDetailsTemplate(orgLocations);
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
	 * Returns File upload jsp
	 * 
	 * @param request
	 * @return ModelAndView
	 */
	@PreAuthorize("hasRole('ROLE_USER_BULK_IMPORTS')")
	@RequestMapping(value = "/uploadAssociateBankForm")
	public ModelAndView fileUploadForm(HttpServletRequest request) {
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession()
				.getAttribute("sessionData");
		if (sessionData == null) {
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Users);

		request.getSession().setAttribute("sessionData", sessionData);
		ModelAndView model = new ModelAndView();
		model.setViewName("upload/uploadAssociateBankForm");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;

	}
	
	
	@PreAuthorize("hasRole('ROLE_USER_BULK_IMPORTS')")
	@RequestMapping(value = "/uploadAssociateDeductionContributionDetails")
	public ModelAndView fileUploadFor(HttpServletRequest request) {
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession()
				.getAttribute("sessionData");
		if (sessionData == null) {
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Users);

		request.getSession().setAttribute("sessionData", sessionData);
		ModelAndView model = new ModelAndView();
		model.setViewName("upload/uploadAssociateDeductionContributionDetails");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;

	}
	
	@RequestMapping(value = {"/saveAssociateBankDetailTemplate","/rest/saveAssociateBankDetailTemplate"}, method = RequestMethod.POST)
	@ResponseBody
	public AssociateBankErrorData saveAssociateBankDetailTemplate(MultipartHttpServletRequest request) throws Exception
	{
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    MultipartFile multipartFile = null;
			Iterator<String> itrator = request.getFileNames();
			if (itrator.hasNext()) {
				multipartFile = request.getFile(itrator.next());
			}
			AssociateBankErrorData associateBankData = associateBankDataService.saveAssociateBankData(multipartFile, orgLocations);
		    return associateBankData;
	}
	
	@RequestMapping(value = {"/saveAssociateDedAndContriDetailTemplate",
			"/rest/saveAssociateDedAndContriDetailTemplate"}, method = RequestMethod.POST)
	@ResponseBody
	public Response<String> saveAssociateDedAndContriDetailTemplate(MultipartHttpServletRequest request) throws Exception
	{
		Response<String> res = new Response<>();
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    MultipartFile multipartFile = null;
			Iterator<String> itrator = request.getFileNames();
			if (itrator.hasNext()) {
				multipartFile = request.getFile(itrator.next());
			}
			String str = associateContributionService.saveAssociateDedAndContriData(multipartFile, orgLocations);
		    return res;
	}
	
}
