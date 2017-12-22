package com.bis.sourcelead.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * A dirty simple program that reads an Excel file.
 * 
 * @author Venkat Kesav
 *
 */
public class DataMigrationReadDataFromExcel {
	public static void main(String[] args) throws IOException
	{
		String excelFilePath = "C:/Users/BIS101/Desktop/data migration exls/clients_contacts_Import.xls";
		//String excelFilePath = "C:/Users/Vijay/Desktop/Books.xlsx";
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		Workbook workbook = null;
		if (excelFilePath.endsWith(".xls")) {
		workbook = new HSSFWorkbook(inputStream);
		} else {
		workbook = new XSSFWorkbook(inputStream);
		}
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = firstSheet.iterator();
		while (rowIterator.hasNext()) {

		Map<String, String> dataWithCellId = new TreeMap<String, String>();

		Row nextRow = rowIterator.next();
		Iterator<Cell> cellIterator = nextRow.cellIterator();
		while (cellIterator.hasNext()) {
		Cell cell = cellIterator.next();

		System.out.println();
		System.out
		.print("CELL Address = " + CellReference.convertNumToColString(cell.getColumnIndex()) + " ## ");

				 CellReference cr = new
				 CellReference(CellReference.convertNumToColString(cell.getColumnIndex())+cell.getRowIndex()); 
				 Row r = firstSheet.getRow(cr.getRow()); 
				 if (r != null) { Cell c = r.getCell(cr.getCol());
				 switch (c.getCellType()) { 
					 case Cell.CELL_TYPE_STRING:
						 System.out.print(c.getStringCellValue()); 
						 break; 
					 case Cell.CELL_TYPE_BOOLEAN:
						 System.out.print(c.getBooleanCellValue()); 
						 break; 
					 case Cell.CELL_TYPE_NUMERIC:
						 System.out.print(c.getNumericCellValue()); 
						 break; 
				 	} 
				 }

		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
		System.out.print(cell.getStringCellValue());
		dataWithCellId.put(CellReference.convertNumToColString(cell.getColumnIndex()) + cell.getRowIndex(),
		cell.getStringCellValue());
		break;
		case Cell.CELL_TYPE_BOOLEAN:
		System.out.print(cell.getBooleanCellValue());
		dataWithCellId.put(CellReference.convertNumToColString(cell.getColumnIndex()) + cell.getRowIndex(),
		String.valueOf(cell.getBooleanCellValue()));
		break;
		case Cell.CELL_TYPE_NUMERIC:
		System.out.print(cell.getNumericCellValue());
		dataWithCellId.put(CellReference.convertNumToColString(cell.getColumnIndex()) + cell.getRowIndex(),
		String.valueOf(cell.getNumericCellValue()));
		break;
		}
		}

		for (Map.Entry<String, String> entry : dataWithCellId.entrySet()) {
		System.out.println();
		String key = entry.getKey();
		System.out.print("CELL_ID = " + key);

		String value = entry.getValue();
		System.out.print(" , VALUE = " + value);
		}
		System.out.println();

		}
		workbook.close();
		inputStream.close();
	}
}
