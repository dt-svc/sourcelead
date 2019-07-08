package com.bis.sourcelead.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.mockito.internal.stubbing.answers.ThrowsException;
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

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadTaxFormDTO;
import com.bis.sourcelead.dto.SourceleadTaxITDeclarationSubmissionDTO;
import com.bis.sourcelead.dto.helper.Paginator;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadTaxITDeclarationSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.TaxService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class TaxSubmissionController {
	private static final Logger logger = LoggerFactory.getLogger(TaxSubmissionController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Value("${static.path}")
	private String staticPath;

	@Autowired
	private TaxService taxService;

	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	private CommonUtil commonUtil;

	// forms tab org/user

	@RequestMapping(value = "/forms/list")
	@ResponseBody
	public ModelAndView getForms(HttpServletRequest request, @RequestParam(defaultValue = "") String financialYear,
			@RequestParam(defaultValue = "") String uuid, Paginator paginator) {
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			commonUtil.highLightMenu(Constants.FORMS, request);
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			if (user != null) {
				if (!uuid.isEmpty()) {
					modelAndView.addObject("financialYear", financialYear);
					modelAndView.addObject("uuid", uuid);
					SourceleadTaxFormDTO sourceleadTaxFormDTO = taxService.getTaxFormType(uuid, false, false);
					if (sourceleadTaxFormDTO != null) {
						modelAndView.addObject("taxForm", sourceleadTaxFormDTO);
	//					modelAndView.addObject("formList", taxService.getITDeclarationSubmissionList(uuid, financialYear, paginator));
	//					modelAndView.addObject("paginator",paginator);
						modelAndView.setViewName("payroll_india/org/form_list_by_formType");
					}
				} else {
					TaxAdminController.setYears(modelAndView, financialYear);
					String year = "";
					if (financialYear.isEmpty()) {
						Calendar c = Calendar.getInstance();
						Calendar c1 = Calendar.getInstance();
						c1.set(Calendar.YEAR, c.get(Calendar.YEAR));
						c1.roll(Calendar.YEAR, true);
						year = c.get(Calendar.YEAR) + "-" + c1.get(Calendar.YEAR);
	
					} else {
						year = financialYear;
					}
					modelAndView.addObject("formList", taxService.getTaxFormTypeListForSubmission(year, orgLocations.getId()));
					modelAndView.setViewName("payroll_india/org/forms_list");
				}
			} else {
				modelAndView.setViewName("login");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			modelAndView.setViewName("errorPage");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/forms/create")
	public ModelAndView createForm(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView.addObject("formList", taxService.getTaxFormTypeList(false));
			modelAndView.setViewName("payroll_india/org/addForm");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/itDeclaration")
	public String itDeclaration(HttpServletRequest request,
			@RequestParam(defaultValue = "false") boolean autoSelectUser) {
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				List<SourceleadTaxFormDTO> formList = taxService.getTaxFormTypeList(false);
				return "redirect:/app/itDeclaration/getForm?uuid=" + formList.get(0).getUuid()
						+ (autoSelectUser ? "&autoSelectUser=" + autoSelectUser: "");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "";
	}

	@RequestMapping(value = { "/forms/getForm", "/forms/editForm", "/forms/viewForm" })
	public ModelAndView getDynamicTaxForm(HttpServletRequest request, @RequestParam String uuid,
			@RequestParam(defaultValue = "") String formUUID) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				SourceleadTaxFormDTO sourceleadTaxFormDTO = taxService.getTaxFormType(uuid, true, false);
				if (sourceleadTaxFormDTO != null) {
					TaxAdminController.setYears(modelAndView, null);
					OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
							"orgLocations");
					List<UserRole> userRoleList = userRoleService.getAllUsersBasedOrgLocationId(orgLocations.getId());
					modelAndView.addObject("userRoleList", userRoleList);
					
					List<Employees> employeeList = employeesService.getEmployeesByLocationId(orgLocations.getId());
					if(employeeList != null && !employeeList.isEmpty()) {
						for(Employees employee : employeeList) {
							employee.setPanNumber(candidateService.getCandidatePanNumberByUserId(employee.getUser().getId(), orgLocations.getId()));
//							System.out.println("User Id :" + employee.getUser().getId() + "UserName : " + employee.getUser().getFullName() + " Emp ID : " + employee.getEmployeeId() + " Pan : " + employee.getPanNumber() + " DOB : " + employee.getUser().getDateOfBirth());
							employee.setDateOfBirth(candidateService.getCandidateDateOfBirthByUserId(employee.getUser().getId(), orgLocations));
						}
					}
					modelAndView.addObject("employeeList", employeeList);
					
					if (!formUUID.isEmpty()) {
						modelAndView.addObject("editForm", request.getRequestURL().toString().contains("editForm"));
						modelAndView.addObject("viewForm", request.getRequestURL().toString().contains("viewForm"));
						modelAndView.addObject("uuid", uuid);
						SourceleadTaxITDeclarationSubmissionDTO itDeclarationSubmissionDTO = taxService
								.getITDeclarationSubmission(uuid, formUUID, orgLocations.getId());
						modelAndView.addObject("form", itDeclarationSubmissionDTO);
						modelAndView.addObject("documents",
								taxService.getUploadedDocumentsBySubmissionId(itDeclarationSubmissionDTO.getId()));
						modelAndView.setViewName(
								"payroll_india/org/" + sourceleadTaxFormDTO.getName().toLowerCase().replaceAll(" ", "_")
										+ "_submitted_form");
					} else {
						modelAndView.addObject("form", sourceleadTaxFormDTO);
						modelAndView.setViewName("payroll_india/org/"
								+ sourceleadTaxFormDTO.getName().toLowerCase().replaceAll(" ", "_") + "_form");
					}
				} else
					modelAndView.setViewName("errorPage");
			} else {
				modelAndView.setViewName("login");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/itDeclaration/getForm")
	public ModelAndView getDynamicTaxForm(HttpServletRequest request, @RequestParam String uuid,
			@RequestParam(defaultValue = "false") boolean autoSelectUser) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				SourceleadTaxFormDTO sourceleadTaxFormDTO = taxService.getTaxFormType(uuid, true, false);

				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				List<Employees> employeeList = employeesService.getEmployeesByLocationId(orgLocations.getId());
				if(employeeList != null && !employeeList.isEmpty()) {
					for(Employees employee : employeeList) {
						employee.setPanNumber(candidateService.getCandidatePanNumberByUserId(employee.getUser().getId(), orgLocations.getId()));
//						System.out.println("User Id :" + employee.getUser().getId() + "UserName : " + employee.getUser().getFullName() + " Emp ID : " + employee.getEmployeeId() + " Pan : " + employee.getPanNumber() + " DOB : " + employee.getUser().getDateOfBirth());
						employee.setDateOfBirth(candidateService.getCandidateDateOfBirthByUserId(employee.getUser().getId(), orgLocations));
					}
				}
				modelAndView.addObject("employeeList", employeeList);
				
				modelAndView.addObject("form", sourceleadTaxFormDTO);
				modelAndView.addObject("formList", taxService.getTaxFormTypeList(false));
				modelAndView.addObject("activeFormTabUUID", uuid);
				TaxAdminController.setYears(modelAndView, null);
				List<UserRole> userRoleList = userRoleService.getAllUsersBasedOrgLocationId(orgLocations.getId());
				modelAndView.addObject("userRoleList", userRoleList);
				if (autoSelectUser) {
					modelAndView.addObject("user", user);
					modelAndView.addObject("autoSelectUser",autoSelectUser);
				}
				modelAndView.setViewName("payroll_india/org/"
						+ sourceleadTaxFormDTO.getName().toLowerCase().replaceAll(" ", "_") + "_form");
			} else {
				modelAndView.setViewName("login");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/itDeclaration/save")
	@ResponseBody
	public Response<Long> saveITDeclarationForm(HttpServletRequest request,
			SourceleadTaxITDeclarationSubmissionDTO itDeclarationSubmissionDTO) {
		Response<Long> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			if (user != null) {
				itDeclarationSubmissionDTO.setStatus(1);
				response = taxService.submitITDeclaration(itDeclarationSubmissionDTO, user, orgLocations);
				if (response.getResponse().equals(Result.SUCCESS.name())) {
					Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
					OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
							"orgLocations");
					if (itDeclarationSubmissionDTO.getDocumentList() != null && !itDeclarationSubmissionDTO.getDocumentList().isEmpty()) {
						taxService.saveUploadedDocuments(itDeclarationSubmissionDTO.getDocumentList(), staticPath,
								org.getId(), loginOrgLocation.getId(), response.getObject());
					}
					//delete attachments based on attachmentRemoveIds
					if (itDeclarationSubmissionDTO.getAttachmentRemoveIds() != null && !itDeclarationSubmissionDTO.getAttachmentRemoveIds().isEmpty()) {
						for (String attachmentRemoveId : itDeclarationSubmissionDTO.getAttachmentRemoveIds().split(",")) {
							taxService.deleteDocumentAttachment(Long.parseLong(attachmentRemoveId));
						}
					}
					// delete documents based on documentRemoveIdsIDs
					if (itDeclarationSubmissionDTO.getDocumentRemoveIds() != null && !itDeclarationSubmissionDTO.getDocumentRemoveIds().isEmpty()) {
						//from javascript we have a chance of getting duplicate documentIds, so we are assigning to Set<Long>
						Set<Long> docRemoveIdSet = new HashSet<>();
						for (String documentRemoveId : itDeclarationSubmissionDTO.getDocumentRemoveIds().split(",")) {
							docRemoveIdSet.add(Long.parseLong(documentRemoveId));
						}
						for(Long docId : docRemoveIdSet) {
							taxService.deleteDocument(docId);
						}
					}
				}
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	
	@RequestMapping(value = "/itDeclaration/list", method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<SourceleadTaxITDeclarationSubmissionDTO> getITDeclarationSubmissionList(HttpServletRequest request,
			@RequestParam(defaultValue = "") String uuid, @RequestParam(defaultValue = "") String financialYear,
			Paginator paginator, @RequestParam(defaultValue = "") String searchValue) {
		ResponseList<SourceleadTaxITDeclarationSubmissionDTO> response = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			if (user != null) {
				List<SourceleadTaxITDeclarationSubmissionDTO> itDeclarationSubmissionDTOList = taxService
						.getITDeclarationSubmissionList(orgLocations.getId(), uuid, financialYear, searchValue, paginator);
				response.setData(itDeclarationSubmissionDTOList);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
				response.setPaginator(paginator);
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
	
	@RequestMapping(value = "/itDeclaration/changeStatus")
	@ResponseBody
	public Response<String> changeITDeclarationStatus(HttpServletRequest request,
			@RequestParam(defaultValue = "0") long id, @RequestParam(defaultValue = "0") int status) {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				response = taxService.changeITDeclarationSubmissionStatus(id, status);
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	
	@RequestMapping(value = "/itDeclartionSubmissionExport")
	@ResponseBody
	public void exportItDeclarationSubmission(HttpServletRequest request, HttpServletResponse response,
			SourceleadTaxITDeclarationSubmissionDTO sourceleadTaxITDeclarationSubmissionDTO, String exportType,
			long formId) throws Exception {
		try {
			File file = null;
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			SourceleadTaxITDeclarationSubmission sourceleadTaxITDeclarationSubmissionDetails = taxService.getITDeclarationSubmissionDetails(formId);
			User user = taxService.getEmployeeDetails(sourceleadTaxITDeclarationSubmissionDetails.getUser().getId());
			if (sourceleadTaxITDeclarationSubmissionDetails != null) {
				if (exportType.equalsIgnoreCase("excel")) {
					file = taxService.exportItDeclarationSubmissionExcel(sourceleadTaxITDeclarationSubmissionDetails, loginUser, orgLoc.getId(), user);
				} else {
					file = taxService.exportItDeclarationSubmissionPdf(sourceleadTaxITDeclarationSubmissionDetails, loginUser, orgLoc.getId(), user);
				}
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

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
