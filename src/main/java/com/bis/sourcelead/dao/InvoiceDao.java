package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.Invoice;

public interface InvoiceDao{

	Invoice saveInvoce(Invoice invoice)throws Exception;
	
}
