package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bis.sourcelead.dto.SourceleadTaxChallanDTO;

@Entity
@Table(name = "SOURCELEAD_TAX_FORM")
public class SourceleadTaxForm implements Serializable {

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

	@Column(name = "ACTIVE")
	private boolean active;

	@Column(name = "UUID")
	private String uuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@OneToMany(mappedBy = "sourceleadTaxForm", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SourceleadTaxITDeclaration> sourceleadTaxSectionList;

	@OneToMany(mappedBy = "sourceleadTaxForm", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SourceleadTaxChallan> sourceleadTaxChallanSectionList;

	
	
	public List<SourceleadTaxChallan> getSourceleadTaxChallanSectionList() {
		return sourceleadTaxChallanSectionList;
	}

	public void setSourceleadTaxChallanSectionList(List<SourceleadTaxChallan> sourceleadTaxChallanSectionList) {
		this.sourceleadTaxChallanSectionList = sourceleadTaxChallanSectionList;
	}

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

	public List<SourceleadTaxITDeclaration> getSourceleadTaxSectionList() {
		return sourceleadTaxSectionList;
	}

	public void setSourceleadTaxSectionList(List<SourceleadTaxITDeclaration> sourceleadTaxSectionList) {
		this.sourceleadTaxSectionList = sourceleadTaxSectionList;
	}

	

}
