package com.bis.sourcelead.rest.elastic;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.elk.index.ClientVendorContactsIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@RestController
@RequestMapping("/app")
public class DumpSpocUserDetails {
	private static final Logger logger = LoggerFactory.getLogger(ClientVendorContactsElkRestController.class);
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);

	@RequestMapping(value = "/ws/dumpSpocUserDetails", method = RequestMethod.GET)
	public void dumpSpocUserDetails() throws Exception {

		DataSource dataSource = comboPooledDataSource();
		ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
		DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
		try {
			System.out.println("------------ dumpSpocUserDetails starts ----------");
			String sql = "SELECT * FROM " + dbconfig.getSchema() + ".ORG_SPOC orgSpock where orgSpock.CLIENT_USER_ID IS NULL ";
			Connection conn = dataSource.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {

				Long clientOrgLocationId = rs.getLong("CLIENT_LOCATION_ID");
				Long orgSpocId = rs.getLong("ID");
				
				String sql1 = "SELECT * FROM " + dbconfig.getSchema()
						+ ".USER_ROLE  userRole where userRole.ORG_LOCATIONS_ID = '" + clientOrgLocationId
						+ "' ";

				Statement stmt1 = conn.createStatement();
				ResultSet rs1 = stmt1.executeQuery(sql1);
				if (rs1 != null) {
					while (rs1.next()) {
						Long userId = rs1.getLong("USER_ID");
						 String query = "UPDATE "+dbconfig.getSchema()+".ORG_SPOC set CLIENT_USER_ID = ? where ID = ?";
						 PreparedStatement pstmt = conn.prepareStatement(query);
						 pstmt.setLong(1, userId);
						 pstmt.setLong(2, orgSpocId);
						 pstmt.executeUpdate(); 
					}
				}
			}
			conn.close();
			System.out.println("------------ dumpSpocUserDetails ends ----------");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

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

	public String firstLetterCapitalizedString(String name) {
		String finalString = WordUtils.capitalizeFully(name);
		return finalString;
	}

	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	private boolean isNullOrEmpty(long long1) {
		// TODO Auto-generated method stub
		return false;
	}

}