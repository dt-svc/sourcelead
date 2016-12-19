package com.bis.sourcelead.service;

public interface QuickBookCustomerService {
	
	
	 /**
	 * @author Ajith Matta and sarath kumar punnana 
	 * @return
	 * @throws Exception
	 */
	public String getAllCustomers() throws Exception;
	
	 /**
	 * @author  Ajith Matta and sarath kumar punnana  
	 * @param customerId
	 * @return
	 * @throws Exception
	 */
	public String getCustomerByCustomerId(Long customerId) throws Exception;
	 
	 
}