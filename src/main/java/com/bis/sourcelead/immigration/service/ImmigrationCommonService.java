package com.bis.sourcelead.immigration.service;

import java.util.List;

import com.bis.sourcelead.dto.CandidateFamilyDetailsDTO;
import com.bis.sourcelead.dto.ImmigrationDTO;
import com.bis.sourcelead.dto.ImmigrationPassportVisaDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateDocDTO;
import com.bis.sourcelead.dto.ResidenceDTO;
import com.bis.sourcelead.entity.CandidateFamilyDetails;
import com.bis.sourcelead.entity.CandidatePassportVisa;
import com.bis.sourcelead.entity.CandidateResidence;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationCandidateDocuments;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

/**
 * @author Srinivas.Sabbu
 *
 */
public interface ImmigrationCommonService {
	
	List<ImmigrationTemplate> getImmigrationTemplateList(OrgLocations orgLocations);

	Immigration savePetitionDetails(ImmigrationDTO immigrationDTO, User user, OrgLocations orgLocations) throws Exception;

	ImmigrationDTO getImmigrationViewList(String immigrationId, OrgLocations orgLocations);
	
	String saveCandidateFamilyMemberDetails(CandidateFamilyDetailsDTO familyDetailsDTO, OrgLocations orgLocations, User user) throws Exception;

	CandidatePassportVisa savePassportDetails(ImmigrationPassportVisaDTO immigrationPassportVisaDTO, User user, OrgLocations orgLocations)throws Exception;
	
	CandidateResidence saveResidenceDetails(ResidenceDTO residenceDTO, OrgLocations orgLocations, User user, Long candidateId) throws Exception;

	List<CandidateFamilyDetails> getCandidateFamilyDetails(Long candidateId);

	String deletetCandidateFamilyDetails(Long id);
	
	List<CandidateResidence> getCandidatesResidenceListByCandidateIdAndOrgLocationID(Long candidateId);
	
	List<CandidatePassportVisa> getCandidatePassportVisaDetails(Long candidateId,String idType);
	
	CandidateFamilyDetails getCandidateFamilyDetailsById(Long id);

	 void removeCandidatePassportVisaDetails(Long candidateId, Long id, User user, OrgLocations orgLocations, String idType);
	 
	 String deleteCandidateResidenceDetails(Long id,Long candidateId);
	 
	 CandidateResidence getCandidateResidenceDetailsById(Long id);
	 
	 CandidatePassportVisa getCandidatePassportVisaDetailsById(Long id);
	 
	 ImmigrationCandidateDocuments saveImmigrationCandidateDocs(ImmigrationTemplateDocDTO immigrationTemplateDocDTO,User user, OrgLocations orgLocations) throws Exception;



}
