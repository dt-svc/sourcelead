package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.ExpensesEntryDocuments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.AddExpensesService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.ExpensesApprovalService;
import com.bis.sourcelead.service.ExpensesCategoryService;
import com.bis.sourcelead.service.ExpensesEntryDocumentsService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.PdfConversionUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class addExpenseController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private AddExpensesService addExpensesService;
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	ExpensesCategoryService expensesCategoryService;
	
	@Autowired
	ProjectsService projectsService;
	
	@Autowired
	ExpensesService expensesService;
	
	@Autowired
	ExpensesApprovalService expensesApprovalService;
	
	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private ExpensesEntryDocumentsService expensesEntryDocumentsService;
	
	@Autowired
	private PdfConversionUtil pdfConversionUtil;
	
	@RequestMapping(value = "/acceptOrRejectReopenExpense")
	@ResponseBody  ModelAndView acceptOrRejectReopenExpense(
			@RequestParam(value="type", required=false) String type,
			@RequestParam(value="expenseId", required=false) String expenseId,
			@RequestParam(value="reOpenedUserId", required=false) String reOpenedUserId,
			@RequestParam(value="reason", required=false) String reason,HttpServletRequest request) throws Exception {
		
		
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	    OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User reopenedUser = userService.getUserById(Long.parseLong(reOpenedUserId));
	    
	    Long expenseID=Long.parseLong(expenseId);
		List<ExpensesApproverHierarchy> ls = expensesApprovalService.getExpensesApproverHierarchyListByExpenseId(expenseID);
		String isSubmittedForApproval="";
		for(ExpensesApproverHierarchy expensesApproverHierarchy:ls){
			isSubmittedForApproval=expensesApproverHierarchy.getExpenseApprovalStatus().getValue();
				
		}
	    
	    
		if(expenseId != null && null != type && !type.equalsIgnoreCase("rejecting")){
			expensesApprovalService.updateExpenseStatusByExpenseIdAndReOpenReason(Long.parseLong(expenseId),reason,loginUser,type,Constants.YES);
		  	
		}/*else if(expenseId != null &&  null != type && "reject".equalsIgnoreCase(type) && null != reason && !"".equalsIgnoreCase(reason)){
			expensesApprovalService.updateExpenseStatusByExpenseIdAndReOpenReason(Long.parseLong(expenseId),reason,loginUser,type,Constants.YES);
		}*/
		
		User superUser = userService.getUserById(Long.parseLong(reOpenedUserId));
		ExpensesApproverHierarchy sah = expensesApprovalService.getExpensesApproverHierarchyByApproverUserIdAndExpenseId(loginUser.getId(), Long.parseLong(expenseId));
		Expenses expense = expensesService.getExpenseByExpenseId(Long.parseLong(expenseId));
		if(StringUtils.isNotEmpty(expenseId) && null != type && !type.equalsIgnoreCase("rejecting")){
			expensesApprovalService.sendReopenExpenseAcceptOrRejectMail (expense,reason,superUser,orgLoc,type,reopenedUser, sah.getExpenseId().getProjectMembers().getProjects().getProjectName());
		}
		
		ModelAndView model = new ModelAndView();
		model.setViewName("expenses/reopenExpenseAcceptOrReject");
		model.addObject("type",type);
		model.addObject("expenseId",expenseId);
		model.addObject("reOpenedUserId",reOpenedUserId);
		model.addObject("reason",reason);
		model.addObject("isSubmittedForApproval",isSubmittedForApproval);
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	 @RequestMapping(value = "/viewExpensesEntryDocument/{title}", method = RequestMethod.GET)
	    public void viewExpensesEntryDocument(@RequestParam(value="entryDocumentId", required=false) Long entryDocumentId,
	    		@RequestParam(value="title", required=false) String title
	    		,HttpServletResponse response) throws Exception {

		 ExpensesEntryDocuments	expensesEntryDocument =	expensesEntryDocumentsService.getExpensesEntryDocumentById(entryDocumentId);	
			    
		 
		 byte[] pdfBytes = null;
			if("application/pdf".equalsIgnoreCase(expensesEntryDocument.getFileContentType())){
				pdfBytes = expensesEntryDocument.getDocFile();
			}
			else{
				 pdfBytes = pdfConversionUtil.convertDocToPdf(expensesEntryDocument.getDocFile());
			}
			 response.setHeader("Content-disposition","inline; filename=contractPdf.pdf");
		  	    response.setContentType("application/pdf");
		  	    ServletOutputStream sos = response.getOutputStream();
		  	    if(null != pdfBytes){
		  	    	response.setContentLength(pdfBytes.length);
		  	    	sos.write(pdfBytes);
		  	    }
		          sos.flush();
		          sos.close();

		} 
	 
	
}
