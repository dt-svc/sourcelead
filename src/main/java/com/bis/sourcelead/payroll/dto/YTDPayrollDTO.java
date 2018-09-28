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
	
	
}
