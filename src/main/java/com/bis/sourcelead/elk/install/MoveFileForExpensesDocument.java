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

public class MoveFileForExpensesDocument {
	
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
 

private void moveFileForExpensesDocumentFormFILE_URLtoFILEURL() {
	Connection connection = null ;
	java.sql.PreparedStatement pstmt = null;
	Statement stmt = null;
	Statement stmt1 = null;
	
	DataSource dataSource = comboPooledDataSource();
	try {
		connection = dataSource.getConnection();
		stmt = connection.createStatement();
		String entriesDocument = "SELECT * FROM "+dbconfig.getSchema()+".EXPENSES_ENTRIES_DOCUMENTS entriesDocument";
		ResultSet rs = stmt.executeQuery(entriesDocument);
		 while(rs.next()){
			 Long  documentId = rs.getLong("ID");
			 if(rs.getString("FILE_URL")!= null) {
			 String  FILE_URL = rs.getString("FILE_URL");
			 String query = "update "+dbconfig.getSchema()+".EXPENSES_ENTRIES_DOCUMENTS set FILEURL = ? where ID = ? ";
			 pstmt = connection.prepareStatement(query); // create a statement
		     pstmt.setString(1,FILE_URL);
		     pstmt.setLong(2, documentId);
		     pstmt.executeUpdate();
			 }
		 }
	} catch (SQLException e) {
		e.printStackTrace();
	}finally{
		try {
			stmt1 = connection.createStatement();
			String dropFILE_URL = "ALTER TABLE "+dbconfig.getSchema()+".EXPENSES_ENTRIES_DOCUMENTS DROP COLUMN FILE_URL";
			boolean rs = stmt1.execute(dropFILE_URL);
			if(!rs) {
				System.out.println("Deleted successfully");
			}else {
				System.out.println("column not deleted");
			}
		} catch (SQLException e) {
			System.out.println("Column does not exist");
			e.printStackTrace();
		}
		
		

	}
	
}

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
	MoveFileForExpensesDocument dump = new MoveFileForExpensesDocument();
	dump.moveFileForExpensesDocumentFormFILE_URLtoFILEURL();
}

}
