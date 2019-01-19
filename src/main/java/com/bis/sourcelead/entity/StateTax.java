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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "STATE_TAX")
public class StateTax implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private long id;

	@Column(name = "FINANCIAL_YEAR")
	private String financialYear;

	@Column(name = "STATETAX_UUID")
	private String statetaxuuid;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TAX_TYPE_ID", nullable = false)
	private SourceleadTaxType sourceleadTaxType;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATE_ID", nullable = false)
	private States states;

	@OneToMany(mappedBy = "stateTax", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ProfessionalTax> professionalTaxList;

	@Column(name = "ACTIVE")
	private boolean active;

	public long getId() {
		return id;
	}

	public List<ProfessionalTax> getProfessionalTaxList() {
		return professionalTaxList;
	}

	public void setProfessionalTaxList(List<ProfessionalTax> professionalTaxList) {
		this.professionalTaxList = professionalTaxList;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public SourceleadTaxType getSourceleadTaxType() {
		return sourceleadTaxType;
	}

	public void setSourceleadTaxType(SourceleadTaxType sourceleadTaxType) {
		this.sourceleadTaxType = sourceleadTaxType;
	}

	public States getStates() {
		return states;
	}

	public void setStates(States states) {
		this.states = states;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean isActive) {
		this.active = isActive;
	}

	public String getStatetaxuuid() {
		return statetaxuuid;
	}

	public void setStatetaxuuid(String statetaxuuid) {
		this.statetaxuuid = statetaxuuid;
	}

}
