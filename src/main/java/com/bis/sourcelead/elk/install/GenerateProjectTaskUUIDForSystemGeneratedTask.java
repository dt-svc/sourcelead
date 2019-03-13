package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class GenerateProjectTaskUUIDForSystemGeneratedTask {
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);

	/**
	 * @author shivayogi
	 * this method is using for update the UUID to project tasks	
	 * @throws IOException
	 * @throws SQLException
	 */
	public void projectTaskUUID() throws IOException, SQLException {
		Connection conn = null ;
		PreparedStatement stmt1 = null;
		
		List<String> taskName =  new ArrayList<>();
		taskName.add("System Task Billable,Billable");
		taskName.add("System Task NonBillable,Non-Billable");

		try {
			DataSource dataSource = comboPooledDataSource();
			conn = dataSource.getConnection();
			for(String task:taskName) {
				String taskNameArray[]  = task.split(",");
				 String sql = "SELECT distinct PROJECT_ID FROM "+dbconfig.getSchema()+".PROJECT_TASKS";
				 stmt1 = conn.prepareStatement(sql);
				 ResultSet rs = stmt1.executeQuery();
				 while(rs.next()){
					 Long projectId = rs.getLong("PROJECT_ID");
					 
					 if(projectId != null && projectId > 0) {
					 String uuid = SourceLeadRandomCodeHelper.generateRandomUUID(); 
					 String query = " INSERT INTO "+dbconfig.getSchema()+".PROJECT_TASKS(`PROJECT_TASKS_UUID`,`TASK_NAME`, `DESCRIPTION`, `BILLABLE_TYPE`,`PROJECT_ID`,`TASK_TYPE`,`TASK_STATUS`,`STATUS`) VALUES (?,?,?,?,?,?,?,?)";
					 PreparedStatement pstmt = conn.prepareStatement(query);
					 pstmt.setString(1, uuid);
					 pstmt.setString(2, taskNameArray[0]);
					 pstmt.setString(3, taskNameArray[0]);
					 pstmt.setString(4, taskNameArray[1]);
					 pstmt.setLong(5, projectId);
					 pstmt.setLong(6, 1060);
					 pstmt.setLong(7, 1069);
					 pstmt.setString(8, "1");
					 pstmt.executeUpdate();
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
		GenerateProjectTaskUUIDForSystemGeneratedTask dump = new GenerateProjectTaskUUIDForSystemGeneratedTask();
		dump.projectTaskUUID();
	}

}
