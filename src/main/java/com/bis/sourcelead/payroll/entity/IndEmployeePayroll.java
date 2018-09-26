package com.bis.sourcelead.payroll.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bis.sourcelead.entity.AssociatePayrollPayheadHistory;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Employees;

@Entity
@Table(name = "IND_EMPLOYEE_PAYROLL")
public class IndEmployeePayroll implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employees employee;

	@ManyToOne
	@JoinColumn(name = "CANDIDATE_ID", nullable = false)
	private Candidate candidate;

	@ManyToOne
	@JoinColumn(name = "IND_PAY_SHEET_FREQUENCY_ID", nullable = false)
	private IndPaysheetFrequency indPaysheetFrequency;


	@Column(name = "REGULAR_HOURS")
	private String regularHours;

	@Column(name = "OVER_TIME_AMOUNT")
	private BigDecimal overTimeAmount;

	@Column(name = "OVER_TIME_HOURS")
	private String overTimeHours;

	@Column(name = "NO_OF_LEAVES")
	private BigDecimal noOfLeaves;
	
	@Column(name = "NO_OF_DAYS")
	private BigDecimal noOfDays;
	
	@Column(name = "EMP_PAY_RATE")
	private BigDecimal empPayRate;

	@Column(name = "GROSS_PAY")
	private BigDecimal grossPay;

	@Column(name = "NET_PAY")
	private BigDecimal netPay;

	@Column(name = "PROFESSIONAL_TAX")
	private BigDecimal professionalTax;

	@Column(name = "TDS")
	private BigDecimal tds;

	@Column(name = "SUBMIT_FOR_APROVER_STATUS")
	private String submitForAproverStatus;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "indEmployeePayrollId")
	private Set<AssociatePayrollPayheadHistory> associatePayrollPayheadHistory;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employees getEmployee() {
		return employee;
	}

	public void setEmployee(Employees employee) {
		this.employee = employee;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public IndPaysheetFrequency getIndPaysheetFrequency() {
		return indPaysheetFrequency;
	}

	public void setIndPaysheetFrequency(IndPaysheetFrequency indPaysheetFrequency) {
		this.indPaysheetFrequency = indPaysheetFrequency;
	}

	public BigDecimal getEmpPayRate() {
		return empPayRate;
	}

	public void setEmpPayRate(BigDecimal empPayRate) {
		this.empPayRate = empPayRate;
	}

	public String getRegularHours() {
		return regularHours;
	}

	public void setRegularHours(String regularHours) {
		this.regularHours = regularHours;
	}

	public BigDecimal getOverTimeAmount() {
		return overTimeAmount;
	}

	public void setOverTimeAmount(BigDecimal overTimeAmount) {
		this.overTimeAmount = overTimeAmount;
	}

	public String getOverTimeHours() {
		return overTimeHours;
	}

	public void setOverTimeHours(String overTimeHours) {
		this.overTimeHours = overTimeHours;
	}

	public BigDecimal getNoOfLeaves() {
		return noOfLeaves;
	}

	public void setNoOfLeaves(BigDecimal noOfLeaves) {
		this.noOfLeaves = noOfLeaves;
	}

	public BigDecimal getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(BigDecimal noOfDays) {
		this.noOfDays = noOfDays;
	}

	public BigDecimal getGrossPay() {
		return grossPay;
	}

	public void setGrossPay(BigDecimal grossPay) {
		this.grossPay = grossPay;
	}

	public BigDecimal getNetPay() {
		return netPay;
	}

	public void setNetPay(BigDecimal netPay) {
		this.netPay = netPay;
	}

	public BigDecimal getProfessionalTax() {
		return professionalTax;
	}

	public void setProfessionalTax(BigDecimal professionalTax) {
		this.professionalTax = professionalTax;
	}

	public BigDecimal getTds() {
		return tds;
	}

	public void setTds(BigDecimal tds) {
		this.tds = tds;
	}

	public String getSubmitForAproverStatus() {
		return submitForAproverStatus;
	}

	public void setSubmitForAproverStatus(String submitForAproverStatus) {
		this.submitForAproverStatus = submitForAproverStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Set<AssociatePayrollPayheadHistory> getAssociatePayrollPayheadHistory() {
		return associatePayrollPayheadHistory;
	}

	public void setAssociatePayrollPayheadHistory(Set<AssociatePayrollPayheadHistory> associatePayrollPayheadHistory) {
		this.associatePayrollPayheadHistory = associatePayrollPayheadHistory;
	}

	
}
