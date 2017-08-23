package com.bis.sourcelead.elk.install;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.engine.DocumentMissingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;


public class AddElkCandidate{
	private static final Logger logger = LoggerFactory.getLogger(AddElkCandidate.class);
	private ComboPooledDataSource comboPooledDataSource;
	 private TransportClient client;
	public void addElkCandidate() throws IOException, SQLException {
		ArrayList<CandidateIndex> elkCandidateList = getElkCandidates();
		if(elkCandidateList != null && !elkCandidateList.isEmpty()){
			for (CandidateIndex candidateIndex :elkCandidateList) {
				saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,candidateIndex.getCandidateId().toString());
			}
		}
		  logger.info("Candidates Successfully Added To Elk");
	}
	
	 protected boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
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
					candidateIndex.setCandidateUUID(rs.getString("CANDIDATE_UUID"));
					candidateIndex.setLocation(rs.getString("LOCATION"));
					candidateIndex.setZipCode(rs.getString("ZIPCODE"));
					candidateIndex.setLatitude(rs.getString("LATITUDE"));
					candidateIndex.setLongitude(rs.getString("LONGITUDE"));
					
					candidateIndex.setGeneratedId(rs.getString("GENERATED_ID"));
					
					if(!isNullOrEmpty(rs.getLong("MASTER_CANDIDATE_ID"))){
						candidateIndex.setMasterCandidateId(rs.getLong("MASTER_CANDIDATE_ID"));
					}
					
					if(!isNullOrEmpty(rs.getLong("CONSULTANT_USER_ID"))){
						candidateIndex.setUserId(rs.getLong("CONSULTANT_USER_ID"));
					}
					
					if(!isNullOrEmpty(rs.getLong("RECRUITER_USER_ID"))){
						candidateIndex.setRecruiterUserId(rs.getLong("RECRUITER_USER_ID"));
					}else{
						candidateIndex.setRecruiterUserId(0L);
					}
					
					if(!isNullOrEmpty(rs.getString("TEAM_ID"))){
						candidateIndex.setTeamId(rs.getLong("TEAM_ID"));
					}else{
						candidateIndex.setTeamId(0L);
					}
					
					if(!isNullOrEmpty(rs.getString("IS_FAVOURITE"))){
					candidateIndex.setIsFavourite(rs.getInt("IS_FAVOURITE"));
					}else{
						candidateIndex.setIsFavourite(0);
					}
					
					if(!isNullOrEmpty(rs.getString("IS_FAVOURITE_ASSOCIATE"))){
					    candidateIndex.setIsFavouriteAssociate(rs.getInt("IS_FAVOURITE_ASSOCIATE"));
					}else{
						candidateIndex.setIsFavouriteAssociate(0);
					}
					
					if(!isNullOrEmpty(rs.getString("IS_ASSOCIATE"))){
					    candidateIndex.setIsAssociate(rs.getString("IS_ASSOCIATE"));
					}
					
					if(!isNullOrEmpty(rs.getLong("ORG_RELATION_ID"))){
					    candidateIndex.setOrgRelationId(rs.getLong("ORG_RELATION_ID"));
					}
					
					if(!isNullOrEmpty(rs.getLong("SPOC_ID"))){
					    candidateIndex.setSpocId(rs.getLong("SPOC_ID"));
					}
					
					
					if(StringUtils.isNotEmpty(rs.getString("EXPERIENCE_YEARS"))){
						if(rs.getString("EXPERIENCE_YEARS").equalsIgnoreCase("25+")){
							candidateIndex.setExperienceYears(Constants.EXPERIENCE_26YEARS);
						}
						else{
							candidateIndex.setExperienceYears(Integer.parseInt(rs.getString("EXPERIENCE_YEARS")));
						}
					}
					
					if(StringUtils.isNotEmpty(rs.getString("EXPERIENCE_MONTHS"))){
						candidateIndex.setExperienceMonths(Integer.parseInt(rs.getString("EXPERIENCE_MONTHS")));
					}
					candidateIndex.setDesignation(rs.getString("DESIGNATION"));
					candidateIndex.setDesignationType(rs.getString("DESIGNATION_TYPE"));
					candidateIndex.setNoticePriod(rs.getString("NOTICE_PERIOD"));
					candidateIndex.setCurrentRateType(rs.getString("CURRENT_RATE_TYPE"));
					candidateIndex.setCurrentRateCurrency(rs.getString("CURRENT_RATE_CURRENCY"));
					
					if(StringUtils.isNotEmpty(rs.getString("CURRENT_RATE"))){
						candidateIndex.setCurrentRate(rs.getString("CURRENT_RATE"));
					}
					
					if(NumberUtils.isParsable(rs.getString("EXPECTED_RATE"))){
						candidateIndex.setExpectedRate(Float.parseFloat(rs.getString("EXPECTED_RATE")));
					}
					candidateIndex.setRateType(rs.getString("RATE_TYPE"));
					candidateIndex.setExpectedRateCurrencey(rs.getString("EXPECTED_RATE_CURRENCY"));
					candidateIndex.setInvitationstatus(rs.getString("INVITATION_STATUS"));
					candidateIndex.setStatus(rs.getInt("STATUS"));
					candidateIndex.setTerminateReason(rs.getString("TERMINATE_REASON"));
					 	
					candidateIndex.setSkillsName(rs.getString("SKILLS_NAME"));
					if(rs.getString("VISA_TYPE") !=null){
						candidateIndex.setVisaType(rs.getString("VISA_TYPE").split(","));
					}

					Timestamp createdDateTimestamp = rs.getTimestamp("CREATED_DATE");
				    if (createdDateTimestamp != null){
				    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						candidateIndex.setCreatedDate(df.format(new java.util.Date(createdDateTimestamp.getTime())));
				    }
				    candidateIndex.setCreatedBy(rs.getString("CREATED_BY"));   
					Timestamp updatedDateTimestamp = rs.getTimestamp("UPDATED_DATE");
					if (updatedDateTimestamp != null){
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						candidateIndex.setUpdatedDate(df.format(new java.util.Date(updatedDateTimestamp.getTime())));
				    }
					
					if(StringUtils.isNotEmpty(rs.getString("IS_PROFILE_PUBLIC"))){
						candidateIndex.setIsProfilePublic(rs.getString("IS_PROFILE_PUBLIC"));
					}
					
					
					candidateIndex.setUpdatedBy(rs.getString("UPDATED_BY"));
				    
				    Long candidateId = rs.getLong("ID");
		    	    if(candidateId != null && candidateId != 0){
		    	    	
		    	    	/**
		    	    	 * Skills Save in Candidate table Not In Candidate Skills Table
		    	    	 */
		    	    	    /*String sql1 = "SELECT * FROM sourcelead.CANDIDATE_SKILLS candidateSkills where candidateSkills.CANDIDATE_ID = '"+ candidateId+"' ";
							Statement stmt1 = conn.createStatement();
							ResultSet rs1 = stmt1.executeQuery(sql1);
							if(rs1 != null){
                                ArrayList<String> skillList = new ArrayList<>();
								 while(rs1.next()){
									 skillList.add(rs1.getString("NAME"));
							    }
							    if(null != skillList && !skillList.isEmpty()){
									candidateIndex.setSkills(skillList);
								}
		    	           }*/
							
						    String sql2 = "SELECT * FROM sourcelead.CANDIDATE_EMPLOYERS candidateEmployers where candidateEmployers.CANDIDATE_ID = '"+ candidateId+"' ";
							Statement stmt2 = conn.createStatement();
							ResultSet rs2 = stmt2.executeQuery(sql2);
							if(rs2 != null){
								ArrayList<String> employerList = new ArrayList<>();
								 while(rs2.next()){
									 employerList.add(rs2.getString("NAME"));
							    }
							    if(null != employerList && !employerList.isEmpty()){
									candidateIndex.setEmployer(employerList);
								}
		    	           }
							
						   String sql3= "SELECT * FROM sourcelead.CANDIDATE_EDUCATION candidateEducation where candidateEducation.CANDIDATE_ID = '"+ candidateId+"' ";
						   Statement stmt3 = conn.createStatement();
							ResultSet rs3 = stmt3.executeQuery(sql3);
							if(rs3 != null){
								ArrayList<String> educationList = new ArrayList<>();
								 while(rs3.next()){
									 educationList.add(rs3.getString("NAME"));
							    }
							    if(null != educationList && !educationList.isEmpty()){
									candidateIndex.setEducation(educationList);
								}
		    	           }
							
						   String sql5= "SELECT * FROM sourcelead.CANDIDATE_CERTIFICATIONS candidateCertifications where candidateCertifications.CANDIDATE_ID = '"+ candidateId+"' ";
						   Statement stmt5 = conn.createStatement();
							ResultSet rs5 = stmt5.executeQuery(sql5);
							if(rs5 != null){
								ArrayList<String> certList = new ArrayList<>();
								 while(rs5.next()){
									 certList.add(rs5.getString("NAME"));
							    }
							    if(null != certList && !certList.isEmpty()){
									candidateIndex.setCertifications(certList);
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
	
	
	private boolean isNullOrEmpty(long long1) {
		// TODO Auto-generated method stub
		return false;
	}

	public void saveOrUpdateElkDocument(CandidateIndex candidateIndex,String index,String type,String id) {
		String entityInJson = null;
		try {
		    entityInJson = toJSON(candidateIndex);
		    client.prepareUpdate(index, type,id).setDoc(entityInJson).get();
		    logger.info(type+" has been updated.");
		} 
        catch(DocumentMissingException e){
        	client.prepareIndex(index, type,id)
		    .setSource(entityInJson).execute().actionGet();
		    logger.info(type+" has been indexed.");
		}
		catch (Exception e) {
			logger.error(type+" could not be serialized. "+e.getLocalizedMessage());
		}
		
     }
	
	 public String toJSON(Object obj){
	        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	            public HierarchicalStreamWriter createWriter(Writer writer) {
	                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	            }
	        });
	        return xstream.toXML(obj);
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
		
		try {
	    	  Settings settings = Settings.settingsBuilder()
	    			  .put("cluster.name", dbconfig.getCluster())
	                  .put("node.name", dbconfig.getNode())
	         		  .put("path.home", dbconfig.getPath())
	                  .build();
			client = TransportClient.builder().settings(settings).build().
			      addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(dbconfig.getHost()), 9300));
	     } catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		
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
