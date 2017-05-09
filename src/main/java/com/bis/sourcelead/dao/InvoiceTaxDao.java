package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.InvoiceTax;

public interface InvoiceTaxDao {

	List<InvoiceTax> getInvoiceTaxListByInvoiceId(Long invoiceId) throws Exception;
}
