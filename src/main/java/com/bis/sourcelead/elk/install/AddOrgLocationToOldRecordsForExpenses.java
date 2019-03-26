package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class AddOrgLocationToOldRecordsForExpenses {
private ComboPooledDataSource comboPooledDataSource;
ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);

private void AddNewSkills() throws SQLException {
	 DataSource dataSource = comboPooledDataSource();
	 try (Connection con = dataSource.getConnection()) {
		 Statement stmt = con.createStatement();
		 Statement stmt1 = con.createStatement();
		 Statement stmt2 = con.createStatement();
		 Statement stmt3 = con.createStatement();
		 Statement stmt4 = con.createStatement();
		 Statement stmt5 = con.createStatement();
		 String query = "SELECT * From "+dbconfig.getSchema()+".EXPENSES_APPROVER_HIERARCHY "; 
		 ResultSet eah = stmt.executeQuery(query);
		 while(eah.next()){
			Long eahId = eah.getLong("ID");
			if(eah.getString("ACTIONPERFORMEDBY") != null && !eah.getString("ACTIONPERFORMEDBY").equalsIgnoreCase("")) {
				String orgName = eah.getString("ACTIONPERFORMEDBY").trim();
				String org = "SELECT * From "+dbconfig.getSchema()+".ORG  where ORG_NAME = '"+orgName+"'";
				ResultSet orgData = stmt1.executeQuery(org);
				if(orgData.next() == false) {
					String user = "SELECT ID, CONCAT(FIRST_NAME,' ',IFNULL(LAST_NAME, '')) AS name FROM "+dbconfig.getSchema()+".USER where CONCAT(FIRST_NAME,' ',LAST_NAME) = '"+orgName+"'";
					ResultSet userData = stmt3.executeQuery(user);
					while(userData.next()) {
						Long  userId = userData.getLong("ID");
						String orgLoc = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS";
						ResultSet orgLocat = stmt4.executeQuery(orgLoc);
						while(orgLocat.next()) {
							Long  primaryContactUser = orgLocat.getLong("PRIMARY_CONTACT_USER_ID");
							Long  orgLocation = orgLocat.getLong("ID");
							if(primaryContactUser == userId) {
								String query1 = "UPDATE "+dbconfig.getSchema()+".EXPENSES_APPROVER_HIERARCHY SET ACTION_BY_ORGLOC_ID = ? WHERE ID = ?";
								PreparedStatement pstmt = con.prepareStatement(query1);
								pstmt.setLong(1, orgLocation);
								pstmt.setLong(2, eahId);
								System.out.println(orgLocation+"   "+eahId);
								pstmt.executeUpdate();
							}
							
						}
					}
				}else {
					/*ResultSet orgDataName = stmt5.executeQuery(org);
					while(orgDataName.next()){*/
						Long  orgId = orgData.getLong("ID");
						String orgLoc = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS where ORG_ID = '"+orgId+"'";
						ResultSet orgLocations = stmt2.executeQuery(orgLoc);
						while(orgLocations.next()){
							Long  orgLocation = orgLocations.getLong("ID");
							String query1 = "UPDATE "+dbconfig.getSchema()+".EXPENSES_APPROVER_HIERARCHY SET ACTION_BY_ORGLOC_ID = ? WHERE ID = ?";
							PreparedStatement pstmt = con.prepareStatement(query1);
							pstmt.setLong(1, orgLocation);
							pstmt.setLong(2, eahId);
							System.out.println(orgLocation+"   "+eahId);
							pstmt.executeUpdate();	
						}
					//}		
				}
				
			}
		 }
				 
			
	 }catch(Exception e) {
		 e.printStackTrace();
	 }
}
	
	
	
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
			
public static void main(String[] args) throws SQLException {
	AddOrgLocationToOldRecordsForExpenses alertExpenseCode = new AddOrgLocationToOldRecordsForExpenses();
	alertExpenseCode.AddNewSkills();
}
	
}
