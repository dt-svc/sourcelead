package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.InvoiceLineItems;

public interface InvoiceLineItemDao {

	List<InvoiceLineItems> getInvoiceLineItemsByInvoiceId(Long invoiceId) throws Exception;
	
	InvoiceLineItems getInvoiceLineItemsById(Long Id) throws Exception;
	
	InvoiceLineItems saveInvoiceLineItems(InvoiceLineItems invoiceLineItems) throws Exception;
	
	public void deleteLineItem(InvoiceLineItems invoiceLineItems) throws Exception;
	
	List<Long> getInvoiceLineItemsIdsByInvoiceId(Long invoiceId) throws Exception;
}

