package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.InvoicePaymentDTO;
import com.bis.sourcelead.entity.InvoicePayment;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface InvoicePaymentService {
	
	InvoicePayment saveInvoicePayment(InvoicePaymentDTO invoicePaymentDTO, User user, OrgLocations orgLocation,MultipartFile attachedfileList,Long invoiceId)throws Exception;
	
	InvoicePayment saveInvoiceBulkPayment(InvoicePaymentDTO invoicePaymentDTO, User user, OrgLocations orgLocation,Map<Long,String> invoiceIds)throws Exception;
	
	List<InvoicePayment>  getInvoicePaymentList(Long invoiceId)throws Exception;

	InvoicePayment invoicePaymentById(Long id)throws Exception;
	
	List<InvoicePayment> getInvoicePaymentListByOrgloactionIdAndDates(Long orgLocationId,Date startDate,Date endDate) throws Exception;

}
