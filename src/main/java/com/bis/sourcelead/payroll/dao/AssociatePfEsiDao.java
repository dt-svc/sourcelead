package com.bis.sourcelead.payroll.dao;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.AssociateBankDataDTO;
import com.bis.sourcelead.payroll.dto.AssociatePfEsiDetailsDTO;
import com.bis.sourcelead.payroll.entity.AssociatePfEsiDetails;

public interface AssociatePfEsiDao {

	AssociatePfEsiDetails saveAssociatePfEsi(AssociatePfEsiDetails associatePfEsiDetails);

	AssociatePfEsiDetails findAssociatePfEsiByCandidateId(Long candidateId, OrgLocations orgLocation);

	AssociatePfEsiDetails findAssociatePfEsiId(Long id);

	List<AssociatePfEsiDetails> getAllPfEsiDetailsByOrgLocationid(Long id);

	int saveAssociateInformation(Long id, List<AssociatePfEsiDetailsDTO> list, Map<String, Long> canMap);
}
