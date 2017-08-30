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

public class JobsGeneratedIdpdatedFile {

	private ComboPooledDataSource comboPooledDataSource;
	
	
	public void addCandidateGeneratedId() throws IOException, SQLException {
		Connection conn = null ;
		java.sql.PreparedStatement pstmt = null;
		Statement stmt1 = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		
		try {
			DataSource dataSource = comboPooledDataSource();
			conn = dataSource.getConnection();
			// String candGeneratedId = null;
			 String finalJobsGeneratedId = null;
				 String sql1 = "SELECT DISTINCT requirement.ORG_LOCATION_ID FROM sourcelead.REQUIREMENT requirement GROUP BY requirement.ORG_LOCATION_ID,requirement.ID";
				 stmt1 = conn.createStatement();
				 stmt2 = conn.createStatement();
				 stmt3 = conn.createStatement();
				 ResultSet rs1 = stmt1.executeQuery(sql1);
				 while(rs1.next()){
					 Long  jobsOrgLocId = rs1.getLong("ORG_LOCATION_ID");
					 String organisationShortCode = "SELECT * FROM sourcelead.ORG_LOCATIONS orglocations where orglocations.id = "+jobsOrgLocId+"";
					 ResultSet rSetorgShortCode = stmt3.executeQuery(organisationShortCode);
					 while(rSetorgShortCode.next()){
						 //Long orgId = rSetorgShortCode.getLong("id");
						 String  orgShortCode = rSetorgShortCode.getString("GENERATED_USER_ID");
						 System.out.println("orgShortCode :"+orgShortCode);
						 if(orgShortCode != null && orgShortCode != ""){
							 String queryOrg = "SELECT * FROM sourcelead.REQUIREMENT requirement where requirement.ORG_LOCATION_ID= "+jobsOrgLocId+"";
							 ResultSet rs2 = stmt2.executeQuery(queryOrg);
							 Long JobsGeneratedId = 0L;
							 while(rs2.next()){
								 Long  jobsId = rs2.getLong("ID");
								 JobsGeneratedId = JobsGeneratedId + 1;
								 final DecimalFormat decimalFormat = new DecimalFormat("0");                
							        String ConvetredJobId = decimalFormat.format(JobsGeneratedId);
							        
							        StringBuffer sb = new StringBuffer();
							        finalJobsGeneratedId = sb.append(orgShortCode.toUpperCase()).append(Constants.JOB_ENTITY).append(ConvetredJobId).toString();
						    
									 String query = "update sourcelead.REQUIREMENT set GENERATED_ID = ? where ID = ? ";
									 pstmt = conn.prepareStatement(query); // create a statement
								     pstmt.setString(1,finalJobsGeneratedId);
								     pstmt.setLong(2, jobsId);
								     pstmt.executeUpdate();
						   //  conn.commit();
						}
					}
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
		JobsGeneratedIdpdatedFile dump = new JobsGeneratedIdpdatedFile();
		dump.addCandidateGeneratedId();
	}
}
