package com.bis.sourcelead.service;

import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface CandidateUploadFileService {

	
	
	public String uploadCandidateFile(MultipartFile multipartFile, OrgLocations orgLocations, User user, String isMailChecked, Org org, Long roleHierarchyId) throws Exception;

}
