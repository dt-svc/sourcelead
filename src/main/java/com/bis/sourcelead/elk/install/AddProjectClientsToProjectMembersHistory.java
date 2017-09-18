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

public class AddProjectClientsToProjectMembersHistory {
	private ComboPooledDataSource comboPooledDataSource;
	
/**
 * @author Venkat I
 * this method is adding default projects for org locations
 * @throws IOException
 * @throws SQLException
 */
public void dumpProjectClientsToProjectMembersHistory() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			
		
			 String sql1 = "SELECT * from sourcelead.PROJECT_MEMBERS_HISTORY";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			
			 System.out.println("----Adding Project Clients to Projects members history starts-----");
			 while(rs1.next()){
				 
				 Long  projectMemberId = rs1.getLong("PROJECT_MEMBERS_ID");
				 Long  id = rs1.getLong("ID");
				 
				 String query = "SELECT * from sourcelead.PROJECT_MEMBERS where ID = '"+projectMemberId+"' ";
				 java.sql.PreparedStatement pstmt = conn.prepareStatement(query); 
				 ResultSet rs2 = pstmt.executeQuery();
				 
				 if (rs2.next()) {
					 Long  projectId = rs2.getLong("PROJECT_ID");
					 String query3 = "SELECT * from sourcelead.PROJECT_CLIENTS where PROJECT_ID = '"+projectId+"' ";
					 java.sql.PreparedStatement pstmt1 = conn.prepareStatement(query3); 
					 ResultSet rs3 = pstmt1.executeQuery();
					 if (rs3.next()) {
						 Long  projectClientId = rs3.getLong("ID");
						 
							String query4 = " update sourcelead.PROJECT_MEMBERS_HISTORY set PROJECT_CLIENTS_ID = ? where ID = ? ";
				    		java.sql.PreparedStatement pstmt3 = conn.prepareStatement(query4); 
				    		pstmt3.setLong(1, projectClientId);
				    		pstmt3.setLong(2, id);
				    		pstmt3.executeUpdate(); 
					 }
					 
				 }
			
		}
			System.out.println("----Adding Project Clients to Projects members history ends-----");
		
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
	AddProjectClientsToProjectMembersHistory dump = new AddProjectClientsToProjectMembersHistory();
	dump.dumpProjectClientsToProjectMembersHistory();
}

}
