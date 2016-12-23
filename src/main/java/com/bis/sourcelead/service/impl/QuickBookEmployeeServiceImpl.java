package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.service.QuickBookEmployeeService;
import com.bis.sourcelead.util.QuickBookOAuthAuthentication;

@Service
public class QuickBookEmployeeServiceImpl implements QuickBookEmployeeService {
	
	
	
	@Value("${quickBoxUrl}")
	private String quickBoxUrl;
	
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
	public String getEmoloyeesByEmoloyeesId(Long employeeId) throws Exception {
		
		  String uri = quickBoxUrl+"/employee/"+employeeId+"?minorversion=4";
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
	public String getAllEmoloyees() throws Exception {
		 	
		  String uri = quickBoxUrl+"/query?query=SELECT%20%2A%20FROM%20Employee%20STARTPOSITION%201%20MAXRESULTS%201000&minorversion=4";

		  String outPut = quickBookOAuthAuthentication.oAuthSignIn(uri);
		  return outPut;
	}
	

	public static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}


	
}
