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

public class UpdateEmailToContacts {
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Venkat I
 * this method is using for update email for contacts
 * @throws IOException
 * @throws SQLException
 */
public void updateContactEmail() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			
		
			 String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".REFER_CONTACTS contacts where contacts.CONTACT_USER_ID IS NOT NULL AND contacts.CONTACT_USER_EMAIL IS NULL ";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			
			 System.out.println("----Updating email to contacts starts-----");
			 while(rs1.next()){
				 Long  userId = rs1.getLong("CONTACT_USER_ID");
				 Long contactId =  rs1.getLong("ID");
				 
				 if(userId != null) {
					 String sql2 = "SELECT * from "+dbconfig.getSchema()+".USER where ID = '"+userId+"' ";
					 Statement stmt2 = conn.createStatement();
					 ResultSet rs2 = stmt2.executeQuery(sql2);
					 
					 if (rs2.next()) {
						 
						    String email = rs2.getString("EMAIL");
						 
						    String query1 = " update "+dbconfig.getSchema()+".REFER_CONTACTS set CONTACT_USER_EMAIL = '"+email+"' where ID = '"+contactId+"' ";
					    	java.sql.PreparedStatement pstmt2 = conn.prepareStatement(query1); 
							pstmt2.executeUpdate(); 
				    	} 
				 }
			}
			System.out.println("----Updating email to contacts ends-----");
		
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
	UpdateEmailToContacts dump = new UpdateEmailToContacts();
	dump.updateContactEmail();
}

}
