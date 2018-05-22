package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class CentralTaxDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private long id;
	
	private Long sourceleadTaxId;
	
	private String sourceleadTaxType;
	
	private Integer ceilvaluepf;
 
	private  Double noofemployees;
	
	private Double employeepf;
	
	private Double employerpf;	
	
	private Integer ceilvalueEsi;
	
	private Double employeeEsi;
	
	private Double employerEsi;
	
	private Integer centraltaxid;
	
	private String ctfinancialYear;
	
	private List<TdsDTO> tdsTaxList;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getSourceleadTaxId() {
		return sourceleadTaxId;
	}

	public void setSourceleadTaxId(Long sourceleadTaxId) {
		this.sourceleadTaxId = sourceleadTaxId;
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

	public Integer getCeilvalueEsi() {
		return ceilvalueEsi;
	}

	public void setCeilvalueEsi(Integer ceilvalueEsi) {
		this.ceilvalueEsi = ceilvalueEsi;
	}

	public Double getEmployeeEsi() {
		return employeeEsi;
	}

	public void setEmployeeEsi(Double employeeEsi) {
		this.employeeEsi = employeeEsi;
	}

	public Double getEmployerEsi() {
		return employerEsi;
	}

	public void setEmployerEsi(Double employerEsi) {
		this.employerEsi = employerEsi;
	}

	public Integer getCentraltaxid() {
		return centraltaxid;
	}

	public void setCentraltaxid(Integer centraltaxid) {
		this.centraltaxid = centraltaxid;
	}

	public String getCtfinancialYear() {
		return ctfinancialYear;
	}

	public void setCtfinancialYear(String ctfinancialYear) {
		this.ctfinancialYear = ctfinancialYear;
	}

	public List<TdsDTO> getTdsTaxList() {
		return tdsTaxList;
	}

	public void setTdsTaxList(List<TdsDTO> tdsTaxList) {
		this.tdsTaxList = tdsTaxList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
