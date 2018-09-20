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

@Entity
@Table(name = "US_EMPLOYER_PAYROLL_TAX")
public class EmployerPayrollTax {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "US_EMP_PAYROLL_ID",nullable = false)
	private EmployeePayroll employeePayroll;
	
	@Column (name = "EMPLOYER_FICA")
	private BigDecimal employerFICA;
	
	@Column (name = "EMPLOYER_MEDICARE")
	private BigDecimal employerMedicare;
	
	@Column (name = "EMPLOYER_FUTA")
	private BigDecimal employerFUTA;
	
	@Column (name = "EMPLOYER_SUTA")
	private BigDecimal employerSUTA;
	
	@Column (name = "EMPLOYER_RUIA")
	private BigDecimal employerRUIA;
	
	@Column (name = "EMPLOYER_SUTA_SURCHARGE_TAXE")
	private BigDecimal employerSutaSurchargeTax;
	
	@Column (name = "EMPLOYER_SDI")
	private BigDecimal employerSDI;
	
	@Column (name = "EMPLOYER_POLITICAL_SUBDIVISIONS")
	private BigDecimal employerPoliticalSubdivisions;
	
	@Column (name = "EMPLOYER_TRANSIT_TAXES")
	private BigDecimal employerTransitTaxes;
	
	@Column (name = "EMPLOYER_UHI")
	private BigDecimal employerUHI;
	
	@Column (name = "EMPLOYER_EMAC")
	private BigDecimal employerEMAC;
	
	@Column (name = "EMPLOYER_EMAC_SUPPLEMENTAL")
	private BigDecimal employerEmacSupplemental;
	
	@Column (name = "EMPLOYER_MCTMT")
	private BigDecimal employerMCTMT;
	
	@Column (name = "EMPLOYER_WYOMING_WC_TAX")
	private BigDecimal employerWyomingWcTax;
	
	@Column (name = "EMPLOYER_WASHINGTON_INDUSTRIAL_INSU_TAX")
	private BigDecimal employerWashingtonIndustrialInsuTax;
	
	@Column (name = "EMPLOYER_OREGON_WC_TAX")
	private BigDecimal employerOregonWcTax;
	
	@Column (name = "EMPLOYER_HEAD_TAX")
	private BigDecimal employerHeadTax;
	
	@Column (name = "EMPLOYER_POP")
	private BigDecimal employerPOP;
	
	@Column (name = "US_EMPLOYER_PAYROLL_TAX_UUID")
	private String usEmployerPayrollTaxUUID;
	
	
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


	public BigDecimal getEmployerMedicare()
	{
		return employerMedicare;
	}

	public BigDecimal getEmployerFUTA()
	{
		return employerFUTA;
	}

	public BigDecimal getEmployerSUTA()
	{
		return employerSUTA;
	}

	public BigDecimal getEmployerRUIA()
	{
		return employerRUIA;
	}

	public BigDecimal getEmployerSutaSurchargeTax()
	{
		return employerSutaSurchargeTax;
	}

	public BigDecimal getEmployerSDI()
	{
		return employerSDI;
	}

	public BigDecimal getEmployerPoliticalSubdivisions()
	{
		return employerPoliticalSubdivisions;
	}

	public BigDecimal getEmployerTransitTaxes()
	{
		return employerTransitTaxes;
	}

	public BigDecimal getEmployerUHI()
	{
		return employerUHI;
	}

	public BigDecimal getEmployerEMAC()
	{
		return employerEMAC;
	}

	public BigDecimal getEmployerEmacSupplemental()
	{
		return employerEmacSupplemental;
	}

	public BigDecimal getEmployerMCTMT()
	{
		return employerMCTMT;
	}

	public BigDecimal getEmployerWyomingWcTax()
	{
		return employerWyomingWcTax;
	}

	public BigDecimal getEmployerWashingtonIndustrialInsuTax()
	{
		return employerWashingtonIndustrialInsuTax;
	}

	public BigDecimal getEmployerOregonWcTax()
	{
		return employerOregonWcTax;
	}

	public BigDecimal getEmployerHeadTax()
	{
		return employerHeadTax;
	}

	public BigDecimal getEmployerPOP()
	{
		return employerPOP;
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


	public void setEmployerMedicare(BigDecimal employerMedicare)
	{
		this.employerMedicare = employerMedicare;
	}

	public void setEmployerFUTA(BigDecimal employerFUTA)
	{
		this.employerFUTA = employerFUTA;
	}

	public void setEmployerSUTA(BigDecimal employerSUTA)
	{
		this.employerSUTA = employerSUTA;
	}

	public void setEmployerRUIA(BigDecimal employerRUIA)
	{
		this.employerRUIA = employerRUIA;
	}

	public void setEmployerSutaSurchargeTax(BigDecimal employerSutaSurchargeTax)
	{
		this.employerSutaSurchargeTax = employerSutaSurchargeTax;
	}

	public void setEmployerSDI(BigDecimal employerSDI)
	{
		this.employerSDI = employerSDI;
	}

	public void setEmployerPoliticalSubdivisions(BigDecimal employerPoliticalSubdivisions)
	{
		this.employerPoliticalSubdivisions = employerPoliticalSubdivisions;
	}

	public void setEmployerTransitTaxes(BigDecimal employerTransitTaxes)
	{
		this.employerTransitTaxes = employerTransitTaxes;
	}

	public void setEmployerUHI(BigDecimal employerUHI)
	{
		this.employerUHI = employerUHI;
	}

	public void setEmployerEMAC(BigDecimal employerEMAC)
	{
		this.employerEMAC = employerEMAC;
	}

	public void setEmployerEmacSupplemental(BigDecimal employerEmacSupplemental)
	{
		this.employerEmacSupplemental = employerEmacSupplemental;
	}

	public void setEmployerMCTMT(BigDecimal employerMCTMT)
	{
		this.employerMCTMT = employerMCTMT;
	}

	public void setEmployerWyomingWcTax(BigDecimal employerWyomingWcTax)
	{
		this.employerWyomingWcTax = employerWyomingWcTax;
	}

	public void setEmployerWashingtonIndustrialInsuTax(BigDecimal employerWashingtonIndustrialInsuTax)
	{
		this.employerWashingtonIndustrialInsuTax = employerWashingtonIndustrialInsuTax;
	}

	public void setEmployerOregonWcTax(BigDecimal employerOregonWcTax)
	{
		this.employerOregonWcTax = employerOregonWcTax;
	}

	public void setEmployerHeadTax(BigDecimal employerHeadTax)
	{
		this.employerHeadTax = employerHeadTax;
	}

	public void setEmployerPOP(BigDecimal employerPOP)
	{
		this.employerPOP = employerPOP;
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

	public String getUsEmployerPayrollTaxUUID()
	{
		return usEmployerPayrollTaxUUID;
	}

	public void setUsEmployerPayrollTaxUUID(String usEmployerPayrollTaxUUID)
	{
		this.usEmployerPayrollTaxUUID = usEmployerPayrollTaxUUID;
	}

	public BigDecimal getEmployerFICA()
	{
		return employerFICA;
	}

	public void setEmployerFICA(BigDecimal employerFICA)
	{
		this.employerFICA = employerFICA;
	}
	
	
}
