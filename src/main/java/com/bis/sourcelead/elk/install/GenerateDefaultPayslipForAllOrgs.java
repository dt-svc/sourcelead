package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class GenerateDefaultPayslipForAllOrgs {

	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
	
/**
 * @author
 * this method is using for generate Payroll Payheads
 * @throws IOException
 * @throws SQLException
 */
public void generatePayheadsByOrg() throws IOException, SQLException {

	Connection conn = null ;
	PreparedStatement stmt = null;
		try {
			DataSource dataSource = comboPooledDataSource();
			conn = dataSource.getConnection();
			
				String trucate = "TRUNCATE sourcelead.IND_PAYSLIP";
				PreparedStatement trucateStmt= conn.prepareStatement(trucate);
				trucateStmt.executeUpdate();
			
				 String sql = "SELECT * FROM sourcelead.ORG_LOCATIONS";
				 stmt = conn.prepareStatement(sql);
				 ResultSet rs = stmt.executeQuery();
				 System.out.println("----Adding Payslip for All Orgs starts-----");
				 while(rs.next()){
					 Long orgLocationId = rs.getLong("ID");
					 if(orgLocationId != null ) {
							 Integer status = 1;
							 String uuid = SourceLeadRandomCodeHelper.generateRandomUUID(); 
							 String query = "INSERT INTO `IND_PAYSLIP` (`UUID`, `IS_ACTIVE`,`DELETE_PERMISSION`,"
							 		+ " `PAYSLIP_NAME`,`PAYSLIP_DESCRIPTION`,`COMPANY_NAME`,`COMPANY_ADDRESS`,`COMPANY_LOGO`,`EMPLOYEE_ID`,`EMPLOYEE_TYPE`,`EMPLOYEE_UAN_NUMBER`,`EMPLOYEE_PF_NUMBER`,`EMPLOYEE_BANK_DETAILS`,`EMPLOYEE_DESIGNATION`,`EMPLOYEE_NAME`,"
							 		+ " `EMPLOYEE_CONTACT_DETAILS`,`EMPLOYEE_JOIN_DATE`,`EMPLOYEE_LOCATION`,`EMPLOYEE_PAN_NUMBER`,`LEAVE_ATTENDED`,`LEAVE_LEAVES`,`ORGLOCATION_ID`,`STATUS`) "
									 
						 	        + " VALUES( '"+uuid+"', 'Y', 'N',"
						 	   		+ " 'Default Payslip', 'Default Payslip', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y',"
						 	 		+ " 'Y','Y','Y','Y','Y','Y','"+orgLocationId+"','"+status+"') ";
							 PreparedStatement pstmt = conn.prepareStatement(query);
							 pstmt.executeUpdate();
					 }
					 System.out.println("----Adding Payslip for "+orgLocationId+" Org Done.");	 
				 }
				 System.out.println("----Adding Payslip for All Orgs ends-----");
		}
		
	catch(Exception e){
		e.printStackTrace();
	}finally {
		try {
			stmt.close();
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
	GenerateDefaultPayslipForAllOrgs dump = new GenerateDefaultPayslipForAllOrgs();
	dump.generatePayheadsByOrg();
	
}


}
