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

public class GenerateUniqueIdForLeaves {

	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Pavan Jampana
 * this method is using for update the UUID for Connections
 * @throws IOException
 * @throws SQLException
 */
public void LeaveUUID() throws IOException, SQLException {
	Connection conn = null ;
	PreparedStatement stmt1 = null;
try {
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			 String sql = "SELECT * FROM "+dbconfig.getSchema()+".USER_LEAVE";
			 stmt1 = conn.prepareStatement(sql);
			 ResultSet rs = stmt1.executeQuery();
			 System.out.println("----Adding unique UUID leave  starts-----");
			 while(rs.next()){
				 String UUID = rs.getString("LEAVE_UUID");
				 Long id = rs.getLong("ID");
				 if(UUID == null) {
					 String uuid = SourceLeadRandomCodeHelper.generateRandomUUID(); 
					 String query = "update "+dbconfig.getSchema()+".USER_LEAVE set LEAVE_UUID = ? where ID = ?";
					 PreparedStatement pstmt = conn.prepareStatement(query);
					 pstmt.setString(1, uuid);
					 pstmt.setLong(2, id);
					 pstmt.executeUpdate(); 
				 }
			 }
			 stmt1.close();
			 
			 System.out.println("----Adding unique UUID org leave ends-----");
			 
			 
			 String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".LEAVE_OPTION";
			 PreparedStatement pstmt1 = conn.prepareStatement(sql1);
			 ResultSet rs1 = pstmt1.executeQuery();
			 System.out.println("----Adding unique UUID leave option starts-----");
			 while(rs1.next()){
				 String UUID = rs1.getString("LEAVE_UUID");
				 Long id = rs1.getLong("ID");
				 if(UUID == null) {
					 String uuid = SourceLeadRandomCodeHelper.generateRandomUUID(); 
					 String query = "update "+dbconfig.getSchema()+".LEAVE_OPTION set OPTION_UUID = ? where ID = ?";
					 PreparedStatement pstmt = conn.prepareStatement(query);
					 pstmt.setString(1, uuid);
					 pstmt.setLong(2, id);
					 pstmt.executeUpdate(); 
				 }
			 }
			 pstmt1.close();
			 
			 System.out.println("----Adding unique UUID org leave ends-----");
			 
			 String typeSql = "SELECT * FROM "+dbconfig.getSchema()+".LEAVE_TYPE";
			 PreparedStatement pstmt2 = conn.prepareStatement(typeSql);
			 ResultSet typers = pstmt2.executeQuery();
			 System.out.println("----Adding unique UUID leave type starts-----");
			 while(typers.next()){
				 String UUID = typers.getString("LEAVE_TYPE_UUID");
				 Long id = typers.getLong("ID");
				 if(UUID == null) {
					 String uuid = SourceLeadRandomCodeHelper.generateRandomUUID(); 
					 String query = "update "+dbconfig.getSchema()+".LEAVE_TYPE set LEAVE_TYPE_UUID = ? where ID = ?";
					 PreparedStatement pstmt = conn.prepareStatement(query);
					 pstmt.setString(1, uuid);
					 pstmt.setLong(2, id);
					 pstmt.executeUpdate(); 
				 }
			 }
			 pstmt2.close();
			 
			 System.out.println("----Adding unique UUID org leave type ends-----");
			 
			 
			 String shiftSql = "SELECT * FROM "+dbconfig.getSchema()+".SHIFT";
			 PreparedStatement pstmt3 = conn.prepareStatement(shiftSql);
			 ResultSet shiftrs = pstmt3.executeQuery();
			 System.out.println("----Adding unique UUID leave shift starts-----");
			 while(shiftrs.next()){
				 String UUID = shiftrs.getString("SHIFT_UUID");
				 Long id = shiftrs.getLong("ID");
				 if(UUID == null) {
					 String uuid = SourceLeadRandomCodeHelper.generateRandomUUID(); 
					 String query = "update "+dbconfig.getSchema()+".SHIFT set SHIFT_UUID = ? where ID = ?";
					 PreparedStatement pstmt = conn.prepareStatement(query);
					 pstmt.setString(1, uuid);
					 pstmt.setLong(2, id);
					 pstmt.executeUpdate(); 
				 }
			 }
			 pstmt3.close();
			 
			 System.out.println("----Adding unique UUID org leave shift ends-----");
	
			 
			 String holidaySql = "SELECT * FROM "+dbconfig.getSchema()+".HOLIDAY";
			 PreparedStatement pstmt4 = conn.prepareStatement(holidaySql);
			 ResultSet holidayrs = pstmt4.executeQuery();
			 System.out.println("----Adding unique UUID leave holiday starts-----");
			 while(holidayrs.next()){
				 String UUID = holidayrs.getString("HOLIDAY_UUID");
				 Long id = holidayrs.getLong("ID");
				 if(UUID == null) {
					 String uuid = SourceLeadRandomCodeHelper.generateRandomUUID(); 
					 String query = "update "+dbconfig.getSchema()+".HOLIDAY set HOLIDAY_UUID = ? where ID = ?";
					 PreparedStatement pstmt = conn.prepareStatement(query);
					 pstmt.setString(1, uuid);
					 pstmt.setLong(2, id);
					 pstmt.executeUpdate(); 
				 }
			 }
			 pstmt4.close();
			 
			 System.out.println("----Adding unique UUID org leave holiday ends-----");
			 
			 
			 String restrictSql = "SELECT * FROM "+dbconfig.getSchema()+".RESTRICT_DAY";
			 PreparedStatement pstmt5 = conn.prepareStatement(restrictSql);
			 ResultSet restrictrs = pstmt5.executeQuery();
			 System.out.println("----Adding unique UUID leave restrict day starts-----");
			 while(restrictrs.next()){
				 String UUID = restrictrs.getString("RESTRICT_UUID");
				 Long id = restrictrs.getLong("ID");
				 if(UUID == null) {
					 String uuid = SourceLeadRandomCodeHelper.generateRandomUUID(); 
					 String query = "update "+dbconfig.getSchema()+".RESTRICT_DAY set RESTRICT_UUID = ? where ID = ?";
					 PreparedStatement pstmt = conn.prepareStatement(query);
					 pstmt.setString(1, uuid);
					 pstmt.setLong(2, id);
					 pstmt.executeUpdate(); 
				 }
			 }
			 pstmt5.close();
			 
			 System.out.println("----Adding unique UUID org leave restrict day ends-----");
			 
			 
	}catch(Exception e){
		e.printStackTrace();
	}finally {
		try {
//			stmt1.close();
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
	GenerateUniqueIdForLeaves dump = new GenerateUniqueIdForLeaves();
	dump.LeaveUUID();
}



}
