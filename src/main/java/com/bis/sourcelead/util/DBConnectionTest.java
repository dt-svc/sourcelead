package com.bis.sourcelead.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnectionTest {

	public static void main(String args[]) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//Class.forName("org.mariadb.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://74.208.87.190:3306/sourcelead?useSSL=false&user=admin&password=Bis@123");
			//Connection con = DriverManager.getConnection("jdbc:mariadb://74.208.86.170:3306/sourcelead?user=root&password=Bis@123");
			// here sonoo is database name, root is username and password
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from ORG");
			while (rs.next())
				System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(4));
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
