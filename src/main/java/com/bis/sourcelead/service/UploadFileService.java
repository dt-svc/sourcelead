package com.bis.sourcelead.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.UsersImport;

public interface UploadFileService {
	
	public List<UsersImport> uploadFile(MultipartFile multipartFile)throws Exception;

}
