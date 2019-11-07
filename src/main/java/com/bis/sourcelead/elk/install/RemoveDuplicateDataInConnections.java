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

public class RemoveDuplicateDataInConnections{

	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Venkat
 * this method is using for update the UUID for Connections
 * @throws IOException
 * @throws SQLException
 */
public void removeDuplicateRequestSent(String status) throws IOException, SQLException {
	Connection conn = null ;
	PreparedStatement stmt1 = null;
try {
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			 String sql = "SELECT c.TO_UID as CTO_UID, u.ID as UID FROM "+dbconfig.getSchema()+".CONNECTIONS c, "+dbconfig.getSchema()+".USER u where c.FROM_UID = u.ID and c.CONNECTION_STATUS = ?";
			 stmt1 = conn.prepareStatement(sql);
			 stmt1.setString(1, status);
			 ResultSet rs = stmt1.executeQuery();
			 System.out.println("----Adding unique UUID connections  starts-----");
			 while(rs.next()){
				 String to_uid = rs.getString("CTO_UID");
				 String user_id = rs.getString("UID");
				 String sql2 = "SELECT ID FROM CONNECTIONS where TO_UID = ? AND FROM_UID = ? AND CONNECTION_STATUS = ?";
				 stmt1 = conn.prepareStatement(sql2);
				 stmt1.setString(1, user_id);
				 stmt1.setString(2, to_uid);
				 stmt1.setString(3, status);
				 ResultSet rs1 = stmt1.executeQuery(); 
				 while(rs1.next()) {
					 System.out.println("THE ID VALUE ="+rs1.getInt("ID"));
					 String sql3 = "DELETE FROM CONNECTIONS where ID = ?";
					 stmt1 = conn.prepareStatement(sql3);
					 stmt1.setInt(1, rs1.getInt("ID"));
					 int count = stmt1.executeUpdate();
					 System.out.println(count);
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
	RemoveDuplicateDataInConnections dump = new RemoveDuplicateDataInConnections();
	dump.removeDuplicateRequestSent("REQUEST_SENT");
	dump.removeDuplicateRequestSent("CONNECTED");
}



}
