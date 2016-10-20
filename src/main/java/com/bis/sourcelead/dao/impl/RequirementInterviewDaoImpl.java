package com.bis.sourcelead.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bis.sourcelead.dao.RequirementInterviewDao;

@Repository
public class RequirementInterviewDaoImpl implements RequirementInterviewDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
}
