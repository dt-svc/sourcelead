package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.CentralTax;
import com.bis.sourcelead.entity.SourceleadTaxType;
import com.bis.sourcelead.entity.StateTax;
import com.bis.sourcelead.entity.Tds;

public interface TaxDao {

	public List<SourceleadTaxType> getAllTaxesByType(long type);

	public List<StateTax> getStateTaxList(String yearFrequency, long stateId);


	public CentralTax storeCentralTax(CentralTax centralTax);


	Tds storeTds(Tds tds);
	

	public StateTax getStateTax(long stateTaxId);

	public long addStateTax(StateTax stateTax);

	public SourceleadTaxType getTaxTypeById(long id);


}
