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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	private String uuid;
	private List<SourceleadTaxSectionDTO> sourceleadTaxSectionList;

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

	public List<SourceleadTaxSectionDTO> getSourceleadTaxSectionList() {
		return sourceleadTaxSectionList;
	}

	public void setSourceleadTaxSectionList(List<SourceleadTaxSectionDTO> sourceleadTaxSectionList) {
		this.sourceleadTaxSectionList = sourceleadTaxSectionList;
	}
}
