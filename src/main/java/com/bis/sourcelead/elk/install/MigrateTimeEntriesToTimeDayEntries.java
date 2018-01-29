package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class MigrateTimeEntriesToTimeDayEntries {
	private ComboPooledDataSource comboPooledDataSource;
	
	private void migrateTimesheetEntriesData() {
		DataSource dataSource = comboPooledDataSource();
		try (Connection con = dataSource.getConnection()) {
			Statement stmt = con.createStatement();
			Statement stmt1 = con.createStatement();
			Statement stmt2 = con.createStatement();
			
			/*Fetching distinct date using submission id from time-sheet submission table*/
			String fetchDates = "SELECT * FROM sourcelead.TIMESHEET_SUBMISSIONS";
			ResultSet resultSet = stmt.executeQuery(fetchDates);
			while(resultSet.next()){
				 Long submissionId = resultSet.getLong("ID");
				 String  startDate = resultSet.getString("START_DATE");
				 String  endDate = resultSet.getString("END_DATE");
				 LinkedHashMap<String,java.sql.Date> weeekDateList  = calculateDate(startDate,endDate);
					/*Fetching all data of time-sheet Entries*/ 
				 	String timesheetEntries = "SELECT * FROM sourcelead.TIMESHEET_ENTRIES te where te.TIMESHEET_SUBMISSION_ID = '"+submissionId+"'";
					 ResultSet timesheetEntriesResultSet = stmt1.executeQuery(timesheetEntries);
					 while(timesheetEntriesResultSet.next()){
						 Long  projectTaskId = timesheetEntriesResultSet.getLong("PROJECT_TASK");
						 if(projectTaskId != null && StringUtils.isNotEmpty(projectTaskId.toString())) {
						 Date  createdDate = timesheetEntriesResultSet.getDate("CREATED_DATE");
						 String createdBy = timesheetEntriesResultSet.getString("CREATED_BY");
						 Date  updatedDate = timesheetEntriesResultSet.getDate("UPDATED_DATE");
						 String updatedBy = timesheetEntriesResultSet.getString("UPDATED_BY");
						 	String Type = "SELECT BILLABLE_TYPE FROM sourcelead.PROJECT_TASKS where ID = "+projectTaskId+"";
						 	ResultSet ptResultSet = stmt2.executeQuery(Type);
						 	while(ptResultSet.next()){
						 	String  taskType = ptResultSet.getString("BILLABLE_TYPE");
						 	for(String dayOftheDate : weeekDateList.keySet()) {
						 		java.sql.Date date = null;
						 		String billableHours= null;
						 		String nonbillableHours= null;
						 		String comments = null;
						 		String  Hours = null;
						 		String taskTypeSave = null;
						 		if(dayOftheDate.equalsIgnoreCase("Mon")) {
						 					date = weeekDateList.get(dayOftheDate);
						 					Hours = timesheetEntriesResultSet.getString("MON_HOURS");
						 					String  moncomments = timesheetEntriesResultSet.getString("MON_COMMENTS");
						 					if(taskType.equalsIgnoreCase("Billable")) {
						 						billableHours = Hours;	
						 					}else if(taskType.equalsIgnoreCase("Non-Billable")) {
						 						nonbillableHours= Hours;
						 					}
						 					if(StringUtils.isNotEmpty(moncomments) && StringUtils.isNotBlank(moncomments)) {
						 						comments = moncomments;
						 					}
						 					if(StringUtils.isNotEmpty(taskType) && StringUtils.isNotBlank(taskType)) {
						 						taskTypeSave = taskType;
						 					}
						 			
						 		}
						 		if(dayOftheDate.equalsIgnoreCase("Tue")) {
				 					date = weeekDateList.get(dayOftheDate);
				 					Hours = timesheetEntriesResultSet.getString("TUE_HOURS");
				 					String  tuecomments = timesheetEntriesResultSet.getString("TUE_COMMENTS");
				 					if(taskType.equalsIgnoreCase("Billable")) {
				 						billableHours = Hours;	
				 					}else if(taskType.equalsIgnoreCase("Non-Billable")) {
				 						nonbillableHours= Hours;
				 					}
				 					if(StringUtils.isNotEmpty(tuecomments) && StringUtils.isNotBlank(tuecomments)) {
				 						comments = tuecomments;
				 					}
				 					if(StringUtils.isNotEmpty(taskType) && StringUtils.isNotBlank(taskType)) {
				 						taskTypeSave = taskType;
				 					}
				 			
						 		}
						 		if(dayOftheDate.equalsIgnoreCase("Wed")) {
				 					date = weeekDateList.get(dayOftheDate);
				 					Hours = timesheetEntriesResultSet.getString("WED_HOURS");
				 					String  wedcomments = timesheetEntriesResultSet.getString("WED_COMMENTS");
				 					if(taskType.equalsIgnoreCase("Billable")) {
				 						billableHours = Hours;	
				 					}else if(taskType.equalsIgnoreCase("Non-Billable")) {
				 						nonbillableHours= Hours;
				 					}
				 					if(StringUtils.isNotEmpty(wedcomments) && StringUtils.isNotBlank(wedcomments)) {
				 						comments = wedcomments;
				 					}
				 					if(StringUtils.isNotEmpty(taskType) && StringUtils.isNotBlank(taskType)) {
				 						taskTypeSave = taskType;
				 					}
				 			
						 		}
						 		if(dayOftheDate.equalsIgnoreCase("Thu")) {
				 					date = weeekDateList.get(dayOftheDate);
				 					Hours = timesheetEntriesResultSet.getString("THU_HOURS");
				 					String  thucomments = timesheetEntriesResultSet.getString("THU_COMMENTS");
				 					if(taskType.equalsIgnoreCase("Billable")) {
				 						billableHours = Hours;	
				 					}else if(taskType.equalsIgnoreCase("Non-Billable")) {
				 						nonbillableHours= Hours;
				 					}
				 					if(StringUtils.isNotEmpty(thucomments) && StringUtils.isNotBlank(thucomments)) {
				 						comments = thucomments;
				 					}
				 					if(StringUtils.isNotEmpty(taskType) && StringUtils.isNotBlank(taskType)) {
				 						taskTypeSave = taskType;
				 					}
				 			
						 		}
						 		if(dayOftheDate.equalsIgnoreCase("Fri")) {
				 					date = weeekDateList.get(dayOftheDate);
				 					Hours = timesheetEntriesResultSet.getString("FRI_HOURS");
				 					String  fricomments = timesheetEntriesResultSet.getString("FRI_COMMENTS");
				 					if(taskType.equalsIgnoreCase("Billable")) {
				 						billableHours = Hours;	
				 					}else if(taskType.equalsIgnoreCase("Non-Billable")) {
				 						nonbillableHours= Hours;
				 					}
				 					if(StringUtils.isNotEmpty(fricomments) && StringUtils.isNotBlank(fricomments)) {
				 						comments = fricomments;
				 					}
				 					if(StringUtils.isNotEmpty(taskType) && StringUtils.isNotBlank(taskType)) {
				 						taskTypeSave = taskType;
				 					}
				 			
						 		}
						 		if(dayOftheDate.equalsIgnoreCase("Sat")) {
				 					date = weeekDateList.get(dayOftheDate);
				 					Hours = timesheetEntriesResultSet.getString("SAT_HOURS");
				 					String  satcomments = timesheetEntriesResultSet.getString("SAT_COMMENTS");
				 					if(taskType.equalsIgnoreCase("Billable")) {
				 						billableHours = Hours;	
				 					}else if(taskType.equalsIgnoreCase("Non-Billable")) {
				 						nonbillableHours= Hours;
				 					}
				 					if(StringUtils.isNotEmpty(satcomments) && StringUtils.isNotBlank(satcomments)) {
				 						comments = satcomments;
				 					}
				 					if(StringUtils.isNotEmpty(taskType) && StringUtils.isNotBlank(taskType)) {
				 						taskTypeSave = taskType;
				 					}
				 			
						 		}
						 		if(dayOftheDate.equalsIgnoreCase("Sun")) {
				 					date = weeekDateList.get(dayOftheDate);
				 					Hours = timesheetEntriesResultSet.getString("SUN_HOURS");
				 					String  suncomments = timesheetEntriesResultSet.getString("SUN_COMMENTS");
				 					if(taskType.equalsIgnoreCase("Billable")) {
				 						billableHours = Hours;	
				 					}else if(taskType.equalsIgnoreCase("Non-Billable")) {
				 						nonbillableHours= Hours;
				 					}
				 					if(StringUtils.isNotEmpty(suncomments) && StringUtils.isNotBlank(suncomments)) {
				 						comments = suncomments;
				 					}
				 					if(StringUtils.isNotEmpty(taskType) && StringUtils.isNotBlank(taskType)) {
				 						taskTypeSave = taskType;
				 					}
				 			
						 		}
						 		String query4 = " update sourcelead.TIMESHEET_DAY_ENTRIES (SUBMISSION_ID, PROJECT_TASK_ID, TIMESHEET_DATE, TASK_TYPE, BILLABLE_HOURS, NONBILLABLE_HOURS,TOTALHOURS, COMMENTS, CREATED_DATE,CREATED_BY,UPDATED_DATE,UPDATED_BY)"
										+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,?)";
					    		
					    		try {
					    			
					    			 PreparedStatement pstmt4 = con.prepareStatement(query4);
						    		 pstmt4.setLong(1, submissionId);
						    		 pstmt4.setLong(2, projectTaskId);
						    		 pstmt4.setDate(3, date);
						    		 pstmt4.setString(4, taskTypeSave);
						    		 pstmt4.setString(5, billableHours);
						    		 pstmt4.setString(6, nonbillableHours);
						    		 pstmt4.setString(7, Hours);
						    		 pstmt4.setString(8, comments);
						    		 pstmt4.setDate(9,(java.sql.Date) createdDate);
						    		 pstmt4.setString(10,createdBy);
						    		 pstmt4.setDate(11,(java.sql.Date) updatedDate);
						    		 pstmt4.setString(12,updatedBy);
						    		 pstmt4.executeUpdate();	
						    		 
					    		}catch (Exception e){   
									e.printStackTrace();
								}
						 		}
						 	}
					 	}	
						 		 	
					 }
				 
			}
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static Date getNextDate(Date  curDate) throws ParseException {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(curDate);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		return calendar.getTime(); 
	}
	
	 private LinkedHashMap<String,java.sql.Date> calculateDate(String startDate, String endDate) throws ParseException {
		 LinkedHashMap<String,java.sql.Date> dateList = new LinkedHashMap<>();
		 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		 Date start = sdf1.parse(startDate);
		 Date end = sdf1.parse(endDate);
		 while(start.before(end) || start.equals(end)){
		     SimpleDateFormat sf = new SimpleDateFormat("E"); 
			 if(sf.format(start).equalsIgnoreCase("Mon")) {
				 dateList.put("Mon",new java.sql.Date(start.getTime())); 
			 }else if(sf.format(start).equalsIgnoreCase("Tue")) {
				 dateList.put("Tue",new java.sql.Date(start.getTime()));
			 }else if(sf.format(start).equalsIgnoreCase("Wed")) {
				 dateList.put("Wed",new java.sql.Date(start.getTime()));
			 }else if(sf.format(start).equalsIgnoreCase("Thu")) {
				 dateList.put("Thu",new java.sql.Date(start.getTime()));
			 }else if(sf.format(start).equalsIgnoreCase("Fri")) {
				 dateList.put("Fri",new java.sql.Date(start.getTime()));
			 }else if(sf.format(start).equalsIgnoreCase("Sat")) {
				 dateList.put("Sat",new java.sql.Date(start.getTime()));
			 }else if(sf.format(start).equalsIgnoreCase("Sun")) {
				 dateList.put("Sun",new java.sql.Date(start.getTime()));
			 }
			 start = getNextDate(end);
	    }
		return dateList;
	}




	private DataSource comboPooledDataSource() {
	        @SuppressWarnings("resource")
	        ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	        DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);

	        this.comboPooledDataSource = new ComboPooledDataSource();
	        try {
	            comboPooledDataSource.setDriverClass(dbconfig.getDriverClass());
	        } catch (PropertyVetoException e) {
	            throw new RuntimeException(e);
	        }
	        comboPooledDataSource.setJdbcUrl(dbconfig.getJdbcUrl());
	        comboPooledDataSource.setUser(dbconfig.getUser());
	        comboPooledDataSource.setPassword(dbconfig.getPassword());
	        return comboPooledDataSource;
	    }
	 
	public static void main(String... args){
		MigrateTimeEntriesToTimeDayEntries timeDayEntries = new MigrateTimeEntriesToTimeDayEntries();
		timeDayEntries.migrateTimesheetEntriesData();
	}
	
	
	
	
	
	
}