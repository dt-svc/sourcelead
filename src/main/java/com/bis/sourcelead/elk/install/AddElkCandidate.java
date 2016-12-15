package com.bis.sourcelead.elk.install;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.mchange.v2.c3p0.ComboPooledDataSource;


public class AddElkCandidate{
	private static final Logger logger = LoggerFactory.getLogger(AddElkCandidate.class);
	private ComboPooledDataSource comboPooledDataSource;
	public void addElkCandidate() throws IOException, SQLException {
		ArrayList<CandidateIndex> elkCandidateList = getElkCandidates();
		if(elkCandidateList != null && !elkCandidateList.isEmpty()){
			for (CandidateIndex candidateIndex :elkCandidateList) {
				SourceleadELKBaseIndexer<CandidateIndex> candidateIndexer = new SourceleadELKBaseIndexer<CandidateIndex>();
				candidateIndexer.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,candidateIndex.getCandidateId().toString());
			}
		}
		  logger.info("Candidates Successfully Added To Elk");
	}
	
	private ArrayList<CandidateIndex> getElkCandidates(){
		 DataSource dataSource = comboPooledDataSource();
		ArrayList<CandidateIndex> candidateIndexsList = new ArrayList<CandidateIndex>();
		 try {
				String sql = "SELECT * FROM sourcelead.CANDIDATE candidate";
				Connection conn = dataSource.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
			    while(rs.next()){
			    	CandidateIndex candidateIndex = new CandidateIndex();
			    	candidateIndex.setCandidateId(rs.getLong("ID"));
			    	candidateIndex.setOrgLocationId(rs.getLong("ORG_LOCATION_ID"));
					candidateIndex.setFirstName(rs.getString("FIRST_NAME"));
					candidateIndex.setLastName(rs.getString("LAST_NAME"));
					candidateIndex.setEmail(rs.getString("EMAIL"));
					candidateIndex.setAlternateEmail(rs.getString("ALTERNATE_EMAIL"));
					candidateIndex.setMobilePhone(rs.getString("MOBILE_PHONE"));
					candidateIndex.setAlternateMobilePhone(rs.getString("ALTERNATE_MOBILE_PHONE"));
					candidateIndex.setCountry(rs.getString("COUNTRY"));
					candidateIndex.setState(rs.getString("STATE"));
					candidateIndex.setCity(rs.getString("CITY"));
					candidateIndex.setAddress(rs.getString("ADDRESS"));
					candidateIndex.setSummary(rs.getString("SUMMARY"));
					candidateIndex.setCandidateType(rs.getString("CANDIDATE_TYPE"));
					candidateIndex.setExperienceYears(rs.getString("EXPERIENCE_YEARS"));
					candidateIndex.setExperienceMonths(rs.getString("EXPERIENCE_MONTHS"));
					candidateIndex.setDesignation(rs.getString("DESIGNATION"));
					candidateIndex.setDesignationType(rs.getString("DESIGNATION_TYPE"));
					candidateIndex.setCurrentRate(rs.getString("CURRENT_RATE"));
					candidateIndex.setCurrentRateType(rs.getString("CURRENT_RATE_TYPE"));
					candidateIndex.setCurrentRateCurrency(rs.getString("CURRENT_RATE_CURRENCY"));
					candidateIndex.setExpectedRate(rs.getString("EXPECTED_RATE"));
					candidateIndex.setRateType(rs.getString("RATE_TYPE"));
					candidateIndex.setExpectedRateCurrencey(rs.getString("EXPECTED_RATE_CURRENCY"));
					candidateIndex.setInvitationstatus(rs.getString("INVITATION_STATUS"));
					candidateIndex.setStatus(rs.getInt("STATUS"));
					candidateIndex.setTerminateReason(rs.getString("TERMINATE_REASON"));
					 	

					Timestamp createdDateTimestamp = rs.getTimestamp("CREATED_DATE");
				    if (createdDateTimestamp != null){
					  candidateIndex.setCreatedDate(new java.util.Date(createdDateTimestamp.getTime()));
				    }
				    candidateIndex.setCreatedBy(rs.getString("CREATED_BY"));   
					Timestamp updatedDateTimestamp = rs.getTimestamp("UPDATED_DATE");
					if (updatedDateTimestamp != null){
						candidateIndex.setUpdatedDate(new java.util.Date(updatedDateTimestamp.getTime()));
				    }
					candidateIndex.setUpdatedBy(rs.getString("UPDATED_BY"));
				    
				    Long candidateId = rs.getLong("ID");
		    	    if(candidateId != null && candidateId != 0){
		    	    	    String sql1 = "SELECT * FROM sourcelead.CANDIDATE_SKILLS candidateSkills where candidateSkills.CANDIDATE_ID = '"+ candidateId+"' ";
							Statement stmt1 = conn.createStatement();
							ResultSet rs1 = stmt1.executeQuery(sql1);
							if(rs1 != null){
								HashSet<String> skillList = new HashSet<>();
								 while(rs1.next()){
									 skillList.add(rs1.getString("NAME"));
							    }
							    if(null != skillList && !skillList.isEmpty()){
									candidateIndex.setSkills(skillList.toArray(new String[0]));
								}
		    	           }
							
						    String sql2 = "SELECT * FROM sourcelead.CANDIDATE_EMPLOYERS candidateEmployers where candidateEmployers.CANDIDATE_ID = '"+ candidateId+"' ";
							Statement stmt2 = conn.createStatement();
							ResultSet rs2 = stmt2.executeQuery(sql2);
							if(rs2 != null){
								HashSet<String> employerList = new HashSet<>();
								 while(rs2.next()){
									 employerList.add(rs2.getString("NAME"));
							    }
							    if(null != employerList && !employerList.isEmpty()){
									candidateIndex.setEmployer(employerList.toArray(new String[0]));
								}
		    	           }
							
						   String sql3= "SELECT * FROM sourcelead.CANDIDATE_EDUCATION candidateEducation where candidateEducation.CANDIDATE_ID = '"+ candidateId+"' ";
						   Statement stmt3 = conn.createStatement();
							ResultSet rs3 = stmt3.executeQuery(sql3);
							if(rs3 != null){
								HashSet<String> educationList = new HashSet<>();
								 while(rs1.next()){
									 educationList.add(rs1.getString("NAME"));
							    }
							    if(null != educationList && !educationList.isEmpty()){
									candidateIndex.setEducation(educationList.toArray(new String[0]));
								}
		    	           }
							
							
						   String sql4= "SELECT * FROM sourcelead.CANDIDATE_RESUMES candidateResumes where candidateResumes.CANDIDATE_ID = '"+ candidateId+"'  and candidateResumes.CREATED_DATE IN (SELECT max(canresume.CREATED_DATE) FROM sourcelead.CANDIDATE_RESUMES canresume where canresume.CANDIDATE_ID = '"+ candidateId+"' and canresume.status = '1') and candidateResumes.status = '1' ";
						   Statement stmt4 = conn.createStatement();
							ResultSet rs4 = stmt4.executeQuery(sql4);
							if(rs4 != null){
								 while(rs4.next()){
									 candidateIndex.setCurrentResumeId(rs4.getLong("ID"));
									 candidateIndex.setCurrentResumeName(rs4.getString("FILE_NAME"));
									 candidateIndex.setResumeContent("");
							    }
		    	           }
		    	    }
		    	    candidateIndexsList.add(candidateIndex);
			    }
			   conn.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return candidateIndexsList;
	}
	@Bean
	public DataSource comboPooledDataSource() {
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
	public static void main(String[] args) throws IOException {
			
		try {
			   AddElkCandidate addElkCandidate = new AddElkCandidate();
			   addElkCandidate.addElkCandidate();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
