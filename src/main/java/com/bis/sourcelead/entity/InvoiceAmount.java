package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

public class InvoiceAmount implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private double totalOpenUnpaidAmount ;
	private double totalOverDueUnpaidAmount ;
	private double totalPaidAmount ;
	private double totalUnbilledAmount ;
	
	
	public double getTotalOpenUnpaidAmount() {
		return totalOpenUnpaidAmount;
	}
	public void setTotalOpenUnpaidAmount(double totalOpenUnpaidAmount) {
		this.totalOpenUnpaidAmount = totalOpenUnpaidAmount;
	}
	public double getTotalOverDueUnpaidAmount() {
		return totalOverDueUnpaidAmount;
	}
	public void setTotalOverDueUnpaidAmount(double totalOverDueUnpaidAmount) {
		this.totalOverDueUnpaidAmount = totalOverDueUnpaidAmount;
	}
	public double getTotalPaidAmount() {
		return totalPaidAmount;
	}
	public void setTotalPaidAmount(double totalPaidAmount) {
		this.totalPaidAmount = totalPaidAmount;
	}
	public double getTotalUnbilledAmount() {
		return totalUnbilledAmount;
	}
	public void setTotalUnbilledAmount(double totalUnbilledAmount) {
		this.totalUnbilledAmount = totalUnbilledAmount;
	}

	
	
}
