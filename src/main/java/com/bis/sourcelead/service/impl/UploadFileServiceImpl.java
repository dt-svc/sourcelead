package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dao.UploadFileDao;
import com.bis.sourcelead.entity.UsersImport;
import com.bis.sourcelead.service.UploadFileService;

/**
 * 
 * @author Rajashekar
 * @date 6th July 2016
 */
@Service
public class UploadFileServiceImpl implements UploadFileService{

	
	@Autowired
	private UploadFileDao uploadFileDao;
	
	@Override
	public List<UsersImport> uploadFile(MultipartFile multipartFile) throws Exception {
		
		return uploadFileDao.uploadFile(multipartFile);
	}

}
