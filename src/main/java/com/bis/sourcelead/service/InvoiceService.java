package com.bis.sourcelead.service;

import java.io.File;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.Contracts;

import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface InvoiceService {
	

	List<Invoice> invoiceListByOrgLocationId(Long orgLocationId) throws Exception;

	Invoice getInvoicetById(Long invoiceId) throws Exception;
	
	List<Contracts> getSowcontractListByAssociateId(Long candidateId, Long orgLocationId, Long orgRelationId)throws Exception;

	Invoice saveInvoice(JSONObject jsonObj, User user, OrgLocations orgLocation, String saveType, List<MultipartFile> attachedfileList)throws Exception;

	void getPdfFileForInvoice(Invoice invoice, OrgLocations orgLocation, User user, Org org) throws Exception;

}
