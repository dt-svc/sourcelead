package com.bis.sourcelead.service;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.AmexUploadFile;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface UploadFileService {

	public String uploadFile(MultipartFile multipartFile, OrgLocations orgLocations, User user, String isMailChecked) throws Exception;
	

}
