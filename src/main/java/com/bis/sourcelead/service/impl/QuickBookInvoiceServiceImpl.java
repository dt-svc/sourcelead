package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.service.QuickBookInvoiceService;
import com.bis.sourcelead.util.QuickBookOAuthAuthentication;

@Service
public class QuickBookInvoiceServiceImpl implements QuickBookInvoiceService {
	
	
	
	@Value("${sandBoxUrl}")
	private String sandBoxUrl;
	
	@Autowired
	private QuickBookOAuthAuthentication quickBookOAuthAuthentication;
	/**
	 * @author Ajith Matta and sarath kumar punnana 
	 * @param invoiceId
	 * @return invoice by id
	 * @throws Exception
	 * This method is used to get the invoice by id.
	 */

	@Override
	public String getInvoiceByInvoiceId(Long invoiceId) throws Exception {
		
		  String uri = sandBoxUrl+"/invoice/"+invoiceId+"?minorversion=4";
		  String outPut = quickBookOAuthAuthentication.oAuthSignIn(uri);
		  return outPut;  
	}

	/**
	 * @author Ajith Matta and sarath kumar punnana
	 * @return all invoice data
	 * @throws Exception
	 * This method is used to get all invoices.
	 */

	@Override
	public String getAllInvoice() throws Exception {
		 	
		  String uri = sandBoxUrl+"/query?query=SELECT%20%2A%20FROM%20Invoice&minorversion=4";
		  String outPut = quickBookOAuthAuthentication.oAuthSignIn(uri);
		  return outPut;
	}
	

	public static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}


	
}
