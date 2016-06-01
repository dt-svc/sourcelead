package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.Customer;

public interface TestEhCacheService {
	
	void saveCustomer(Customer customer);
	
	void updateCustomer();
	
	void testSecondLevelCache();
	
	void testSecondLevelCache2();
	
	void updateCustomer2();

}
