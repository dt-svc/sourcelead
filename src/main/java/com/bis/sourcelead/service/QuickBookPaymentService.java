package com.bis.sourcelead.service;

public interface QuickBookPaymentService {
	
	
	 /**
	 * @author Ajith Matta and sarath kumar punnana
	 * @return
	 * @throws Exception
	 */
	public String getAllPayments() throws Exception;
	
	 /**
	 * @author  Ajith Matta and sarath kumar punnana
	 * @param paymentId
	 * @return
	 * @throws Exception
	 */
	public String getPaymentById(Long paymentId) throws Exception;
	 
	 
}