package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class CandidateGeneratedIdpdatedFileForBusinessIntelliUSAndIND{

	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
	
	public void addCandidateGeneratedId() throws IOException, SQLException {
		Connection conn = null ;
		java.sql.PreparedStatement pstmt = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		
		Long businessIntelliIndiaOrgId = 5l;
		Long businessIntelliUsaOrgId = 3l;
		
		List<Long> bisOrgId = new ArrayList<>();
		bisOrgId.add(businessIntelliUsaOrgId);
		bisOrgId.add(businessIntelliIndiaOrgId);
		try {
			DataSource dataSource = comboPooledDataSource();
			conn = dataSource.getConnection();
			 String finalCandidateGeneratedId = null;
				 stmt2 = conn.createStatement();
				 stmt3 = conn.createStatement();
				 
				 
				 for(Long candidatesOrgLocIds :bisOrgId){
					 String organisationShortCode = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS orglocations where orglocations.id = "+candidatesOrgLocIds+" ";
					 
					 ResultSet rSetorgShortCode = stmt3.executeQuery(organisationShortCode);
					 while(rSetorgShortCode.next()){
						 String  orgShortCode = rSetorgShortCode.getString("GENERATED_USER_ID");
						 System.out.println("orgShortCode :"+orgShortCode);
						 if(orgShortCode != null && orgShortCode != ""){
							 String queryOrg = "SELECT * FROM "+dbconfig.getSchema()+".CANDIDATE candidate where candidate.ORG_LOCATION_ID= "+candidatesOrgLocIds+"";
							 ResultSet rs2 = stmt2.executeQuery(queryOrg);
							 Long candidateGeneratedId = 0L;
							 while(rs2.next()){
								 Long  candidateId = rs2.getLong("ID");
								 candidateGeneratedId = candidateGeneratedId + 1;
								 final DecimalFormat decimalFormat = new DecimalFormat("0");                
							        String ConvetredCandidateId = decimalFormat.format(candidateGeneratedId);
							        
							        StringBuffer sb = new StringBuffer();
							        finalCandidateGeneratedId = sb.append(orgShortCode.toUpperCase()).append(Constants.CANDIDATE_ENTITY).append(ConvetredCandidateId).toString();
						    
									 String query = "update "+dbconfig.getSchema()+".CANDIDATE set GENERATED_ID = ? where ID = ? ";
									 pstmt = conn.prepareStatement(query); // create a statement
								     pstmt.setString(1,finalCandidateGeneratedId);
								     pstmt.setLong(2, candidateId);
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
		CandidateGeneratedIdpdatedFileForBusinessIntelliUSAndIND dump = new CandidateGeneratedIdpdatedFileForBusinessIntelliUSAndIND();
		dump.addCandidateGeneratedId();
	}
}
