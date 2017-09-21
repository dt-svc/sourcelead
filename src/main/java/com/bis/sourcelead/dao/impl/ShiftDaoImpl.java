package com.bis.sourcelead.dao.impl;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ShiftDao;
import com.bis.sourcelead.entity.Shift;

@Repository
public class ShiftDaoImpl implements ShiftDao {
	
	@Autowired
	SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(SourceleadNotesDaoImpl.class);

	@Override
	@Transactional
	public Shift saveShift(Shift shift) throws Exception
	{
		logger.debug("Adding addShift");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (shift != null) {
				session.saveOrUpdate(shift);
			}
			logger.debug("Shift Added Successfully");
		} catch (Exception exception) {
		}
		return shift;
	}

	@Override
	@Transactional
	public List<Shift> getShiftList(Long orgLocationId)	
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Shift shift where shift.orgLocationId.id=:orgLocationId";
	    List<Shift> shiftList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		return shiftList;
	}

	@Override
	@Transactional
	public List<Shift> getShiftById(String shiftId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Shift shift where shift.id=:shiftId";
		List<Shift> shiftList = session.createQuery(query).setString("shiftId", shiftId).setCacheable(true).list();
		return shiftList;
	}
	
}
