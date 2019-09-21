package com.bis.sourcelead.immigration.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.ImmigrationListViewDTO;
import com.bis.sourcelead.dto.ImmigrationPassportDTO;
import com.bis.sourcelead.dto.ImmigrationVisaDTO;
import com.bis.sourcelead.dto.LcaDTO;
import com.bis.sourcelead.dto.LcaDetailsDTO;
import com.bis.sourcelead.dto.LcaListViewDTO;
import com.bis.sourcelead.dto.LcaReportViewDTO;
import com.bis.sourcelead.dto.MyImmigrationViewDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateFamilyDetails;
import com.bis.sourcelead.entity.CandidateIdendities;
import com.bis.sourcelead.entity.CandidateLcaJobDetails;
import com.bis.sourcelead.entity.CandidatePassport;
import com.bis.sourcelead.entity.CandidateResidence;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.CandidateVisa;
import com.bis.sourcelead.entity.CandidateVisaHistory;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationActivityHistory;
import com.bis.sourcelead.entity.ImmigrationCandidateDocuments;
import com.bis.sourcelead.entity.ImmigrationCandidateDocumentsHistory;
import com.bis.sourcelead.entity.ImmigrationCandidateForms;
import com.bis.sourcelead.entity.ImmigrationDashboardView;
import com.bis.sourcelead.entity.ImmigrationListExportView;
import com.bis.sourcelead.entity.ImmigrationTasks;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.Lca;
import com.bis.sourcelead.entity.LcaDocumentHistory;
import com.bis.sourcelead.entity.LcaHistory;
import com.bis.sourcelead.entity.LcaListExportView;
import com.bis.sourcelead.entity.LcaPlaceOfEployment;
import com.bis.sourcelead.entity.LcaVisaClarifications;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.ImmigrationReportViewDTO;

/**
 * @author Srinivas.Sabbu
 *
 */
public interface ImmigrationCommonDao {

	List<ImmigrationTemplate> getImmigrationTemplateList(OrgLocations orgLocations, Long status);

	Immigration savePetitionDetails(Immigration immigration, Candidate candidate,
			CandidateIdendities candidateIdendities, CandidateResumes candidateResume,
			CandidateLcaJobDetails candidateLcaJobDetails, List<CandidateResidence> residenceEntityLst)
			throws Exception;

	Immigration getImmigrationDetails(String immigrationId);

	String getPetitionTypeById(Long petitionType, String petitionTypeConstant);

	ImmigrationCandidateDocuments getImmigrationCandidateDocsById(Long id, Long candidateId, Long immigrationId)
			throws Exception;

	CandidateLcaJobDetails getCandidateLcaJobDetails(Long id);

	List<LcaHistory> getLcaHistoryDetails(Long id, String yes);

	String saveCandidateFamilyMemberDetails(CandidateFamilyDetails familyDetails) throws Exception;

	// visa saving
	CandidateVisa saveVisaDetails(CandidateVisa candidateVisa) throws Exception;

	// passport saving
	CandidatePassport savePassportDetails(CandidatePassport candidatePassport) throws Exception;

	CandidateResidence saveResidenceDetails(CandidateResidence candidateResidence) throws Exception;

	List<CandidateFamilyDetails> getCandidateFamilyDetails(Long candidateId, String searchVal);

	String deletetCandidateFamilyDetails(Long id);

	Long getImmigrationGeneratedIdByOrgLocationId(OrgLocations orgLocations, Integer generatedIdCount);

	List<CandidateResidence> getCandidatesResidenceListByCandidateIdAndOrgLocationID(Long candidateId,
			String searchValue);

	// visa List
	List<ImmigrationVisaDTO> getCandidateVisaDetails(Long candidateId, String searchValue);

	ImmigrationVisaDTO getCandidateVisaDetailsByCandidateIdAndVisaNumber(Long candidateId, String visaNumber);

	// passport list
	List<CandidatePassport> getCandidatePassportDetails(Long candidateId, String searchValue);
	
	ResponseList<ImmigrationPassportDTO> getAssociatePassportDetails(Long candidateId, OrgLocations orgLocations)throws Exception;

	List<LcaDTO> getLcaHistoryDetailsInfo(Long immigrationId);

	CandidateFamilyDetails getCandidateFamilyDetailsById(Long id);

	// visa delete
	void removeCandidateVisaDetails(Long visaHistoryId);

	// passport delete
	void removeCandidatePassportDetails(Long candidateId, Long id, User user, OrgLocations orgLocations);

	String deleteCandidateResidenceDetails(Long id, Long candidateId);

	CandidateResidence getCandidateResidenceDetailsById(Long id);

	// for Visa Saving
	CandidateVisa getCandidateVisaDetailsById(Long id);

	// for passport saving
	CandidatePassport getCandidatePassportDetailsById(Long id);

	Immigration getImmigrationById(Long id);

	Lca getLcaById(Long id);

	ImmigrationCandidateForms getImmigrationCandidateFormById(Long id);

	public String deletetImmigrationCandidateFormsById(Long id);

	String deleteDocumentHistoryRecordById(Long id);

	public String deletetImmigrationCandidateDocsById(Long id);

