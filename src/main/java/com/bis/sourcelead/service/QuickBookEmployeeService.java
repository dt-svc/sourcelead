package com.bis.sourcelead.service;

public interface QuickBookEmployeeService {
	
	
	 /**
	 * @author Ajith Matta and sarath kumar punnana
	 * @return
	 * @throws Exception
	 */
	public String getAllEmoloyees() throws Exception;
	
	 /**
	 * @author Ajith Matta and sarath kumar punnana 
	 * @param invoiceId
	 * @return
	 * @throws Exception
	 */
	public String getEmoloyeesByEmoloyeesId(Long employeeId) throws Exception;
	 
	 
	 
}