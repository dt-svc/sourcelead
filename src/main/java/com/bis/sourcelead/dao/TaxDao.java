package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.helper.Paginator;
import com.bis.sourcelead.entity.ProfessionalTax;
import com.bis.sourcelead.entity.SourceleadTaxForm;
import com.bis.sourcelead.entity.SourceleadTaxSection;
import com.bis.sourcelead.entity.SourceleadTaxSectionItem;
import com.bis.sourcelead.entity.SourceleadTaxType;
import com.bis.sourcelead.entity.StateTax;

public interface TaxDao {

	public List<SourceleadTaxType> getAllTaxesByType(long type, boolean includeInactive);

	/* StateTax */
	public List<StateTax> getStateTaxList(String yearFrequency, long stateId, long taxTypeId, Paginator paginator);

	public StateTax getStateTax(long stateTaxId);

	public long addStateTax(StateTax stateTax);

	public boolean removeProfesionalTax(ProfessionalTax professionalTax);

	public SourceleadTaxType getTaxTypeById(long id);

	public ProfessionalTax getProfessionalTaxById(long id);

	public long changeStateTaxStatus(long stateTaxId);

	/* Forms */
	public List<SourceleadTaxForm> getTaxFormTypeList(boolean adminFlag);

	public SourceleadTaxForm getTaxFormType(long taxFormId);

	public long addITDeclarationSection(SourceleadTaxSection taxSection);

	public SourceleadTaxSection getTaxSection(long taxSectionId);

	public SourceleadTaxSectionItem getTaxSectionItem(long id);

	public long addITDeclarationSectionItem(SourceleadTaxSectionItem taxSectionItem);

	public long changeITDeclarationSectionStatus(long id);

	public long changeITDeclarationSectionItemStatus(long id);

}
