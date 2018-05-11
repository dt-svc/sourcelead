package com.bis.sourcelead.immigration.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.ImmigrationListViewDTO;
import com.bis.sourcelead.dto.MyImmigrationViewDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateFamilyDetails;
import com.bis.sourcelead.entity.CandidateIdendities;
import com.bis.sourcelead.entity.CandidateLcaI94Summary;
import com.bis.sourcelead.entity.CandidatePassportVisa;
import com.bis.sourcelead.entity.CandidateResidence;
import com.bis.sourcelead.entity.CandidateResumes;
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
public interface ImmigrationCommonDao {

	List<ImmigrationTemplate> getImmigrationTemplateList(OrgLocations orgLocations);
	
	Immigration savePetitionDetails(Immigration immigration, Candidate candidate, CandidateIdendities candidateIdendities,
			CandidateResumes candidateResume, List<CandidateLcaI94Summary> lcaSummaryLst, List<CandidateResidence> residenceEntityLst) throws Exception;

	Immigration getImmigrationDetails(String immigrationId);

	String getPetitionTypeById(Long petitionType, String petitionTypeConstant);
	
	ImmigrationCandidateDocuments getImmigrationCandidateDocsById(Long id, Long candidateId,Long immigrationId) throws Exception;

	CandidateLcaI94Summary getCandidateLCADetails(Long id, String yes);

	String saveCandidateFamilyMemberDetails(CandidateFamilyDetails familyDetails) throws Exception;

	CandidatePassportVisa savePassportDetails(CandidatePassportVisa candidatePassportVisa)throws Exception;

	CandidateResidence saveResidenceDetails(CandidateResidence candidateResidence)	throws Exception;

	List<CandidateFamilyDetails> getCandidateFamilyDetails(Long candidateId, String searchVal);

	String deletetCandidateFamilyDetails(Long id);
	
	Long getImmigrationGeneratedIdByOrgLocationId(OrgLocations orgLocations, Integer generatedIdCount);
	
 	List<CandidateResidence> getCandidatesResidenceListByCandidateIdAndOrgLocationID(Long candidateId,String searchValue);

	List<CandidatePassportVisa> getCandidatePassportVisaDetails(Long candidateId,String idType,String searchValue);
	
	CandidateFamilyDetails getCandidateFamilyDetailsById(Long id);

	void removeCandidatePassportVisaDetails(Long candidateId, Long id, User user, OrgLocations orgLocations, String idType);
	
	 String deleteCandidateResidenceDetails(Long id,Long candidateId);
	 
	 CandidateResidence getCandidateResidenceDetailsById(Long id);
	 
	 CandidatePassportVisa getCandidatePassportVisaDetailsById(Long id);
	 
	 Immigration  getImmigrationById(Long id);
	 
	 ImmigrationCandidateForms getImmigrationCandidateFormById(Long id);
		 
	 public String deletetImmigrationCandidateFormsById(Long id);
	 
	 String deleteDocumentHistoryRecordById(Long id);
	 
	 public String deletetImmigrationCandidateDocsById(Long id);
	 
	 public ImmigrationCandidateDocuments getImmigrationCandidateDocumentById(Long id);
	 
	 ImmigrationCandidateDocumentsHistory saveImmigrationCandidateDocumentHistory(ImmigrationCandidateDocumentsHistory immigrationCandidateDocumentsHistory) throws Exception;
	 
	 ImmigrationCandidateDocuments saveImmigrationCandidateDocs(ImmigrationCandidateDocuments immigrationCandidateDocuments) throws Exception;

	 ImmigrationCandidateForms saveImmigrationCandidateForms(ImmigrationCandidateForms immigrationCandidateforms) throws Exception;
	 
	Immigration getImmigrationByImmigrationId(Long immigrationId) throws Exception;
	
	List<ImmigrationCandidateDocuments> getImmigrationCandidateDocs(Long immigrationId, Long templateId,Long candidateId, String searchVal);

	List<ImmigrationCandidateForms> getCandidateImmigartionFormsDetails(Long candidateId, Long templateId, Long immigrationId, String searchVal);

	ImmigrationCandidateForms getImmigrationCandidateFormsById(Long id, Long candidateId, Long immigrationId) throws Exception;

	ResponseList<ImmigrationListViewDTO> getImmigrationsList(ImmigrationListViewDTO immigrationListDTO,	String searchValue, OrgLocations orgLocations, Map<String, Long[]> filterVal, String orgTypeVal)throws Exception;

	String updateUscisTypeDropDown(Long templateId, Long uscisId);

	ImmigrationDashboardView getImmigrationDashBoardDetails(OrgLocations orgLocations);
	
	List<ImmigrationCandidateDocumentsHistory> getImmigrationCandidateDocumentHistoryList(Long immigrationId,Long candidateId,Long immigrationCanDocid) throws Exception;

	List<ImmigrationCandidateDocumentsHistory> getImmigrationCandidateDocumentHistoryListByImmigrationId(Long immigrationId, Long docId) throws Exception;

	ImmigrationCandidateDocumentsHistory getImmigrationCandidateDocumentsHistoryById(Long docHistoryId)throws Exception;
	
	String deleteImmigrationDetailsById(Long immigrationId);

	List<ImmigrationCandidateForms> getImmigrationCandidateFormsByImmigrationId(Long immigrationId);

	Integer getTableRecordCountByCandidateId(String entityName, Long candidateId, String condition);

	Immigration updateImigrationDetatils(Immigration immigration);

	ResponseList<MyImmigrationViewDTO> getMyImmigrationsList(MyImmigrationViewDTO immigrationListDTO,	OrgLocations orgLocations, Long userId);

}
