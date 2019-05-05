package com.bis.sourcelead.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.bis.sourcelead.dto.CentralTaxDTO;
import com.bis.sourcelead.dto.PayrollTaxDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.SourceleadTaxChallanDTO;
import com.bis.sourcelead.dto.SourceleadTaxChallanItemDTO;
import com.bis.sourcelead.dto.SourceleadTaxChallanItemSubFormFieldDTO;
import com.bis.sourcelead.dto.SourceleadTaxFormDTO;
import com.bis.sourcelead.dto.SourceleadTaxITDeclarationDTO;
import com.bis.sourcelead.dto.SourceleadTaxITDeclarationItemDTO;
import com.bis.sourcelead.dto.SourceleadTaxITDeclarationSubmissionDTO;
import com.bis.sourcelead.dto.SourceleadTaxSubmissionDocumentDTO;
import com.bis.sourcelead.dto.SourceleadTaxSubmissionMasterDTO;
import com.bis.sourcelead.dto.StateTaxDTO;
import com.bis.sourcelead.dto.helper.Paginator;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProfessionalTax;
import com.bis.sourcelead.entity.SourceleadTaxITDeclarationSubmission;
import com.bis.sourcelead.entity.SourceleadTaxType;
import com.bis.sourcelead.entity.Tds;
import com.bis.sourcelead.entity.User;

public interface TaxService {

	SourceleadTaxType getTaxTypeById(long id);

	List<SourceleadTaxType> getAllTaxesByType(long type, boolean includeInactive);

	List<StateTaxDTO> getStateTaxList(String yearFrequency, long stateId, Paginator paginator);

	long saveCentralTax(CentralTaxDTO centralTaxDTO, User user, OrgLocations orgLocations);

	StateTaxDTO getStateTax(String statetaxuuid);
	
	StateTaxDTO getStateTaxByStateNameAndFinancialYear(String stateName, String financialYear);

	long addStateTax(StateTaxDTO stateTaxDTO);

	ProfessionalTax getProfessionalTaxById(long id);

	List<CentralTaxDTO> getCentralTaxList(String yearFrequency);

	CentralTaxDTO getCentralTax(String centraltaxuuid);
	
	CentralTaxDTO getCentralTaxByTaxType(String taxType);

	Response<String> changeStateTaxStatus(long stateTaxId);

	List<SourceleadTaxFormDTO> getTaxFormTypeList(boolean includeAll);

	List<SourceleadTaxFormDTO> getTaxFormTypeListForSubmission(String financialYear, long orgLocationId);

	long addITDeclarationSection(SourceleadTaxITDeclarationDTO sectionDTO);

	public SourceleadTaxFormDTO getTaxFormType(long taxFormId, boolean includeAll, boolean includeInactive);

	public SourceleadTaxFormDTO getTaxFormType(String uuid, boolean includeAll, boolean includeInactive);

	long addITDeclarationSectionItem(SourceleadTaxITDeclarationItemDTO sectionItemDTO);

	Response<String> changeTaxFormStatus(long id);

	Response<String> changeITDeclarationSectionStatus(long id);

	Response<String> changeITDeclarationSectionItemStatus(long id);

	Response<String> changeCentralTaxStatus(long centralTaxId);

	Tds getTDSById(long id);
	
	List<Tds> getTDSListByFinancialYear(String financialYear);

	Response<Long> submitITDeclaration(SourceleadTaxITDeclarationSubmissionDTO itDeclarationSubmissionDTO,
			User loggedInUser, OrgLocations orgLocations);

	List<SourceleadTaxITDeclarationSubmissionDTO> getITDeclarationSubmissionList(long orgLocationId, String uuid, String financialYear, String searchValue, Paginator paginator);

	SourceleadTaxITDeclarationSubmissionDTO getITDeclarationSubmission(String taxFormUUID, String formUUID, Long orgLocationId);

	void saveUploadedDocuments(List<SourceleadTaxSubmissionDocumentDTO> documentList, String staticPath, Long orgId,
			Long orgLocationId, long submissionId);
	
	void deleteDocumentAttachment(long attachmentId);
	
	void deleteDocument(long documentId);

	List<SourceleadTaxSubmissionDocumentDTO> getUploadedDocumentsBySubmissionId(long submissionId);

	public long addITChallanSectionItem(SourceleadTaxChallanItemDTO sectionItemDTO);

	long addItChallanSection(SourceleadTaxChallanDTO sourceleadTaxSectionDTO);

	public long addSourceleadTaxChallanItemSubFormField(SourceleadTaxChallanItemSubFormFieldDTO optionData,
			long addChallanSectionItemId);

	public SourceleadTaxChallanItemDTO getChallanItemDetails(Long itemRowId);


	 public Response<String> changeChallanItemStatus(long itemId);

	 public Response<String> changeSectionStatus(long sectionId);

	Response<String> changeITDeclarationSubmissionStatus(long id, int status);

	 public SourceleadTaxITDeclarationSubmission getITDeclarationSubmissionDetails(long formId);

     public File exportItDeclarationSubmissionPdf(SourceleadTaxITDeclarationSubmission sourceleadTaxITDeclarationSubmissionDetails, User loginUser,OrgLocations orgLoc,User user);

	  public  User getEmployeeDetails(Long id);

	 public File exportItDeclarationSubmissionExcel(SourceleadTaxITDeclarationSubmission sourceleadTaxITDeclarationSubmissionDetails, User loginUser,OrgLocations orgLoc, User user) throws IOException, Exception;

	List<SourceleadTaxSubmissionMasterDTO> getTaxSubmissionMaster(Long userId, String dateFormat, String financialYear, Paginator paginator);
	
	SourceleadTaxITDeclarationSubmission getITDeclarationSubmissionByUserIdOrgLocationAndFinancialYear(Long userId, Long orgLocationId, String financialYear);

}