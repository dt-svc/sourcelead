package com.bis.sourcelead.dao;

import java.util.List;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.Invoice;

public interface InvoiceDao{

	Invoice saveInvoce(Invoice invoice)throws Exception;
	Invoice getInvoicetById(Long invoiceId) throws Exception;
	List<Invoice> invoiceListByOrgLocationId(Long orgLocationId);
	List<Contracts> getSowcontractListByAssociateId(Long candidateId, Long orgLocationId, Long orgRelationId)throws Exception;

}
