package com.bis.sourcelead.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.service.ExpensesCategoryService;
import com.bis.sourcelead.web.HomeController;

/**
 * Handles requests for the ExpensesCategory service.
 * @author BIS_Prasad
 *
 */
@RestController
public class ExpensesCategoryRestController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ExpensesCategoryService expensesCategoryService;
    
	
	
	
}
