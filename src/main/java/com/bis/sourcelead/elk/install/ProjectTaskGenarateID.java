package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ProjectTaskGenarateID {
	

	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Laxman
 * this method is using for update the RequirmentUUID	
 * @throws IOException
 * @throws SQLException
 */
public List<Long> projectShortCode() throws IOException, SQLException {
	Connection conn = null ;
	PreparedStatement stmt1 = null;
	PreparedStatement stmt2 = null;
try {
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			 String sql = "SELECT * FROM "+dbconfig.getSchema()+".PROJECT_TASKS";
			 stmt1 = conn.prepareStatement(sql);
			 ResultSet rs = stmt1.executeQuery();
			 while(rs.next()){
				 Long projectId = rs.getLong("PROJECT_ID");
				 if(projectId != null && projectId != 0) {
					 String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".PROJECTS ps where ps.ID = "+rs.getLong("PROJECT_ID")+" ";
					 stmt2 = conn.prepareStatement(sql1);
					 ResultSet rs1 = stmt2.executeQuery();
					 while(rs1.next()){
						 Long OrgLocationId  = rs1.getLong("ORG_LOCATIONS_ID");
						 String projrctNameShortCode  = rs1.getString("PROJECT_SHORT_CODE");
						 generatedIdForTasks(projrctNameShortCode,projectId ,OrgLocationId,conn);
					 }
				 }
				
			 }
		
	}catch(Exception e){
		e.printStackTrace();
	}finally {
		try {
			stmt1.close();
			stmt2.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
return null;
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
	ProjectTaskGenarateID dump = new ProjectTaskGenarateID();
	dump.projectShortCode();
}

private  String generatedIdForTasks(String projrctNameShortCode, Long projectId, Long orgLocationId, Connection conn) {
	PreparedStatement stmt1 = null;
	PreparedStatement stmt2 = null;
	String generatedTaskName = null;
try {
		int count = 1;
		 String sql = "SELECT * FROM "+dbconfig.getSchema()+".PROJECT_TASKS pt where pt.PROJECT_ID = "+projectId+"";
		 stmt1 = conn.prepareStatement(sql);
		 ResultSet rs = stmt1.executeQuery();
		 while(rs.next()){
			 String generatedId = rs.getString("GENERATED_ID");
			 Long Id = rs.getLong("ID");
			 if(generatedId == null) {
				 generatedTaskName = projrctNameShortCode+count;
				 String sql2 = "update "+dbconfig.getSchema()+".PROJECT_TASKS pt , "+dbconfig.getSchema()+".PROJECTS pr set pt.GENERATED_ID = ? where pt.PROJECT_ID= ? and pt.PROJECT_ID = pr.ID and pr.ORG_LOCATIONS_ID = ? and pt.ID=?";
				 stmt2 = conn.prepareStatement(sql2);
				 stmt2.setString(1, generatedTaskName);
				 stmt2.setLong(2, projectId);
				 stmt2.setLong(3, orgLocationId);
				 stmt2.setLong(4, Id);
				 stmt2.executeUpdate();
			 }
			 count++;
		 }
		
	}catch(Exception e){
		e.printStackTrace();
	}finally {
		try {
			stmt1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
return generatedTaskName;
}
}
