package com.bis.sourcelead.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "PROFESSIONAL_TAX")
public class ProfessionalTax implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private long id;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATE_TAX_ID", nullable = false)
	private StateTax stateTax;

	@Column(name = "AMOUNT_FROM")
	private double amountFrom;
	@Column(name = "AMOUNT_TO")
	private double amountTo;
	@Column(name = "AMOUNT")
	private double amount;
	@Column(name = "MONTH_APPLICABLE")
	private String monthApplicable;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public StateTax getStateTax() {
		return stateTax;
	}

	public void setStateTax(StateTax stateTax) {
		this.stateTax = stateTax;
	}

	public double getAmountFrom() {
		return amountFrom;
	}

	public void setAmountFrom(double amountFrom) {
		this.amountFrom = amountFrom;
	}

	public double getAmountTo() {
		return amountTo;
	}

	public void setAmountTo(double amountTo) {
		this.amountTo = amountTo;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getMonthApplicable() {
		return monthApplicable;
	}

	public void setMonthApplicable(String monthApplicable) {
		this.monthApplicable = monthApplicable;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}