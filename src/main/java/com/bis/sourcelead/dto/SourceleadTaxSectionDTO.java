package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class SourceleadTaxSectionDTO implements Serializable {

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
	private List<SourceleadTaxSectionItemDTO> sourceleadTaxSectionItemList;

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

	public List<SourceleadTaxSectionItemDTO> getSourceleadTaxSectionItemList() {
		return sourceleadTaxSectionItemList;
	}

	public void setSourceleadTaxSectionItemList(List<SourceleadTaxSectionItemDTO> sourceleadTaxSectionItemList) {
		this.sourceleadTaxSectionItemList = sourceleadTaxSectionItemList;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
