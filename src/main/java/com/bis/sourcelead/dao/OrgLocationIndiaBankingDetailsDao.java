package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.OrgLocationIndiaBankingDetailsDTO;
import com.bis.sourcelead.entity.OrglocationIndiaBankingDetails;

public interface OrgLocationIndiaBankingDetailsDao {

	OrglocationIndiaBankingDetails saveOrgLocationIndiaBankingDetails(OrglocationIndiaBankingDetails orgLocationIndiaBankingDetails) throws Exception;
	
	OrglocationIndiaBankingDetails getOrglocationIndiaBankingDetailsByUUID(String uuid) throws Exception;
	
	List<OrgLocationIndiaBankingDetailsDTO> getOrglocationIndiaBankingDetailsList(Long orgLocationId) throws Exception;
	
	OrglocationIndiaBankingDetails deleteOrglocationIndiaBankingDetails(OrglocationIndiaBankingDetails orgLocationIndiaBankingDetails) throws Exception;
}
