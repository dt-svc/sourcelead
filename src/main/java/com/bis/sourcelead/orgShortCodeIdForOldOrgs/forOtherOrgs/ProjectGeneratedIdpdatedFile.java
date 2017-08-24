package com.bis.sourcelead.orgShortCodeIdForOldOrgs.forOtherOrgs;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ProjectGeneratedIdpdatedFile {

	private ComboPooledDataSource comboPooledDataSource;
	
	
	public void addCandidateGeneratedId() throws IOException, SQLException {
		Connection conn = null ;
		java.sql.PreparedStatement pstmt = null;
		Statement stmt1 = null;
		Statement stmt2 = null;
		try {
			
			DataSource dataSource = comboPooledDataSource();
			conn = dataSource.getConnection();
			// String candGeneratedId = null;
			 String finalProjectsGeneratedId = null;
				 String sql1 = "SELECT DISTINCT projects.ORG_LOCATION_ID FROM sourcelead.PROJECTS projects GROUP BY projects.ORG_LOCATIONS_ID,projects.ID";
				 stmt1 = conn.createStatement();
				 stmt2 = conn.createStatement();
				 ResultSet rs1 = stmt1.executeQuery(sql1);
				while(rs1.next()){
					 
					 Long  projectsOrgLocId = rs1.getLong("ORG_LOCATION_ID");
					
					 String queryOrg = "SELECT * FROM sourcelead.PROJECTS projects where projects.ORG_LOCATIONS_ID= "+projectsOrgLocId+"";
					 
					 ResultSet rs2 = stmt2.executeQuery(queryOrg);
					 Long projectGeneratedId = 0L;
					 while(rs2.next()){
						 Long  projectId = rs2.getLong("ID");
						 projectGeneratedId = projectGeneratedId + 1;
						 final DecimalFormat decimalFormat = new DecimalFormat("0");                
					        String ConvetredProjectsId = decimalFormat.format(projectGeneratedId);
					        
					        StringBuffer sb = new StringBuffer();
					        finalProjectsGeneratedId = sb.append(Constants.PROJECT_ENTITY).append(ConvetredProjectsId).toString();
				    
							 String query = "update sourcelead.PROJECTS set GENERATED_ID = ? where ID = ? ";
							 pstmt = conn.prepareStatement(query); // create a statement
						     pstmt.setString(1,finalProjectsGeneratedId);
						     pstmt.setLong(2, projectId); // set input parameter 2
						     pstmt.executeUpdate();
					
				   //  conn.commit();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
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
		ProjectGeneratedIdpdatedFile dump = new ProjectGeneratedIdpdatedFile();
		dump.addCandidateGeneratedId();
	}
}
