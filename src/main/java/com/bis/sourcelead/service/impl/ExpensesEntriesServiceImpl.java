package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ExpensesEntriesDao;
import com.bis.sourcelead.dao.ExpensesEntryDocumentsDao;
import com.bis.sourcelead.dto.ExpensesDTO;
import com.bis.sourcelead.dto.ExpensesEntriesDTO;
import com.bis.sourcelead.dto.FileInfo;
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
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class ExpensesEntriesServiceImpl implements ExpensesEntriesService{
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Value("${static.path}")
	private String staticPath;
	
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
	public Set<ExpensesEntries> prepareExpensesEntriesListByJson(ExpensesDTO addExpensesDTO,HttpServletRequest request,Expenses expenses) throws Exception {
		
		    List<Long> expenseEntryDbIds = null;
		    ArrayList<Long> expenseEntryIds = new ArrayList<>();
		    if(null != expenses && null != expenses.getId()){
		    	expenseEntryDbIds = expensesEntriesDao.getExpensesEntryIdsByExpenseId(expenses.getId());
		    }
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 
		    HashSet<ExpensesEntries> expensesEntriesList = new HashSet<ExpensesEntries>(); 
		    for(ExpensesEntriesDTO category:addExpensesDTO.getExpenseEntry()){
			    if(StringUtils.isNotEmpty(category.getCategoryType())){
						ExpensesEntries  entries  = new ExpensesEntries();
						if(category.getId() != null){
							entries.setId(category.getId());
						}
						ExpensesCategory ec = new ExpensesCategory();
						ec.setId(Long.parseLong(category.getCategoryType().split(",")[0]));
						entries.setExpensesCategoryId(ec);
						
						if(StringUtils.isNotBlank(category.getDateOccurred())){
							Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
							DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
							Date date = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),category.getDateOccurred());
							String currentTimeStr = Constants.TZ_DF.format(date);  
							String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
							Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
							entries.setExpenseDate(converteDate);
						}
						
						if(StringUtils.isNotBlank(category.getReceiptIssuedBy())){
							entries.setReceiptIssuedBy(category.getReceiptIssuedBy());
						}
						if(StringUtils.isNotBlank(category.getCategoryAmount()) && StringUtils.isNotBlank(category.getCategoryAmountType())){
							Countries country = countriesService.getCountriesByCountryName(orgLocation.getCountry());
							if(!country.getCurrency().equalsIgnoreCase(category.getCategoryAmountType())){
							 Float amount = conversionUtil.convert(category.getCategoryAmountType(),country.getCurrency(),Float.valueOf(category.getCategoryAmount()));
							 entries.setCurrencyType(country.getCurrency());
							 entries.setAmount(amount);
							}else{
								entries.setCurrencyType((category.getCategoryAmountType()));
								entries.setAmount(Float.valueOf(category.getCategoryAmount()));
							}
						}
					if(StringUtils.isNotBlank(category.getEntryDescription())){
						entries.setDescription(category.getEntryDescription());
					}
					
					
					if (StringUtils.isNotBlank(category.getPaidBy())){
						entries.setPaidBy(category.getPaidBy());
					 }
					
					if(StringUtils.isNotBlank(category.getPaymentMode())){
						entries.setPaymentMode(PaymentModes.getNameByValue(category.getPaymentMode()));
					}
					
					//category.setStaticPath(staticPath);
					
					entries.setCreatedDate(CommonUtil.currentDate());
					entries.setCreatedBy(expenses.getCreatedBy());
					entries.setUpdatedDate(CommonUtil.currentDate());
					entries.setExpensesId(expenses);
					entries.setUpdatedBy(expenses.getUpdatedBy());
					
					
				    //save  mutliple attachments.
					 HashSet<ExpensesEntryDocuments> eeDocList = new HashSet<>();
 	                   if(category.getFileInfo() != null && !category.getFileInfo().isEmpty()) {
				    		for ( FileInfo fileinfo:category.getFileInfo()) {
				    		  ExpensesEntryDocuments eedoc = new ExpensesEntryDocuments();
				    		  eedoc.setExpensesEntriesId(entries);	
				    		  eedoc.setFileURL(fileinfo.getFileURL());
				    		  eedoc.setFileName(fileinfo.getFileName());
				    		  eedoc.setFileContentType(fileinfo.getContentType());
							  FileUploadUtil.moveFile(staticPath, fileinfo, "expsesDocuments");
				    		  
			                       eedoc.setStatus(Constants.CANDIDATE_ACTIVE);
			                       eedoc.setCreatedDate(CommonUtil.currentDate());
			                       eedoc.setCreatedBy(entries.getCreatedBy());
			                       eedoc.setUpdatedDate(CommonUtil.currentDate());
			                       eedoc.setUpdatedBy(entries.getUpdatedBy());
				                   eeDocList.add(eedoc);
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