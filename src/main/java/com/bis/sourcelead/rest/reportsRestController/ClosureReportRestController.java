package com.bis.sourcelead.rest.reportsRestController;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dto.ClosureReportDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.InvoicePayment;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.indPayroll.service.IndPaysheetFrequencyService;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.service.PaysheetFrequencyService;
import com.bis.sourcelead.service.AppliedLeaveDateService;
import com.bis.sourcelead.service.InvoicePaymentService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.TimesheetApprovalFrequencyGeneratorUtil;

@RestController
@RequestMapping("/app")
public class ClosureReportRestController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	TimesheetApprovalFrequencyGeneratorUtil timesheetApprovalFrequencyGeneratorUtil;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	ProjectMembersHistoryService projectMembersHistoryService;
	
	@Autowired
	AppliedLeaveDateService appliedLeaveDateService;
	
	@Autowired
	InvoicePaymentService invoicePaymentService;
	
	@Autowired
	IndPaysheetFrequencyService indPaysheetFrequencyService;
	
	@Autowired
	PaysheetFrequencyService paysheetFrequencyService;

	@RequestMapping(value = "/closureReport")
	public ModelAndView timeSheetReports(HttpServletRequest request) throws Exception {
		 ModelAndView model = new ModelAndView();
		 model.setViewName("reports/closureReport");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 
		 String[] monthName = {"January", "February",
	                "March", "April", "May", "June", "July",
	                "August", "September", "October", "November",
	                "December"};

	        Calendar cal = Calendar.getInstance();
	        String month = monthName[cal.get(Calendar.MONTH)];
	        model.addObject("currentMonth", month);
	        
		 return model;

	}
	
	
	@RequestMapping(value = "/getClosureReportDetails", method = RequestMethod.POST ) 
	public ClosureReportDTO getClosureReportDetails(ClosureReportDTO closureReportDTO,HttpServletRequest request) throws Exception {
		try {
			  OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			  User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			  Map<String,Date> dateList =  CalendarUtil.getMonthStartDateAndEndDateFromGivenMonthStringForTsReports(closureReportDTO.getFrequencyMonth(),closureReportDTO.getFrequencyYear());
			  Date startDate = dateList.get("STARTDATE");
			  Date endDate = dateList.get("ENDDATE");
			  
				LinkedHashMap<Date, Date> displayWeeksList = new LinkedHashMap<>();
				Map<String,String> frequencyDropDown =  timesheetApprovalFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),startDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),endDate),orgLocations.getDateFormat());
				if(frequencyDropDown != null && !frequencyDropDown.isEmpty()){
					for (Map.Entry<String,String> entry : frequencyDropDown.entrySet()) {
			    		 String  k = entry.getKey();
			    		 String  v = entry.getValue();
					        Date weekStartDate;
					        Date weekEndDate;
						try {
							 weekStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),k.replaceAll("/", "-"));
							 weekEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),v.replaceAll("/", "-"));
							 displayWeeksList.put(weekStartDate, weekEndDate);
		   					
						} catch (Exception e) {}
					}
				}

			  ResponseList<ProjectMembers> pmList = projectMembersService.getProjectClosureReport(startDate, endDate, orgLocations.getId(), user.getId());
			  List<ProjectMembers> projectMembersList = pmList.getData();
			  float employeeMonthBillableTotalHours    = 0f;
			  float contractorsMonthBillableTotalHours    = 0f;
			  float totalBillableHours = 0f;
			  Set<Long> employeesSet = new HashSet<>();
			  Set<Long> contractEmployeeSet = new HashSet<>();
			  
			  for (ProjectMembers projectMember : projectMembersList)	{
				  
				    User pmUser = null;
				     ProjectMembers pm =  projectMembersService.getConsultantUserProjectMemberByProjectMemberId(projectMember.getId());
					 if(pm != null) {
						 pmUser = pm.getProjectMemberCandidteId().getConsultantUser();
					 }
					 
					 Long workLocationId = 0l;
					 if(pm.getProjectMemberCandidteId() != null && pm.getProjectMemberCandidteId().getWorkLocation() != null && pm.getProjectMemberCandidteId().getWorkLocation().getId() != null) {
						workLocationId = pm.getProjectMemberCandidteId().getWorkLocation().getId();
					 }
				  
				    SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(user.getId(),orgLocations.getId(),projectMember.getId(),startDate,endDate,null);
				    
					 
					  if(displayWeeksList != null && !displayWeeksList.isEmpty()){
					    	 for (Map.Entry<Date,Date> entry : displayWeeksList.entrySet()) { 
					    	    	
					    		   float monthTotalHours = 0f;
					    	    	
					    		   Float leaveBillableTotal = 0f;
						    	    if(projectMember.getProjectMemberCandidteId().getConsultantUser() != null) {
						    	    	 Map<String,Float> leaveTotalMap = appliedLeaveDateService.getAppliedLeaveDateAndHolidayHoursByUserId(pmUser.getId(), entry.getKey(), entry.getValue(),workLocationId);
							    	     if(leaveTotalMap != null && !leaveTotalMap.isEmpty()) {
							    	    	 leaveBillableTotal = leaveTotalMap.get("BILLABLE");
							    	     }
							    	     
						    	    }
							    	 
					    	    	Float  weekBillableHours = 0f;
					    	    	if(sah.getWeekBillableHoursMap().get(entry.getKey()) != null){
					    	    		weekBillableHours = sah.getWeekBillableHoursMap().get(entry.getKey());
					    	    		if(leaveBillableTotal != null) {
					    	    			weekBillableHours = CommonUtil.getCombinedHours(weekBillableHours, leaveBillableTotal);
					    	    		}
					    	    	}
					    	    	
					    	    	Float  weekTotalHours = 0f;
					    	    	if(sah.getWeekTotalHoursMap().get(entry.getKey()) != null){
					    	    		 weekTotalHours = sah.getWeekTotalHoursMap().get(entry.getKey());
					    	    		 if(leaveBillableTotal != null) {
					    	    			 weekTotalHours = CommonUtil.getCombinedHours(weekTotalHours, leaveBillableTotal);
						    	    	 }
					    	    	}
					    	       
					    	        monthTotalHours = CommonUtil.getCombinedHours(monthTotalHours, weekTotalHours);
					    	        
					    	        if(pm.getProjectMemberCandidteId() != null && pm.getProjectMemberCandidteId().getDesignationType() != null 
					    	        		&&  (pm.getProjectMemberCandidteId().getDesignationType().equalsIgnoreCase("Full Time") || pm.getProjectMemberCandidteId().getDesignationType().equalsIgnoreCase("Offshore C2C"))) {
					    	        	employeesSet.add(pm.getProjectMemberCandidteId().getId());
					    	        	employeeMonthBillableTotalHours = CommonUtil.getCombinedHours(employeeMonthBillableTotalHours, monthTotalHours);
					    	        }else {
					    	        	contractEmployeeSet.add(pm.getProjectMemberCandidteId().getId());
					    	        	contractorsMonthBillableTotalHours = CommonUtil.getCombinedHours(contractorsMonthBillableTotalHours, monthTotalHours);
					    	        }
					    	       
					    	      
					    	     }
					       }
					    
					
			  }
			  
			  totalBillableHours = CommonUtil.getCombinedHours(employeeMonthBillableTotalHours, contractorsMonthBillableTotalHours);
			  closureReportDTO.setEmployeeBillableHours(employeeMonthBillableTotalHours);
			  closureReportDTO.setContractorsBillableHours(contractorsMonthBillableTotalHours);
			  closureReportDTO.setTotalBillableHours(totalBillableHours);
			  closureReportDTO.setEmployeeCount(employeesSet.size());
			  closureReportDTO.setContractorsCount(contractEmployeeSet.size());
			  
			  Float revenueTotalAmount = 0f;
			  List<InvoicePayment> invoicePaymentList =  invoicePaymentService.getInvoicePaymentListByOrgloactionIdAndDates(orgLocations.getId(), startDate, endDate);
			  if(invoicePaymentList != null && !invoicePaymentList.isEmpty()) {
				  for(InvoicePayment invoicePayment : invoicePaymentList) {
					  revenueTotalAmount = revenueTotalAmount + Float.parseFloat(invoicePayment.getPaymentAmount());
				  }
				  
			  }
			  closureReportDTO.setRevenue(String.format("%.2f", revenueTotalAmount)); 
			  
			  Float totalEmployeeSalary = 0f;
			  Float totalContractorsSalary = 0f;
			  
			  if(StringUtils.isNotBlank(orgLocations.getCountry()) && orgLocations.getCountry().equalsIgnoreCase("India")) {
				  List<IndEmployeePayroll> indPayRollList =  indPaysheetFrequencyService.getEmployeePayRollListByFrequencyStartDateAndEndDateAndOrgLocationId(startDate, endDate, orgLocations.getId());
				  if(indPayRollList != null & !indPayRollList.isEmpty()) {
					  for(IndEmployeePayroll indEmpPayroll : indPayRollList) {
						  if(StringUtils.isNotBlank(indEmpPayroll.getCandidate().getDesignationType()) && 
								  (indEmpPayroll.getCandidate().getDesignationType().equalsIgnoreCase("Full Time") || indEmpPayroll.getCandidate().getDesignationType().equalsIgnoreCase("Offshore C2C"))) {
							  totalEmployeeSalary = totalEmployeeSalary+indEmpPayroll.getGrossPay().floatValue();
						  }else {
							  totalContractorsSalary = totalContractorsSalary+indEmpPayroll.getGrossPay().floatValue();
						  }
					  }
				  }
			  
			  }else if(StringUtils.isNotBlank(orgLocations.getCountry()) && orgLocations.getCountry().equalsIgnoreCase("United States")) {
				  List<EmployeePayroll> usPayrollList = paysheetFrequencyService.getUSEmployeePayRollListByFrequencyStartDateAndEndDateAndOrgLocationId(startDate, endDate, orgLocations.getId());
				  if(usPayrollList != null && !usPayrollList.isEmpty()) {
					  for(EmployeePayroll usEmpPayroll : usPayrollList) {
						  if(StringUtils.isNotBlank(usEmpPayroll.getCandidate().getDesignationType()) && 
								  (usEmpPayroll.getCandidate().getDesignationType().equalsIgnoreCase("Full Time") || usEmpPayroll.getCandidate().getDesignationType().equalsIgnoreCase("Offshore C2C"))) {
							  totalEmployeeSalary = totalEmployeeSalary+usEmpPayroll.getGrossPay().floatValue();
						  }else {
							  totalContractorsSalary = totalContractorsSalary+usEmpPayroll.getGrossPay().floatValue();
						  }
					  }
				  }
			  }
			  
			  closureReportDTO.setEmployeeSalary(String.format("%.2f", totalEmployeeSalary)); 
			  closureReportDTO.setContractorsPayment(String.format("%.2f", totalContractorsSalary));
			  
			  Float totalSalary = totalEmployeeSalary+totalContractorsSalary;
			  Float grossMarginAmount = revenueTotalAmount - totalSalary;
			  closureReportDTO.setGrossAmount(String.format("%.2f", grossMarginAmount));
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return closureReportDTO;
		
	}

}
