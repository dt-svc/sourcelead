package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ExpensesHistoryDao;
import com.bis.sourcelead.entity.ExpensesHistory;
import com.bis.sourcelead.service.ExpensesHistoryService;

@Service
public class ExpensesHistoryServiceImpl implements ExpensesHistoryService{
	
	@Autowired
	ExpensesHistoryDao expensesHistoryDao;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;

	@Override
	public ExpensesHistory storeExpensesHistory(ExpensesHistory expensesHistory) throws Exception {
		
		return expensesHistoryDao.storeExpensesHistory(expensesHistory);
	}

	@Override
	public ExpensesHistory getExpensesHistoryByExpensesHistoryId(Long expensesHistoryId) throws Exception {
		return expensesHistoryDao.getExpensesHistoryByExpensesHistoryId(expensesHistoryId);
	}

	@Override
	public List<ExpensesHistory> getExpensesHistoryListByExpensesHistoryId(Long expensesHistoryId) throws Exception {
		return expensesHistoryDao.getExpensesHistoryListByExpensesHistoryId(expensesHistoryId);
	}

}
