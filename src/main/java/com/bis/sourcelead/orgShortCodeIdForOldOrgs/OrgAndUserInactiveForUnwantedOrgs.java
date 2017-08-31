package com.bis.sourcelead.orgShortCodeIdForOldOrgs;


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

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class OrgAndUserInactiveForUnwantedOrgs {
	private ComboPooledDataSource comboPooledDataSource;
	
	public void OrgAndUserInactiveForUnwantedOrg() throws IOException, SQLException {
		Connection conn = null;
		Statement stmt1 = null;
		try {
			DataSource dataSource = comboPooledDataSource();
			conn = dataSource.getConnection();
			List<Long> orgIds = new ArrayList<>();
			orgIds.add(27L);
			orgIds.add(38L);
			orgIds.add(39L);
			orgIds.add(42L);
			orgIds.add(43L);
			orgIds.add(44L);
			orgIds.add(45L);
			orgIds.add(48L);
			orgIds.add(111L);
			orgIds.add(129L);
			orgIds.add(376L);
			orgIds.add(380L);
			orgIds.add(1140L);
			orgIds.add(1507L);
			String sql = "SELECT * FROM sourcelead.ORG org where org.ID= ? ";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (Long orgIds1 : orgIds) {
				pstmt.setLong(1, orgIds1);
				System.out.println(orgIds1);
				ResultSet rs1 = pstmt.executeQuery();
				while (rs1.next()) {
					Long orgId = rs1.getLong("ID");
					// orglocation list
					String orgLocations = "SELECT * FROM sourcelead.ORG_LOCATIONS orgLocations where orgLocations.ORG_ID ="
							+ orgId + "";
					stmt1 = conn.createStatement();
					ResultSet rs2 = stmt1.executeQuery(orgLocations);
					while (rs2.next()) {
						Long orgLocId = rs2.getLong("ID");
						// userrole list
						String userRole = "SELECT * FROM sourcelead.USER_ROLE userRole where userRole.ORG_LOCATIONS_ID ="+ orgLocId + "";
						stmt1 = conn.createStatement();
						ResultSet rs3 = stmt1.executeQuery(userRole);
						while (rs3.next()) {
							Long userRoleID = rs3.getLong("ID");

							// update userrole
							String query3 = "UPDATE sourcelead.USER_ROLE set USER_STATE = ?  where ID = ? ";
							PreparedStatement pstmt3 = conn.prepareStatement(query3);
							pstmt3.setString(1, "INACTIVE");
							pstmt3.setLong(2, userRoleID);
							pstmt3.executeUpdate();
							System.out.println("userRoleID" + userRoleID);
							// user list
							Long userId = rs3.getLong("USER_ID");
							String user = "SELECT * FROM sourcelead.USER user where user.ID =" + userId + "";
							stmt1 = conn.createStatement();
							ResultSet rs4 = stmt1.executeQuery(user);
							while (rs4.next()) {
								// update User
								Long uId = rs4.getLong("ID");
								String query4 = "UPDATE sourcelead.USER set USER_STATE = ?  where ID = ? ";
								PreparedStatement pstmt4 = conn.prepareStatement(query4);
								pstmt4.setString(1, "INACTIVE");
								pstmt4.setLong(2, uId);
								pstmt4.executeUpdate();
								System.out.println("userID" + uId);
							}
						}
						// Long userRoleID = rs3.getLong("ID");

					}
				}
				String query5 = "UPDATE sourcelead.ORG set STATUS = ?  where ID = ? ";
				PreparedStatement pstmt5 = conn.prepareStatement(query5);
				pstmt5.setString(1, "INACTIVE");
				pstmt5.setLong(2, orgIds1);
				pstmt5.executeUpdate();
				
				String query6 = "UPDATE sourcelead.ORG_LOCATIONS set STATUS = ?  where ORG_ID = ? ";
				PreparedStatement pstmt6 = conn.prepareStatement(query6);
				pstmt6.setString(1, "INACTIVE");
				pstmt6.setLong(2, orgIds1);
				pstmt6.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
		OrgAndUserInactiveForUnwantedOrgs actu = new OrgAndUserInactiveForUnwantedOrgs();
		actu.OrgAndUserInactiveForUnwantedOrg();
}

}

