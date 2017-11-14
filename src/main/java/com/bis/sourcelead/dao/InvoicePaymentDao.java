package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.InvoicePayment;

public interface InvoicePaymentDao{

	InvoicePayment saveInvoicePayment(InvoicePayment invoicePayment)throws Exception;
	
	 List<InvoicePayment> getInvoicePaymentList(Long invoiceId) throws Exception;
	 
	 InvoicePayment invoicePaymentById(Long id) throws Exception;
	 
	 List<InvoicePayment> getInvoicePaymentListByOrgloactionIdAndDates(Long orgLocationId, Date startDate,Date endDate) throws Exception;
	 
}
