package com.bis.sourcelead.immigration.dao;

import java.util.List;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateFamilyDetails;
import com.bis.sourcelead.entity.CandidateIdendities;
import com.bis.sourcelead.entity.CandidateLcaI94Summary;
import com.bis.sourcelead.entity.CandidatePassportVisa;
import com.bis.sourcelead.entity.CandidateResidence;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationCandidateDocuments;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
/**
 * @author Srinivas.Sabbu
 *
 */
public interface ImmigrationCommonDao {

	List<ImmigrationTemplate> getImmigrationTemplateList(OrgLocations orgLocations);
	
	Immigration savePetitionDetails(Immigration immigration, Candidate candidate, CandidateIdendities candidateIdendities,
			CandidateResumes candidateResume, List<CandidateLcaI94Summary> lcaSummaryLst, List<CandidateResidence> residenceEntityLst) throws Exception;

	Immigration getImmigrationViewList(String immigrationId);

	String getPetitionTypeById(Long petitionType, String petitionTypeConstant);

	CandidateLcaI94Summary getCandidateLCADetails(Long id, String yes);

	String saveCandidateFamilyMemberDetails(CandidateFamilyDetails familyDetails) throws Exception;

	CandidatePassportVisa savePassportDetails(CandidatePassportVisa candidatePassportVisa)throws Exception;

	CandidateResidence saveResidenceDetails(CandidateResidence candidateResidence)	throws Exception;

	List<CandidateFamilyDetails> getCandidateFamilyDetails(Long candidateId);

	String deletetCandidateFamilyDetails(Long id);
	
	List<CandidateResidence> getCandidatesResidenceListByCandidateIdAndOrgLocationID(Long candidateId);

	List<CandidatePassportVisa> getCandidatePassportVisaDetails(Long candidateId,String idType);
	
	CandidateFamilyDetails getCandidateFamilyDetailsById(Long id);

	void removeCandidatePassportVisaDetails(Long candidateId, Long id, User user, OrgLocations orgLocations, String idType);
	
	 String deleteCandidateResidenceDetails(Long id,Long candidateId);
	 
	 CandidateResidence getCandidateResidenceDetailsById(Long id);
	 
	 CandidatePassportVisa getCandidatePassportVisaDetailsById(Long id);
	 
	 Immigration  getImmigrationById(Long id);
	 
	 ImmigrationCandidateDocuments saveImmigrationCandidateDocs(ImmigrationCandidateDocuments immigrationCandidateDocuments) throws Exception;

	Immigration getImmigrationByImmigrationId(Long immigrationId) throws Exception;
	
	List<ImmigrationCandidateDocuments> getImmigrationCandidateDocs(Long immigrationId, Long templateId,Long candidateId);


}
