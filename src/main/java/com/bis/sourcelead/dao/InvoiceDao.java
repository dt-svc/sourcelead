package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;

public interface InvoiceDao{

	Invoice saveInvoce(Invoice invoice)throws Exception;

	List<Contracts> getSowcontractListByAssociateId(Long candidateId, Long orgLocationId, Long orgRelationId)throws Exception;
	
}
