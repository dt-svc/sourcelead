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

public class GenerateProjectTaskUUID {
	

	private ComboPooledDataSource comboPooledDataSource;
	
/**
 * @author Laxman
 * this method is using for update the RequirmentUUID	
 * @throws IOException
 * @throws SQLException
 */
public List<Long> projectTaskUUID() throws IOException, SQLException {
	Connection conn = null ;
	PreparedStatement stmt1 = null;
try {
		List<Long> list = new ArrayList<>();
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			 String sql = "SELECT * FROM sourcelead.PROJECT_TASKS";
			 stmt1 = conn.prepareStatement(sql);
			 ResultSet rs = stmt1.executeQuery();
			 while(rs.next()){
				 list.add(rs.getLong("ID"));
			 }
			 return list;
		
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
return null;
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
	GenerateProjectTaskUUID dump = new GenerateProjectTaskUUID();
	dump.addUUID(dump.projectTaskUUID());
}

private  void addUUID(List<Long> projectsList) {
	Connection conn = null ;
	PreparedStatement stmt1 = null;
try {
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
		for(Long id : projectsList) {
			 String uuid = SourceLeadRandomCodeHelper.generateRandomUUID(); 
			String sql = "update sourcelead.PROJECT_TASKS set PROJECT_TASKS_UUID = ? where ID = ?";
			 stmt1 = conn.prepareStatement(sql);
			 stmt1.setString(1, uuid);
			 stmt1.setLong(2, id);
			int i = stmt1.executeUpdate();
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
}
