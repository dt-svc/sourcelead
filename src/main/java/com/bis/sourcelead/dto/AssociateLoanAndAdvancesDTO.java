package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import com.bis.sourcelead.annotations.Required;
import com.bis.sourcelead.entity.AssociateLoanAndAdvanceHistory;
import com.bis.sourcelead.entity.AssociateLoanAndAdvances;

public class AssociateLoanAndAdvancesDTO implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	 @Required(desc="loanAndAdvType")
	 private String loanAndAdvType;
	 
	 @Required(desc="loanAndAdvName")
	 private String loanAndAdvName;
	 
	 @Required(desc="loanAndAdvStatus")
	 private String loanAndAdvStatus;
	 
	 @Required(desc="loanAmount")
	 private String loanAmount;
	 
	 @Required(desc="scheduler")
	 private String scheduler;
	 
	 @Required(desc="effectiveDate")
	 private String effectiveDate;
	 
	 @Required (desc = "issuedDate", dependsOn = "loanAndAdvStatus", parentValue = {"Outstanding", "Completed"})
	 private String issuedDate;
	 
	 @Required (desc = "paidAmount", dependsOn = "loanAndAdvStatus", parentValue = "Outstanding")
	 private String paidAmount;
	 
	 @Required (desc = "pendingAmount", dependsOn = "loanAndAdvStatus", parentValue = "Outstanding")
	 private String pendingAmount;
	 
	 @Required(desc="noOfSchedules")
	 private String noOfSchedules;
	 
	 @Required(desc="endDate")
	 private String endDate;
	 
	 @Required(desc="deductAmount")
	 private String deductAmount;
	 
	 @Required(desc="loanAndAdvApprover")
	 private String loanAndAdvApprover;
	 
	 private String description;
	 
	 private long id;
	 
	 private String uuid;
	 
	 private String associateUserId;
	 
	 private String loanAndAdvId;
	 
	 private BigDecimal totalLoanAmount;
	 
	 private BigDecimal totalAdvanceAmount;
	 
	 private String name;
	 
	 private String createdDate;
	 
	 private String approverUser;
	 
	 private int status;
	 
     private String lastPaidOnMaxDate;
     
     Set<AssociateLoanAndAdvanceHistory> loanHis;
     
     AssociateLoanAndAdvances associateLoanAndAdvances;
     
	public Set<AssociateLoanAndAdvanceHistory> getLoanHis() {
		return loanHis;
	}

	public void setLoanHis(Set<AssociateLoanAndAdvanceHistory> loanHis) {
		this.loanHis = loanHis;
	}

	public AssociateLoanAndAdvances getAssociateLoanAndAdvances() {
		return associateLoanAndAdvances;
	}

	public void setAssociateLoanAndAdvances(AssociateLoanAndAdvances associateLoanAndAdvances) {
		this.associateLoanAndAdvances = associateLoanAndAdvances;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLoanAndAdvType() {
		return loanAndAdvType;
	}

	public void setLoanAndAdvType(String loanAndAdvType) {
		this.loanAndAdvType = loanAndAdvType;
	}

	public String getLoanAndAdvName() {
		return loanAndAdvName;
	}

	public void setLoanAndAdvName(String loanAndAdvName) {
		this.loanAndAdvName = loanAndAdvName;
	}

	public String getLoanAndAdvStatus() {
		return loanAndAdvStatus;
	}

	public void setLoanAndAdvStatus(String loanAndAdvStatus) {
		this.loanAndAdvStatus = loanAndAdvStatus;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getScheduler() {
		return scheduler;
	}

	public void setScheduler(String scheduler) {
		this.scheduler = scheduler;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(String issuedDate) {
		this.issuedDate = issuedDate;
	}

	public String getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getPendingAmount() {
		return pendingAmount;
	}

	public void setPendingAmount(String pendingAmount) {
		this.pendingAmount = pendingAmount;
	}

	public String getNoOfSchedules() {
		return noOfSchedules;
	}

	public void setNoOfSchedules(String noOfSchedules) {
		this.noOfSchedules = noOfSchedules;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDeductAmount() {
		return deductAmount;
	}

	public void setDeductAmount(String deductAmount) {
		this.deductAmount = deductAmount;
	}

	public String getLoanAndAdvApprover() {
		return loanAndAdvApprover;
	}

	public void setLoanAndAdvApprover(String loanAndAdvApprover) {
		this.loanAndAdvApprover = loanAndAdvApprover;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getAssociateUserId() {
		return associateUserId;
	}

	public void setAssociateUserId(String associateUserId) {
		this.associateUserId = associateUserId;
	}

	public String getLoanAndAdvId() {
		return loanAndAdvId;
	}

	public void setLoanAndAdvId(String loanAndAdvId) {
		this.loanAndAdvId = loanAndAdvId;
	}

	public BigDecimal getTotalLoanAmount()
	{
		return totalLoanAmount;
	}

	public void setTotalLoanAmount(BigDecimal totalLoanAmount)
	{
		this.totalLoanAmount = totalLoanAmount;
	}

	public BigDecimal getTotalAdvanceAmount()
	{
		return totalAdvanceAmount;
	}

	public void setTotalAdvanceAmount(BigDecimal totalAdvanceAmount)
	{
		this.totalAdvanceAmount = totalAdvanceAmount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getApproverUser() {
		return approverUser;
	}

	public void setApproverUser(String approverUser) {
		this.approverUser = approverUser;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLastPaidOnMaxDate() {
		return lastPaidOnMaxDate;
	}

	public void setLastPaidOnMaxDate(String lastPaidOnMaxDate) {
		this.lastPaidOnMaxDate = lastPaidOnMaxDate;
	}

	
}
