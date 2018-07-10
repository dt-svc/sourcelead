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
import com.bis.sourcelead.dto.SourceleadTaxITDeclarationDTO;
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

	@Autowired
	private TaxService taxService;

	@Autowired
	UserRoleService userRoleService;

	// forms tab org/user

	@RequestMapping(value = "/forms/list")
	public ModelAndView getForms(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			Map<String, Boolean> yearList = new LinkedHashMap<>();
			Calendar c = Calendar.getInstance();
			c.roll(Calendar.YEAR, -2);
			for (int i = 0; i < 4; i++) {
				Calendar c1 = Calendar.getInstance();
				boolean isCurrent = c1.get(Calendar.YEAR) == c.get(Calendar.YEAR);
				yearList.put(c.get(Calendar.YEAR) + "", isCurrent);
				c.roll(Calendar.YEAR, 1);
			}
			modelAndView.addObject("yearList", yearList);

			// modelAndView.addObject("formList", taxService.getTaxFormTypeList(false));

			modelAndView.setViewName("tax/org/forms_list");
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

	
	@RequestMapping(value = "/itDeclaration/getForm")
	public ModelAndView getDynamicTaxForm(HttpServletRequest request, @RequestParam String uuid) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			SourceleadTaxFormDTO sourceleadTaxFormDTO = taxService.getTaxFormType(uuid, true, false);
			modelAndView.addObject("form", sourceleadTaxFormDTO);
			modelAndView.addObject("formList", taxService.getTaxFormTypeList(false));
			modelAndView.addObject("activeFormTabUUID", uuid);

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
	public Response<String> saveITDeclarationForm(HttpServletRequest request, SourceleadTaxITDeclarationDTO itDeclarationDTO) {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {

				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
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
