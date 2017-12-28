package com.bis.sourcelead.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
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
					errorSB.append(errorCount).append(") ").append(error.getValue()).append(". ");
				}
				 	nextRow.createCell(1).setCellValue(errorSB.toString());

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
		reqDTO.setSaveType("draft");
		Requirement requirement = requirementService.storeRequirement(reqDTO, orgLocation, user);
	}
	
	
	
	@SuppressWarnings("unchecked")
	private void dataValidationForJobs(HashMap< String, String> errorSet, JSONObject dataMigrationDTOJsonCopy, FormulaEvaluator objFormulaEvaluator, DataFormatter objDefaultFormat, User user, OrgLocations orgLocation) throws Exception
	{
		dataMigrationDTOJsonCopy.keys().forEachRemaining(k -> {
			try {
				Boolean flag;				
				if(k.toString().equalsIgnoreCase("jobId") && dataMigrationDTOJsonCopy.get((String)k) != null){
					flag = false;
					flag = requirementService.validateJobid(dataMigrationDTOJsonCopy.get((String)k).toString(), orgLocation.getId());
					if(flag){
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is Already Existed");
					}
				}
				
				if(k.toString().equalsIgnoreCase("applicationFrom") && dataMigrationDTOJsonCopy.get((String)k) != null){
					SubmissionTemplate submissionTemplate = submissionTemplateService.getSubmissionTemplateByTemplateNameByOrg(orgLocation.getId(), dataMigrationDTOJsonCopy.get((String)k).toString());
					if(null != submissionTemplate){
						dataMigrationDTOJsonCopy.put((String) k, submissionTemplate.getId().toString());
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid");
					}
				}
				
				if(k.toString().equalsIgnoreCase("priority") && dataMigrationDTOJsonCopy.get((String)k) != null){
					String jobPriority = dataMigrationDTOJsonCopy.get((String)k).toString().toLowerCase();
					if(jobPriority.equalsIgnoreCase("medium") || jobPriority.equalsIgnoreCase("low") || jobPriority.equalsIgnoreCase("high") || jobPriority.equalsIgnoreCase("urgent")){
						jobPriority = jobPriority.substring(0, 1).toUpperCase()+jobPriority.substring(1, jobPriority.length());
						dataMigrationDTOJsonCopy.put((String) k, jobPriority);
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid");
					}
				}
				
				if(k.toString().equalsIgnoreCase("manager") && dataMigrationDTOJsonCopy.get((String)k) != null){
					User approverList = userService.getTabAccessOwnersByOrgLocationIdEmailAndTabId(orgLocation.getId(), dataMigrationDTOJsonCopy.get(k.toString()).toString(), Long.valueOf(Constants.TAB_REQUIREMENT));
					if(approverList == null){
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Email");
					}else{
						dataMigrationDTOJsonCopy.put((String) k, approverList.getId().toString());
					}
				}
				
				if(k.toString().equalsIgnoreCase("selectClient") && dataMigrationDTOJsonCopy.get((String)k) != null){
					OrgRelations orgRelations = orgRelationsService.getClientsByOrgLocationIdAndName(orgLocation.getId(), dataMigrationDTOJsonCopy.get(k.toString()).toString());
					if(orgRelations == null){
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Client");
					}else{
						dataMigrationDTOJsonCopy.put((String) k, orgRelations.getId().toString());
					}
				}
				
				if(k.toString().equalsIgnoreCase("selectContact") && dataMigrationDTOJsonCopy.get((String)k) != null){
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
				
				if(k.toString().equalsIgnoreCase("clientSubmissionForm") && dataMigrationDTOJsonCopy.get((String)k) != null){
					if(dataMigrationDTOJsonCopy.get("selectClient") != null){
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
