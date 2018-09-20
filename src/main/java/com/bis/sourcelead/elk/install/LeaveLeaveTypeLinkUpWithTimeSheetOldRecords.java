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
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class LeaveLeaveTypeLinkUpWithTimeSheetOldRecords {
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
	
public void LeaveLeaveTypeLinkUpWithTimeSheet() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	Statement stmt2 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			
		
			 String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".LEAVE_TYPE;";
			 stmt1 = conn.createStatement();
			 stmt2 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			 while(rs1.next()){
				 Long  orgLocationId = rs1.getLong("ORGLOCATION_ID");
				 String projects ="SELECT * FROM "+dbconfig.getSchema()+".PROJECTS ps WHERE ps.id IN (select pc.PROJECT_ID from  sourcelead.PROJECT_CLIENTS pc where pc.ORG_LOCATIONS_ID = "+orgLocationId+");";
				 ResultSet rs2 = stmt2.executeQuery(projects);
				 while(rs2.next()){
					 	String projectTasksUUID = SourceLeadRandomCodeHelper.generateRandomUUID();
						String TaskName = rs1.getString("NAME");
						String description = rs1.getString("NAME");
						String taskType = rs1.getString("LEAVE_TYPE");
						String tasks = null;
						if(taskType.equalsIgnoreCase("Non Billable")) {
							tasks = "Non-Billable";
						}else {
							tasks = "Billable";
						}
						Long tasksProjectId = rs2.getLong("ID");
						Long TaskType = 1060L;
						Long leaveTypeId = rs1.getLong("ID");
						Long TaskStatus = 1069L;
						Integer Status = 1;
						String CreatedBy = "SLADMIN";
						Date createdDate = new Date(new java.util.Date().getTime());
						String UpdatedBy = "SLADMIN";
						Date updatedDate = new Date(new java.util.Date().getTime());
						
						String projectTasks = "INSERT INTO `"+dbconfig.getSchema()+"`.`PROJECT_TASKS` (PROJECT_TASKS_UUID, TASK_NAME, DESCRIPTION, BILLABLE_TYPE, PROJECT_ID, TASK_TYPE, LEAVE_TYPE_ID,TASK_STATUS,STATUS, CREATED_DATE, CREATED_BY, UPDATED_DATE, UPDATED_BY) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
						try {
			    			 PreparedStatement pstmt2 = conn.prepareStatement(projectTasks);
				    		 pstmt2.setString(1, projectTasksUUID);
				    		 pstmt2.setString(2,TaskName);
				    		 pstmt2.setString(3, description);
				    		 pstmt2.setString(4, tasks);
				    		 pstmt2.setLong(5,tasksProjectId);
				    		 pstmt2.setLong(6,TaskType);
				    		 pstmt2.setLong(7,leaveTypeId);
				    		 pstmt2.setLong(8,TaskStatus);
				    		 pstmt2.setInt(9,Status);
				    		 pstmt2.setDate(10,createdDate);
				    		 pstmt2.setString(11,CreatedBy);
				    		 pstmt2.setDate(12,updatedDate);
				    		 pstmt2.setString(13,UpdatedBy);
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
	LeaveLeaveTypeLinkUpWithTimeSheetOldRecords oldRecords = new LeaveLeaveTypeLinkUpWithTimeSheetOldRecords();
	oldRecords.LeaveLeaveTypeLinkUpWithTimeSheet();
}

}
