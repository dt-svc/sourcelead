package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.CentralTaxDTO;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.SourceleadTaxFormDTO;
import com.bis.sourcelead.dto.SourceleadTaxSectionDTO;
import com.bis.sourcelead.dto.SourceleadTaxSectionItemDTO;
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

	long addITDeclarationSection(SourceleadTaxSectionDTO sectionDTO);

	public SourceleadTaxFormDTO getTaxFormType(long taxFormId);

	public SourceleadTaxFormDTO getTaxFormType(SourceleadTaxForm sourceleadTaxForm, boolean includeAll);

	long addITDeclarationSectionItem(SourceleadTaxSectionItemDTO sectionItemDTO);

	Response<String> changeTaxFormStatus(long id);

	Response<String> changeITDeclarationSectionStatus(long id);

	Response<String> changeITDeclarationSectionItemStatus(long id);

	Response<String> changeCentralTaxStatus(long centralTaxId);

	Tds getTDSById(long id);

}
