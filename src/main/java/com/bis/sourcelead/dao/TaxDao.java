package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.helper.Paginator;
import com.bis.sourcelead.entity.CentralTax;
import com.bis.sourcelead.entity.PayrollTax;
import com.bis.sourcelead.entity.ProfessionalTax;
import com.bis.sourcelead.entity.SourceleadTaxChallan;
import com.bis.sourcelead.entity.SourceleadTaxChallanItem;
import com.bis.sourcelead.entity.SourceleadTaxChallanItemSubFormField;
import com.bis.sourcelead.entity.SourceleadTaxForm;
import com.bis.sourcelead.entity.SourceleadTaxITDeclaration;
import com.bis.sourcelead.entity.SourceleadTaxITDeclarationItem;
import com.bis.sourcelead.entity.SourceleadTaxITDeclarationSubmission;
import com.bis.sourcelead.entity.SourceleadTaxSubmissionDocument;
import com.bis.sourcelead.entity.SourceleadTaxSubmissionDocumentAttachment;
import com.bis.sourcelead.entity.SourceleadTaxSubmissionMaster;
import com.bis.sourcelead.entity.SourceleadTaxType;
import com.bis.sourcelead.entity.StateTax;
import com.bis.sourcelead.entity.Tds;
import com.bis.sourcelead.entity.User;

public interface TaxDao {

	public List<SourceleadTaxType> getAllTaxesByType(long type, boolean includeInactive);

	/* StateTax */
	public List<StateTax> getStateTaxList(String yearFrequency, long stateId, long taxTypeId, Paginator paginator);

	public long storeCentralTax(CentralTax centralTax);

	public StateTax getStateTax(String statetaxuuid);
	
	public StateTax getStateTaxById(long stateTaxId);
	
	public StateTax getStateTaxByStateNameAndFinancialYear(String stateName, String financialYear);

	public long addStateTax(StateTax stateTax);

	public boolean removeProfesionalTax(ProfessionalTax professionalTax);

	public SourceleadTaxType getTaxTypeById(long id);

	public ProfessionalTax getProfessionalTaxById(long id);

	public List<CentralTax> getCentralTaxList(String yearFrequency, long taxTypeId);

	public CentralTax getCentralTax(String centraltaxuuid);
	
	public CentralTax getCentralTaxByTaxType(String taxType);
	
	public long changeStateTaxStatus(long stateTaxId);

	/* Forms */
	public List<SourceleadTaxForm> getTaxFormTypeList(boolean adminFlag);

	public SourceleadTaxForm getTaxFormType(long taxFormId);

	public SourceleadTaxForm getTaxFormType(String uuid);

	public long addITDeclarationSection(SourceleadTaxITDeclaration taxSection);

	public SourceleadTaxITDeclaration getTaxSection(long taxSectionId);

	public SourceleadTaxITDeclarationItem getTaxSectionItem(long id);

	public long addITDeclarationSectionItem(SourceleadTaxITDeclarationItem taxSectionItem);

	public long changeTaxFormStatus(long id);

	public long changeITDeclarationSectionStatus(long id);

	public long changeITDeclarationSectionItemStatus(long id);

	public long changeCentralTaxStatus(long centralTaxId);

	public boolean removeTDSTax(Tds tds);

	public Tds getTDSById(long id);

	public long submitITDeclaration(SourceleadTaxITDeclarationSubmission itDeclarationSubmission);

	public int getITDeclarationFormsCountByYear(String financialYear, String tableName, long orgLocationId);

	public List<SourceleadTaxITDeclarationSubmission> getITDeclarationSubmissionList(long orgLocationId, String uuid, String financialYear, String searchValue,
			Paginator paginator);

	public SourceleadTaxITDeclarationSubmission getITDeclarationSubmission(String taxFormUUID, String formUUID);

	public SourceleadTaxITDeclarationSubmission getITDeclarationSubmission(long id);

	public SourceleadTaxITDeclarationSubmission getITDeclarationSubmission(long userId, String financialYear);

	// public SourceleadTaxITDeclarationSubmissionItem
	// getITDeclarationSubmissionItem(long id);

	public boolean saveDocument(SourceleadTaxSubmissionDocument taxSubmissionDocument);
	
	public void deleteDocumentAttachment(long attachmentId);

	public void deleteDocument(long documentId);
	
	public List<SourceleadTaxSubmissionDocument> getUploadedDocumentsBySubmissionId(long submissionId);

	public SourceleadTaxSubmissionDocument getSourceleadTaxSubmissionDocumentById(long submissionDocumentId);

	public void removeTaxSubmissionDocumentAttachment(SourceleadTaxSubmissionDocumentAttachment attachment);

	public long addITChallanSectionItem(SourceleadTaxChallanItem sectionItem);

	public long addItChallanSection(SourceleadTaxChallan section);

	public SourceleadTaxChallanItem getSourceleadTaxChallanItemById(long addChallanSectionItemId);

	public long addSourceleadTaxChallanItemSubFormField(SourceleadTaxChallanItemSubFormField sourceleadTaxChallanItem);

	public SourceleadTaxChallan getTaxChallanSection(long taxSectionId);

	public SourceleadTaxChallanItem getChallanItemDetails(Long itemRowId);

	public SourceleadTaxChallanItem getITChallanSectionItem(Long id);

	public long changeChallanItemStatus(long id);

	public long changeSectionStatus(long sectionId);

	public long changeITDeclarationSubmissionStatus(long id, int status);


	public SourceleadTaxITDeclarationSubmission getITDeclarationSubmissionDetails(long formId);

	public User getEmployeeDetails(Long id);

	public List<SourceleadTaxSubmissionMaster> getTaxSubmissionMasterList(Long userId, String financialYear,
			Paginator paginator);

	public void saveTaxSubmissionMaster(SourceleadTaxSubmissionMaster sourceleadTaxSubmissionMaster);

	public List<Tds> getTDSListByFinancialYear(String financialYear);
	
	public SourceleadTaxITDeclarationSubmission getITDeclarationSubmissionByUserIdOrgLocationAndFinancialYear(Long userId, Long orgLocationId, String financialYear);

}
