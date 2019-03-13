package com.bis.sourcelead.payroll.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.symmetry.ste.Money;

@Entity
@Table(name = "US_EMP_PAYROLL_TAX")
public class EmployeePayrollTax {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "US_EMP_PAYROLL_ID",nullable = false)
	private EmployeePayroll employeePayroll;
	
	@Column(name = "FICA_WITHHOLDING")
	private BigDecimal ficaWithholding;
	
	@Column(name = "MEDICARE_WITHHOLDING")
	private BigDecimal medicareWithholding;
	
	@Column(name = "FEDERAL_WITHHOLDING")
	private BigDecimal federalWithholding;
	
	@Column(name = "EARNED_INC_CREDIT")
	private BigDecimal earnedIncCredit;
	
	@Column(name = "STATE_WITHHOLDING")
	private BigDecimal stateWithholding;
	
	@Column(name = "COUNTY_WITHHOLDING")
	private BigDecimal countyWithholding;
	
	@Column(name = "SUI")
	private BigDecimal SUI;
	
	@Column(name = "SDI")
	private BigDecimal SDI;
	
	@Column(name = "FLI")
	private BigDecimal FLI;
	
	@Column(name = "HCSF")
	private BigDecimal HCSF;
	
	@Column(name = "WC")
	private BigDecimal WC;
	
	@Column(name = "LOCAL_TAX")
	private BigDecimal localTax;
	
	@Column(name = "LOCATION_CODE")
	private String locationCode;
	
	@Column(name = "US_EMP_PAYROLL_TAX_UUID")
	private String usEmpPayrollTaxUUID;
	
	
	@Column (name = "STE_VERSION", columnDefinition = "TEXT default NULL")
	private String steVersion;
	
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private Long createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private Long updatedBy;
	
	
	
	

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public Long getId()
	{
		return id;
	}

	public EmployeePayroll getEmployeePayroll()
	{
		return employeePayroll;
	}

	public BigDecimal getFicaWithholding()
	{
		return ficaWithholding;
	}

	public BigDecimal getMedicareWithholding()
	{
		return medicareWithholding;
	}

	public BigDecimal getFederalWithholding()
	{
		return federalWithholding;
	}

	public BigDecimal getEarnedIncCredit()
	{
		return earnedIncCredit;
	}

	public BigDecimal getStateWithholding()
	{
		return stateWithholding;
	}

	public BigDecimal getCountyWithholding()
	{
		return countyWithholding;
	}

	public BigDecimal getLocalTax()
	{
		return localTax;
	}

	public String getLocationCode()
	{
		return locationCode;
	}

	public Integer getStatus()
	{
		return status;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public Long getCreatedBy()
	{
		return createdBy;
	}

	public Date getUpdatedDate()
	{
		return updatedDate;
	}

	public Long getUpdatedBy()
	{
		return updatedBy;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setEmployeePayroll(EmployeePayroll employeePayroll)
	{
		this.employeePayroll = employeePayroll;
	}

	public void setFicaWithholding(BigDecimal ficaWithholding)
	{
		this.ficaWithholding = ficaWithholding;
	}

	public void setMedicareWithholding(BigDecimal medicareWithholding)
	{
		this.medicareWithholding = medicareWithholding;
	}

	public void setFederalWithholding(BigDecimal federalWithholding)
	{
		this.federalWithholding = federalWithholding;
	}

	public void setEarnedIncCredit(BigDecimal earnedIncCredit)
	{
		this.earnedIncCredit = earnedIncCredit;
	}

	public void setStateWithholding(BigDecimal stateWithholding)
	{
		this.stateWithholding = stateWithholding;
	}

	public void setCountyWithholding(BigDecimal countyWithholding)
	{
		this.countyWithholding = countyWithholding;
	}

	public void setLocalTax(BigDecimal localTax)
	{
		this.localTax = localTax;
	}

	public void setLocationCode(String locationCode)
	{
		this.locationCode = locationCode;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public void setCreatedBy(Long createdBy)
	{
		this.createdBy = createdBy;
	}

	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	public void setUpdatedBy(Long updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	public String getUsEmpPayrollTaxUUID()
	{
		return usEmpPayrollTaxUUID;
	}

	public String getSteVersion()
	{
		return steVersion;
	}

	public void setUsEmpPayrollTaxUUID(String usEmpPayrollTaxUUID)
	{
		this.usEmpPayrollTaxUUID = usEmpPayrollTaxUUID;
	}

	public void setSteVersion(String steVersion)
	{
		this.steVersion = steVersion;
	}

	public BigDecimal getSUI()
	{
		if(SUI != null) {
			return SUI;	
		}else {
			return BigDecimal.ZERO;
		}
	}

	public BigDecimal getSDI()
	{
		if(SDI != null) {
			return SDI;
		}else {
			return BigDecimal.ZERO;
		}
	}

	public BigDecimal getFLI()
	{
		if(FLI != null) {
			return FLI;
		}else {
			return BigDecimal.ZERO;
		}
	}

	public BigDecimal getHCSF()
	{
		if(HCSF != null) {
			return HCSF;
		}else {
			return BigDecimal.ZERO;
		}
	}

	public BigDecimal getWC()
	{
		if(WC != null) {
			return WC;
		}else {
			return BigDecimal.ZERO;
		}
	}

	public void setSUI(BigDecimal sUI)
	{
		SUI = sUI;
	}

	public void setSDI(BigDecimal sDI)
	{
		SDI = sDI;
	}

	public void setFLI(BigDecimal fLI)
	{
		FLI = fLI;
	}

	public void setHCSF(BigDecimal hCSF)
	{
		HCSF = hCSF;
	}

	public void setWC(BigDecimal wC)
	{
		WC = wC;
	}
	
	
	
}
