package com.bis.sourcelead.service;
import java.util.List;
import com.bis.sourcelead.dto.CentralTaxDTO;
import com.bis.sourcelead.dto.StateTaxDTO;

import com.bis.sourcelead.entity.OrgLocations;

import com.bis.sourcelead.dto.helper.Paginator;

import com.bis.sourcelead.entity.ProfessionalTax;
import com.bis.sourcelead.entity.SourceleadTaxType;
import com.bis.sourcelead.entity.User;

public interface TaxService {

	SourceleadTaxType getTaxTypeById(long id);

	List<SourceleadTaxType> getAllTaxesByType(long type, boolean includeInactive);

	List<StateTaxDTO> getStateTaxList(String yearFrequency, long stateId, Paginator paginator);

	long saveCentralTax(CentralTaxDTO centralTaxDTO, User user, OrgLocations orgLocations) ;
	
	StateTaxDTO getStateTax(long stateTaxId);

	long addStateTax(StateTaxDTO stateTaxDTO);

	ProfessionalTax getProfessionalTaxById(long id);

	List<CentralTaxDTO> getCentralTaxList(String yearFrequency);

	CentralTaxDTO getCentralTax(long sourceleadTaxId);

}
