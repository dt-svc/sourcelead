package com.bis.sourcelead.immigration.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.bis.sourcelead.dto.CandidateFamilyDetailsDTO;
import com.bis.sourcelead.dto.ImmigrationDTO;
import com.bis.sourcelead.dto.ImmigrationListViewDTO;
import com.bis.sourcelead.dto.ImmigrationPassportDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateDocDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateFormDTO;
import com.bis.sourcelead.dto.ImmigrationVisaDTO;
import com.bis.sourcelead.dto.LcaDTO;
import com.bis.sourcelead.dto.LcaDetailsDTO;
import com.bis.sourcelead.dto.LcaListViewDTO;
import com.bis.sourcelead.dto.LcaReportViewDTO;
import com.bis.sourcelead.dto.MyImmigrationViewDTO;
import com.bis.sourcelead.dto.ResidenceDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateFamilyDetails;
import com.bis.sourcelead.entity.CandidatePassport;
import com.bis.sourcelead.entity.CandidateResidence;
import com.bis.sourcelead.entity.CandidateVisa;
import com.bis.sourcelead.entity.CandidateVisaHistory;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationActivityHistory;
import com.bis.sourcelead.entity.ImmigrationCandidateDocuments;
import com.bis.sourcelead.entity.ImmigrationCandidateDocumentsHistory;
import com.bis.sourcelead.entity.ImmigrationCandidateForms;
import com.bis.sourcelead.entity.ImmigrationDashboardView;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.Lca;
import com.bis.sourcelead.entity.LcaHistory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.ImmigrationReportViewDTO;

/**
 * @author Srinivas.Sabbu
 *
 */
public interface ImmigrationCommonService {
	
	List<ImmigrationTemplate> getImmigrationTemplateList(OrgLocations orgLocations, Long status);

	Immigration savePetitionDetails(ImmigrationDTO immigrationDTO, User user, OrgLocations orgLocations, String[] ids) throws Exception;

	ImmigrationDTO getImmigrationDetails(String immigrationId, OrgLocations orgLocations);
	
	String saveCandidateFamilyMemberDetails(CandidateFamilyDetailsDTO familyDetailsDTO, OrgLocations orgLocations, User user,Long immigrationId) throws Exception;
	
	CandidateVisa saveVisaDetails(ImmigrationVisaDTO immigrationVisaDTO, User user, OrgLocations orgLocations,Long immigrationId,  Long visaHistoryId)throws Exception;
	
	CandidatePassport savePassportDetails(ImmigrationPassportDTO immigrationPassportDTO, User user, OrgLocations orgLocations,Long immigrationId)throws Exception;
	
	CandidateResidence saveResidenceDetails(ResidenceDTO residenceDTO, OrgLocations orgLocations, User user, Long candidateId,Long immigrationId) throws Exception;

	List<CandidateFamilyDetails> getCandidateFamilyDetails(Long candidateId, String searchVal);

	String deletetCandidateFamilyDetails(Long id);
	
	String deletetImmigrationCandidateDocsById(Long id);
	
	public String getImmigrationIdForGeneratedId(OrgLocations orgLocations);
	 
	ImmigrationCandidateDocuments getImmigrationCandidateDocumentById(Long id);
	 
	ImmigrationCandidateDocuments uploadRequstedDocForCandidate(ImmigrationTemplateDocDTO immigrationTemplateDocDTO,Long id,Long candidateId,Long immigrationId,User user, OrgLocations orgLocations)throws Exception;
	
	List<CandidateResidence> getCandidatesResidenceListByCandidateIdAndOrgLocationID(Long candidateId, String searchVal);
	
	List<ImmigrationVisaDTO> getCandidateVisaDetails(Long candidateId, String searchValue);
	
	List<CandidatePassport> getCandidatePassportDetails(Long candidateId,String searchValue);
	
	CandidateFamilyDetails getCandidateFamilyDetailsById(Long id);

	//passport delete
	 void removeCandidateVisaDetails(Long visaHistoryId);
		 
	 //passport delete
	 void removeCandidatePassportDetails(Long candidateId, Long id, User user, OrgLocations orgLocations);
	 
	 String deleteCandidateResidenceDetails(Long id,Long candidateId);
	 
