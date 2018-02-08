package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.fabric.xmlrpc.base.Array;

public class AddMonthlyLeavesForExistingLeaves {
	private ComboPooledDataSource comboPooledDataSource;
	
/**
 * @author Venkat I
 * this method is adding default projects for org locations
 * @throws IOException
 * @throws SQLException
 */
public void dumpMonthlyLeaves() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			
		
			 String sql1 = "SELECT * FROM sourcelead.USER_LEAVE";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			 
			 List<Leave> leavesList = new ArrayList<>();
			 
			 System.out.println("----Adding Monthly Leaves starts-----");
			 while(rs1.next()){
				 Leave leave = new Leave();
				 leave.setId(rs1.getString("ID"));
				 leave.setFromDate(rs1.getDate("FROM_DATE"));
				 leave.setToDate(rs1.getDate("TO_DATE"));
				 leave.setCreatedBy(rs1.getString("CREATED_BY"));
				 leave.setUpdatedBy(rs1.getString("UPDATED_BY"));
				 leave.setCreatedDate(rs1.getDate("CREATED_DATE"));
				 leave.setUpdatedDate(rs1.getDate("UPDATED_DATE"));
				 leavesList.add(leave);
			 }
			 for(Leave leave : leavesList){
				 
				 
				 Map<String,Integer> monthlyLeaves =  getMonthlyAppliedLeaves(leave);
				 System.out.println(monthlyLeaves);
					 String query1 = " insert into sourcelead.MONTHLY_LEAVES (LEAVE_ID, JANUARY_LEAVES, FEBRUARY_LEAVES, MARCH_LEAVES, APRIL_LEAVES, MAY_LEAVES, JUNE_LEAVES, JULY_LEAVES, AUGUST_LEAVES, SEPTEMBER_LEAVES, OCTOBER_LEAVES, NOVEMBER_LEAVES, DECEMBER_LEAVES, CREATED_DATE, CREATED_BY, UPDATED_DATE, UPDATED_BY, ID )"
								+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					 try {
		    			 PreparedStatement pstmt1 = conn.prepareStatement(query1);
		    			 
		    			 
		    			 pstmt1.setString(18, RandomStringUtils.randomAlphanumeric(16));
			    		 pstmt1.setString(1, leave.getId());
			    		 
			    		 int jan = 0;
		    			 int feb = 0;
		    			 int mar = 0;
		    			 int apr = 0;
		    			 int may = 0;
		    			 int jun = 0;
		    			 int jul = 0;
		    			 int aug = 0;
		    			 int sep = 0;
		    			 int oct = 0;
		    			 int nov = 0;
		    			 int dec = 0;
			    			
			    		 for(String key : monthlyLeaves.keySet()){
			    			 if("January".equalsIgnoreCase(key)){
			    				 jan = monthlyLeaves.get(key);
			    			 }
			    			 if("February".equalsIgnoreCase(key)){
			    				 feb =  monthlyLeaves.get(key);
			    			 }
			    			 if("March".equalsIgnoreCase(key)){
			    				 mar =  monthlyLeaves.get(key);
			    			 }
			    			 if("April".equalsIgnoreCase(key)){
			    				 apr =  monthlyLeaves.get(key);
			    			 }
			    			 if("May".equalsIgnoreCase(key)){
			    				 may =  monthlyLeaves.get(key);
			    			 }
			    			 if("June".equalsIgnoreCase(key)){
			    				 jun =  monthlyLeaves.get(key);
			    			 }
			    			 if("July".equalsIgnoreCase(key)){
			    				 jul =  monthlyLeaves.get(key);
			    			 }
			    			 if("August".equalsIgnoreCase(key)){
			    				 aug =  monthlyLeaves.get(key);
			    			 }
			    			 if("September".equalsIgnoreCase(key)){
			    				 sep =  monthlyLeaves.get(key);
			    			 }
			    			 if("October".equalsIgnoreCase(key)){
			    				 oct =  monthlyLeaves.get(key);
			    			 }
			    			 if("November".equalsIgnoreCase(key)){
			    				 nov =  monthlyLeaves.get(key);
			    			 }
			    			 if("December".equalsIgnoreCase(key)){
			    				 dec =  monthlyLeaves.get(key);
			    			 }
			    		 }
	    				 pstmt1.setInt(2, jan);
	    				 pstmt1.setInt(3, feb);
	    				 pstmt1.setInt(4, mar);
	    				 pstmt1.setInt(5, apr);
	    				 pstmt1.setInt(6, may);
	    				 pstmt1.setInt(7, jun);
	    				 pstmt1.setInt(8, jul);
	    				 pstmt1.setInt(9, aug);
	    				 pstmt1.setInt(10, sep);
	    				 pstmt1.setInt(11, oct);
	    				 pstmt1.setInt(12, nov);
	    				 pstmt1.setInt(13, dec);
			    		 java.sql.Date createdDate = new java.sql.Date(leave.getCreatedDate().getTime());
			    		 java.sql.Date updatedDate = new java.sql.Date(leave.getUpdatedDate().getTime());
			    		 pstmt1.setDate(14, createdDate);
			    		 pstmt1.setString(15, leave.getCreatedBy());
			    		 pstmt1.setDate(16, updatedDate);
			    		 pstmt1.setString(17, leave.getUpdatedBy());
			    		 pstmt1.executeUpdate();	
		    		}catch (Exception e){   
						e.printStackTrace();
					}
			 }
			 
			 
			System.out.println("----Adding Monthly Leaves ends-----");
		
	}catch(Exception e){
		e.printStackTrace();
	}finally {
		try {
			stmt1.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}


/**
 * getting connection
 * @return DataSource
 */ 
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
 
public static void main(String[] args) throws IOException, SQLException {
	AddMonthlyLeavesForExistingLeaves dump = new AddMonthlyLeavesForExistingLeaves();
	dump.dumpMonthlyLeaves();
}


public Map<String,Integer> getMonthlyAppliedLeaves(Leave leave) throws Exception{
	Map<String,Integer> monthlyLeaves = new HashMap<>();
	String months[] = {"January","February","March","April","May","June","July","August","September","October","November","December"};
	
	Calendar fromCal = Calendar.getInstance();
	Calendar toCal = Calendar.getInstance();
	fromCal.setTime(leave.getFromDate());
	int fromMonth = fromCal.get(Calendar.MONTH);
	toCal.setTime(leave.getToDate());
	int toMonth = toCal.get(Calendar.MONTH);
	int noOfDays = 0;
	if(fromMonth == toMonth){
		noOfDays = getNumberOfDaysBetweenTwoDates(leave.getFromDate(),leave.getToDate());
		monthlyLeaves.put(months[fromMonth], noOfDays);
	}else{
		for(int i=1; i <= toMonth;i++){
			if(fromMonth+i == toMonth){
				Date[] dates = new Date[2];
				fromCal.set(Calendar.DATE, fromCal.getActualMaximum(Calendar.DAY_OF_MONTH));
				dates[0] = (Date) fromCal.getTime();
				noOfDays = getNumberOfDaysBetweenTwoDates(leave.getFromDate(),dates[0]);
				monthlyLeaves.put(months[fromMonth], noOfDays);
				
				toCal.set(Calendar.DATE, toCal.getActualMinimum(Calendar.DAY_OF_MONTH));
				dates[1] = (Date) toCal.getTime();
				noOfDays = getNumberOfDaysBetweenTwoDates(dates[1],leave.getToDate());
				monthlyLeaves.put(months[toMonth], noOfDays);
			}else{
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.MONTH,fromMonth+i);
				noOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				monthlyLeaves.put(months[toMonth], noOfDays);
			}
		}
		
	}
	return monthlyLeaves;
}

public int getNumberOfDaysBetweenTwoDates(java.util.Date date, java.util.Date date2) throws ParseException {
	long diff = date2.getTime() - date.getTime();
	int  nuberOfDays = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	return nuberOfDays+1;
}

}
