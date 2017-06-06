package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesCategory;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.service.ExpensesEntriesService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class ExpensesEntriesServiceImpl implements ExpensesEntriesService{
	
	@Autowired
	TimeZoneService timeZoneService;

	@Override
	public Set<ExpensesEntries> prepareExpensesEntriesListByJson(JSONObject jsonObj,  HttpServletRequest request,
			Expenses expenses) throws Exception {
		
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 
		    HashSet<ExpensesEntries> expensesEntriesList = new HashSet<ExpensesEntries>(); 
		    if(jsonObj.has("maxRecCount")){
			int maxCount = Integer.parseInt(jsonObj.getString("maxRecCount"));

			for (int c = 1; c < maxCount; c++) {
				ExpensesEntries  entries  = new ExpensesEntries();
					if(jsonObj.has("categoryType"+c)){
						ExpensesCategory ec = new ExpensesCategory();
						ec.setId(Long.parseLong(jsonObj.getString("categoryType"+c)));
						entries.setExpensesCategoryId(ec);
					
					
					if(jsonObj.has("categoryDate"+c)){
						DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
						Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
						DateFormat standardFormat = new SimpleDateFormat ("dd-MM-yyyy");
						String currentTimeStr = Constants.TZ_DF.format(jsonObj.has("categoryDate"+c));  
						String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
						Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
						currentDateInTimezone = standardFormat.format(converteDate);
					
						entries.setExpenseDate(converteDate);
					}
					
					if(jsonObj.has("receiptIssuedBy"+c)){
						entries.setReceiptIssuedBy(jsonObj.getString("receiptIssuedBy"+c));
					}
					
					if(jsonObj.has("expenseRateType"+c)){
						entries.setCurrencyType(jsonObj.getString("expenseRateType"+c));
					}
					
					if(jsonObj.has("expenseAmount"+c)){
						entries.setAmount(Float.valueOf(jsonObj.getString("expenseAmount"+c)));
					}
					
					if(jsonObj.has("entryDes"+c)){
						entries.setReceiptIssuedBy(jsonObj.getString("entryDes"+c));
					}
						
						entries.setCreatedDate(new Date());
						entries.setUpdatedDate(new Date());
						entries.setExpensesId(expenses);
						expensesEntriesList.add(entries);
					}
			  }
		    }
		   return expensesEntriesList;
		    		  
		
	}
	
}
