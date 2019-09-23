package com.bis.sourcelead.payroll.entity;

import java.io.Serializable;
import java.text.DecimalFormat;
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

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.util.ConversionUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "CHECKS")
public class Checks implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "EMP_ID")
	private String  empId;
	
	@Column(name = "EMP_NAME")
	private String empName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;
	
	@Column(name = "Date")
	private Date date;
	
	@Column(name = "TYPE_OF_EXPENSE")
	private String typeOfExpense;
	
	@Column(name = "BANK")
	private String bankName;
	
	@Column(name = "MONTH")
	private String month;
	
	@Column(name = "LEGAL_FEES")
	private double legalFees;
	
	@Column(name = "ADVANCE")
	private double advance;
	
	@Column(name = "GENERAL_EXPENSES")
	private double generalExpenses;
	
	@Column(name = "VENDOR_PAYMENTS")
	private double vendorPayments;
	
	
	@Column(name = "ELECTRONIC_WITHDRAWALS")
	private String electronicWithdrawals;
	
	@Column(name = "HEALTH_INSURANCE")
	private String healthInsurance;
	
	@Column(name = "IMMIGRATION")
	private String immigration;
	
	@Column(name = "PAYROLL_CHECKS")
	private double payrollChecks;
	
	@Column(name = "AMOUNT")
	private double amount;
	
	@Column(name = "CHECK_NO")
	private String checkNo;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "VERSION")
	private int version;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "UUID")
	private String uuid;
	
	@Transient
	@JsonProperty
	private String dateStr;
	
	@Transient
	@JsonProperty
	private String advanceStr;
	
	@Transient
	@JsonProperty
	private String generalExpensesStr;
	
	@Transient
	@JsonProperty
	private String vendorPaymentsStr;
	
	@Transient
	@JsonProperty
	private String payrollChecksStr;
	
	@Transient
	@JsonProperty
	private String amountStr;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTypeOfExpense() {
		return typeOfExpense;
	}

	public void setTypeOfExpense(String typeOfExpense) {
		this.typeOfExpense = typeOfExpense;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public double getLegalFees() {
		return legalFees;
	}

	public void setLegalFees(double legalFees) {
		this.legalFees = legalFees;
	}

	public double getAdvance() {
		return advance;
	}

	public void setAdvance(double advance) {
		this.advance = advance;
	}

	public double getGeneralExpenses() {
		return generalExpenses;
	}

	public void setGeneralExpenses(double generalExpenses) {
		this.generalExpenses = generalExpenses;
	}

	public double getVendorPayments() {
		return vendorPayments;
	}

	public void setVendorPayments(double vendorPayments) {
		this.vendorPayments = vendorPayments;
	}


	public String getElectronicWithdrawals() {
		return electronicWithdrawals;
	}

	public void setElectronicWithdrawals(String electronicWithdrawals) {
		this.electronicWithdrawals = electronicWithdrawals;
	}

	public double getPayrollChecks() {
		return payrollChecks;
	}

	public void setPayrollChecks(double payrollChecks) {
		this.payrollChecks = payrollChecks;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDateStr() {
		
		if(date != null){
			dateStr = ConversionUtil.getDateAsddMMMYYYY(date);
		}
		
		
		return dateStr;
	}

	public String getAdvanceStr() {
		DecimalFormat df = new DecimalFormat("#.00");
		if(advance > 0){
			advanceStr = df.format(advance);
		}
		
		return advanceStr;
	}

	public String getGeneralExpensesStr() {
		DecimalFormat df = new DecimalFormat("#.00");
		if(generalExpenses > 0){
			generalExpensesStr = df.format(generalExpenses);
		}
		return generalExpensesStr;
	}

	public String getVendorPaymentsStr() {
		DecimalFormat df = new DecimalFormat("#.00");
		if(vendorPayments > 0){
			vendorPaymentsStr = df.format(vendorPayments);
		}
		return vendorPaymentsStr;
	}

	public String getPayrollChecksStr() {
		DecimalFormat df = new DecimalFormat("#.00");
		if(payrollChecks > 0){
			payrollChecksStr = df.format(payrollChecks);
		}
		return payrollChecksStr;
	}

	public String getAmountStr() {
		DecimalFormat df = new DecimalFormat("#.00");
		if(amount > 0){
			amountStr = df.format(amount);
		}
		return amountStr;
	}

	public int getVersion()
	{
		return version;
	}

	public void setVersion(int version)
	{
		this.version = version;
	}

	public String getHealthInsurance() {
		return healthInsurance;
	}

	public void setHealthInsurance(String healthInsurance) {
		this.healthInsurance = healthInsurance;
	}

	public String getImmigration() {
		return immigration;
	}

	public void setImmigration(String immigration) {
		this.immigration = immigration;
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}
}
