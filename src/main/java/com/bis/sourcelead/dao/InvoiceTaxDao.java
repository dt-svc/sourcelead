package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.InvoiceTax;

public interface InvoiceTaxDao {

	List<InvoiceTax> getInvoiceTaxListByInvoiceId(Long invoiceId) throws Exception;
	
	List<Long> getInvoiceTaxIdsByInvoiceId(Long invoiceId) throws Exception;
	
	public void deleteInvoiceTax(InvoiceTax invoiceTax) throws Exception;
	
	InvoiceTax getInvoiceTaxById(Long Id) throws Exception;
}

