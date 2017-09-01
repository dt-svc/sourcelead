package com.bis.sourcelead.util;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Date;

public class BlobToFile {
	
	private void convertBlobToFile(String table, String idColumn, String blobColumn, String fileNameColumn, String urlColumn, String bucketName, String environment)
	{
		try (Connection con = getDBConnection()) {
			Statement stmt = con.createStatement();
			Statement stmt1 = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT c.*, o.ID as ORG_ID FROM "+table+" c, ORG o, ORG_LOCATIONS ol where o.ID = ol.ORG_ID and ol.id = c.ORG_LOCATION_ID");
			int i = 0;
			ResultSetMetaData rsmd = rs.getMetaData();
			boolean flag = false;
			for (int j = 0; j < rsmd.getColumnCount(); j++) {
				if (rsmd.getColumnLabel(j+1).equalsIgnoreCase(urlColumn)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				con.createStatement().execute("alter table " + table + " add column(" + urlColumn
						+ " varchar(4000))");
			}
			while (rs.next()) {
				String fileName = rs.getString(fileNameColumn);
				if(rs.getBlob(blobColumn) != null) {
				InputStream in = rs.getBlob(blobColumn).getBinaryStream();
//				Path tempPath = Files.createTempFile(fileName, "");
				byte[] b = new byte[in.available()];
				in.read(b);
				File folder = new File("F:/BlobRemove/slead-static/"+rs.getString("ORG_ID")+"/"+rs.getString("ORG_LOCATION_ID")+"/resumes/");
				if(!folder.exists())
					folder.mkdirs();
				File file = new File(folder,fileName.substring(0, fileName.lastIndexOf("."))+"-"+(new Date().getTime())+(fileName.substring(fileName.lastIndexOf("."))));
				Files.write(file.toPath(), b);
//				File tempFile = tempPath.toFile();
//				tempFile.deleteOnExit();
//				String link = uploadFile(environment+"/"+rs.getString("ORG_NAME")+"/"+rs.getString("ORG_LOCATION_ID")+"/resumes/"+fileName.substring(0, fileName.lastIndexOf("."))+"-"+(new Date().getTime())+(fileName.substring(fileName.lastIndexOf("."))), "application/msword", tempFile, bucketName);
//				System.out.println(rs.getString(idColumn)+ " - " + tempPath.getFileName() );
				String link = "/slead-static/"+rs.getString("ORG_ID")+"/"+rs.getString("ORG_LOCATION_ID")+"/resumes/"+file.getName();
				stmt1.addBatch("update " + table + " set " + urlColumn + " = '" + link.replace("'", "''") + "' where "
						+ idColumn + "='" + rs.getString(idColumn) + "'");
				i++;
				if (i % 25 == 0) {
					stmt1.executeBatch();
				}
				System.out.println(link);
				}
			}
			stmt1.executeBatch();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private Connection getDBConnection() throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/sourcelead", "root", "BIS@123");
	}
	
	public static void main(String... args)
	{
		long st = new Date().getTime();
		new BlobToFile().convertBlobToFile("CANDIDATE_RESUMES", "ID", "DOC_FILE", "FILE_NAME", "FILE_URL", "sourcelead", "dev");
		long end = new Date().getTime();
		System.out.println(end-st);
	}
}
