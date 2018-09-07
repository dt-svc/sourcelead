package com.bis.sourcelead.util;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;


public class ExcelHelperUtil {

	public static void setDataValueInCell(Cell cell, Object value, CellStyle cellStyle) {
		// logger.error( "setDataValueInCell value = " + value );
		if (value != null) {
			if (value instanceof String) {
				cell.setCellValue((String) (value));
			} else if (value instanceof Character) {
				cell.setCellValue(value.toString());
			} else if (value instanceof Double) {
				cell.setCellValue(((Double) value).doubleValue());
			} else if (value instanceof BigDecimal) {
				cell.setCellValue(((BigDecimal) value).doubleValue());
			} else if (value instanceof Long) {
				cell.setCellValue(((Long) value).doubleValue());
			} else if (value instanceof BigInteger) {
				cell.setCellValue(((BigInteger) value).doubleValue());
			} else if (value instanceof Integer) {
				cell.setCellValue(((Integer) value).doubleValue());
			} else if (value instanceof java.sql.Date) {
				if (cellStyle != null) {
					cell.setCellStyle(cellStyle);
				}
				cell.setCellValue((java.sql.Date) value);
			} else if (value instanceof java.util.Date) {
				if (cellStyle != null) {
					cell.setCellStyle(cellStyle);
				}
				cell.setCellValue((java.util.Date) value);
			} else if (value instanceof java.sql.Timestamp) {
				if (cellStyle != null) {
					cell.setCellStyle(cellStyle);
				}
				cell.setCellValue((java.sql.Timestamp) value);
			} else {
				cell.setCellValue(value.toString());
			}
		}
		if (value == null) {
			cell.setCellValue("");
		}
	}
}
