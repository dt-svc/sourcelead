package com.bis.sourcelead.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "USER_ID_VIEW")
public class UserIdView {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "ORG_LOCATION_ID")
	private Long orgLocationId;

	@Column(name = "ASSOCIATE_ID")
	@Type(type = "text")
	private String associateId;

	public Long getOrgLocationId()
	{
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}

	public String getAssociateId()
	{
		return associateId;
	}

	public void setAssociateId(String associateId)
	{
		this.associateId = associateId;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	
	
}
