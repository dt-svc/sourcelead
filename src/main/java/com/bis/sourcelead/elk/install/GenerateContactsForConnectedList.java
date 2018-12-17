package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.enums.Contact_Source;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class GenerateContactsForConnectedList {
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Venkat
 * this method is using for update the UUID for Connections
 * @throws IOException
 * @throws SQLException
 */
public void addContactsFromConnections() throws IOException, SQLException {
	Connection conn = null ;
	PreparedStatement stmt1 = null;
try {
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			 String sql = "SELECT * FROM "+dbconfig.getSchema()+".CONNECTIONS where CONNECTION_STATUS = 'CONNECTED'";
			 stmt1 = conn.prepareStatement(sql);
			 ResultSet rs = stmt1.executeQuery();
			 System.out.println("----Adding Contacts from connections  starts-----");
			 while(rs.next()){
				 Long FROM_UID = rs.getLong("FROM_UID");
				 Long TO_UID = rs.getLong("TO_UID");
				 String query = "SELECT COUNT(*) FROM "+dbconfig.getSchema()+".REFER_CONTACTS where REFER_FROM_USER_ID = ? and CONTACT_USER_ID = ?";
				 stmt1 = conn.prepareStatement(query);
				 stmt1.setLong(1, FROM_UID);
				 stmt1.setLong(2, TO_UID);
				 ResultSet rs1 = stmt1.executeQuery();
				 int count1 = 0;
				 while(rs1.next()) {
					 count1 = rs1.getInt(1);
				 }
				 if(count1 == 0) {
					 saveContacts(FROM_UID, TO_UID);
				 }
				 stmt1 = conn.prepareStatement(query);
				 stmt1.setLong(1, TO_UID);
				 stmt1.setLong(2, FROM_UID);
				 ResultSet rs2 = stmt1.executeQuery();
				 int count2 = 0;
				 while(rs2.next()) {
					 count2 = rs2.getInt(1);
				 }
				 if(count2 == 0) {
					 saveContacts(TO_UID, FROM_UID);
				 }
			 }
			 
			 System.out.println("----Adding Contacts from connections ends-----");
		
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



public void saveContacts(Long FROM_UID, Long TO_UID) {
	Connection conn = null ;
	PreparedStatement stmt1 = null;
	DataSource dataSource = comboPooledDataSource();
	try {
		conn = dataSource.getConnection();
	String fromUidQuery= "SELECT * FROM "+dbconfig.getSchema()+".USER where ID = ?";
	 stmt1 = conn.prepareStatement(fromUidQuery);
	 stmt1.setLong(1, TO_UID);
	 ResultSet rs1 = stmt1.executeQuery();
	 if(rs1.next()) {
		 String contactUserFirstName = rs1.getString("FIRST_NAME");
		 String contactUserLastName = rs1.getString("LAST_NAME");
		 String contactUserEmail = rs1.getString("EMAIL");
		 Long contactUserId = TO_UID;
		 String contactUserUUID = SourceLeadRandomCodeHelper.generateUUID();
		 String toUidQuery = "SELECT * FROM "+dbconfig.getSchema()+".USER where ID = ?";
		 stmt1 = conn.prepareStatement(toUidQuery);
		 stmt1.setLong(1, FROM_UID);
		 ResultSet rs2 = stmt1.executeQuery();
		 String createdBy = null;
		 String updatedBy = null;
		 if(rs2.next()) {
			 createdBy = rs2.getString("USER_CODE");
			 updatedBy = rs2.getString("USER_CODE");
		 }
		 String contactSource = Contact_Source.SELF.toString();
		 String query = "insert into "+dbconfig.getSchema()+".REFER_CONTACTS(REFER_FROM_USER_ID, CONTACT_USER_FIRSTNAME, CONTACT_USER_LASTNAME, CONTACT_USER_EMAIL, CONTACT_USER_ID, CONTACT_USER_UUID, CREATED_DATE, CREATED_BY, UPDATED_DATE, UPDATED_BY, CONTACT_SOURCE) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		 PreparedStatement pstmt = conn.prepareStatement(query);
		 pstmt.setLong(1,FROM_UID);
		 pstmt.setString(2, contactUserFirstName);
		 pstmt.setString(3, contactUserLastName);
		 pstmt.setString(4, contactUserEmail);
		 pstmt.setLong(5, contactUserId);
		 pstmt.setString(6, contactUserUUID);
		 java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		 pstmt.setTimestamp(7, date);
		 pstmt.setString(8, createdBy);
		 pstmt.setTimestamp(9, date);
		 pstmt.setString(10, updatedBy);
		 pstmt.setString(11, contactSource);
		 pstmt.executeUpdate();
	 }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
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
	GenerateContactsForConnectedList dump = new GenerateContactsForConnectedList();
	dump.addContactsFromConnections();
}



}
