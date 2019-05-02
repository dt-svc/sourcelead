package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.constants.ImmigrationConstants;
import com.bis.sourcelead.couchbase.connection.CouchbaseConnectionConfig;
import com.bis.sourcelead.couchbase.connection.CouchbasePropertyPathConfiguration;
import com.bis.sourcelead.couchbase.util.UserStatsCacheHelper;
import com.bis.sourcelead.dto.CandidateBulkUploadDTO;
import com.bis.sourcelead.dto.DataMigrationCommonDTO;
import com.bis.sourcelead.dto.DataMigrationDTO;
import com.bis.sourcelead.dto.UploadEntriesDTO;
import com.bis.sourcelead.dto.UploadXlsHistoryDTO;
import com.bis.sourcelead.dto.XlsBulkUploadHistroyDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Shift;
import com.bis.sourcelead.entity.SourceleadFormFields;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.XlsBulkUploadHistroy;
import com.bis.sourcelead.service.CouchbaseClusterService;
import com.bis.sourcelead.service.DataMigrationService;
import com.bis.sourcelead.service.ShiftService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UploadXlsHistoryService;
import com.bis.sourcelead.service.XlsBulkUploadHistroyService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.couchbase.client.java.Bucket;

/**
 * This class is used for...
 * 
 * @author: Venkat Kesav
 * @date: 19-Dec-2017
 */
