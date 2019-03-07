package com.bis.sourcelead.payroll.service;

import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.IndAssociatePaymentAccountDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PaymentAccount;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.AssociateBankDataDTO;
import com.bis.sourcelead.payroll.entity.AssociateBankData;

public interface AssociateBankDataService {

	Response<AssociateBankDataDTO> saveAssociateBankData(AssociateBankDataDTO associateBankDataDTO, OrgLocations orgLocation, User loginUser);

	AssociateBankDataDTO findAssociateBankDataByUserId(Long userId, OrgLocations orgLocation);

	AssociateBankData getAssociateBankDataByOrgLocationId(Long locationId);
	
//	AssociateBankData saveAssociateBankData(AssociateBankDataDTO account, OrgLocations orgLocations) throws Exception;
	public AssociateBankData saveAssociateBankData(MultipartFile multipartFile, OrgLocations orgLocation) throws Exception;
}