	 CandidateResidence getCandidateResidenceDetailsById(Long id);
	 
	 ImmigrationCandidateForms getImmigrationCandidateFormById(Long id);
		
	 String deletetImmigrationCandidateFormsById(Long id);
	 
	 String deleteDocumentHistoryRecordById(Long id);
	 //visa
	 CandidateVisa getCandidateVisaDetailsById(Long id);
	//passport 
	 CandidatePassport getCandidatePassportDetailsById(Long id);
	 
	 ImmigrationCandidateDocuments saveImmigrationCandidateDocs(ImmigrationTemplateDocDTO immigrationTemplateDocDTO,User user, OrgLocations orgLocations) throws Exception;

	 Immigration getImmigrationByImmigrationId(Long immigrationId) throws Exception;
	 
	 List<ImmigrationCandidateDocuments> getImmigrationCandidateDocs(Long immigrationId,Long templateId, Long candidateId,User user,OrgLocations orgLocations, String searchVal, String myImmigration) throws Exception;

	List<ImmigrationCandidateForms> getCandidateImmigartionFormsDetails(Long candidateId, Long templateId, Long immigrationId, String searchVal, String myImmigration);

	ImmigrationCandidateForms saveImmigrationCandidateForms(ImmigrationTemplateFormDTO immigrationTemplateFormDTO, User user, OrgLocations orgLocations) throws Exception;

	ImmigrationCandidateForms uploadRequstedFormForCandidate(ImmigrationTemplateFormDTO immigrationTemplateFormDTO, Long id,	Long candidateId, Long immigrationId, User user, OrgLocations orgLocations);

	ResponseList<ImmigrationListViewDTO> getImmigrationsList(ImmigrationListViewDTO immigrationListDTO, String searchValue,  OrgLocations orgLocations, Map<String, Long[]> filterVal, String orgTypeVal) throws Exception;

	ResponseList<ImmigrationListViewDTO> getImmigrationsListByCountsLink(ImmigrationListViewDTO immigrationListDTO, String searchValue,  OrgLocations orgLocations) throws Exception;

	
	String updateUscisTypeDropDown(Long templateId, Long uscisId,Long uscisOldId,User user,OrgLocations orgLocations) throws Exception;

	ImmigrationDTO editImmigrationDetails(Long immigrationUUID, OrgLocations orgLocations);

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

	List<ImmigrationActivityHistory> getImmigrationActivitiesList(Long immigrationId,Long orglocationId) throws Exception;
	
	Integer getTableRecordCountByImmigrationId(String entityName,Long immigrationId, String condition);
	
	
	//visa
	 public Boolean validateCandidateVisa(Long candidateId,String idNumVal) throws Exception;
	
	//passport
	public Boolean validateCandidatePassport(Long candidateId,String idNumVal) throws Exception;
	
	
	//visa
	public CandidateVisa getCandidateVisaDetailsByIdNumberAndId(String idNumber, Long candidateId,Long id) throws Exception;
	
	//passport
	public CandidatePassport getCandidatePassportDetailsByIdNumberAndId(String idNumber, Long candidateId,Long id) throws Exception;

	Long getImmigrationIdByUUID(String immigrationUUID);

	Long getCandidateIdByUUID(String candidateUUID);
	
	public ImmigrationCandidateForms getImmigrationCandidateFormsDetailsByFormNameAndId(String formName, Long candidateId,Long immigrationId,Long id) throws Exception;
	
	public Boolean validateImmigrationCandidateForms(String formName, Long candidateId,Long immigrationId) throws Exception;

	Sheet getExcelFile(Sheet createExcelsheet, Workbook createExcelworkbook, OrgLocations orgLocations);
	
	Long getTemplateDetailsByNameAndOrgLoacationId(String name, OrgLocations orgLocations);

	List<Immigration> getImmigrationsByOrgId(Long id);
	
    ResponseList<LcaListViewDTO> getLcasList(LcaListViewDTO lcaListViewDTO, String status,String lcaStatus, String searchValue, OrgLocations orgLocations, Map<String, Long[]> filterVal,String filterByDays) throws Exception;
    
    //Lca method declaration
    
    // below method added to get the LCA list which end dates are lesser than current date used in CRON.
     public List<Lca> getAllLcaList(Date currentDate);
 
