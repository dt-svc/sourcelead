package com.bis.sourcelead.payroll.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.InvoiceData;

public interface InvoiceDataService {

	Integer saveInvoiceData(MultipartFile multipartFile, OrgLocations orgLocation, User user, JSONObject jsonObj) throws Exception;

	List<InvoiceData> getInvoiceDetails(Long userId, Long orgLocationId)throws Exception;

	List<InvoiceData> getInvoiceDetailsByEmpId(Long empId, OrgLocations orgLocations)throws Exception;
}
