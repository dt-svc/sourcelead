package com.bis.sourcelead.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.MultiTenantConnectionConfig;
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
			//TODO 
			//For generation of Id for multitenant db by criteria.
			
			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			System.out.println(dateFormat.format(cal.getTimeInMillis()));
			Criteria criteria = session.createCriteria(Customer.class).setProjection(Projections.max("autoIncrement"));;
			Integer resultCount = (Integer)criteria.uniqueResult();
			DateFormat dateFormat1 = new SimpleDateFormat("HH:mm:ss.SSS");
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(new Date());
			System.out.println(dateFormat1.format(cal1.getTimeInMillis()));
			
			if(resultCount == null){
				resultCount = 1;
				customer.setId("Slead"+resultCount);
			}else{
				resultCount = resultCount+1;
				customer.setId("Slead"+resultCount);
				
			}
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
			Customer customer = session.load(Customer.class,"Slead1");
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
			Customer customer = session.load(Customer.class,"Slead1");
			printData(customer,1);
			
			customer = (Customer) session.load(Customer.class, "Slead1");
		    printData(customer, 2);
			
		  //clear first level cache, so that second level cache is used
	        session.evict(customer);
	        customer = (Customer) session.load(Customer.class, "Slead1");
	        printData(customer, 3);
	         
	        customer = (Customer) session.load(Customer.class, "Slead2");
	        printData(customer, 4);
	         
	        customer = (Customer) session.load(Customer.class, "Slead1");
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
		Session session = this.sessionFactory.getCurrentSession();
		try {
			Customer customer = session.load(Customer.class,"Slead1");
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

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void updateCustomer2() {
		logger.debug("In Customer update method");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			Customer customer = session.load(Customer.class,"Slead1");
			customer.setCustomerName("Venkat Idapalapati");
			customer.setCustomerAddress("Miyapur");
			System.out.println("----------------Changes done in session level------------");
			printData(customer, 7);
			session.update(customer);
			System.out.println("----------------Fetch the Updated record form session not from DB------------");
		} catch (HibernateException exception) {
             exception.printStackTrace();
		} finally {
			//session.close();
		}
		logger.debug("Customer Saving end");
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Customer getCustomer(String Id) {
		logger.debug("In Customer get method");
		Customer customer = null;
		Session session = this.sessionFactory.getCurrentSession();
		try {
			customer = session.load(Customer.class,Id);
		} catch (HibernateException exception) {
             exception.printStackTrace();
		} finally {
			//session.close();
		}
		logger.debug("Customer get method end");
		return customer;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void saveOrUpdateCustomer(Customer customer) {
		logger.debug("In Customer update method");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.update(customer);
		} catch (HibernateException exception) {
             exception.printStackTrace();
		} finally {
			//session.close();
		}
		logger.debug("Customer Update method end");
	}

	@Override
	public Collection<Customer> getAllCustomers() {
		logger.debug("getAllCustomers start");
		Session session = this.sessionFactory.getCurrentSession();
		Collection<Customer> customersList = null;
		Query getAllCustomers= session.createQuery("from Customer");
		customersList = getAllCustomers.list();;
		logger.debug("getAllCustomers end");
		return customersList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void testMultiTenentSessionFactory() {
		logger.debug("In MultiTenentSessionFactory method");
		MultiTenantConnectionConfig multiTenantConnectionConfig = new MultiTenantConnectionConfig();
		SessionFactory sessionFactory2 = multiTenantConnectionConfig.sessionFactory();
		Session session2 = sessionFactory2.getCurrentSession();
		try {
			Customer customer = session2.load(Customer.class,"tcs1");
			printData(customer,6);
			
		} catch (HibernateException exception) {
             exception.printStackTrace();
		} finally {
			//session.close();
		}
		logger.debug("MultiTenentSessionFactory method end");
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void saveMultiTenentCustomer(Customer customer) {
		logger.debug("In Customer Save method");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			//TODO 
			//For generation of Id for multitenant db by criteria.
			
			MultiTenantConnectionConfig multiTenantConnectionConfig = new MultiTenantConnectionConfig();
			SessionFactory sessionFactory2 = multiTenantConnectionConfig.sessionFactory();
			Session session2 = sessionFactory2.getCurrentSession();
			
			Criteria criteria = session2.createCriteria(Customer.class).setProjection(Projections.max("autoIncrement"));;
			Integer resultCount = (Integer)criteria.uniqueResult();
			
			if(resultCount == null){
				resultCount = 1;
				customer.setId("tcs"+resultCount);
			}else{
				resultCount = resultCount+1;
				customer.setId("tcs"+resultCount);
				
			}
			session2.save(customer);
			
		} catch (HibernateException exception) {
             exception.printStackTrace();
		} finally {
			//session.close();
		}
		logger.debug("Customer Saving end");
	}

}
