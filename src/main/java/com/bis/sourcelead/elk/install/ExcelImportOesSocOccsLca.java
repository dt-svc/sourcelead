package com.bis.sourcelead.elk.install;


import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import javax.sql.DataSource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ExcelImportOesSocOccsLca {

	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);

	/**
	 * @author Venkat this method is using for update the UUID for Connections
	 * @throws IOException
	 * @throws SQLException
	 */
	public void dumpOesSocOccsFromExcelToTable() throws IOException, SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			DataSource dataSource = comboPooledDataSource();
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			if (conn != null) {
				System.out.println("Connection successfully loaded....");
			}

			String completFilePath = "/home/oes_soc_occs.xlsx";
			FileInputStream fis = new FileInputStream(completFilePath);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheet("oes_soc_occs");
			XSSFRow row = null;
			String soccode = null;
			String soctitle = null;
			String socdefinition = null;
			String query = "INSERT INTO  " + dbconfig.getSchema()
					+ ".OES_SOC_OCCS (SOC_CODE, SOC_TITLE, SOC_DEFINITION)" + "VALUES (?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			for (int i = 1; i <= sheet.getLastRowNum(); i++) { // sheet.getLastRowNum();
				row = sheet.getRow(i);
				if(row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING) {
					soccode = row.getCell(0).getStringCellValue().trim();
				}else if(DateUtil.isCellDateFormatted(row.getCell(0))) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(row.getCell(0).getDateCellValue());
				    int month = cal.get(Calendar.MONTH) + 1;
				    int year = cal.get(Calendar.YEAR);
				    System.out.println(" Month :"+month+" year :"+year);
				    soccode = month+"-"+year;
				}
				soctitle = row.getCell(1).getStringCellValue().trim();
				socdefinition = row.getCell(2).getStringCellValue().trim();
				System.out.println("Row Num: " + i + ": " + soccode + " :" + soctitle);
				pstmt.setString(1, soccode);
				pstmt.setString(2, soctitle);
				pstmt.setString(3, socdefinition);
				pstmt.addBatch();
			}
			int count[] = pstmt.executeBatch();
			conn.commit();
			System.out.println("affectedRecords Count --:> " + count.length);
			fis.close();
			System.out.println("----Lookup data inserted successfully-----");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * getting connection
	 * 
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
		ExcelImportOesSocOccsLca dump = new ExcelImportOesSocOccsLca();
		dump.dumpOesSocOccsFromExcelToTable();
		//	dump.dumpEdcExportFromExcelToTable();
		// dump.dumpAlcExportFromExcelToTable();
		// dump.dumpGeographyExportFromExcelToTable();
		//dump.dumpOnetOccsExportFromExcelToTable();
		//dump.dumpXwalkPlusExportFromExcelToTable();

	}

	private void dumpXwalkPlusExportFromExcelToTable() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			DataSource dataSource = comboPooledDataSource();
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			if (conn != null) {
				System.out.println("Connection successfully loaded....");
			}
			String completFilePath = "C:/Users/Unifinte/Desktop/ImmigrationExcelData/xwalk_plus.xlsx";
			FileInputStream fis = new FileInputStream(completFilePath);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheet("Worksheet");
			XSSFRow row = null;
			String query = "INSERT INTO  " + dbconfig.getSchema()
					+ ".XWALK_PLUS (OES_SOCCODE, OES_SOCTITLE, TRUNC_ONET_CODE, ONET_CODE, O_NET_TITLE)"
					+ "VALUES (?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			for (int i = 1; i <= sheet.getLastRowNum(); i++) { // sheet.getLastRowNum();
				row = sheet.getRow(i);
				System.out.println(row.getCell(0).getStringCellValue().trim());
				if (!row.getCell(0).getStringCellValue().trim().isEmpty()) {
					pstmt.setString(1, row.getCell(0).getStringCellValue().trim());
				}
				System.out.println(row.getCell(1).getStringCellValue().trim());
				if (!row.getCell(1).getStringCellValue().trim().isEmpty()) {
					pstmt.setString(2, row.getCell(1).getStringCellValue().trim());
				}
				System.out.println(row.getCell(2).getStringCellValue().trim());
				if (!row.getCell(2).getStringCellValue().trim().isEmpty()) {
					pstmt.setString(3, row.getCell(2).getStringCellValue().trim());
				}
				System.out.println(row.getCell(3).getStringCellValue().trim());
				if (!row.getCell(3).getStringCellValue().trim().isEmpty()) {
					pstmt.setString(4, row.getCell(3).getStringCellValue().trim());
				}
				System.out.println(row.getCell(4).getStringCellValue().trim());
				if (!row.getCell(4).getStringCellValue().trim().isEmpty()) {
					pstmt.setString(5, row.getCell(4).getStringCellValue().trim());
				}
				pstmt.addBatch();
				/*
				 * if(i%100==0) { int count[] = pstmt.executeBatch();
				 * System.out.println("The count is :"+count.length); conn.commit(); }
				 */
			}
			int count[] = pstmt.executeBatch();
			System.out.println("The count is :" + count.length);
			conn.commit();
			fis.close();
			System.out.println("----Lookup data inserted successfully-----");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	private void dumpOnetOccsExportFromExcelToTable() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			DataSource dataSource = comboPooledDataSource();
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			if (conn != null) {
				System.out.println("Connection successfully loaded....");
			}
			String completFilePath = "C:/Users/Unifinte/Desktop/ImmigrationExcelData/onet_occs.xlsx";
			FileInputStream fis = new FileInputStream(completFilePath);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheet("Worksheet");
			XSSFRow row = null;
			String query = "INSERT INTO  " + dbconfig.getSchema()
					+ ".ONET_OCCS (ONET_CODE, ONET_TITLE, ONET_DESCRIPTION, JOB_ZONE, DOL_TRAINING)"
					+ "VALUES (?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			for (int i = 1; i <= sheet.getLastRowNum(); i++) { // sheet.getLastRowNum();
				row = sheet.getRow(i);
				System.out.println(row.getCell(0).getStringCellValue().trim());
				if (!row.getCell(0).getStringCellValue().trim().isEmpty()) {
					pstmt.setString(1, row.getCell(0).getStringCellValue().trim());
				}
				System.out.println(row.getCell(1).getStringCellValue().trim());
				if (!row.getCell(1).getStringCellValue().trim().isEmpty()) {
					pstmt.setString(2, row.getCell(1).getStringCellValue().trim());
				}
				System.out.println(row.getCell(2).getStringCellValue().trim());
				if (!row.getCell(2).getStringCellValue().trim().isEmpty()) {
					pstmt.setString(3, row.getCell(2).getStringCellValue().trim());
				}
				Cell cell = row.getCell(3);
				if (cell != null) {
					Integer numericCell1 = (int) row.getCell(3).getNumericCellValue();
					System.out.println(numericCell1);
					pstmt.setInt(4, numericCell1);
				}else {
					pstmt.setInt(4, 0);
				}
				Cell cell1 = row.getCell(4);
				if (cell1 != null) {
					Integer numericCell2 = (int) row.getCell(4).getNumericCellValue();
					System.out.println(numericCell2);
					pstmt.setInt(5, numericCell2);
				}else {
					pstmt.setInt(5, 0);
				}
				pstmt.addBatch();
				/*
				 * if(i%100==0) { int count[] = pstmt.executeBatch();
				 * System.out.println("The count is :"+count.length); conn.commit(); }
				 */
			}
			int count[] = pstmt.executeBatch();
			System.out.println("The count is :" + count.length);
			conn.commit();
			fis.close();
			System.out.println("----Lookup data inserted successfully-----");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void dumpGeographyExportFromExcelToTable() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			DataSource dataSource = comboPooledDataSource();
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			if (conn != null) {
				System.out.println("Connection successfully loaded....");
			}
			String completFilePath = "C:/Users/Unifinte/Desktop/ImmigrationExcelData/geography.xlsx";
			FileInputStream fis = new FileInputStream(completFilePath);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheet("Worksheet");
			XSSFRow row = null;
			String query = "INSERT INTO  " + dbconfig.getSchema()
					+ ".GEOGRAPHY (AREA, AREA_NAME, STATE_AB, STATE, COUNTRY_TOWN_NAME)" + "VALUES (?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			for (int i = 1; i <= sheet.getLastRowNum(); i++) { // sheet.getLastRowNum();
				row = sheet.getRow(i);
				Integer firstCell = (int) row.getCell(0).getNumericCellValue();
				System.out.println(firstCell);
				if (firstCell != null) {
					pstmt.setInt(1, firstCell);
				}
				String strCellValue = null;
				if (row.getCell(1).getCellType() == Cell.CELL_TYPE_BOOLEAN) {
					strCellValue = new String(new Boolean(row.getCell(1).getBooleanCellValue()).toString());
				} else {
					strCellValue = row.getCell(1).getStringCellValue().trim();
				}
				System.out.println(strCellValue);
				pstmt.setString(2, strCellValue);
				System.out.println(row.getCell(2).getStringCellValue().trim());
				if (!row.getCell(2).getStringCellValue().trim().isEmpty()) {
					pstmt.setString(3, row.getCell(2).getStringCellValue().trim());
				}
				System.out.println(row.getCell(3).getStringCellValue().trim());
				if (!row.getCell(3).getStringCellValue().trim().isEmpty()) {
					pstmt.setString(4, row.getCell(3).getStringCellValue().trim());
				}
				System.out.println(row.getCell(4).getStringCellValue().trim());
				if (!row.getCell(4).getStringCellValue().trim().isEmpty()) {
					pstmt.setString(5, row.getCell(4).getStringCellValue().trim());
				}
				pstmt.addBatch();
				/*
				 * if(i%100==0) { int count[] = pstmt.executeBatch();
				 * System.out.println("The count is :"+count.length); conn.commit(); }
				 */
			}
			int count[] = pstmt.executeBatch();
			System.out.println("The count is :" + count.length);
			conn.commit();
			fis.close();
			System.out.println("----Lookup data inserted successfully-----");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private void dumpEdcExportFromExcelToTable() {

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			DataSource dataSource = comboPooledDataSource();
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			if (conn != null) {
				System.out.println("Connection successfully loaded....");
			}
			String completFilePath = "C:/Users/Unifinte/Desktop/ImmigrationExcelData/EDC_Export.xlsx";
			FileInputStream fis = new FileInputStream(completFilePath);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheet("Worksheet");
			XSSFRow row = null;
			String query = "INSERT INTO  " + dbconfig.getSchema()
					+ ".EDC_EXPORT (AREA, SOC_CODE, GEO_LVL, LEVEL1, LEVEL2, LEVEL3, LEVEL4, AVERAGE)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			for (int i = 1; i <= sheet.getLastRowNum(); i++) { // sheet.getLastRowNum();
				row = sheet.getRow(i);
				Integer firstCell = (int) row.getCell(0).getNumericCellValue();
				System.out.println(firstCell);
				if (firstCell != null) {
					pstmt.setInt(1, firstCell);
				}
				System.out.println(row.getCell(1).getStringCellValue().trim().isEmpty());
				if (!row.getCell(1).getStringCellValue().trim().isEmpty()) {
					pstmt.setString(2, row.getCell(1).getStringCellValue().trim());
				}
				Double thirdCell = row.getCell(2).getNumericCellValue();
				System.out.println(thirdCell);
				if (thirdCell != null) {
					pstmt.setDouble(3, thirdCell);
				}
				Cell fourthCell = row.getCell(3);
				System.out.println(fourthCell);
				if (fourthCell != null) {
					pstmt.setDouble(4, fourthCell.getNumericCellValue());
				} else {
					pstmt.setDouble(4, 0.0);
				}
				Cell fifthCell = row.getCell(4);
				System.out.println(fifthCell);
				if (fifthCell != null) {
					pstmt.setDouble(5, fifthCell.getNumericCellValue());
				} else {
					pstmt.setDouble(5, 0.0);
				}
				Cell sixthCell = row.getCell(5);
				System.out.println(sixthCell);
				if (sixthCell != null) {
					pstmt.setDouble(6, sixthCell.getNumericCellValue());
				} else {
					pstmt.setDouble(6, 0.0);
				}
				Cell seventhCell = row.getCell(6);
				System.out.println(seventhCell);
				if (seventhCell != null) {
					pstmt.setDouble(7, seventhCell.getNumericCellValue());
				} else {
					pstmt.setDouble(7, 0.0);
				}
				Cell eightCell = row.getCell(7);
				System.out.println(eightCell);
				if (eightCell != null) {
					pstmt.setDouble(8, eightCell.getNumericCellValue());
				} else {
					pstmt.setDouble(8, 0.0);
				}
				pstmt.addBatch();
				if (i % 60 == 0) {
					int count[] = pstmt.executeBatch();
					System.out.println("The count is :" + count.length);
					conn.commit();
					pstmt = conn.prepareStatement(query);
					//System.out.println("Press \"ENTER\" to continue...");
					//Scanner scanner = new Scanner(System.in);
					//scanner.nextLine();
				}
			}
			int count[] = pstmt.executeBatch();
			System.out.println("The count is :" + count.length);
			conn.commit();
			// System.out.println("affectedRecords Count --:> " + count.length);
			fis.close();
			System.out.println("----Lookup data inserted successfully-----");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void dumpAlcExportFromExcelToTable() {

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			DataSource dataSource = comboPooledDataSource();
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			if (conn != null) {
				System.out.println("Connection successfully loaded....");
			}
			String completFilePath = "C:/Users/Unifinte/Desktop/ImmigrationExcelData/ALC_Export.xlsx";
			FileInputStream fis = new FileInputStream(completFilePath);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			int sheetCount = workbook.getNumberOfSheets();
			System.out.println("sheetCount :" + sheetCount);
			for (int j = 0; j < sheetCount; j++) {
				XSSFSheet sheet = workbook.getSheetAt(j);
				System.out.println(sheet.getSheetName());
				XSSFRow row = null;
				String query = "INSERT INTO  " + dbconfig.getSchema()
						+ ".ALC_EXPORT (AREA, SOC_CODE, GEO_LVL, LEVEL1, LEVEL2, LEVEL3, LEVEL4, AVERAGE)"
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(query);
				for (int i = 1; i <= sheet.getLastRowNum(); i++) { // sheet.getLastRowNum();
					row = sheet.getRow(i);
					Integer firstCell = (int) row.getCell(0).getNumericCellValue();
					if (firstCell != null) {
						pstmt.setInt(1, firstCell);
					}
					
					if (!row.getCell(1).getStringCellValue().trim().isEmpty()) {
						pstmt.setString(2, row.getCell(1).getStringCellValue().trim());
					}
					Double thirdCell = row.getCell(2).getNumericCellValue();
					if (thirdCell != null) {
						pstmt.setDouble(3, thirdCell);
					}
					Cell fourthCell = row.getCell(3);
					if (fourthCell != null) {
						pstmt.setDouble(4, fourthCell.getNumericCellValue());
					} else {
						pstmt.setDouble(4, 0.0);
					}
					Cell fifthCell = row.getCell(4);
					if (fifthCell != null) {
						pstmt.setDouble(5, fifthCell.getNumericCellValue());
					} else {
						pstmt.setDouble(5, 0.0);
					}
					Cell sixthCell = row.getCell(5);
					if (sixthCell != null) {
						pstmt.setDouble(6, sixthCell.getNumericCellValue());
					} else {
						pstmt.setDouble(6, 0.0);
					}
					Cell seventhCell = row.getCell(6);
					if (seventhCell != null) {
						pstmt.setDouble(7, seventhCell.getNumericCellValue());
					} else {
						pstmt.setDouble(7, 0.0);
					}
					Cell eightCell = row.getCell(7);
					if (eightCell != null) {
						pstmt.setDouble(8, eightCell.getNumericCellValue());
					} else {
						pstmt.setDouble(8, 0.0);
					}
					pstmt.addBatch();
					if (i % 60 == 0) {
						int count[] = pstmt.executeBatch();
						System.out.println("The count is :" + count.length);
						conn.commit();
					}
				}

				// System.out.println("affectedRecords Count --:> " + count.length);
				fis.close();
				System.out.println("----Lookup data inserted successfully-----");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