@RestController
@RequestMapping("/app")
public class DataMigrationCommonRestController {
	private static final Logger logger = LoggerFactory.getLogger(DataMigrationCommonRestController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	@Value("${static.path}")
	private String staticPath;
	@Autowired
	CommonUtil commonUtil;
	@Autowired
	TimeZoneService timeZoneService;
	@Autowired
	DataMigrationService dataMigrationService;

	@Autowired
	private ShiftService shiftService;

	@Autowired
	UploadXlsHistoryService uploadXlsHistoryService;

	@Autowired
	XlsBulkUploadHistroyService xlsBulkUploadHistroyService;

	@Autowired
	UserStatsCacheHelper userStatsCacheHelper;

	@Autowired
	CouchbaseClusterService couchbaseClusterService;

	@RequestMapping(value = { "/dataMigrationCommon", "/rest/dataMigrationCommon" }, method = RequestMethod.POST)
	public ModelAndView dataMigrationCommon(DataMigrationCommonDTO dataMigrationCommonDTO, HttpServletRequest request)
			throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");

		ModelAndView model = new ModelAndView();
		try {
			String ac = StringUtils.removeEnd(staticPath, "/slead-static");
			Map<String, String> dataWithCellId = new TreeMap<String, String>();
			dataWithCellId = dataMigrationService.getfileData(ac + dataMigrationCommonDTO.getFilePath(),
					dataMigrationCommonDTO.getHeaderPosition());
			List<SourceleadFormFields> formFields = null;
			switch (dataMigrationCommonDTO.getUploadType()) {
			case "jobUpload":
				formFields = dataMigrationService.getfieldsByActionIdAndImportStatus(46l, 1);
				break;
			case "projectTasks":
				formFields = dataMigrationService.getfieldsByActionIdAndImportStatus(94l, 1);
				break;
			case "associateUpload":
				formFields = dataMigrationService.getfieldsByActionIdAndImportStatus(161l, 1);
				break;
			case "candidatesUpload":
				formFields = dataMigrationService.getfieldsByActionIdAndImportStatus(73l, 1);
				break;
			case "submmissionTemplateUpload":
				formFields = dataMigrationService
						.getSubmissionTemplateFormFields(dataMigrationCommonDTO.getRequirementId());
				break;
			}

			if ("timesheetUpload".equalsIgnoreCase(dataMigrationCommonDTO.getUploadType())) {
				int noOfUnits = 0;
				switch (dataMigrationCommonDTO.getTimesheetMode()) {
				case "BiMonthly":
					noOfUnits = 15;
					break;
				case "Monthly":
					noOfUnits = 31;
					break;
				case "MonthlyWeekWise":
					noOfUnits = 6;
					break;
				case "Weekly":
					noOfUnits = 7;
					break;
				}
				formFields = new ArrayList<SourceleadFormFields>();
				for (int i = 0; i < noOfUnits; i++) {
					SourceleadFormFields field = new SourceleadFormFields();
					if ("Weekly".equalsIgnoreCase(dataMigrationCommonDTO.getTimesheetMode())) {
						field.setLabelName(Constants.SHORTWEEKENDS[i]);
					} else if ("MonthlyWeekWise".equalsIgnoreCase(dataMigrationCommonDTO.getTimesheetMode())) {
						field.setLabelName(Constants.SHORTWEEKWISEMONTH[i]);
					} else {
						field.setLabelName(Constants.SHORTDAYS[i]);
					}
					formFields.add(field);
				}
			}

			List<Shift> shiftList = shiftService.getShiftList(orgLocations.getId());
			model.addObject("shiftList", shiftList);

			model.setViewName("upload/dataImport");
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.addObject("dataWithCellId", dataWithCellId);
			model.addObject("fields", formFields);
			model.addObject("requirementId", dataMigrationCommonDTO.getRequirementId());
			model.addObject("projectUUID", dataMigrationCommonDTO.getProjectUUID());
			model.addObject("projectClientUUID", dataMigrationCommonDTO.getProjectClientUUID());
			model.addObject("exportFilePath", dataMigrationCommonDTO.getFilePath());
			model.addObject("uploadType", dataMigrationCommonDTO.getUploadType());
			model.addObject("timesheetMode", dataMigrationCommonDTO.getTimesheetMode());
			model.addObject("headerPosition", dataMigrationCommonDTO.getHeaderPosition());
			if (StringUtils.isNotBlank(dataMigrationCommonDTO.getUploadType()) && dataMigrationCommonDTO.getUploadType()
					.equalsIgnoreCase(ImmigrationConstants.IMMIGRATION_UPLOAD)) {

				model.addObject("columnHeaders", ImmigrationConstants.IMPORT_IMMIGRATION_EXCEL_HEADERS);
				model.addObject("headersNamesMap", ImmigrationConstants.HEADERS_NAMES_MAP);
				model.addObject("headersFieldNamesMap", ImmigrationConstants.HEADERS_FIELD_NAMES_MAP);
			} else if (StringUtils.isNotBlank(dataMigrationCommonDTO.getUploadType())
					&& dataMigrationCommonDTO.getUploadType().equalsIgnoreCase(ImmigrationConstants.LCA_UPLOAD)) {

				model.addObject("columnHeaders", ImmigrationConstants.IMPORT_LCA_EXCEL_HEADERS);
				model.addObject("headersNamesMap", ImmigrationConstants.LCA_HEADERS_NAMES_MAP);
				model.addObject("headersFieldNamesMap", ImmigrationConstants.LCA_HEADERS_FIELD_NAMES_MAP); // user for
																											// Mandatory
																											// column
																											// labels in
																											// UI.

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = { "/candidateUploadFieldMapping",
			"/rest/candidateUploadFieldMapping" }, method = RequestMethod.POST)
	public ModelAndView candidateUploadFieldMapping(DataMigrationCommonDTO dataMigrationCommonDTO,
			HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		ModelAndView model = new ModelAndView();
		try {
			String ac = StringUtils.removeEnd(staticPath, "/slead-static");
			Map<String, String> dataWithCellId = new TreeMap<String, String>();
			List<SourceleadFormFields> formFieldsList = new CopyOnWriteArrayList<>();
			dataWithCellId = dataMigrationService.getCandidateMappingFileData(ac + dataMigrationCommonDTO.getFilePath(),
					dataMigrationCommonDTO.getHeaderPosition());
			List<SourceleadFormFields> formFields = dataMigrationService.getfieldsByActionIdAndImportStatus(73l, 1);
			
			for (SourceleadFormFields fields : formFields) {
				
				if (orgLocations.getCountry().equalsIgnoreCase("India")) {
					if (StringUtils.isNotBlank(fields.getFieldName()) && !fields.getFieldName().equalsIgnoreCase("ssnNumber")) {
						formFieldsList.add(fields);
					}
				}else {
					if (StringUtils.isNotBlank(fields.getFieldName()) && !fields.getFieldName().equalsIgnoreCase("panNumber")
							&& !fields.getFieldName().equalsIgnoreCase("aadharNumber")) {
						formFieldsList.add(fields);
					}
				}
			}

			model.setViewName("upload/candidateImportFieldMapping");
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.addObject("dataWithCellId", dataWithCellId);
			model.addObject("fields", formFieldsList);
			model.addObject("exportFilePath", dataMigrationCommonDTO.getFilePath());
			model.addObject("uploadType", dataMigrationCommonDTO.getUploadType());
			model.addObject("headerPosition", dataMigrationCommonDTO.getHeaderPosition());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = { "/storeCandidateBulkUploadToCouchbase",
			"/rest/storeCandidateBulkUploadToCouchbase" }, method = RequestMethod.POST)
	public ModelAndView storeCandidateBulkUploadToCouchbase(CandidateBulkUploadDTO candidateBulkUploadDTO,
			UploadEntriesDTO uploadEntriesDTO, HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		candidateBulkUploadDTO.setStaticPath(staticPath);
		String couchbaseQueueKey = dataMigrationService.storeCandidateBulkUploadToCouchbase(candidateBulkUploadDTO,
				uploadEntriesDTO, request);

		model.setViewName("upload/candidateImportFieldMapping");
		model.addObject("couchbaseQueueKey", couchbaseQueueKey);
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}

	@RequestMapping(value = { "/storeCandidateBulkUpload",
			"/rest/storeCandidateBulkUpload" }, method = RequestMethod.POST)
	public void storeCandidateBulkUpload(
			@RequestParam(value = "couchbaseQueueKey", required = true) String couchbaseQueueKey,
			HttpServletRequest request) throws Exception {
		dataMigrationService.saveCandidateBulkImports(couchbaseQueueKey, request);
	}

	@RequestMapping(value = { "/getDataByMatchedFields", "/rest/getDataByMatchedFields" }, method = RequestMethod.POST)
	public ModelAndView getDataByMatchedFields(DataMigrationDTO dataMigrationDTO, HttpServletRequest request)
			throws Exception {
		ModelAndView model = new ModelAndView();
		String importStatus = dataMigrationService.validateImportedFormData(dataMigrationDTO, request);

		model.setViewName("upload/dataImport");
		model.addObject("importStatus", importStatus);
		model.addObject("uploadType", dataMigrationDTO.getUploadType());
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}

	@RequestMapping(value = "/getXlsHistroyByUploadId", method = RequestMethod.POST)
	public @ResponseBody UploadXlsHistoryDTO getXlsHistroyByUploadId(
			@RequestParam(value = "uploadid", required = true) String uploadid) throws Exception {
		UploadXlsHistoryDTO uploadXlsHistoryDto = null;
		try {
			uploadXlsHistoryDto = uploadXlsHistoryService.getUploadXlsHistoryByUploadId(uploadid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uploadXlsHistoryDto;
	}

	@RequestMapping(value = "/getXlsProcessingHistroyListByEntityType", method = RequestMethod.POST)
	public @ResponseBody List<UploadXlsHistoryDTO> getXlsProcessingHistroyListByEntityType(
			@RequestParam(value = "entityType", required = true) String entityType, HttpServletRequest request)
			throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<UploadXlsHistoryDTO> uploadXlsHistoryDTOList = null;
		try {
			uploadXlsHistoryDTOList = uploadXlsHistoryService.getUploadXlsHistoryListByUserId(user.getId(),
					orgLocations.getId(), entityType, orgLocations.getDateFormat());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uploadXlsHistoryDTOList;
	}

	@RequestMapping(value = "/getBulkImportsFailureUploadList", method = RequestMethod.POST)
	public @ResponseBody List<XlsBulkUploadHistroyDTO> getBulkImportsFailureUploadList(
			@RequestParam(value = "entityType", required = true) String entityType, HttpServletRequest request)
			throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		List<XlsBulkUploadHistroyDTO> xlsBulkUploadHistroyDTOList = null;
		try {
			xlsBulkUploadHistroyDTOList = xlsBulkUploadHistroyService
					.getXlsBulkUploadHistroyByUploadTypeAndOrgLocationId(entityType,
							Constants.COUCH_DOCUMENT_UPLOAD_FAILURE_TYPE, orgLocations.getId(),
							orgLocations.getDateFormat());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xlsBulkUploadHistroyDTOList;
	}

	@RequestMapping(value = "/getAllOrgsUploadsList", method = RequestMethod.POST)
	public @ResponseBody List<XlsBulkUploadHistroyDTO> getAllOrgsUploadsList(HttpServletRequest request)
			throws Exception {

		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		List<XlsBulkUploadHistroyDTO> xlsBulkUploadHistroyDTOList = null;
		try {
			xlsBulkUploadHistroyDTOList = xlsBulkUploadHistroyService.getRegisteredOrgsXlsBulkUploadHistroyByUploadType(
					Constants.COUCH_DOCUMENT_UPLOAD_TYPE, orgLocations.getDateFormat());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xlsBulkUploadHistroyDTOList;
	}

	@RequestMapping(value = { "/saveOrgImportedBulkUploads",
			"/rest/saveOrgImportedBulkUploads" }, method = RequestMethod.POST)
	public String saveOrgImportedBulkUploads(@RequestParam(value = "documentKey", required = true) String documentKey,
			HttpServletRequest request) throws Exception {
		dataMigrationService.saveCandidateBulkImports(documentKey, request);
		return "Success";
	}

	@RequestMapping(value = { "/deleteBulkImportFailureRecordsByFailureDocumentKey",
			"/rest/deleteBulkImportFailureRecordsByFailureDocumentKey" }, method = RequestMethod.POST)
	public String deleteBulkImportFailureRecordsByFailureDocumentKey(
			@RequestParam(value = "documentKey", required = true) String documentKey, HttpServletRequest request)
			throws Exception {

		JSONObject jsonObject = userStatsCacheHelper.getBulkImportsFailureList(documentKey);

		if (!jsonObject.isEmpty()) {
			ApplicationContext ctx = new AnnotationConfigApplicationContext(CouchbasePropertyPathConfiguration.class);
			CouchbaseConnectionConfig couchConfig = ctx.getBean(CouchbaseConnectionConfig.class);
			Bucket bucket = couchbaseClusterService.openBucket(couchConfig.getBulkImportsBucket(),
					couchConfig.getPassword());

			for (Object key : jsonObject.keySet()) {
				String keyStr = (String) key;
				if (keyStr.equalsIgnoreCase("failureList")) {
					String jsonString = jsonObject.get(keyStr).toString();
					JSONArray jsonArray = new JSONArray(jsonString);

					for (int i = 0; i < jsonArray.length(); i++) {
						JSONParser parser = new JSONParser();
						JSONObject jsonObjVal = (JSONObject) parser.parse(jsonArray.get(i).toString());
						for (Object okey : jsonObjVal.keySet()) {
							String failurekeyStr = (String) okey;
							if (failurekeyStr.equalsIgnoreCase("documentId")) {
								String keyvalue = jsonObjVal.get(failurekeyStr).toString();
								bucket.remove(keyvalue);
							}

						}
					}
				}
			}
			bucket.remove(documentKey);
		}

		XlsBulkUploadHistroy history = xlsBulkUploadHistroyService.getXlsBulkUploadHistroyByDocumentKey(documentKey);
		history.setStatus(Constants.RECORD_INACTIVE);
		xlsBulkUploadHistroyService.storeXlsBulkUploadHistroy(history);
		return "Success";

	}

	@RequestMapping(value = { "/bulkUploadEmails", "/rest/bulkUploadEmails" }, method = RequestMethod.POST)
	public void bulkUploadEmails(@RequestParam(value = "couchbaseQueueKey", required = true) String couchbaseQueueKey,
			@RequestParam(value = "uploadedSize", required = true) String uploadedSize,
			@RequestParam(value = "type", required = true) String type, HttpServletRequest request) throws Exception {
		dataMigrationService.bulkUploadEmails(couchbaseQueueKey, uploadedSize, type, request);
	}

}
