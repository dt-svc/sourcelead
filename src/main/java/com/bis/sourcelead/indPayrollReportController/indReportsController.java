package com.bis.sourcelead.indPayrollReportController;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.indPayroll.dto.IndEmployeePayrollDTO;
import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.indPayroll.entity.IndPaysheetFrequency;
import com.bis.sourcelead.indPayroll.service.IndPaysheetFrequencyService;
import com.bis.sourcelead.indPayroll.utill.INDPayrollCommonUtil;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.UserGroupService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.dynamicReport.IndPaySlipPdf;

@Controller
@RequestMapping("/app")
public class indReportsController {

private static final Logger logger = LoggerFactory.getLogger(indReportsController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	OrgRelationsService orgRelationsService;

	@Autowired
	TabsService tabsService;
	
	@Autowired
	ProjectsService projectsService;
	
	@Autowired
	INDPayrollCommonUtil iNDPayrollCommonUtil;
	
	@Autowired
	IndPaySlipPdf indPaySlipPdf;
	
	@Autowired
	IndPaysheetFrequencyService indPaysheetFrequencyService;
	 
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	UserGroupService userGroupService;
	
	@Autowired
	RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	
	@Autowired
	private EmployeesService employeesService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
		
		 @RequestMapping(value = "/indPayrollReportList")
			public ModelAndView indPayrollReportList(HttpServletRequest request,HttpServletResponse response) throws Exception {
			 ModelAndView model = new ModelAndView();
			 model.addObject("sourceleadUrl", sourceleadUrl);
			 model.setViewName("reports/indPayrollReportList");
			 return model;
		 }
		 
		 
		 @RequestMapping(value = "/indPayrollRegisterReport")
			public ModelAndView indPayrollRegisterReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
			 ModelAndView model = new ModelAndView();
			 model.addObject("sourceleadUrl", sourceleadUrl);
			 model.setViewName("reports/indPayrollRegisterReport");
			 return model;
		 }
		 
