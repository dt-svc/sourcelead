package com.bis.sourcelead.dao;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

/**
 * 
 * @author Rajashekar
 * @date 6th July 2016
 */

public interface UploadFileDao {

	public String uploadFile(MultipartFile multipartFile, OrgLocations orgLocations, User user,String isMailChecked) throws Exception;
    
	public void downloadFile(String fileName,HttpServletResponse response) throws IOException;
	
	public void downloadCreateUserTemplate(HttpServletResponse response) throws Exception;
}
