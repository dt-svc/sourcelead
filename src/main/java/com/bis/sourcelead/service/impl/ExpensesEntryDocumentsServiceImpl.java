package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ExpensesEntryDocumentsDao;
import com.bis.sourcelead.service.ExpensesEntryDocumentsService;


@Service
public class ExpensesEntryDocumentsServiceImpl implements ExpensesEntryDocumentsService {

	@Autowired
	ExpensesEntryDocumentsDao expensesEntryDocumentsDao;
	
	
	
	
}
