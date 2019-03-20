package com.bis.sourcelead.payroll.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.AssociateBankDataDTO;
import com.bis.sourcelead.payroll.dto.AssociateBankErrorData;
import com.bis.sourcelead.payroll.entity.AssociateBankData;

public interface AssociateBankDataService {

	Response<AssociateBankDataDTO> saveAssociateBankData(AssociateBankDataDTO associateBankDataDTO, OrgLocations orgLocation, User loginUser);

	AssociateBankDataDTO findAssociateBankDataByUserId(Long userId, OrgLocations orgLocation);

	AssociateBankData getAssociateBankDataByOrgLocationId(Long locationId);
	
	AssociateBankErrorData  saveAssociateBankData(MultipartFile multipartFile, OrgLocations orgLocation) throws Exception;

	List<AssociateBankData> findAllAssociateBankDataByCandidateId(Long candidateId, Long orgLocationId);

	AssociateBankData findAssociateBankDataByUUID(String associateBankUUID, Long orgLocationId);

	boolean changeActiveAccount(Long associateBankId, Long candidateId);

}
