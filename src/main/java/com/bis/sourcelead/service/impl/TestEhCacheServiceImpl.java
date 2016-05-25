package com.bis.sourcelead.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CustomerDao;
import com.bis.sourcelead.dao.impl.CustomerDaoImpl;
import com.bis.sourcelead.entity.Customer;
import com.bis.sourcelead.service.TestEhCacheService;

@Service("testEhCacheService")
public class TestEhCacheServiceImpl implements TestEhCacheService {

	@Autowired
	CustomerDao customerDao;
	
	private static final Logger logger = LoggerFactory.getLogger(TestEhCacheServiceImpl.class);
	
	@Override
	public void saveCustomer(Customer customer) {
		
		//TODO write any business logic here
		logger.info("in saveCustomer method");
		customerDao.saveCustomer(customer);

	}

	@Override
	public void updateCustomer() {
		//TODO write any business logic here
				logger.info("in updateCustomer method");
				customerDao.updateCustomer();
		
	}

	@Override
	public void testSecondLevelCache() {
		// TODO Auto-generated method stub
		logger.info("in testSecondLevelCache method");
		customerDao.testSecondLevelCache();
		
	}

	@Override
	public void testSecondLevelCache2() {
		// TODO Auto-generated method stub
		logger.info("in testSecondLevelCache2 method");
		customerDao.testSecondLevelCache2();
		
	}

}
