package com.bis.sourcelead.payroll.service;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface InvoiceDataService {

	Integer saveInvoiceData(MultipartFile multipartFile, OrgLocations orgLocation, User user, JSONObject jsonObj) throws Exception;
}
