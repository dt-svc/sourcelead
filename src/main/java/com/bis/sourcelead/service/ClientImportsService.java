package com.bis.sourcelead.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface ClientImportsService {

	String  clientBulkSaving(MultipartFile multipartFile,User user,OrgLocations orgLocation,Org  org,String isMailCheck, String responderType)throws Exception;

	File getVendorContactsTemplate(Long orgLocationID) throws Exception;
	
	File getClientContactsTemplate(Long orgLocationID) throws Exception;
	
}
