package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class UpdateDroppedHolidaysFromDateAndToDate {
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Naresh Bolisetty
 * this method is used for update worklocations of candidates
 * @throws IOException
 * @throws SQLException
 */
public void updateContactEmail() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			
		     System.out.println("----Updating FROM_DATE AND TO_DATE in Dropped Holidays Started-----");
			 String sql1 = "SELECT UDH.ID As id,HD.FROM_DATE As fromDate ,HD.TO_DATE As toDate FROM "+dbconfig.getSchema()+".USER_DROPPED_HOLIDAYS UDH INNER JOIN (SELECT ID,FROM_DATE,TO_DATE FROM HOLIDAY) AS HD ON HD.ID = UDH.HOLIDAY_ID";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			 while(rs1.next()){
				 Long  udhId = rs1.getLong("id");
				  Date  fromDate = rs1.getDate("fromDate");
				  Date toDate = rs1.getDate("toDate");
				  if(udhId != null && udhId != null && udhId != 0) {
						    String query1 = " update "+dbconfig.getSchema()+".USER_DROPPED_HOLIDAYS set FROM_DATE =  STR_TO_DATE('"+fromDate+"', '%Y-%m-%d')  , TO_DATE = STR_TO_DATE('"+toDate+"', '%Y-%m-%d') where ID = '"+udhId+"' ";
						    java.sql.PreparedStatement pstmt2 = conn.prepareStatement(query1); 
							pstmt2.executeUpdate(); 
							System.out.println("Record with ID "+udhId+" Updated");
				  }
			}
			 System.out.println("----Updating FROM_DATE AND TO_DATE in Dropped Holidays Ends-----");
		
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
	UpdateDroppedHolidaysFromDateAndToDate dump = new UpdateDroppedHolidaysFromDateAndToDate();
	dump.updateContactEmail();
}

}
