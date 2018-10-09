package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;
import java.util.Map;

public class YTDPayrollDTO {
	
	private BigDecimal ytdGross;
	
	private BigDecimal ytdNet ;
	
	private BigDecimal ytdTaxes ;
	
	private BigDecimal ytdDeduction ;
	
	private BigDecimal ytdFederal ;
	
	private BigDecimal ytdFICA;
	
	private BigDecimal ytdMedicare;
	
	private BigDecimal ytdCounty;
	
	private BigDecimal ytdlocaltax;
	
	private Map<String, Double> ytdDedTypeMap;
	
	private Map<String, Double> ytdContributionTypeMap;
	
	private BigDecimal contributionYTD;
	
	private BigDecimal contributionMTD;
	
	private BigDecimal contributionQTD;
	
	private BigDecimal deductionYTD;
	
	private BigDecimal deductionMTD;
	
	private BigDecimal deductionQTD;
	
	private BigDecimal ficaYTD;
	
	private BigDecimal medicareYTD;
	
	private BigDecimal federalYTD;
	
	private BigDecimal earnedIncCreditYTD;
	
	private BigDecimal stateWithholdingYTD;
	
	private BigDecimal countyWithholdingYTD;
	
	private BigDecimal suiYTD;
	
	private BigDecimal sdiYTD;
	
	private BigDecimal fliYTD;
	
	private BigDecimal hcsfYTD;
	
	private BigDecimal wcYTD;
	
	private BigDecimal localTaxYTD;
	

	public BigDecimal getContributionYTD()
	{
		return contributionYTD;
	}

	public BigDecimal getContributionMTD()
	{
		return contributionMTD;
	}

	public BigDecimal getContributionQTD()
	{
		return contributionQTD;
	}

	public void setContributionYTD(BigDecimal contributionYTD)
	{
		this.contributionYTD = contributionYTD;
	}

	public void setContributionMTD(BigDecimal contributionMTD)
	{
		this.contributionMTD = contributionMTD;
	}

	public void setContributionQTD(BigDecimal contributionQTD)
	{
		this.contributionQTD = contributionQTD;
	}

	public Map<String, Double> getYtdContributionTypeMap()
	{
		return ytdContributionTypeMap;
	}

	public void setYtdContributionTypeMap(Map<String, Double> ytdContributionTypeMap)
	{
		this.ytdContributionTypeMap = ytdContributionTypeMap;
	}

	public BigDecimal getYtdGross()
	{
		return ytdGross;
	}

	public BigDecimal getYtdNet()
	{
		return ytdNet;
	}

	public BigDecimal getYtdTaxes()
	{
		return ytdTaxes;
	}

	public BigDecimal getYtdDeduction()
	{
		return ytdDeduction;
	}

	public BigDecimal getYtdFederal()
	{
		return ytdFederal;
	}

	public BigDecimal getYtdFICA()
	{
		return ytdFICA;
	}

	public void setYtdGross(BigDecimal ytdGross)
	{
		this.ytdGross = ytdGross;
	}

	public void setYtdNet(BigDecimal ytdNet)
	{
		this.ytdNet = ytdNet;
	}

	public void setYtdTaxes(BigDecimal ytdTaxes)
	{
		this.ytdTaxes = ytdTaxes;
	}

	public void setYtdDeduction(BigDecimal ytdDeduction)
	{
		this.ytdDeduction = ytdDeduction;
	}

	public void setYtdFederal(BigDecimal ytdFederal)
	{
		this.ytdFederal = ytdFederal;
	}

	public void setYtdFICA(BigDecimal ytdFICA)
	{
		this.ytdFICA = ytdFICA;
	}

	public BigDecimal getYtdMedicare()
	{
		return ytdMedicare;
	}

	public BigDecimal getYtdCounty()
	{
		return ytdCounty;
	}

	public BigDecimal getYtdlocaltax()
	{
		return ytdlocaltax;
	}

