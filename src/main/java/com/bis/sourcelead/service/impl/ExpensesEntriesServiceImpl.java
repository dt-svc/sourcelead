package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ExpensesEntriesDao;
import com.bis.sourcelead.dao.ExpensesEntryDocumentsDao;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesCategory;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.ExpensesEntryDocuments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.enums.PaymentModes;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.ExpensesEntriesService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class ExpensesEntriesServiceImpl implements ExpensesEntriesService{
	
	@Autowired
	TimeZoneService timeZoneService;
	
	
	@Autowired
	CountriesService countriesService;

	@Autowired
	private ExpensesEntriesDao expensesEntriesDao;
	
	@Autowired
	private ExpensesEntryDocumentsDao expensesEntryDocumentsDao;
	
	@Autowired
	private ConversionUtil conversionUtil;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Override
	public Set<ExpensesEntries> prepareExpensesEntriesListByJson(JSONObject jsonObj,  MultipartHttpServletRequest request,
			Expenses expenses) throws Exception {
		
		    List<Long> expenseEntryDbIds = null;
		    ArrayList<Long> expenseEntryIds = new ArrayList<>();
		    if(null != expenses && null != expenses.getId()){
		    	expenseEntryDbIds = expensesEntriesDao.getExpensesEntryIdsByExpenseId(expenses.getId());
		    }
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 
		    HashSet<ExpensesEntries> expensesEntriesList = new HashSet<ExpensesEntries>(); 
		    if(jsonObj.has("maxRecCount")){
			int maxCount = Integer.parseInt(jsonObj.getString("maxRecCount"));
			//SimpleDateFormat dateFormat=orgLocation.getLocFullFormDateFormatter();
			for (int c = 1; c < maxCount; c++) {
					ExpensesEntries  entries  = new ExpensesEntries();
					if(jsonObj.has("expenseEntryId"+c)){
						entries.setId(Long.parseLong(jsonObj.getString("expenseEntryId"+c)));
					}
					if(jsonObj.has("categoryType"+c)){
						ExpensesCategory ec = new ExpensesCategory();
						String categoryType[] = jsonObj.getString("categoryType"+c).split(",");
						ec.setId(Long.parseLong(categoryType[0]));
						entries.setExpensesCategoryId(ec);
					
					if(jsonObj.has("categoryDate"+c)){
						Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
						DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
						Date date = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),jsonObj.getString("categoryDate"+c));
						String currentTimeStr = Constants.TZ_DF.format(date);  
						String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
						Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
						entries.setExpenseDate(converteDate);
					}
					
					if(jsonObj.has("receiptIssuedBy"+c)){
						entries.setReceiptIssuedBy(jsonObj.getString("receiptIssuedBy"+c));
					}
					
					/*if(jsonObj.has("expenseRateType"+c)){
						entries.setCurrencyType(jsonObj.getString("expenseRateType"+c));
					}
					
					if(jsonObj.has("expenseAmount"+c)){
						entries.setAmount(Float.valueOf(jsonObj.getString("expenseAmount"+c)));
					}*/
					if(jsonObj.has("expenseRateType"+c) && jsonObj.has("expenseAmount"+c)){
						if(StringUtils.isNotEmpty(jsonObj.getString("expenseRateType"+c)) && jsonObj.getString("expenseAmount"+c) != null){
						Countries country = countriesService.getCountriesByCountryName(orgLocation.getCountry());
						if(!country.getCurrency().equalsIgnoreCase(jsonObj.getString("expenseRateType"+c))){
						 Float amount = conversionUtil.convert(jsonObj.getString("expenseRateType"+c),country.getCurrency(),Float.valueOf(jsonObj.getString("expenseAmount"+c)));
						 entries.setCurrencyType(country.getCurrency());
						 entries.setAmount(amount);
						}else{
							entries.setCurrencyType(jsonObj.getString("expenseRateType"+c));
							entries.setAmount(Float.valueOf(jsonObj.getString("expenseAmount"+c)));
						}
					}
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
						HashSet<ExpensesEntryDocuments> eeDocList = new HashSet<>();
						
						if(jsonObj.has("dbAttachmentMaxRowNumber"+c)){
							String dbAttcahmentMaxRowNumber = jsonObj.getString("dbAttachmentMaxRowNumber"+c);
							int dbAttcahmentMaxRowNumberCount = Integer.parseInt(dbAttcahmentMaxRowNumber);
							for (int j = 0; j < dbAttcahmentMaxRowNumberCount; j++) {
								if(jsonObj.has(j+"DbdocFileIsDeleted"+c) && jsonObj.getString(j+"DbdocFileIsDeleted"+c).equalsIgnoreCase("Y") && jsonObj.has(j+"DbdocFileId"+c)){
									expensesEntryDocumentsDao.removeExpensesEntryDocouments(Long.parseLong(jsonObj.getString(j+"DbdocFileId"+c)));
								}
								
							}
						}
						
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
						
						if(entries.getId() != null){
							expenseEntryIds.add(entries.getId());
						}
						expensesEntriesList.add(entries);
					}
			  }
		    }
		    if(expenseEntryDbIds != null && !expenseEntryDbIds.isEmpty()){
		    	expenseEntryDbIds.removeAll(expenseEntryIds);
		    	if(expenseEntryDbIds != null && !expenseEntryDbIds.isEmpty()){
		    		expensesEntriesDao.removeAllExpenseEntries(expenseEntryDbIds);
		    	}
		    }
		   return expensesEntriesList;
		    		  
	}

	@Override
	public List<ExpensesEntries> getExpensesEntriesByExpenseId(long expenseId) {
		return expensesEntriesDao.getExpensesEntriesByExpenseId(expenseId);
	}
	
}