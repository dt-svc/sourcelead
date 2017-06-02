package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bis.sourcelead.dao.ExpensesCategoryDao;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.service.ExpensesCategoryService;
/**
 * 
 * @author BIS_Prasad
 *
 */

@Service
public class ExpensesCategoryServiceImpl implements ExpensesCategoryService {

	@Autowired
	ExpensesCategoryDao expensesCategoryDao;
	
	@Autowired
	OrgLocationsDao orgLocationsDao;
	
}
