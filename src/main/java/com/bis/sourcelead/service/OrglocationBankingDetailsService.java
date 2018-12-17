package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.OrgLocationBankingDetailsDTO;
import com.bis.sourcelead.entity.OrglocationBankingDetails;

public interface OrglocationBankingDetailsService {

	OrglocationBankingDetails saveOrglocationBankingDetails(OrglocationBankingDetails orglocationBankingDetails) throws Exception;
	
	OrglocationBankingDetails getOrglocationBankingDetailsByUUID(String uuid) throws Exception;
	
	OrglocationBankingDetails getOrglocationBankingDetailsByOrgLocationId(Long orgLocationId) throws Exception;
	
	List<OrgLocationBankingDetailsDTO> getOrglocationBankingDetailsListByOrgLocationId(Long orgLocationId) throws Exception;
	
	OrglocationBankingDetails deleteOrglocationBankingDetails(OrglocationBankingDetails orglocationBankingDetails) throws Exception;
}
