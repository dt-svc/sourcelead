package com.bis.sourcelead.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.service.QuickBookPaymentService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.QuickBookOAuthAuthentication;

/**
 * @author Ajith Matta
 *
 */
@Service
public class QuickBookPaymentServiceImpl implements QuickBookPaymentService {
	
	private static final Logger logger = LoggerFactory.getLogger(QuickBookPaymentServiceImpl.class);
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private QuickBookOAuthAuthentication quickBookOAuthAuthentication;
	
	@Value("${sandBoxUrl}")
	private String sandBoxUrl;
	
	/**
	 * @author Ajith Matta and sarath kumar punnana
	 * @return complete payment list
	 * @throws Exception
	 * This method is used to returns complete payment list
	 */
	
	@Override
	public String getAllPayments() throws Exception {
		  
		  String uri = sandBoxUrl+"/query?query=select%20%2A%20from%20payment&minorversion=4";
		  
		  String outPut = quickBookOAuthAuthentication.oAuthSignIn(uri);
		  return outPut;  
		   
	}
	/**
	 * @author  Ajith Matta and sarath kumar punnana
	 * @param paymentId
	 * @return payment list by id
	 * @throws Exception
	 *  This method is used to returns  payment list by id
	 */

	
	@Override
	public String getPaymentById(Long paymentId) throws Exception {

		  String uri = sandBoxUrl+"/payment/"+paymentId+"?minorversion=4";
		  String outPut = quickBookOAuthAuthentication.oAuthSignIn(uri);
		  return outPut;  
		  
	}
	

	public static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
}
