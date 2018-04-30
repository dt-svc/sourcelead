package com.bis.sourcelead.payroll.service;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.AssociateBankDataDTO;

public interface AssociateBankDataService {

	Response<AssociateBankDataDTO> saveAssociateBankData(AssociateBankDataDTO associateBankDataDTO, OrgLocations orgLocation, User loginUser);

	AssociateBankDataDTO findAssociateBankDataByUserId(Long userId, OrgLocations orgLocation);
}
