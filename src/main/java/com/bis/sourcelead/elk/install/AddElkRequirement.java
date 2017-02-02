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

import javax.sql.DataSource;

import org.apache.commons.lang.exception.ExceptionUtils;
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
				if(null != rs.getString("RATE") && !"".equalsIgnoreCase(rs.getString("RATE"))){
					requirementIndex.setRate(Float.parseFloat(rs.getString("RATE")));
				}
				requirementIndex.setRateType(rs.getString("RATE_TYPE"));
				if(null != rs.getString("MIN_EXP") && !"".equalsIgnoreCase(rs.getString("MIN_EXP"))){
					requirementIndex.setMinExperience(Integer.parseInt(rs.getString("MIN_EXP")));
				}
				if(null != rs.getString("MAX_EXP") && !"".equalsIgnoreCase(rs.getString("MAX_EXP"))){
					requirementIndex.setMaxExperience(Integer.parseInt(rs.getString("MAX_EXP")));
				}
				requirementIndex.setRequirementStatus(rs.getString("REQUIREMENT_SUBMISSION_STATUS"));
				requirementIndex.setCountry(rs.getString("COUNTRY"));
				requirementIndex.setState(rs.getString("STATE"));
				requirementIndex.setCity(rs.getString("CITY"));
				requirementIndex.setReqCreatedBy(rs.getString("CREATED_BY"));
				requirementIndex.setReqUpdatedBy(rs.getString("UPDATED_BY"));
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String reqcreatedDate = dateFormat.format(rs.getDate("CREATED_DATE"));
				requirementIndex.setReqCreatedDate(reqcreatedDate);
				String reqUpDatedDate = dateFormat.format(rs.getDate("CREATED_DATE"));
				requirementIndex.setReqUpdatedDate(reqUpDatedDate);
				requirementIndex.setIsTemplate(rs.getString("IS_Template"));
				requirementIndex.setManagerUserId(rs.getString("MANAGER_USER_ID"));
				ResultSet approverSet = getApproverUserDetails(Long.parseLong(rs.getString("ID")));
				while(approverSet.next()){
					if(approverSet.getInt("LEVEL") == 1){
						requirementIndex.setReqLevel1ApproverUserId(approverSet.getString("APPROVER_USER_ID").split(","));
					}
					if(approverSet.getInt("LEVEL") == 2){
						requirementIndex.setReqLevel2ApproverUserId(approverSet.getString("APPROVER_USER_ID").split(","));
					}
				}
				
				ResultSet teamSet = getTeamDetails(Long.parseLong(rs.getString("ID")));
				while(teamSet.next()){
					requirementIndex.setTeamOwnerUserId(teamSet.getString("OWNER_USER_ID").split(","));
					requirementIndex.setTeamAssignUserId(teamSet.getString("ASSIGN_USER_ID").split(","));
				}
				
				ResultSet noOfApplicants = getNoOfApplicantDetails(Long.parseLong(rs.getString("ID")),rs.getString("ORG_LOCATION_ID"));
				while(noOfApplicants.next()){
					System.out.println(noOfApplicants.getString(1));
					requirementIndex.setTeamOwnerUserId(noOfApplicants.getString(1).split(","));
				}
				
				String education = rs.getString("EDUCATION");
				if(null != education && !"".equalsIgnoreCase(education)){
					requirementIndex.setEducation(education.split(","));
				}
				if(rs.getString("ORG_RELATION_ID") !=null && !"".equalsIgnoreCase(rs.getString("ORG_RELATION_ID"))){
					ResultSet rs1 = getClientContactDetails(rs.getString("ORG_RELATION_ID"));
					String clientName =null;;
					while(rs1.next()){
						String type = rs1.getString("INITIATOR_TYPE");
						if("Client".equalsIgnoreCase(type)){
							clientName = getClientName(rs1.getString("LOCATION_ID"));
						}
						else{
							clientName = getClientName(rs1.getString("RESPONDER_LOCATION_ID"));
						}
					}
					requirementIndex.setClient(clientName);
					String skills = rs.getString("SKILLS");
					if(!("").equalsIgnoreCase(skills) && skills!=null){
						requirementIndex.setSkills(skills.split(","));
					}
					requirementIndex.setJobType(rs.getString("JOB_TYPE"));
					requirementIndex.setJobId(rs.getString("JOB_ID"));
					String clientContactName = null;
					String clientContactEmail = null;
					while(rs1.next()){
						String type = rs1.getString("INITIATOR_TYPE");
						if("Client".equalsIgnoreCase(type)){
							String clientContactSql = "SELECT user.FIRST_NAME,user.EMAIL FROM sourcelead.ORG_SPOC spoc INNER JOIN USER user on spoc.OWNER_USER_ID=user.ID where spoc.INITIATOR_LOCATION_ID = ?;";
							ResultSet clientNameRs = getClientContactName(rs.getString("LOCATION_ID"),clientContactSql);
							clientContactName = clientNameRs.getString("FIRST_NAME");
							clientContactEmail = clientNameRs.getString("EMAIL");
							
						}
						if("Vendor".equalsIgnoreCase(type)){
							String clientContactSql = "SELECT user.FIRST_NAME,user.EMAIL FROM sourcelead.ORG_SPOC spoc INNER JOIN USER user on spoc.SPOC_USER_ID=user.ID where spoc.RESPONDER_LOCATION_ID = ?;";
							ResultSet clientNameRs = getClientContactName(rs.getString("RESPONDER_LOCATION_ID"),clientContactSql);
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
	
	
	private ResultSet getTeamDetails(long requirementId) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		 try{
			  logger.info("********************************8"+requirementId);  
			 DataSource dataSource = comboPooledDataSource();
				String sql = "select * from REQUIREMENT_ASSIGN_TO reqAssaign INNER JOIN TEAM team on reqAssaign.TEAM_ID=team.ID where reqAssaign.REQUIREMENT_ID = ? ;";
				 conn = dataSource.getConnection();
				 ps = conn.prepareStatement(sql);
				 ps.setLong(1, requirementId);
				 rs = ps.executeQuery();
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
		 }
	     return rs;
	}
	
	private ResultSet getNoOfApplicantDetails(long requirementId,String orgLocationId) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		 try{
			  logger.info("*******************orgLocationId************"+orgLocationId);  
			 DataSource dataSource = comboPooledDataSource();
				String sql = "select count(*) from REQUIREMENT_SUBMISSION rs where rs.REQUIREMENT_ID = ? and rs.ORG_LOCATION_ID = ?;";
				 conn = dataSource.getConnection();
				 ps = conn.prepareStatement(sql);
				 ps.setLong(1, requirementId);
				 ps.setString(2, orgLocationId);
				 rs = ps.executeQuery();
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
		 }
	     return rs;
	}


	private ResultSet getApproverUserDetails(long requirementId) throws SQLException {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		 try{
			  logger.info("********************************team..."+requirementId);  
			 DataSource dataSource = comboPooledDataSource();
				String sql = "select * from REQUIREMENT_APPROVER_HIERARCHY rah where rah.REQUIREMENT_ID = ? ;";
				 conn = dataSource.getConnection();
				 ps = conn.prepareStatement(sql);
				 ps.setLong(1, requirementId);
				 rs = ps.executeQuery();
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
		 }
	     return rs;
	}
	
	
	
/**
 * read client Contact name based on orgLocId
 * @param orgLocId
 * @param clientContactSql
 * @return ResultSet
 */
	private ResultSet getClientContactName(String orgLocId, String clientContactSql) {
		DataSource dataSource = comboPooledDataSource();
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null ;
		try {
			conn = dataSource.getConnection();
			 ps = conn.prepareStatement(clientContactSql);
			ps.setString(1, orgLocId);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rs;
	}

	/**
	 * read client  name based on location
	 * @param orgLocId
	 * @param clientContactSql
	 * @return ResultSet
	 */
	private String getClientName(String location) {
		
		DataSource dataSource = comboPooledDataSource();
        String orgName = null;
		String sql = "SELECT org.ORG_NAME FROM sourcelead.ORG_LOCATIONS orgloc INNER JOIN ORG org on orgloc.ORG_ID=org.ID where orgloc.ID = ?;";
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			 ps = conn.prepareStatement(sql);
			ps.setString(1, location);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				orgName = rs.getString("ORG_NAME");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return orgName;
		
		
	}

/**
 * Reading client details
 * @param relationId
 * @return ResultSet
 * @throws SQLException
 */
	private ResultSet getClientContactDetails(String relationId) throws SQLException {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		 try{
			  logger.info("********************************8"+relationId);  
			 DataSource dataSource = comboPooledDataSource();
				String sql = "select * from ORG_RELATIONS as orgrel where orgrel.ID = ?";
				 conn = dataSource.getConnection();
				 ps = conn.prepareStatement(sql);
				 ps.setString(1, relationId);
				 rs = ps.executeQuery();
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
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
		    logger.info(type+" has been updated.");
		} 
        catch(DocumentMissingException e){
        	client.prepareIndex(index, type,requirementId)
		    .setSource(entityInJson).execute().actionGet();
		    logger.info(type+" has been indexed.");
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
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
}
