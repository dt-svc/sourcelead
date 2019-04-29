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

public class UpdateOrgIdInProjectClients {
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
	Statement stmt3 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			
		     System.out.println("----Updating Org Id in ProjectClients Starts-----");
			 String sql1 = "SELECT pc.ORG_LOCATIONS_ID,pc.ID FROM "+dbconfig.getSchema()+".PROJECT_CLIENTS pc";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			 while(rs1.next()){
				 Long  orglocationId = rs1.getLong("ORG_LOCATIONS_ID");
				 Long  projectClientId = rs1.getLong("ID");
				  sql1 = "SELECT loc.ORG_ID FROM "+dbconfig.getSchema()+".ORG_LOCATIONS loc  where  loc.ID ="+orglocationId+"";
				  stmt3 = conn.createStatement();
				  ResultSet rs2 = stmt3.executeQuery(sql1);
				  while(rs2.next()){
					  Long  orgId = rs2.getLong("ORG_ID");
						 if(orgId != null && orgId != null && orgId != 0) {
								    String query1 = " update "+dbconfig.getSchema()+".PROJECT_CLIENTS set ORG_ID = '"+orgId+"' where ID = '"+projectClientId+"' ";
							    	java.sql.PreparedStatement pstmt2 = conn.prepareStatement(query1); 
									pstmt2.executeUpdate(); 
						 }
				  }
			}
			 System.out.println("----Updating Org Id in ProjectClients ends-----");
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
	UpdateOrgIdInProjectClients dump = new UpdateOrgIdInProjectClients();
	dump.updateContactEmail();
}

}
