package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoicePayment;
import com.bis.sourcelead.entity.OrgRelations;

public interface InvoiceDao{

	Invoice saveInvoce(Invoice invoice)throws Exception;
	
	List<Invoice> invoiceListByOrgLocationId(Long orgLocationId, String invoiceStatus, String paymentStatus,Date startDateUTC,Date endDateUTC,String invoiceType);
	
	Invoice getInvoicetById(Long invoiceId) throws Exception;

	Invoice getInvoicetByParentInvoiceId(Long parentInvoiceId) throws Exception;
	
	List<Contracts> getSowcontractListByAssociateId(Long candidateId, Long orgLocationId, Long orgRelationId,String contractType)throws Exception;
	
	Invoice getInvoicetByInvoiceUUID(String invoiceUUID) throws Exception;
	
	Invoice updateInvoice(Invoice invoice) throws Exception;

	List<Invoice> getViewInvoiceListByInvoiceIdAndOrglocationId(Long invoiceId, Long orgLocationId) throws Exception;

	Invoice getInvoicetBySelfJoinInvoiceId(Long parentInvoiceId) throws Exception;

	List<Invoice> getInvoiceByOrgLocationId(Long orgLocationId, Date startDateUTC, Date endDateUTC, String invoiceTYpe)throws Exception;

	List<Invoice> getClientBasedInvoiceLists(Long orgRelationId,Long orgLocationId, String invoiceType);
	
	List<Invoice> getInvoiceByOrgRelationId(Long orgRelationId, Long clientLocationId,Date startDateUTC, Date endDateUTC,String invoiceStatus, String paymentStatus, String invoiceType);

	List<Invoice> getInvoiceByOrgLocationIdNetIncome(Long orgLocationId, Date startDateUTC, Date endDateUTC)throws Exception;

	List<Invoice> getDueDateCompletedInvoicesList() throws Exception;

	List<InvoicePayment> getPaymentHistroyByInvoiceId(Long invoiceId)throws Exception;

	Invoice getInvoicetByParentId(Long invoiceId)throws Exception;

}
