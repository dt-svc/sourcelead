package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class BillableAndNonBillableHoursUpdator {
		private ComboPooledDataSource comboPooledDataSource;
		ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
		DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
	private static final Logger logger = LoggerFactory.getLogger(BillableAndNonBillableHoursUpdator.class);
	/**
	 * @author Naresh Bolisetty
	 *
	 */
	private  void SubmissionTotalAndBillableNonBillableHoursValidator () {
		Connection conn = null ;
		Statement stmt1 = null;
		try {
			
			DataSource dataSource = comboPooledDataSource();
			conn = dataSource.getConnection();
			int count =0;
				
				 String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".TIMESHEET_SUBMISSIONS ts where ts.STATUS = '1' ";
				 stmt1 =  conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				 ResultSet rs1 = stmt1.executeQuery(sql1);
				 int totalFaults = 0;
					int billableFaults = 0;
					int nonBillableFaults = 0;
					//int totalEffected = 0;
					
				while(rs1.next()){
					 count++;
					 Float weekTotalHours            = 0f;
					 Float weekBillableTotalHours    = 0f;
				  	 Float weekNonBillableTotalHours = 0f;
						
					 Long  submissionId = rs1.getLong("ID");
					 String sql2 = "SELECT * FROM "+dbconfig.getSchema()+".TIMESHEET_ENTRIES te where te.TIMESHEET_SUBMISSION_ID = "+submissionId+"  and (te.STATUS = '0' or te.STATUS = '' or te.STATUS IS NULL) ";
					 Connection	 conn1 = dataSource.getConnection();
					 Statement stmt2 = conn1.createStatement();
					 ResultSet rs2 = stmt2.executeQuery(sql2);
					 while(rs2.next()){
						 Long  projectTaskId = rs2.getLong("PROJECT_TASK");
						 String sql3 = "SELECT * FROM "+dbconfig.getSchema()+".PROJECT_TASKS pt where pt.ID = "+projectTaskId+" ";
						 Connection	 conn3 = dataSource.getConnection();
						 Statement stmt3 = conn3.createStatement();
						 ResultSet rs3 = stmt3.executeQuery(sql3);
						 if(rs3.next() == false){
							 weekBillableTotalHours = getCombinedHours(weekBillableTotalHours,getTotalHours(rs2)); 
						 }
						 else{
							 String billableType = rs3.getString("BILLABLE_TYPE");
							 if(StringUtils.isNotEmpty(billableType) && billableType.equalsIgnoreCase(Constants.TASK_NON_BILLABLE))	{
								 weekNonBillableTotalHours = getCombinedHours(weekNonBillableTotalHours,getTotalHours(rs2));
							 }
							 else{
								 weekBillableTotalHours = getCombinedHours(weekBillableTotalHours,getTotalHours(rs2));
							 }
						 }
						 stmt3.close();
						 conn3.close();
					 }
					
					 weekTotalHours =  getCombinedHours(weekNonBillableTotalHours,weekBillableTotalHours);
						
					 String dbTotalHours = rs1.getString("TOTAL_HOURS");
					 String dbBillableHours = rs1.getString("BILLABLE_HOURS");
					 String dbNonBillableHours = rs1.getString("NON_BILLABLE_HOURS");
					 
		               if(StringUtils.isNotEmpty(dbTotalHours) && Float.parseFloat(dbTotalHours) != weekTotalHours){
		                	totalFaults++;
							rs1.updateString("TOTAL_HOURS", String.format("%.2f", (weekTotalHours)));
							rs1.updateRow();
						}
						if(StringUtils.isNotEmpty(dbBillableHours) && Float.parseFloat(dbBillableHours) != weekBillableTotalHours){
							billableFaults++;
							rs1.updateString("BILLABLE_HOURS", String.format("%.2f", (weekBillableTotalHours)));
							rs1.updateString("NON_BILLABLE_HOURS", String.format("%.2f", (weekNonBillableTotalHours)));
							rs1.updateRow();
						}
						
						if(StringUtils.isNotEmpty(dbNonBillableHours) && Float.parseFloat(dbNonBillableHours) != weekNonBillableTotalHours){
							nonBillableFaults++;
							rs1.updateString("BILLABLE_HOURS", String.format("%.2f", (weekBillableTotalHours)));
							rs1.updateString("NON_BILLABLE_HOURS", String.format("%.2f", (weekNonBillableTotalHours)));
							rs1.updateRow();
						}
					
				      stmt2.close();
				      conn1.close();
				}
				
				logger.info("Total Records ...."+count);
				logger.info("Total Faults Updated ...."+totalFaults);
				logger.info("Billable Faults Updated ...."+billableFaults);
				logger.info("Non-Billable Faults Updated ...."+nonBillableFaults);
				
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
	
	
	private Float getTotalHours(ResultSet rs) throws SQLException{
		 Float weekTotalHours = 0f;
		 
		 String mondayHours = rs.getString("MON_HOURS");
		 if(StringUtils.isNotEmpty(mondayHours)){
			 mondayHours = mondayHours.replaceAll(":", ".");
			 Float monHours = Float.parseFloat(mondayHours);
			 weekTotalHours =  getCombinedHours(weekTotalHours, monHours);
		 }
		 String tuesdayHours = rs.getString("TUE_HOURS");
		 if(StringUtils.isNotEmpty(tuesdayHours)){
			 tuesdayHours = tuesdayHours.replaceAll(":", ".");
			 Float tueHours = Float.parseFloat(tuesdayHours);
			 weekTotalHours = getCombinedHours(weekTotalHours, tueHours);
		 }
		 
		 String weddayHours = rs.getString("WED_HOURS");
		 if(StringUtils.isNotEmpty(weddayHours)){
			 weddayHours = weddayHours.replaceAll(":", ".");
			 Float wedHours = Float.parseFloat(weddayHours);
			 weekTotalHours =getCombinedHours(weekTotalHours, wedHours);
		 }
		 
		 String thursdayHours = rs.getString("THU_HOURS");
		 if(StringUtils.isNotEmpty(thursdayHours)){
			 thursdayHours = thursdayHours.replaceAll(":", ".");
			 Float thursHours = Float.parseFloat(thursdayHours);
			 weekTotalHours =getCombinedHours(weekTotalHours, thursHours);
		 }
		 
		 String friayHours = rs.getString("FRI_HOURS");
		 if(StringUtils.isNotEmpty(friayHours)){
			 friayHours = friayHours.replaceAll(":", ".");
			 Float friHours = Float.parseFloat(friayHours);
			 weekTotalHours =getCombinedHours(weekTotalHours, friHours);
		 }
		 
		 String satdayHours = rs.getString("SAT_HOURS");
		 if(StringUtils.isNotEmpty(satdayHours)){
			 satdayHours = satdayHours.replaceAll(":", ".");
			 Float satHours = Float.parseFloat(satdayHours);
			 weekTotalHours =getCombinedHours(weekTotalHours, satHours);
		 }
		 
		 String sundayHours = rs.getString("SUN_HOURS");
		 if(StringUtils.isNotEmpty(sundayHours)){
			 sundayHours = sundayHours.replaceAll(":", ".");
			 Float sunHours = Float.parseFloat(sundayHours);
			 weekTotalHours =getCombinedHours(weekTotalHours, sunHours);
		 }
		return weekTotalHours;
	}
	
	
	
	public static Float getCombinedHours(Float hours1,Float hours2){
		float cthHours = hours1;
		float cthHoursValue = (int) cthHours;
		float cthMinutesValue = Float.parseFloat(String.format("%.2f", cthHours - cthHoursValue)) ;
		float totalCthMinutes = (cthHoursValue* 60)+(cthMinutesValue*100);

		float wthHours = hours2;
		float wthHoursValue = (int) wthHours;
		float wthMinutesValue = Float.parseFloat(String.format("%.2f", wthHours - wthHoursValue)) ;
		float totalWthMinutes = (wthHoursValue* 60)+(wthMinutesValue*100);

		float totalMinutes = totalCthMinutes+totalWthMinutes;

		float actualHours = (int) (totalMinutes / 60); //since both are ints, you get an int
		float actualMinutes = (totalMinutes % 60)/100;

		String actualTimeString = String.format("%.2f", (actualHours+actualMinutes));
		
		return Float.parseFloat(actualTimeString);
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
		BillableAndNonBillableHoursUpdator updator = new BillableAndNonBillableHoursUpdator();
		updator.SubmissionTotalAndBillableNonBillableHoursValidator();
	}

}