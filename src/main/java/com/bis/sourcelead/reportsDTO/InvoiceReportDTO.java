package com.bis.sourcelead.reportsDTO;

import java.util.Map;

public class InvoiceReportDTO {

	private Float draftInvoiceTotal;
	private Float openInvoiceTotal;
	private Float paidInvoiceTotal;
	private Float overDueInvoicetotal;
	private Map<String,Float> newAmountList;
	
	public Float getDraftInvoiceTotal() {
		return draftInvoiceTotal;
	}
	public void setDraftInvoiceTotal(Float draftInvoiceTotal) {
		this.draftInvoiceTotal = draftInvoiceTotal;
	}
	public Float getOpenInvoiceTotal() {
		return openInvoiceTotal;
	}
	public void setOpenInvoiceTotal(Float openInvoiceTotal) {
		this.openInvoiceTotal = openInvoiceTotal;
	}
	public Float getPaidInvoiceTotal() {
		return paidInvoiceTotal;
	}
	public void setPaidInvoiceTotal(Float paidInvoiceTotal) {
		this.paidInvoiceTotal = paidInvoiceTotal;
	}
	public Float getOverDueInvoicetotal() {
		return overDueInvoicetotal;
	}
	public void setOverDueInvoicetotal(Float overDueInvoicetotal) {
		this.overDueInvoicetotal = overDueInvoicetotal;
	}
	public Map<String, Float> getNewAmountList() {
		return newAmountList;
	}
	public void setNewAmountList(Map<String, Float> newAmountList) {
		this.newAmountList = newAmountList;
	}
	
	
}
