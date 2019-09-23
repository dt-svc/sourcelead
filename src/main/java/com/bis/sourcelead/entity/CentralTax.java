package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

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
@Table(name = "CENTRAL_TAX")
public class CentralTax implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private long id;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TAX_TYPE_ID", nullable = false)
	private SourceleadTaxType sourceleadTaxType;

	@Column(name = "CT_FINANCIAL_YEAR")
	private String ctfinancialYear;

	@Column(name = "CEIL_VALUE_PF")
	private Integer ceilvaluepf;

	@Column(name = "NO_OF_EMPLOYEES")
	private Double noofemployees;

	@Column(name = "EMPLOYEE_PF")
	private Double employeepf;

	@Column(name = "EMPLOYER_PF")
	private Double employerpf;

	@Column(name = "CEIL_VALUE_ESI")
	private Integer ceilvalueesi;

	@Column(name = "EMPLOYEE_ESI")
	private Double employeeesi;

	@Column(name = "EMPLOYER_ESI")
	private Double employeresi;

	@Column(name = "ACTIVE")
	private boolean active;
	
	@Column(name = "CENTRALTAX_UUID")
	private String centraltaxuuid;
	
	
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@OneToMany(mappedBy = "centraltaxid", cascade = CascadeType.ALL)
	@JsonBackReference
	private Set<Tds> tdsList;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public SourceleadTaxType getSourceleadTaxType() {
		return sourceleadTaxType;
	}

	public void setSourceleadTaxType(SourceleadTaxType sourceleadTaxType) {
		this.sourceleadTaxType = sourceleadTaxType;
	}

	public String getCtfinancialYear() {
		return ctfinancialYear;
	}

	public void setCtfinancialYear(String ctfinancialYear) {
		this.ctfinancialYear = ctfinancialYear;
	}

	public Integer getCeilvaluepf() {
		return ceilvaluepf;
	}

	public void setCeilvaluepf(Integer ceilvaluepf) {
		this.ceilvaluepf = ceilvaluepf;
	}

	public Double getNoofemployees() {
		return noofemployees;
	}

	public void setNoofemployees(Double noofemployees) {
		this.noofemployees = noofemployees;
	}

	public Double getEmployeepf() {
		return employeepf;
	}

	public void setEmployeepf(Double employeepf) {
		this.employeepf = employeepf;
	}

	public Double getEmployerpf() {
		return employerpf;
	}

	public void setEmployerpf(Double employerpf) {
		this.employerpf = employerpf;
	}

	public Integer getCeilvalueesi() {
		return ceilvalueesi;
	}

	public void setCeilvalueesi(Integer ceilvalueesi) {
		this.ceilvalueesi = ceilvalueesi;
	}

	public Double getEmployeeesi() {
		return employeeesi;
	}

	public void setEmployeeesi(Double employeeesi) {
		this.employeeesi = employeeesi;
	}

	public Double getEmployeresi() {
		return employeresi;
	}

	public void setEmployeresi(Double employeresi) {
		this.employeresi = employeresi;
	}

	public Set<Tds> getTdsList() {
		return tdsList;
	}

	public void setTdsList(Set<Tds> tdsList) {
		this.tdsList = tdsList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCentraltaxuuid() {
		return centraltaxuuid;
	}

	public void setCentraltaxuuid(String centraltaxuuid) {
		this.centraltaxuuid = centraltaxuuid;
	}

}
