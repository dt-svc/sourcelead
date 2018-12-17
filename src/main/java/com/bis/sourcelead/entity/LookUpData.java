package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Prasad K
 * @date 30th Aug 2016
 *
 */
@Entity
@Table(name="LOOKUP_DATA")
public class LookUpData implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID", nullable = false)
	private Long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DESCRIPTION" , columnDefinition = "LONGTEXT default NULL")
	private String description;
	
	@Column(name = "CATEGORY")
	private String category;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
    private String createdBy;
	
	@Column(name = "UNIQUE_KEY")
	private String uniqueKey;
	
	@Column(name = "LOCATION")
	private String location;
	
	@Column(name = "ORG_LOCATIONID")
	private Long orgLocationId;
	
	@Column(name = "REFER_ID")
	private Long referId;
	
	@Column(name = "COLOR_PIC")
	private String colorPic;
	
	@Transient
    @JsonProperty
    private String referName;
	 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Long getReferId() {
		return referId;
	}

	public void setReferId(Long referId) {
		this.referId = referId;
	}

	public String getReferName() {
		return referName;
	}

	public void setReferName(String referName) {
		this.referName = referName;
	}

	public String getColorPic() {
		return colorPic;
	}

	public void setColorPic(String colorPic) {
		this.colorPic = colorPic;
	}
}
