package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKRestClient;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class AddElkCandidateRest {
	private static final Logger logger = LoggerFactory.getLogger(AddElkCandidateRest.class);
	private ComboPooledDataSource comboPooledDataSource;
	private SourceleadELKRestClient<CandidateIndex> client;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);

	public void addElkCandidate() throws IOException, SQLException {
		System.out.println("initializing addElkCandidate()");
		ArrayList<CandidateIndex> elkCandidateList = getElkCandidates();
		if (elkCandidateList != null && !elkCandidateList.isEmpty()) {
			for (CandidateIndex candidateIndex : elkCandidateList) {
				client.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX,
						Constants.ELK_CANDIDATE_TYPE, candidateIndex.getCandidateId().toString());
			}
		}
		System.out.println("Candidates Successfully Added To Elk");
	}

	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	private ArrayList<CandidateIndex> getElkCandidates() {
		DataSource dataSource = comboPooledDataSource();
		ArrayList<CandidateIndex> candidateIndexsList = new ArrayList<CandidateIndex>();
		try {
			String sql = "SELECT * FROM " + dbconfig.getSchema() + ".CANDIDATE candidate";
			Connection conn = dataSource.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("AddElkCandidate --> getElkCandidates() --> Query : " + sql);
			while (rs.next()) {
				System.out.println("Candidate ID : " + rs.getLong("ID"));
				CandidateIndex candidateIndex = new CandidateIndex();
				candidateIndex.setCandidateId(rs.getLong("ID"));
				candidateIndex.setOrgLocationId(rs.getLong("ORG_LOCATION_ID"));
				candidateIndex.setFirstName(firstLetterCapitalizedString(rs.getString("FIRST_NAME")));
				candidateIndex.setLastName(firstLetterCapitalizedString(rs.getString("LAST_NAME")));
				candidateIndex.setIsSharedInMarketplace(rs.getString("IS_SHARED_IN_MARKETPLACE")); // Added by SRINIVAS
																									// SABBU ON
																									// 24-MAY-2019.
				String createdBy = null;
				Long orgLocationId = rs.getLong("ORG_LOCATION_ID");

				if (!isNullOrEmpty(rs.getString("CREATED_BY"))) {
					createdBy = rs.getString("CREATED_BY");
				}

				if (!isNullOrEmpty(rs.getString("LAST_NAME"))) {
					candidateIndex.setFullName(firstLetterCapitalizedString(rs.getString("FIRST_NAME")) + " "
							+ firstLetterCapitalizedString(rs.getString("LAST_NAME")));
				} else {
					candidateIndex.setFullName(firstLetterCapitalizedString(rs.getString("FIRST_NAME")));
				}

				if (StringUtils.isNotBlank(rs.getString("EMAIL"))) {
					candidateIndex.setEmail(rs.getString("EMAIL").toLowerCase());
				}
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

				candidateIndex.setCreatedByUserCode(rs.getString("CREATED_BY"));

				if (!isNullOrEmpty(rs.getLong("MASTER_CANDIDATE_ID"))) {
					candidateIndex.setMasterCandidateId(rs.getLong("MASTER_CANDIDATE_ID"));
				}

				if (!isNullOrEmpty(rs.getLong("CONSULTANT_USER_ID"))) {
					candidateIndex.setUserId(rs.getLong("CONSULTANT_USER_ID"));
				}

				if (!isNullOrEmpty(rs.getLong("RECRUITER_USER_ID"))) {
					candidateIndex.setRecruiterUserId(rs.getLong("RECRUITER_USER_ID"));
				} else {
					candidateIndex.setRecruiterUserId(0L);
				}

				if (!isNullOrEmpty(rs.getString("TEAM_ID"))) {
					candidateIndex.setTeamId(rs.getLong("TEAM_ID"));
				} else {
					candidateIndex.setTeamId(0L);
				}

				if (!isNullOrEmpty(rs.getString("IS_FAVOURITE"))) {
					candidateIndex.setIsFavourite(rs.getInt("IS_FAVOURITE"));
				} else {
					candidateIndex.setIsFavourite(0);
				}

				if (!isNullOrEmpty(rs.getString("IS_FAVOURITE_ASSOCIATE"))) {
					candidateIndex.setIsFavouriteAssociate(rs.getInt("IS_FAVOURITE_ASSOCIATE"));
				} else {
					candidateIndex.setIsFavouriteAssociate(0);
				}

				if (!isNullOrEmpty(rs.getString("IS_ASSOCIATE"))) {
					candidateIndex.setIsAssociate(rs.getString("IS_ASSOCIATE"));
				}

				if (!isNullOrEmpty(rs.getLong("ORG_RELATION_ID"))) {
					candidateIndex.setOrgRelationId(rs.getLong("ORG_RELATION_ID"));
				}

				if (!isNullOrEmpty(rs.getLong("SPOC_ID"))) {
					candidateIndex.setSpocId(rs.getLong("SPOC_ID"));
				}

				if (StringUtils.isNotEmpty(rs.getString("EXPERIENCE_YEARS"))) {
					if (rs.getString("EXPERIENCE_YEARS").equalsIgnoreCase("25+")) {
						candidateIndex.setExperienceYears(Constants.EXPERIENCE_26YEARS);
					} else {
						candidateIndex.setExperienceYears(Integer.parseInt(rs.getString("EXPERIENCE_YEARS")));
					}
				}

				if (StringUtils.isNotEmpty(rs.getString("EXPERIENCE_MONTHS"))) {
					candidateIndex.setExperienceMonths(Integer.parseInt(rs.getString("EXPERIENCE_MONTHS")));
				}
				candidateIndex.setDesignation(rs.getString("DESIGNATION"));
				candidateIndex.setDesignationType(rs.getString("DESIGNATION_TYPE"));
				candidateIndex.setNoticePriod(rs.getString("NOTICE_PERIOD"));
				candidateIndex.setCurrentRateType(rs.getString("CURRENT_RATE_TYPE"));
				candidateIndex.setCurrentRateCurrency(rs.getString("CURRENT_RATE_CURRENCY"));

				if (StringUtils.isNotEmpty(rs.getString("CURRENT_RATE"))) {
					candidateIndex.setCurrentRate(rs.getString("CURRENT_RATE"));
				}

				if (NumberUtils.isParsable(rs.getString("EXPECTED_RATE"))) {
					candidateIndex.setExpectedRate(Float.parseFloat(rs.getString("EXPECTED_RATE")));
				}
				candidateIndex.setRateType(rs.getString("RATE_TYPE"));
				candidateIndex.setExpectedRateCurrencey(rs.getString("EXPECTED_RATE_CURRENCY"));
				candidateIndex.setInvitationstatus(rs.getString("INVITATION_STATUS"));
				candidateIndex.setStatus(rs.getInt("STATUS"));
				candidateIndex.setTerminateReason(rs.getString("TERMINATE_REASON"));

				candidateIndex.setSkillsName(rs.getString("SKILLS_NAME"));
				if (rs.getString("VISA_TYPE") != null) {
					candidateIndex.setVisaType(rs.getString("VISA_TYPE").split(","));
				}

				Timestamp createdDateTimestamp = rs.getTimestamp("CREATED_DATE");
				if (createdDateTimestamp != null) {
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					candidateIndex.setCreatedDate(df.format(new java.util.Date(createdDateTimestamp.getTime())));
				}
				candidateIndex.setCreatedBy(rs.getString("CREATED_BY"));
				Timestamp updatedDateTimestamp = rs.getTimestamp("UPDATED_DATE");
				if (updatedDateTimestamp != null) {
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					candidateIndex.setUpdatedDate(df.format(new java.util.Date(updatedDateTimestamp.getTime())));
				}

				if (StringUtils.isNotEmpty(rs.getString("IS_PROFILE_PUBLIC"))) {
					candidateIndex.setIsProfilePublic(rs.getString("IS_PROFILE_PUBLIC"));
				}

				candidateIndex.setUpdatedBy(rs.getString("UPDATED_BY"));

				Long candidateId = rs.getLong("ID");
				if (candidateId != null && candidateId != 0) {

					/**
					 * Skills Save in Candidate table Not In Candidate Skills Table
					 */
					/*
					 * String sql1 = "SELECT * FROM "+dbconfig.getSchema()
					 * +".CANDIDATE_SKILLS candidateSkills where candidateSkills.CANDIDATE_ID = '"+
					 * candidateId+"' "; Statement stmt1 = conn.createStatement(); ResultSet rs1 =
					 * stmt1.executeQuery(sql1); if(rs1 != null){ ArrayList<String> skillList = new
					 * ArrayList<>(); while(rs1.next()){ skillList.add(rs1.getString("NAME")); }
					 * if(null != skillList && !skillList.isEmpty()){
					 * candidateIndex.setSkills(skillList); } }
					 */

					String sql2 = "SELECT * FROM " + dbconfig.getSchema()
							+ ".CANDIDATE_EMPLOYERS candidateEmployers where candidateEmployers.CANDIDATE_ID = '"
							+ candidateId + "' ";
					Statement stmt2 = conn.createStatement();
					ResultSet rs2 = stmt2.executeQuery(sql2);
					if (rs2 != null) {
						ArrayList<String> employerList = new ArrayList<>();
						while (rs2.next()) {
							employerList.add(rs2.getString("NAME"));
						}
						if (null != employerList && !employerList.isEmpty()) {
							candidateIndex.setEmployer(employerList);
						}
					}

					String sql3 = "SELECT * FROM " + dbconfig.getSchema()
							+ ".CANDIDATE_EDUCATION candidateEducation where candidateEducation.CANDIDATE_ID = '"
							+ candidateId + "' ";
					Statement stmt3 = conn.createStatement();
					ResultSet rs3 = stmt3.executeQuery(sql3);
					if (rs3 != null) {
						ArrayList<String> educationList = new ArrayList<>();
						while (rs3.next()) {
							educationList.add(rs3.getString("NAME"));
						}
						if (null != educationList && !educationList.isEmpty()) {
							candidateIndex.setEducation(educationList);
						}
					}

					String sql5 = "SELECT * FROM " + dbconfig.getSchema()
							+ ".CANDIDATE_CERTIFICATIONS candidateCertifications where candidateCertifications.CANDIDATE_ID = '"
							+ candidateId + "' ";
					Statement stmt5 = conn.createStatement();
					ResultSet rs5 = stmt5.executeQuery(sql5);
					if (rs5 != null) {
						ArrayList<String> certList = new ArrayList<>();
						while (rs5.next()) {
							certList.add(rs5.getString("NAME"));
						}
						if (null != certList && !certList.isEmpty()) {
							candidateIndex.setCertifications(certList);
						}
					}

					String sql4 = "SELECT * FROM " + dbconfig.getSchema()
							+ ".CANDIDATE_RESUMES candidateResumes where candidateResumes.CANDIDATE_ID = '"
							+ candidateId
							+ "'  and candidateResumes.CREATED_DATE IN (SELECT max(canresume.CREATED_DATE) FROM "
							+ dbconfig.getSchema() + ".CANDIDATE_RESUMES canresume where canresume.CANDIDATE_ID = '"
							+ candidateId + "' and canresume.status = '1') and candidateResumes.status = '1' ";
					Statement stmt4 = conn.createStatement();
					ResultSet rs4 = stmt4.executeQuery(sql4);
					if (rs4 != null) {
						while (rs4.next()) {
							candidateIndex.setCurrentResumeId(rs4.getLong("ID"));
							candidateIndex.setCurrentResumeName(rs4.getString("FILE_NAME"));
							candidateIndex.setResumeContent("");
							candidateIndex.setFileUrl(rs4.getString("FILE_URL"));
							candidateIndex.setFileContentType(rs4.getString("FILE_CONTENT_TYPE"));
							candidateIndex.setResumeName(rs4.getString("FILE_NAME"));
						}
					}

					if (createdBy != null && !createdBy.isEmpty()) {
						String sql6 = "SELECT * FROM " + dbconfig.getSchema() + ".USER  user where  user.USER_CODE = '"
								+ createdBy + "' ";
						Statement stmt6 = conn.createStatement();
						ResultSet rs6 = stmt6.executeQuery(sql6);
						if (rs6 != null) {
							while (rs6.next()) {
								if (null != rs6.getString("FIRST_NAME") && null != rs6.getString("LAST_NAME")
										&& !rs6.getString("LAST_NAME").isEmpty()) {
									StringBuilder sb = new StringBuilder();
									candidateIndex.setCreatedBy(sb.append(rs6.getString("FIRST_NAME")).append(" ")
											.append(rs6.getString("LAST_NAME")).toString());
								} else if (null != rs6.getString("FIRST_NAME")) {
									StringBuilder sb = new StringBuilder();
									candidateIndex.setCreatedBy(sb.append(rs6.getString("FIRST_NAME")).toString());
								}
							}
						}
					}

					String sql7 = "SELECT * FROM " + dbconfig.getSchema()
							+ ".SOURCELEAD_TAGS  tags where  tags.ENTITY_ID = '" + candidateId
							+ "' and  tags.ORG_LOCATION_ID = '" + orgLocationId
							+ "' and tags.ENTITY_TYPE = 'CANDIDATES' and tags.STATUS = '1' ";
					Statement stmt7 = conn.createStatement();
					ResultSet rs7 = stmt7.executeQuery(sql7);
					if (rs7 != null) {
						StringBuilder tagsStr = new StringBuilder();

						while (rs7.next()) {
							tagsStr = tagsStr.append(rs7.getString("TAG_NAME")).append(",");
						}
						String tagString = tagsStr.toString();
						if (tagString != null && !tagString.isEmpty()) {
							candidateIndex.setTags(tagString.substring(0, tagString.length() - 1));
						}

					}

				}
				candidateIndexsList.add(candidateIndex);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return candidateIndexsList;
	}

	private boolean isNullOrEmpty(long long1) {
		// TODO Auto-generated method stub
		return false;
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
		System.out.println("JDBC URL : " + comboPooledDataSource.getJdbcUrl());
		return comboPooledDataSource;
	}

	public static void main(String[] args) throws IOException {
		System.out.println("entered main() in AddELKCandidate.java");
		AddElkCandidateRest addElkCandidate = new AddElkCandidateRest();
		try {
			addElkCandidate.client = new SourceleadELKRestClient();
			addElkCandidate.addElkCandidate();
			addElkCandidate.client.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("exiting main() from AddELKCandidate.java");
	}

	public String firstLetterCapitalizedString(String name) {
		String finalString = WordUtils.capitalizeFully(name);
		return finalString;
	}
}