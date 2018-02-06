package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.engine.DocumentMissingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.elk.index.WebGeoCitiesIndex;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

public class AddElkWebGeoCities {
	private static final Logger logger = LoggerFactory.getLogger(AddElkWebGeoCities.class);
	private ComboPooledDataSource comboPooledDataSource;
	private TransportClient client;

	public void addELKWebGeoCities() throws IOException, SQLException {
		getElkWebGeoCities();
		/*ArrayList<CountryIndex> elkCountryList = getElkCountries();
		ArrayList<StateIndex> elkStateList = getElkWebState();
		if (null != elkWebGeoCitiesList && !elkWebGeoCitiesList.isEmpty()) {
			for (WebGeoCitiesIndex webgeoIndex : elkWebGeoCitiesList) {
				saveOrUpdateElkDocument(webgeoIndex, Constants.ELK_WEB_GEO_CITIES_INDEX, Constants.ELK_WEBGEO_CITIES_TYPE,webgeoIndex.getId().toString());
			}
		}
		if (null != elkCountryList && !elkCountryList.isEmpty()) {
			for (CountryIndex countryIndex : elkCountryList) {
				saveOrUpdateElkDocumentCountry(countryIndex, Constants.ELK_WEB_GEO_CITIES_INDEX, Constants.ELK_WEBGEO_CITIES_TYPE,countryIndex.getId().toString());
			}
		}
		if (null != elkStateList && !elkStateList.isEmpty()) {
			for (StateIndex stateIndex : elkStateList) {
				saveOrUpdateElkDocumentState(stateIndex, Constants.ELK_WEB_GEO_CITIES_INDEX, Constants.ELK_WEBGEO_CITIES_TYPE,stateIndex.getId().toString());
			}
		}*/
		logger.info("webgeoCities Successfully Added To Elk");
	}

	/*private ArrayList<WebGeoCitiesIndex> getElkWebGeoCities() throws SQLException {
		DataSource dataSource = comboPooledDataSource();
		ArrayList<WebGeoCitiesIndex> webGeoIndexsList = new ArrayList<WebGeoCitiesIndex>();
		Connection conn = null;
		Statement stmt = null;
		try {
			String sql = "SELECT * FROM sourcelead.WEBGEOCITIES webgeocities";
			conn = dataSource.getConnection();
		    stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				WebGeoCitiesIndex webIndex = new WebGeoCitiesIndex();
				webIndex.setId(rs.getLong("ID"));
				webIndex.setCityName(rs.getString("cityName"));
				webIndex.setCountryCode(rs.getString("countryCode"));
				webIndex.setStateCode(rs.getString("stateCode"));
				webIndex.setCityNameAscii(rs.getString("cityNameAscii"));
				webIndex.setLatitude(rs.getFloat("latitude"));
				webIndex.setLongitude(rs.getFloat("longitude"));
				webIndex.setTimezoneid(rs.getString("timezoneid"));
				
				
				
				saveOrUpdateElkDocument(webIndex, Constants.ELK_WEB_GEO_CITIES_INDEX, Constants.ELK_WEBGEO_CITIES_TYPE,webIndex.getId().toString());
				
				//webGeoIndexsList.add(webIndex);
				}
				

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			conn.close();
		}
		return webGeoIndexsList;
	}*/
	
