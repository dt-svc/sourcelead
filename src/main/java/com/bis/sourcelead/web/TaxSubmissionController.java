package com.bis.sourcelead.web;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.SourceleadTaxFormDTO;
import com.bis.sourcelead.dto.SourceleadTaxITDeclarationSubmissionDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.TaxService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class TaxSubmissionController {
	private static final Logger logger = LoggerFactory.getLogger(TaxAdminController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Value("${static.path}")
	private String staticPath;

	@Autowired
	private TaxService taxService;

	@Autowired
	UserRoleService userRoleService;

	// forms tab org/user

	private void setYears(ModelAndView modelAndView, int selectedYear) {
		Map<String, Boolean> yearList = new LinkedHashMap<>();
		Calendar c = Calendar.getInstance();
		c.roll(Calendar.YEAR, -2);
		for (int i = 0; i < 4; i++) {
			Calendar c1 = Calendar.getInstance();
			boolean isCurrent = selectedYear > 0 ? selectedYear == c.get(Calendar.YEAR) : c1.get(Calendar.YEAR) == c.get(Calendar.YEAR);
			yearList.put(c.get(Calendar.YEAR) + "", isCurrent);
			c.roll(Calendar.YEAR, 1);
		}
		modelAndView.addObject("yearList", yearList);
	}

	@RequestMapping(value = "/forms/list")
	public ModelAndView getForms(HttpServletRequest request, @RequestParam(defaultValue = "0") int year,
			@RequestParam(defaultValue = "") String uuid) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			String financialYear = "";
			Calendar c = Calendar.getInstance();
			if (year > 0) {
				c.set(Calendar.YEAR, year);
				c.roll(Calendar.YEAR, true);
				financialYear = year + "-" + c.get(Calendar.YEAR);
			} else {
				Calendar c1 = Calendar.getInstance();
				c1.set(Calendar.YEAR, c.get(Calendar.YEAR));
				c1.roll(Calendar.YEAR, true);
				financialYear = c.get(Calendar.YEAR) + "-" + c1.get(Calendar.YEAR);
			}
			
			if (!uuid.isEmpty()) {
				modelAndView.addObject("uuid", uuid);
				modelAndView.addObject("formList", taxService.getITDeclarationSubmissionList(uuid, financialYear));
				modelAndView.setViewName("tax/org/form_list_by_formType");
			} else {
				setYears(modelAndView, year);
				modelAndView.addObject("formList", taxService.getTaxFormTypeListForSubmission(financialYear));
				modelAndView.setViewName("tax/org/forms_list");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/forms/create")
	public ModelAndView createForm(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView.addObject("formList", taxService.getTaxFormTypeList(false));
			modelAndView.setViewName("tax/org/addForm");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/itDeclaration")
	public String itDeclaration(HttpServletRequest request) {
		try {
			List<SourceleadTaxFormDTO> formList = taxService.getTaxFormTypeList(false);
			return "redirect:/app/itDeclaration/getForm?uuid=" + formList.get(0).getUuid();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "";
	}


//	@RequestMapping(value = { "/forms/getForm", "/forms/editForm", "/forms/viewForm" })
//	public ModelAndView getDynamicTaxForm(HttpServletRequest request, @RequestParam String uuid,
//			@RequestParam(defaultValue = "") String formUUID) {
//		ModelAndView modelAndView = new ModelAndView();
//		try {
//			SourceleadTaxFormDTO sourceleadTaxFormDTO = taxService.getTaxFormType(uuid, true, false);
//			if (sourceleadTaxFormDTO != null) {
//				setYears(modelAndView, 0);
//				if (!formUUID.isEmpty()) {
//					boolean isEditable = request.getRequestURL().toString().contains("editForm");
//					modelAndView.addObject("isEditable", isEditable);
//					modelAndView.addObject("uuid", uuid);
//					SourceleadTaxITDeclarationSubmissionDTO itDeclarationSubmissionDTO = taxService.getITDeclarationSubmission(uuid,
//							formUUID);
//					modelAndView.addObject("form", itDeclarationSubmissionDTO);
//					modelAndView.addObject("documents", taxService.getUploadedDocumentsBySubmissionId(itDeclarationSubmissionDTO.getId()));
//					modelAndView.setViewName("tax/org/" + sourceleadTaxFormDTO.getName().toLowerCase().replaceAll(" ", "_") + "_form");
//				} else {
//					// modelAndView.addObject("activeFormTabUUID", uuid);
//					// setYears(modelAndView, 0);
//					modelAndView.addObject("form", sourceleadTaxFormDTO);
//					OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
//					List<UserRole> userRoleList = userRoleService.getAllUsersBasedOrgLocationId(orgLocations.getId());
//					modelAndView.addObject("userRoleList", userRoleList);
//					modelAndView.setViewName("tax/org/" + sourceleadTaxFormDTO.getName().toLowerCase().replaceAll(" ", "_") + "_form");
//				}
//			} else
//				modelAndView.setViewName("errorPage");
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//		}
//		return modelAndView;
//	}
	
	@RequestMapping(value = { "/forms/getForm", "/forms/editForm", "/forms/viewForm" })
	public ModelAndView getDynamicTaxForm(HttpServletRequest request, @RequestParam String uuid,
			@RequestParam(defaultValue = "") String formUUID) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			SourceleadTaxFormDTO sourceleadTaxFormDTO = taxService.getTaxFormType(uuid, true, false);
			if (sourceleadTaxFormDTO != null) {
//				setYears(modelAndView, 0);
				TaxAdminController.setYears(modelAndView, null);
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				List<UserRole> userRoleList = userRoleService.getAllUsersBasedOrgLocationId(orgLocations.getId());
				modelAndView.addObject("userRoleList", userRoleList);
				if (!formUUID.isEmpty()) {
					boolean isEditable = request.getRequestURL().toString().contains("editForm");
					modelAndView.addObject("isEditable", isEditable);
					modelAndView.addObject("uuid", uuid);
					SourceleadTaxITDeclarationSubmissionDTO itDeclarationSubmissionDTO = taxService.getITDeclarationSubmission(uuid,
							formUUID);
					modelAndView.addObject("form", itDeclarationSubmissionDTO);
					modelAndView.addObject("documents", taxService.getUploadedDocumentsBySubmissionId(itDeclarationSubmissionDTO.getId()));
					modelAndView.setViewName("tax/org/" + sourceleadTaxFormDTO.getName().toLowerCase().replaceAll(" ", "_") + "_submitted_form");
				} else {
					// modelAndView.addObject("activeFormTabUUID", uuid);
					// setYears(modelAndView, 0);
					modelAndView.addObject("form", sourceleadTaxFormDTO);
					modelAndView.setViewName("tax/org/" + sourceleadTaxFormDTO.getName().toLowerCase().replaceAll(" ", "_") + "_form");
				}
			} else
				modelAndView.setViewName("errorPage");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/itDeclaration/getForm")
	public ModelAndView getDynamicTaxForm(HttpServletRequest request, @RequestParam String uuid) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			SourceleadTaxFormDTO sourceleadTaxFormDTO = taxService.getTaxFormType(uuid, true, false);
			modelAndView.addObject("form", sourceleadTaxFormDTO);
			modelAndView.addObject("formList", taxService.getTaxFormTypeList(false));
			modelAndView.addObject("activeFormTabUUID", uuid);
			TaxAdminController.setYears(modelAndView, null);
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			List<UserRole> userRoleList = userRoleService.getAllUsersBasedOrgLocationId(orgLocations.getId());
			modelAndView.addObject("userRoleList", userRoleList);
			modelAndView.setViewName("tax/org/" + sourceleadTaxFormDTO.getName().toLowerCase().replaceAll(" ", "_") + "_form");
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
			if (user != null) {
				itDeclarationSubmissionDTO.setStatus(1);
				response = taxService.submitITDeclaration(itDeclarationSubmissionDTO, user);
//				response.setObject(1L);
				response.setResponse(Result.SUCCESS.name());
				if (response.getResponse().equals(Result.SUCCESS.name())) {
					Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
					OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

					if (itDeclarationSubmissionDTO.getDocumentList() != null && !itDeclarationSubmissionDTO.getDocumentList().isEmpty()) {
						taxService.saveUploadedDocuments(itDeclarationSubmissionDTO.getDocumentList(), staticPath, org.getId(),
								loginOrgLocation.getId(), response.getObject());
					}
				}
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
}