	public ImmigrationCandidateDocuments getImmigrationCandidateDocumentById(Long id);

	ImmigrationCandidateDocumentsHistory saveImmigrationCandidateDocumentHistory(
			ImmigrationCandidateDocumentsHistory immigrationCandidateDocumentsHistory) throws Exception;

	ImmigrationCandidateDocuments saveImmigrationCandidateDocs(
			ImmigrationCandidateDocuments immigrationCandidateDocuments) throws Exception;

	ImmigrationCandidateForms saveImmigrationCandidateForms(ImmigrationCandidateForms immigrationCandidateforms)
			throws Exception;

	Immigration getImmigrationByImmigrationId(Long immigrationId) throws Exception;

	List<ImmigrationCandidateDocuments> getImmigrationCandidateDocs(Long immigrationId, Long templateId,
			Long candidateId, String searchVal, String myImmigration);

	List<ImmigrationCandidateForms> getCandidateImmigartionFormsDetails(Long candidateId, Long templateId,
			Long immigrationId, String searchVal, String myImmigration);

	ImmigrationCandidateForms getImmigrationCandidateFormsById(Long id, Long candidateId, Long immigrationId)
			throws Exception;

	ResponseList<ImmigrationListViewDTO> getImmigrationsList(ImmigrationListViewDTO immigrationListDTO,
			String searchValue, OrgLocations orgLocations, Map<String, Long[]> filterVal, String orgTypeVal)
			throws Exception;

	ResponseList<ImmigrationListViewDTO> getImmigrationsListByCountsLink(ImmigrationListViewDTO immigrationListDTO,
			String searchValue, OrgLocations orgLocations) throws Exception;

	String updateUscisTypeDropDown(Immigration immigration);

	ImmigrationDashboardView getImmigrationDashBoardDetails(OrgLocations orgLocations);

	List<ImmigrationCandidateDocumentsHistory> getImmigrationCandidateDocumentHistoryList(Long immigrationId,
			Long candidateId, Long immigrationCanDocid) throws Exception;

	List<ImmigrationCandidateDocumentsHistory> getImmigrationCandidateDocumentHistoryListByImmigrationId(
			Long immigrationId, Long docId) throws Exception;

	ImmigrationCandidateDocumentsHistory getImmigrationCandidateDocumentsHistoryById(Long docHistoryId)
			throws Exception;

	String deleteImmigrationDetailsById(Long immigrationId);

	List<ImmigrationCandidateForms> getImmigrationCandidateFormsByImmigrationId(Long immigrationId);

	Integer getTableRecordCountByCandidateId(String entityName, Long candidateId, String condition);

	Immigration updateImigrationDetatils(Immigration immigration);

	ResponseList<MyImmigrationViewDTO> getMyImmigrationsList(MyImmigrationViewDTO immigrationListDTO,
			OrgLocations orgLocations, Long userId);

	Integer getTableRecordCountByTemplateId(String entityName, Long templateId, String condition);

	void saveImmigrationTasksAndTasks(List<ImmigrationTasks> immigrationTasksList, List<Tasks> tasksList);

	public ImmigrationActivityHistory storeImmigrationActivities(ImmigrationActivityHistory immigrationActivityHistory)
			throws Exception;

	public List<ImmigrationActivityHistory> getImmigrationActivitiesList(Long immigrationId, Long orgLocationId)
			throws Exception;

	Integer getTableRecordCountByImmigrationId(String entityName, Long immigrationId, String condition);

	// visa
	public Boolean validateCandidateVisa(Long candidateId, String idNumber) throws Exception;

	// passport
	public Boolean validateCandidatePassport(Long candidateId, String idNumber) throws Exception;

	public CandidateVisa getCandidateVisaDetailsByIdNumberAndId(String idNumber, Long candidateId, Long id)
			throws Exception;

	public CandidatePassport getCandidatePassportDetailsByIdNumberAndId(String idNumber, Long candidateId, Long id)
			throws Exception;

	Immigration getImmigrationByUUID(String immigrationUUID);

	Long getImmigrationIdByUUID(String immigrationUUID);

	Long getCandidateIdByUUID(String candidateUUID);

	public ImmigrationCandidateForms getImmigrationCandidateFormsDetailsByFormNameAndId(String formName,
			Long candidateId, Long immigrationId, Long id) throws Exception;

	public Boolean validateImmigrationCandidateForms(String formName, Long candidateId, Long immigrationId)
			throws Exception;

	List<ImmigrationListExportView> getImmigrationListExportDetails(OrgLocations orgLocations);

	Long getTemplateDetailsByNameAndOrgLoacationId(String name, OrgLocations orgLocations);

	List<Immigration> getImmigrationsByOrgId(Long id);

	Lca saveLcaDetails(Lca lca, List<LcaPlaceOfEployment> lcaPlaceOfEploymentList,
			List<LcaDocumentHistory> lcaDocumentList) throws Exception;

	LcaVisaClarifications saveLcaVisaClarifications(LcaVisaClarifications lcaVisaClarifications) throws Exception;

