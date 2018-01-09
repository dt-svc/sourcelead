package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.format.CellFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.DataMigrationDao;
import com.bis.sourcelead.dto.DataMigrationDTO;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.SourceleadFormFields;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.DataMigrationService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.util.DataMigrationCommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DataMigrationServiceImpl implements DataMigrationService {
	private static final Logger logger = LoggerFactory.getLogger(DataMigrationServiceImpl.class);
	@Autowired
	DataMigrationDao dataMigrationDao;
	
	@Autowired
	private RequirementService requirementService;

	@Autowired
	private SubmissionTemplateService submissionTemplateService;
	
	@Autowired
	private DataMigrationCommonUtil dataMigrationCommonUtil;
	
	@Value("${static.path}")
	private String staticPath;
	
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
	public List<SourceleadFormFields> getfieldsByActionIdAndImportStatus(Long actionId, int importStatus) throws Exception
	{

		return dataMigrationDao.getfieldsByActionIdAndImportStatus(actionId, importStatus);
	}

	@Override
	public String validateImportedFormData(DataMigrationDTO dataMigrationDTO, HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String importStatus = null;
		String ac = StringUtils.removeEnd(staticPath, "/slead-static");
		FileInputStream inputStream = new FileInputStream(new File(ac
				+ dataMigrationDTO.getExportFilePath().replace("/dataMigration/", "/temp/")));
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
		int rowId = 0;
		int colCount = 0;
		while (rowIterator.hasNext()) {
			Row nextRow = rowIterator.next();
			if (rowId == 0) {
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					colCount++;
				}
				rowId = rowId + 1;
				continue;
			}
			HashMap< String, String> errorSet = new HashMap< String, String>();
			JSONObject dataMigrationDTOJson = new JSONObject(dataMigrationDTO);
			DataMigrationDTO dataMigrationDTOCopy = new DataMigrationDTO();
			JSONObject dataMigrationDTOJsonCopy = new JSONObject(dataMigrationDTOCopy);
			
			Iterator<?> keys = dataMigrationDTOJson.keys();

			while( keys.hasNext() ) {
			    String k = (String)keys.next();
			    	Iterator<Cell> cellIterator = nextRow.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						if (dataMigrationDTOJson.get((String) k) != null) {
							if ((dataMigrationDTOJson.get((String) k).toString().replaceAll("\\d", "")).equalsIgnoreCase(CellReference.convertNumToColString(cell.getColumnIndex()))) {
								dataMigrationDTOJsonCopy.put((String) k, objDefaultFormat.formatCellValue(cell, objFormulaEvaluator).toString().trim());
								//dataValidationForJobs(errorSet, dataMigrationDTOJsonCopy, (String) k, objFormulaEvaluator, objDefaultFormat, user, orgLocation);
							}
						}
					}
			}
			
			/*dataMigrationDTOJson.keys().forEachRemaining(k -> {
				try {
					Iterator<Cell> cellIterator = nextRow.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						if (dataMigrationDTOJson.get((String) k) != null) {
							if ((dataMigrationDTOJson.get((String) k).toString().replaceAll("\\d", "")).equalsIgnoreCase(CellReference.convertNumToColString(cell.getColumnIndex()))) {
								dataMigrationDTOJsonCopy.put((String) k, objDefaultFormat.formatCellValue(cell, objFormulaEvaluator).toString().trim());
								//dataValidationForJobs(errorSet, dataMigrationDTOJsonCopy, (String) k, objFormulaEvaluator, objDefaultFormat, user, orgLocation);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});*/
			
			switch(dataMigrationDTO.getUploadType()){  
		    case "jobUpload": dataMigrationCommonUtil.dataValidationForJobs(errorSet, dataMigrationDTOJsonCopy, objFormulaEvaluator, objDefaultFormat, user, orgLocation);break;  
		    case "timesheetUpload": dataMigrationCommonUtil.dataValidationForTimesheets(errorSet, dataMigrationDTOJsonCopy, objFormulaEvaluator, objDefaultFormat, user, orgLocation);break;  
		    }
			
			
			if(errorSet.isEmpty()){
				mapToEntities(dataMigrationDTOJsonCopy.toString(), user, orgLocation, dataMigrationDTO.getUploadType());
				importStatus = "success";
			}else{
				int errorCount = 0;
				StringBuilder errorSB = new StringBuilder();
				for (Map.Entry<String, String> error : errorSet.entrySet()) {
					errorCount++;
				    /*errorSB.append(errorCount).append(") ").append(error.getKey()).append(" : ").append(error.getValue()).append(".");*/
					errorSB.append(errorCount).append(") ").append(error.getValue()).append("\n");
				}
					CellStyle style = workbook.createCellStyle();
			        Font font = workbook.createFont();
			        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
			        font.setColor(HSSFColor.RED.index);
			        style.setFont(font);
			        style.setWrapText(true);
				 	nextRow.createCell(colCount+5).setCellValue(errorSB.toString());
				 	nextRow.getCell(colCount+5).setCellStyle(style);

			        FileOutputStream outputStream = new FileOutputStream(new File(ac + dataMigrationDTO.getExportFilePath()));
			        workbook.write(outputStream);
			        //outputStream.close();
			        importStatus = dataMigrationDTO.getExportFilePath();
			}
		}
		workbook.close();
		inputStream.close();
		return importStatus;
	}
	
	private void mapToEntities(String dataMigrationDTOJsonCopy, User user, OrgLocations orgLocation, String uploadType) throws Exception
	{
		switch(uploadType){
		    case "jobUpload": 
		    	RequirementDTO reqDTO = new RequirementDTO();
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				reqDTO = mapper.readValue(dataMigrationDTOJsonCopy, RequirementDTO.class);
				reqDTO.setDateFormat(orgLocation.getDateFormat());
				if(reqDTO.getRequirementSubmissionStatus().equalsIgnoreCase("SUBMITTED")) {
					reqDTO.setSaveType("submitForApproval");
				}
				if(reqDTO.getRequirementSubmissionStatus().equalsIgnoreCase("DRAFT")) {
					reqDTO.setSaveType("draft");
				}
				if(reqDTO.getRequirementSubmissionStatus().equalsIgnoreCase("OPEN")) {
					reqDTO.setSaveType("save");
				}
				
				if(reqDTO.getParallelApprover() != null){
					reqDTO.setApprover("Parallel");
				}
				if(reqDTO.getCoolingOff() == null || reqDTO.getCoolingOff() == "" || reqDTO.getCoolingOff() == "null"){
					reqDTO.setCoolingOff(orgLocation.getCoolingOffPeriod());
				}
				
				if(StringUtils.isNotEmpty(reqDTO.getClientSubmissionForm()) && StringUtils.isNotEmpty(reqDTO.getSelectContact()) && StringUtils.isNotEmpty(reqDTO.getSelectClient())){
					reqDTO.setIsClient("Y");
					reqDTO.setApplicationFrom(null);
				}else if(StringUtils.isEmpty(reqDTO.getIsClient()) || !reqDTO.getIsClient().equalsIgnoreCase("Y")){
					if(StringUtils.isEmpty(reqDTO.getApplicationFrom())) {
						SubmissionTemplate   submissionTempletes = submissionTemplateService.getDefaultSubmissionTemplateByOrgLocationId(orgLocation.getId());
						reqDTO.setApplicationFrom(submissionTempletes.getId().toString());
					}
				}
				
				Requirement requirement = requirementService.storeRequirement(reqDTO, orgLocation, user);
		    break;
		    
		    case "timesheetUpload": 
		    	System.out.println("Time Sheet block");	    
		    break;
	    }
	}
}
