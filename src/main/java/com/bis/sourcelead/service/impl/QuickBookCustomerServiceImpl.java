package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.service.QuickBookCustomerService;
import com.bis.sourcelead.util.QuickBookOAuthAuthentication;

@Service
public class QuickBookCustomerServiceImpl implements QuickBookCustomerService {
	
	@Autowired
	private QuickBookOAuthAuthentication quickBookOAuthAuthentication;
	
	@Value("${sandBoxUrl}")
	private String sandBoxUrl;
	
	 /**
		 * @author Ajith Matta and sarath kumar punnana 
		 * @return all customers.
		 * @throws Exception
		 * This method is used to get the all customers.
		 */
	
	@Override
	public String getAllCustomers() throws Exception {
		  
		  String uri = sandBoxUrl+"/query?query=select%20%2A%20from%20customer&minorversion=4";
		  String outPut = quickBookOAuthAuthentication.oAuthSignIn(uri);
		  return outPut;  
		   
	}

	 /**
		 * @author  Ajith Matta and sarath kumar punnana  
		 * @param customerId
		 * @return customer by id.
		 * @throws Exception
		 * This method is used to get the customer by id.
		 */
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
