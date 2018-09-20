package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class AlterExpenseShortCode {
private ComboPooledDataSource comboPooledDataSource;
ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
private void AlterExpenseShortCodeBasedOnOrgLocationId() {
	 DataSource dataSource = comboPooledDataSource();
	 try (Connection con = dataSource.getConnection()) {
			Statement stmt = con.createStatement();
			Statement stmt1 = con.createStatement();
			Statement stmt2 = con.createStatement();
			PreparedStatement pstmt = null;
			String expensesOrgList = "SELECT distinct LOCATION_ID FROM "+dbconfig.getSchema()+".EXPENSES";
			ResultSet expensesOrgLocationId = stmt.executeQuery(expensesOrgList);
			while(expensesOrgLocationId.next()){
				 Long locationId = expensesOrgLocationId.getLong("LOCATION_ID");
				 System.out.println(locationId);
				 if(locationId != null) {
					 String orgShortCode ="SELECT GENERATED_USER_ID from "+dbconfig.getSchema()+".ORG_LOCATIONS orgloc where orgloc.ID = "+locationId+"";
					 ResultSet generatedUserId = stmt1.executeQuery(orgShortCode);
					 while(generatedUserId.next()){
					 String  generatedUserCode = generatedUserId.getString("GENERATED_USER_ID");
					 String expenses ="SELECT ID,EXPENSE_ID from "+dbconfig.getSchema()+".EXPENSES expenses where expenses.LOCATION_ID = "+locationId+"";
					 ResultSet expensesIdList = stmt2.executeQuery(expenses);
					 while(expensesIdList.next()){
						 Long  Id = expensesIdList.getLong("ID");
						 String  expenseId = expensesIdList.getString("EXPENSE_ID");
						 final DecimalFormat decimalFormat = new DecimalFormat("0");                
					     String convetredExpId = decimalFormat.format(Long.parseLong(expenseId));
						 StringBuilder genereatedShortCode = new StringBuilder();
						 genereatedShortCode.append(generatedUserCode).append("EP").append(convetredExpId);
						 String query = "update "+dbconfig.getSchema()+".EXPENSES set EXPENSE_ID = ? where ID = ? ";
						 pstmt = con.prepareStatement(query); 
					     pstmt.setString(1,genereatedShortCode.toString());
					     pstmt.setLong(2, Id);
					     pstmt.executeUpdate();
					 	}
					 }
				 }
			 }
	 }catch(Exception e) {
		 e.printStackTrace(); 
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
		
public static void main(String[] args) {
	AlterExpenseShortCode alertExpenseCode = new AlterExpenseShortCode();
	alertExpenseCode.AlterExpenseShortCodeBasedOnOrgLocationId();
}
	
}