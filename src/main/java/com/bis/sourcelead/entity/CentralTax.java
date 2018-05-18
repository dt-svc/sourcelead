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
public class CentralTax implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private long id;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TAX_TYPE_ID", nullable = false)
	private SourceleadTaxType sourceleadTaxType;

	
	@Column(name = "CEIL_VALUE_PF")
	private Integer ceilvaluepf;
	
	@Column(name = "NO_OF_EMPLOYEES")
	private  float noofemployees;
	
	@Column(name = "EMPLOYEE_PF")
	private float employeepf;
	
	@Column(name = "EMPLOYER_PF")
	private float employerpf;
	
	@Column(name = "CEIL_VALUE_ESI")
	private Integer ceilvalueesi;
	
	@Column(name = "EMPLOYEE_ESI")
	private float employeeesi;
	
	@Column(name = "EMPLOYER_ESI")
	private float employeresi;
	
	@OneToMany(mappedBy="centraltaxid",cascade=CascadeType.ALL)
	@JsonBackReference
    private Set<Tds> tdsList;


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getId() {
		return id;
	}

	public SourceleadTaxType getSourceleadTaxType() {
		return sourceleadTaxType;
	}

	public Integer getCeilvaluepf() {
		return ceilvaluepf;
	}

	public float getNoofemployees() {
		return noofemployees;
	}

	public float getEmployeepf() {
		return employeepf;
	}

	public float getEmployerpf() {
		return employerpf;
	}

	public Integer getCeilvalueesi() {
		return ceilvalueesi;
	}

	public float getEmployeeesi() {
		return employeeesi;
	}

	public float getEmployeresi() {
		return employeresi;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setSourceleadTaxType(SourceleadTaxType sourceleadTaxType) {
		this.sourceleadTaxType = sourceleadTaxType;
	}

	public void setCeilvaluepf(Integer ceilvaluepf) {
		this.ceilvaluepf = ceilvaluepf;
	}

	public void setNoofemployees(float noofemployees) {
		this.noofemployees = noofemployees;
	}

	public void setEmployeepf(float employeepf) {
		this.employeepf = employeepf;
	}

	public void setEmployerpf(float employerpf) {
		this.employerpf = employerpf;
	}

	public void setCeilvalueesi(Integer ceilvalueesi) {
		this.ceilvalueesi = ceilvalueesi;
	}

	public void setEmployeeesi(float employeeesi) {
		this.employeeesi = employeeesi;
	}

	public void setEmployeresi(float employeresi) {
		this.employeresi = employeresi;
	}

	public Set<Tds> getTdsList() {
		return tdsList;
	}

	public void setTdsList(Set<Tds> tdsList) {
		this.tdsList = tdsList;
	}

	
	
	
}
