package com.bis.sourcelead.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "SOURCELEAD_TAX_SECTION_ITEM")
public class SourceleadTaxSectionItem implements Serializable {

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
	private double maxLimit;

	@Column(name = "DESCRIPTION", columnDefinition = "LONGTEXT default NULL")
	private String description;

	@Column(name = "ACTIVE")
	private boolean active;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SOURCELEAD_TAX_SECTION_ID", nullable = false)
	private SourceleadTaxSection sourceleadTaxSection;

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

	public double getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(double maxLimit) {
		this.maxLimit = maxLimit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public SourceleadTaxSection getSourceleadTaxSection() {
		return sourceleadTaxSection;
	}

	public void setSourceleadTaxSection(SourceleadTaxSection sourceleadTaxSection) {
		this.sourceleadTaxSection = sourceleadTaxSection;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
