package com.bis.sourcelead.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CandidateEmployersDao;
import com.bis.sourcelead.entity.CandidateEmployers;

@Service
public interface CandidateEmployersDaoImpl extends CandidateEmployersDao {
	
	
	

	public  CandidateEmployers storeCandidateEmployers(CandidateEmployers candidateEmployers) throws Exception; 
}