	LcaPlaceOfEployment saveLcaPlaceOfEployment(LcaPlaceOfEployment lcaPlaceOfEployment) throws Exception;

	LcaDocumentHistory saveLcaDocumentHistory(LcaDocumentHistory lcaDocumentHistory) throws Exception;

	List<LcaDocumentHistory> getLcaDocumentHistoryListById(Long lcaId);

	List<LcaPlaceOfEployment> getLcaPlaceOfEploymentListById(Long lcaId);

	LcaPlaceOfEployment getLcaPlaceOfEploymentDetailsById(Long id);

	LcaDocumentHistory getLcaDocumentHistoryDetailsById(Long id, Long lcaId);

	ResponseList<LcaListViewDTO> getLcaList(LcaListViewDTO lcaListViewDTO, String status, String lcaStatus,
			String searchValue, OrgLocations orgLocations, Map<String, Long[]> filterVal, String filterByDays)
			throws Exception;

	Long getLcaIdByUUID(String lcaUUID);

	String deleteLcaDetailsById(Long lcaId);

	LcaVisaClarifications getLcaVisaClarificationsByIDAndLcaId(Long lcaId);

	LcaVisaClarifications getLcaVisaClarificationsDetailsById(Long id, Long lcaId);

	List<LcaPlaceOfEployment> getLcaPlaceOfEploymentListByIdAndLcaID(Long lcaId);

	String reActiveLcaById(Long lcaId);

	List<Map<String, String>> getSocCodeWithSOCOccupationTitleList(String name, String socType);

	String getOesSocCodeById(Long id, String socType);

	List<LcaListExportView> getLcaListExportDetails(OrgLocations orgLocations);

	public Lca getLcaDetailsByLcaNoAndId(String lcaNo, Long orgLocationId) throws Exception;

	public Boolean validateLcaNumber(String lcaNo, Long orgLocationId) throws Exception;

	public Lca getLcaDetailsByLcaTitleAndId(String lcaTitle, Long lcaId) throws Exception;

	public Boolean validateLcaTitle(String lcaTitle, Long orgLocationId) throws Exception;

	List<Lca> getAllLcaList(Date currentDate);

	String updateLcaStatusWithExpired(Long lcaId);

	List<Map<String, String>> getLcaNumbersList(String lcaNum, String orgLocationId, String lcaStatus,
			String candidateId);

	LcaHistory saveLcaHistory(LcaHistory lcaHistory);

	public Lca getLcaByLcaId(Long lcaId) throws Exception;

	List<LcaDetailsDTO> getEmployeeByLcaId(Long lcaId, OrgLocations orgLocations) throws Exception;

	void deleteLcaPlaceOfEmpmntDetails(Long remaininglpoeIds[]);

	void deleteLcaDocumentsDetails(Long remaininglcaDocIds[]);

	CandidateLcaJobDetails saveCandidateLcaJobDetails(CandidateLcaJobDetails candidateLcaJobDetails);

	LcaHistory getLcaHistoryByStatus(Long immigrationId, Long candidateId, String isCurrent);

	int updateLcaHistoryStatusByLcaNumber(Long lhId, Long immigrationId, Long candidateId);

	int deleteLcaHistoryDetailsByImmigrationId(Long immigrationId);

	List<String> getAllExpiredCandidatesFromLcaHistoryByLcaId(Long lcaId);

	// immigration reports

	ResponseList<ImmigrationReportViewDTO> getImmigrationReports(ImmigrationReportViewDTO immigrationReportDTO,
			OrgLocations orgLocations);

	ResponseList<ImmigrationReportViewDTO> getImmigrationAttorneyReports(ImmigrationReportViewDTO immigrationReportDTO,
			OrgLocations orgLocations);

	ResponseList<LcaReportViewDTO> getLcaReportList(LcaReportViewDTO lcaReportViewDTO, String status, String lcaStatus,
			String searchValue, OrgLocations orgLocations, Map<String, Long[]> filterVal, String filterByDays,
			String positionsAvaialble) throws Exception;

	public Lca updateAvailablePositionCountByLcaId(Long lcaId) throws Exception;

	CandidateVisaHistory saveVisaHistoaryDetails(CandidateVisaHistory visaHistory);

	CandidateVisa getCandidateVisaDetailsByVisaNumber(String visaNumber);

	CandidateVisaHistory getCandidateVisaHistoryByStatus(Long visaId, String isCurrent);

	ImmigrationVisaDTO getCandidateVisaDetailsByVisaHistoryId(Long visaHistoryId);

	int updateCanVisaHistoryDetails(Long visaHistoryId, Long visaId);

	ImmigrationVisaDTO getCandidateActiveVisaDetailsByVisaNumberAndCountry(Long candidateId, String visaNumber,
			String country);

	ImmigrationVisaDTO getCandidateVisaDetailsByVisaNumberAndCountry(Long candidateId, String visaNumber,
			String country);

	int deleteImmigrationNotesByImmigrationId(Long immigrationId);

	Lca updateLcaAvailablePositionsById(Lca lca);

	public Lca getLcaNumber(String lcaNo) throws Exception;

	List<LcaDTO> getAssociateLcaDetails(Long candidateId);
}
