package com.bis.sourcelead.service.impl;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dao.UploadFileDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.UploadFileService;

/**
 * 
 * @author Rajashekar
 * @date 6th July 2016
 */
@Service
public class UploadFileServiceImpl implements UploadFileService {

	@Autowired
	private UploadFileDao uploadFileDao;

	@Override
	public String uploadFile(MultipartFile multipartFile, OrgLocations orgLocations, User user,String isMailChecked) throws Exception {

		return uploadFileDao.uploadFile(multipartFile, orgLocations,user, isMailChecked);
	}

}
