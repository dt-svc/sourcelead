package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class SetOrgShortCodeForBusinessintelliUSandIND {
private ComboPooledDataSource comboPooledDataSource;
ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
	
	public void addorgShortCodeForBusinessintelliIndAndUs() throws IOException, SQLException {
		Connection conn = null ;
		java.sql.PreparedStatement pstmt = null;
		Statement stmt1 = null;
		
		Long businessIntelliIndiaOrgId = 5l;
		Long businessIntelliUsaOrgId = 3l;
		
		List<Long> bisOrgId = new ArrayList<>();
		bisOrgId.add(businessIntelliUsaOrgId);
		bisOrgId.add(businessIntelliIndiaOrgId);
		try {
			DataSource dataSource = comboPooledDataSource();
			conn = dataSource.getConnection();
			 String finalProjectsGeneratedId = null;
			 stmt1 = conn.createStatement();
				 for(Long projectsOrgLocIds :bisOrgId){
					 
					 if(projectsOrgLocIds.equals(businessIntelliUsaOrgId)) {
						 finalProjectsGeneratedId = "BIU";
					 }else if(projectsOrgLocIds.equals(businessIntelliIndiaOrgId)) {
						 finalProjectsGeneratedId = "BII";
					 }
					 
					 String organisationShortCode = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS orglocations where orglocations.id = "+projectsOrgLocIds+" ";
					 ResultSet rSetorgShortCode = stmt1.executeQuery(organisationShortCode);
					 while(rSetorgShortCode.next()){
						 String  orgShortCode = rSetorgShortCode.getString("GENERATED_USER_ID");
						 if(orgShortCode == null){
									 String query = "update "+dbconfig.getSchema()+".ORG_LOCATIONS set GENERATED_USER_ID = ? where ID = ? ";
									 pstmt = conn.prepareStatement(query); // create a statement
								     pstmt.setString(1,finalProjectsGeneratedId);
								     pstmt.setLong(2, projectsOrgLocIds);
								     pstmt.executeUpdate();
						   //  conn.commit();
						}
					}
				}
		}catch(Exception e){
			e.printStackTrace();
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
		SetOrgShortCodeForBusinessintelliUSandIND dump = new SetOrgShortCodeForBusinessintelliUSandIND();
		dump.addorgShortCodeForBusinessintelliIndAndUs();
	}

}
