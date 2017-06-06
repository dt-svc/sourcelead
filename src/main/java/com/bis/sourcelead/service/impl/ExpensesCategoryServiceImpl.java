package com.bis.sourcelead.service.impl;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ExpensesCategoryDao;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.entity.ExpensesCategory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ExpensesCategoryService;
import com.bis.sourcelead.util.CommonUtil;
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
	
	@Autowired
	private CommonUtil commonUtil;

	@Override
	public List<ExpensesCategory> getAllExpensesCategoryByLocationIdAndCategoryId(Long orgLocationId) {
		return expensesCategoryDao.getAllExpensesCategoryByLocationId(orgLocationId);
	}

	@Override
	public ExpensesCategory storeExpenseCategory(JSONObject jsonObject, Long orgLocationId,User user) throws Exception {
		
		ExpensesCategory expensesCategory =null;
	       		expensesCategory = new ExpensesCategory();
		
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(orgLocationId);
		expensesCategory.setOrgLocationId(orgLocations);
		
		
		if(jsonObject.has("expensesName") && jsonObject.getString("expensesName") !=null){
			expensesCategory.setExpensesName(jsonObject.getString("expensesName"));
			}
		
		if(jsonObject.has("allowedAmount") && jsonObject.getString("allowedAmount") !=null){
			expensesCategory.setAllowedAmount(Float.parseFloat(jsonObject.getString("allowedAmount")));
			}
		
			/*if(jsonObject.has("allowedAmount") && jsonObject.getString("allowedAmount") !=null){
			expensesCategory.setCategoryType("Percentage");
			expensesCategory.setAllowedAmount(Float.parseFloat(jsonObject.getString("allowedAmount")));
			}*/
			
		
		if(jsonObject.has("description")){
			expensesCategory.setDescription(jsonObject.getString("description"));
		}
		expensesCategory.setStatus(Constants.MEMBER_ACTIVE);
		expensesCategory.setCreatedBy(user.getUserCode());
		expensesCategory.setCreatedDate(commonUtil.currentDate());
		expensesCategory.setUpdatedBy(user.getUserCode());
		expensesCategory.setUpdatedDate(commonUtil.currentDate());
		expensesCategory = expensesCategoryDao.storeExpenseCategory(expensesCategory);
		
		
		return expensesCategory;
	}

	@Override
	public ExpensesCategory getExpensesCategoryByExpensesCategoryIdAndOrgLocationId(Long expCategoryId,
			Long orgLocationId) throws Exception {
		return expensesCategoryDao.getExpensesCategoryByExpensesCategoryIdAndOrgLocationId(expCategoryId, orgLocationId);
	}
	
}
