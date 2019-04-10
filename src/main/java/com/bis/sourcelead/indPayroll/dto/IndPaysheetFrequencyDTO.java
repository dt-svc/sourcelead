package com.bis.sourcelead.indPayroll.dto;

import java.math.BigDecimal;
import java.util.List;

import com.bis.sourcelead.dto.SearchDTO;

public class IndPaysheetFrequencyDTO  extends SearchDTO{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String payRunId;
	
	private String groupId;
	
	private String payPeriod;
	
	private Long orgLocationId;
	
	private String payFrequency;
	private String payDate;
	private String paySheetFrequencyType;
	private BigDecimal total;
	private String approverStatus;
	private Integer payPeriodNum;
	private String groupName;
	private Long paySheetFrequencyId;
	
	private String actionPerformedBy;
	
	private List<INDPayrollDTO> indPayrollDTO;
	
	private String paySheetFrequencyUUID;
	
	private String frequencyStartDateStr;
	
	private String frequencyEndDateStr;
	
	private String payCheckDateStr;
	
	private Integer status;
	
	private Long createdBy;
	
	private Long updatedBy;
	
	private String updatedDate;
	
	private String createdDate;
	
	private PreCheckINDPayrollDTO preCheckINDPayrollDTO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
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

	public List<INDPayrollDTO> getIndPayrollDTO() {
		return indPayrollDTO;
	}

	public void setIndPayrollDTO(List<INDPayrollDTO> indPayrollDTO) {
		this.indPayrollDTO = indPayrollDTO;
	}

	public String getPaySheetFrequencyUUID() {
		return paySheetFrequencyUUID;
	}

	public void setPaySheetFrequencyUUID(String paySheetFrequencyUUID) {
		this.paySheetFrequencyUUID = paySheetFrequencyUUID;
	}

	public String getFrequencyStartDateStr() {
		return frequencyStartDateStr;
	}

	public void setFrequencyStartDateStr(String frequencyStartDateStr) {
		this.frequencyStartDateStr = frequencyStartDateStr;
	}

	public String getFrequencyEndDateStr() {
		return frequencyEndDateStr;
	}

	public void setFrequencyEndDateStr(String frequencyEndDateStr) {
		this.frequencyEndDateStr = frequencyEndDateStr;
	}

	public String getPayCheckDateStr() {
		return payCheckDateStr;
	}

	public void setPayCheckDateStr(String payCheckDateStr) {
		this.payCheckDateStr = payCheckDateStr;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getActionPerformedBy() {
		return actionPerformedBy;
	}

	public void setActionPerformedBy(String actionPerformedBy) {
		this.actionPerformedBy = actionPerformedBy;
	}

	public PreCheckINDPayrollDTO getPreCheckINDPayrollDTO() {
		return preCheckINDPayrollDTO;
	}

	public void setPreCheckINDPayrollDTO(PreCheckINDPayrollDTO preCheckINDPayrollDTO) {
		this.preCheckINDPayrollDTO = preCheckINDPayrollDTO;
	}
}
