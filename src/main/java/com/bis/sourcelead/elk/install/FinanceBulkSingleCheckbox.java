package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class FinanceBulkSingleCheckbox {
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * 
 * this method is using for update IsClientJob null values
 * @throws IOException
 * @throws SQLException
 */
public void updateOrgrelationCheckBoxesForFinance() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
		
		String sql2 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_RELATIONS";
		 stmt1 = conn.createStatement();
		 ResultSet rs = stmt1.executeQuery(sql2);
		 System.out.println("Updating Bulk or Single Checkboxs In Orgrelation Starts.....");
		 
		 while(rs.next()){
			 String query1 = " update "+dbconfig.getSchema()+".ORG_RELATIONS set GENERATE_INVOICE ='Bulk', GENERATE_BILL ='Bulk' where STATUS = 'ACTIVE'";
		    	java.sql.PreparedStatement pstmt2 = conn.prepareStatement(query1);
		    	pstmt2.executeUpdate(); 
		    	System.out.println("Updating.....");
		    	
		 }
		 System.out.println("Updating Bulk or Single Checkboxs In Orgrelation Ends.....");
		
		
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
	FinanceBulkSingleCheckbox dump = new FinanceBulkSingleCheckbox();
	dump.updateOrgrelationCheckBoxesForFinance();
}

}
