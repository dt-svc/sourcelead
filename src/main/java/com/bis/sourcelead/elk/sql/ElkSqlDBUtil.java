package com.bis.sourcelead.elk.sql;

/*import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Autowired;

import com.bis.sourcelead.elk.setup.SourceleadELKTransportClient;*/

public class ElkSqlDBUtil {

	private static String INDEX_sql4es = "sql4es";

	public static void main(String[] args) {
		// createIndex();
		// createType("tableType");
		// simpleInsert("tableType");
		 // simpleSelect();
		// whereSelectForString();
		//whereSelectForInt();
		//whereSelectForDate();
	}
	
	/*public static void whereSelectForDate() {

		try {
			Statement st = createStatement(INDEX_sql4es);
			// execute a query on mytype within myidx
			//String sql = "SELECT * FROM tabletype where createdDate >= '2017-02-12' and createdDate <= '2017-02-14' order by empId";
			String sql = "SELECT * FROM tabletype where createdDate between '2017-02-12' and '2017-02-14' order by empId";
			ResultSet rs = st.executeQuery(sql);
			int count = 0;
			while (rs.next()) {
				System.out.println(rs.getInt("empId") + ", " + rs.getString("empFname") + ", "
						+ rs.getString("empLname") + ", " + rs.getDate("createdDate"));
				count++;
			}
			System.out.println("count.." + count);
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void whereSelectForInt() {

		try {
			Statement st = createStatement(INDEX_sql4es);
			// execute a query on mytype within myidx
			String sql = "SELECT * FROM tabletype where empId >= 103 and empId <= 105";
			ResultSet rs = st.executeQuery(sql);
			int count = 0;
			while (rs.next()) {
				System.out.println(rs.getInt("empId") + ", " + rs.getString("empFname") + ", "
						+ rs.getString("empLname") + ", " + rs.getDate("createdDate"));
				count++;
			}
			System.out.println("count.." + count);
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static Statement createStatement(String index) {
		Statement stmt = null;
		try {
			Class.forName("nl.anchormen.sql4es.jdbc.ESDriver");

			Connection con = DriverManager
					.getConnection("jdbc:sql4es://localhost:9300/" + index + "?cluster.name=sourcelead");
			stmt = con.createStatement();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return stmt;
	}

	public static void whereSelectForString() {

		try {
			Statement st = createStatement(INDEX_sql4es);
			// execute a query on mytype within myidx
			ResultSet rs = st.executeQuery(
					"SELECT * FROM tabletype where empLname = '%eddy' order by createdDate ");
			int count = 0;
			while (rs.next()) {
				System.out.println(rs.getInt("empId") + ", " + rs.getString("empFname") + ", "
						+ rs.getString("empLname") + ", " + rs.getDate("createdDate"));
				count++;
			}
			System.out.println("count.." + count);
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void simpleSelect() {

		try {
			Statement st = createStatement(INDEX_sql4es);
			// execute a query on mytype within myidx
			ResultSet rs = st.executeQuery("SELECT * FROM tabletype ");
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = 0;
			int nrCols = rsmd.getColumnCount();
			// get other column information like type
			while (rs.next()) {
				
				 * for(int i=1; i<=nrCols; i++){
				 * System.out.println(rsmd.getColumnType(i)+"<==>"+rs.getObject(
				 * i));
				 * 
				 * }
				 
				System.out.println(rs.getInt("empId") + ", " + rs.getString("empFname") + ", "
						+ rs.getString("empLname") + ", " + rs.getDate("createdDate"));
				count++;
			}
			System.out.println("count.." + count);
			rs.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void createType(String type) {
		// String type = "testType";
		Statement st = createStatement(INDEX_sql4es);
		try {
			boolean res = st.execute("CREATE TABLE " + type
					+ " (empId \"type:Integer\", empFname \"type:String\", empLname \"type:String\", createdDate \"type:date\")");
			System.out.println("Created type " + type);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void simpleInsert(String type) {

		Statement st = createStatement(INDEX_sql4es);

		try {
			boolean res = st.execute("INSERT INTO " + type + " (empId, empFname, empLname, createdDate) "
					+ "VALUES (104, 'Avinash','Reddy', '2017-02-10T11:20:16'), (105, 'Snigitha','Paruchuri', '2017-02-11T10:13:02'), (106, 'Roma','Rao', '2017-02-12T09:10:46')");
			System.out.println("Inserted a record in type " + type);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void createIndex() {

		try {
			Settings settings = Settings.settingsBuilder().put("cluster.name", "sourcelead")
					.put("node.name", "sourcelead_node").put("path.home", "C:/SourceLead/elasticsearch-2.4.3").build();
			Client client = TransportClient.builder().settings(settings).build()
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
			CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate("sql4es");
			CreateIndexResponse response = createIndexRequestBuilder.execute().actionGet();
			System.out.println(response.isAcknowledged());
		} catch (Exception exception) {
			System.out.println(ExceptionUtils.getFullStackTrace(exception));
		}
	}*/

	/*
	 * public void createSimple() throws Exception{ //createIndex(index);
	 * 
	 * Connection conn =
	 * DriverManager.getConnection("jdbc:sql4es://localhost:9300/"+index+"?test"
	 * ); Statement st = conn.createStatement(); boolean res = st.execute(
	 * "CREATE TABLE simpletype (myString \"type:String, index:not_analyzed\", myInt \"type:Integer\", myDate \"type:date\")"
	 * ); assert(!res); flush(); refresh(); DatabaseMetaData dmd =
	 * conn.getMetaData(); ResultSet rs = dmd.getTables(null, index,
	 * "simpletype",null); int count = 0; while(rs.next()) count++;
	 * //assertEquals(2, count); rs.close();
	 * 
	 * res = st.execute("INSERT INTO simpletype (myString, myInt, myDate) " +
	 * "VALUES ('abc', 1, '2016-01-09T16:35:46'), ('def', 2, '2016-01-10T12:26:12'), ('ghi', 3, '2016-01-11T09:01:07')"
	 * ); assert(!res); flush(); refresh();
	 * 
	 * rs = st.executeQuery("SELECT * FROM simpletype"); ResultSetMetaData rsm =
	 * rs.getMetaData(); assertEquals(Types.VARCHAR, rsm.getColumnType(4));
	 * assertEquals(Types.DATE, rsm.getColumnType(5));
	 * assertEquals(Types.INTEGER, rsm.getColumnType(6)); count = 0;
	 * while(rs.next()){ count++; assert(rs.getInt("myInt") > 0);
	 * rs.getDate("myDate"); } assertEquals(3, count); st.close(); }
	 */
}
