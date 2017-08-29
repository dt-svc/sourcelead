package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.plexus.util.StringUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.engine.DocumentMissingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

public class AddElkRequirement {
	
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(AddElkRequirement.class);
	private ComboPooledDataSource comboPooledDataSource;
	private TransportClient client;
	public void addElkRequirement() throws IOException, SQLException {
		ArrayList<RequirementIndex> requirementList = getElkRequirements();
		if(null != requirementList && !requirementList.isEmpty()){
			for (RequirementIndex requirementIndex :requirementList) {
				saveOrUpdateElkDocument(requirementIndex, Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_INDEX,requirementIndex.getRequirementId().toString());
			}
		}
		  logger.info("Rquirements Successfully Added To Elk");
	}


	/**
	 * Read all requirements from DB and store it into Elk
	 * @return ArrayList<RequirementIndex>
	 */
	private ArrayList<RequirementIndex> getElkRequirements() {
		ArrayList<RequirementIndex> reqIndexList = new ArrayList<>();
		Connection conn = null ;
		Statement stmt = null;
		try {
			String sql = "SELECT * FROM sourcelead.REQUIREMENT req INNER JOIN USER user on req.MANAGER_USER_ID = user.ID;";
			DataSource dataSource = comboPooledDataSource();
			 conn = dataSource.getConnection();
			 stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				RequirementIndex requirementIndex = new RequirementIndex();
				if(null != rs.getString("ID") && !"".equalsIgnoreCase(rs.getString("ID"))){
					requirementIndex.setRequirementId(Long.parseLong(rs.getString("ID")));
				}
				if(null != rs.getString("ORG_LOCATION_ID") && !"".equalsIgnoreCase(rs.getString("ORG_LOCATION_ID"))){
					requirementIndex.setOrgLocationId(Long.parseLong(rs.getString("ORG_LOCATION_ID")));
				}
				requirementIndex.setJobTitle(rs.getString("JOB_TITLE"));
				requirementIndex.setJobId(rs.getString("JOB_ID"));
				requirementIndex.setCurrentRateCurrency(rs.getString("CURRENCY_CODE"));
				requirementIndex.setNumberOfPositions(rs.getString("POSITIONS"));
				requirementIndex.setIndustryName(rs.getString("INDUSTRY_NAME"));
				requirementIndex.setIndustryName(rs.getString("TRAVEL_ALLOWANCE"));
				requirementIndex.setProjectDurationMonths(rs.getString("PROJECT_DURATION_MONTHS"));
				requirementIndex.setProjectDurationYears(rs.getString("PROJECT_DURATION_YEARS"));
				requirementIndex.setClosedDate(rs.getString("REQUIRMENT_CLOSED_DATE"));
				requirementIndex.setIsreceivingApplicants(rs.getString("IS_RECEIVING_APPLICANTS"));
				
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
					requirementIndex.setMinExperience(rs.getString("MIN_EXP"));
				}
				if(null != rs.getString("MAX_EXP") && !"".equalsIgnoreCase(rs.getString("MAX_EXP"))){
					requirementIndex.setMaxExperience(rs.getString("MAX_EXP"));
				}
				requirementIndex.setRequirementStatus(rs.getString("REQUIREMENT_SUBMISSION_STATUS"));
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
				requirementIndex.setGeneratedId(rs.getString("GENERATED_ID"));
				requirementIndex.setReleasedBy(rs.getString("RELEASED_BY"));
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
				List<String> assignTeamMembers = new ArrayList<>();
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
				List<String> teamOwners = new ArrayList<>();
				if(resultTeam != null){
					while(resultTeam.next()){
//						if(null!= resultTeam.getString("OWNER_USER_ID")&& !"".equalsIgnoreCase(resultTeam.getString("OWNER_USER_ID"))){
							teamOwners.add(resultTeam.getString("OWNER_USER_ID"));
//						}
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
				if(rs.getString("ORG_RELATION_ID") !=null && !"".equalsIgnoreCase(rs.getString("ORG_RELATION_ID"))){
					ResultSet rs1 = getClientContactDetails(rs.getString("ORG_RELATION_ID"),conn);
					String clientName =null;;
					while(rs1.next()){
						String type = rs1.getString("INITIATOR_TYPE");
						if("Client".equalsIgnoreCase(type)){
							clientName = getClientName(rs1.getString("LOCATION_ID"),conn);
						}
						else{
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
					String clientContactName = null;
					String clientContactEmail = null;
					while(rs1.next()){
						String type = rs1.getString("INITIATOR_TYPE");
						if("Client".equalsIgnoreCase(type)){
							String clientContactSql = "SELECT user.FIRST_NAME,user.EMAIL FROM sourcelead.ORG_SPOC spoc INNER JOIN USER user on spoc.OWNER_USER_ID=user.ID where spoc.INITIATOR_LOCATION_ID = ?;";
							ResultSet clientNameRs = getClientContactName(rs.getString("LOCATION_ID"),clientContactSql,conn);
							clientContactName = clientNameRs.getString("FIRST_NAME");
							clientContactEmail = clientNameRs.getString("EMAIL");
							
						}
						if("Vendor".equalsIgnoreCase(type)){
							String clientContactSql = "SELECT user.FIRST_NAME,user.EMAIL FROM sourcelead.ORG_SPOC spoc INNER JOIN USER user on spoc.SPOC_USER_ID=user.ID where spoc.RESPONDER_LOCATION_ID = ?;";
							ResultSet clientNameRs = getClientContactName(rs.getString("RESPONDER_LOCATION_ID"),clientContactSql,conn);
							if(null != clientNameRs.getString("FIRST_NAME") && null != clientNameRs.getString("FIRST_NAME")){
							clientContactName = clientNameRs.getString("FIRST_NAME")+" "+clientNameRs.getString("FIRST_NAME");
							}else{
								clientContactName = clientNameRs.getString("FIRST_NAME");
							}
							clientContactEmail = clientNameRs.getString("EMAIL");
							
						}
					}
					requirementIndex.setClientContactName(clientContactName);
					requirementIndex.setClientContactEmail(clientContactEmail);
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
				reqIndexList.add(requirementIndex);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return reqIndexList;
	}
	
	
	private ResultSet getTeanOwnerIds(String reqId, Connection conn) {
		PreparedStatement ps=null;
		ResultSet rs=null;
		Long requirementId = Long.parseLong(reqId);
		try{
				String sql = "SELECT * FROM sourcelead.TEAM team inner join REQUIREMENT_ASSIGN_TO req on team.ID = req.TEAM_ID where req.REQUIREMENT_ID = ?";
//				 conn = dataSource.getConnection();
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
				String sql = "SELECT * FROM sourcelead.REQUIREMENT_APPROVER_HIERARCHY where REQUIREMENT_ID = ?;";
//				 conn = dataSource.getConnection();
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
//	     return rs;
		return numberOfApplicant;
		
	}


	private ResultSet getTeamDetails(long requirementId, Connection conn) {
		PreparedStatement ps=null;
		ResultSet rs=null;
		 try{
//			 DataSource dataSource = comboPooledDataSource();
				String sql = "select * from REQUIREMENT_ASSIGN_TO reqAssaign  where reqAssaign.REQUIREMENT_ID = ? ;";
//				 conn = dataSource.getConnection();
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
//			 DataSource dataSource = comboPooledDataSource();
				String sql = "select count(*) from REQUIREMENT_SUBMISSION rs where rs.REQUIREMENT_ID = ? and rs.ORG_LOCATION_ID = ?;";
//				 conn = dataSource.getConnection();
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
//			 DataSource dataSource = comboPooledDataSource();
				String sql = "select * from REQUIREMENT_APPROVER_HIERARCHY rah where rah.REQUIREMENT_ID = ? ;";
//				 conn = dataSource.getConnection();
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
//		DataSource dataSource = comboPooledDataSource();
		
		PreparedStatement ps = null;
		ResultSet rs = null ;
		try {
//			conn = dataSource.getConnection();
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
		
//		DataSource dataSource = comboPooledDataSource();
        String orgName = null;
		String sql = "SELECT org.ORG_NAME FROM sourcelead.ORG_LOCATIONS orgloc INNER JOIN ORG org on orgloc.ORG_ID=org.ID where orgloc.ID = ?;";
		PreparedStatement ps = null;
		try {
//			conn = dataSource.getConnection();
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
//			 DataSource dataSource = comboPooledDataSource();
				String sql = "select * from ORG_RELATIONS as orgrel where orgrel.ID = ?";
//				 conn = dataSource.getConnection();
				 ps = conn.prepareStatement(sql);
				 ps.setString(1, relationId);
				 rs = ps.executeQuery();
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	     return rs;
	}


	/**
	 * stores Requirement into elk
	 * @param requirementIndex
	 * @param eLK_REQUIREMENT_INDEX(index)
	 * @param eLK_REQUIREMENT_INDEX2(type)
	 * @param requirementId
	 * @return void
	 */
	private void saveOrUpdateElkDocument(RequirementIndex requirementIndex, String index,String type, String requirementId) {
		String entityInJson = null;
		try {
		    entityInJson = toJSON(requirementIndex);
		    client.prepareUpdate(index, type,requirementId).setDoc(entityInJson).get();
		   // logger.info(type+" has been updated.");
		} 
        catch(DocumentMissingException e){
        	client.prepareIndex(index, type,requirementId)
		    .setSource(entityInJson).execute().actionGet();
		   // logger.info(type+" has been indexed.");
		}
		catch (Exception e) {
			logger.error(type+" could not be serialized. "+e.getLocalizedMessage());
		}
		
	}
	/**
	 * converts entity to JSOn Object
	 * @param obj
	 * @return
	 */
	 public String toJSON(Object obj){
	        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	            public HierarchicalStreamWriter createWriter(Writer writer) {
	                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	            }
	        });
	        return xstream.toXML(obj);
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
				AddElkRequirement addElkCandidate = new AddElkRequirement();
				   addElkCandidate.addElkRequirement();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
}
