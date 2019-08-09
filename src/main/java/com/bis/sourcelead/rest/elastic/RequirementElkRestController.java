package com.bis.sourcelead.rest.elastic;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKRestClient;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@RestController
@RequestMapping("/app")
public class RequirementElkRestController {
    private static final Logger logger = LoggerFactory.getLogger(RequirementElkRestController.class);
	private ComboPooledDataSource comboPooledDataSource;
	private SourceleadELKRestClient<RequirementIndex> client;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
	
	 @RequestMapping(value = "/ws/storeRequirementsInElastic/{minLimitSize}/{maxLimitSize}", method = RequestMethod.GET)
	 public void storeCandidatesInELK(@PathVariable Long minLimitSize,@PathVariable Long maxLimitSize) throws Exception {
		 
		    DataSource dataSource = comboPooledDataSource();
			ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
			DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
			client = new SourceleadELKRestClient<>();
			int count = 0;
		 try {
			 System.out.println("------------ storeRequirementsInElastic starts ----------");
				String sql = "SELECT * FROM "+dbconfig.getSchema()+".REQUIREMENT req INNER JOIN USER user on req.MANAGER_USER_ID = user.ID  where (req.ID >= '"+ minLimitSize+"' and req.ID <= '"+ maxLimitSize+"')";

				Connection conn = dataSource.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while(rs.next()){
					RequirementIndex requirementIndex = new RequirementIndex();
					if(null != rs.getString("ID") && !"".equalsIgnoreCase(rs.getString("ID"))){
						requirementIndex.setRequirementId(Long.parseLong(rs.getString("ID")));
					}
					if(null != rs.getString("ORG_LOCATION_ID") && !"".equalsIgnoreCase(rs.getString("ORG_LOCATION_ID"))){
						requirementIndex.setOrgLocationId(Long.parseLong(rs.getString("ORG_LOCATION_ID")));
					}
					requirementIndex.setJobTitle(firstLetterCapitalizedString(rs.getString("JOB_TITLE")));
					requirementIndex.setJobId(rs.getString("JOB_ID"));
					requirementIndex.setCurrentRateCurrency(rs.getString("CURRENCY_CODE"));
					requirementIndex.setNumberOfPositions(rs.getString("POSITIONS"));
					requirementIndex.setIndustryName(rs.getString("INDUSTRY_NAME"));
					requirementIndex.setIndustryName(rs.getString("TRAVEL_ALLOWANCE"));
					requirementIndex.setProjectDurationMonths(rs.getString("PROJECT_DURATION_MONTHS"));
					requirementIndex.setProjectDurationYears(rs.getString("PROJECT_DURATION_YEARS"));
					//requirementIndex.setWorkflowId(rs.getLong("WORKFLOW_ID"));// ADDED BY SRINIVAS SABBU ON 24-MAY-2019.
					requirementIndex.setClosedDate(rs.getString("REQUIRMENT_CLOSED_DATE"));
					requirementIndex.setIsreceivingApplicants(rs.getString("IS_RECEIVING_APPLICANTS"));
					if(null != rs.getString("PROJECT_CLIENT_ID") && !"".equalsIgnoreCase(rs.getString("PROJECT_CLIENT_ID"))){
						requirementIndex.setProjectClientID(rs.getString("PROJECT_CLIENT_ID"));
					}
					if(null != rs.getString("DESCRIPTION") && !"".equalsIgnoreCase(rs.getString("DESCRIPTION"))){
						requirementIndex.setDescription(rs.getString("DESCRIPTION"));
					}
					
					if(null != rs.getString("MAX_RATE") && !"".equalsIgnoreCase(rs.getString("MAX_RATE"))){
						requirementIndex.setMaxRate(Float.parseFloat(rs.getString("MAX_RATE")));
					}
					if(null != rs.getString("RATE") && !"".equalsIgnoreCase(rs.getString("RATE"))){
						requirementIndex.setRate(Float.parseFloat(rs.getString("RATE")));
					}
					requirementIndex.setRateType(rs.getString("RATE_TYPE"));
					if(null != rs.getString("MIN_EXP") && !"".equalsIgnoreCase(rs.getString("MIN_EXP"))){
						requirementIndex.setMinExperience(Float.parseFloat(rs.getString("MIN_EXP")));
					}
					if(null != rs.getString("MAX_EXP") && !"".equalsIgnoreCase(rs.getString("MAX_EXP"))){
						requirementIndex.setMaxExperience(Float.parseFloat(rs.getString("MAX_EXP")));
					}
					requirementIndex.setRequirementStatus(rs.getString("REQUIREMENT_SUBMISSION_STATUS"));
					requirementIndex.setUrl(rs.getString("URL"));
					//requirementIndex.setRequirementSubmissionPreviousStatus(rs.getString("REQUIREMENT_SUBMISSION_PREVIOUS_STATUS"));
					requirementIndex.setCountry(rs.getString("COUNTRY"));
					requirementIndex.setState(rs.getString("STATE"));
					requirementIndex.setCity(rs.getString("CITY"));
					requirementIndex.setLocation(rs.getString("LOCATION"));
					requirementIndex.setLocation(rs.getString("LOCATION"));
					requirementIndex.setZipCode(rs.getString("ZIPCODE"));
					requirementIndex.setReqCreatedBy(rs.getLong("CREATED_BY"));
					requirementIndex.setReqUpdatedBy(rs.getString("UPDATED_BY"));
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					requirementIndex.setCreatedDate(dateFormat.format(rs.getDate("CREATED_DATE")));
					requirementIndex.setUpdatedDate(dateFormat.format(rs.getDate("UPDATED_DATE")));
					requirementIndex.setIsTemplate(rs.getString("IS_Template"));
					requirementIndex.setManagerUserId(rs.getString("MANAGER_USER_ID"));
					requirementIndex.setPrimaryRecruiterUserId(rs.getString("PRIMARY_RECRUITER_USER_ID"));
					requirementIndex.setGeneratedId(rs.getString("GENERATED_ID"));
					requirementIndex.setReleasedBy(rs.getString("RELEASED_BY"));
					if(null != rs.getString("MEDICAL_CLEARENCE") && !"".equalsIgnoreCase(rs.getString("MEDICAL_CLEARENCE"))){
					requirementIndex.setMedicalClearence(rs.getString("MEDICAL_CLEARENCE"));
					}
					if(null != rs.getString("SECURITY_CLEARANCE") && !"".equalsIgnoreCase(rs.getString("SECURITY_CLEARANCE"))){
						requirementIndex.setSecurityClearance(rs.getString("SECURITY_CLEARANCE"));
						}
					if(null != rs.getString("OVER_TIME") && !"".equalsIgnoreCase(rs.getString("OVER_TIME"))){
						requirementIndex.setOverTime(rs.getString("OVER_TIME"));
						}
					if(null != rs.getString("BACKGROUNG_CHECK") && !"".equalsIgnoreCase(rs.getString("BACKGROUNG_CHECK"))){
						requirementIndex.setBackgroundCheck(rs.getString("BACKGROUNG_CHECK"));
						}
					if(null != rs.getString("GENDER") && !"".equalsIgnoreCase(rs.getString("GENDER"))){
						requirementIndex.setGender(rs.getString("GENDER"));
						}
					
					if(null != rs.getString("EXPERIENCE_LEVEL") && !"".equalsIgnoreCase(rs.getString("EXPERIENCE_LEVEL"))){
						requirementIndex.setExperienceLevel(rs.getString("EXPERIENCE_LEVEL"));
						}
					if(null != rs.getString("CLIENT_CURRENCY_CODE") && !"".equalsIgnoreCase(rs.getString("CLIENT_CURRENCY_CODE"))){
						requirementIndex.setClientCurrencyCode(rs.getString("CLIENT_CURRENCY_CODE"));
					}
					if(null != rs.getString("CLIENT_MAX_RATE") && !"".equalsIgnoreCase(rs.getString("CLIENT_MAX_RATE"))){
						requirementIndex.setClientMaxRate(Float.parseFloat(rs.getString("CLIENT_MAX_RATE")));
					}
					if(null != rs.getString("CLIENT_RATE") && !"".equalsIgnoreCase(rs.getString("CLIENT_RATE"))){
						requirementIndex.setClientRate(Float.parseFloat(rs.getString("CLIENT_RATE")));
					}
					if(null != rs.getString("CLIENT_RATE_TYPE") && !"".equalsIgnoreCase(rs.getString("CLIENT_RATE_TYPE"))){
						requirementIndex.setClientRateType(rs.getString("CLIENT_RATE_TYPE"));
					}
						
					ResultSet approverSet = getApproverUserDetails(Long.parseLong(rs.getString("ID")),conn);
					List<String> lavel1User = new ArrayList<String>();
					List<String> lave21User = new ArrayList<String>();
					while(approverSet.next()){
						if(approverSet.getInt("LEVEL") == 1){
						   lavel1User.add(approverSet.getString("APPROVER_USER_ID"));
						}
						if(approverSet.getInt("LEVEL") == 2){
							lave21User.add(approverSet.getString("APPROVER_USER_ID"));
						}
					}
					String [] lavel1UserArry = lavel1User.toArray(new String[lavel1User.size()]);
					String [] lave21UserArry = lave21User.toArray(new String[lave21User.size()]);
					requirementIndex.setReqLevel1ApproverUserId(lavel1UserArry);
					requirementIndex.setReqLevel2ApproverUserId(lave21UserArry);
					
					ResultSet teamSet = getTeamDetails(Long.parseLong(rs.getString("ID")),conn);
					HashSet<String> assignTeamMembers = new HashSet<>();
					if(teamSet !=null){
						while(teamSet.next()){
							if(teamSet.getString("ASSIGN_USER_ID") !=null && !"".equalsIgnoreCase(teamSet.getString("ASSIGN_USER_ID"))){
							assignTeamMembers.add(teamSet.getString("ASSIGN_USER_ID"));
							}
							
						}
						String [] assignTeamMembersArray = assignTeamMembers.toArray(new String[assignTeamMembers.size()]);
						requirementIndex.setTeamAssignUserId(assignTeamMembersArray);
					}
					ResultSet resultTeam = getTeanOwnerIds(rs.getString("ID"),conn);
					HashSet<String> teamOwners = new HashSet<>();
					if(resultTeam != null){
						while(resultTeam.next()){
//							if(null!= resultTeam.getString("OWNER_USER_ID")&& !"".equalsIgnoreCase(resultTeam.getString("OWNER_USER_ID"))){
								teamOwners.add(resultTeam.getString("OWNER_USER_ID"));
//							}
						}
						String [] teamOwnerArry = teamOwners.toArray(new String[teamOwners.size()]);
						requirementIndex.setTeamOwnerUserId(teamOwnerArry);
					}
					
					ResultSet noOfApplicants = getNoOfApplicantDetails(Long.parseLong(rs.getString("ID")),rs.getString("ORG_LOCATION_ID"),conn);
					while(noOfApplicants.next()){
						requirementIndex.setNumberOfApplicants(noOfApplicants.getString(1));
					}
					
					String education = rs.getString("EDUCATION");
					if(null != education && !"".equalsIgnoreCase(education)){
						requirementIndex.setEducation(education.split(","));
					}
					requirementIndex.setIsClientJob(rs.getString("IS_CLIENT_JOB"));
					if(rs.getString("ORG_RELATION_ID") !=null && !"".equalsIgnoreCase(rs.getString("ORG_RELATION_ID"))){
						requirementIndex.setIsClientJob(rs.getString("IS_CLIENT_JOB"));
						ResultSet rs1 = getClientContactDetails(rs.getString("ORG_RELATION_ID"),conn);
						String clientName =null;
						String clientContactName = null;
						String clientContactEmail = null;
						while(rs1.next()){
							String type = rs1.getString("INITIATOR_TYPE");
							if("Client".equalsIgnoreCase(type)){
								clientName = getClientName(rs1.getString("LOCATION_ID"),conn);
							}
							if("Vendor".equalsIgnoreCase(type)){
								clientName = getClientName(rs1.getString("RESPONDER_LOCATION_ID"),conn);
							}
						}
						requirementIndex.setClient(clientName);
						String skills = rs.getString("SKILLS");
						if(!("").equalsIgnoreCase(skills) && skills!=null){
							requirementIndex.setSkills(skills.split(","));
						}
						requirementIndex.setJobType(rs.getString("JOB_TYPE"));
						requirementIndex.setOtherJobType(rs.getString("OTHER_JOBTYPE"));
						requirementIndex.setJobId(rs.getString("JOB_ID"));
						
						
						String clientContactSql = "SELECT USER.FIRST_NAME, USER.LAST_NAME,USER.EMAIL FROM USER INNER JOIN ORG_SPOC ON USER.ID=ORG_SPOC.CLIENT_USER_ID WHERE ORG_SPOC.ID=?;";
						ResultSet clientNameRs = getClientContactName(rs.getString("SPOC_ID"),clientContactSql,conn);
						if(clientNameRs.next()){
							if(StringUtils.isNotEmpty(clientNameRs.getString("LAST_NAME"))) {
								clientContactName = clientNameRs.getString("FIRST_NAME")+" "+clientNameRs.getString("LAST_NAME");
							}else {
								clientContactName = clientNameRs.getString("FIRST_NAME");
							}
							clientContactEmail = clientNameRs.getString("EMAIL");
						}
						
						requirementIndex.setClientContactName(clientContactName);
						requirementIndex.setClientContactEmail(clientContactEmail);
						requirementIndex.setOrgRelationId(rs.getString("ORG_RELATION_ID"));
					}
					
					if(null != rs.getString("FIRST_NAME") && null != rs.getString("LAST_NAME")){
						requirementIndex.setManager(rs.getString("FIRST_NAME")+" "+rs.getString("LAST_NAME"));
					}else{
						requirementIndex.setManager(rs.getString("FIRST_NAME"));
					}
					requirementIndex.setPriority(rs.getString("PRIORITY"));
					
					if(null != rs.getString("RELEASED_BY") && null != rs.getString("RELEASED_BY")){
						requirementIndex.setReleasedBy(rs.getString("RELEASED_BY"));
					}
					Integer numberOfApplicants = getNumberOfApplicants(rs.getString("ID"),conn,rs.getString("ORG_LOCATION_ID"));
					requirementIndex.setNumberOfApplicants(numberOfApplicants.toString());
					
					if(null != rs.getString("IS_PUBLIC")){
						requirementIndex.setIsPublicJob(rs.getString("IS_PUBLIC"));
					}else{
						requirementIndex.setIsPublicJob("N");
					}
					
					if(null != rs.getString("IS_CROWD_SOURCING")){
						requirementIndex.setIsCrowdSourcingJob(rs.getString("IS_CROWD_SOURCING"));
					}else{
						requirementIndex.setIsCrowdSourcingJob("N");
					}
					
					if(null != rs.getString("POSTED_ORG_NAME")){
						requirementIndex.setPostedOrg(rs.getString("POSTED_ORG_NAME"));
					}
					if(null != rs.getString("REQUIREMENT_UUID")){
						requirementIndex.setRequirementUUID(rs.getString("REQUIREMENT_UUID"));
					}
					
					String appliedProfessionals = rs.getString("APPLIED_PROFESSIONALS");
					if(!("").equalsIgnoreCase(appliedProfessionals) && appliedProfessionals!=null){
						requirementIndex.setAppliedProfessionals(appliedProfessionals.split(","));
					}
					
					String appliedRecruiters = rs.getString("APPLIED_RECRUITERS");
					if(!("").equalsIgnoreCase(appliedRecruiters) && appliedRecruiters!=null){
						requirementIndex.setAppliedRecruiters(appliedRecruiters.split(","));
					}
					
					
					if(null != rs.getString("REWARD_TYPE")){
						requirementIndex.setRewardType(rs.getString("REWARD_TYPE"));
					}
					
					if(null != rs.getString("REWARD_PERCENTAGE")){
						requirementIndex.setRewardPercentage(rs.getString("REWARD_PERCENTAGE"));
					}
					
					if(null != rs.getString("REWARD_FLAT")){
						requirementIndex.setRewardFlat(rs.getString("REWARD_FLAT"));
					}
					
					if(null != rs.getString("REWARD_FLAT_RATE_TYPE")){
						requirementIndex.setRewardFlatRateType(rs.getString("REWARD_FLAT_RATE_TYPE"));
					}
					
					if(null != rs.getString("REWARD_GUARANTEE_PERIOD")){
						requirementIndex.setRewardGuaranteePeriod(rs.getString("REWARD_GUARANTEE_PERIOD"));
					}
					
					String visaTypes = rs.getString("VISA_TYPE");
					if(!("").equalsIgnoreCase(visaTypes) && visaTypes!=null){
						requirementIndex.setVisaTypes(visaTypes.split(","));
					} 
					
					ResultSet statusRs =getApproverHierarchyStatus(rs.getString("ID"),conn);
					if(null != statusRs){
						while(statusRs.next()){
							if(statusRs.getInt("LEVEL") == 1){
								requirementIndex.setReqLevel1ApproverStatus(statusRs.getString("STATUS"));	
							}else{
								requirementIndex.setReqLevel2ApproverStatus(statusRs.getString("STATUS"));
							}
						}
					}
					if(null != rs.getString("REQUIREMENT_SUBMISSION_PREVIOUS_STATUS")){
						requirementIndex.setRequirementSubmissionPreviousStatus(rs.getString("REQUIREMENT_SUBMISSION_PREVIOUS_STATUS"));
					}
					
					client.saveOrUpdateElkDocument(requirementIndex, Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_TYPE,requirementIndex.getRequirementId().toString());
					System.out.println("Saved Count : " + (++count));
				}
				conn.close();
				System.out.println("------------ storeRequirementsInElastic ends ----------");
			}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	 
	 private ResultSet getTeanOwnerIds(String reqId, Connection conn) {
			PreparedStatement ps=null;
			ResultSet rs=null;
			Long requirementId = Long.parseLong(reqId);
			try{
					String sql = "SELECT * FROM "+dbconfig.getSchema()+".TEAM team inner join REQUIREMENT_ASSIGN_TO req on team.ID = req.TEAM_ID where req.STATUS = 1 and req.REQUIREMENT_ID = ?";
					 ps = conn.prepareStatement(sql);
					 ps.setLong(1, requirementId);
					 rs = ps.executeQuery();
			 }catch(Exception e){
				 e.printStackTrace();
			 }
		     return rs;
		}


		private ResultSet getApproverHierarchyStatus(String reqId, Connection conn) {
			PreparedStatement ps=null;
			ResultSet rs=null;
			Long requirementId = Long.parseLong(reqId);
			 try{
					String sql = "SELECT * FROM "+dbconfig.getSchema()+".REQUIREMENT_APPROVER_HIERARCHY where REQUIREMENT_ID = ?;";
					 ps = conn.prepareStatement(sql);
					 ps.setLong(1, requirementId);
					 rs = ps.executeQuery();
			 }catch(Exception e){
				 e.printStackTrace();
			 }
		     return rs;
		}


		private Integer getNumberOfApplicants(String requirementId, Connection conn, String orgLocationId) {
			PreparedStatement ps=null;
			ResultSet rs=null;
			 Integer numberOfApplicant =0;
			 try{
				 String sql ="select count(*) from REQUIREMENT_SUBMISSION rs where rs.ORG_LOCATION_ID = ? and rs.REQUIREMENT_ID =?";
					 ps = conn.prepareStatement(sql);
					 ps.setLong(1, Long.parseLong(orgLocationId));
					 ps.setLong(2, Long.parseLong(requirementId));
					 rs = ps.executeQuery();
					 while(rs.next()){
						 numberOfApplicant = Integer.parseInt(rs.getString(1));
					 }
			 }catch(Exception e){
				 e.printStackTrace();
			 }
			return numberOfApplicant;
			
		}


		private ResultSet getTeamDetails(long requirementId, Connection conn) {
			PreparedStatement ps=null;
			ResultSet rs=null;
			 try{
					String sql = "select * from REQUIREMENT_ASSIGN_TO reqAssaign  where reqAssaign.STATUS = 1 and reqAssaign.REQUIREMENT_ID = ? ;";
					 ps = conn.prepareStatement(sql);
					 ps.setLong(1, requirementId);
					 rs = ps.executeQuery();
			 }catch(Exception e){
				 e.printStackTrace();
			 }
		     return rs;
		}
		
		private ResultSet getNoOfApplicantDetails(long requirementId,String orgLocationId, Connection conn) {
			PreparedStatement ps=null;
			ResultSet rs=null;
			 try{
					String sql = "select count(*) from REQUIREMENT_SUBMISSION rs where rs.REQUIREMENT_ID = ? and rs.ORG_LOCATION_ID = ?;";
					 ps = conn.prepareStatement(sql);
					 ps.setLong(1, requirementId);
					 ps.setString(2, orgLocationId);
					 rs = ps.executeQuery();
			 }catch(Exception e){
				 e.printStackTrace();
			 }
		     return rs;
		}


		private ResultSet getApproverUserDetails(long requirementId, Connection conn) throws SQLException {
			PreparedStatement ps=null;
			ResultSet rs=null;
			 try{
					String sql = "select * from REQUIREMENT_APPROVER_HIERARCHY rah where rah.REQUIREMENT_ID = ? ;";
					 ps = conn.prepareStatement(sql);
					 ps.setLong(1, requirementId);
					 rs = ps.executeQuery();
			 }catch(Exception e){
				 e.printStackTrace();
			 }
		     return rs;
		}
		
		
		
	/**
	 * read client Contact name based on orgLocId
	 * @param orgLocId
	 * @param clientContactSql
	 * @param conn 
	 * @return ResultSet
	 */
		private ResultSet getClientContactName(String orgLocId, String clientContactSql, Connection conn) {
			
			PreparedStatement ps = null;
			ResultSet rs = null ;
			try {
				 ps = conn.prepareStatement(clientContactSql);
				ps.setString(1, orgLocId);
				rs = ps.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return rs;
		}

		/**
		 * read client  name based on location
		 * @param conn 
		 * @param orgLocId
		 * @param clientContactSql
		 * @return ResultSet
		 */
		private String getClientName(String location, Connection conn) {
			
	        String orgName = null;
			String sql = "SELECT org.ORG_NAME FROM "+dbconfig.getSchema()+".ORG_LOCATIONS orgloc INNER JOIN ORG org on orgloc.ORG_ID=org.ID where orgloc.ID = ?;";
			PreparedStatement ps = null;
			try {
				 ps = conn.prepareStatement(sql);
				ps.setString(1, location);
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()){
					orgName = rs.getString("ORG_NAME");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return orgName;
			
			
		}

	/**
	 * Reading client details
	 * @param relationId
	 * @param conn 
	 * @return ResultSet
	 * @throws SQLException
	 */
		private ResultSet getClientContactDetails(String relationId, Connection conn) throws SQLException {
			PreparedStatement ps=null;
			ResultSet rs=null;
			 try{
					String sql = "select * from ORG_RELATIONS as orgrel where orgrel.ID = ?";
					 ps = conn.prepareStatement(sql);
					 ps.setString(1, relationId);
					 rs = ps.executeQuery();
			 }catch(Exception e){
				 e.printStackTrace();
			 }
		     return rs;
		}
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
	 
   public String firstLetterCapitalizedString(String name){
		 String finalString = WordUtils.capitalizeFully(name);
		return finalString;
	}
   
   protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
   
   private boolean isNullOrEmpty(long long1) {
		// TODO Auto-generated method stub
		return false;
	}
   
   

}
