package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class GeneratePrimaryContactUserIdInOrgLocaitonTable {

	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
	
/**
 * @author Laxman
 * @throws IOException
 * @throws SQLException
 */
public void generatePrimaryContactUserId() throws IOException, SQLException {
	Connection conn = null ;
	PreparedStatement stmt1 = null;
try {
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			 String sql = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS where PRIMARY_CONTACT_USER_ID IS NULL";
			 stmt1 = conn.prepareStatement(sql);
			 ResultSet rs = stmt1.executeQuery();
			 System.out.println("---------"+rs);
			 while(rs.next()){
				 Long id = rs.getLong("ID");
				 Long orgId = rs.getLong("ORG_ID");
				 String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".USER_ROLE where ORG_LOCATIONS_ID = "+id+"";
				 stmt1 = conn.prepareStatement(sql1);
				 ResultSet rs1 = stmt1.executeQuery();
				 while(rs1.next()){
					 Long userId = rs1.getLong("USER_ID");
					 String query = "update "+dbconfig.getSchema()+".ORG_LOCATIONS set PRIMARY_CONTACT_USER_ID = ? where ID = ?";
					 PreparedStatement pstmt = conn.prepareStatement(query);
					 pstmt.setLong(1, userId);
					 pstmt.setLong(2, id);
					 pstmt.executeUpdate(); 
					 System.out.println("*************************");
					 System.out.println("orgLocationId >>"+id +" UserId >>"+userId);
					 
					 String query1 = "update "+dbconfig.getSchema()+".ORG set PRIMARY_CONTACT_USER_ID = ? where ID = ?";
					 PreparedStatement pstmt1 = conn.prepareStatement(query1);
					 pstmt1.setLong(1, userId);
					 pstmt1.setLong(2, orgId);
					 pstmt1.executeUpdate(); 
					 
					 System.out.println("orgId >>"+orgId +" UserId >>"+userId);
				 }
				 
			 }
			 
			 System.out.println("----Updating OrgLocation Primary_Contact_User_Id ends-----");
		
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
	GeneratePrimaryContactUserIdInOrgLocaitonTable dump = new GeneratePrimaryContactUserIdInOrgLocaitonTable();
	dump.generatePrimaryContactUserId();
	
}


}
