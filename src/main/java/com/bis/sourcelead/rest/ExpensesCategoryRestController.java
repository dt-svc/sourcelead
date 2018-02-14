package com.bis.sourcelead.rest;

import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
import com.bis.sourcelead.web.HomeController;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

/**
 * Handles requests for the ExpensesCategory service.
 * @author BIS_Prasad
 *
 */
@RestController
@RequestMapping("/app")
public class ExpensesCategoryRestController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ExpensesCategoryService expensesCategoryService;
	
	
	@Autowired
	private LookUpDataService lookUpDataService;
	
	@Autowired
	private CommonUtil commonUtil;
    
	@Autowired
	private ExpensesCategoryDao expensesCategoryDao;
	
	/**
	 * this method is used to Save or Store Expense Category into DB
	 * 
	 * @param json
	 * @param request
	 * @return string
	 * @throws NullPointer Exception
	 */
	
	@RequestMapping(value = "/storeExpensesCategory", method = RequestMethod.POST)
	public @ResponseBody String storeExpensesCategory(@RequestParam(value = "json", required = false) String json,HttpServletRequest request) throws Exception {

		String jsonc = "{" + json + "}";
		JSONObject jsonObject = new JSONObject(jsonc);
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		expensesCategoryService.storeExpenseCategory(jsonObject,loginOrgLocation.getId(), user);
		return "success";
	}
	
	
	
	/**
	 * This method is used to for getting Expense Category List By OrgLocationID
	 *  
	 * @param request
	 * @return
	 * @throws NullPointer
	 */
	
	@RequestMapping(value = "/getExpensesCategoryList", method = RequestMethod.GET)
	public @ResponseBody List<ExpensesCategory> getExpensesCategoryList(HttpServletRequest request) throws Exception {
		/*String resultJson = null;*/
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<ExpensesCategory> expensesCategoryList = expensesCategoryService.getAllExpensesCategoryByLocationIdAndCategoryId(orgLocations.getId());
		/*resultJson = toJSON(expensesCategoryList);*/
		return expensesCategoryList;
	}
	
		
	/**
	 * This Method is for getting ExpensesCategory by category ID
	 * 
	 * @param categoryId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getExpensesCategoryByCategoryId")
	public String getExpensesCategoryByCategoryId(@RequestParam(value = "categoryId", required = false) Long categoryId,HttpServletRequest request) throws Exception {
		String resultJson;
		ExpensesCategory  expensesCategoryList = expensesCategoryService.getExpensesCategoryByExpensesCategoryIdAndOrgLocationId(categoryId);
		
				
				 List<LookUpData> expCatList = lookUpDataService.getLookUpDataByCategory(Constants.EXPENSESCATEGORY); 
					
				 expensesCategoryList.setExpenseCategoryList(expCatList);
				
				     resultJson = commonUtil.toJSON(expensesCategoryList);
				     return resultJson;
	}
	
	/**
	 * This Method is for update  ExpensesCategory by category ID
	 * 
	 * @param json
	 * @param categoryId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upDateExpensesCategoryByCategoryId")
	public String upDateExpensesCategoryByCategoryId(@RequestParam(value="json", required=false) String  json,@RequestParam(value = "categoryId", required = false) Long categoryId,HttpServletRequest request) throws Exception {

		    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    ExpensesCategory  expensesCategory;
			String jsonc = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(jsonc);
			
			expensesCategory = expensesCategoryService.storeExpenseCategory(jsonObj,loginOrgLocation.getId(),user);
			return "success";
	}
	
	/**
	 * This Method is for remove  ExpensesCategory by category ID
	 * 
	 * @param categoryId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
		@RequestMapping(value = "/removeExpensesCategoryByCategoryId")
		public String removeExpensesCategoryByCategoryId(@RequestParam(value = "categoryId", required = false) Long categoryId,HttpServletRequest request) throws Exception {
			String resultJson;
			ExpensesCategory  expensesCategory = expensesCategoryService.removeExpensesCategoryByCategoryId(categoryId);
			 resultJson = commonUtil.toJSON(expensesCategory);

			 return resultJson;
		}
		
		/**
		 * This Method is for ReActivate  ExpensesCategory by category ID
		 * 
		 * @param categoryId
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/reActiveExpenseCategoryByCategoryId")
		public ExpensesCategory reActiveExpenseCategoryByCategoryId(@RequestParam(value = "categoryId", required = false) Long categoryId,HttpServletRequest request) throws Exception {
			ExpensesCategory expenCat = expensesCategoryService.getExpensesCategoryByExpensesCategoryIdAndOrgLocationId(categoryId);
			expenCat.setStatus(Constants.MEMBER_ACTIVE);
			expenCat = expensesCategoryDao.storeExpenseCategory(expenCat);
			 return expenCat;
		}
		
		/**
		 * This Method is used for To Check Expense Category Name Is Unique Or Not
		 * 
		 * @param name
		 * @param request
		 * @return
		 * @throws Exception
		 */
		
		@RequestMapping(value = "/validateExpenseCategoryType")
		public Boolean validateExpenseCategoryType(@RequestParam(value="name", required=false) String  name ,HttpServletRequest request) throws Exception {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
				Boolean expCategory = expensesCategoryService.validateExpensesCategoryType(loginOrgLocation.getId(), name );
				return expCategory;
		}
	
	
	 private String toJSON(Object obj){
	        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	            public HierarchicalStreamWriter createWriter(Writer writer) {
	                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	            }
	        });
	        return xstream.toXML(obj);
	    }
	
}
