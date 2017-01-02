package com.bis.sourcelead.service;

public interface QuickBookVendorService {
	
	
	 /**
	 * @author Ajith Matta and sarath kumar punnana
	 * @return
	 * @throws Exception
	 */
	public String getAllvendors() throws Exception;
	
	 /**
	 * @author Ajith Matta and sarath kumar punnana 
	 * @param invoiceId
	 * @return
	 * @throws Exception
	 */
	public String getVendorByvendorId(Long vendorId) throws Exception;
	 
	 
	 
}