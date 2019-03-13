package com.bis.sourcelead.service.impl;

import java.math.BigDecimal;
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
import com.bis.sourcelead.service.OrgLocationService;
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
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Override
	public Set<ExpensesEntries> prepareExpensesEntriesListByJson(ExpensesDTO addExpensesDTO,HttpServletRequest request,Expenses expenses) throws Exception {
		
		    List<Long> expenseEntryDbIds = null;
		    OrgLocations orgLocation = null;
		    ArrayList<Long> expenseEntryIds = new ArrayList<>();
		    if(null != expenses && null != expenses.getId()){
		    	expenseEntryDbIds = expensesEntriesDao.getExpensesEntryIdsByExpenseId(expenses.getId());
		    }
			 orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 
			/*if(orgLocation == null && addExpensesDTO.getLocationCode() != null ){
			 orgLocation = orgLocationService.getOrgLocationsByLocationCode(addExpensesDTO.getLocationCode());
			}*/
			
		    HashSet<ExpensesEntries> expensesEntriesList = new HashSet<ExpensesEntries>(); 
		    for(ExpensesEntriesDTO entryCategory:addExpensesDTO.getExpenseEntry()){
			    if(StringUtils.isNotEmpty(entryCategory.getCategoryType())){
						ExpensesEntries  entries  = new ExpensesEntries();
						if(entryCategory.getId() != null){
							entries.setId(entryCategory.getId());
						}
						ExpensesCategory ec = new ExpensesCategory();
						if(entryCategory.getCategoryTypeId() > 0){
							ec.setId(entryCategory.getCategoryTypeId());
						}
						else{
							ec.setId(Long.parseLong(entryCategory.getCategoryType().split(",")[0]));
						}
						entries.setExpensesCategoryId(ec);
						
						if(StringUtils.isNotBlank(entryCategory.getDateOccurred())){
							Date date = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),entryCategory.getDateOccurred());
							entries.setExpenseDate(date);
						}
						
						if(StringUtils.isNotBlank(entryCategory.getReceiptIssuedBy())){
							entries.setReceiptIssuedBy(entryCategory.getReceiptIssuedBy());
						}
						

						//Countries country = countriesService.getCountriesByCountryName(orgLocation.getCountry());
						/*String currencyType = null;

						/*Countries country = countriesService.getCountriesByCountryName(orgLocation.getCountry());
						String currencyType = null;

						if(country!= null &&  country.getCurrency()!= null && !country.getCurrency().equalsIgnoreCase(entryCategory.getCategoryAmountType())){
							currencyType = country.getCurrency();
						}
						
						if(StringUtils.isEmpty(entryCategory.getCategoryAmountType()) || (StringUtils.isNotEmpty(currencyType) && !currencyType.equalsIgnoreCase(entryCategory.getCategoryAmountType()))){
							entries.setCurrencyType(entryCategory.getCategoryAmountType());
						}else {
							entries.setCurrencyType(currencyType);
						}*/

						entries.setCurrencyType(entryCategory.getCategoryAmountType());
						

						entries.setSubmittedamount(new BigDecimal(entryCategory.getCategoryAmount()));
						
						if(StringUtils.isNotBlank(entryCategory.getCategoryApprovedAmount())){
							entries.setAmount(new BigDecimal(entryCategory.getCategoryApprovedAmount()));
							entries.setAmountchangereason(null);
						}else {
							entries.setAmount(new BigDecimal(entryCategory.getCategoryAmount()));
							entries.setAmountchangereason(null);
						}
						
						
					if(StringUtils.isNotBlank(entryCategory.getEntryDescription())){
						entries.setDescription(entryCategory.getEntryDescription());
					}
					
					
					if (StringUtils.isNotBlank(entryCategory.getPaidBy())){
						entries.setPaidBy(entryCategory.getPaidBy());
					 }
					
					if(StringUtils.isNotBlank(entryCategory.getPaymentMode())){
						entries.setPaymentMode(PaymentModes.getNameByValue(entryCategory.getPaymentMode()));
					}
					
					//category.setStaticPath(staticPath);
					
					entries.setCreatedDate(CommonUtil.currentDate());
					entries.setCreatedBy(expenses.getCreatedBy());
					entries.setUpdatedDate(CommonUtil.currentDate());
					entries.setExpensesId(expenses);
					entries.setUpdatedBy(expenses.getUpdatedBy());
					if(entryCategory.getBillable() != null) {
						entries.setBillable(entryCategory.getBillable());
					}else {
						entries.setBillable(false);
					}
					
					
					
					
					
				    //save  mutliple attachments.
					 HashSet<ExpensesEntryDocuments> eeDocList = new HashSet<>();
 	                   if(entryCategory.getFileInfo() != null && !entryCategory.getFileInfo().isEmpty()) {
				    		for ( FileInfo fileinfo:entryCategory.getFileInfo()) {
				    			if(StringUtils.isEmpty(fileinfo.getFileDbId()) || (fileinfo.getFileDbId().contains("null")))	{
				    		      ExpensesEntryDocuments eedoc = new ExpensesEntryDocuments();
				    		  	  FileUploadUtil.moveFile(staticPath, fileinfo, "expsesDocuments");
					    		  eedoc.setExpensesEntriesId(entries);	
					    		  eedoc.setFileURL(fileinfo.getFileURL());
					    		  eedoc.setFileName(fileinfo.getFileName());
					    		  eedoc.setFileContentType(fileinfo.getContentType());
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
		    if(expenseEntryDbIds != null && !expenseEntryDbIds.isEmpty()){
		    	expenseEntryDbIds.removeAll(expenseEntryIds);
		    	if(expenseEntryDbIds != null && !expenseEntryDbIds.isEmpty()){
		    		expensesEntriesDao.removeAllExpenseEntries(expenseEntryDbIds);
		    	}
		    }
		    if(addExpensesDTO.getRemovedDocumentIds() != null && ! addExpensesDTO.getRemovedDocumentIds().isEmpty()){
		    	for(Long id :addExpensesDTO.getRemovedDocumentIds()) {
		    		expensesEntryDocumentsDao.removeExpensesEntryDocouments(id);
		    	}
		    }
		   
		   return expensesEntriesList;
		    		  
	}

	@Override
	public List<ExpensesEntries> getExpensesEntriesByExpenseId(long expenseId) {
		return expensesEntriesDao.getExpensesEntriesByExpenseId(expenseId);
	}

	@Override
	public int dumpExpenceCategoryAmountToOldRecords() {
		return expensesEntriesDao.dumpExpenceCategoryAmountToOldRecords();
	}

	@Override
	public int changeDocumentNameWithWhiteSpacesToUnderScore(String staticPath) {
		try {
			return expensesEntriesDao.changeDocumentNameWithWhiteSpacesToUnderScore(staticPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	
}