package com.bis.sourcelead.entity;

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

@Entity
@Table(name = "PAYROLL_PAYHEAD_SALARY_SETUP_HISTORY")
public class PayrollPayheadSalarySetup {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@ManyToOne
    @JoinColumn(name="PAYHEAD")        
    private PayrollPayhead payhead;
	
	@Column(name="PAYHEAD_NAME")        
    private String payheadName;
	
	@Column(name = "METHOD")
	private String method;
	
	@Column(name = "FORMULA", columnDefinition = "TEXT default NULL")
	private String formula;
    
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "CREATED_BY")
  	private User createdBy;
    
    @Column(name = "CREATED_DATE")
  	private Date createdDate;
    
    @Transient
    private User updatedBy;

    @Transient
	private Integer status;
    
    @Transient
   	private Date updatedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PayrollPayhead getPayhead() {
		return payhead;
	}

	public void setPayhead(PayrollPayhead payhead) {
		this.payhead = payhead;
	}

	public String getPayheadName() {
		return payheadName;
	}

	public void setPayheadName(String payheadName) {
		this.payheadName = payheadName;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
   	
}
