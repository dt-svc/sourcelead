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
	
	/*private BigDecimal ficaYTD;
	
	private BigDecimal medicareYTD;
	
	private BigDecimal federalYTD;
	
	private BigDecimal earnedIncCreditYTD;
	
	private BigDecimal stateWithholdingYTD;
	
	private BigDecimal countyWithholdingYTD;*/
	
	private BigDecimal ytdSUI;
	
	private BigDecimal ytdSDI;
	
	private BigDecimal ytdFLI;
	
	private BigDecimal ytdHCSF;
	
	private BigDecimal ytdWC;
	
//	private BigDecimal localTaxYTD;
	
	private BigDecimal ytdStateWithholding;
	
	private BigDecimal ytdEarnedIncCredit;

	public BigDecimal getYtdGross() {
		return ytdGross;
	}

	public BigDecimal getYtdNet() {
		return ytdNet;
	}

	public BigDecimal getYtdTaxes() {
		return ytdTaxes;
	}

	public BigDecimal getYtdDeduction() {
		return ytdDeduction;
	}

	public BigDecimal getYtdFederal() {
		return ytdFederal;
	}

	public BigDecimal getYtdFICA() {
		return ytdFICA;
	}

	public BigDecimal getYtdMedicare() {
		return ytdMedicare;
	}

	public BigDecimal getYtdCounty() {
		return ytdCounty;
	}

	public BigDecimal getYtdlocaltax() {
		return ytdlocaltax;
	}

	public Map<String, Double> getYtdDedTypeMap() {
		return ytdDedTypeMap;
	}

	public Map<String, Double> getYtdContributionTypeMap() {
		return ytdContributionTypeMap;
	}

	public BigDecimal getContributionYTD() {
		return contributionYTD;
	}

	public BigDecimal getContributionMTD() {
		return contributionMTD;
	}

	public BigDecimal getContributionQTD() {
		return contributionQTD;
	}

	public BigDecimal getDeductionYTD() {
		return deductionYTD;
	}

	public BigDecimal getDeductionMTD() {
		return deductionMTD;
	}

	public BigDecimal getDeductionQTD() {
		return deductionQTD;
	}

	public BigDecimal getYtdSUI() {
		return ytdSUI;
	}

	public BigDecimal getYtdSDI() {
		return ytdSDI;
	}

	public BigDecimal getYtdFLI() {
		return ytdFLI;
	}

	public BigDecimal getYtdHCSF() {
		return ytdHCSF;
	}

	public BigDecimal getYtdWC() {
		return ytdWC;
	}

	public BigDecimal getYtdStateWithholding() {
		return ytdStateWithholding;
	}

	public BigDecimal getYtdEarnedIncCredit() {
		return ytdEarnedIncCredit;
	}

	public void setYtdGross(BigDecimal ytdGross) {
		this.ytdGross = ytdGross;
	}

	public void setYtdNet(BigDecimal ytdNet) {
		this.ytdNet = ytdNet;
	}

	public void setYtdTaxes(BigDecimal ytdTaxes) {
		this.ytdTaxes = ytdTaxes;
	}

	public void setYtdDeduction(BigDecimal ytdDeduction) {
		this.ytdDeduction = ytdDeduction;
	}

	public void setYtdFederal(BigDecimal ytdFederal) {
		this.ytdFederal = ytdFederal;
	}

	public void setYtdFICA(BigDecimal ytdFICA) {
		this.ytdFICA = ytdFICA;
	}

	public void setYtdMedicare(BigDecimal ytdMedicare) {
		this.ytdMedicare = ytdMedicare;
	}

	public void setYtdCounty(BigDecimal ytdCounty) {
		this.ytdCounty = ytdCounty;
	}

	public void setYtdlocaltax(BigDecimal ytdlocaltax) {
		this.ytdlocaltax = ytdlocaltax;
	}

	public void setYtdDedTypeMap(Map<String, Double> ytdDedTypeMap) {
		this.ytdDedTypeMap = ytdDedTypeMap;
	}

	public void setYtdContributionTypeMap(Map<String, Double> ytdContributionTypeMap) {
		this.ytdContributionTypeMap = ytdContributionTypeMap;
	}

	public void setContributionYTD(BigDecimal contributionYTD) {
		this.contributionYTD = contributionYTD;
	}

	public void setContributionMTD(BigDecimal contributionMTD) {
		this.contributionMTD = contributionMTD;
	}

	public void setContributionQTD(BigDecimal contributionQTD) {
		this.contributionQTD = contributionQTD;
	}

	public void setDeductionYTD(BigDecimal deductionYTD) {
		this.deductionYTD = deductionYTD;
	}

	public void setDeductionMTD(BigDecimal deductionMTD) {
		this.deductionMTD = deductionMTD;
	}

	public void setDeductionQTD(BigDecimal deductionQTD) {
		this.deductionQTD = deductionQTD;
	}

	public void setYtdSUI(BigDecimal ytdSUI) {
		this.ytdSUI = ytdSUI;
	}

	public void setYtdSDI(BigDecimal ytdSDI) {
		this.ytdSDI = ytdSDI;
	}

	public void setYtdFLI(BigDecimal ytdFLI) {
		this.ytdFLI = ytdFLI;
	}

	public void setYtdHCSF(BigDecimal ytdHCSF) {
		this.ytdHCSF = ytdHCSF;
	}

	public void setYtdWC(BigDecimal ytdWC) {
		this.ytdWC = ytdWC;
	}

	public void setytdStateWithholding(BigDecimal ytdStateWithholding) {
		this.ytdStateWithholding = ytdStateWithholding;
	}

	public void setYtdEarnedIncCredit(BigDecimal ytdEarnedIncCredit) {
		this.ytdEarnedIncCredit = ytdEarnedIncCredit;
	}
	

	
}
