package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.bis.sourcelead.annotations.Required;

public class CentralTaxDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Required(desc = "Tax Type", isPositiveNumber = true)
	private long sourceleadTaxId;
	private String sourceleadTaxType;
	
	@Required(desc = "CtfinancialYear")
	private String ctfinancialYear;
	
	private long id;

	@Required(desc = "Ceilvaluepf")
	private Integer ceilvaluepf;

	private Double noofemployees;

	private Double employeepf;

	private Double employerpf;

	@Required(desc = "CeilvalueEsi")
	private Integer ceilvalueEsi;

	private Double employeeEsi;

	private Double employerEsi;

	private Integer centraltaxid;
	
	private String centraltaxuuid;

	private List<TdsDTO> tdsTaxList;

	private String removeIds;

	private boolean active;

	public boolean isActive() {
		return active;
	}
	
	public String getCentraltaxuuid() {
		return centraltaxuuid;
	}

	public void setCentraltaxuuid(String centraltaxuuid) {
		this.centraltaxuuid = centraltaxuuid;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getRemoveIds() {
		return removeIds;
	}

	public void setRemoveIds(String removeIds) {
		this.removeIds = removeIds;
	}

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
