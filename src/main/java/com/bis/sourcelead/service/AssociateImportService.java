package com.bis.sourcelead.service;

import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.AssociateBankErrorData;

public interface AssociateImportService {
	
	AssociateBankErrorData saveAssociateInformation(MultipartFile multipartFile, OrgLocations orgLocation) throws Exception;
}
