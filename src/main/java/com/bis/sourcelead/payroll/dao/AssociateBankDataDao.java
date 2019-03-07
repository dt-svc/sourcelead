package com.bis.sourcelead.payroll.dao;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.AssociateBankDataDTO;
import com.bis.sourcelead.payroll.entity.AssociateBankData;

public interface AssociateBankDataDao {

	AssociateBankData saveAssociateBankData(AssociateBankData associateBankData);

	AssociateBankData findAssociateBankDataByCandidateId(Long userId, OrgLocations orgLocation);

	AssociateBankData findAssociateBankDataById(long id);

	boolean getisActivePaymentAccountExistedByCandidateId(Long candidateId);
	
	AssociateBankData getAssociateBankDataByOrgLocationId(Long locationId);
	
	void saveAssociateBankingDetailInAssociateBankData(Long id, List<AssociateBankDataDTO> list, Map<String, Long> map) throws Exception;
}
