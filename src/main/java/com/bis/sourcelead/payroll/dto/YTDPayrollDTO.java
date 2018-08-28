package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;

public class YTDPayrollDTO {
	
	private BigDecimal ytdGross;
	
	private BigDecimal ytdNet ;
	
	private BigDecimal ytdTaxes ;
	
	private BigDecimal ytdDeduction ;
	
	private BigDecimal ytdFederal ;
	
	private BigDecimal ytdFICA;
	
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
	
	
}
