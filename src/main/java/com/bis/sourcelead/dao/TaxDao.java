package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ProfessionalTax;
import com.bis.sourcelead.entity.SourceleadTaxType;
import com.bis.sourcelead.entity.StateTax;

public interface TaxDao {

	public List<SourceleadTaxType> getAllTaxesByType(long type);

	public List<StateTax> getStateTaxList(String yearFrequency, long stateId, long taxTypeId);

	public StateTax getStateTax(long stateTaxId);

	public long addStateTax(StateTax stateTax);
	
	public boolean removeProfesionalTax(ProfessionalTax professionalTax);

	public SourceleadTaxType getTaxTypeById(long id);

	public ProfessionalTax getProfessionalTaxById(long id);

}