		 @RequestMapping(value = "/indPayrollBankTransferReport")
			public ModelAndView indPayrollBankTransferReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
			 ModelAndView model = new ModelAndView();
			 model.addObject("sourceleadUrl", sourceleadUrl);
			 model.setViewName("reports/indPayrollBankTransferReport");
			 return model;
		 }
		 @RequestMapping(value = "/indPayrollYTDSummaryReport")
			public ModelAndView indPayrollYTDSummaryReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
			 ModelAndView model = new ModelAndView();
			 model.addObject("sourceleadUrl", sourceleadUrl);
			 model.setViewName("reports/indPayrollYTDSummaryReport");
			 return model;
		 }
		 
		 @RequestMapping(value = "/indPayrollAnnualTaxReport")
			public ModelAndView indPayrollAnnualTaxReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
			 ModelAndView model = new ModelAndView();
             OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
             List<String> finYearList = iNDPayrollCommonUtil.getFinancialYearByOrgLocation(orgLocation);
			 model.addObject("finYearList", finYearList);
			 model.addObject("sourceleadUrl", sourceleadUrl);
			 model.setViewName("reports/indPayrollAnnualTaxReport");
			 return model;
		 }
		 
		 @RequestMapping(value = "/indPayrollLoanReport")
			public ModelAndView indPayrollLoanReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
			 ModelAndView model = new ModelAndView();
			 model.addObject("sourceleadUrl", sourceleadUrl);
			 model.setViewName("reports/indPayrollLoanReport");
			 return model;
		 }
		 
		 @RequestMapping(value = "/indPayrollSalaryStatementReport")
			public ModelAndView indPayrollSalaryStatementReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
			 ModelAndView model = new ModelAndView();
             OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
             List<String> finYearList = iNDPayrollCommonUtil.getFinancialYearByOrgLocation(orgLocation);
			 model.addObject("finYearList", finYearList);
			 model.addObject("sourceleadUrl", sourceleadUrl);
			 model.setViewName("reports/indPayrollSalaryStatementReport");
			 return model;
		 }
		 
		 
		 @RequestMapping(value = "/indPayrollESISummaryReport")
			public ModelAndView indPayrollESISummaryReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
			 ModelAndView model = new ModelAndView();
             OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
             List<String> finYearList = iNDPayrollCommonUtil.getFinancialYearByOrgLocation(orgLocation);
			 model.addObject("finYearList", finYearList);
			 model.addObject("sourceleadUrl", sourceleadUrl);
			 model.setViewName("reports/indPayrollESISummaryReport");
			 return model;
		 }
		 
		 @RequestMapping(value = "/indPayrollPFSummaryReport")
			public ModelAndView indPayrollPFSummaryReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
			 ModelAndView model = new ModelAndView();
			 Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		     OrgLocations orgLocation = orgLocationService.getOrgLocations(orgLocationId);
             List<String> finYearList = iNDPayrollCommonUtil.getFinancialYearByOrgLocation(orgLocation);
			 model.addObject("finYearList", finYearList);
			 model.addObject("sourceleadUrl", sourceleadUrl);
			 model.setViewName("reports/indPayrollPFSummaryReport");
			 return model;
		 }
		 
		 
		 
		 @RequestMapping(value = "/indPayrollSalaryRegisterReport")
			public ModelAndView indPayrollSalaryRegisterReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
			 ModelAndView model = new ModelAndView();
			 model.addObject("sourceleadUrl", sourceleadUrl);
			 model.setViewName("reports/indPayrollSalaryRegisterReport");
			 return model;
		 }
		 
		 @RequestMapping(value = { "/runIndPayrollregister","/rest/runIndPayrollregister" }, method = RequestMethod.POST)
			@ResponseBody
			public ResponseList<IndEmployeePayrollDTO> runPayrollregister(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
			{
				ResponseList<IndEmployeePayrollDTO> responseList = new ResponseList<>();
				try {
					OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
					if (orgLocations != null) {
						String[] tableColumns = {"empId", "empName", "department", "", "", "", "", "", ""};
					    int sortColumnIndex = 1;		 //by default sorting using updated date
						String sortColumnOrder = "asc";  //by default descending order
						if(indEmployeePayrollDTO.getOrder() != null && !indEmployeePayrollDTO.getOrder().isEmpty()) {
							sortColumnIndex = Integer.parseInt(indEmployeePayrollDTO.getOrder().get(0).get("column"));
							sortColumnOrder = indEmployeePayrollDTO.getOrder().get(0).get("dir");
						}
						indEmployeePayrollDTO.setSortColumn(tableColumns[sortColumnIndex]);
						indEmployeePayrollDTO.setSortOrder(sortColumnOrder);
						
						ResponseList<IndEmployeePayroll> payResponseList = indPaysheetFrequencyService.getPayrollRegisterReports(indEmployeePayrollDTO,orgLocations);
						List<IndEmployeePayrollDTO> indEmployeePayrollList = new ArrayList<>();
						for(IndEmployeePayroll iepdt :payResponseList.getData()) {
							IndEmployeePayrollDTO dto = new IndEmployeePayrollDTO();
							dto.setEmployeeid(iepdt.getEmployee().getEmployeeId());
							dto.setEmployeeName(iepdt.getCandidate().getFullName());
							dto.setDepartmentName(iepdt.getEmployee().getDepartmentId().getDepartmentName());
							BigDecimal currentHours = new BigDecimal("0");
							if(iepdt.getRegularHours() != null) {
								currentHours.add(new BigDecimal(iepdt.getRegularHours()));
							}
							if(iepdt.getOverTimeHours() != null) {
								currentHours.add(new BigDecimal(iepdt.getOverTimeHours()));
							}
							dto.setCurrentHours(currentHours);
							dto.setGrossPay(iepdt.getGrossPay());
							dto.setDeducationAmount(iepdt.getGrossPay().subtract(iepdt.getNetPay()));
							dto.setNetPay(iepdt.getNetPay());
							
							List<IndEmployeePayroll> ytdList = indPaysheetFrequencyService.getYTDIndEmployeePayrollList(iepdt.getCandidate().getId(), orgLocations.getStartMonth());
							if(ytdList != null && ytdList.size() > 0) {
								double ytdPT = ytdList.stream().mapToDouble(ap->ap != null && ap.getProfessionalTax()!= null ? ap.getProfessionalTax().doubleValue(): 0).sum();
								double ytdTDS = ytdList.stream().mapToDouble(ap->ap != null && ap.getTds() != null ? ap.getTds().doubleValue() : 0).sum();
								double ytdNetPay = ytdList.stream().mapToDouble(ap->ap != null && ap.getNetPay() != null ? ap.getNetPay().doubleValue() : 0).sum();
								dto.setYtdTaxes(new BigDecimal(ytdPT).add(new BigDecimal(ytdTDS)));
								dto.setYtdNetPay(new BigDecimal(ytdNetPay));
							}else {
								dto.setYtdTaxes(new BigDecimal(0.00));
								dto.setYtdNetPay(new BigDecimal(0.00));
							}
							indEmployeePayrollList.add(dto);
						}
						responseList.setRecordsTotal(indEmployeePayrollList.size());
						responseList.setRecordsFiltered(indEmployeePayrollList.size());
						responseList.setData(indEmployeePayrollList);
						responseList.setResponse(Result.SUCCESS.name());
						responseList.setResponseMessage(Result.SUCCESS.getValue());
					}else{
						responseList.setResponse(Result.SESSION_TIMEDOUT.name());
						responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
					}
				} catch (Exception e) {
					e.printStackTrace();
					responseList.setResponse(Result.AWKWARD.name());
					responseList.setResponseMessage(Result.AWKWARD.getValue());
				}
				return responseList;
			}
		    
		    
		    
		    
		    @RequestMapping(value = { "/exportIndPayrollRegisterReportPdf"})
			public void exportIndPayrollRegisterReportPdf(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
			{
					OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
					if (orgLocations != null) { 
						ResponseList<IndEmployeePayroll> payResponseList = indPaysheetFrequencyService.getPayrollRegisterReports(indEmployeePayrollDTO,orgLocations);
						if(payResponseList.getData().size() > 0) {
							File file = indPaySlipPdf.getIndPayrollRegisterReportsPdf(payResponseList,orgLocations);
							String mimeType = URLConnection.guessContentTypeFromName(file.getName());
							if (mimeType == null) {
								mimeType = "application/octet-stream";
							}
							response.setContentType(mimeType);
							StringBuilder fileName = new StringBuilder();
							response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
							response.setContentLength((int) file.length());
							InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
							FileCopyUtils.copy(inputStream, response.getOutputStream());
							inputStream.close();
							response.getOutputStream().close();
							response.getOutputStream().flush();
							if (file != null) {
								file.delete();
							}
						}
					}
			}
		    
		    
		    
		    
		    @RequestMapping(value = { "/indPayrollBankTranseferReport","/rest/indPayrollBankTranseferReport" }, method = RequestMethod.POST)
		   	@ResponseBody
		   	public ResponseList<IndEmployeePayrollDTO> indPayrollBankTranseferReport(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
		   	{
		   		ResponseList<IndEmployeePayrollDTO> responseList = new ResponseList<>();
		   		try {
		   			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		   			if (orgLocations != null) {
		   				String[] tableColumns = {"empId", "empName", "department", "", "", "", "", "", ""};
		   			    int sortColumnIndex = 1;		 //by default sorting using updated date
		   				String sortColumnOrder = "asc";  //by default descending order
		   				if(indEmployeePayrollDTO.getOrder() != null && !indEmployeePayrollDTO.getOrder().isEmpty()) {
		   					sortColumnIndex = Integer.parseInt(indEmployeePayrollDTO.getOrder().get(0).get("column"));
		   					sortColumnOrder = indEmployeePayrollDTO.getOrder().get(0).get("dir");
		   				}
		   				indEmployeePayrollDTO.setSortColumn(tableColumns[sortColumnIndex]);
		   				indEmployeePayrollDTO.setSortOrder(sortColumnOrder);
		   				
		   				ResponseList<IndEmployeePayroll> payResponseList = indPaysheetFrequencyService.getPayrollBankReportReportList(indEmployeePayrollDTO,orgLocations);
		   				List<IndEmployeePayrollDTO> indEmployeePayrollList = new ArrayList<>();
		   				for(IndEmployeePayroll iepdt :payResponseList.getData()) {
		   					IndEmployeePayrollDTO dto = new IndEmployeePayrollDTO();
		   					dto.setEmployeeid(iepdt.getEmployee().getEmployeeId());
		   					dto.setEmployeeName(iepdt.getCandidate().getFullName());
		   					dto.setDepartmentName(iepdt.getEmployee().getDepartmentId().getDepartmentName());
		   				 
		   					if(iepdt.getPaymentAccount() != null) {
		   						Boolean flage = iepdt.getPaymentAccount().isDefaultAccount();
		   						if(flage) {
		   							dto.setBankName(iepdt.getPaymentAccount().getBankName());
		   	   						dto.setAccountNumber(iepdt.getPaymentAccount().getAccountNumber());
		   	   						dto.setIfscCode(iepdt.getPaymentAccount().getRoutingNumber());
		   						}
		   					}
		   					dto.setGrossPay(iepdt.getGrossPay());
		   					dto.setDeducationAmount(iepdt.getGrossPay().subtract(iepdt.getNetPay()));
		   					dto.setNetPay(iepdt.getNetPay());
		   					indEmployeePayrollList.add(dto);
		   				}
		   				responseList.setRecordsTotal(indEmployeePayrollList.size());
		   				responseList.setRecordsFiltered(indEmployeePayrollList.size());
		   				responseList.setData(indEmployeePayrollList);
		   				responseList.setResponse(Result.SUCCESS.name());
		   				responseList.setResponseMessage(Result.SUCCESS.getValue());
		   			}else{
		   				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
		   				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		   			}
		   		} catch (Exception e) {
		   			e.printStackTrace();
		   			responseList.setResponse(Result.AWKWARD.name());
		   			responseList.setResponseMessage(Result.AWKWARD.getValue());
		   		}
		   		return responseList;
		   	}
		       
		    
		    @RequestMapping(value = { "/indPayrollBankTransferReports"})
		   	public void indPayrollBankTransferReports(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
		   	{
		   			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		   			if (orgLocations != null) { 
		   				ResponseList<IndEmployeePayroll> payResponseList = indPaysheetFrequencyService.getPayrollBankReportReportList(indEmployeePayrollDTO,orgLocations);
		   				if(payResponseList.getData().size() > 0) {
		   					File file = null;
		   					if(indEmployeePayrollDTO.getExportType().equalsIgnoreCase("pdf")) {
		      					 file = indPaySlipPdf.getIndPayrollBankTransferReportsPdf(payResponseList,orgLocations);
		   					}else if(indEmployeePayrollDTO.getExportType().equalsIgnoreCase("excel")) {
		      					 file = indPaySlipPdf.getIndPayrollBankTransferReportsExcel(payResponseList,orgLocations);
		   					}
		   					
		   					
		   					String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		   					if (mimeType == null) {
		   						mimeType = "application/octet-stream";
		   					}
		   					response.setContentType(mimeType);
		   					StringBuilder fileName = new StringBuilder();
		   					response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		   					response.setContentLength((int) file.length());
		   					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		   					FileCopyUtils.copy(inputStream, response.getOutputStream());
		   					inputStream.close();
		   					response.getOutputStream().close();
		   					response.getOutputStream().flush();
		   					if (file != null) {
		   						file.delete();
		   					}
		   				}
		   			}
		   	}
		       
		       
		    @RequestMapping(value = { "/indPayrollYTDSummaryReport","/rest/indPayrollYTDSummaryReport" }, method = RequestMethod.POST)
		   	@ResponseBody
		   	public ResponseList<IndEmployeePayrollDTO> indPayrollYTDSummaryReport(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
		   	{
		   		ResponseList<IndEmployeePayrollDTO> responseList = new ResponseList<>();
		   		try {
		   			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		   			if (orgLocations != null) {
		   				String[] tableColumns = {"empId", "empName", "department", "", "", "", "", "", ""};
		   			    int sortColumnIndex = 1;		 //by default sorting using updated date
		   				String sortColumnOrder = "asc";  //by default descending order
		   				if(indEmployeePayrollDTO.getOrder() != null && !indEmployeePayrollDTO.getOrder().isEmpty()) {
		   					sortColumnIndex = Integer.parseInt(indEmployeePayrollDTO.getOrder().get(0).get("column"));
		   					sortColumnOrder = indEmployeePayrollDTO.getOrder().get(0).get("dir");
		   				}
		   				indEmployeePayrollDTO.setSortColumn(tableColumns[sortColumnIndex]);
		   				indEmployeePayrollDTO.setSortOrder(sortColumnOrder);
		   				
		   				ResponseList<IndEmployeePayroll> payResponseList = indPaysheetFrequencyService.getPayrollYTDSummaryReportReportList(indEmployeePayrollDTO,orgLocations);
		   				Map<Long,List<IndEmployeePayroll>> mapList = payResponseList.getData().stream().collect(Collectors.groupingBy(IndEmployeePayroll::getCandidateId,Collectors.toList()));
		   				List<IndEmployeePayrollDTO> indEmployeePayrollList = new ArrayList<>();
		   				for (Map.Entry<Long, List<IndEmployeePayroll>> entry : mapList.entrySet()) {
		   					IndEmployeePayrollDTO dto = new IndEmployeePayrollDTO();
		   					IndEmployeePayroll iepdt = entry.getValue().get(0);
		   					dto.setEmployeeid(iepdt.getEmployee().getEmployeeId());
		   					dto.setEmployeeName(iepdt.getCandidate().getFullName());
		   					dto.setDepartmentName(iepdt.getEmployee().getDepartmentId().getDepartmentName());
		   					double noOfLeaves = entry.getValue().stream().mapToDouble(i->i.getNoOfLeaves() != null ? i.getNoOfLeaves().doubleValue() : 0).sum();
		   					double noOfDays = entry.getValue().stream().mapToDouble(i->i.getNoOfDays() != null ? i.getNoOfDays().doubleValue() : 0).sum();
		   					double grossPay = entry.getValue().stream().mapToDouble(i->i.getGrossPay() != null ? i.getGrossPay().doubleValue() : 0).sum();
		   					double netPay = entry.getValue().stream().mapToDouble(i->i.getNetPay() != null ? i.getNetPay().doubleValue() : 0).sum();
		   	   				dto.setNoOfLeaves(new BigDecimal(noOfLeaves));
		   	   			    dto.setNoOfDays(new BigDecimal(noOfDays));
		   					dto.setGrossPay(new BigDecimal(grossPay));
		   					dto.setNetPay(new BigDecimal(netPay));
		   					dto.setDeducationAmount(dto.getGrossPay().subtract(dto.getNetPay()));
		   					indEmployeePayrollList.add(dto);
		   				}
		   				responseList.setRecordsTotal(indEmployeePayrollList.size());
		   				responseList.setRecordsFiltered(indEmployeePayrollList.size());
		   				responseList.setData(indEmployeePayrollList);
		   				responseList.setResponse(Result.SUCCESS.name());
		   				responseList.setResponseMessage(Result.SUCCESS.getValue());
		   			}else{
		   				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
		   				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		   			}
		   		} catch (Exception e) {
		   			e.printStackTrace();
		   			responseList.setResponse(Result.AWKWARD.name());
		   			responseList.setResponseMessage(Result.AWKWARD.getValue());
		   		}
		   		return responseList;
		   	}
		       
		    
		    @RequestMapping(value = { "/indPayrollYTDSummaryExport"})
		   	public void indPayrollYTDSummaryExport(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
		   	{
		   			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		   			if (orgLocations != null) { 
		   				ResponseList<IndEmployeePayroll> payResponseList = indPaysheetFrequencyService.getPayrollYTDSummaryReportReportList(indEmployeePayrollDTO,orgLocations);
		   				if(payResponseList.getData().size() > 0) {
		   					File file = null;
		   					if(indEmployeePayrollDTO.getExportType().equalsIgnoreCase("pdf")) {
		      					 file = indPaySlipPdf.getIndPayrollYTDPdf(payResponseList,orgLocations);
		   					}else if(indEmployeePayrollDTO.getExportType().equalsIgnoreCase("excel")) {
		      					 file = indPaySlipPdf.getIndPayrollYTDExcel(payResponseList,orgLocations);
		   					}
		   					
		   					
		   					String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		   					if (mimeType == null) {
		   						mimeType = "application/octet-stream";
		   					}
		   					response.setContentType(mimeType);
		   					StringBuilder fileName = new StringBuilder();
		   					response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		   					response.setContentLength((int) file.length());
		   					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		   					FileCopyUtils.copy(inputStream, response.getOutputStream());
		   					inputStream.close();
		   					response.getOutputStream().close();
		   					response.getOutputStream().flush();
		   					if (file != null) {
		   						file.delete();
		   					}
		   				}
		   			}
		   	}
		       
		    
		    @RequestMapping(value = { "/indPayrollAnnualTaxReport","/rest/indPayrollAnnualTaxReport" }, method = RequestMethod.POST)
		   	@ResponseBody
		   	public ResponseList<IndEmployeePayrollDTO> indPayrollAnnualTaxReport(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
		   	{
		   		ResponseList<IndEmployeePayrollDTO> responseList = new ResponseList<>();
		   		try {
		   			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		   			if (orgLocations != null) {
		   				String[] tableColumns = {"empId", "empName", "department", "", "", "", "", "", ""};
		   			    int sortColumnIndex = 1;		 //by default sorting using updated date
		   				String sortColumnOrder = "asc";  //by default descending order
		   				if(indEmployeePayrollDTO.getOrder() != null && !indEmployeePayrollDTO.getOrder().isEmpty()) {
		   					sortColumnIndex = Integer.parseInt(indEmployeePayrollDTO.getOrder().get(0).get("column"));
		   					sortColumnOrder = indEmployeePayrollDTO.getOrder().get(0).get("dir");
		   				}
		   				indEmployeePayrollDTO.setSortColumn(tableColumns[sortColumnIndex]);
		   				indEmployeePayrollDTO.setSortOrder(sortColumnOrder);
		   				
		   				ResponseList<IndEmployeePayroll> payResponseList = indPaysheetFrequencyService.getPayrollAnnualTaxReportReportList(indEmployeePayrollDTO,orgLocations);
		   				Map<Long,List<IndEmployeePayroll>> mapList = payResponseList.getData().stream().collect(Collectors.groupingBy(IndEmployeePayroll::getCandidateId,Collectors.toList()));
		   				List<IndEmployeePayrollDTO> indEmployeePayrollList = new ArrayList<>();
		   				for (Map.Entry<Long, List<IndEmployeePayroll>> entry : mapList.entrySet()) {
		   					IndEmployeePayrollDTO dto = new IndEmployeePayrollDTO();
		   					IndEmployeePayroll iepdt = entry.getValue().get(0);
		   					dto.setEmployeeid(iepdt.getEmployee().getEmployeeId());
		   					dto.setEmployeeName(iepdt.getCandidate().getFullName());
		   					dto.setDepartmentName(iepdt.getEmployee().getDepartmentId().getDepartmentName());
		   					double professionalTax = entry.getValue().stream().mapToDouble(i->i.getProfessionalTax() != null ? i.getProfessionalTax().doubleValue() : 0).sum();
		   					double tds = entry.getValue().stream().mapToDouble(i->i.getTds() != null ? i.getTds().doubleValue() : 0).sum();
		   					double grossPay = entry.getValue().stream().mapToDouble(i->i.getGrossPay() != null ? i.getGrossPay().doubleValue() : 0).sum();
		   					double netPay = entry.getValue().stream().mapToDouble(i->i.getNetPay() != null ? i.getNetPay().doubleValue() : 0).sum();
		   	   				dto.setProfessionalTax(new BigDecimal(professionalTax));
		   	   			    dto.setTds(new BigDecimal(tds));
		   					dto.setGrossPay(new BigDecimal(grossPay));
		   					dto.setNetPay(new BigDecimal(netPay));
		   					dto.setDeducationAmount(dto.getGrossPay().subtract(dto.getNetPay()));
		   					indEmployeePayrollList.add(dto);
		   				}
		   				responseList.setRecordsTotal(indEmployeePayrollList.size());
		   				responseList.setRecordsFiltered(indEmployeePayrollList.size());
		   				responseList.setData(indEmployeePayrollList);
		   				responseList.setResponse(Result.SUCCESS.name());
		   				responseList.setResponseMessage(Result.SUCCESS.getValue());
		   			}else{
		   				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
		   				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		   			}
		   		} catch (Exception e) {
		   			e.printStackTrace();
		   			responseList.setResponse(Result.AWKWARD.name());
		   			responseList.setResponseMessage(Result.AWKWARD.getValue());
		   		}
		   		return responseList;
		   	}
		       
		    @RequestMapping(value = { "/indPayrollAnnualTaxExport"})
		   	public void indPayrollAnnualTaxExport(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
		   	{
		   			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		   			if (orgLocations != null) { 
		   				ResponseList<IndEmployeePayroll> payResponseList = indPaysheetFrequencyService.getPayrollAnnualTaxReportReportList(indEmployeePayrollDTO,orgLocations);
		   				if(payResponseList.getData().size() > 0) {
		   					File file = null;
		   					if(indEmployeePayrollDTO.getExportType().equalsIgnoreCase("pdf")) {
		      					 file = indPaySlipPdf.getIndPayrollAnnualTaxPdf(payResponseList,orgLocations);
		   					}else if(indEmployeePayrollDTO.getExportType().equalsIgnoreCase("excel")) {
		      					 file = indPaySlipPdf.getIndPayrollAnnualTaxExcel(payResponseList,orgLocations);
		   					}
		   					
		   					
		   					String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		   					if (mimeType == null) {
		   						mimeType = "application/octet-stream";
		   					}
		   					response.setContentType(mimeType);
		   					StringBuilder fileName = new StringBuilder();
		   					response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		   					response.setContentLength((int) file.length());
		   					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		   					FileCopyUtils.copy(inputStream, response.getOutputStream());
		   					inputStream.close();
		   					response.getOutputStream().close();
		   					response.getOutputStream().flush();
		   					if (file != null) {
		   						file.delete();
		   					}
		   				}
		   			}
		   	}
		    
		    @RequestMapping(value ="/indPayrollSaleryStatementReport")
			public @ResponseBody List<IndEmployeePayrollDTO> indPayrollSaleryStatementReport(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request)throws Exception{
					Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
			        OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
			    	List<IndEmployeePayrollDTO> salareyStatementReportList = indPaysheetFrequencyService.getPayrollSalaryStatementReportList(indEmployeePayrollDTO, orgLocations);
			    	Map<String, List<IndEmployeePayrollDTO>> map = salareyStatementReportList.stream().collect(Collectors.groupingBy(IndEmployeePayrollDTO :: getPayHeadName,Collectors.toList()));
			    	Map<BigInteger, List<IndEmployeePayrollDTO>> canMap = salareyStatementReportList.stream().collect(Collectors.groupingBy(IndEmployeePayrollDTO::getCanId,Collectors.toList()));
			    	List<IndEmployeePayrollDTO> mp = new ArrayList<>();
			    	 for (Map.Entry<BigInteger, List<IndEmployeePayrollDTO>> entry : canMap.entrySet())  {
			    		 IndEmployeePayrollDTO dty = new IndEmployeePayrollDTO();
			    		 Map<String,String> innerMap = new HashMap<>();
			    		 IndEmployeePayrollDTO dto = entry.getValue().get(0);
			    		 if(dto.getLastName() != null) {
			    			 dty.setEmployeeName(dto.getFirstName() +" "+ dto.getLastName());
				    	  }else {
				    		 dty.setEmployeeName(dto.getFirstName());
				          }
						  dty.setEmployeeid(dto.getEmployeeid());
						  dty.setDepartmentName(dto.getDepartmentName());
						  dty.setGrossPay(dto.getGrossPay());
						  dty.setDeducations(dto.getGrossPay().subtract(dto.getNetPay()));
						  dty.setNetPay(dto.getNetPay());
						  
						  dty.setStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), dto.getDeteOfJoin()));
						  for (Map.Entry<String, List<IndEmployeePayrollDTO>> canEntry : map.entrySet())  {
								  List<IndEmployeePayrollDTO> s = entry.getValue().stream().filter(pd->canEntry.getKey().equalsIgnoreCase(pd.getPayHeadName())).collect(Collectors.toList());
								  if(s.size() > 0) {
									  innerMap.put(canEntry.getKey(), String.valueOf(s.get(0).getPayHeadAmount()));
								  }else {
									  innerMap.put(canEntry.getKey(), String.valueOf(0.00));
								  }
							  }
						  dty.setPayHeadMapList(innerMap);
						  mp.add(dty);
			    	 }
			    	return mp;
		    	
		    } 
		    
		    @RequestMapping(value ="/indPayrollESIAndPFReport", method = RequestMethod.POST)
		  	public @ResponseBody ResponseList<IndEmployeePayrollDTO> indPayrollESIAndPFReport(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request)throws Exception{
		    	ResponseList<IndEmployeePayrollDTO> responseList = new ResponseList<>();	
		    	try {
		  	  			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		  	   			if (orgLocations != null) {
		  	   				String[] tableColumns = {"empId", "empName", "", "", "", "", "", "", ""};
		  	   			    int sortColumnIndex = 1;		 //by default sorting using updated date
		  	   				String sortColumnOrder = "asc";  //by default descending order
		  	   				if(indEmployeePayrollDTO.getOrder() != null && !indEmployeePayrollDTO.getOrder().isEmpty()) {
		  	   					sortColumnIndex = Integer.parseInt(indEmployeePayrollDTO.getOrder().get(0).get("column"));
		  	   					sortColumnOrder = indEmployeePayrollDTO.getOrder().get(0).get("dir");
		  	   				}
		  	   				indEmployeePayrollDTO.setSortColumn(tableColumns[sortColumnIndex]);
		  	   				indEmployeePayrollDTO.setSortOrder(sortColumnOrder);
		  	   				

		  	   			ResponseList<IndEmployeePayrollDTO> pfESIReportList = indPaysheetFrequencyService.getPayrollESIAndPFReportList(indEmployeePayrollDTO, orgLocations);
		  	  	    	Map<BigInteger, List<IndEmployeePayrollDTO>> canMap = pfESIReportList.getData().stream().collect(Collectors.groupingBy(IndEmployeePayrollDTO::getCanId,Collectors.toList()));
		  	  	    	List<IndEmployeePayrollDTO> list = new ArrayList<>();
		  	  	    	
		  	  	    	for (Map.Entry<BigInteger, List<IndEmployeePayrollDTO>> entry : canMap.entrySet())  {
		  	  	    		 IndEmployeePayrollDTO dty = new IndEmployeePayrollDTO();
		  	  	    		 IndEmployeePayrollDTO dto = entry.getValue().get(0);
		  	  	    		 if(dto.getLastName() != null) {
		  	  	    			 dty.setEmployeeName(dto.getFirstName() +" "+ dto.getLastName());
		  	  		    	  }else {
		  	  		    		 dty.setEmployeeName(dto.getFirstName());
		  	  		          }
		  	  				  dty.setEmployeeid(dto.getEmployeeid());
		  	  				  dty.setEsiNumber(dto.getEsiNumber());
		  	  				  dty.setDepartmentName(dto.getDepartmentName());
		  	  				  dty.setPfNumber(dto.getPfNumber());
		  	  				  dty.setUanNumber(dto.getUanNumber());
		  	  				  if(dto.getEligibleForEsi() != null && dto.getEligibleForEsi().equalsIgnoreCase("Y")) {
		  	  				  Double employeeESI = 0d;
		  	  				  Double employerESI = 0d;
		  	  				  for(IndEmployeePayrollDTO  ipd :entry.getValue()) {
		  	  					  if(ipd.getEmployeeEsi() != null && ipd.getPayHeadAmount().longValue() > 0) {
		  	  						  employeeESI = employeeESI + ((ipd.getPayHeadAmount().doubleValue() * ipd.getEmployeeEsi())/100);
		  	  					  }
		  	  					  if(ipd.getEmployerEsi() != null && ipd.getPayHeadAmount().longValue() > 0) {
		  	  						  employerESI = employerESI + ((ipd.getPayHeadAmount().doubleValue() * ipd.getEmployerEsi())/100);
		  	  					  }
		  	  				  }
		  	  				  dty.setEmployeeEsi(employeeESI);
		  	  				  dty.setEmployerEsi(employerESI);
		  	  				  dty.setTotalESI(employeeESI+employerESI);
		  	  				  }
		  	  				
		  	  				  if(dto.getEligibleForPf() != null && dto.getEligibleForPf().equalsIgnoreCase("Y")) {
		  	  				  Double employeePF = 0d;
		  	  				  Double employerPF = 0d;
		  	  				  for(IndEmployeePayrollDTO  ipd :entry.getValue()) {
		  	  					  if(ipd.getEmployeePf() != null && ipd.getPayHeadAmount().longValue() > 0) {
		  	  						  employeePF = employeePF + ((ipd.getPayHeadAmount().doubleValue() * ipd.getEmployeePf())/100);
		  	  					  }
		  	  					  if(ipd.getEmployerPf() != null && ipd.getPayHeadAmount().longValue() > 0) {
		  	  						  employerPF = employerPF + ((ipd.getPayHeadAmount().doubleValue() * ipd.getEmployerPf())/100);
		  	  					  }
		  	  				  }
		  	  				  dty.setEmployeePf(employeePF);
		  	  				  dty.setEmployerPf(employerPF);
		  	  				  dty.setTotalPF(employeePF+employerPF);
		  	  				  }
		  	  				  
		  	  				  
		  	  				  list.add(dty);
		  	  	    	    }
		  	  	    	
		  	   			responseList.setRecordsTotal(list.size());
		   				responseList.setRecordsFiltered(list.size());
		   				responseList.setData(list);
		   				responseList.setResponse(Result.SUCCESS.name());
		   				responseList.setResponseMessage(Result.SUCCESS.getValue());
		   			}else{
		   				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
		   				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		   			}
		   		} catch (Exception e) {
		   			e.printStackTrace();
		   			responseList.setResponse(Result.AWKWARD.name());
		   			responseList.setResponseMessage(Result.AWKWARD.getValue());
		   		}
			return responseList;
		    }
		    
		    
		    @RequestMapping(value = { "/indPayrollESIExport"})
		   	public void indPayrollESIExport(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
		   	{
		   			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		   			if (orgLocations != null) { 
		   				ResponseList<IndEmployeePayrollDTO> payResponseList = indPaysheetFrequencyService.getPayrollESIAndPFReportList(indEmployeePayrollDTO,orgLocations);
		   				if(payResponseList.getData().size() > 0) {
		   					File file = null;
		   					if(indEmployeePayrollDTO.getExportType().equalsIgnoreCase("pdf")) {
		      					 file = indPaySlipPdf.getIndPayrollESIPdf(payResponseList,orgLocations);
		   					}else if(indEmployeePayrollDTO.getExportType().equalsIgnoreCase("excel")) {
		      					 file = indPaySlipPdf.getIndPayrollESIExcel(payResponseList,orgLocations);
		   					}
		   					String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		   					if (mimeType == null) {
		   						mimeType = "application/octet-stream";
		   					}
		   					response.setContentType(mimeType);
		   					StringBuilder fileName = new StringBuilder();
		   					response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		   					response.setContentLength((int) file.length());
		   					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		   					FileCopyUtils.copy(inputStream, response.getOutputStream());
		   					inputStream.close();
		   					response.getOutputStream().close();
		   					response.getOutputStream().flush();
		   					if (file != null) {
		   						file.delete();
		   					}
		   				}
		   			}
		   	}
		    
		    
		    @RequestMapping(value = { "/indPayrollPFExport"})
		   	public void indPayrollPFExport(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
		   	{
		   			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		   			if (orgLocations != null) { 
		   				ResponseList<IndEmployeePayrollDTO> payResponseList = indPaysheetFrequencyService.getPayrollESIAndPFReportList(indEmployeePayrollDTO,orgLocations);
		   				if(payResponseList != null) {
		   					File file = null;
		   					if(indEmployeePayrollDTO.getExportType().equalsIgnoreCase("pdf")) {
		      					 file = indPaySlipPdf.getIndPayrollPFPdf(payResponseList,orgLocations);
		   					}else if(indEmployeePayrollDTO.getExportType().equalsIgnoreCase("excel")) {
		      					 file = indPaySlipPdf.getIndPayrollPFExcel(payResponseList,orgLocations);
		   					}
		   					String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		   					if (mimeType == null) {
		   						mimeType = "application/octet-stream";
		   					}
		   					response.setContentType(mimeType);
		   					StringBuilder fileName = new StringBuilder();
		   					response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		   					response.setContentLength((int) file.length());
		   					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		   					FileCopyUtils.copy(inputStream, response.getOutputStream());
		   					inputStream.close();
		   					response.getOutputStream().close();
		   					response.getOutputStream().flush();
		   					if (file != null) {
		   						file.delete();
		   					}
		   				}
		   			}
		   	}
		    
		 
		    @RequestMapping(value = { "/indPayrollSalaryRegisterReport","/rest/indPayrollSalaryRegisterReport" }, method = RequestMethod.POST)
		   	@ResponseBody
		   	public ResponseList<IndEmployeePayrollDTO> indPayrollSalaryRegisterReport(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
		   	{
		   		ResponseList<IndEmployeePayrollDTO> responseList = new ResponseList<>();
		   		try {
		   			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		   			if (orgLocations != null) {
		   				String[] tableColumns = {"empId", "empName", "department", "", "", "", "", "", ""};
		   			    int sortColumnIndex = 1;		 //by default sorting using updated date
		   				String sortColumnOrder = "asc";  //by default descending order
		   				if(indEmployeePayrollDTO.getOrder() != null && !indEmployeePayrollDTO.getOrder().isEmpty()) {
		   					sortColumnIndex = Integer.parseInt(indEmployeePayrollDTO.getOrder().get(0).get("column"));
		   					sortColumnOrder = indEmployeePayrollDTO.getOrder().get(0).get("dir");
		   				}
		   				indEmployeePayrollDTO.setSortColumn(tableColumns[sortColumnIndex]);
		   				indEmployeePayrollDTO.setSortOrder(sortColumnOrder);
		   				
		   				ResponseList<IndEmployeePayrollDTO> payResponseList = indPaysheetFrequencyService.getPayrollSalaryRegisterReportReportList(indEmployeePayrollDTO,orgLocations);
			   			List<IndEmployeePayrollDTO> salaryRegisterList = new ArrayList<>();
			   				for(IndEmployeePayrollDTO asi:payResponseList.getData()) {
			   					IndEmployeePayrollDTO iepd = new IndEmployeePayrollDTO();
			   					iepd.setEmployeeid(asi.getEmployeeid());
			   					if(asi.getLastName() != null && asi.getLastName() !="" && asi.getLastName() !="undefined") {
			   						iepd.setEmployeeName(asi.getFirstName()+" "+asi.getLastName());
			   					}else {
			   						iepd.setEmployeeName(asi.getFirstName());
			   					}
			   					iepd.setDepartmentName(asi.getDepartmentName());
			   					iepd.setEffectiveDate(asi.getEffectiveDate());
			   					iepd.setIncresePercentage(asi.getIncresePercentage());
			   					iepd.setAnnualSalary(asi.getAnnualSalary());
			   				    salaryRegisterList.add(iepd);
			   			  }
		   				responseList.setRecordsTotal(salaryRegisterList.size());
		   				responseList.setRecordsFiltered(salaryRegisterList.size());
		   				responseList.setData(salaryRegisterList);
		   				responseList.setResponse(Result.SUCCESS.name());
		   				responseList.setResponseMessage(Result.SUCCESS.getValue());
		   			}else{
		   				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
		   				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		   			}
		   		} catch (Exception e) {
		   			e.printStackTrace();
		   			responseList.setResponse(Result.AWKWARD.name());
		   			responseList.setResponseMessage(Result.AWKWARD.getValue());
		   		}
		   		return responseList;
		   	}
		    
		    
		    
		    @RequestMapping(value = { "/indPayrollSalaryRegisterExport"})
		   	public void indPayrollSalaryRegisterExport(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
		   	{
		   			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		   			if (orgLocations != null) { 
		   				ResponseList<IndEmployeePayrollDTO> payResponseList = indPaysheetFrequencyService.getPayrollSalaryRegisterReportReportList(indEmployeePayrollDTO,orgLocations);
		   				if(payResponseList.getData().size() > 0) {
		   					File file = null;
		   					if(indEmployeePayrollDTO.getExportType().equalsIgnoreCase("pdf")) {
		      					 file = indPaySlipPdf.getIndPayrollSalaryRegisterPdf(payResponseList,orgLocations);
		   					}else if(indEmployeePayrollDTO.getExportType().equalsIgnoreCase("excel")) {
		      					 file = indPaySlipPdf.getIndPayrollSalaryRegisterExcel(payResponseList,orgLocations);
		   					}
		   					
		   					
		   					String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		   					if (mimeType == null) {
		   						mimeType = "application/octet-stream";
		   					}
		   					response.setContentType(mimeType);
		   					StringBuilder fileName = new StringBuilder();
		   					response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		   					response.setContentLength((int) file.length());
		   					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		   					FileCopyUtils.copy(inputStream, response.getOutputStream());
		   					inputStream.close();
		   					response.getOutputStream().close();
		   					response.getOutputStream().flush();
		   					if (file != null) {
		   						file.delete();
		   					}
		   				}
		   			}
		   	}
		    
		    
		    
		    @RequestMapping(value = "/getFromMonthNames", method = RequestMethod.POST)
		   	public @ResponseBody List<String> getMonthNames(@RequestParam(value="financialYear", required=false) String financialYear , HttpServletRequest request, HttpServletResponse response) throws Exception
		   	{
		        Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		        OrgLocations orgLocation = orgLocationService.getOrgLocations(orgLocationId);
			     List<Date> finYearList = iNDPayrollCommonUtil.getFinancialYearStartAndEndDateByOrgLocations(orgLocation, financialYear);
			     java.sql.Date freqStartDate = new java.sql.Date(finYearList.get(0).getTime());
			     java.sql.Date freqEndDate = new java.sql.Date(finYearList.get(1).getTime());
			    	String date1 = freqStartDate.toString();
			        String date2 = freqEndDate.toString();
			        

		        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.ROOT);
		        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM-uuuu", Locale.ROOT);
		        YearMonth endMonth = YearMonth.parse(date2, dateFormatter);
		        List<String> months = new ArrayList<>();
		        for (YearMonth month = YearMonth.parse(date1, dateFormatter);
		                ! month.isAfter(endMonth);
		                month = month.plusMonths(1)) {
		        	months.add(month.format(monthFormatter));
		        }
				return months;
		   	}
		    
		    
		    @RequestMapping(value = "/getToMonthNames", method = RequestMethod.POST)
		   	public @ResponseBody List<String> getToMonthNames(@RequestParam(value="financialYear", required=false) String financialYear,
		   			@RequestParam(value="fromMonth", required=false) String fromMonth,HttpServletRequest request, HttpServletResponse response) throws Exception
		   	{
		    	Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		        OrgLocations orgLocation = orgLocationService.getOrgLocations(orgLocationId);
		        
		        String[] selectMonth = fromMonth.split("-");
		        //Start Year
				 Date startDate = new SimpleDateFormat("MMMM").parse(selectMonth[0]);
				 Calendar cal = Calendar.getInstance();
				 cal.setTime(startDate);
				 cal.set(Calendar.YEAR, Integer.valueOf(selectMonth[1]));
				 Date sDate = cal.getTime();
				 java.sql.Date freqStartDate = new java.sql.Date(sDate.getTime());
				 
				 //End Year
				 Date eeDate = null;
				 Date endDate = new SimpleDateFormat("MMMM").parse(orgLocation.getStartMonth());
				 Calendar endCal = Calendar.getInstance();
				 endCal.setTime(endDate);
				 if(financialYear.contains("-")) {
					 String[] finYears  = financialYear.split("-");
					 endCal.set(Calendar.YEAR, Integer.valueOf(finYears[1]));
					 endCal.add(Calendar.MONTH, -1);  
					 endCal.set(Calendar.DAY_OF_MONTH, 1);  
					 endCal.add(Calendar.DATE, 1);  
				      eeDate = endCal.getTime();
				 }else {
					 endCal.set(Calendar.YEAR, Integer.valueOf(financialYear));
					 endCal.set(Calendar.MONTH, 11);
					 endCal.set(Calendar.DAY_OF_MONTH, 31);
					 eeDate = endCal.getTime();
				 }
				 
			     java.sql.Date freqEndDate = new java.sql.Date(eeDate.getTime());
			     
		    	String date1 = freqStartDate.toString();
		        String date2 = freqEndDate.toString();

		        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.ROOT);
		        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM-uuuu", Locale.ROOT);
		        YearMonth endMonth = YearMonth.parse(date2, dateFormatter);
		        List<String> months = new ArrayList<>();
		        for (YearMonth month = YearMonth.parse(date1, dateFormatter);
		                ! month.isAfter(endMonth);
		                month = month.plusMonths(1)) {
		        	months.add(month.format(monthFormatter));
		        }
				return months;
		   	}
		    
	    @RequestMapping(value = { "/getIndPayGroupList","/rest/getIndPayGroupList" }, method = RequestMethod.GET)
		@ResponseBody
		public List<UserGroup> findAllDeduction(HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			return userGroupService.getAllUserGroupsByLocationId(orgLocation);
		}
	    
	    

	    @RequestMapping(value = { "/getIndPayPeriodByGroupId","/rest/getIndPayPeriodByGroupId" }, method = RequestMethod.GET)
		@ResponseBody
		public List<IndPaysheetFrequency> getIndPayPeriodByGroupId(@RequestParam(value = "groupId",required = false) Long groupId,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
		    OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<IndPaysheetFrequency> PayPeriodDTOList = userGroupService.getIndPayPeriodsByGroupId(groupId,orgLocation);
			return PayPeriodDTOList;
		}
	    
		@RequestMapping(value = { "/indEmployeesListBasedOnDepartment","/rest/indEmployeesListBasedOnDepartment" }, method = RequestMethod.GET)
		@ResponseBody
		public List<Employees> getEmployeesBasedOnDepartment(@RequestParam(value="departmentId", required=true) String departmentId,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<Employees> departmentList = employeesService.getIndEmployeesBasedOnDepartment(departmentId,orgLocation);
			for (Employees emp : departmentList) {
				emp.setEmpId(emp.getId());
				emp.setFullName(emp.getUser().getFullName());
			}
			return departmentList;
		}
	    
}
