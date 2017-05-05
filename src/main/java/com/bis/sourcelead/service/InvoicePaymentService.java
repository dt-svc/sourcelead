package com.bis.sourcelead.service;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.InvoicePayment;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface InvoicePaymentService {
	
	InvoicePayment saveInvoicePayment(JSONObject jsonObj, User user, OrgLocations orgLocation,MultipartFile attachedfileList,Long invoiceId)throws Exception;

}
