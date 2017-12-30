package com.bis.sourcelead.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.DataMigrationDao;
import com.bis.sourcelead.dto.DataMigrationDTO;
import com.bis.sourcelead.dto.OrgSpocDTO;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.SourceleadFormFields;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.rest.ClientRestController;
import com.bis.sourcelead.service.DataMigrationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

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
	private UserService userService;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private OrgSpocService orgSpocService;
	
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
		while (rowIterator.hasNext()) {
			Row nextRow = rowIterator.next();
			if (rowId == 0) {
				rowId = rowId + 1;
				continue;
			}
			HashMap< String, String> errorSet = new HashMap< String, String>();
			JSONObject dataMigrationDTOJson = new JSONObject(dataMigrationDTO);
			DataMigrationDTO dataMigrationDTOCopy = new DataMigrationDTO();
			JSONObject dataMigrationDTOJsonCopy = new JSONObject(dataMigrationDTOCopy);
			dataMigrationDTOJson.keys().forEachRemaining(k -> {
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
			});
			
			dataValidationForJobs(errorSet, dataMigrationDTOJsonCopy, objFormulaEvaluator, objDefaultFormat, user, orgLocation);
			
			if(errorSet.isEmpty()){
				mapToEntities(dataMigrationDTOJsonCopy.toString(), user, orgLocation);
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
			        font.setColor(HSSFColor.RED.index);
			        style.setFont(font);
			        style.setWrapText(true);
				 	nextRow.createCell(0).setCellValue(errorSB.toString());
				 	nextRow.getCell(0).setCellStyle(style);

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
	
	private void mapToEntities(String dataMigrationDTOJsonCopy, User user, OrgLocations orgLocation) throws Exception
	{
		RequirementDTO reqDTO = new RequirementDTO();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		reqDTO = mapper.readValue(dataMigrationDTOJsonCopy, RequirementDTO.class);
		reqDTO.setDateFormat(orgLocation.getDateFormat());
		reqDTO.setSaveType("save");
		if(reqDTO.getParallelApprover() != null){
			reqDTO.setApprover("Parallel");
		}
		Requirement requirement = requirementService.storeRequirement(reqDTO, orgLocation, user);
	}
	
	
	
	@SuppressWarnings("unchecked")
	private void dataValidationForJobs(HashMap< String, String> errorSet, JSONObject dataMigrationDTOJsonCopy, FormulaEvaluator objFormulaEvaluator, DataFormatter objDefaultFormat, User user, OrgLocations orgLocation) throws Exception
	{
		logger.debug("In dataValidationForJobs method...");
		dataMigrationDTOJsonCopy.keys().forEachRemaining(k -> {
			try {
				Boolean flag;
				if((k.toString().equalsIgnoreCase("manager") || k.toString().equalsIgnoreCase("priority") || k.toString().equalsIgnoreCase("jobTitle") || k.toString().equalsIgnoreCase("location")) && (dataMigrationDTOJsonCopy.get(k.toString()) != null || dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") || dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null"))){
					errorSet.put(k.toString(), k.toString()+"  field is missing");
				}
				
				if(k.toString().equalsIgnoreCase("jobId") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					flag = false;
					flag = requirementService.validateJobid(dataMigrationDTOJsonCopy.get((String)k).toString(), orgLocation.getId());
					if(flag){
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is Already Existed");
					}
				}
				
				if(k.toString().equalsIgnoreCase("applicationFrom") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					SubmissionTemplate submissionTemplate = submissionTemplateService.getSubmissionTemplateByTemplateNameByOrg(orgLocation.getId(), dataMigrationDTOJsonCopy.get((String)k).toString());
					if(null != submissionTemplate){
						dataMigrationDTOJsonCopy.put((String) k, submissionTemplate.getId().toString());
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid");
					}
				}
				
				if(k.toString().equalsIgnoreCase("priority") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String jobPriority = dataMigrationDTOJsonCopy.get((String)k).toString().toLowerCase();
					if(jobPriority.equalsIgnoreCase("medium") || jobPriority.equalsIgnoreCase("low") || jobPriority.equalsIgnoreCase("high") || jobPriority.equalsIgnoreCase("urgent")){
						jobPriority = jobPriority.substring(0, 1).toUpperCase()+jobPriority.substring(1, jobPriority.length());
						dataMigrationDTOJsonCopy.put((String) k, jobPriority);
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid");
					}
				}
				
				if(k.toString().equalsIgnoreCase("noticePeriod") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String noticePeriod = dataMigrationDTOJsonCopy.get((String)k).toString();
					if(noticePeriod.equalsIgnoreCase("Immediately Available") || noticePeriod.equalsIgnoreCase("15 Days") || noticePeriod.equalsIgnoreCase("1 Month") || noticePeriod.equalsIgnoreCase("2 Months") || noticePeriod.equalsIgnoreCase("3 Months")){
						dataMigrationDTOJsonCopy.put((String) k, noticePeriod);
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Notice Period");
					}
				}
				
				if(k.toString().equalsIgnoreCase("experienceLevel") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String experienceLevel = dataMigrationDTOJsonCopy.get((String)k).toString();
					if(experienceLevel.equalsIgnoreCase("Level-1") || experienceLevel.equalsIgnoreCase("Level-2") || experienceLevel.equalsIgnoreCase("Level-3") || experienceLevel.equalsIgnoreCase("Level-4")){
						dataMigrationDTOJsonCopy.put((String) k, experienceLevel);
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Experience Level");
					}
				}
				
				if(k.toString().equalsIgnoreCase("minExp") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String minExp = dataMigrationDTOJsonCopy.get((String)k).toString();
					if(NumberUtils.isNumber(minExp)){
						if(Float.valueOf(minExp) <= 999){
							dataMigrationDTOJsonCopy.put((String) k, minExp);
						}else{
							errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid, should between 0 to 999");
						}
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Min Experience");
					}
				}
				
				if(k.toString().equalsIgnoreCase("maxExp") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String maxExp = dataMigrationDTOJsonCopy.get((String)k).toString();
					if(NumberUtils.isNumber(maxExp)){
						if(Float.valueOf(maxExp) <= 999){
							dataMigrationDTOJsonCopy.put((String) k, maxExp);
						}else{
							errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid, should between 0 to 999");
						}
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Max Experience");
					}
				}
				
				if(k.toString().equalsIgnoreCase("rate") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String rate = dataMigrationDTOJsonCopy.get((String)k).toString();
					if(NumberUtils.isNumber(rate)){
						if(Long.valueOf(rate) <= 99999999){
							dataMigrationDTOJsonCopy.put((String) k, rate);
						}else{
							errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Rate, should between 1 to 8 digits");
						}
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Rate");
					}
				}
				
				if(k.toString().equalsIgnoreCase("clientRate") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String clientRate = dataMigrationDTOJsonCopy.get((String)k).toString();
					if(NumberUtils.isNumber(clientRate)){
						if(Long.valueOf(clientRate) <= 99999999){
							dataMigrationDTOJsonCopy.put((String) k, clientRate);
						}else{
							errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Client Rate, should between 1 to 8 digits");
						}
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Client Rate");
					}
				}
				
				if(k.toString().equalsIgnoreCase("coolingOff") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String coolingOff = dataMigrationDTOJsonCopy.get((String)k).toString();
					String[] coolingOffSet = {"0 Days","1 Week","2 Weeks","3 Weeks","1 Month","2 Months","3 Months","4 Months","5 Months","6 Months","7 Months","8 Months","9 Months","10 Months","11 Months","1 Year"};
					if(ArrayUtils.contains( coolingOffSet, coolingOff)){
						dataMigrationDTOJsonCopy.put((String) k, coolingOff);
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid cooling-off period");
					}
				}
				
				if(k.toString().equalsIgnoreCase("closedDate") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					try {
						DateFormat formatter = new SimpleDateFormat("DD-MM-yyyy"); 
						Date date = (Date)formatter.parse(dataMigrationDTOJsonCopy.get((String)k).toString());
						String closedDate = formatter.format(date);
						dataMigrationDTOJsonCopy.put((String) k, closedDate);
					} catch (Exception e) {
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Close Date");
					}
				}
				
				if(k.toString().equalsIgnoreCase("manager") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					User approverList = userService.getTabAccessOwnersByOrgLocationIdEmailAndTabId(orgLocation.getId(), dataMigrationDTOJsonCopy.get(k.toString()).toString(), Long.valueOf(Constants.TAB_REQUIREMENT));
					if(approverList == null){
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Email");
					}else{
						dataMigrationDTOJsonCopy.put((String) k, approverList.getId().toString());
					}
				}
				
				if(k.toString().equalsIgnoreCase("selectClient") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					OrgRelations orgRelations = orgRelationsService.getClientsByOrgLocationIdAndName(orgLocation.getId(), dataMigrationDTOJsonCopy.get(k.toString()).toString());
					if(orgRelations == null){
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Client");
					}else{
						dataMigrationDTOJsonCopy.put((String) k, orgRelations.getId().toString());
					}
				}
				
				if(k.toString().equalsIgnoreCase("selectContact") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					if(dataMigrationDTOJsonCopy.get("selectClient") != null){
						OrgRelations orgRelations = orgRelationsService.getClientsByOrgLocationIdAndName(orgLocation.getId(), dataMigrationDTOJsonCopy.get("selectClient").toString());
						if(orgRelations != null){
							List<OrgSpoc> orgSpocsList  = orgSpocService.getAllSpocsByOrgRelationId(orgRelations.getId(), user.getId(),orgLocation.getId(),"Client");
							if(orgSpocsList != null){
								for(OrgSpoc orgspoc:orgSpocsList){
									if(dataMigrationDTOJsonCopy.get(k.toString()).toString().toLowerCase().equalsIgnoreCase(orgspoc.getSpocEmail())){
										dataMigrationDTOJsonCopy.put((String) k, orgspoc.getId().toString());
									}else{
										errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Client Contact");
									}
								}
							}else{
								errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Client Contact");
							}
						}else{
							errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Client Contact");
						}
						
					}
				}
				
				if(k.toString().equalsIgnoreCase("clientSubmissionForm") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					if(dataMigrationDTOJsonCopy.get("selectClient") != null && dataMigrationDTOJsonCopy.get("selectClient") != ""){
						OrgRelations orgRelations = orgRelationsService.getClientsByOrgLocationIdAndName(orgLocation.getId(), dataMigrationDTOJsonCopy.get("selectClient").toString());
						if(orgRelations != null){
							Long clientLocationId = null;
							if(orgRelations.getLocationId().getId() == orgLocation.getId()){
								clientLocationId = orgRelations.getResponderLocationId().getId();
							}
							if(orgRelations.getResponderLocationId().getId() == orgLocation.getId()){
								clientLocationId = orgRelations.getLocationId().getId();
							}
							List<SubmissionTemplate> submissiontTemplateList  = submissionTemplateService.getSubmissionTemplateByClientLocationIdAndOrgLocationIdAndTemplateType(clientLocationId, orgLocation.getId(),"Submission");
							if(submissiontTemplateList != null){
								for(SubmissionTemplate submissiontTemplate:submissiontTemplateList){
									if(dataMigrationDTOJsonCopy.get(k.toString()).toString().toLowerCase().equalsIgnoreCase(submissiontTemplate.getTemplateName())){
										dataMigrationDTOJsonCopy.put((String) k, submissiontTemplate.getId().toString());
									}else{
										errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Submission Form");
									}
								}
							}else{
								errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Submission Form");
							}
						}else{
							errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Submission Form");
						}
					}
				}
				
				if(k.toString().equalsIgnoreCase("assignedTo") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					List<User> usersList = userService.getUsersByTeamTypeAndTabId(orgLocation.getId(),"", Long.valueOf(Constants.TAB_REQUIREMENT));
					List<String> validAssTo = new ArrayList<String>();
					StringBuffer invalidAssMembers = new StringBuffer();
					String[] assTo = dataMigrationDTOJsonCopy.get(k.toString()).toString().split(",");
					//int assToCount = assTo.length;
					//int matchedCount = 0;
					for(User assignUser : usersList){
						for(String userEmail : assTo){
							if(assignUser.getEmail().toLowerCase().equalsIgnoreCase(userEmail.trim().toLowerCase())){
								validAssTo.add(assignUser.getId().toString()+",undefined");
								//matchedCount++;
							}else{
								invalidAssMembers.append(userEmail).append(", ");
							}
						}
					}
					if(validAssTo == null){
						errorSet.put(k.toString(), "Asigned members are invalid");
					}else{
						dataMigrationDTOJsonCopy.put((String) k, validAssTo);
					}
				}
				
				
				if(k.toString().equalsIgnoreCase("parallelApprover") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					List<User> usersList = userService.getUsersByTeamTypeAndTabId(orgLocation.getId(),"", Long.valueOf(Constants.TAB_REQUIREMENT));
					List<String> validApprovers = new ArrayList<String>();
					StringBuffer invalidApprovers = new StringBuffer();
					String[] apprs = dataMigrationDTOJsonCopy.get(k.toString()).toString().split(",");
					for(User appUser : usersList){
						for(String userEmail : apprs){
							if(appUser.getEmail().toLowerCase().equalsIgnoreCase(userEmail.trim().toLowerCase())){
								validApprovers.add(appUser.getId().toString());
							}else{
								invalidApprovers.append(userEmail).append(", ");
							}
						}
					}
					if(validApprovers == null){
						errorSet.put(k.toString(), "Approvers are invalid");
					}else{
						dataMigrationDTOJsonCopy.put((String) k, validApprovers);
					}
				}
								
				/*String keyStr = (String)k;
				System.out.println(keyStr);
		        Object keyvalue = dataMigrationDTOJsonCopy.get(keyStr);
		        System.out.println(keyvalue);*/
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
}
