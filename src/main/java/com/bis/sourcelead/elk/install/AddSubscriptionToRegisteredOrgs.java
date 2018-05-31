package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.ibm.icu.util.Calendar;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class AddSubscriptionToRegisteredOrgs {
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Venkat I
 * this method is using for org subscription
 * @throws IOException
 * @throws SQLException
 */
public void updateSubscription() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			
		      System.out.println("----Adding subscription to orgs starts -----");
		      
			 String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".ROLES roles where roles.ROLE_NAME = 'ROLE_ADMIN' ";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			
			
			 while(rs1.next()){
				 Long  orgId = rs1.getLong("ORG_ID");
				 Long orgLocationId =  rs1.getLong("ORG_LOCATION_ID");
				 String country = null;
				 String orgType = null;
				 String createdDate = null;
				 String timeZoneId = null;
				 String actionIds = rs1.getString("ACTION_ID");
				 
				 String sql2 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS orgloc where orgloc.ID = '"+orgLocationId+"' ";
				 Statement stmt2 = conn.createStatement();
				 ResultSet rs2 = stmt2.executeQuery(sql2);
				 if (rs2.next()) {
					 country = rs2.getString("COUNTRY"); 
					 timeZoneId = rs2.getString("TIMEZONE_ID"); 
				 }
				 
				 
				 String sql4 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LICENSE_SUBSCRIPTION subscription where subscription.ORG_ID = '"+orgId+"' and subscription.COUNTRY = '"+country+"' ";
				 Statement stmt3 = conn.createStatement();
				 ResultSet rs4 = stmt3.executeQuery(sql4);
				 
				 if(!rs4.next()) {
					 
					 String sql3 = "SELECT * FROM "+dbconfig.getSchema()+".ORG org where org.ID = '"+orgId+"' ";
					 Statement stmt4 = conn.createStatement();
					 ResultSet rs3 = stmt4.executeQuery(sql3);
					 if (rs3.next()) {
						 orgType = rs3.getString("ORG_TYPE"); 
						 createdDate = rs3.getString("CREATED_DATE");
					 }
					 
					 String subscriptionType = null;
					 String currency = "USD";
					 
					 if(orgType.equalsIgnoreCase("Guest")) {
						 subscriptionType = "6";
					 }else {
						 subscriptionType = "1";
					 }
					 
			/*		 String sql5 = "SELECT * FROM "+dbconfig.getSchema()+".countries countries where countries.Name = '"+country+"' ";
					 Statement stmt5 = conn.createStatement();
					 ResultSet rs5 = stmt5.executeQuery(sql5);
					 if(rs5.next()) {
						 currency = rs5.getString("currency");
					 }*/
					 
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						Date subscriptionDate = df.parse(createdDate);
						 
						 Calendar toDate = Calendar.getInstance();
						 toDate.setTime(subscriptionDate);  
						 toDate.add(Calendar.MONTH, +1); 
						 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						 Date endDate = dateFormat.parse(dateFormat.format(toDate.getTime()));
						 String endDateStr = df.format(endDate);
						 String createdDateStr = df.format(subscriptionDate);
						 
						 
						 String[] actionIdsList = actionIds.split(",");
						 Set<Long> tabIds = new HashSet<>();
						 for(int i=0 ; i<actionIdsList.length; i++) {
							 String sql6 = "SELECT * FROM "+dbconfig.getSchema()+".SOURCELEAD_ACTIONS actions where actions.ID = '"+actionIdsList[i]+"' ";
							 Statement stmt6 = conn.createStatement();
							 ResultSet rs6 = stmt6.executeQuery(sql6);
							 if(rs6.next()) {
								 Long tabId = rs6.getLong("TAB_ID");
								 String sql7 = "SELECT * FROM "+dbconfig.getSchema()+".TABS tabs where tabs.ID = '"+tabId+"' ";
								 Statement stmt7 = conn.createStatement();
								 ResultSet rs7 = stmt7.executeQuery(sql7);
								 if(rs7.next()) {
									 if(rs7.getString("TAB_ID") != null) {
										 if(rs7.getLong("ID") == 14L) {
											 tabIds.add(rs7.getLong("ID"));
										 }else {
											 tabIds.add(rs7.getLong("TAB_ID"));
										 }
									 }else {
										 tabIds.add(rs7.getLong("ID"));
									 }
								 }
								 
							 }
						 }
						 
						 StringBuilder tabsBuilder = new StringBuilder();
						 for (Long tabId : tabIds) {
							 tabsBuilder.append(tabId).append(",");
						 }
						 String tabsBuilderString = tabsBuilder.substring(0, tabsBuilder.length()-1);
						 
						 
					      String query1 = " insert into "+dbconfig.getSchema()+".ORG_LICENSE_SUBSCRIPTION (ORG_ID, COUNTRY, SUBSCRIPTION_TYPE, USER_SUBSCRIPTION_TYPE, STORAGE_SUBSCRIPTION_TYPE,  "
								+ "CURRENCY, INVOICE_AMOUNT, SUBSCRIPTION_TABS,FROM_DATE,TO_DATE,TIMEZONE_ID,UUID,STATUS,CREATED_BY,CREATED_DATE,IS_INVOICE_AMOUNT_PAID)"
								+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			    		
			    		try {
			    			 PreparedStatement pstmt2 = conn.prepareStatement(query1);
				    		 pstmt2.setLong(1, orgId);
				    		 pstmt2.setString(2,country);
				    		 pstmt2.setString(3, subscriptionType);
				    		 pstmt2.setString(4, "8");
				    		 pstmt2.setString(5,"10");
				    		 pstmt2.setString(6,currency);
				    		 pstmt2.setDouble(7,0d);
				    		 pstmt2.setString(8,tabsBuilderString);
				    		 pstmt2.setString(9,createdDateStr);
				    		 pstmt2.setString(10,endDateStr);
				    		 pstmt2.setString(11,timeZoneId);
				    		 pstmt2.setString(12,SourceLeadRandomCodeHelper.generateRandomUUID());
				    		 pstmt2.setString(13,"1");
				    		 pstmt2.setLong(14,1l);
				    		 pstmt2.setString(15,createdDateStr);
				    		 pstmt2.setString(16,"N");
				    		 pstmt2.execute();	
				    		 
			    		}catch (Exception e){   
							e.printStackTrace();
						}
				 }
				 
				
			}
			System.out.println("----Adding subscription to orgs ends-----");
		
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
}


/**
 * getting connection
 * @return DataSource
 */ 
private DataSource comboPooledDataSource() {
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
	AddSubscriptionToRegisteredOrgs dump = new AddSubscriptionToRegisteredOrgs();
	dump.updateSubscription();
}

}
