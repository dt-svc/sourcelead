package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

import com.bis.sourcelead.annotations.Required;
import com.bis.sourcelead.entity.AssociateLoanAndAdvanceHistory;
import com.bis.sourcelead.entity.AssociateLoanAndAdvances;

public class AssociateLoanAndAdvancesDTO extends SearchDTO implements Serializable{
	
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
	 
	 private String dueAmount;
	 
	 private String nextDueDate;
	 
	 private Long id;
	 
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
     
     private BigDecimal outstandingAmount;
     
     private Double rateOfInterest;
     
     private String firstName;
     
     private String lastName;
     
     private String employeeId;
     
     private String empType;
     
     private Date startDate;
     
     private Date loanEndDate;
     
     private Double loanTotalAmount;
     
     private Double loanPaidAmount;
     
     private Double loanPendingAmount;
     
     private Double loanDeductAmount;
     
     private int loanNoOfSchedules;
     
     private String loanName;
     
     private String searchValue;
     
     private String fromDate;
     
     private String toDate;
     
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

	public String getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(String dueAmount) {
		this.dueAmount = dueAmount;
	}
	
	 public String getNextDueDate() {
		return nextDueDate;
	}

	public void setNextDueDate(String nextDueDate) {
		this.nextDueDate = nextDueDate;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public BigDecimal getOutstandingAmount() {
		return outstandingAmount;
	}

	public Double getRateOfInterest() {
		return rateOfInterest;
	}

	public void setOutstandingAmount(BigDecimal outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	public void setRateOfInterest(Double rateOfInterest) {
		this.rateOfInterest = rateOfInterest;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmpType() {
		return empType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getLoanEndDate() {
		return loanEndDate;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setLoanEndDate(Date loanEndDate) {
		this.loanEndDate = loanEndDate;
	}

	public Double getLoanTotalAmount() {
		return loanTotalAmount;
	}

	public void setLoanTotalAmount(Double loanTotalAmount) {
		this.loanTotalAmount = loanTotalAmount;
	}

	public Double getLoanPaidAmount() {
		return loanPaidAmount;
	}

	public Double getLoanPendingAmount() {
		return loanPendingAmount;
	}

	public Double getLoanDeductAmount() {
		return loanDeductAmount;
	}

	public int getLoanNoOfSchedules() {
		return loanNoOfSchedules;
	}

	public void setLoanPaidAmount(Double loanPaidAmount) {
		this.loanPaidAmount = loanPaidAmount;
	}

	public void setLoanPendingAmount(Double loanPendingAmount) {
		this.loanPendingAmount = loanPendingAmount;
	}

	public void setLoanDeductAmount(Double loanDeductAmount) {
		this.loanDeductAmount = loanDeductAmount;
	}

	public void setLoanNoOfSchedules(int loanNoOfSchedules) {
		this.loanNoOfSchedules = loanNoOfSchedules;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getFromDate() {
		return fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	
}
