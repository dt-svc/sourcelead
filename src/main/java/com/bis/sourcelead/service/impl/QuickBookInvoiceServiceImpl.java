package com.bis.sourcelead.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.service.QuickBookInvoiceService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.QuickBookOAuthAuthentication;

@Service
public class QuickBookInvoiceServiceImpl implements QuickBookInvoiceService {
	
	private static final Logger logger = LoggerFactory.getLogger(QuickBookInvoiceServiceImpl.class);
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Value("${sandBoxUrl}")
	private String sandBoxUrl;
	
	@Autowired
	private QuickBookOAuthAuthentication quickBookOAuthAuthentication;
	

	@Override
	public String getInvoiceByInvoiceId(Long invoiceId) throws Exception {
		
		  String uri = sandBoxUrl+"/invoice/"+invoiceId+"?minorversion=4";
		  String outPut = quickBookOAuthAuthentication.oAuthSignIn(uri);
		  return outPut;  
	}



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
