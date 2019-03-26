package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
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

public class CopyUserIdIntoUsersColumnInvoice {

	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Venkat
 * this method is using for update the UUID for Connections
 * @throws IOException
 * @throws SQLException
 */
public void copyUserIds() throws IOException, SQLException {
	Connection conn = null ;
	Statement pstmt = null;
try {
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
		String query = "update "+dbconfig.getSchema()+".INVOICE set OWNER_USERS = OWNER_USER_ID where OWNER_USER_ID IS NOT NULL";
		pstmt = conn.createStatement();
		int firstCount = pstmt.executeUpdate(query);
		System.out.println("----Updating userIds is Starts-----");
		System.out.println("---- Number of rows affected by owner user : "+firstCount);
		
		String query1 = "update "+dbconfig.getSchema()+".INVOICE set CONTACT_USERS = CONTACT_USER_ID where CONTACT_USER_ID IS NOT NULL";
		int secondCount = pstmt.executeUpdate(query1);
		System.out.println("---- Number of rows affected by contact user : "+secondCount);
		System.out.println("----Updating userIds is ends-----");
		
	}catch(Exception e){
		e.printStackTrace();
	}finally {
		try {
			pstmt.close();
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
	CopyUserIdIntoUsersColumnInvoice dump = new CopyUserIdIntoUsersColumnInvoice();
	dump.copyUserIds();
}



}
