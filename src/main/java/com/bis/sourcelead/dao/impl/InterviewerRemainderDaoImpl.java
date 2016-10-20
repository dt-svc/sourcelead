package com.bis.sourcelead.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bis.sourcelead.dao.InterviewRemainderDao;
import com.bis.sourcelead.dao.RequirementInterviewDao;
import com.bis.sourcelead.dao.RequirementInterviewerDao;

@Repository
public class InterviewerRemainderDaoImpl implements InterviewRemainderDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
}
