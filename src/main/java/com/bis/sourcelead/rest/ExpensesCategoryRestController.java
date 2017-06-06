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

import com.bis.sourcelead.entity.ExpensesCategory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ExpensesCategoryService;
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
public class ExpensesCategoryRestController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ExpensesCategoryService expensesCategoryService;
    
	
	
	/**
	 * this method is used to save Expense Category into DB
	 * @param json
	 * @param candidateId
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
	
	
	
	
	@RequestMapping(value = "/getExpensesCategoryList", method = RequestMethod.GET)
	public @ResponseBody String getExpensesCategoryList(HttpServletRequest request) throws Exception {
		String resultJson = null;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<ExpensesCategory> expensesCategoryList = expensesCategoryService.getAllExpensesCategoryByLocationIdAndCategoryId(orgLocations.getId());
		resultJson = toJSON(expensesCategoryList);
		return resultJson;
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
