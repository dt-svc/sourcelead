package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.InvoicePayment;

public interface InvoicePaymentDao{

	InvoicePayment saveInvoicePayment(InvoicePayment invoicePayment)throws Exception;

}
