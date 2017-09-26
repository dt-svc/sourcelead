package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.InvoicePaymentDTO;
import com.bis.sourcelead.entity.InvoicePayment;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface InvoicePaymentService {
	
	InvoicePayment saveInvoicePayment(InvoicePaymentDTO invoicePaymentDTO, JSONObject jsonObj, User user, OrgLocations orgLocation,MultipartFile attachedfileList,Long invoiceId)throws Exception;
	
	List<InvoicePayment>  getInvoicePaymentList(Long invoiceId)throws Exception;

	InvoicePayment invoicePaymentById(Long id)throws Exception;
}
