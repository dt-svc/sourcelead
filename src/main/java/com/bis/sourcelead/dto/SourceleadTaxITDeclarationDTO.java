package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class SourceleadTaxITDeclarationDTO implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private String description;
	private boolean active;
	private String uuid;
	private long taxFormId;
	private String taxFormName;
	private String financialYear;
	private long userId;
	private List<SourceleadTaxITDeclarationItemDTO> sourceleadTaxSectionItemList;

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

	public long getTaxFormId() {
		return taxFormId;
	}

	public void setTaxFormId(long taxFormId) {
		this.taxFormId = taxFormId;
	}

	public String getTaxFormName() {
		return taxFormName;
	}

	public void setTaxFormName(String taxFormName) {
		this.taxFormName = taxFormName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String desctiption) {
		this.description = desctiption;
	}

	public List<SourceleadTaxITDeclarationItemDTO> getSourceleadTaxSectionItemList() {
		return sourceleadTaxSectionItemList;
	}

	public void setSourceleadTaxSectionItemList(List<SourceleadTaxITDeclarationItemDTO> sourceleadTaxSectionItemList) {
		this.sourceleadTaxSectionItemList = sourceleadTaxSectionItemList;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}
