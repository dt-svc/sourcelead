package com.bis.sourcelead.dto;

public class InvoiceTaxDTO {
	private String taxType;
	private String invoiceTaxId;
	private String taxdiscountType;
	private String taxdiscountvalue;
	private String taxTotal;

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public String getInvoiceTaxId() {
		return invoiceTaxId;
	}

	public void setInvoiceTaxId(String invoiceTaxId) {
		this.invoiceTaxId = invoiceTaxId;
	}


	public String getTaxdiscountType() {
		return taxdiscountType;
	}

	public void setTaxdiscountType(String taxdiscountType) {
		this.taxdiscountType = taxdiscountType;
	}

	public String getTaxdiscountvalue() {
		return taxdiscountvalue;
	}

	public void setTaxdiscountvalue(String taxdiscountvalue) {
		this.taxdiscountvalue = taxdiscountvalue;
	}

	public String getTaxTotal() {
		return taxTotal;
	}

	public void setTaxTotal(String taxTotal) {
		this.taxTotal = taxTotal;
	}

}
