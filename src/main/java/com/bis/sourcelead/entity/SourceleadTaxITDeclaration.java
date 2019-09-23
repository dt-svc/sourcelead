package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "SOURCELEAD_TAX_IT_DECLARATION")
public class SourceleadTaxITDeclaration implements Serializable {
	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private long id;

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "MAX_LIMIT")
	private BigDecimal maxLimit;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "UUID")
	private String uuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<SourceleadTaxITDeclarationItem> getSourceleadTaxITDeclarationList() {
		return sourceleadTaxITDeclarationList;
	}

	public void setSourceleadTaxITDeclarationList(List<SourceleadTaxITDeclarationItem> sourceleadTaxSectionItemsList) {
		this.sourceleadTaxITDeclarationList = sourceleadTaxSectionItemsList;
	}

	@Column(name = "ACTIVE")
	private boolean active;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SOURCELEAD_TAX_FORM_ID", nullable = false)
	private SourceleadTaxForm sourceleadTaxForm;

	@OneToMany(mappedBy = "sourceleadTaxITDeclaration", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SourceleadTaxITDeclarationItem> sourceleadTaxITDeclarationList;

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

	public BigDecimal getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(BigDecimal maxLimit) {
		this.maxLimit = maxLimit;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public SourceleadTaxForm getSourceleadTaxForm() {
		return sourceleadTaxForm;
	}

	public void setSourceleadTaxForm(SourceleadTaxForm sourceleadTaxForm) {
		this.sourceleadTaxForm = sourceleadTaxForm;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
