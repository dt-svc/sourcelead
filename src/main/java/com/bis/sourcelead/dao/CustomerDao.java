package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.Customer;

public interface CustomerDao {
	
	public void saveCustomer(Customer customer);
	
	public void updateCustomer();
	
	public void testSecondLevelCache();
	
	public void testSecondLevelCache2();

}
