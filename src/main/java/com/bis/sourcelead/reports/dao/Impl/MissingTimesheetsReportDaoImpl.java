package com.bis.sourcelead.reports.dao.Impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bis.sourcelead.reports.dao.MissingTimesheetsReportDao;

@Repository
public class MissingTimesheetsReportDaoImpl implements MissingTimesheetsReportDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	 
}
