package com.bis.sourcelead.service;

import java.util.List;

<<<<<<< HEAD
=======
import com.bis.sourcelead.entity.Contracts;
>>>>>>> af0abbdd814ae1667a5151d3e464f9f134b46f4f
import com.bis.sourcelead.entity.Invoice;

public interface InvoiceService {
	
	Invoice saveInvoice(Invoice invoice)throws Exception;
<<<<<<< HEAD
	
	List<Invoice> invoiceListByOrgLocationId(Long orgLocationId) throws Exception;
=======

	List<Contracts> getSowcontractListByAssociateId(Long candidateId, Long orgLocationId, Long orgRelationId)throws Exception;
	
	
>>>>>>> af0abbdd814ae1667a5151d3e464f9f134b46f4f
}
