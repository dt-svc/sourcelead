package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ExpensesCategoryDao;
import com.bis.sourcelead.entity.ExpensesCategory;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ExpensesCategoryService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@PropertySource("${propertyLocation:app.properties}")
@RequestMapping("/app")
public class ExpensesCategoryController {
	
	private static final Logger logger = LoggerFactory.getLogger(ExpensesCategoryController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	private ExpensesCategoryService expensesCategoryService;
	
	@Autowired
	private LookUpDataService lookUpDataService;
	
	@Autowired
	private ExpensesCategoryDao expensesCategoryDao;
	
	
	
	
	
	protected boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
	
	/**
	 * Handles to display expenseCategory jsp (this page having ExpensesCategoryList,AddExpensesCategory),
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/expenseCategory")
 	public ModelAndView expenseCategory(HttpServletRequest request) throws Exception {
		

		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    
		    ModelAndView model = new ModelAndView();
		    try {
		    commonUtil.highLightMenu(Constants.OrgSettings, request);
		    
		    List<LookUpData> lookUpExpCatList = lookUpDataService.getLookUpDataByCategory(Constants.EXPENSESCATEGORY);
		    
		    for(LookUpData lookUpData:lookUpExpCatList){
		    	  boolean expCategory = expensesCategoryService.validateExpensesCategoryType(orgLocations.getId(), lookUpData.getName());
		    	  if(!expCategory){
		    		    ExpensesCategory expensesCategory = new ExpensesCategory();
		    		    expensesCategory.setExpensesName(lookUpData.getName());
		    		    expensesCategory.setAmountType("Flat");
		    		    expensesCategory.setOrgLocationId(orgLocations);
		    		    expensesCategory.setStatus(Constants.MEMBER_ACTIVE);
		    			expensesCategory.setCreatedBy(user.getUserCode());
		    			expensesCategory.setCreatedDate(commonUtil.currentDate());
		    			expensesCategory.setUpdatedBy(user.getUserCode());
		    			expensesCategory.setUpdatedDate(commonUtil.currentDate());
		    		   expensesCategoryDao.storeExpenseCategory(expensesCategory); 
		    	  }
		    }
		    
		    model.addObject("lookUpExpCatList", lookUpExpCatList);
		    
		    List<ExpensesCategory>  expensesCategoryList = expensesCategoryService.getAllExpensesCategoryByLocationIdAndCategoryId(orgLocations.getId());
		    model.addObject("expensesCategoryList", expensesCategoryList);
		    
		    model.setViewName("expenses/expenseCategory");
 			model.addObject("sourceleadUrl", sourceleadUrl);
		}catch(Exception e){
			  model.addObject("AWKWARD", true);
			  model.setViewName("expenses/expenseCategory");
		}
 			
 			return model;
 	}
	
	
	
}
