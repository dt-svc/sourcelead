package com.bis.sourcelead.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.UploadedFile;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ClientImportsService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.validation.FileValidator;

/**
 * Handles all imports for client and client contacts saving
 * @author Rajashekar
 * @date 12 Sep 2016
 *
 */


@Controller
public class ClientUploadController {
	private static final Logger logger = LoggerFactory.getLogger(ClientUploadController.class);
	
	@Autowired
	ClientImportsService clientImportsService;
	/**
	 * Handles download templates for client and client contacts
	 * @author Rajashekar
	 * @param response
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@RequestMapping(value = "/downloadClientTemplate", method = RequestMethod.GET)
	public void downloadClientTemplate(HttpServletResponse response,@RequestParam(value = "name", required = false) String name) throws IOException, URISyntaxException{
		logger.debug("download template is for : "+ name);
		String fn;	
		    if("client".equalsIgnoreCase(name)){
	    		fn = "/templates/Clients_import_template.xls";
	    	}else{
	    		fn = "/templates/Client contacts_Import.xls";
	    	}
			
			URL url = getClass().getResource(fn);
			File file = new File(url.toURI());

			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			inputStream.close();
			response.getOutputStream().close();
			response.getOutputStream().flush();
		
	}
	/**
	 * Handles saving for client uploads
	 * @param uploadedFile
	 * @param result
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/clientFileUpload")
	public String fileUploaded(@ModelAttribute("uploadedFile") UploadedFile uploadedFile, BindingResult result,Model model, HttpServletRequest request)throws IOException,JSONException {
				
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		MultipartFile multipartFile = uploadedFile.getFile();
		String fileName = multipartFile.getOriginalFilename();
		
		
		clientImportsService.clientBulkSaving(multipartFile,user,orgLocation,org);
		
		return "redirect:/clientDashboard";
		
	}
}
