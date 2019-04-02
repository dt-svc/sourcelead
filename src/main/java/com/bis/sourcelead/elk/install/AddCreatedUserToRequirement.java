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

public class AddCreatedUserToRequirement {
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Venkat I
 * this method is using for update the userCode to userId	
 * @throws IOException
 * @throws SQLException
 */
public void dumpUserIdToJob() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			
		
			 String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".REQUIREMENT requirement ";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			
			 System.out.println("----Adding userId to createdBy column starts-----");
			 while(rs1.next()){
				 
				 Long  requirementId = rs1.getLong("ID");
				 String  userCode = rs1.getString("CREATED_BY");
				 
				 String query = "SELECT * from "+dbconfig.getSchema()+".USER where USER_CODE = '"+userCode+"' ";
				 java.sql.PreparedStatement pstmt = conn.prepareStatement(query); 
				 ResultSet rs2 = pstmt.executeQuery();
				
				 
			 	if (rs2.next()) {
			 		Long  userId = rs2.getLong("ID");
				    String query1 = " update "+dbconfig.getSchema()+".REQUIREMENT set CREATED_BY = ? where ID = ? ";
			    	java.sql.PreparedStatement pstmt2 = conn.prepareStatement(query1); 
			    	pstmt2.setLong(1, userId);
			    	pstmt2.setLong(2, requirementId);
					pstmt2.executeUpdate(); 
		    	} 
				
			}
			System.out.println("----Adding userId to createdBy column ends-----");
		
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
	AddCreatedUserToRequirement dump = new AddCreatedUserToRequirement();
	dump.dumpUserIdToJob();
}

}
