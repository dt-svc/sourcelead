package com.bis.sourcelead.dao.impl;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ShiftDao;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Shift;

@Repository
@Transactional
public class ShiftDaoImpl implements ShiftDao {
	
	@Autowired
	SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(SourceleadNotesDaoImpl.class);

	@Override
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
	public List<Shift> getShiftList(Long orgLocationId)	
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Shift shift where shift.orgLocations.id=:orgLocationId and shift.status = '1'";
	    List<Shift> shiftList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		return shiftList;
	}

	@Override
	public Shift getShiftById(String shiftId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Shift shift where shift.shiftUUID=:shiftUUID";
		List<Shift> shiftList = session.createQuery(query).setString("shiftUUID", shiftId).list();
		if(shiftList != null && shiftList.size()>0){
			return shiftList.get(0);
		}else{
			return null;
		}
		
	}

	@Override
	public Shift deleteShift(Shift shift)
	{
		logger.debug("Deleting Shift");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (shift != null) {
				session.saveOrUpdate(shift);
			}
			logger.debug("Shift Deleted Successfully");
		} catch (Exception exception) {
		}
		return shift;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Shift getShiftListByShiftNameAndOrglocationId(String ShiftName, Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Shift> shift = new ArrayList<Shift>();
		shift = session.createQuery("from Shift shift where shift.shiftName = :ShiftName and shift.orgLocations.id = :orgLocationId " ).setString( "ShiftName", ShiftName ).setLong("orgLocationId", orgLocationId).list();
		if (shift.size() > 0) {
			return shift.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<String> getAssociatesShiftListByOrgLocationId(Long orgLocationId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		query ="SELECT "
				+" S.SHIFT_NAME "
				+" FROM SHIFT S "
				+" WHERE "
				+" S.ORGLOCATION_ID = :orgLocationId AND S.STATUS = '1' ";
		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("orgLocationId", orgLocationId);	
		List<String> shiftsList = (List<String>)queryObj.list();
		
		return shiftsList;
	
	}
	
	
}
