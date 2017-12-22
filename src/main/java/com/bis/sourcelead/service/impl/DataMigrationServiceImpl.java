package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.DataMigrationDao;
import com.bis.sourcelead.dto.DataMigrationDTO;
import com.bis.sourcelead.entity.SourceleadFormFields;
import com.bis.sourcelead.service.DataMigrationService;

@Service
public class DataMigrationServiceImpl implements DataMigrationService {
	@Autowired
	DataMigrationDao dataMigrationDao;
	
	@Value("${static.path}")
	private String staticPath;
	
	String[] rowStrategyCheck = {"title","job","job title","job name", "location", "city", "zip code"};
	
	@Override
	public Map<String, String> getfileData(String filePath) throws Exception
	{
		FileInputStream inputStream = new FileInputStream(new File(filePath.replace("/dataMigration/", "/temp/")));
		Workbook workbook = null;
		if (filePath.endsWith(".xls")) {
			workbook = new HSSFWorkbook(inputStream);
		}
		else {
			workbook = new XSSFWorkbook(inputStream);
		}
		DataFormatter objDefaultFormat = new DataFormatter();
		FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
		
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = firstSheet.iterator();
		Map<String, String> dataWithCellId = new TreeMap<String, String>();
		List<String> fieldList = new ArrayList<String>();
		while (rowIterator.hasNext()) {
			Row nextRow = rowIterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				String cellValueStr = objDefaultFormat.formatCellValue(cell,objFormulaEvaluator);
				if (!fieldList.contains(CellReference.convertNumToColString(cell.getColumnIndex())) && StringUtils.isNotEmpty(cellValueStr))
					/*switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							dataWithCellId.put(CellReference.convertNumToColString(cell.getColumnIndex())
									+ cell.getRowIndex(), cell.getStringCellValue());
							fieldList.add(CellReference.convertNumToColString(cell.getColumnIndex()));
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							dataWithCellId.put(CellReference.convertNumToColString(cell.getColumnIndex())
									+ cell.getRowIndex(), String.valueOf(cell.getBooleanCellValue()));
							fieldList.add(CellReference.convertNumToColString(cell.getColumnIndex()));
							break;
						case Cell.CELL_TYPE_NUMERIC:
							dataWithCellId.put(CellReference.convertNumToColString(cell.getColumnIndex())
									+ cell.getRowIndex(), String.valueOf(cell.getNumericCellValue()));
							fieldList.add(CellReference.convertNumToColString(cell.getColumnIndex()));
							break;
					}*/
				mapExcelColumns(cellValueStr, cell, dataWithCellId, fieldList);
			}
		}
		workbook.close();
		inputStream.close();
		return dataWithCellId;
	}
	
	private void mapExcelColumns(String cellValueStr, Cell cell, Map<String, String> dataWithCellId, List<String> fieldList)
	{
		mapCellValueType(cellValueStr, cell, dataWithCellId, fieldList);
	}
	
	private void mapCellValueType(String cellValueStr, Cell cell, Map<String, String> dataWithCellId, List<String> fieldList)
	{
		dataWithCellId.put(CellReference.convertNumToColString(cell.getColumnIndex()) + cell.getRowIndex(), cellValueStr);
		fieldList.add(CellReference.convertNumToColString(cell.getColumnIndex()));
	}

	@Override
	public List<SourceleadFormFields> getfieldsBySectionIdAndImportStatus(Long sectionId, int importStatus) throws Exception
	{

		return dataMigrationDao.getfieldsBySectionIdAndImportStatus(sectionId, importStatus);
	}

	@Override
	public Map<String, String> validateImportedFormData(DataMigrationDTO dataMigrationDTO) throws Exception
	{
		String ac = StringUtils.removeEnd(staticPath, "/slead-static");
		FileInputStream inputStream = new FileInputStream(new File(ac+dataMigrationDTO.getExportFilePath().replace("/dataMigration/", "/temp/")));
		Workbook workbook = null;
		if (dataMigrationDTO.getExportFilePath().endsWith(".xls")) {
			workbook = new HSSFWorkbook(inputStream);
		}
		else {
			workbook = new XSSFWorkbook(inputStream);
		}
		DataFormatter objDefaultFormat = new DataFormatter();
		FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
		
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = firstSheet.iterator();
		
		while (rowIterator.hasNext()) {
			Map<String, String> dataWithCellId = new TreeMap<String, String>();
			boolean earlyExit = false;
			Row nextRow = rowIterator.next();
			
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			for (Field field : dataMigrationDTO.getClass().getDeclaredFields()) {
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					if(field.get(dataMigrationDTO) != null && (field.get(dataMigrationDTO).toString()).equalsIgnoreCase(CellReference.convertNumToColString(cell.getColumnIndex()) + cell.getRowIndex())){
						String cellValueStr = objDefaultFormat.formatCellValue(cell,objFormulaEvaluator);
						System.out.println(cellValueStr);
					}
				}
		        
		    }
			
			
			
			/*while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				
				
				
				
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
				 
				
				
				
				
				String cellValueStr = objDefaultFormat.formatCellValue(cell,objFormulaEvaluator);
				if(!ArrayUtils.contains( rowStrategyCheck, cellValueStr)){
					for (Field field : dataMigrationDTO.getClass().getDeclaredFields()) {
				        
				    }
				}else{
					earlyExit=true;
				}
				
			}*/
			
		}
		workbook.close();
		inputStream.close();
		return null;
	}
}
