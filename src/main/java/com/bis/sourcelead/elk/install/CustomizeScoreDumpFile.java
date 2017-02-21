package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class CustomizeScoreDumpFile {
		private ComboPooledDataSource comboPooledDataSource;
		
		
	public void addCustomizeScore() throws IOException, SQLException {
		List<CustomizeSocreIndex> customizescorelist = geRequirementCustomizeScore();
		Connection conn = null ;
		PreparedStatement ps = null;
		String query = " insert into sourcelead.RECOMMENDED_CUSTOMIZE_SCORE (ID,REQIREMENT_ID, ORGLOCATION_ID, CUSTOMIZE_SKILLS, CUSTOMIZE_SKILL_SCORE, CUSTOMIZE_MIN_EXPERIENCE, CUSTOMIZE_MAX_EXPERIENCE, "
				+ "CUSTOMIZE_EXPERIENCE_SCORE, CUSTOMIZE_RATE, CUSTOMIZE_RATE_TYPE, CUSTOMIZE_RATE_SCORE, CUSTOMIZE_NOTICEPERIOD, CUSTOMIZE_NOTICEPERIOD_SCORE, CUSTOMIZE_KEYWORDS, "
				+ "CUSTOMIZE_KEYWORDS_SCORE, CUSTOMIZE_COUNTRY, CUSTOMIZE_STATE, CUSTOMIZE_CITY, CUSTOMIZE_LOCATION_SCORE, STATUS, CREATED_DATE, CREATED_BY)"
				+ " values (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
			try {
				DataSource dataSource = comboPooledDataSource();
				conn = dataSource.getConnection();
				ps = conn.prepareStatement(query);
			Long i=1L;
				for(CustomizeSocreIndex cus :customizescorelist){
				ps.setLong(1, i);
				ps.setLong(2, cus.getRequirementId());
				ps.setLong(3, cus.getOrgLocationId());
				ps.setString(4,cus.getCustomizeSkills());
				ps.setString(5,cus.getCustomizeSkillScore());
				ps.setString(6,cus.getCustomizeMinExperience());
				ps.setString(7,cus.getCustomizeMaxExperience());
				ps.setString(8,cus.getCustomizeExperienceScore());
				ps.setString(9,cus.getCustomizeRate());
				ps.setString(10,cus.getCustomizeRatetype());
				ps.setString(11,cus.getCustomizeRateScore());
				ps.setString(12, null);
				ps.setString(13,cus.getCustomizeNoticeperiodScore());
				ps.setString(14, null);
				ps.setString(15,cus.getCustomizeKeywordsScore());
				ps.setString(16,cus.getCustomizeCountry());
				ps.setString(17,cus.getCustomizeState());
				ps.setString(18,cus.getCustomizeCity());
			    ps.setString(19,cus.getCustomizeLocationScore());
			    ps.setString(20, null);
			    ps.setDate(21, cus.getCreatedDate());
			    ps.setString(22,cus.getCreatedBy());
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
		
		
		
		
	}


	/**
	 * this is method using get the RequirementCustomizeScore     
	 * @author Laxman
	 * @return
	 */
	private List<CustomizeSocreIndex> geRequirementCustomizeScore() {
		List<CustomizeSocreIndex> reqIndexList = new ArrayList<>();
		Connection conn = null ;
		Statement stmt = null;
		try {
			String sql = "SELECT * FROM sourcelead.REQUIREMENT requirement where requirement.id NOT IN (SELECT score.REQIREMENT_ID FROM sourcelead.RECOMMENDED_CUSTOMIZE_SCORE score) ";
			DataSource dataSource = comboPooledDataSource();
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				CustomizeSocreIndex cus = new CustomizeSocreIndex();
                cus.setRequirementId(rs.getLong("ID"));
                cus.setOrgLocationId(rs.getLong("ORG_LOCATION_ID"));
                cus.setCustomizeSkills(rs.getString("SKILLS"));
                cus.setCustomizeSkillScore(Constants.SCORE_20);
                cus.setCustomizeMinExperience(rs.getString("MIN_EXP"));
                cus.setCustomizeMaxExperience(rs.getString("MAX_EXP"));
                cus.setCustomizeExperienceScore(Constants.SCORE_20);
                cus.setCustomizeRate(rs.getString("RATE"));
                cus.setCustomizeRatetype(rs.getString("RATE_TYPE"));
                cus.setCustomizeRateScore(Constants.SCORE_20);
                cus.setCustomizeNoticeperiodScore(Constants.SCORE_10);
                cus.setCustomizeKeywordsScore(Constants.SCORE_20);
                cus.setCustomizeCountry(rs.getString("COUNTRY"));
                cus.setCustomizeState(rs.getString("STATE"));
                cus.setCustomizeCity(rs.getString("CITY"));
                cus.setCustomizeLocationScore(Constants.SCORE_10);
                cus.setCreatedBy(rs.getString("CREATED_BY"));
                cus.setCreatedDate(rs.getDate("CREATED_DATE"));
				reqIndexList.add(cus);
				
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
		CustomizeScoreDumpFile dump = new CustomizeScoreDumpFile();
		dump.addCustomizeScore();
	}

}