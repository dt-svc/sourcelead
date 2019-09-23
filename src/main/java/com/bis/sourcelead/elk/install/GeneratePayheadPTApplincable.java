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

public class GeneratePayheadPTApplincable {

	private ComboPooledDataSource comboPooledDataSource;
	
/**
 * @author
 * this method is using for update the UUID for Payroll Payhead
 * @throws IOException
 * @throws SQLException
 */
public void generatePTApplicable() throws IOException, SQLException {
	Connection conn = null ;
	PreparedStatement stmt1 = null;
try {
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			 String sql = "SELECT * FROM sourcelead.PAYROLL_PAYHEAD WHERE TYPE='Earnings'";
			 stmt1 = conn.prepareStatement(sql);
			 ResultSet rs = stmt1.executeQuery();
			 System.out.println("----Adding PT_APPLICABLE PAYROLL PAYHEAD starts-----");
			 while(rs.next()){
				 Long id = rs.getLong("ID");
					 String pt = "Y"; 
					 String query = "update sourcelead.PAYROLL_PAYHEAD set PT_APPLICABLE = ? where ID = ?";
					 PreparedStatement pstmt = conn.prepareStatement(query);
					 pstmt.setString(1, pt);
					 pstmt.setLong(2, id);
					 pstmt.executeUpdate(); 
					 
					 System.out.println(">>>Adding PT_APPLICABLE for PAYROLL_PAYHEAD "+id+" Done.");
			 }
			 
			 System.out.println("----Adding PT_APPLICABLE PAYROLL PAYHEAD ends-----");
		
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
		@SuppressWarnings("resource")
		ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
		DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);

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
	GeneratePayheadPTApplincable dump = new GeneratePayheadPTApplincable();
	dump.generatePTApplicable();
}


}
