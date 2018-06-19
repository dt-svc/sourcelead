package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ExpensesCategoryDao;
import com.bis.sourcelead.entity.ExpensesCategory;

@Repository
public class ExpensesCategoryDaoImpl implements ExpensesCategoryDao {
	private static final Logger logger = LoggerFactory.getLogger(ExpensesCategoryDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${SourceLead.100}")
	private String notFound;
	@Value("${SourceLead.101}")
	private String noRecords;
	@Value("${SourceLead.102}")
	private String statusUpdate;
	@Value("${SourceLead.103}")
	private String statusDelete;
	@Value("${SourceLead.106}")
	private String duplicateValue;
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}


	@Override
	@Transactional
	public List<ExpensesCategory> getAllExpensesCategoryByLocationId(Long orgLocationId) {
		
				
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from ExpensesCategory ec where ec.orgLocationId.id = :orgLocationId  ORDER BY ec.id DESC";
	    List<ExpensesCategory> documentsList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
        return documentsList;
	}
	
	@Override
	@Transactional
	public List<ExpensesCategory> getAllExpensesCategoryByLocationIdAndStatus(Long orgLocationId, String status,String isGlobalUser) {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		String globalUserQuery = "";
		 if(StringUtils.isNotEmpty(isGlobalUser) && isGlobalUser.equalsIgnoreCase("Y")) {
		    	globalUserQuery = " ec.orgLocationId.org.id IN (select ol.org.id from OrgLocations ol where ol.id = :orgLocationId)";
		    }else {
		    	globalUserQuery = " ec.orgLocationId.id = :orgLocationId";
		    }
		 
	    String query = "from ExpensesCategory ec where "+globalUserQuery+" and ec.status =:status ORDER BY ec.id DESC";
	    List<ExpensesCategory> documentsList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setString("status", status).list();
        return documentsList;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ExpensesCategory storeExpenseCategory(ExpensesCategory expensesCategory) throws Exception {
		
		logger.debug("Adding Expenses Category");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (expensesCategory != null) {
				session.saveOrUpdate(expensesCategory);
			}
			logger.debug("Expenses Category Added Successfully");
		}catch(ConstraintViolationException exception)
			{
			
			}
		return expensesCategory;
	}


	@Override
	@Transactional
	public ExpensesCategory getExpensesCategoryByExpensesCategoryIdAndOrgLocationId(Long expCategoryId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ExpensesCategory expCat  where expCat.id = :expCategoryId";  
		List<ExpensesCategory> expensesCategoryList = session.createQuery(query).setLong("expCategoryId", expCategoryId).list();
		ExpensesCategory expensesCategory = null;
		if(expensesCategoryList != null && !expensesCategoryList.isEmpty()){
			expensesCategory = expensesCategoryList.iterator().next();
		}
		return expensesCategory;
	}


	@Override
	@Transactional
	public Boolean validateExpensesCategoryType(Long orgLocationId, String categoryName) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = false;
		String query ="from ExpensesCategory ec where ec.expensesName = :categoryName and ec.orgLocationId.id = :orgLocationId  ";
		List<ExpensesCategory> expensesCategoryList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("categoryName", categoryName).list();
		if(expensesCategoryList !=null && expensesCategoryList.size() >0){
			flag = true;
			return flag;
		}else{
			return flag;
		}
	}


	@Override
	@Transactional
	public String getExpenseAttachmentStatusByCategoryId(Long categoryId) {
		String attachment = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query ="select ec.isAttachemtRequired from ExpensesCategory ec where ec.id = :categoryId";
		List<String> expensesCategoryList = session.createQuery(query).setLong("categoryId", categoryId).list();
		if(expensesCategoryList != null && !expensesCategoryList.isEmpty()) {
			attachment = expensesCategoryList.get(0);
		}
		return attachment;
	}
	
	
}

