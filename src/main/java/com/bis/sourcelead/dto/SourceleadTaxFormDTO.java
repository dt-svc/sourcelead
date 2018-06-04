package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;

public class SourceleadTaxFormDTO implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private boolean active;
	private String financialYear;
	private int totalFormsCount;
	private String uuid;
	private List<SourceleadTaxITDeclarationDTO> itDeclarationSectionList;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public int getTotalFormsCount() {
		return totalFormsCount;
	}

	public void setTotalFormsCount(int totalFormsCount) {
		this.totalFormsCount = totalFormsCount;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<SourceleadTaxITDeclarationDTO> getItDeclarationSectionList() {
		return itDeclarationSectionList;
	}

	public void setItDeclarationSectionList(List<SourceleadTaxITDeclarationDTO> sourceleadTaxSectionList) {
		this.itDeclarationSectionList = sourceleadTaxSectionList;
	}
}