	private void getElkWebGeoCities() throws SQLException {
		DataSource dataSource = comboPooledDataSource();
		Connection conn = dataSource.getConnection();;
		Statement stmt = conn.createStatement();
		Statement stmt1 = conn.createStatement();
		Statement stmt2 = conn.createStatement();
		try {
			String sql = "SELECT * FROM sourcelead.COUNTRIES countries";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String countryCode = rs.getString("Code2");
				String countryName = rs.getString("Name");
					String state = "SELECT * FROM sourcelead.STATES states where states.countryCode = '"+countryCode+"'";
					ResultSet rs1 = stmt1.executeQuery(state);
					while (rs1.next()) {
						String stateCode = rs1.getString("stateCode");
						String stateName = rs1.getString("stateName");
						String webGeo = "SELECT * FROM sourcelead.WEBGEOCITIES webgeocities where countryCode ='"+countryCode+"' and stateCode = '"+stateCode+"'";
						ResultSet rs2 = stmt2.executeQuery(webGeo);
						while (rs2.next()) {
							WebGeoCitiesIndex webIndex = new WebGeoCitiesIndex();
							webIndex.setId(rs2.getLong("ID"));
							webIndex.setCityName(rs2.getString("cityName"));
							webIndex.setCountryCode(rs2.getString("countryCode"));
							webIndex.setStateCode(rs2.getString("stateCode"));
							webIndex.setCityNameAscii(rs2.getString("cityNameAscii"));
							webIndex.setLatitude(rs2.getFloat("latitude"));
							webIndex.setLongitude(rs2.getFloat("longitude"));
							webIndex.setTimezoneid(rs2.getString("timezoneid"));
							StringBuilder location =  new StringBuilder();
							location.append(rs2.getString("cityName")).append(",").append(stateName).append(",").append(countryName);
							webIndex.setLocation(location.toString());
							saveOrUpdateElkDocument(webIndex, Constants.ELK_WEB_GEO_CITIES_INDEX, Constants.ELK_WEBGEO_CITIES_TYPE,webIndex.getId().toString());
						}
					}
			}
				

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			conn.close();
		}
	}
	

	public void saveOrUpdateElkDocument(WebGeoCitiesIndex webGeoCitiesIndex, String index, String type, String id) {
		String entityInJson = null;
		try {
			entityInJson = toJSON(webGeoCitiesIndex);
			client.prepareUpdate(index, type, id).setDoc(entityInJson).get();
			logger.info(type + " has been updated.");
		} catch (DocumentMissingException e) {
			client.prepareIndex(index, type, id).setSource(entityInJson).execute().actionGet();
			logger.info(type + " has been indexed.");
		} catch (Exception e) {
			logger.error(type + " could not be serialized. " + e.getLocalizedMessage());
		}

	}
	
	/*private void saveOrUpdateElkDocumentCountry(CountryIndex countryIndex, String index,String type, String id) {
		String entityInJson = null;
		try {
			entityInJson = toJSON(countryIndex);
			client.prepareUpdate(index, type, id).setDoc(entityInJson).get();
			logger.info(type + " has been updated.");
		} catch (DocumentMissingException e) {
			client.prepareIndex(index, type, id).setSource(entityInJson).execute().actionGet();
			logger.info(type + " has been indexed.");
		} catch (Exception e) {
			logger.error(type + " could not be serialized. " + e.getLocalizedMessage());
		}
		
	}
	
	private void saveOrUpdateElkDocumentState(StateIndex stateIndex,String index,String type, String id) {
		String entityInJson = null;
		try {
			entityInJson = toJSON(stateIndex);
			client.prepareUpdate(index, type, id).setDoc(entityInJson).get();
			logger.info(type + " has been updated.");
		} catch (DocumentMissingException e) {
			client.prepareIndex(index, type, id).setSource(entityInJson).execute().actionGet();
			logger.info(type + " has been indexed.");
		} catch (Exception e) {
			logger.error(type + " could not be serialized. " + e.getLocalizedMessage());
		}
		
	}
*/
	
	

	public String toJSON(Object obj) {
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

		/*try {
			Settings settings = Settings.settingsBuilder().put("cluster.name", dbconfig.getCluster())
					.put("node.name", dbconfig.getNode()).put("path.home", dbconfig.getPath()).build();
			client = TransportClient.builder().settings(settings).build().addTransportAddress(
					new InetSocketTransportAddress(InetAddress.getByName(dbconfig.getHost()), 9300));
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}*/

		return comboPooledDataSource;
	}

	public static void main(String[] args) throws IOException {

		try {
			AddElkWebGeoCities addElkWebGeoCities = new AddElkWebGeoCities();
			addElkWebGeoCities.addELKWebGeoCities();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
