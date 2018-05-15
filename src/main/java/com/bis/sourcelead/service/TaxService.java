package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.StateTaxDTO;
import com.bis.sourcelead.entity.SourceleadTaxType;

public interface TaxService {

	List<SourceleadTaxType> getAllTaxesByType(long type);

	List<StateTaxDTO> getStateTaxList(String yearFrequency, long stateId);

}
