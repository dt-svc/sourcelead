package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.util.CalendarUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class AddWeekStartDateToProjects {
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Naresh Bolisetty
 *
 */
public void addWeekStartDateToProjects() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			 String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".PROJECTS";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			 while(rs1.next()){
				 Long  id = rs1.getLong("ID");
				 Date  startDate = rs1.getDate("START_DATE");
				 if(startDate == null)
					 continue;
				 Date  weekStartDate =  getBiweeklyStartMonDayDateByUsingProjectStartDateAndCurrentDate(startDate);
				 String query2 = " update "+dbconfig.getSchema()+".PROJECTS set WEEK_START_DATE = ? where ID = ? ";
		    		java.sql.PreparedStatement pstmt3 = conn.prepareStatement(query2); 
		    		pstmt3.setDate(1, new java.sql.Date(weekStartDate.getTime()));
		    		pstmt3.setLong(2,id);
		    		pstmt3.executeUpdate(); 
				
			}
			System.out.println("----WEEK_START_DATE Added to all projects successfully-----");
		
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

public static Calendar getCurrentWeekDayByCalendar(Calendar calendar,String day_Of_Week) {
	Calendar cal = Calendar.getInstance(TimeZone.getDefault());
	int day_offset = getDayOffset(day_Of_Week);
	try {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String nowStr = sdf.format(calendar.getTime());
		Date nowDate = sdf.parse(nowStr);
		cal.setTime(nowDate);
		if(day_offset == 8){
			cal.add(Calendar.DATE, 7);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		}
		else{
			cal.set(Calendar.DAY_OF_WEEK, day_offset);
		}

		return cal;
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
}

protected static int getDayOffset(String day_Of_Week){
	int day_offset = 0;
	switch (day_Of_Week) {
	case "MON":  day_offset = Calendar.MONDAY;
	break;
	case "TUE":  day_offset = Calendar.TUESDAY;
	break;
	case "WED":  day_offset = Calendar.WEDNESDAY;
	break;
	case "THU":  day_offset = Calendar.THURSDAY;
	break;
	case "FRI":  day_offset = Calendar.FRIDAY;
	break;
	case "SAT":  day_offset = Calendar.SATURDAY;
	break;
	case "SUN":  day_offset = Calendar.SATURDAY+1;
	break;
	default:   day_offset = Calendar.SUNDAY;
	break;
	}
	return day_offset;
}

public Date getBiweeklyStartMonDayDateByUsingProjectStartDateAndCurrentDate(Date projectStartDate) throws Exception{
	Date biWeeklyStartMondayDate = null;
	Calendar projCal = Calendar.getInstance(TimeZone.getDefault());
	projCal.setLenient(false);
	projCal.setTime(projectStartDate);
	Calendar projectStartMonday = getCurrentWeekDayByCalendar(projCal, "MON");
	
	Calendar cal = Calendar.getInstance(TimeZone.getDefault());
	cal.setLenient(false);
	cal.setTime(projectStartDate);
	Calendar currentMonday = getCurrentWeekDayByCalendar(cal, "MON");
	if(projectStartMonday.before(currentMonday)){
		while(projectStartMonday.before(currentMonday)){
			projectStartMonday = CalendarUtil.getDateIncrementByincrement(projectStartMonday,14);
		}
		biWeeklyStartMondayDate = projectStartMonday.getTime();
	}
	else{
		biWeeklyStartMondayDate = projectStartMonday.getTime();
	}
	
	return biWeeklyStartMondayDate;
}

/**
 * getting connection
 * @return DataSource
 */ 
private DataSource comboPooledDataSource() {
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
	AddWeekStartDateToProjects dump = new AddWeekStartDateToProjects();
	dump.addWeekStartDateToProjects();
}

}
