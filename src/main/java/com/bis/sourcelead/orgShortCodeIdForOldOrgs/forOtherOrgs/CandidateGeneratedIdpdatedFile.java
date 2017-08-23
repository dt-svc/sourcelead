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

public class CandidateGeneratedIdpdatedFile {

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
			 String finalCanGeneratedId = null;
				 String sql1 = "SELECT DISTINCT candidate.ORG_LOCATION_ID FROM sourcelead.CANDIDATE candidate GROUP BY candidate.ORG_LOCATION_ID,candidate.ID";
				 stmt1 = conn.createStatement();
				 stmt2 = conn.createStatement();
				 ResultSet rs1 = stmt1.executeQuery(sql1);
				while(rs1.next()){
					 
					 Long  canOrgLocId = rs1.getLong("ORG_LOCATION_ID");
					
					 String queryOrg = "SELECT * FROM sourcelead.CANDIDATE candidate where candidate.ORG_LOCATION_ID= "+canOrgLocId+"";
					 
					 ResultSet rs2 = stmt2.executeQuery(queryOrg);
					 Long candGeneratedId = 0L;
					 while(rs2.next()){
						 Long  canId = rs2.getLong("ID");
						 candGeneratedId = candGeneratedId + 1;
						 final DecimalFormat decimalFormat = new DecimalFormat("000000000");                
					        String ConvetredCandidatesId = decimalFormat.format(candGeneratedId);
					        
					        StringBuffer sb = new StringBuffer();
							 finalCanGeneratedId = sb.append(Constants.CANDIDATE_ENTITY).append(ConvetredCandidatesId).toString();
				    
							 String query = "update sourcelead.CANDIDATE set GENERATED_ID = ? where ID = ? ";
							 pstmt = conn.prepareStatement(query); // create a statement
						     pstmt.setString(1,finalCanGeneratedId);
						     pstmt.setLong(2, canId); // set input parameter 2
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
		CandidateGeneratedIdpdatedFile dump = new CandidateGeneratedIdpdatedFile();
		dump.addCandidateGeneratedId();
	}
}
