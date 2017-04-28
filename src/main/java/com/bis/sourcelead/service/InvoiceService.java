package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Invoice;

public interface InvoiceService {
	
	Invoice saveInvoice(Invoice invoice)throws Exception;
	
	List<Invoice> invoiceListByOrgLocationId(Long orgLocationId) throws Exception;
}