    // below method used to the update lca status through cron.
    public String updateLcaStatusWithExpired(Long lcaId);
    
    public List<Map<String, String>> getSocCodeWithSOCOccupationTitleList(String name,String category,String socType);
    
    Lca saveLcaDetails(LcaDTO lcaDTO, User user, OrgLocations orgLocations) throws Exception;
    
    LcaDTO editLcaDetails(Long lcaId,OrgLocations orgLocations);
    
    Long getLcaIdByUUID(String lcaUUID);
    
    String deleteLcaDetailsById(Long lcaId);
    
    String reActiveLcaById(Long lcaId);
    
	String getOesSocCodeById(Long id, String socType);

	
    Sheet getLCAExcelFile(Sheet createExcelsheet, Workbook createExcelworkbook, OrgLocations orgLocations);
    
    public Lca getLcaDetailsByLcaNoAndId(String lcaNo, Long lcaId) throws Exception;
    
    public Boolean validateLcaNumber(String lcaNo, Long orgLocationId) throws Exception;
    
    public Lca getLcaDetailsByLcaTitleAndId(String lcaTitle, Long lcaId) throws Exception;
    
    public Boolean validateLcaTitle(String lcaTitle, Long orgLocationId) throws Exception;
    
    List<Map<String, String>> getLcaNumbersList(String lcaNum,String orgLocationId,String statusLca,String candidateId);
    
    public Lca getLcaByLcaId(Long lcaId) throws Exception;
    
    List<LcaDTO> getLcaHistoryDetailsInfo(Long immigrationId);
    
    List<LcaDetailsDTO> getEmployeeByLcaId(Long lcaId ,OrgLocations orgLocations)throws Exception;
   
    LcaHistory  getLcaHistoryByStatus(Long immigrationId,Long candidateId, String isCurrent);  
    
    LcaHistory saveLcaHistory(LcaHistory lcaHistory);
    
    int updateLcaHistoryStatusByLcaNumber(Long lhId,Long immigrationId,Long candidateId);
    
    int deleteLcaHistoryDetailsByImmigrationId(Long immigrationId);
    
    List<String> getAllExpiredCandidatesFromLcaHistoryByLcaId(Long lcaId);
 //   void deleteLcaPlaceOfEmpmntDetails(Long remaininglpoeIds[]);
    
    // for Immigration reports
    ResponseList<ImmigrationReportViewDTO> getImmigrationReports(ImmigrationReportViewDTO immigrationReportDTO, OrgLocations orgLocations);
    
    ResponseList<ImmigrationReportViewDTO> getImmigrationAttorneyReports(ImmigrationReportViewDTO immigrationReportDTO, OrgLocations orgLocations);
 // for LCA reports
    ResponseList<LcaReportViewDTO> getLcaReportsList(LcaReportViewDTO lcaReportViewDTO, String status,String lcaStatus, String searchValue, OrgLocations orgLocations, Map<String, Long[]> filterVal,String filterByDays,String positionsAvaialble) throws Exception;

	ImmigrationVisaDTO getCandidateVisaDetailsByVisaHistoryId(Long visaHistoryId);

	CandidateVisaHistory saveVisaHistoryDetails(ImmigrationVisaDTO immigrationVisaDTO, User user, OrgLocations orgLocations,
			long immigrationId);
	
	ImmigrationVisaDTO getCandidateVisaDetailsByCandidateIdAndVisaNumber(Long candidateId,String visaNumber);
	ImmigrationVisaDTO getCandidateActiveVisaDetailsByVisaNumberAndCountry(Long candidateId, String visaNumber, String country);
    
	Lca updateLcaAvailablePositionsById(Lca lca);
	public Lca getLcaNumber(String lcaNo) throws Exception;
	
	// below method added to get the CandidatePassport list which end dates are equal to expected expiry date used in CRON.
	public List<CandidatePassport> getAllPassportList(Date expectedExpiryDate)throws Exception;
	
	// below method added to get the CandidateVisaHistory list which end dates are equal to expected expiry date used in CRON.
	public List<CandidateVisaHistory> getAllVisaHistoryList(Date expectedExpiryDate)throws Exception;
	
}
