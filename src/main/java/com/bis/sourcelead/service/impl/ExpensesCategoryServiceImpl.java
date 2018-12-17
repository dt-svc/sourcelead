package com.bis.sourcelead.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ExpensesCategoryDao;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.ExpensesCategory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ExpensesCategoryService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
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
	public List<ExpensesCategory> getAllExpensesCategoryByLocationIdAndStatus(Long orgLocationId, String status,String isGlobalUser) {
		return expensesCategoryDao.getAllExpensesCategoryByLocationIdAndStatus(orgLocationId, status,isGlobalUser);
	}

	@Override
	public ExpensesCategory storeExpenseCategory(JSONObject jsonObject, Long orgLocationId,User user) throws Exception {
		
	       		
	       	ExpensesCategory expensesCategory =null;
	 		if(jsonObject.has("categoryId") && jsonObject.getString("categoryId") != null && !"".equals(jsonObject.getString("categoryId"))){
	 			expensesCategory = expensesCategoryDao.getExpensesCategoryByExpensesCategoryIdAndOrgLocationId(Long.parseLong(jsonObject.getString("categoryId")));
	 		}else{	
	 			expensesCategory = new ExpensesCategory();
	 			expensesCategory.setCreatedDate(commonUtil.currentDate());
	 			expensesCategory.setCreatedBy(user.getUserCode());
	 			
	 		}
		
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(orgLocationId);
		expensesCategory.setOrgLocationId(orgLocations);
		
		
		if(jsonObject.has("expensesName") && jsonObject.getString("expensesName") !=null){
			expensesCategory.setExpensesName(jsonObject.getString("expensesName"));
			}
		
		
		if(jsonObject.has("taxPercentageReward") && null != jsonObject.getString("taxPercentageReward")){
			if(StringUtils.isNotBlank(jsonObject.getString("taxPercentageReward"))) {
				expensesCategory.setAllowedAmount(Float.parseFloat(jsonObject.getString("taxPercentageReward")));
			}else {
				expensesCategory.setAllowedAmount(0);
			}
			expensesCategory.setAmountType("Percentage");
		}
			
		//if we are not given any rate(Flat , Percentage) by default it will take Flat.
		
		if(jsonObject.has("taxFlatReward") && null != jsonObject.getString("taxFlatReward") && !"".equals(jsonObject.getString("taxFlatReward")) ){
			expensesCategory.setAllowedAmount(Float.parseFloat(jsonObject.getString("taxFlatReward")));
			expensesCategory.setAmountType("Flat");
		}
		
		
		if(jsonObject.has("description")){
			expensesCategory.setDescription(jsonObject.getString("description"));
		}
		
		if(jsonObject.has("isAttachmetRequired")){
			expensesCategory.setIsAttachemtRequired(jsonObject.getString("isAttachmetRequired"));
		}else {
			expensesCategory.setIsAttachemtRequired(null);
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
	public ExpensesCategory getExpensesCategoryByExpensesCategoryIdAndOrgLocationId(Long expCategoryId) throws Exception {
		return expensesCategoryDao.getExpensesCategoryByExpensesCategoryIdAndOrgLocationId(expCategoryId);
	}

	@Override
	public ExpensesCategory removeExpensesCategoryByCategoryId(Long expCategoryId) throws Exception {
		
		ExpensesCategory expenCat = expensesCategoryDao.getExpensesCategoryByExpensesCategoryIdAndOrgLocationId(expCategoryId);
		expenCat.setStatus(Constants.MEMBER_IN_ACTIVE);
		expenCat = expensesCategoryDao.storeExpenseCategory(expenCat);
		
		return expenCat;
	}

	@Override
	public Boolean validateExpensesCategoryType(Long orgLocationId, String categoryName) throws Exception {
		return expensesCategoryDao.validateExpensesCategoryType(orgLocationId, categoryName);
	}

	@Override
	public String getExpenseAttachmentStatusByCategoryId(Long categoryId) {
		return expensesCategoryDao.getExpenseAttachmentStatusByCategoryId(categoryId);

	}
	
	
}
