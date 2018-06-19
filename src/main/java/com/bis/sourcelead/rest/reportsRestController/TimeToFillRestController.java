package com.bis.sourcelead.rest.reportsRestController;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.reports.service.TimeToFillService;
import com.bis.sourcelead.reportsDTO.JobFilterDTO;
import com.bis.sourcelead.reportsDTO.TimeToFillDTO;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class TimeToFillRestController {
	
	@Autowired
	TimeToFillService timeToFillService;
	
	@RequestMapping(value = "/getTimeToFillDetails", method = RequestMethod.GET)
	public List<TimeToFillDTO> getTimeToFillDetailsForReports(JobFilterDTO jobFilterDTO, HttpServletRequest request) throws Exception {
		
		List<TimeToFillDTO> timeToFillList = new ArrayList<>();
		int year = 0;
		if(StringUtils.isNotBlank(jobFilterDTO.getYear())) {
			 year = Integer.parseInt(jobFilterDTO.getYear());
		}else {
			 year = Calendar.getInstance().get(Calendar.YEAR);
		}
		DecimalFormat df = new DecimalFormat("#.00");
		List<Long> jobIdList = new ArrayList<>();
		
		if(StringUtils.isNotBlank(jobFilterDTO.getJobIds())) {
			String[] jobIds = jobFilterDTO.getJobIds().split(",");
			for(int i=0; i<jobIds.length; i++) {
				jobIdList.add(Long.valueOf(jobIds[i]));
			}
		}else {
			jobIdList = (List<Long>) SourceLeadSessionManager.getSessionAttribute(request, "requirementIdsList");
		}
		
		
		for(int i=0; i<12; i++) {
			
			  Calendar Q1StartDate=Calendar.getInstance();
			  Q1StartDate.set(Calendar.YEAR,year);
		      Q1StartDate.set(Calendar.MONTH,i);
		      Q1StartDate.set(Calendar.DATE,1);
			   
			  Calendar Q1EndDate =Calendar.getInstance();
			  Q1EndDate.set(Calendar.YEAR,year);
			  Q1EndDate.set(Calendar.MONTH, i);
			  Q1EndDate.set(Calendar.DATE, Q1EndDate.getActualMaximum(Calendar.DAY_OF_MONTH));
			  java.sql.Date fromDate = new java.sql.Date(Q1StartDate.getTime().getTime());
			  java.sql.Date toDate = new java.sql.Date(Q1EndDate.getTime().getTime());
			  Date[] searchDates = new Date[2];
    	      searchDates[0] = fromDate;
    	      searchDates[1] = toDate;
    	      
    	      Set<Long> requirementIds = new HashSet<>();
    	      Long timeToFillDays = 0L;
    	      TimeToFillDTO timeToFillDTO = new TimeToFillDTO();
    	      
    	      String month = Constants.Y_M.format(Q1EndDate.getTime());
    	      String monthStr = Constants.M.format(Q1EndDate.getTime());
    	      
    	  	  List<RequirementSubmissionHistory> offerAcceptedList = timeToFillService.getRequirementHistoryByJobIdsAndStageForTimeToFill(jobIdList, searchDates);
    	      
    	      
        	      if(offerAcceptedList != null && offerAcceptedList.size() > 0) {
        	    	  for(RequirementSubmissionHistory rh : offerAcceptedList) {
        	    		  RequirementSubmissionHistory rsHisory = timeToFillService.getRequirementHistoryBySubmissionIdsAndStage(rh.getSubmissionId().getId(), "SUBMISSION");
        	    		  requirementIds.add(rsHisory.getSubmissionId().getRequirement().getId());
        	    		  
        	    		  Long dayDiff = CalendarUtil.getNumberOfDaysForReports(rsHisory.getCreatedDate(), rh.getCreatedDate());
        	    		  timeToFillDays = timeToFillDays+dayDiff;
        	    	  }
        	      }
        	    //  timeToFillDays
        	      Double timeToFillCount = 0d;
        	      if(requirementIds.size() > 0) {
        	    	  timeToFillDays = (timeToFillDays/offerAcceptedList.size());
        	    	  timeToFillCount = (double) ((double)timeToFillDays/((double)requirementIds.size()));
        	      }else {
        	    	  timeToFillDTO.setFillCount(String.valueOf(timeToFillCount));
        	      }
        	      
        	      timeToFillDTO.setMonthStr(monthStr);
        	      timeToFillDTO.setFillCount(df.format(timeToFillCount));
        	      timeToFillDTO.setFillCountValue(timeToFillCount);
        	      timeToFillDTO.setMonth(month);
        	      timeToFillList.add(timeToFillDTO);
    			  
    		}
    		
    		return timeToFillList;
    	}
	
	
	
	
	/*@RequestMapping(value = "/getTimeToFillDetails", method = RequestMethod.GET)
	public List<TimeToFillDTO> getTimeToFillDetails(JobFilterDTO jobFilterDTO, HttpServletRequest request) throws Exception {
		
		List<TimeToFillDTO> timeToFillList = new ArrayList<>();
		int year = 0;
		if(StringUtils.isNotBlank(jobFilterDTO.getYear())) {
			 year = Integer.parseInt(jobFilterDTO.getYear());
		}else {
			 year = Calendar.getInstance().get(Calendar.YEAR);
		}
		DecimalFormat df = new DecimalFormat("#.00");
		List<Long> jobIdList = new ArrayList<>();
		
		if(StringUtils.isNotBlank(jobFilterDTO.getJobIds())) {
			String[] jobIds = jobFilterDTO.getJobIds().split(",");
			for(int i=0; i<jobIds.length; i++) {
				jobIdList.add(Long.valueOf(jobIds[i]));
			}
		}else {
			jobIdList = (List<Long>) SourceLeadSessionManager.getSessionAttribute(request, "requirementIdsList");
		}
		
		
		for(int i=0; i<12; i++) {
			
			  Calendar Q1StartDate=Calendar.getInstance();
			  Q1StartDate.set(Calendar.YEAR,year);
		      Q1StartDate.set(Calendar.MONTH,i);
		      Q1StartDate.set(Calendar.DATE,1);
			   
			  Calendar Q1EndDate =Calendar.getInstance();
			  Q1EndDate.set(Calendar.YEAR,year);
			  Q1EndDate.set(Calendar.MONTH, i);
			  Q1EndDate.set(Calendar.DATE, Q1EndDate.getActualMaximum(Calendar.DAY_OF_MONTH));
			  java.sql.Date fromDate = new java.sql.Date(Q1StartDate.getTime().getTime());
			  java.sql.Date toDate = new java.sql.Date(Q1EndDate.getTime().getTime());
			  Date[] searchDates = new Date[2];
    	      searchDates[0] = fromDate;
    	      searchDates[1] = toDate;
    	      
    	      Set<Long> requirementIds = new HashSet<>();
    	      Long timeToFillDays = 0L;
    	      TimeToFillDTO timeToFillDTO = new TimeToFillDTO();
    	      
    	      String month = Constants.Y_M.format(Q1EndDate.getTime());
    	      String monthStr = Constants.M.format(Q1EndDate.getTime());
    	      
    	      List<RequirementSubmissionHistory> offerAcceptedList = timeToFillService.getRequirementHistoryByJobIdsAndStage(jobIdList, searchDates, "OFFERACCEPTED");
    	   //   System.out.println(" searchDates[0] ="+ searchDates[0]+" "+"searchDates[1]="+searchDates[1]+" "+"jobIdList ="+jobIdList);
    	      
    	  //    System.out.println("offerAcceptedList=="+offerAcceptedList.size());
    	      if(offerAcceptedList != null && offerAcceptedList.size() > 0) {
    	    	  for(RequirementSubmissionHistory rh : offerAcceptedList) {
    	    		  RequirementSubmissionHistory rsHisory = timeToFillService.getRequirementHistoryBySubmissionIdsAndStage(rh.getSubmissionId().getId(), "SUBMISSION");
    	    		  System.out.println("rh.getSubmissionId().getId()="+rh.getSubmissionId());
    	    		  requirementIds.add(rsHisory.getSubmissionId().getRequirement().getId());
    	    		  
    	    		  System.out.println("rsHisory.getCreatedDate()  ====>"+rsHisory.getCreatedDate()); 
    	    		  System.out.println("rh.getCreatedDate()  ====>"+rh.getCreatedDate());
    	    		  Long dayDiff = CalendarUtil.getNumberOfDaysForReports(rsHisory.getCreatedDate(), rh.getCreatedDate());
    	    		  System.out.println("dayDiff  ====>"+dayDiff);
    	    		  timeToFillDays = timeToFillDays+dayDiff;
    	    		  System.out.println("timeToFillDays  ====>"+timeToFillDays);
    	    	  }
    	      }
    	    //  timeToFillDays
    	      Double timeToFillCount = 0d;
    	      System.out.println("requirementIds ==> "+requirementIds.size());
    	      if(requirementIds.size() > 0) {
    	    	  timeToFillDays = (timeToFillDays/offerAcceptedList.size());
    	    	  System.out.println("timeToFillDays ==> "+timeToFillDays);
    	    	  timeToFillCount = (double) ((double)timeToFillDays/((double)requirementIds.size()));
    	      }else {
    	    	  timeToFillDTO.setFillCount(String.valueOf(timeToFillCount));
    	      }
    	      
    	      timeToFillDTO.setMonthStr(monthStr);
    	      timeToFillDTO.setFillCount(df.format(timeToFillCount));
    	      timeToFillDTO.setFillCountValue(timeToFillCount);
    	      timeToFillDTO.setMonth(month);
    	      timeToFillList.add(timeToFillDTO);
			  
		}
		
		return timeToFillList;
	}*/

}
