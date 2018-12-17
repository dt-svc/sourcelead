package com.bis.sourcelead.payroll.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.InvoiceDetailsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.InvoiceData;
import com.bis.sourcelead.payroll.entity.PayrollBalance;

public interface InvoiceDataService {

	Integer saveInvoiceData(MultipartFile multipartFile, OrgLocations orgLocation, User user, JSONObject jsonObj) throws Exception;

	ResponseList<InvoiceData> getInvoiceDetails(InvoiceDetailsDTO invoiceDetailsDTO, OrgLocations orgLocation)throws Exception;

	ResponseList<InvoiceData> getInvoiceDetailsByEmpId(OrgLocations orgLocation, InvoiceDetailsDTO invoiceDetailsDTO)throws Exception;
	
	int getInvoiceDataVersionByOrgLocationId(Long orgLocationId);

	ResponseList<InvoiceData> getAllEmployeeInvoiceDetailsByEmpIdAndOrgLocationId(Long orgLocationId, Long userId);

	List<InvoiceData> getInvoiceDetailsByEmpId(Long empId, OrgLocations orgLocations);
}
