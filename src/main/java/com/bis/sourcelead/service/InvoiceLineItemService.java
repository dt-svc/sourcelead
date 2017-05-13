package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.InvoiceLineItems;
import com.bis.sourcelead.entity.Org;

public interface InvoiceLineItemService {
	
	List<InvoiceLineItems> getInvoiceLineItemsByInvoiceId(Long invoiceId) throws Exception;
	
	List<InvoiceLineItems> invoiceLineItemsByInvoiceIdAndOrglocationId(Long invoiceId,Long orgLocationId)throws Exception;

	InvoiceLineItems getInvoiceLineItemsById(Long Id) throws Exception;
	
	InvoiceLineItems saveInvoiceLineItems(InvoiceLineItems invoiceLineItems) throws Exception;
	
	public void deleteLineItem(InvoiceLineItems invoiceLineItems) throws Exception;
	
	List<Long> getInvoiceLineItemsIdsByInvoiceId(Long invoiceId) throws Exception;

}
