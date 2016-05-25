package com.bis.sourcelead.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CustomerDao;
import com.bis.sourcelead.entity.Customer;

@Repository
public class CustomerDaoImpl implements CustomerDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerDaoImpl.class);
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public void saveCustomer(Customer customer) {
		logger.debug("In Customer Save method");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.save(customer);
		} catch (HibernateException exception) {
             exception.printStackTrace();
		} finally {
			//session.close();
		}
		logger.debug("Customer Saving end");
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public void updateCustomer() {
		logger.debug("In Customer update method");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			Customer customer = session.load(Customer.class,1);
			customer.setCustomerName("IVS");
			session.update(customer);
		} catch (HibernateException exception) {
             exception.printStackTrace();
		} finally {
			//session.close();
		}
		logger.debug("Customer Saving end");
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	
	public void testSecondLevelCache() {
		logger.debug("In TestSeconLevelCache method");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			Customer customer = session.load(Customer.class,1);
			printData(customer,1);
			
			customer = (Customer) session.load(Customer.class, 1);
		    printData(customer, 2);
			
		  //clear first level cache, so that second level cache is used
	        session.evict(customer);
	        customer = (Customer) session.load(Customer.class, 1);
	        printData(customer, 3);
	         
	        customer = (Customer) session.load(Customer.class, 2);
	        printData(customer, 4);
	         
	        customer = (Customer) session.load(Customer.class, 1);
	        printData(customer, 5);
	        
	        
		} catch (HibernateException exception) {
             exception.printStackTrace();
		} finally {
			//session.close();
		}
		logger.debug("TestSeconLevelCache method end");
	}
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void testSecondLevelCache2() {
		logger.debug("In TestSeconLevelCache2 method");
		Session session = sessionFactory.getCurrentSession();
		try {
			Customer customer = session.load(Customer.class,1);
			printData(customer,6);
			
		} catch (HibernateException exception) {
             exception.printStackTrace();
		} finally {
			//session.close();
		}
		logger.debug("TestSeconLevelCache2 method end");
	}
	
	private void printData(Customer customer,  int count) {
        System.out.println(count+":: Name="+customer.getCustomerName()+", Address="+customer.getCustomerAddress());
    }

}
