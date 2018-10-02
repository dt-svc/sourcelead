package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;
import java.util.List;

import com.bis.sourcelead.dto.SearchDTO;
import com.bis.sourcelead.payroll.entity.IndPaysheetFrequency;

public class IndPaysheetFrequencyDTO  extends SearchDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String payRunId;
	
	private String groupId;
	
	private String payPeriod;
	
	private String orgLocationId;
	
	private String payFrequency;
	private String payDate;
	private String paySheetFrequencyType;
	private BigDecimal total;
	private String approverStatus;
	private Integer payPeriodNum;
	private String groupName;
	private Long paySheetFrequencyId;
	
	private IndPaysheetFrequency indPaySheetFrequency;
	
	private List<INDPayrollDTO> indPayrollDTO;
	
	private String payrollFrequencyUUID;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPayRunId() {
		return payRunId;
	}

	public void setPayRunId(String payRunId) {
		this.payRunId = payRunId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getPayPeriod() {
		return payPeriod;
	}

	public void setPayPeriod(String payPeriod) {
		this.payPeriod = payPeriod;
	}

	public String getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getPayFrequency() {
		return payFrequency;
	}

	public void setPayFrequency(String payFrequency) {
		this.payFrequency = payFrequency;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getPaySheetFrequencyType() {
		return paySheetFrequencyType;
	}

	public void setPaySheetFrequencyType(String paySheetFrequencyType) {
		this.paySheetFrequencyType = paySheetFrequencyType;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getApproverStatus() {
		return approverStatus;
	}

	public void setApproverStatus(String approverStatus) {
		this.approverStatus = approverStatus;
	}

	public Integer getPayPeriodNum() {
		return payPeriodNum;
	}

	public void setPayPeriodNum(Integer payPeriodNum) {
		this.payPeriodNum = payPeriodNum;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Long getPaySheetFrequencyId() {
		return paySheetFrequencyId;
	}

	public void setPaySheetFrequencyId(Long paySheetFrequencyId) {
		this.paySheetFrequencyId = paySheetFrequencyId;
	}

	public IndPaysheetFrequency getIndPaySheetFrequency() {
		return indPaySheetFrequency;
	}

	public void setIndPaySheetFrequency(IndPaysheetFrequency indPaySheetFrequency) {
		this.indPaySheetFrequency = indPaySheetFrequency;
	}

	public List<INDPayrollDTO> getIndPayrollDTO() {
		return indPayrollDTO;
	}

	public void setIndPayrollDTO(List<INDPayrollDTO> indPayrollDTO) {
		this.indPayrollDTO = indPayrollDTO;
	}

	public String getPayrollFrequencyUUID() {
		return payrollFrequencyUUID;
	}

	public void setPayrollFrequencyUUID(String payrollFrequencyUUID) {
		this.payrollFrequencyUUID = payrollFrequencyUUID;
	}
	
	
	
}
