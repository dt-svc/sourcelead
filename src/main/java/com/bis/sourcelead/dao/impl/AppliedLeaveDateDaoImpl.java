package com.bis.sourcelead.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.AppliedLeaveDateDao;

@Repository
public class AppliedLeaveDateDaoImpl implements AppliedLeaveDateDao {

	private static final Logger logger = LoggerFactory.getLogger(AppliedLeaveDateDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public int deleteAppliedLeaveDatesByLeaveId(Long leaveId)
	{
		int isDeleted = 0;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "delete from AppliedLeaveDate aleave where aleave.leave.id =:leaveId ";
			Query qry = session.createQuery(query).setLong("leaveId", leaveId);
			int i = qry.executeUpdate();
			return i;
		} catch (Exception e) {
			logger.error("Delete Applied Leave Date By Leave Id Failed Due to "
					+ e.getLocalizedMessage());
		}
		return isDeleted;
	}
}
