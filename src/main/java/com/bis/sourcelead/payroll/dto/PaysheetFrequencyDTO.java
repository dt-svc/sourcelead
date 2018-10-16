package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;
import java.util.List;

import com.bis.sourcelead.dto.SearchDTO;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;

public class PaysheetFrequencyDTO  extends SearchDTO{
	
	private String id;
	
	private String groupId;
	
	private String payPeriod;
	
	private String orgLocationId;
	
	private List<USPaysheetDTO> uSPaysheetDTO;
	
	private String payFrequency;
	private String payDate;
	private String paySheetFrequencyType;
	private BigDecimal total;
	private String paySheetFrequencyuuid;
	private String approverStatus;
	private Integer payPeriodNum;
	private String groupName;
	private Long paySheetFrequencyId;
	
	private PaysheetFrequency paySheetFrequency;
	

	private List<USPayrollDTO> payrollDTO;
	
	private String payrollFrequencyUUID;
	
	private int noOfGeneratedPayroll;
	private BigDecimal totalGeneratedAmount;
	
	public String getGroupId()
	{
		return groupId;
	}
	public String getPayPeriod()
	{
		return payPeriod;
	}
	public String getOrgLocationId()
	{
		return orgLocationId;
	}
	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}
	public void setPayPeriod(String payPeriod)
	{
		this.payPeriod = payPeriod;
	}
	public void setOrgLocationId(String orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}
	public List<USPaysheetDTO> getuSPaysheetDTO()
	{
		return uSPaysheetDTO;
	}
	public void setuSPaysheetDTO(List<USPaysheetDTO> uSPaysheetDTO)
	{
		this.uSPaysheetDTO = uSPaysheetDTO;
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
	public String getPaySheetFrequencyuuid() {
		return paySheetFrequencyuuid;
	}
	public void setPaySheetFrequencyuuid(String paySheetFrequencyuuid) {
		this.paySheetFrequencyuuid = paySheetFrequencyuuid;
	}

	public String getPayrollFrequencyUUID()
	{
		return payrollFrequencyUUID;
	}
	public void setPayrollFrequencyUUID(String payrollFrequencyUUID)
	{
		this.payrollFrequencyUUID = payrollFrequencyUUID;
	}
	public List<USPayrollDTO> getPayrollDTO()
	{
		return payrollDTO;
	}
	public void setPayrollDTO(List<USPayrollDTO> payrollDTO)
	{
		this.payrollDTO = payrollDTO;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
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
	public PaysheetFrequency getPaySheetFrequency() {
		return paySheetFrequency;
	}
	public void setPaySheetFrequency(PaysheetFrequency paySheetFrequency) {
		this.paySheetFrequency = paySheetFrequency;
	}
	public int getNoOfGeneratedPayroll() {
		return noOfGeneratedPayroll;
	}
	public void setNoOfGeneratedPayroll(int noOfGeneratedPayroll) {
		this.noOfGeneratedPayroll = noOfGeneratedPayroll;
	}
	public BigDecimal getTotalGeneratedAmount() {
		return totalGeneratedAmount;
	}
	public void setTotalGeneratedAmount(BigDecimal totalGeneratedAmount) {
		this.totalGeneratedAmount = totalGeneratedAmount;
	}
	
}
