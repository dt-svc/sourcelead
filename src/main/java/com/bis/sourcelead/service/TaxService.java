package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.StateTaxDTO;
import com.bis.sourcelead.entity.ProfessionalTax;
import com.bis.sourcelead.entity.SourceleadTaxType;

public interface TaxService {

	SourceleadTaxType getTaxTypeById(long id);

	List<SourceleadTaxType> getAllTaxesByType(long type);

	List<StateTaxDTO> getStateTaxList(String yearFrequency, long stateId);

	StateTaxDTO getStateTax(long stateTaxId);

	long addStateTax(StateTaxDTO stateTaxDTO);

	ProfessionalTax getProfessionalTaxById(long id);

}
