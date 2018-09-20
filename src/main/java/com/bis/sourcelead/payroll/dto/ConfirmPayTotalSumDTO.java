package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ConfirmPayTotalSumDTO {
	
	private double regularHours;
	
	private double overTimeHours;
	
	private double totalHours;
	
    private BigDecimal regularHoursAmount;
	
	private BigDecimal overTimeHoursAmount;
	
	private BigDecimal totalEarnings;

	private BigDecimal ficaWithholding;
	
	private BigDecimal medicareWithholding;
	
	private BigDecimal federalWithholding;
	
	private BigDecimal earnedIncCredit;
	
	private BigDecimal stateWithholding;
	
	private BigDecimal countyWithholding;
	
	private BigDecimal localTax;
	
	private BigDecimal employerFICA;
	
	private BigDecimal employerMedicare;
	
	private BigDecimal employerFUTA;
	
	private BigDecimal employerSUTA;
	
	private BigDecimal employerRUIA;
	
	private BigDecimal employerSutaSurchargeTax;
	
	private BigDecimal employerSDI;
	
	private BigDecimal employerPoliticalSubdivisions;
	
	private BigDecimal employerTransitTaxes;
	
	private BigDecimal employerUHI;
	
	private BigDecimal employerEMAC;
	
	private BigDecimal employerEmacSupplemental;
	
	private BigDecimal employerMCTMT;
	
	private BigDecimal employerWyomingWcTax;
	
	private BigDecimal employerWashingtonIndustrialInsuTax;
	
	private BigDecimal employerOregonWcTax;
	
	private BigDecimal employerHeadTax;
	
	private BigDecimal employerPOP;
	
	private BigDecimal totalTax;
	
	private BigDecimal netPay;
	
	private BigDecimal employerTotalPay;
	
	private Map<String, Double> deduction;
	
	private Map<String, Double> contribution;
	
	private List<Long> pid;
	
	private String pfUUID;
	
	private String confirmType;
	
	private String ppn;
	
	

	public double getTotalHours()
	{
		return totalHours;
	}

	public BigDecimal getTotalEarnings()
	{
		return totalEarnings;
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

	public BigDecimal getEmployerFICA()
	{
		return employerFICA;
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

	public void setTotalHours(double totalHours)
	{
		this.totalHours = totalHours;
	}

	public void setTotalEarnings(BigDecimal totalEarnings)
	{
		this.totalEarnings = totalEarnings;
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

	public void setEmployerFICA(BigDecimal employerFICA)
	{
		this.employerFICA = employerFICA;
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

	public double getRegularHours()
	{
		return regularHours;
	}

	public double getOverTimeHours()
	{
		return overTimeHours;
	}

	public BigDecimal getRegularHoursAmount()
	{
		return regularHoursAmount;
	}

	public BigDecimal getOverTimeHoursAmount()
	{
		return overTimeHoursAmount;
	}

	public void setRegularHours(double regularHours)
	{
		this.regularHours = regularHours;
	}

	public void setOverTimeHours(double overTimeHours)
	{
		this.overTimeHours = overTimeHours;
	}

	public void setRegularHoursAmount(BigDecimal regularHoursAmount)
	{
		this.regularHoursAmount = regularHoursAmount;
	}

	public void setOverTimeHoursAmount(BigDecimal overTimeHoursAmount)
	{
		this.overTimeHoursAmount = overTimeHoursAmount;
	}

	public BigDecimal getTotalTax()
	{
		return totalTax;
	}

	public BigDecimal getNetPay()
	{
		return netPay;
	}

	public void setTotalTax(BigDecimal totalTax)
	{
		this.totalTax = totalTax;
	}

	public void setNetPay(BigDecimal netPay)
	{
		this.netPay = netPay;
	}

	public BigDecimal getEmployerTotalPay()
	{
		return employerTotalPay;
	}

	public void setEmployerTotalPay(BigDecimal employerTotalPay)
	{
		this.employerTotalPay = employerTotalPay;
	}

	public Map<String, Double> getDeduction()
	{
		return deduction;
	}

	public void setDeduction(Map<String, Double> deduction)
	{
		this.deduction = deduction;
	}

	public Map<String, Double> getContribution()
	{
		return contribution;
	}

	public void setContribution(Map<String, Double> contribution)
	{
		this.contribution = contribution;
	}

	public String getPfUUID()
	{
		return pfUUID;
	}

	public void setPfUUID(String pfUUID)
	{
		this.pfUUID = pfUUID;
	}


	public String getConfirmType()
	{
		return confirmType;
	}

	public void setConfirmType(String confirmType)
	{
		this.confirmType = confirmType;
	}

	public List<Long> getPid()
	{
		return pid;
	}

	public void setPid(List<Long> pid)
	{
		this.pid = pid;
	}

	public String getPpn()
	{
		return ppn;
	}

	public void setPpn(String ppn)
	{
		this.ppn = ppn;
	}

	

	
	
}
