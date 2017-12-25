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

import javax.servlet.http.HttpServletRequest;

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
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.dao.DataMigrationDao;
import com.bis.sourcelead.dto.DataMigrationDTO;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.SourceleadFormFields;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.DataMigrationService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.google.gson.Gson;

@Service
public class DataMigrationServiceImpl implements DataMigrationService {
	@Autowired
	DataMigrationDao dataMigrationDao;
	
	@Autowired
	private RequirementService requirementService;
	
	@Value("${static.path}")
	private String staticPath;
	
	String[] rowStrategyCheck = {"title","job","job title","job name", "location", "city", "zip code"};
	
	@Override
	public Map<String, String> getfileData(String filePath) throws Exception
	{
		FileInputStream inputStream = new FileInputStream(new File(filePath.replace("/dataMigration/", "/temp/")));
		Workbook workbook = null;
		DataFormatter objDefaultFormat = new DataFormatter();
		FormulaEvaluator objFormulaEvaluator = null;
		if (filePath.endsWith(".xls")) {
			workbook = new HSSFWorkbook(inputStream);
			objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
		}
		else {
			workbook = new XSSFWorkbook(inputStream);
			objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
		}
		
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
	public Map<String, String> validateImportedFormData(DataMigrationDTO dataMigrationDTO, HttpServletRequest request) throws Exception
	{
		String ac = StringUtils.removeEnd(staticPath, "/slead-static");
		FileInputStream inputStream = new FileInputStream(new File(ac+dataMigrationDTO.getExportFilePath().replace("/dataMigration/", "/temp/")));
		Workbook workbook = null;
		DataFormatter objDefaultFormat = new DataFormatter();
		FormulaEvaluator objFormulaEvaluator;
		if (dataMigrationDTO.getExportFilePath().endsWith(".xls")) {
			workbook = new HSSFWorkbook(inputStream);
			objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
		}
		else {
			workbook = new XSSFWorkbook(inputStream);
			objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
		}
		
		
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = firstSheet.iterator();
		
		while (rowIterator.hasNext()) {
			/*Map<String, String> dataWithCellId = new TreeMap<String, String>();
			boolean earlyExit = false;*/
			Row nextRow = rowIterator.next();
			RequirementDTO reqDTO = new RequirementDTO();
			JSONObject dataMigrationDTOJson = new JSONObject(dataMigrationDTO);
			dataMigrationDTOJson.keys().forEachRemaining(k -> {
				try {
					Iterator<Cell> cellIterator = nextRow.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						if (dataMigrationDTOJson.get((String) k) != null) {
							if ((dataMigrationDTOJson.get((String) k).toString().replaceAll("\\d", ""))
									.equalsIgnoreCase(CellReference.convertNumToColString(cell.getColumnIndex()))) {
								// String cellValueStr = dataMigrationDTOJson.get((String)k).toString();
								// System.out.println("SSSSSS"+objDefaultFormat.formatCellValue(cell,objFormulaEvaluator));
								dataMigrationDTOJson.put((String) k,
										objDefaultFormat.formatCellValue(cell, objFormulaEvaluator));
							}
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			});
			ObjectMapper mapper = new ObjectMapper();
			reqDTO = mapper.readValue(dataMigrationDTOJson.toString(), RequirementDTO.class);
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			reqDTO.setDateFormat(orgLocation.getDateFormat());
			reqDTO.setSaveType("draft");
			Requirement requirement = requirementService.storeRequirement(reqDTO, orgLocation, user);
			
			
			/*for (Field field : dataMigrationDTO.getClass().getDeclaredFields()) {
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					if(field.get(dataMigrationDTO) != null ) {
						if((field.get(dataMigrationDTO).toString().replaceAll("\\d","")).equalsIgnoreCase(CellReference.convertNumToColString(cell.getColumnIndex()))){
							String cellValueStr = objDefaultFormat.formatCellValue(cell,objFormulaEvaluator);
							System.out.println(cellValueStr);
							
						}
					}
				}
		    }*/
			System.out.println("setting values");
			
			
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
