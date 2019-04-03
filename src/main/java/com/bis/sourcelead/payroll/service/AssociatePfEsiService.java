package com.bis.sourcelead.payroll.service;

import java.util.List;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.AssociatePfEsiDetailsDTO;
import com.bis.sourcelead.payroll.entity.AssociatePfEsiDetails;

public interface AssociatePfEsiService {

	Response<AssociatePfEsiDetailsDTO> saveAssociatePfEsi(AssociatePfEsiDetailsDTO associatePfEsiDetailsDTO, OrgLocations orgLocation, User loginUser);

	AssociatePfEsiDetails findAssociatePfEsiByCandidateId(Long candidateId, OrgLocations orgLocation);
	
	AssociatePfEsiDetailsDTO getAssociatePfEsiDetailsDTOByCandidateId(Long candidateId, OrgLocations orgLocation);

	List<AssociatePfEsiDetails> getAllPfEsiDetailsByOrgLocationid(Long id) throws Exception;

}
