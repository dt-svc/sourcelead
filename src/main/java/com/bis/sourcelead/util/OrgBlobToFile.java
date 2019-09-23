package com.bis.sourcelead.util;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class OrgBlobToFile {
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);

	private void convertBlobToFile(String table, String idColumn, String blobColumn, String filename, String urlColumn) {
		DataSource dataSource = comboPooledDataSource();
		try (Connection con = dataSource.getConnection()) {
			Statement stmt = con.createStatement();
			Statement stmt1 = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT org.*FROM " + table + " org");
			while (rs.next()) {
				String fileName = rs.getString(filename);
				if (rs.getBlob(blobColumn) != null) {
					InputStream in = rs.getBlob(blobColumn).getBinaryStream();
					byte[] b = new byte[in.available()];
					in.read(b);
					File folder = new File(dbconfig.getStaticPath() + "/profile/");
					if (!folder.exists())
						folder.mkdirs();
					File file = new File(folder, fileName+"-"+(new Date().getTime()));
					Files.write(file.toPath(), b);
					String link = "/slead-static/profile/" + file.getName();
					stmt1.addBatch("update " + table + " set " + urlColumn + " = '" + link.replace("'", "''")+ "' where " + idColumn + "='" + rs.getString(idColumn) + "'");
					stmt1.executeBatch();
					System.out.println(link);
				}
			}
			stmt1.executeBatch();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

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

	public static void main(String... args) {
		long st = new Date().getTime();
		new OrgBlobToFile().convertBlobToFile("ORG", "ID", "DOC_FILE", "ID", "FILE_URL");
		long end = new Date().getTime();
		System.out.println(end - st);
	}
}
