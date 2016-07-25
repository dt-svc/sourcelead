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
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.UploadedFile;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.UploadFileService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.validation.FileValidator;

@Controller
public class UploadController {

	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	@Autowired
	FileValidator fileValidator;

	@Autowired
	UploadFileService uploadFileService;

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
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		try {
			String isFileCreated = uploadFileService.uploadFile(uploadedFile.getFile(), orgLocations,user,uploadedFile.getEmail());
			if (isFileCreated.equalsIgnoreCase("FailureUsersForCheck.xls")) {
				model.addAttribute("errorMessage", "Please download Error file here  …");
				return "upload/uploadForm";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("msg", "successfully uploaded");
		return "redirect:/usersList";
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void downloadFile(HttpServletResponse response) throws IOException {
		File file = new File("FailureUsersForCheck.xls");
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		response.getOutputStream().close();
		response.getOutputStream().flush();
	}

	@RequestMapping(value = "/downloadCreateUserTemplate", method = RequestMethod.GET)
	public void downloadCreateUserTemplate(HttpServletResponse response) throws IOException, URISyntaxException {

		String fn = "/templates/Import Users.xlsx";
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
		response.getOutputStream().close();
		response.getOutputStream().flush();
	}
	
	//cancelImport
	
	@RequestMapping(value="/cancelImport", method = RequestMethod.GET)
	public ModelAndView cancelImport() throws IOException {
		ModelAndView view = new ModelAndView("redirect:/timesheetexcel/excelDownload");
		return view;
		
	}
	
}
