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
import org.springframework.context.annotation.Bean;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.elk.index.UserIndex;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

public class AddElkUser {
	private static final Logger logger = LoggerFactory.getLogger(AddElkUser.class);
	private ComboPooledDataSource comboPooledDataSource;
	 private TransportClient client;
	public void addElkUser() throws IOException, SQLException {
		ArrayList<UserIndex> elkUserList = getElkUsers();
		if(null != elkUserList && !elkUserList.isEmpty()){
			for (UserIndex userIndex :elkUserList) {
				saveOrUpdateElkDocument(userIndex,Constants.ELK_USER_INDEX, Constants.ELK_USER_TYPE,userIndex.getUserId().toString());
			}
		}
		  logger.info("User Successfully Added To Elk");
	}
	
	private ArrayList<UserIndex> getElkUsers(){
		 DataSource dataSource = comboPooledDataSource();
		ArrayList<UserIndex> userIndexsList = new ArrayList<UserIndex>();
		 try {
				String sql = "SELECT * FROM sourcelead.USER_ROLE userRole";
				Connection conn = dataSource.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
			    while(rs.next()){
			    	UserIndex userIndex = new UserIndex();
			    	userIndex.setUserId(rs.getLong("USER_ID"));
			    	userIndex.setOrgLocationId(rs.getLong("ORG_LOCATIONS_ID"));
			    	userIndex.setUserStatus(rs.getString("USER_STATE"));
					userIndex.setUserType(rs.getString("USER_TYPE"));
					userIndex.setStatus(rs.getString("STATUS"));
					userIndex.setTerminateReason(rs.getString("TERMINATE_REASON"));
					if(0 != rs.getLong("REPORTING_MANAGER_ID") && !"".equals(rs.getLong("REPORTING_MANAGER_ID"))){
						userIndex.setReportingManagerId(rs.getLong("REPORTING_MANAGER_ID"));
					}
					Timestamp createdDateTimestamp = rs.getTimestamp("CREATED_DATE");
					DateFormat StringDate = new SimpleDateFormat("dd-MM-yyyy"); 
				    if (null != createdDateTimestamp){
						String createdDate  = StringDate.format(new java.util.Date(createdDateTimestamp.getTime()));
					  userIndex.setCreatedDate(createdDate);
				    }
				    userIndex.setCreatedBy(rs.getString("CREATED_BY"));   
					Timestamp updatedDateTimestamp = rs.getTimestamp("UPDATED_DATE");
					if (null != updatedDateTimestamp){
						String updatedDate  = StringDate.format(new java.util.Date(updatedDateTimestamp.getTime()));
						userIndex.setUpdatedDate(updatedDate);
				    }
					userIndex.setUpdatedBy(rs.getString("UPDATED_BY"));
				    
					
					
				    Long userId = rs.getLong("USER_ID");
				    Long orgLocationId = rs.getLong("ORG_LOCATIONS_ID");
				    Long userRoleId =rs.getLong("ROLE_ID");
		    	    if(null != userId && userId != 0){
		    	    	    String sql1 = "SELECT * FROM sourcelead.USER user where user.ID = '"+ userId+"' ";
							Statement stmt1 = conn.createStatement();
							ResultSet rs1 = stmt1.executeQuery(sql1);
							if(rs1 != null){
								 while(rs1.next()){
									    userIndex.setFirstName(rs1.getString("FIRST_NAME"));
										userIndex.setLastName(rs1.getString("LAST_NAME"));
										userIndex.setEmail(rs1.getString("EMAIL"));
										userIndex.setMobilePhone(rs1.getString("MOBILE_PHONE"));
										userIndex.setIsMailSent(rs1.getString("IS_MAIL_SENT"));
										userIndex.setUserCode(rs1.getString("USER_CODE"));
							    }
							   
		    	           }
							
						    String sql2 = "SELECT * FROM sourcelead.EMPLOYEE employee where employee.USER_ID = '"+ userId+"' and employee.ORG_LOCATIONS_ID = '"+ orgLocationId+"' ";
							Statement stmt2 = conn.createStatement();
							ResultSet rs2 = stmt2.executeQuery(sql2);
							if(rs2 != null){
								 while(rs2.next()){
									userIndex.setEmployeeId(rs2.getString("EMPLOYEE_ID"));
									userIndex.setRate(rs2.getString("RATE"));
									userIndex.setRatePer(rs2.getString("RATE_PER"));
									if(null != rs2.getString("HIRE_DATE") && !"".equals(rs2.getString("HIRE_DATE"))){
									userIndex.setHireDate(rs2.getString("HIRE_DATE"));
									}

							    }
							   
		    	           }
						String sql3 = "SELECT * FROM sourcelead.ROLES roles where roles.ORG_LOCATION_ID='"+orgLocationId+"' and roles.ID = '"+userRoleId+"'";
						Statement stmt3 = conn.createStatement();
						ResultSet rs3 = stmt3.executeQuery(sql3);
						if(rs3 != null){
							 while(rs3.next()){
								userIndex.setUserRoleId(rs3.getString("ID"));
								
						    }
						   
	    	           }
							
		    	    }
		    	    userIndexsList.add(userIndex);
			    }
			   conn.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return userIndexsList;
	}
	
	
	public void saveOrUpdateElkDocument(UserIndex userIndex,String index,String type,String id) {
		String entityInJson = null;
		try {
		    entityInJson = toJSON(userIndex);
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
			   AddElkUser addElkUser = new AddElkUser();
			   addElkUser.addElkUser();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

}
