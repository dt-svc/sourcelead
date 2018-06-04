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
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class AddCandidateToContact {
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * 
 * this method is using for update candidate as a contact 
 * @throws IOException
 * @throws SQLException
 */
@SuppressWarnings("unused")
public void updateCandidateToContact() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	Statement stmt2 = null;
	try {
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
		
		String sql = "SELECT * FROM "+dbconfig.getSchema()+".CANDIDATE " ;
		 stmt1 = conn.createStatement();
		 ResultSet rs = stmt1.executeQuery(sql);
		 
		 while(rs.next()){
			String userCode=rs.getString("CREATED_BY");
			String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".USER where USER_CODE='"+userCode+"' ";
			stmt2 = conn.createStatement();
			 ResultSet rs1 = stmt2.executeQuery(sql1);
			if(rs1.next()){
				Long userid=rs1.getLong("ID");
				String  firstName = rs.getString("FIRST_NAME");
				 String  lastName = rs.getString("LAST_NAME");
				 String  email = rs.getString("EMAIL");
				 String  mobilePhone = rs.getString("MOBILE_PHONE");
				 String  createdBy = rs.getString("CREATED_BY");
				 Date createdtDate = rs.getDate("CREATED_DATE");
				 
				 String query2 = " insert into "+dbconfig.getSchema()+".REFER_CONTACTS (REFER_FROM_USER_ID, CONTACT_USER_FIRSTNAME, CONTACT_USER_LASTNAME, CONTACT_USER_EMAIL, CONTACT_USER_PHONE,  "
							+ "CREATED_DATE, CREATED_BY,CANDIDATE_TYPE)"
							+ " values (?, ?, ?, ?, ?, ?, ?, ?)";
		    		
		    		try {
		    			 PreparedStatement pstmt2 = conn.prepareStatement(query2);
			    		 pstmt2.setLong(1, userid);
			    		 pstmt2.setString(2,firstName);
			    		 pstmt2.setString(3, lastName);
			    		 pstmt2.setString(4, email);
			    		 pstmt2.setString(5,mobilePhone);
			    		 pstmt2.setDate(6,(java.sql.Date) createdtDate);
			    		
			    		 pstmt2.setString(7,createdBy);
			    		 pstmt2.setInt(8, 1);
			    		 pstmt2.execute();
			    		 
		    		}catch (Exception e){   
						e.printStackTrace();
					}
			}
		 }
		
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
	AddCandidateToContact dump = new AddCandidateToContact();
	dump.updateCandidateToContact();
}

}
