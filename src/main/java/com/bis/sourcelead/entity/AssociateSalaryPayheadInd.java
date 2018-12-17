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
@Table(name = "ASSOCIATE_SALARY_PAYHEAD_IND")
public class AssociateSalaryPayheadInd {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@ManyToOne
    @JoinColumn(name="ASSOCIATE_SALARY_IND")        
    private AssociateSalaryInd associateSalaryInd;
	
	@ManyToOne
    @JoinColumn(name="PAYROLL_PAYHEAD")        
    private PayrollPayhead payrollPayhead;
	
	@Column(name = "MONTHLY")
	private BigDecimal monthly;
	
	@Column(name = "ANNUALY")
	private BigDecimal annualy;
	
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
