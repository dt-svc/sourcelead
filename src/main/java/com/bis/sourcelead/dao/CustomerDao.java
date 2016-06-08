package com.bis.sourcelead.dao;

import java.util.Collection;

import com.bis.sourcelead.entity.Customer;

public interface CustomerDao {
	
	public void saveCustomer(Customer customer);
	
	public Customer getCustomer(Integer Id);
	
	public void updateCustomer();
	
	public void testSecondLevelCache();
	
	public void testSecondLevelCache2();
	
	public void updateCustomer2();
	
	public void saveOrUpdateCustomer(Customer customer);
	
	Collection<Customer> getAllCustomers();
	
	
	public void testMultiTenentSessionFactory();

}
