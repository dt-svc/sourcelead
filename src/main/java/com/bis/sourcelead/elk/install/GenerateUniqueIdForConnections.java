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
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class GenerateUniqueIdForConnections {

	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Venkat
 * this method is using for update the UUID for Connections
 * @throws IOException
 * @throws SQLException
 */
public void connectionsUUID() throws IOException, SQLException {
	Connection conn = null ;
	PreparedStatement stmt1 = null;
try {
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			 String sql = "SELECT * FROM "+dbconfig.getSchema()+".CONNECTIONS";
			 stmt1 = conn.prepareStatement(sql);
			 ResultSet rs = stmt1.executeQuery();
			 System.out.println("----Adding unique UUID connections  starts-----");
			 while(rs.next()){
				 String UUID = rs.getString("CONNECTION_UID");
				 Long id = rs.getLong("ID");
				 if(UUID == null) {
					 String uuid = SourceLeadRandomCodeHelper.generateRandomUUID(); 
					 String query = "update "+dbconfig.getSchema()+".CONNECTIONS set CONNECTION_UID = ? where ID = ?";
					 PreparedStatement pstmt = conn.prepareStatement(query);
					 pstmt.setString(1, uuid);
					 pstmt.setLong(2, id);
					 pstmt.executeUpdate(); 
				 }
			 }
			 
			 System.out.println("----Adding unique UUID org connections ends-----");
		
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
	GenerateUniqueIdForConnections dump = new GenerateUniqueIdForConnections();
	dump.connectionsUUID();
}



}
