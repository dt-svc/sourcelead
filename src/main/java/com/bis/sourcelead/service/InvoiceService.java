package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;

public interface InvoiceService {
	
	Invoice saveInvoice(Invoice invoice)throws Exception;

	List<Contracts> getSowcontractListByAssociateId(Long candidateId, Long orgLocationId, Long orgRelationId)throws Exception;
	
	
}
