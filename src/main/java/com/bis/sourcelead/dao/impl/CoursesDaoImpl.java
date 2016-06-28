package com.bis.sourcelead.dao.impl;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CoursesDao;

@Repository
public class CoursesDaoImpl implements CoursesDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(CoursesDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void getCourses() {
		// TODO Auto-generated method stub
		
	}

}
