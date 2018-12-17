package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.dto.InvoiceDetailsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.InvoiceData;

public interface InvoiceDataDao {

	InvoiceData saveInvoiceData(InvoiceData invoiceData);

	ResponseList<InvoiceData> getInvoiceDetailsByEmpId(OrgLocations orgLocations, InvoiceDetailsDTO invoiceDetailsDTO)throws Exception;

	int getInvoiceDataVersionByOrgLocationId(Long orgLocationId);

	ResponseList<InvoiceData> getInvoiceDetails(InvoiceDetailsDTO invoiceDetailsDTO, OrgLocations orgLocation) throws Exception;

	ResponseList<InvoiceData> getAllEmployeeInvoiceDetailsByEmpIdAndOrgLocationId(Long orgLocationId, Long userId);

	List<InvoiceData> getInvoiceDetailsByEmpId(Long empId, OrgLocations orgLocations);

}
