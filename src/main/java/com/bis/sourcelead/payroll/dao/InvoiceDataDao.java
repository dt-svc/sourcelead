package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.InvoiceData;

public interface InvoiceDataDao {

	InvoiceData saveInvoiceData(InvoiceData invoiceData);

	List<InvoiceData> getInvoiceDetails(Long userId, Long orgLocationId)throws Exception;

	List<InvoiceData> getInvoiceDetailsByEmpId(Long empId, OrgLocations orgLocations)throws Exception;

}
