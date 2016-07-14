package com.bis.sourcelead.service;

import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface UploadFileService {

	public String uploadFile(MultipartFile multipartFile, OrgLocations orgLocations) throws Exception;

}
