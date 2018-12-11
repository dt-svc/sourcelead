package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.TimeZoneDao;
import com.bis.sourcelead.entity.Cities;
import com.bis.sourcelead.entity.Timezone;

@Repository
public class TimeZoneDaoImpl implements TimeZoneDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Timezone> getAllTimeZones() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Timezone> timeZoneList = null;
		Query timeZoneListQuery= session.createQuery("from Timezone");
		timeZoneList=timeZoneListQuery.list();
		return timeZoneList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Timezone getTimezone(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Timezone timezone = (Timezone) session.get(Timezone.class, id);
		return timezone;
	}

}
