package com.bis.sourcelead.service.impl;

import java.util.List;

import com.bis.sourcelead.entity.InvoiceDocument;

public interface InvoiceDocumentsService {
	
	List<InvoiceDocument> getInvoiceDocumentsListByInvoiceId(Long invoiceId) throws Exception;
	
	InvoiceDocument getInvoiceDocumentById(Long documentId) throws Exception;
	
	InvoiceDocument saveInvoiceDocument(InvoiceDocument invoiceDocument) throws Exception;

}
