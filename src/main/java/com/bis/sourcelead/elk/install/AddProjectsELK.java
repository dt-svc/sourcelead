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
import com.bis.sourcelead.elk.index.ProjectsIndex;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

public class AddProjectsELK {
	private static final Logger logger = LoggerFactory.getLogger(AddProjectsELK.class);
	private ComboPooledDataSource comboPooledDataSource;
	 private TransportClient client;
	 
	 protected boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}
	 
	public void addProjectsELK() throws IOException, SQLException {
		ArrayList<ProjectsIndex> elkProjectsList = getElkProjects();
		if(elkProjectsList != null && !elkProjectsList.isEmpty()){
			for (ProjectsIndex projectsIndex :elkProjectsList) {
				saveOrUpdateElkDocument(projectsIndex, Constants.ELK_PROJECT_INDEX, Constants.ELK_PROJECT_TYPE,projectsIndex.getId().toString());
			}
		}
		  logger.info("Projectss Successfully Added To Elk");
	}
	
	private ArrayList<ProjectsIndex> getElkProjects(){
		 DataSource dataSource = comboPooledDataSource();
		ArrayList<ProjectsIndex> projectsIndexsList = new ArrayList<ProjectsIndex>();
		 try {
				String sql = "SELECT * FROM sourcelead.PROJECTS mailbox";
				Connection conn = dataSource.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
			    while(rs.next()){
			    	ProjectsIndex projectsIndex = new ProjectsIndex();
			    	
			    	projectsIndex = new ProjectsIndex();
					projectsIndex.setId(rs.getLong("ID"));
					projectsIndex.setProjectSequeneNumber(rs.getString("PROJECT_SEQUENCE_NUMBER"));
					projectsIndex.setProjectName(rs.getString("PROJECT_NAME"));
					projectsIndex.setStartDate(rs.getString("START_DATE"));
					projectsIndex.setEndDate(rs.getString("END_DATE"));
					projectsIndex.setInvoiceFrequency(rs.getString("INVOICE_FREQUENCY"));
					projectsIndex.setProjectStatus(rs.getString("PROJECT_STATUS"));
					
					
					/*if(!isNullOrEmpty(rs.getString("CANDIDATE_ID"))){
						projectsIndex.setCandidateId(rs.getString("CANDIDATE_ID"));
					}*/
					
					
					
					
			    	
					projectsIndexsList.add(projectsIndex);
			    }
			   conn.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return projectsIndexsList;
	}
	
	
	public void saveOrUpdateElkDocument(ProjectsIndex projectsIndex,String index,String type,String id) {
		String entityInJson = null;
		try {
		    entityInJson = toJSON(projectsIndex);
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
			   AddProjectsELK addProjectsELK = new AddProjectsELK();
			   addProjectsELK.addProjectsELK();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
