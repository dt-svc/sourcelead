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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.entity.ClassField;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.ibm.icu.util.Calendar;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import antlr.StringUtils;

public class EmailScenerioConversion {
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Venkat I
 * this method is using for org subscription
 * @throws IOException
 * @throws SQLException
 */
public void updateEmailScenerio() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			
		      System.out.println("----Modifing Email Scenerio starts -----");
		      
			 String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".EMAIL_SCENARIO";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			
			 while(rs1.next()){
				 Set<String> fieldsSet = new HashSet<>();
				 Long id = rs1.getLong("ID");
				  String body = null;
				  String subject = null;
				  String signature = null;
				 
					if(rs1.getString("BODY") != null && !rs1.getString("BODY").isEmpty()) {
						  body =  rs1.getString("BODY");
						  Matcher matcher = Pattern.compile("(\\{\\{)(.*?)(\\}\\})").matcher(body);
							while (matcher.find())
							{
								String delimeter = matcher.group();
							    if(delimeter != null && !delimeter.isEmpty()) {
							    	String delimeterStr = delimeter.substring(2, delimeter.length()-2);
							    	String fieldName = delimeterStr.split("\\.")[1];
							    	fieldsSet.add(fieldName);
							    	fieldName = fieldName.replaceAll("URL", "Url");
							    	fieldName = fieldName.substring(0,1).toUpperCase()+fieldName.substring(1, fieldName.length());
							    	fieldName = fieldName.replaceAll("(.)([A-Z])", "$1_$2");
							    	body = body.replace(delimeter, "["+fieldName+"]");
							    	
							    }
							}
					}
			
					
					if(rs1.getString("SUBJECT") != null && !rs1.getString("SUBJECT").isEmpty()) {
						subject =  rs1.getString("SUBJECT");
						  Matcher subMatcher = Pattern.compile("(\\{\\{)(.*?)(\\}\\})").matcher(subject);
							while (subMatcher.find())
							{
								String delimeter = subMatcher.group();
							    if(subMatcher != null && !delimeter.isEmpty()) {
							    	String delimeterStr = delimeter.substring(2, delimeter.length()-2);
							    	String fieldName = delimeterStr.split("\\.")[1];
							    	fieldsSet.add(fieldName);
							    	fieldName = fieldName.replaceAll("URL", "Url");
							    	fieldName = fieldName.substring(0,1).toUpperCase()+fieldName.substring(1, fieldName.length());
							    	fieldName = fieldName.replaceAll("(.)([A-Z])", "$1_$2");
							    	subject = subject.replace(delimeter, "["+fieldName+"]");
							    	
							    }
							}
					}
					 
						
						if(rs1.getString("SIGNATURE") != null && !rs1.getString("SIGNATURE").isEmpty()) {
							signature =  rs1.getString("SIGNATURE");
							  Matcher signatureMatcher = Pattern.compile("(\\{\\{)(.*?)(\\}\\})").matcher(signature);
								while (signatureMatcher.find())
								{
									String delimeter = signatureMatcher.group();
								    if(signatureMatcher != null && !delimeter.isEmpty()) {
								    	String delimeterStr = delimeter.substring(2, delimeter.length()-2);
								    	String fieldName = delimeterStr.split("\\.")[1];
								    	fieldsSet.add(fieldName);
								    	fieldName = fieldName.replaceAll("URL", "Url");
								    	fieldName = fieldName.substring(0,1).toUpperCase()+fieldName.substring(1, fieldName.length());
								    	fieldName = fieldName.replaceAll("(.)([A-Z])", "$1_$2");
								    	signature = signature.replace(delimeter, "["+fieldName+"]");
								    	
								    }
								}
						}
							 
							
							 String query = "update "+dbconfig.getSchema()+".EMAIL_SCENARIO set SUBJECT = ?,  BODY = ?,  SIGNATURE = ?  where ID = ?";
							 PreparedStatement pstmt = conn.prepareStatement(query);
							 pstmt.setString(1, subject);
							 pstmt.setString(2, body);
							 pstmt.setString(3, signature);
							 pstmt.setLong(4, id);
							 pstmt.executeUpdate(); 
					        
					        if(fieldsSet != null && fieldsSet.size() > 0) {
					        	
					        	for(String fieldName : fieldsSet) {
					        		String scenerioFieldName = fieldName;
					        		fieldName = fieldName.replaceAll("URL", "Url");
					        		fieldName = fieldName.substring(0,1).toUpperCase()+fieldName.substring(1, fieldName.length());
					        		fieldName = fieldName.replaceAll("(.)([A-Z])", "$1_$2");
					        		StringBuilder delimiterBuilder = new StringBuilder();
					        		delimiterBuilder.append("[").append(fieldName).append("]");
					        		
					        		String query1 = " insert into "+dbconfig.getSchema()+".CLASS_FIELD (FIELD_NAME, DELIMITER_STR, EMAIL_SCENARIO_ID)"
											+ " values (?, ?, ?)";
						    		
						    		try {
						    			 PreparedStatement pstmt2 = conn.prepareStatement(query1);
							    		 pstmt2.setString(1, scenerioFieldName);
							    		 pstmt2.setString(2,delimiterBuilder.toString());
							    		 pstmt2.setLong(3, id);
							    		 pstmt2.execute();	
							    		 
						    		}catch (Exception e){   
										e.printStackTrace();
									}
					        	}
					        	
					        	
					        }
				
			  }
			System.out.println("----Modifing Email Scenerio ends-----");
		
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
	EmailScenerioConversion dump = new EmailScenerioConversion();
	dump.updateEmailScenerio();
}

}
