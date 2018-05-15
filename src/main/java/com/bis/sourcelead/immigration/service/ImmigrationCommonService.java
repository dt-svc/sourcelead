package com.bis.sourcelead.immigration.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.CandidateFamilyDetailsDTO;
import com.bis.sourcelead.dto.ImmigrationDTO;
import com.bis.sourcelead.dto.ImmigrationListViewDTO;
import com.bis.sourcelead.dto.ImmigrationPassportVisaDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateDocDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateFormDTO;
import com.bis.sourcelead.dto.MyImmigrationViewDTO;
import com.bis.sourcelead.dto.ResidenceDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateFamilyDetails;
import com.bis.sourcelead.entity.CandidatePassportVisa;
import com.bis.sourcelead.entity.CandidateResidence;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationCandidateDocuments;
import com.bis.sourcelead.entity.ImmigrationCandidateDocumentsHistory;
import com.bis.sourcelead.entity.ImmigrationCandidateForms;
import com.bis.sourcelead.entity.ImmigrationDashboardView;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

/**
 * @author Srinivas.Sabbu
 *
 */
public interface ImmigrationCommonService {
	
	List<ImmigrationTemplate> getImmigrationTemplateList(OrgLocations orgLocations);

	Immigration savePetitionDetails(ImmigrationDTO immigrationDTO, User user, OrgLocations orgLocations, String[] ids, String status, String sendRequest) throws Exception;

	ImmigrationDTO getImmigrationDetails(String immigrationId, OrgLocations orgLocations);
	
	String saveCandidateFamilyMemberDetails(CandidateFamilyDetailsDTO familyDetailsDTO, OrgLocations orgLocations, User user) throws Exception;

	CandidatePassportVisa savePassportDetails(ImmigrationPassportVisaDTO immigrationPassportVisaDTO, User user, OrgLocations orgLocations)throws Exception;
	
	CandidateResidence saveResidenceDetails(ResidenceDTO residenceDTO, OrgLocations orgLocations, User user, Long candidateId) throws Exception;

	List<CandidateFamilyDetails> getCandidateFamilyDetails(Long candidateId, String searchVal);

	String deletetCandidateFamilyDetails(Long id);
	
	String deletetImmigrationCandidateDocsById(Long id);
	
	public String getImmigrationIdForGeneratedId(OrgLocations orgLocations);
	 
	ImmigrationCandidateDocuments getImmigrationCandidateDocumentById(Long id);
	 
	ImmigrationCandidateDocuments uploadRequstedDocForCandidate(ImmigrationTemplateDocDTO immigrationTemplateDocDTO,Long id,Long candidateId,Long immigrationId,User user, OrgLocations orgLocations)throws Exception;
	
	List<CandidateResidence> getCandidatesResidenceListByCandidateIdAndOrgLocationID(Long candidateId, String searchVal);
	
	List<CandidatePassportVisa> getCandidatePassportVisaDetails(Long candidateId,String idType, String searchValue);
	
	CandidateFamilyDetails getCandidateFamilyDetailsById(Long id);

	 void removeCandidatePassportVisaDetails(Long candidateId, Long id, User user, OrgLocations orgLocations, String idType);
	 
	 String deleteCandidateResidenceDetails(Long id,Long candidateId);
	 
	 CandidateResidence getCandidateResidenceDetailsById(Long id);
	 
	 ImmigrationCandidateForms getImmigrationCandidateFormById(Long id);
		
	 String deletetImmigrationCandidateFormsById(Long id);
	 
	 String deleteDocumentHistoryRecordById(Long id);
	 
	 CandidatePassportVisa getCandidatePassportVisaDetailsById(Long id);
	 
	 ImmigrationCandidateDocuments saveImmigrationCandidateDocs(ImmigrationTemplateDocDTO immigrationTemplateDocDTO,User user, OrgLocations orgLocations) throws Exception;

	 Immigration getImmigrationByImmigrationId(Long immigrationId) throws Exception;
	 
	 List<ImmigrationCandidateDocuments> getImmigrationCandidateDocs(Long immigrationId,Long templateId, Long candidateId,User user,OrgLocations orgLocations, String searchVal) throws Exception;

	List<ImmigrationCandidateForms> getCandidateImmigartionFormsDetails(Long candidateId, Long templateId, Long immigrationId, String searchVal);

	ImmigrationCandidateForms saveImmigrationCandidateForms(ImmigrationTemplateFormDTO immigrationTemplateFormDTO, User user, OrgLocations orgLocations) throws Exception;

	ImmigrationCandidateForms uploadRequstedFormForCandidate(ImmigrationTemplateFormDTO immigrationTemplateFormDTO, Long id,	Long candidateId, Long immigrationId, User user, OrgLocations orgLocations);

	ResponseList<ImmigrationListViewDTO> getImmigrationsList(ImmigrationListViewDTO immigrationListDTO, String searchValue,  OrgLocations orgLocations, Map<String, Long[]> filterVal, String orgTypeVal) throws Exception;

	String updateUscisTypeDropDown(Long templateId, Long uscisId,Long uscisOldId,Long userId);

	ImmigrationDTO editImmigrationDetails(Long immigrationId, OrgLocations orgLocations);

	ImmigrationDashboardView getImmigrationDashBoardDetails(OrgLocations orgLocations);
	
	List<ImmigrationCandidateDocumentsHistory> getImmigrationCandidateDocumentHistoryList(Long immigrationId,Long candidateId,Long immigrationCanDocid) throws Exception;

	List<ImmigrationCandidateDocumentsHistory> getImmigrationCandidateDocumentHistoryListByImmigrationId(Long immigrationId, Long docId) throws Exception;

	String deleteImmigrationDetailsById(Long immigrationId);

	List<ImmigrationCandidateForms> getImmigrationCandidateFormsByImmigrationId(Long immigrationId);

	Integer getTableRecordCountByCandidateId(String entityName,Long candidateId, String condition);
	
	Immigration getImmigrationById(Long immigrationId);

	Immigration updateImigrationDetatils(Immigration immigration);

	void sendEmailToImmigrationBeneficiary(Immigration immigration, User user, OrgLocations orgLocations) throws Exception;
	
	ResponseList<MyImmigrationViewDTO> getMyImmigrationsList(MyImmigrationViewDTO immigrationListDTO, OrgLocations orgLocations, Long userId) throws Exception;
}
