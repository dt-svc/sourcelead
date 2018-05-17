package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class CentralTaxDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long sourceleadTaxId;
	private String sourceleadTaxType;
	
	private Integer ceilvaluepf;
 
	private  float noofemployees;
	
	private float employeepf;
	
	private float employerpf;	
	
	private Integer ceilvalueEsi;
	
	private float employeeEsi;
	
	private float employerEsi;
	
	private Integer centraltaxid;
	
	
	private List<TdsDTO> tdsTaxList;
	
	



	public List<TdsDTO> getTdsTaxList() {
		return tdsTaxList;
	}

	public void setTdsTaxList(List<TdsDTO> tdsTaxList) {
		this.tdsTaxList = tdsTaxList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSourceleadTaxType() {
		return sourceleadTaxType;
	}

	public void setSourceleadTaxType(String sourceleadTaxType) {
		this.sourceleadTaxType = sourceleadTaxType;
	}

	public Integer getCeilvaluepf() {
		return ceilvaluepf;
	}

	public void setCeilvaluepf(Integer ceilvaluepf) {
		this.ceilvaluepf = ceilvaluepf;
	}

	public float getNoofemployees() {
		return noofemployees;
	}

	public void setNoofemployees(float noofemployees) {
		this.noofemployees = noofemployees;
	}

	public float getEmployeepf() {
		return employeepf;
	}

	public void setEmployeepf(float employeepf) {
		this.employeepf = employeepf;
	}

	public float getEmployerpf() {
		return employerpf;
	}

	public void setEmployerpf(float employerpf) {
		this.employerpf = employerpf;
	}

	public Integer getCeilvalueEsi() {
		return ceilvalueEsi;
	}

	public void setCeilvalueEsi(Integer ceilvalueEsi) {
		this.ceilvalueEsi = ceilvalueEsi;
	}

	public float getEmployeeEsi() {
		return employeeEsi;
	}

	public void setEmployeeEsi(float employeeEsi) {
		this.employeeEsi = employeeEsi;
	}

	public float getEmployerEsi() {
		return employerEsi;
	}

	public void setEmployerEsi(float employerEsi) {
		this.employerEsi = employerEsi;
	}

	public Integer getCentraltaxid() {
		return centraltaxid;
	}

	public void setCentraltaxid(Integer centraltaxid) {
		this.centraltaxid = centraltaxid;
	}

	
	public Long getSourceleadTaxId() {
		return sourceleadTaxId;
	}

	public void setSourceleadTaxId(Long sourceleadTaxId) {
		this.sourceleadTaxId = sourceleadTaxId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    
}
