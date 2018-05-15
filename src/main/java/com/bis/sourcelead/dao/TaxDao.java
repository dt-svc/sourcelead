package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.SourceleadTaxType;
import com.bis.sourcelead.entity.StateTax;

public interface TaxDao {

	public List<SourceleadTaxType> getAllTaxesByType(long type);

	public List<StateTax> getStateTaxList(String yearFrequency, long stateId);

}
