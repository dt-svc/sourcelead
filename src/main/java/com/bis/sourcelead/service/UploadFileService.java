package com.bis.sourcelead.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface UploadFileService {

	public String uploadFile(MultipartFile multipartFile, OrgLocations orgLocations, User user, String isMailChecked) throws Exception;

	public void downloadFile(String fileName, HttpServletResponse response) throws Exception;
	
	public void downloadCreateUserTemplate(HttpServletResponse response) throws Exception;
}
