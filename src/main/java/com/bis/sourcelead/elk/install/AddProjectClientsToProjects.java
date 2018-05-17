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

public class AddProjectClientsToProjects {
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Venkat I
 * this method is adding project clients to projects
 * @throws IOException
 * @throws SQLException
 */
public void dumpProjectClientsToProjects() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			
		
			 String sql1 = "SELECT * from "+dbconfig.getSchema()+".PROJECTS where (IS_DEFAULT_PROJECT = 'N' OR IS_DEFAULT_PROJECT IS NULL) ";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			
			 System.out.println("----Adding Project Clients Projects starts-----");
			 while(rs1.next()){
				 
				 Long  projectId = rs1.getLong("ID");
				 Long  orgLocationId = rs1.getLong("ORG_LOCATIONS_ID");
				 Date startDate = rs1.getDate("CREATED_DATE");
				 String createdBy = rs1.getString("CREATED_BY");
				 
				 String query = "SELECT * from "+dbconfig.getSchema()+".USER where USER_CODE = '"+createdBy+"' ";
				 java.sql.PreparedStatement pstmt = conn.prepareStatement(query); 
				 ResultSet rs2 = pstmt.executeQuery();
				 
				 if (rs2.next()) {
					 Long  userId = rs2.getLong("ID");
						 
				 		String query2 = " insert into "+dbconfig.getSchema()+".PROJECT_CLIENTS (PROJECT_ID, ORG_LOCATIONS_ID, UUID, STATUS, LEVEL,  "
								+ "CREATED_DATE, CREATED_BY)"
								+ " values (?, ?, ?, ?, ?, ?, ?)";
			    		
			    		try {
			    			 PreparedStatement pstmt2 = conn.prepareStatement(query2);
				    		 pstmt2.setLong(1, projectId);
				    		 pstmt2.setLong(2,orgLocationId);
				    		 pstmt2.setString(3, SourceLeadRandomCodeHelper.generateRandomUUID());
				    		 pstmt2.setInt(4, 1);
				    		 pstmt2.setInt(5,1);
				    		 pstmt2.setDate(6,(java.sql.Date) startDate);
				    		 pstmt2.setLong(7,userId);
				    		 pstmt2.execute();	
				    		 
			    		}catch (Exception e){   
							e.printStackTrace();
						}
					 
				 }
				 
				 String query3 = "SELECT * from "+dbconfig.getSchema()+".PROJECT_CLIENTS where ORG_LOCATIONS_ID = '"+orgLocationId+"' and PROJECT_ID = '"+projectId+"' ";
				 java.sql.PreparedStatement pstmt1 = conn.prepareStatement(query3); 
				 ResultSet rs3 = pstmt1.executeQuery();
				 if (rs3.next()) {
					 Long  projectClientId = rs3.getLong("ID");
					 
						String query4 = " update "+dbconfig.getSchema()+".PROJECT_APPROVER_HIERARCHY set PROJECT_CLIENTS_ID = ? where PROJECT_ID = ? ";
			    		java.sql.PreparedStatement pstmt3 = conn.prepareStatement(query4); 
			    		pstmt3.setLong(1, projectClientId);
			    		pstmt3.setLong(2, projectId);
			    		pstmt3.executeUpdate(); 
				 }
				
		}
			System.out.println("----Adding Project Clients Projects ends-----");
		
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
	AddProjectClientsToProjects dump = new AddProjectClientsToProjects();
	dump.dumpProjectClientsToProjects();
}

}
