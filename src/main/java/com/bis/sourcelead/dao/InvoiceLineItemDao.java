package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.InvoiceLineItems;

public interface InvoiceLineItemDao {

	List<InvoiceLineItems> getInvoiceLineItemsByInvoiceId(Long invoiceId) throws Exception;
	
	List<InvoiceLineItems> getInvoiceLineItemsByInvoiceIdAndOrglocationId(Long invoiceId, Long orgLocationId) throws Exception;
}
