package com.bis.sourcelead.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.UploadFile;
import com.bis.sourcelead.entity.UsersImport;
import com.bis.sourcelead.service.UploadFileService;
 

/**
 * Handles requests for the application file upload requests
 */
@RestController
public class FileUploadController {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	@Autowired
	private UploadFileService uploadFileService;

	/**
	 * Upload single file 
	 */
	@RequestMapping(value = "/fileUpload", headers = "content-type=multipart/form-data", method = RequestMethod.POST)
	public @ResponseBody List<UsersImport> uploadFileHandler(@ModelAttribute("FileUploadForm") UploadFile uploadfile) throws Exception{
		MultipartFile multipartFile = uploadfile.getFile();
        return uploadFileService.uploadFile(multipartFile);
	}

}
