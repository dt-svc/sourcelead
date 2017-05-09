package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.InvoiceTax;

public interface InvoiceTaxService {

	List<InvoiceTax> getInvoiceTaxListByInvoiceId(Long invoiceId) throws Exception;
}
