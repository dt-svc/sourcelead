package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.OrgLocationBankingDetailsDTO;
import com.bis.sourcelead.entity.OrglocationBankingDetails;

public interface OrglocationBankingDetailsDao {

	OrglocationBankingDetails saveOrglocationBankingDetails(OrglocationBankingDetails orglocationBankingDetails) throws Exception;
	
	OrglocationBankingDetails getOrglocationBankingDetailsByUUID(String uuid) throws Exception;
	
	OrglocationBankingDetails getOrglocationBankingDetailsByOrgLocationId(Long orgLocationId) throws Exception;
	
	List<OrgLocationBankingDetailsDTO> getOrglocationBankingDetailsListByOrgLocationId(Long orgLocationId) throws Exception;
	
	OrglocationBankingDetails deleteOrglocationBankingDetails(OrglocationBankingDetails orglocationBankingDetails) throws Exception;
}
