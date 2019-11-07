package com.bis.sourcelead.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.service.QuickBookCustomerService;

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
public class QuickBooksCustomerRestController {

	private static final Logger logger = LoggerFactory.getLogger(QuickBooksCustomerRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	QuickBookCustomerService quickBookCustomerService ;
	
	
	/**
	 * @author sarath kumar punnana
	 * 
	 * @description this method is for get invoice details by customer id
	 * 
	 * @return JSON  : this method returns json objects
	 *  
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/getCustomerByCustomerId", method = RequestMethod.POST)
	public @ResponseBody String getCustomerByCustomerId(Long customerId) throws Exception {
		

			String outPut = quickBookCustomerService.getCustomerByCustomerId(customerId);
		    return outPut;
		    
	}
	
	/**
	 * @author sarath kumar punnana
	 * 
	 * @description this method returns customers list
	 * 
	 * @return JSON  : this method returns json objects.
	 *  
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllCustomers", method = RequestMethod.POST)
	public @ResponseBody String getAllCustomers() throws Exception {
			
			String outPut = quickBookCustomerService.getAllCustomers();
		    return outPut;
	    
	}
	
	@RequestMapping(value = "/saveCustomer", method = RequestMethod.GET)
	public @ResponseBody void saveCustomer() throws Exception{
    
		quickBookCustomerService.saveCustomer();
		
		
	}

	 
}
