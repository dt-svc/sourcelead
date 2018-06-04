package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.CentralTaxDTO;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.SourceleadTaxFormDTO;
import com.bis.sourcelead.dto.SourceleadTaxITDeclarationDTO;
import com.bis.sourcelead.dto.SourceleadTaxITDeclarationItemDTO;
import com.bis.sourcelead.dto.SourceleadTaxITDeclarationSubmissionDTO;
import com.bis.sourcelead.dto.SourceleadTaxITDeclarationSubmissionItemDTO;
import com.bis.sourcelead.dto.StateTaxDTO;

import com.bis.sourcelead.entity.OrgLocations;

import com.bis.sourcelead.dto.helper.Paginator;

import com.bis.sourcelead.entity.ProfessionalTax;
import com.bis.sourcelead.entity.SourceleadTaxForm;
import com.bis.sourcelead.entity.SourceleadTaxType;
import com.bis.sourcelead.entity.Tds;
import com.bis.sourcelead.entity.User;

public interface TaxService {

	SourceleadTaxType getTaxTypeById(long id);

	List<SourceleadTaxType> getAllTaxesByType(long type, boolean includeInactive);

	List<StateTaxDTO> getStateTaxList(String yearFrequency, long stateId, Paginator paginator);

	long saveCentralTax(CentralTaxDTO centralTaxDTO, User user, OrgLocations orgLocations);

	StateTaxDTO getStateTax(String statetaxuuid);

	long addStateTax(StateTaxDTO stateTaxDTO);

	ProfessionalTax getProfessionalTaxById(long id);

	List<CentralTaxDTO> getCentralTaxList(String yearFrequency);

	CentralTaxDTO getCentralTax(String centraltaxuuid);

	Response<String> changeStateTaxStatus(long stateTaxId);

	List<SourceleadTaxFormDTO> getTaxFormTypeList(boolean includeAll);

	List<SourceleadTaxFormDTO> getTaxFormTypeListForSubmission(int year);

	long addITDeclarationSection(SourceleadTaxITDeclarationDTO sectionDTO);

	public SourceleadTaxFormDTO getTaxFormType(long taxFormId, boolean includeAll, boolean includeInactive);

	public SourceleadTaxFormDTO getTaxFormType(String uuid, boolean includeAll, boolean includeInactive);

	long addITDeclarationSectionItem(SourceleadTaxITDeclarationItemDTO sectionItemDTO);

	Response<String> changeTaxFormStatus(long id);

	Response<String> changeITDeclarationSectionStatus(long id);

	Response<String> changeITDeclarationSectionItemStatus(long id);

	Response<String> changeCentralTaxStatus(long centralTaxId);

	Tds getTDSById(long id);

	Response<String> submitITDeclaration(SourceleadTaxITDeclarationSubmissionDTO itDeclarationSubmissionDTO, User loggedInUser);

	List<SourceleadTaxITDeclarationSubmissionDTO> getITDeclarationSubmissionList(String uuid, int year);

	SourceleadTaxITDeclarationSubmissionDTO getITDeclarationSubmission(String taxFormUUID, String formUUID);

}
