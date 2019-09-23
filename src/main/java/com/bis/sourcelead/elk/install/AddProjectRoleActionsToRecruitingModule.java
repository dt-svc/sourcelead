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

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class AddProjectRoleActionsToRecruitingModule {
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Venkat I
 * this method is adding project role actions for ATS and VMS	
 * @throws IOException
 * @throws SQLException
 */
public void dumpProjectRoleActions() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			
		
			 String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			
			 System.out.println("----Adding Project Action Roles Starts-----");
			 while(rs1.next()){
				 
				 Long  orgLocId = rs1.getLong("ID");
				 
				 String query = "SELECT * from "+dbconfig.getSchema()+".ORG_LOCATIONS where ID = '"+orgLocId+"' ";
				 java.sql.PreparedStatement pstmt = conn.prepareStatement(query); 
				 ResultSet rs2 = pstmt.executeQuery();
				
				 
			 	if (rs2.next()) {
			 		if(rs2.getString("MODULES_ID") != null){
			 		String  registerModules = rs2.getString("MODULES_ID");
			 		List<Integer> moduleIds = new ArrayList<Integer>();
					 for (String s : registerModules.split(",")){
						 moduleIds.add(Integer.parseInt(s));
					 }
					 
					if(!moduleIds.contains(Constants.MODULE_TIMESHEET)){
						
						String projectActionIds = "90,91,92,93,94,95,96,99,100,101,132";
						
						String query1 = "SELECT * from "+dbconfig.getSchema()+".ROLES where ORG_LOCATION_ID = ? and ROLE_NAME = ? ";
				    	java.sql.PreparedStatement pstmt2 = conn.prepareStatement(query1); 
				    	pstmt2.setLong(1, orgLocId);
				    	pstmt2.setString(2, "ROLE_ADMIN");
				    	ResultSet rs3 = pstmt2.executeQuery();
				    	
				    	if (rs3.next()) {
				    		Long  roleId = rs3.getLong("ID");
				    		String actionIds = rs3.getString("ACTION_ID");
				    		actionIds = actionIds+","+projectActionIds;
				    		String query2 = " update "+dbconfig.getSchema()+".ROLES set ACTION_ID = ? where ID = ? ";
				    		java.sql.PreparedStatement pstmt3 = conn.prepareStatement(query2); 
				    		pstmt3.setString(1, actionIds);
				    		pstmt3.setLong(2, roleId);
				    		pstmt3.executeUpdate(); 
				    	}
				    	
				    	
				    	
				    	String query3 = "SELECT * from "+dbconfig.getSchema()+".ROLES where ORG_LOCATION_ID = ? and ROLE_NAME = ? ";
				    	java.sql.PreparedStatement pstmt4 = conn.prepareStatement(query3); 
				    	pstmt4.setLong(1, orgLocId);
				    	pstmt4.setString(2, "ROLE_MANAGER");
				    	ResultSet rs4 = pstmt4.executeQuery();
				    	
				    	if (rs4.next()) {
				    		Long  roleId = rs4.getLong("ID");
				    		String actionIds = rs4.getString("ACTION_ID");
				    		actionIds = actionIds+","+projectActionIds;
				    		String query4 = " update "+dbconfig.getSchema()+".ROLES set ACTION_ID = ? where ID = ? ";
				    		java.sql.PreparedStatement pstmt5 = conn.prepareStatement(query4); 
				    		pstmt5.setString(1, actionIds);
				    		pstmt5.setLong(2, roleId);
				    		pstmt5.executeUpdate(); 
				    	}
				    	
					}
		    	} 
		 	}
		}
			System.out.println("----Adding Project Action Roles ends-----");
		
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
	AddProjectRoleActionsToRecruitingModule dump = new AddProjectRoleActionsToRecruitingModule();
	dump.dumpProjectRoleActions();
}

}
