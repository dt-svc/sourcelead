package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.LoginDAO;

/**
 * This class is used for...
 *  
 * @author: Srinivas Vemula
 * @date: 12-May-2016
 */

@Repository
public class LoginDAoImpl implements LoginDAO{

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(LoginDAoImpl.class);

	
	
	@Transactional
	public boolean checkAuthentication(String userName, String userPassword) {
		boolean userFound = false;
		Session session = this.sessionFactory.getCurrentSession();
		logger.debug("In the Authentication Method");
		try {
			
			//TODO:
	
		} catch (HibernateException exception) {
             exception.printStackTrace();
		} finally {
			session.close();
		}
//		session.close();
		logger.debug("check authentication end");
		return userFound;
	}

}
