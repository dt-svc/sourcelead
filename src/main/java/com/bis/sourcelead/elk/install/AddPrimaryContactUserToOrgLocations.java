package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class AddPrimaryContactUserToOrgLocations {
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Venkat I
 * this method is adding primary user for org locations
 * @throws IOException
 * @throws SQLException
 */
public void dumpPrimaryUser() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			
		
			 String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			
			 System.out.println("----Adding Primary User to OrgLocations Starts-----");
			 while(rs1.next()){
				 
				 Long  orgLocId = rs1.getLong("ID");
				 
				 if(rs1.getString("PRIMARY_CONTACT_USER_ID") == null){
					 
					    String query1 = "SELECT * from "+dbconfig.getSchema()+".ROLES where ORG_LOCATION_ID = ? and ROLE_NAME = ? ";
				    	java.sql.PreparedStatement pstmt2 = conn.prepareStatement(query1); 
				    	pstmt2.setLong(1, orgLocId);
				    	pstmt2.setString(2, "ROLE_MANAGER");
				    	ResultSet rs3 = pstmt2.executeQuery();
				    	
				    	if (rs3.next()) {
				    		Long  roleId = rs3.getLong("ID");
				    		String query2 = "SELECT * from "+dbconfig.getSchema()+".USER_ROLE where ORG_LOCATIONS_ID = ? and ROLE_ID = ? ";
				    		java.sql.PreparedStatement pstmt3 = conn.prepareStatement(query2); 
				    		pstmt3.setLong(1, orgLocId);
				    		pstmt3.setLong(2, roleId);
				    		ResultSet rs4 = pstmt3.executeQuery();
				    		
				    		if (rs4.next()) {
				    			Long primaryUserId = rs4.getLong("USER_ID");
				    			String query3 = " update "+dbconfig.getSchema()+".ORG_LOCATIONS set PRIMARY_CONTACT_USER_ID = ? where ID = ? ";
					    		java.sql.PreparedStatement pstmt4 = conn.prepareStatement(query3); 
					    		pstmt4.setLong(1, primaryUserId);
					    		pstmt4.setLong(2, orgLocId);
					    		pstmt4.executeUpdate(); 
				    		}
				    	}
				 }
		    		
		}
			System.out.println("----Adding Primary User to OrgLocations ends-----");
		
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
	AddPrimaryContactUserToOrgLocations dump = new AddPrimaryContactUserToOrgLocations();
	dump.dumpPrimaryUser();
}

}
