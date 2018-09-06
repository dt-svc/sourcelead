package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	
	@JsonProperty
	private List<SourceleadTaxITDeclarationDTO> sourceleadTaxSectionList;
	
	private String uuid1;
	@JsonProperty
	private List<SourceleadTaxChallanDTO> sourceleadTaxChallanSectionList;
	

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

	public List<SourceleadTaxITDeclarationDTO> getSourceleadTaxSectionList() {
		return sourceleadTaxSectionList;
	}

	public void setSourceleadTaxSectionList(List<SourceleadTaxITDeclarationDTO> sourceleadTaxSectionList) {
		this.sourceleadTaxSectionList = sourceleadTaxSectionList;
	}

	public List<SourceleadTaxChallanDTO> getSourceleadTaxChallanSectionList() {
		return sourceleadTaxChallanSectionList;
	}

	public void setSourceleadTaxChallanSectionList(List<SourceleadTaxChallanDTO> sourceleadTaxChallanSectionList) {
		this.sourceleadTaxChallanSectionList = sourceleadTaxChallanSectionList;
	}
	
}
