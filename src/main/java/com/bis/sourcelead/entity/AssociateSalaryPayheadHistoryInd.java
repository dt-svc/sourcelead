package com.bis.sourcelead.entity;

import java.math.BigDecimal;
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

@Entity
@Table(name = "ASSOCIATE_SALARY_PAYHEAD_HISTORY_IND")
public class AssociateSalaryPayheadHistoryInd {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@ManyToOne
    @JoinColumn(name="ASSOCIATE_SALARY_IND")        
    private AssociateSalaryInd associateSalaryInd;
	
	@ManyToOne
    @JoinColumn(name="ASSOCIATE_SALARY_HISTORY_IND")        
    private AssociateSalaryHistoryInd associateSalaryHistoryInd;
	
	@ManyToOne
    @JoinColumn(name="PAYROLL_PAYHEAD")        
    private PayrollPayhead payrollPayhead;
	
	@Column(name = "MONTHLY")
	private BigDecimal monthly;
	
	@Column(name = "ANNUALY")
	private BigDecimal annualy;
	
	@ManyToOne
    @JoinColumn(name="PREVIOUS_PAYROLL_PAYHEAD")        
    private PayrollPayhead previousPayrollPayhead;
	
	@Column(name = "PREVIOUS_MONTHLY")
	private BigDecimal previousMonthly;
	
	@Column(name = "PREVIOUS_ANNUALY")
	private BigDecimal previousAnnualy;
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "PREVIOUS_CREATED_BY")
  	private User previousCreatedBy;
    
    @Column(name = "PREVIOUS_CREATED_DATE")
  	private Date previousCreatedDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "PREVIOUS_UPDATED_BY")
  	private User previousUpdatedBy;
    
    @Column(name = "PREVIOUS_UPDATED_DATE")
  	private Date previousUpdatedDate;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "UUID")
	private String uuid;
    
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "CREATED_BY")
  	private User createdBy;
    
    @Column(name = "CREATED_DATE")
  	private Date createdDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "UPDATED_BY")
  	private User updatedBy;
    
    @Column(name = "UPDATED_DATE")
  	private Date updatedDate;
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AssociateSalaryInd getAssociateSalaryInd() {
		return associateSalaryInd;
	}

	public void setAssociateSalaryInd(AssociateSalaryInd associateSalaryInd) {
		this.associateSalaryInd = associateSalaryInd;
	}

	public AssociateSalaryHistoryInd getAssociateSalaryHistoryInd() {
		return associateSalaryHistoryInd;
	}

	public void setAssociateSalaryHistoryInd(AssociateSalaryHistoryInd associateSalaryHistoryInd) {
		this.associateSalaryHistoryInd = associateSalaryHistoryInd;
	}

	public PayrollPayhead getPayrollPayhead() {
		return payrollPayhead;
	}

	public void setPayrollPayhead(PayrollPayhead payrollPayhead) {
		this.payrollPayhead = payrollPayhead;
	}

	public BigDecimal getMonthly() {
		return monthly;
	}

	public void setMonthly(BigDecimal monthly) {
		this.monthly = monthly;
	}

	public BigDecimal getAnnualy() {
		return annualy;
	}

	public void setAnnualy(BigDecimal annualy) {
		this.annualy = annualy;
	}

	public PayrollPayhead getPreviousPayrollPayhead() {
		return previousPayrollPayhead;
	}

	public void setPreviousPayrollPayhead(PayrollPayhead previousPayrollPayhead) {
		this.previousPayrollPayhead = previousPayrollPayhead;
	}

	public BigDecimal getPreviousMonthly() {
		return previousMonthly;
	}

	public void setPreviousMonthly(BigDecimal previousMonthly) {
		this.previousMonthly = previousMonthly;
	}

	public BigDecimal getPreviousAnnualy() {
		return previousAnnualy;
	}

	public void setPreviousAnnualy(BigDecimal previousAnnualy) {
		this.previousAnnualy = previousAnnualy;
	}

	public User getPreviousCreatedBy() {
		return previousCreatedBy;
	}

	public void setPreviousCreatedBy(User previousCreatedBy) {
		this.previousCreatedBy = previousCreatedBy;
	}

	public Date getPreviousCreatedDate() {
		return previousCreatedDate;
	}

	public void setPreviousCreatedDate(Date previousCreatedDate) {
		this.previousCreatedDate = previousCreatedDate;
	}

	public User getPreviousUpdatedBy() {
		return previousUpdatedBy;
	}

	public void setPreviousUpdatedBy(User previousUpdatedBy) {
		this.previousUpdatedBy = previousUpdatedBy;
	}

	public Date getPreviousUpdatedDate() {
		return previousUpdatedDate;
	}

	public void setPreviousUpdatedDate(Date previousUpdatedDate) {
		this.previousUpdatedDate = previousUpdatedDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}
