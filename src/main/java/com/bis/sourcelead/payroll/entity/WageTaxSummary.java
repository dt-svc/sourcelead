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
@Table(name = "WAGE_TAX_SUMMARY", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "wageTaxSummary")
public class WageTaxSummary implements Serializable {
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

	@Column(name = "CHECK_DATE")
	private Date checkDate;

	@Column(name = "CHECK_NO")
	private String checkNo;

	@Column(name = "GROSS")
	private float gross;

	@Column(name = "FWT")
	private float fwt;

	@Column(name = "SSEC")
	private float ssec;

	@Column(name = "MED")
	private float med;

	@Column(name = "SWT")
	private float swt;

	@Column(name = "SDI_SUI")
	private float sdiSui;

	@Column(name = "LOCAL")
	private float local;

	@Column(name = "DEDS")
	private float deds;

	@Column(name = "NET")
	private float net;
	
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