	public void setYtdMedicare(BigDecimal ytdMedicare)
	{
		this.ytdMedicare = ytdMedicare;
	}

	public void setYtdCounty(BigDecimal ytdCounty)
	{
		this.ytdCounty = ytdCounty;
	}

	public void setYtdlocaltax(BigDecimal ytdlocaltax)
	{
		this.ytdlocaltax = ytdlocaltax;
	}

	public Map<String, Double> getYtdDedTypeMap()
	{
		return ytdDedTypeMap;
	}

	public void setYtdDedTypeMap(Map<String, Double> ytdDedTypeMap)
	{
		this.ytdDedTypeMap = ytdDedTypeMap;
	}

	public BigDecimal getDeductionYTD() {
		return deductionYTD;
	}

	public void setDeductionYTD(BigDecimal deductionYTD) {
		this.deductionYTD = deductionYTD;
	}

	public BigDecimal getDeductionMTD() {
		return deductionMTD;
	}

	public void setDeductionMTD(BigDecimal deductionMTD) {
		this.deductionMTD = deductionMTD;
	}

	public BigDecimal getDeductionQTD() {
		return deductionQTD;
	}

	public void setDeductionQTD(BigDecimal deductionQTD) {
		this.deductionQTD = deductionQTD;
	}

	public BigDecimal getFicaYTD()
	{
		return ficaYTD;
	}

	public BigDecimal getMedicareYTD()
	{
		return medicareYTD;
	}

	public BigDecimal getFederalYTD()
	{
		return federalYTD;
	}

	public BigDecimal getEarnedIncCreditYTD()
	{
		return earnedIncCreditYTD;
	}

	public BigDecimal getStateWithholdingYTD()
	{
		return stateWithholdingYTD;
	}

	public BigDecimal getCountyWithholdingYTD()
	{
		return countyWithholdingYTD;
	}

	public BigDecimal getSuiYTD()
	{
		return suiYTD;
	}

	public BigDecimal getSdiYTD()
	{
		return sdiYTD;
	}

	public BigDecimal getFliYTD()
	{
		return fliYTD;
	}

	public BigDecimal getHcsfYTD()
	{
		return hcsfYTD;
	}

	public BigDecimal getWcYTD()
	{
		return wcYTD;
	}

	public BigDecimal getLocalTaxYTD()
	{
		return localTaxYTD;
	}

	public void setFicaYTD(BigDecimal ficaYTD)
	{
		this.ficaYTD = ficaYTD;
	}

	public void setMedicareYTD(BigDecimal medicareYTD)
	{
		this.medicareYTD = medicareYTD;
	}

	public void setFederalYTD(BigDecimal federalYTD)
	{
		this.federalYTD = federalYTD;
	}

	public void setEarnedIncCreditYTD(BigDecimal earnedIncCreditYTD)
	{
		this.earnedIncCreditYTD = earnedIncCreditYTD;
	}

	public void setStateWithholdingYTD(BigDecimal stateWithholdingYTD)
	{
		this.stateWithholdingYTD = stateWithholdingYTD;
	}

	public void setCountyWithholdingYTD(BigDecimal countyWithholdingYTD)
	{
		this.countyWithholdingYTD = countyWithholdingYTD;
	}

	public void setSuiYTD(BigDecimal suiYTD)
	{
		this.suiYTD = suiYTD;
	}

	public void setSdiYTD(BigDecimal sdiYTD)
	{
		this.sdiYTD = sdiYTD;
	}

	public void setFliYTD(BigDecimal fliYTD)
	{
		this.fliYTD = fliYTD;
	}

	public void setHcsfYTD(BigDecimal hcsfYTD)
	{
		this.hcsfYTD = hcsfYTD;
	}

	public void setWcYTD(BigDecimal wcYTD)
	{
		this.wcYTD = wcYTD;
	}

	public void setLocalTaxYTD(BigDecimal localTaxYTD)
	{
		this.localTaxYTD = localTaxYTD;
	}
	
}
