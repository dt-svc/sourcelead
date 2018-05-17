package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.CentralTaxDTO;
import com.bis.sourcelead.dto.StateTaxDTO;
<<<<<<< HEAD
import com.bis.sourcelead.entity.OrgLocations;
=======
import com.bis.sourcelead.entity.ProfessionalTax;
>>>>>>> 34dff709ca00a613a170d2ffbb74a1cfa971254e
import com.bis.sourcelead.entity.SourceleadTaxType;
import com.bis.sourcelead.entity.User;

public interface TaxService {

	SourceleadTaxType getTaxTypeById(long id);

	List<SourceleadTaxType> getAllTaxesByType(long type);

	List<StateTaxDTO> getStateTaxList(String yearFrequency, long stateId);

	long saveCentralTax(CentralTaxDTO centralTaxDTO, User user, OrgLocations orgLocations) ;
	
	StateTaxDTO getStateTax(long stateTaxId);

	long addStateTax(StateTaxDTO stateTaxDTO);

	ProfessionalTax getProfessionalTaxById(long id);

}
