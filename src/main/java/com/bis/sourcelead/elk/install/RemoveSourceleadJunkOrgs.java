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

public class RemoveSourceleadJunkOrgs {
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Venkat I
 * this method is using for deleting junk org locations
 * @throws IOException
 * @throws SQLException
 */
public void removeOrgs() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	String sqlQuery = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			
		String[] orgLocationsList = BisJunkOrgLocations.ORG_lOCATIONS;
		
		 String foriegnChecks = "set foreign_key_checks = 0";
		 stmt1 = conn.createStatement();
		 stmt1.executeQuery(foriegnChecks);
		 
		 System.out.println("----Deletion of org locations starts-----");
		
		for (String orgLocation : orgLocationsList) {
			
			 
			 sqlQuery = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS orgLocations where orgLocations.ORG_NAME = '"+orgLocation+"' ";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sqlQuery);
			
			 if(rs1.next()){
				 
				 String orgLocations = rs1.getString("ID");
				 String orgId = rs1.getString("ORG_ID");
				 String orgLocationName = rs1.getString("ORG_NAME");
				 String primaryUserId = rs1.getString("PRIMARY_CONTACT_USER_ID");
				 
				 
				     System.out.println("Removing Submission Template Default Data....");
				    
				     sqlQuery = "SELECT * from "+dbconfig.getSchema()+".SUBMISSION_TEMPLATE st where st.ORG_LOCATION_ID = '"+orgLocations+"' ";
					 Statement stmt2 = conn.createStatement();
					 ResultSet rs2 = stmt2.executeQuery(sqlQuery);
					
					 while(rs2.next()) {
						    
						    String sumbissionTemplateId = rs2.getString("ID");
						 
						    sqlQuery = "delete from "+dbconfig.getSchema()+".SUBMISSION_TEMPLATE  where ID = '"+sumbissionTemplateId+"' ";
						    Statement stmt3 = conn.createStatement();
						    stmt3.executeUpdate(sqlQuery);
				     } 
					 
					 System.out.println("Removing Vendor Template Default Data....");
					 
					 sqlQuery = "SELECT * from "+dbconfig.getSchema()+".VENDOR_ON_BOARDING_TEMPLATE  template where template.ORG_LOCATION_ID = '"+orgLocations+"' ";
					 Statement stmt4 = conn.createStatement();
					 ResultSet rs3 = stmt4.executeQuery(sqlQuery);
					 
					 while(rs3.next()) {
						 
						   String vendorTemplateId = rs3.getString("ID");
						   
						     sqlQuery = "SELECT * from "+dbconfig.getSchema()+".VENDOR_ON_BOARDING_TEMPLATE_DOCUMENTS  docs where docs.VENDOR_ON_BOARDING_TEMPLATE_ID = '"+vendorTemplateId+"' ";
							 Statement docsStmt = conn.createStatement();
							 ResultSet docsrs = docsStmt.executeQuery(sqlQuery);
							 
							 while(docsrs.next()) {
								 String docId = docsrs.getString("ID");
								 
								    sqlQuery = "delete from "+dbconfig.getSchema()+".VENDOR_ON_BOARDING_TEMPLATE_DOCUMENTS  where ID = '"+docId+"' ";
								    Statement docstmt1 = conn.createStatement();
								    docstmt1.executeUpdate(sqlQuery);
								 
							 }
						 
						    sqlQuery = "delete from "+dbconfig.getSchema()+".VENDOR_ON_BOARDING_TEMPLATE  where ID = '"+vendorTemplateId+"' ";
						    Statement stmt5 = conn.createStatement();
						    stmt5.executeUpdate(sqlQuery);
					 }
					 
					 System.out.println("Removing Contracts Template Default Data....");
					 
					 sqlQuery = "SELECT * from "+dbconfig.getSchema()+".CONTRACTS_TEMPLATE  template where template.ORGLOCATION_ID = '"+orgLocations+"' ";
					 Statement stmt5 = conn.createStatement();
					 ResultSet rs4 = stmt5.executeQuery(sqlQuery);
					 
					 while(rs4.next()) {
						    String contractTemplateId = rs4.getString("ID");
						 
						    sqlQuery = "delete from "+dbconfig.getSchema()+".CONTRACTS_TEMPLATE  where ID = '"+contractTemplateId+"' ";
						    Statement stmt6 = conn.createStatement();
						    stmt6.executeUpdate(sqlQuery);
					 }
					 
					 System.out.println("Removing Expense Category Default Data....");
					 
					 sqlQuery = "SELECT * from "+dbconfig.getSchema()+".EXPENSE_CATEGORY  category where category.LOCATION_ID = '"+orgLocations+"' ";
					 Statement stmt6 = conn.createStatement();
					 ResultSet rs5 = stmt6.executeQuery(sqlQuery);
					 
					 while(rs5.next()) {
						    String expenceCategoryId = rs5.getString("ID");
						 
						    sqlQuery = "delete from "+dbconfig.getSchema()+".EXPENSE_CATEGORY  where ID = '"+expenceCategoryId+"' ";
						    Statement stmt7 = conn.createStatement();
						    stmt7.executeUpdate(sqlQuery);
					 }
					 
					 
					 System.out.println("Removing Default Project Data....");
					 sqlQuery = "SELECT * from "+dbconfig.getSchema()+".PROJECTS projects where projects.ORG_LOCATIONS_ID = '"+orgLocations+"' ";
					 Statement stmt8 = conn.createStatement();
					 ResultSet rs6 = stmt8.executeQuery(sqlQuery);
					 
					 while(rs6.next()) {
						 
						 String projectId = rs6.getString("ID");
						 
						 sqlQuery = "SELECT * from "+dbconfig.getSchema()+".PROJECT_APPROVER_HIERARCHY pah where pah.PROJECT_ID = '"+projectId+"' ";
						 Statement stmt9 = conn.createStatement();
						 ResultSet rs7 = stmt9.executeQuery(sqlQuery);
						 
						 while(rs7.next()) {
							   String pahId = rs7.getString("ID");
							 
							   sqlQuery = "delete from "+dbconfig.getSchema()+".PROJECT_APPROVER_HIERARCHY  where ID = '"+pahId+"' ";
							    Statement stmt7 = conn.createStatement();
							    stmt7.executeUpdate(sqlQuery);
						 }
						 
						 
						 sqlQuery = "SELECT * from "+dbconfig.getSchema()+".PROJECT_CLIENTS pc where pc.PROJECT_ID = '"+projectId+"' ";
						 Statement stmt10 = conn.createStatement();
						 ResultSet rs8 = stmt10.executeQuery(sqlQuery);
						 
						 while(rs8.next()) {
							    String pcId = rs8.getString("ID");
							 
							    sqlQuery = "delete from "+dbconfig.getSchema()+".PROJECT_CLIENTS  where ID = '"+pcId+"' ";
							    Statement stmt11 = conn.createStatement();
							    stmt11.executeUpdate(sqlQuery);
						 }
						 
						 
						 sqlQuery = "SELECT * from "+dbconfig.getSchema()+".PROJECT_TASKS tasks where tasks.PROJECT_ID = '"+projectId+"' ";
						 Statement stmt11 = conn.createStatement();
						 ResultSet rs9 = stmt11.executeQuery(sqlQuery);
						 
						 while(rs9.next()) {
							    String taskId = rs9.getString("ID");
							 
							    sqlQuery = "delete from "+dbconfig.getSchema()+".PROJECT_TASKS  where ID = '"+taskId+"' ";
							    Statement stmt12 = conn.createStatement();
							    stmt12.executeUpdate(sqlQuery);
						 }
						 
						    sqlQuery = "delete from "+dbconfig.getSchema()+".PROJECTS  where ID = '"+projectId+"' ";
						    Statement stmt13 = conn.createStatement();
						    stmt13.executeUpdate(sqlQuery);
						 
					 }
					 
					 
					 System.out.println("Removing Roles Data....");
					 
					 
					 sqlQuery = "SELECT * from "+dbconfig.getSchema()+".USER_ROLE userRole where userRole.ORG_LOCATIONS_ID = '"+orgLocations+"' ";
					 Statement stmt15 = conn.createStatement();
					 ResultSet rs12 = stmt15.executeQuery(sqlQuery);
					 
					 while(rs12.next()) {
						 
						    String userRoleId = rs12.getString("ID");
						    String userId = rs12.getString("USER_ID");
						 
						    sqlQuery = "delete from "+dbconfig.getSchema()+".USER_ROLE  where ID = '"+userRoleId+"' ";
						    Statement stmt16 = conn.createStatement();
						    stmt16.executeUpdate(sqlQuery);
						    
						    sqlQuery = "delete from "+dbconfig.getSchema()+".EMPLOYEE  where USER_ID = '"+userId+"' and ORG_LOCATIONS_ID = '"+orgLocations+"' ";
						    Statement stmt17 = conn.createStatement();
						    stmt17.executeUpdate(sqlQuery);
					 }
					 
					 sqlQuery = "SELECT * from "+dbconfig.getSchema()+".ROLES roles where roles.ORG_LOCATION_ID = '"+orgLocations+"' ";
					 Statement stmt14 = conn.createStatement();
					 ResultSet rs11 = stmt14.executeQuery(sqlQuery);
					 
					 while(rs11.next()) {
						 
						    String roleId = rs11.getString("ID");
						 
						    sqlQuery = "delete from "+dbconfig.getSchema()+".ROLES  where ID = '"+roleId+"' ";
						    Statement rolesStmt = conn.createStatement();
						    rolesStmt.executeUpdate(sqlQuery);
					 }
					 
					 
					 
					 
					 sqlQuery = "SELECT * from "+dbconfig.getSchema()+".ROLE_HIERARCHY hierarchy where hierarchy.ORG_LOCATION_ID = '"+orgLocations+"' ";
					 Statement stmt13 = conn.createStatement();
					 ResultSet rs10 = stmt13.executeQuery(sqlQuery);
					 
					 while(rs10.next()) {
						 
						    String hierarchyId = rs10.getString("ID");
						 
						    sqlQuery = "delete from "+dbconfig.getSchema()+".ROLE_HIERARCHY  where ID = '"+hierarchyId+"' ";
						    Statement hierstmt = conn.createStatement();
						    hierstmt.executeUpdate(sqlQuery);
					 }
					 
					 
					 
					 System.out.println("Removing Requirement Data....");
					 
					 sqlQuery = "SELECT * from "+dbconfig.getSchema()+".REQUIREMENT req where req.ORG_LOCATION_ID = '"+orgLocations+"' ";
					 Statement stmt16 = conn.createStatement();
					 ResultSet rs13 = stmt16.executeQuery(sqlQuery);
					 
					 while(rs13.next()) {
						 
						    String reqId = rs13.getString("ID");
						 
						    sqlQuery = "delete from "+dbconfig.getSchema()+".REQUIREMENT  where ID = '"+reqId+"' ";
						    Statement stmt17 = conn.createStatement();
						    stmt17.executeUpdate(sqlQuery);
					 }
					 
					 
					 System.out.println("Removing Vendor Relation Data....");
					 sqlQuery = "SELECT * from "+dbconfig.getSchema()+".ORG_RELATIONS orl where (orl.RESPONDER_LOCATION_ID = '"+orgLocations+"' or  orl.LOCATION_ID = '"+orgLocations+"') ";
					 Statement stmt17 = conn.createStatement();
					 ResultSet rs14 = stmt17.executeQuery(sqlQuery);
					 
					 while(rs14.next()) {
						 
						    String orgRelationId = rs14.getString("ID");
						    
						    
						    sqlQuery = "SELECT * from "+dbconfig.getSchema()+".ORG_SPOC spoc where spoc.ORG_RELATION_ID = '"+orgRelationId+"' ";
							 Statement stmt18 = conn.createStatement();
							 ResultSet rs15 = stmt18.executeQuery(sqlQuery);
							 
							 while(rs15.next()) {
								 
								 String spocId = rs15.getString("ID");
								 
								    sqlQuery = "delete from "+dbconfig.getSchema()+".ORG_SPOC  where ID = '"+spocId+"' ";
								    Statement stmt19 = conn.createStatement();
								    stmt19.executeUpdate(sqlQuery);
							 }
							 
						 
							sqlQuery = "delete from "+dbconfig.getSchema()+".ORG_RELATIONS  where ID = '"+orgRelationId+"' ";
						    Statement stmt20 = conn.createStatement();
						    stmt20.executeUpdate(sqlQuery);
					 }
					 
					 
					 System.out.println("Removing Nofifications Data....");
					 
					 sqlQuery = "SELECT * from "+dbconfig.getSchema()+".NOTIFICATIONS notifications where notifications.NOTIFICATION_TO = '"+primaryUserId+"' ";
					 Statement tonotistmt = conn.createStatement();
					 ResultSet tonotirs = tonotistmt.executeQuery(sqlQuery);
					 while(tonotirs.next()) {
						 String notificaytionId = tonotirs.getString("ID");
						    sqlQuery = "delete from "+dbconfig.getSchema()+".NOTIFICATIONS  where ID = '"+notificaytionId+"' ";
						    Statement tonotistmt1 = conn.createStatement();
						    tonotistmt1.executeUpdate(sqlQuery);
					 }
					 
					 sqlQuery = "SELECT * from "+dbconfig.getSchema()+".NOTIFICATIONS notifications where notifications.NOTIFICATION_FORM = '"+primaryUserId+"' ";
					 Statement fromnotistmt = conn.createStatement();
					 ResultSet fromnotirs = fromnotistmt.executeQuery(sqlQuery);
					 while(fromnotirs.next()) {
						 String notificaytionId = fromnotirs.getString("ID");
						    sqlQuery = "delete from "+dbconfig.getSchema()+".NOTIFICATIONS  where ID = '"+notificaytionId+"' ";
						    Statement fromnotirs1 = conn.createStatement();
						    fromnotirs1.executeUpdate(sqlQuery);
					 }
					 
					 
					 
					 
					 System.out.println("Removing Candidate Data....");
					 sqlQuery = "SELECT * from "+dbconfig.getSchema()+".CANDIDATE candidate where candidate.ORG_LOCATION_ID = '"+orgLocations+"' ";
					 Statement stmt21 = conn.createStatement();
					 ResultSet rs15 = stmt21.executeQuery(sqlQuery);
					 while(rs15.next()) {
						 String candidateId = rs15.getString("ID");
						    sqlQuery = "delete from "+dbconfig.getSchema()+".CANDIDATE  where ID = '"+candidateId+"' ";
						    Statement stmt22 = conn.createStatement();
						    stmt22.executeUpdate(sqlQuery);
					 }
					 
					 
					 
					 System.out.println("Removing OrgLocations Data....");
					 
					 sqlQuery = "delete from "+dbconfig.getSchema()+".ORG_LOCATIONS  where ID = '"+orgLocations+"' ";
					 Statement stmt23 = conn.createStatement();
					 stmt23.executeUpdate(sqlQuery);
					 
					 
					 System.out.println("Removing Org Data....");
					 
					 sqlQuery = "delete from "+dbconfig.getSchema()+".ORG  where ID = '"+orgId+"' ";
					 Statement stmt24 = conn.createStatement();
					 stmt24.executeUpdate(sqlQuery);
					 
					 System.out.println("Removing User Data....");
					 
					 
					 sqlQuery = "SELECT * from "+dbconfig.getSchema()+".USER_ROLE userRole where userRole.USER_ID = '"+primaryUserId+"' and userRole.ORG_LOCATIONS_ID != '"+orgLocations+"' ";
					 Statement stmt26 = conn.createStatement();
					 ResultSet rs16 = stmt26.executeQuery(sqlQuery);
					 Integer userRolesCount = 0;
					
					 while(rs16.next()) {
						 userRolesCount = userRolesCount+1;
					 }
					 
					 if(userRolesCount == 0) {
						 sqlQuery = "delete from "+dbconfig.getSchema()+".USER  where ID = '"+primaryUserId+"' ";
						 Statement stmt25 = conn.createStatement();
						 stmt25.executeUpdate(sqlQuery);
					 }
					
					 
					 System.out.println("---------Removed "+orgLocationName+" Successfully------");
			}
			 
		}	
		
		 String endforiegnChecks = "set foreign_key_checks = 1";
		 Statement stmt2 = conn.createStatement();
		 stmt2.executeQuery(endforiegnChecks);
		 
		 System.out.println("----Deletion of org locations ends-----");
		
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
	RemoveSourceleadJunkOrgs dump = new RemoveSourceleadJunkOrgs();
	dump.removeOrgs();
}

}
