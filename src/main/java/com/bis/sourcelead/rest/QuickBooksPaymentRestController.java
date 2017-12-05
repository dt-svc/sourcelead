package com.bis.sourcelead.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.service.QuickBookPaymentService;

/**
 * 
 * Handles requests for the Requirement service.
 * 
 * @author sarath kumar punnana
 * @date 14th Dec 2016
 *
 */

@RestController
@RequestMapping("/app")
public class QuickBooksPaymentRestController {

	private static final Logger logger = LoggerFactory.getLogger(QuickBooksPaymentRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	QuickBookPaymentService  quickBookPaymentService;
	
	
	/**
	 * @author sarath kumar punnana and Ajith Matta
	 * 
	 * @description this method is for get payment details by id
	 * 
	 * @return JSON  : this method returns json objects
	 *  
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/getPaymentById", method = RequestMethod.POST)
	public @ResponseBody String getPaymentById(Long paymentId) throws Exception {
		

			String outPut = quickBookPaymentService.getPaymentById(paymentId);
		    return outPut;
		    
	}
	
	/**
	 * @author sarath kumar punnana and Ajith Matta
	 * 
	 * @description this method returns payments list
	 * 
	 * @return JSON  : this method returns json objects.
	 *  
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllPayments", method = RequestMethod.POST)
	public @ResponseBody String getAllPayments() throws Exception {
			
			String outPut = quickBookPaymentService.getAllPayments();
		    return outPut;
	    
	}
		
	

	 
}
