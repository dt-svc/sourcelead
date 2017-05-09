package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;

public interface InvoiceDao{

	Invoice saveInvoce(Invoice invoice)throws Exception;
	
	List<Invoice> invoiceListByOrgLocationId(Long orgLocationId, String invoiceStatus, String paymentStatus,Date startDateUTC,Date endDateUTC,String invoiceType);
	
	Invoice getInvoicetById(Long invoiceId) throws Exception;

	List<Contracts> getSowcontractListByAssociateId(Long candidateId, Long orgLocationId, Long orgRelationId,String contractType)throws Exception;
	
	Invoice getInvoicetByInvoiceUUID(String invoiceUUID) throws Exception;

	List<Invoice> getViewInvoiceListByInvoiceIdAndOrglocationId(Long invoiceId, Long orgLocationId) throws Exception;
}
