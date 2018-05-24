package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.CentralTax;

import com.bis.sourcelead.dto.helper.Paginator;

import com.bis.sourcelead.entity.ProfessionalTax;

import com.bis.sourcelead.entity.SourceleadTaxType;
import com.bis.sourcelead.entity.StateTax;
import com.bis.sourcelead.entity.Tds;

public interface TaxDao {

	public List<SourceleadTaxType> getAllTaxesByType(long type, boolean includeInactive);

	public List<StateTax> getStateTaxList(String yearFrequency, long stateId, long taxTypeId, Paginator paginator);

	public long storeCentralTax(CentralTax centralTax);

	public StateTax getStateTax(String statetaxuuid);

	public long addStateTax(StateTax stateTax);

	public boolean removeProfesionalTax(ProfessionalTax professionalTax);

	public SourceleadTaxType getTaxTypeById(long id);

	public ProfessionalTax getProfessionalTaxById(long id);

	public List<CentralTax> getCentralTaxList(String yearFrequency, long taxTypeId);

	public CentralTax getCentralTax(String centraltaxuuid);

	public long changeStateTaxStatus(long stateTaxId);

	public long changeCentralTaxStatus(long centralTaxId);

	public boolean removeTDSTax(Tds tds);

	public Tds getTDSById(long id);

}
