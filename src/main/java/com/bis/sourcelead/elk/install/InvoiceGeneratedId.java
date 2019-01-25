package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.Year;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class InvoiceGeneratedId{

	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
	
	public void addInvoiceGeneratedId() throws IOException, SQLException {
		Connection conn = null ;
		java.sql.PreparedStatement pstmt = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		Statement stmt4 = null;
		
		try {
			DataSource dataSource = comboPooledDataSource();
			conn = dataSource.getConnection();
			 String finalJobsGeneratedId = null;
				 stmt2 = conn.createStatement();
				 stmt3 = conn.createStatement();
				 stmt4 = conn.createStatement();
				 
				 
					 String organisationShortCode = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS orglocations";
					 
					 ResultSet rSetorgShortCode = stmt3.executeQuery(organisationShortCode);
					 Year year = Year.now();
					 System.out.println("------- Starting -------");
					 while(rSetorgShortCode.next()){
						 Long  jobsOrgLocIds = rSetorgShortCode.getLong("ID");
						 String  orgShortCode = rSetorgShortCode.getString("GENERATED_USER_ID");
						 if(orgShortCode != null && orgShortCode != ""){
							 String queryOrg = "SELECT * FROM "+dbconfig.getSchema()+".INVOICE invoice where invoice.ORG_LOCATION_ID= "+jobsOrgLocIds+"";
							 ResultSet rs2 = stmt2.executeQuery(queryOrg);
							 Long JobsGeneratedId = 0L;
							 while(rs2.next()){
							     Long vendorLocId =  rs2.getLong("RESPONDER_LOCATION_ID");
							     //client or vendor location GENERATED_USER_ID
								 String vendorOrgShortCode = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS orglocations WHERE ID = "+vendorLocId+"";
								 ResultSet vndorShortCode = stmt4.executeQuery(vendorOrgShortCode);
								  vndorShortCode.next();
								  String venodrShorCode = vndorShortCode.getString("GENERATED_USER_ID");
								 Long  invoiceId = rs2.getLong("ID");
								 JobsGeneratedId = JobsGeneratedId + 1;
								 final DecimalFormat decimalFormat = new DecimalFormat("0");                
							        String ConvetredJobId = decimalFormat.format(JobsGeneratedId);
							        
							        StringBuffer sb = new StringBuffer();
							        finalJobsGeneratedId = sb.append(orgShortCode.toUpperCase()).append("-").append(venodrShorCode.toUpperCase()).append("-").append(year).append("-").append(ConvetredJobId).toString();
						    
									 String query = "update "+dbconfig.getSchema()+".INVOICE set GENERATED_ID = ? where ID = ? ";
									 pstmt = conn.prepareStatement(query); // create a statement
								     pstmt.setString(1,finalJobsGeneratedId);
								     pstmt.setLong(2, invoiceId);
								     pstmt.executeUpdate();
								     System.out.println("------ updating invoice-------"+invoiceId);
						   //  conn.commit();
						}
					}
				}
					 System.out.println("------- Completed -------");
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
		InvoiceGeneratedId dump = new InvoiceGeneratedId();
		dump.addInvoiceGeneratedId();
	}
}
