package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ExpensesEntriesDao;
import com.bis.sourcelead.dao.ExpensesEntryDocumentsDao;
import com.bis.sourcelead.dto.AddExpensesDTO;
import com.bis.sourcelead.dto.CategoryDTO;
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
	public Set<ExpensesEntries> prepareExpensesEntriesListByJson(AddExpensesDTO addExpensesDTO,HttpServletRequest request,Expenses expenses) throws Exception {
		
		    List<Long> expenseEntryDbIds = null;
		    ArrayList<Long> expenseEntryIds = new ArrayList<>();
		    if(null != expenses && null != expenses.getId()){
		    	expenseEntryDbIds = expensesEntriesDao.getExpensesEntryIdsByExpenseId(expenses.getId());
		    }
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 
		    HashSet<ExpensesEntries> expensesEntriesList = new HashSet<ExpensesEntries>(); 
		    for(CategoryDTO category:addExpensesDTO.getCategoryDTO()){
			    if(StringUtils.isNotBlank(category.getCategoryType())){
				//int maxCount = Integer.parseInt(category.getMaxRecCount());
				//SimpleDateFormat dateFormat=orgLocation.getLocFullFormDateFormatter();
				//for (int c = 1; c < maxCount; c++) {
						ExpensesEntries  entries  = new ExpensesEntries();
						if(StringUtils.isNotBlank(category.getExpenseEntryId())){
							entries.setId(Long.parseLong(category.getExpenseEntryId()));
						}
						if(StringUtils.isNotBlank(category.getCategoryType())){
							ExpensesCategory ec = new ExpensesCategory();
							String categoryType[] = category.getCategoryType().split(",");
							ec.setId(Long.parseLong(categoryType[0]));
							entries.setExpensesCategoryId(ec);
						
						if(StringUtils.isNotBlank(category.getCategoryDate())){
							Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
							DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
							Date date = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),category.getCategoryDate());
							String currentTimeStr = Constants.TZ_DF.format(date);  
							String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
							Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
							entries.setExpenseDate(converteDate);
						}
						
						if(StringUtils.isNotBlank(category.getReceiptIssuedBy())){
							entries.setReceiptIssuedBy(category.getReceiptIssuedBy());
						}
						
						/*if(jsonObj.has("expenseRateType"+c)){
							entries.setCurrencyType(jsonObj.getString("expenseRateType"+c));
						}
						
						if(jsonObj.has("expenseAmount"+c)){
							entries.setAmount(Float.valueOf(jsonObj.getString("expenseAmount"+c)));
						}*/
						if(StringUtils.isNotBlank(category.getExpenseAmount()) && StringUtils.isNotBlank(category.getExpenseRateType())){
							Countries country = countriesService.getCountriesByCountryName(orgLocation.getCountry());
							if(!country.getCurrency().equalsIgnoreCase(category.getExpenseRateType())){
							 Float amount = conversionUtil.convert(category.getExpenseRateType(),country.getCurrency(),Float.valueOf(category.getExpenseAmount()));
							 entries.setCurrencyType(country.getCurrency());
							 entries.setAmount(amount);
							}else{
								entries.setCurrencyType((category.getExpenseRateType()));
								entries.setAmount(Float.valueOf(category.getExpenseAmount()));
							}
						}
					}
					if(StringUtils.isNotBlank(category.getEntryDes())){
						entries.setDescription(category.getEntryDes());
					}
					
					if(StringUtils.isNotBlank(category.getPaymentMode())){
						entries.setPaymentMode(PaymentModes.getNameByValue(category.getPaymentMode()));
					}
					
					category.setStaticPath(staticPath);
					
					entries.setCreatedDate(CommonUtil.currentDate());
					entries.setCreatedBy(expenses.getCreatedBy());
					entries.setUpdatedDate(CommonUtil.currentDate());
					entries.setExpensesId(expenses);
					entries.setUpdatedBy(expenses.getUpdatedBy());
					
					if(StringUtils.isNotBlank(category.getDbdocFileId())){
						System.out.println("ExpenseEntryId"+category.getDbdocFileId());
						System.out.println("ExpenseEntryId"+category.getDbdocFileIsDeleted());
					}
					
				    //save  mutliple attachments.
					 HashSet<ExpensesEntryDocuments> eeDocList = new HashSet<>();
 	                   if(category.getFileInfo() != null && !category.getFileInfo().isEmpty()) {
				    		for ( FileInfo fileinfo:category.getFileInfo()) {
				    		  ExpensesEntryDocuments eedoc = new ExpensesEntryDocuments();
				    		  eedoc.setExpensesEntriesId(entries);	
				    		  eedoc.setFileURL(fileinfo.getFileURL());
				    		  eedoc.setFileName(fileinfo.getFileName());
				    		  eedoc.setFileContentType(fileinfo.getContentType());
							  FileUploadUtil.moveFile(category.getStaticPath(), fileinfo, "expsesDocuments");
				    		  
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