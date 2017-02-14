package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class CandidateSkillsDumpFile {
		private ComboPooledDataSource comboPooledDataSource;
		
		
	public void addElkCandidate() throws IOException, SQLException {
		List<CandidateSkillCustomize> customizescorelist = geRequirementCustomizeScore();
		/*Connection conn = null ;
		PreparedStatement ps = null;
		String query = " insert into sourcelead.recommended_customize_score (ID,REQIREMENT_ID, ORGLOCATION_ID, CUSTOMIZE_SKILLS, CUSTOMIZE_SKILL_SCORE, CUSTOMIZE_MIN_EXPERIENCE, CUSTOMIZE_MAX_EXPERIENCE, "
				+ "CUSTOMIZE_EXPERIENCE_SCORE, CUSTOMIZE_RATE, CUSTOMIZE_RATE_TYPE, CUSTOMIZE_RATE_SCORE, CUSTOMIZE_NOTICEPERIOD, CUSTOMIZE_NOTICEPERIOD_SCORE, CUSTOMIZE_KEYWORDS, "
				+ "CUSTOMIZE_KEYWORDS_SCORE, CUSTOMIZE_COUNTRY, CUSTOMIZE_STATE, CUSTOMIZE_CITY, CUSTOMIZE_LOCATION_SCORE, STATUS, CREATED_DATE, CREATED_BY)"
				+ " values (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
			try {
				DataSource dataSource = comboPooledDataSource();
				conn = dataSource.getConnection();
				ps = conn.prepareStatement(query);
			Long i=1L;
				for(CandidateSkillCustomize cus :customizescorelist){
				ps.setLong(1, i);
				ps.setString(39, cus.getSkillsName());
			    ps.executeUpdate();		
			    i++;
				}
			}catch(Exception e){   
				e.printStackTrace();
			}finally {
				try {
					ps.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		
		
		*/
		
	}


	/**
	 * this is method using get the RequirementCustomizeScore     
	 * @author Laxman
	 * @return
	 */
	private List<CandidateSkillCustomize> geRequirementCustomizeScore() {
		List<CandidateSkillCustomize> reqIndexList = new ArrayList<>();
		 
		Connection conn = null ;
		Statement stmt1 = null;
		try {
			
			DataSource dataSource = comboPooledDataSource();
			conn = dataSource.getConnection();
			
				
				 String sql1 = "SELECT * FROM sourcelead.CANDIDATE cs";
				 stmt1 = conn.createStatement();
				 ResultSet rs1 = stmt1.executeQuery(sql1);
				
				while(rs1.next()){
					 Long  candidateId = rs1.getLong("ID");
					 Long  orgLocationsId = rs1.getLong("ORG_LOCATION_ID");
					 
					 Statement stmt2 = null;
					 String sql2 = "SELECT * FROM sourcelead.CANDIDATE_SKILLS cs where cs.ORG_LOCATION_ID = "+orgLocationsId+" and cs.CANDIDATE_ID = "+candidateId+" ";
					 conn = dataSource.getConnection();
					 stmt2 = conn.createStatement();
					 ResultSet rs2 = stmt2.executeQuery(sql2);
					 ArrayList<String> skillstoBeAdded = new ArrayList<String>();
					while(rs2.next()){
						skillstoBeAdded.add(rs2.getString("NAME"));
					}
						StringBuilder skillBuffer =  new StringBuilder();
						for(String skill :skillstoBeAdded){
							skillBuffer.append(skill);
						}
					
					
					 String query = "update sourcelead.CANDIDATE set SKILLS_NAME = ? where ID = ? ";
				     java.sql.PreparedStatement pstmt = conn.prepareStatement(query); // create a statement
				      pstmt.setString(1, skillBuffer.toString()); // set input parameter 1
				      pstmt.setLong(2, candidateId); // set input parameter 2
				      pstmt.executeUpdate(); 
				      
				}
			
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
		
		return reqIndexList;
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
			return comboPooledDataSource;
		}
	 
	public static void main(String[] args) throws IOException, SQLException {
		CandidateSkillsDumpFile dump = new CandidateSkillsDumpFile();
		dump.addElkCandidate();
	}

}