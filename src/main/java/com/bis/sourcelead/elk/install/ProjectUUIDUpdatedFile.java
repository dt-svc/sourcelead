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
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ProjectUUIDUpdatedFile {
		private ComboPooledDataSource comboPooledDataSource;
		
	/**
	 * @author Venkat
	 * this method is using for update the ProjectUUID	
	 * @throws IOException
	 * @throws SQLException
	 */
	public void addProjectUUID() throws IOException, SQLException {
		Connection conn = null ;
		Statement stmt1 = null;
		try {
			
			DataSource dataSource = comboPooledDataSource();
			conn = dataSource.getConnection();
				 String sql1 = "SELECT * FROM sourcelead.PROJECTS projects";
				 stmt1 = conn.createStatement();
				 ResultSet rs1 = stmt1.executeQuery(sql1);
				while(rs1.next()){
					 Long  resumeId = rs1.getLong("ID");
					
					 String query = "update sourcelead.PROJECTS set PROJECT_UUID = ? where ID = ? ";
				     java.sql.PreparedStatement pstmt = conn.prepareStatement(query); // create a statement
				      pstmt.setString(1, SourceLeadRandomCodeHelper.generateRandomUUID()); // set input parameter 1
				      pstmt.setLong(2, resumeId); // set input parameter 2
				      pstmt.executeUpdate(); 
				      
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
		ProjectUUIDUpdatedFile dump = new ProjectUUIDUpdatedFile();
		dump.addProjectUUID();
	}

}