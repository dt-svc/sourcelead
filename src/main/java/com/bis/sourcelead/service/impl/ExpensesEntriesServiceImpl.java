package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesCategory;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.ExpensesEntryDocuments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.enums.PaymentModes;
import com.bis.sourcelead.service.ExpensesEntriesService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class ExpensesEntriesServiceImpl implements ExpensesEntriesService{
	
	@Autowired
	TimeZoneService timeZoneService;

	@Override
	public Set<ExpensesEntries> prepareExpensesEntriesListByJson(JSONObject jsonObj,  MultipartHttpServletRequest request,
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
						Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
						DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
						Date date = Constants.DF_DMY.parse(jsonObj.getString("categoryDate"+c));
						String currentTimeStr = Constants.TZ_DF.format(date);  
						String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
						Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
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
						entries.setDescription(jsonObj.getString("entryDes"+c));
					}
					
					if(jsonObj.has("paymentMode"+c)){
						entries.setPaymentMode(PaymentModes.getNameByValue(jsonObj.getString("paymentMode"+c)));
					}
					
					entries.setCreatedDate(CommonUtil.currentDate());
					entries.setCreatedBy(expenses.getCreatedBy());
					entries.setUpdatedDate(CommonUtil.currentDate());
					entries.setExpensesId(expenses);
					entries.setUpdatedBy(expenses.getUpdatedBy());
					
				    //save  mutliple attachment in timesheet submissison attachment.
					if ((jsonObj.has("attcahmentMaxRowNumber"+c))){
				    	String attcahmentMaxRowNumber = jsonObj.getString("attcahmentMaxRowNumber"+c);
						int attcahmentMaxRowNumberCount = Integer.parseInt(attcahmentMaxRowNumber);
						int attachmentRowNum = 0;
						HashSet<ExpensesEntryDocuments> eeDocList = new HashSet<>();
				    	for (int i = 0; i < attcahmentMaxRowNumberCount; i++) {
				    		  ExpensesEntryDocuments eedoc = new ExpensesEntryDocuments();
			       		      final Map files = request.getFileMap();
			       		      eedoc.setExpensesEntriesId(entries);
			                   MultipartFile  multiFile = (MultipartFile)files.get(i+"docFile"+c);
			                   if(multiFile != null && !multiFile.isEmpty()){
			                	   eedoc.setDocFile(multiFile.getBytes());
			                	   eedoc.setFileName(multiFile.getOriginalFilename());
			                       
			                       if(multiFile.getContentType()!=null){
			                    	   eedoc.setFileContentType(multiFile.getContentType());
			                       }
			                       eedoc.setStatus(Constants.CANDIDATE_ACTIVE);
			                       eedoc.setCreatedDate(CommonUtil.currentDate());
			                       eedoc.setCreatedBy(entries.getCreatedBy());
			                       eedoc.setUpdatedDate(CommonUtil.currentDate());
			                       eedoc.setUpdatedBy(entries.getUpdatedBy());
				                   eeDocList.add(eedoc);
			                   }
				    	  }
				    	  entries.setExpensesEntryDocuments(eeDocList);
						}
						

						entries.setStatus(Constants.RECORD_ACTIVE.toString());
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
