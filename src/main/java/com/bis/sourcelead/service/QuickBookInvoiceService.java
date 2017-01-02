package com.bis.sourcelead.service;

public interface QuickBookInvoiceService {
	
	
	 /**
	 * @author Ajith Matta and sarath kumar punnana
	 * @return
	 * @throws Exception
	 */
	public String getAllInvoice() throws Exception;
	
	 /**
	 * @author Ajith Matta and sarath kumar punnana 
	 * @param invoiceId
	 * @return
	 * @throws Exception
	 */
	public String getInvoiceByInvoiceId(Long invoiceId) throws Exception;
	 
	 
	 
}