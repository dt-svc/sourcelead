package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.InvoiceDocumentsDao;
import com.bis.sourcelead.entity.InvoiceDocument;

@Service
public class InvoiceDocumentsServiceImpl implements InvoiceDocumentsService {
	
	@Autowired
	InvoiceDocumentsDao invoiceDocumentsDao;

	@Override
	public List<InvoiceDocument> getInvoiceDocumentsListByInvoiceId(Long invoiceId) throws Exception {
		return invoiceDocumentsDao.getInvoiceDocumentsListByInvoiceId(invoiceId);	
	}

	@Override
	public InvoiceDocument getInvoiceDocumentById(Long documentId) throws Exception {
		return invoiceDocumentsDao.getInvoiceDocumentById(documentId);
	}

	@Override
	public InvoiceDocument saveInvoiceDocument(InvoiceDocument invoiceDocument) throws Exception {
		return invoiceDocumentsDao.saveInvoiceDocument(invoiceDocument);
	}

	
}
