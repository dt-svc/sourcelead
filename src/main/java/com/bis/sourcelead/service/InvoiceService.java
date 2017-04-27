package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.Invoice;

public interface InvoiceService {
	
	Invoice saveInvoice(Invoice invoice)throws Exception;
}
