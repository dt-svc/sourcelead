package com.bis.sourcelead.dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.UsersImport;

/**
 * 
 * @author Rajashekar
 * @date 6th July 2016 
 */

public interface UploadFileDao {
	
	public List<UsersImport> uploadFile(MultipartFile multipartFile) throws Exception;

}
