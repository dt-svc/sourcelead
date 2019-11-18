package com.bis.sourcelead.web;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CentralTaxDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadTaxChallanDTO;
import com.bis.sourcelead.dto.SourceleadTaxChallanItemDTO;
import com.bis.sourcelead.dto.SourceleadTaxChallanItemSubFormFieldDTO;
import com.bis.sourcelead.dto.SourceleadTaxFormDTO;
import com.bis.sourcelead.dto.SourceleadTaxITDeclarationDTO;
import com.bis.sourcelead.dto.SourceleadTaxITDeclarationItemDTO;
import com.bis.sourcelead.dto.StateTaxDTO;
import com.bis.sourcelead.dto.helper.Paginator;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadTaxType;
import com.bis.sourcelead.entity.States;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.service.TaxService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class TaxAdminControllerPMS {

	private static final Logger logger = LoggerFactory.getLogger(TaxAdminControllerPMS.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	private StatesService statesService;

	@Autowired
	private TaxService taxService;

	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	private CommonUtil commonUtil;

	public static void setYears(ModelAndView modelAndView, String selectedYear) {
		Map<String, Boolean> yearList = new LinkedHashMap<>();
		Calendar c = Calendar.getInstance();
		c.roll(Calendar.YEAR, -2);
		for (int i = 0; i < 4; i++) {
			Calendar c1 = Calendar.getInstance();
			boolean isCurrent = (selectedYear != null && !selectedYear.isEmpty())
					? Integer.parseInt(selectedYear.split("-")[0]) == c.get(Calendar.YEAR)
					: c1.get(Calendar.YEAR) == c.get(Calendar.YEAR);

			String fromYear = c.get(Calendar.YEAR) + "";
			c.roll(Calendar.YEAR, 1);
			String toYear = c.get(Calendar.YEAR) + "";
			yearList.put(fromYear + "-" + toYear, isCurrent);
		}
		modelAndView.addObject("yearList", yearList);
	}

	private void setStates(ModelAndView modelAndView) throws Exception {
		List<States> stateList = statesService.getAllStatesByCountry("India");
		modelAndView.addObject("stateList", stateList);
	}

	// state tab
	@RequestMapping(value = "/stateTax/list")
	public ModelAndView stateTaxList(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			commonUtil.highLightMenu(Constants.TAXES, request);
			setYears(modelAndView, null);
			setStates(modelAndView);
			modelAndView.addObject("activeTaxTab", "state");
			modelAndView.setViewName("payroll_india/stateTax");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/stateTax/create")
	public ModelAndView addStateTaxPage(HttpServletRequest request,
			@RequestParam(defaultValue = "0") String statetaxuuid) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			StateTaxDTO stateTaxDTO = new StateTaxDTO();
			List<SourceleadTaxType> taxTypeList = taxService.getAllTaxesByType(1, false);
			modelAndView.addObject("taxTypes", taxTypeList);
			stateTaxDTO = taxService.getStateTax(statetaxuuid);
			modelAndView.addObject("stateTax", stateTaxDTO);
			setYears(modelAndView, stateTaxDTO.getFinancialYear());
			setStates(modelAndView);

			modelAndView.addObject("activeTaxTab", "createStateTax");
			modelAndView.setViewName("payroll_india/addStateTax");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}


	@RequestMapping(value = "/stateTax/add")
	@ResponseBody
	public Response<Long> addStateTax(HttpServletRequest request, StateTaxDTO stateTaxDTO) {
		Response<Long> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null && Validator.validate(stateTaxDTO)) {
				long result = taxService.addStateTax(stateTaxDTO);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
				response.setObject(result);
			} else if (user == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	@RequestMapping(value = "/stateTax/taxSummary")
	public ModelAndView stateTaxSummary(HttpServletRequest request,
			@RequestParam(defaultValue = "0") String statetaxuuid) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			StateTaxDTO stateTaxDTO = taxService.getStateTax(statetaxuuid);
			modelAndView.addObject("stateTax", stateTaxDTO);
			modelAndView.setViewName("payroll_india/stateTaxSummary");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/centralTax/taxSummary")
	public ModelAndView centralTaxSummary(HttpServletRequest request,
			@RequestParam(defaultValue = "0") String centraltaxuuid) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			CentralTaxDTO centralTaxDTO = taxService.getCentralTax(centraltaxuuid);
			modelAndView.addObject("centralTax", centralTaxDTO);
			modelAndView.setViewName("payroll_india/centralTaxSummary");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/getCentralTaxes", method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<CentralTaxDTO> getCentralTaxes(HttpServletRequest request,
			@RequestParam(defaultValue = "") String yearFrequency) {
		ResponseList<CentralTaxDTO> response = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				List<CentralTaxDTO> centralTaxList = taxService.getCentralTaxList(yearFrequency);
				response.setData(centralTaxList);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
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

	@RequestMapping(value = "/stateTax/changeStatus")
	@ResponseBody
	public Response<String> changeStateTaxStatus(HttpServletRequest request,
			@RequestParam(defaultValue = "0") long taxId) {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				response = taxService.changeStateTaxStatus(taxId);
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

	@RequestMapping(value = "/getStateTaxes", method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<StateTaxDTO> getStateTaxes(HttpServletRequest request,
			@RequestParam(defaultValue = "") String yearFrequency, @RequestParam(defaultValue = "0") long stateId,
			Paginator paginator) {
		ResponseList<StateTaxDTO> response = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				List<StateTaxDTO> stateTaxList = taxService.getStateTaxList(yearFrequency, stateId, paginator);
				response.setData(stateTaxList);
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

	// central tab
	@RequestMapping(value = "/centralTax/list")
	public ModelAndView centralTaxList(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			setYears(modelAndView, null);
			modelAndView.addObject("activeTaxTab", "central");
			modelAndView.setViewName("payroll_india/centralTax");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/centralTax/create")
	public ModelAndView addCentralTax(HttpServletRequest request,
			@RequestParam(value = "centraltaxuuid", required = false) String centraltaxuuid) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			CentralTaxDTO centralTaxDTO = new CentralTaxDTO();
			List<SourceleadTaxType> taxTypeList = taxService.getAllTaxesByType(2, false);
			modelAndView.addObject("taxTypes", taxTypeList);
			if (centraltaxuuid != null) {
				centralTaxDTO = taxService.getCentralTax(centraltaxuuid);
			}
			modelAndView.addObject("centralTax", centralTaxDTO);
			setYears(modelAndView, centralTaxDTO.getCtfinancialYear());
			modelAndView.addObject("activeTaxTab", "createCentralTax");
			modelAndView.setViewName("payroll_india/addCentralTax");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/centralTax/add", method = RequestMethod.POST)
	@ResponseBody
	public Response<Long> saveCentralTax(CentralTaxDTO centralTaxDTO, HttpServletRequest request) {

		Response<Long> response = new Response<>();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		try {
			if (user != null && orgLocations != null) {
				if (Validator.validate(centralTaxDTO)) {
					long result = taxService.saveCentralTax(centralTaxDTO, user, orgLocations);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
					response.setObject(result);
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;

	}

	@RequestMapping(value = "/centralTax/changeStatus")
	@ResponseBody
	public Response<String> changeCentralTaxStatus(HttpServletRequest request,
			@RequestParam(defaultValue = "0") long taxId) {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				response = taxService.changeCentralTaxStatus(taxId);
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

	// forms tab admin

	@RequestMapping(value = "/form/list")
	public ModelAndView formsList(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			setYears(modelAndView, null);
			modelAndView.addObject("activeTaxTab", "forms");
			modelAndView.setViewName("payroll_india/form_list");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/form/list", method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<SourceleadTaxFormDTO> getTaxForms(HttpServletRequest request) {
		ResponseList<SourceleadTaxFormDTO> response = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				// getList of forms
				response.setData(taxService.getTaxFormTypeList(true));
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
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

	@RequestMapping(value = { "/form/create", "/form/view" })
	public ModelAndView addTaxFormPage(HttpServletRequest request, @RequestParam(defaultValue = "") String uuid) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			List<SourceleadTaxType> taxTypeList = taxService.getAllTaxesByType(1, false);
			modelAndView.addObject("taxTypes", taxTypeList);
			boolean isEditable = request.getRequestURL().toString().contains("create");
			modelAndView.addObject("isEditable", isEditable);
			if (!uuid.isEmpty()) {
				SourceleadTaxFormDTO sourceleadTaxFormDTO = taxService.getTaxFormType(uuid, true, isEditable);
				modelAndView.addObject("form", sourceleadTaxFormDTO);
				modelAndView.addObject("formId", sourceleadTaxFormDTO.getId());
			}
			modelAndView.addObject("formTypes", taxService.getTaxFormTypeList(false));
			modelAndView.addObject("activeTaxTab", "forms");
			modelAndView.addObject("formTabType", isEditable ? "createForm" : "viewForm");
			modelAndView.setViewName("payroll_india/addForm");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/form/getDynamicForm")
	public ModelAndView getDynamicTaxForm(HttpServletRequest request, @RequestParam long formId,
			@RequestParam(defaultValue = "false") boolean isEditable) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			SourceleadTaxFormDTO sourceleadTaxFormDTO = taxService.getTaxFormType(formId, true, isEditable);
			modelAndView.addObject("isEditable", isEditable);
			modelAndView.addObject("form", sourceleadTaxFormDTO);
			modelAndView.addObject("activeTaxTab", "forms");
			modelAndView
					.setViewName("payroll_india/" + sourceleadTaxFormDTO.getName().toLowerCase().replaceAll(" ", "_") + "_form");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/form/addITDeclarationSection", method = RequestMethod.POST)
	@ResponseBody
	public Response<Long> addItDeclarationSection(HttpServletRequest request,
			SourceleadTaxITDeclarationDTO sourceleadTaxSectionDTO) {
		Response<Long> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				// Add ItDeclarationSection
				long result = taxService.addITDeclarationSection(sourceleadTaxSectionDTO);
				if (result > 0) {
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else if (result == -1) {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				} else {
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
				}
				response.setObject(result);
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

	@RequestMapping(value = "/form/addITDeclarationSectionItem", method = RequestMethod.POST)
	@ResponseBody
	public Response<Long> addItDeclarationSectionItem(HttpServletRequest request,
			SourceleadTaxITDeclarationItemDTO sectionItemDTO) {
		Response<Long> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				long result = taxService.addITDeclarationSectionItem(sectionItemDTO);
				if (result > 0) {
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else if (result == -1) {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				} else {
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
				}
				response.setObject(result);
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

	@RequestMapping(value = "/form/changeStatus")
	@ResponseBody
	public Response<String> changeTaxFormStatus(HttpServletRequest request, @RequestParam(defaultValue = "0") long id) {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				response = taxService.changeTaxFormStatus(id);
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

	@RequestMapping(value = "/form/itDeclaration/changeStatus")
	@ResponseBody
	public Response<String> changeITDeclarationStatus(HttpServletRequest request,
			@RequestParam(defaultValue = "0") long id, @RequestParam(defaultValue = "0") long type) {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				if (type == 3) {
					response = taxService.changeITDeclarationSectionStatus(id);
				} else if (type == 4) {
					response = taxService.changeITDeclarationSectionItemStatus(id);
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

	@RequestMapping(value = "/form/addITChallanSectionItem", method = RequestMethod.POST)
	@ResponseBody
	public Response<Long> addITChallanSectionItem(HttpServletRequest request,
			SourceleadTaxChallanItemDTO sectionItemDTO) {
		Response<Long> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				long addChallanSectionItemId = taxService.addITChallanSectionItem(sectionItemDTO);
				if (sectionItemDTO.getOptionData() != null) {
					for (SourceleadTaxChallanItemSubFormFieldDTO optionData : sectionItemDTO.getOptionData()) {
						taxService.addSourceleadTaxChallanItemSubFormField(optionData, addChallanSectionItemId);
					}
				}
				if (addChallanSectionItemId > 0) {
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else if (addChallanSectionItemId == -1) {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				} else {
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
				}
				response.setObject(addChallanSectionItemId);
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

	@RequestMapping(value = "/form/addITChallanSection", method = RequestMethod.POST)
	@ResponseBody
	public Response<Long> addItChallanSection(HttpServletRequest request,
			SourceleadTaxChallanDTO sourceleadTaxSectionDTO) {
		Response<Long> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				// Add ITChallanSection
				long result = taxService.addItChallanSection(sourceleadTaxSectionDTO);
				if (result > 0) {
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else if (result == -1) {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				} else {
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
				}
				response.setObject(result);
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

	@RequestMapping(value = "/form/editChallanItem", method = RequestMethod.POST)
	public ModelAndView getChallanItemDetails(HttpServletRequest request,
			SourceleadTaxChallanItemDTO sourceleadTaxChallanItemDTO, @RequestParam(value = "itemRowId") Long itemRowId)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		try {
			SourceleadTaxChallanItemDTO ChallanItemDTO = taxService.getChallanItemDetails(itemRowId);
			modelAndView.addObject("form", ChallanItemDTO);
			modelAndView.setViewName("payroll_india/org/it_Challan_CustomForm");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}


	
	@RequestMapping(value = "/form/changeChallanItemStatus", method = RequestMethod.POST)
	@ResponseBody
	public Response<String> changeChallanItemStatus(HttpServletRequest request,
			@RequestParam(defaultValue = "0") long itemId) {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				response = taxService.changeChallanItemStatus(itemId);
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


	@RequestMapping(value = "/form/changeChallanSectionStatus", method = RequestMethod.POST)
	@ResponseBody
	public Response<String> changeSectionStatus(HttpServletRequest request,
			@RequestParam(defaultValue = "0") long sectionId) {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				response = taxService.changeSectionStatus(sectionId);
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