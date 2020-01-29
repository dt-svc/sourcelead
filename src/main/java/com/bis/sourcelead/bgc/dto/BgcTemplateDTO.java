package com.bis.sourcelead.bgc.dto;

import java.io.Serializable;

import com.bis.sourcelead.dto.SearchDTO;

/**
 * @author Srinivas.Sabbu
 *
 */
public class BgcTemplateDTO extends SearchDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long orgLocationId;
	
	private String name;
	
	private String description;
	
	private String configureType;
	
	private String uuid;
	
	private Long bgcStatusId;
	
	private Long bgcComponentId;
	
	private Integer status;
	
	private String createdDate;
	
	private Long createdBy;

	private String updatedDate;
	
	private Long updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
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

	public String getConfigureType() {
		return configureType;
	}

	public void setConfigureType(String configureType) {
		this.configureType = configureType;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getBgcStatusId() {
		return bgcStatusId;
	}

	public void setBgcStatusId(Long bgcStatusId) {
		this.bgcStatusId = bgcStatusId;
	}

	public Long getBgcComponentId() {
		return bgcComponentId;
	}

	public void setBgcComponentId(Long bgcComponentId) {
		this.bgcComponentId = bgcComponentId;
	}

	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
	
}
