package com.bis.sourcelead.indPayroll.entity;

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

import com.bis.sourcelead.entity.PayrollPayhead;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;

@Entity
@Table(name = "ASSOCIATE_PAYROLL_PAYHEAD_HISTORY")
public class AssociatePayrollPayheadHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@ManyToOne
    @JoinColumn(name="IND_EMPLOYEE_PAYROLL_ID")        
    private IndEmployeePayroll indEmployeePayrollId;
	
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

	public IndEmployeePayroll getIndEmployeePayrollId() {
		return indEmployeePayrollId;
	}

	public PayrollPayhead getPayrollPayhead() {
		return payrollPayhead;
	}

	public BigDecimal getMonthly() {
		return monthly;
	}

	public BigDecimal getAnnualy() {
		return annualy;
	}

	public Integer getStatus() {
		return status;
	}

	public String getUuid() {
		return uuid;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIndEmployeePayrollId(IndEmployeePayroll indEmployeePayrollId) {
		this.indEmployeePayrollId = indEmployeePayrollId;
	}

	public void setPayrollPayhead(PayrollPayhead payrollPayhead) {
		this.payrollPayhead = payrollPayhead;
	}

	public void setMonthly(BigDecimal monthly) {
		this.monthly = monthly;
	}

	public void setAnnualy(BigDecimal annualy) {
		this.annualy = annualy;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	
}
