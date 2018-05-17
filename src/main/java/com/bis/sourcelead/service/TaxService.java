package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.CentralTaxDTO;
import com.bis.sourcelead.dto.StateTaxDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadTaxType;
import com.bis.sourcelead.entity.User;

public interface TaxService {

	List<SourceleadTaxType> getAllTaxesByType(long type);

	List<StateTaxDTO> getStateTaxList(String yearFrequency, long stateId);

	void saveCentralTax(CentralTaxDTO centralTaxDTO, User user, OrgLocations orgLocations) ;

}
