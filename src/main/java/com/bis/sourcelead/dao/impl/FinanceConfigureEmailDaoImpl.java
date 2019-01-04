package com.bis.sourcelead.dao.impl;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.FinanceConfigureEmailDao;
import com.bis.sourcelead.dto.EmailReminderDTO;
import com.bis.sourcelead.entity.EmailReminder;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;

@Repository
public class FinanceConfigureEmailDaoImpl implements FinanceConfigureEmailDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public EmailReminder saveFinanceConfigureEmail(EmailReminder email) throws Exception {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			if(email != null)
				session.saveOrUpdate(email);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return email;
	}
}
