package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.Period;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ExpensesReportDao;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.service.ExpensesReportService;
import com.bis.sourcelead.util.CalendarUtil;

@Service
public class ExpensesReportServiceImpl implements ExpensesReportService {

	@Autowired
	ExpensesReportDao expensesReportDao;

	@Override
	public List<ExpensesApproverHierarchy> getAllExpensesReportListByLoginUserIdAndOrgLocationId(Long loginUserId,Long loginOrgLocationId) throws Exception {
		return expensesReportDao.getAllExpensesReportListByLoginUserIdAndOrgLocationId(loginUserId, loginOrgLocationId);
	}

	@Override
	public Map<String, Map<String,String>> getYearsAndQuartersComboList() throws JSONException, ParseException {
		 LinkedHashSet<String>  tsrYearsList = CalendarUtil.getYearsListFromCurrentDate(1);
		 Map<String, Map<String,String>> yearsAndQuartersComboList = new LinkedHashMap<String, Map<String,String>>();
		 if(tsrYearsList != null && !tsrYearsList.isEmpty()){
			 for(String year : tsrYearsList){
				 Map<String, String> quarterlyDate = getStartAndEndDate(Integer.parseInt(year));
				 yearsAndQuartersComboList.put(year, quarterlyDate);
			 }
		 }
		 return yearsAndQuartersComboList;
	}
	
	
	private Map<String, String> getStartAndEndDate(int years) {
		      Map<String, String> startEndDate = new LinkedHashMap<>();
		      int count = 1;
		     Calendar YearStartDate = Calendar.getInstance();
		     Calendar YearEndDate = Calendar.getInstance();
			for(int i =0;i<12;i=i+3){
			    Calendar Q1StartDate=Calendar.getInstance();
			    Q1StartDate.set(Calendar.YEAR,years);
			    Q1StartDate.set(Calendar.MONTH,i);
			    Q1StartDate.set(Calendar.DATE,1);
			   
			    if(count==1)
			    YearStartDate = Q1StartDate;
			   
			    Calendar Q1EndDate =Calendar.getInstance();
			    Q1EndDate.set(Calendar.YEAR,years);
			    Q1EndDate.set(Calendar.MONTH, i+2);
			    Q1EndDate.set(Calendar.DATE, Q1EndDate.getActualMaximum(Calendar.DAY_OF_MONTH));
			    
			    if(count==4)
			    YearEndDate = Q1EndDate;
			    
			    String StartDate = Constants.DMY.format(Q1StartDate.getTime());
			    String endDate = Constants.DMY.format(Q1EndDate.getTime());
			    startEndDate.put("Q"+count,StartDate+" TO "+endDate);
			    count++;
		   }
		    startEndDate.put("All",Constants.DMY.format(YearStartDate.getTime())+" TO "+Constants.DMY.format(YearEndDate.getTime()));
			return startEndDate;
		
	}
	
	@Override
	public List<Expenses> getExpensesByProjectMemberIdAndExpenseStartAndEndDate(Long proMembersId,Date startDate, Date endtDate) throws ParseException {
		  return expensesReportDao.getExpensesByProjectMemberIdAndExpenseStartAndEndDate(proMembersId,startDate,endtDate);
	}

	@Override
	public Map<String, String> getmonthStringNamefromStartAndEndDate(Date convertedStartDate, Date convertedEndDate,List<ProjectMembers> projectMembers) {
		Map<String,String> monthStringDate = new LinkedHashMap<>();
		 LocalDate date1 = new LocalDate(convertedStartDate);
		 //LocalDate date2 = new LocalDate(convertedEndDate);
		 DateFormat format =Constants.M;
		 for(int i=0;i<3;i++){
			 Calendar startCal = Calendar.getInstance();  
			 startCal.setTime(date1.toDate());
			 startCal.add(Calendar.MONTH, i);
			 
			 Calendar endCal = Calendar.getInstance();
			 endCal.setTime(date1.toDate());
			 endCal.add(Calendar.MONTH, i);
			 endCal.set(Calendar.DATE, endCal.getActualMaximum(Calendar.DAY_OF_MONTH));
			 
			 	/*List<Long> proMembersIds = new ArrayList<>();
				for(ProjectMembers proMembers:projectMembers){
					List<ExpensesApproverHierarchy> expensesRecoreds = expensesReportDao.getExpensesTotalMontWiseCountBasedOnprojectMemberIdStartDateAndEndDate(convertedStartDate,convertedEndDate,proMembers.getId());
						for( ExpensesApproverHierarchy expRecored:expensesRecoreds){
							 expRecored.getExpenseId().getTotalAmount();
						}
					proMembersIds.add(proMembers.getId());
				}*/
			 monthStringDate.put(format.format(startCal.getTime()),"");
			 System.out.println(startCal.getTime());
			 System.out.println(endCal.getTime());
		 }
		return monthStringDate;
	}

	@Override
	public void expensesReportExportListPdf() {
		
	}

	@Override
	public void expensesReportExportListExcel() {
		
	}

	


	
	
}
