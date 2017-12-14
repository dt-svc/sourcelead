package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.OrglocationBankingDetails;

public interface OrglocationBankingDetailsDao {

	OrglocationBankingDetails saveOrglocationBankingDetails(OrglocationBankingDetails orglocationBankingDetails) throws Exception;
	
	OrglocationBankingDetails getOrglocationBankingDetailsByUUID(String uuid) throws Exception;
	
	OrglocationBankingDetails getOrglocationBankingDetailsByOrgLocationId(Long orgLocationId) throws Exception;
}
