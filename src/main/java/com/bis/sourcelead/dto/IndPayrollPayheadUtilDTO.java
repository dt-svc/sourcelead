package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;

import com.bis.sourcelead.indPayroll.entity.AssociatePayrollPayheadHistory;

public class IndPayrollPayheadUtilDTO implements Serializable{
	
	 	private static final long serialVersionUID = 1L;
	 	
	 	private HashSet<AssociatePayrollPayheadHistory> associatePayrollPayheadHistory;
	 	
	 	private BigDecimal grossPay;
	 	
	 	private BigDecimal netPay;
	 	
	 	private BigDecimal professionalTax;
	 	
	 	private BigDecimal tds;

		public HashSet<AssociatePayrollPayheadHistory> getAssociatePayrollPayheadHistory() {
			return associatePayrollPayheadHistory;
		}

		public void setAssociatePayrollPayheadHistory(HashSet<AssociatePayrollPayheadHistory> associatePayrollPayheadHistory) {
			this.associatePayrollPayheadHistory = associatePayrollPayheadHistory;
		}

		public BigDecimal getGrossPay() {
			return grossPay;
		}

		public void setGrossPay(BigDecimal grossPay) {
			this.grossPay = grossPay;
		}

		public BigDecimal getNetPay() {
			return netPay;
		}

		public void setNetPay(BigDecimal netPay) {
			this.netPay = netPay;
		}

		public BigDecimal getProfessionalTax() {
			return professionalTax;
		}

		public void setProfessionalTax(BigDecimal professionalTax) {
			this.professionalTax = professionalTax;
		}

		public BigDecimal getTds() {
			return tds;
		}

		public void setTds(BigDecimal tds) {
			this.tds = tds;
		}
	 	
	 	
	 	
	 	
		
}
