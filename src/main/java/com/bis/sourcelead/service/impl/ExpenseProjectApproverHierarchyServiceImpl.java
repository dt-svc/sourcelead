package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ExpenseProjectApproverHierarchyDao;
import com.bis.sourcelead.service.ExpenseProjectApproverHierarchyService;

@Service
public class ExpenseProjectApproverHierarchyServiceImpl implements ExpenseProjectApproverHierarchyService {

	@Autowired
	private  ExpenseProjectApproverHierarchyDao  expenseProjectApproverHierarchyDao;
	
	
}
