package com.bis.sourcelead.service;

import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.AssociateSalaryIndDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.AssociateBankErrorData;

public interface AssociateImportService {
	
	AssociateBankErrorData saveAssociateInformation(MultipartFile multipartFile, OrgLocations orgLocation) throws Exception;
	
	AssociateBankErrorData saveImportBankDetails(MultipartFile multipartFile, OrgLocations orgLocation) throws Exception;
	
	ResponseList<AssociateSalaryIndDTO> saveAssociateSalaryDetails(MultipartFile multipartFile,OrgLocations orgLocation, User user);
		
}
