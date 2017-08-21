package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.payroll.entity.InvoiceData;

public interface InvoiceDataDao {

	int saveInvoiceData(List<InvoiceData> invoicedatalist);

}
