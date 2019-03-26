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

public class ApproverHierarchyToRequirement {
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Venkat I
 * this method is using for update the Approver hierarchy	
 * @throws IOException
 * @throws SQLException
 */
public void dumpApproverHierarchy() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			
		
			 String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".REQUIREMENT requirement ";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			
			 System.out.println("----Adding Approver hierarchy column starts-----");
			 while(rs1.next()){
				 Long  requirementId = rs1.getLong("ID");
				 
				 String query = null;
				 ResultSet rs2 = null;
				 
				 query = "SELECT distinct REQUIREMENT_ID from "+dbconfig.getSchema()+".REQUIREMENT_APPROVER_HIERARCHY where REQUIREMENT_ID = '"+requirementId+"' ";
				 java.sql.PreparedStatement pstmt = conn.prepareStatement(query); 
				 rs2 = pstmt.executeQuery();
				 
				 
				 if (rs2.next()) {
					    do {
					    	String query1 = " update "+dbconfig.getSchema()+".REQUIREMENT set APPRVER_HIERARCHY = 'Y' where ID = '"+requirementId+"' ";
					    	java.sql.PreparedStatement pstmt2 = conn.prepareStatement(query1); 
							pstmt2.executeUpdate(); 
				    		
			    	    } while(rs2.next());
			    	} else {
			    			String query1 = " update "+dbconfig.getSchema()+".REQUIREMENT set APPRVER_HIERARCHY = 'N' where ID = '"+requirementId+"' ";
			    			java.sql.PreparedStatement	 pstmt2 = conn.prepareStatement(query1); 
			    			pstmt2.executeUpdate(); 
			    	
			    	}
				
			}
			System.out.println("----Adding Approver hierarchy column ends-----");
		
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
	ApproverHierarchyToRequirement dump = new ApproverHierarchyToRequirement();
	dump.dumpApproverHierarchy();
}

}
