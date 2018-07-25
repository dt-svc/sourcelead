package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;


public class SourceleadTaxChallanDTO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private String description;
	private String uuid;
	private boolean active;
	private long challanId;
	private List<SourceleadTaxITDeclarationItemDTO> sourceleadTaxChallanSectionItemList;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public long getChallanId() {
		return challanId;
	}
	public void setChallanId(long challanId) {
		this.challanId = challanId;
	}
	public List<SourceleadTaxITDeclarationItemDTO> getSourceleadTaxChallanSectionItemList() {
		return sourceleadTaxChallanSectionItemList;
	}
	public void setSourceleadTaxChallanSectionItemList(
			List<SourceleadTaxITDeclarationItemDTO> sourceleadTaxChallanSectionItemList) {
		this.sourceleadTaxChallanSectionItemList = sourceleadTaxChallanSectionItemList;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
