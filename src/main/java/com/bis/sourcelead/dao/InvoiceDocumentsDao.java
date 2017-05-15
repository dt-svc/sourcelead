package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.InvoiceDocument;

public interface InvoiceDocumentsDao {
	
	List<InvoiceDocument> getInvoiceDocumentsListByInvoiceId(Long invoiceId) throws Exception;
	
	InvoiceDocument getInvoiceDocumentById(Long documentId) throws Exception;
	
	InvoiceDocument saveInvoiceDocument(InvoiceDocument invoiceDocument) throws Exception;

}
