package com.bis.sourcelead.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ProbationHistoryDao;
import com.bis.sourcelead.entity.ProbationHistory;

@Repository
public class ProbationHistoryDaoImpl implements ProbationHistoryDao {
	
	@Autowired
    private SessionFactory sessionFactory;

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void saveProbationHistory(ProbationHistory probationHistory) {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (probationHistory != null) {
				session.saveOrUpdate(probationHistory);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
