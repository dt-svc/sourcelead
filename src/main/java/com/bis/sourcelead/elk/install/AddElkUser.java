package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.elk.index.UserIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class AddElkUser {
	private static final Logger logger = LoggerFactory.getLogger(AddElkUser.class);
	private ComboPooledDataSource comboPooledDataSource;
	private SourceleadELKBaseIndexer<UserIndex> client;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
	public void addElkUser() throws IOException, SQLException {
		ArrayList<UserIndex> elkUserList = getElkUsers();
		if (null != elkUserList && !elkUserList.isEmpty()) {
			for (UserIndex userIndex : elkUserList) {
				client.saveOrUpdateElkDocument(userIndex, Constants.ELK_USER_INDEX, Constants.ELK_USER_TYPE,
						userIndex.getUserRoleTableId().toString());
			}
		}
		logger.info("User Successfully Added To Elk");
	}

	private ArrayList<UserIndex> getElkUsers() throws SQLException {
		DataSource dataSource = comboPooledDataSource();
		ArrayList<UserIndex> userIndexsList = new ArrayList<UserIndex>();
		Connection conn = null;
		Statement stmt = null;
		try {
			String sql = "SELECT * FROM "+dbconfig.getSchema()+".USER_ROLE userRole";
			conn = dataSource.getConnection();
		    stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				UserIndex userIndex = new UserIndex();
				userIndex.setUserRoleTableId(rs.getLong("ID"));
				userIndex.setUserId(rs.getLong("USER_ID"));
				userIndex.setOrgLocationId(rs.getLong("ORG_LOCATIONS_ID"));
				userIndex.setUserStatus(rs.getString("USER_STATE"));
				userIndex.setUserType(rs.getString("USER_TYPE"));
				userIndex.setStatus(rs.getString("STATUS"));
				userIndex.setTerminateReason(rs.getString("TERMINATE_REASON"));
				if (0 != rs.getLong("REPORTING_MANAGER_ID") && !"".equals(rs.getLong("REPORTING_MANAGER_ID"))) {
					userIndex.setReportingManagerId(rs.getLong("REPORTING_MANAGER_ID"));
				}
				Timestamp createdDateTimestamp = rs.getTimestamp("CREATED_DATE");
				DateFormat StringDate = new SimpleDateFormat("yyyy-MM-dd");
				if (null != createdDateTimestamp) {
					String createdDate = StringDate.format(new java.util.Date(createdDateTimestamp.getTime()));
					userIndex.setCreatedDate(createdDate);
				}
				userIndex.setCreatedBy(rs.getString("CREATED_BY"));
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				if(null != rs.getDate("CREATED_DATE")){
					userIndex.setCreatedDate(dateFormat.format(rs.getDate("CREATED_DATE")));
				}
				
				if(null != rs.getDate("UPDATED_DATE")){
					userIndex.setUpdatedDate(dateFormat.format(rs.getDate("UPDATED_DATE")));
				}else{
					userIndex.setUpdatedDate(dateFormat.format(rs.getDate("CREATED_DATE")));
				}
				
			
				userIndex.setUpdatedBy(rs.getString("UPDATED_BY"));

				Long userId = rs.getLong("USER_ID");
				Long orgLocationId = rs.getLong("ORG_LOCATIONS_ID");
				Long userRoleId = rs.getLong("ROLE_ID");
				if (null != userId && userId != 0) {
					Statement stmt1 = conn.createStatement();
					try {
						String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".USER user where user.ID = '" + userId + "'  and  '" + userId + "' IN (SELECT userRole.USER_ID FROM "+dbconfig.getSchema()+".USER_ROLE userRole where userRole.ORG_LOCATIONS_ID = '" + orgLocationId + "') ";

						ResultSet rs1 = stmt1.executeQuery(sql1);
						if (rs1 != null) {
							while (rs1.next()) {
								userIndex.setFirstName(rs1.getString("FIRST_NAME"));
								userIndex.setLastName(rs1.getString("LAST_NAME"));
								userIndex.setEmail(rs1.getString("EMAIL"));
								userIndex.setMobilePhone(rs1.getString("MOBILE_PHONE"));
								userIndex.setIsMailSent(rs1.getString("IS_MAIL_SENT"));
								userIndex.setUserCode(rs1.getString("USER_CODE"));
								userIndex.setGender(rs1.getString("GENDER"));
								userIndex.setMiddleName(rs1.getString("MIDDLE_NAME"));
								Timestamp dateofbirthstr = rs1.getTimestamp("DATE_OF_BIRTH");
								if (null != dateofbirthstr) {
									String updatedDateofBirth = StringDate.format(new java.util.Date(dateofbirthstr.getTime()));
									userIndex.setDateofBirth(updatedDateofBirth);
								}
							}

						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						stmt1.close();
					}

					Statement stmt2 = conn.createStatement();
					try {
						String sql2 = "SELECT * FROM "+dbconfig.getSchema()+".EMPLOYEE employee where employee.USER_ID = '" + userId
								+ "' and employee.ORG_LOCATIONS_ID = '" + orgLocationId + "' ";

						ResultSet rs2 = stmt2.executeQuery(sql2);
						if (rs2 != null) {
							while (rs2.next()) {
								userIndex.setEmployeeId(rs2.getString("EMPLOYEE_ID"));
								userIndex.setRate(rs2.getString("RATE"));
								userIndex.setRatePer(rs2.getString("RATE_PER"));

								if (!"".equalsIgnoreCase(rs2.getString("HIRE_DATE"))) {
									Timestamp hiredate = rs2.getTimestamp("HIRE_DATE");
									if (null != hiredate) {
										String updatedHireDate = StringDate
												.format(new java.util.Date(hiredate.getTime()));
										userIndex.setHireDate(updatedHireDate);
									}

								}

							}

						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						stmt2.close();
					}

					Statement stmt3 = conn.createStatement();
					try {
						String sql3 = "SELECT * FROM "+dbconfig.getSchema()+".ROLES roles where roles.ORG_LOCATION_ID='"
								+ orgLocationId + "' and roles.ID = '" + userRoleId + "'";

						ResultSet rs3 = stmt3.executeQuery(sql3);
						if (rs3 != null) {
							while (rs3.next()) {
								userIndex.setUserRoleId(rs3.getString("ID"));

							}

						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						stmt3.close();
					}

				}
				userIndexsList.add(userIndex);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			conn.close();
		}
		return userIndexsList;
	}
	
	@Bean
	public DataSource comboPooledDataSource() {
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

	public static void main(String[] args) throws IOException {

		try {
			AddElkUser addElkUser = new AddElkUser();
			addElkUser.client = new SourceleadELKBaseIndexer<>();
			addElkUser.addElkUser();
//			addElkUser.client.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
