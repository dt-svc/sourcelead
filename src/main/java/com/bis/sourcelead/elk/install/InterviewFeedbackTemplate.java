package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class InterviewFeedbackTemplate {
		private ComboPooledDataSource comboPooledDataSource;
		
		
		
		
		@SuppressWarnings("null")
		public void addElkRequirement() throws IOException, SQLException {
			List<SubmissionTemplate> templates = addInterviewTemplate();
			if(null != templates && !templates.isEmpty()){
				
				Connection conn = null ;
				PreparedStatement ps = null;
				String query = " insert into sourcelead.SUBMISSION_TEMPLATE (ID,TEMPLATE_NAME, IS_DEFAULT,CLIENT_LOCATION_ID, ORG_LOCATION_ID, JSON_STRING, DESCRIPTION, STATUS, "
						+ "TEMPLATE_TYPE, CREATED_DATE, CREATED_BY, UPDATED_DATE, UPDATED_BY)"
						+ " values (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				
					try {
						DataSource dataSource = comboPooledDataSource();
						conn = dataSource.getConnection();
						ps = conn.prepareStatement(query);
						ResultSet rs = ps.executeQuery("SELECT COUNT(*)  FROM SUBMISSION_TEMPLATE");
						String count = null;
						while(rs.next()){
						 count  = rs.getString(1);
						}
					    Long i=Long.parseLong(count);
					    Long j=i+1;
						for(SubmissionTemplate cus :templates){
						ps.setLong(1, j);
						ps.setString(2, cus.getTemplateName());
						ps.setString(3, cus.getIsDefault());
						ps.setNull(4, java.sql.Types.INTEGER);
						ps.setLong(5,cus.getOrgLocationId().getId());
						ps.setString(6,cus.getJsonString());
						ps.setString(7,cus.getDescription());
						ps.setInt(8,cus.getStatus());
						ps.setString(9,cus.getTemplateType());
						ps.setDate(10,(java.sql.Date) cus.getCreatedDate());
						ps.setString(11,cus.getCreatedBy());
						ps.setDate(12,(java.sql.Date) cus.getUpdatedDate());
						ps.setString(13, cus.getUpdatedBy());
						ps.executeUpdate();		
					    j++;
						}
					}catch(Exception e){   
						e.printStackTrace();
					}finally {
						try {
							ps.close();
							conn.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				
				
			}
		}
		
		
	/**
	 * @author LAxman
	 * this method is using for update the RequirmentUUID	
	 * @throws IOException
	 * @throws SQLException
	 */
	public  List<SubmissionTemplate>  addInterviewTemplate() throws IOException, SQLException {
		Connection conn = null ;
		Statement stmt1 = null;
		  List<SubmissionTemplate> subList = new ArrayList<SubmissionTemplate>();
		try {
			
			DataSource dataSource = comboPooledDataSource();
			conn = dataSource.getConnection();
			 String sql1 = "SELECT * FROM sourcelead.ORG  org ";
				 stmt1 = conn.createStatement();
				
                
				ResultSet rs1 = stmt1.executeQuery(sql1);
				if(rs1 != null){
					 while(rs1.next()){
						 SubmissionTemplate  subTemp = new SubmissionTemplate();
						 Long  orgId = rs1.getLong("ID");
						 
						 // Saving  the OwnerUserId in ELk Search
	  						String sql2 = "SELECT * FROM sourcelead.ORG_LOCATIONS  orgLocation where orgLocation.ORG_ID = '"+orgId+"' ";
	  						Statement stmt2 = conn.createStatement();
							ResultSet rs2 = stmt2.executeQuery(sql2);
							if(rs2 != null){
								 while(rs2.next()){
									 Long orgPrimaryContactUserId = rs1.getLong("PRIMARY_CONTACT_USER_ID");
									 OrgLocations orgLocation =  new OrgLocations() ;
									 orgLocation.setId(rs2.getLong("ID"));
									 subTemp.setOrgLocationId(orgLocation);
									 subTemp.setTemplateName(rs1.getString("ORG_NAME") +"  Inerview Feedback Template");
									 subTemp.setStatus(1);
									 subTemp.setDescription(subTemp.getTemplateName());
									 subTemp.setTemplateType("Feedback");
									 subTemp.setCreatedDate(rs1.getDate("CREATED_DATE"));
									 subTemp.setIsDefault("Y");
									  // Saving  the OwnerUserId in ELk Search
				  						String sql3 = "SELECT * FROM sourcelead.USER  user where user.ID = '"+orgPrimaryContactUserId+"' ";
				  						Statement stmt3 = conn.createStatement();
										ResultSet rs3 = stmt3.executeQuery(sql3);
										if(rs3 != null){
											 while(rs3.next()){
												 subTemp.setCreatedBy(rs3.getString("USER_CODE"));
										     }
											 
										}
										
										// Saving  the OwnerUserId in ELk Search
				  						String sql4 = "SELECT * FROM sourcelead.SUBMISSION_TEMPLATE  sub where sub.ORG_LOCATION_ID = '"+1L+"' and sub.TEMPLATE_NAME ='Sourcelead Inerview Feedback Template' ";
				  						Statement stmt4 = conn.createStatement();
										ResultSet rs4 = stmt4.executeQuery(sql4);
										if(rs4 != null){
											 while(rs4.next()){
												 subTemp.setJsonString(rs4.getString("JSON_STRING"));
										     }
										}
										 subList.add(subTemp);
							     }
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
		return subList;
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
		InterviewFeedbackTemplate dump = new InterviewFeedbackTemplate();
		dump.addElkRequirement();
	}

}