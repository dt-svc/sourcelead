package com.bis.sourcelead.entity;

import java.io.Serializable;
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
@Table(name = "PAYROLL_TAXES")
public class PayrollTax implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", referencedColumnName = "ID")
	private OrgLocations orgLocations;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATE_TAX_ID", referencedColumnName = "ID")
	private StateTax stateTax;

	@Column(name = "FINANCIAL_YEAR")
	private String financialYear;

	@Column(name = "EMPLOYEE_ESI_CONTRIBUTION")
	private BigDecimal employeeESIContribution;

	@Column(name = "EMPLOYER_ESI_CONTRIBUTION")
	private BigDecimal employerESIContribution;

	@Column(name = "EMPLOYEE_PF_CONTRIBUTION")
	private BigDecimal employeePFContribution;

	@Column(name = "EMPLOYER_PF_CONTRIBUTION")
	private BigDecimal employerPFContribution;

	@Column(name = "EDLI")
	private BigDecimal edli;

	@Column(name = "PENSION")
	private BigDecimal pension;

	@Column(name = "PF_ADMIN_CHARGES")
	private BigDecimal pfAdminCharges;

	@Column(name = "EDLI_ADMIN_CHARGES")
	private BigDecimal edliAdminCharges;

	@Column(name = "PF_CEIL_VALUE")
	private BigDecimal pfCeilValue;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@JoinColumn(name = "CREATED_BY", referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private User createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@JoinColumn(name = "UPDATED_BY", referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private User updatedBy;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public OrgLocations getOrgLocations() {
		return orgLocations;
	}

	public void setOrgLocations(OrgLocations orgLocations) {
		this.orgLocations = orgLocations;
	}

	public StateTax getStateTax() {
		return stateTax;
	}

	public void setStateTax(StateTax stateTax) {
		this.stateTax = stateTax;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public BigDecimal getEmployeeESIContribution() {
		return employeeESIContribution;
	}

	public void setEmployeeESIContribution(BigDecimal employeeESIContribution) {
		this.employeeESIContribution = employeeESIContribution;
	}

	public BigDecimal getEmployerESIContribution() {
		return employerESIContribution;
	}

	public void setEmployerESIContribution(BigDecimal employerESIContribution) {
		this.employerESIContribution = employerESIContribution;
	}

	public BigDecimal getEmployeePFContribution() {
		return employeePFContribution;
	}

	public void setEmployeePFContribution(BigDecimal employeePFContribution) {
		this.employeePFContribution = employeePFContribution;
	}

	public BigDecimal getEmployerPFContribution() {
		return employerPFContribution;
	}

	public void setEmployerPFContribution(BigDecimal employerPFContribution) {
		this.employerPFContribution = employerPFContribution;
	}

	public BigDecimal getEdli() {
		return edli;
	}

	public void setEdli(BigDecimal edli) {
		this.edli = edli;
	}

	public BigDecimal getPension() {
		return pension;
	}

	public void setPension(BigDecimal pension) {
		this.pension = pension;
	}

	public BigDecimal getPfAdminCharges() {
		return pfAdminCharges;
	}

	public void setPfAdminCharges(BigDecimal pfAdminCharges) {
		this.pfAdminCharges = pfAdminCharges;
	}

	public BigDecimal getEdliAdminCharges() {
		return edliAdminCharges;
	}

	public void setEdliAdminCharges(BigDecimal edliAdminCharges) {
		this.edliAdminCharges = edliAdminCharges;
	}

	public BigDecimal getPfCeilValue() {
		return pfCeilValue;
	}

	public void setPfCeilValue(BigDecimal pfCeilValue) {
		this.pfCeilValue = pfCeilValue;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

}
