package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ProbationHistoryDao;
import com.bis.sourcelead.entity.ProbationHistory;
import com.bis.sourcelead.service.ProbationHistoryService;

@Service
public class ProbationHistoryServiceImpl implements ProbationHistoryService{
	
	@Autowired
	private ProbationHistoryDao probationHistoryDao;

	@Override
	public void saveProbationHistory(ProbationHistory probationHistory) {
		
		probationHistoryDao.saveProbationHistory(probationHistory);
		
	}

}
