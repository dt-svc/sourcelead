package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.InvoiceLineItems;
import com.bis.sourcelead.entity.InvoiceTax;

public interface InvoiceTaxService {

	List<InvoiceTax> getInvoiceTaxListByInvoiceId(Long invoiceId) throws Exception;
	
	List<Long> getInvoiceTaxIdsByInvoiceId(Long invoiceId) throws Exception;
	
	public void deleteInvoiceTax(InvoiceTax invoiceTax) throws Exception;
	
	InvoiceTax getInvoiceTaxById(Long Id) throws Exception;
}
