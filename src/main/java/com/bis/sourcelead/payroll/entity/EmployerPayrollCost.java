package com.bis.sourcelead.payroll.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bis.sourcelead.entity.OrgLocations;

@Entity
@Table(name = "EMPLOYER_PAYROLL_COST", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "employerPayrollCost")
public class EmployerPayrollCost implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;

	@Column(name = "EMPLOYEE_ID")
	private String employeeId;

	@Column(name = "EMPLOYEE_NAME")
	private String employeeName;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "TOTAL_HRS")
	private String totalHrs;

	@Column(name = "GROSS_WAGES")
	private float grossWages;

	@Column(name = "FICA")
	private float fica;

	@Column(name = "FUTA")
	private float futa;

	@Column(name = "SUI")
	private float sui;

	@Column(name = "ER_LOCALS")
	private float erLocals;

	@Column(name = "WC_COST")
	private float wcCost;

	@Column(name = "ADMIN_FEE")
	private float adminFee;

	@Column(name = "TOTAL_ER_COST")
	private float totalErCost;
	
	@Column(name = "FROM_DATE")
	private Date fromDate;
	
	@Column(name = "TO_DATE")
	private Date 	toDate;

	@Column(name = "BATCH_NUMBER")
	private String batchNumber;
	
	@Column(name = "STATUS")
	private String status;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
}
