package com.bis.sourcelead.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bis.sourcelead.enums.Status;

@Entity
@Table(name = "MODULES", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL,region="modules")
public class Modules {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@Column(name = "MODULE_NAME")
	private String moduleName;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_ID", nullable = false)
	private Org org;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_MODULE_ID")
	private Modules parentModule;
	
	@Column(name = "IS_ENROLED")
	@Enumerated(EnumType.STRING)
	private Status isEnrolled;
	
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public Status getIsEnrolled() {
		return isEnrolled;
	}

	public void setIsEnrolled(Status isEnrolled) {
		this.isEnrolled = isEnrolled;
	}

	public Modules getParentModule() {
		return parentModule;
	}

	public void setParentModule(Modules parentModule) {
		this.parentModule = parentModule;
	}

	
	
	
}
