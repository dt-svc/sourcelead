package com.bis.sourcelead.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.service.QuickBookCustomerService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.QuickBookOAuthAuthentication;

@Service
public class QuickBookCustomerServiceImpl implements QuickBookCustomerService {
	
	private static final Logger logger = LoggerFactory.getLogger(QuickBookCustomerServiceImpl.class);
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private QuickBookOAuthAuthentication quickBookOAuthAuthentication;
	
	@Value("${sandBoxUrl}")
	private String sandBoxUrl;
	
	
	@Override
	public String getAllCustomers() throws Exception {
		  
		  String uri = sandBoxUrl+"/query?query=select%20%2A%20from%20customer&minorversion=4";
		  String outPut = quickBookOAuthAuthentication.oAuthSignIn(uri);
		  return outPut;  
		   
	}


	@Override
	public String getCustomerByCustomerId(Long customerId) throws Exception {

		  String uri = sandBoxUrl+"/customer/"+customerId+"?minorversion=4";
		  String outPut = quickBookOAuthAuthentication.oAuthSignIn(uri);
		  return outPut;  
		  
	}
	

	public static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
}
