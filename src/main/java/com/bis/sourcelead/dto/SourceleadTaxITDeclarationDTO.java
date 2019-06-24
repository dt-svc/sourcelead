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
	private String maxLimit;

	private List<SourceleadTaxITDeclarationItemDTO> sectionItemList;

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

	public List<SourceleadTaxITDeclarationItemDTO> getSectionItemList() {
		return sectionItemList;
	}

	public void setSectionItemList(List<SourceleadTaxITDeclarationItemDTO> sourceleadTaxSectionItemList) {
		this.sectionItemList = sourceleadTaxSectionItemList;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(String maxLimit) {
		this.maxLimit = maxLimit;
	}
}
