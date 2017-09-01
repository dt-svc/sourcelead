package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.dto.InvoiceDetailsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.InvoiceData;

public interface InvoiceDataDao {

	InvoiceData saveInvoiceData(InvoiceData invoiceData);

	List<InvoiceData> getInvoiceDetailsByEmpId(Long empId, OrgLocations orgLocations)throws Exception;

	ResponseList<InvoiceData> getInvoiceDetails(InvoiceDetailsDTO invoiceDetailsDTO, Long orgLocationId) throws Exception;

}
