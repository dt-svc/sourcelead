package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class CompareDataDTO {
	
	private double totalRegHours;
	private double totalOTHours;
	private BigDecimal totalGross;
	private BigDecimal netPay;
	
	private Map<String,Double> contriMap;
	private Map<String,Double> dedMap;
	private Map<String,Double> payItemMap;
	private Map<String,BigDecimal> taxMap;
	
	public double getTotalRegHours() {
		return totalRegHours;
	}
	public void setTotalRegHours(double totalRegHours) {
		this.totalRegHours = totalRegHours;
	}
	public double getTotalOTHours() {
		return totalOTHours;
	}
	public void setTotalOTHours(double totalOTHours) {
		this.totalOTHours = totalOTHours;
	}
	public BigDecimal getTotalGross() {
		return totalGross;
	}
	public void setTotalGross(BigDecimal totalGross) {
		this.totalGross = totalGross;
	}
	public BigDecimal getNetPay() {
		return netPay;
	}
	public void setNetPay(BigDecimal netPay) {
		this.netPay = netPay;
	}
	public Map<String, Double> getContriMap() {
		return contriMap;
	}
	public void setContriMap(Map<String, Double> contriMap) {
		this.contriMap = contriMap;
	}
	public Map<String, Double> getDedMap() {
		return dedMap;
	}
	public void setDedMap(Map<String, Double> dedMap) {
		this.dedMap = dedMap;
	}
	public Map<String, Double> getPayItemMap() {
		return payItemMap;
	}
	public void setPayItemMap(Map<String, Double> payItemMap) {
		this.payItemMap = payItemMap;
	}
	public Map<String, BigDecimal> getTaxMap() {
		return taxMap;
	}
	public void setTaxMap(Map<String, BigDecimal> taxMap) {
		this.taxMap = taxMap;
	}

}
