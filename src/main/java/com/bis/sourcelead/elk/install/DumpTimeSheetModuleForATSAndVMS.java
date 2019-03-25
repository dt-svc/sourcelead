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

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DumpTimeSheetModuleForATSAndVMS {


	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Venkat I
 * this method is using for update time sheet modules for orglocations
 * @throws IOException
 * @throws SQLException
 */
public void dumpTimeSheetModules() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			
		
			 String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			
			 System.out.println("----Adding time sheet module for orglocations starts-----");
			 while(rs1.next()){
				 
				 String  modules = rs1.getString("MODULES_ID");
				 Long orgLocationId = rs1.getLong("ID");
				 
				 if(modules == null)
				 continue;
				 
				 List<Integer> moduleIds = new ArrayList<Integer>();
				 for (String s : modules.split(",")){
					 moduleIds.add(Integer.parseInt(s));
				 }
				 
				 boolean tsflag = moduleIds.contains(Constants.MODULE_TIMESHEET);
				 if(tsflag == false) {
					    modules = modules+","+Constants.MODULE_TIMESHEET;
						String query2 = " update "+dbconfig.getSchema()+".ORG_LOCATIONS set MODULES_ID = ? where ID = ? ";
			    		java.sql.PreparedStatement pstmt3 = conn.prepareStatement(query2); 
			    		pstmt3.setString(1, modules);
			    		pstmt3.setLong(2, orgLocationId);
			    		pstmt3.executeUpdate(); 
			    		
			    		
			    		 String sql2 = "SELECT * FROM "+dbconfig.getSchema()+".ROLES where ORG_LOCATION_ID = '"+orgLocationId+"' ";
						 Statement stmt2 = conn.createStatement();
						 ResultSet rs2 = stmt2.executeQuery(sql2);
						 
						 while(rs2.next()) {
							 String actionIds = null;
							 String roleActionIds = rs2.getString("ACTION_ID");
							 Long roleId = rs2.getLong("ID");
							 
							 String roleName = rs2.getString("ROLE_NAME");
							 if(roleName.equalsIgnoreCase("ROLE_ADMIN") || roleName.equalsIgnoreCase("ROLE_MANAGER")) {
								 actionIds = "102,104,105,106,107,108";
							 }else {
								 actionIds = "102,104,105";
							 }
							 
							 if(StringUtils.isNotBlank(roleActionIds)) {
								 roleActionIds = roleActionIds+","+actionIds;
							 }else {
								 roleActionIds = actionIds;
							 }
							 
							 
							    String query3 = " update "+dbconfig.getSchema()+".ROLES set ACTION_ID = ? where ID = ? ";
					    		java.sql.PreparedStatement pstmt4 = conn.prepareStatement(query3); 
					    		pstmt4.setString(1, roleActionIds);
					    		pstmt4.setLong(2, roleId);
					    		pstmt4.executeUpdate(); 
						 }
				 }
				
			}
			System.out.println("----Adding time sheet module for orglocations ends-----");
		
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
	DumpTimeSheetModuleForATSAndVMS dump = new DumpTimeSheetModuleForATSAndVMS();
	dump.dumpTimeSheetModules();
}


}
