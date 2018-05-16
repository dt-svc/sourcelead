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

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class CloseRequirementsOneMonthEarlier {
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Venkat I
 * this method is using for update the RequirmentUUID	
 * @throws IOException
 * @throws SQLException
 */
public void closeRequirements() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		 Calendar cal = Calendar.getInstance();
		 cal.add(Calendar.MONTH, -1);
		 String earlierDate = formatter.format(cal.getTime());
		 
		 SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		 Date date = new Date();
 	     String currentDate = formatter1.format(date);
		
			 String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".REQUIREMENT requirement where requirement.CREATED_DATE < '"+earlierDate+"' ";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			
			 System.out.println("----Closing One month before Requirements Starts-----");
			 while(rs1.next()){
				 Long  requirementId = rs1.getLong("ID");
				 String  currentStatus = "DRAFT";
				 if(null != rs1.getString("REQUIREMENT_SUBMISSION_STATUS")) {
					 currentStatus = rs1.getString("REQUIREMENT_SUBMISSION_STATUS");
				 }
				 
				 if(currentStatus.equalsIgnoreCase("OPEN") || currentStatus.equalsIgnoreCase("NEW") 
						 || currentStatus.equalsIgnoreCase("DRAFT") || currentStatus.equalsIgnoreCase("SUBMITTED") ){
					 String query = " update "+dbconfig.getSchema()+".REQUIREMENT set REQUIREMENT_SUBMISSION_STATUS = 'CLOSED', REQUIREMENT_SUBMISSION_PREVIOUS_STATUS = ?,UPDATED_DATE = ? where ID = ? ";
					 java.sql.PreparedStatement pstmt = conn.prepareStatement(query); 
					 pstmt.setString(1, currentStatus);
					 pstmt.setString(2, currentDate);
					 pstmt.setLong(3, requirementId);
			         pstmt.executeUpdate(); 
				 }
				
			}
			System.out.println("----Closing One month before Requirements End-----");
		
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
	CloseRequirementsOneMonthEarlier dump = new CloseRequirementsOneMonthEarlier();
	dump.closeRequirements();
}

}
