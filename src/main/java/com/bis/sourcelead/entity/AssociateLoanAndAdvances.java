package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.bis.sourcelead.constants.Constants;
import java.math.BigDecimal;

@Entity
@Table(name = "ASSOCIATE_LOAN_ADVANCES")
public class AssociateLoanAndAdvances implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_ID")
    private Candidate candidateId;
	
	@Column(name = "LOAN_ADVANCE_TYPE")
	private String loanAdvanceType;
	
	@Column(name = "LOAN_AND_ADVANCES_STATUS")
	private String loanAndAdvanceStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LOAN_AND_ADVANCES_ID")
    private PayrollLoanAndAdvances payrollLoanAndAdvanceId;
	
	@Column(name = "AMOUNT")
	private Double amount;
	
	@Column(name = "SCHEDULER")
	private String scheduler;
	
	@Column(name = "EFFECTIVE_DATE")
	private Date effectiveDate;
	
	@Column(name = "ISSUED_DATE")
	private Date issuedDate;
	
	@Column(name = "PAID_AMOUNT")
	private Double paidAmount;
	
	@Column(name = "NO_OF_SCHEDULES")
	private Integer noOfSchedules;
	
	@Column(name = "PENDING_AMOUNT")
	private Double pendingAmount;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "DEDUCT_AMOUNT")
	private Double deductAmount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPROVER_USER")
    private User approverUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID")
    private OrgLocations orgLocationId;
	
	@Column(name = "DESCRIPTION", columnDefinition = "LONGTEXT default NULL")
	private String description;
	
	@Column(name = "UUID")
	private String uuid;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "DEDUCT_START_DATE")
	private Date deductStartDate;
	
	@Column(name = "DEDUCT_END_DATE")
	private Date deductEndDate;
	
	@Column(name = "DEDUCTION_STATUS")
	private String deductionStatus;
	
    @Column(name = "CREATED_DATE")
	private Date createdDate;
   
    @Column(name = "UPDATED_DATE")
  	private Date updatedDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY")
    private User createdBy;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPDATED_BY")
    private User updatedBy;
    
    @Column(name = "OUTSTANDING_AMOUNT")
	private BigDecimal outstandingAmount;
    
    @Column(name = "RATE_OF_INTEREST")
	private Double rateOfInterest;
    
    @Transient
    private String approverName;
    
    @Transient
    private String effectiveDateStr;
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Candidate getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Candidate candidateId) {
		this.candidateId = candidateId;
	}

	public String getLoanAdvanceType() {
		return loanAdvanceType;
	}

	public void setLoanAdvanceType(String loanAdvanceType) {
		this.loanAdvanceType = loanAdvanceType;
	}

	public String getLoanAndAdvanceStatus() {
		return loanAndAdvanceStatus;
	}

	public void setLoanAndAdvanceStatus(String loanAndAdvanceStatus) {
		this.loanAndAdvanceStatus = loanAndAdvanceStatus;
	}

	public PayrollLoanAndAdvances getPayrollLoanAndAdvanceId() {
		return payrollLoanAndAdvanceId;
	}

	public void setPayrollLoanAndAdvanceId(PayrollLoanAndAdvances payrollLoanAndAdvanceId) {
		this.payrollLoanAndAdvanceId = payrollLoanAndAdvanceId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getScheduler() {
		return scheduler;
	}

	public void setScheduler(String scheduler) {
		this.scheduler = scheduler;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Integer getNoOfSchedules() {
		return noOfSchedules;
	}

	public void setNoOfSchedules(Integer noOfSchedules) {
		this.noOfSchedules = noOfSchedules;
	}

	public Double getPendingAmount() {
		return pendingAmount;
	}

	public void setPendingAmount(Double pendingAmount) {
		this.pendingAmount = pendingAmount;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getDeductAmount() {
		return deductAmount;
	}

	public void setDeductAmount(Double deductAmount) {
		this.deductAmount = deductAmount;
	}

	public User getApproverUser() {
		return approverUser;
	}

	public void setApproverUser(User approverUser) {
		this.approverUser = approverUser;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getApproverName() {
			if(StringUtils.isNotEmpty(approverUser.getFirstName()) && StringUtils.isEmpty(approverUser.getLastName()) && StringUtils.isEmpty(approverUser.getMiddelName())){
				approverName = approverUser.getFirstName();
			}else if(StringUtils.isNotEmpty(approverUser.getFirstName()) && StringUtils.isNotEmpty(approverUser.getLastName()) && StringUtils.isEmpty(approverUser.getMiddelName())){
				approverName = approverUser.getFirstName() +" "+approverUser.getLastName();
			}else if(StringUtils.isNotEmpty(approverUser.getFirstName()) && StringUtils.isEmpty(approverUser.getLastName()) && StringUtils.isNotEmpty(approverUser.getMiddelName())){
				approverName = approverUser.getFirstName() +" "+approverUser.getMiddelName();
			}else if(StringUtils.isNotEmpty(approverUser.getFirstName()) && StringUtils.isNotEmpty(approverUser.getLastName()) && StringUtils.isNotEmpty(approverUser.getMiddelName())){
				approverName = approverUser.getFirstName()+" "+approverUser.getLastName()+" "+approverUser.getMiddelName();
			}
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getEffectiveDateStr() {
		
		if(effectiveDate != null) {
			SimpleDateFormat dateFormat = null;
			if(StringUtils.isNotBlank(orgLocationId.getDateFormat())) {
				dateFormat = new SimpleDateFormat(orgLocationId.getDateFormat());
			}else {
				dateFormat = (SimpleDateFormat) Constants.DF_YMD;
			}
			effectiveDateStr = dateFormat.format(effectiveDate);
		}
		
		return effectiveDateStr;
	}

	public void setEffectiveDateStr(String effectiveDateStr) {
		this.effectiveDateStr = effectiveDateStr;
	}
	
	public Date getDeductStartDate()
	{
		return deductStartDate;
	}

	public void setDeductStartDate(Date deductStartDate)
	{
		this.deductStartDate = deductStartDate;
	}

	public Date getDeductEndDate()
	{
		return deductEndDate;
	}

	public void setDeductEndDate(Date deductEndDate)
	{
		this.deductEndDate = deductEndDate;
	}

	public String getDeductionStatus()
	{
		return deductionStatus;
	}

	public void setDeductionStatus(String deductionStatus)
	{
		this.deductionStatus = deductionStatus;
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
	
	
}
