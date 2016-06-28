package com.bis.sourcelead.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CoursesDao;
import com.bis.sourcelead.dao.CustomerDao;
import com.bis.sourcelead.dao.StudentDao;
import com.bis.sourcelead.dao.SubjectsDao;
import com.bis.sourcelead.dao.impl.CustomerDaoImpl;
import com.bis.sourcelead.entity.Customer;
import com.bis.sourcelead.service.TestEhCacheService;

@Service("testEhCacheService")
public class TestEhCacheServiceImpl implements TestEhCacheService {

	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	StudentDao studentDao;
	
	@Autowired
	SubjectsDao subjectsDao;
	
	@Autowired
	CoursesDao coursesDao;
	
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

	@Override
	public void updateCustomer2() {
		//TODO write any business logic here
		logger.info("in updateCustomer method");
		customerDao.updateCustomer2();

}

	@Override
	public void testMultiTenentSessionFactory() {
		// TODO Auto-generated method stub
		logger.info("in testMultiTenentSessionFactory method");
		customerDao.testMultiTenentSessionFactory();
		
	}

	@Override
	public void saveMultiTenentCustomer(Customer customer) {
		
		//TODO write any business logic here
		logger.info("in multiTenentCustomer Save method");
		customerDao.saveMultiTenentCustomer(customer);

	}

	@Override
	public void getStudent() {
		//TODO write any business logic here
		logger.info("in Student get method");
		studentDao.getStudent();
		
	}

	@Override
	public void getSubjects() {
		//TODO write any business logic here
		logger.info("in Subjects get method");
		subjectsDao.getSubjects();
		
	}

	@Override
	public void getCourses() {
		//TODO write any business logic here
		logger.info("in Courses get method");
		coursesDao.getCourses();
		
	}

}
