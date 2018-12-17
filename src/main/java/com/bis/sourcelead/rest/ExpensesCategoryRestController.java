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
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.ExpensesCategory;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
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
	public @ResponseBody Response<String> storeExpensesCategory(@RequestParam(value = "json", required = false) String json,HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		String jsonc = "{" + json + "}";
		JSONObject jsonObject = new JSONObject(jsonc);
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(user != null){
			try{
				expensesCategoryService.storeExpenseCategory(jsonObject,loginOrgLocation.getId(), user);
				response.setObject("success");
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.name());
	    	}catch(Exception e) {
	    		response.setResponse(Result.AWKWARD.name());
	    		response.setResponseMessage(Result.AWKWARD.name());
	    	}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.name());
		}
		return response;
	}
	
	
	
	/**
	 * This method is used to for getting Expense Category List By OrgLocationID
	 *  
	 * @param request
	 * @return
	 * @throws NullPointer
	 */
	
	@RequestMapping(value = "/getExpensesCategoryList", method = RequestMethod.GET)
	public @ResponseBody ResponseList<ExpensesCategory> getExpensesCategoryList(HttpServletRequest request)  {
		/*String resultJson = null;*/
		ResponseList<ExpensesCategory> response = new ResponseList<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(user != null){
		try{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<ExpensesCategory> expensesCategoryList = expensesCategoryService.getAllExpensesCategoryByLocationIdAndCategoryId(orgLocations.getId());
		/*resultJson = toJSON(expensesCategoryList);*/
		response.setData(expensesCategoryList);
		response.setResponse(Result.SUCCESS.name());
		response.setResponseMessage(Result.SUCCESS.name());
		}catch(Exception e) {
    		response.setResponse(Result.AWKWARD.name());
    		response.setResponseMessage(Result.AWKWARD.name());
    	}
	}else {
		response.setResponse(Result.SESSION_TIMEDOUT.name());
		response.setResponseMessage(Result.SESSION_TIMEDOUT.name());
	}
		
		return response;
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
	public Response<ExpensesCategory> getExpensesCategoryByCategoryId(@RequestParam(value = "categoryId", required = false) Long categoryId,HttpServletRequest request) throws Exception {
		Response<ExpensesCategory> response = new Response();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(user != null){
		try{
		ExpensesCategory  expensesCategoryList = expensesCategoryService.getExpensesCategoryByExpensesCategoryIdAndOrgLocationId(categoryId);
				 List<LookUpData> expCatList = lookUpDataService.getLookUpDataByCategory(Constants.EXPENSESCATEGORY); 
				 expensesCategoryList.setExpenseCategoryList(expCatList);
				     response.setObject(expensesCategoryList);
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.name());
						}catch(Exception e) {
				    		response.setResponse(Result.AWKWARD.name());
				    		response.setResponseMessage(Result.AWKWARD.name());
				    	}
					}else {
						response.setResponse(Result.SESSION_TIMEDOUT.name());
						response.setResponseMessage(Result.SESSION_TIMEDOUT.name());
					}
						
						return response;
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
	public Response<String> upDateExpensesCategoryByCategoryId(@RequestParam(value="json", required=false) String  json,@RequestParam(value = "categoryId", required = false) Long categoryId,HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    if(user != null){
				try{
		    ExpensesCategory  expensesCategory;
			String jsonc = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(jsonc);
			
			expensesCategory = expensesCategoryService.storeExpenseCategory(jsonObj,loginOrgLocation.getId(),user);
			response.setObject("success");
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.name());
    	}catch(Exception e) {
    		response.setResponse(Result.AWKWARD.name());
    		response.setResponseMessage(Result.AWKWARD.name());
    	}
	}else {
		response.setResponse(Result.SESSION_TIMEDOUT.name());
		response.setResponseMessage(Result.SESSION_TIMEDOUT.name());
	}
	return response;
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
		public Response<String> removeExpensesCategoryByCategoryId(@RequestParam(value = "categoryId", required = false) Long categoryId,HttpServletRequest request) throws Exception {
			//String resultJson;
			Response<String> response = new Response<>();
		    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    if(user != null){
				try{
			ExpensesCategory  expensesCategory = expensesCategoryService.removeExpensesCategoryByCategoryId(categoryId);
			// resultJson = commonUtil.toJSON(expensesCategory);
				response.setObject("success");
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.name());
	    	}catch(Exception e) {
	    		response.setResponse(Result.AWKWARD.name());
	    		response.setResponseMessage(Result.AWKWARD.name());
	    	}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.name());
		}
		return response;
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
		public Response<String> reActiveExpenseCategoryByCategoryId(@RequestParam(value = "categoryId", required = false) Long categoryId,HttpServletRequest request) throws Exception {
			
			Response<String> response = new Response<>();
		    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    if(user != null){
				try{
			
			
			ExpensesCategory expenCat = expensesCategoryService.getExpensesCategoryByExpensesCategoryIdAndOrgLocationId(categoryId);
			expenCat.setStatus(Constants.MEMBER_ACTIVE);
			expenCat = expensesCategoryDao.storeExpenseCategory(expenCat);
			response.setObject("success");
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.name());
    	}catch(Exception e) {
    		response.setResponse(Result.AWKWARD.name());
    		response.setResponseMessage(Result.AWKWARD.name());
    	}
	}else {
		response.setResponse(Result.SESSION_TIMEDOUT.name());
		response.setResponseMessage(Result.SESSION_TIMEDOUT.name());
	}
	return response;
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
		public Response<String> validateExpenseCategoryType(@RequestParam(value="name", required=false) String  name ,HttpServletRequest request) throws Exception {
			Response<String> response = new Response<>();
		    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    if(user != null){
				try{
				Boolean expCategory = expensesCategoryService.validateExpensesCategoryType(loginOrgLocation.getId(), name );
				response.setObject(expCategory.toString());
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.name());
	    	}catch(Exception e) {
	    		response.setResponse(Result.AWKWARD.name());
	    		response.setResponseMessage(Result.AWKWARD.name());
	    	}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.name());
		}
		return response;
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
