package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.InvoiceLineItems;

public interface InvoiceLineItemService {
	
	List<InvoiceLineItems> getInvoiceLineItemsByInvoiceId(Long invoiceId) throws Exception;
	
	InvoiceLineItems getInvoiceLineItemsById(Long Id) throws Exception;
	
	InvoiceLineItems saveInvoiceLineItems(InvoiceLineItems invoiceLineItems) throws Exception;

}
