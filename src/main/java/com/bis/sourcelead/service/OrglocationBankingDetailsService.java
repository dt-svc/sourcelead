package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.OrglocationBankingDetails;

public interface OrglocationBankingDetailsService {

	OrglocationBankingDetails saveOrglocationBankingDetails(OrglocationBankingDetails orglocationBankingDetails) throws Exception;
	
	OrglocationBankingDetails getOrglocationBankingDetailsByUUID(String uuid) throws Exception;
	
	OrglocationBankingDetails getOrglocationBankingDetailsByOrgLocationId(Long orgLocationId) throws Exception;
}
