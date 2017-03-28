package com.bis.sourcelead.service;

import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface VendorImportService {

	String vendorBulkSaving(MultipartFile multipartFile, User user, OrgLocations orgLocation, Org org, String email, String responderType)throws Exception;
	
}
