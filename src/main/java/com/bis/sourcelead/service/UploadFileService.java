package com.bis.sourcelead.service;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UsersImport;

public interface UploadFileService {

	public String uploadFile(MultipartFile multipartFile, OrgLocations orgLocations, User user, String isMailChecked, Org org, Long roleHierarchyId) throws Exception;

	public File getUserTemplate(Org org, Long long1, Long roleHierarchyId) throws FileNotFoundException, Exception;

	public void saveBulkAssociate(UsersImport userImport, OrgLocations orgLocations, User loginUser)throws Exception;

	public void changeDocumentNamesWithWhiteSpacesToUnderScore(String staticPath);

	public File getAssociateBankDetailsTemplate();

	public File getAssociateDedAndContriDetailsTemplate(OrgLocations orgLocations);
}
