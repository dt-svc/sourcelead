package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.elk.index.ProjectsIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class AddProjectsELK {
	private static final Logger logger = LoggerFactory.getLogger(AddProjectsELK.class);
	private ComboPooledDataSource comboPooledDataSource;
	 private SourceleadELKBaseIndexer<ProjectsIndex> client;
	 ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
		DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
	 
	 protected boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}
	 
	public void addProjectsELK() throws IOException, SQLException {
		ArrayList<ProjectsIndex> elkProjectsList = getElkProjects();
		if(elkProjectsList != null && !elkProjectsList.isEmpty()){
			for (ProjectsIndex projectsIndex :elkProjectsList) {
				client.saveOrUpdateElkDocument(projectsIndex, Constants.ELK_PROJECT_INDEX, Constants.ELK_PROJECT_TYPE,projectsIndex.getId().toString());
			}
		}
		  logger.info("Projectss Successfully Added To Elk");
	}
	
	private ArrayList<ProjectsIndex> getElkProjects(){
		 DataSource dataSource = comboPooledDataSource();
		ArrayList<ProjectsIndex> projectsIndexsList = new ArrayList<ProjectsIndex>();
		 try {
				String sql = "SELECT * FROM "+dbconfig.getSchema()+".PROJECTS projects";
				Connection conn = dataSource.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
			    while(rs.next()){
			    	ProjectsIndex projectsIndex = new ProjectsIndex();
			    	
			    	if(!isNullOrEmpty(rs.getString("ID"))){
			    		projectsIndex.setId(rs.getLong("ID"));
					}
			    	if(!isNullOrEmpty(rs.getString("PROJECT_SEQUENCE_NUMBER"))){
			    		projectsIndex.setProjectSequeneNumber(rs.getString("PROJECT_SEQUENCE_NUMBER"));
					}
					projectsIndex.setProjectName(rs.getString("PROJECT_NAME"));
					projectsIndex.setStartDate((rs.getString("START_DATE")));
					if(!isNullOrEmpty(rs.getString("END_DATE"))){
						projectsIndex.setEndDate((rs.getString("END_DATE")));
					}
					if(!isNullOrEmpty(rs.getString("INVOICE_FREQUENCY"))){
						projectsIndex.setInvoiceFrequency(rs.getString("INVOICE_FREQUENCY"));
					}
					if(!isNullOrEmpty(rs.getString("PROJECT_STATUS"))){
						projectsIndex.setProjectStatus(rs.getString("PROJECT_STATUS"));
					}
					if(!isNullOrEmpty(rs.getString("PROJECT_UUID"))){
			    		projectsIndex.setProjectUUID(rs.getString("PROJECT_UUID"));
					}
					
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					projectsIndex.setCreatedDate(dateFormat.format(rs.getDate("CREATED_DATE")));
					projectsIndex.setUpdatedDate(dateFormat.format(rs.getDate("UPDATED_DATE")));
					
					Long projectId = rs.getLong("ID");
		    	    if(projectId != null && projectId != 0){
		    	    		String sqlManager = "SELECT * FROM "+dbconfig.getSchema()+".PROJECT_APPROVER_HIERARCHY projectsApprovers where projectsApprovers.PROJECT_ID = '"+ projectId+"' ";
		    	    		Statement stmtManager = conn.createStatement();
		    	    		ResultSet rsManager = stmtManager.executeQuery(sqlManager);
							if(rsManager != null){
								while(rsManager.next()){
									if(rsManager.getInt("LEVEL") == 1){
										Long rsManagerUserId = rsManager.getLong("APPROVER_USER_ID");
										String sqlManagerUser = "SELECT * FROM "+dbconfig.getSchema()+".USER user where user.ID = '"+ rsManagerUserId+"' ";
					    	    		Statement stmtManagerUser = conn.createStatement();
					    	    		ResultSet rsManagerUser = stmtManagerUser.executeQuery(sqlManagerUser);
					    	    		while (rsManagerUser.next()) {
											if(null != rsManagerUser.getString("LAST_NAME") && !rsManagerUser.getString("LAST_NAME").isEmpty()){
												projectsIndex.setLevel1ManagerName(rsManagerUser.getString("FIRST_NAME")+ " " +rsManagerUser.getString("LAST_NAME"));
											}else{
												projectsIndex.setLevel1ManagerName(rsManagerUser.getString("FIRST_NAME"));
											}
					    	    		}
									}
									if(rsManager.getInt("LEVEL") == 2){
										Long rsManagerUserId = rsManager.getLong("APPROVER_USER_ID");
										String sqlManagerUser = "SELECT * FROM "+dbconfig.getSchema()+".USER user where user.ID = '"+ rsManagerUserId+"' ";
					    	    		Statement stmtManagerUser = conn.createStatement();
					    	    		ResultSet rsManagerUser = stmtManagerUser.executeQuery(sqlManagerUser);
					    	    		while (rsManagerUser.next()) {
											if(null != rsManagerUser.getString("LAST_NAME") && !rsManagerUser.getString("LAST_NAME").isEmpty()){
												projectsIndex.setLevel2ManagerName(rsManagerUser.getString("FIRST_NAME")+ " " +rsManagerUser.getString("LAST_NAME"));
											}else{
												projectsIndex.setLevel2ManagerName(rsManagerUser.getString("FIRST_NAME"));
											}
					    	    		}
									}
							    }
		    	            }
							
							String sqlprojectMembers = "SELECT * FROM "+dbconfig.getSchema()+".PROJECT_MEMBERS projectMembers where projectMembers.PROJECT_ID = '"+ projectId+"' ";
		    	    		Statement stmtprojectMembers = conn.createStatement();
		    	    		ResultSet rsprojectMembers = stmtprojectMembers.executeQuery(sqlprojectMembers);
							if(rsprojectMembers != null){
								ArrayList<String> memberName = new ArrayList<>();
								ArrayList<String> memberEmail = new ArrayList<>();
								while(rsprojectMembers.next()){
									Long rsMemberEmpId = rsprojectMembers.getLong("EMPLOYEE_ID");
									String sqlMemberEmp = "SELECT * FROM "+dbconfig.getSchema()+".EMPLOYEE emp where emp.ID = '"+ rsMemberEmpId+"' ";
				    	    		Statement stmtMemberEmp = conn.createStatement();
				    	    		ResultSet rsMemberEmp = stmtMemberEmp.executeQuery(sqlMemberEmp);
				    	    		
				    	    		while(rsMemberEmp.next()){
					    	    		Long rsMemberUserId = rsMemberEmp.getLong("USER_ID");
										String sqlMemberUser = "SELECT * FROM "+dbconfig.getSchema()+".USER user where user.ID = '"+ rsMemberUserId+"' ";
					    	    		Statement stmtMemberUser = conn.createStatement();
					    	    		ResultSet rsMemberUser = stmtMemberUser.executeQuery(sqlMemberUser);
					    	    		while(rsMemberUser.next()){
											if(!isNullOrEmpty(rsMemberUser.getString("LAST_NAME"))){
												memberName.add(rsMemberUser.getString("FIRST_NAME")+ " " +rsMemberUser.getString("LAST_NAME"));
											}else{
												memberName.add(rsMemberUser.getString("FIRST_NAME"));
											}
											memberEmail.add(rsMemberUser.getString("EMAIL"));
					    	    		}
				    	    		}
							    }
								if(null != memberName && !memberName.isEmpty()){
							    	projectsIndex.setProjectMembers(memberName);
								}
								if(null != memberEmail && !memberEmail.isEmpty()){
							    	projectsIndex.setProjectMemberEmail(memberEmail);
								}
		    	           }
							
							Long clientOrgRelationId = rs.getLong("ORG_RELATION_ID");
							String sqlClient = "SELECT * FROM "+dbconfig.getSchema()+".ORG_RELATIONS orgRelations where orgRelations.ID = '"+ clientOrgRelationId+"' ";
		    	    		Statement stmtClient = conn.createStatement();
		    	    		ResultSet rsClient = stmtClient.executeQuery(sqlClient);
							if(rsClient != null){
								Long rsClientLocationId = null;
								while (rsClient.next()) {
									if(rsClient.getLong("LOCATION_ID") == rs.getLong("ORG_LOCATIONS_ID")){
										rsClientLocationId = rsClient.getLong("RESPONDER_LOCATION_ID");
									}else{
										rsClientLocationId = rsClient.getLong("LOCATION_ID");
									}
								}
								
								String sqlClientLocation = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS orgLocations where orgLocations.ID = '"+ rsClientLocationId+"' ";
			    	    		Statement stmtClientLocation = conn.createStatement();
			    	    		ResultSet rsClientLocation = stmtClientLocation.executeQuery(sqlClientLocation);
			    	    		
			    	    		if(rsClientLocation != null){
			    	    			while (rsClientLocation.next()) {
				    	    			Long rsClientOrgId = rsClientLocation.getLong("ORG_ID");
					    	    		String sqlClientOrg = "SELECT * FROM "+dbconfig.getSchema()+".ORG org where org.ID = '"+ rsClientOrgId+"' ";
					    	    		Statement stmtClientOrg = conn.createStatement();
					    	    		ResultSet rsClientOrg = stmtClientOrg.executeQuery(sqlClientOrg);
					    	    		if(rsClientOrg != null){
					    	    			while (rsClientOrg.next()) {
					    	    				projectsIndex.setClientName(rsClientOrg.getString("ORG_NAME"));
					    	    			}
					    	    		}
			    	    			}
			    	    		}
			    	    		
		    	            }
		    	    }
					
					projectsIndexsList.add(projectsIndex);
			    }
			   conn.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return projectsIndexsList;
	}
	
	
	@Bean
	public DataSource comboPooledDataSource() {
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
	public static void main(String[] args) throws IOException {
			
		try {
			   AddProjectsELK addProjectsELK = new AddProjectsELK();
			   addProjectsELK.client = new SourceleadELKBaseIndexer<>();
			   addProjectsELK.addProjectsELK();
//			   addProjectsELK.client.shutdown();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
