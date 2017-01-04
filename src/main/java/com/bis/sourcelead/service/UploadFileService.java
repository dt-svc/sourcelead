package com.bis.sourcelead.service;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface UploadFileService {

	public String uploadFile(MultipartFile multipartFile, OrgLocations orgLocations, User user, String isMailChecked, Org org, Long roleHierarchyId) throws Exception;

	public File getUserTemplate(Org org, Long long1, Long roleHierarchyId) throws FileNotFoundException;

}
