package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Invoice;

public interface InvoiceDao{

	Invoice saveInvoce(Invoice invoice)throws Exception;

	List<Invoice> invoiceListByOrgLocationId(Long orgLocationId);
	
}
